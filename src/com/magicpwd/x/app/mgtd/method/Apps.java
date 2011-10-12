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
import com.magicpwd._comp.BtnLabel;
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
public class Apps extends javax.swing.JPanel implements IMgtdBean
{

    private MgtdDlg mgtdDlg;

    public Apps(MgtdDlg mgtdDlg)
    {
        this.mgtdDlg = mgtdDlg;
    }

    @Override
    public void initView()
    {
        btPath = new BtnLabel();
        btPath.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                btPathActionPerformed(e);
            }
        });
        tfPath = new javax.swing.JTextField();
        lbPath = new javax.swing.JLabel();
        tfArgs = new javax.swing.JTextField();
        lbArgs = new javax.swing.JLabel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(tfPath, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(btPath, 21, 21, 21);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lbArgs, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lbPath, javax.swing.GroupLayout.Alignment.TRAILING);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addGroup(hsg1);
        hpg2.addComponent(tfArgs, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
//        hsg2.addContainerGap();
        hsg2.addGroup(hpg1);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addGroup(hpg2);
//        hsg2.addContainerGap();
        layout.setHorizontalGroup(hsg2);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lbPath);
        vpg1.addComponent(tfPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg1.addComponent(btPath, 21, 21, 21);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lbArgs);
        vpg2.addComponent(tfArgs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
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
        lbPath.setText("程序路径");
        lbArgs.setText("执行参数");
    }

    @Override
    public int getKey()
    {
        return ConsDat.MGTD_METHOD_APPS;
    }

    @Override
    public String getName()
    {
        return "apps";
    }

    @Override
    public String getTitle()
    {
        return "操行程序";
    }

    @Override
    public boolean showData(MgtdHeader mgtd)
    {
        tfPath.setText(mgtd.getP30F0310());
        tfArgs.setText(mgtd.getP30F0311());
        return true;
    }

    @Override
    public boolean saveData(MgtdHeader mgtd)
    {
        String text = tfPath.getText();
        if (!Char.isValidate(text))
        {
            Lang.showMesg(mgtdDlg, null, "请输入或选择您要打开的文件！");
            tfPath.requestFocus();
            return false;
        }
        java.io.File file = new java.io.File(text);
        if (!file.exists())
        {
            Lang.showMesg(mgtdDlg, null, "您输入或选择的文件不存在！");
            tfPath.requestFocus();
            return false;
        }
        if (!file.canRead())
        {
            Lang.showMesg(mgtdDlg, null, "无法访问您输入或选择的文件！");
            tfPath.requestFocus();
            return false;
        }

        mgtd.setP30F030F(text);
        mgtd.setP30F0311(tfArgs.getText());
        return true;
    }

    private void btPathActionPerformed(java.awt.event.ActionEvent e)
    {
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        fc.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        java.io.File file = null;
        if (Char.isValidate(tfPath.getText()))
        {
            file = new java.io.File(tfPath.getText().trim());
            fc.setSelectedFile(file);
        }
        int status = fc.showOpenDialog(mgtdDlg);
        if (status != javax.swing.JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        file = fc.getSelectedFile();
        if (file != null)
        {
            tfPath.setText(file.getAbsolutePath());
        }
    }
    private BtnLabel btPath;
    private javax.swing.JLabel lbArgs;
    private javax.swing.JLabel lbPath;
    private javax.swing.JTextField tfArgs;
    private javax.swing.JTextField tfPath;
}
