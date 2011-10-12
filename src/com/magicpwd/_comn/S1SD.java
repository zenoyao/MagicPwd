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

import java.util.Calendar;

public class S1SD extends S1S2
{
    private Calendar _v3;

    public S1SD()
    {
    }

    public S1SD(String k, String v1, String v2, Calendar v3)
    {
        super(k, v1, v2);
        _v3 = v3;
    }

    /**
     * @return the v3
     */
    public Calendar getV3()
    {
        return _v3;
    }

    /**
     * @param v3
     *            the v3 to set
     */
    public void setV3(Calendar v3)
    {
        _v3 = v3;
    }
}
