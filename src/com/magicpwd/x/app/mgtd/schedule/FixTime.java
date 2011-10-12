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
public class FixTime extends javax.swing.JPanel implements IMgtdBean
{

    private MgtdDlg mgtdDlg;
    private javax.swing.SpinnerDateModel smTime;

    public FixTime(MgtdDlg mgtdDlg)
    {
        this.mgtdDlg = mgtdDlg;
    }

    @Override
    public void initView()
    {
        btTime = new javax.swing.JLabel();
        btTime.setIcon(mgtdDlg.getFeelIcon("date-now", "var:date-now"));
        btTime.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btTimeActionPerformed(evt);
            }
        });
        spTime = new javax.swing.JSpinner();
        smTime = new javax.swing.SpinnerDateModel();
        spTime.setModel(smTime);
        lbTime = new javax.swing.JLabel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
//        hsg.addContainerGap();
        hsg.addComponent(lbTime);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(spTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(btTime);
//        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg.addComponent(lbTime);
        vpg.addComponent(spTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg.addComponent(btTime);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
//        vsg.addContainerGap();
        vsg.addGroup(vpg);
//        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);
    }

    @Override
    public void initLang()
    {
        lbTime.setText("提醒时间");
        btTime.setToolTipText("当前时间");
    }

    @Override
    public int getKey()
    {
        return ConsDat.MGTD_INTVAL_FIXTIME;
    }

    @Override
    public String getName()
    {
        return "fixTime";
    }

    @Override
    public String getTitle()
    {
        return "定时提醒";
    }

    @Override
    public boolean showData(MgtdHeader mgtd)
    {
        MgtdDetail detail = mgtd.getHint(0);
        if (detail != null)
        {
            spTime.setValue(new java.util.Date(detail.getP30F0403()));
            return true;
        }
        return false;
    }

    @Override
    public boolean saveData(MgtdHeader mgtd)
    {
        mgtd.setP30F030D(0L);
        mgtd.setP30F030E(0L);
        mgtd.setP30F030F(0L);

        java.util.List<MgtdDetail> list = new java.util.ArrayList<MgtdDetail>();

        MgtdDetail hint = new MgtdDetail();
        hint.setP30F0403(smTime.getDate().getTime());
        hint.setP30F0404(0);
        hint.setP30F0405(0);
        hint.setP30F0406("");
        list.add(hint);

        mgtd.setHintList(list);
        return true;
    }

    private void btTimeActionPerformed(java.awt.event.MouseEvent e)
    {
        spTime.setValue(new java.util.Date());
//        WDateChooser dc = new WDateChooser();
//        dc.show(btTime, 0, btTime.getHeight());
    }
    private javax.swing.JLabel lbTime;
    private javax.swing.JSpinner spTime;
    private javax.swing.JLabel btTime;
}
