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
package com.magicpwd._comp;

import com.magicpwd.__i.mpro.IMproBean;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;
import com.magicpwd.v.app.mpro.MproPtn;

/**
 *
 * @author Amon
 */
public class WEditBox extends javax.swing.JPanel
{

    private IMproBean mproBean;
    private MproPtn mainPtn;
    private boolean metaData;

    public WEditBox(MproPtn mainPtn, IMproBean bean, boolean meta)
    {
        this.mainPtn = mainPtn;
        mproBean = bean;
        metaData = meta;
    }

    public void initView()
    {
        bt_DropData = new BtnLabel();
        bt_DropData.setIcon(mainPtn.getFeelIcon("prop-drop", "var:prop-drop"));
        bt_DropData.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mproBean.dropDataActionPerformed(evt);
            }
        });

        bt_SaveData = new BtnLabel();
        bt_SaveData.setIcon(mainPtn.getFeelIcon("prop-save", "var:prop-save"));
        bt_SaveData.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mproBean.saveDataActionPerformed(evt);
            }
        });

        bt_CopyData = new BtnLabel();
        bt_CopyData.setIcon(mainPtn.getFeelIcon("prop-copy", "var:prop-copy"));
        bt_CopyData.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mproBean.copyDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hgp = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hgp.addGap(0, 0, Short.MAX_VALUE);
        hgp.addComponent(bt_DropData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hgp.addComponent(bt_SaveData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hgp.addComponent(bt_CopyData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        layout.setHorizontalGroup(hgp);

        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap(1, Short.MAX_VALUE);
        vsg.addComponent(bt_CopyData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addComponent(bt_SaveData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addComponent(bt_DropData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vsg);
        layout.setVerticalGroup(vpg);
    }

    public void initLang()
    {
        if (!metaData)
        {
            Lang.setWText(bt_CopyData, LangRes.P30F1501, "@C");
            Lang.setWTips(bt_CopyData, LangRes.P30F1502, "复制属性数据(Alt + C)");
        }
        Lang.setWText(bt_SaveData, LangRes.P30F1503, "@U");
        Lang.setWTips(bt_SaveData, LangRes.P30F1504, "应用属性变更(Alt + U)");
        if (!metaData)
        {
            Lang.setWText(bt_DropData, LangRes.P30F1505, "@D");
            Lang.setWTips(bt_DropData, LangRes.P30F1506, "删除属性数据(Alt + D)");
        }
    }

    public void setCopyButtonEnabled(boolean enabled)
    {
        bt_CopyData.setEnabled(enabled);
    }

    public void setCopyButtonVisible(boolean visible)
    {
        bt_CopyData.setVisible(visible);
    }

    public void setSaveButtonEnabled(boolean enabled)
    {
        bt_SaveData.setEnabled(enabled);
    }

    public void setSaveButtonVisible(boolean visible)
    {
        bt_SaveData.setVisible(visible);
    }

    public void setDropButtonEnabled(boolean enabled)
    {
        bt_DropData.setEnabled(enabled);
    }

    public void setDropButtonVisible(boolean visible)
    {
        bt_DropData.setVisible(visible);
    }
    private BtnLabel bt_CopyData;
    private BtnLabel bt_SaveData;
    private BtnLabel bt_DropData;
}
