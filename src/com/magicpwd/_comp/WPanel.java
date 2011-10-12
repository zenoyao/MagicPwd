package com.magicpwd._comp;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.Timer;

public class WPanel extends JComponent
{

    protected boolean animated;
    private float frameIndex;
    private Timer timer;

    public WPanel()
    {
    }

    @Override
    public void paint(Graphics g)
    {
        if (!animated)
        {
            super.paint(g);
            return;
        }

        if (timer != null && timer.isRunning())
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, frameIndex / ANIMATION_FRAMES));
            super.paint(g2d);
            return;
        }

        frameIndex = 0;
        timer = new Timer(ANIMATION_INTERVAL, new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                doAnimation();
            }
        });
        timer.start();
    }

    private void doAnimation()
    {
        frameIndex += 1;
        if (frameIndex >= ANIMATION_FRAMES)
        {
            timer.stop();
            timer = null;
            return;
        }

        repaint();
    }
    private static final int ANIMATION_FRAMES = 50;
    private static final int ANIMATION_INTERVAL = 10;

    /**
     * @return the animated
     */
    public boolean isAnimated()
    {
        return animated;
    }

    /**
     * @param animated the animated to set
     */
    public void setAnimated(boolean animated)
    {
        this.animated = animated;
    }
}
