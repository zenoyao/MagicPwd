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
public class Mail extends javax.swing.JPanel implements IMgtdBean
{

    private MgtdDlg mgtdDlg;

    public Mail(MgtdDlg mgtdDlg)
    {
        this.mgtdDlg = mgtdDlg;
    }

    @Override
    public void initView()
    {
        lbMail = new javax.swing.JLabel();
        tfMail = new javax.swing.JTextField();

        lbBody = new javax.swing.JLabel();
        tfBody = new javax.swing.JTextField();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lbMail, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbBody, javax.swing.GroupLayout.Alignment.TRAILING);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(tfMail, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE);
        hpg2.addComponent(tfBody, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
//        hsg2.addContainerGap();
        hsg2.addGroup(hpg1);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addGroup(hpg2);
//        hsg2.addContainerGap();
        layout.setHorizontalGroup(hsg2);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lbMail);
        vpg1.addComponent(tfMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lbBody);
        vpg2.addComponent(tfBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
//        vsg.addContainerGap();
        vsg.addGroup(vpg1);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg2);
//        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);
    }

    @Override
    public void initLang()
    {
        lbMail.setText("邮件地址");
        lbBody.setText("邮件内容");
    }

    @Override
    public int getKey()
    {
        return ConsDat.MGTD_METHOD_MAIL;
    }

    @Override
    public String getName()
    {
        return "mail";
    }

    @Override
    public String getTitle()
    {
        return "发送邮件";
    }

    @Override
    public boolean showData(MgtdHeader mgtd)
    {
        tfMail.setText(mgtd.getP30F0310());
        tfBody.setText(mgtd.getP30F0311());
        return true;
    }

    @Override
    public boolean saveData(MgtdHeader mgtd)
    {
        String text = tfMail.getText();
        if (!Char.isValidate(text))
        {
            Lang.showMesg(mgtdDlg, null, "请输入邮件地址！");
            tfMail.requestFocus();
            return false;
        }
        StringBuilder buf = new StringBuilder();
        for (String tmp : text.split("[,\\s]"))
        {
            if (!Char.isValidateMail(tmp))
            {
                Lang.showMesg(mgtdDlg, null, tmp + " 不是一个有效的电子邮件地址！");
                tfMail.requestFocus();
                return false;
            }
        }

        mgtd.setP30F030F(text);
        mgtd.setP30F0311(tfBody.getText());
        return true;
    }
    private javax.swing.JLabel lbMail;
    private javax.swing.JTextField tfMail;
    private javax.swing.JLabel lbBody;
    private javax.swing.JTextField tfBody;
}
