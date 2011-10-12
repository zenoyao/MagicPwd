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
package com.magicpwd.v.app.mwiz;

import com.magicpwd.__a.AMpwdPtn;
import com.magicpwd.__i.IBackCall;
import com.magicpwd._bean.mail.Connect;
import com.magicpwd._comn.I1S2;
import com.magicpwd._comn.mpwd.MpwdHeader;
import com.magicpwd._comn.S1S1;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._comp.WButtonGroup;
import com.magicpwd._cons.ConsCfg;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._enum.AppView;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.m.HintMdl;
import com.magicpwd.m.UserMdl;
import com.magicpwd.m.mail.Reader;
import com.magicpwd.m.mwiz.KeysMdl;
import com.magicpwd.m.mwiz.MwizMdl;
import com.magicpwd.v.app.MenuPtn;
import com.magicpwd.v.app.tray.TrayPtn;
import com.magicpwd.v.app.HintBar;
import com.magicpwd.v.app.mail.MailDlg;
import com.magicpwd.x.app.mail.MailOpt;

/**
 * 向导模式
 * @author Amon
 */
public class MwizPtn extends AMpwdPtn
{

    private MenuPtn menuPtn;
    private MwizMdl mwizMdl;
    private EditPtn editPtn;
    private String lastQry;
    private int lastOpt;

    public MwizPtn(TrayPtn trayPtn, UserMdl userMdl)
    {
        super(trayPtn, userMdl);
    }

    @Override
    public boolean initView()
    {
        tb_ToolBar = new javax.swing.JToolBar();
        tb_ToolBar.setFloatable(false);
        tb_ToolBar.setRollover(true);

        fb_FindBar = new FindBar(this);
        fb_FindBar.initView();
        fb_FindBar.setVisible(false);
        getLayeredPane().add(fb_FindBar, new Integer(javax.swing.JLayeredPane.MODAL_LAYER + 1));

        tb_KeysList = new javax.swing.JTable();
        tb_KeysList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        hb_HintBar = new HintBar(userMdl);
        hb_HintBar.initView();

        pm_MenuPop = new javax.swing.JPopupMenu();
        javax.swing.JScrollPane jsp = new javax.swing.JScrollPane(tb_KeysList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addContainerGap();
        hsg1.addComponent(tb_ToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        hsg1.addContainerGap();
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addContainerGap();
        hsg2.addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE);
        hsg2.addContainerGap();
        javax.swing.GroupLayout.SequentialGroup hsg3 = layout.createSequentialGroup();
//        hsg3.addContainerGap();
        hsg3.addComponent(hb_HintBar, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE);
//        hsg3.addContainerGap();
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(hsg1).addGroup(hsg2).addGroup(hsg3));

        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addComponent(tb_ToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addComponent(hb_HintBar);
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(vsg));

        this.setIconImage(Bean.getLogo(16));

        this.pack();
        Bean.centerForm(this, null);
        this.setVisible(true);
        return true;
    }

    @Override
    public boolean initLang()
    {
        setTitle(Lang.getLang(LangRes.P30F6201, "魔方密码"));

        hb_HintBar.initLang();

        this.pack();
        Bean.centerForm(this, null);
        return true;
    }

