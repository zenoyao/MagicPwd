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
package com.magicpwd.e.mpro.safe;

import com.magicpwd.__a.mpro.AMproAction;
import com.magicpwd.__i.IBackCall;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;

/**
 *
 * @author Amon
 */
public class RemoteBackupAction extends AMproAction implements IBackCall<String, String>
{

    public RemoteBackupAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        if (javax.swing.JOptionPane.YES_OPTION != Lang.showFirm(mproPtn, LangRes.P30F7A40, "确认要执行备份数据到云端的操作吗，此操作将需要一定的时间？"))
        {
            return;
        }

        new Thread()
        {

            @Override
            public void run()
            {
                doBackup();
            }
        }.start();
    }

    @Override
    public void doInit(String value)
    {
    }

    @Override
    public void reInit(javax.swing.AbstractButton button, String value)
    {
    }

    @Override
    public boolean callBack(String options, String object)
    {
        return true;
    }

    private void doBackup()
    {
        mproPtn.showProgress();

        try
        {
            boolean b = mproPtn.remoteBackup(this);
            mproPtn.hideProgress();

            if (b)
            {
                Lang.showMesg(mproPtn, LangRes.P30F7A3D, "恭喜，数据备份成功！");
            }
            else
            {
                Lang.showMesg(mproPtn, LangRes.P30F7A3C, "数据备份失败，请重启软件后重试！");
            }
        }
        catch (Exception exp)
        {
            mproPtn.hideProgress();

            Logs.exception(exp);
            Lang.showMesg(mproPtn, null, exp.getLocalizedMessage());
        }
    }
}
