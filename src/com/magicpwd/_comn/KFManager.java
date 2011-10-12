/*
 *  Copyright (C) 2011 Amon
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

import java.awt.Component;
import java.awt.Container;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.event.KeyEvent;

/**
 *
 * @author Amon
 */
public class KFManager extends DefaultKeyboardFocusManager
{

    private String containerName;

    public void setContainerName(String name)
    {
        if (name != null)
        {
            name = name.trim();
            if (name.length() < 1)
            {
                name = null;
            }
        }
        containerName = name;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e)
    {
        if (containerName == null)
        {
            return super.dispatchKeyEvent(e);
        }

        Component cmp = e.getComponent();
        if (isModal(cmp))
        {
            return super.dispatchKeyEvent(e);
        }

        e.consume();
        return false;
    }

    private boolean isModal(Component cmp)
    {
        if (cmp == null)
        {
            return false;
        }

        System.out.println("::" + cmp.getClass());
        Container con = (cmp instanceof Container) ? (Container) cmp : cmp.getParent();
        while (con != null)
        {
            if (con instanceof javax.swing.JDialog || containerName.equalsIgnoreCase(con.getName()))
            {
                return true;
            }
            con = con.getParent();
        }
        return false;
    }
}
