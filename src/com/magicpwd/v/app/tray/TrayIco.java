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
package com.magicpwd.v.app.tray;

import com.magicpwd.__i.ITrayView;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Logs;

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
public class TrayIco extends java.awt.TrayIcon implements ITrayView, java.awt.event.MouseListener
{

    private TrayPtn trayPtn;

    TrayIco(TrayPtn trayPtn)
    {
        super(Bean.getLogo(16));
        this.trayPtn = trayPtn;
    }

    public boolean initView()
    {
        trayWnd = new javax.swing.JWindow();
        trayWnd.setAlwaysOnTop(true);

        if (!java.awt.SystemTray.isSupported())
        {
            return false;
        }
        setImageAutoSize(true);
        return true;
    }

    public void initLang()
    {
        setToolTip(ConsEnv.SOFTNAME + ' ' + ConsEnv.VERSIONS);
    }

    public void initData()
    {
        addMouseListener(this);
    }

    @Override
    public void deActive()
    {
    }

    @Override
    public void displayMessage(String title, String message)
    {
        super.displayMessage(title, message, java.awt.TrayIcon.MessageType.INFO);
    }

    @Override
    public void setVisible(boolean visible)
    {
        try
        {
            if (visible)
            {
                java.awt.SystemTray.getSystemTray().add(this);
            }
            else
            {
                java.awt.SystemTray.getSystemTray().remove(this);
            }
        }
        catch (java.awt.AWTException ex)
        {
            Logs.exception(ex);
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e)
    {
        if (e.isPopupTrigger())
        {
            showPopupMenu(e);
        }
        else if (e.getClickCount() > 1)
        {
            trayPtn.showLast();
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e)
    {
        if (e.isPopupTrigger())
        {
            showPopupMenu(e);
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e)
    {
        if (e.isPopupTrigger())
        {
            showPopupMenu(e);
        }
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e)
    {
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e)
    {
    }

    private void showPopupMenu(java.awt.event.MouseEvent evt)
    {
        java.awt.Dimension window = trayPtn.trayMenu.getPreferredSize();
        java.awt.Dimension screan = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = evt.getX();
        if (x + window.width > screan.width && x > window.width)
        {
            x -= window.width;
        }
        int y = evt.getY();
        if (y + window.height > screan.height && y > window.height)
        {
            y -= window.height;
        }
        trayWnd.setLocation(x, y);
        trayWnd.setVisible(true);

        // trayMenu.setInvoker(trayMenu);
        trayPtn.trayMenu.show(trayWnd.getContentPane(), 0, 0);
        trayWnd.toFront();
    }
    private javax.swing.JWindow trayWnd;
}
