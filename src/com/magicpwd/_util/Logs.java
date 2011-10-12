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
import com.magicpwd._enum.RunMode;
import com.magicpwd.m.MpwdMdl;

/**
 * @author Amon
 */
public class Logs
{

    private static java.io.PrintWriter writer;

    public static void dev(String log)
    {
        System.out.println(log);
    }

    public static void log(String log)
    {
        if (MpwdMdl.getRunMode() == RunMode.cmd)
        {
            System.out.println(log);
        }
    }

    public static void exception(Exception exp)
    {
        if (MpwdMdl.getRunMode() == RunMode.cmd)
        {
            exp.printStackTrace();
        }
        getWriter().println(exp.toString());
    }

    public static void end()
    {
        if (writer != null)
        {
            writer.flush();
            writer.close();
        }
    }

    private static java.io.PrintWriter getWriter()
    {
        if (writer == null)
        {
            try
            {
                java.io.File logs = new java.io.File(ConsEnv.DIR_LOG);
                if (!logs.exists())
                {
                    logs.mkdirs();
                }
                logs = new java.io.File(logs, "amon.log");
                if (!logs.exists())
                {
                    logs.createNewFile();
                }
                writer = new java.io.PrintWriter(logs);
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
        return writer;
    }
}
