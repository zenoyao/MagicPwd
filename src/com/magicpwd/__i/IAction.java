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
package com.magicpwd.__i;

import com.magicpwd.v.app.tray.TrayPtn;

/**
 *
 * @author Amon
 */
public interface IAction extends javax.swing.Action
{

    void setTrayPtn(TrayPtn trayPtn);

    /**
     * Action自身参数初始化
     * @param value 配置文件中需要传递给Action的初始化参数
     */
    public abstract void doInit(String value);

    /**
     * Action对抽象按钮的初始化
     * @param button 需要处理的按钮
     * @param value 当事件被附加于按钮时，需要处理的初始化参数
     */
    public abstract void reInit(javax.swing.AbstractButton button, String value);

    boolean isVisible();

    boolean isSelected();
}
