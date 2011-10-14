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
package com.magicpwd.m.mpro;

import com.magicpwd._comn.mpwd.Mcat;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.magicpwd.r.mpro.CatNode;
import com.magicpwd._cons.ConsDat;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.UserMdl;

/**
 * @author Amon
 * 
 */
public class CatModel extends DefaultTreeModel
{

    private UserMdl userMdl;

    CatModel(UserMdl userMdl, CatNode root)
    {
        super(root);
        this.userMdl = userMdl;
    }

    void init()
    {
    }

    public void wAppend(TreePath path, Mcat kind)
    {
        CatNode r = (CatNode) path.getLastPathComponent();
        if (r == null)
        {
            return;
        }
        r.add(new CatNode(userMdl, kind));
        nodeStructureChanged(r);

        Mcat k = (Mcat) r.getUserObject();
        kind.setC2010201(r.getChildCount());
        kind.setC2010204(k.getC2010203());
        DBA4000.updateKindData(userMdl, kind);
    }

    public void wUpdate(TreePath path, Mcat kind)
    {
        CatNode c = (CatNode) path.getLastPathComponent();
        if (c == null)
        {
            return;
        }
        c.setUserObject(kind);
        nodeChanged(c);
        DBA4000.updateKindData(userMdl, kind);
    }

    public void wRemove(TreePath path)
    {
        CatNode c = (CatNode) path.getLastPathComponent();
        if (c == null)
        {
            return;
        }
        int size = c.getChildCount();
        CatNode r = (CatNode) c.getRoot();
        removeNodeFromParent(c);
        for (int i = 0; i < size; i += 1)
        {
            r.add((CatNode) c.getChildAt(0));
        }
        Mcat item = (Mcat) c.getUserObject();
        DBA4000.deleteKindData(userMdl, ConsDat.HASH_ROOT, item, r.getChildCount());
        nodeStructureChanged(r);
    }

    public java.util.List<Mcat> getKindByParent(String hash)
    {
        return DBA4000.listCatByHash(userMdl, hash);
    }
}
