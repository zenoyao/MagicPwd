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
import java.text.DateFormat;
import java.util.Calendar;

/**
 *
 * @author Amon
 */
public class Date
{

    /**
     * 本地格式日期
     * @return
     */
    public static DateFormat getLocaleDateFormat()
    {
        if (locFormat == null)
        {
            locFormat = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.FULL, java.text.DateFormat.MEDIUM);
        }
        return locFormat;
    }
    private static DateFormat locFormat;

    /**
     * 统一格式日期
     * @return
     */
    public static DateFormat getFieldDateFormat()
    {
        if (uniFormat == null)
        {
            uniFormat = new java.text.SimpleDateFormat(ConsEnv.HINT_DATE);
        }
        return uniFormat;
    }
    private static DateFormat uniFormat;

    public static Calendar toDate(String text)
    {
        Calendar cal = null;
        if (com.magicpwd._util.Char.isValidate(text))
        {
            String tmp = text.toLowerCase();

            if (tmp.startsWith("fix:"))
            {
                cal = processFix(tmp.substring(4));
            }

            if (tmp.startsWith("var:"))
            {
                cal = processVar(tmp.substring(4));
            }
        }
        return cal;
    }

    /**
     * 固定日期及日期，格式要求：
     * fix:yyyy-MM-dd HH:mm:ss
     * @param txt
     */
    public static Calendar processFix(String txt)
    {
        if (!com.magicpwd._util.Char.isValidateDateTime(txt))
        {
            return null;
        }

        try
        {
            return toDate(txt, '-', ':', ' ');
        }
        catch (Exception ex)
        {
            Logs.exception(ex);
            return null;
        }
    }

    /**
     * 可变日期及时间，格式要求：
     * var:1minute
     * var:2hour
     * var:1day
     * ...
     * @param txt
     */
    public static Calendar processVar(String txt)
    {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("[-+]\\d+").matcher(txt);
        if (!matcher.find())
        {
            return null;
        }

        String tmp = matcher.group();
        if (tmp.charAt(0) == '+')
        {
            tmp = tmp.substring(1);
        }
        int step = Integer.parseInt(tmp);

        java.util.Calendar cal = java.util.Calendar.getInstance();
        if (txt.endsWith("second"))
        {
            cal.add(java.util.Calendar.SECOND, step);
            return cal;
        }
        if (txt.endsWith("minute"))
        {
            cal.add(java.util.Calendar.MINUTE, step);
            return cal;
        }
        if (txt.endsWith("hour"))
        {
            cal.add(java.util.Calendar.HOUR_OF_DAY, step);
            return cal;
        }
        if (txt.endsWith("day"))
        {
            cal.add(java.util.Calendar.DAY_OF_MONTH, step);
            return cal;
        }
        if (txt.endsWith("week"))
        {
            cal.add(java.util.Calendar.WEEK_OF_MONTH, step);
            return cal;
        }
        if (txt.endsWith("month"))
        {
            cal.add(java.util.Calendar.MONTH, step);
            return cal;
        }
        if (txt.endsWith("year"))
        {
            cal.add(java.util.Calendar.YEAR, step);
            return cal;
        }

        return cal;
    }

    public static Calendar toDate(String datetime, char datesp, char timesp, char dtsp) throws Exception
    {
        // 若指定日期时间字符串为空，则直接返回当前时间
        if (!Char.isValidate(datetime))
        {
            return Calendar.getInstance();
        }

        // 日期时间字符串分隔
        int dtspIndx = datetime.indexOf(dtsp);
        String date = datetime;
        String time = "";
        if (dtspIndx >= 0 && dtspIndx < datetime.length())
        {
            date = datetime.substring(0, dtspIndx);
            time = datetime.substring(dtspIndx + 1);
        }

        // 日期对象
        Calendar cal = Calendar.getInstance();

        parseDate(cal, date, datesp);
        parseTime(cal, time, timesp);

        return cal;
    }

    /**
     * 日期信息解析
     * @param cal
     * @param date
     * @param datesp
     */
    private static Calendar parseDate(Calendar cal, String date, char datesp)
    {
        if (Char.isValidate(date))
        {
            // 读取日期分隔符在日期字符串中位置信息
            int f = date.indexOf(datesp);
            int e = date.lastIndexOf(datesp);

            String y = null;// 年份
            String m = null;// 月份
            String d = null;// 日期
            // 没有日期分隔符号的情况下，日期字符串均按年份处理
            if (f < 0)
            {
                y = date;
            }
            // 存在日期分隔符号的情况下的处理
            else if (f >= 0)
            {
                // 只存在一个日期分隔符号的情况下，日期字符串按年月格式处理
                if (f == e)
                {
                    y = date.substring(0, f);
                    m = date.substring(e + 1);
                }
                // 存在两个日期分隔符号的情况下，日期字符串按年月日格式处理
                else
                {
                    y = date.substring(0, f);
                    m = date.substring(f + 1, e);
                    d = date.substring(e + 1);
                }
            }

            // 年份信息读取
            if (Char.isValidate(y))
            {
                cal.set(Calendar.YEAR, Integer.parseInt(y));
            }

            // 月份信息读取
            if (Char.isValidate(m))
            {
                cal.set(Calendar.MONTH, Integer.parseInt(m) - 1);
            }

            // 日期信息读取
            if (Char.isValidate(d))
            {
                cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d));
            }
        }
        return cal;
    }

    /**
     * 时间信息解析
     * @param cal
     * @param time
     * @param timesp
     */
    private static Calendar parseTime(Calendar cal, String time, char timesp)
    {
        if (Char.isValidate(time))
        {
            // 读取日期分隔符在日期字符串中位置信息
            int f = time.indexOf(timesp);
            int e = time.lastIndexOf(timesp);

            String h = null;// 小时
            String m = null;// 分钟
            String s = null;// 秒钟
            // 没有时间分隔符号的情况下，时间字符串均按小时处理
            if (f < 0)
            {
                h = time;
            }
            // 存在日期分隔符号的情况下的处理
            else if (f >= 0)
            {
                // 只存在一个日期分隔符号的情况下，日期字符串按年月格式处理
                if (f == e)
                {
                    h = time.substring(0, f);
                    m = time.substring(e + 1);
                }
                // 存在两个日期分隔符号的情况下，日期字符串按年月日格式处理
                else
                {
                    h = time.substring(0, f);
                    m = time.substring(f + 1, e);
                    s = time.substring(e + 1);
                }
            }

            // 年份信息读取
            if (Char.isValidate(h))
            {
                cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(h));
            }

            // 月份信息读取
            if (Char.isValidate(m))
            {
                cal.set(Calendar.MINUTE, Integer.parseInt(m));
            }

            // 日期信息读取
            if (Char.isValidate(s))
            {
                cal.set(Calendar.SECOND, Integer.parseInt(s));
            }
        }
        return cal;
    }

    /**
     * 获取UTC时间
     * @return
     */
    public static Calendar utcDate()
    {
        // 万年历对象
        java.util.Calendar cal = java.util.Calendar.getInstance();
        // 当前时区偏差
        int offset = cal.get(java.util.Calendar.DST_OFFSET) + cal.get(java.util.Calendar.ZONE_OFFSET);
        // 校正时区偏差到GMT时区
        cal.add(java.util.Calendar.MILLISECOND, -offset);
        return cal;
    }

    public static String nowTime()
    {
        return Char.lPad(Long.toHexString(System.currentTimeMillis()), 16, '0');
    }

    public static int getDaysOfMonth(int year, int month)
    {
        if (month > 7)
        {
            return month % 2 == 0 ? 31 : 30;
        }

        if (month != 2)
        {
            return month % 2 == 0 ? 30 : 31;
        }

        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
        {
            return 29;
        }
        return 28;
    }

    public static int getFirstDayOfMonth(int date, int day)
    {
        if (date >= 7)
        {
            int mod = date % 7;
            if (mod == 0)
            {
                return day + 1 > 7 ? 1 : day + 1;
            }

            return day - mod + 1 < 1 ? 8 + day - mod : day - mod + 1;
        }

        int difference = date - 1;
        if (day > difference)
        {
            return day - difference;
        }

        return day + 8 - difference;
    }

    public static int getLastDayOfMonth(int date, int day, int year, int month)
    {
        int lastDate = getDaysOfMonth(year, month);
        int diffencesDate = lastDate - date;
        if (diffencesDate >= 7)
        {
            int mod = diffencesDate % 7;
            if (mod == 0)
            {
                return day;
            }

            return day + mod > 7 ? (day + mod) - 7 : day + mod;
        }

        int difference = diffencesDate + day;
        if (difference > 7)
        {
            return 7 - difference;
        }

        return difference;
    }
}
