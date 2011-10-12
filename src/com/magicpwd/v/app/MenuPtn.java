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
package com.magicpwd.v.app;

import com.magicpwd.v.app.tray.TrayPtn;
import com.magicpwd.__i.IAction;
import com.magicpwd.__i.maoc.IMaocAction;
import com.magicpwd.__i.mgtd.IMgtdAction;
import com.magicpwd.__i.mpro.IMproAction;
import com.magicpwd._comp.WButtonGroup;
import com.magicpwd._cons.ConsCfg;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd._util.File;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.__i.mpad.IMpadAction;
import com.magicpwd.__i.mruc.IMrucAction;
import com.magicpwd.__i.mwiz.IMwizAction;
import com.magicpwd.__i.tray.ITrayAction;
import com.magicpwd._enum.AppView;
import com.magicpwd._util.Util;
import com.magicpwd.e.mpro.skin.FeelAction;
import com.magicpwd.e.mpro.skin.LookAction;
import com.magicpwd.e.mpro.skin.MoreAction;
import com.magicpwd.e.mpro.skin.ThemeAction;
import com.magicpwd.m.UserMdl;
import com.magicpwd.r.AmonFF;
import com.magicpwd.v.app.maoc.MaocPtn;
import com.magicpwd.v.app.mpro.MproPtn;
import com.magicpwd.v.app.mgtd.MgtdPtn;
import com.magicpwd.v.app.mpad.MpadPtn;
import com.magicpwd.v.app.mruc.MrucPtn;
import com.magicpwd.v.app.mwiz.MwizPtn;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 *
 * @author Amon
 */
public class MenuPtn
{
    private Document document;
    private TrayPtn trayPtn;
    private UserMdl userMdl;
    private java.util.regex.Pattern pattern;
    private java.util.HashMap<String, javax.swing.AbstractButton> buttons;
    private java.util.HashMap<String, javax.swing.Action> actions;
    private java.util.HashMap<String, WButtonGroup> groups;

    public MenuPtn(TrayPtn trayPtn, UserMdl userMdl)
    {
        this.trayPtn = trayPtn;
        this.userMdl = userMdl;
        buttons = new java.util.HashMap<String, javax.swing.AbstractButton>();
        actions = new java.util.HashMap<String, javax.swing.Action>();
        groups = new java.util.HashMap<String, WButtonGroup>();
    }

    public boolean loadData(String uri) throws Exception
    {
        return loadData(File.open4Read(uri));
    }

    public boolean loadData(java.io.File file) throws Exception
    {
        return loadData(new java.io.FileInputStream(file));
    }

    public boolean loadData(java.io.InputStream stream) throws Exception
    {
        pattern = java.util.regex.Pattern.compile("^[$]P30F[A-F0-9]{4}$", java.util.regex.Pattern.CASE_INSENSITIVE);
        document = new SAXReader().read(stream);
        return document != null;
    }

    public javax.swing.AbstractButton getButton(String id)
    {
        return buttons.get(id);
    }

    public java.awt.event.ActionListener getAction(String id)
    {
        return actions.get(id);
    }

    public WButtonGroup getGroup(String id)
    {
        return groups.get(id);
    }

    public boolean getMenuBar(String menuId, javax.swing.JMenuBar menuBar, javax.swing.JComponent component)
    {
        if (!Char.isValidate(menuId) || document == null)
        {
            return false;
        }
        Node node = document.getRootElement().selectSingleNode(Char.format("/magicpwd/menubar[@id='{0}']", menuId));
        if (node == null || !(node instanceof Element))
        {
            return false;
        }
        Element element = (Element) node;

        java.util.List elementList = element.elements("menu");
        if (elementList == null || elementList.size() < 1)
        {
            return false;
        }

        menuBar.setName(menuId);

        Element tmp;
        for (Object obj : elementList)
        {
            if (!(obj instanceof Element))
            {
                continue;
            }
            tmp = (Element) obj;
            javax.swing.JMenu menu = createMenu(tmp, component, null);
            if (menu == null)
            {
                continue;
            }
            menuBar.add(menu);
        }

        final String KEY_SKIN = "skin";
        if (buttons.containsKey(KEY_SKIN))
        {
            javax.swing.JMenu skin = (javax.swing.JMenu) buttons.get(KEY_SKIN);
            if (skin == null)
            {
                skin = new javax.swing.JMenu();
                menuBar.add(skin);
            }
            loadSkin(skin);
        }
        return true;
    }

