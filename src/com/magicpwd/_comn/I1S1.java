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
package com.magicpwd._comn;

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
public class I1S1
{

    private int k;
    private String v;

    public I1S1()
    {
    }

    public I1S1(int k)
    {
        this.k = k;
    }

    public I1S1(int k, String v)
    {
        this.k = k;
        this.v = v;
    }

    /**
     * @return the k
     */
    public int getK()
    {
        return k;
    }

    /**
     * @param k the k to set
     */
    public void setK(int k)
    {
        this.k = k;
    }

    /**
     * @return the v
     */
    public String getV()
    {
        return v;
    }

    /**
     * @param v the v to set
     */
    public void setV(String v)
    {
        this.v = v;
    }

    @Override
    public String toString()
    {
        return v;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }

        if (o instanceof I1S1)
        {
            I1S1 t = (I1S1) o;
            return getK() == t.getK();
        }
        if (o instanceof Integer)
        {
            return getK() == (Integer) o;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 89 * hash + this.k;
        return hash;
    }
}
