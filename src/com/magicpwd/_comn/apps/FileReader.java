/*
 *  Copyright (C) 2011 Amon
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
package com.magicpwd._comn.apps;

import com.magicpwd.__i.lock.ILockClient;

/**
 *
 * @author Amon
 */
public class FileReader implements ILockClient
{

    private java.io.File file;
    private java.nio.channels.FileChannel fc;
    private java.nio.channels.FileLock fl;

    public FileReader()
    {
    }

    public FileReader(java.io.File file)
    {
        this.file = file;
    }

    @Override
    public boolean canRead()
    {
        if (file == null)
        {
            return false;
        }

        // Check if the lock exist
        if (file.exists())
        {
            // if exist try to delete it
            file.delete();
        }

        try
        {
            // Try to get the lock
            fc = new java.io.RandomAccessFile(file, "rw").getChannel();
            fl = fc.tryLock();
            if (fl == null)
            {
                // File is locked by other application
                fc.close();
                return false;
            }

            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * @return the file
     */
    public java.io.File getFile()
    {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(java.io.File file)
    {
        this.file = file;
    }
}