    public boolean getToolBar(String toolId, javax.swing.JToolBar toolBar, javax.swing.JComponent component, AppView viewPtn)
    {
        if (!Char.isValidate(toolId) || document == null)
        {
            return false;
        }
        Node node = document.getRootElement().selectSingleNode(Char.format("/magicpwd/toolbar[@id='{0}']", toolId));
        if (node == null || !(node instanceof Element))
        {
            return false;
        }
        Element element = (Element) node;

        java.util.List elementList = element.elements();
        if (elementList == null || elementList.size() < 1)
        {
            return false;
        }

        toolBar.setName(toolId);

        Element tmp;
        for (Object obj : elementList)
        {
            if (!(obj instanceof Element))
            {
                continue;
            }
            tmp = (Element) obj;
            if ("item".equals(tmp.getName()))
            {
                toolBar.add(createButton(tmp, component, viewPtn));
                continue;
            }
            if ("seperator".equals(tmp.getName()))
            {
                toolBar.addSeparator();
                continue;
            }
        }
        return true;
    }

    public boolean getPopMenu(String menuId, javax.swing.JPopupMenu menuPop)
    {
        if (!Char.isValidate(menuId) || document == null)
        {
            return false;
        }
        Node node = document.getRootElement().selectSingleNode(Char.format("/magicpwd/popmenu[@id='{0}']", menuId));
        if (node == null || !(node instanceof Element))
        {
            return false;
        }
        Element element = (Element) node;

        java.util.List elementList = element.elements();
        if (elementList == null || elementList.size() < 1)
        {
            return false;
        }

        menuPop.setName(menuId);

        Element tmp;
        for (Object obj : elementList)
        {
            if (!(obj instanceof Element))
            {
                continue;
            }
            tmp = (Element) obj;
            if ("menu".equals(tmp.getName()))
            {
                menuPop.add(createMenu(tmp, null, null));
                continue;
            }
            if ("item".equals(tmp.getName()))
            {
                menuPop.add(createItem(tmp, null, null));
                continue;
            }
            if ("seperator".equals(tmp.getName()))
            {
                menuPop.addSeparator();
                continue;
            }
        }
        return true;
    }

    public boolean getSubMenu(String partId, javax.swing.JPopupMenu menu, java.awt.event.ActionListener action)
    {
        if (!Char.isValidate(partId) || document == null)
        {
            return false;
        }

        Node node = document.getRootElement().selectSingleNode(Char.format("/magicpwd/submenu[@id='{0}']", partId));
        if (node == null || !(node instanceof Element))
        {
            return false;
        }
        Element element = (Element) node;

        java.util.List elementList = element.elements();
        if (elementList != null)
        {
            Element tmp;
            for (Object obj : elementList)
            {
                if (!(obj instanceof Element))
                {
                    continue;
                }
                tmp = (Element) obj;
                if ("menu".equals(tmp.getName()))
                {
                    menu.add(createMenu(tmp, null, action));
                    continue;
                }
                if ("item".equals(tmp.getName()))
                {
                    menu.add(createItem(tmp, null, action));
                    continue;
                }
                if ("seperator".equals(tmp.getName()))
                {
                    menu.addSeparator();
                    continue;
                }
            }
        }
        return true;
    }

    public boolean getSubMenu(String partId, javax.swing.JMenu menu, java.awt.event.ActionListener action)
    {
        if (!Char.isValidate(partId) || document == null)
        {
            return false;
        }

        Node node = document.getRootElement().selectSingleNode(Char.format("/magicpwd/submenu[@id='{0}']", partId));
        if (node == null || !(node instanceof Element))
        {
            return false;
        }
        Element element = (Element) node;

        java.util.List elementList = element.elements();
        if (elementList != null)
        {
            Element tmp;
            for (Object obj : elementList)
            {
                if (!(obj instanceof Element))
                {
                    continue;
                }
                tmp = (Element) obj;
                if ("menu".equals(tmp.getName()))
                {
                    menu.add(createMenu(tmp, null, action));
                    continue;
                }
                if ("item".equals(tmp.getName()))
                {
                    menu.add(createItem(tmp, null, action));
                    continue;
                }
                if ("seperator".equals(tmp.getName()))
                {
                    menu.addSeparator();
                    continue;
                }
            }
        }
        return true;
    }

