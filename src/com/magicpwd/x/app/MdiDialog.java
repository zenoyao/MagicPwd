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

import com.magicpwd.__i.IPropBean;
import com.magicpwd._comn.S1S2;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._prop.CharProp;
import com.magicpwd._prop.IdioProp;
import com.magicpwd._prop.InfoProp;
import com.magicpwd._prop.JavaProp;
import com.magicpwd._prop.SKeyProp;
import com.magicpwd._prop.USetProp;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Lang;
import com.magicpwd.r.ListCR;
import com.magicpwd.v.app.mpro.MproPtn;

/**
 * 软件设置对话框
 * @author Amon
 */
public class MdiDialog extends javax.swing.JDialog
{

    private String lastPanel;
    private java.awt.CardLayout cl_CardLayout;
    private javax.swing.DefaultListModel lm_PropList;
    private java.util.HashMap<String, IPropBean> hm_PropList;
    private MproPtn mainPtn;

    public MdiDialog(MproPtn mainPtn)
    {
        super(mainPtn);
        this.mainPtn = mainPtn;
        lm_PropList = new javax.swing.DefaultListModel();
        cl_CardLayout = new java.awt.CardLayout();
        setDefaultCloseOperation(MdiDialog.DISPOSE_ON_CLOSE);
    }

