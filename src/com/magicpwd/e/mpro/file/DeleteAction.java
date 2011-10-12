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
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;

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
        Object object = mproPtn.getSelectedListValue();
        if (object == null)
        {
            return;
        }

        if (Lang.showFirm(mproPtn, LangRes.P30F7A0A, "您正在进行的操作是删除记录数据及其所有历史信息，确认继续么？") != javax.swing.JOptionPane.YES_OPTION)
        {
            return;
        }
        if (Lang.showFirm(mproPtn, LangRes.P30F7A0B, "确认一下您操作的正确性，要返回么？") != javax.swing.JOptionPane.NO_OPTION)
        {
            return;
        }
        mproPtn.removeSelectedKeys();
        mproPtn.clearGrid();
        mproPtn.showPropInfo();
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
