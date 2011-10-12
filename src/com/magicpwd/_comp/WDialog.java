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

import com.magicpwd._cons.ConsEnv;
import com.magicpwd._util.Logs;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.util.EventObject;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Amon
 */
public class WDialog extends javax.swing.JPanel implements AWTEventListener
{

    public WDialog(JFrame frame)
    {
        this.parentWindow = frame;
        toolkit = Toolkit.getDefaultToolkit();
    }

    public void init()
    {
        super.setVisible(false);
        super.setName(ConsEnv.WDIALOG_NAME);
        super.setLayout(null);
        this.addMouseListener(new MouseAdapter()
        {
        });
        this.addComponentListener(new ComponentAdapter()
        {

            @Override
            public void componentResized(ComponentEvent e)
            {
            }
        });
    }

//    @Override
//    public void setVisible(boolean visible)
//    {
//        if (visible)
//        {
//            if (!super.isVisible())
//            {
//                KFManager kfm = (KFManager) java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager();
//                kfm.setContainerName(ConsEnv.WDIALOG_NAME);
//                lastComponent = kfm.getFocusOwner();
//                lastResizable = owner.isResizable();
//                owner.setResizable(lastResizable & nextResizable);
//                fixSize();
//                (getComponentCount() > 0 ? getComponents()[0] : this).requestFocus();
//            }
//        }
//        else
//        {
//            if (super.isVisible())
//            {
//                KFManager kfm = (KFManager) java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager();
//                kfm.setContainerName(null);
//                owner.setResizable(lastResizable);
//                if (lastComponent != null && lastComponent.isShowing())
//                {
//                    lastComponent.requestFocus();
//                }
//            }
//        }
//
//        super.setVisible(visible);
//    }
    @Override
    public void setVisible(boolean visible)
    {
        if (visible)
        {
            if (parentWindow == null)
            {
                parentWindow = SwingUtilities.windowForComponent(this);
            }
            Component focusOwner = parentWindow.getFocusOwner();
            if (focusOwner != this)
            {
                lastFocusOwner = focusOwner;
            }
            toolkit.addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
            toolkit.addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK);
            requestFocus();
        }
        else
        {
            toolkit.removeAWTEventListener(this);
            if (lastFocusOwner != null)
            {
                lastFocusOwner.requestFocus();
                lastFocusOwner = null;
            }
        }
        super.setVisible(visible);
    }

    @Override
    public void eventDispatched(AWTEvent e)
    {
        if (!(e instanceof EventObject))
        {
            return;
        }
        Object obj = e.getSource();
        if (!(obj instanceof Component))
        {
            return;
        }

        if (SwingUtilities.windowForComponent((Component) obj) == parentWindow)
        {
            try
            {
                Class[] cls =
                {
                };
                Object[] args =
                {
                };
                EventObject evt = (EventObject) e;
                evt.getClass().getMethod("consume", cls).invoke(evt, args);
            }
            catch (Exception ex)
            {
                Logs.exception(ex);
            }
        }
    }

    private Window parentWindow;
    private Component lastFocusOwner;
    private final Toolkit toolkit;
}