    public boolean getStrokes(String strokesId, javax.swing.JComponent component)
    {
        if (!Char.isValidate(strokesId) || document == null)
        {
            return false;
        }

        Node node = document.getRootElement().selectSingleNode(Char.format("/magicpwd/strokes[@id='{0}']", strokesId));
        if (node == null || !(node instanceof Element))
        {
            return false;
        }
        processAction((Element) node, null, component);
        return true;
    }

    private javax.swing.JMenu createMenu(Element element, javax.swing.JComponent component, java.awt.event.ActionListener action)
    {
        javax.swing.JMenu menu = new javax.swing.JMenu();
        String id = element.attributeValue("id");
        if (Char.isValidate(id))
        {
            buttons.put(id, menu);
        }

        processText(element, menu);
        processTips(element, menu);
        processIcon(element, menu);

        java.util.List list = element.elements();
        if (list != null)
        {
            for (Object obj : list)
            {
                if (!(obj instanceof Element))
                {
                    continue;
                }
                element = (Element) obj;
                if ("menu".equals(element.getName()))
                {
                    menu.add(createMenu(element, component, action));
                    continue;
                }
                if ("item".equals(element.getName()))
                {
                    menu.add(createItem(element, component, action));
                    continue;
                }
                if ("seperator".equals(element.getName()))
                {
                    menu.addSeparator();
                    continue;
                }
            }
        }
        return menu;
    }

    private javax.swing.JMenuItem createItem(Element element, javax.swing.JComponent component, java.awt.event.ActionListener action)
    {
        javax.swing.JMenuItem item = processType(element);
        String id = element.attributeValue("id");
        if (Char.isValidate(id))
        {
            buttons.put(id, item);
        }

        processText(element, item);
        processTips(element, item);
        processIcon(element, item);
        processEnabled(element, item);
        processVisible(element, item);
        if (action == null)
        {
            processAction(element, item, component);
        }
        else
        {
            item.addActionListener(action);
            if (action instanceof IAction)
            {
                ((IAction) action).reInit(item, element.attributeValue("init"));
            }
        }
        processCommand(element, item);
        processGroup(element, item);
        return item;
    }

    private javax.swing.AbstractButton createButton(Element element, javax.swing.JComponent component, AppView viewPtn)
    {
        javax.swing.AbstractButton button = null;
        String type = element.attributeValue("type");
        if ("toggle".equals(type))
        {
            button = new javax.swing.JToggleButton();
        }
        else
        {
            button = new javax.swing.JButton();
        }

        String id = element.attributeValue("id");
        if (Char.isValidate(id))
        {
            buttons.put(id, button);
        }

        if (userMdl.getCfg(viewPtn, ConsCfg.CFG_VIEW_TOOL_MOD, "icon").toLowerCase().indexOf("text") > -1)
        {
            String pos = userMdl.getCfg(viewPtn, ConsCfg.CFG_VIEW_TOOL_POS, "").toLowerCase();
            if (Char.isValidate(pos))
            {
                if ("top".equals(pos))
                {
                    button.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
                }
                else if ("bottom".equals(pos))
                {
                    button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                }
            }
            processText(element, button);
        }
        processTips(element, button);
        processIcon(element, button);
        processEnabled(element, button);
        processVisible(element, button);
        processGroup(element, button);
        processAction(element, button, component);
        processCommand(element, button);
        return button;
    }

    public void loadSkin(javax.swing.JMenu skinMenu)
    {
        java.io.File skinFile = new java.io.File(ConsEnv.DIR_SKIN);
        if (!skinFile.exists() || !skinFile.isDirectory() || !skinFile.canRead())
        {
            return;
        }

        loadLook(skinMenu);
        loadTheme(skinMenu);
        loadFeel(skinMenu);

        java.io.File[] files = skinFile.listFiles(new AmonFF("[^\\s]+[.ams]$", true));
        if (files != null && files.length > 0)
        {
            skinMenu.addSeparator();

            // 动态扩展风格
            java.util.Properties prop = new java.util.Properties();
            javax.swing.JCheckBoxMenuItem item;
            WButtonGroup group = new WButtonGroup();
            for (java.io.File ams : files)
            {
                try
                {
                    prop.load(new java.io.InputStreamReader(new java.io.FileInputStream(ams), ConsEnv.FILE_ENCODING));
                    item = new javax.swing.JCheckBoxMenuItem();
                    Bean.setText(item, getLang(prop, "text"));
                    Bean.setTips(item, getLang(prop, "tips"));
                    item.setSelected(userMdl.getSkin().equals(prop.getProperty("name")));
                    skinMenu.add(item);
                    group.add(item);
                    prop.clear();
                }
                catch (Exception exp)
                {
                    Logs.exception(exp);
                }
            }
        }

        skinMenu.addSeparator();

        javax.swing.JMenuItem moreSkin = new javax.swing.JMenuItem();
        Bean.setText(moreSkin, Lang.getLang(LangRes.P30F7642, "更多皮肤"));
//        Bean.setTips(moreSkin, Lang.getLang("", "tips"));
        moreSkin.setActionCommand(ConsEnv.HOMEPAGE + "mpwd/mpwd0100.aspx?sid=" + ConsEnv.VERSIONS);
        moreSkin.addActionListener(new MoreAction());
        skinMenu.add(moreSkin);
    }

