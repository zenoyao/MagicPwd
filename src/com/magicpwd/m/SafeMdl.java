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

import com.magicpwd.__i.IEditItem;
import com.magicpwd._comn.mpwd.MpwdHeader;
import com.magicpwd._comn.mpwd.MpwdDetail;
import com.magicpwd.__a.AEditItem;
import com.magicpwd._comn.item.GuidItem;
import com.magicpwd._comn.item.HintItem;
import com.magicpwd._comn.item.LogoItem;
import com.magicpwd._comn.item.MetaItem;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._util.Char;
import com.magicpwd._util.Logs;
import com.magicpwd._util.Util;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.d.db.DBAccess;

/**
 *
 * @author Amon
 */
public abstract class SafeMdl
{

    protected MpwdHeader mkey;
    protected UserMdl userMdl;
    protected java.util.ArrayList<IEditItem> itemList;
    /**
     * 临时数据保存
     */
    private boolean interim;
    private boolean modified;
    private javax.crypto.Cipher dCipher;
    private javax.crypto.Cipher eCipher;

    public SafeMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
        mkey = new MpwdHeader();
        itemList = new java.util.ArrayList<IEditItem>();
    }

    public abstract void initHead();

    public abstract void initBody();

    public abstract void clear();

    /**
     * @return the dCipher
     */
    public javax.crypto.Cipher getDCipher()
    {
        if (dCipher == null)
        {
            try
            {
                dCipher = javax.crypto.Cipher.getInstance(ConsEnv.NAME_CIPHER);
                dCipher.init(javax.crypto.Cipher.DECRYPT_MODE, UserMdl.safeKey);
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
                //Lang.showMesg(null, LangRes.P30FAA04, "系统错误：系统无法加载密码算法！");
                Logs.end();
                System.exit(0);
            }
        }
        return dCipher;
    }

    /**
     * @return the eCipher
     */
    public javax.crypto.Cipher getECipher()
    {
        if (eCipher == null)
        {
            try
            {
                eCipher = javax.crypto.Cipher.getInstance(ConsEnv.NAME_CIPHER);
                eCipher.init(javax.crypto.Cipher.ENCRYPT_MODE, UserMdl.safeKey);
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
                //Lang.showMesg(null, LangRes.P30FAA04, "系统错误：系统无法加载密码算法！");
                Logs.end();
                System.exit(0);
            }
        }
        return eCipher;
    }

    /**
     * 解密处理
     * @param text
     * @throws Exception
     */
    public String deCrypt(String text) throws Exception
    {
        return new String(getDCipher().doFinal(Char.toBytes(text, UserMdl.safeKey.getMask())), ConsEnv.FILE_ENCODING);
    }

    /**
     * 对文件进行加密或解密处理
     * @param c 密码算法
     * @param s 来源文件
     * @param d 写入文件
     * @throws Exception
     */
    public final void deCrypt(java.io.File s, java.io.File d) throws Exception
    {
        byte[] buf = new byte[1024];
        java.io.FileInputStream fis = new java.io.FileInputStream(s);
        java.io.FileOutputStream fos = new java.io.FileOutputStream(d);
        int len = fis.read(buf);
        while (len >= 0)
        {
            fos.write(getDCipher().update(buf, 0, len));
            len = fis.read(buf);
        }
        fos.write(getDCipher().doFinal());
        fos.flush();
        fos.close();
        fis.close();
    }

    /**
     * 加密处理
     * @param text
     * @throws Exception
     */
    public String enCrypt(String text) throws Exception
    {
        return Util.bytesToString(getECipher().doFinal(text.getBytes(ConsEnv.FILE_ENCODING)), UserMdl.safeKey.getMask());
    }

    /**
     * 对文件进行加密或解密处理
     * @param c 密码算法
     * @param s 来源文件
     * @param d 写入文件
     * @throws Exception
     */
    public final void enCrypt(java.io.File s, java.io.File d) throws Exception
    {
        byte[] buf = new byte[1024];
        java.io.FileInputStream fis = new java.io.FileInputStream(s);
        java.io.FileOutputStream fos = new java.io.FileOutputStream(d);
        int len = fis.read(buf);
        while (len >= 0)
        {
            fos.write(getECipher().update(buf, 0, len));
            len = fis.read(buf);
        }
        fos.write(getECipher().doFinal());
        fos.flush();
        fos.close();
        fis.close();
    }

    /**
     * 是否使用临时口令名称
     *
     * @return
     */
    public boolean isInterim()
    {
        return interim;
    }

    /**
     * @param interim
     */
    public void setInterim(boolean interim)
    {
        this.interim = interim;
    }

    /**
     * 数据是否被修改过
     *
     * @return
     */
    public boolean isModified()
    {
        return modified;
    }

    /**
     * @param modified
     */
    public void setModified(boolean modified)
    {
        this.modified = modified;
    }

    /**
     * 读取指定索引的密码数据
     *
     * @param mkeyHash
     */
    public void loadData(String mkeyHash) throws Exception
    {
        clear();
        mkey.setP30F0104(mkeyHash);
        DBA4000.readMpwdData(userMdl, mkey);
        deCrypt(mkey, itemList);
    }

    /**
     * 是否要更新原有数据
     *
     * @param histBack
     * @throws Exception
     */
    public void saveData(boolean histBack) throws Exception
    {
        mkey.setP30F0105(userMdl.getCode());
        mkey.setHistBack(histBack);
        enCrypt(mkey, itemList);
        DBAccess dba = new DBAccess();
        dba.init(userMdl);
        try
        {
            DBA4000.saveMpwdData(dba, mkey);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            dba.dispose();
        }
        clear();
    }

    public int getItemSize()
    {
        return itemList.size();
    }

    public String getKeysHash()
    {
        return mkey.getP30F0104();
    }

    /**
     * 向导初始化
     * @return
     */
    public IEditItem initGuid()
    {
        GuidItem guid = new GuidItem(userMdl);
        guid.setName(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        itemList.add(guid);
        return guid;
    }

    /**
     * 关键搜索
     * @return
     */
    public IEditItem initMeta()
    {
        MetaItem meta = new MetaItem(userMdl);
        itemList.add(meta);
        return meta;
    }

    /**
     * 徽标
     * @return
     */
    public IEditItem initLogo()
    {
        LogoItem logo = new LogoItem(userMdl);
        itemList.add(logo);
        return logo;
    }

    /**
     * 过时提醒
     * @return
     */
    public IEditItem initHint()
    {
        HintItem hint = new HintItem(userMdl);
        itemList.add(hint);
        return hint;
    }

    public IEditItem getItemAt(int index)
    {
        return itemList.get(index);
    }

    public void setKeysLabel(int label)
    {
        mkey.setP30F0102(label);
        DBA4000.saveKeysData(userMdl, mkey);
    }

    public void setKeysMajor(int major)
    {
        mkey.setP30F0103(major);
        DBA4000.saveKeysData(userMdl, mkey);
    }

    private StringBuffer deCrypt(MpwdDetail pwds) throws Exception
    {
//        pwds.deCript(userMdl.getDCipher(), userMdl.getSec().getMask());
//        return pwds.getP30F0203();
        StringBuffer buf = pwds.getP30F0203();
        String tmp = buf.toString();
        return buf.delete(0, buf.length()).append(deCrypt(tmp));
    }

    /**
     * 数据解密处理
     *
     * @param dba
     */
    public final void deCrypt(MpwdHeader keys, java.util.List<IEditItem> list) throws Exception
    {
        // 查询数据是否为空
        StringBuffer text = deCrypt(keys.getMpwd());
        if (text.length() < 1)
        {
            return;
        }

        // Guid
        GuidItem guid = new GuidItem(userMdl);
        guid.setData(keys.getP30F0106());
        guid.setName(keys.getP30F0107());
        guid.setSpec(GuidItem.SPEC_GUID_TPLT, keys.getP30F0108());
        list.add(guid);

        // MetaItem
        MetaItem meta = new MetaItem(userMdl);
        meta.setName(keys.getP30F0109());
        meta.setData(keys.getP30F010A());
        list.add(meta);

        // LogoItem
        LogoItem logo = new LogoItem(userMdl);
        logo.setName(keys.getP30F010B());
        logo.setData(keys.getP30F010D());
        logo.setPath(keys.getP30F010C());
        list.add(logo);

        // HintItem
        HintItem hint = new HintItem(userMdl);
        hint.setData(keys.getP30F010E());
        hint.setName(keys.getP30F010F());
        hint.setMgtd(mkey.getMgtd());
        list.add(hint);

        // 处理每一个数据
        java.util.StringTokenizer st = new java.util.StringTokenizer(text.toString(), ConsDat.SP_SQL_EE);
        String name;
        String data;
        String spec;
        int dn;
        int dd;
        int ds;
        int type;
        String t;
        IEditItem item;
        while (st.hasMoreTokens())
        {
            t = st.nextToken() + ConsDat.SP_SQL_KV;
            dn = t.indexOf(ConsDat.SP_SQL_KV);
            dd = t.indexOf(ConsDat.SP_SQL_KV, dn + 1);
            ds = t.indexOf(ConsDat.SP_SQL_KV, dd + 1);

            type = Integer.parseInt(t.substring(0, dn));
            name = t.substring(dn + 1, dd);
            data = t.substring(dd + 1, ds);
            spec = t.substring(ds + 1, t.length());
            item = AEditItem.getInstance(userMdl, type, name, data);
            if (spec.length() > 0)
            {
                item.deCodeSpec(spec, ConsDat.SP_SQL_KV);
            }
            list.add(item);
        }
    }

    private StringBuffer enCrypt(MpwdDetail pwds) throws Exception
    {
//        pwds.enCrypt(userMdl.getECipher(), userMdl.getSec().getMask());
//        return pwds.getP30F0203();
        StringBuffer buf = pwds.getP30F0203();
        String tmp = buf.toString();
        return buf.delete(0, buf.length()).append(enCrypt(tmp));
    }

    /**
     * 数据加密处理
     *
     * @param dba
     */
    public final void enCrypt(MpwdHeader keys, java.util.List<IEditItem> list) throws Exception
    {
        MpwdDetail pwds = keys.getMpwd();
        StringBuffer text = pwds.getP30F0203();
        text.delete(0, text.length());

        // Guid
        GuidItem guid = (GuidItem) list.get(ConsEnv.PWDS_HEAD_GUID);
        keys.setP30F0106(guid.getData());
        keys.setP30F0107(guid.getName());
        keys.setP30F0108(guid.getSpec(GuidItem.SPEC_GUID_TPLT));

        // MetaItem
        MetaItem meta = (MetaItem) list.get(ConsEnv.PWDS_HEAD_META);
        keys.setP30F0109(interim ? '<' + meta.getName() + '_' + keys.getP30F0107() + '>' : meta.getName());
        keys.setP30F010A(meta.getData());
        interim = false;

        // LogoItem
        LogoItem logo = (LogoItem) list.get(ConsEnv.PWDS_HEAD_LOGO);
        keys.setP30F010B(logo.getName());
        keys.setP30F010D(logo.getData());
        keys.setP30F010C(logo.getPath());

        // HintItem
        HintItem hint = (HintItem) list.get(ConsEnv.PWDS_HEAD_HINT);
        keys.setP30F010E(hint.getData());
        keys.setP30F010F(hint.getName());
        keys.setMgtd(hint.getMgtd());

        // 字符串拼接
        IEditItem item;
        for (int i = ConsEnv.PWDS_HEAD_SIZE, j = list.size(); i < j; i += 1)
        {
            item = list.get(i);
            text.append(item.getType());
            text.append(ConsDat.SP_SQL_KV);
            text.append(item.getName());
            text.append(ConsDat.SP_SQL_KV);
            text.append(item.getData());
            text.append(item.enCodeSpec(ConsDat.SP_SQL_KV));
            text.append(ConsDat.SP_SQL_EE);
        }

        enCrypt(pwds);
    }
}
