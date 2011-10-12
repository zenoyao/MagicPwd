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

import com.magicpwd.__i.IBackCall;
import com.magicpwd.__i.IEditItem;
import com.magicpwd._comn.item.HintItem;
import com.magicpwd._comn.item.LogoItem;
import com.magicpwd._comn.item.MetaItem;
import com.magicpwd._comn.mpwd.MgtdDetail;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._comp.BtnLabel;
import com.magicpwd._comp.IcoLabel;
import com.magicpwd._comp.WTextBox;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd._util.Date;
import com.magicpwd._util.Lang;
import com.magicpwd.m.mwiz.KeysMdl;
import com.magicpwd.v.app.mwiz.EditPtn;
import com.magicpwd.v.app.mwiz.MwizPtn;
import com.magicpwd.x.app.icon.IcoDialog;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-10-24 23:52:16
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class HeadBean extends javax.swing.JPanel implements IBackCall<String, String>
{

    private MwizPtn mwizPtn;
    private EditPtn editPtn;
    private KeysMdl keysMdl;
    private WTextBox nameBox;
    private WTextBox metaBox;
    private int mgtdType;
    private int mgtdStat;
    private int mgtdData;
    private int mgtdUnit;
    private java.util.Calendar mgtdCal;

    public HeadBean(MwizPtn mwizPtn, EditPtn editPtn)
    {
        this.mwizPtn = mwizPtn;
        this.editPtn = editPtn;
    }

    public void initView()
    {
        lb_MetaName = new javax.swing.JLabel();
        tf_MetaName = new javax.swing.JTextField(24);
        lb_MetaData = new javax.swing.JLabel();
        ta_MetaData = new javax.swing.JTextArea();
        lb_HintDate = new javax.swing.JLabel();
        tf_HintDate = new javax.swing.JTextField(24);

        lb_MetaName.setLabelFor(tf_MetaName);

        ib_KeysIcon = new IcoLabel();
        ib_KeysIcon.setIcon(Bean.getNone());
        ib_KeysIcon.setOpaque(true);
        ib_KeysIcon.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ib_KeysIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ib_KeysIcon.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.background"));
        ib_KeysIcon.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        ib_KeysIcon.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ib_KeysIconActionPerformed(evt);
            }
        });

        nameBox = new WTextBox(tf_MetaName, true);
        nameBox.initView();

        lb_MetaData.setLabelFor(ta_MetaData);

        metaBox = new WTextBox(ta_MetaData, true);
        metaBox.initView();

        lb_HintDate.setLabelFor(tf_HintDate);

        ib_HintDate = new BtnLabel();
        ib_HintDate.setIcon(mwizPtn.getFeelIcon(null, "var:hint-time"));
        ib_HintDate.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ib_HintDateActionPerformed(evt);
            }
        });

        pm_HintDate = new javax.swing.JPopupMenu();
        mi_HalfHour = new javax.swing.JMenuItem();
        pm_HintDate.add(mi_HalfHour);

        mi_FullHour = new javax.swing.JMenuItem();
        pm_HintDate.add(mi_FullHour);

        pm_HintDate.addSeparator();

        miNaMgtd = new javax.swing.JMenuItem();
        miOkMgtd = new javax.swing.JMenuItem();

        javax.swing.JScrollPane jsp = new javax.swing.JScrollPane(ta_MetaData);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(tf_MetaName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(ib_KeysIcon, 21, 21, 21);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addComponent(tf_HintDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addComponent(ib_HintDate, 20, 20, 20);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lb_MetaName, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lb_MetaData, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lb_HintDate, javax.swing.GroupLayout.Alignment.TRAILING);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addGroup(hsg1);
        hpg2.addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE);
        hpg2.addGroup(hsg2);
        javax.swing.GroupLayout.SequentialGroup hsg3 = layout.createSequentialGroup();
        hsg3.addContainerGap();
        hsg3.addGroup(hpg1);
        hsg3.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg3.addGroup(hpg2);
        hsg3.addContainerGap();
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(hsg3));

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER);
        vpg1.addComponent(lb_MetaName);
        vpg1.addComponent(tf_MetaName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg1.addComponent(ib_KeysIcon, 21, 21, 21);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg2.addComponent(lb_MetaData);
        vpg2.addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup vpg4 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER);
        vpg4.addComponent(lb_HintDate);
        vpg4.addComponent(tf_HintDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg4.addComponent(ib_HintDate, 20, 20, 20);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addContainerGap();
        vsg1.addGroup(vpg1);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg2);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg4);
        vsg1.addContainerGap(14, Short.MAX_VALUE);
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(vsg1));
    }

    public void initLang()
    {
        Bean.setText(lb_MetaName, Lang.getLang(LangRes.P30F6303, "标题"));

        Lang.setWText(ib_KeysIcon, LangRes.P30F6304, "@L");
        Lang.setWTips(ib_KeysIcon, LangRes.P30F6305, "点击选择徽标(Alt + L)");

        Bean.setText(lb_MetaData, Lang.getLang(LangRes.P30F6306, "搜索"));

        Lang.setWText(lb_HintDate, LangRes.P30F6307, "提示");

        Lang.setWText(ib_HintDate, LangRes.P30F6309, "@O");
        Lang.setWTips(ib_HintDate, LangRes.P30F630A, "提醒时间(Alt + O)");

        Lang.setWText(miNaMgtd, null, "已作废(@C)");
        Lang.setWTips(miNaMgtd, null, "标记当前提醒为已作废");

        Lang.setWText(miOkMgtd, null, "已完成(@V)");
        Lang.setWTips(miOkMgtd, null, "标记当前提醒为已完成");

        nameBox.initLang();
        metaBox.initLang();
    }

    public void initData()
    {
        java.awt.event.ActionListener action = new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                mi_DateTimeActionPerformed(e);
            }
        };
        mi_HalfHour.addActionListener(action);
        mi_FullHour.addActionListener(action);
        mwizPtn.getMenuPtn().getSubMenu("date-interval", pm_HintDate, action);

        miNaMgtd.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                miNaMgtdActionPerformed(e);
            }
        });
        pm_HintDate.add(miNaMgtd);
        miOkMgtd.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                miOkMgtdActionPerformed(e);
            }
        });
        pm_HintDate.add(miOkMgtd);

        nameBox.initData();
        metaBox.initData();
    }

    public final void showData(KeysMdl keysMdl)
    {
        this.keysMdl = keysMdl;

        MetaItem meta = (MetaItem) keysMdl.getItemAt(ConsEnv.PWDS_HEAD_META);
        tf_MetaName.setText(meta.getName());
        ta_MetaData.setText(meta.getData());

        LogoItem logo = (LogoItem) keysMdl.getItemAt(ConsEnv.PWDS_HEAD_LOGO);
        ib_KeysIcon.setIcon(mwizPtn.getDataIcon(logo.getPath(), logo.getName(), 16));

        HintItem hint = (HintItem) keysMdl.getItemAt(ConsEnv.PWDS_HEAD_HINT);
        tf_HintDate.setText(hint.getName());
    }

    public boolean saveData()
    {
        String metaName = tf_MetaName.getText();
        if (!com.magicpwd._util.Char.isValidate(metaName))
        {
            Lang.showMesg(editPtn, LangRes.P30FAA1A, "记录标题不能为空!");
            tf_MetaName.requestFocus();
            return false;
        }
        if (keysMdl == null)
        {
            return false;
        }

        IEditItem meta = keysMdl.getItemAt(ConsEnv.PWDS_HEAD_META);
        meta.setName(metaName);
        meta.setData(ta_MetaData.getText());

        HintItem hint = (HintItem) keysMdl.getItemAt(ConsEnv.PWDS_HEAD_HINT);
        hint.setName(tf_HintDate.getText());
        if (mgtdStat == ConsDat.MGTD_STATUS_DONE || mgtdStat == ConsDat.MGTD_STATUS_ABORT)
        {
            MgtdHeader header = hint.getMgtd();
            if (header != null)
            {
                header.setP30F0303(mgtdStat);
            }
        }
        else if (mgtdType > 0)
        {
            MgtdHeader header = hint.getMgtd();
            if (header == null)
            {
                header = new MgtdHeader();
                hint.setMgtd(header);
            }

            header.setP30F0301(0);
            header.setP30F0302(ConsDat.MGTD_TYPE_DATETIME);
            header.setP30F0303(ConsDat.MGTD_STATUS_INIT);
            header.setP30F0304(0);
            header.setP30F0305(mgtdType);
            header.setP30F0306(ConsDat.MGTD_METHOD_NOTE);
            header.setP30F0307(0);
            header.setP30F0308(0);
            switch (mgtdType)
            {
                case ConsDat.MGTD_INTVAL_FIXTIME:
                    header.setP30F030C("定时提醒");
                    break;
                case ConsDat.MGTD_INTVAL_PERIOD:
                    header.setP30F030C("周期提醒");
                    break;
                case ConsDat.MGTD_INTVAL_INTVAL:
                    header.setP30F030C("间隔提醒");
                    break;
                case ConsDat.MGTD_INTVAL_SPECIAL:
                    header.setP30F030C("特殊提醒");
                    break;
                case ConsDat.MGTD_INTVAL_FORMULA:
                    header.setP30F030C("公式提醒");
                    break;
                default:
                    header.setP30F030C("未知");
            }
            header.setP30F030D(System.currentTimeMillis());
            header.setP30F030E(0L);
            header.setP30F030F(0L);
            header.setP30F0312(ConsDat.MGTD_UNIT_MINUTE);
            header.setP30F0313(5);
            header.setP30F0314(hint.getName());

            java.util.ArrayList<MgtdDetail> list = new java.util.ArrayList<MgtdDetail>(1);
            MgtdDetail detail = new MgtdDetail();
            detail.setP30F0403(mgtdCal.getTimeInMillis());
            detail.setP30F0404(mgtdUnit);
            detail.setP30F0405(mgtdData);
            detail.setP30F0406("");
            list.add(detail);
            header.setHintList(list);
        }

        return true;
    }

    @Override
    public boolean callBack(String options, String object)
    {
        if (IBackCall.OPTIONS_ABORT.equalsIgnoreCase(options))
        {
            return false;
        }

        LogoItem logo = (LogoItem) keysMdl.getItemAt(ConsEnv.PWDS_HEAD_LOGO);

        if ("0".equals(options))
        {
            ib_KeysIcon.setIcon(Bean.getNone());
            logo.setName(options);
            logo.setPath("");
            return true;
        }

        if (!com.magicpwd._util.Char.isValidateHash(options))
        {
            return false;
        }
        ib_KeysIcon.setIcon(mwizPtn.getDataIcon(object, options, 16));
        logo.setName(options);
        logo.setPath(object);
        return true;
    }

    @Override
    public void requestFocus()
    {
        tf_MetaName.requestFocus();
    }

    private void ib_KeysIconActionPerformed(java.awt.event.ActionEvent evt)
    {
        IcoDialog ico = new IcoDialog(editPtn, mwizPtn.getUserMdl(), this);
        ico.initView();
        ico.initLang();
        ico.initData();
        ico.showData("", keysMdl.getItemAt(ConsEnv.PWDS_HEAD_LOGO).getName(), 16);
        ico.setVisible(true);
    }

    private void ib_HintDateActionPerformed(java.awt.event.ActionEvent evt)
    {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.SECOND, 0);
        c.set(java.util.Calendar.MILLISECOND, 0);

        java.util.Date d1;
        java.util.Date d2;
        String t1;
        String t2;
        if (c.get(java.util.Calendar.MINUTE) < 30)
        {
            c.set(java.util.Calendar.MINUTE, 30);
            d1 = c.getTime();
            t1 = c.get(java.util.Calendar.HOUR_OF_DAY) + ":30";
            c.add(java.util.Calendar.MINUTE, 30);
            d2 = c.getTime();
            t2 = c.get(java.util.Calendar.HOUR_OF_DAY) + ":00";
        }
        else
        {
            c.set(java.util.Calendar.MINUTE, 0);
            c.add(java.util.Calendar.HOUR_OF_DAY, 1);
            d1 = c.getTime();
            t1 = c.get(java.util.Calendar.HOUR_OF_DAY) + ":00";

            c.add(java.util.Calendar.MINUTE, 30);
            d2 = c.getTime();
            t2 = c.get(java.util.Calendar.HOUR_OF_DAY) + ":30";
        }

        Bean.setText(mi_HalfHour, t1);
        mi_HalfHour.setActionCommand("fix:" + Date.getFieldDateFormat().format(d1));
        Bean.setText(mi_FullHour, t2);
        mi_FullHour.setActionCommand("fix:" + Date.getFieldDateFormat().format(d2));
        pm_HintDate.show(ib_HintDate, 0, ib_HintDate.getHeight());
    }

    private void mi_DateTimeActionPerformed(java.awt.event.ActionEvent e)
    {
        String cmd = e.getActionCommand();
        if (!Char.isValidate(cmd))
        {
            return;
        }
        // 定时提醒
        if (cmd.startsWith("fix:"))
        {
            cmd = cmd.substring(4);
            mgtdCal = Date.processFix(cmd);
            if (mgtdCal == null)
            {
                return;
            }
            tf_HintDate.setText("定时提醒：" + cmd);
            mgtdType = ConsDat.MGTD_INTVAL_FIXTIME;
            mgtdStat = ConsDat.MGTD_STATUS_INIT;
            mgtdData = 0;
            mgtdUnit = 0;
            return;
        }
        // 延后提醒
        if (cmd.startsWith("var:"))
        {
            cmd = cmd.substring(4);
            mgtdCal = Date.processVar(cmd);
            if (mgtdCal == null)
            {
                return;
            }
            tf_HintDate.setText("定时提醒：" + Date.getFieldDateFormat().format(mgtdCal.getTime()));
            mgtdType = ConsDat.MGTD_INTVAL_FIXTIME;
            mgtdStat = ConsDat.MGTD_STATUS_INIT;
            mgtdData = 0;
            mgtdUnit = 0;
            return;
        }
    }

    private void miNaMgtdActionPerformed(java.awt.event.ActionEvent e)
    {
        mgtdStat = ConsDat.MGTD_STATUS_ABORT;
    }

    private void miOkMgtdActionPerformed(java.awt.event.ActionEvent e)
    {
        mgtdStat = ConsDat.MGTD_STATUS_DONE;
    }
    private BtnLabel ib_HintDate;
    private IcoLabel ib_KeysIcon;
    private javax.swing.JLabel lb_HintDate;
    private javax.swing.JLabel lb_MetaData;
    private javax.swing.JLabel lb_MetaName;
    private javax.swing.JTextArea ta_MetaData;
    private javax.swing.JTextField tf_HintDate;
    private javax.swing.JTextField tf_MetaName;
    private javax.swing.JPopupMenu pm_HintDate;
    private javax.swing.JMenuItem mi_HalfHour;
    private javax.swing.JMenuItem mi_FullHour;
    private javax.swing.JMenuItem miNaMgtd;
    private javax.swing.JMenuItem miOkMgtd;
}
