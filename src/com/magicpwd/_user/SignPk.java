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
package com.magicpwd._user;

import com.magicpwd.__i.IUserView;
import com.magicpwd._cons.LangRes;
import com.magicpwd._enum.AuthLog;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

/**
 *
 * @author Aven
 */
public class SignPk extends javax.swing.JPanel implements IUserView
{

    private UserPtn userPtn;

    SignPk(UserPtn userPtn)
    {
        this.userPtn = userPtn;
    }

    @Override
    public void initView()
    {
        lbUserPwd0 = new javax.swing.JLabel();
        pfUserPwd0 = new javax.swing.JPasswordField();
        lbUserPwd1 = new javax.swing.JLabel();
        pfUserPwd1 = new javax.swing.JPasswordField();
        lbUserPwd2 = new javax.swing.JLabel();
        pfUserPwd2 = new javax.swing.JPasswordField();
        plUserOpts = new javax.swing.JPanel();

        plUserOpts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        plUserOpts.setVisible(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lbUserPwd0, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbUserPwd1, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbUserPwd2, javax.swing.GroupLayout.Alignment.TRAILING);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(pfUserPwd0, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE);
        hpg2.addComponent(pfUserPwd1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE);
        hpg2.addComponent(pfUserPwd2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addGroup(hpg1);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addGroup(hpg2);
        javax.swing.GroupLayout.ParallelGroup hpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg3.addGroup(hsg1);
        hpg3.addComponent(plUserOpts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addContainerGap();
        hsg2.addGroup(hpg3);
        hsg2.addContainerGap();
        layout.setHorizontalGroup(hsg2);
        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lbUserPwd0);
        vpg1.addComponent(pfUserPwd0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lbUserPwd1);
        vpg2.addComponent(pfUserPwd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg3.addComponent(lbUserPwd2);
        vpg3.addComponent(pfUserPwd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
//        vsg1.addContainerGap();
        vsg1.addGroup(vpg1);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg2);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg3);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addComponent(plUserOpts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg1.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        layout.setVerticalGroup(vsg1);
    }

    @Override
    public void initMenu(javax.swing.JPopupMenu menu)
    {
    }

    @Override
    public void initLang()
    {
        Lang.setWText(lbUserPwd0, LangRes.P30FA304, "现有口令(@O)");

        Lang.setWText(lbUserPwd1, LangRes.P30FA305, "修改口令(@N)");

        Lang.setWText(lbUserPwd2, LangRes.P30FA306, "口令确认(@R)");

        Lang.setWText(userPtn.getApplyButton(), LangRes.P30FA503, "修改(@S)");

        Lang.setWText(userPtn.getAbortButton(), LangRes.P30FA504, "取消(@C)");

        userPtn.setTitle(Lang.getLang(LangRes.P30FA205, "登录口令修改"));
    }

    @Override
    public void initData()
    {
    }

    @Override
    public JPanel getPanel()
    {
        return this;
    }

    @Override
    public void btApplyActionPerformed(ActionEvent e)
    {
        String p1 = new String(pfUserPwd1.getPassword());
        String p2 = new String(pfUserPwd2.getPassword());
        if (!p1.equals(p2))
        {
            Lang.showMesg(this, LangRes.P30FAA08, "新输入的两次口令不匹配，请重新输入！");
            pfUserPwd1.setText("");
            pfUserPwd2.setText("");
            pfUserPwd1.requestFocus();
            return;
        }

        String p0 = new String(pfUserPwd0.getPassword());
        if (!com.magicpwd._util.Char.isValidate(p0))
        {
            Lang.showMesg(this, LangRes.P30FAA02, "请输入登录口令！");
            pfUserPwd0.requestFocus();
            return;
        }
        try
        {
            boolean b = userPtn.getUserMdl().signPk(p0, p1);
            if (!b)
            {
                Lang.showMesg(this, LangRes.P30FAA09, "口令更改失败，请检查您输入的口令是否与原口令相同！");
                pfUserPwd0.setText("");
                pfUserPwd1.setText("");
                pfUserPwd2.setText("");
                pfUserPwd0.requestFocus();
                return;
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(this, LangRes.P30FAA09, "口令更改失败，请检查您输入的口令是否与原口令相同！");
            return;
        }

        userPtn.callBack(AuthLog.signPk, null);
        userPtn.hideWindow();
        Lang.showMesg(this, LangRes.P30FAA0A, "登录口令修改成功，您可以使用新口令登录了！");
    }

    @Override
    public void btAbortActionPerformed(ActionEvent e)
    {
        userPtn.hideWindow();
    }

    private void lbUserOptsMouseReleased(java.awt.event.MouseEvent evt)
    {
        plUserOpts.setVisible(!plUserOpts.isVisible());
        userPtn.pack();
    }
    private javax.swing.JLabel lbUserPwd0;
    private javax.swing.JPasswordField pfUserPwd0;
    private javax.swing.JLabel lbUserPwd1;
    private javax.swing.JPasswordField pfUserPwd1;
    private javax.swing.JLabel lbUserPwd2;
    private javax.swing.JPasswordField pfUserPwd2;
    private javax.swing.JPanel plUserOpts;
}
