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
import com.magicpwd._comn.S1S1;
import com.magicpwd._cons.ConsCfg;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.x.app.DatDialog;

/**
 * 从数据文件恢复
 * @author Amon
 */
public class NativeResumeAction extends AMproAction implements IBackCall<String, String>
{

    public NativeResumeAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        if (!Char.isValidate(mproPtn.getUserMdl().getCfg(ConsCfg.CFG_SAFE_BACK_LOC, "")))
        {
            Lang.showMesg(mproPtn, LangRes.P30F7A54, "您还没有配置本地备份目录！");
            return;
        }

        if (javax.swing.JOptionPane.YES_OPTION != Lang.showFirm(mproPtn, LangRes.P30F7A53, "确认要执行恢复操作吗？"))
        {
            return;
        }

        new Thread()
        {

            @Override
            public void run()
            {
                doResume();
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
        if (IBackCall.OPTIONS_ABORT.equals(options))
        {
            return true;
        }
        if (!IBackCall.OPTIONS_APPLY.equals(options))
        {
            return false;
        }

        mproPtn.showProgress();
        doResume(object);
        return true;
    }

    private void doResume()
    {
        mproPtn.showProgress();

        java.util.List<S1S1> list = new java.util.ArrayList<S1S1>();
        try
        {
            mproPtn.nativeDetect(list);
        }
        catch (Exception exp)
        {
            mproPtn.hideProgress();

            Logs.exception(exp);
            Lang.showMesg(mproPtn, null, exp.getLocalizedMessage());
            return;
        }

        if (list.size() < 1)
        {
            mproPtn.hideProgress();

            Lang.showMesg(mproPtn, LangRes.P30F7A55, "没有发现可用的备份数据！");
            return;
        }

        if (list.size() == 1)
        {
            doResume(list.get(0).getK());
            return;
        }

        mproPtn.hideProgress();

        DatDialog datDialog = new DatDialog(mproPtn, this);
        datDialog.initView();
        datDialog.initLang();
        datDialog.initData();
        datDialog.showData(list);
        datDialog.setVisible(true);
    }

    private void doResume(String file)
    {
        try
        {
            boolean b = mproPtn.nativeResume(file, null);
            mproPtn.hideProgress();

            if (b)
            {
                Lang.showMesg(mproPtn, LangRes.P30F7A3F, "恭喜，数据恢复成功，您需要重新启动本程序！");
            }
            else
            {
                Lang.showMesg(mproPtn, LangRes.P30F7A3E, "数据恢复失败，请重启软件后重试！");
            }
        }
        catch (Exception exp)
        {
            mproPtn.hideProgress();

            Logs.exception(exp);
            Lang.showMesg(mproPtn, null, exp.getLocalizedMessage());
        }
        System.exit(0);
    }
}
