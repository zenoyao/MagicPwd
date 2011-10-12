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
public class SignItem extends AEditItem
{

    public static final int SPEC_SIGN_TYPE = 0;//控制类型
    public static final int SPEC_SIGN_TPLT = 1;//显示模板

    public SignItem(UserMdl userMdl)
    {
        super(userMdl, ConsDat.INDX_SIGN, "", "");
    }

    @Override
    public boolean exportAsTxt(StringBuilder builder)
    {
        if (builder == null)
        {
            return false;
        }
        builder.append(doEscape(getName())).append(',').append(doEscape(getData()));
        for (String tmp : ext)
        {
            builder.append(',').append(tmp);
        }
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

        ext.clear();
        for (int i = 2, j = list.size(); i < j; i += 1)
        {
            ext.add(list.get(i));
        }
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

        ext.add("def");
        ext.add("P30F7E02");
    }
}
