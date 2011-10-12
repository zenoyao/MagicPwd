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
package com.magicpwd._bean;

import com.magicpwd.__a.AEditBean;
import com.magicpwd.__a.AMpwdPtn;
import com.magicpwd._comp.WTextBox;

/**
 *
 * @author Amon
 */
public abstract class AAreaBean extends AEditBean
{

    private WTextBox dataBox;

    public AAreaBean(AMpwdPtn mpwdPtn)
    {
        this.formPtn = mpwdPtn;
    }

    protected void initConfView()
    {
        ta_PropData = new javax.swing.JTextArea();
        ta_PropData.setLineWrap(true);

        dataBox = new WTextBox(ta_PropData);
        dataBox.initView();
    }

    protected void initConfLang()
    {
        dataBox.initLang();
    }

    protected void initConfData()
    {
        dataBox.initData();
    }

    protected void showConfData()
    {
    }
    protected javax.swing.JTextArea ta_PropData;
    // 配置信息
}
