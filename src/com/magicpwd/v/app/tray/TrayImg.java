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

import com.magicpwd._cons.ConsEnv;
import com.magicpwd._enum.AppView;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd.m.UserMdl;

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
public class TrayImg extends java.awt.Canvas
{

    /**
     * 窗口大小
     */
    private int wndMaxW;
    private int wndMaxH;
    /**
     * 默认图标大小
     */
    private int imgDefW;
    private int imgDefH;
    /**
     * 选中图标大小
     */
    private int imgSelW;
    private int imgSelH;
    private int imgSelP;
    /**
     * 背景图像偏移值
     */
    private int wndOffX = 8;
    private int wndOffY = 8;
    private int imgOffX;
    private int imgOffY;
    private java.awt.image.BufferedImage minBg;//默认背景
    private java.awt.image.BufferedImage maxBg;//导航背景
    private java.awt.image.BufferedImage bufBg;//缓冲背景
    private java.util.HashMap<String, java.awt.image.BufferedImage> imgBg;
    private String loc;
    private java.util.HashMap<String, AppView> locApp;
    private java.util.HashMap<String, java.awt.image.BufferedImage> locImg;
    private TrayPtn trayPtn;
    private UserMdl userMdl;

    public TrayImg(TrayPtn trayPtn, UserMdl userMdl)
    {
        this.trayPtn = trayPtn;
        this.userMdl = userMdl;
    }