    private void loadLook(javax.swing.JMenu skinMenu)
    {
        javax.swing.JMenu lookMenu = new javax.swing.JMenu();
        Bean.setText(lookMenu, Lang.getLang(LangRes.P30F763B, "外观"));
        skinMenu.add(lookMenu);

        java.io.File lookFile = new java.io.File(ConsEnv.DIR_SKIN, ConsEnv.DIR_LOOK);
        if (!lookFile.exists() || !lookFile.isDirectory() || !lookFile.canRead())
        {
            return;
        }

        javax.swing.JCheckBoxMenuItem item;
        String lookName = userMdl.getLook();
        LookAction action = new LookAction();
        action.setMproPtn((MproPtn) trayPtn.getMpwdPtn(AppView.mpro));
        WButtonGroup group = new WButtonGroup();

        // Java默认风格
        java.io.File defaultSkin = new java.io.File(lookFile, ConsEnv.SKIN_LOOK_DEF_DIR + '/' + ConsEnv.SKIN_LOOK_FILE);
        if (defaultSkin.exists() && defaultSkin.isFile() && defaultSkin.canRead())
        {
            item = new javax.swing.JCheckBoxMenuItem();
            item.addActionListener(action);
            Bean.setText(item, Lang.getLang(LangRes.P30F7632, "默认界面"));
            Bean.setTips(item, "");
            item.setActionCommand(ConsCfg.DEF_SKIN_LOOK_DEF + ".Default");
            item.setSelected(lookName.equals(ConsCfg.DEF_SKIN_LOOK_DEF));
            lookMenu.add(item);
            group.add(item.getActionCommand(), item);
        }

        // 系统默认风格
        java.io.File sytemSkin = new java.io.File(lookFile, ConsEnv.SKIN_LOOK_SYS_DIR + '/' + ConsEnv.SKIN_LOOK_FILE);
        if (sytemSkin.exists() && sytemSkin.isFile() && sytemSkin.canRead())
        {
            item = new javax.swing.JCheckBoxMenuItem();
            item.addActionListener(action);
            Bean.setText(item, Lang.getLang(LangRes.P30F7633, "系统界面"));
            Bean.setTips(item, "");
            item.setActionCommand(ConsCfg.DEF_SKIN_LOOK_SYS + ".System");
            item.setSelected(lookName.equals(ConsCfg.DEF_SKIN_LOOK_SYS));
            lookMenu.add(item);
            group.add(item.getActionCommand(), item);
        }

        java.io.File dirs[] = lookFile.listFiles(new AmonFF(true, ConsEnv.SKIN_LOOK_DEF_DIR, ConsEnv.SKIN_LOOK_SYS_DIR));
        if (dirs != null && dirs.length > 0)
        {
            lookMenu.addSeparator();
            String os = Util.isWindows() ? "win" : (Util.isMac() ? "mac" : "lin");

            for (java.io.File dir : dirs)
            {
                java.io.File aml = new java.io.File(dir, ConsEnv.SKIN_LOOK_FILE);
                if (!aml.exists() || !aml.isFile() || !aml.canRead())
                {
                    continue;
                }
                try
                {
                    Document doc = new SAXReader().read(new java.io.FileInputStream(aml));
                    if (doc == null)
                    {
                        continue;
                    }
                    Element root = doc.getRootElement();
                    if (root == null)
                    {
                        continue;
                    }
                    Element look = root.element("look");
                    if (look == null)
                    {
                        continue;
                    }
                    java.util.List<?> items = look.elements("item");
                    if (items == null || items.size() < 1)
                    {
                        continue;
                    }
                    if (items.size() == 1)
                    {
                        Element element = look.element("item");

                        item = new javax.swing.JCheckBoxMenuItem();
                        item.addActionListener(action);
                        Bean.setText(item, getLang(element.attributeValue("text")));
                        Bean.setTips(item, getLang(element.attributeValue("tips")));
                        String id = dir.getName() + '.' + element.attributeValue("id");
                        item.setSelected(lookName.equals(id));
                        item.setActionCommand(id);
                        lookMenu.add(item);
                        group.add(item.getActionCommand(), item);
                    }
                    else
                    {
                        String grpText = getLang(look.attributeValue("group"));
                        if (!com.magicpwd._util.Char.isValidate(grpText))
                        {
                            grpText = dir.getName();
                        }
                        javax.swing.JMenu subMenu = new javax.swing.JMenu();
                        Bean.setText(subMenu, grpText);
                        lookMenu.add(subMenu);

                        for (Object object : items)
                        {
                            if (!(object instanceof Element))
                            {
                                continue;
                            }
                            Element element = (Element) object;
                            String platform = element.attributeValue("platform");
                            if (Char.isValidate(platform) && platform.toLowerCase().indexOf(os) < 0)
                            {
                                continue;
                            }

                            item = new javax.swing.JCheckBoxMenuItem();
                            item.addActionListener(action);
                            Bean.setText(item, getLang(element.attributeValue("text")));
                            Bean.setTips(item, getLang(element.attributeValue("tips")));
                            String id = dir.getName() + '.' + element.attributeValue("id");
                            item.setSelected(lookName.equals(id));
                            item.setActionCommand(id);
                            subMenu.add(item);
                            group.add(item.getActionCommand(), item);
                        }
                    }
                }
                catch (Exception exp)
                {
                    Logs.exception(exp);
                }
            }
        }

        lookMenu.addSeparator();

        javax.swing.JMenuItem moreLook = new javax.swing.JMenuItem();
        Bean.setText(moreLook, Lang.getLang(LangRes.P30F763C, "更多外观"));
//        Bean.setTips(moreSkin, Lang.getLang("", "tips"));
        moreLook.setActionCommand(ConsEnv.HOMEPAGE + "mpwd/mpwd0101.aspx?sid=" + ConsEnv.VERSIONS);
        moreLook.addActionListener(new MoreAction());
        lookMenu.add(moreLook);
    }

