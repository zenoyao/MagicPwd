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

import com.magicpwd.__i.IEditItem;
import com.magicpwd._comn.I1S2;
import com.magicpwd.__a.AEditItem;
import com.magicpwd._comn.item.GuidItem;
import com.magicpwd._comn.item.SignItem;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.SafeMdl;
import com.magicpwd.m.UserMdl;

/**
 * @author Amon
 */
public class AttModel extends SafeMdl implements javax.swing.table.TableModel, java.io.Serializable
{

    private javax.swing.event.EventListenerList listenerList;

    AttModel(UserMdl userMdl)
    {
        super(userMdl);
    }

    void init()
    {
        listenerList = new javax.swing.event.EventListenerList();
    }

    @Override
    public void initHead()
    {
        initGuid();
        fireTableDataChanged();
    }

    @Override
    public void initBody()
    {
        initMeta();
        initLogo();
        initHint();

        DBA4000.selectTpltData(userMdl, itemList.get(ConsEnv.PWDS_HEAD_GUID).getSpec(GuidItem.SPEC_GUID_TPLT), itemList);
        fireTableDataChanged();
    }

    /**
     *
     */
    @Override
    public void clear()
    {
        itemList.clear();
        mkey.setDefault();
        setModified(false);
        fireTableDataChanged();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.DefaultTableModel#getColumnCount()
     */
    @Override
    public int getColumnCount()
    {
        return 2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnIndex == 0 ? Integer.class : String.class;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.DefaultTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int columnIndex)
    {
        return columnIndex == 1 ? Lang.getLang(LangRes.P30F7304, "属性(T)") : Lang.getLang(LangRes.P30F7303, "");
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.DefaultTableModel#getRowCount()
     */
    @Override
    public int getRowCount()
    {
        return itemList != null ? itemList.size() : 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int row, int column)
    {
        // 公共属性
        if (itemList == null)
        {
            return "";
        }

        // 索引列
        if (column == 0)
        {
            switch (row)
            {
                case ConsDat.INDX_GUID - ConsDat.INDX_GUID:
                    return Lang.getLang(LangRes.P30F1106, "日期");
                case ConsDat.INDX_META - ConsDat.INDX_GUID:
                    return Lang.getLang(LangRes.P30F110A, "标题");
                case ConsDat.INDX_LOGO - ConsDat.INDX_GUID:
                    return Lang.getLang(LangRes.P30F1112, "徽标");
                case ConsDat.INDX_HINT - ConsDat.INDX_GUID:
                    return Lang.getLang(LangRes.P30F110B, "提醒");
                default:
                    return row + 1 - ConsEnv.PWDS_HEAD_SIZE;
            }
        }

        IEditItem item = itemList.get(row);
        if (item.getType() == ConsDat.INDX_SIGN)
        {
            return Char.format(Lang.getLang(item.getSpec(SignItem.SPEC_SIGN_TPLT), ""), item.getName());
        }
        return item;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
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

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    /**
     * @param index
     * @return
     */
    @Override
    public IEditItem getItemAt(int index)
    {
        return itemList.get(index);
    }

    @Override
    public void loadData(String keysHash) throws Exception
    {
        super.loadData(keysHash);
        this.fireTableDataChanged();
    }

    /**
     * 添加指定类别的数据
     * 
     * @param type
     */
    public IEditItem wAppend(int indx, int type)
    {
        return wAppend(indx, AEditItem.getInstance(userMdl, type));
    }

    /**
     * @param pi
     */
    public IEditItem wAppend(int indx, IEditItem item)
    {
        itemList.add(indx, item);
        fireTableDataChanged();
        return item;
    }

    /**
     * @param pi
     */
    public IEditItem wAppend(IEditItem item)
    {
        itemList.add(item);
        fireTableDataChanged();
        return item;
    }

    /**
     * 查找特定类别且具有特定附加属性的字段
     * @param type
     * @param args
     */
    public int wSelect(int type, int args)
    {
        int t = (type | args);
        int n = 0;
        int l = 0;
        for (int i = 0, j = itemList.size(); i < j; i += 1)
        {
            if (itemList.get(i).getType() == t)
            {
                l = i;
                n += 1;
            }
        }
        return n == 1 ? l : -1;
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

    public void wUpdate()
    {
        fireTableDataChanged();
    }

    public void wUpdate(int indx, AEditItem tplt)
    {
        itemList.set(indx, tplt);
        fireTableDataChanged();
    }

    public void wRemove(int indx)
    {
        itemList.remove(indx);
        fireTableDataChanged();
        setModified(true);
    }

    public void wRemove(IEditItem item)
    {
        itemList.remove(item);
        fireTableDataChanged();
        setModified(true);
    }

    /**
     * @param row
     * @param to
     */
    public void wMoveto(int row, int to)
    {
        if (row == to)
        {
            return;
        }

        IEditItem p = itemList.remove(row);
        itemList.add(to, p);
        setModified(true);
    }

    public boolean setKeysKind(String hash)
    {
        boolean b = mkey.getP30F0106().equals(hash);
        mkey.setP30F0106(hash);
        DBA4000.saveKeysData(userMdl, mkey);
        return !b;
    }

    public void saveData(boolean histBack, boolean repaint) throws Exception
    {
        saveData(histBack);
        if (repaint)
        {
            fireTableDataChanged();
        }
    }

    public int getSequence()
    {
        return mkey.getP30F0101();
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
