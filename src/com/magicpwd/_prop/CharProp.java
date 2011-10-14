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
package com.magicpwd._prop;

import javax.swing.DefaultComboBoxModel;

import com.magicpwd.__i.IPropBean;
import com.magicpwd._comn.prop.Mucs;
import com.magicpwd._comp.IcoLabel;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;
import com.magicpwd.m.UcsList;
import com.magicpwd.r.ListCR;
import com.magicpwd.v.app.mpro.MproPtn;

/**
 * @author Amon
 * 
 */
public class CharProp extends javax.swing.JPanel implements IPropBean
{

    /**
     * 当前编辑的字符空间
     */
    private Mucs charItem;
    private boolean isUpdate;
    private MproPtn mainPtn;

    public CharProp(MproPtn mainPtn)
    {
        this.mainPtn = mainPtn;
    }

    @Override
    public void initView()
    {
        initSortView();
        initInfoView();
        initBaseView();
    }

    @Override
    public void initLang()
    {
        initSortLang();
        initInfoLang();
        initBaseLang();
    }

    @Override
    public void initData()
    {
        if (cbCharTplt.getItemCount() < 1)
        {
            UcsList cm = mainPtn.getUserMdl().getCharMdl();
            lsCharList.setModel(cm);

            DefaultComboBoxModel cm_CharTplt = new DefaultComboBoxModel();
            Mucs c = new Mucs();
            c.setP30F2103("0");
            c.setP30F2104(Lang.getLang(LangRes.P30F1114, "请选择"));
            c.setP30F2105(Lang.getLang(LangRes.P30F1114, "请选择"));
            c.setP30F2106("");
            cm_CharTplt.addElement(c);
            for (Mucs item : cm.getCharSys())
            {
                cm_CharTplt.addElement(item);
            }
            cbCharTplt.setModel(cm_CharTplt);
        }
    }

    @Override
    public void showData()
    {
    }

    @Override
    public void saveData()
    {
    }

    @Override
    public javax.swing.JPanel getPanel()
    {
        return this;
    }

    private void initInfoView()
    {
        plItemInfo = new javax.swing.JPanel();

        cbCharTplt = new javax.swing.JComboBox();
        cbCharTplt.setRenderer(new ListCR());
        cbCharTplt.addItemListener(new java.awt.event.ItemListener()
        {

            @Override
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                cbCharTpltItemStateChanged(evt);
            }
        });
        lbCharTplt = new javax.swing.JLabel();
        lbCharTplt.setLabelFor(cbCharTplt);

        tfCharName = new javax.swing.JTextField(16);
        lbCharName = new javax.swing.JLabel();
        lbCharName.setLabelFor(tfCharName);

        tfCharTips = new javax.swing.JTextField(16);
        lbCharTips = new javax.swing.JLabel();
        lbCharTips.setLabelFor(tfCharTips);

