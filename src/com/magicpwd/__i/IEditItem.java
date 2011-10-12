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
package com.magicpwd.__i;

import org.dom4j.Element;

/**
 *
 * @author Amon
 */
public interface IEditItem
{

    int getType();

    void setType(int type);

    String getName();

    void setName(String name);

    String getData();

    boolean setData(String data);

    void addSpec(String spec);

    String getSpec(int index);

    String getSpec(int index, String def);

    void setSpec(int index, String spec);

    String enCodeSpec(String c);

    void deCodeSpec(String text, String c);

    void setDefault();

    boolean exportAsTxt(StringBuilder builder);

    boolean exportAsXml(Element element);

    boolean importByTxt(String txt);

    boolean importByXml(String xml);
}
