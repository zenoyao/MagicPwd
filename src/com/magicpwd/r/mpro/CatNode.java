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
package com.magicpwd.r.mpro;

import com.magicpwd._comn.mpwd.Mcat;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.UserMdl;

/**
 * @author Amon
 * 
 */
public class CatNode extends DefaultMutableTreeNode
{

    private UserMdl userMdl;

    /**
     * @param kvItem
     */
    public CatNode(UserMdl userMdl, Mcat kvItem)
    {
        super(kvItem);
        this.userMdl = userMdl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.DefaultMutableTreeNode#isLeaf()
     */
    @Override
    public boolean isLeaf()
    {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.DefaultMutableTreeNode#getChildCount()
     */
    @Override
    public int getChildCount()
    {
        if (!hasLoaded)
        {
            loadChildren();
        }
        return super.getChildCount();
    }

    /**
     * 获取下级目录数据列表
     */
    private void loadChildren()
    {
        Mcat kvItem = (Mcat) getUserObject();
        if (kvItem != null)
        {
            List<Mcat> list = DBA4000.listCatByHash(userMdl, kvItem.getC2010203());
            if (list != null)
            {
                for (int i = 0, j = list.size(); i < j; i += 1)
                {
                    insert(new CatNode(userMdl, list.get(i)), i);
                }
            }
        }
        hasLoaded = true;
    }

    /**
     * @return
     */
    public boolean isLoaded()
    {
        return hasLoaded;
    }
    private boolean hasLoaded;
}
