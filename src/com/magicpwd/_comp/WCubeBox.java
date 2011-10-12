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
import com.magicpwd._util.Logs;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author Amon
 */
public class WCubeBox extends java.awt.Canvas implements Runnable
{

    private Graphics graphics;
    private BufferedImage backImg;
    private BufferedImage cubeImg;
    private int width = 100;
    private int height = 100;
    private int J;
    private int K;
    private int L;
    private int M;
    private String[] pUrls;
    private long O;
    private long P;
    private int[] Q;
    private int[] S;
    private int[] T;
    private int[] U;
    private int[] V;
    private int[] W;
    private int[] X;
    private int[] Y =
    {
        3, 2, 1, 0, -1, 4, 5, 6, 7, -1, 0, 1, 5, 4, -1, 2, 3, 7, 6, -1, 1, 2, 6, 5, -1, 0, 4, 7, 3, -1, -1
    };
    private double[] Z;
    private double[] a;
    private int sleepTime;
    private int d;
    private int e;
    private int f;
    private int g;
    private double k;
    private double l;
    private double m;
    private double n;
    private double o;
    private double p;
    private double q;
    private double r;
    private double s;
    private double t;
    private double u;
    private double v;
    private double w;
    private double x;
    private double mouseResponse;
    private double zoomSpeed;
    private double angleStep;
    private double Ä;
    private double Å;
    private double Æ;
    private double Ç;
    private double È;
    private double É;
    private double Ê;
    private double Ë;
    private double Í;
    private double Î;
    private double Ï;
    private double Ð;
    private double Ñ;
    private double Ò;
    private java.awt.Color backGround;
    private java.awt.Color shadowColor;
    private int textColor;
    private int Ö;
    private int size;
    private int cubeLen;
    private int Ú;
    private int Û;
    private int Ü;
    private int[] Þ;
    private int[] ß;
    private boolean showLightButton;
    private boolean spotLight;
    private boolean firstRun;
    private boolean â;
    private boolean ä;
    private boolean needDraw;
    private boolean mouseEntered;
    private boolean ç;
    private boolean è;
    private boolean é;
    private boolean running;

