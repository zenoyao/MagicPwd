/*
 *  Copyright (C) 2010 Amon
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.magicpwd.m;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.magicpwd._cons.ConsCfg;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._util.Char;
import com.magicpwd._util.Logs;
import com.magicpwd._util.Util;

/**
 * 用户安全信息
 * @author Amon
 */
final class SafeKey implements Key
{

    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户口令
     */
    private String pwds;
    /**
     * 用户配置口令
     */
    private byte[] keys;
    /**
     * 口令转换字符
     */
    private char[] mask;
    private UserMdl userMdl;

    /**
     * 默认构造器
     */
    SafeKey(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    /**
     * 有参构造器
     * @param name
     * @param pwds
     * @param salt
     */
    SafeKey(String name, String pwds, String salt)
    {
    }

    @Override
    public final String getAlgorithm()
    {
        return ConsEnv.NAME_CIPHER;
    }

    @Override
    public final byte[] getEncoded()
    {
        return keys;
    }

    @Override
    public final String getFormat()
    {
        return "RAW";
    }

    /**
     * @return the usid
     */
    public final String getCode()
    {
        return userMdl.getCode();
    }

    final char[] getMask()
    {
        return mask;
    }

    /**
     * 用户登录
     * 
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    final boolean signIn() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        // 用户登录身份认证
        String text = userMdl.getCfg(ConsCfg.CFG_USER_INFO, "");
        if (!com.magicpwd._util.Char.isValidate(text))
        {
            return false;
        }
        byte[] temp = signInDigest();
        if (!text.equals(Util.bytesToString(temp, true)))
        {
            return false;
        }

        // 获取用户配置密文
        keys = cipherDigest();

        text = userMdl.getCfg(ConsCfg.CFG_USER_PKEY, "");
        temp = Char.toBytes(text, true);

        // 解密用户配置密文获得解密数据
        Cipher aes = Cipher.getInstance(ConsEnv.NAME_CIPHER);
        aes.init(Cipher.DECRYPT_MODE, this);
        temp = aes.doFinal(temp);
        System.arraycopy(temp, 16, keys, 0, 16);
        mask = new String(temp, 0, 16).toCharArray();
        pwds = null;
        return true;
    }

    final boolean signPb() throws Exception
    {
        return true;
    }

    /**
     * 网络登录
     * @return
     * @throws java.lang.Exception
     */
    final boolean signNw() throws Exception
    {
        return true;
    }

    /**
     * 修改登录口令
     * @param oldPwds
     * @param newPwds
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    final boolean signPk(String oldPwds, String newPwds) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        // 已有口令校验
        pwds = oldPwds;
        byte[] temp = signInDigest();
        if (!Util.bytesToString(temp, true).equals(userMdl.getCfg(ConsCfg.CFG_USER_INFO, "")))
        {
            return false;
        }

        // 摘要用户登录信息
        pwds = newPwds;
        temp = signInDigest();
        userMdl.setCfg(ConsCfg.CFG_USER_INFO, Util.bytesToString(temp, true));

        // 生成加密密钥及字符空间
        byte[] t = new byte[32];
        temp = new String(mask).getBytes();
        System.arraycopy(temp, 0, t, 0, temp.length);
        System.arraycopy(keys, 0, t, 16, keys.length);

        // 摘要用户加密信息
        temp = keys;
        keys = cipherDigest();

        // 加密安全数据
        Cipher aes = Cipher.getInstance(ConsEnv.NAME_CIPHER);
        aes.init(Cipher.ENCRYPT_MODE, this);
        keys = aes.doFinal(t);
        userMdl.setCfg(ConsCfg.CFG_USER_PKEY, Util.bytesToString(keys, true));

        // 恢复原有数据加密口令
        keys = temp;
        return true;
    }

    /**
     * 口令找回
     * @param usrName
     * @param secPwds
     * @return
     * @throws Exception
     */
    final boolean signFp(String usrName, StringBuffer secPwds) throws Exception
    {
        name = usrName;

        // 用户登录身份认证
        String text = userMdl.getCfg(ConsCfg.CFG_USER_SKEY, "");
        if (!com.magicpwd._util.Char.isValidate(text))
        {
            return false;
        }

        pwds = secPwds.toString();
        byte[] temp = signSkDigest();
        if (text.indexOf(Util.bytesToString(temp, true)) != 0)
        {
            return false;
        }

        // 获取用户配置密文
        keys = cipherDigest();

        text = text.substring(128);
        temp = Char.toBytes(text, true);

        // 解密用户配置密文获得解密数据
        Cipher aes = Cipher.getInstance(ConsEnv.NAME_CIPHER);
        aes.init(Cipher.DECRYPT_MODE, this);
        temp = aes.doFinal(temp);

        // 生成随机口令
        this.name = usrName;
        this.pwds = new String(generateUserChar());
        byte[] t = signInDigest();
        userMdl.setCfg(ConsCfg.CFG_USER_INFO, Util.bytesToString(t, true));

        this.keys = cipherDigest();
        aes.init(Cipher.ENCRYPT_MODE, this);
        t = aes.doFinal(temp);
        userMdl.setCfg(ConsCfg.CFG_USER_PKEY, Util.bytesToString(t, true));

        System.arraycopy(temp, 16, keys, 0, temp.length - 16);
        mask = new String(temp, 0, 16).toCharArray();
        secPwds.delete(0, secPwds.length()).append(pwds);
        return true;
    }

    /**
     * 设定安全口令
     * 
     * @param oldPwds
     * @param newPwds
     * @return
     * @throws Exception
     */
    final boolean signSk(String oldPwds, String secPwds) throws Exception
    {
        // 已有口令校验
        pwds = oldPwds;
        byte[] temp = signInDigest();
        if (!Util.bytesToString(temp, true).equals(userMdl.getCfg(ConsCfg.CFG_USER_INFO, "")))
        {
            return false;
        }

        // 认证信息
        this.pwds = secPwds;
        String sKey = Util.bytesToString(signSkDigest(), true);

        temp = new String(mask).getBytes();

        // 生成加密密钥及字符空间
        byte[] t = new byte[32];
        System.arraycopy(temp, 0, t, 0, temp.length);// 字符空间
        System.arraycopy(keys, 0, t, 16, keys.length);// 加密密钥

        // 摘要用户加密信息
        temp = keys;
        keys = cipherDigest();

        // 加密安全数据
        Cipher aes = Cipher.getInstance(ConsEnv.NAME_CIPHER);
        aes.init(Cipher.ENCRYPT_MODE, this);
        t = aes.doFinal(t);
        userMdl.setCfg(ConsCfg.CFG_USER_SKEY, sKey + Util.bytesToString(t, true));

        this.keys = temp;
        this.pwds = null;

        return true;
    }

    /**
     * 用户注册
     * 
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    final boolean signUp() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        if (com.magicpwd._util.Char.isValidate(userMdl.getCfg(ConsCfg.CFG_USER_INFO, "")))
        {
            return false;
        }

        // 摘要用户登录信息
        byte[] temp = signInDigest();
        userMdl.setCfg(ConsCfg.CFG_USER_INFO, Util.bytesToString(temp, true));

        // 摘要用户加密信息
        keys = cipherDigest();

        // 生成加密密钥及字符空间
        byte[] t = new byte[33];
        mask = generateDataChar();
        temp = new String(mask).getBytes();// 字符空间
        System.arraycopy(temp, 0, t, 0, temp.length);
        temp = generateDataKeys();// 加密密钥
        System.arraycopy(temp, 0, t, 16, temp.length);
        t[32] = 0;// 用户权限

        // 加密安全数据
        Cipher aes = Cipher.getInstance(ConsEnv.NAME_CIPHER);
        aes.init(Cipher.ENCRYPT_MODE, this);
        keys = temp;
        temp = aes.doFinal(t);
        userMdl.setCfg(ConsCfg.CFG_USER_PKEY, Util.bytesToString(temp, true));

        // 用户列表
        userMdl.setCfg(ConsCfg.CFG_USER, userMdl.getCfg(ConsCfg.CFG_USER, "") + name + ',');
        // 用户编码
        userMdl.setCfg(ConsCfg.CFG_USER_CODE, "00000000");
        // 用户名称
        userMdl.setCfg(ConsCfg.CFG_USER_NAME, name);
        return true;
    }

    /**
     * 用户退出
     * @return
     */
    final boolean signOx()
    {
        return true;
    }

    /**
     * @return the name
     */
    final String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    final void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the pwds
     */
    final String getPwds()
    {
        return pwds;
    }

    /**
     * @param pwds
     *            the pwds to set
     */
    final void setPwds(String pwds)
    {
        this.pwds = pwds;
    }

    /**
     * @return the salt
     */
    final String getSalt(String t)
    {
        return ConsEnv.USER_SALT[t.hashCode() & 3];
    }

    /**
     * 登录信息摘要
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    private byte[] signInDigest() throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance(ConsEnv.NAME_DIGEST);
        String s = new StringBuffer(name).append('@').append(pwds).append('/').toString();
        return md.digest((s + getSalt(s)).getBytes());
    }

    /**
     * 安全口令摘要
     * @return
     * @throws NoSuchAlgorithmException
     */
    private byte[] signSkDigest() throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance(ConsEnv.NAME_DIGEST);
        String s = new StringBuffer(name).append('$').append(pwds).append('#').toString();
        return md.digest((s + getSalt(s)).getBytes());
    }

