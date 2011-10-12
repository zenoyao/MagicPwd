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
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._enum.AuthLog;
import com.magicpwd._util.Lang;

/**
 * Application: MagicPwd
 * Author     : Aven
 * Encoding   : UTF-8
 * Website    : http://magicpwd.com/
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : Amon@magicpwd.com
 * CopyRight  : Winshine.biz
 * Description: 数据升级
 */
public class SignDu extends javax.swing.JPanel implements IUserView
{

    private UserPtn userPtn;

    SignDu(UserPtn userPtn)
    {
        this.userPtn = userPtn;
    }

    @Override
    public void initView()
    {
        lbSrcPath = new javax.swing.JLabel();
        tfSrcPath = new javax.swing.JTextField();
        btSrcPath = new javax.swing.JLabel();
        lbDstPath = new javax.swing.JLabel();
        tfDstPath = new javax.swing.JTextField();
        btDstPath = new javax.swing.JLabel();
        plUserOpts = new javax.swing.JPanel();

        btSrcPath.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btDstPath.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        plUserOpts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        plUserOpts.setVisible(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lbDstPath);
        hpg1.addComponent(lbSrcPath);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(tfDstPath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE);
        hpg2.addComponent(tfSrcPath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup hpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg3.addComponent(btSrcPath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg3.addComponent(btDstPath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addGroup(hpg1);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg2);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg3);
        hsg.addContainerGap();
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(hsg));

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lbSrcPath);
        vpg1.addComponent(tfSrcPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg1.addComponent(btSrcPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lbDstPath);
        vpg2.addComponent(tfDstPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg2.addComponent(btDstPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        vsg.addGroup(vpg1);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg2);
        vsg.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(vsg));
    }

    @Override
    public void initMenu(javax.swing.JPopupMenu menu)
    {
    }

    @Override
    public void initLang()
    {
        Lang.setWText(lbSrcPath, null, "来源路径(@S)");

        Lang.setWText(lbDstPath, null, "目标路径(@D)");

        Lang.setWText(userPtn.getApplyButton(), null, "升级(@U)");

        Lang.setWText(userPtn.getAbortButton(), null, "返回(@C)");

        //userPtn.setTitle(Lang.getLang(LangRes.P30FA204, "口令找回"));
        userPtn.setTitle("数据升级");
    }

    @Override
    public void initData()
    {
        javax.swing.Icon icon = new javax.swing.ImageIcon(getClass().getResource(ConsEnv.RES_ICON + "open.png"));
        btSrcPath.setIcon(icon);
        btSrcPath.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
            }
        });

        btDstPath.setIcon(icon);
        btDstPath.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
            }
        });
    }

    @Override
    public javax.swing.JPanel getPanel()
    {
        return this;
    }

    @Override
    public void btApplyActionPerformed(java.awt.event.ActionEvent e)
    {
        String name = tfSrcPath.getText();
        if (!com.magicpwd._util.Char.isValidate(name))
        {
            Lang.showMesg(this, LangRes.P30FAA01, "请输入用户名称！");
            tfSrcPath.requestFocus();
            return;
        }
        String pwds = tfDstPath.getText();
        if (!com.magicpwd._util.Char.isValidate(pwds))
        {
            Lang.showMesg(this, LangRes.P30FAA14, "请输入安全口令！");
            tfDstPath.requestFocus();
            return;
        }
    }

    @Override
    public void btAbortActionPerformed(java.awt.event.ActionEvent e)
    {
        userPtn.initView(AuthLog.signIn);
        userPtn.initLang();
        userPtn.initData();
    }

    private void lbUserOptsMouseReleased(java.awt.event.MouseEvent evt)
    {
        plUserOpts.setVisible(!plUserOpts.isVisible());
        userPtn.pack();
    }
    private javax.swing.JLabel lbSrcPath;
    private javax.swing.JTextField tfSrcPath;
    private javax.swing.JLabel btSrcPath;
    private javax.swing.JLabel lbDstPath;
    private javax.swing.JTextField tfDstPath;
    private javax.swing.JLabel btDstPath;
    private javax.swing.JPanel plUserOpts;
}
