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
package com.magicpwd._bean.mpro;

import com.magicpwd.__i.IEditItem;
import com.magicpwd.__i.mpro.IMproBean;
import com.magicpwd._bean.APwdsBean;
import com.magicpwd._comp.WEditBox;
import com.magicpwd._comp.WTextBox;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Util;
import com.magicpwd.v.app.mpro.MproPtn;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class PwdsBean extends APwdsBean implements IMproBean
{

    private MproPtn mainPtn;
    private WEditBox dataEdit;
    private WTextBox nameBox;

    public PwdsBean(MproPtn mainPtn)
    {
        super(mainPtn);
        this.mainPtn = mainPtn;
    }

    @Override
    public void initView()
    {
        dataEdit = new WEditBox(mainPtn, this, false);
        dataEdit.initView();

        lb_PropConf = new javax.swing.JLabel();
        initConfView();

        lb_PropName = new javax.swing.JLabel();
        tf_PropName = new javax.swing.JTextField(14);
        lb_PropName.setLabelFor(tf_PropName);
        nameBox = new WTextBox(tf_PropName, true);
        nameBox.initView();

        lb_PropData = new javax.swing.JLabel();
        lb_PropData.setLabelFor(pfPropData);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lb_PropConf);
        hpg1.addComponent(lb_PropData);
        hpg1.addComponent(lb_PropName);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(tf_PropName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(pfPropData, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE);
        hpg2.addComponent(plPropConf, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addGroup(hpg1);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg2);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(dataEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lb_PropName);
        vpg1.addComponent(tf_PropName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lb_PropData);
        vpg2.addComponent(pfPropData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg3.addComponent(lb_PropConf);
        vpg3.addComponent(plPropConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addGroup(vpg1);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg2);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg3);
        vsg1.addContainerGap(14, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        vpg.addGroup(vsg1);
        vpg.addComponent(dataEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        layout.setVerticalGroup(vpg);
    }

    @Override
    public void initLang()
    {
        Lang.setWText(lb_PropName, LangRes.P30F1309, "名称");
        Lang.setWText(lb_PropData, LangRes.P30F130A, "口令");

        initConfLang();

        nameBox.initLang();
        dataEdit.initLang();
    }

    @Override
    public void initData()
    {
        initConfData();

        nameBox.initData();
    }

    @Override
    public void showData(IEditItem item)
    {
        itemData = item;

        tf_PropName.setText(showName());
        pfPropData.setText(itemData.getData());
    }

    @Override
    public void requestFocus()
    {
        if (!com.magicpwd._util.Char.isValidate(tf_PropName.getText()))
        {
            tf_PropName.requestFocus();
            return;
        }
        pfPropData.requestFocus();
    }

    @Override
    public void dropDataActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (Lang.showFirm(formPtn, LangRes.P30F1A01, "确认要删除此属性数据么？") == javax.swing.JOptionPane.YES_OPTION)
        {
            mainPtn.removeSelectedItem();
        }
    }

    @Override
    public void saveDataActionPerformed(java.awt.event.ActionEvent evt)
    {
        String name = tf_PropName.getText();
        if (!com.magicpwd._util.Char.isValidate(name))
        {
            Lang.showMesg(formPtn, LangRes.P30F7A35, "请输入口令名称！");
            tf_PropName.requestFocus();
            return;
        }

        itemData.setName(name);
        itemData.setData(new String(pfPropData.getPassword()));

        mainPtn.updateSelectedItem();
    }

    @Override
    public void copyDataActionPerformed(java.awt.event.ActionEvent evt)
    {
        pfPropData.selectAll();
        Util.setClipboardContents(new String(pfPropData.getPassword()), formPtn.getUserMdl().getClipDlt());
    }
    private javax.swing.JLabel lb_PropData;
    private javax.swing.JLabel lb_PropName;
    private javax.swing.JTextField tf_PropName;
    // 配置信息
    private javax.swing.JLabel lb_PropConf;
}
