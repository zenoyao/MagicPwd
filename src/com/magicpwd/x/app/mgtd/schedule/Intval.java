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
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._comn.mpwd.MgtdDetail;
import com.magicpwd._cons.ConsDat;
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
public class Intval extends javax.swing.JPanel implements IMgtdBean
{

    private MgtdDlg mgtdDlg;
    private javax.swing.SpinnerDateModel smFtime;
    private javax.swing.SpinnerDateModel smTtime;
    private javax.swing.SpinnerDateModel smStime;
    private javax.swing.SpinnerNumberModel smIntval;

    public Intval(MgtdDlg mgtdDlg)
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

        spIntval = new javax.swing.JSpinner();
        smIntval = new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1));
        spIntval.setModel(smIntval);
        cbIntval = new javax.swing.JComboBox();
        lbIntval = new javax.swing.JLabel();
        lbIntval.setLabelFor(cbIntval);

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
        javax.swing.GroupLayout.SequentialGroup hsg4 = layout.createSequentialGroup();
        hsg4.addComponent(spIntval, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg4.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg4.addComponent(cbIntval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lbFtime, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbTtime, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbStime, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbIntval, javax.swing.GroupLayout.Alignment.TRAILING);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addGroup(hsg1);
        hpg2.addGroup(hsg2);
        hpg2.addGroup(hsg3);
        hpg2.addGroup(hsg4);
        javax.swing.GroupLayout.SequentialGroup hsg5 = layout.createSequentialGroup();
//        hsg5.addContainerGap();
        hsg5.addGroup(hpg1);
        hsg5.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg5.addGroup(hpg2);
//        hsg5.addContainerGap();
        layout.setHorizontalGroup(hsg5);

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
        vpg4.addComponent(lbIntval);
        vpg4.addComponent(cbIntval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg4.addComponent(spIntval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
//        vsg1.addContainerGap();
        vsg1.addGroup(vpg1);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg2);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg3);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg4);
//        vsg1.addContainerGap();
        layout.setVerticalGroup(vsg1);
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

        lbIntval.setText("间隔时间");
        cbIntval.addItem(new I1S1(0, "无"));
        cbIntval.addItem(new I1S1(ConsDat.MGTD_UNIT_SECOND, "秒"));
        cbIntval.addItem(new I1S1(ConsDat.MGTD_UNIT_MINUTE, "分"));
        cbIntval.addItem(new I1S1(ConsDat.MGTD_UNIT_HOUR, "时"));
        cbIntval.addItem(new I1S1(ConsDat.MGTD_UNIT_WEEK, "周"));
        cbIntval.addItem(new I1S1(ConsDat.MGTD_UNIT_DAY, "日"));
        cbIntval.addItem(new I1S1(ConsDat.MGTD_UNIT_MONTH, "月"));
        cbIntval.addItem(new I1S1(ConsDat.MGTD_UNIT_YEAR, "年"));
    }

    @Override
    public int getKey()
    {
        return ConsDat.MGTD_INTVAL_INTVAL;
    }

    @Override
    public String getName()
    {
        return "interval";
    }

    @Override
    public String getTitle()
    {
        return "间隔提醒";
    }

    @Override
    public boolean showData(MgtdHeader header)
    {
        boolean b = header.getP30F030D() != 0;
        if (b)
        {
            spFtime.setValue(new java.util.Date(header.getP30F030D()));
        }
        spFtime.setEnabled(b);
        cbFtime.setSelected(b);

        b = header.getP30F030E() != 0;
        if (b)
        {
            spTtime.setValue(new java.util.Date(header.getP30F030E()));
        }
        spTtime.setEnabled(b);
        cbTtime.setSelected(b);

        MgtdDetail detail = header.getHint(0);
        if (detail != null)
        {
            spStime.setValue(new java.util.Date(detail.getP30F0403()));

            cbIntval.setSelectedItem(new I1S1(detail.getP30F0404()));
            spIntval.setValue(detail.getP30F0405());
            return true;
        }
        return false;
    }

    @Override
    public boolean saveData(MgtdHeader header)
    {
        Object unitObj = cbIntval.getSelectedItem();
        if (unitObj == null || !(unitObj instanceof I1S1))
        {
            return false;
        }

        I1S1 unit = (I1S1) unitObj;
        header.setP30F030D(cbFtime.isSelected() ? smFtime.getDate().getTime() : 0);
        header.setP30F030E(cbTtime.isSelected() ? smTtime.getDate().getTime() : 0);
        header.setP30F030F(0L);

        java.util.List<MgtdDetail> list = new java.util.ArrayList<MgtdDetail>();

        MgtdDetail detail = new MgtdDetail();
        detail.setP30F0403(smStime.getDate().getTime());
        detail.setP30F0404(unit.getK());
        detail.setP30F0405(smIntval.getNumber().intValue());
        detail.setP30F0406("");
        list.add(detail);

        header.setHintList(list);
        return true;
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
    private javax.swing.JLabel lbStime;
    private javax.swing.JSpinner spStime;
    private javax.swing.JLabel btStime;
    private javax.swing.JLabel lbTtime;
    private javax.swing.JSpinner spTtime;
    private javax.swing.JCheckBox cbTtime;
    private javax.swing.JLabel lbIntval;
    private javax.swing.JSpinner spIntval;
    private javax.swing.JComboBox cbIntval;
}
