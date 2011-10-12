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
package com.magicpwd.m.mpad;

import com.magicpwd.m.UserMdl;

/**
 *
 * @author Amon
 */
public final class MpadMdl
{

    private UserMdl userMdl;
    private NoteMdl noteMdl;

    public MpadMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    public void init()
    {
        noteMdl = new NoteMdl(userMdl);
        noteMdl.init();
    }

    /**
     * @return the noteMdl
     */
    public NoteMdl getNoteMdl()
    {
        return noteMdl;
    }

    /**
     * @param noteMdl the noteMdl to set
     */
    public void setNoteMdl(NoteMdl noteMdl)
    {
        this.noteMdl = noteMdl;
    }
}
