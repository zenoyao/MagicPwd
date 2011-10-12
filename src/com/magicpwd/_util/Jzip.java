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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Amon
 */
public class Jzip
{

    public static void doZip(String zipFileName, String... srcFileList) throws IOException
    {
        java.io.File zipFile = new java.io.File(zipFileName);

        // 文件是否存在
        if (!zipFile.exists())
        {
            zipFile.getParentFile().mkdirs();
            zipFile.createNewFile();
        }

        // 文件是否文档
        if (!zipFile.isFile())
        {
            return;
        }

        // 文件是否可写
        if (!zipFile.canWrite())
        {
            return;
        }

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(zipFile));
        ZipOutputStream zos = new ZipOutputStream(bos);
        for (String name : srcFileList)
        {
            doZip(zos, new java.io.File(name), "");
        }
        zos.flush();
        bos.flush();
        zos.close();
        bos.close();
    }

    public static void doZip(java.io.File zipFilePath, java.io.File... srcFileList) throws IOException
    {
        // 文件是否存在
        if (!zipFilePath.exists())
        {
            zipFilePath.getParentFile().mkdirs();
            zipFilePath.createNewFile();
        }

        // 文件是否文档
        if (!zipFilePath.isFile())
        {
            return;
        }

        // 文件是否可写
        if (!zipFilePath.canWrite())
        {
            return;
        }

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(zipFilePath));
        ZipOutputStream zos = new ZipOutputStream(bos);
        for (java.io.File file : srcFileList)
        {
            doZip(zos, file, "");
        }
        zos.flush();
        bos.flush();
        zos.close();
        bos.close();
    }

    private static void doZip(ZipOutputStream zos, java.io.File file, String base) throws IOException
    {
        byte[] buff = new byte[2048];

        base += file.getName();

        if (file.isDirectory())
        {
            base += '/';

            // 顶级目录扩展
            zos.putNextEntry(new ZipEntry(base));
            zos.closeEntry();

            // 递归文件压缩
            java.io.File[] list = file.listFiles();
            for (java.io.File temp : list)
            {
                doZip(zos, temp, base);
            }
        }
        else
        {
            zos.putNextEntry(new ZipEntry(base));
            saveEntry(zos, file, buff);
            zos.closeEntry();
        }
    }

    private static void saveEntry(ZipOutputStream zos, java.io.File file, byte[] buff) throws IOException
    {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        int size = bis.read(buff);
        while (size != -1)
        {
            zos.write(buff, 0, size);
            size = bis.read(buff);
        }
        bis.close();
    }

    public static void unZip(String zipFileName, String dstFileName) throws IOException
    {
        unZip(zipFileName, dstFileName, true);
    }

    public static void unZip(String zipFileName, String dstFileName, boolean overWrite) throws IOException
    {
        unZip(new java.io.File(zipFileName), new java.io.File(dstFileName));
    }

    public static void unZip(java.io.File zipFileName, java.io.File dstFilePath) throws IOException
    {
        unZip(zipFileName, dstFilePath, true);
    }

    public static void unZip(java.io.File zipFileName, java.io.File dstFilePath, boolean overWrite) throws IOException
    {
        // 文件是否存在
        if (!dstFilePath.exists())
        {
            dstFilePath.mkdirs();
        }

        // 文件是否为目录
        if (!dstFilePath.isDirectory())
        {
            return;
        }

        // 文件是否可写
        if (!dstFilePath.canWrite())
        {
            return;
        }

        unZip(new FileInputStream(zipFileName), dstFilePath, overWrite);
    }

    public static void unZip(InputStream inputStream, java.io.File dstFilePath, boolean overWrite) throws IOException
    {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ZipInputStream zis = new ZipInputStream(bis);
        byte[] buff = new byte[2048];

        ZipEntry zip = zis.getNextEntry();
        while (zip != null)
        {
            java.io.File file = new java.io.File(dstFilePath, zip.getName());

            if (zip.isDirectory())
            {
                if (!file.exists())
                {
                    file.mkdirs();
                }
            }
            else
            {
                if (!file.exists())
                {
                    file.createNewFile();
                    readEntry(zis, file, buff);
                }
                else if (overWrite)
                {
                    readEntry(zis, file, buff);
                }
            }
            zip = zis.getNextEntry();
        }

        zis.close();
        bis.close();
    }

    private static void readEntry(ZipInputStream zis, java.io.File file, byte[] buff) throws IOException
    {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        int size = zis.read(buff);
        while (size >= 0)
        {
            bos.write(buff, 0, size);
            size = zis.read(buff);
        }
        bos.close();
    }
}
