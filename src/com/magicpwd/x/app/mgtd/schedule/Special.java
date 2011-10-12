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
import com.magicpwd._comn.mpwd.MgtdDetail;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._cons.ConsDat;
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
public class Special extends javax.swing.JPanel implements IMgtdBean
{

    private MgtdDlg mgtdDlg;

    public Special(MgtdDlg mgtdDlg)
    {
        this.mgtdDlg = mgtdDlg;
    }

    @Override
    public void initView()
    {
        lbSpecial = new javax.swing.JLabel();
        cbStartup = new javax.swing.JCheckBox();
        cbBeforeExit = new javax.swing.JCheckBox();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(cbBeforeExit);
        hpg1.addComponent(cbStartup);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
//        hsg1.addContainerGap();
        hsg1.addComponent(lbSpecial);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addGroup(hpg1);
//        hsg1.addContainerGap();
        layout.setHorizontalGroup(hsg1);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lbSpecial);
        vpg1.addComponent(cbStartup);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
//        vsg1.addContainerGap();
        vsg1.addGroup(vpg1);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addComponent(cbBeforeExit);
//        vsg1.addContainerGap();
        layout.setVerticalGroup(vsg1);
    }

    @Override
    public void initLang()
    {
        lbSpecial.setText("特殊功能");
        cbStartup.setText("程序启动时");
        cbBeforeExit.setText("程序退出时");
    }

    @Override
    public int getKey()
    {
        return ConsDat.MGTD_INTVAL_SPECIAL;
    }

    @Override
    public String getName()
    {
        return "special";
    }

    @Override
    public String getTitle()
    {
        return "特殊提醒";
    }

    @Override
    public boolean showData(MgtdHeader mgtd)
    {
        for (MgtdDetail hint : mgtd.getHintList())
        {
            if (ConsDat.MGTD_INTVAL_STARTUP == hint.getP30F0404())
            {
                cbStartup.setSelected(true);
                continue;
            }
            if (ConsDat.MGTD_INTVAL_BEFOREND == hint.getP30F0404())
            {
                cbBeforeExit.setSelected(true);
                continue;
            }
        }
        return true;
    }

    @Override
    public boolean saveData(MgtdHeader mgtd)
    {
        mgtd.setP30F030D(0L);
        mgtd.setP30F030E(0L);
        mgtd.setP30F030F(0L);

        java.util.List<MgtdDetail> list = new java.util.ArrayList<MgtdDetail>();

        if (cbStartup.isSelected())
        {
            MgtdDetail hint = new MgtdDetail();
            hint.setP30F0403(0L);
            hint.setP30F0404(ConsDat.MGTD_INTVAL_STARTUP);
            hint.setP30F0405(0);
            hint.setP30F0406("");
            list.add(hint);
        }
        if (cbBeforeExit.isSelected())
        {
            MgtdDetail hint = new MgtdDetail();
            hint.setP30F0403(0L);
            hint.setP30F0404(ConsDat.MGTD_INTVAL_BEFOREND);
            hint.setP30F0405(0);
            hint.setP30F0406("");
            list.add(hint);
        }
        if (list.size() < 1)
        {
            Lang.showMesg(mgtdDlg, null, "请选择特殊提醒时间！");
            return false;
        }

        mgtd.setHintList(list);
        return true;
    }
    private javax.swing.JCheckBox cbStartup;
    private javax.swing.JCheckBox cbBeforeExit;
    private javax.swing.JLabel lbSpecial;
}
