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
package com.magicpwd.m.mwiz;

import com.magicpwd._comn.I1S2;
import com.magicpwd._comn.mpwd.MpwdHeader;
import com.magicpwd._comn.S1S2;
import com.magicpwd._comn.S1S3;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.SafeMdl;
import com.magicpwd.m.UserMdl;

/**
 * @author Amon
 */
public class GridMdl extends SafeMdl implements javax.swing.table.TableModel, java.io.Serializable
{

    private KeysMdl keysMdl;
    private java.util.List<MpwdHeader> ls_KeysList;
    private javax.swing.event.EventListenerList listenerList;

    GridMdl(UserMdl userMdl)
    {
        super(userMdl);
    }

    void init()
    {
        keysMdl = new KeysMdl(userMdl);
        ls_KeysList = new java.util.ArrayList<MpwdHeader>();
        listenerList = new javax.swing.event.EventListenerList();
    }

    @Override
    public int getRowCount()
    {
        return ls_KeysList != null ? ls_KeysList.size() : 0;
    }

    @Override
    public int getColumnCount()
    {
        return ConsEnv.PWDS_HEAD_SIZE;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return "..";
            case 1:
                return "徽标";
            case 2:
                return "标题";
            case 3:
                return "提示";
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        if (columnIndex == 0)
        {
            return Integer.class;
        }
        if (columnIndex == 1)
        {
            return I1S2.class;
        }
        if (columnIndex == 2)
        {
            return S1S2.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        // 公共属性
        if (ls_KeysList == null || rowIndex < -1 || rowIndex >= ls_KeysList.size())
        {
            return null;
        }

        MpwdHeader temp = ls_KeysList.get(rowIndex);
        switch (columnIndex)
        {
            case 0:
                return rowIndex + 1;
            case 1:
                return new I1S2(rowIndex, "keys-label" + temp.getP30F0102(), "keys-major" + (temp.getP30F0103() > 0 ? "+" : "") + temp.getP30F0103());
            case 2:
                return new S1S3(temp.getP30F010B(), temp.getP30F0109(), temp.getP30F010A(), temp.getP30F010C());
            case 3:
                return temp.getP30F010F();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
    }

    @Override
    public void initHead()
    {
    }

    @Override
    public void initBody()
    {
    }

    @Override
    public void clear()
    {
        itemList.clear();
        mkey.setDefault();
        setModified(false);
    }

    public boolean listKeysByKind(String kindHash)
    {
        ls_KeysList.size();
        ls_KeysList.clear();
        boolean b = DBA4000.listKeyHeaderByCat(userMdl, kindHash, ls_KeysList);
        int s = ls_KeysList.size();
        fireTableDataChanged();
        return b & (s > 0);
    }

    public boolean listKeysByMeta(String keysMeta)
    {
        int s = ls_KeysList.size();
        ls_KeysList.clear();
        boolean b = DBA4000.findUserData(userMdl, keysMeta, ls_KeysList);
        s = ls_KeysList.size();
        fireTableDataChanged();
        return b & (s > 0);
    }

    public void listHint(java.util.List<MgtdHeader>... hintList)
    {
        ls_KeysList.clear();
        DBA4000.findHintList(userMdl, ls_KeysList, hintList);
        fireTableDataChanged();
    }

    public MpwdHeader getKeysAt(int index)
    {
        return ls_KeysList.get(index);
    }

    /**
     * @return the keysMdl
     */
    public KeysMdl getKeysMdl()
    {
        return keysMdl;
    }

    /**
     * @param keysMdl the keysMdl to set
     */
    public void setKeysMdl(KeysMdl keysMdl)
    {
        this.keysMdl = keysMdl;
    }

    public void setKeysLabel(int row, int label)
    {
        if (row < 0 || row >= ls_KeysList.size())
        {
            return;
        }
        this.mkey = ls_KeysList.get(row);
        setKeysLabel(label);
    }

    public void setKeysMajor(int row, int major)
    {
        if (row < 0 || row >= ls_KeysList.size())
        {
            return;
        }
        this.mkey = ls_KeysList.get(row);
        setKeysMajor(major);
    }

    public void wDelete(int index)
    {
        DBA4000.deletePwdsData(userMdl, ls_KeysList.get(index).getP30F0104());
        ls_KeysList.remove(index);
        fireTableDataChanged();
    }

    @Override
    public void addTableModelListener(javax.swing.event.TableModelListener l)
    {
        listenerList.add(javax.swing.event.TableModelListener.class, l);
    }

    @Override
    public void removeTableModelListener(javax.swing.event.TableModelListener l)
    {
        listenerList.remove(javax.swing.event.TableModelListener.class, l);
    }

    public void fireTableChanged(javax.swing.event.TableModelEvent e)
    {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == javax.swing.event.TableModelListener.class)
            {
                ((javax.swing.event.TableModelListener) listeners[i + 1]).tableChanged(e);
            }
        }
    }

    public void fireTableCellUpdated(int row, int column)
    {
        fireTableChanged(new javax.swing.event.TableModelEvent(this, row, row, column));
    }

    public void fireTableRowsDeleted(int firstRow, int lastRow)
    {
        fireTableChanged(new javax.swing.event.TableModelEvent(this, firstRow, lastRow, javax.swing.event.TableModelEvent.ALL_COLUMNS, javax.swing.event.TableModelEvent.DELETE));
    }

    public void fireTableRowsUpdated(int firstRow, int lastRow)
    {
        fireTableChanged(new javax.swing.event.TableModelEvent(this, firstRow, lastRow, javax.swing.event.TableModelEvent.ALL_COLUMNS, javax.swing.event.TableModelEvent.UPDATE));
    }

    public void fireTableRowsInserted(int firstRow, int lastRow)
    {
        fireTableChanged(new javax.swing.event.TableModelEvent(this, firstRow, lastRow, javax.swing.event.TableModelEvent.ALL_COLUMNS, javax.swing.event.TableModelEvent.INSERT));
    }

    public void fireTableStructureChanged()
    {
        fireTableChanged(new javax.swing.event.TableModelEvent(this, javax.swing.event.TableModelEvent.HEADER_ROW));
    }

    public void fireTableDataChanged()
    {
        fireTableChanged(new javax.swing.event.TableModelEvent(this));
    }
}
