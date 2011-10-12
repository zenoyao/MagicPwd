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
package com.magicpwd.__i.mpro;

import com.magicpwd.__i.IEditBean;
import com.magicpwd.__i.IEditItem;

/**
 *
 * @author Amon
 */
public interface IMproBean extends IEditBean
{

    /**
     * 显示指定数据
     * @param item
     */
    void showData(IEditItem item);

    /**
     * 界面焦点初始化
     */
    void requestFocus();

    void copyDataActionPerformed(java.awt.event.ActionEvent evt);

    void saveDataActionPerformed(java.awt.event.ActionEvent evt);

    void dropDataActionPerformed(java.awt.event.ActionEvent evt);
}
