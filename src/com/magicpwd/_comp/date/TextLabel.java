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
package com.magicpwd._comp.date;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import javax.swing.ImageIcon;

class TextLabel extends javax.swing.JLabel
{

    protected static final ImageIcon bgIcon = new ImageIcon("img" + File.separator + "bg1.png");
    protected static final ImageIcon bgIcon1 = new ImageIcon("img" + File.separator + "bg.png");
    protected static final ImageIcon unbgIcon = new ImageIcon("img" + File.separator + "unbg1.png");
    protected static final ImageIcon unbgIcon1 = new ImageIcon("img" + File.separator + "unbg.png");
    protected int STATE = 0;

    public TextLabel(int horizontalAlignment, int STATE)
    {
        this.STATE = STATE;
        this.setOpaque(false);
        this.setHorizontalAlignment(CENTER);
        Dimension d = new Dimension(21, 21);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);

        this.setForeground(STATE == 1 ? Color.WHITE : Color.decode("#0597DB"));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if (STATE == 0)
        {
            if (isEnabled())
            {
                g.drawImage(unbgIcon.getImage(), 0, 0, null);
            }
            else
            {
                g.drawImage(bgIcon.getImage(), 0, 0, null);
            }
        }
        else
        {
            if (isEnabled())
            {
                g.drawImage(unbgIcon1.getImage(), 0, 0, null);
            }
            else
            {
                g.drawImage(bgIcon1.getImage(), 0, 0, null);
            }
        }
        super.paintComponent(g);
    }
}
