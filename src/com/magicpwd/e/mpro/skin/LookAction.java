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
package com.magicpwd.e.mpro.skin;

import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd.__a.mpro.AMproAction;

/**
 *
 * @author aven
 */
public class LookAction extends AMproAction
{

    public LookAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent ae)
    {
        String command = ae.getActionCommand();
        if (!Char.isValidate(command))
        {
            return;
        }

        mproPtn.getUserMdl().setLook(command);

        Lang.showMesg(mproPtn, LangRes.P30FAA1B, "新外观将在重启后生效！\n注意：一些外观可能造成性能问题，如果您觉得所选择的外观使系统运行变慢，\n　　　请选择使用“默认”或“系统”外观！");
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
