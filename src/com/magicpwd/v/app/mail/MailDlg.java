/*
 *  Copyright (C) 2011 Amon
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
package com.magicpwd.v.app.mail;

import com.magicpwd.__a.ADialog;
import com.magicpwd.__a.AMpwdPtn;
import com.magicpwd.__i.IBackCall;
import com.magicpwd._comn.S1S1;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Lang;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2011-1-16 11:54:05
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class MailDlg extends ADialog
{

    private AMpwdPtn formPtn;
    private IBackCall<String, String> backCall;
    private javax.swing.DefaultListModel lm_MailList;

    public MailDlg(AMpwdPtn mpwdPtn)
    {
        super(mpwdPtn, true);
        this.formPtn = mpwdPtn;
    }

    public void initView()
    {
        ls_MailList = new javax.swing.JList();
        bt_Abort = new javax.swing.JButton();
        bt_Apply = new javax.swing.JButton();

        javax.swing.JScrollPane sp_KindList = new javax.swing.JScrollPane();
        sp_KindList.setViewportView(ls_MailList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(bt_Apply);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(bt_Abort);
        javax.swing.GroupLayout.ParallelGroup hpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        hpg.addComponent(sp_KindList, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE);
        hpg.addGroup(hsg1);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addGroup(hpg);
        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(bt_Abort);
        vpg1.addComponent(bt_Apply);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        vsg.addComponent(sp_KindList, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg1);
        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//        setIconImage(Bean.getLogo(16));
    }

    public void initLang()
    {
        this.setTitle("邮件列表");

        Lang.setWText(bt_Apply, null, "打开(@O)");

        Lang.setWText(bt_Abort, null, "取消(@C)");
    }

    public void initData()
    {
        lm_MailList = new javax.swing.DefaultListModel();
        ls_MailList.setModel(lm_MailList);

        bt_Abort.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                hideDialog();
            }
        });

        bt_Apply.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_ApplyActionPerformed(evt);
            }
        });

        processEscape();
    }

    public void showData(java.util.List<S1S1> mailList)
    {
        lm_MailList.clear();

        if (mailList != null)
        {
            for (S1S1 item : mailList)
            {
                lm_MailList.addElement(item);
            }
        }

        pack();
        Bean.centerForm(this, formPtn);
        setVisible(true);
    }

    @Override
    protected boolean hideDialog()
    {
        setVisible(false);
        dispose();
        return true;
    }

    private void bt_ApplyActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (backCall != null)
        {
            backCall.callBack(IBackCall.OPTIONS_APPLY, "");
        }
        this.setVisible(false);
        this.dispose();
    }
    private javax.swing.JList ls_MailList;
    private javax.swing.JButton bt_Apply;
    private javax.swing.JButton bt_Abort;

    /**
     * @return the backCall
     */
    public IBackCall<String, String> getBackCall()
    {
        return backCall;
    }

    /**
     * @param backCall the backCall to set
     */
    public void setBackCall(IBackCall<String, String> backCall)
    {
        this.backCall = backCall;
    }
}
