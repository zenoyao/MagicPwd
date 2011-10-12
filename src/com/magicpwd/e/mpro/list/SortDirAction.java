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
package com.magicpwd.e.mpro.list;

import com.magicpwd.__a.mpro.AMproAction;
import com.magicpwd._cons.ConsCfg;
import com.magicpwd._enum.AppView;

/**
 *
 * @author Amon
 */
public class SortDirAction extends AMproAction
{

    public SortDirAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        mproPtn.getUserMdl().setCfg(AppView.mpro, ConsCfg.CFG_VIEW_LIST_ASC, e.getActionCommand());
        mproPtn.findLast();
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
