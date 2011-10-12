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
package com.magicpwd.e.mpro.view;

import com.magicpwd.__a.mpro.AMproAction;
import com.magicpwd._enum.AppView;

/**
 *
 * @author Amon
 */
public class InfoVisibleAction extends AMproAction
{

    public InfoVisibleAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        boolean b = !mproPtn.getUserMdl().isInfoVisible(AppView.mpro);
        mproPtn.setInfoVisible(b);
        mproPtn.pack();
    }

    @Override
    public void doInit(String value)
    {
        setSelected(mproPtn.getUserMdl().isInfoVisible(AppView.mpro));
    }

    @Override
    public void reInit(javax.swing.AbstractButton button, String value)
    {
        button.setSelected(isSelected());
    }
}
