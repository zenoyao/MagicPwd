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
package com.magicpwd.x.app.icon;

import com.magicpwd.__a.ADialog;
import com.magicpwd.__i.IBackCall;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.m.UserMdl;
import com.magicpwd.r.AmonFF;
import java.util.Properties;

/**
 * 图标管理对话窗口
 * @author Amon
 */
public class IcoDialog extends ADialog
{

    private IcoModel icoModel;
    private java.io.File filePath;
    private java.io.File iconHome;
    private String iconPath;
    private String iconHash;
    private UserMdl userMdl;
    private int iconSize;
    private java.awt.Window window;
    private IBackCall<String, String> backCall;

    public IcoDialog(java.awt.Window window, UserMdl userMdl, IBackCall<String, String> backCall)
    {
        super(window, true);
        this.window = window;
        this.userMdl = userMdl;
        this.backCall = backCall;
    }

    public void initView()
    {
        plCatePane = new javax.swing.JPanel();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(plCatePane);
        plCatePane.setLayout(layout);

        btScrollL = new javax.swing.JLabel();
        btScrollR = new javax.swing.JLabel();

        plCateList = new javax.swing.JPanel();
        plCateList.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
        spCateList = new javax.swing.JScrollPane(plCateList);
        spCateList.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spCateList.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        spCateList.setBorder(null);

        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addComponent(btScrollL, 16, 16, 16);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(spCateList, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(btScrollR, 16, 16, 16);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false);
        vpg.addComponent(btScrollL, 16, 16, 16);
        vpg.addComponent(spCateList, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        vpg.addComponent(btScrollR, 16, 16, 16);
        layout.setVerticalGroup(vpg);

        btRemove = new javax.swing.JButton();
        btAppend = new javax.swing.JButton();
        btSelect = new javax.swing.JButton();

        tbIconGrid = new javax.swing.JTable();
        tbIconGrid.setTableHeader(null);
        tbIconGrid.setCellSelectionEnabled(true);
        tbIconGrid.setShowHorizontalLines(false);
        tbIconGrid.setShowVerticalLines(false);
        tbIconGrid.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        javax.swing.JScrollPane spIconGrid = new javax.swing.JScrollPane();
        spIconGrid.setViewportView(tbIconGrid);

        layout = new javax.swing.GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(btSelect);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(btAppend);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(btRemove);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(plCatePane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE);
        hpg1.addComponent(spIconGrid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE);
        hpg1.addGroup(hsg1);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addContainerGap();
        hsg2.addGroup(hpg1);
        hsg2.addContainerGap();
        layout.setHorizontalGroup(hsg2);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(btRemove);
        vpg1.addComponent(btAppend);
        vpg1.addComponent(btSelect);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addContainerGap();
        vsg1.addComponent(plCatePane, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg1.addComponent(spIconGrid, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg1);
        vsg1.addContainerGap();
        layout.setVerticalGroup(vsg1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(Bean.getLogo(16));
        setResizable(false);
        pack();
        Bean.centerForm(this, window);
    }

    public void initLang()
    {
        Lang.setWText(btSelect, LangRes.P30FA50C, "选择(@C)");

        Lang.setWText(btAppend, LangRes.P30FA50D, "追加(@A)");

        Lang.setWText(btRemove, null, "删除(@D)");

        this.setTitle(Lang.getLang(LangRes.P30FA50F, "徽标"));
    }

    public void initData()
    {
        btScrollL.setIcon(userMdl.getFeelFav("edit-move-left", "var:edit-move-left"));
        btScrollR.setIcon(userMdl.getFeelFav("edit-move-right", "var:edit-move-right"));

        iconHome = new java.io.File(userMdl.getDataDir(), ConsEnv.DIR_ICO);

        icoModel = new IcoModel(userMdl);
        tbIconGrid.setModel(icoModel);
        tbIconGrid.setRowHeight(icoModel.getRowHeight());

        javax.swing.table.TableCellRenderer renderer = new javax.swing.table.TableCellRenderer()
        {

            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                if (!(value instanceof javax.swing.JLabel))
                {
                    return null;
                }

                javax.swing.JLabel label = (javax.swing.JLabel) value;
                // 前景及背景颜色设置
                if (isSelected)
                {
                    label.setBackground(table.getSelectionBackground());
                    label.setForeground(table.getSelectionForeground());
                }
                else
                {
                    label.setBackground(table.getBackground());
                    label.setForeground(table.getForeground());
                }

                // 文字属性设置
                label.setFont(table.getFont());
                // 可编辑状态设置
                label.setEnabled(table.isEnabled());
                return label;
            }
        };
        java.util.Enumeration<javax.swing.table.TableColumn> columns = tbIconGrid.getColumnModel().getColumns();
        while (columns.hasMoreElements())
        {
            columns.nextElement().setCellRenderer(renderer);
        }

        btScrollL.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btScrollLActionPerformed(evt);
            }
        });

