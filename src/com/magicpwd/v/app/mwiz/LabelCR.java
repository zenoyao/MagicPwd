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

import com.magicpwd._comn.S1S3;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-11-21 10:04:43
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class LabelCR extends javax.swing.JPanel implements javax.swing.table.TableCellRenderer
{

    private MwizPtn mwizPtn;

    public LabelCR(MwizPtn normPtn)
    {
        this.mwizPtn = normPtn;
        this.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 0));

        title = new javax.swing.JLabel();
        add(title);
    }

    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        setFont(table.getFont());
        setEnabled(table.isEnabled());

//        if (isSelected)
//        {
        this.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
//            this.setForeground(table.getSelectionForeground());
//        }
//        else
//        {
//            this.setBackground(table.getBackground());
//            this.setForeground(table.getForeground());
//        }

        this.setFocusable(hasFocus);

        if (value instanceof S1S3)
        {
            S1S3 item = (S1S3) value;
            title.setIcon(mwizPtn.getDataIcon(item.getV3(), item.getK(), 16));
            title.setText(item.getV());
            setToolTipText(item.getV2());
        }
        return this;
    }
    private javax.swing.JLabel title;
}
