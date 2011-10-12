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
public class DataItem extends AEditItem
{

    public static final int SPEC_OPT = 0;//可选输入
    public static final int SPEC_SET = 1;//数据集
    public static final int SPEC_DOT_INT = 2;//整数位
    public static final int SPEC_DOT_DEC = 3;//小数位
    public static final int SPEC_CHAR = 4;//特殊符号
    public static final int SPEC_CHAR_OPT = 5;//是否可选
    public static final int SPEC_CHAR_POS = 6;//符号位置
    public static final int SPEC_EXP = 7;//表达式

    public DataItem(UserMdl userMdl)
    {
        super(userMdl, ConsDat.INDX_DATA, "", "");
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
            this.ext = new java.util.ArrayList<String>(8);
        }
        else
        {
            ext.clear();
        }

        ext.add(SPEC_VALUE_TRUE);
        ext.add("+0-");
        ext.add("0");
        ext.add("8");
        ext.add(SPEC_VALUE_NONE);
        ext.add(SPEC_VALUE_TRUE);
        ext.add("^");
        ext.add(SPEC_VALUE_FAIL);
    }
}
