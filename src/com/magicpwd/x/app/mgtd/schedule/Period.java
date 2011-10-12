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
package com.magicpwd.x.app.mgtd.schedule;

import com.magicpwd.__i.mgtd.IMgtdBean;
import com.magicpwd._comn.I1S1;
import com.magicpwd._comn.mpwd.MgtdDetail;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd.x.app.mgtd.MgtdDlg;

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
public class Period extends javax.swing.JPanel implements IMgtdBean
{

    private MgtdDlg mgtdDlg;
    private javax.swing.DefaultListModel lmEnum;
    private javax.swing.SpinnerDateModel smFtime;
    private javax.swing.SpinnerDateModel smTtime;
    private javax.swing.SpinnerDateModel smStime;

    public Period(MgtdDlg mgtdDlg)
    {
        this.mgtdDlg = mgtdDlg;
    }

    @Override
    public void initView()
    {
        cbFtime = new javax.swing.JCheckBox();
        spFtime = new javax.swing.JSpinner();
        smFtime = new javax.swing.SpinnerDateModel();
        spFtime.setModel(smFtime);
        lbFtime = new javax.swing.JLabel();
        lbFtime.setLabelFor(spFtime);
        cbFtime.setSelected(true);
        cbFtime.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cbFtimeActionPerformed(evt);
            }
        });

        cbTtime = new javax.swing.JCheckBox();
        spTtime = new javax.swing.JSpinner();
        smTtime = new javax.swing.SpinnerDateModel();
        spTtime.setModel(smTtime);
        lbTtime = new javax.swing.JLabel();
        lbTtime.setLabelFor(spTtime);
        cbTtime.setSelected(true);
        cbTtime.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cbTtimeActionPerformed(evt);
            }
        });

        btStime = new javax.swing.JLabel();
        spStime = new javax.swing.JSpinner();
        smStime = new javax.swing.SpinnerDateModel();
        spStime.setModel(smStime);
        lbStime = new javax.swing.JLabel();
        lbStime.setLabelFor(spStime);
        btStime.setIcon(mgtdDlg.getFeelIcon("date-now", "var:date-now"));
        btStime.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                cbStimeActionPerformed(evt);
            }
        });

        cbPeriod = new javax.swing.JComboBox();
        cbPeriod.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                cbIntvalActionPerformed(e);
            }
        });
        lbPeriod = new javax.swing.JLabel();
        lbPeriod.setLabelFor(cbPeriod);

        lsPeriod = new javax.swing.JList();
        lmEnum = new javax.swing.DefaultListModel();
        lsPeriod.setModel(lmEnum);
        lsPeriod.setVisibleRowCount(4);
        javax.swing.JScrollPane spEnum = new javax.swing.JScrollPane(lsPeriod);
        lbEnum = new javax.swing.JLabel();
        lbEnum.setLabelFor(lsPeriod);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(spFtime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(cbFtime);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addComponent(spTtime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addComponent(cbTtime);
        javax.swing.GroupLayout.SequentialGroup hsg3 = layout.createSequentialGroup();
        hsg3.addComponent(spStime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg3.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg3.addComponent(btStime);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbEnum);
        hpg1.addComponent(lbFtime);
        hpg1.addComponent(lbTtime);
        hpg1.addComponent(lbStime);
        hpg1.addComponent(lbPeriod);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addGroup(hsg1);
        hpg2.addGroup(hsg2);
        hpg2.addGroup(hsg3);
        hpg2.addComponent(cbPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(spEnum, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg4 = layout.createSequentialGroup();
//        hsg4.addContainerGap();
        hsg4.addGroup(hpg1);
        hsg4.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg4.addGroup(hpg2);
//        hsg4.addContainerGap();
        layout.setHorizontalGroup(hsg4);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lbFtime);
        vpg1.addComponent(spFtime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg1.addComponent(cbFtime);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lbTtime);
        vpg2.addComponent(spTtime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg2.addComponent(cbTtime);
        javax.swing.GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg3.addComponent(lbStime);
        vpg3.addComponent(spStime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg3.addComponent(btStime);
        javax.swing.GroupLayout.ParallelGroup vpg4 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg4.addComponent(lbPeriod);
        vpg4.addComponent(cbPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg5 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg5.addComponent(lbEnum);
        vpg5.addComponent(spEnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
//        vsg.addContainerGap();
        vsg.addGroup(vpg1);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg2);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg3);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg4);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg5);
//        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);
    }

    @Override
    public void initLang()
    {
        lbFtime.setText("起始时间");
        cbFtime.setToolTipText("启用或禁用起始时间");
        lbTtime.setText("结束时间");
        cbTtime.setToolTipText("启用或禁用结束时间");
        lbStime.setText("执行时间");
        btStime.setToolTipText("当前时间");

        lbPeriod.setText("间隔周期");
        cbPeriod.addItem(new I1S1(ConsDat.MGTD_UNIT_MINUTE, "秒"));
        cbPeriod.addItem(new I1S1(ConsDat.MGTD_UNIT_SECOND, "分"));
        cbPeriod.addItem(new I1S1(ConsDat.MGTD_UNIT_HOUR, "时"));
        cbPeriod.addItem(new I1S1(ConsDat.MGTD_UNIT_DAY, "日"));
        cbPeriod.addItem(new I1S1(ConsDat.MGTD_UNIT_WEEK, "周"));
        cbPeriod.addItem(new I1S1(ConsDat.MGTD_UNIT_MONTH, "月"));
        cbPeriod.addItem(new I1S1(ConsDat.MGTD_UNIT_YEAR, "年"));
    }

    @Override
    public int getKey()
    {
        return ConsDat.MGTD_INTVAL_PERIOD;
    }

    @Override
    public String getName()
    {
        return "period";
    }

    @Override
    public String getTitle()
    {
        return "周期提醒";
    }

    @Override
    public boolean showData(MgtdHeader header)
    {
        boolean b = header.getP30F030D() != 0;
        if (b)
        {
            smFtime.setValue(new java.util.Date(header.getP30F030D()));
        }
        spFtime.setEnabled(b);
        cbFtime.setSelected(b);

        b = header.getP30F030E() != 0;
        if (b)
        {
            smTtime.setValue(new java.util.Date(header.getP30F030E()));
        }
        spTtime.setEnabled(b);
        cbTtime.setSelected(b);

        smStime.setValue(new java.util.Date(header.getP30F030F()));

        int[] item = new int[header.getHintList().size()];
        int i = 0;
        for (MgtdDetail hint : header.getHintList())
        {
            if (i == 0)
            {
                cbPeriod.setSelectedItem(new I1S1(hint.getP30F0404()));
            }
            if (hint.getP30F0404() == ConsDat.MGTD_UNIT_DAY || hint.getP30F0404() == ConsDat.MGTD_UNIT_MONTH)
            {
                item[i++] = hint.getP30F0405() - 1;
                continue;
            }
            if (hint.getP30F0404() == ConsDat.MGTD_UNIT_YEAR)
            {
                for (int j = 0, k = lsPeriod.getModel().getSize(); j < k; j += 1)
                {
                    if (lsPeriod.getModel().getElementAt(j).equals(hint.getP30F0405()))
                    {
                        item[i++] = j;
                        break;
                    }
                }
                continue;
            }
            item[i++] = hint.getP30F0405();
        }
        lsPeriod.setSelectedIndices(item);
        return true;
    }

    @Override
    public boolean saveData(MgtdHeader mgtd)
    {
        Object unitObj = cbPeriod.getSelectedItem();
        if (unitObj == null || !(unitObj instanceof I1S1))
        {
            return false;
        }

        Object[] timeObj = lsPeriod.getSelectedValues();
        if (timeObj == null || timeObj.length < 1)
        {
            Lang.showMesg(mgtdDlg, null, "请选择提醒周期信息！");
            lsPeriod.requestFocus();
            return false;
        }

        mgtd.setP30F030D(cbFtime.isSelected() ? smFtime.getDate().getTime() : 0);
        mgtd.setP30F030E(cbTtime.isSelected() ? smTtime.getDate().getTime() : 0);
        mgtd.setP30F030F(smStime.getDate().getTime());

        MgtdDetail hint;
        I1S1 time;
        I1S1 unit = (I1S1) unitObj;
        java.util.List<MgtdDetail> list = new java.util.ArrayList<MgtdDetail>();
        for (Object obj : timeObj)
        {
            if (!(obj instanceof I1S1))
            {
                continue;
            }
            time = (I1S1) obj;
            hint = new MgtdDetail();
            hint.setP30F0403(0L);
            hint.setP30F0404(unit.getK());
            hint.setP30F0405(time.getK());
            hint.setP30F0406("");
            list.add(hint);
        }
        mgtd.setHintList(list);
        return true;
    }

    private void cbIntvalActionPerformed(java.awt.event.ActionEvent e)
    {
        int idx = cbPeriod.getSelectedIndex() + 1;
        if (idx == ConsDat.MGTD_UNIT_SECOND)
        {
            lmEnum.clear();
            String tmp = Lang.getLang(LangRes.P30F110D, "{0}秒");
            for (int i = 0; i <= 59; i += 1)
            {
                lmEnum.addElement(new I1S1(i, Char.format(tmp, "" + i)));
            }
            return;
        }
        if (idx == ConsDat.MGTD_UNIT_MINUTE)
        {
            lmEnum.clear();
            String tmp = Lang.getLang(LangRes.P30F110F, "{0}分");
            for (int i = 0; i <= 59; i += 1)
            {
                lmEnum.addElement(new I1S1(i, Char.format(tmp, "" + i)));
            }
            return;
        }
        if (idx == ConsDat.MGTD_UNIT_HOUR)
        {
            lmEnum.clear();
            String tmp = Lang.getLang(LangRes.P30F1113, "{0}点");
            for (int i = 0; i <= 23; i += 1)
            {
                lmEnum.addElement(new I1S1(i, Char.format(tmp, "" + i)));
            }
            return;
        }
        if (idx == ConsDat.MGTD_UNIT_DAY)
        {
            lmEnum.clear();
            String tmp = Lang.getLang(LangRes.P30F1119, "{0}日");
            for (int i = 1; i <= 31; i += 1)
            {
                lmEnum.addElement(new I1S1(i, Char.format(tmp, "" + i)));
            }
            return;
        }
        if (idx == ConsDat.MGTD_UNIT_WEEK)
        {
            lmEnum.clear();
            String[] arr = Lang.getLang(LangRes.P30F111C, "星期日,星期一,星期二,星期三,星期四,星期五,星期六").split(",");
            for (int i = 0; i <= 6; i += 1)
            {
                lmEnum.addElement(new I1S1(i, arr[i]));
            }
            return;
        }
        if (idx == ConsDat.MGTD_UNIT_MONTH)
        {
            lmEnum.clear();
            String[] arr = Lang.getLang(LangRes.P30F111F, "一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月").split(",");
            for (int i = 1; i <= 12; i += 1)
            {
                lmEnum.addElement(new I1S1(i, arr[i - 1]));
            }
            return;
        }
        if (idx == ConsDat.MGTD_UNIT_YEAR)
        {
            lmEnum.clear();
            String tmp = Lang.getLang(LangRes.P30F1120, "{0}年");
            int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
            for (int i = year - 5, j = year + 24; i <= j; i += 1)
            {
                lmEnum.addElement(new I1S1(i, Char.format(tmp, "" + i)));
            }
            return;
        }
    }

    private void cbFtimeActionPerformed(java.awt.event.ActionEvent evt)
    {
        spFtime.setEnabled(cbFtime.isSelected());
    }

    private void cbTtimeActionPerformed(java.awt.event.ActionEvent evt)
    {
        spTtime.setEnabled(cbTtime.isSelected());
    }

    private void cbStimeActionPerformed(java.awt.event.MouseEvent evt)
    {
        spStime.setValue(new java.util.Date());
    }
    private javax.swing.JLabel lbFtime;
    private javax.swing.JSpinner spFtime;
    private javax.swing.JCheckBox cbFtime;
    private javax.swing.JLabel lbTtime;
    private javax.swing.JSpinner spTtime;
    private javax.swing.JCheckBox cbTtime;
    private javax.swing.JLabel lbStime;
    private javax.swing.JSpinner spStime;
    private javax.swing.JLabel btStime;
    private javax.swing.JLabel lbPeriod;
    private javax.swing.JComboBox cbPeriod;
    private javax.swing.JLabel lbEnum;
    private javax.swing.JList lsPeriod;
}
