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
package com.magicpwd.v.app.mgtd;

import com.magicpwd.__a.AMpwdPtn;
import com.magicpwd.__i.IBackCall;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._enum.AppView;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.UserMdl;
import com.magicpwd.m.mgtd.MgtdMdl;
import com.magicpwd.v.app.MenuPtn;
import com.magicpwd.v.app.tray.TrayPtn;
import com.magicpwd.x.app.mgtd.MgtdDlg;

/**
 *
 * @author Amon
 */
public class MgtdPtn extends AMpwdPtn
{

    private MgtdMdl mgtdMdl;
    private MenuPtn menuPtn;

    public MgtdPtn(TrayPtn trayPtn, UserMdl userMdl)
    {
        super(trayPtn, userMdl);
    }

    @Override
    public boolean initView()
    {
        tbTaskTool = new javax.swing.JToolBar();
        tbTaskTool.setRollover(true);
        this.getContentPane().add(tbTaskTool, java.awt.BorderLayout.NORTH);

        plTaskList = new javax.swing.JPanel();
        this.getContentPane().add(plTaskList, java.awt.BorderLayout.CENTER);

        tbTaskList = new javax.swing.JTable();
        javax.swing.JScrollPane spTaskList = new javax.swing.JScrollPane(tbTaskList);

        pmTaskMenu = new javax.swing.JPopupMenu();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(plTaskList);
        plTaskList.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addComponent(spTaskList, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE);
        hsg.addContainerGap();
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(hsg));

        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        vsg.addComponent(spTaskList, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE);
        vsg.addContainerGap();
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
        setTitle("任务管理");
        return true;
    }

    @Override
    public boolean initData()
    {
        mgtdMdl = new MgtdMdl(userMdl);
        mgtdMdl.init();

        tbTaskList.setModel(mgtdMdl.getGridMdl());
        tbTaskList.getColumnModel().getColumn(0).setMaxWidth(tbTaskList.getFontMetrics(tbTaskList.getFont()).stringWidth("6666"));

        tbTaskList.addMouseListener(new java.awt.event.MouseAdapter()
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

        try
        {
            menuPtn = new MenuPtn(trayPtn, userMdl);
            menuPtn.loadData(new java.io.File(userMdl.getDataDir(), "mgtd.xml"));

            menuPtn.getPopMenu("grid", pmTaskMenu);
            menuPtn.getToolBar("mgtd", tbTaskTool, rootPane, AppView.mgtd);
            menuPtn.getStrokes("mgtd", rootPane);
        }
        catch (Exception e)
        {
            Logs.exception(e);
        }
        return true;
    }

    @Override
    public boolean showData()
    {
        return true;
    }

    @Override
    public MenuPtn getMenuPtn()
    {
        return menuPtn;
    }

    @Override
    public boolean endSave()
    {
        return true;
    }

    @Override
    public void requestFocus()
    {
    }

    public void appendMgtd()
    {
        MgtdDlg dlg = new MgtdDlg(this, true);
        dlg.setBackCall(new IBackCall<String, String>()
        {

            @Override
            public boolean callBack(String options, String object)
            {
                mgtdMdl.getGridMdl().wReload();
                return true;
            }
        });
        dlg.initView();
        dlg.initLang();
        dlg.initData(null);
    }

    public void saveMgtd(MgtdHeader mgtd)
    {
        mgtdMdl.getGridMdl().wAppend(mgtd);
    }

    public void updateMgtd()
    {
        int row = tbTaskList.getSelectedRow();
        if (row < 0)
        {
            return;
        }

        MgtdHeader mgtd = mgtdMdl.getGridMdl().getMgtdAt(row);

        DBA4000.listGtdDetail(userMdl, mgtd);

        MgtdDlg dlg = new MgtdDlg(this, true);
        dlg.setBackCall(new IBackCall<String, String>()
        {

            @Override
            public boolean callBack(String options, String object)
            {
                mgtdMdl.getGridMdl().wReload();
                return true;
            }
        });
        dlg.initView();
        dlg.initLang();
        dlg.initData(mgtd);
    }

    public void deleteMgtd()
    {
        int row = tbTaskList.getSelectedRow();
        if (row < 0)
        {
            return;
        }

        if (Lang.showFirm(this, null, "确认要删除吗？此操作可能会影响到其它计划任务！") != javax.swing.JOptionPane.YES_OPTION)
        {
            return;
        }
        mgtdMdl.getGridMdl().wDelete(row);
    }

    private void showPopupMenu(java.awt.event.MouseEvent evt)
    {
        int row = tbTaskList.rowAtPoint(evt.getPoint());
        if (row < 0)
        {
            return;
        }

        tbTaskList.setRowSelectionInterval(row, row);
        pmTaskMenu.show(tbTaskList, evt.getX(), evt.getY());
    }

    private void tbKeysListMouseClicked(java.awt.event.MouseEvent e)
    {
        if (e.getClickCount() > 1)
        {
            updateMgtd();
        }
    }
    private javax.swing.JTable tbTaskList;
    private javax.swing.JToolBar tbTaskTool;
    private javax.swing.JPanel plTaskList;
    private javax.swing.JPopupMenu pmTaskMenu;
}
