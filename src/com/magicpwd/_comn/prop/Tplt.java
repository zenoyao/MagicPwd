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
 * 属性：模板
 * @author Amon
 */
public class Tplt
{

    private boolean tpltType;
    /**排序依据*/
    private int P30F1101;
    /**属性类别*/
    private int P30F1102;
    /**属性索引*/
    private String P30F1103;
    /**类别索引*/
    private String P30F1104;
    /**属性名称*/
    private String P30F1105;
    /**属性提示*/
    private String P30F1106;
    /**属性说明*/
    private String P30F1107;

    public Tplt()
    {
    }

    public Tplt(boolean isType)
    {
        this.tpltType = isType;
    }

    public boolean isType()
    {
        return tpltType;
    }

    public void addP30F1101(int step)
    {
        P30F1101 += step;
    }

    /**
     * 排序依据
     * @return the P30F1101
     */
    public int getP30F1101()
    {
        return P30F1101;
    }

    /**
     * 排序依据
     * @param P30F1101 the P30F1101 to set
     */
    public void setP30F1101(int P30F1101)
    {
        this.P30F1101 = P30F1101;
    }

    /**
     * 属性类别
     * @return the P30F1102
     */
    public int getP30F1102()
    {
        return P30F1102;
    }

    /**
     * 属性类别
     * @param P30F1102 the P30F1102 to set
     */
    public void setP30F1102(int P30F1102)
    {
        this.P30F1102 = P30F1102;
    }

    /**
     * 属性索引
     * @return the P30F1103
     */
    public String getP30F1103()
    {
        return P30F1103;
    }

    /**
     * 属性索引
     * @param P30F1103 the P30F1103 to set
     */
    public void setP30F1103(String P30F1103)
    {
        this.P30F1103 = P30F1103;
    }

    /**
     * 类别索引
     * @return the P30F1104
     */
    public String getP30F1104()
    {
        return P30F1104;
    }

    /**
     * 类别索引
     * @param P30F1104 the P30F1104 to set
     */
    public void setP30F1104(String P30F1104)
    {
        this.P30F1104 = P30F1104;
    }

    /**
     * 属性名称
     * @return the P30F1105
     */
    public String getP30F1105()
    {
        return P30F1105;
    }

    /**
     * 属性名称
     * @param P30F1105 the P30F1105 to set
     */
    public void setP30F1105(String P30F1105)
    {
        this.P30F1105 = P30F1105;
    }

    /**
     * 属性提示
     * @return the P30F1106
     */
    public String getP30F1106()
    {
        return P30F1106;
    }

    /**
     * 属性提示
     * @param P30F1106 the P30F1106 to set
     */
    public void setP30F1106(String P30F1106)
    {
        this.P30F1106 = P30F1106;
    }

    /**
     * 属性说明
     * @return the P30F1107
     */
    public String getP30F1107()
    {
        return P30F1107;
    }

    /**
     * 属性说明
     * @param P30F1107 the P30F1107 to set
     */
    public void setP30F1107(String P30F1107)
    {
        this.P30F1107 = P30F1107;
    }

    @Override
    public String toString()
    {
        return P30F1105;
    }
}
