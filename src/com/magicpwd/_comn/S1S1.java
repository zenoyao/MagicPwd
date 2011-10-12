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
 * @author amon
 */
public class S1S1 implements java.io.Serializable, Comparable<S1S1>
{

    private String _k;
    private String _v;

    public S1S1()
    {
        this("", "");
    }

    public S1S1(String k, String v)
    {
        this._k = k;
        this._v = v;
    }

    /**
     * @return the _k
     */
    public String getK()
    {
        return _k;
    }

    /**
     * @param k the _k to set
     */
    public void setK(String k)
    {
        this._k = k;
    }

    /**
     * @return the _v
     */
    public String getV()
    {
        return _v;
    }

    /**
     * @param v the _v to set
     */
    public void setV(String v)
    {
        this._v = v;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }

        if (o instanceof S1S1)
        {
            S1S1 t = (S1S1) o;
            if (getK() == null)
            {
                return t.getK() == null;
            }
            return getK().equals(t.getK());
        }

        return getK().equals(o);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 29 * hash + (this._k != null ? this._k.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return getV();
    }

	@Override
	public int compareTo(S1S1 o)
    {
		if (o == null)
        {
			return 1;
		}
		return this.getK().compareTo(o.getK());
	}
}