    private void loadTheme(javax.swing.JMenu skinMenu)
    {
        javax.swing.JMenu themeMenu = new javax.swing.JMenu();
        Bean.setText(themeMenu, Lang.getLang(LangRes.P30F763D, "主题"));
        skinMenu.add(themeMenu);

        javax.swing.JCheckBoxMenuItem item;
        ThemeAction action = new ThemeAction();
        WButtonGroup group = new WButtonGroup();

        // Java默认风格
//        java.io.File defaultSkin = new java.io.File(lookFile, ConsEnv.SKIN_LOOK_DEFAULT + '/' + ConsEnv.SKIN_LOOK_FILE);
//        if (defaultSkin.exists() && defaultSkin.isFile() && defaultSkin.canRead())
//        {
        item = new javax.swing.JCheckBoxMenuItem();
        item.addActionListener(action);
        Bean.setText(item, Lang.getLang(LangRes.P30F7641, "默认主题"));
        Bean.setTips(item, "");
        item.setActionCommand(ConsCfg.DEF_SKIN_LOOK_DEF);
        item.setSelected(true);
        themeMenu.add(item);
        group.add(item.getActionCommand(), item);
//        }

        themeMenu.addSeparator();

        javax.swing.JMenuItem moreTheme = new javax.swing.JMenuItem();
        Bean.setText(moreTheme, Lang.getLang(LangRes.P30F763E, "更多主题"));
//        Bean.setTips(moreSkin, Lang.getLang("", "tips"));
        moreTheme.setActionCommand(ConsEnv.HOMEPAGE + "mpwd/mpwd0102.aspx?sid=" + ConsEnv.VERSIONS);
        moreTheme.addActionListener(new MoreAction());
        themeMenu.add(moreTheme);
    }

