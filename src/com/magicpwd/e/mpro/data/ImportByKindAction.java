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
import com.magicpwd._comn.mpwd.Mcat;
import com.magicpwd._cons.LangRes;
import com.magicpwd._enum.AuthLog;
import com.magicpwd._user.UserDto;
import com.magicpwd._util.Lang;
import com.magicpwd.r.KindTN;

/**
 *
 * @author Amon
 */
public class ImportByKindAction extends AMproAction implements IBackCall<AuthLog, UserDto>
{

    public ImportByKindAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        javax.swing.tree.TreePath path = mproPtn.getSelectedKindValue();
        if (path == null)
        {
            Lang.showMesg(mproPtn, LangRes.P30F7A02, "");
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
            javax.swing.tree.TreePath path = mproPtn.getSelectedKindValue();
            KindTN node = (KindTN) path.getLastPathComponent();
            Mcat kind = (Mcat) node.getUserObject();
            if (!mproPtn.isKindValidate(kind))
            {
                Lang.showMesg(mproPtn, LangRes.P30F7A4A, "不能保存到任务列表中去！");
//                    tr_GuidTree.requestFocus();
                return false;
            }
            if (mproPtn.importByKind(kind.getC2010203()))
            {
                mproPtn.findLast();
            }
            return true;
        }
        return false;
    }
}
