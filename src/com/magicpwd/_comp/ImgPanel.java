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
package com.magicpwd._comp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Amon
 */
public class ImgPanel extends java.awt.Canvas
{

    private boolean showPLine;
    private boolean showMLine;
    private Point point;
    private Paint paint;
    private BufferedImage srcImage;
    private BufferedImage dstImage;

    public ImgPanel()
    {
        this.addMouseListener(new java.awt.event.MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                paint(e.getPoint());
                update();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
            }
        });
        this.addMouseMotionListener(new java.awt.event.MouseMotionListener()
        {

            @Override
            public void mouseDragged(MouseEvent e)
            {
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                point = e.getPoint();
                update();
            }
        });
    }

    @Override
    public void paint(Graphics g)
    {
        if (g == null)
        {
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (dstImage != null)
        {
            g2d.drawImage(dstImage, 0, 0, this);
        }

        if (showMLine && point != null)
        {
            g2d.setPaint(Color.green);
            g2d.drawLine(0, point.y, this.getWidth(), point.y);
            g2d.drawLine(point.x, 0, point.x, this.getHeight());
        }
    }

    private void paint(Point point)
    {
        if (showPLine)
        {
            Graphics2D g2d = dstImage.createGraphics();
            g2d.setPaint(Color.magenta);
            g2d.drawLine(0, point.y, this.getWidth(), point.y);
            g2d.drawLine(point.x, 0, point.x, this.getHeight());
            g2d.dispose();
        }
    }

    public void clearLine()
    {
        resize();
        update();
    }

    private void update()
    {
        paint(getGraphics());
    }

    public void resize()
    {
        if (srcImage == null)
        {
            return;
        }

        // 计算大小
        int cw = this.getWidth();
        int ch = this.getHeight();
        int iw = srcImage.getWidth();
        int ih = srcImage.getHeight();
        if (cw < 1 || ch < 1 || iw < 1 || ih < 1)
        {
            return;
        }

        // 缩放处理
        if (cw < iw || ch < ih)
        {
            double dw = (double) cw / iw;
            double dh = (double) ch / ih;
            double r = dw < dh ? dw : dh;
            iw = (int) (iw * r);
            ih = (int) (ih * r);
        }

        if (paint == null)
        {
            paint = Color.white;
        }

        dstImage = new BufferedImage(cw, ch, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dstImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // 绘制背景
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, cw, ch);

        // 绘制图片
        g2d.drawImage(srcImage, (cw - iw) >> 1, (ch - ih) >> 1, iw, ih, this);
        g2d.dispose();
    }

    /**
     * @return the image
     */
    public BufferedImage getImage()
    {
        return srcImage;
    }

    /**
     * @param image the image to set
     */
    public void setImage(BufferedImage image)
    {
        this.srcImage = image;
//        resize();
//        update();
    }

    /**
     * @return the showPLine
     */
    public boolean isShowPLine()
    {
        return showPLine;
    }

    /**
     * @param showPLine the showPLine to set
     */
    public void setShowPLine(boolean showPLine)
    {
        this.showPLine = showPLine;
    }

    /**
     * @return the showMLine
     */
    public boolean isShowMLine()
    {
        return showMLine;
    }

    /**
     * @param showMLine the showMLine to set
     */
    public void setShowMLine(boolean showMLine)
    {
        this.showMLine = showMLine;
    }
}
