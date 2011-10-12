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

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import java.net.URL;

/**
 *
 * @author Amon
 */
public class Desk
{

    public static boolean browse(String url)
    {
        // 桌面支持性判断
        if (!Desktop.isDesktopSupported())
        {
            return false;
        }

        // 指定事件可用性判断
        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Action.BROWSE))
        {
            return false;
        }

        // 浏览指定地址
        try
        {
            desktop.browse(new URI(url));
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
    }

    public static boolean browse(URL url)
    {
        // 桌面支持性判断
        if (!Desktop.isDesktopSupported())
        {
            return false;
        }

        // 指定事件可用性判断
        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Action.BROWSE))
        {
            return false;
        }

        // 浏览指定地址
        try
        {
            desktop.browse(url.toURI());
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
    }

    public static boolean open(java.io.File file)
    {
        // 桌面支持性判断
        if (!Desktop.isDesktopSupported())
        {
            return false;
        }

        // 指定事件可用性判断
        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Action.OPEN))
        {
            return false;
        }

        // 浏览指定地址
        try
        {
            desktop.open(file);
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
    }

    public static boolean mail(String mailto)
    {
        Logs.log(mailto);

        // 桌面支持性判断
        if (!Desktop.isDesktopSupported())
        {
            return false;
        }

        // 指定事件可用性判断
        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Action.MAIL))
        {
            return false;
        }

        // 发送邮件
        try
        {
            if (!mailto.toLowerCase().startsWith("mailto:"))
            {
                mailto = "mailto:" + mailto;
            }
            desktop.mail(new URI(mailto));
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
    }
}
