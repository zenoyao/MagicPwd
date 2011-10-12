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
package com.magicpwd._comp;

import com.magicpwd._util.Desk;

/**
 * @author Amon
 * 
 */
public class LnkLabel extends javax.swing.JLabel
{

    public LnkLabel()
    {
        setForeground(java.awt.Color.BLUE);
        setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    /**
     * @return the linkUrl
     */
    public String getLinkUrl()
    {
        return linkUrl;
    }

    /**
     * @param linkUrl
     *            the linkUrl to set
     */
    public void setLinkUrl(String linkUrl)
    {
        this.linkUrl = linkUrl;
    }

    /**
     * @param autoOpen
     */
    public void setAutoOpenLink(boolean autoOpen)
    {
        if (autoOpen)
        {
            if (listener == null)
            {
                listener = new java.awt.event.MouseAdapter()
                {

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt)
                    {
                        openLinkUrl(linkUrl);
                    }
                };
            }
            this.addMouseListener(listener);
        }
        else if (listener != null)
        {
            this.removeMouseListener(listener);
        }
    }

    /**
     * @param linkUrl
     * @return
     */
    public boolean openLinkUrl(String linkUrl)
    {
        if (!com.magicpwd._util.Char.isValidate(linkUrl))
        {
            return false;
        }

        if (linkUrl.toLowerCase().startsWith("mailto:"))
        {
            Desk.mail(linkUrl);
        }
        else
        {
            Desk.browse(linkUrl);
        }
        return true;
    }
    /** 链接地址 */
    private String linkUrl;
    /**  */
    private java.awt.event.MouseListener listener;
}
