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
package com.magicpwd._bean.mwiz;

import com.magicpwd.__i.IEditItem;
import com.magicpwd._comn.item.GuidItem;
import com.magicpwd._comn.prop.Tplt;
import com.magicpwd._comp.BtnLabel;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.m.mwiz.KeysMdl;
import com.magicpwd.r.FileTM;
import com.magicpwd.v.app.mwiz.MwizPtn;
import java.util.regex.Pattern;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * 属性：向导
 * 键值：ConsEnv.INDX_GUID
 * @author Amon
 */
public class GuidBean extends javax.swing.JPanel
{

    private KeysMdl keysMdl;
    private MwizPtn normPtn;
    private BtnLabel bt_ReadMail;
    private BtnLabel bt_ExptCard;
    private FileTM fileTM;

    public GuidBean(MwizPtn normPtn)
    {
        this.normPtn = normPtn;
    }

    public void initView()
    {
        lb_PropName = new javax.swing.JLabel();
        tf_PropName = new javax.swing.JTextField(20);
        tf_PropName.setEditable(false);
        tf_PropName.setFocusable(false);
        lb_PropName.setLabelFor(tf_PropName);

        lb_PropData = new javax.swing.JLabel();
        cb_PropData = new javax.swing.JComboBox();
        cb_PropData.setModel(normPtn.getUserMdl().getTpltMdl());
        lb_PropData.setLabelFor(cb_PropData);

        lb_PropEdit = new javax.swing.JLabel();
        pl_PropEdit = new javax.swing.JPanel();
        pl_PropEdit.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 0));

        bt_ReadMail = new BtnLabel();
        bt_ReadMail.setIcon(normPtn.getFeelIcon(null, "var:mail-receive"));
        bt_ReadMail.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                readMailActionPerformed(evt);
            }
        });
        pl_PropEdit.add(bt_ReadMail);

        bt_ExptCard = new BtnLabel();
        bt_ExptCard.setIcon(normPtn.getFeelIcon(null, "var:info-card"));
        bt_ExptCard.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exptCardActionPerformed(evt);
            }
        });
        pl_PropEdit.add(bt_ExptCard);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lb_PropEdit);
        hpg1.addComponent(lb_PropData);
        hpg1.addComponent(lb_PropName);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(tf_PropName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(cb_PropData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(pl_PropEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addGroup(hpg1);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg2);
        hsg.addContainerGap(1, Short.MAX_VALUE);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lb_PropName);
        vpg1.addComponent(tf_PropName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lb_PropData);
        vpg2.addComponent(cb_PropData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg3.addComponent(lb_PropEdit);
        vpg3.addComponent(pl_PropEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addContainerGap();
        vsg1.addGroup(vpg1);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg2);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg3);
        vsg1.addContainerGap(1, Short.MAX_VALUE);
        layout.setVerticalGroup(vsg1);
    }

    public void initLang()
    {
        Lang.setWText(lb_PropName, LangRes.P30F6301, "时间");
        Lang.setWText(lb_PropData, LangRes.P30F6302, "模板");

        Lang.setWText(bt_ReadMail, LangRes.P30F1519, "@M");
        Lang.setWTips(bt_ReadMail, LangRes.P30F151A, "检测邮件(Alt + M)");
        bt_ReadMail.setVisible(false);

        Lang.setWText(bt_ExptCard, LangRes.P30F151D, "@C");
        Lang.setWTips(bt_ExptCard, LangRes.P30F151E, "生成卡片(Alt + C)");
        bt_ExptCard.setVisible(false);
    }

    public void initData()
    {
    }

    public void showData(KeysMdl keysMdl)
    {
        this.keysMdl = keysMdl;
        if (keysMdl == null)
        {
            return;
        }

        if (keysMdl.getItemSize() < 1)
        {
            keysMdl.initGuid();
        }

        IEditItem item = keysMdl.getItemAt(ConsEnv.PWDS_HEAD_GUID);
        tf_PropName.setText(item.getName());

        String kind = item.getSpec(GuidItem.SPEC_GUID_TPLT);
        boolean hash = com.magicpwd._util.Char.isValidateHash(kind);
        if (!hash)
        {
            cb_PropData.setSelectedIndex(-1);
//            bt_ReadMail.setVisible(false);
//            bt_ExptCard.setVisible(false);
            return;
        }

        Tplt tplt = null;
        for (Tplt temp : normPtn.getUserMdl().getTpltMdl().getAllItems())
        {
            if (kind.equals(temp.getP30F1103()))
            {
                tplt = temp;
                cb_PropData.setSelectedItem(temp);
                break;
            }
        }
        if (tplt == null)
        {
            cb_PropData.setSelectedIndex(-1);
            return;
        }

        kind = ' ' + tplt.getP30F1106() + ' ';
//        bt_ReadMail.setVisible(kind.indexOf(ConsDat.TEXT_MAIL) > -1);
//        bt_ExptCard.setVisible(kind.indexOf(ConsDat.TEXT_CARD) > -1);
    }

    public boolean saveData()
    {
        Object obj = cb_PropData.getSelectedItem();
        if (obj == null)
        {
            Lang.showMesg(normPtn, LangRes.P30F7A29, "请选择口令模板!");
            cb_PropData.requestFocus();
            return false;
        }

        if (keysMdl == null)
        {
            return false;
        }

        Tplt tplt = (Tplt) obj;
        IEditItem guidItem = keysMdl.getItemAt(ConsEnv.PWDS_HEAD_GUID);
        if (!Char.isValidate(guidItem.getData()))
        {
            guidItem.setData("0");
        }
        guidItem.setSpec(GuidItem.SPEC_GUID_TPLT, tplt.getP30F1103());
        if (keysMdl.getItemSize() <= ConsEnv.PWDS_HEAD_SIZE)
        {
            keysMdl.initHead();
        }
        return true;
    }

    @Override
    public void requestFocus()
    {
        cb_PropData.requestFocus();
    }

    public void readMailActionPerformed(java.awt.event.ActionEvent evt)
    {
//        normPtn.showMailPtn();
    }

    public void exptCardActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (processing)
        {
            return;
        }

        processing = true;
        if (fileTM == null)
        {
            fileTM = new FileTM("card", Pattern.compile("[^.]+[.]amc$", Pattern.CASE_INSENSITIVE), false);

            pm_CardMenu = new javax.swing.JPopupMenu();
            mu_HtmMenu = new javax.swing.JMenu("HTM");
            mu_TxtMenu = new javax.swing.JMenu("TXT");
            mu_PngMenu = new javax.swing.JMenu("PNG");
            mu_SvgMenu = new javax.swing.JMenu("SVG");

            al_Listener = new java.awt.event.ActionListener()
            {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    cardItemActionPerformed(evt);
                }
            };
        }
        if (fileTM.checkModified())
        {
            pm_CardMenu.removeAll();

            mu_HtmMenu.removeAll();
            pm_CardMenu.add(mu_HtmMenu);

            mu_TxtMenu.removeAll();
            pm_CardMenu.add(mu_TxtMenu);

            mu_PngMenu.removeAll();
            pm_CardMenu.add(mu_PngMenu);

            mu_SvgMenu.removeAll();
            pm_CardMenu.add(mu_SvgMenu);

            SAXReader reader = new SAXReader();
            javax.swing.JMenuItem item;
            for (java.io.File cardFile : fileTM.getFileList())
            {
                try
                {
                    Document doc = reader.read(cardFile);

                    Node name = doc.selectSingleNode("/magicpwd/base/name");
                    item = new javax.swing.JMenuItem();
                    item.addActionListener(al_Listener);
                    item.setText(name != null ? name.getText() : cardFile.getName());

                    Node type = doc.selectSingleNode("/magicpwd/base/type");
                    if (type != null)
                    {
                        String text = (type.getText() + "").trim().toLowerCase();
                        if (ConsEnv.CARD_HTM.equals(text))
                        {
                            item.setActionCommand(ConsEnv.CARD_HTM + '/' + cardFile.getPath());
                            mu_HtmMenu.add(item);
                            continue;
                        }
                        if (ConsEnv.CARD_TXT.equals(text))
                        {
                            item.setActionCommand(ConsEnv.CARD_TXT + '/' + cardFile.getPath());
                            mu_TxtMenu.add(item);
                            continue;
                        }
                        if (ConsEnv.CARD_PNG.equals(text))
                        {
                            item.setActionCommand(ConsEnv.CARD_PNG + '/' + cardFile.getPath());
                            mu_PngMenu.add(item);
                            continue;
                        }
                        if (ConsEnv.CARD_SVG.equals(text))
                        {
                            item.setActionCommand(ConsEnv.CARD_SVG + '/' + cardFile.getPath());
                            mu_SvgMenu.add(item);
                            continue;
                        }
                    }
                    item.setActionCommand(ConsEnv.CARD_ALL + '/' + cardFile.getPath());
                    pm_CardMenu.add(item);
                }
                catch (Exception exp)
                {
                    processing = false;
                    Logs.exception(exp);
                    Lang.showMesg(normPtn, null, exp.getLocalizedMessage());
                    return;
                }
            }
        }
        pm_CardMenu.show(bt_ExptCard, 0, bt_ExptCard.getPreferredSize().height);

        processing = false;
    }

    private void cardItemActionPerformed(java.awt.event.ActionEvent evt)
    {
        javax.swing.JMenuItem item = (javax.swing.JMenuItem) evt.getSource();
        if (item == null)
        {
            return;
        }

        String command = item.getActionCommand();
        if (!com.magicpwd._util.Char.isValidate(command))
        {
            return;
        }

        int dot = command.indexOf('/');
        if (dot < 0)
        {
            return;
        }

        String key = command.substring(0, dot).toLowerCase();
        String src = command.substring(dot + 1);
        if (!com.magicpwd._util.Char.isValidate(key) || !com.magicpwd._util.Char.isValidate(src))
        {
            return;
        }
        java.io.File srcFile = new java.io.File(src).getAbsoluteFile();
        if (!srcFile.exists() || !srcFile.isFile() || !srcFile.canRead())
        {
            Lang.showMesg(normPtn, LangRes.P30F7A44, "无法读取卡片模板文件：{0}", src);
            return;
        }

        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        fc.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        fc.setMultiSelectionEnabled(false);
        if (fc.showOpenDialog(normPtn) != javax.swing.JFileChooser.APPROVE_OPTION)
        {
            return;
        }

        java.io.File dstFile = fc.getSelectedFile();
        if (dstFile == null)
        {
            Lang.showMesg(normPtn, LangRes.P30F7A1B, "您选择的目录不存在！");
            return;
        }
        if (!dstFile.exists())
        {
            dstFile.mkdirs();
        }
        if (!dstFile.canWrite())
        {
            Lang.showMesg(normPtn, LangRes.P30F7A45, "您不具有保存文件到{0}的权限！", dstFile.getPath());
            return;
        }

//        normPtn.exportCard(srcFile, dstFile, key);
    }
    private boolean processing;
    private java.awt.event.ActionListener al_Listener;
    private javax.swing.JLabel lb_PropData;
    private javax.swing.JLabel lb_PropEdit;
    private javax.swing.JLabel lb_PropName;
    private javax.swing.JPanel pl_PropEdit;
    private javax.swing.JComboBox cb_PropData;
    private javax.swing.JTextField tf_PropName;
    private javax.swing.JPopupMenu pm_CardMenu;
    private javax.swing.JMenu mu_HtmMenu;
    private javax.swing.JMenu mu_TxtMenu;
    private javax.swing.JMenu mu_SvgMenu;
    private javax.swing.JMenu mu_PngMenu;
}
