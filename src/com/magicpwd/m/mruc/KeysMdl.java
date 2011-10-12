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
package com.magicpwd.m.mruc;

import com.magicpwd._comn.S1S2;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.UserMdl;

/**
 *
 * @author Amon
 */
public class KeysMdl extends javax.swing.DefaultComboBoxModel
{

    private UserMdl userMdl;
    private java.util.ArrayList<S1S2> unitList;

    public KeysMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    void init()
    {
        unitList = new java.util.ArrayList<S1S2>();
        DBA4000.findUnitList(userMdl, unitList);
    }

    @Override
    public int getSize()
    {
        return unitList.size();
    }

    @Override
    public Object getElementAt(int index)
    {
        return unitList.get(index);
    }
}
