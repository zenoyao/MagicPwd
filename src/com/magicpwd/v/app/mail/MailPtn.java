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
package com.magicpwd.v.app.mail;

import com.magicpwd._bean.mail.Connect;
import com.magicpwd.m.mail.MailMdl;
import com.magicpwd.m.mail.NodeMdl;
import com.magicpwd.m.mail.Reader;
import com.magicpwd._comn.S1S1;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Desk;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.m.UserMdl;
import com.magicpwd.r.MailCR;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;
import javax.swing.BorderFactory;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * 
 * @author Amon
 */
public class MailPtn extends javax.swing.JFrame implements Runnable
{

    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;
    private MailMdl tableMode;
    private Folder folder;
    private Connect connect;
    private Reader reader;
    private java.io.File filePath;
    private UserMdl userMdl;

    public MailPtn(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    @Override
    public void run()
    {
        loadMsg();
    }

    public void initView()
    {
        initCtrlView();
        initEditView();
        initBaseView();
    }

    public void initLang()
    {
        lb_MailHead.setText("标题：");
        lb_MailUser.setText("收件人：");
        bt_Delete.setText("删除(D)");
        bt_SaveAs.setText("下载(D)");
        bt_Replay.setText("回复(D)");
    }

    public void initData()
    {
        rootNode = new DefaultMutableTreeNode("邮箱列表");
        treeModel = new DefaultTreeModel(rootNode);
        tr_MailBoxs.setModel(treeModel);
        tableMode = new MailMdl(userMdl);
        tb_MailMsgs.setModel(tableMode);
        tb_MailMsgs.setRowSorter(new TableRowSorter<MailMdl>(tableMode));

        MailCR mailCR = new MailCR();
        javax.swing.table.TableColumnModel colModel = tb_MailMsgs.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(16);
        colModel.getColumn(0).setCellRenderer(mailCR);
        colModel.getColumn(1).setCellRenderer(mailCR);
        colModel.getColumn(2).setCellRenderer(mailCR);
        colModel.getColumn(3).setPreferredWidth(tb_MailMsgs.getFontMetrics(tb_MailMsgs.getFont()).stringWidth("0000-00-00"));
        colModel.getColumn(3).setCellRenderer(mailCR);
    }

    public void append(Connect connect, String folder)
    {
        showNotice("正在检测邮件信息，请稍候……");
        Store store = null;
        try
        {
            store = connect.getStore();
            Folder f = (com.magicpwd._util.Char.isValidate(folder) ? store.getFolder(folder) : store.getDefaultFolder());
            NodeMdl model = new NodeMdl(connect, f);
            listFolders(connect, model, f);

            // 已有节点
            String display = model.toString();
            boolean exists = false;
            for (int i = 0, j = rootNode.getChildCount(); i < j; i += 1)
            {
                if (display.equalsIgnoreCase(rootNode.getChildAt(i).toString()))
                {
                    model = (NodeMdl) rootNode.getChildAt(i);
                    treeModel.nodeChanged(model);
                    exists = true;
                    break;
                }
            }

            // 新增节点
            if (!exists)
            {
                rootNode.add(model);
                treeModel.nodeStructureChanged(rootNode);
            }
            tr_MailBoxs.setSelectionPath(new TreePath(new DefaultMutableTreeNode[]
                    {
                        rootNode, model
                    }));
            showNotice("邮件信息检测完毕！");
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            showNotice("邮件信息检测异常:(");
        }
        finally
        {
            if (store != null)
            {
                try
                {
                    store.close();
                }
                catch (Exception ex)
                {
                    Logs.exception(ex);
                }
            }
        }
    }

    private static void listFolders(Connect connect, DefaultMutableTreeNode node, Folder folder) throws Exception
    {
        for (Folder sub : folder.list())
        {
            try
            {
                sub.open(Folder.READ_ONLY);
                NodeMdl temp = new NodeMdl(connect, sub);
                node.add(temp);
                if ((sub.getType() & Folder.HOLDS_FOLDERS) != 0)
                {
                    listFolders(connect, temp, sub);
                }
                sub.close(false);
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
        }
    }

    private void initCtrlView()
    {
        pl_MailCtrl = new javax.swing.JPanel();
        bt_SaveAs = new javax.swing.JButton();
        bt_Replay = new javax.swing.JButton();
        bt_Delete = new javax.swing.JButton();
        lb_MailInfo = new javax.swing.JLabel();

        bt_SaveAs.setEnabled(false);
        bt_SaveAs.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_SaveAsActionPerformed(evt);
            }
        });

        bt_Replay.setEnabled(false);

