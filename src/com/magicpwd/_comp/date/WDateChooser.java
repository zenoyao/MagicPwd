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
package com.magicpwd._comp.date;

import com.magicpwd.__i.IBackCall;
import java.util.HashMap;

public class WDateChooser
{

    private boolean dateVisible;
    private boolean timeVisible;
    private String format;
    private java.util.HashMap<Integer, java.util.HashMap<Integer, java.util.HashMap<Integer, Integer>>> specialDays;

    public WDateChooser()
    {
    }

    public void setDateFormat(String format)
    {
        this.format = format;
    }

    public boolean isDateVisible()
    {
        return dateVisible;
    }

    public void setDateVisible(boolean visible)
    {
        this.dateVisible = visible;
    }

    public boolean isTimeVisible()
    {
        return timeVisible;
    }

    public void setTimeVisible(boolean visible)
    {
        this.timeVisible = visible;
    }

    public void show(final javax.swing.text.JTextComponent cmp, int x, int y)
    {
        showMenu(new IBackCall<String, java.util.Calendar>()
        {

            @Override
            public boolean callBack(String options, java.util.Calendar object)
            {
                cmp.setText(getDate(object));
                return true;
            }
        });
        pm_DateMenu.show(cmp, x, y);
    }

    public void show(final javax.swing.AbstractButton btn, int x, int y)
    {
        showMenu(new IBackCall<String, java.util.Calendar>()
        {

            @Override
            public boolean callBack(String options, java.util.Calendar object)
            {
                btn.setText(getDate(object));
                return true;
            }
        });
        pm_DateMenu.show(btn, x, y);
    }

    public void show(final javax.swing.JLabel lbl, int x, int y)
    {
        showMenu(new IBackCall<String, java.util.Calendar>()
        {

            @Override
            public boolean callBack(String options, java.util.Calendar object)
            {
                lbl.setText(getDate(object));
                return true;
            }
        });
        pm_DateMenu.show(lbl, x, y);
    }

    public void show(int x, int y, IBackCall<String, java.util.Calendar> backCall)
    {
        showMenu(backCall);
        pm_DateMenu.show(null, x, y);
    }

    private void showMenu(IBackCall<String, java.util.Calendar> backCall)
    {
        if (dp_DatePanel == null)
        {
            dp_DatePanel = new DatePanel(this);
            dp_DatePanel.initView();
            dp_DatePanel.initLang();
            dp_DatePanel.initData();

            pm_DateMenu = new javax.swing.JPopupMenu();
            pm_DateMenu.add(dp_DatePanel);
        }
        dp_DatePanel.showData();
        dp_DatePanel.setBackCall(backCall);
    }

    String getDate(java.util.Calendar calendar)
    {
        if (calendar == null)
        {
            return "";
        }
        if (format == null)
        {
            format = "";
        }
//        return format.format(calendar.getTime());
        return calendar.get(java.util.Calendar.YEAR) + "-" + calendar.get(java.util.Calendar.MONTH) + "-" + calendar.get(java.util.Calendar.DAY_OF_MONTH);
    }

    public void appendDisabledDay(int day)
    {
        appendDisabledDay(0, day);
    }

    public void appendDisabledDay(int month, int day)
    {
        appendDisabledDay(0, 0, day);
    }

    public void appendDisabledDay(int year, int month, int day)
    {
        if (day < 1 || day > 31)
        {
            return;
        }
        if (specialDays == null)
        {
            specialDays = new java.util.HashMap<Integer, java.util.HashMap<Integer, java.util.HashMap<Integer, Integer>>>();
        }
        java.util.HashMap<Integer, java.util.HashMap<Integer, Integer>> yMap = specialDays.get(year);
        if (yMap == null)
        {
            yMap = new java.util.HashMap<Integer, java.util.HashMap<Integer, Integer>>(14);
            specialDays.put(year, yMap);
        }
        java.util.HashMap<Integer, Integer> mMap = yMap.get(month);
        if (mMap == null)
        {
            mMap = new java.util.HashMap<Integer, Integer>(36);
            yMap.put(month, mMap);
        }
        mMap.put(day, day);
    }

    public void setSelectedDate(int year, int month, int day)
    {
    }

    public void setSelectedTime(int hour, int minute, int second)
    {
    }

    public void setSelectedDateTime(int year, int month, int day, int hour, int minute, int second)
    {
    }
    private DatePanel dp_DatePanel;
    private javax.swing.JPopupMenu pm_DateMenu;
}
