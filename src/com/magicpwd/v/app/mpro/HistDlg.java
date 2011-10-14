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
package com.magicpwd.v.app.mpro;

import com.magicpwd.__i.IEditItem;
import com.magicpwd._comn.mpwd.MpwdHeader;
import com.magicpwd._comn.S1S2;
import com.magicpwd._comn.item.GuidItem;
import com.magicpwd._comn.item.HintItem;
import com.magicpwd._comn.item.LogoItem;
import com.magicpwd._comn.item.MetaItem;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.mpro.AttModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Amon
 */
public class HistDlg extends javax.swing.JDialog
{

    private int indx;
    private MpwdHeader keys;
    private List<S1S2> hist;
    private List<IEditItem> ls_ItemList;
    private DefaultListModel lm_HistList;
    private AttModel gridMdl;
    private MproPtn mproPtn;

    public HistDlg(MproPtn mproPtn, AttModel gridMdl)
    {
        super(mproPtn, true);
        this.mproPtn = mproPtn;
        this.gridMdl = gridMdl;
        keys = new MpwdHeader();
    }

    public void initView()
    {
        bt_DropAll = new javax.swing.JButton();
        bt_DropCur = new javax.swing.JButton();
        bt_PickCur = new javax.swing.JButton();
        javax.swing.JScrollPane jsp1 = new javax.swing.JScrollPane();
        ls_HistList = new javax.swing.JList();
        javax.swing.JScrollPane jsp2 = new javax.swing.JScrollPane();
        ta_HistInfo = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bt_DropAll.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_DropAllActionPerformed(evt);
            }
        });

        bt_DropCur.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_DropCurActionPerformed(evt);
            }
        });

        bt_PickCur.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_PickCurActionPerformed(evt);
            }
        });

        ls_HistList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {

            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                ls_HistListValueChanged(evt);
            }
        });
        jsp1.setViewportView(ls_HistList);

        jsp2.setViewportView(ta_HistInfo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(bt_PickCur);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(bt_DropCur);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(bt_DropAll);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addComponent(jsp1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED);
        hsg2.addComponent(jsp2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup hpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hsg1);
        hpg.addGroup(hsg2);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addGroup(hpg);
        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg1.addComponent(jsp2, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE);
        vpg1.addComponent(jsp1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(bt_DropAll);
        vpg2.addComponent(bt_DropCur);
        vpg2.addComponent(bt_PickCur);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        vsg.addGroup(vpg1);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg2);
        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);

        pack();
        setResizable(false);
    }

    public void initLang()
    {
        setTitle(Lang.getLang(LangRes.P30F1207, "历史记录"));

        Lang.setWText(bt_PickCur, LangRes.P30F850D, "@R");
        Lang.setWTips(bt_PickCur, LangRes.P30F850E, "恢复(Alt + R)");

        Lang.setWText(bt_DropCur, LangRes.P30F850F, "@C");
        Lang.setWTips(bt_DropCur, LangRes.P30F8510, "删除当前(Alt + C)");

        Lang.setWText(bt_DropAll, LangRes.P30F8511, "@D");
        Lang.setWTips(bt_DropAll, LangRes.P30F8512, "删除所有(Alt + D)");
    }

    public void initData()
    {
        if (lm_HistList != null)
        {
            lm_HistList.clear();
            hist.clear();
        }
        else
        {
            keys = new MpwdHeader();
            hist = new ArrayList<S1S2>();
            ls_ItemList = new ArrayList<IEditItem>();

            lm_HistList = new DefaultListModel();
            ls_HistList.setModel(lm_HistList);
        }

        DBA4000.selectHistData(mproPtn.getUserMdl(), gridMdl.getKeysHash(), hist);
        for (S1S2 temp : hist)
        {
            lm_HistList.addElement(temp);
        }
        ta_HistInfo.setText("");
        indx = -1;
    }

    public void saveData()
    {
    }

    private void ls_HistListValueChanged(javax.swing.event.ListSelectionEvent evt)
    {
        int idx = ls_HistList.getSelectedIndex();
        if (idx < 0 || idx == indx)
        {
            return;
        }
        indx = idx;

        S1S2 item = (S1S2) ls_HistList.getSelectedValue();
        if (item == null)
        {
            return;
        }

        try
        {
            keys.setDefault();
            DBA4000.selectHistData(mproPtn.getUserMdl(), item.getK(), keys);
            gridMdl.deCrypt(keys, ls_ItemList);

            StringBuilder sb = new StringBuilder();
            idx = 0;
            String t = Lang.getLang(LangRes.P30FA101, "：");
            GuidItem guid = (GuidItem) ls_ItemList.get(idx++);
            sb.append(Lang.getLang(LangRes.P30FA102, "时间")).append(t).append(guid.getName()).append('\n');
            MetaItem meta = (MetaItem) ls_ItemList.get(idx++);
            sb.append(Lang.getLang(LangRes.P30FA103, "标题")).append(t).append(meta.getName()).append('\n');
            LogoItem logo = (LogoItem) ls_ItemList.get(idx++);
            sb.append(Lang.getLang(LangRes.P30FA104, "徽标")).append(t);
            if (Char.isValidate(logo.getPath()))
            {
                sb.append(logo.getPath()).append(java.io.File.separator);
            }
            sb.append(logo.getName()).append('\n');
            HintItem hint = (HintItem) ls_ItemList.get(idx++);
            sb.append(Lang.getLang(LangRes.P30FA105, "提醒")).append(t).append(hint.getData()).append('\n');
            IEditItem temp;
            for (int j = ls_ItemList.size(); idx < j; idx += 1)
            {
                temp = ls_ItemList.get(idx);
                sb.append(temp.getName()).append(t).append(temp.getData()).append('\n');
            }
            ta_HistInfo.setText(sb.toString());
            ls_ItemList.clear();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(this, LangRes.P30FAA0F, "");
        }
    }

    public void bt_DropCurActionPerformed(java.awt.event.ActionEvent evt)
    {
        S1S2 temp = (S1S2) ls_HistList.getSelectedValue();
        if (temp == null)
        {
            return;
        }

        if (Lang.showFirm(this, LangRes.P30FAA11, "确认要删除选中的历史信息么，此操作不可恢复？") != JOptionPane.YES_OPTION)
        {
            return;
        }
        DBA4000.deleteHistData(mproPtn.getUserMdl(), gridMdl.getKeysHash(), temp.getK());
        lm_HistList.removeElement(temp);
        ta_HistInfo.setText("");
    }

    private void bt_DropAllActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (lm_HistList.size() < 1)
        {
            return;
        }
        if (Lang.showFirm(this, LangRes.P30FAA12, "确认要删除此记录的所有历史信息吗，此操作将不可恢复？") != JOptionPane.YES_OPTION)
        {
            return;
        }
        DBA4000.deleteHistData(mproPtn.getUserMdl(), gridMdl.getKeysHash(), null);
        lm_HistList.clear();
        ta_HistInfo.setText("");
    }

    private void bt_PickCurActionPerformed(java.awt.event.ActionEvent evt)
    {
        S1S2 temp = (S1S2) ls_HistList.getSelectedValue();
        if (temp == null)
        {
            return;
        }

        if (Lang.showFirm(this, LangRes.P30FAA13, "为了确保您的数据安全，此操作仅复制一份选中的数据为最新数据，\n确认要执行此操作么？") != JOptionPane.YES_OPTION)
        {
            return;
        }
        DBA4000.pickupHistData(mproPtn.getUserMdl(), gridMdl.getKeysHash(), temp.getK(), 0);//TODO:数据恢复序列
        lm_HistList.clear();
        hist.clear();

        DBA4000.selectHistData(mproPtn.getUserMdl(), gridMdl.getKeysHash(), hist);
        for (int i = 0, j = hist.size(); i < j; i += 1)
        {
            lm_HistList.addElement(hist.get(i));
        }
        indx = -1;
        ta_HistInfo.setText("");
    }
    private javax.swing.JList ls_HistList;
    private javax.swing.JTextArea ta_HistInfo;
    private javax.swing.JButton bt_DropCur;
    private javax.swing.JButton bt_DropAll;
    private javax.swing.JButton bt_PickCur;
}