        bt_Delete.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(pl_MailCtrl);
        pl_MailCtrl.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addComponent(lb_MailInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(bt_Delete);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(bt_Replay);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(bt_SaveAs);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg.addComponent(bt_SaveAs);
        vpg.addComponent(bt_Replay);
        vpg.addComponent(bt_Delete);
        vpg.addComponent(lb_MailInfo);
        layout.setVerticalGroup(vpg);
    }

    private void initEditView()
    {
        pl_MailEdit = new javax.swing.JPanel();
        lb_MailHead = new javax.swing.JLabel();
        lb_MailUser = new javax.swing.JLabel();
        tf_MailHead = new javax.swing.JTextField();
        tf_MailUser = new javax.swing.JTextField();
        javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane();
        ta_MailBody = new javax.swing.JEditorPane();

        lb_MailUser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lb_MailHead.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        ta_MailBody.addHyperlinkListener(new javax.swing.event.HyperlinkListener()
        {

            @Override
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt)
            {
                ta_MailBodyHyperlinkUpdate(evt);
            }
        });
        ta_MailBody.setEditable(false);
        sp1.setViewportView(ta_MailBody);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(pl_MailEdit);
        pl_MailEdit.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lb_MailHead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, 80);
        hpg1.addComponent(lb_MailUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, 80);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(tf_MailHead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        hpg2.addComponent(tf_MailUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addGroup(hpg1);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg2);
        javax.swing.GroupLayout.ParallelGroup hvg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        hvg.addGroup(javax.swing.GroupLayout.Alignment.LEADING, hsg);
        hvg.addComponent(sp1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE);
        layout.setHorizontalGroup(hvg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lb_MailHead);
        vpg1.addComponent(tf_MailHead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lb_MailUser);
        vpg2.addComponent(tf_MailUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        vsg.addGroup(vpg1);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg2);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addComponent(sp1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE);
        layout.setVerticalGroup(vsg);
    }

    private void initBaseView()
    {
        tr_MailBoxs = new javax.swing.JTree();
        tb_MailMsgs = new javax.swing.JTable();

        javax.swing.JSplitPane sp1 = new javax.swing.JSplitPane();
        sp1.setBorder(BorderFactory.createEmptyBorder());
        sp1.setDividerLocation(120);
        sp1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sp1.setOneTouchExpandable(true);

        javax.swing.JSplitPane sp2 = new javax.swing.JSplitPane();
        sp2.setBorder(BorderFactory.createEmptyBorder());
        sp2.setDividerLocation(180);
        sp2.setOneTouchExpandable(true);

        tr_MailBoxs.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
        tr_MailBoxs.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                tr_MailBoxsMouseClicked(evt);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                tr_MailBoxsMouseReleased(evt);
            }
        });
        sp2.setLeftComponent(new javax.swing.JScrollPane(tr_MailBoxs));

        tb_MailMsgs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tb_MailMsgs.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                tb_MailMsgsMouseReleased(evt);
            }
        });
        sp2.setRightComponent(new javax.swing.JScrollPane(tb_MailMsgs));

        sp1.setTopComponent(sp2);
        sp1.setBottomComponent(pl_MailEdit);

        java.awt.Container container = getContentPane();
        setTitle("邮件检测");
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(container);
        container.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        hpg.addComponent(sp1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE);
        hpg.addComponent(pl_MailCtrl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addGroup(hpg);
        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        vsg.addComponent(sp1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED);
        vsg.addComponent(pl_MailCtrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);

        pack();
        setIconImage(Bean.getLogo(16));
    }

    /**
     * 消息加载线程专用
     * @return
     */
    private boolean loadMsg()
    {
        try
        {
            if (folder != null)
            {
                showNotice("正在获取邮件列表信息……");
                connect.loadMailInfo();
                Message[] msgs = folder.getMessages();
//                FetchProfile profile = new FetchProfile();
//                profile.add(FetchProfile.Item.ENVELOPE);
//                folder.fetch(msgs, profile);

                Reader mail;
                for (int i = 1, j = msgs.length; i <= j; i += 1)
                {
                    showNotice("正处理第" + i + "封邮件……");
                    mail = new Reader(connect);
                    mail.read(folder, msgs[i - 1]);
                    tableMode.append(mail);
                }

                folder.close(true);
                folder = null;

                showNotice("邮件列表读取完毕！");
                return true;
            }
            return false;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
    }

    private void tr_MailBoxsMouseClicked(java.awt.event.MouseEvent evt)
    {
        if (evt.getClickCount() < 2)
        {
            return;
        }

        showNotice("");
        TreePath path = tr_MailBoxs.getSelectionPath();
        if (path == null)
        {
            return;
        }
        Object obj = path.getLastPathComponent();
        if (!(obj instanceof NodeMdl))
        {
            return;
        }

        NodeMdl node = (NodeMdl) obj;
        String name = node.getKeyWord();
        if (!com.magicpwd._util.Char.isValidate(name))
        {
            return;
        }

        Store store = null;

        try
        {
            showNotice("正在处理与服务器通讯……");
            connect = node.getConnect();
            store = connect.getStore();
            if (!store.isConnected())
            {
                store.connect();
            }
            folder = store.getFolder(node.getKeyWord());
            if (folder == null)
            {
                return;
            }
            if ((folder.getType() & Folder.HOLDS_MESSAGES) == 0)
            {
                return;
            }
            showNotice("正在读取邮件列表，请稍候……");
            if (!folder.isOpen())
            {
                folder.open(Folder.READ_WRITE);
            }
            tableMode.clear();

            Thread thread = new Thread(this);
            thread.start();
//            SwingUtilities.invokeLater(thread);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            showNotice("邮件列表读取异常:(");
        }
    }

    private void tr_MailBoxsMouseReleased(java.awt.event.MouseEvent evt)
    {
        java.awt.Point p = evt.getPoint();
        TreePath path = tr_MailBoxs.getPathForLocation(p.x, p.y);
//        tr_MailBoxs.setSelectionPath(path);
        if (path != null)
        {
            Object obj = path.getLastPathComponent();
            if ((obj instanceof NodeMdl) && evt.isPopupTrigger())
            {
            }
        }
    }

    private void tb_MailMsgsMouseReleased(java.awt.event.MouseEvent evt)
    {
        try
        {
            showNotice("正在加载邮件内容……");
            reader = tableMode.getMailInf(tb_MailMsgs.getSelectedRow());
            ta_MailBody.setContentType(reader.getContentType());
            tf_MailHead.setText(reader.getSubject());
            tf_MailUser.setText(reader.getTo());
            ta_MailBody.setText(reader.getContent());
            bt_SaveAs.setEnabled(reader.hasAttachment());
            showNotice("邮件内容加载完毕！");

            connect.setMailReaded(reader.getMessageId(), true);
            connect.saveMailInfo();
        }
        catch (Exception ex)
        {
            Logs.exception(ex);
            showNotice("邮件内容加载异常:(");
        }
    }

    private void ta_MailBodyHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt)
    {
        if (evt.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED)
        {
            if (evt instanceof javax.swing.text.html.HTMLFrameHyperlinkEvent)
            {
                javax.swing.text.html.HTMLDocument doc = (javax.swing.text.html.HTMLDocument) ta_MailBody.getDocument();
                doc.processHTMLFrameHyperlinkEvent((javax.swing.text.html.HTMLFrameHyperlinkEvent) evt);
            }
            else
            {
                Desk.browse(evt.getURL().toString());
            }
        }
    }

    private void bt_SaveAsActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (reader == null || !reader.hasAttachment())
        {
            return;
        }

        javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
        jfc.setMultiSelectionEnabled(false);
        jfc.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        if (filePath != null)
        {
            jfc.setSelectedFile(filePath);
        }
        int status = jfc.showSaveDialog(this);
        if (status != javax.swing.JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        filePath = jfc.getSelectedFile();
        if (filePath == null)
        {
            return;
        }
        if (!filePath.exists())
        {
            filePath.mkdirs();
        }
        if (!filePath.isDirectory() || !filePath.canWrite())
        {
            Lang.showMesg(this, LangRes.P30F7A2F, "无法保存数据到您指定的路径，请确认您是否有足够的权限！");
            return;
        }

        try
        {
            java.io.File file;
            for (S1S1 item : reader.getAttachmentList())
            {
                file = new java.io.File(item.getV());
                if (!file.exists())
                {
                    continue;
                }
                file.renameTo(new java.io.File(filePath, item.getK()));
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(this, LangRes.P30F7A2D, "文件下载保存出错，请重试！");
        }
    }

    private synchronized void showNotice(String notice)
    {
        lb_MailInfo.setText(notice);
    }
    private javax.swing.JEditorPane ta_MailBody;
    private javax.swing.JTable tb_MailMsgs;
    private javax.swing.JTree tr_MailBoxs;
    private javax.swing.JPanel pl_MailEdit;
    private javax.swing.JLabel lb_MailHead;
    private javax.swing.JLabel lb_MailUser;
    private javax.swing.JLabel lb_MailInfo;
    private javax.swing.JTextField tf_MailHead;
    private javax.swing.JTextField tf_MailUser;
    private javax.swing.JPanel pl_MailCtrl;
    private javax.swing.JButton bt_Delete;
    private javax.swing.JButton bt_SaveAs;
    private javax.swing.JButton bt_Replay;
}
