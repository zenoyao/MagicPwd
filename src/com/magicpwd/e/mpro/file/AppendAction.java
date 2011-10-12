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
package com.magicpwd.e.mpro.file;

import com.magicpwd.__a.mpro.AMproAction;
import com.magicpwd._enum.AppView;
import com.magicpwd._util.Char;

/**
 *
 * @author Amon
 */
public class AppendAction extends AMproAction
{

    public AppendAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        if (!mproPtn.newKeys())
        {
            return;
        }

        if (mproPtn.getUserMdl().isEditVisible(AppView.mpro))
        {
            return;
        }

        String cmd = e.getActionCommand();
        if (Char.isValidate(cmd))
        {
            String[] arr = cmd.split(",");
            if (arr != null && arr.length == 2)
            {
                mproPtn.getMenuPtn().getButton(arr[0]).setSelected(true);
                mproPtn.getMenuPtn().getButton(arr[1]).setSelected(true);
            }
        }

        mproPtn.getUserMdl().setEditVisible(AppView.mpro, true);
        mproPtn.getUserMdl().setEditIsolate(AppView.mpro, true);

        mproPtn.setEditVisible(true);
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