    private void loadFeel(javax.swing.JMenu skinMenu)
    {
        javax.swing.JMenu feelMenu = new javax.swing.JMenu();
        Bean.setText(feelMenu, Lang.getLang(LangRes.P30F763F, "图标"));
        skinMenu.add(feelMenu);

        java.io.File feelFile = new java.io.File(ConsEnv.DIR_SKIN, ConsEnv.DIR_FEEL);
        if (!feelFile.exists() || !feelFile.isDirectory() || !feelFile.canRead())
        {
            return;
        }

        java.io.File dirs[] = feelFile.listFiles(new AmonFF(true));
        if (dirs != null && dirs.length > 0)
        {
            feelMenu.addSeparator();

            javax.swing.JCheckBoxMenuItem item;
            String feelName = userMdl.getFeel();
            FeelAction action = new FeelAction();
            action.setMproPtn((MproPtn) trayPtn.getMpwdPtn(AppView.mpro));
            WButtonGroup group = new WButtonGroup();

            java.util.Properties prop = new java.util.Properties();
            java.io.InputStreamReader reader = null;
            for (java.io.File dir : dirs)
            {
                java.io.File amf = new java.io.File(dir, ConsEnv.SKIN_FEEL_FORM);
                if (!amf.exists() || !amf.isFile() || !amf.canRead())
                {
                    continue;
                }
                try
                {
                    reader = new java.io.InputStreamReader(new java.io.FileInputStream(amf), ConsEnv.FILE_ENCODING);
                    prop.load(reader);

                    item = new javax.swing.JCheckBoxMenuItem();
                    item.addActionListener(action);
                    Bean.setText(item, getLang(prop, "text"));
                    Bean.setTips(item, getLang(prop, "tips"));
                    String name = dir.getName();
                    item.setSelected(feelName.equals(name));
                    item.setActionCommand(name);
                    feelMenu.add(item);
                    group.add(name, item);

                    prop.clear();
                }
                catch (Exception exp)
                {
                    Logs.exception(exp);
                }
                finally
                {
                    Bean.closeReader(reader);
                }
            }
        }

        feelMenu.addSeparator();

        javax.swing.JMenuItem morefeel = new javax.swing.JMenuItem();
        Bean.setText(morefeel, Lang.getLang(LangRes.P30F7640, "更多风格"));
//        Bean.setTips(moreSkin, Lang.getLang("", "tips"));
        morefeel.setActionCommand(ConsEnv.HOMEPAGE + "mpwd/mpwd0103.aspx?sid=" + ConsEnv.VERSIONS);
        morefeel.addActionListener(new MoreAction());
        feelMenu.add(morefeel);
    }

    private javax.swing.AbstractButton processText(Element element, javax.swing.AbstractButton button)
    {
        String vText = element.attributeValue("text");
        String dText = element.attributeValue("text-def");
        if (dText == null)
        {
            dText = vText;
        }
        if (vText != null && pattern.matcher(vText).matches())
        {
            vText = vText.substring(1).toUpperCase();
            vText = Lang.getLang(vText, dText);
        }
        if (vText != null)
        {
            Bean.setText(button, vText.length() > 0 ? vText : "...");
        }
        return button;
    }

    private javax.swing.AbstractButton processTips(Element element, javax.swing.AbstractButton button)
    {
        String vTips = element.attributeValue("tips");
        String dTips = element.attributeValue("tips-def");
        if (dTips == null)
        {
            dTips = vTips;
        }
        if (vTips != null && pattern.matcher(vTips).matches())
        {
            vTips = vTips.substring(1).toUpperCase();
            vTips = Lang.getLang(vTips, dTips);
        }
        Bean.setTips(button, "".equals(vTips) ? null : vTips);
        return button;
    }

    private javax.swing.AbstractButton processIcon(Element element, javax.swing.AbstractButton button)
    {
        java.util.List elements = element.elements("icon");
        if (elements == null || elements.size() < 1)
        {
            return button;
        }
        element = (Element) elements.get(0);
        Element temp = element.element("default");
        if (temp != null)
        {
            button.setIcon(createIcon(temp));
        }

        temp = element.element("pressed");
        if (temp != null)
        {
            button.setPressedIcon(createIcon(temp));
        }

        temp = element.element("rollover");
        if (temp != null)
        {
            button.setRolloverIcon(createIcon(temp));
        }

        temp = element.element("disabled");
        if (temp != null)
        {
            button.setDisabledIcon(createIcon(temp));
        }
        return button;
    }

