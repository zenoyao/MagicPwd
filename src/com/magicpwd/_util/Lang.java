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
package com.magicpwd._util;

import com.magicpwd._comp.BtnLabel;
import com.magicpwd._comp.IcoLabel;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._enum.RunMode;
import com.magicpwd.m.MpwdMdl;
import java.awt.Component;
import java.util.Properties;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Amon
 */
public class Lang
{
    private static Properties lang;
    private static String tips;

    private Lang()
    {
    }

    public static boolean loadLang(String langRes)
    {
        if (lang == null)
        {
            lang = new Properties();
        }

        if (MpwdMdl.getRunMode() != RunMode.web)
        {
            if (!Char.isValidate(langRes))
            {
                langRes = "zh_CN";
            }

            java.io.InputStream stream = null;
            try
            {
                String name = '_' + langRes;
                java.io.File file = null;
                while (name.length() > 0)
                {
                    //file = new java.io.File(ConsEnv.DIR_LANG, MpwdMdl.getRunMode().name() + name + ".properties");
                    file = new java.io.File(ConsEnv.DIR_LANG, "lang" + name + ".properties");
                    if (file.exists() && file.isFile() && file.canRead())
                    {
                        break;
                    }
                    file = null;
                    name = name.substring(0, name.lastIndexOf("_"));
                }

                if (file != null)
                {
                    stream = new java.io.FileInputStream(file);
                    lang.load(stream);
                }
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
            finally
            {
                Bean.closeStream(stream);
            }
        }

        tips = lang.getProperty(LangRes.P30FA208);
        if (tips == null)
        {
            tips = "友情提示";
        }
        return true;
    }

    public static String getLang(String key, String def)
    {
        key = key != null ? lang.getProperty(key) : def;
        return key != null ? key : def;
    }

    /**
     * 设置按钮的显示文本
     * @param c
     * @param wText
     * @param isHash
     */
    public static void setWText(AbstractButton c, String sid, String def)
    {
        Bean.setText(c, getLang(sid, def));
    }

    /**
     * 设置标签的显示文本
     * @param c
     * @param wText
     * @param isHash
     */
    public static void setWText(BtnLabel c, String sid, String def)
    {
        String txt = getLang(sid, def);
        Bean.setText(c, txt);
    }

    public static void setWText(IcoLabel c, String sid, String def)
    {
        String txt = getLang(sid, def);
        int idx = -1;
        // 快捷字符替换
        if (txt.length() > 0)
        {
            idx = txt.indexOf(ConsEnv.CHAR_ALT_KEY);
            if (idx >= 0)
            {
                txt = txt.replace(ConsEnv.CHAR_ALT_KEY, "");
                if (txt.length() > idx)
                {
                    c.setMnemonic(txt.charAt(idx));
                }
            }
        }

        if (!(idx == 0 && txt.length() == 1))
        {
            c.setText(txt);
            c.setDisplayedMnemonicIndex(idx);
        }
    }

    /**
     * 设置文本区域的默认显示信息
     * @param c
     * @param wText
     * @param isHash
     */
    public static void setWText(JTextComponent c, String key, String def)
    {
        c.setText(getLang(key, def));
    }

    /**
     * 设置标签的显示文本
     * @param c
     * @param wText
     * @param isHash
     */
    public static void setWText(JLabel c, String sid, String def)
    {
        String txt = getLang(sid, def);
        Bean.setText(c, txt);
    }

    /**
     * @param c
     * @param wTips
     * @param isHash
     */
    public static void setWTips(JComponent c, String sid, String tip)
    {
        c.setToolTipText(getLang(sid, tip));
    }

    public static void showMesg(java.io.Console c, String t, String d, String... z)
    {
        if (c != null)
        {
            c.format(Char.format(getLang(t, d) + "\n", z));
        }
        else
        {
            System.out.println(Char.format(getLang(t, d) + "\n", z));
        }
    }

    public static void showMesg(Component c, String t, String d, String... z)
    {
        t = getLang(t, d);
        if (z != null)
        {
            t = Char.format(t, z);
        }
        JOptionPane.showMessageDialog(c, t, tips, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int showFirm(Component c, String t, String d, String... z)
    {
        t = getLang(t, d);
        if (z != null)
        {
            t = Char.format(t, z);
        }
        return JOptionPane.showConfirmDialog(c, t, tips, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
}