    /**
     * 加密数据摘要
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    private byte[] cipherDigest() throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String s = new StringBuffer(name).append('&').append(pwds).append('!').toString();
        return md.digest((s + getSalt(s)).getBytes());
    }

    /**
     * 获取可用于数据库存储的掩码
     * @return
     */
    private char[] generateDataChar()
    {
        char[] c = new char[93];
        for (char i = 0; i < 6; i += 1)
        {
            c[i] = (char) (33 + i);
        }
        for (char i = 6; i < 93; i += 1)
        {
            c[i] = (char) (34 + i);
        }

        try
        {
            return Util.nextRandomKey(c, 16, false);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return null;
        }
    }

    private byte[] generateDataKeys()
    {
        byte[] b = new byte[16];
        new Random().nextBytes(b);
        return b;
    }

    /**
     * 
     * @return
     */
    private char[] generateUserChar()
    {
        char[] c = new char[93];
        for (char i = 0; i < 6; i += 1)
        {
            c[i] = (char) (33 + i);
        }
        for (char i = 6; i < 93; i += 1)
        {
            c[i] = (char) (34 + i);
        }

        try
        {
            return Util.nextRandomKey(c, 8, false);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return null;
        }
    }

    public boolean hasSkey()
    {
        return com.magicpwd._util.Char.isValidate(userMdl.getCfg(ConsCfg.CFG_USER_SKEY, ""), 224);
    }
}
