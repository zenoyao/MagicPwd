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
import com.magicpwd._cons.ConsDat;
import com.magicpwd._util.Char;
import com.magicpwd.m.UserMdl;
import org.dom4j.Element;

/**
 * 使用说明：<br />
 * name:记录创建时间<br />
 * data:所属类别索引<br />
 * spec:[0]口令模板索引<br />
 *
 * @author Amon
 */
public class GuidItem extends AEditItem
{

    public static final int SPEC_GUID_TPLT = 0;// 口令模板索引

    public GuidItem(UserMdl userMdl)
    {
        super(userMdl, ConsDat.INDX_GUID, "", "");
    }

    @Override
    public boolean exportAsTxt(StringBuilder builder)
    {
        if (builder == null)
        {
            return false;
        }
        builder.append(doEscape(getName()));
        //builder.append(',').append(doEscape(getData()));
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
        if (!Char.isValidateDateTime(txt))
        {
            return false;
        }
        setName(unEscape(txt));
//        setData(unEscape(arr[1]));
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
            this.ext = new java.util.ArrayList<String>(2);
        }
        else
        {
            ext.clear();
        }

        ext.add(SPEC_VALUE_NONE);
        ext.add(SPEC_VALUE_NONE);
    }
}
