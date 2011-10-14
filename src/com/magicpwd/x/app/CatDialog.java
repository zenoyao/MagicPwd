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
package com.magicpwd.x.app;

import com.magicpwd.__a.ADialog;
import com.magicpwd.__i.IBackCall;
import com.magicpwd._comn.mpwd.Mcat;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Lang;
import com.magicpwd.r.mpro.CatNode;
import com.magicpwd.v.app.mpro.MproPtn;

/**
 * 数据迁移对话窗口
 * @author Amon
 */
public class CatDialog extends ADialog
{

    private MproPtn mainPtn;
    private IBackCall<String, String> backCall;

    public CatDialog(MproPtn mainPtn, IBackCall<String, String> backCall)
    {
        super(mainPtn, true);
        this.mainPtn = mainPtn;
        this.backCall = backCall;
    }

    public void initView()
    {
        tr_KindList = new javax.swing.JTree();
        bt_Abort = new javax.swing.JButton();
        bt_Apply = new javax.swing.JButton();

        javax.swing.JScrollPane sp_KindList = new javax.swing.JScrollPane();
        tr_KindList.setModel(mainPtn.getTreeMdl());
        sp_KindList.setViewportView(tr_KindList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(bt_Apply);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(bt_Abort);
        javax.swing.GroupLayout.ParallelGroup hpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        hpg.addComponent(sp_KindList, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE);
        hpg.addGroup(hsg1);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addGroup(hpg);
        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(bt_Abort);
        vpg1.addComponent(bt_Apply);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        vsg.addComponent(sp_KindList, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg1);
        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(Bean.getLogo(16));
        pack();
        Bean.centerForm(this, mainPtn);
    }

    public void initLang()
    {
        Lang.setWText(bt_Apply, LangRes.P30FA50A, "确定(@O)");

        Lang.setWText(bt_Abort, LangRes.P30FA50B, "取消(@C)");

        setTitle(Lang.getLang(LangRes.P30F4206, "把记录迁移到..."));
    }

    public void initData()
    {
        bt_Abort.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                hideDialog();
            }
        });

        bt_Apply.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_ApplyActionPerformed(evt);
            }
        });

        processEscape();
    }

    @Override
    protected boolean hideDialog()
    {
        this.setVisible(false);
        this.dispose();
        return true;
    }

    private void bt_ApplyActionPerformed(java.awt.event.ActionEvent evt)
    {
        javax.swing.tree.TreePath tp = tr_KindList.getSelectionPath();
        if (tp == null)
        {
            Lang.showMesg(this, LangRes.P30FAA19, "请选择您要移动的目标类别！");
            return;
        }

        Object obj = tp.getLastPathComponent();
        if (obj instanceof CatNode)
        {
            CatNode item = (CatNode) obj;
            Mcat kind = (Mcat) item.getUserObject();
            backCall.callBack(IBackCall.OPTIONS_APPLY, kind.getC2010203());
        }
        this.setVisible(false);
        this.dispose();
    }
    private javax.swing.JTree tr_KindList;
    private javax.swing.JButton bt_Apply;
    private javax.swing.JButton bt_Abort;
}
