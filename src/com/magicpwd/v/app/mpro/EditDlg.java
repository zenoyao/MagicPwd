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
package com.magicpwd.v.app.mpro;

import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;

/**
 * 属性编辑独立窗口
 * @author Amon
 */
public class EditDlg extends javax.swing.JDialog
{
    private MproPtn mainPtn;

    public EditDlg(MproPtn mainPtn)
    {
        super(mainPtn);
        this.mainPtn = mainPtn;
    }

    public void initView()
    {
        pl_PropEdit = new javax.swing.JPanel();
        pl_PropEdit.setLayout(new java.awt.BorderLayout());

        addWindowListener(new java.awt.event.WindowAdapter()
        {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                windowClosingEvent(e);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addComponent(pl_PropEdit,
                javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addComponent(pl_PropEdit,
                javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE)));

        setResizable(false);
    }

    public void initLang()
    {
        setTitle(Lang.getLang(LangRes.P30FA208, "友情提示"));
    }

    public void initData()
    {
    }

    public void setPropView(javax.swing.JPanel prop)
    {
        pl_PropEdit.add(prop);

        pack();
        java.awt.Dimension a = getSize();
        java.awt.Dimension b = mainPtn.getSize();
        java.awt.Point p = mainPtn.getLocation();
        setLocation(p.x + b.width, p.y + b.height - a.height);
    }

    private void windowClosingEvent(java.awt.event.WindowEvent evt)
    {
//        menuEvt.viewSideActionPerformed(null);
    }
    private javax.swing.JPanel pl_PropEdit;
}
