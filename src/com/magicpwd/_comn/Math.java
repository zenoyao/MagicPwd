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
package com.magicpwd._comn;

import com.magicpwd._util.Char;

/**
 *
 * @author Amon
 */
public class Math
{

    private String s0;
    private String s1;
    private String s2;

    public Math()
    {
    }

    public Math(String s0)
    {
        this.s0 = s0;
    }

    public Math(String s0, String s1, String s2)
    {
        this.s0 = s0;
        this.s1 = s1;
        this.s2 = s2;
    }

    /**
     * @return the s0
     */
    public String getS0()
    {
        return s0;
    }

    /**
     * @param s0 the s0 to set
     */
    public void setS0(String s0)
    {
        this.s0 = s0;
    }

    /**
     * @return the s1
     */
    public String getS1()
    {
        return s1;
    }

    /**
     * @param s1 the s1 to set
     */
    public void setS1(String s1)
    {
        this.s1 = s1;
    }

    /**
     * @return the s2
     */
    public String getS2()
    {
        return s2;
    }

    /**
     * @param s2 the s2 to set
     */
    public void setS2(String s2)
    {
        this.s2 = s2;
    }

    @Override
    public String toString()
    {
        return "<html>" + (Char.isValidate(s1) ? s0.replace(s1, "<font color=\"#FF0000\">" + s1 + "</font>") : s0) + "</html>";
    }
}
