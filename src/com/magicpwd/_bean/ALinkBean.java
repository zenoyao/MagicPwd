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
import com.magicpwd._comp.BtnLabel;
import com.magicpwd._comp.WTextBox;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Desk;
import com.magicpwd._util.Lang;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-10-27 20:23:07
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public abstract class ALinkBean extends AEditBean
{

    private WTextBox dataBox;

    public ALinkBean(AMpwdPtn mpwdPtn)
    {
        this.formPtn = mpwdPtn;
    }

    protected void initConfView()
    {
        tf_PropData = new javax.swing.JTextField();
        dataBox = new WTextBox(tf_PropData, true);
        dataBox.initView();

        pl_PropConf = new javax.swing.JPanel();
        pl_PropConf.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 0));

        bt_LinkView = new BtnLabel();
        bt_LinkView.setIcon(formPtn.getFeelIcon(null,"var:link-open"));
        bt_LinkView.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_LinkViewActionPerformed(evt);
            }
        });
        pl_PropConf.add(bt_LinkView);
    }

    protected void initConfLang()
    {
        Lang.setWText(bt_LinkView, LangRes.P30F150F, "@O");
        Lang.setWTips(bt_LinkView, LangRes.P30F1510, "打开链接(Alt + O)");

        dataBox.initLang();
    }

    protected void initConfData()
    {
        dataBox.initData();
    }

    protected void showConfData()
    {
    }

    private void bt_LinkViewActionPerformed(java.awt.event.ActionEvent evt)
    {
        String link = tf_PropData.getText();
        if (!com.magicpwd._util.Char.isValidate(link))
        {
            Lang.showMesg(formPtn, "", "");
            return;
        }
        if (link.indexOf("://") < 0)
        {
            link = "http://" + link;
        }
        Desk.browse(link);
    }
    protected javax.swing.JTextField tf_PropData;
    // 配置信息
    protected javax.swing.JPanel pl_PropConf;
    private BtnLabel bt_LinkView;
}