    public void initData()
    {
        this.backGround = javax.swing.UIManager.getDefaults().getColor("control");
        this.shadowColor = backGround;
        this.textColor = 0x000000;
        this.spotLight = false;
        this.showLightButton = false;
        this.sleepTime = 100;
        this.angleStep = 0.1;
        this.mouseResponse = 0.2;
        this.zoomSpeed = 5.0;
        this.size = 48;
        firstRun = true;

        this.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                _mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                _mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                _mouseExited(e);
            }
        });
        this.addMouseMotionListener(new MouseMotionListener()
        {

            @Override
            public void mouseDragged(MouseEvent e)
            {
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                _mouseMoved(e);
            }
        });

        Dimension dim = this.getPreferredSize();
        this.width = dim.width;
        this.height = dim.height;
        this.size = this.width < this.height ? this.width : this.height;
        this.cubeLen = (this.size / 2);
        this.a = new double[]
                {
                    -cubeLen, -cubeLen, -cubeLen, cubeLen, -cubeLen, -cubeLen, cubeLen, cubeLen,
                    -cubeLen, -cubeLen, cubeLen, -cubeLen, -cubeLen, -cubeLen, cubeLen, cubeLen,
                    -cubeLen, cubeLen, cubeLen, cubeLen, cubeLen, -cubeLen, cubeLen, cubeLen
                };
        this.J = (this.width / 2);
        this.K = (this.height / 2);
        this.S = new int[this.height];
        this.T = new int[this.height];
        this.Q = new int[this.width * this.height];
        this.W = new int[this.width * this.height];
        this.X = new int[this.width * this.height];
        this.ß = new int[this.width];
        Arrays.fill(this.ß, 0xFF000000 + this.shadowColor.getRGB());
        this.V = new int[this.height];
        this.d = 512;
        this.e = 900;
        this.g = 30;
        this.f = (this.a.length / 3);
        this.Z = new double[this.a.length];

        backImg = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        this.graphics = backImg.getGraphics();
        this.graphics.setColor(this.backGround);
        this.graphics.fillRect(0, 0, this.width, this.height);

        if (this.showLightButton)
        {
            loadImg(this.cubeImg);
            this.graphics.drawImage(this.cubeImg, this.width - 18, this.height - 32, this);
        }
        PixelGrabber pg1 = new PixelGrabber(backImg, 0, 0, this.width, this.height, this.W, 0, this.width);
        try
        {
            pg1.grabPixels();
        }
        catch (InterruptedException exp)
        {
            Logs.exception(exp);
        }
        if (this.showLightButton)
        {
            loadImg(this.cubeImg);
            this.graphics.drawImage(this.cubeImg, this.width - 18, this.height - 32, this);
        }
        PixelGrabber pg2 = new PixelGrabber(backImg, 0, 0, this.width, this.height, this.X, 0, this.width);
        try
        {
            pg2.grabPixels();
            return;
        }
        catch (InterruptedException exp)
        {
            Logs.exception(exp);
        }
    }

    private BufferedImage readImg()
    {
        try
        {
            return ImageIO.read(WCubeBox.class.getResourceAsStream("/res/icon/0030.png"));
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return null;
        }
    }

    @Override
    public void run()
    {
        if (firstRun)
        {
            String str;
            FontMetrics metrics = graphics.getFontMetrics();
            this.Þ = new int[this.width * this.height * 6];
            this.needDraw = true;
            for (int i1 = 0; i1 < 6; ++i1)
            {
                graphics.setColor(this.backGround);
                graphics.fillRect(0, 0, this.width, this.height);
                graphics.setColor(new Color(this.textColor));
                str = "Loading Image " + Integer.toString(i1 + 1);
                graphics.drawString(str, this.J - (metrics.stringWidth(str) / 2), this.K);
                update(super.getGraphics());
                this.cubeImg = readImg();
                loadImg(this.cubeImg);
                this.cubeImg = scale(this.cubeImg, this.size, this.size);
                System.arraycopy(this.U, 0, this.Þ, i1 * this.size * this.size, this.size * this.size);
            }
            this.needDraw = false;

            this.Ò = 0.0D;
            this.Ð = 50.0D;
            this.Ñ = 50.0D;
            this.ä = false;
            this.mouseEntered = false;
            this.ç = false;
            this.è = false;
            this.é = false;
            M();
            System.arraycopy(this.a, 0, this.Z, 0, this.a.length);
            this.n = (this.r = this.v = 0.0D);
            this.k = (this.p = this.u = 1.0D);
            this.l = (this.m = this.o = this.q = this.s = this.t = 0.0D);
            this.Ä = (this.Å = this.Æ = 0.0D);
            F();
            this.ç = false;
            this.firstRun = false;
        }

        while (running)
        {
            this.â = this.spotLight;
            this.n = (this.r = this.v = 0.0D);
            if (this.è)
            {
                this.v = 750.0D;
                double d1 = Math.asin(Math.sqrt(this.Ê * this.Ê + this.Ë * this.Ë));
                double d2 = Math.sqrt(this.Ê * this.Ê + this.Ë * this.Ë);
                double d3 = (int) (d1 / this.zoomSpeed);
                for (int i2 = 0; i2 < d3; ++i2)
                {
                    D(-this.Ë / d2, this.Ê / d2, 0.0D, this.zoomSpeed, 1.0D);
                    A();
                    this.needDraw = true;
                    update(this.getGraphics());
                    this.needDraw = false;
                }
                D(-this.Ë / d2, this.Ê / d2, 0.0D, d1 - (d3 * this.zoomSpeed), 1.0D);
                A();
                this.needDraw = true;
                update(getGraphics());
                this.needDraw = false;
                double d4 = Math.acos(this.Í);
                if (this.Î > 0.0D)
                {
                    d4 = -d4;
                }
                int i3 = Math.max((int) (Math.abs(d4) / this.zoomSpeed / 3.0D), 5);
                double d5 = Math.exp(Math.log(1.45D) / i3);
                for (int i4 = 1; i4 <= i3; ++i4)
                {
                    D(0.0D, 0.0D, -1.0D, -d4 / i3, d5);
                    A();
                    this.needDraw = true;
                    update(getGraphics());
                    this.needDraw = false;
                }
                while (!(this.ä))
                {
                    this.needDraw = true;
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException exp)
                    {
                        Logs.exception(exp);
                    }
                }
                for (int i5 = 1; i5 <= i3; ++i5)
                {
                    D(0.0D, 0.0D, -1.0D, d4 / i3, 1.0D / d5);
                    A();
                    this.needDraw = true;
                    update(getGraphics());
                    this.needDraw = false;
                }
                this.v = 750.0D;
                d2 = Math.sqrt(this.Ê * this.Ê + this.Ë * this.Ë);
                for (int i6 = 0; i6 < d3; ++i6)
                {
                    D(-this.Ë / d2, this.Ê / d2, 0.0D, -this.zoomSpeed, 1.0D);
                    A();
                    this.needDraw = true;
                    update(getGraphics());
                    this.needDraw = false;
                }
                D(-this.Ë / d2, this.Ê / d2, 0.0D, -d1 + d3 * this.zoomSpeed, 1.0D);
                A();
                this.needDraw = true;
                update(getGraphics());
                this.needDraw = false;
                this.n = (this.r = this.v = 0.0D);
                this.Ä = this.w;
                this.Å = this.x;
                this.è = false;
            }
            else if (this.mouseEntered)
            {
                this.Ä = this.w;
                this.Å = this.x;
            }
            else
            {
                if (this.ç)
                {
                    this.O = System.currentTimeMillis();
                    this.Ä = (this.Å = this.w = this.x = 0.0D);
                    this.ç = false;
                }
                if (System.currentTimeMillis() - this.O > 5000L)
                {
                    this.Å = this.angleStep;
                    this.Ä = (2.0D * this.angleStep / 3.0D);
                }
            }
            E();
            G();
            this.É = 750.0D;
            J();
            F();
            A();
            this.needDraw = true;
            update(getGraphics());
            this.needDraw = false;
            try
            {
                Thread.sleep(this.sleepTime);
            }
            catch (InterruptedException exp)
            {
                Logs.exception(exp);
            }
        }
    }

    public void A()
    {
        this.backImg.flush();
        this.é = false;
        if (this.â)
        {
            System.arraycopy(this.W, 0, this.Q, 0, this.width * this.height);
        }
        else
        {
            System.arraycopy(this.X, 0, this.Q, 0, this.width * this.height);
        }
        int i1 = 0;
        int[] arrayOfInt1 = new int[this.f];
        int[] arrayOfInt2 = new int[this.f];
        int i2 = 0;
        int i4, i6;
        for (int i3 = 0; i3 < this.f * 3; i3 += 3)
        {
            i4 = (int) this.Z[i3];
            int i5 = (int) this.Z[(i3 + 1)];
            i6 = (int) this.Z[(i3 + 2)];
            arrayOfInt1[i2] = (i4 * this.d / i6 + this.J);
            arrayOfInt2[i2] = (i5 * this.d / i6 + this.K);
            ++i2;
        }
        i4 = 0;
        Polygon localPolygon1 = new Polygon();
        i6 = this.Y[0];
        int i9;
        int i10;
        int i11;
        int i12;
        double d3;
        double d4;
        double d5;
        double d6;
        double d7;
        double d8;
        double d9;
        int i8;
        for (int i7 = 0; i7 < this.g; ++i7)
        {
            i8 = this.Y[i7];
            if (i8 < 0)
            {
                localPolygon1.addPoint(arrayOfInt1[i6], arrayOfInt2[i6]);
                i6 = this.Y[(i7 + 1)];
                i9 = 0;
                i10 = 0;
                i11 = 0;
                i12 = 0;
                switch (i1)
                {
                    case 0:
                        i9 = 0;
                        i10 = 1;
                        i11 = 3;
                        i12 = 2;
                        break;
                    case 1:
                        i9 = 5;
                        i10 = 4;
                        i11 = 6;
                        i12 = 7;
                        break;
                    case 2:
                        i9 = 4;
                        i10 = 5;
                        i11 = 0;
                        i12 = 1;
                        break;
                    case 3:
                        i9 = 3;
                        i10 = 2;
                        i11 = 7;
                        i12 = 6;
                        break;
                    case 4:
                        i9 = 1;
                        i10 = 5;
                        i11 = 2;
                        i12 = 6;
                        break;
                    case 5:
                        i9 = 4;
                        i10 = 0;
                        i11 = 7;
                        i12 = 3;
                }
                Polygon localPolygon2 = new Polygon();
                double d1 = this.e - this.Ò;
                double d2 = (this.Z[(i10 * 3)] - this.Ð) * d1 / (this.Z[(i10 * 3 + 2)] - this.Ò);
                d3 = (this.Z[(i10 * 3 + 1)] - this.Ñ) * d1 / (this.Z[(i10 * 3 + 2)] - this.Ò);
                d4 = (this.Z[(i9 * 3)] - this.Ð) * d1 / (this.Z[(i9 * 3 + 2)] - this.Ò);
                d5 = (this.Z[(i9 * 3 + 1)] - this.Ñ) * d1 / (this.Z[(i9 * 3 + 2)] - this.Ò);
                d6 = (this.Z[(i11 * 3)] - this.Ð) * d1 / (this.Z[(i11 * 3 + 2)] - this.Ò);
                d7 = (this.Z[(i11 * 3 + 1)] - this.Ñ) * d1 / (this.Z[(i11 * 3 + 2)] - this.Ò);
                d8 = (this.Z[(i12 * 3)] - this.Ð) * d1 / (this.Z[(i12 * 3 + 2)] - this.Ò);
                d9 = (this.Z[(i12 * 3 + 1)] - this.Ñ) * d1 / (this.Z[(i12 * 3 + 2)] - this.Ò);
                localPolygon2.addPoint((int) (d2 * this.d / d1 + this.J), (int) (d3 * this.d / d1 + this.J));
                localPolygon2.addPoint((int) (d4 * this.d / d1 + this.J), (int) (d5 * this.d / d1 + this.J));
                localPolygon2.addPoint((int) (d6 * this.d / d1 + this.J), (int) (d7 * this.d / d1 + this.J));
                localPolygon2.addPoint((int) (d8 * this.d / d1 + this.J), (int) (d9 * this.d / d1 + this.J));
                int[] arrayOfInt3 = localPolygon2.ypoints;
                int[] arrayOfInt4 = new int[2];
                arrayOfInt4 = N(localPolygon2.xpoints, localPolygon2.ypoints);
                int i18 = arrayOfInt4[0];
                int i19 = arrayOfInt4[1];
                for (int i20 = Math.max(arrayOfInt3[i19], 0); i20 < Math.min(arrayOfInt3[i18], this.height); ++i20)
                {
                    int i21 = Math.min(this.S[i20], this.T[i20]);
                    int i22 = Math.max(this.S[i20], this.T[i20]);
                    System.arraycopy(this.ß, 0, this.Q, i21 + i20 * this.width, i22 - i21);
                }
                ++i1;
                localPolygon1 = new Polygon();
                i4 = i7 + 1;
            }
            else
            {
                localPolygon1.addPoint(arrayOfInt1[i8], arrayOfInt2[i8]);
            }
        }
        i4 = 0;
        localPolygon1 = new Polygon();
        i6 = this.Y[0];
        i1 = 0;
        for (i8 = 0; i8 < this.g; ++i8)
        {
            i9 = this.Y[i8];
            if (i9 < 0)
            {
                localPolygon1.addPoint(arrayOfInt1[i6], arrayOfInt2[i6]);
                i6 = this.Y[(i8 + 1)];
                i10 = 0;
                i11 = 0;
                i12 = 0;
                int i13 = 0;
                switch (i1)
                {
                    case 0:
                        i10 = 0;
                        i11 = 1;
                        i12 = 3;
                        i13 = 2;
                        break;
                    case 1:
                        i10 = 5;
                        i11 = 4;
                        i12 = 6;
                        i13 = 7;
                        break;
                    case 2:
                        i10 = 4;
                        i11 = 5;
                        i12 = 0;
                        i13 = 1;
                        break;
                    case 3:
                        i10 = 3;
                        i11 = 2;
                        i12 = 7;
                        i13 = 6;
                        break;
                    case 4:
                        i10 = 1;
                        i11 = 5;
                        i12 = 2;
                        i13 = 6;
                        break;
                    case 5:
                        i10 = 4;
                        i11 = 0;
                        i12 = 7;
                        i13 = 3;
                }
                int i14 = arrayOfInt1[i12] - arrayOfInt1[i10];
                int i15 = arrayOfInt2[i12] - arrayOfInt2[i10];
                int i16 = arrayOfInt1[i11] - arrayOfInt1[i10];
                int i17 = arrayOfInt2[i11] - arrayOfInt2[i10];
                if (i14 * i17 - (i15 * i16) <= 0)
                {
                    d3 = (this.Z[(i11 * 3)] - this.Z[(i10 * 3)]) / this.size;
                    d4 = (this.Z[(i11 * 3 + 1)] - this.Z[(i10 * 3 + 1)]) / this.size;
                    d5 = (this.Z[(i11 * 3 + 2)] - this.Z[(i10 * 3 + 2)]) / this.size;
                    d6 = (this.Z[(i12 * 3)] - this.Z[(i10 * 3)]) / this.size;
                    d7 = (this.Z[(i12 * 3 + 1)] - this.Z[(i10 * 3 + 1)]) / this.size;
                    d8 = (this.Z[(i12 * 3 + 2)] - this.Z[(i10 * 3 + 2)]) / this.size;
                    d9 = this.Z[(i10 * 3)];
                    double d10 = this.Z[(i10 * 3 + 1)];
                    double d11 = this.Z[(i10 * 3 + 2)];
                    double d12 = d7 * d5 - (d8 * d4);
                    double d13 = d8 * d3 - (d6 * d5);
                    double d14 = d6 * d4 - (d7 * d3);
                    double d15 = Math.sqrt(d12 * d12 + d13 * d13 + d14 * d14);
                    d12 /= d15;
                    d13 /= d15;
                    d14 /= d15;
                    double d16 = this.Ú - (d9 + this.cubeLen * d3 + this.cubeLen * d6);
                    double d17 = this.Û - (d10 + this.cubeLen * d4 + this.cubeLen * d7);
                    double d18 = this.Ü - (d11 + this.cubeLen * d5 + this.cubeLen * d8);
                    i2 = i4;
                    localPolygon1 = new Polygon();
                    int i23 = this.Y[i2];
                    do
                    {
                        localPolygon1.addPoint(arrayOfInt1[i23], arrayOfInt2[i23]);
                        i23 = this.Y[(++i2)];
                    }
                    while (i23 >= 0);
                    double d19 = (d16 * d12 + d17 * d13 + d18 * d14) / Math.sqrt(d16 * d16 + d17 * d17 + d18 * d18);
                    int i24 = ((this.â & !(this.è))) ? 255 : Math.max(Math.min((int) (255.0D * d19 * 0.9D + 25.5D), 255), 20);
                    int[] arrayOfInt5 = new int[2];
                    arrayOfInt5 = N(localPolygon1.xpoints, localPolygon1.ypoints);
                    int i25 = Math.min(localPolygon1.ypoints[arrayOfInt5[0]], this.height);
                    int i26 = Math.max(localPolygon1.ypoints[arrayOfInt5[1]], 0);
                    double d20 = this.d * (d3 * d11 - (d5 * d9));
                    double d21 = this.d * (d5 * d6 - (d3 * d8));
                    double d22 = this.d * (d5 * d10 - (d4 * d11));
                    double d23 = this.d * (d4 * d8 - (d5 * d7));
                    double d24 = this.d * this.d * (d4 * d9 - (d3 * d10));
                    double d25 = this.d * this.d * (d3 * d7 - (d4 * d6));
                    double d26 = this.d * (d6 * d11 - (d8 * d9));
                    double d27 = this.d * (d8 * d3 - (d6 * d5));
                    double d28 = this.d * (d8 * d10 - (d7 * d11));
                    double d29 = this.d * (d7 * d5 - (d8 * d4));
                    double d30 = this.d * this.d * (d7 * d9 - (d6 * d10));
                    double d31 = this.d * this.d * (d6 * d4 - (d7 * d3));
                    int i27 = this.width;
                    int i28 = i1 * this.size * this.size;
                    double d34;
                    double d35;
                    double d32;
                    double d33;
                    double d38;
                    double d40;
                    double d39;
                    double d41;
                    double d37;
                    double d36;
                    int i29;
                    int i30;
                    int i31;
                    double d42;
                    if ((!(this.â)) || (this.è))
                    {
                        for (int i32 = i26; i32 < i25; ++i32)
                        {
                            d34 = (i32 - this.K) * d20 + d24;
                            d35 = (i32 - this.K) * d21 + d25;
                            d32 = (i32 - this.K) * d26 + d30;
                            d33 = (i32 - this.K) * d27 + d31;
                            int i33 = Math.min(this.S[i32], this.T[i32]);
                            int i34 = Math.max(this.S[i32], this.T[i32]);
                            d38 = (i33 - this.J) * d28 + d32;
                            d40 = (i33 - this.J) * d29 + d33;
                            d39 = (i33 - this.J) * d22 + d34;
                            d41 = (i33 - this.J) * d23 + d35;
                            int i35 = i32 * i27;
                            for (int i36 = i33; i36 <= i34; ++i36)
                            {
                                if ((i36 >= 0) && (i36 < i27))
                                {
                                    d37 = d38 / d40;
                                    d36 = d39 / d41;
                                    i29 = (int) d37;
                                    i30 = (int) d36;
                                    i29 = (i29 > this.size - 1) ? this.size - 1 : (i29 < 0) ? 0 : i29;
                                    i30 = (i30 > this.size - 1) ? this.size - 1 : (i30 < 0) ? 0 : i30;
                                    i31 = this.Þ[(i28 + i29 + this.size * i30)];
                                    int i37 = (i31 & 0xFF) * i24 & 0xFF00;
                                    int i38 = ((i31 & 0xFF00) >> 8) * i24 & 0xFF00;
                                    int i39 = ((i31 & 0xFF0000) >> 16) * i24 & 0xFF00;
                                    this.Q[(i36 + i35)] = (-16777216 + (i39 << 8) + i38 + (i37 >> 8));
                                }
                                d38 += d28;
                                d40 += d29;
                                d39 += d22;
                                d41 += d23;
                            }
                        }
                    }
                    else
                    {
                        double d47 = Math.sqrt((0.0D - this.Ð) * (0.0D - this.Ð) + (0.0D - this.Ñ) * (0.0D - this.Ñ) + (750.0D - this.Ò) * (750.0D - this.Ò));
                        double d48 = -this.Ð / d47;
                        double d49 = -this.Ñ / d47;
                        double d50 = (750.0D - this.Ò) / d47;
                        for (int i45 = i26; i45 < i25; ++i45)
                        {
                            int i46 = Math.min(this.S[i45], this.T[i45]);
                            int i47 = Math.max(this.S[i45], this.T[i45]);
                            d34 = (i45 - this.K) * d20 + d24;
                            d35 = (i45 - this.K) * d21 + d25;
                            d32 = (i45 - this.K) * d26 + d30;
                            d33 = (i45 - this.K) * d27 + d31;
                            d38 = (i46 - this.J) * d28 + d32;
                            d40 = (i46 - this.J) * d29 + d33;
                            d39 = (i46 - this.J) * d22 + d34;
                            d41 = (i46 - this.J) * d23 + d35;
                            int i48 = i45 * i27;
                            for (int i49 = i46; i49 <= i47; ++i49)
                            {
                                if ((i49 >= 0) && (i49 < i27))
                                {
                                    d37 = d38 / d40;
                                    d36 = d39 / d41;
                                    d42 = d9 + d3 * d37 + d6 * d36 - this.Ð;
                                    double d43 = d10 + d4 * d37 + d7 * d36 - this.Ñ;
                                    double d44 = d11 + d5 * d37 + d8 * d36 - this.Ò;
                                    double d45 = Math.sqrt(d42 * d42 + d43 * d43 + d44 * d44);
                                    d42 /= d45;
                                    d43 /= d45;
                                    d44 /= d45;
                                    double d46 = d48 * d42 + d49 * d43 + d50 * d44;
                                    double d51 = -(d12 * d42 + d13 * d43 + d14 * d44);
                                    double d52 = Math.max(35.0D * (d46 - 0.96D), 0.0D);
                                    int i43 = (int) (i24 * d51 * (d52 * d52 + 1.0D) / 2.0D) - 255;
                                    int i44 = (int) (255.0D * 50.0D * Math.max(d51 - 0.96D, 0.025D));
                                    i29 = (int) d37;
                                    i30 = (int) d36;
                                    i29 = (i29 > this.size - 1) ? this.size - 1 : (i29 < 0) ? 0 : i29;
                                    i30 = (i30 > this.size - 1) ? this.size - 1 : (i30 < 0) ? 0 : i30;
                                    i31 = this.Þ[(i28 + i29 + this.size * i30)];
                                    int i42 = i31 & 0xFF;
                                    int i41 = (i31 & 0xFF00) >> 8;
                                    int i40 = (i31 & 0xFF0000) >> 16;
                                    i42 *= i44;
                                    i41 *= i44;
                                    i40 *= i44;
                                    i42 >>= 8;
                                    i41 >>= 8;
                                    i40 >>= 8;
                                    i42 += i43;
                                    i41 += i43;
                                    i40 += i43;
                                    i42 = (i42 > 255) ? 255 : i42;
                                    i41 = (i41 > 255) ? 255 : i41;
                                    i40 = (i40 > 255) ? 255 : i40;
                                    i42 = (i42 < 0) ? 0 : i42;
                                    i41 = (i41 < 0) ? 0 : i41;
                                    i40 = (i40 < 0) ? 0 : i40;
                                    this.Q[(i49 + i48)] = (-16777216 + (i40 << 16) + (i41 << 8) + i42);
                                }
                                d38 += d28;
                                d40 += d29;
                                d39 += d22;
                                d41 += d23;
                            }
                        }
                    }
                    if (localPolygon1.contains(this.L, this.M))
                    {
                        this.Ö = i1;
                        this.é = true;
                        if ((this.ä) && (!(this.è)))
                        {
                            this.Ê = d12;
                            this.Ë = d13;
                            d42 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                            this.Í = (d3 / d42);
                            this.Î = (d4 / d42);
                            this.Ï = (d5 / d42);
                            this.Ö = i1;
                            this.è = true;
                            this.ä = false;
                        }
                        else
                        {
                            this.ä = false;
                        }
                    }
                }
                ++i1;
                localPolygon1 = new Polygon();
                i4 = i8 + 1;
            }
            else
            {
                localPolygon1.addPoint(arrayOfInt1[i9], arrayOfInt2[i9]);
            }
        }
        backImg.setRGB(0, 0, this.width, this.height, this.Q, 0, this.width);
//        this.backImg = super.createImage(new MemoryImageSource(this.width, this.height, this.Q, 0, this.width));
        loadImg(this.backImg);
        if (this.é)
        {
            return;
        }
        this.Ö = 7;
    }

    public int[] N(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    {
        System.arraycopy(this.V, 0, this.S, 0, this.height);
        System.arraycopy(this.V, 0, this.T, 0, this.height);
        int i1 = -16777215;
        int i2 = 16777215;
        int i3 = 5;
        int i4 = 5;
        for (int i5 = 0; i5 < 4; ++i5)
        {
            if (paramArrayOfInt2[i5] < i2)
            {
                i2 = paramArrayOfInt2[i5];
                i4 = i5;
            }
            if (paramArrayOfInt2[i5] <= i1)
            {
                continue;
            }
            i1 = paramArrayOfInt2[i5];
            i3 = i5;
        }
        int i6 = (i4 + 1) % 4;
        int i7 = (4 + i4 - 1) % 4;
        int i8 = 5;
        if (paramArrayOfInt1[i6] < paramArrayOfInt1[i7])
        {
            K(paramArrayOfInt1[i6], paramArrayOfInt2[i6], paramArrayOfInt1[i4], paramArrayOfInt2[i4], true);
            K(paramArrayOfInt1[i7], paramArrayOfInt2[i7], paramArrayOfInt1[i4], paramArrayOfInt2[i4], false);
            if (i6 == i3)
            {
                i8 = (4 + i4 - 2) % 4;
                K(paramArrayOfInt1[i7], paramArrayOfInt2[i7], paramArrayOfInt1[i8], paramArrayOfInt2[i8], false);
                K(paramArrayOfInt1[i8], paramArrayOfInt2[i8], paramArrayOfInt1[i3], paramArrayOfInt2[i3], false);
            }
            else if (i7 == i3)
            {
                i8 = (i4 + 2) % 4;
                K(paramArrayOfInt1[i6], paramArrayOfInt2[i6], paramArrayOfInt1[i8], paramArrayOfInt2[i8], true);
                K(paramArrayOfInt1[i8], paramArrayOfInt2[i8], paramArrayOfInt1[i3], paramArrayOfInt2[i3], true);
            }
            else
            {
                K(paramArrayOfInt1[i6], paramArrayOfInt2[i6], paramArrayOfInt1[i3], paramArrayOfInt2[i3], true);
                K(paramArrayOfInt1[i7], paramArrayOfInt2[i7], paramArrayOfInt1[i3], paramArrayOfInt2[i3], false);
            }
        }
        else
        {
            K(paramArrayOfInt1[i6], paramArrayOfInt2[i6], paramArrayOfInt1[i4], paramArrayOfInt2[i4], false);
            K(paramArrayOfInt1[i7], paramArrayOfInt2[i7], paramArrayOfInt1[i4], paramArrayOfInt2[i4], true);
            if (i6 == i3)
            {
                i8 = (4 + i4 - 2) % 4;
                K(paramArrayOfInt1[i7], paramArrayOfInt2[i7], paramArrayOfInt1[i8], paramArrayOfInt2[i8], true);
                K(paramArrayOfInt1[i8], paramArrayOfInt2[i8], paramArrayOfInt1[i3], paramArrayOfInt2[i3], true);
            }
            else if (i7 == i3)
            {
                i8 = (i4 + 2) % 4;
                K(paramArrayOfInt1[i6], paramArrayOfInt2[i6], paramArrayOfInt1[i8], paramArrayOfInt2[i8], false);
                K(paramArrayOfInt1[i8], paramArrayOfInt2[i8], paramArrayOfInt1[i3], paramArrayOfInt2[i3], false);
            }
            else
            {
                K(paramArrayOfInt1[i6], paramArrayOfInt2[i6], paramArrayOfInt1[i3], paramArrayOfInt2[i3], false);
                K(paramArrayOfInt1[i7], paramArrayOfInt2[i7], paramArrayOfInt1[i3], paramArrayOfInt2[i3], true);
            }
        }
        int[] arrayOfInt =
        {
            i3, i4
        };
        return arrayOfInt;
    }

    public void K(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
    {
        int i3 = paramInt3 - paramInt1;
        int i4 = paramInt4 - paramInt2;
        int i5;
        if (Math.abs(i3) > Math.abs(i4))
        {
            if (i3 < 0)
            {
                i5 = paramInt1;
                paramInt1 = paramInt3;
                paramInt3 = i5;
                i5 = paramInt2;
                paramInt2 = paramInt4;
                paramInt4 = i5;
            }
            int i2;
            if (paramInt4 > paramInt2)
            {
                i2 = 1;
            }
            else
            {
                i2 = -1;
            }
            i3 = paramInt3 - paramInt1;
            i4 = Math.abs(paramInt4 - paramInt2);
            i5 = paramInt1;
            int i6 = paramInt2;
            int i7 = -(i3 / 2);
            if ((i5 >= 0) && (i6 >= 0) && (i6 < this.height))
            {
                if (paramBoolean)
                {
                    this.S[i6] = Math.min(i5, this.width - 1);
                }
                else
                {
                    this.T[i6] = Math.min(i5, this.width - 1);
                }
            }
            while (true)
            {
                int i8 = 0;
                i7 += i4;
                if (i7 >= 0)
                {
                    i6 += i2;
                    i7 -= i3;
                    i8 = 1;
                }
                if ((++i5 >= 0) && (i6 >= 0) && (i6 < this.height) && (i8 != 0))
                {
                    if (paramBoolean)
                    {
                        this.S[i6] = Math.min(i5, this.width - 1);
                    }
                    else
                    {
                        this.T[i6] = Math.min(i5, this.width - 1);
                    }
                }
                if (i5 >= paramInt3)
                {
                    return;
                }
            }
        }
        if (i4 < 0)
        {
            i5 = paramInt1;
            paramInt1 = paramInt3;
            paramInt3 = i5;
            i5 = paramInt2;
            paramInt2 = paramInt4;
            paramInt4 = i5;
        }
        int i1;
        if (paramInt3 > paramInt1)
        {
            i1 = 1;
        }
        else
        {
            i1 = -1;
        }
        i3 = Math.abs(paramInt3 - paramInt1);
        i4 = paramInt4 - paramInt2;
        i5 = paramInt1;
        int i6 = paramInt2;
        int i7 = -(i4 / 2);
        if ((i5 >= 0) && (i6 >= 0) && (i6 < this.height))
        {
            if (paramBoolean)
            {
                this.S[i6] = Math.min(i5, this.width - 1);
            }
            else
            {
                this.T[i6] = Math.min(i5, this.width - 1);
            }
        }
        while (i6 < paramInt4)
        {
            i7 += i3;
            if (i7 >= 0)
            {
                i5 += i1;
                i7 -= i4;
            }
            ++i6;
            if ((i5 < 0) || (i6 < 0) || (i6 >= this.height))
            {
                continue;
            }
            if (paramBoolean)
            {
                this.S[i6] = Math.min(i5, this.width - 1);
            }
            else
            {
                this.T[i6] = Math.min(i5, this.width - 1);
            }
        }
    }

    public void M()
    {
        if (this.f <= 0)
        {
            return;
        }
        double d1 = 0.0D;
        double d2;
        for (int i1 = 0; i1 < this.f * 3; i1 += 3)
        {
            d2 = this.a[i1];
            double d3 = this.a[(i1 + 1)];
            double d4 = this.a[(i1 + 2)];
            double d5 = Math.sqrt(d2 * d2 + d3 * d3 + d4 * d4) * Math.sqrt(2.0D);
            if (d5 <= d1)
            {
                continue;
            }
            d1 = d5;
        }
        d2 = ((this.width > this.height) ? this.height : this.width) / d1;
        for (int i2 = 0; i2 < this.f * 3; i2 += 3)
        {
            this.a[i2] *= d2;
            this.a[(i2 + 1)] *= d2;
            this.a[(i2 + 2)] *= d2;
        }
    }

    public void F()
    {
        int i1 = this.f * 3;
        while ((i1 -= 3) >= 0)
        {
            double d1 = this.a[i1];
            double d2 = this.a[(i1 + 1)];
            double d3 = this.a[(i1 + 2)];
            this.Z[i1] = (d1 * this.k + d2 * this.l + d3 * this.m + this.n);
            this.Z[(i1 + 1)] = (d1 * this.o + d2 * this.p + d3 * this.q + this.r);
            this.Z[(i1 + 2)] = (d1 * this.s + d2 * this.t + d3 * this.u + this.v);
        }
    }

    public void J()
    {
        this.n += this.Ç;
        this.r += this.È;
        this.v += this.É;
    }

    public void D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5)
    {
        double d4 = Math.cos(paramDouble4);
        double d5 = Math.sin(paramDouble4);
        int i1 = this.f * 3;
        double d1, d2, d3, d6;
        while ((i1 -= 3) >= 0)
        {
            d1 = this.Z[i1];
            d2 = this.Z[(i1 + 1)];
            d3 = this.Z[(i1 + 2)] - this.v;
            d6 = paramDouble1 * d1 + paramDouble2 * d2 + paramDouble3 * d3;
            this.Z[i1] = (d6 * paramDouble1 + d4 * (d1 - (d6 * paramDouble1)) + d5 * (paramDouble2 * d3 - (paramDouble3 * d2)));
            this.Z[(i1 + 1)] = (d6 * paramDouble2 + d4 * (d2 - (d6 * paramDouble2)) + d5 * (paramDouble3 * d1 - (paramDouble1 * d3)));
            this.Z[(i1 + 2)] = (d6 * paramDouble3 + d4 * (d3 - (d6 * paramDouble3)) + d5 * (paramDouble1 * d2 - (paramDouble2 * d1)));
            this.Z[i1] *= paramDouble5;
            this.Z[(i1 + 1)] *= paramDouble5;
            this.Z[(i1 + 2)] *= paramDouble5;
            this.Z[(i1 + 2)] += this.v;
        }
        d1 = this.Í;
        d2 = this.Î;
        d3 = this.Ï;
        d6 = paramDouble1 * d1 + paramDouble2 * d2 + paramDouble3 * d3;
        this.Í = (d6 * paramDouble1 + d4 * (d1 - (d6 * paramDouble1)) + d5 * (paramDouble2 * d3 - (paramDouble3 * d2)));
        this.Î = (d6 * paramDouble2 + d4 * (d2 - (d6 * paramDouble2)) + d5 * (paramDouble3 * d1 - (paramDouble1 * d3)));
        this.Ï = (d6 * paramDouble3 + d4 * (d3 - (d6 * paramDouble3)) + d5 * (paramDouble1 * d2 - (paramDouble2 * d1)));
    }

    public void E()
    {
        double d1 = Math.cos(this.Ä);
        double d2 = Math.sin(this.Ä);
        double d3 = this.o * d1 + this.s * d2;
        double d4 = this.p * d1 + this.t * d2;
        double d5 = this.q * d1 + this.u * d2;
        double d6 = this.s * d1 - (this.o * d2);
        double d7 = this.t * d1 - (this.p * d2);
        double d8 = this.u * d1 - (this.q * d2);
        this.o = d3;
        this.p = d4;
        this.q = d5;
        this.s = d6;
        this.t = d7;
        this.u = d8;
    }

    public void G()
    {
        double d1 = Math.cos(this.Å);
        double d2 = Math.sin(this.Å);
        double d3 = this.k * d1 + this.s * d2;
        double d4 = this.l * d1 + this.t * d2;
        double d5 = this.m * d1 + this.u * d2;
        double d6 = this.s * d1 - (this.k * d2);
        double d7 = this.t * d1 - (this.l * d2);
        double d8 = this.u * d1 - (this.m * d2);
        this.k = d3;
        this.l = d4;
        this.m = d5;
        this.s = d6;
        this.t = d7;
        this.u = d8;
    }

    public void H()
    {
        double d1 = Math.cos(this.Æ);
        double d2 = Math.sin(this.Æ);
        double d3 = this.o * d1 + this.k * d2;
        double d4 = this.p * d1 + this.l * d2;
        double d5 = this.q * d1 + this.m * d2;
        double d6 = this.k * d1 - (this.o * d2);
        double d7 = this.l * d1 - (this.p * d2);
        double d8 = this.m * d1 - (this.q * d2);
        this.o = d3;
        this.p = d4;
        this.q = d5;
        this.k = d6;
        this.l = d7;
        this.m = d8;
    }

    public BufferedImage scale(BufferedImage image, int dw, int dh)
    {
        int sw = image.getWidth();
        int sh = image.getHeight();
        int[] arr = new int[sw * sh];
        PixelGrabber localPixelGrabber = new PixelGrabber(image, 0, 0, sw, sh, arr, 0, sw);
        try
        {
            localPixelGrabber.grabPixels();
        }
        catch (InterruptedException exp)
        {
            Logs.exception(exp);
        }
        this.U = new int[dw * dh];
        int i3 = 65536 * sh / dh;
        int i4 = 65536 * sw / dw;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < dw; ++i7)
        {
            for (int i8 = 0; i8 < dh; ++i8)
            {
                this.U[(i7 + i8 * dw)] = arr[((i5 >> 16) + (i6 >> 16) * sw)];
                i6 += i3;
            }
            i5 += i4;
            i6 = 0;
        }
        BufferedImage bufImg = new BufferedImage(dw, dh, BufferedImage.TYPE_INT_ARGB);
        bufImg.setRGB(0, 0, dw, dh, this.U, 0, dw);

//        bufImg.getGraphics().drawImage(image, 0, 0, dw, dh, this);
        return bufImg;
    }

    private void _mouseClicked(MouseEvent paramEvent)
    {
        Point point = paramEvent.getPoint();
        if ((this.showLightButton) && (this.height - point.y < 32) && (this.width - point.x < 18))
        {
            this.spotLight = (!(this.spotLight));
        }
        else if (this.Ö != 7)
        {
            if (System.currentTimeMillis() - this.P < 500L)
            {
                if (this.pUrls[this.Ö] != null)
                {
                    Desk.browse(this.pUrls[this.Ö]);
                }
            }
            else
            {
                this.ä = true;
                this.P = System.currentTimeMillis();
            }
        }
    }

    private void _mouseEntered(MouseEvent e)
    {
        this.mouseEntered = true;
    }

    private void _mouseExited(MouseEvent e)
    {
        this.ç = true;
        this.mouseEntered = false;
    }

    private void _mouseMoved(MouseEvent e)
    {
        this.mouseEntered = true;
        Point point = e.getPoint();
        this.L = point.x;
        this.M = point.y;
        if (this.showLightButton)
        {
            if ((this.width - point.x >= 18) || (this.height - point.y >= 32))
            {
                this.w = ((this.height / 2 - point.y) * this.mouseResponse / this.width * 2.0D);
                this.x = ((this.width / 2 - point.x) * this.mouseResponse / this.height * 2.0D);
            }
        }
        else
        {
            this.w = ((this.height / 2 - point.y) * this.mouseResponse / this.width * 2.0D);
            this.x = ((this.width / 2 - point.x) * this.mouseResponse / this.height * 2.0D);
        }
    }

    private void loadImg(Image paramImage)
    {
        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(paramImage, 0);
        try
        {
            tracker.waitForID(0);
            return;
        }
        catch (InterruptedException exp)
        {
            Logs.exception(exp);
        }
    }

    @Override
    public synchronized void paint(Graphics g)
    {
        if (this.backImg != null && this.needDraw && g != null)
        {
            g.drawImage(this.backImg, 0, 0, this);
        }
    }

    @Override
    public void update(Graphics g)
    {
        paint(g);
    }

    public void start()
    {
        if (!running)
        {
            running = true;
            new Thread(this).start();
        }
    }

    public void stop()
    {
        running = false;
    }
}
