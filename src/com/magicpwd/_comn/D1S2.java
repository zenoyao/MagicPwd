/*
 *  Copyright (C) 2011 Amon
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
public class D1S2 extends S1S1
{

    private double _d;

    public D1S2(double d, String k, String v)
    {
        super(k, v);
        this._d = d;
    }

    public void setD(double d)
    {
        this._d = d;
    }

    public double getD()
    {
        return _d;
    }
}
