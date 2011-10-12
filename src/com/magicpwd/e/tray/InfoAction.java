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
import com.magicpwd._util.Bean;
import com.magicpwd._util.Lang;

/**
 *
 * @author Amon
 */
public class InfoAction extends ATrayAction
{

    public InfoAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        StringBuilder buf = new StringBuilder();
        buf.append(Lang.getLang(LangRes.P30F7201, "魔方密码")).append("\n");
        buf.append(ConsEnv.VERSIONS).append(" Build ").append(ConsEnv.BUILDER);
        javax.swing.JOptionPane.showMessageDialog(trayPtn.getMpwdPtn(), buf.toString(), Lang.getLang(LangRes.P30F1208, "关于软件"), javax.swing.JOptionPane.INFORMATION_MESSAGE, new javax.swing.ImageIcon(Bean.getLogo(32)));
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
