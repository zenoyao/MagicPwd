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
package com.magicpwd._bean;

import com.magicpwd.__a.AAction;
import com.magicpwd.__a.AEditBean;
import com.magicpwd.__a.AMpwdPtn;
import com.magicpwd.__i.docs.IDocsViewer;
import com.magicpwd._comn.item.FileItem;
import com.magicpwd._comp.BtnLabel;
import com.magicpwd._comp.WTextBox;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Char;
import com.magicpwd._util.Desk;
import com.magicpwd._util.File;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import java.lang.reflect.Constructor;
import javax.swing.AbstractButton;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-10-27 20:22:02
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public abstract class AFileBean extends AEditBean
{

    protected java.io.File filePath;
    protected java.io.File amaPath;
    private WTextBox dataBox;
    private static java.util.HashMap<String, String> extList;

    public AFileBean(AMpwdPtn mpwdPtn)
    {
        this.formPtn = mpwdPtn;
        extList = new java.util.HashMap<String, String>();
    }

    protected void initConfView()
    {
        tf_PropData = new javax.swing.JTextField();
        dataBox = new WTextBox(tf_PropData, true);
        dataBox.initView();

        pl_PropConf = new javax.swing.JPanel();
        pl_PropConf.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 0));

        bt_FileView = new BtnLabel();
        bt_FileView.setIcon(formPtn.getFeelIcon(null, "var:file-preview"));
        bt_FileView.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_FileViewActionPerformed(evt);
            }
        });
        pl_PropConf.add(bt_FileView);

        bt_FileApnd = new BtnLabel();
        bt_FileApnd.setIcon(formPtn.getFeelIcon(null, "var:file-append"));
        bt_FileApnd.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_FileApndActionPerformed(evt);
            }
        });
        pl_PropConf.add(bt_FileApnd);

        pm_FileView = new javax.swing.JPopupMenu();
        mi_ViewDef = new javax.swing.JMenuItem();
        pm_FileView.add(mi_ViewDef);
        pm_FileView.addSeparator();
    }

    protected void initConfLang()
    {
        Lang.setWText(bt_FileView, LangRes.P30F1515, "@V");
        Lang.setWTips(bt_FileView, LangRes.P30F1516, "打开附件(Alt + V)");

        Lang.setWText(bt_FileApnd, LangRes.P30F1517, "@P");
        Lang.setWTips(bt_FileApnd, LangRes.P30F1518, "添加附件(Alt + P)");

        Lang.setWText(mi_ViewDef, null, "系统默认程序");

        dataBox.initLang();
    }

    protected void initConfData()
    {
        java.awt.event.ActionListener osAction = new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                mi_ViewOsActionPerformed(e);
            }
        };
        mi_ViewDef.addActionListener(osAction);

        AAction asAction = new AAction()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                mi_ViewAsActionPerformed(e);
            }

            @Override
            public void doInit(String value)
            {
            }

            @Override
            public void reInit(AbstractButton button, String value)
            {
                initExts(value);
            }
        };
        formPtn.getMenuPtn().getSubMenu("file-preview", pm_FileView, asAction);
    }

    protected void showConfData()
    {
        if (amaPath == null)
        {
            amaPath = new java.io.File(formPtn.getUserMdl().getDataDir(), ConsEnv.DIR_AMA);
            if (!amaPath.exists())
            {
                amaPath.mkdirs();
            }
        }
    }

    private void initExts(String value)
    {
        if (!Char.isValidate(value))
        {
            return;
        }
        int mi = value.indexOf(':');
        if (mi < 1)
        {
            return;
        }
        String type = value.substring(0, mi);
        value = value.substring(mi + 1).replaceAll("\\s+", "").toUpperCase();
        for (String ext : value.split(","))
        {
            extList.put(ext.replaceAll("^\\.|\\.$", ""), type);
        }
    }

    protected abstract void deCrypt(java.io.File src, java.io.File dst) throws Exception;

    protected abstract void enCrypt(java.io.File src, java.io.File dst) throws Exception;

    private void bt_FileViewActionPerformed(java.awt.event.ActionEvent evt)
    {
        pm_FileView.show(bt_FileView, 0, bt_FileView.getSize().height);
    }

    private void bt_FileApndActionPerformed(java.awt.event.ActionEvent evt)
    {
        javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
        jfc.setMultiSelectionEnabled(false);
        jfc.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        if (filePath != null)
        {
            jfc.setSelectedFile(filePath);
        }
        int status = jfc.showOpenDialog(formPtn);
        if (status != javax.swing.JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        filePath = jfc.getSelectedFile();
        tf_PropData.setText(filePath.getName());
    }

    private void mi_ViewOsActionPerformed(java.awt.event.ActionEvent evt)
    {
        java.io.File file = deCryptData();
        if (file == null)
        {
            return;
        }

        if (!Desk.open(file))
        {
            Lang.showMesg(formPtn, LangRes.P30F1A03, "打开文件错误，请尝试手动方式查看！");
        }
    }

    private void mi_ViewAsActionPerformed(java.awt.event.ActionEvent evt)
    {
        String ext = itemData.getSpec(FileItem.SPEC_FILE_EXTS, "").toUpperCase();
        if (!extList.containsKey(ext))
        {
            Lang.showMesg(formPtn, null, "查看器无法打开 {0} 类型的文件！", ext);
            return;
        }

        java.io.File file = deCryptData();
        if (file == null)
        {
            return;
        }

        try
        {
            Constructor c = Class.forName(extList.get(ext)).getConstructor(AMpwdPtn.class);
            if (c != null)
            {
                Object obj = c.newInstance(formPtn);
                if (obj != null && obj instanceof IDocsViewer)
                {
                    IDocsViewer viewer = (IDocsViewer) obj;
                    viewer.show(file);
                }
            }
        }
        catch (Exception ex)
        {
            Logs.exception(ex);
        }
    }

    protected java.io.File deCryptData()
    {
        try
        {
            java.io.File tmpPath = new java.io.File(System.getProperty("java.io.tmpdir"));
            if (!tmpPath.exists() || !tmpPath.canWrite())
            {
                tmpPath = new java.io.File("./tmp/");
                if (!tmpPath.exists() || !tmpPath.isDirectory())
                {
                    tmpPath.mkdirs();
                }
            }

            java.io.File srcFile = new java.io.File(amaPath, itemData.getSpec(FileItem.SPEC_FILE_NAME) + ConsEnv.FILE_ATTACHMENT);
            java.io.File tmpFile = new java.io.File(tmpPath, itemData.getData());
            deCrypt(srcFile, tmpFile);

            return tmpFile;
        }
        catch (Exception exp)
        {
            Lang.showMesg(formPtn, null, exp.getLocalizedMessage());
            Logs.exception(exp);
            return null;
        }
    }

    protected boolean enCryptData()
    {
        String file = itemData.getSpec(FileItem.SPEC_FILE_NAME);
        if (filePath != null)
        {
            if (!filePath.exists())
            {
                Lang.showMesg(formPtn, LangRes.P30F7A03, "");
                tf_PropData.requestFocus();
                return false;
            }
            if (!filePath.isFile())
            {
                Lang.showMesg(formPtn, LangRes.P30F7A04, "");
                tf_PropData.requestFocus();
                return false;
            }
            if (!filePath.canRead())
            {
                Lang.showMesg(formPtn, LangRes.P30F7A05, "");
                tf_PropData.requestFocus();
                return false;
            }
            if (filePath.length() > 1048576)
            {
                Lang.showMesg(formPtn, LangRes.P30F7A06, "");
                tf_PropData.requestFocus();
                return false;
            }

            if (!com.magicpwd._util.Char.isValidate(file))
            {
                file = com.magicpwd._util.Char.lPad(Long.toHexString(System.currentTimeMillis()), 16, '0');
            }
            try
            {
                java.io.File amaFile = new java.io.File(amaPath, file + ConsEnv.FILE_ATTACHMENT);
                if (!amaFile.exists())
                {
                    if (!amaFile.createNewFile())
                    {
                        Lang.showMesg(formPtn, LangRes.P30F7A2C, "文件上传保存出错，请重试！");
                        return false;
                    }
                }
                enCrypt(filePath, amaFile);
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
                Lang.showMesg(formPtn, LangRes.P30F7A2C, "文件上传保存出错，请重试！");
                return false;
            }
        }

        itemData.setData(tf_PropData.getText());
        itemData.setSpec(FileItem.SPEC_FILE_NAME, file);
        itemData.setSpec(FileItem.SPEC_FILE_EXTS, filePath != null ? File.getExtension(filePath.getName()) : "");
        return true;
    }
    protected javax.swing.JTextField tf_PropData;
    // 配置信息
    protected javax.swing.JPanel pl_PropConf;
    private BtnLabel bt_FileApnd;
    private BtnLabel bt_FileView;
    private javax.swing.JPopupMenu pm_FileView;
    private javax.swing.JMenuItem mi_ViewDef;
}
