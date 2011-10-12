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
package com.magicpwd.e.maoc;

import com.magicpwd.__a.maoc.AMaocAction;
import com.magicpwd._util.Char;

/**
 *
 * @author Amon
 */
public class OperatorAction extends AMaocAction
{

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        String cmd = e.getActionCommand();
        if (!Char.isValidate(cmd))
        {
            return;
        }
        String[] arr = cmd.split(":");
        if (arr == null || arr.length != 2)
        {
            return;
        }
        cmd = arr[0];
        String tmp = arr[1];
        if (!Char.isValidate(cmd) || !java.util.regex.Pattern.matches("^[-+]\\d+$", tmp))
        {
            return;
        }
        maocPtn.replaceExpression(cmd, tmp.charAt(0) == '+', Integer.parseInt(tmp.substring(1)));
    }

    @Override
    public void doInit(String value)
    {
    }

    @Override
    public void reInit(javax.swing.AbstractButton button, String value)
    {
    }
}
