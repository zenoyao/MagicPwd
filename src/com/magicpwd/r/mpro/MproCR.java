/*
 *  Copyright (C) 2011 Amon
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
package com.magicpwd.r.mpro;

import com.magicpwd._cons.ConsEnv;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author Amon
 */
public class MproCR extends javax.swing.table.DefaultTableCellRenderer
{

    private java.awt.Color rowColor;
    private java.awt.Color colColor;

    public MproCR()
    {
        rowColor = new java.awt.Color(240, 240, 240);
        colColor = new java.awt.Color(224, 224, 224);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (column == 0)
        {
            this.setBackground(colColor);
        }
        else if (row < ConsEnv.PWDS_HEAD_SIZE)
        {
            this.setBackground(rowColor);
        }
        else
        {
            this.setBackground(java.awt.Color.white);
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
