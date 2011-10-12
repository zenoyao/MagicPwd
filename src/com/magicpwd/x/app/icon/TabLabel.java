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
package com.magicpwd.x.app.icon;

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
class TabLabel extends javax.swing.JLabel
{

    private static java.awt.Font selFont;
    private static java.awt.Font defFont;
    private String key;
    private boolean selected;

    TabLabel(String key, String text)
    {
        super(text);
        this.key = key;
        setForeground(java.awt.Color.darkGray);
        setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        setOpaque(true);
    }

    public void setRollover(boolean rollover)
    {
        if (rollover)
        {
            setForeground(java.awt.Color.blue);
            setFont(selFont);
        }
        else
        {
            if (selected)
            {
                setForeground(java.awt.Color.red);
            }
            else
            {
                setForeground(java.awt.Color.darkGray);
            }
            setFont(defFont);
        }
    }

    public void setSelected(boolean selected)
    {
        if (selFont == null)
        {
            defFont = getFont();
            selFont = defFont.deriveFont(java.awt.Font.BOLD);
        }

        if (selected)
        {
            setFont(selFont);
            setForeground(java.awt.Color.red);
            setBackground(java.awt.Color.lightGray);
        }
        else
        {
            setFont(defFont);
            setForeground(java.awt.Color.darkGray);
            setBackground(null);
        }
        this.selected = selected;
    }

    /**
     * @return the key
     */
    public String getKey()
    {
        return key;
    }
}
