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
import com.magicpwd._cons.ConsCfg;
import com.magicpwd._util.Logs;
import com.magicpwd.m.UserMdl;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

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
public class TrayWnd extends javax.swing.JWindow implements ITrayView, java.awt.event.MouseListener, java.awt.event.MouseMotionListener
{

    // 中间变量，用于控制绘制过程
    private int recSize;
    private int wndStep;
    private int arcSize;
    private int arcStep;
    /**
     * 窗口最大宽度
     */
    private int maxWndSize = 64;
    private int maxRecSize = 56;
    /**
     * 窗口最小宽度
     */
    private int minWndSize = 24;
    private int minRecSize = 24;
    private int minArcSize = 1;
    /**
     * 圆角最大宽度
     */
    private int maxArcSize = 8;
    /**
     * 动画时长
     */
    private int aniTime = 100;
    /**
     * 动画侦数
     */
    private int aniStep = 10;
    private int gap = 0;
    private boolean aniDir = true;
    private boolean hasAni = false;
    private javax.swing.Timer timer;
    private TrayPtn trayPtn;
    private UserMdl userMdl;
    private TrayImg trayImg;

    public TrayWnd(TrayPtn trayPtn, UserMdl userMdl)
    {
        this.trayPtn = trayPtn;
        this.userMdl = userMdl;
    }

    public void initView()
    {
        trayImg = new TrayImg(trayPtn, userMdl);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addGap(gap);
        hsg.addComponent(trayImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        hsg.addGap(gap);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addGap(gap);
        vsg.addComponent(trayImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        vsg.addGap(gap);
        layout.setVerticalGroup(vsg);

        setAlwaysOnTop(true);
    }

    public void initLang()
    {
    }

    public void initData()
    {
        trayImg.init();

        java.awt.Dimension size = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        String loc = userMdl.getCfg(ConsCfg.CFG_TRAY_LOC, "");
        if (com.magicpwd._util.Char.isValidate(loc))
        {
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\d+").matcher(loc);
            int x = -1;
            if (matcher.find())
            {
                x = Integer.parseInt(matcher.group());
            }
            int y = -1;
            if (matcher.find())
            {
                y = Integer.parseInt(matcher.group());
            }
            if (x >= 0 && x < size.width && y >= 0 && y < size.height)
            {
                formLoc = new java.awt.Point(x, y);
            }
        }

        if (formLoc == null)
        {
            formLoc = new java.awt.Point(size.width - 120 - getWidth(), 80);
        }

        setSize(maxWndSize, maxWndSize);
        setLocation(formLoc);
        recSize = minWndSize;
        arcSize = minArcSize;
        int p = (maxWndSize - recSize) >> 1;
        com.sun.awt.AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(p, p, recSize, recSize, arcSize, arcSize));

        trayImg.addMouseListener(this);
        trayImg.addMouseMotionListener(this);

        // 矩形步增量
        wndStep = (maxWndSize - minWndSize) / aniStep;
        // 圆角步增量
        arcStep = (maxArcSize - minArcSize) / aniStep;
        timer = new javax.swing.Timer(aniTime / aniStep, new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                timerActionPerformed(e);
            }
        });

        try
        {
            robot = new java.awt.Robot();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
    }

    @Override
    public void deActive()
    {
        aniDir = false;
        if (!timer.isRunning())
        {
            timer.start();
        }

        trayImg.deActive();
    }

    @Override
    public void displayMessage(String title, String message)
    {
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt)
    {
        if (evt.isPopupTrigger())
        {
            Point p = evt.getPoint();
            trayPtn.trayMenu.show(this, p.x, p.y);
        }
        else if (evt.getClickCount() > 1)
        {
            trayPtn.showView(trayImg.getAppView());
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent evt)
    {
        if (evt.isPopupTrigger())
        {
            Point p = evt.getPoint();
            trayPtn.trayMenu.show(this, p.x, p.y);
        }
        dragLoc = getScreenLocation(evt);
        formLoc = getLocation();
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent evt)
    {
        if (evt.isPopupTrigger())
        {
            Point p = evt.getPoint();
            trayPtn.trayMenu.show(this, p.x, p.y);
        }
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt)
    {
        if (!hasAni)
        {
            return;
        }

        trayImg.setBackgroud(robot.createScreenCapture(this.getBounds()));

        aniDir = true;
        if (!timer.isRunning())
        {
            timer.start();
        }

        trayImg.enActive(evt.getPoint());
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt)
    {
        if (trayPtn.trayMenu.isVisible())
        {
            return;
        }

        if (!hasAni)
        {
            return;
        }

        aniDir = false;
        if (!timer.isRunning())
        {
            timer.start();
        }

        trayImg.deActive();
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent evt)
    {
        java.awt.Point tmp = getScreenLocation(evt);
        tmp.x += formLoc.x - dragLoc.x;
        tmp.y += formLoc.y - dragLoc.y;
        java.awt.Dimension scrSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dlgSize = getSize();
        if (tmp.x < 10)
        {
            tmp.x = 1;
        }
        if (tmp.y < 10)
        {
            tmp.y = 1;
        }
        int x = scrSize.width - dlgSize.width;
        if (tmp.x + 10 > x)
        {
            tmp.x = x;
        }
        int y = scrSize.height - dlgSize.height;
        if (tmp.y + 10 > y)
        {
            tmp.y = y;
        }
        setLocation(tmp);
        userMdl.setCfg(ConsCfg.CFG_TRAY_LOC, "[" + tmp.x + "," + tmp.y + "]");
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent evt)
    {
        if (!hasAni)
        {
            return;
        }

        trayImg.enActive(evt.getPoint());
    }

    @Override
    protected void processWindowEvent(java.awt.event.WindowEvent evt)
    {
        if (evt.getID() == java.awt.event.WindowEvent.WINDOW_CLOSING)
        {
            return;
        }
        if (evt.getID() == java.awt.event.WindowEvent.WINDOW_DEACTIVATED)
        {
            setVisible(false);
        }
        super.processWindowEvent(evt);
    }

    private void timerActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (aniDir)
        {
            if (recSize <= maxRecSize)
            {
                recSize += wndStep;
                arcSize += arcStep;
                int p = (maxWndSize - recSize) >> 1;
                com.sun.awt.AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(p, p, recSize, recSize, arcSize, arcSize));
                return;
            }

            recSize = maxRecSize;
            arcSize = maxArcSize;
            int p = (maxWndSize - recSize) >> 1;
            com.sun.awt.AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(p, p, recSize, recSize, arcSize, arcSize));
            timer.stop();
            return;
        }

        if (recSize >= minRecSize)
        {
            recSize -= wndStep;
            arcSize -= arcStep;
            int p = (maxWndSize - recSize) >> 1;
            com.sun.awt.AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(p, p, recSize, recSize, arcSize, arcSize));
            return;
        }

        recSize = minRecSize;
        arcSize = minArcSize;
        int p = (maxWndSize - recSize) >> 1;
        com.sun.awt.AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(p, p, recSize, recSize, arcSize, arcSize));
        timer.stop();
    }

    private java.awt.Point getScreenLocation(java.awt.event.MouseEvent evt)
    {
        java.awt.Point cur = evt.getPoint();
        java.awt.Point dlg = getLocationOnScreen();
        return new java.awt.Point(dlg.x + cur.x, dlg.y + cur.y);
    }
    private java.awt.Point dragLoc;
    private java.awt.Point formLoc;
    private java.awt.Robot robot;
}
