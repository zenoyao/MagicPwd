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
package com.magicpwd.e.mpro.safe;

import com.magicpwd.__a.mpro.AMproAction;
import com.magicpwd.__i.IBackCall;
import com.magicpwd._enum.AuthLog;
import com.magicpwd._user.UserDto;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;

/**
 *
 * @author Amon
 */
public class RemoteConfigAction extends AMproAction implements IBackCall<AuthLog, UserDto>
{

    public RemoteConfigAction()
    {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        trayPtn.getUserPtn(AuthLog.signCs, this);
    }

    @Override
    public void doInit(String value)
    {
    }

    @Override
    public void reInit(javax.swing.AbstractButton button, String value)
    {
    }

    @Override
    public boolean callBack(AuthLog options, UserDto object)
    {
        if (AuthLog.signCs != options || object == null)
        {
            return false;
        }

        try
        {
            if (!Char.isValidateMail(object.getUserType()))
            {
                object.setUserType(object.getUserName() + '@' + object.getUserType());
            }
            mproPtn.setCfgText("pop_mail", object.getUserType() + '\n' + object.getUserName() + '\n' + object.getUserPwds());
        }
        catch (Exception ex)
        {
            Logs.exception(ex);
            Lang.showMesg(mproPtn, null, ex.getLocalizedMessage());
        }
        return true;
    }
}
