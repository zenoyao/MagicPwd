/*
 *  Copyright (C) 2010 Administrator
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
package com.magicpwd.m.mwiz;

import com.magicpwd.__i.IEditItem;
import com.magicpwd._comn.I1S2;
import com.magicpwd._comn.item.GuidItem;
import com.magicpwd._comn.mpwd.MpwdHeader;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._util.Char;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.SafeMdl;
import com.magicpwd.m.UserMdl;

/**
 * Application: MagicPwd
 * Author     : Administrator
 * Encoding   : UTF-8
 * Created    : 2010-10-24 22:09:08
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class KeysMdl extends SafeMdl
{

    KeysMdl(UserMdl userMdl)
    {
        super(userMdl);
    }

    void init()
    {
    }

    @Override
    public void initHead()
    {
        initMeta();
        initLogo();
        initHint();
    }

    @Override
    public void initBody()
    {
        DBA4000.selectTpltData(userMdl, itemList.get(ConsEnv.PWDS_HEAD_GUID).getSpec(GuidItem.SPEC_GUID_TPLT), itemList);
    }

    @Override
    public void clear()
    {
        itemList.clear();
        mkey.setDefault();
    }

    public void clear(int sIndex)
    {
        clear(sIndex, itemList.size() - 1);
    }

    public void clear(int sIndex, int eIndex)
    {
        if (sIndex >= 0 && eIndex >= sIndex)
        {
            while (eIndex >= sIndex)
            {
                itemList.remove(eIndex--);
            }
        }
    }

    public boolean isUpdate()
    {
        return Char.isValidateHash(mkey.getP30F0104());
    }

    public void loadData(MpwdHeader keys) throws Exception
    {
        loadData(keys.getP30F0104());
    }

    /**
     *
     * @param type
     * @return
     */
    public java.util.List<I1S2> wSelect(int type)
    {
        java.util.ArrayList<I1S2> list = new java.util.ArrayList<I1S2>();
        int i = 0;
        for (IEditItem item : itemList)
        {
            if (item.getType() == type)
            {
                list.add(new I1S2(i, item.getData(), item.getName()));
            }
            i += 1;
        }
        return list;
    }
}
