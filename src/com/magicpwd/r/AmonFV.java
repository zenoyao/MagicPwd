/*
 *  Copyright (C) 2011 Aven
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

import com.magicpwd._util.Bean;

/**
 * Application: MagicPwd
 * Author     : Aven
 * Encoding   : UTF-8
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class AmonFV extends javax.swing.filechooser.FileView
{

    @Override
    public javax.swing.Icon getIcon(java.io.File file)
    {
        if (file == null || !file.exists() || !file.canRead() || !file.isDirectory())
        {
            return super.getIcon(file);
        }
        if (new java.io.File(file, "amon.script").exists())
        {
            return new javax.swing.ImageIcon(Bean.getLogo(16));
        }
        return super.getIcon(file);
    }
}
