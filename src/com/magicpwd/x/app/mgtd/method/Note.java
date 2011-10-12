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
package com.magicpwd.x.app.mgtd.method;

import com.magicpwd.__i.mgtd.IMgtdBean;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._cons.ConsDat;
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
public class Note extends javax.swing.JPanel implements IMgtdBean
{

    private MgtdDlg mgtdDlg;

    public Note(MgtdDlg mgtdDlg)
    {
        this.mgtdDlg = mgtdDlg;
    }

    @Override
    public void initView()
    {
        lbNote = new javax.swing.JLabel();
        tfNote = new javax.swing.JTextField();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
//        hsg.addContainerGap();
        hsg.addComponent(lbNote);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(tfNote, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE);
//        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg.addComponent(lbNote);
        vpg.addComponent(tfNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
//        vsg.addContainerGap();
        vsg.addGroup(vpg);
//        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);
    }

    @Override
    public void initLang()
    {
        lbNote.setText("提示信息");
    }

    @Override
    public int getKey()
    {
        return ConsDat.MGTD_METHOD_NOTE;
    }

    @Override
    public String getName()
    {
        return "note";
    }

    @Override
    public String getTitle()
    {
        return "提示信息";
    }

    @Override
    public boolean showData(MgtdHeader mgtd)
    {
        tfNote.setText(mgtd.getP30F0310());
        return true;
    }

    @Override
    public boolean saveData(MgtdHeader mgtd)
    {
        String text = tfNote.getText();
        if (!Char.isValidate(text))
        {
            Lang.showMesg(mgtdDlg, null, "请输入有效的提示信息！");
            tfNote.requestFocus();
            return false;
        }

        mgtd.setP30F030F(text);
        return true;
    }
    private javax.swing.JLabel lbNote;
    private javax.swing.JTextField tfNote;
}
