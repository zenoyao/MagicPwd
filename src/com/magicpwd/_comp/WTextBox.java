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

import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import javax.swing.undo.UndoManager;

/**
 *
 * @author aven
 */
public class WTextBox implements java.awt.datatransfer.ClipboardOwner
{

    private boolean selectAll;
    private javax.swing.text.JTextComponent textBox;
    private UndoManager undo = new UndoManager();

    public WTextBox(javax.swing.text.JTextComponent textBox)
    {
        this(textBox, false);
    }

    public WTextBox(javax.swing.text.JTextComponent textBox, boolean selectAll)
    {
        this.textBox = textBox;
        this.selectAll = selectAll;
    }

    public void initView()
    {
        if (selectAll)
        {
            textBox.addFocusListener(new java.awt.event.FocusAdapter()
            {

                @Override
                public void focusGained(java.awt.event.FocusEvent fe)
                {
                    textBox.selectAll();
                }
            });
        }

        popMenu = new javax.swing.JPopupMenu();

        undoItem = new javax.swing.JMenuItem();
        javax.swing.Action action = new javax.swing.AbstractAction()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                editUndoActionPerformed(evt);
            }
        };
        undoItem.addActionListener(action);
        popMenu.add(undoItem);
        Bean.registerKeyStrokeAction(textBox, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK), action, "undo", javax.swing.JComponent.WHEN_FOCUSED);

        redoItem = new javax.swing.JMenuItem();
        action = new javax.swing.AbstractAction()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                editRedoActionPerformed(evt);
            }
        };
        redoItem.addActionListener(action);
        popMenu.add(redoItem);
        Bean.registerKeyStrokeAction(textBox, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK), action, "redo", javax.swing.JComponent.WHEN_FOCUSED);
        Bean.registerKeyStrokeAction(textBox, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK | java.awt.event.InputEvent.SHIFT_DOWN_MASK), action, "redo", javax.swing.JComponent.WHEN_FOCUSED);

        popMenu.addSeparator();

        cutItem = new javax.swing.JMenuItem();
        action = new javax.swing.AbstractAction()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                editCutsActionPerformed(evt);
            }
        };
        cutItem.addActionListener(action);
        popMenu.add(cutItem);
        Bean.registerKeyStrokeAction(textBox, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK), action, "cut-to-clipboard", javax.swing.JComponent.WHEN_FOCUSED);

        copyItem = new javax.swing.JMenuItem();
        action = new javax.swing.AbstractAction()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                editCopyActionPerformed(evt);
            }
        };
        copyItem.addActionListener(action);
        popMenu.add(copyItem);
        Bean.registerKeyStrokeAction(textBox, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK), action, "copy-to-clipboard", javax.swing.JComponent.WHEN_FOCUSED);

        pastItem = new javax.swing.JMenuItem();
        action = new javax.swing.AbstractAction()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                editPastActionPerformed(evt);
            }
        };
        pastItem.addActionListener(action);
        popMenu.add(pastItem);
        Bean.registerKeyStrokeAction(textBox, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK), action, "paste-from-clipboard", javax.swing.JComponent.WHEN_FOCUSED);

        popMenu.addSeparator();

        sallItem = new javax.swing.JMenuItem();
        action = new javax.swing.AbstractAction()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                if (textBox.getText().length() > 0)
                {
                    textBox.selectAll();
                    dallItem.setEnabled(true);
                }
            }
        };
        sallItem.addActionListener(action);
        popMenu.add(sallItem);
        Bean.registerKeyStrokeAction(textBox, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK), action, "select-all", javax.swing.JComponent.WHEN_FOCUSED);

        dallItem = new javax.swing.JMenuItem();
        action = new javax.swing.AbstractAction()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                int i = textBox.getCaretPosition();
                textBox.select(i, i);
                dallItem.setEnabled(false);
            }
        };
        dallItem.addActionListener(action);
        popMenu.add(dallItem);
        Bean.registerKeyStrokeAction(textBox, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK), action, "deselect-all", javax.swing.JComponent.WHEN_FOCUSED);
        Bean.registerKeyStrokeAction(textBox, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK | java.awt.event.InputEvent.SHIFT_DOWN_MASK), action, "deselect-all", javax.swing.JComponent.WHEN_FOCUSED);

        textBox.setComponentPopupMenu(popMenu);
    }

    public void initLang()
    {
        Lang.setWText(undoItem, LangRes.P30F1601, "撤消");
        Lang.setWText(redoItem, LangRes.P30F1602, "重做");
        Lang.setWText(cutItem, LangRes.P30F1603, "剪切");
        Lang.setWText(copyItem, LangRes.P30F1604, "复制");
        Lang.setWText(pastItem, LangRes.P30F1605, "粘贴");
        Lang.setWText(sallItem, LangRes.P30F1606, "全选");
        Lang.setWText(dallItem, LangRes.P30F1607, "取消全选");
    }

    public void initData()
    {
        textBox.getDocument().addUndoableEditListener(new javax.swing.event.UndoableEditListener()
        {

            @Override
            public void undoableEditHappened(javax.swing.event.UndoableEditEvent evt)
            {
                undo.addEdit(evt.getEdit());
                changeMenuStatus();
            }
        });

        changeMenuStatus();
    }

    public void setTextComponent(javax.swing.text.JTextComponent textBox)
    {
        this.textBox = textBox;
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents)
    {
    }

    public void reset()
    {
        undo.discardAllEdits();
        changeMenuStatus();
    }

    private void editCutsActionPerformed(ActionEvent evt)
    {
        String copy = textBox.getSelectedText();
        if (copy != null)
        {
            java.awt.datatransfer.StringSelection sSelection = new java.awt.datatransfer.StringSelection(copy);
            java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sSelection, this);
            textBox.replaceSelection("");
        }
        changeMenuStatus();
    }

    private void editCopyActionPerformed(ActionEvent evt)
    {
        String copy = textBox.getSelectedText();
        if (!Char.isValidate(copy))
        {
            return;
        }

        java.awt.datatransfer.StringSelection sSelection = new java.awt.datatransfer.StringSelection(copy);
        java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sSelection, this);
        changeMenuStatus();
    }

    private void editPastActionPerformed(ActionEvent evt)
    {
        java.awt.datatransfer.Transferable transfer = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);
        if (transfer != null)
        {
            try
            {
                Object data = transfer.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
                if (data != null)
                {
                    textBox.replaceSelection(data.toString());
                }
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
        }
        changeMenuStatus();
    }

    private void editUndoActionPerformed(ActionEvent evt)
    {
        if (undo.canUndo())
        {
            try
            {
                undo.undo();
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
            changeMenuStatus();
        }
    }

    private void editRedoActionPerformed(ActionEvent evt)
    {
        if (undo.canRedo())
        {
            try
            {
                undo.redo();
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
            changeMenuStatus();
        }
    }

    private void changeMenuStatus()
    {
        undoItem.setEnabled(undo.canUndo());
        redoItem.setEnabled(undo.canRedo());

        dallItem.setEnabled(false);
    }

    public boolean canUndo()
    {
        return undo.canUndo();
    }

    public boolean canRedo()
    {
        return undo.canRedo();
    }
    private javax.swing.JPopupMenu popMenu;
    private javax.swing.JMenuItem sallItem;
    private javax.swing.JMenuItem dallItem;
    private javax.swing.JMenuItem cutItem;
    private javax.swing.JMenuItem copyItem;
    private javax.swing.JMenuItem pastItem;
    private javax.swing.JMenuItem undoItem;
    private javax.swing.JMenuItem redoItem;
}
