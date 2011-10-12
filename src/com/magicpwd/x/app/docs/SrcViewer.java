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
import com.magicpwd._comn.S1S1;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd._util.Desk;
import com.magicpwd._util.File;
import com.magicpwd._util.Http;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;

/**
 * 源码查看
 * @author Amon
 */
public class SrcViewer extends ADialog implements IDocsViewer, Runnable
{

    private AMpwdPtn formPtn;
    private java.io.File srcFile;

    public SrcViewer(AMpwdPtn mpwdPtn)
    {
        super(mpwdPtn, true);
        this.formPtn = mpwdPtn;
    }

    public void init()
    {
    }

    @Override
    public void show(java.io.File file)
    {
        this.srcFile = file;
        initView();
        initLang();
        initData();
        new Thread(this).start();
        setVisible(true);
    }

    @Override
    public void run()
    {
        String src = null;
        try
        {
            src = File.readText(srcFile);
            if (Char.isValidate(src))
            {
                java.net.URL url = new java.net.URL(ConsEnv.HOMEPAGE + "code/code0001.ashx");
                java.util.HashMap<String, String> params = new java.util.HashMap<String, String>();
                String ext = File.getExtension(srcFile.getName());
                if (Char.isValidate(ext) && ext.charAt(0) == '.')
                {
                    ext = ext.substring(1);
                }
                params.put("l", ext);
                params.put("i", "1");
                params.put("n", ck_LineNbr.isSelected() ? "1" : "0");
                params.put("u", ck_LinkUri.isSelected() ? "1" : "0");
                params.put("t", cb_TagStyle.getSelectedItem() + "");
                String tab = tf_TabCount.getText().trim();
                if (java.util.regex.Pattern.matches("^\\d+$", tab))
                {
                    tab = "4";
                }
                params.put("s", tab);
                params.put("o", "html");
                params.put("c", src);
                java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();
                java.io.InputStream stream = Http.post(con, params);
                ep_CodeView.setContentType(con.getContentType());
                src = File.readText(stream);
                stream.close();
                con.disconnect();

                java.io.File tmpFile = java.io.File.createTempFile("src_", ".html");
                tmpFile.deleteOnExit();
                File.saveText(tmpFile, src);
                ep_CodeView.setPage(tmpFile.toURI().toURL());
                ep_CodeView.setFont(ep_CodeView.getFont().deriveFont(20f));
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
    }

    public void initView()
    {
        initOptView();

        bt_CodeView = new javax.swing.JButton();
        ep_CodeView = new javax.swing.JEditorPane();

        javax.swing.JScrollPane sp_CodeView = new javax.swing.JScrollPane(ep_CodeView);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(bt_CodeView);
        hpg1.addComponent(pl_CodeView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addContainerGap();
        hsg1.addComponent(sp_CodeView, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addGroup(hpg1);
        hsg1.addContainerGap();
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hsg1));

        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addComponent(pl_CodeView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addComponent(bt_CodeView);
        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg1.addComponent(sp_CodeView, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE);
        vpg1.addGroup(vsg1);
        javax.swing.GroupLayout.SequentialGroup vsg2 = layout.createSequentialGroup();
        vsg2.addContainerGap();
        vsg2.addGroup(vpg1);
        vsg2.addContainerGap();
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(vsg2));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//        setAlwaysOnTop(true);
    }

    private void initOptView()
    {
        pl_CodeView = new javax.swing.JPanel();

        lb_LineNbr = new javax.swing.JLabel();
        ck_LineNbr = new javax.swing.JCheckBox();
        ck_LinkUri = new javax.swing.JCheckBox();
        cb_TagStyle = new javax.swing.JComboBox();
        lb_LinkUri = new javax.swing.JLabel();
        lb_TagStyle = new javax.swing.JLabel();
        tf_TabCount = new javax.swing.JTextField();
        lb_TabCount = new javax.swing.JLabel();
        tt_TabCount = new javax.swing.JLabel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(pl_CodeView);
        pl_CodeView.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lb_LineNbr, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lb_LinkUri, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lb_TagStyle, javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(lb_TabCount, javax.swing.GroupLayout.Alignment.TRAILING);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(tf_TabCount);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(tt_TabCount);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addGroup(hsg1);
        hpg2.addComponent(cb_TagStyle);
        hpg2.addComponent(ck_LinkUri);
        hpg2.addComponent(ck_LineNbr);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addGroup(hpg1);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addGroup(hpg2);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(hsg2));

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lb_LineNbr).addComponent(ck_LineNbr);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(ck_LinkUri).addComponent(lb_LinkUri);
        javax.swing.GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(cb_TagStyle).addComponent(lb_TagStyle);
        javax.swing.GroupLayout.ParallelGroup vpg4 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(tf_TabCount).addComponent(lb_TabCount).addComponent(tt_TabCount);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addGroup(vpg1);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg2);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg3);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg4);
        vsg1.addContainerGap(0, Short.MAX_VALUE);
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(vsg1));
    }

    public void initLang()
    {
        this.setTitle("源码预览");

        lb_LineNbr.setText("行号：");

        ck_LineNbr.setText("显示行号");

        ck_LinkUri.setText("显示资源链接");

        lb_LinkUri.setText("资源：");

        lb_TagStyle.setText("格式：");

        tf_TabCount.setColumns(2);

        lb_TabCount.setText("制表符：");

        tt_TabCount.setText("空格");

        Lang.setWText(bt_CodeView, null, "查看(@V)");

        pack();
        Bean.centerForm(this, formPtn);
    }

    public void initData()
    {
        ep_CodeView.setEditable(false);
        ep_CodeView.addHyperlinkListener(new javax.swing.event.HyperlinkListener()
        {

            @Override
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent e)
            {
                if (javax.swing.event.HyperlinkEvent.EventType.ACTIVATED == e.getEventType())
                {
                    Desk.browse(e.getURL());
                }
            }
        });

        cb_TagStyle.addItem(new S1S1("pre", "<pre>标签"));
        cb_TagStyle.addItem(new S1S1("div", "<div>标签"));
        cb_TagStyle.addItem(new S1S1("table", "<table>标签"));
        cb_TagStyle.setSelectedIndex(0);

        tf_TabCount.setText("4");

        bt_CodeView.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_CodeViewActionPerformed(evt);
            }
        });

        this.processEscape();
    }

    @Override
    protected boolean hideDialog()
    {
        setVisible(false);
        dispose();
        return true;
    }

    private void bt_CodeViewActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (java.util.regex.Pattern.matches("^\\d+$", tf_TabCount.getText().trim()))
        {
            tf_TabCount.setText("4");
        }
        new Thread(this).start();
    }
    private javax.swing.JButton bt_CodeView;
    private javax.swing.JEditorPane ep_CodeView;
    private javax.swing.JPanel pl_CodeView;
    //选项
    private javax.swing.JCheckBox ck_LineNbr;
    private javax.swing.JCheckBox ck_LinkUri;
    private javax.swing.JComboBox cb_TagStyle;
    private javax.swing.JLabel lb_LineNbr;
    private javax.swing.JLabel lb_LinkUri;
    private javax.swing.JLabel lb_TabCount;
    private javax.swing.JLabel lb_TagStyle;
    private javax.swing.JTextField tf_TabCount;
    private javax.swing.JLabel tt_TabCount;
}
