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
package com.magicpwd._comp;

/**
 *
 * @author Administrator
 */
public class WButtonGroup extends javax.swing.ButtonGroup
{

    private java.util.HashMap<String, javax.swing.ButtonModel> buttonModel;

    public WButtonGroup()
    {
        buttonModel = new java.util.HashMap<String, javax.swing.ButtonModel>();
    }

    public void add(String actionCommand, javax.swing.AbstractButton button)
    {
        buttonModel.put(actionCommand, button.getModel());
        add(button);
    }

    public boolean setSelected(String actionCommand, boolean selected)
    {
        javax.swing.ButtonModel model = buttonModel.get(actionCommand);
        if (model != null)
        {
            setSelected(model, selected);
            return true;
        }
        return false;
    }

    public boolean setSelected(String actionCommand, boolean selected, String defaultCommand)
    {
        javax.swing.ButtonModel model = buttonModel.get(actionCommand);
        if (model != null)
        {
            setSelected(model, selected);
            return true;
        }

        model = buttonModel.get(defaultCommand);
        if (model != null)
        {
            setSelected(model, selected);
            return true;
        }

        return false;
    }

    public boolean contains(String actionCommand)
    {
        return buttonModel.containsKey(actionCommand);
    }
}
