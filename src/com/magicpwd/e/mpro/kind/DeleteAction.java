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
import com.magicpwd._comn.mpwd.Mcat;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;
import com.magicpwd.r.mpro.CatNode;

/**
 *
 * @author Amon
 */
public class DeleteAction extends AMproAction
{

    public DeleteAction()
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
        if (obj == null || !(obj instanceof CatNode))
        {
            return;
        }

        CatNode node = (CatNode) obj;
        if (node.isRoot())
        {
            return;
        }

        Mcat kind = (Mcat) node.getUserObject();
        if ("0".equals(kind.getC2010203()))
        {
            Lang.showMesg(mproPtn, null, "无法删除默认类别！");
            return;
        }

        if (Lang.showFirm(mproPtn, LangRes.P30F7A1A, "执行此操作后，此类别下的其它类别将会移动到根类别下，\n确认要删除“{0}”类别么？", kind.getC2010206()) == javax.swing.JOptionPane.YES_OPTION)
        {
            mproPtn.getTreeMdl().wRemove(path);
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
