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
import com.magicpwd._cons.ConsCfg;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._enum.AuthLog;
import com.magicpwd._util.Char;
import com.magicpwd._util.Jzip;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.UserMdl;
import java.util.regex.Pattern;
import javax.crypto.KeyGenerator;

/**
 *
 * @author Aven
 */
public class SignUp extends javax.swing.JPanel implements IUserView
{

    private UserPtn userPtn;

    SignUp(UserPtn userPtn)
    {
        this.userPtn = userPtn;
    }

    @Override
    public void initView()
    {
        initOptsView();
        initBaseView();
    }

    @Override
    public void initMenu(javax.swing.JPopupMenu menu)
    {
        menu.addSeparator();

        miUserOpts = new javax.swing.JCheckBoxMenuItem();
        miUserOpts.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                miUserOptsActionPerformed(e);
            }
        });
        menu.add(miUserOpts);
    }

    @Override
    public void initLang()
    {
        Lang.setWText(lbUserName, LangRes.P30FA301, "用户(@U)");

        Lang.setWText(lbUserPwd1, LangRes.P30FA302, "口令(@P)");

        Lang.setWText(lbUserPwd2, LangRes.P30FA303, "确认(@R)");

        Lang.setWText(miUserOpts, null, "高级选项");
        Lang.setWTips(miUserOpts, null, "");

        Lang.setWText(userPtn.getApplyButton(), LangRes.P30FA502, "注册(@S)");

        Lang.setWText(userPtn.getAbortButton(), LangRes.P30FA505, "返回(@C)");

        userPtn.setTitle(Lang.getLang(LangRes.P30FA203, "用户注册"));

        lbDatPath.setText("数据路径(D)");

        btDatPath.setToolTipText("选择文件路径");

        cbSfKey.setText("使用密码文件");

        cbDbSec.setText("使用数据库加密");
    }

    @Override
    public void initData()
    {
        tfUserName.addFocusListener(new java.awt.event.FocusListener()
        {

            @Override
            public void focusGained(java.awt.event.FocusEvent e)
            {
                tfUserName.selectAll();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e)
            {
                String path = tfUserName.getText();
                if (!Char.isValidate(path))
                {
                    path = "dat";
                }
                tfDatPath.setText(Char.format(".{0}{1}{0}", java.io.File.separator, path));
            }
        });
        tfUserName.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                checkUserName();
            }
        });
        pfUserPwd1.addFocusListener(new java.awt.event.FocusListener()
        {

            @Override
            public void focusGained(java.awt.event.FocusEvent e)
            {
                pfUserPwd1.selectAll();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e)
            {
            }
        });
        pfUserPwd1.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                checkUserPwd1();
            }
        });
        pfUserPwd2.addFocusListener(new java.awt.event.FocusListener()
        {

            @Override
            public void focusGained(java.awt.event.FocusEvent e)
            {
                pfUserPwd2.selectAll();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e)
            {
            }
        });
        pfUserPwd2.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                checkUserPwd2();
            }
        });

        tfDatPath.setText(Char.format(".{0}{1}{0}", java.io.File.separator, "dat"));
        btDatPath.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                chooseDatPath();
            }
        });

        cbDbSec.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                cbDbSecActionPerformed(e);
            }
        });

        tfUserName.requestFocus();
    }

    @Override
    public javax.swing.JPanel getPanel()
    {
        return this;
    }

    @Override
    public void btApplyActionPerformed(java.awt.event.ActionEvent evt)
    {
        signUp();
    }

    @Override
    public void btAbortActionPerformed(java.awt.event.ActionEvent evt)
    {
        userPtn.initView(AuthLog.signIn);
        userPtn.initLang();
        userPtn.initData();
    }

    private void chooseDatPath()
    {
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser(tfDatPath.getText());
        fc.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        if (javax.swing.JFileChooser.APPROVE_OPTION != fc.showOpenDialog(userPtn))
        {
            return;
        }
        java.io.File file = fc.getSelectedFile();
        tfDatPath.setText(file.getPath());
    }

    private void checkUserName()
    {
        String un = tfUserName.getText().trim().replaceAll("\\s+", "");
        if (!com.magicpwd._util.Char.isValidate(un))
        {
            Lang.showMesg(this, LangRes.P30FAA01, "请输入用户名称！");
            tfUserName.requestFocus();
            return;
        }
        pfUserPwd1.requestFocus();
    }

    private void checkUserPwd1()
    {
        String p1 = new String(pfUserPwd1.getPassword());
        if (!com.magicpwd._util.Char.isValidate(p1))
        {
            Lang.showMesg(this, LangRes.P30FAA02, "请输入登录口令！");
            pfUserPwd1.requestFocus();
            return;
        }
        pfUserPwd2.requestFocus();
    }

    private void checkUserPwd2()
    {
        String p1 = new String(pfUserPwd1.getPassword());
        String p2 = new String(pfUserPwd2.getPassword());
        if (!p1.equals(p2))
        {
            Lang.showMesg(this, LangRes.P30FAA05, "您输入的口令不一致，请重新输入！");
            pfUserPwd1.setText("");
            pfUserPwd2.setText("");
            pfUserPwd1.requestFocus();
            return;
        }
        signUp();
    }

    private void signUp()
    {
        // 登录名称检测
        String un = tfUserName.getText().trim().replaceAll("\\s+", "").toLowerCase();
        if (!com.magicpwd._util.Char.isValidate(un))
        {
            Lang.showMesg(this, LangRes.P30FAA01, "请输入用户名称！");
            tfUserName.requestFocus();
            return;
        }
        if (!java.util.regex.Pattern.matches("^\\w+$", un))
        {
            return;
        }

        // 检测是否用户已存在
        if (Char.isValidate(userPtn.getUserMdl().getMpwdMdl().getCfg("dat." + un, null)))
        {
            Lang.showMesg(this, null, Char.format("用户{0}已存在，请选择其它用户名！", un));
            return;
        }

        // 口令为空检测
        String p1 = new String(pfUserPwd1.getPassword());
        if (!com.magicpwd._util.Char.isValidate(p1))
        {
            Lang.showMesg(this, LangRes.P30FAA02, "请输入登录口令！");
            pfUserPwd1.requestFocus();
            return;
        }

        // 口令不一致检测
        String p2 = new String(pfUserPwd2.getPassword());
        if (!p1.equals(p2))
        {
            Lang.showMesg(this, LangRes.P30FAA05, "您输入的口令不一致，请重新输入！");
            pfUserPwd1.setText("");
            pfUserPwd2.setText("");
            pfUserPwd1.requestFocus();
            return;
        }
        String path = tfDatPath.getText();
        if (!com.magicpwd._util.Char.isValidate(path))
        {
            Lang.showMesg(this, null, "请输入或选择您要存放的数据目录！");
            tfDatPath.requestFocus();
            return;
        }
        if (Pattern.matches("^[a-zA-z]{2,}:/{2,3}[^\\s]+", path))
        {
            Lang.showMesg(this, null, "当前版本暂不支持基于网络的文件访问！");
            tfDatPath.requestFocus();
            return;
        }
        java.io.File datPath = new java.io.File(path);
        if (!datPath.exists())
        {
            datPath.mkdirs();
        }
        if (!datPath.isDirectory())
        {
            Lang.showMesg(this, LangRes.P30F1A08, "请选择一个目录！");
            return;
        }
        if (!datPath.canWrite())
        {
            Lang.showMesg(this, LangRes.P30F1A0F, "无法写入文件 {0} ，请确认您是否有足够的权限！", datPath.getPath());
            return;
        }
        java.io.File datFile = new java.io.File(datPath, "amon.script");
        if (datFile.exists() && datFile.isFile())
        {
            if (javax.swing.JOptionPane.YES_OPTION != Lang.showFirm(this, LangRes.P30F1A0D, "您选择的文件已存在，确认要继续吗？"))
            {
                return;
            }
        }
        tfUserName.setText("");
        pfUserPwd1.setText("");
        pfUserPwd2.setText("");

        UserMdl userMdl = userPtn.getUserMdl();
        userMdl.loadDef();
        boolean isOK = false;
        try
        {
            isOK = userMdl.signUp(un, p1);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(this, LangRes.P30FAA07, "创建用户失败，请重新启动本程序尝试！");
            System.exit(0);
            return;
        }

        if (!isOK)
        {
            Lang.showMesg(this, LangRes.P30FAA06, "注册用户失败，请更换用户名及口令后重试！");
            tfUserName.requestFocus();
            return;
        }

        try
        {
            Jzip.unZip(Jzip.class.getResourceAsStream("/res/dat.zip"), datPath, true);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(userPtn, null, exp.getLocalizedMessage());
        }

        if (cbDbSec.isSelected())
        {
            String cipher = "AES";
            String key = null;
            try
            {
                KeyGenerator kg = KeyGenerator.getInstance(cipher);
                key = Char.toHex(kg.generateKey().getEncoded());
            }
            catch (Exception exp)
            {
                Lang.showMesg(this, null, exp.getLocalizedMessage());
            }

            if (!Char.isValidate(key))
            {
                Lang.showMesg(this, null, "尝试使用数据库加密失败，软件将以默认方式运行！");
            }

            userMdl.setCfg(ConsCfg.CFG_DB_SC, cipher);
            userMdl.setCfg(ConsCfg.CFG_DB_SK, key);
        }

        userMdl.setDataDir(path);
        userMdl.getMpwdMdl().setUserLast(un);
        DBA4000.initDataBase(userMdl);

        if (userPtn.callBack(AuthLog.signUp, new UserDto("mwiz", un, p1)))
        {
            userPtn.hideWindow();
            return;
        }
    }

    private void initBaseView()
    {
        lbUserName = new javax.swing.JLabel();
        tfUserName = new javax.swing.JTextField();
        lbUserPwd1 = new javax.swing.JLabel();
        pfUserPwd1 = new javax.swing.JPasswordField();
        lbUserPwd2 = new javax.swing.JLabel();
        pfUserPwd2 = new javax.swing.JPasswordField();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lbUserName, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbUserPwd1, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbUserPwd2, javax.swing.GroupLayout.Alignment.TRAILING);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(tfUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE);
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
        vpg1.addComponent(lbUserName);
        vpg1.addComponent(tfUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
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
//        vsg1.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        layout.setVerticalGroup(vsg1);
    }

    private void initOptsView()
    {
        plUserOpts = new javax.swing.JPanel();

        spSepLine = new javax.swing.JSeparator();
        lbDatPath = new javax.swing.JLabel();
        tfDatPath = new javax.swing.JTextField();
        btDatPath = new javax.swing.JLabel();
        cbSfKey = new javax.swing.JCheckBox();
        cbDbSec = new javax.swing.JCheckBox();

        btDatPath.setIcon(new javax.swing.ImageIcon(getClass().getResource(ConsEnv.RES_ICON + "open.png")));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(plUserOpts);
        plUserOpts.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(tfDatPath, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(btDatPath, 18, 18, 18);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(cbDbSec);
        hpg1.addComponent(cbSfKey);
        hpg1.addGroup(hsg1);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
//        hsg2.addContainerGap();
        hsg2.addComponent(lbDatPath);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addGroup(hpg1);
//        hsg2.addContainerGap();
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(spSepLine, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE);
        hpg2.addGroup(hsg2);
        layout.setHorizontalGroup(hpg2);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER);
        vpg1.addComponent(lbDatPath);
        vpg1.addComponent(tfDatPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg1.addComponent(btDatPath, 18, 18, 18);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
//        vsg1.addContainerGap();
        vsg1.addComponent(spSepLine, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg1);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addComponent(cbSfKey);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addComponent(cbDbSec);
//        vsg1.addContainerGap();
        layout.setVerticalGroup(vsg1);

        plUserOpts.setVisible(false);
    }

    private void miUserOptsActionPerformed(java.awt.event.ActionEvent evt)
    {
        plUserOpts.setVisible(!plUserOpts.isVisible());
        userPtn.pack();
    }

    private void cbDbSecActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (cbDbSec.isSelected())
        {
            Lang.showMesg(userPtn, null, "使用数据库加密在一定程度上能够增强您的数据安全性，\n但当文件比较大时软件运行速度会稍慢，请谨慎使用！");
        }
    }
    private javax.swing.JLabel lbUserName;
    private javax.swing.JTextField tfUserName;
    private javax.swing.JLabel lbUserPwd1;
    private javax.swing.JPasswordField pfUserPwd1;
    private javax.swing.JLabel lbUserPwd2;
    private javax.swing.JPasswordField pfUserPwd2;
    private javax.swing.JCheckBoxMenuItem miUserOpts;
    private javax.swing.JPanel plUserOpts;
    //=============================
    private javax.swing.JLabel btDatPath;
    private javax.swing.JCheckBox cbDbSec;
    private javax.swing.JCheckBox cbSfKey;
    private javax.swing.JLabel lbDatPath;
    private javax.swing.JTextField tfDatPath;
    private javax.swing.JSeparator spSepLine;
}
