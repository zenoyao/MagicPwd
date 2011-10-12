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
package com.magicpwd.m.maoc;

import com.magicpwd._comn.mpwd.Mexp;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.UserMdl;

/**
 *
 * @author Aven
 */
public class MnumMdl implements javax.swing.ListModel, java.io.Serializable
{

    private java.util.List<Mexp> numList;
    private UserMdl userMdl;

    public MnumMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
        numList = new java.util.ArrayList<Mexp>();
    }

    @Override
    public int getSize()
    {
        return numList != null ? numList.size() : 0;
    }

    @Override
    public Object getElementAt(int index)
    {
        return numList.get(index);
    }

    @Override
    public void addListDataListener(javax.swing.event.ListDataListener l)
    {
        listenerList.add(javax.swing.event.ListDataListener.class, l);
    }

    @Override
    public void removeListDataListener(javax.swing.event.ListDataListener l)
    {
        listenerList.remove(javax.swing.event.ListDataListener.class, l);
    }

    public Mexp getItemAt(int index)
    {
        return numList.get(index);
    }

    public void appendItem(Mexp mexp)
    {
        int i = numList.size();
        mexp.setP30F0801(i);
        numList.add(mexp);
        DBA4000.saveMexpData(userMdl, mexp);
        this.fireIntervalAdded(mexp, i, i);
    }

    public void updateItem(Mexp mexp)
    {
        DBA4000.saveMexpData(userMdl, mexp);
        this.fireContentsChanged(mexp, 0, 0);
    }

    public boolean deleteItem(int rowIndex)
    {
        if (rowIndex > -1 && rowIndex < numList.size())
        {
            Mexp mexp = numList.remove(rowIndex);
            DBA4000.deleteMexpData(userMdl, mexp);
            this.fireIntervalRemoved(numList.remove(rowIndex), rowIndex, rowIndex);
            return true;
        }
        return false;
    }

    public void deleteItem(Mexp mexp)
    {
        if (mexp != null && numList.remove(mexp))
        {
            DBA4000.deleteMexpData(userMdl, mexp);
            this.fireIntervalRemoved(mexp, 0, numList.size());
        }
    }

    private void fireContentsChanged(Object source, int index0, int index1)
    {
        Object[] listeners = listenerList.getListenerList();
        javax.swing.event.ListDataEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == javax.swing.event.ListDataListener.class)
            {
                if (e == null)
                {
                    e = new javax.swing.event.ListDataEvent(source, javax.swing.event.ListDataEvent.CONTENTS_CHANGED, index0, index1);
                }
                ((javax.swing.event.ListDataListener) listeners[i + 1]).contentsChanged(e);
            }
        }
    }

    private void fireIntervalAdded(Object source, int index0, int index1)
    {
        Object[] listeners = listenerList.getListenerList();
        javax.swing.event.ListDataEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == javax.swing.event.ListDataListener.class)
            {
                if (e == null)
                {
                    e = new javax.swing.event.ListDataEvent(source, javax.swing.event.ListDataEvent.INTERVAL_ADDED, index0, index1);
                }
                ((javax.swing.event.ListDataListener) listeners[i + 1]).intervalAdded(e);
            }
        }
    }

    private void fireIntervalRemoved(Object source, int index0, int index1)
    {
        Object[] listeners = listenerList.getListenerList();
        javax.swing.event.ListDataEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == javax.swing.event.ListDataListener.class)
            {
                if (e == null)
                {
                    e = new javax.swing.event.ListDataEvent(source, javax.swing.event.ListDataEvent.INTERVAL_REMOVED, index0, index1);
                }
                ((javax.swing.event.ListDataListener) listeners[i + 1]).intervalRemoved(e);
            }
        }
    }
    private javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
}
