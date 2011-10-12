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
package com.magicpwd.v.cmd.mcmd;

import com.magicpwd.__i.mcmd.IPageMdl;
import com.magicpwd._comn.mpwd.Mcat;
import com.magicpwd._comn.mpwd.MpwdHeader;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._util.Char;
import com.magicpwd._util.Jzip;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd._util.Util;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.d.db.DBAccess;
import com.magicpwd.m.MpwdMdl;
import com.magicpwd.m.UserMdl;
import com.magicpwd.m.mcmd.MattMdl;
import com.magicpwd.m.mcmd.McatMdl;
import com.magicpwd.m.mcmd.MkeyMdl;

/**
 * 命令行模式
 * @author Aven
 */
public class McmdPtn
{
    private MpwdMdl mpwdMdl;
    private UserMdl userMdl;
    private McatMdl mcatMdl;
    private MkeyMdl mkeyMdl;
    private MattMdl mattMdl;
    private IPageMdl pageMdl;

    public McmdPtn(MpwdMdl mpwdMdl)
    {
        this.mpwdMdl = mpwdMdl;
    }

    public void init()
    {
        userMdl = new UserMdl(mpwdMdl);

        console = System.console();
        if (console == null)
        {
            scanner = new java.util.Scanner(System.in);
        }

        if (!signIn())
        {
            return;
        }

        Lang.loadLang(mpwdMdl.getAppLang());
        Lang.showMesg(console, null, "用户登录成功！\n");

        showHelp();

        mcatMdl = new McatMdl(userMdl);
        mcatMdl.init();
        mkeyMdl = new MkeyMdl(userMdl);
        mkeyMdl.init();
        mattMdl = new MattMdl(userMdl);
        mattMdl.init();

        String cmd;
        String tmp;
        String sep = " ";
        while (true)
        {
            cmd = readText();
            if (!Char.isValidate(cmd))
            {
                continue;
            }
            cmd = cmd.trim();
            tmp = cmd.toLowerCase();
            int idx = tmp.indexOf(sep);
            if (idx > 0)
            {
                cmd = cmd.substring(idx).trim();
                tmp = tmp.substring(0, idx);
            }

            if ("&".equals(tmp))
            {
                copyData(cmd);
                continue;
            }
            if ("@".equals(tmp))
            {
                copyName(cmd);
                continue;
            }
            if (">".equals(tmp))
            {
                if (pageMdl != null)
                {
                    Lang.showMesg(console, null, pageMdl.nextPage());
                }
                continue;
            }
            if (">>".equals(tmp))
            {
                if (pageMdl != null)
                {
                    Lang.showMesg(console, null, pageMdl.lastPage());
                }
                continue;
            }
            if ("<".equals(tmp))
            {
                if (pageMdl != null)
                {
                    Lang.showMesg(console, null, pageMdl.prevPage());
                }
                continue;
            }
            if ("<<".equals(tmp))
            {
                if (pageMdl != null)
                {
                    Lang.showMesg(console, null, pageMdl.firstPage());
                }
                continue;
            }
            if ("cd".equals(tmp))
            {
                if (mcatMdl.changeCat(cmd))
                {
                    Lang.showMesg(console, null, "目录已成功切换！\n", cmd);
                }
                else
                {
                    Lang.showMesg(console, null, "无效的页码参数 {0}！\n", cmd);
                }
                pageMdl = null;
                mkeyMdl.clear();
                mattMdl.clear();
                continue;
            }
            if ("lc".equals(tmp))
            {
                listCat(cmd);
                pageMdl = mcatMdl;
                mattMdl.clear();
                continue;
            }
            if ("lk".equals(tmp))
            {
                listKey(cmd);
                pageMdl = mkeyMdl;
                mattMdl.clear();
                continue;
            }
            if ("pwd".equals(tmp))
            {
                Lang.showMesg(console, null, mcatMdl.showPath());
                pageMdl = null;
                mcatMdl.clear();
                mkeyMdl.clear();
                mattMdl.clear();
                continue;
            }
            if ("cat".equals(tmp))
            {
                viewKeys(cmd);
                pageMdl = mattMdl;
                continue;
            }
            if ("help".equals(tmp))
            {
                showHelp();
                pageMdl = null;
                mcatMdl.clear();
                mkeyMdl.clear();
                mattMdl.clear();
                continue;
            }
            if ("exit".equals(tmp))
            {
                endSave();
                Logs.end();
                break;
            }
            Lang.showMesg(console, null, "未知的命令：{0}！\n", tmp);
        }
    }

