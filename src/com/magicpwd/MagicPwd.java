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
package com.magicpwd;

import com.magicpwd._cons.ConsEnv;
import com.magicpwd._enum.RunMode;
import com.magicpwd._util.Jzip;
import com.magicpwd._util.Logs;
import com.magicpwd.m.MpwdMdl;
import com.magicpwd.v.cmd.mcmd.McmdPtn;
import com.magicpwd.v.app.tray.TrayPtn;

/**
 * @author Amon
 */
public class MagicPwd
{
    private MpwdMdl mpwdMdl;

    private MagicPwd()
    {
    }

    private void init()
    {
        // 系统配置信息读取
        mpwdMdl = new MpwdMdl();
        mpwdMdl.loadCfg();

        // 命令模式
        if (MpwdMdl.getRunMode() == RunMode.cmd)
        {
            McmdPtn mcmdPtn = new McmdPtn(mpwdMdl);
            mcmdPtn.init();
            return;
        }

        try
        {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            java.io.File skin = new java.io.File(ConsEnv.DIR_SKIN);
            if (!skin.exists() || !skin.isDirectory() || !skin.canRead())
            {
                skin.mkdir();
                Jzip.unZip(MagicPwd.class.getResourceAsStream("/res/skin.zip"), skin, true);
            }
            java.io.File lang = new java.io.File(ConsEnv.DIR_LANG);
            if (!lang.exists() || !lang.isDirectory() || !lang.canRead())
            {
                lang.mkdir();
                Jzip.unZip(MagicPwd.class.getResourceAsStream("/res/lang.zip"), lang, true);
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return;
        }

        TrayPtn trayPtn = new TrayPtn(mpwdMdl);
        trayPtn.init();
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        System.setProperty("file.encoding", "UTF-8");

        // 界面启动参数读取
        if (args != null && args.length > 0)
        {
            MpwdMdl.setRunMode(args[0]);
        }

        new MagicPwd().init();
    }
}
