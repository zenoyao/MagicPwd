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
import com.magicpwd._bean.AAreaBean;
import com.magicpwd._comp.WEditBox;
import com.magicpwd._comp.WTextBox;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Util;
import com.magicpwd.v.app.mpro.MproPtn;

/**
 * 属性：附注
 * 键值：ConsEnv.INDX_AREA
 * @author Amon
 */
public class AreaBean extends AAreaBean implements IMproBean
{

    private MproPtn mainPtn;
    private WEditBox dataEdit;
    private WTextBox nameBox;

    public AreaBean(MproPtn mainPtn)
    {
        super(mainPtn);
        this.mainPtn = mainPtn;
    }

    @Override
    public void initView()
    {
        dataEdit = new WEditBox(mainPtn, this, false);
        dataEdit.initView();

        initConfView();

        lbPropName = new javax.swing.JLabel();
        tfPropName = new javax.swing.JTextField(14);
        lbPropName.setLabelFor(tfPropName);
        nameBox = new WTextBox(tfPropName, true);
        nameBox.initView();

        lbPropData = new javax.swing.JLabel();
        lbPropData.setLabelFor(ta_PropData);
        javax.swing.JScrollPane sp_PropData = new javax.swing.JScrollPane(ta_PropData);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lbPropName);
        hpg1.addComponent(lbPropData);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(tfPropName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(sp_PropData, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addGroup(hpg1);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg2);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(dataEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER);
        vpg1.addComponent(lbPropName);
        vpg1.addComponent(tfPropName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addComponent(lbPropData);
        vsg1.addContainerGap(49, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg2.addGroup(vsg1);
        vpg2.addComponent(sp_PropData, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup vsg2 = layout.createSequentialGroup();
        vsg2.addGroup(vpg1);
        vsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg2.addGroup(vpg2);
        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg.addGroup(vsg2);
        vpg.addComponent(dataEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        layout.setVerticalGroup(vpg);
    }

    @Override
    public void initLang()
    {
        Lang.setWText(lbPropName, LangRes.P30F1311, "名称");
        Lang.setWText(lbPropData, LangRes.P30F1312, "附注");

        nameBox.initLang();
        dataEdit.initLang();

        initConfLang();
    }

    @Override
    public void initData()
    {
        nameBox.initData();

        initConfData();
    }

    @Override
    public void showData(IEditItem item)
    {
        itemData = item;

        tfPropName.setText(showName());
        ta_PropData.setText(itemData.getData());

        showConfData();
    }

    @Override
    public void requestFocus()
    {
        if (!com.magicpwd._util.Char.isValidate(tfPropName.getText()))
        {
            tfPropName.requestFocus();
            return;
        }
        ta_PropData.requestFocus();
    }

    @Override
    public void copyDataActionPerformed(java.awt.event.ActionEvent evt)
    {
        ta_PropData.selectAll();
        Util.setClipboardContents(ta_PropData.getText());
    }

    @Override
    public void dropDataActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (Lang.showFirm(mainPtn, LangRes.P30F1A01, "确认要删除此属性数据么？") == javax.swing.JOptionPane.YES_OPTION)
        {
            mainPtn.removeSelectedItem();
        }
    }

    @Override
    public void saveDataActionPerformed(java.awt.event.ActionEvent evt)
    {
        String name = tfPropName.getText();
        if (!com.magicpwd._util.Char.isValidate(name))
        {
            Lang.showMesg(mainPtn, LangRes.P30F7A38, "请输入附注名称！");
            tfPropName.requestFocus();
            return;
        }

        itemData.setName(name);
        itemData.setData(ta_PropData.getText());

        mainPtn.updateSelectedItem();
    }
    private javax.swing.JLabel lbPropData;
    private javax.swing.JLabel lbPropName;
    private javax.swing.JTextField tfPropName;
    // 配置信息
}