        btScrollR.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btScrollRActionPerformed(evt);
            }
        });

        btAppend.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btAppendActionPerformed(evt);
            }
        });

        btSelect.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btSelectActionPerformed(evt);
            }
        });

        btRemove.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btRemoveActionPerformed(evt);
            }
        });
    }

    @Override
    protected boolean hideDialog()
    {
        setVisible(false);
        dispose();
        return true;
    }

    public void showData(final String iconPath, String hash, int size)
    {
        this.iconHash = hash;
        this.iconSize = size;
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                listCat(iconPath);
                listIco(iconPath, iconSize);
            }
        });
    }

    public synchronized void listIco(String iconPath, int iconSize)
    {
        icoModel.loadIco(new java.io.File(iconHome, iconPath), iconHash, iconSize);
        tbIconGrid.setRowSelectionInterval(icoModel.getSelectedRow(), icoModel.getSelectedRow());
        tbIconGrid.setColumnSelectionInterval(icoModel.getSelectedColumn(), icoModel.getSelectedColumn());
    }

    public synchronized void listCat(String iconPath)
    {
        java.awt.event.MouseAdapter listener = new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                TabLabel label = (TabLabel) evt.getSource();
                label.setRollover(true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                TabLabel label = (TabLabel) evt.getSource();
                label.setRollover(false);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                changeCategory(evt);
            }
        };

        Properties prop = new Properties();
        try
        {
            java.io.InputStreamReader reader = new java.io.InputStreamReader(new java.io.FileInputStream(new java.io.File(iconHome, "ico.properties")), ConsEnv.FILE_ENCODING);
            prop.load(reader);
            reader.close();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }

        TabLabel label = new TabLabel("", prop.getProperty(".", "默认"));
        if (!Char.isValidate(iconPath))
        {
            label.setSelected(true);
            lbCateLast = label;
        }
        label.addMouseListener(listener);
        plCateList.add(label);

        if (!iconHome.exists())
        {
            return;
        }

        String tmp;
        for (java.io.File file : iconHome.listFiles())
        {
            if (file == null || !file.exists() || !file.isDirectory() || !file.canRead())
            {
                continue;
            }
            tmp = file.getName();
            if (!Char.isValidate(file.getName(), 1, 64))
            {
                continue;
            }
            label = new TabLabel(tmp, prop.getProperty(tmp, tmp));
            if (iconPath.equals(tmp))
            {
                label.setSelected(true);
                lbCateLast = label;
            }
            label.addMouseListener(listener);
            plCateList.add(label);
        }
    }

    private void changeCategory(java.awt.event.MouseEvent evt)
    {
        TabLabel label = (TabLabel) evt.getSource();
        if (label == null)
        {
            return;
        }

        label.setSelected(true);
        iconPath = label.getKey();
        listIco(iconPath, iconSize);

        if (lbCateLast != null)
        {
            lbCateLast.setSelected(false);
        }
        lbCateLast = label;
    }

    private void btScrollLActionPerformed(java.awt.event.MouseEvent evt)
    {
        java.awt.Rectangle rect = spCateList.getVisibleRect();
        rect.x -= 10;
        spCateList.getViewport().scrollRectToVisible(rect);
    }

    private void btScrollRActionPerformed(java.awt.event.MouseEvent evt)
    {
        java.awt.Rectangle rect = spCateList.getVisibleRect();
        rect.x += 10;
        spCateList.getViewport().scrollRectToVisible(rect);
    }

    private void btSelectActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (backCall.callBack(icoModel.getSelectedIcon(tbIconGrid.getSelectedRow(), tbIconGrid.getSelectedColumn()), iconPath))
        {
            this.setVisible(false);
            this.dispose();
        }
    }

    private void btAppendActionPerformed(java.awt.event.ActionEvent evt)
    {
        javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
        AmonFF ff = new AmonFF("[^\\.]+\\." + ConsEnv.IMAGE_FORMAT + "$", false);
        ff.setIncludeDir(true);
        ff.setDescription('.' + ConsEnv.IMAGE_FORMAT);
        jfc.setFileFilter(ff);
        jfc.setMultiSelectionEnabled(false);
        jfc.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        if (filePath != null)
        {
            jfc.setSelectedFile(filePath);
        }
        if (jfc.showOpenDialog(this) != javax.swing.JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        filePath = jfc.getSelectedFile();
        if (!filePath.exists())
        {
            Lang.showMesg(this, LangRes.P30F7A03, "您选取的文件不存在！");
            return;
        }
        if (!filePath.isFile() || !filePath.canRead())
        {
            Lang.showMesg(this, LangRes.P30F7A05, "无法读取您选择的文件，请确认您是否有足够的权限！");
            return;
        }

        try
        {
            icoModel.appendIcon(filePath, Char.isValidate(iconPath) ? new java.io.File(iconHome, iconPath) : iconHome);
            tbIconGrid.setRowSelectionInterval(icoModel.getSelectedRow(), icoModel.getSelectedRow());
            tbIconGrid.setColumnSelectionInterval(icoModel.getSelectedColumn(), icoModel.getSelectedColumn());
        }
        catch (Exception exp)
        {
            Lang.showMesg(this, null, exp.getLocalizedMessage());
            Logs.exception(exp);
            return;
        }
    }

    private void btRemoveActionPerformed(java.awt.event.ActionEvent evt)
    {
    }
    private javax.swing.JButton btAppend;
    private javax.swing.JButton btSelect;
    private javax.swing.JButton btRemove;
    private javax.swing.JLabel btScrollL;
    private javax.swing.JLabel btScrollR;
    private javax.swing.JPanel plCatePane;
    private TabLabel lbCateLast;
    private javax.swing.JPanel plCateList;
    private javax.swing.JScrollPane spCateList;
    private javax.swing.JTable tbIconGrid;
}
