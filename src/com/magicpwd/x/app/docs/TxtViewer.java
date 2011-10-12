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
package com.magicpwd.x.app.docs;

import com.magicpwd.__a.ADialog;
import com.magicpwd.__a.AMpwdPtn;
import com.magicpwd.__i.docs.IDocsViewer;
import com.magicpwd._util.Bean;
import com.magicpwd._util.File;
import com.magicpwd._util.Logs;

/**
 *
 * @author Amon
 */
public class TxtViewer extends ADialog implements IDocsViewer
{

    private AMpwdPtn formPtn;

    public TxtViewer(AMpwdPtn mpwdPtn)
    {
        super(mpwdPtn, true);
        this.formPtn = mpwdPtn;
    }

    @Override
    public void show(java.io.File file)
    {
        initView();
        initLang();
        initData();
        String ext = File.getExtension(file.getName());
        tp_TextArea.setContentType("text/" + ("rtf".equalsIgnoreCase(ext) ? "rtf" : "plain"));
        try
        {
            tp_TextArea.setPage(file.toURI().toURL());
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            tp_TextArea.setContentType("text/plain");
            tp_TextArea.setText(exp.getLocalizedMessage());
        }
        setVisible(true);
    }

    @Override
    protected boolean hideDialog()
    {
        setVisible(false);
        dispose();
        return true;
    }

    public void initView()
    {
        sp_TextArea = new javax.swing.JScrollPane();
        tp_TextArea = new javax.swing.JEditorPane();

        sp_TextArea.setViewportView(tp_TextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addComponent(sp_TextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE);
        hsg.addContainerGap();
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(hsg));

        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        vsg.addComponent(sp_TextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE);
        vsg.addContainerGap();
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(vsg));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//        setAlwaysOnTop(true);
    }

    public void initLang()
    {
        this.setTitle("文本预览");

        pack();
        Bean.centerForm(this, formPtn);
    }

    public void initData()
    {
        tp_TextArea.setEditable(false);

        this.processEscape();
    }
    private javax.swing.JScrollPane sp_TextArea;
    private javax.swing.JEditorPane tp_TextArea;
}
