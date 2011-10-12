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
package com.magicpwd.r;

/**
 *
 * @author Amon
 */
public class MailCR implements javax.swing.table.TableCellRenderer
{

    public MailCR()
    {
    }

    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        javax.swing.JLabel label;
        if (value instanceof javax.swing.JLabel)
        {
            label = (javax.swing.JLabel) value;
        }
        else
        {
            label = new javax.swing.JLabel(value.toString());
        }

        label.setOpaque(true);
        // 前景及背景颜色设置
        if (isSelected)
        {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        }
        else
        {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
        }
        return label;
    }
}
