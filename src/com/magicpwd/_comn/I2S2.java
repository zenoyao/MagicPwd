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

/**
 *
 * @author Amon
 */
public class I2S2 extends I1S2
{

    private int j;

    /**
     *
     */
    public I2S2()
    {
    }

    /**
     * @param i
     */
    public I2S2(int i, int j)
    {
        super(i, "", "");
    }

    /**
     * @param i
     * @param k
     * @param v
     */
    public I2S2(int i, int j, String k, String v)
    {
        super(i, k, v);
        this.j = j;
    }

    /**
     * @return the j
     */
    public int getJ()
    {
        return j;
    }

    /**
     * @param j the j to set
     */
    public void setJ(int j)
    {
        this.j = j;
    }

    public void addJ(int j)
    {
        this.j += j;
    }
}
