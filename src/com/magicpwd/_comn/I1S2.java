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
 * @author Amon
 */
public class I1S2 extends I1S1
{

    private String v2;

    /**
     * 
     */
    public I1S2()
    {
    }

    /**
     * @param k
     */
    public I1S2(int k)
    {
        super(k);
    }

    /**
     * @param k
     * @param k
     * @param v
     */
    public I1S2(int k, String v1, String v2)
    {
        super(k, v1);
        this.v2 = v2;
    }

    /**
     * @return the v2
     */
    public String getV2()
    {
        return v2;
    }

    /**
     * @param v2 the v2 to set
     */
    public void setV2(String v2)
    {
        this.v2 = v2;
    }
}
