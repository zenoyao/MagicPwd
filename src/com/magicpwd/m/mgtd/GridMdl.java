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
package com.magicpwd.m.mgtd;

import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._cons.ConsDat;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.UserMdl;

/**
 * @author Amon
 */
public class GridMdl extends javax.swing.table.DefaultTableModel
{

    private java.util.List<MgtdHeader> lsMgtdList;
    private java.util.Map<String, Integer> updtList;
    private UserMdl userMdl;

    GridMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    void init()
    {
        lsMgtdList = new java.util.ArrayList<MgtdHeader>();
        updtList = new java.util.HashMap<String, Integer>();
        DBA4000.listGtdHeader(userMdl, lsMgtdList);
    }

    @Override
    public int getRowCount()
    {
        return lsMgtdList != null ? lsMgtdList.size() : 0;
    }

    @Override
    public int getColumnCount()
    {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return "..";
            case 1:
                return "标题";
            case 2:
                return "优先级";
            case 3:
                return "提醒类型";
            case 4:
                return "提示方式";
            case 5:
                return "提前时间";
            default:
                return "";
        }
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
        // 公共属性
        if (lsMgtdList != null && rowIndex > -1 && rowIndex < lsMgtdList.size())
        {
            MgtdHeader temp = lsMgtdList.get(rowIndex);
            switch (columnIndex)
            {
                case 0:
                    return rowIndex + 1;
                case 1:
                    return temp.getP30F030C();
                case 2:
                    switch (temp.getP30F0304())
                    {
                        case 1:
                            return "低";
                        case 2:
                            return "中";
                        case 3:
                            return "高";
                        default:
                            return "无";
                    }
                case 3:
                    switch (temp.getP30F0305())
                    {
                        case ConsDat.MGTD_INTVAL_FIXTIME:
                            return "定时提醒";
                        case ConsDat.MGTD_INTVAL_PERIOD:
                            return "周期提醒";
                        case ConsDat.MGTD_INTVAL_INTVAL:
                            return "间隔提醒";
                        case ConsDat.MGTD_INTVAL_SPECIAL:
                            return "特殊提醒";
                        case ConsDat.MGTD_INTVAL_FORMULA:
                            return "公式提醒";
                        default:
                            return "未知";
                    }
                case 4:
                    switch (temp.getP30F0306())
                    {
                        case ConsDat.MGTD_METHOD_NOTE:
                            return "提示信息";
                        case ConsDat.MGTD_METHOD_MAIL:
                            return "发送邮件";
                        case ConsDat.MGTD_METHOD_APPS:
                            return "执行程序";
                        case ConsDat.MGTD_METHOD_FILE:
                            return "打开文件";
                        case ConsDat.MGTD_METHOD_HTTP:
                            return "访问网页";
                        case ConsDat.MGTD_METHOD_AUDIO:
                            return "播放声音";
                        default:
                            return "未知";
                    }
                case 5:
                    switch (temp.getP30F0312())
                    {
                        case ConsDat.MGTD_UNIT_SECOND:
                            return "秒";
                        case ConsDat.MGTD_UNIT_MINUTE:
                            return "分";
                        case ConsDat.MGTD_UNIT_HOUR:
                            return "时";
                        case ConsDat.MGTD_UNIT_DAY:
                            return "日";
                        case ConsDat.MGTD_UNIT_WEEK:
                            return "周";
                        case ConsDat.MGTD_UNIT_MONTH:
                            return "月";
                        case ConsDat.MGTD_UNIT_YEAR:
                            return "日";
                        default:
                            return "无";
                    }
            }
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
    }

    public MgtdHeader getMgtdAt(int index)
    {
        return lsMgtdList.get(index);
    }

    public void wDelete(int index)
    {
        if (DBA4000.deleteMgtdData(userMdl, lsMgtdList.get(index)))
        {
            lsMgtdList.remove(index);
            fireTableDataChanged();
        }
    }

    public void wAppend(MgtdHeader mgtd)
    {
        if (DBA4000.saveMgtdData(userMdl, mgtd))
        {
            lsMgtdList.clear();
            DBA4000.listGtdHeader(userMdl, lsMgtdList);
            fireTableDataChanged();
        }
    }

    public void wReload()
    {
        lsMgtdList.clear();
        DBA4000.listGtdHeader(userMdl, lsMgtdList);
        fireTableDataChanged();
    }
}
