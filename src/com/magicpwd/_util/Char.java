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

import com.magicpwd._cons.ConsEnv;
import com.magicpwd._enum.AppView;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 *
 * @author Amon
 */
public class Char
{

    public static String format(String src, String... arg)
    {
        if (src != null)
        {
            for (int i = 0, j = arg.length; i < j; i += 1)
            {
                if (arg[i] != null)
                {
                    src = src.replace("{" + (i) + "}", arg[i]);
                }
            }
        }
        return src;
    }

    public static AppView parseAppView(String appView)
    {
        if (Char.isValidate(appView, 4))
        {
            appView = appView.toLowerCase();
            try
            {
                return AppView.valueOf(appView);
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
        }
        return AppView.mwiz;
    }

    public static java.util.List<String> split(String src, String txt)
    {
        java.util.List<String> list = new java.util.ArrayList<String>();

        if (src == null || txt == null || txt.length() < 1)
        {
            return list;
        }
        src += txt;

        int len = txt.length();
        int idx = src.indexOf(txt);
        int tmp = 0;
        while (idx > -1)
        {
            list.add(src.substring(tmp, idx));
            tmp = idx + len;
            idx = src.indexOf(txt, tmp);
        }
        return list;
    }

    public static String lPad(String s, int length, char c)
    {
        if (length <= s.length())
        {
            return s;
        }

        char[] pad = new char[length - s.length()];
        Arrays.fill(pad, c);
        return new String(pad) + s;
    }

    /**
     * 去除左空白
     *
     * @param s
     * @return
     */
    public static String lTrim(String s)
    {
        return lTrim(s, " ");
    }

    /**
     * @param s
     * @param c
     * @return
     */
    public static String lTrim(String s, String c)
    {
        return (s != null) ? s.replaceAll("^[\\s" + c + "]+", c) : s;
    }

    /**
     * 右填充指定字符，使原字符串达到指定的长度
     *
     * @param s
     * @param length
     * @param c
     * @return
     */
    public static String rPad(String s, int length, char c)
    {
        if (length <= s.length())
        {
            return s;
        }

        char[] pad = new char[length - s.length()];
        Arrays.fill(pad, c);
        return s + new String(pad);
    }

    /**
     * 去除右空白
     *
     * @param s
     * @return
     */
    public static String rTrim(String s)
    {
        return (s != null) ? s.replaceAll("[\\s　]+$", "") : s;
    }

    public static boolean isValidate(String t)
    {
        return t != null ? t.trim().length() > 0 : false;
    }

    public static boolean isValidate(String t, int size)
    {
        return t != null ? t.trim().length() == size : false;
    }

    public static boolean isValidate(String t, int min, int max)
    {
        if (t == null)
        {
            return false;
        }
        int l = t.trim().length();
        return (l >= min && l <= max);
    }

    public static boolean isValidateDate(String text, boolean formatOnly)
    {
        if (text == null)
        {
            return false;
        }
        if (formatOnly)
        {
            return Pattern.matches("(^[1-9][0-9]{0,3}[-\\/\\._](0[1-9]|1[012])[-\\/\\._](0[1-9]|[12][0-9]|3[01])$)", text);
        }
        return Pattern.matches("((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))", text);
    }

    public static boolean isValidateTime(String text)
    {
        if (text == null)
        {
            return false;
        }
        return Pattern.matches("(^([01][0-9]|2[0-3])[:\\.]([0-5][0-9])[:\\.]([0-5][0-9])$)", text);
    }

    public static boolean isValidateDateTime(String text)
    {
        if (text == null)
        {
            return false;
        }
        return Pattern.matches("^[1-9][0-9]{0,3}[-\\/\\._](0[1-9]|1[012])[-\\/\\._](0[1-9]|[12][0-9]|3[01]) ([01][0-9]|2[0-3])[:\\.]([0-5][0-9])[:\\.]([0-5][0-9])$", text);
    }

    public static boolean isValidateCode(String text)
    {
        if (text == null)
        {
            return false;
        }
        return Pattern.compile("^[0-9a-zA-Z]{8}$", Pattern.CASE_INSENSITIVE).matcher(text).matches();
    }

    public static boolean isValidateHash(String text)
    {
        if (text == null)
        {
            return false;
        }
        return Pattern.compile("^[\\w]{16}$", Pattern.CASE_INSENSITIVE).matcher(text).matches();
    }

    /**
     * 验证给定字符串是否为有效电子邮件
     * @param mail
     * @return
     */
    public static boolean isValidateMail(String mail)
    {
        if (mail == null)
        {
            return false;
        }
        return Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", Pattern.CASE_INSENSITIVE).matcher(mail).matches();
    }

    public static boolean isValidateDecimal(String t)
    {
        return t != null ? Pattern.compile("^[+-]?((\\d*\\.\\d+)|(\\d+(\\.\\d*)?))([*]?[eE][-+]?\\d+)?$", Pattern.CASE_INSENSITIVE).matcher(t).matches() : false;
    }

    public static boolean isValidateInteger(String t)
    {
        return t != null ? Pattern.compile("^[+-]?\\d+$", Pattern.CASE_INSENSITIVE).matcher(t).matches() : false;
    }

    public static boolean isValidateNegativeInteger(String t)
    {
        return t != null ? Pattern.compile("^[-][123456789]\\d*$", Pattern.CASE_INSENSITIVE).matcher(t).matches() : false;
    }

    public static boolean isValidatePositiveInteger(String t)
    {
        return isValidatePositiveInteger(t, true);
    }

    public static boolean isValidatePositiveInteger(String t, boolean s)
    {
        return t != null ? Pattern.compile(s ? "^[+]?[123456789]\\d*$" : "^[123456789]\\d*$", Pattern.CASE_INSENSITIVE).matcher(t).matches() : false;
    }

    public static boolean isValidatePositiveDecimal(String t)
    {
        return t != null ? Pattern.compile("^[+]?[\\d]*([.]\\d*)?$", Pattern.CASE_INSENSITIVE).matcher(t).matches() : false;
    }

    public static String escape(String src)
    {
        StringBuilder buf = new StringBuilder(src.length() * 6);
        for (char c : src.toCharArray())
        {
            if (Character.isDigit(c) || Character.isLowerCase(c) || Character.isUpperCase(c))
            {
                buf.append(c);
                continue;
            }
            if (c < 256)
            {
                buf.append('%');
                if (c < 16)
                {
                    buf.append('0');
                }
                buf.append(Integer.toString(c, 16));
                continue;
            }

            buf.append("%u");
            buf.append(Integer.toString(c, 16));
        }
        return buf.toString();
    }

    public static String unescape(String src)
    {
        StringBuilder tmp = new StringBuilder(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length())
        {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos)
            {
                if (src.charAt(pos + 1) == 'u')
                {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                }
                else
                {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            }
            else
            {
                if (pos == -1)
                {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                }
                else
                {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    public static String replace(String res, String src, String dst)
    {
        if (res == null)
        {
            return res;
        }
        return replace(new StringBuilder(res), src, dst).toString();
    }

    public static StringBuilder replace(StringBuilder res, String src, String dst)
    {
        if (res != null && res.length() > 0 && src != null)
        {
            if (dst == null)
            {
                dst = "null";
            }

            int s = src.length();
            if (s > 0 && !src.equals(dst))
            {
                int d = dst.length();
                int i = res.indexOf(src);
                while (i >= 0)
                {
                    res.replace(i, i + s, dst);
                    i = res.indexOf(src, i + d);
                }
            }
        }
        return res;
    }

    public static String lUpper(String text)
    {
        return "";
    }

    /**
     * 按常规规则进行字符串到字节数组的变换
     *
     * @param s
     * @return
     */
    public static byte[] toBytes(String s, boolean bigCase)
    {
        return toBytes(s, bigCase ? ConsEnv.UPPER_NUMBER : ConsEnv.LOWER_NUMBER);
    }

    /**
     * 按指定变换规则进行字符串到字节数组的变换
     *
     * @param s
     * @param c
     * @return
     */
    public static byte[] toBytes(String s, char[] c)
    {
        char[] t = s.toCharArray();
        int i = 0, j = 0, k = t.length;
        byte[] b = new byte[k >>> 1];
        byte p;
        while (i < k)
        {
            p = 0;
            p |= charIndex(t[i++], c) << 4;
            p |= charIndex(t[i++], c);
            b[j++] = p;
        }
        return b;
    }

    private static int charIndex(char a, char[] c)
    {
        int i = 0;
        for (char t : c)
        {
            if (a == t)
            {
                break;
            }
            i += 1;
        }
        return i;
    }

    public static String toHex(byte[] b)
    {
        StringBuilder buf = new StringBuilder();
        for (byte t : b)
        {
            buf.append(t & 0xFF);
        }
        return buf.toString();
    }
}
