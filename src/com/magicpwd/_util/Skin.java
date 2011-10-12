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
package com.magicpwd._util;

import com.magicpwd._cons.ConsCfg;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._enum.RunMode;
import com.magicpwd.m.MpwdMdl;
import com.magicpwd.r.AmonFF;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.SubstanceConstants.ImageWatermarkKind;
import org.jvnet.substance.api.SubstanceSkin;
import org.jvnet.substance.watermark.SubstanceImageWatermark;

/**
 *
 * @author Amon
 */
public class Skin
{

    public static void loadFeel(String feel)
    {
        if (MpwdMdl.getRunMode() == RunMode.cmd)
        {
            return;
        }

        if (!Char.isValidate(feel))
        {
            feel = ConsCfg.DEF_SKIN_FEEL_DEF;
        }
    }

    public static void loadLook(String look)
    {
        if (MpwdMdl.getRunMode() == RunMode.cmd)
        {
            return;
        }

        if (!Char.isValidate(look))
        {
            look = ConsCfg.DEF_SKIN_LOOK_SYS;
        }

        // 查找对应的文件
        String name = "System";
        int di = look.indexOf('.');
        if (di > 0)
        {
            name = look.substring(di + 1);
            look = look.substring(0, di);
        }

        if (!Char.isValidate(look))
        {
            name = "System";
            look = ConsCfg.DEF_SKIN_LOOK_SYS;
        }

        String type = "java";
        String clazz = ConsCfg.DEF_SKIN_LOOK_SYS;
        String theme = "";
        String image = "";
        java.io.File file = new java.io.File(ConsEnv.DIR_SKIN + '/' + ConsEnv.DIR_LOOK + '/' + look + '/' + ConsEnv.SKIN_LOOK_FILE);
        if (file.exists() && file.isFile() && file.canRead())
        {
            // 查找对应的ITEM
            Element item = getElement(file, name);
            if (item != null)
            {
                file = file.getParentFile();

                loadResource(item, file);
                loadProperty(item, file);

                // 窗口装饰
                boolean deco = ConsCfg.DEF_TRUE.equalsIgnoreCase(item.attributeValue("decorated", "").trim());
                javax.swing.JFrame.setDefaultLookAndFeelDecorated(deco);
                javax.swing.JDialog.setDefaultLookAndFeelDecorated(deco);

                type = item.attributeValue("type", "").trim();
                clazz = item.attributeValue("class", "").trim();
                theme = item.attributeValue("theme", "").trim();
                image = item.attributeValue("image", "").trim();
            }
        }

        if (!Char.isValidate(clazz))
        {
            clazz = ConsCfg.DEF_SKIN_LOOK_SYS;
            type = "java";
        }

        if ("java".equals(type))
        {
            // 系统默认界面
            if (ConsCfg.DEF_SKIN_LOOK_DEF.equals(clazz))
            {
                return;
            }

            // 操作系统界面
            if (ConsCfg.DEF_SKIN_LOOK_SYS.equalsIgnoreCase(clazz))
            {
                clazz = javax.swing.UIManager.getSystemLookAndFeelClassName();
            }

            // 使用界面
            try
            {
                Class obj = Class.forName(clazz);
                javax.swing.LookAndFeel laf = (javax.swing.LookAndFeel) (obj.newInstance());
                loadLook(laf);
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
            return;
        }

        if ("synth".equals(type))
        {
            try
            {
                // 加载界面
                javax.swing.plaf.synth.SynthLookAndFeel synth = new javax.swing.plaf.synth.SynthLookAndFeel();
                synth.load(new java.io.File(file, clazz).toURI().toURL());
                loadLook(synth);
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
            return;
        }

        if ("substance".equals(type))
        {
            try
            {
                SubstanceLookAndFeel.setToUseConstantThemesOnDialogs(true);
                javax.swing.UIManager.put(SubstanceLookAndFeel.TABBED_PANE_CLOSE_BUTTONS_PROPERTY, Boolean.TRUE);
                javax.swing.UIManager.put(SubstanceLookAndFeel.SHOW_EXTRA_WIDGETS, Boolean.TRUE);

                if (Char.isValidate(theme))
                {
                    SubstanceSkin skin = (SubstanceSkin) Class.forName(theme).newInstance();
                    if (Char.isValidate(image))
                    {
                        java.io.InputStream stream = File.open4Read(theme);
                        SubstanceImageWatermark watermark = new SubstanceImageWatermark(stream);
                        stream.close();

                        watermark.setKind(ImageWatermarkKind.APP_CENTER);
                        watermark.setOpacity((float) 0.7);
                        skin.withWatermark(watermark);
                    }
                    SubstanceLookAndFeel.setSkin(skin);
                }
                Class obj = Class.forName(clazz);
                javax.swing.LookAndFeel laf = (javax.swing.LookAndFeel) (obj.newInstance());
                loadLook(laf);
                //SwingUtilities.updateComponentTreeUI(null);
            }
            catch (Exception ex)
            {
                Logs.exception(ex);
            }
            return;
        }

        if ("jgoodies".equals(type))
        {
            return;
        }

        if (!"user".equals(type))
        {
            return;
        }

        java.io.File jars[] = file.listFiles(new AmonFF("^.+\\.jar$", false));
        if (jars != null && jars.length > 0)
        {
            try
            {
                // 加载扩展库
                Bean.loadJar(jars);

                Class obj = Class.forName(clazz);
                javax.swing.LookAndFeel laf = (javax.swing.LookAndFeel) (obj.newInstance());
                loadLook(laf);
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
        }
    }

    private static void loadLook(javax.swing.LookAndFeel laf) throws Exception
    {
        //改变全局设置
        javax.swing.JFrame.setDefaultLookAndFeelDecorated(laf.getSupportsWindowDecorations());
        javax.swing.JDialog.setDefaultLookAndFeelDecorated(laf.getSupportsWindowDecorations());
        javax.swing.UIManager.setLookAndFeel(laf);

        //改变当前frame的窗口,边框,标题
//        frame.dispose();
//        frame.setUndecorated(lnf.getSupportsWindowDecorations());
//        frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
//        frame.show();
//
//        SwingUtilities.updateComponentTreeUI(frame);
    }

    private static void loadResource(Element item, java.io.File file)
    {
        // 附加属性
        java.util.List list = item.selectNodes("resource/font");
        if (list != null)
        {
            String key;
            String font;
            Element element;
            for (Object obj : list)
            {
                if (!(obj instanceof Element))
                {
                    continue;
                }
                element = (Element) obj;
                key = element.attributeValue("for");
                if (!Char.isValidate(key))
                {
                    continue;
                }
                font = element.attributeValue("font-name");
                if (Char.isValidate(font))
                {
                    setFont(key, new java.awt.Font(font, getFontStyle(element.attributeValue("font-style"), java.awt.Font.PLAIN), getInt(element.attributeValue("font-size"), 12)));
                    continue;
                }
                font = element.attributeValue("font-file");
                if (Char.isValidate(font))
                {
                    try
                    {
                        setFont(key, java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new java.io.File(file, font)).deriveFont(getFontStyle(element.attributeValue("font-style"), java.awt.Font.PLAIN), getInt(element.attributeValue("font-size"), 12)));
                    }
                    catch (Exception exp)
                    {
                        Logs.exception(exp);
                    }
                    continue;
                }
            }
        }
        list = item.selectNodes("resource/color");
        if (list != null)
        {
            String key;
            String color;
            Element element;
            for (Object obj : list)
            {
                if (!(obj instanceof Element))
                {
                    continue;
                }
                element = (Element) obj;
                key = element.attributeValue("for");
                if (!Char.isValidate(key))
                {
                    continue;
                }
                color = element.attributeValue("rgb");
                if (Char.isValidate(color, 6))
                {
                    setColor(key, java.awt.Color.decode(color));
                    continue;
                }
                setColor(key, new java.awt.Color(getInt(element.attributeValue("r"), 0), getInt(element.attributeValue("g"), 0), getInt(element.attributeValue("b"), 0), getInt(element.attributeValue("a"), 0)));
            }
        }
        list = item.selectNodes("resource/boolean");
        if (list != null)
        {
            String key;
            String bool;
            Element element;
            for (Object obj : list)
            {
                if (!(obj instanceof Element))
                {
                    continue;
                }
                element = (Element) obj;
                key = element.attributeValue("for");
                if (!Char.isValidate(key))
                {
                    continue;
                }
                bool = element.attributeValue("value");
                if (Char.isValidate(bool))
                {
                    setBoolean(key, Boolean.parseBoolean(bool));
                }
            }
        }
    }

    private static void loadProperty(Element item, java.io.File file)
    {
        java.util.List list = item.selectNodes("property/string");
        if (list != null)
        {
            String key;
            Element element;
            for (Object obj : list)
            {
                if (!(obj instanceof Element))
                {
                    continue;
                }
                element = (Element) obj;
                key = element.attributeValue("key");
                if (!Char.isValidate(key))
                {
                    continue;
                }
                System.setProperty(key, element.attributeValue("value"));
            }
        }
    }

    private static Element getElement(java.io.File file, String name)
    {
        try
        {
            Document doc = new SAXReader().read(new java.io.FileInputStream(file));
            if (doc != null)
            {
                Element root = doc.getRootElement();
                if (root != null)
                {
                    Node node = root.selectSingleNode(Char.format("/magicpwd/look/item[@id='{0}']", name));
                    if (node instanceof Element)
                    {
                        return (Element) node;
                    }
                }
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        return null;
    }

    private static int getInt(String str, int def)
    {
        if (java.util.regex.Pattern.matches(str, "^\\d+$"))
        {
            return Integer.parseInt(str);
        }
        return def;
    }

    private static void setFont(String key, java.awt.Font font)
    {
        if (!"*".equals(key))
        {
            javax.swing.UIManager.put(key, font);
            return;
        }

        java.util.Enumeration keys = javax.swing.UIManager.getDefaults().keys();
        Object k;
        Object v;
        while (keys.hasMoreElements())
        {
            k = keys.nextElement();
            v = javax.swing.UIManager.get(k);
            if (v instanceof java.awt.Font)
            {
                javax.swing.UIManager.put(k, font);
            }
        }
    }

    private static void setColor(String key, java.awt.Color color)
    {
        if (!"*".equals(key))
        {
            javax.swing.UIManager.put(key, color);
            return;
        }

        java.util.Enumeration keys = javax.swing.UIManager.getDefaults().keys();
        Object k;
        Object v;
        while (keys.hasMoreElements())
        {
            k = keys.nextElement();
            v = javax.swing.UIManager.get(k);
            if (v instanceof java.awt.Color)
            {
                javax.swing.UIManager.put(k, color);
            }
        }
    }

    private static void setBoolean(String key, Boolean bool)
    {
        if (!"*".equals(key))
        {
            javax.swing.UIManager.put(key, bool);
            return;
        }

        java.util.Enumeration keys = javax.swing.UIManager.getDefaults().keys();
        Object k;
        Object v;
        while (keys.hasMoreElements())
        {
            k = keys.nextElement();
            v = javax.swing.UIManager.get(k);
            if (v instanceof Boolean)
            {
                javax.swing.UIManager.put(k, bool);
            }
        }
    }

    private static int getFontStyle(String str, int def)
    {
        if (!Char.isValidate(str))
        {
            return def;
        }
        str = ',' + str.toLowerCase() + ',';
        int tmp = 0;
        if (str.indexOf("bold") > -1)
        {
            tmp &= java.awt.Font.BOLD;
        }
        if (str.indexOf("italic") > -1)
        {
            tmp &= java.awt.Font.ITALIC;
        }
        return tmp;
    }
}