    private static javax.swing.JMenuItem processType(Element element)
    {
        String type = element.attributeValue("type");
        if ("checkbox".equals(type))
        {
            javax.swing.JCheckBoxMenuItem item = new javax.swing.JCheckBoxMenuItem();
            item.setSelected(ConsCfg.DEF_TRUE.equalsIgnoreCase(element.attributeValue("checked")));
            return item;
        }
        else if ("radiobox".equals("type"))
        {
            javax.swing.JRadioButtonMenuItem item = new javax.swing.JRadioButtonMenuItem();
            item.setSelected(ConsCfg.DEF_TRUE.equalsIgnoreCase(element.attributeValue("checked")));
            return item;
        }

        return new javax.swing.JMenuItem();
    }

    private javax.swing.AbstractButton processGroup(Element element, javax.swing.AbstractButton button)
    {
        String group = element.attributeValue("group");
        if (Char.isValidate(group))
        {
            WButtonGroup bg = groups.get(group);
            if (bg == null)
            {
                bg = new WButtonGroup();
                groups.put(group, bg);
            }
            bg.add(button.getActionCommand(), button);
        }
        return button;
    }

    private javax.swing.AbstractButton processEnabled(Element element, javax.swing.AbstractButton button)
    {
        String text = element.attributeValue("enabled");
        if (Char.isValidate(text))
        {
            button.setEnabled("true".equals(text));
        }
        return button;
    }

    private javax.swing.AbstractButton processVisible(Element element, javax.swing.AbstractButton button)
    {
        String text = element.attributeValue("visible");
        if (Char.isValidate(text))
        {
            button.setVisible("true".equals(text));
        }
        return button;
    }

    private static javax.swing.AbstractButton processCommand(Element element, javax.swing.AbstractButton button)
    {
        String command = element.attributeValue("command");
        if (Char.isValidate(command))
        {
            button.setActionCommand(command);
        }
        return button;
    }

    private static javax.swing.AbstractButton processStrokes(Element element, javax.swing.AbstractButton button, javax.swing.Action action, javax.swing.JComponent component)
    {
        java.util.List list = element.elements("stroke");
        if (list == null || list.size() < 1)
        {
            return button;
        }


        for (int i = 0, j = list.size(); i < j; i += 1)
        {
            String temp = ((Element) list.get(i)).attributeValue("key");
            if (Char.isValidate(temp))
            {
                temp = temp.toUpperCase().replaceAll("~|SHIFT", "shift").replaceAll("\\^|CONTROL|CTRL", "control").replaceAll("#|ALT", "alt").replaceAll("!|META", "meta").replaceAll("[^-=`;',./\\[\\]a-zA-Z0-9]+", " ").trim();
                javax.swing.KeyStroke stroke = javax.swing.KeyStroke.getKeyStroke(temp);
                if (component != null)
                {
                    Bean.registerKeyStrokeAction(component, stroke, action, temp, javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW);
                }
                if (button != null && i == 0 && (button instanceof javax.swing.JMenuItem))
                {
                    ((javax.swing.JMenuItem) button).setAccelerator(stroke);
                }
            }
        }
        return button;
    }

