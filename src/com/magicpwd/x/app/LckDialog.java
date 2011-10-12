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
package com.magicpwd.x.app;

import com.magicpwd.__a.AMpwdPtn;

/**
 * 运行等待对话框
 * @author Awen
 */
public class LckDialog extends javax.swing.JPanel
{

    public LckDialog(AMpwdPtn form)
    {
    }

    public boolean initView()
    {
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new java.awt.BorderLayout());
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        lb_BusyIcon = new javax.swing.JLabel();
        lb_BusyIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_BusyIcon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        panel.add(lb_BusyIcon, java.awt.BorderLayout.CENTER);

        add(panel, java.awt.BorderLayout.CENTER);
        return true;
    }

    public boolean initLang()
    {
        return true;
    }

    public boolean initData()
    {
        return true;
    }
    private javax.swing.JLabel lb_BusyIcon;
}
