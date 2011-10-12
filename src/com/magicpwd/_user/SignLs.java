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

/**
 *
 * @author Aven
 */
public class SignLs extends javax.swing.JPanel implements IUserView
{

    private UserPtn userPtn;
    /**
     * 登录错误次数
     */
    private int errCount;

    SignLs(UserPtn userPtn)
    {
        this.userPtn = userPtn;
    }

    @Override
    public void initView()
    {
        lbUserPwds = new javax.swing.JLabel();
        pfUserPwds = new javax.swing.JPasswordField();
        plUserOpts = new javax.swing.JPanel();

        plUserOpts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        plUserOpts.setVisible(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(lbUserPwds);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(pfUserPwds, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup hpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg3.addGroup(hsg1);
        hpg3.addComponent(plUserOpts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addContainerGap();
        hsg2.addGroup(hpg3);
        hsg2.addContainerGap();
        layout.setHorizontalGroup(hsg2);
        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lbUserPwds);
        vpg1.addComponent(pfUserPwds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
//        vsg1.addContainerGap();
        vsg1.addGroup(vpg1);
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
        Lang.setWText(lbUserPwds, LangRes.P30FA302, "口令(@P)");

        Lang.setWText(userPtn.getApplyButton(), LangRes.P30FA501, "登录(@S)");

        userPtn.setTitle(Lang.getLang(LangRes.P30FA202, "身份验证"));
    }

    @Override
    public void initData()
    {
    }

    @Override
    public javax.swing.JPanel getPanel()
    {
        return this;
    }

    @Override
    public void btApplyActionPerformed(java.awt.event.ActionEvent e)
    {
        String pwds = new String(pfUserPwds.getPassword());
        if (!com.magicpwd._util.Char.isValidate(pwds))
        {
            Lang.showMesg(this, LangRes.P30FAA02, "请输入登录口令！");
            pfUserPwds.requestFocus();
            return;
        }
        pfUserPwds.setText("");

        try
        {
            boolean b = userPtn.getUserMdl().signLs(pwds);
            if (!b)
            {
                errCount += 1;
                if (errCount > 2)
                {
                    Lang.showMesg(this, LangRes.P30FAA1C, "您操作的错误次太多，请确认您是否为合法用户！\n为了保障用户数据安全，软件将自动关闭。");
                    System.exit(0);
                }
                else
                {
                    Lang.showMesg(this, LangRes.P30FAA03, "身份验证错误，请确认您的用户名及口令是否正确！");
                    pfUserPwds.requestFocus();
                }
                return;
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(this, LangRes.P30FAA03, "身份验证错误，请确认您的用户名及口令是否正确！");
            System.exit(0);
            return;
        }

        userPtn.callBack(AuthLog.signLs, null);
        userPtn.hideWindow();
    }

    @Override
    public void btAbortActionPerformed(java.awt.event.ActionEvent e)
    {
    }

    private void lbUserOptsMouseReleased(java.awt.event.MouseEvent evt)
    {
        plUserOpts.setVisible(!plUserOpts.isVisible());
        userPtn.pack();
    }
    private javax.swing.JLabel lbUserPwds;
    private javax.swing.JPasswordField pfUserPwds;
    private javax.swing.JPanel plUserOpts;
}
