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
package com.magicpwd.e.mwiz;

import com.magicpwd.__a.mwiz.AMwizAction;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2011-1-16 12:07:33
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class MailAction extends AMwizAction
{

    public MailAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        try
        {
            mwizPtn.showMailPtn();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(mwizPtn, null, exp.getLocalizedMessage());
        }
    }

    @Override
    public void doInit(String value)
    {
    }

    @Override
    public void reInit(javax.swing.AbstractButton button, String value)
    {
    }
}
