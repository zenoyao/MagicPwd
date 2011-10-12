/*
 *  Copyright (C) 2011 Aven
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
package com.magicpwd.x.app.mpro;

import com.magicpwd.__a.ADialog;
import com.magicpwd._comn.I1S1;
import com.magicpwd._comn.I1S2;
import com.magicpwd._comn.prop.Tplt;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Lang;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.r.TreeCR;
import com.magicpwd.v.app.mpro.MproPtn;
import java.awt.event.MouseEvent;

/**
 * Application: MagicPwd
 * Author     : Aven
 * Encoding   : UTF-8
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class TpltDialog extends ADialog
{

    private MproPtn mproPtn;
    private boolean isUpdate;
    private boolean relayout;
    private Tplt currTplt = new Tplt();
    private javax.swing.tree.DefaultTreeModel tmTpltList;
    private javax.swing.tree.DefaultMutableTreeNode rootNode;
    private javax.swing.tree.DefaultMutableTreeNode currNode;

    public TpltDialog(MproPtn mproPtn)
    {
        super(mproPtn, true);
        this.mproPtn = mproPtn;
    }

    public void initView()
    {
        trTpltList = new javax.swing.JTree();
        trTpltList.setRootVisible(false);
        trTpltList.setCellRenderer(new TreeCR());
        trTpltList.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
        javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(trTpltList);

        cbTpltKind = new javax.swing.JComboBox();
        lbTpltKind = new javax.swing.JLabel();
        lbTpltKind.setLabelFor(cbTpltKind);

        tfTpltName = new javax.swing.JTextField(14);
        lbTpltName = new javax.swing.JLabel();
        lbTpltName.setLabelFor(tfTpltName);

        lbTpltData = new javax.swing.JLabel();

        tfTpltData = new javax.swing.JTextField(14);
        cbTpltData = new javax.swing.JComboBox();
        plTpltData = new javax.swing.JPanel();
        plTpltData.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        plTpltData.add(tfTpltData);
        lbTpltData.setLabelFor(tfTpltData);

        taTpltDesp = new javax.swing.JTextArea();
        lbTpltDesp = new javax.swing.JLabel();
        lbTpltDesp.setLabelFor(taTpltDesp);
        javax.swing.JScrollPane sp2 = new javax.swing.JScrollPane(taTpltDesp);

        btSaveDat = new javax.swing.JButton();
        btNewType = new javax.swing.JButton();
        btNewProp = new javax.swing.JButton();

        pmSortMenu = new javax.swing.JPopupMenu();
        miSortUp = new javax.swing.JMenuItem();
        pmSortMenu.add(miSortUp);
        miSortDn = new javax.swing.JMenuItem();
        pmSortMenu.add(miSortDn);
        pmSortMenu.addSeparator();
        miRemove = new javax.swing.JMenuItem();
        pmSortMenu.add(miRemove);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lbTpltKind, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbTpltName, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbTpltData, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbTpltDesp, javax.swing.GroupLayout.Alignment.TRAILING);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(cbTpltKind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(tfTpltName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(plTpltData, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE);
        hpg2.addComponent(sp2, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(btNewProp);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(btNewType);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(btSaveDat);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addComponent(sp1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED);
        hsg2.addGroup(hpg1);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addGroup(hpg2);
        javax.swing.GroupLayout.ParallelGroup hpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg3.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hsg1);
        hpg3.addGroup(hsg2);
        javax.swing.GroupLayout.SequentialGroup hsg3 = layout.createSequentialGroup();
        hsg3.addContainerGap();
        hsg3.addGroup(hpg3);
        hsg3.addContainerGap();
        layout.setHorizontalGroup(hsg3);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lbTpltKind);
        vpg1.addComponent(cbTpltKind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lbTpltName);
        vpg2.addComponent(tfTpltName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg3.addComponent(lbTpltData);
        vpg3.addComponent(plTpltData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg4 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg4.addComponent(lbTpltDesp);
        vpg4.addComponent(sp2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup vpg5 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg5.addComponent(btSaveDat);
        vpg5.addComponent(btNewType);
        vpg5.addComponent(btNewProp);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addGroup(vpg1);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg2);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg3);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg4);
        javax.swing.GroupLayout.ParallelGroup vpg6 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg6.addComponent(sp1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE);
        vpg6.addGroup(vsg1);
        javax.swing.GroupLayout.SequentialGroup vsg2 = layout.createSequentialGroup();
        vsg2.addContainerGap();
        vsg2.addGroup(vpg6);
        vsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg2.addGroup(vpg5);
        vsg2.addContainerGap();
        layout.setVerticalGroup(vsg2);
    }

    public void initLang()
    {
        Bean.setText(lbTpltKind, "属性类型(@T)");
        Bean.setText(lbTpltName, "属性名称(@N)");
        Bean.setText(lbTpltData, "默认数据(@D)");
        Bean.setText(lbTpltDesp, "备注(@M)");

        Bean.setText(btNewProp, "新增属性(@P)");
        Bean.setText(btNewType, "新增类别(@K)");
        Bean.setText(btSaveDat, "保存(@S)");

        Bean.setText(miSortUp, "上移一行(@U)");
        Bean.setText(miSortDn, "下移一行(@D)");
        Bean.setText(miRemove, "删除(@R)");

        setTitle(Lang.getLang(LangRes.P30F1204, "模板管理"));
    }

    public void initData()
    {
        cbTpltKind.addItem(new I1S1(0, "请选择"));
        cbTpltKind.addItem(new I1S1(ConsDat.INDX_TEXT, "文本"));
        cbTpltKind.addItem(new I1S1(ConsDat.INDX_PWDS, "口令"));
        cbTpltKind.addItem(new I1S1(ConsDat.INDX_LINK, "链接"));
        cbTpltKind.addItem(new I1S1(ConsDat.INDX_MAIL, "邮件"));
        cbTpltKind.addItem(new I1S1(ConsDat.INDX_DATE, "日期"));
        cbTpltKind.addItem(new I1S1(ConsDat.INDX_AREA, "备注"));
        cbTpltKind.addItem(new I1S1(ConsDat.INDX_FILE, "文件"));
        cbTpltKind.addItem(new I1S1(ConsDat.INDX_DATA, "数值"));
        cbTpltKind.addItem(new I1S1(ConsDat.INDX_LIST, "列表"));
        cbTpltKind.addItem(new I1S1(ConsDat.INDX_SIGN, "分组"));

        rootNode = new javax.swing.tree.DefaultMutableTreeNode("Root");
        java.util.List<Tplt> propList;
        javax.swing.tree.DefaultMutableTreeNode node;
        for (Tplt tplt : mproPtn.getUserMdl().getTpltMdl().getAllItems())
        {
            node = new javax.swing.tree.DefaultMutableTreeNode(tplt);

            propList = DBA4000.selectTpltData(mproPtn.getUserMdl(), tplt.getP30F1103());
            for (Tplt prop : propList)
            {
                node.add(new javax.swing.tree.DefaultMutableTreeNode(prop));
            }

            rootNode.add(node);
        }
        tmTpltList = new javax.swing.tree.DefaultTreeModel(rootNode);
        trTpltList.setModel(tmTpltList);
        trTpltList.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
        {

            @Override
            public void valueChanged(javax.swing.event.TreeSelectionEvent e)
            {
                trTpltListValueChanged(e);
            }
        });
        trTpltList.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseReleased(MouseEvent e)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        btSaveDat.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btSaveDatActionPerformed(evt);
            }
        });

        btNewType.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btNewTypeActionPerformed(evt);
            }
        });

        btNewProp.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btNewPropActionPerformed(evt);
            }
        });
        cbTpltKind.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cbTpltKindActionPerformed(evt);
            }
        });
        miSortUp.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miSortUpActionPerformed(evt);
            }
        });
        miSortDn.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miSortDnActionPerformed(evt);
            }
        });

        pack();
        Bean.centerForm(this, mproPtn);
    }

    @Override
    protected boolean hideDialog()
    {
        return true;
    }

    public void trTpltListValueChanged(javax.swing.event.TreeSelectionEvent evt)
    {
        javax.swing.tree.TreePath path = trTpltList.getSelectionPath();
        if (path == null)
        {
            return;
        }
        currNode = (javax.swing.tree.DefaultMutableTreeNode) path.getLastPathComponent();
        if (currNode == null)
        {
            return;
        }
        Object obj = currNode.getUserObject();
        if (!(obj instanceof Tplt))
        {
            return;
        }
        currTplt = (Tplt) currNode.getUserObject();
        viewInfo(currTplt);
        isUpdate = true;
    }

    private void btNewPropActionPerformed(java.awt.event.ActionEvent evt)
    {
        currTplt = new Tplt(false);
        viewInfo(currTplt);
        isUpdate = false;
    }

    private void btNewTypeActionPerformed(java.awt.event.ActionEvent evt)
    {
        currTplt = new Tplt(true);
        viewInfo(currTplt);
        isUpdate = false;
    }

    private void btSaveDatActionPerformed(java.awt.event.ActionEvent evt)
    {
        int indx = cbTpltKind.getSelectedIndex();
        if (indx < 0)
        {
            Lang.showMesg(this, LangRes.P30F8A05, "请选择属性类别！");
            cbTpltKind.requestFocus();
            return;
        }

        String name = tfTpltName.getText();
        if (!com.magicpwd._util.Char.isValidate(name))
        {
            Lang.showMesg(this, LangRes.P30F8A06, "类别名称不能为空！");
            tfTpltName.requestFocus();
            return;
        }

        String tips = tfTpltData.getText();
        if (indx == 0)
        {
            tips = tips.replaceAll("[^\\w]+", " ").trim();
        }
        else
        {
            if (!name.startsWith(ConsDat.SP_TPL_LS))
            {
                name = ConsDat.SP_TPL_LS + name;
            }
            if (!name.endsWith(ConsDat.SP_TPL_RS))
            {
                name = name + ConsDat.SP_TPL_RS;
            }
        }

        if (currTplt == null)
        {
            currTplt = new Tplt();
            isUpdate = false;
        }

        currTplt.setP30F1102(indx);
        currTplt.setP30F1105(name);
        currTplt.setP30F1106(tips);
        currTplt.setP30F1107(taTpltDesp.getText());

        if (isUpdate)
        {
            DBA4000.saveTpltData(mproPtn.getUserMdl(), currTplt);
            tmTpltList.nodeChanged(currNode);
            mproPtn.getUserMdl().getTpltMdl().wUpdate();

            isUpdate = false;
        }
        else
        {
            javax.swing.tree.DefaultMutableTreeNode node;
            if (indx == 0)
            {
                node = rootNode;
                currTplt.setP30F1104("0");
            }
            else
            {
                if (currNode == null)
                {
                    Lang.showMesg(this, LangRes.P30F8A07, "请选择属性对应的模板！");
                    trTpltList.requestFocus();
                    return;
                }
                node = (javax.swing.tree.DefaultMutableTreeNode) trTpltList.getSelectionPath().getPath()[1];
                currTplt.setP30F1104(((Tplt) node.getUserObject()).getP30F1103());
            }
            currTplt.setP30F1101(node.getChildCount());
            DBA4000.saveTpltData(mproPtn.getUserMdl(), currTplt);
            node.add(new javax.swing.tree.DefaultMutableTreeNode(currTplt));
            tmTpltList.nodeStructureChanged(node);

            if (indx == 0)
            {
                mproPtn.getUserMdl().getTpltMdl().wAppend(currTplt);
            }
        }

        currTplt = new Tplt();
        viewInfo(currTplt);
        isUpdate = false;
    }

    private void cbTpltKindActionPerformed(java.awt.event.ActionEvent evt)
    {
        Object obj = cbTpltKind.getSelectedItem();
        if (obj == null || !(obj instanceof I1S1))
        {
            return;
        }
        viewData((I1S1) obj);
    }

    private void viewData(I1S1 item)
    {
        if (item.getK() == ConsDat.INDX_LIST)
        {
            plTpltData.removeAll();
            plTpltData.add(cbTpltData);
            lbTpltData.setLabelFor(cbTpltData);
            plTpltData.validate();
            plTpltData.repaint();
            relayout = true;

            java.util.List<I1S2> list = DBA4000.selectListData(mproPtn.getUserMdl(), currTplt.getP30F1103());
            cbTpltData.removeAllItems();
            for (I1S2 temp : list)
            {
                cbTpltData.addItem(temp);
            }
        }
        else if (relayout)
        {
            plTpltData.removeAll();
            plTpltData.add(tfTpltData);
            lbTpltData.setLabelFor(tfTpltData);
            plTpltData.validate();
            plTpltData.repaint();
            relayout = false;
        }
    }

    private void viewInfo(Tplt item)
    {
        cbTpltKind.setSelectedItem(new I1S1(item.getP30F1102(), ""));
        if (item.isType())
        {
            Lang.setWText(lbTpltData, LangRes.P30F8343, "类别键值");
            cbTpltKind.setEnabled(false);
        }
        else
        {
            Lang.setWText(lbTpltData, LangRes.P30F8344, "默认数据");
            cbTpltKind.setEnabled(true);
        }

        String text = item.getP30F1105();
        if (text != null)
        {
            text = text.replaceAll('^' + ConsDat.SP_TPL_LS + '|' + ConsDat.SP_TPL_RS + '$', "");
        }
        tfTpltName.setText(text);

        text = item.getP30F1106();
        if (text != null)
        {
            text = text.replaceAll('^' + ConsDat.SP_TPL_LS + '|' + ConsDat.SP_TPL_RS + '$', "");
        }
        tfTpltData.setText(text);

        taTpltDesp.setText(item.getP30F1107());
    }

    private void miSortUpActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (currNode == null)
        {
            return;
        }

        javax.swing.tree.DefaultMutableTreeNode p = currNode;
        javax.swing.tree.DefaultMutableTreeNode n = p.getPreviousSibling();
        if (n == null)
        {
            return;
        }
        javax.swing.tree.DefaultMutableTreeNode o = (javax.swing.tree.DefaultMutableTreeNode) p.getParent();
        if (o == null)
        {
            return;
        }

        int i = o.getIndex(p);
        o.remove(i--);
        o.insert(p, i);
        tmTpltList.nodeStructureChanged(o);

        trTpltList.setSelectionPath(new javax.swing.tree.TreePath(p.getPath()));

        // 上移
        Tplt c = (Tplt) p.getUserObject();
        c.addP30F1101(-1);
        DBA4000.saveTpltData(mproPtn.getUserMdl(), c);

        // 下移
        c = (Tplt) n.getUserObject();
        c.addP30F1101(1);
        DBA4000.saveTpltData(mproPtn.getUserMdl(), c);
    }

    private void miSortDnActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (currNode == null)
        {
            return;
        }

        javax.swing.tree.DefaultMutableTreeNode p = currNode;
        javax.swing.tree.DefaultMutableTreeNode n = p.getNextSibling();
        if (n == null)
        {
            return;
        }
        javax.swing.tree.DefaultMutableTreeNode o = (javax.swing.tree.DefaultMutableTreeNode) p.getParent();
        if (o == null)
        {
            return;
        }

        int i = o.getIndex(p);
        o.remove(i++);
        o.insert(p, i);
        tmTpltList.nodeStructureChanged(o);

        trTpltList.setSelectionPath(new javax.swing.tree.TreePath(p.getPath()));

        Tplt c = (Tplt) p.getUserObject();
        c.addP30F1101(1);
        DBA4000.saveTpltData(mproPtn.getUserMdl(), c);
        c = (Tplt) n.getUserObject();
        c.addP30F1101(-1);
        DBA4000.saveTpltData(mproPtn.getUserMdl(), c);
    }
    private javax.swing.JButton btNewProp;
    private javax.swing.JButton btNewType;
    private javax.swing.JButton btSaveDat;
    private javax.swing.JComboBox cbTpltKind;
    private javax.swing.JTree trTpltList;
    private javax.swing.JLabel lbTpltData;
    private javax.swing.JLabel lbTpltDesp;
    private javax.swing.JLabel lbTpltKind;
    private javax.swing.JLabel lbTpltName;
    private javax.swing.JTextArea taTpltDesp;
    private javax.swing.JTextField tfTpltData;
    private javax.swing.JTextField tfTpltName;
    private javax.swing.JPanel plTpltData;
    private javax.swing.JComboBox cbTpltData;
    private javax.swing.JPopupMenu pmSortMenu;
    private javax.swing.JMenuItem miSortUp;
    private javax.swing.JMenuItem miSortDn;
    private javax.swing.JMenuItem miRemove;
}
