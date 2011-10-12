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

/**
 *
 * @author Amon
 */
public interface IPropBean
{

    /**
     * 界面布局初始化
     */
    void initView();

    /**
     * 界面语言初始化
     */
    void initLang();

    /**
     * 界面数据初始化
     * @param tplt
     */
    void initData();

    /**
     * 显示数据
     */
    void showData();

    /**
     * 界面数据保存
     */
    void saveData();

    /**
     * 界面焦点初始化
     */
    void requestFocus();

    javax.swing.JPanel getPanel();
}
