/*
 *  Copyright (C) 2010 Amon
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
package com.magicpwd._prop;

import com.magicpwd.__i.IPropBean;
import com.magicpwd._comn.prop.Char;
import com.magicpwd._comn.S1S1;
import com.magicpwd._cons.ConsCfg;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;
import com.magicpwd.m.UserMdl;
import com.magicpwd.v.app.mpro.MproPtn;

/**
 * @author Amon
 * 
 */
public class USetProp extends javax.swing.JPanel implements IPropBean
{

    private MproPtn mainPtn;
    private java.io.File backPath;

    public USetProp(MproPtn mainPtn)
    {
        this.mainPtn = mainPtn;
    }

    @Override
    public void initView()
    {
        lb_UserLang = new javax.swing.JLabel();
        cb_UserLang = new javax.swing.JComboBox();
        lb_PwdsChar = new javax.swing.JLabel();
        cb_PwdsChar = new javax.swing.JComboBox();
        lb_PwdsSize = new javax.swing.JLabel();
        tf_PwdsSize = new javax.swing.JTextField(6);
        ck_PwdsLoop = new javax.swing.JCheckBox();
        lb_BackCount = new javax.swing.JLabel();
        tf_BackCount = new javax.swing.JTextField(6);
        lb_BackPath = new javax.swing.JLabel();
        tf_BackPath = new javax.swing.JTextField(20);
        bt_BackPath = new javax.swing.JButton();
        lb_StayTime = new javax.swing.JLabel();
        tf_StayTime = new javax.swing.JTextField(6);

        bt_BackPath.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_BackPathActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(lb_UserLang).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cb_UserLang, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addComponent(lb_PwdsChar).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cb_PwdsChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addComponent(lb_PwdsSize).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(tf_PwdsSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(ck_PwdsLoop)).addGroup(layout.createSequentialGroup().addComponent(lb_BackCount).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(tf_BackCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addComponent(lb_BackPath).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(tf_BackPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(bt_BackPath)).addGroup(layout.createSequentialGroup().addComponent(lb_StayTime).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(tf_StayTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(97, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lb_UserLang).addComponent(cb_UserLang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lb_PwdsChar).addComponent(cb_PwdsChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lb_PwdsSize).addComponent(tf_PwdsSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(ck_PwdsLoop)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lb_BackCount).addComponent(tf_BackCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lb_BackPath).addComponent(tf_BackPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(bt_BackPath)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lb_StayTime).addComponent(tf_StayTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(59, Short.MAX_VALUE)));
    }

    @Override
    public void initLang()
    {
        Lang.setWText(lb_PwdsChar, LangRes.P30F8301, "字符空间");
        Lang.setWText(lb_PwdsSize, LangRes.P30F8302, "口令长度");
        Lang.setWText(ck_PwdsLoop, LangRes.P30F8303, "允许重复");

        Lang.setWText(lb_BackCount, LangRes.P30F8304, "备份数量");
        Lang.setWText(lb_BackPath, LangRes.P30F8305, "备份路径");
        Lang.setWText(bt_BackPath, LangRes.P30F8501, ">>");

        Lang.setWText(lb_StayTime, LangRes.P30F8306, "剪贴板口令驻留时间（秒）");

        Lang.setWText(lb_UserLang, LangRes.P30F8307, "语言区域");
//        Lang.setWText(bt_Update, LangRes.P30F8503, "保存");
//        Lang.setWText(bt_Default, LangRes.P30F8505, "默认");
    }

    @Override
    public void initData()
    {
        if (cb_UserLang.getItemCount() < 1)
        {
            java.util.Locale[] locales = java.util.Locale.getAvailableLocales();
            S1S1[] names = new S1S1[locales.length];
            java.util.Locale local;
            for (int i = 0; i < locales.length; i += 1)
            {
                local = locales[i];
                names[i] = new S1S1(local.getLanguage() + '_' + local.getCountry(), local.getDisplayName(local));
            }
            java.util.Arrays.sort(names);
            cb_UserLang.setModel(new javax.swing.DefaultComboBoxModel(names));
        }
        cb_UserLang.setSelectedItem(new S1S1(mainPtn.getUserMdl().getLang(), ""));

        cb_PwdsChar.removeAllItems();
        Char cher = new Char();
        cher.setP30F2103(ConsCfg.DEF_PWDS_HASH);
        cher.setP30F2104(Lang.getLang(LangRes.P30F7C06, "默认"));
        cher.setP30F2106(ConsCfg.DEF_PWDS_CHAR);
        cb_PwdsChar.addItem(cher);
        UserMdl userMdl = mainPtn.getUserMdl();
        for (Char item : userMdl.getCharMdl().getCharSys())
        {
            cb_PwdsChar.addItem(item);
            if (item.getP30F2103().equals(userMdl.getPwdsKey()))
            {
                cb_PwdsChar.setSelectedItem(item);
            }
        }
        for (Char item : userMdl.getCharMdl().getCharUsr())
        {
            cb_PwdsChar.addItem(item);
            if (item.getP30F2103().equals(userMdl.getPwdsKey()))
            {
                cb_PwdsChar.setSelectedItem(item);
            }
        }

        tf_PwdsSize.setText(userMdl.getPwdsLen());
        ck_PwdsLoop.setSelected(userMdl.isPwdsLoop());

        tf_BackCount.setText("" + userMdl.getDumpCnt());
        tf_BackPath.setText(userMdl.getDumpDir());

        tf_StayTime.setText("" + userMdl.getClipDlt());
    }

    @Override
    public void showData()
    {
    }

    @Override
    public void saveData()
    {
        UserMdl userMdl = mainPtn.getUserMdl();
        Object obj = cb_UserLang.getSelectedItem();
        if (obj != null && obj instanceof S1S1)
        {
            userMdl.setLang(((S1S1) obj).getK());
        }

        obj = cb_PwdsChar.getSelectedItem();
        if (obj != null && obj instanceof Char)
        {
            userMdl.setPwdsKey(((Char) obj).getP30F2103());
        }

        String txt = tf_PwdsSize.getText().trim();
        if (com.magicpwd._util.Char.isValidatePositiveInteger(txt))
        {
            userMdl.setPwdsLen(Integer.parseInt(txt));
        }
        userMdl.setPwdsLoop(ck_PwdsLoop.isSelected());

        txt = tf_BackCount.getText().trim();
        if (com.magicpwd._util.Char.isValidatePositiveInteger(txt))
        {
            userMdl.setDumpCnt(Integer.parseInt(txt));
        }

        txt = tf_BackPath.getText();
        java.io.File file = new java.io.File(txt);
        if (file.exists() && file.isDirectory() && file.canWrite())
        {
            userMdl.setDumpDir(txt);
        }

        txt = tf_StayTime.getText().trim();
        if (com.magicpwd._util.Char.isValidatePositiveInteger(txt))
        {
            userMdl.setClipDlt(Integer.parseInt(txt));
        }
    }

    @Override
    public javax.swing.JPanel getPanel()
    {
        return this;
    }

    private void bt_BackPathActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (backPath == null)
        {
            if (com.magicpwd._util.Char.isValidate(tf_BackPath.getText()))
            {
                backPath = new java.io.File(tf_BackPath.getText());
            }
            else
            {
                backPath = new java.io.File(ConsCfg.DEF_DUMP_PATH);
            }
        }
        javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
        jfc.setMultiSelectionEnabled(false);
        jfc.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        jfc.setSelectedFile(backPath);
        int status = jfc.showOpenDialog(this);
        if (status != javax.swing.JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        backPath = jfc.getSelectedFile();
        if (!backPath.exists())
        {
            Lang.showMesg(this, LangRes.P30F7A1B, "您选择的目录不存在！");
            return;
        }
        if (!backPath.isDirectory())
        {
            Lang.showMesg(this, LangRes.P30F7A1C, "请选择一个合适的目录！");
            return;
        }
        if (!backPath.canWrite())
        {
            Lang.showMesg(this, LangRes.P30F7A1D, "无法保存数据到您选择的目录，请确认您是否有足够的权限！");
            return;
        }

        tf_BackPath.setText(backPath.getPath());
    }
    private javax.swing.JButton bt_BackPath;
    private javax.swing.JComboBox cb_PwdsChar;
    private javax.swing.JCheckBox ck_PwdsLoop;
    private javax.swing.JComboBox cb_UserLang;
    private javax.swing.JLabel lb_BackCount;
    private javax.swing.JLabel lb_BackPath;
    private javax.swing.JLabel lb_PwdsChar;
    private javax.swing.JLabel lb_PwdsSize;
    private javax.swing.JLabel lb_StayTime;
    private javax.swing.JLabel lb_UserLang;
    private javax.swing.JTextField tf_BackCount;
    private javax.swing.JTextField tf_BackPath;
    private javax.swing.JTextField tf_PwdsSize;
    private javax.swing.JTextField tf_StayTime;
}
