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
package com.magicpwd.v.app.mpro;

import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;

/**
 *
 * @author aven
 */
public class EditBar extends javax.swing.JPanel
{

    private javax.swing.border.TitledBorder border;

    public EditBar()
    {
    }

    public void initView()
    {
        setLayout(new java.awt.BorderLayout());
        border = javax.swing.BorderFactory.createTitledBorder(Lang.getLang(LangRes.P30F7305, ""));
        setBorder(border);
    }

    public void initLang()
    {
    }

    public void initData()
    {
    }

    public void setPropView(javax.swing.JPanel prop)
    {
        add(prop);
    }

    public void setTitle(String title)
    {
        border.setTitle(title);
    }
}
