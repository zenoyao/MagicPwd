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
public class File extends javax.swing.JPanel implements IMgtdBean
{

    private MgtdDlg mgtdDlg;

    public File(MgtdDlg mgtdDlg)
    {
        this.mgtdDlg = mgtdDlg;
    }

    @Override
    public void initView()
    {
        btFile = new BtnLabel();
        btFile.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                btFileActionPerformed(e);
            }
        });
        tfFile = new javax.swing.JTextField();
        lbFile = new javax.swing.JLabel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
//        hsg.addContainerGap();
        hsg.addComponent(lbFile);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(tfFile, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(btFile, 21, 21, 21);
//        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg.addComponent(lbFile);
        vpg.addComponent(tfFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg.addComponent(btFile, 21, 21, 21);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
//        vsg.addContainerGap();
        vsg.addGroup(vpg);
//        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);
    }

    @Override
    public void initLang()
    {
        lbFile.setText("文件路径");
    }

    @Override
    public int getKey()
    {
        return ConsDat.MGTD_METHOD_FILE;
    }

    @Override
    public String getName()
    {
        return "file";
    }

    @Override
    public String getTitle()
    {
        return "打开文件";
    }

    @Override
    public boolean showData(MgtdHeader mgtd)
    {
        tfFile.setText(mgtd.getP30F0310());
        return true;
    }

    @Override
    public boolean saveData(MgtdHeader mgtd)
    {
        String text = tfFile.getText();
        if (!Char.isValidate(text))
        {
            Lang.showMesg(mgtdDlg, null, "请输入或选择您要打开的文件！");
            tfFile.requestFocus();
            return false;
        }
        java.io.File file = new java.io.File(text);
        if (!file.exists())
        {
            Lang.showMesg(mgtdDlg, null, "您输入或选择的文件不存在！");
            tfFile.requestFocus();
            return false;
        }
        if (!file.canRead())
        {
            Lang.showMesg(mgtdDlg, null, "无法访问您输入或选择的文件！");
            tfFile.requestFocus();
            return false;
        }

        mgtd.setP30F030F(text);
        return true;
    }

    private void btFileActionPerformed(java.awt.event.ActionEvent e)
    {
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        fc.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        java.io.File file = null;
        if (Char.isValidate(tfFile.getText()))
        {
            file = new java.io.File(tfFile.getText().trim());
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
            tfFile.setText(file.getAbsolutePath());
        }
    }
    private BtnLabel btFile;
    private javax.swing.JLabel lbFile;
    private javax.swing.JTextField tfFile;
}
