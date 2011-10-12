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

package com.magicpwd.m.maoc;

import com.magicpwd._cons.maoc.MaocEnv;

/**
 * <ul>
 * <li>功能说明：</li>
 * <br />
 * 运算符级别类
 * <li>使用说明：</li>
 * <br />
 * 本类用于封装各个运算符及其级别信息，用户只需要指定运算符，系统会根据内部设定的运算符优先级对其进行封装。
 * </ul>
 * @author Amon
 */
public class WOperator
{
    /** 运算符 */
    private String s;
    /** 运算符级别 */
    private int l;

    /**
     *
     */
    public WOperator()
    {
        this(MaocEnv.OPR_ADD_EXP_EN, MaocEnv.OPR_ADD_INT);
    }

    /**
     * @param s
     * @param l
     */
    public WOperator(String s, int l)
    {
        this.s = s;
        this.l = l;
    }

    /**
     * 获取运算符
     *
     * @return the s
     */
    public String getS()
    {
        return s;
    }

    /**
     * 设置运算符
     *
     * @param s the s to set
     */
    public void setS(String s)
    {
        this.s = s;
    }

    /**
     * 获取运算符级别
     *
     * @return the l
     */
    public int getL()
    {
        return l;
    }
}