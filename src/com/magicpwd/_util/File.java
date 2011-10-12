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

import java.util.regex.Pattern;

/**
 *
 * @author Amon
 */
public class File
{

    /**
     * 复制文件或目录
     * @param srcPath
     * @param dstPath
     * @param overide
     * @return
     */
    public static int copy(String srcPath, String dstPath, boolean overide)
    {
        return copy(new java.io.File(srcPath), new java.io.File(dstPath), overide);
    }

    /**
     * 复制文件或目录
     * @param srcFile
     * @param dstFile
     * @param overide
     * @return
     */
    public static int copy(java.io.File srcFile, java.io.File dstFile, boolean overide)
    {
        if (srcFile == null || !srcFile.exists() || !srcFile.canRead() || dstFile == null)
        {
            return 0;
        }

        // 复制文件到下级目录时的特殊处理
        String srcPath = srcFile.getAbsolutePath().toLowerCase();
        String dstPath = dstFile.getAbsolutePath().toLowerCase();
        if (srcFile.isDirectory())
        {
            srcPath += java.io.File.separatorChar;
            dstPath += java.io.File.separatorChar;
        }
        if (dstPath.indexOf(srcPath) >= 0)
        {
            return 0;
        }

        if (dstFile.exists())
        {
            // 不覆盖复制
            if (!overide)
            {
                return 0;
            }
        }
        else
        {
            if (srcFile.isDirectory())
            {
                //dstFile = new java.io.File(dstFile, srcFile.getName());
                dstFile.mkdirs();
            }
            else if (srcFile.isFile())
            {
                dstFile.getParentFile().mkdirs();
            }
            else
            {
                return -1;
            }
        }

        int cnt = 0;
        try
        {
            if (srcFile.isFile())
            {
                if (!dstFile.exists())
                {
                    dstFile.createNewFile();
                }
                cnt += copyOne(srcFile, dstFile) ? 1 : 0;
            }
            else if (srcFile.isDirectory())
            {
                for (java.io.File tmp : srcFile.listFiles())
                {
                    cnt += copy(tmp, new java.io.File(dstFile, tmp.getName()), overide);
                }
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        return cnt;
    }

    private static boolean copyOne(java.io.File srcFile, java.io.File dstFile)
    {
        java.io.FileInputStream fis = null;
        java.io.FileOutputStream fos = null;

        byte[] buf = new byte[4096];
        try
        {
            fis = new java.io.FileInputStream(srcFile);
            fos = new java.io.FileOutputStream(dstFile);

            int len = fis.read(buf);
            while (len > 0)
            {
                fos.write(buf, 0, len);
                len = fis.read(buf);
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            Bean.closeStream(fos);
            Bean.closeStream(fis);
        }
        return true;
    }

    public static void remove(java.io.File file)
    {
        for (java.io.File temp : file.listFiles())
        {
            if (temp.isFile())
            {
                temp.delete();
                continue;
            }
            if (temp.isDirectory())
            {
                remove(temp);
                temp.delete();
                continue;
            }
        }
    }

    public static void removeSub(java.io.File file)
    {
        for (java.io.File temp : file.listFiles())
        {
            remove(temp);
        }
    }

    public static boolean byte2Text(String srcPath, String dstPath)
    {
        return (Char.isValidate(srcPath) && Char.isValidate(dstPath)) ? byte2Text(new java.io.File(srcPath), new java.io.File(dstPath)) : false;
    }

    public static boolean byte2Text(java.io.File srcFile, java.io.File dstFile)
    {
        if (srcFile == null || !srcFile.exists() || !srcFile.isFile() || !srcFile.canRead() || dstFile == null || !dstFile.exists() || !dstFile.isFile() || !dstFile.canWrite())
        {
            return false;
        }

        java.io.BufferedInputStream bi = null;
        java.io.BufferedWriter bw = null;

        try
        {
            bi = new java.io.BufferedInputStream(new java.io.FileInputStream(srcFile));
            bw = new java.io.BufferedWriter(new java.io.FileWriter(dstFile));

            byte[] buf = new byte[1920];
            int len = bi.read(buf);
            while (len >= 0)
            {
                bw.write(Code.encodeLines(buf, 0, len, 128, System.getProperty("line.separator")));
                len = bi.read(buf);
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            if (bw != null)
            {
                try
                {
                    bw.flush();
                }
                catch (Exception exp)
                {
                    Logs.exception(exp);
                    return false;
                }
                try
                {
                    bw.close();
                }
                catch (Exception exp)
                {
                    Logs.exception(exp);
                    return false;
                }
            }
            if (bi != null)
            {
                try
                {
                    bi.close();
                }
                catch (Exception exp)
                {
                    Logs.exception(exp);
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean text2Byte(String srcPath, String dstPath)
    {
        return (Char.isValidate(srcPath) && Char.isValidate(dstPath)) ? text2Byte(new java.io.File(srcPath), new java.io.File(dstPath)) : false;
    }

    public static boolean text2Byte(java.io.File srcFile, java.io.File dstFile)
    {
        if (srcFile == null || !srcFile.exists() || !srcFile.isFile() || !srcFile.canRead() || dstFile == null || !dstFile.exists() || !dstFile.isFile() || !dstFile.canWrite())
        {
            return false;
        }

        java.io.BufferedReader br = null;
        java.io.BufferedOutputStream bo = null;

        try
        {
            br = new java.io.BufferedReader(new java.io.FileReader(srcFile));
            bo = new java.io.BufferedOutputStream(new java.io.FileOutputStream(dstFile));

            String line = br.readLine();
            while (line != null)
            {
                bo.write(Code.decodeLines(line));
                line = br.readLine();
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            if (bo != null)
            {
                try
                {
                    bo.flush();
                }
                catch (Exception exp)
                {
                    Logs.exception(exp);
                    return false;
                }
                try
                {
                    bo.close();
                }
                catch (Exception exp)
                {
                    Logs.exception(exp);
                    return false;
                }
            }
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (Exception exp)
                {
                    Logs.exception(exp);
                    return false;
                }
            }
        }

        return true;
    }

    public static java.io.InputStream open4Read(String uri) throws Exception
    {
        if (!Char.isValidate(uri))
        {
            return null;
        }
        java.io.File file = new java.io.File(uri).getAbsoluteFile();
        if (file.exists())
        {
            return file.canRead() ? new java.io.FileInputStream(file) : null;
        }

        if (Pattern.matches("^[a-zA-z]+:/{2,3}[^\\s]+", uri))
        {
            return new java.net.URI(uri).toURL().openStream();
        }
        return null;
    }

    public static String getExtension(String fileName)
    {
        if (Char.isValidate(fileName))
        {
            int di = fileName.lastIndexOf('.') + 1;
            if (di > 0 && di < fileName.length())
            {
                return fileName.substring(di);
            }
        }
        return "";
    }

    public static String readText(String file) throws Exception
    {
        return readText(new java.io.File(file));
    }

    public static String readText(java.io.File file) throws Exception
    {
        if (file == null || !file.exists() || !file.isFile() || !file.canRead())
        {
            return null;
        }
        return readText(new java.io.FileInputStream(file));
    }

    public static String readText(java.io.InputStream stream) throws Exception
    {
        java.io.BufferedReader reader = null;
        try
        {
            reader = new java.io.BufferedReader(new java.io.InputStreamReader(stream));
            StringBuilder buf = new StringBuilder();
            char[] arr = new char[2048];
            int len = reader.read(arr, 0, arr.length);
            while (len >= 0)
            {
                buf.append(arr, 0, len);
                len = reader.read(arr, 0, arr.length);
            }
            reader.close();
            return buf.toString();
        }
        finally
        {
            Bean.closeReader(reader);
        }
    }

    public static boolean saveText(String path, String text) throws Exception
    {
        if (!Char.isValidate(path))
        {
            return false;
        }
        return saveText(new java.io.File(path), text);
    }

    public static boolean saveText(java.io.File file, String text) throws Exception
    {
        if (file == null)
        {
            return false;
        }
        if (!file.exists())
        {
            java.io.File path = file.getParentFile();
            if (!path.exists())
            {
                path.mkdirs();
            }
            file.createNewFile();
        }
        if (!file.isFile() || !file.canWrite())
        {
            return false;
        }
        return saveText(new java.io.FileOutputStream(file), text);
    }

    public static boolean saveText(java.io.OutputStream stream, String text) throws Exception
    {
        java.io.BufferedWriter writer = null;
        try
        {
            writer = new java.io.BufferedWriter(new java.io.OutputStreamWriter(stream));
            writer.write(text);
            return true;
        }
        finally
        {
            Bean.closeWriter(writer);
        }
    }
}