    public void init()
    {
        java.util.Properties prop = trayPtn.getTrayFav();

        // 窗口大小
        wndMaxW = getInt(prop.getProperty("wnd.max.width"), 64);
        wndMaxH = getInt(prop.getProperty("wnd.max.height"), 64);

        // 导航窗口背景
        maxBg = userMdl.readFeelImage(ConsEnv.FEEL_PATH + prop.getProperty("bg.image", "guid.png"));
        minBg = Bean.getLogo(24);

        wndOffX = getInt(prop.getProperty("bg.offset.x"), 0);
        wndOffY = getInt(prop.getProperty("bg.offset.y"), 0);

        // 默认图像大小
        imgDefW = getInt(prop.getProperty("img.default.width"), 16);
        imgDefH = getInt(prop.getProperty("img.default.width"), 16);

        // 鼠标经过特效
        imgSelW = getInt(prop.getProperty("img.selected.width"), 24);
        imgSelH = getInt(prop.getProperty("img.selected.height"), 24);
        imgSelP = getInt(prop.getProperty("img.selected.padding"), 24);

        imgOffX = (imgSelW - imgDefW) >> 1;
        imgOffY = (imgSelH - imgDefH) >> 1;

        imgBg = new java.util.HashMap<String, java.awt.image.BufferedImage>();
        locApp = new java.util.HashMap<String, AppView>();
        locImg = new java.util.HashMap<String, java.awt.image.BufferedImage>();

        String tmp;
        String key;
        AppView view;
        for (int y = 0; y < 3; y += 1)
        {
            for (int x = 0; x < 3; x += 1)
            {
                key = x + "," + y;

                // 背景
                tmp = prop.getProperty("cell[" + key + "].clip");
                if (!Char.isValidate(tmp))
                {
                    continue;
                }
                imgBg.put(key, userMdl.readFeelImage(ConsEnv.FEEL_PATH + tmp));

                // 功能
                tmp = prop.getProperty("cell[" + key + "].view");
                if (!Char.isValidate(tmp))
                {
                    continue;
                }
                view = AppView.valueOf(tmp);
                if (view == null)
                {
                    continue;
                }
                locApp.put(key, view);

                // 图标
                tmp = prop.getProperty("cell[" + key + "].icon");
                if (!Char.isValidate(tmp))
                {
                    continue;
                }
                locImg.put(key, userMdl.readFeelImage(ConsEnv.FEEL_PATH + tmp));
            }
        }

        bufBg = new java.awt.image.BufferedImage(maxBg.getWidth(), maxBg.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2d = bufBg.createGraphics();
        g2d.setColor(new java.awt.Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, bufBg.getWidth(), bufBg.getHeight());
        g2d.drawImage(maxBg, 0, 0, this);
        g2d.drawImage(minBg, (wndMaxW - imgSelW) >> 1, (wndMaxH - imgSelH) >> 1, this);
        g2d.dispose();
    }

    @Override
    public void paint(java.awt.Graphics g)
    {
        if (bufBg != null)
        {
            g.drawImage(bufBg, 0, 0, this);
        }
    }

    @Override
    public void update(java.awt.Graphics g)
    {
        paint(g);
    }

    public void setBackgroud(java.awt.image.BufferedImage image)
    {
        this.bgImage = image;
    }

    public AppView getAppView()
    {
        return locApp.get(loc);
    }

    public void deActive()
    {
        java.awt.Graphics2D g2d = bufBg.createGraphics();
        g2d.drawImage(maxBg, 0, 0, this);
        g2d.drawImage(minBg, (wndMaxW - imgSelW) >> 1, (wndMaxH - imgSelH) >> 1, this);

        paint(getGraphics());
    }

    public void enActive(java.awt.Point p)
    {
        java.awt.Graphics2D g2d = bufBg.createGraphics();
        if (bgImage != null)
        {
            g2d.drawImage(bgImage, 0, 0, this);
        }
        else
        {
            g2d.setColor(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
            g2d.fillRect(0, 0, wndMaxW, wndMaxH);
        }
        g2d.drawImage(maxBg, 0, 0, this);
        for (String key : locImg.keySet())
        {
            String[] arr = key.split(",");
            g2d.drawImage(locImg.get(key), wndOffX + Integer.parseInt(arr[0]) * imgDefW, wndOffY + Integer.parseInt(arr[1]) * imgDefW, this);
        }

        if (p.x > wndOffX && p.x < wndMaxW - wndOffX && p.y > wndOffY && p.y < wndMaxH - wndOffY)
        {
            int x = x2GridIndex(p.x);
            int y = y2GridIndex(p.y);
            loc = x + "," + y;
            java.awt.image.BufferedImage bi = imgBg.get(loc);

            x = x2GridPoint(p.x);
            y = y2GridPoint(p.y);
            if (x <= wndOffX)
            {
                x -= imgSelP;
            }
            else
            {
                x -= imgOffX;
                x -= imgSelP;
                if (x + imgSelW > wndMaxW - wndOffX)
                {
                    x -= imgOffX;
                }
            }
            if (y <= wndOffY)
            {
                y -= imgSelP;
            }
            else
            {
                y -= imgOffY;
                y -= imgSelP;
                if (y + imgSelH > wndMaxH - wndOffY)
                {
                    y -= imgOffY;
                }
            }
            if (locImg.containsKey(loc))
            {
                g2d.drawImage(bi, x, y, this);
                g2d.drawImage(locImg.get(loc), x + imgOffX + imgSelP, y + imgOffY + imgSelP, this);
            }
        }
        g2d.dispose();

        paint(getGraphics());
    }

    private int getInt(String txt, int def)
    {
        if (Char.isValidateInteger(txt))
        {
            return Integer.parseInt(txt, 10);
        }
        return def;
    }

    private int x2GridIndex(int x)
    {
        if (x - wndOffX < imgDefW)
        {
            return 0;
        }
        if (x + imgDefW >= wndMaxW - wndOffX)
        {
            return 2;
        }
        return 1;
    }

    private int x2GridPoint(int x)
    {
        x -= wndOffX;
        return x - x % imgDefW + wndOffX;
    }

    private int y2GridIndex(int y)
    {
        if (y - wndOffY < imgDefH)
        {
            return 0;
        }
        if (y + imgDefW >= wndMaxH - wndOffY)
        {
            return 2;
        }
        return 1;
    }

    private int y2GridPoint(int y)
    {
        y -= wndOffY;
        return y - y % imgDefH + wndOffY;
    }
    private java.awt.image.BufferedImage bgImage;
}