    private javax.swing.AbstractButton processAction(Element element, javax.swing.AbstractButton button, javax.swing.JComponent component)
    {
        java.util.List list = element.elements("action");
        if (list == null || list.size() < 1)
        {
            return button;
        }
        String btnInit = element.attributeValue("init");

        for (int i = 0, j = list.size(); i < j; i += 1)
        {
            element = (Element) list.get(i);
            String name = element.attributeValue("id");
            boolean validate = Char.isValidate(name);
            javax.swing.Action action = validate ? actions.get(name) : null;
            if (action == null)
            {
                String type = element.attributeValue("class");
                if (Char.isValidate(type))
                {
                    try
                    {
                        Object obj = Class.forName(type).newInstance();
                        if (obj instanceof javax.swing.Action)
                        {
                            action = (javax.swing.Action) obj;
                            if (action instanceof IAction)
                            {
                                IAction iAction = (IAction) action;
                                iAction.setTrayPtn(trayPtn);
                                String actInit = element.attributeValue("init");

                                if (action instanceof ITrayAction)
                                {
                                    ITrayAction trayAction = (ITrayAction) action;
                                    trayAction.setTrayPtn(trayPtn);
                                    trayAction.doInit(actInit);
                                }
                                else if (action instanceof IMproAction)
                                {
                                    IMproAction mproAction = (IMproAction) action;
                                    mproAction.setTrayPtn(trayPtn);
                                    mproAction.setMproPtn((MproPtn) trayPtn.getMpwdPtn(AppView.mpro));
                                    mproAction.doInit(actInit);
                                }
                                else if (action instanceof IMwizAction)
                                {
                                    IMwizAction mwizAction = (IMwizAction) action;
                                    mwizAction.setTrayPtn(trayPtn);
                                    mwizAction.setMwizPtn((MwizPtn) trayPtn.getMpwdPtn(AppView.mwiz));
                                    mwizAction.doInit(actInit);
                                }
                                else if (action instanceof IMpadAction)
                                {
                                    IMpadAction mpadAction = (IMpadAction) action;
                                    mpadAction.setTrayPtn(trayPtn);
                                    mpadAction.setMpadPtn((MpadPtn) trayPtn.getMpwdPtn(AppView.mpad));
                                    mpadAction.doInit(actInit);
                                }
                                else if (action instanceof IMaocAction)
                                {
                                    IMaocAction maocAction = (IMaocAction) action;
                                    maocAction.setTrayPtn(trayPtn);
                                    maocAction.setMaocPtn((MaocPtn) trayPtn.getMpwdPtn(AppView.maoc));
                                    maocAction.doInit(actInit);
                                }
                                else if (action instanceof IMrucAction)
                                {
                                    IMrucAction mrucAction = (IMrucAction) action;
                                    mrucAction.setTrayPtn(trayPtn);
                                    mrucAction.setMrucPtn((MrucPtn) trayPtn.getMpwdPtn(AppView.mruc));
                                    mrucAction.doInit(actInit);
                                }
                                else if (action instanceof IMgtdAction)
                                {
                                    IMgtdAction mgtdAction = (IMgtdAction) action;
                                    mgtdAction.setTrayPtn(trayPtn);
                                    mgtdAction.setMgtdPtn((MgtdPtn) trayPtn.getMpwdPtn(AppView.mgtd));
                                    mgtdAction.doInit(actInit);
                                }
                            }
                            if (validate)
                            {
                                actions.put(name, action);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        Logs.exception(ex);
                        Lang.showMesg(trayPtn.getMpwdPtn(), null, ex.getLocalizedMessage());
                    }
                }
            }
            if (button != null)
            {
                button.addActionListener(action);
            }
            if (action instanceof IAction)
            {
                ((IAction) action).reInit(button, btnInit);
            }
            processStrokes(element, button, action, component);
            processReference(element, button, action);
        }
        return button;
    }

    private javax.swing.AbstractButton processReference(Element element, javax.swing.AbstractButton button, javax.swing.Action action)
    {
        if (button == null)
        {
            return button;
        }
        java.util.List list = element.elements("property");
        if (list == null || list.size() < 1)
        {
            return button;
        }

        String name;
        String refId;
        for (int i = 0, j = list.size(); i < j; i += 1)
        {
            element = (Element) list.get(i);

            // 处理属性
            name = element.attributeValue("name");
            if (!Char.isValidate(name))
            {
                continue;
            }

            // 处理引用
            refId = element.attributeValue("ref-id");
            if (!Char.isValidate(refId))
            {
                continue;
            }

            // 引用对象
            action = actions.get(refId);
            if (action == null)
            {
                continue;
            }

            try
            {
                java.lang.reflect.Method method = button.getAction().getClass().getDeclaredMethod("set" + Char.lUpper(name), java.net.URL.class);
                if (method != null)
                {
                    method.invoke(button.getAction(), action);
                }
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
        }
        return button;
    }

    private javax.swing.Icon createIcon(Element element)
    {
        return userMdl.getFeelFav(element.attributeValue("cache-id"), element.attributeValue("path"));
    }

    private static String getLang(java.util.Properties prop, String text)
    {
        if (text == null)
        {
            return text;
        }
        text = prop.getProperty(text);
        return java.util.regex.Pattern.matches("^[$]P30F[0123456789ABCDEF]{4}$", text) ? Lang.getLang(text, text) : text;
    }

    private static String getLang(String text)
    {
        return (text != null && java.util.regex.Pattern.matches("^[$]P30F[0123456789ABCDEF]{4}$", text)) ? Lang.getLang(text, text) : text;
    }

    public boolean isEnabled(String id)
    {
        return true;
    }

    public boolean isVisible(String id)
    {
        return true;
    }
}