    private boolean signIn()
    {
        int errCnt = 0;

        String name;
        String pwds;
        String path;
        while (errCnt < 3)
        {
            name = readText("用户：");
            if (!Char.isValidate(name))
            {
                Lang.showMesg(console, null, "请输入用户名称！");
                continue;
            }
            name = name.trim();

            // 获得数据路径
            path = mpwdMdl.getDatPath(name);
            if (!Char.isValidate(path))
            {
                Lang.showMesg(console, null, "系统无法定位当前用户的数据文件，请尝试以下操作：\n1、打开文件：定位您之前的数据文件；\n2、用户注册：注册名称为 {0} 的用户。", name);
                return false;
            }

            pwds = readPass("口令：");
            if (!Char.isValidate(pwds))
            {
                Lang.showMesg(console, null, "请输入登录口令！");
                continue;
            }

            if (!userMdl.loadCfg(path))
            {
                Lang.showMesg(console, null, "用户数据加载异常！");
                return false;
            }

            try
            {
                if (!userMdl.signIn(name, pwds))
                {
                    errCnt += 1;
                    Lang.showMesg(console, null, "身份验证错误，请确认您的登录用户及登录口令是否正确！");
                    continue;
                }

                if (!ConsDat.VERSIONS.equals(DBA4000.readConfig(userMdl, "versions")))
                {
                    Lang.showMesg(console, null, "不支持的数据库！");
                    return false;
                }
                mpwdMdl.setUserLast(name);
                mpwdMdl.setViewLast("mcmd");
                return true;
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
                Lang.showMesg(console, null, exp.getLocalizedMessage());
                return false;
            }
        }

        Lang.showMesg(console, null, "您操作的错误次太多，请确认您是否为合法用户！\n为了保障用户数据安全，软件将自动关闭。");
        return false;
    }

    private void listCat(String cmd)
    {
        mcatMdl.setOnePage("all".equalsIgnoreCase(cmd));
        mcatMdl.listCat();
        Lang.showMesg(console, null, mcatMdl.print());
    }

    private void listKey(String cmd)
    {
        Mcat cat = mcatMdl.getCat();
        if (cat == null)
        {
            return;
        }
        mkeyMdl.setOnePage("all".equalsIgnoreCase(cmd));
        mkeyMdl.listKey(cat.getC2010203());
        Lang.showMesg(console, null, mkeyMdl.print());
    }

    private void viewKeys(String cmd)
    {
        MpwdHeader header = mkeyMdl.getKey(cmd);
        if (header == null)
        {
            Lang.showMesg(console, null, "无效的页码 {0}！\n", cmd);
            return;
        }

        try
        {
            mattMdl.loadData(header.getP30F0104());
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return;
        }

        mattMdl.setOnePage("all".equalsIgnoreCase(cmd));
        Lang.showMesg(console, null, mattMdl.print());
    }

    private void copyName(String cmd)
    {
        if (mattMdl.copyName(cmd))
        {
            Lang.showMesg(console, null, "数据已复制到系统剪贴板！\n");
        }
        else
        {
            Lang.showMesg(console, null, "复制数据到系统剪贴板异常！\n");
        }
    }

    private void copyData(String cmd)
    {
        if (mattMdl.copyData(cmd))
        {
            Lang.showMesg(console, null, "数据已复制到系统剪贴板！\n");
        }
        else
        {
            Lang.showMesg(console, null, "复制数据到系统剪贴板异常！\n");
        }
    }

    private void showHelp()
    {
        StringBuilder buf = new StringBuilder();
        buf.append("使用说明：\n");
        buf.append("cd\t\t切换分类\n");
        buf.append("\t..\t上级分类\n");
        buf.append("\t~\t根分类\n");
        buf.append("\t数字\t指定页码的分类\n");
        buf.append("lc\t\t分页方式查看当前分类下数据信息\n");
        buf.append("\tall\t不分页方式查看当前分类下数据信息\n");
        buf.append("lk\t\t分页方式查看当前分类下数据信息\n");
        buf.append("\tall\t不分页方式查看当前分类下数据信息\n");
        buf.append("cat\t\t分页方式查看指定记录的口令\n");
        buf.append("\tall\t不分页方式查看指定记录的口令\n");
        buf.append("pwd\t\t查看当前所处分类路径信息\n");
        buf.append("&\t数字\t复制当前属性的值到剪贴板\n");
        buf.append("@\t数字\t复制当前属性的键到剪贴板\n");
        buf.append("<<\t\t转到首屏\n");
        buf.append("<\t\t转到上一屏\n");
        buf.append(">\t\t转到下一屏\n");
        buf.append(">>\t\t转到未屏\n");

        Lang.showMesg(console, null, buf.toString());
    }

    public java.io.File endSave()
    {
        try
        {
            DBAccess.locked = true;

            userMdl.saveCfg();
            new DBAccess().shutdown();

            java.io.File backFile = Util.nextBackupFile(userMdl.getDumpDir(), userMdl.getDumpCnt());
            Jzip.doZip(backFile, new java.io.File(userMdl.getDataDir()));
            return backFile;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return null;
        }
        finally
        {
            DBAccess.locked = false;
        }
    }

    public void print(String cmd)
    {
        System.out.print(cmd);
    }

    public void print(String cmd, Object... obj)
    {
        console.format(cmd, obj);
    }

    public void printLine(String cmd)
    {
        System.out.println(cmd);
    }

    public String readText()
    {
        if (console != null)
        {
            return console.readLine();
        }
        return scanner.nextLine();
    }

    public String readText(String msg)
    {
        System.out.print(msg);
        return readText();
    }

    public String readPass(String msg)
    {
        System.out.print(msg);
        if (console != null)
        {
            return new String(console.readPassword());
        }
        return scanner.next();
    }

    public Integer readInt()
    {
        return scanner.nextInt();
    }

    public Long readLong()
    {
        return scanner.nextLong();
    }

    public Double readDouble()
    {
        return scanner.nextDouble();
    }
    private java.io.Console console;
    private java.util.Scanner scanner;
}
