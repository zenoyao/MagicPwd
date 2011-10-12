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
package com.magicpwd._comn.item;

import com.magicpwd.__a.AEditItem;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._cons.ConsDat;
import com.magicpwd.m.UserMdl;
import org.dom4j.Element;

/**
 *
 * @author Amon
 */
public class HintItem extends AEditItem
{

    private MgtdHeader mgtd;

    public HintItem(UserMdl userMdl)
    {
        super(userMdl, ConsDat.INDX_HINT, "", "");
    }

    @Override
    public boolean exportAsTxt(StringBuilder builder)
    {
        return true;
    }

    @Override
    public boolean exportAsXml(Element element)
    {
        element.addElement("name").setText(getName());
        element.addElement("data").setText(getData());
        return true;
    }

    @Override
    public boolean importByTxt(String txt)
    {
        return true;
    }

    @Override
    public boolean importByXml(String xml)
    {
        return true;
    }

    @Override
    public final void setDefault()
    {
    }

    /**
     * @return the mgtd
     */
    public MgtdHeader getMgtd()
    {
        return mgtd;
    }

    /**
     * @param mgtd the mgtd to set
     */
    public void setMgtd(MgtdHeader mgtd)
    {
        this.mgtd = mgtd;
    }
}
