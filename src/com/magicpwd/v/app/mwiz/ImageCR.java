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
package com.magicpwd.v.app.mwiz;

import com.magicpwd._comn.I1S2;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-11-21 10:04:36
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class ImageCR extends javax.swing.JPanel implements javax.swing.table.TableCellRenderer
{

    private MwizPtn normPtn;

    public ImageCR(MwizPtn normPtn)
    {
        this.normPtn = normPtn;
        this.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 0));

        label = new javax.swing.JLabel();
        add(label);
        major = new javax.swing.JLabel();
        add(major);
    }

    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        setFont(table.getFont());
        setEnabled(table.isEnabled());

        this.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        this.setFocusable(hasFocus);

        if (value instanceof I1S2)
        {
            I1S2 item = (I1S2) value;
            label.setIcon(normPtn.getFeelIcon(item.getV(), "var:" + item.getV()));
            major.setIcon(normPtn.getFeelIcon(item.getV2(), "var:" + item.getV2()));
        }
        return this;
    }
    private javax.swing.JLabel label;
    private javax.swing.JLabel major;
}
