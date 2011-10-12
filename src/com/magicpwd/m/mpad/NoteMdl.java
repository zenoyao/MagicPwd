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

import com.magicpwd.__a.AEditItem;

import com.magicpwd.__i.IEditItem;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd.m.SafeMdl;
import com.magicpwd.m.UserMdl;

/**
 * 记事便签数据模型
 * @author Amon
 */
public class NoteMdl extends SafeMdl
{

    NoteMdl(UserMdl userMdl)
    {
        super(userMdl);
    }

    void init()
    {
    }

    @Override
    public void initHead()
    {
        // Guid
        initGuid().setData(ConsDat.HASH_NOTE);

        // Meta
        initMeta();
        // Logo
        initLogo();
        // Hint
        initHint();
    }

    @Override
    public void initBody()
    {
        itemList.add(AEditItem.getInstance(userMdl, ConsDat.INDX_AREA));
    }

    @Override
    public void clear()
    {
        itemList.clear();
        mkey.setDefault();
        setModified(false);
    }

    public void setNote(String name, String note)
    {
        itemList.get(ConsEnv.PWDS_HEAD_META).setName(name);
        itemList.get(ConsEnv.PWDS_HEAD_SIZE).setName(name);
        itemList.get(ConsEnv.PWDS_HEAD_SIZE).setData(note);
    }

    public IEditItem getNote()
    {
        return itemList.get(ConsEnv.PWDS_HEAD_SIZE);
    }

    public int getSize()
    {
        return itemList.size();
    }
}
