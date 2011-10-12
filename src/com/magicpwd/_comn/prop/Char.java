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
package com.magicpwd._comn.prop;

/**
 * 口令字符空间
 * @author Amon
 */
public class Char
{

    /**
     * 显示排序
     */
    private int P30F2101;
    /**
     * 使用状态
     */
    private int P30F2102;
    /**
     * 空间索引
     */
    private String P30F2103;
    /**
     * 空间名称
     */
    private String P30F2104;
    /**
     * 空间提示
     */
    private String P30F2105;
    /**
     * 空间字符
     */
    private String P30F2106;
    /**
     * 相关说明
     */
    private String P30F2107;

    /**
     * 显示排序调整
     * @param step
     */
    public void addP30F2101(int step)
    {
        P30F2101 += step;
    }

    /**
     * 显示排序
     * @return the P30F2101
     */
    public int getP30F2101()
    {
        return P30F2101;
    }

    /**
     * 显示排序
     * @param P30F2101 the P30F2101 to set
     */
    public void setP30F2101(int P30F2101)
    {
        this.P30F2101 = P30F2101;
    }

    /**
     * 使用状态
     * @return the P30F2102
     */
    public int getP30F2102()
    {
        return P30F2102;
    }

    /**
     * 使用状态
     * @param P30F2102 the P30F2102 to set
     */
    public void setP30F2102(int P30F2102)
    {
        this.P30F2102 = P30F2102;
    }

    /**
     * 空间索引
     * @return the P30F2103
     */
    public String getP30F2103()
    {
        return P30F2103;
    }

    /**
     * 空间索引
     * @param P30F2103 the P30F2103 to set
     */
    public void setP30F2103(String P30F2103)
    {
        this.P30F2103 = P30F2103;
    }

    /**
     * 空间名称
     * @return the P30F2104
     */
    public String getP30F2104()
    {
        return P30F2104;
    }

    /**
     * 空间名称
     * @param P30F2104 the P30F2104 to set
     */
    public void setP30F2104(String P30F2104)
    {
        this.P30F2104 = P30F2104;
    }

    /**
     * 空间提示
     * @return the P30F2105
     */
    public String getP30F2105()
    {
        return P30F2105;
    }

    /**
     * 空间提示
     * @param P30F2105 the P30F2105 to set
     */
    public void setP30F2105(String P30F2105)
    {
        this.P30F2105 = P30F2105;
    }

    /**
     * 空间字符
     * @return the P30F2106
     */
    public String getP30F2106()
    {
        return P30F2106;
    }

    /**
     * 空间字符
     * @param P30F2106 the P30F2106 to set
     */
    public void setP30F2106(String P30F2106)
    {
        this.P30F2106 = P30F2106;
    }

    /**
     * 相关说明
     * @return the P30F2107
     */
    public String getP30F2107()
    {
        return P30F2107;
    }

    /**
     * 相关说明
     * @param P30F2107 the P30F2107 to set
     */
    public void setP30F2107(String P30F2107)
    {
        this.P30F2107 = P30F2107;
    }

    @Override
    public String toString()
    {
        return P30F2104;
    }
}
