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
package com.magicpwd.m.mruc;

import com.magicpwd.m.UserMdl;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-12-12 17:33:27
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public final class MrucMdl
{

    private UserMdl userMdl;
    private KeysMdl keysMdl;
    private UnitMdl unitMdl;

    public MrucMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    public void init()
    {
        keysMdl = new KeysMdl(userMdl);
        keysMdl.init();

        unitMdl = new UnitMdl(userMdl);
        unitMdl.init();
    }

    /**
     * @return the unitMdl
     */
    public UnitMdl getUnitMdl()
    {
        return unitMdl;
    }

    /**
     * @return the keysMdl
     */
    public KeysMdl getKeysMdl()
    {
        return keysMdl;
    }
}
