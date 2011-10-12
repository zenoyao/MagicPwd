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
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 * 属性：文本
 * 键值：ConsEnv.INDX_TEXT
 */
public abstract class ATextBean extends AEditBean
{

    private WTextBox dataBox;

    public ATextBean(AMpwdPtn mpwdPtn)
    {
        this.formPtn = mpwdPtn;
    }

    protected void initConfView()
    {
        tf_PropData = new javax.swing.JTextField();

        dataBox = new WTextBox(tf_PropData, true);
        dataBox.initView();

        pl_PropConf = new javax.swing.JPanel();
        pl_PropConf.setLayout(new java.awt.FlowLayout());
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
    protected javax.swing.JTextField tf_PropData;
    // 配置信息
    protected javax.swing.JPanel pl_PropConf;
}
