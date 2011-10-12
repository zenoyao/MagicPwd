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

import com.magicpwd.__a.AMpwdPtn;
import com.magicpwd.__i.IBackCall;
import com.magicpwd._comn.S1S1;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Lang;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-12-5 20:42:32
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class DatDialog extends javax.swing.JDialog
{

    private AMpwdPtn formPtn;
    private IBackCall<String, String> backCall;

    public DatDialog(AMpwdPtn mpwdPtn, IBackCall<String, String> backCall)
    {
        super(mpwdPtn, true);
        this.formPtn = mpwdPtn;
        this.backCall = backCall;
    }

    public void initView()
    {
        ls_DataList = new javax.swing.JList();
        bt_Cancel = new javax.swing.JButton();
        bt_Update = new javax.swing.JButton();

        ls_DataList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        javax.swing.JScrollPane sp_DataList = new javax.swing.JScrollPane();
        sp_DataList.setViewportView(ls_DataList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(bt_Update);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(bt_Cancel);
        javax.swing.GroupLayout.ParallelGroup hpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        hpg.addComponent(sp_DataList, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE);
        hpg.addGroup(hsg1);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addGroup(hpg);
        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(bt_Cancel);
        vpg1.addComponent(bt_Update);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        vsg.addComponent(sp_DataList, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg1);
        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(Bean.getLogo(16));
    }

    public void initLang()
    {
        Lang.setWText(bt_Update, LangRes.P30FA50A, "确定(@O)");

        Lang.setWText(bt_Cancel, LangRes.P30FA50B, "取消(@C)");

        setTitle(Lang.getLang(LangRes.P30F4207, "选择还原节点"));

        pack();
    }

    public void initData()
    {
        bt_Cancel.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_CancelActionPerformed(evt);
            }
        });

        bt_Update.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_UpdateActionPerformed(evt);
            }
        });

        pack();
        Bean.centerForm(this, formPtn);
    }

    public void showData(java.util.List<S1S1> list)
    {
        javax.swing.DefaultListModel model = new javax.swing.DefaultListModel();
        if (list != null)
        {
            for (S1S1 item : list)
            {
                model.addElement(item);
            }
        }
        ls_DataList.setModel(model);
    }

    void bt_UpdateActionPerformed(java.awt.event.ActionEvent evt)
    {
        Object obj = ls_DataList.getSelectedValue();
        if (obj == null || !(obj instanceof S1S1))
        {
            Lang.showMesg(this, LangRes.P30FAA1E, "请选择您要恢复的节点！");
            return;
        }

        this.setVisible(false);

        S1S1 item = (S1S1) obj;
        backCall.callBack(IBackCall.OPTIONS_APPLY, item.getK());

        this.dispose();
    }

    void bt_CancelActionPerformed(java.awt.event.ActionEvent evt)
    {
        this.setVisible(false);

        if (backCall != null)
        {
            backCall.callBack(IBackCall.OPTIONS_ABORT, null);
        }

        this.dispose();
    }
    private javax.swing.JList ls_DataList;
    private javax.swing.JButton bt_Update;
    private javax.swing.JButton bt_Cancel;
}
