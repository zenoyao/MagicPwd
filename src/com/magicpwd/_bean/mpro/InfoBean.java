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
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.v.app.mpro.MproPtn;

/**
 * 属性：信息
 * 键值：ConsEnv.INDX_INFO
 * @author Amon
 */
public class InfoBean extends javax.swing.JPanel implements IMproBean
{

    private int tipsSize;

    public InfoBean(MproPtn mainPtn)
    {
    }

    @Override
    public void initView()
    {
        lb_PropName = new javax.swing.JLabel();
        lb_PropName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        ta_PropData = new javax.swing.JTextArea();
        ta_PropData.setEditable(false);
        ta_PropData.setEnabled(false);
        ta_PropData.setLineWrap(true);
        ta_PropData.setOpaque(false);
        ta_PropData.setTabSize(4);

        lb_NextTips = new javax.swing.JLabel();
        lb_NextTips.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                lb_NextTipsMouseReleased(evt);
            }
        });
        lb_NextTips.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg.addComponent(lb_PropName, javax.swing.GroupLayout.PREFERRED_SIZE, 260, Short.MAX_VALUE);
        hpg.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(lb_NextTips));
        hpg.addComponent(ta_PropData, javax.swing.GroupLayout.PREFERRED_SIZE, 260, Short.MAX_VALUE);
        layout.setHorizontalGroup(hpg);

        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addComponent(lb_PropName);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addComponent(ta_PropData, javax.swing.GroupLayout.PREFERRED_SIZE, 49, Short.MAX_VALUE);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addComponent(lb_NextTips);
        layout.setVerticalGroup(vsg);
    }

    @Override
    public void initLang()
    {
        Lang.setWText(lb_PropName, LangRes.P30F1317, "每日提示");
        Lang.setWText(lb_NextTips, LangRes.P30F1318, "下一提示");
    }

    @Override
    public void initData()
    {
    }

    @Override
    public void showData(IEditItem item)
    {
        if (item != null)
        {
            lb_PropName.setText(item.getName());
            ta_PropData.setText(item.getData());
            return;
        }
        try
        {
            tipsSize = Integer.parseInt(Lang.getLang(LangRes.P30F1B00, "0"), 16);
        }
        catch (Exception exp)
        {
            tipsSize = 0;
        }
        tipsSize -= 1;

        Lang.setWText(ta_PropData, LangRes.P30F1B01, "您可以使用快捷键 Ctrl+N 快速开始添加一个新的密码记录！");

        nextTips();
    }

    @Override
    public void requestFocus()
    {
    }

    private void lb_NextTipsMouseReleased(java.awt.event.MouseEvent evt)
    {
        nextTips();
    }

    private void nextTips()
    {
        Logs.log("提示数量：" + tipsSize);
        if (tipsSize <= 0)
        {
            return;
        }

        // 显示提示信息
        String t = Integer.toHexString(new java.util.Random().nextInt(tipsSize) + 1);
        t = "P30F1B" + com.magicpwd._util.Char.lPad(t, 2, '0').toUpperCase();
        Lang.setWText(ta_PropData, t, "");
    }

    @Override
    public void saveDataActionPerformed(java.awt.event.ActionEvent evt)
    {
    }

    @Override
    public void copyDataActionPerformed(java.awt.event.ActionEvent evt)
    {
    }

    @Override
    public void dropDataActionPerformed(java.awt.event.ActionEvent evt)
    {
    }
    private javax.swing.JLabel lb_NextTips;
    private javax.swing.JLabel lb_PropName;
    private javax.swing.JTextArea ta_PropData;
}
