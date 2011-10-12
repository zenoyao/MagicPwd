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
package com.magicpwd.e.mpro.kind;

import com.magicpwd.__a.mpro.AMproAction;
import com.magicpwd.__i.IBackCall;
import com.magicpwd._comn.mpwd.Mcat;
import com.magicpwd._util.Lang;
import com.magicpwd.r.KindTN;
import com.magicpwd.v.app.mpro.KindDlg;

/**
 *
 * @author Amon
 */
public class UpdateAction extends AMproAction implements IBackCall<String, Mcat>
{

    public UpdateAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        javax.swing.tree.TreePath path = mproPtn.getSelectedKindValue();
        if (path == null)
        {
            return;
        }

        Object obj = path.getLastPathComponent();
        if (obj == null || !(obj instanceof KindTN))
        {
            return;
        }

        KindTN node = (KindTN) obj;
        if (node.isRoot())
        {
            return;
        }

        Mcat kind = (Mcat) node.getUserObject();
        if ("0".equals(kind.getC2010203()))
        {
            Lang.showMesg(mproPtn, null, "无法更新默认类别！");
            return;
        }

        KindDlg kindDlg = new KindDlg(mproPtn, this);
        kindDlg.initView();
        kindDlg.initLang();
        kindDlg.initData(kind);
        kindDlg.setVisible(true);
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
    public boolean callBack(String options, Mcat object)
    {
        if (OPTIONS_APPLY.equalsIgnoreCase(options))
        {
            mproPtn.updateKindBySelected(object);
            return true;
        }
        return false;
    }
}