    @Override
    public boolean initData()
    {
        hb_HintBar.initData();
        hb_HintBar.setBackCall(new IBackCall<String, java.util.List<MgtdHeader>>()
        {

            @Override
            public boolean callBack(String options, java.util.List<MgtdHeader> object)
            {
                hintCallBack(object);
                return true;
            }
        });

        mwizMdl = new MwizMdl(userMdl);
        mwizMdl.init();

        tb_KeysList.setModel(mwizMdl.getGridMdl());
        int w = tb_KeysList.getFontMetrics(tb_KeysList.getFont()).stringWidth("99999");
        tb_KeysList.getColumnModel().getColumn(0).setMaxWidth(w);
        tb_KeysList.getColumnModel().getColumn(1).setMaxWidth(40);
        tb_KeysList.getColumnModel().getColumn(1).setCellRenderer(new ImageCR(this));
        tb_KeysList.getColumnModel().getColumn(2).setCellRenderer(new LabelCR(this));

        try
        {
            menuPtn = new MenuPtn(trayPtn, userMdl);
            menuPtn.loadData(new java.io.File(userMdl.getDataDir(), "mwiz.xml"));
            menuPtn.getToolBar("mwiz", tb_ToolBar, rootPane, AppView.mwiz);
            menuPtn.getPopMenu("mwiz", pm_MenuPop);
            menuPtn.getStrokes("mwiz", rootPane);
        }
        catch (Exception e)
        {
            Logs.exception(e);
        }

        fb_FindBar.initData();

        WButtonGroup group = menuPtn.getGroup("order-dir");
        if (group != null)
        {
            group.setSelected(userMdl.getCfg(AppView.mwiz, ConsCfg.CFG_VIEW_LIST_ASC, ConsCfg.DEF_FALSE), true);
        }
        group = menuPtn.getGroup("order-key");
        if (group != null)
        {
            group.setSelected(userMdl.getCfg(AppView.mwiz, ConsCfg.CFG_VIEW_LIST_KEY, "01"), true);
        }

        tb_KeysList.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                // 右键事件处理
                if (evt.isPopupTrigger())
                {
                    showPopupMenu(evt);
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                // 右键事件处理
                if (evt.isPopupTrigger())
                {
                    showPopupMenu(evt);
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                tbKeysListMouseClicked(evt);
            }
        });
        tb_KeysList.addKeyListener(new java.awt.event.KeyAdapter()
        {

            @Override
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER && !evt.isControlDown())
                {
                    showViewPtn();
                }
            }
        });

        safeMdl = mwizMdl.getKeysMdl();

        this.addComponentListener(new java.awt.event.ComponentAdapter()
        {

            @Override
            public void componentResized(java.awt.event.ComponentEvent e)
            {
                if (fb_FindBar.isVisible())
                {
                    moveFindBar();
                }
            }
        });
        return true;
    }

    @Override
    public boolean showData()
    {
        mwizMdl.getGridMdl().listKeysByKind("0");
        hb_HintBar.showInfo("共 " + mwizMdl.getGridMdl().getRowCount() + " 条数据");
        return true;
    }

    @Override
    public boolean endSave()
    {
        return true;
    }

    public void findKeys(String meta)
    {
        lastQry = meta;
        if (Char.isValidate(meta))
        {
            lastOpt = ConsEnv.QUERY_FIND;
            mwizMdl.getGridMdl().listKeysByMeta(meta);
        }
        else
        {
            lastOpt = ConsEnv.QUERY_NORM;
            mwizMdl.getGridMdl().listKeysByKind("0");
        }
        hb_HintBar.showInfo("共 " + mwizMdl.getGridMdl().getRowCount() + " 条数据");
    }

    public void findHint()
    {
        lastOpt = ConsEnv.QUERY_HINT;
        HintMdl hintMdl = userMdl.getHintMdl();
        mwizMdl.getGridMdl().listHint(hintMdl.getTodoList(), hintMdl.getHistList());
    }

    public HintBar getHintPtn()
    {
        return hb_HintBar;
    }

    public void changeLabel(int label)
    {
        mwizMdl.getGridMdl().setKeysLabel(tb_KeysList.getSelectedRow(), label);
    }

    public void changeMajor(int major)
    {
        mwizMdl.getGridMdl().setKeysMajor(tb_KeysList.getSelectedRow(), major);
    }

    public void appendKeys()
    {
        EditPtn editDlg = getEditPtn();
        editDlg.setTitle(Lang.getLang(LangRes.P30F6203, "口令编辑"));
        KeysMdl keysMdl = mwizMdl.getKeysMdl();
        keysMdl.clear();
        editDlg.showData(keysMdl, true);
    }

    public void showEditPtn()
    {
        int row = tb_KeysList.getSelectedRow();
        if (row < 0)
        {
            Lang.showMesg(this, LangRes.P30F6A02, "请选择您要修改的口令！");
            return;
        }

        try
        {
            EditPtn editDlg = getEditPtn();
            editDlg.setTitle(Lang.getLang(LangRes.P30F6203, "口令编辑"));
            KeysMdl keysMdl = mwizMdl.getKeysMdl();
            keysMdl.loadData(mwizMdl.getGridMdl().getKeysAt(row));
            editDlg.showData(keysMdl, true);
        }
        catch (Exception ex)
        {
            Logs.exception(ex);
        }
    }

    public void showViewPtn()
    {
        int row = tb_KeysList.getSelectedRow();
        if (row < 0)
        {
            Lang.showMesg(this, LangRes.P30F6A01, "请选择您要查看的口令！");
            return;
        }

        try
        {
            EditPtn editDlg = getEditPtn();
            editDlg.setTitle(Lang.getLang(LangRes.P30F6202, "口令查看"));
            KeysMdl keysMdl = mwizMdl.getKeysMdl();
            keysMdl.loadData(mwizMdl.getGridMdl().getKeysAt(row));
            editDlg.showData(keysMdl, false);
        }
        catch (Exception ex)
        {
            Logs.exception(ex);
        }
    }

    public void showMailPtn() throws Exception
    {
        int row = tb_KeysList.getSelectedRow();
        if (row < 0)
        {
            return;
        }

        KeysMdl keysMdl = mwizMdl.getKeysMdl();
        keysMdl.loadData(mwizMdl.getGridMdl().getKeysAt(row));

        MailOpt mailOpt = new MailOpt();
        mailOpt.initView();
        mailOpt.initLang();
        java.util.List<I1S2> mailList = keysMdl.wSelect(ConsDat.INDX_MAIL);
        mailOpt.initMail(mailList);
        if (mailList.size() < 1)
        {
            Lang.showMesg(this, null, "没有可用的邮件类型数据！");
            return;
        }
        java.util.List<I1S2> userList = keysMdl.wSelect(ConsDat.INDX_TEXT);
        mailOpt.initUser(userList);
        if (userList.size() < 1)
        {
            Lang.showMesg(this, null, "没有可用的文本类型数据！");
            return;
        }
        java.util.List<I1S2> pwdsList = keysMdl.wSelect(ConsDat.INDX_PWDS);
        mailOpt.initPwds(pwdsList);
        if (pwdsList.size() < 1)
        {
            Lang.showMesg(this, null, "没有可用的口令类型数据！");
            return;
        }
        if (javax.swing.JOptionPane.OK_OPTION != javax.swing.JOptionPane.showConfirmDialog(this, mailOpt, "登录确认", javax.swing.JOptionPane.OK_CANCEL_OPTION))
        {
            return;
        }

        String mail = mailList.get(mailOpt.getMail()).getV();
        String user = userList.get(mailOpt.getUser()).getV();
        String pwds = pwdsList.get(mailOpt.getPwds()).getV();

        String host = mail.substring(mail.indexOf('@') + 1);
        if (!com.magicpwd._util.Char.isValidate(host))
        {
            return;
        }

        showProgress();

        final Connect connect = new Connect(mail, pwds);
        connect.setUsername(user);
        if (!connect.useDefault())
        {
            Lang.showMesg(this, null, "查找不到对应的服务信息，如有疑问请与作者联系！");
            hideProgress();
            return;
        }

        new Thread()
        {

            @Override
            public void run()
            {
                readMailList(connect);
            }
        }.start();
    }

    private void readMailList(final Connect connect)
    {
        java.util.List<S1S1> list = null;

        Reader reader = new Reader(connect);
        try
        {
            list = reader.getUnReadMail();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(this, null, exp.getLocalizedMessage());
            hideProgress();
            return;
        }

        if (list == null || list.size() < 1)
        {
            Lang.showMesg(this, null, "没有新邮件信息！");
            hideProgress();
            return;
        }

        MailDlg mailDlg = new MailDlg(this);
        mailDlg.initView();
        mailDlg.initLang();
        mailDlg.initData();
        mailDlg.setBackCall(new IBackCall<String, String>()
        {

            @Override
            public boolean callBack(String options, String object)
            {
                return connect.browseMailOnline();
            }
        });
        mailDlg.showData(list);

        hideProgress();
    }

    private void hintCallBack(java.util.List<MgtdHeader> object)
    {
        javax.swing.AbstractButton button = getMenuPtn().getButton("hint");
        if (button != null)
        {
            button.setSelected(true);
        }
        lastOpt = ConsEnv.QUERY_HINT;
        mwizMdl.getGridMdl().listHint(object);
    }

    private void moveFindBar()
    {
        java.awt.Dimension size = fb_FindBar.getPreferredSize();
        fb_FindBar.setBounds(this.getContentPane().getSize().width - size.width - 10, 0, size.width, size.height);
    }

    public void deleteKeys()
    {
        int row = tb_KeysList.getSelectedRow();
        if (row < 0)
        {
            Lang.showMesg(this, LangRes.P30F6A03, "请选择您要删除的口令！");
            return;
        }

        try
        {
            mwizMdl.getGridMdl().wDelete(row);
            mwizMdl.getKeysMdl().clear();
        }
        catch (Exception ex)
        {
            Logs.exception(ex);
        }
    }

    @Override
    public void requestFocus()
    {
        tb_KeysList.requestFocus();
    }

    public void setFindVisible(boolean visible)
    {
        if (visible)
        {
            fb_FindBar.setVisible(true);
            moveFindBar();
            fb_FindBar.requestFocus();
        }
        else
        {
            fb_FindBar.setVisible(false);
            tb_KeysList.requestFocus();
        }
    }

    private EditPtn getEditPtn()
    {
        if (editPtn == null)
        {
            editPtn = new EditPtn(this);
            editPtn.initView();
            editPtn.initLang();
            editPtn.initData();
        }
        return editPtn;
    }

    public void findLast()
    {
        if (lastOpt == ConsEnv.QUERY_HINT)
        {
            findHint();
        }
        else if (lastOpt == ConsEnv.QUERY_FIND)
        {
            findKeys(lastQry);
        }
        else
        {
            showData();
        }
    }

    @Override
    public MenuPtn getMenuPtn()
    {
        return menuPtn;
    }

    public void deCrypt(java.io.File src, java.io.File dst) throws Exception
    {
        mwizMdl.getGridMdl().deCrypt(src, dst);
    }

    public void enCrypt(java.io.File src, java.io.File dst) throws Exception
    {
        mwizMdl.getGridMdl().enCrypt(src, dst);
    }

    private void tbKeysListMouseClicked(java.awt.event.MouseEvent e)
    {
        if (e.getClickCount() > 1)
        {
            showViewPtn();
        }
    }

    private void showPopupMenu(java.awt.event.MouseEvent evt)
    {
        int row = tb_KeysList.rowAtPoint(evt.getPoint());
        if (row < 0)
        {
            return;
        }

        tb_KeysList.setRowSelectionInterval(row, row);
        pm_MenuPop.show(tb_KeysList, evt.getX(), evt.getY());
        showKeysInfo(mwizMdl.getGridMdl().getKeysAt(row));
    }

    private void showKeysInfo(MpwdHeader keys)
    {
        WButtonGroup group = menuPtn.getGroup("label");
        if (group != null)
        {
            group.setSelected(Integer.toString(keys.getP30F0102()), true);
        }

        group = menuPtn.getGroup("major");
        if (group != null)
        {
            group.setSelected(Integer.toString(keys.getP30F0103()), true);
        }
    }
    private HintBar hb_HintBar;
    private FindBar fb_FindBar;
    private javax.swing.JTable tb_KeysList;
    private javax.swing.JToolBar tb_ToolBar;
    private javax.swing.JPopupMenu pm_MenuPop;
}
