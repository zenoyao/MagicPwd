/*
 *  Copyright (C) 2011 Amon
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
package com.magicpwd.v.app.mpro;

import com.magicpwd.__a.ADialog;
import com.magicpwd.__i.IBackCall;
import com.magicpwd._comn.mpwd.Mcat;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Lang;

/**
 *
 * @author Amon
 */
public class KindDlg extends ADialog
{

    private MproPtn mproPtn;
    private IBackCall<String, Mcat> backCall;
    private Mcat kind;

    public KindDlg(MproPtn mproPtn, IBackCall<String, Mcat> backCall)
    {
        super(mproPtn, true);
        this.mproPtn = mproPtn;
        this.backCall = backCall;
    }

    public void initView()
    {
        lb_KindName = new javax.swing.JLabel();
        tf_KindName = new javax.swing.JTextField();
        lb_KindName.setLabelFor(tf_KindName);

        lb_KindTips = new javax.swing.JLabel();
        tf_KindTips = new javax.swing.JTextField();
        lb_KindTips.setLabelFor(tf_KindTips);

        tf_KindKind = new javax.swing.JTextField();
        lb_KindKind = new javax.swing.JLabel();
        lb_KindKind.setLabelFor(tf_KindKind);

        ta_KindDesp = new javax.swing.JTextArea();
        lb_KindDesp = new javax.swing.JLabel();
        lb_KindDesp.setLabelFor(ta_KindDesp);

        javax.swing.JScrollPane sp_KindDesp = new javax.swing.JScrollPane(ta_KindDesp);

        bt_Abort = new javax.swing.JButton();
        bt_Apply = new javax.swing.JButton();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lb_KindKind, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lb_KindName, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lb_KindTips, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lb_KindDesp, javax.swing.GroupLayout.Alignment.TRAILING);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(tf_KindKind, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE);
        hpg2.addComponent(tf_KindName, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE);
        hpg2.addComponent(tf_KindTips, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE);
        hpg2.addComponent(sp_KindDesp, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addGroup(hpg1);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addGroup(hpg2);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addComponent(bt_Apply);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addComponent(bt_Abort);
        javax.swing.GroupLayout.ParallelGroup hpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg.addGroup(hsg1);
        hpg.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hsg2);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addGroup(hpg);
        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lb_KindName);
        vpg1.addComponent(tf_KindName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lb_KindTips);
        vpg2.addComponent(tf_KindTips, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg3.addComponent(lb_KindKind);
        vpg3.addComponent(tf_KindKind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg4 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg4.addComponent(lb_KindDesp);
        vpg4.addComponent(sp_KindDesp, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup vpg5 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg5.addComponent(bt_Apply);
        vpg5.addComponent(bt_Abort);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        vsg.addGroup(vpg1);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg2);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg3);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg4);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg5);
        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);

        this.setResizable(false);
        this.setIconImage(Bean.getLogo(16));
    }

    public void initLang()
    {
        this.setTitle("类别管理");

        Lang.setWText(lb_KindKind, LangRes.P30F8361, "默认键值");
        Lang.setWText(lb_KindName, LangRes.P30F8362, "显示名称");
        Lang.setWText(lb_KindTips, LangRes.P30F8363, "提示信息");
        Lang.setWText(lb_KindDesp, LangRes.P30F8365, "相关说明");

        Bean.setText(bt_Abort, "取消(@C)");
        Bean.setText(bt_Apply, "确认(@O)");
    }

    public void initData(Mcat kind)
    {
        bt_Abort.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                hideDialog();
            }
        });
        bt_Apply.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                bt_ApplyActionPerformed(e);
            }
        });

        if (kind != null)
        {
            tf_KindName.setText(kind.getC2010206());
            tf_KindTips.setText(kind.getC2010207());
            tf_KindKind.setText(kind.getC2010209());
            ta_KindDesp.setText(kind.getC201020A());
        }
        this.kind = kind;

        processEscape();

        this.pack();
        Bean.centerForm(this, mproPtn);
    }

    @Override
    protected boolean hideDialog()
    {
        setVisible(false);
        if (backCall != null)
        {
            backCall.callBack(IBackCall.OPTIONS_ABORT, null);
        }
        return true;
    }

    private void bt_ApplyActionPerformed(java.awt.event.ActionEvent e)
    {
        String kindName = tf_KindName.getText();
        if (!com.magicpwd._util.Char.isValidate(kindName))
        {
            Lang.showMesg(this, LangRes.P30F7A15, "请输入类别名称：");
            tf_KindName.requestFocus();
            return;
        }

        setVisible(false);

        if (backCall == null)
        {
            return;
        }

        if (kind == null)
        {
            kind = new Mcat();
        }

        kind.setC2010106(kindName);
        kind.setC2010207(tf_KindTips.getText());
        kind.setC2010209(tf_KindKind.getText());
        kind.setC201020A(ta_KindDesp.getText());
        backCall.callBack(IBackCall.OPTIONS_APPLY, kind);
    }
    private javax.swing.JTextField tf_KindName;
    private javax.swing.JLabel lb_KindName;
    private javax.swing.JTextField tf_KindTips;
    private javax.swing.JLabel lb_KindTips;
    private javax.swing.JTextField tf_KindKind;
    private javax.swing.JLabel lb_KindKind;
    private javax.swing.JTextArea ta_KindDesp;
    private javax.swing.JLabel lb_KindDesp;
    private javax.swing.JButton bt_Apply;
    private javax.swing.JButton bt_Abort;
}
