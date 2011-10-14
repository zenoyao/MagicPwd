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
package com.magicpwd.m;

import com.magicpwd._comn.prop.Mlib;
import com.magicpwd.d.db.DBA4000;

/**
 * 魔方密码：口令模板下拉列表模型
 * @author Amon
 */
public class LibList extends javax.swing.AbstractListModel implements javax.swing.MutableComboBoxModel, java.io.Serializable
{

    private java.util.List<Mlib> itemList;
    private Object selectedObject;
    private UserMdl userMdl;

    LibList(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    void initData()
    {
        itemList = DBA4000.selectTpltData(userMdl, "0");
    }

    @Override
    public void setSelectedItem(Object anObject)
    {
        if ((selectedObject != null && !selectedObject.equals(anObject)) || (selectedObject == null && anObject != null))
        {
            selectedObject = anObject;
            fireContentsChanged(this, -1, -1);
        }
    }

    @Override
    public Object getSelectedItem()
    {
        return selectedObject;
    }

    @Override
    public int getSize()
    {
        return itemList.size();
    }

    @Override
    public Mlib getElementAt(int index)
    {
        if (index >= 0 && index < itemList.size())
        {
            return itemList.get(index);
        }
        else
        {
            return null;
        }
    }

    @Override
    public void addElement(Object anObject)
    {
        if (anObject instanceof Mlib)
        {
            itemList.add((Mlib) anObject);
            fireIntervalAdded(this, itemList.size() - 1, itemList.size() - 1);
            if (itemList.size() == 1 && selectedObject == null && anObject != null)
            {
                setSelectedItem(anObject);
            }
        }
    }

    @Override
    public void insertElementAt(Object anObject, int index)
    {
        if (anObject instanceof Mlib)
        {
            itemList.add(index, (Mlib) anObject);
            fireIntervalAdded(this, index, index);
        }
    }

    @Override
    public void removeElementAt(int index)
    {
        if (getElementAt(index) == selectedObject)
        {
            if (index == 0)
            {
                setSelectedItem(getSize() == 1 ? null : getElementAt(index + 1));
            }
            else
            {
                setSelectedItem(getElementAt(index - 1));
            }
        }

        itemList.remove(index);

        fireIntervalRemoved(this, index, index);
    }

    @Override
    public void removeElement(Object anObject)
    {
        int index = itemList.indexOf(anObject);
        if (index != -1)
        {
            removeElementAt(index);
        }
    }

    public int getIndexOf(Object anObject)
    {
        return itemList.indexOf(anObject);
    }

    public void removeAllElements()
    {
        if (itemList.size() > 0)
        {
            int firstIndex = 0;
            int lastIndex = itemList.size() - 1;
            itemList.clear();
            fireIntervalRemoved(this, firstIndex, lastIndex);
        }
        selectedObject = null;
    }

    public java.util.List<Mlib> getAllItems()
    {
        return itemList;
    }

    public void moveTo(int s, int t)
    {
    }

    public void wAppend(Mlib item)
    {
        itemList.add(item);
        fireIntervalAdded(this, itemList.size() - 1, itemList.size() - 1);
        if (itemList.size() == 1 && selectedObject == null && item != null)
        {
            setSelectedItem(item);
        }
    }

    public void wUpdate()
    {
        this.fireContentsChanged(this, 0, itemList.size() - 1);
    }

    public void wRemove(int index)
    {
        itemList.remove(index);
        fireIntervalRemoved(this, index, index);
        if (index > itemList.size() - 1)
        {
            index = 0;
        }
        setSelectedItem(itemList.get(index));
    }
}
