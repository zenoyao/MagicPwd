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
package com.magicpwd.r;

import com.magicpwd._comn.mpwd.Mcat;
import com.magicpwd._comn.*;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * @author Amon
 * 
 */
public class TreeCR extends DefaultTreeCellRenderer
{

    private java.util.HashMap<String, javax.swing.ImageIcon> iconMap;

    public TreeCR()
    {
        iconMap = new java.util.HashMap<String, javax.swing.ImageIcon>();
    }
    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeCR#getTreeCellRendererComponent(javax.swing.JTree,
     *      java.lang.Object, boolean, boolean, boolean, int, boolean)
     */

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, null, isSelected, expanded, leaf, row, hasFocus);

        if (value instanceof KindTN)
        {
            value = ((KindTN) value).getUserObject();
            Mcat kind = (Mcat) value;
            String icon = kind.getC2010208();
            if (Char.isValidateHash(icon))
            {
                if (!iconMap.containsKey(icon))
                {
                    iconMap.put(icon, Bean.readIcon(ConsEnv.DIR_ICO + '/' + icon + ".png"));
                }
                setIcon(iconMap.get(icon));
            }
            setText(kind.getC2010206());
            setToolTipText(kind.getC2010207());
            return this;
        }

        if (value instanceof S1S2)
        {
            S1S2 kvItem = (S1S2) value;
            setText(kvItem.getV());
            setToolTipText(kvItem.getV2());
            return this;
        }

        if (value != null)
        {
            setText(value.toString());
            setToolTipText(value.toString());
            return this;
        }

        return this;
    }
}
