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
package com.magicpwd.x.app.icon;

/**
 * Application: MagicPwd
 * Author     : Aven
 * Encoding   : UTF-8
 * Website    : http://magicpwd.com/
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : Amon@magicpwd.com
 * CopyRight  : Winshine.biz
 * Description:
 */
class IcoLabel extends javax.swing.JLabel
{

    private int num;
    private String key;

    public IcoLabel()
    {
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    }

    public IcoLabel(int num, String key, javax.swing.Icon ico)
    {
        this.num = num;
        this.key = key;
        setText(Integer.toString(num));
        setIcon(ico);
        setOpaque(true);
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    }

    /**
     * @return the key
     */
    public String getKey()
    {
        return key;
    }
}
