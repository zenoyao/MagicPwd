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
package com.magicpwd.r;

import java.util.regex.Pattern;

/**
 * 功能：文件监视
 * @author Amon
 */
public class FileTM implements java.io.FileFilter
{

    private boolean hasFolder;
    private int lastSize;
    private long lastTime;
    private java.io.File filePath;
    private Pattern pattern;

    public FileTM(String path, Pattern pattern, boolean hasFolder)
    {
        this(new java.io.File(path).getAbsoluteFile(), pattern, hasFolder);
    }

    public FileTM(java.io.File file, Pattern pattern, boolean hasFolder)
    {
        this.filePath = file;
        this.hasFolder = hasFolder;
        this.pattern = pattern;
    }

    @Override
    public boolean accept(java.io.File file)
    {
        if (file == null || !file.exists() || !file.canRead())
        {
            return false;
        }
        if (file.isDirectory())
        {
            return hasFolder;
        }
        if (!file.isFile())
        {
            return false;
        }

        return pattern.matcher(file.getName()).matches();
    }

    public boolean checkModified()
    {
        fileList = filePath.listFiles(this);
        lastTime = System.currentTimeMillis();
        if (fileList != null)
        {
            if (fileList.length != lastSize)
            {
                return true;
            }

            for (java.io.File file : fileList)
            {
                if (file.lastModified() > lastTime)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return the fileList
     */
    public java.io.File[] getFileList()
    {
        return fileList;
    }

    /**
     * @param fileList the fileList to set
     */
    public void setFileList(java.io.File[] fileList)
    {
        this.fileList = fileList;
    }
    private java.io.File[] fileList;
}
