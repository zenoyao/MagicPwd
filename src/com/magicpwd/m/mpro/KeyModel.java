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
package com.magicpwd.m.mpro;

import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._comn.mpwd.MpwdHeader;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.UserMdl;

/**
 * @author Amon
 * 
 */
public class KeyModel extends DefaultListModel
{

    private java.util.List<MpwdHeader> mkeyList;
    private UserMdl userMdl;

    KeyModel(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    void init()
    {
        mkeyList = new ArrayList<MpwdHeader>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @Override
    public Object getElementAt(int index)
    {
        return mkeyList.get(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ListModel#getSize()
     */
    @Override
    public int getSize()
    {
        return mkeyList != null ? mkeyList.size() : 0;
    }

    public void reLayout()
    {
        this.fireContentsChanged(this, 0, mkeyList.size());
    }

    /**
     * 重要程序
     */
    public void listMajor(int major)
    {
        if (major < -2 || major > 2)
        {
            return;
        }

        int c = mkeyList.size();
        mkeyList.clear();
        fireIntervalRemoved(this, 0, c);
        DBA4000.listRecHeaderByMajor(userMdl, major, mkeyList);
        c = mkeyList.size();
        fireIntervalAdded(this, 0, c);
    }

    /**
     * 用户标签
     */
    public void listLabel(int label)
    {
        if (label < 0 || label > 9)
        {
            return;
        }

        int c = mkeyList.size();
        mkeyList.clear();
        fireIntervalRemoved(this, 0, c);
        DBA4000.listRecHeaderByLabel(userMdl, label, mkeyList);
        c = mkeyList.size();
        fireIntervalAdded(this, 0, c);
    }

    public void listHint(java.util.List<MgtdHeader>... hintList)
    {
        int c = mkeyList.size();
        mkeyList.clear();
        fireIntervalRemoved(this, 0, c);
        DBA4000.findHintList(userMdl, mkeyList, hintList);
        c = mkeyList.size();
        fireIntervalAdded(this, 0, c);
    }

    public void listHint(java.util.Date s, java.util.Date t)
    {
        int c = mkeyList.size();
        mkeyList.clear();
        fireIntervalRemoved(this, 0, c);
        DBA4000.listRecHeaderByTime(userMdl, s.getTime(), t.getTime(), mkeyList);
        c = mkeyList.size();
        fireIntervalAdded(this, 0, c);
    }

    public boolean listKeyByCat(String typeHash)
    {
        userMdl.clearDataIcon();

        int s = mkeyList.size();
        mkeyList.clear();
        fireIntervalRemoved(this, 0, s);
        boolean b = DBA4000.listKeyHeaderByCat(userMdl, typeHash, mkeyList);
        s = mkeyList.size();
        b &= s > 0;
        fireIntervalAdded(this, 0, s);
        return b;
    }

    public boolean listKeyByMeta(String keysMeta)
    {
        userMdl.clearDataIcon();

        int s = mkeyList.size();
        mkeyList.clear();
        fireIntervalRemoved(this, 0, s);
        boolean b = DBA4000.findUserData(userMdl, keysMeta, mkeyList);
        s = mkeyList.size();
        b &= s > 0;
        if (b)
        {
            fireIntervalAdded(this, 0, s);
        }
        return b;
    }

    public void wAppend(MpwdHeader keys)
    {
        mkeyList.add(keys);
        this.fireIntervalAdded(this, 0, mkeyList.size());
    }

    public void wUpdate()
    {
        fireIntervalRemoved(this, 0, mkeyList.size());
    }

    public void wRemove(int index)
    {
        mkeyList.remove(index);
        fireIntervalRemoved(this, 0, index);
    }

    public void wRemove(MpwdHeader keys)
    {
        mkeyList.remove(keys);
        fireIntervalRemoved(this, 0, mkeyList.size());
    }

    public void wDelete(int index)
    {
        if (index > -1 && index < mkeyList.size())
        {
            DBA4000.deletePwdsData(userMdl, mkeyList.get(index).getP30F0104());
            mkeyList.remove(index);
            fireIntervalRemoved(this, 0, index);
        }
    }

    public MpwdHeader getElement(int index)
    {
        return mkeyList.get(index);
    }
}
