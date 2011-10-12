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
import com.magicpwd._bean.mail.Connect;
import com.magicpwd._cons.LangRes;
import com.magicpwd._enum.AuthLog;
import com.magicpwd._util.Lang;

/**
 *
 * @author Aven
 */
public class SignCs extends javax.swing.JPanel implements IUserView
{

    private UserPtn userPtn;

    SignCs(UserPtn userPtn)
    {
        this.userPtn = userPtn;
    }

    @Override
    public void initView()
    {
        lbMailList = new javax.swing.JLabel();
        cbMailList = new javax.swing.JComboBox();
        lbUserName = new javax.swing.JLabel();
        tfUserName = new javax.swing.JTextField();
        lbUserPwds = new javax.swing.JLabel();
        pfUserPwds = new javax.swing.JPasswordField();
        plUserOpts = new javax.swing.JPanel();

        plUserOpts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        plUserOpts.setVisible(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lbMailList, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbUserName, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbUserPwds, javax.swing.GroupLayout.Alignment.TRAILING);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(cbMailList, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE);
        hpg2.addComponent(tfUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE);
        hpg2.addComponent(pfUserPwds, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE);
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
        vpg1.addComponent(lbMailList);
        vpg1.addComponent(cbMailList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lbUserName);
        vpg2.addComponent(tfUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg3.addComponent(lbUserPwds);
        vpg3.addComponent(pfUserPwds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
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
        Lang.setWText(lbMailList, LangRes.P30FA313, "服务器(@M)");

        Lang.setWText(lbUserName, LangRes.P30FA301, "用户(@U)");

        Lang.setWText(lbUserPwds, LangRes.P30FA302, "口令(@P)");

        Lang.setWText(userPtn.getApplyButton(), LangRes.P30FA508, "设定(@S)");

        Lang.setWText(userPtn.getAbortButton(), LangRes.P30FA504, "取消(@C)");

        userPtn.setTitle(Lang.getLang(LangRes.P30FA20B, "配置邮件账户"));
    }

    @Override
    public void initData()
    {
        java.util.Properties prop = Connect.getMailCfg();
        cbMailList.removeAllItems();
        java.util.Enumeration<?> names = prop.propertyNames();
        java.util.LinkedList<String> text = new java.util.LinkedList<String>();
        while (names.hasMoreElements())
        {
            String t = names.nextElement() + "";
            int i = t.lastIndexOf(".type");
            if (i <= 0 || i + 5 != t.length())
            {
                continue;
            }
            t = t.substring(0, i);

            // 排序
            i = 0;
            int j = text.size();
            while (i < j)
            {
                if (t.compareTo(text.get(i)) < 0)
                {
                    break;
                }
                i += 1;
            }
            text.add(i, t);
            cbMailList.insertItemAt(t, i);
        }
    }

    @Override
    public javax.swing.JPanel getPanel()
    {
        return this;
    }

    @Override
    public void btApplyActionPerformed(java.awt.event.ActionEvent e)
    {
        // 登录名称检测
        String ut = (cbMailList.getSelectedItem() + "").trim();
        if (ut.length() < 1)
        {
            Lang.showMesg(this, LangRes.P30FAA1D, "请选择邮件服务器！");
            cbMailList.requestFocus();
            return;
        }

        String un = tfUserName.getText();
        if (un == null)
        {
            Lang.showMesg(this, LangRes.P30FAA01, "请输入用户名称！");
            tfUserName.requestFocus();
            return;
        }
        un = un.trim();
        if (un.length() < 1)
        {
            Lang.showMesg(this, LangRes.P30FAA01, "请输入用户名称！");
            tfUserName.requestFocus();
            return;
        }

        char[] uc = pfUserPwds.getPassword();
        if (uc == null || uc.length < 1)
        {
            Lang.showMesg(this, LangRes.P30FAA02, "请输入登录口令！");
            pfUserPwds.requestFocus();
            return;
        }
        userPtn.callBack(AuthLog.signCs, new UserDto(ut, un, new String(uc)));
        userPtn.hideWindow();
    }

    @Override
    public void btAbortActionPerformed(java.awt.event.ActionEvent e)
    {
        userPtn.hideWindow();
    }

    private void lbUserOptsMouseReleased(java.awt.event.MouseEvent evt)
    {
        plUserOpts.setVisible(!plUserOpts.isVisible());
        userPtn.pack();
    }
    private javax.swing.JLabel lbMailList;
    private javax.swing.JComboBox cbMailList;
    private javax.swing.JLabel lbUserName;
    private javax.swing.JTextField tfUserName;
    private javax.swing.JLabel lbUserPwds;
    private javax.swing.JPasswordField pfUserPwds;
    private javax.swing.JPanel plUserOpts;
}
