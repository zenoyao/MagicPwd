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
package com.magicpwd._comn.mpwd;

import com.magicpwd._cons.ConsEnv;
import com.magicpwd._util.Char;
import com.magicpwd._util.Util;
import javax.crypto.Cipher;

/**
 * 口令数据
 * @author Amon
 */
public class MpwdDetail
{

    /**
     * 内容索引
     */
    private int P30F0201;
    /**
     * 口令索引
     */
    private String P30F0202;
    /**
     * 口令内容
     */
    private StringBuffer P30F0203 = new StringBuffer();

    public MpwdDetail()
    {
        setDefault();
    }

    public boolean loadPwds()
    {
        return true;
    }

    public boolean savePwds()
    {
        return true;
    }

    public final void setDefault()
    {
        P30F0202 = "";
        P30F0203.delete(0, P30F0203.length());
    }

    /**
     * @return the P30F0201
     */
    public int getP30F0201()
    {
        return P30F0201;
    }

    /**
     * @param P30F0201 the P30F0201 to set
     */
    public void setP30F0201(int P30F0201)
    {
        this.P30F0201 = P30F0201;
    }

    /**
     * @return the P30F0202
     */
    public String getP30F0202()
    {
        return P30F0202;
    }

    /**
     * @param P30F0202 the P30F0202 to set
     */
    public void setP30F0202(String P30F0202)
    {
        this.P30F0202 = P30F0202;
    }

    /**
     * 口令内容
     * @return the P30F0203
     */
    public StringBuffer getP30F0203()
    {
        return P30F0203;
    }

    /**
     * 解密处理
     */
    public void deCript(Cipher c, char[] m) throws Exception
    {
        String t = P30F0203.toString();
        P30F0203.delete(0, t.length()).append(new String(c.doFinal(Char.toBytes(t, m)), ConsEnv.FILE_ENCODING));
    }

    /**
     * 加密处理
     */
    public void enCrypt(Cipher c, char[] m) throws Exception
    {
        String t = P30F0203.toString();
        P30F0203.delete(0, t.length()).append(Util.bytesToString(c.doFinal(t.getBytes(ConsEnv.FILE_ENCODING)), m));
    }
}
