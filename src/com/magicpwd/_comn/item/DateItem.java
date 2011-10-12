/*
 *  Copyright (C) 2011 yaoshangwen
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
import com.magicpwd._cons.ConsDat;
import com.magicpwd._util.Char;
import com.magicpwd.m.UserMdl;
import org.dom4j.Element;

/**
 *
 * @author yaoshangwen
 */
public class DateItem extends AEditItem
{

    public static final int SPEC_FORMAT = 0;// 日期显示格式

    public DateItem(UserMdl userMdl)
    {
        super(userMdl, ConsDat.INDX_DATE, "", "");
    }

    @Override
    public boolean exportAsTxt(StringBuilder builder)
    {
        if (builder == null)
        {
            return false;
        }
        builder.append(doEscape(getName())).append(',').append(doEscape(getData()));
        builder.append(',').append(getSpec(SPEC_FORMAT, SPEC_VALUE_NONE));
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
        if (!Char.isValidate(txt))
        {
            return false;
        }
        java.util.List<String> list = Char.split(txt.replace("\\,", "\f"), ",");
        if (list == null || list.size() < 2)
        {
            return false;
        }
        setName(unEscape(list.get(0).replace("\f", "\\,")));
        setData(unEscape(list.get(1).replace("\f", "\\,")));
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
        if (ext == null)
        {
            this.ext = new java.util.ArrayList<String>(1);
        }
        else
        {
            ext.clear();
        }

        ext.add(SPEC_VALUE_NONE);
    }
}
