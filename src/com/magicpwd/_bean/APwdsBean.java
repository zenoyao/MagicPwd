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

import com.magicpwd.__a.AEditBean;
import com.magicpwd.__a.AMpwdPtn;
import com.magicpwd._comn.item.PwdsItem;
import com.magicpwd._comn.prop.Mucs;
import com.magicpwd._comp.BtnLabel;
import com.magicpwd._comp.WButtonGroup;
import com.magicpwd._cons.ConsCfg;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd._util.Util;
import javax.swing.JOptionPane;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 * 属性：口令
 * 键值：ConsEnv.INDX_PWDS
 */
public abstract class APwdsBean extends AEditBean
{

    private boolean askOverWrite;

    public APwdsBean(AMpwdPtn mpwdPtn)
    {
        this.formPtn = mpwdPtn;
    }

    protected void initConfView()
    {
        pfPropData = new javax.swing.JPasswordField();
        pfPropData.setEchoChar(ConsEnv.PWDS_MASK);

        plPropConf = new javax.swing.JPanel();
        plPropConf.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 0));

        btPwdsView = new BtnLabel();
        btPwdsView.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btPwdsViewActionPerformed(evt);
            }
        });
        plPropConf.add(btPwdsView);

        btPwdsGent = new BtnLabel();
        btPwdsGent.setIcon(formPtn.getFeelIcon("pwds-generate", "var:pwds-generate"));
        btPwdsGent.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btPwdsGentActionPerformed(evt);
            }
        });
        plPropConf.add(btPwdsGent);

        btPwdsConf = new BtnLabel();
        btPwdsConf.setIcon(formPtn.getFeelIcon("pwds-options", "var:pwds-options"));
        btPwdsConf.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btPwdsConfActionPerformed(evt);
            }
        });
        plPropConf.add(btPwdsConf);

        pmConfMenu = new javax.swing.JPopupMenu();
        muSizeMenu = new javax.swing.JMenu();
        pmConfMenu.add(muSizeMenu);
        muCharMenu = new javax.swing.JMenu();
        pmConfMenu.add(muCharMenu);

        initSizeView();
        initUrptView();
        initCharMenu();
    }

    private void initSizeView()
    {
        bgSizeGroup = new WButtonGroup();
        java.awt.event.ActionListener al = new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miSizeMenuActionPerformed(evt);
            }
        };

        // 默认
        miSizeDef = new javax.swing.JCheckBoxMenuItem();
        miSizeDef.addActionListener(al);
        muSizeMenu.add(miSizeDef);
        bgSizeGroup.add("0", miSizeDef);

        muSizeMenu.addSeparator();

        miSizeNum = new javax.swing.JCheckBoxMenuItem[6];
        javax.swing.JCheckBoxMenuItem menuItem;
        for (int i = 0, j = miSizeNum.length; i < j; i += 1)
        {
            menuItem = new javax.swing.JCheckBoxMenuItem();
            menuItem.addActionListener(al);
            muSizeMenu.add(menuItem);
            miSizeNum[i] = menuItem;
        }

        muSizeMenu.addSeparator();

        // 其它...
        miSizeMore = new javax.swing.JCheckBoxMenuItem();
        miSizeMore.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miSizeMoreActionPerformed(evt);
            }
        });
        muSizeMenu.add(miSizeMore);
    }

    private void initUrptView()
    {
        miLoopMenu = new javax.swing.JCheckBoxMenuItem();
        miLoopMenu.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miUrptMenuActionPerformed(evt);
            }
        });
        pmConfMenu.add(miLoopMenu);
        miLoopMenu.setSelected(formPtn.getUserMdl().isPwdsLoop());
    }

    protected void initConfLang()
    {
        Lang.setWText(btPwdsView, LangRes.P30F1507, "@M");
        Lang.setWTips(btPwdsView, LangRes.P30F1508, "点击显示口令(Alt + M)");

        Lang.setWText(btPwdsGent, LangRes.P30F150B, "@G");
        Lang.setWTips(btPwdsGent, LangRes.P30F150C, "生成口令(Alt + G)");

        Lang.setWText(btPwdsConf, LangRes.P30F150D, "@O");
        Lang.setWTips(btPwdsConf, LangRes.P30F150E, "口令设置(Alt + O)");

        initSizeLang();
        initCharLang();
        initNrptLang();
    }

    private void initSizeLang()
    {
        Lang.setWText(muSizeMenu, LangRes.P30F7C01, "口令长度");

        Lang.setWText(miSizeDef, LangRes.P30F7C02, "默认");
        miSizeDef.setActionCommand("0");

        String t = Lang.getLang(LangRes.P30F7C03, "位");
        String[] text =
        {
            "6", "8", "12", "16", "24", "32"
        };
        String[] keys =
        {
            "6", "8", "12", "16", "24", "32"
        };

        javax.swing.JCheckBoxMenuItem menuItem;
        for (int i = 0; i < 6; i += 1)
        {
            menuItem = miSizeNum[i];
            menuItem.setText(text[i] + t);
            menuItem.setToolTipText(text[i] + t);
            menuItem.setActionCommand(keys[i]);
            bgSizeGroup.add(keys[i], menuItem);
        }

        Lang.setWText(miSizeMore, LangRes.P30F7C04, "其它...");
        bgSizeGroup.add("-1", miSizeMore);
    }

    private void initCharLang()
    {
        Lang.setWText(muCharMenu, LangRes.P30F7C05, "字符空间");
    }

    private void initNrptLang()
    {
        Lang.setWText(miLoopMenu, LangRes.P30F7C07, "允许重复");
    }

    protected void initConfData()
    {
        changeView(true);
    }

    protected void showConfData()
    {
        String size = itemData.getSpec(PwdsItem.SPEC_PWDS_SIZE, "0");
        if (!com.magicpwd._util.Char.isValidatePositiveInteger(size))
        {
            size = "0";
        }
        if (!bgSizeGroup.setSelected(size, true))
        {
            miSizeMore.setSelected(true);
            miSizeMore.setActionCommand(size);
        }
        bgCharGroup.setSelected(itemData.getSpec(PwdsItem.SPEC_PWDS_HASH), true);
        miLoopMenu.setSelected(PwdsItem.SPEC_VALUE_TRUE.equals(itemData.getSpec(PwdsItem.SPEC_PWDS_LOOP)));
    }

    private void btPwdsConfActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (formPtn.getUserMdl().isUcsTemplateUpdated())
        {
            initCharMenu();
            formPtn.getUserMdl().setUcsTemplateUpdated(false);
        }
        showConfData();
        pmConfMenu.show(btPwdsConf, 0, btPwdsConf.getSize().height);
    }

    private void btPwdsGentActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (askOverWrite && pfPropData.getPassword().length > 1)
        {
            if (Lang.showFirm(formPtn, "", "") != javax.swing.JOptionPane.YES_OPTION)
            {
                return;
            }
            askOverWrite = false;
        }

        try
        {
            String c = bgCharGroup.getSelection().getActionCommand();
            c = getPwdsChar(c);
            String s = bgSizeGroup.getSelection().getActionCommand();
            if (!com.magicpwd._util.Char.isValidatePositiveInteger(s))
            {
                s = ConsCfg.DEF_PWDS_SIZE;
            }
            pfPropData.setText(new String(Util.nextRandomKey(c, Integer.parseInt(s), miLoopMenu.isSelected())));
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(formPtn, null, exp.getLocalizedMessage());
        }
    }

    private void btPwdsViewActionPerformed(java.awt.event.ActionEvent evt)
    {
        changeView(pfPropData.getEchoChar() == 0);
    }

    private void miSizeMenuActionPerformed(java.awt.event.ActionEvent evt)
    {
        String t = evt.getActionCommand();
        if (!com.magicpwd._util.Char.isValidatePositiveInteger(t))
        {
            t = "0";
            miSizeDef.setSelected(true);
        }

        itemData.setSpec(PwdsItem.SPEC_PWDS_SIZE, t);
    }

    private void miSizeMoreActionPerformed(java.awt.event.ActionEvent evt)
    {
        String s = JOptionPane.showInputDialog(formPtn, "", itemData.getSpec(PwdsItem.SPEC_PWDS_SIZE, ConsCfg.DEF_PWDS_SIZE));
        if (s == null)
        {
            return;
        }
        s = s.trim();
        if (!com.magicpwd._util.Char.isValidatePositiveInteger(s))
        {
            Lang.showMesg(formPtn, "", "请输入一个有效的自然数！");
            s = ConsCfg.DEF_PWDS_SIZE;
            miSizeDef.setSelected(true);
            return;
        }

        miSizeMore.setActionCommand(s);
        itemData.setSpec(PwdsItem.SPEC_PWDS_SIZE, s);
    }

    private void miCharMenuActionPerformed(java.awt.event.ActionEvent evt)
    {
        String t = evt.getActionCommand();
        if (!com.magicpwd._util.Char.isValidateHash(t))
        {
            return;
        }

        itemData.setSpec(PwdsItem.SPEC_PWDS_HASH, t);
    }

    private void miUrptMenuActionPerformed(java.awt.event.ActionEvent evt)
    {
        itemData.setSpec(PwdsItem.SPEC_PWDS_LOOP, miLoopMenu.isSelected() ? PwdsItem.SPEC_VALUE_TRUE : PwdsItem.SPEC_VALUE_FAIL);
    }

    private void changeView(boolean mask)
    {
        if (mask)
        {
            btPwdsView.setIcon(formPtn.getFeelIcon("pwds-mask", "var:pwds-mask"));
            pfPropData.setEchoChar(ConsEnv.PWDS_MASK);
            Lang.setWText(btPwdsView, LangRes.P30F1507, "@M");
            Lang.setWTips(btPwdsView, LangRes.P30F1508, "点击显示口令(Alt + M)");
        }
        else
        {
            btPwdsView.setIcon(formPtn.getFeelIcon("pwds-view", "var:pwds-view"));
            pfPropData.setEchoChar('\0');
            Lang.setWText(btPwdsView, LangRes.P30F1509, "@M");
            Lang.setWTips(btPwdsView, LangRes.P30F150A, "点击隐藏口令(Alt + M)");
        }
    }

    private String getPwdsChar(String hash)
    {
        if (!com.magicpwd._util.Char.isValidateHash(hash))
        {
            return ConsCfg.DEF_PWDS_CHAR;
        }
        for (Mucs item : formPtn.getUserMdl().getCharMdl().getCharSys())
        {
            if (hash.equals(item.getP30F2103()))
            {
                return item.getP30F2106();
            }
        }
        for (Mucs item : formPtn.getUserMdl().getCharMdl().getCharUsr())
        {
            if (hash.equals(item.getP30F2103()))
            {
                return item.getP30F2106();
            }
        }
        return ConsCfg.DEF_PWDS_CHAR;
    }

    private void initCharMenu()
    {
        bgCharGroup = new WButtonGroup();
        java.awt.event.ActionListener al = new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miCharMenuActionPerformed(evt);
            }
        };

        miCharDef = new javax.swing.JCheckBoxMenuItem();
        miCharDef.setText(Lang.getLang(LangRes.P30F7C06, "默认"));
        miCharDef.setActionCommand(ConsCfg.DEF_PWDS_HASH);
        miCharDef.addActionListener(al);
        muCharMenu.add(miCharDef);
        bgCharGroup.add("", miCharDef);

        muCharMenu.addSeparator();

        java.util.List<Mucs> list = formPtn.getUserMdl().getCharMdl().getCharSys();
        int cnt = list.size();

        Mucs charItem;
        javax.swing.JCheckBoxMenuItem menuItem;
        for (int i = 0; i < cnt; i += 1)
        {
            charItem = list.get(i);
            menuItem = new javax.swing.JCheckBoxMenuItem();
            menuItem.setText(charItem.getP30F2104());
            menuItem.setToolTipText(charItem.getP30F2105());
            menuItem.setActionCommand(charItem.getP30F2103());
            menuItem.addActionListener(al);
            muCharMenu.add(menuItem);
            bgCharGroup.add(charItem.getP30F2103(), menuItem);
        }

        list = formPtn.getUserMdl().getCharMdl().getCharUsr();
        cnt = list.size();
        if (cnt < 1)
        {
            return;
        }
        muCharMenu.addSeparator();

        for (int i = 0; i < cnt; i += 1)
        {
            charItem = list.get(i);
            menuItem = new javax.swing.JCheckBoxMenuItem();
            menuItem.setText(charItem.getP30F2104());
            menuItem.setToolTipText(charItem.getP30F2105());
            menuItem.setActionCommand(charItem.getP30F2103());
            menuItem.addActionListener(al);
            muCharMenu.add(menuItem);
            bgCharGroup.add(charItem.getP30F2103(), menuItem);
        }
    }
    protected javax.swing.JPasswordField pfPropData;
    // 配置信息
    protected javax.swing.JPanel plPropConf;
    private BtnLabel btPwdsConf;
    private BtnLabel btPwdsGent;
    private BtnLabel btPwdsView;
    private javax.swing.JPopupMenu pmConfMenu;
    private javax.swing.JMenu muSizeMenu;
    private javax.swing.JCheckBoxMenuItem miSizeDef;
    private javax.swing.JCheckBoxMenuItem[] miSizeNum;
    private javax.swing.JCheckBoxMenuItem miSizeMore;
    private WButtonGroup bgSizeGroup;
    private javax.swing.JMenu muCharMenu;
    private javax.swing.JCheckBoxMenuItem miCharDef;
    private WButtonGroup bgCharGroup;
    private javax.swing.JCheckBoxMenuItem miLoopMenu;
}
