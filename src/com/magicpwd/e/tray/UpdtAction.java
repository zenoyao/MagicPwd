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
package com.magicpwd.e.tray;

import com.magicpwd.__a.tray.ATrayAction;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Desk;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd._util.Util;

/**
 *
 * @author Amon
 */
public class UpdtAction extends ATrayAction
{

    public UpdtAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        try
        {
            boolean b = Util.checkUpdate(ConsEnv.SOFTCODE, ConsEnv.VERSIONS);
            if (b)
            {
                if (Lang.showFirm(trayPtn.getMpwdPtn(), LangRes.P30F7A12, "检测到新版本，现在要下载吗？") == javax.swing.JOptionPane.YES_OPTION)
                {
                    Desk.browse(ConsEnv.HOMEPAGE);
                }
            }
            else
            {
                Lang.showMesg(trayPtn.getMpwdPtn(), LangRes.P30F7A13, "您使用的已是最新版本。");
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(trayPtn.getMpwdPtn(), LangRes.P30F7A14, "");
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
