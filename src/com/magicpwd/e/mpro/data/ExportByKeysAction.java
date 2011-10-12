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
package com.magicpwd.e.mpro.data;

import com.magicpwd.__a.mpro.AMproAction;
import com.magicpwd.__i.IBackCall;
import com.magicpwd._cons.LangRes;
import com.magicpwd._enum.AuthLog;
import com.magicpwd._user.UserDto;
import com.magicpwd._util.Lang;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-12-23 9:50:01
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class ExportByKeysAction extends AMproAction implements IBackCall<AuthLog, UserDto>
{

    public ExportByKeysAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        javax.swing.tree.TreePath path = mproPtn.getSelectedKindValue();
        if (path == null)
        {
            Lang.showMesg(mproPtn, LangRes.P30F7A20, "请选择您要导出数据的类别信息！");
            return;
        }

        trayPtn.getUserPtn(AuthLog.signRs, this);
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
    public boolean callBack(AuthLog options, UserDto object)
    {
        if (AuthLog.signRs == options)
        {
            return mproPtn.exportByKeys("");
        }
        return false;
    }
}
