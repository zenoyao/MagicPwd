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
package com.magicpwd.m.mail;

import com.magicpwd._cons.ConsEnv;
import com.magicpwd._util.Bean;
import com.magicpwd.m.UserMdl;

/**
 * @author Amon
 * 
 */
public class MailMdl extends javax.swing.table.AbstractTableModel
{

    private java.util.ArrayList<Reader> messages;
    private java.text.SimpleDateFormat format;
    private UserMdl userMdl;

    public MailMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
        messages = new java.util.ArrayList<Reader>();
        format = new java.text.SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public int getColumnCount()
    {
        return 4;
    }

    @Override
    public int getRowCount()
    {
        return messages.size();
    }

    @Override
    public Class getColumnClass(int columnIndex)
    {
        return javax.swing.JLabel.class;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return "";
            case 1:
                return "发送人";
            case 2:
                return "标题";
            case 3:
                return "时间";
            default:
                return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (rowIndex < 0 || rowIndex >= messages.size())
        {
            return null;
        }

        javax.swing.JLabel label = new javax.swing.JLabel();
        Reader reader = messages.get(rowIndex);
        if (!reader.isReaded())
        {
            label.setFont(label.getFont().deriveFont(java.awt.Font.BOLD));
        }

        switch (columnIndex)
        {
            case 0:
                label.setIcon(reader.hasAttachment() ? userMdl.readFeelIcon(ConsEnv.FEEL_PATH + "mail-attach.png") : Bean.getNone());
                break;
            case 1:
                label.setText(reader.getFrom());
                break;
            case 2:
                label.setText(reader.getSubject());
                break;
            case 3:
                label.setText(format.format(reader.getSentDate()));
                break;
            default:
                break;
        }
        return label;
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return false;
    }

    public Reader getMailInf(int rowIndex)
    {
        if (rowIndex < 0 || rowIndex >= messages.size())
        {
            return null;
        }
        return messages.get(rowIndex);
    }

    public void clear()
    {
        messages.clear();
    }

    public void append(Reader mailInf)
    {
        messages.add(mailInf);
        fireTableDataChanged();
    }
}
