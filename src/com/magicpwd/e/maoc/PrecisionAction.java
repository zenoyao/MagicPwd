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
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;

/**
 *
 * @author Amon
 */
public class PrecisionAction extends AMaocAction
{

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        String cmd = e.getActionCommand();
        if (!Char.isValidate(cmd) || !cmd.startsWith("dec:"))
        {
            return;
        }

        cmd = cmd.substring(4);

        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("^[+-]?\\d+$").matcher(cmd);
        if (matcher.find())
        {
            cmd = matcher.group();
            if (cmd.charAt(0) == '-')
            {
                do
                {
                    cmd = javax.swing.JOptionPane.showInputDialog(maocPtn, Lang.getLang(LangRes.P30FBA02, "请输入一个0到16之间的整数！"), "" + maocPtn.getPrecision());
                    if (cmd == null)
                    {
                        return;
                    }
                    cmd = cmd.trim();
                }
                while (!java.util.regex.Pattern.matches("^0?\\d|1[0-6]$", cmd));
            }
            maocPtn.setPrecision(Integer.parseInt(cmd));
            return;
        }
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
