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
package com.magicpwd._prop;

import com.magicpwd.__i.IPropBean;
import com.magicpwd._comn.S1S2;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;
import com.magicpwd.v.app.mpro.MproPtn;

/**
 * @author Amon
 * 
 */
public class SKeyProp extends javax.swing.JPanel implements IPropBean
{

    private MproPtn mainPtn;
    private java.util.List<S1S2> skeyList;

    public SKeyProp(MproPtn mainPtn)
    {
        this.mainPtn = mainPtn;
    }

    @Override
    public void initView()
    {
        javax.swing.JScrollPane sp_SkeyList = new javax.swing.JScrollPane();
        tb_SkeyList = new javax.swing.JTable();
        sp_SkeyList.setViewportView(tb_SkeyList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(sp_SkeyList, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(sp_SkeyList, javax.swing.GroupLayout.PREFERRED_SIZE, 200, Short.MAX_VALUE));
    }

    @Override
    public void initLang()
    {
    }

    @Override
    public void initData()
    {
        if (skeyList == null)
        {
            skeyList = new java.util.ArrayList<S1S2>();
            int size = Integer.parseInt(Lang.getLang(LangRes.P30F2B00, "0"), 16);
            int indx = 3;
            S1S2 item;
            String[] value =
            {
                "", ""
            };
            while (indx < size)
            {
                item = new S1S2();
                item.setV(Lang.getLang("P30F" + Integer.toHexString(11008 + (indx++)).toUpperCase(), ""));
                item.setV2(Lang.getLang("P30F" + Integer.toHexString(11008 + (indx++)).toUpperCase(), ""));
                skeyList.add(item);

                if (item.getV().length() > value[0].length())
                {
                    value[0] = item.getV();
                }
                if (item.getV2().length() > value[1].length())
                {
                    value[1] = item.getV2();
                }
            }
            SkeyModel sm = new SkeyModel();
            tb_SkeyList.setModel(sm);
            tb_SkeyList.setRowSorter(new javax.swing.table.TableRowSorter<SkeyModel>(sm));
            reSize(sm, value);
        }
    }

    @Override
    public void showData()
    {
    }

    @Override
    public void saveData()
    {
    }

    @Override
    public javax.swing.JPanel getPanel()
    {
        return this;
    }

    private void reSize(SkeyModel model, Object[] value)
    {
        javax.swing.table.TableColumn column = null;
        int headerWidth;
        int columnWidth;
        javax.swing.table.TableCellRenderer headerRenderer = tb_SkeyList.getTableHeader().getDefaultRenderer();
        javax.swing.table.TableCellRenderer columnRenderer;
        javax.swing.table.TableColumnModel columnModel = tb_SkeyList.getColumnModel();
        for (int i = 0, j = columnModel.getColumnCount(); i < j; i++)
        {
            column = columnModel.getColumn(i);

            headerWidth = headerRenderer.getTableCellRendererComponent(tb_SkeyList, column.getHeaderValue(), false, false, 0, i).getPreferredSize().width;
            columnRenderer = tb_SkeyList.getDefaultRenderer(model.getColumnClass(i));
            columnWidth = columnRenderer.getTableCellRendererComponent(tb_SkeyList, value[i], false, false, 0, i).getPreferredSize().width;

            column.setPreferredWidth(Math.max(headerWidth, columnWidth));
        }
    }

    private class SkeyModel implements javax.swing.table.TableModel
    {

        @Override
        public void addTableModelListener(javax.swing.event.TableModelListener l)
        {
        }

        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            return String.class;
        }

        @Override
        public int getColumnCount()
        {
            return 2;
        }

        @Override
        public String getColumnName(int columnIndex)
        {
            return columnIndex == 0 ? Lang.getLang(LangRes.P30F2B01, "Key") : Lang.getLang(LangRes.P30F2B02, "Desp");
        }

        @Override
        public int getRowCount()
        {
            return skeyList.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            S1S2 item = skeyList.get(rowIndex);
            return columnIndex == 0 ? item.getV() : item.getV2();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return false;
        }

        @Override
        public void removeTableModelListener(javax.swing.event.TableModelListener l)
        {
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex)
        {
        }
    }
    private javax.swing.JTable tb_SkeyList;
}
