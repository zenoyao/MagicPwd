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
package com.magicpwd._bean;

import com.magicpwd.__a.AEditBean;
import com.magicpwd.__a.AMpwdPtn;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-10-27 20:23:24
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public abstract class AListBean extends AEditBean
{

    public AListBean(AMpwdPtn mpwdPtn)
    {
        this.formPtn = mpwdPtn;
    }

    protected void initConfView()
    {
    }

    protected void initConfLang()
    {
    }

    protected void initConfData()
    {
    }

    protected void showConfData()
    {
    }
}
