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
package com.magicpwd._util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amon
 */
public class Jpng
{

    private int dt = 30;
    private int it;
    private int fi;
    private int ti;
    private int ci;
    private javax.swing.Timer timer;
    private javax.swing.JLabel label;
    private javax.swing.JButton button;
    private List<javax.swing.Icon> icons;
    private static Jpng jpng;

    public Jpng()
    {
        this(new ArrayList<javax.swing.Icon>());
    }

    public Jpng(List<javax.swing.Icon> icons)
    {
        this.icons = icons;
    }

    public static Jpng getInstance()
    {
        if (jpng == null)
        {
            jpng = new Jpng();
        }
        return jpng;
    }

    public boolean readIcons(String filePath, int iconWidth, int iconHeight) throws Exception
    {
        return readIcons(new java.io.File(filePath), iconWidth, iconHeight);
    }

    public boolean readIcons(java.io.File file, int iconWidth, int iconHeight) throws Exception
    {
        if (file != null && file.exists() && file.isFile() && file.canRead())
        {
            return readIcons(new java.io.FileInputStream(file), iconWidth, iconHeight);
        }
        return false;
    }

    public boolean readIcons(java.io.InputStream stream, int iconWidth, int iconHeight) throws Exception
    {
        fi = 0;
        ti = 0;
        if (icons.size() > 0)
        {
            icons.clear();
        }

        if (stream == null || iconWidth < 1 || iconHeight < 1)
        {
            return false;
        }

        java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(stream);
        int w = image.getWidth();
        int h = image.getHeight();
        if (w < iconWidth || h < iconHeight)
        {
            return false;
        }

        for (int y = 0; y < h; y += iconHeight)
        {
            for (int x = 0; x < w; x += iconWidth)
            {
                icons.add(new javax.swing.ImageIcon(image.getSubimage(x, y, iconWidth, iconHeight)));
            }
        }
        return true;
    }

    public void start()
    {
        if (timer == null)
        {
            timer = new javax.swing.Timer(dt, new java.awt.event.ActionListener()
            {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    showIcon();
                }
            });
        }
        if (ti <= fi)
        {
            ti = icons.size() - 1;
        }
        timer.setInitialDelay(it);
        timer.setDelay(dt);
        if (label != null)
        {
            label.putClientProperty("amon-icon", label.getIcon());
        }
        if (button != null)
        {
            button.putClientProperty("amon-icon", button.getIcon());
        }
        timer.start();
    }

    public void stop()
    {
        if (timer.isRunning())
        {
            timer.stop();
        }
        ci = fi;
        if (label != null)
        {
            label.setIcon((javax.swing.Icon) label.getClientProperty("amon-icon"));
        }
        if (button != null)
        {
            button.setIcon((javax.swing.Icon) button.getClientProperty("amon-icon"));
        }
    }

    public void suspend()
    {
        if (timer.isRunning())
        {
            timer.stop();
        }
    }

    public void continve()
    {
        if (!timer.isRunning())
        {
            timer.start();
        }
    }

    /**
     * @return the dt
     */
    public int getDt()
    {
        return dt;
    }

    /**
     * @param dt the dt to set
     */
    public void setDt(int dt)
    {
        if (dt < 1)
        {
            dt = 1;
        }
        this.dt = dt;
    }

    /**
     * @return the it
     */
    public int getIt()
    {
        return it;
    }

    /**
     * @param it the it to set
     */
    public void setIt(int it)
    {
        this.it = it;
    }

    /**
     * @return the fi
     */
    public int getFi()
    {
        return fi;
    }

    /**
     * @param fi the fi to set
     */
    public void setFi(int fi)
    {
        if (fi < 0 || fi >= icons.size() - 1)
        {
            fi = 0;
        }
        this.fi = fi;
    }

    /**
     * @return the ti
     */
    public int getTi()
    {
        return ti;
    }

    /**
     * @param ti the ti to set
     */
    public void setTi(int ti)
    {
        if (ti <= fi || ti >= icons.size())
        {
            ti = icons.size() - 1;
        }
        this.ti = ti;
    }

    /**
     * @return the label
     */
    public javax.swing.JLabel getLabel()
    {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(javax.swing.JLabel label)
    {
        this.label = label;
    }

    /**
     * @return the button
     */
    public javax.swing.JButton getButton()
    {
        return button;
    }

    /**
     * @param button the button to set
     */
    public void setButton(javax.swing.JButton button)
    {
        this.button = button;
    }

    private void showIcon()
    {
        if (label != null)
        {
            ci = (ci + 1) % icons.size();
            label.setIcon(icons.get(ci));
        }
        if (button != null)
        {
            ci = (ci + 1) % icons.size();
            button.setIcon(icons.get(ci));
        }
    }
}
