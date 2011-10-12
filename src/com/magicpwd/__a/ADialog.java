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
package com.magicpwd.__a;

import com.magicpwd._util.Bean;

/**
 *
 * @author Amon
 */
public abstract class ADialog extends javax.swing.JDialog
{

    public ADialog(java.awt.Window owner, boolean modal)
    {
        super(owner, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
    }

    abstract protected boolean hideDialog();

    protected void processEscape()
    {
        javax.swing.KeyStroke stroke = javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0);
        javax.swing.AbstractAction action = new javax.swing.AbstractAction()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                hideDialog();
            }
        };
        Bean.registerKeyStrokeAction(rootPane, stroke, action, "esc", javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}
