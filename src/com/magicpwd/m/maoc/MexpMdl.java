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
package com.magicpwd.m.maoc;

import com.magicpwd._comn.D1S2;
import com.magicpwd.m.UserMdl;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Amon
 */
public class MexpMdl implements javax.swing.table.TableModel, java.io.Serializable
{

    private boolean multiable;
    private java.util.List<D1S2> expList;
    private UserMdl userMdl;
    private javax.swing.event.EventListenerList listenerList;

    public MexpMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
        expList = new java.util.ArrayList<D1S2>();
        listenerList = new javax.swing.event.EventListenerList();
    }

    @Override
    public int getRowCount()
    {
        return expList != null ? expList.size() : 0;
    }

    @Override
    public int getColumnCount()
    {
        return multiable ? 3 : 2;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        if (columnIndex == 0)
        {
            return "";
        }
        return multiable ? (columnIndex == 2 ? "结果" : "表达式") : "计算式";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnIndex == 0 ? Integer.class : String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (rowIndex < 0 || rowIndex >= expList.size())
        {
            return "";
        }

        if (columnIndex == 0)
        {
            return rowIndex + 1;
        }

        D1S2 item = expList.get(rowIndex);
        return multiable ? (columnIndex != 2 ? item.getK() : item.getV()) : item.getK() + "=" + item.getV();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
    }

    @Override
    public void addTableModelListener(TableModelListener l)
    {
        listenerList.add(javax.swing.event.TableModelListener.class, l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l)
    {
        listenerList.remove(javax.swing.event.TableModelListener.class, l);
    }

    /**
     * @return the distinct
     */
    public boolean isMultiable()
    {
        return multiable;
    }

    /**
     * @param multiable the multiable to set
     */
    public void setMultiColumn(boolean multiable)
    {
        if (this.multiable != multiable)
        {
            this.multiable = multiable;
            this.fireTableStructureChanged();
        }
    }

    public D1S2 getItemAt(int rowIndex)
    {
        return expList.get(rowIndex);
    }

    public void appendItem(D1S2 item)
    {
        expList.add(item);
        fireTableDataChanged();
    }

    public void removeAll()
    {
        expList.clear();
        fireTableDataChanged();
    }

    public void removeItem(D1S2 item)
    {
        expList.remove(item);
        fireTableDataChanged();
    }

    public void removeItemAt(int rowIndex)
    {
        expList.remove(rowIndex);
        fireTableDataChanged();
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
