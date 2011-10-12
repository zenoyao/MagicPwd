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

import com.magicpwd.__i.IBackCall;
import com.magicpwd._comn.Task;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.JViewport;

import com.magicpwd.r.AmonFF;
import com.magicpwd._cons.ConsEnv;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * @author Amon
 */
public final class Util
{

    /**
     * 长整形数据加密
     * @param l
     * @param bigCase
     * @return
     */
    public static String encodeLong(long l, boolean bigCase)
    {
        // 不同进制使用的数值表示字符
        char[] digits = bigCase ? ConsEnv.UPPER_DIGEST : ConsEnv.LOWER_DIGEST;

        // 缓冲字符数组
        char[] buf = new char[16];
        int charPos = 16;
        do
        {
            buf[--charPos] = digits[(int) (l & 0xF)];
            l >>>= 4;
        }
        while (charPos > 0);

        // 返回符合用户要求格式的数组字符串
        return new String(buf);
    }

    public static BufferedImage getImage(String name)
    {
        return getImage(new File(name));
    }

    public static BufferedImage getImage(File file)
    {
        if (file == null || !file.exists() || !file.isFile() || !file.canRead())
        {
            return null;
        }

        try
        {
            java.io.InputStream stream = new java.io.FileInputStream(file);
            BufferedImage img = ImageIO.read(stream);
            stream.close();
            return img;
        }
        catch (Exception exp)
        {
            Logs.log(file.getName());
            Logs.exception(exp);
            return null;
        }
    }

    public static File nextBackupFile(String bakDir, int bakCnt) throws Exception
    {
        File bak = new File(bakDir);
        if (!bak.isDirectory())
        {
            bak.mkdir();
        }

        File[] list = bak.listFiles(new AmonFF("^amon_[^.]+\\.backup$", false));

        File backup;
        if (list != null)
        {
            int len = list.length;
            if (len == bakCnt)
            {
                backup = list[0];
                for (int i = 1; i < len; i += 1)
                {
                    if (backup.lastModified() > list[i].lastModified())
                    {
                        backup = list[i];
                    }
                }
                backup.delete();
            }
            else if (len > bakCnt)
            {
                sortFileByLastModified(list);
                len -= bakCnt;
                while (len > -1)
                {
                    list[len--].delete();
                }
            }
        }

        backup = new File(bakDir, Char.format(ConsEnv.FILE_BACK, currTime()));
        backup.createNewFile();
        return backup;
    }

    private static String currTime()
    {
        return new SimpleDateFormat(ConsEnv.FILE_DATE).format(new Date());
    }

    private static void sortFileByLastModified(File[] list)
    {
        File temp;
        for (int i = 0, j = list.length; i < j; i += 1)
        {
            temp = list[i];
            for (int m = i + 1, n = j; m < n; m += 1)
            {
                if (temp.lastModified() > list[m].lastModified())
                {
                    list[i] = list[m];
                    list[m] = temp;
                    temp = list[i];
                }
            }
        }
    }

    /**
     * 按正常十六进制将字节数组转换为可以显示的字符串数据
     * 
     * @param v
     *            等进行转换的字节数组
     * @param bigCase
     *            返回结果字符串是否使用大写字符，true大写，false小写
     * @return
     */
    public static String bytesToString(byte[] v, boolean bigCase)
    {
        return bytesToString(v, bigCase ? ConsEnv.UPPER_NUMBER : ConsEnv.LOWER_NUMBER);
    }

    /**
     * 指定转换参考码内的可显示字符串数据
     * 
     * @param c
     *            字节转换参考码，其长度不能小于16
     * @param v
     *            待转换的字节数组
     * @return
     */
    public static String bytesToString(byte[] v, char[] c)
    {
        // 转换参考码及字节数组合法性判断
        if (c == null || c.length < 16 || v == null || v.length < 1)
        {
            return "";
        }

        // 缓冲字符串大小判断及创建
        int len = v.length;
        StringBuilder strBuf = new StringBuilder(len << 1);

        // 字节数据转换为可显示字符串数据
        byte tmp;
        for (int i = 0; i < len; ++i)
        {
            tmp = v[i];
            strBuf.append(c[(tmp >>> 4) & 0xF]);
            strBuf.append(c[tmp & 0xF]);
        }

        return strBuf.toString();
    }

    public static char[] nextRandomKey(char[] sets, int size, boolean loop) throws Exception
    {
        if (sets == null)
        {
            throw new Exception("随机口令生成异常：口令字符空间不能为空！");
        }
        if (!loop && sets.length < size)
        {
            throw new Exception("随机口令生成异常：口令长度 " + size + " 大于口令字符空间长度 " + sets.length + " ，无法正确生成不可重复口令！");
        }

        char[] r = new char[size];
        Random random = new Random();
        for (int i = 0, l = sets.length, t; i < size; i++)
        {
            t = random.nextInt(l);
            r[i] = sets[t];
            if (!loop)
            {
                l -= 1;
                sets[t] = sets[l];
            }
        }

        return r;
    }

