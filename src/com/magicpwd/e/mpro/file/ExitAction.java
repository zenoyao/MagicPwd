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
package com.magicpwd.e.mpro.file;

import com.magicpwd.__a.mpro.AMproAction;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;

/**
 *
 * @author Amon
 */
public class ExitAction extends AMproAction
{

    public ExitAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        if (mproPtn.gridModified())
        {
            if (javax.swing.JOptionPane.YES_OPTION != Lang.showFirm(mproPtn, LangRes.P30F7A42, "您的数据尚未保存，确认要退出吗？"))
            {
                return;
            }
        }
        else
        {
            if (javax.swing.JOptionPane.YES_OPTION != Lang.showFirm(mproPtn, LangRes.P30F1A04, "确认要退出吗？"))
            {
                return;
            }
        }

        mproPtn.setVisible(false);
        trayPtn.endExit(0);
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