        taCharSets = new javax.swing.JTextArea();
        taCharSets.setLineWrap(true);
        javax.swing.JScrollPane sp_ItemDesp = new javax.swing.JScrollPane(taCharSets);
        lbCharSets = new javax.swing.JLabel();
        lbCharSets.setLabelFor(taCharSets);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(plItemInfo);
        plItemInfo.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lbCharSets);
        hpg1.addComponent(lbCharTips);
        hpg1.addComponent(lbCharName);
        hpg1.addComponent(lbCharTplt);
        hpg1.addComponent(plItemSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(cbCharTplt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(tfCharName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(tfCharTips, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(sp_ItemDesp, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addGroup(hpg1);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg2);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lbCharTplt);
        vpg1.addComponent(cbCharTplt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lbCharName);
        vpg2.addComponent(tfCharName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg3.addComponent(lbCharTips);
        vpg3.addComponent(tfCharTips, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addComponent(lbCharSets);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addComponent(plItemSort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup vpg4 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg4.addGroup(vsg1);
        vpg4.addComponent(sp_ItemDesp, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup vsg2 = layout.createSequentialGroup();
        vsg2.addGroup(vpg1);
        vsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg2.addGroup(vpg2);
        vsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg2.addGroup(vpg3);
        vsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg2.addGroup(vpg4);
        layout.setVerticalGroup(vsg2);
    }

    private void initSortView()
    {
        plItemSort = new javax.swing.JPanel();

        btDropData = new IcoLabel();
        btDropData.setIcon(mainPtn.getFeelIcon(null, "var:edit-delete-cur"));
        btDropData.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                dropDataActionPerformed(evt);
            }
        });

        btSaveData = new IcoLabel();
        btSaveData.setIcon(mainPtn.getFeelIcon(null, "var:file-save"));
        btSaveData.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveDataActionPerformed(evt);
            }
        });

        btApndData = new IcoLabel();
        btApndData.setIcon(mainPtn.getFeelIcon(null, "var:file-new"));
        btApndData.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                apndDataActionPerformed(evt);
            }
        });
        btSortU = new IcoLabel();
        btSortU.setIcon(mainPtn.getFeelIcon(null, "var:edit-move-up"));
        btSortU.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                sortUActionPerformed(evt);
            }
        });
        add(btSortU);

        btSortD = new IcoLabel();
        btSortD.setIcon(mainPtn.getFeelIcon(null, "var:edit-move-down"));
        btSortD.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                sortDActionPerformed(evt);
            }
        });
        add(btSortD);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(plItemSort);
        plItemSort.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(btSortU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg1.addComponent(btSortD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(btApndData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(btSaveData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(btDropData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addGroup(hpg1);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg2);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addContainerGap(1, Short.MAX_VALUE);
        vsg1.addComponent(btApndData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addComponent(btSaveData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addComponent(btDropData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg2 = layout.createSequentialGroup();
        vsg2.addContainerGap(1, Short.MAX_VALUE);
        vsg2.addComponent(btSortU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg2.addComponent(btSortD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg.addGroup(vsg1);
        vpg.addGroup(vsg2);
        layout.setVerticalGroup(vpg);
    }

    private void initBaseView()
    {
        javax.swing.JScrollPane sp_ItemList = new javax.swing.JScrollPane();
        lsCharList = new javax.swing.JList();
        lsCharList.setCellRenderer(new ListCR());
        lsCharList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sp_ItemList.setViewportView(lsCharList);
        lsCharList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {

            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                lsCharListValueChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addComponent(sp_ItemList, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(plItemInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        layout.setHorizontalGroup(hsg);
        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg.addComponent(sp_ItemList, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE);
        vpg.addComponent(plItemInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE);
        layout.setVerticalGroup(vpg);
    }

    private void initInfoLang()
    {
        Lang.setWText(lbCharTplt, LangRes.P30F8321, "基本字符");
        Lang.setWText(lbCharName, LangRes.P30F8322, "显示名称");
        Lang.setWText(lbCharTips, LangRes.P30F8323, "提示信息");
        Lang.setWText(lbCharSets, LangRes.P30F8324, "字符空间");
    }

    public void initSortLang()
    {
        Lang.setWText(btApndData, LangRes.P30F8509, "@N");
        Lang.setWTips(btApndData, LangRes.P30F850A, "新增(Alt + N)");

        Lang.setWText(btSaveData, LangRes.P30F8507, "@S");
        Lang.setWTips(btSaveData, LangRes.P30F8508, "保存(Alt + S)");

        Lang.setWText(btDropData, LangRes.P30F850B, "@D");
        Lang.setWTips(btDropData, LangRes.P30F850C, "删除(Alt + D)");
    }

    private void initBaseLang()
    {
    }

    private void sortUActionPerformed(java.awt.event.ActionEvent evt)
    {
        int indx = lsCharList.getSelectedIndex();
        if (indx < 1 || indx >= mainPtn.getUserMdl().getCharMdl().getCharUsr().size())
        {
            return;
        }

        mainPtn.getUserMdl().getCharMdl().changeItemAt(indx, -1);
        lsCharList.setSelectedIndex(indx - 1);
        mainPtn.getUserMdl().setUcsTemplateUpdated(true);
    }

    private void sortDActionPerformed(java.awt.event.ActionEvent evt)
    {
        int indx = lsCharList.getSelectedIndex();
        if (indx < 0 || indx >= mainPtn.getUserMdl().getCharMdl().getCharUsr().size() - 1)
        {
            return;
        }

        mainPtn.getUserMdl().getCharMdl().changeItemAt(indx, 1);
        lsCharList.setSelectedIndex(indx + 1);
        mainPtn.getUserMdl().setUcsTemplateUpdated(true);
    }

    private void apndDataActionPerformed(java.awt.event.ActionEvent evt)
    {
        charItem = new Mucs();
        cbCharTplt.setSelectedIndex(0);
        lsCharList.setSelectedIndex(-1);
        showInfo(charItem);
        isUpdate = false;
    }

    private void saveDataActionPerformed(java.awt.event.ActionEvent evt)
    {
        String name = tfCharName.getText();
        if (!com.magicpwd._util.Char.isValidate(name))
        {
            Lang.showMesg(this, LangRes.P30F8A02, "空间名称不能为空！");
            tfCharName.requestFocus();
            return;
        }

        String sets = taCharSets.getText();
        if (!com.magicpwd._util.Char.isValidate(sets))
        {
            Lang.showMesg(this, LangRes.P30F8A03, "空间内容不能为空！");
            taCharSets.requestFocus();
            return;
        }

        if (charItem == null)
        {
            charItem = new Mucs();
        }
        charItem.setP30F2104(name);
        charItem.setP30F2105(tfCharTips.getText());
        charItem.setP30F2106(sets);
        if (isUpdate)
        {
            mainPtn.getUserMdl().getCharMdl().updateItemAt(lsCharList.getSelectedIndex(), charItem);
        }
        else
        {
            mainPtn.getUserMdl().getCharMdl().appendItem(charItem);
        }

        charItem = new Mucs();
        cbCharTplt.setSelectedIndex(0);
        showInfo(charItem);
        isUpdate = false;
        mainPtn.getUserMdl().setUcsTemplateUpdated(true);
    }

    private void dropDataActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (charItem == null)
        {
            Lang.showMesg(this, LangRes.P30F8A04, "请选择您要删除的类别数据！");
            lsCharList.requestFocus();
            return;
        }
        if (Lang.showFirm(this, LangRes.P30F8A09, "确认要删除此数据吗，此操作将不可恢复？") != javax.swing.JOptionPane.YES_OPTION)
        {
            return;
        }

        mainPtn.getUserMdl().getCharMdl().removeItemAt(lsCharList.getSelectedIndex());
        charItem = new Mucs();
        showInfo(charItem);
        isUpdate = false;
        mainPtn.getUserMdl().setUcsTemplateUpdated(true);
    }

    private void lsCharListValueChanged(javax.swing.event.ListSelectionEvent evt)
    {
        charItem = (Mucs) lsCharList.getSelectedValue();
        if (charItem == null)
        {
            return;
        }

        cbCharTplt.setSelectedIndex(0);
        showInfo(charItem);
        isUpdate = true;
    }

    private void cbCharTpltItemStateChanged(java.awt.event.ItemEvent evt)
    {
        Mucs item = (Mucs) cbCharTplt.getSelectedItem();
        taCharSets.setText(item.getP30F2106());
    }

    /**
     * 显示字符空间信息
     * @param item
     */
    private void showInfo(Mucs item)
    {
        tfCharName.setText(item.getP30F2104());
        tfCharTips.setText(item.getP30F2105());
        taCharSets.setText(item.getP30F2106());
    }
    private javax.swing.JList lsCharList;
    private javax.swing.JPanel plItemInfo;
    private javax.swing.JLabel lbCharTplt;
    private javax.swing.JComboBox cbCharTplt;
    private javax.swing.JLabel lbCharName;
    private javax.swing.JTextField tfCharName;
    private javax.swing.JLabel lbCharTips;
    private javax.swing.JTextField tfCharTips;
    private javax.swing.JLabel lbCharSets;
    private javax.swing.JTextArea taCharSets;
    private javax.swing.JPanel plItemSort;
    private IcoLabel btApndData;
    private IcoLabel btSaveData;
    private IcoLabel btDropData;
    private IcoLabel btSortU;
    private IcoLabel btSortD;
}