    public void initView()
    {
        ls_PropList = new javax.swing.JList();
        ls_PropList.setModel(lm_PropList);
        ls_PropList.setCellRenderer(new ListCR(javax.swing.SwingConstants.CENTER));
        ls_PropList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {

            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                ls_PropListValueChanged(evt);
            }
        });
        javax.swing.JScrollPane sp_PropList = new javax.swing.JScrollPane();
        sp_PropList.setViewportView(ls_PropList);

        lb_HeadPanel = new javax.swing.JLabel()
        {

            @Override
            protected void paintComponent(java.awt.Graphics g)
            {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                java.awt.Dimension size = getSize();
                g2d.setPaint(new java.awt.GradientPaint(0f, 0f, java.awt.Color.lightGray, size.width, size.height, java.awt.Color.white));
                g2d.fillRect(0, 0, size.width, size.height);

                super.paintComponent(g);
            }
        };
        lb_HeadPanel.setIcon(Bean.getNone());
        lb_HeadPanel.setFont(lb_HeadPanel.getFont().deriveFont(java.awt.Font.BOLD));

        pl_CardPanel = new javax.swing.JPanel();
        cl_CardLayout = new java.awt.CardLayout();
        pl_CardPanel.setLayout(cl_CardLayout);

        bt_Discard = new javax.swing.JButton();
        bt_Discard.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_DiscardActionPerformed(evt);
            }
        });
        bt_Confirm = new javax.swing.JButton();
        bt_Confirm.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_ConfirmActionPerformed(evt);
            }
        });
        bt_Applied = new javax.swing.JButton();
        bt_Applied.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_AppliedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(pl_CardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE);
        hpg1.addComponent(lb_HeadPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(sp_PropList, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addGroup(hpg1);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addComponent(bt_Applied);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addComponent(bt_Confirm);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addComponent(bt_Discard);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addGroup(hsg1);
        hpg2.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hsg2);
        layout.setHorizontalGroup(layout.createSequentialGroup().addContainerGap().addGroup(hpg2).addContainerGap());

        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addComponent(lb_HeadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addComponent(pl_CardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg1.addComponent(sp_PropList, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE);
        vpg1.addGroup(vsg1);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(bt_Discard);
        vpg2.addComponent(bt_Confirm);
        vpg2.addComponent(bt_Applied);
        javax.swing.GroupLayout.SequentialGroup vsg2 = layout.createSequentialGroup();
        vsg2.addContainerGap();
        vsg2.addGroup(vpg1);
        vsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg2.addGroup(vpg2);
        vsg2.addContainerGap();
        layout.setVerticalGroup(vsg2);

        setIconImage(Bean.getLogo(16));
        setResizable(false);
    }

    public void initLang()
    {
        Lang.setWText(bt_Applied, LangRes.P30FA50E, "应用(@A)");
        Lang.setWText(bt_Confirm, LangRes.P30FA50A, "确定(@O)");
        Lang.setWText(bt_Discard, LangRes.P30FA50B, "取消(@C)");
    }

    public void initData()
    {
        hm_PropList = new java.util.HashMap<String, IPropBean>();

        String t;

        t = Lang.getLang(LangRes.P30F1202, "常规设置");
        lm_PropList.addElement(new S1S2(ConsEnv.PROP_USET, t, t));
        USetProp up = new USetProp(mainPtn);
        up.initView();
        up.initLang();
        up.initData();
        pl_CardPanel.add(ConsEnv.PROP_USET, up);
        hm_PropList.put(ConsEnv.PROP_USET, up);

        t = Lang.getLang(LangRes.P30F1203, "口令管理");
        lm_PropList.addElement(new S1S2(ConsEnv.PROP_CHAR, t, t));
        CharProp cp = new CharProp(mainPtn);
        cp.initView();
        cp.initLang();
        cp.initData();
        pl_CardPanel.add(ConsEnv.PROP_CHAR, cp);
        hm_PropList.put(ConsEnv.PROP_CHAR, cp);

        t = Lang.getLang(LangRes.P30F1206, "键盘快捷");
        lm_PropList.addElement(new S1S2(ConsEnv.PROP_SKEY, t, t));
        SKeyProp sp = new SKeyProp(mainPtn);
        sp.initView();
        sp.initLang();
        sp.initData();
        pl_CardPanel.add(ConsEnv.PROP_SKEY, sp);
        hm_PropList.put(ConsEnv.PROP_SKEY, sp);

        t = Lang.getLang(LangRes.P30F1208, "Java环境");
        lm_PropList.addElement(new S1S2(ConsEnv.PROP_JAVA, t, t));
        JavaProp jp = new JavaProp(mainPtn);
        jp.initView();
        jp.initLang();
        jp.initData();
        pl_CardPanel.add(ConsEnv.PROP_JAVA, jp);
        hm_PropList.put(ConsEnv.PROP_JAVA, jp);

        t = Lang.getLang(LangRes.P30F1209, "特别致谢");
        lm_PropList.addElement(new S1S2(ConsEnv.PROP_IDIO, t, t));
        IdioProp dp = new IdioProp(mainPtn);
        dp.initView();
        dp.initLang();
        dp.initData();
        pl_CardPanel.add(ConsEnv.PROP_IDIO, dp);
        hm_PropList.put(ConsEnv.PROP_IDIO, dp);

        t = Lang.getLang(LangRes.P30F120A, "关于软件");
        lm_PropList.addElement(new S1S2(ConsEnv.PROP_INFO, t, t));
        InfoProp ip = new InfoProp(mainPtn);
        ip.initView();
        ip.initLang();
        ip.initData();
        pl_CardPanel.add(ConsEnv.PROP_INFO, ip);
        hm_PropList.put(ConsEnv.PROP_INFO, ip);

        pack();
        Bean.centerForm(this, mainPtn);
    }

    /**
     * 显示对应的面板
     * @param panelKey 要显示的面板键值
     * @param showList 是否显示导航列表
     */
    public void showProp(String panelKey, boolean showList)
    {
        if (!com.magicpwd._util.Char.isValidate(panelKey))
        {
            return;
        }
        if (isVisible() && panelKey.equals(lastPanel))
        {
            return;
        }

        int idx = 0;
        S1S2 kvItem = (S1S2) lm_PropList.get(0);
        for (int i = 0, j = lm_PropList.getSize(); i < j; i += 1)
        {
            kvItem = (S1S2) lm_PropList.get(i);
            if (kvItem.getK().equals(panelKey))
            {
                idx = i;
                break;
            }
        }

        ls_PropList.setSelectedIndex(idx);
        showPanel(kvItem);

        if (!isVisible())
        {
            setVisible(true);
        }
    }

    private void showPanel(S1S2 kvItem)
    {
        if (kvItem.getK().equals(lastPanel))
        {
            return;
        }

        setTitle(kvItem.getV());
        cl_CardLayout.show(pl_CardPanel, kvItem.getK());
        lb_HeadPanel.setText(kvItem.getV());
        hm_PropList.get(kvItem.getK()).showData();

        if (lastPanel != null)
        {
            hm_PropList.get(lastPanel).saveData();
        }
        lastPanel = kvItem.getK();
    }

    private void ls_PropListValueChanged(javax.swing.event.ListSelectionEvent evt)
    {
        Object obj = ls_PropList.getSelectedValue();
        if (!(obj instanceof S1S2))
        {
            return;
        }

        showPanel((S1S2) obj);
    }

    private void bt_AppliedActionPerformed(java.awt.event.ActionEvent evt)
    {
        hm_PropList.get(lastPanel).saveData();
    }

    private void bt_ConfirmActionPerformed(java.awt.event.ActionEvent evt)
    {
        setVisible(false);
        dispose();
        mainPtn.getUserMdl().saveCfg();
    }

    private void bt_DiscardActionPerformed(java.awt.event.ActionEvent evt)
    {
        setVisible(false);
        dispose();
    }
    private javax.swing.JList ls_PropList;
    private javax.swing.JPanel pl_CardPanel;
    private javax.swing.JButton bt_Applied;
    private javax.swing.JButton bt_Confirm;
    private javax.swing.JButton bt_Discard;
    private javax.swing.JLabel lb_HeadPanel;
}
