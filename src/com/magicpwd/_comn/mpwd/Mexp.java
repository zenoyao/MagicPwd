/*
 *  Copyright (C) 2011 Aven
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

import com.magicpwd._enum.ExpType;

/**
 * Application: MagicPwd
 * Author     : Aven
 * Encoding   : UTF-8
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class Mexp
{

    /**显示排序*/
    private int P30F0801;
    /**公式类型*/
    private int P30F0802;
    /**公式索引*/
    private String P30F0803;
    /**公式名*/
    private String P30F0804;
    /**公式体*/
    private String P30F0805;
    /**公式标题*/
    private String P30F0806;
    /**相关说明*/
    private String P30F0807;

    public Mexp()
    {
    }

    public Mexp(ExpType expType)
    {
        this.P30F0802 = expType.ordinal();
    }

    /**
     * @return the P30F0801
     */
    public int getP30F0801()
    {
        return P30F0801;
    }

    /**
     * @param P30F0801 the P30F0801 to set
     */
    public void setP30F0801(int P30F0801)
    {
        this.P30F0801 = P30F0801;
    }

    /**
     * @return the P30F0802
     */
    public int getP30F0802()
    {
        return P30F0802;
    }

    /**
     * @param P30F0802 the P30F0802 to set
     */
    public void setP30F0802(int P30F0802)
    {
        this.P30F0802 = P30F0802;
    }

    /**
     * 公式索引
     * @return the P30F0803
     */
    public String getP30F0803()
    {
        return P30F0803;
    }

    /**
     * 公式索引
     * @param P30F0803 the P30F0803 to set
     */
    public void setP30F0803(String P30F0803)
    {
        this.P30F0803 = P30F0803;
    }

    /**
     * 公式名
     * @return the P30F0804
     */
    public String getP30F0804()
    {
        return P30F0804;
    }

    /**
     * 公式名
     * @param P30F0804 the P30F0804 to set
     */
    public void setP30F0804(String P30F0804)
    {
        this.P30F0804 = P30F0804;
    }

    /**
     * 公式体
     * @return the P30F0805
     */
    public String getP30F0805()
    {
        return P30F0805;
    }

    /**
     * 公式体
     * @param P30F0805 the P30F0805 to set
     */
    public void setP30F0805(String P30F0805)
    {
        this.P30F0805 = P30F0805;
    }

    /**
     * @return the P30F0806
     */
    public String getP30F0806()
    {
        return P30F0806;
    }

    /**
     * @param P30F0806 the P30F0806 to set
     */
    public void setP30F0806(String P30F0806)
    {
        this.P30F0806 = P30F0806;
    }

    /**
     * @return the P30F0807
     */
    public String getP30F0807()
    {
        return P30F0807;
    }

    /**
     * @param P30F0807 the P30F0807 to set
     */
    public void setP30F0807(String P30F0807)
    {
        this.P30F0807 = P30F0807;
    }

    @Override
    public String toString()
    {
        return P30F0804;
    }
}