    /**
     * 向系统剪贴板添加数据
     * 
     * @param text
     */
    public static void setClipboardContents(String text)
    {
        copy2Clipboard(text);

        if (Time.getInstance().getAction("clip-mon") != null)
        {
            Time.getInstance().deActive("clip-mon");
            return;
        }
    }

    public static void copy2Clipboard(String text)
    {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static void setClipboardContents(String text, int maxDelay)
    {
        copy2Clipboard(text);

        if (Time.getInstance().getAction("clip-mon") != null)
        {
            Time.getInstance().reActive("clip-mon", 0, maxDelay);
            return;
        }

        Time.getInstance().registerAction(new Task(0, maxDelay, "clip-mon", ""), new IBackCall<String, Task>()
        {

            @Override
            public boolean callBack(String options, Task object)
            {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(null), null);
                return true;
            }
        });
    }

    public static void scrollToVisible(JTable table, int rowIndex, int colIndex, boolean center)
    {
        if (!(table.getParent() instanceof JViewport))
        {
            return;
        }

        JViewport viewport = (JViewport) table.getParent();
        Rectangle rect = table.getCellRect(rowIndex, colIndex, true);
        Rectangle viewRect = viewport.getViewRect();
        rect.setLocation(rect.x - viewRect.x, rect.y - viewRect.y);

        if (center)
        {
            int centerX = (viewRect.width - rect.width) >> 1;
            int centerY = (viewRect.height - rect.height) >> 1;

            if (rect.x < centerX)
            {
                centerX = -centerX;
            }
            if (rect.y < centerY)
            {
                centerY = -centerY;
            }
            rect.translate(centerX, centerY);
        }

        viewport.scrollRectToVisible(rect);
    }

    public static char[] nextRandomKey(String sets, int size, boolean loop) throws Exception
    {
        if (sets == null)
        {
            throw new Exception("随机口令生成异常：口令字符空间不能为空！");
        }
        return nextRandomKey(sets.toCharArray(), size, loop);
    }

    public static boolean checkUpdate(String sid, String ver) throws Exception
    {
        if (!Char.isValidate(sid, 8))
        {
            throw new IOException("未知软件标记信息！");
        }
        if (!Char.isValidate(ver))
        {
            throw new IOException("未知软件版本信息！");
        }

        // 属性读取
        Document document = new SAXReader().read(new URL(ConsEnv.HOMEPAGE + "soft/soft0001.ashx?sid=" + sid));
        Node node = document.selectSingleNode("/amonsoft/version");
        if (node == null)
        {
            throw new Exception("读取软件版本信息出错！");
        }

        // 新版本标记处理
        String tmp = node.getText();
        if (!Char.isValidate(tmp))
        {
            return false;
        }
        tmp = tmp.toUpperCase().replaceAll("\\s+", "");
        if (tmp.charAt(0) == 'V')
        {
            tmp = tmp.substring(1);
        }
        String[] newVer = tmp.split("\\.");

        // 老版本标记处理
        ver = ver.toUpperCase().replaceAll("\\s+", "");
        if (ver.charAt(0) == 'V')
        {
            ver = ver.substring(1);
        }
        String[] oldVer = ver.split("\\.");

        if (newVer == null || newVer.length != 4 || newVer.length != oldVer.length)
        {
            return false;
        }

        for (int i = 0; i < newVer.length; i += 1)
        {
            if (oldVer[i].length() < newVer[i].length())
            {
                return true;
            }
            int x = oldVer[i].compareTo(newVer[i]);
            if (x > 0)
            {
                return false;
            }
            if (x < 0)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * 用户输入文本转换为数据库可接受文本
     * @param text
     * @return
     */
    public static String text2DB(String text)
    {
        return text != null ? text.replace("'", "''") : "";
    }

    public static String db2Text(String text)
    {
        return "";
    }

    public static boolean isWindows()
    {
        return isWindows(System.getProperty("os.name"));
    }

    public static boolean isWindows(String osName)
    {
        return Char.isValidate(osName) ? osName.toLowerCase().indexOf("windows") > -1 : false;
    }

    public static boolean isMac()
    {
        return isMac(System.getProperty("os.name"));
    }

    public static boolean isMac(String osName)
    {
        return Char.isValidate(osName) ? osName.toLowerCase().indexOf("mac") > -1 : false;
    }

    public static boolean isLinux()
    {
        return isLinux(System.getProperty("os.name"));
    }

    public static boolean isLinux(String osName)
    {
        return Char.isValidate(osName) ? osName.toLowerCase().indexOf("linux") > -1 : false;
    }
}
