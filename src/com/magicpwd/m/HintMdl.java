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
package com.magicpwd.m;

import com.magicpwd.__i.IBackCall;
import com.magicpwd.__i.IHintView;
import com.magicpwd._comn.Task;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._util.Time;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.d.db.DBAccess;

/**
 * Application: MagicPwd
 * Author     : Aven
 * Encoding   : UTF-8
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public final class HintMdl
{

    private UserMdl userMdl;
    /**
     * 计划任务列表
     */
    private java.util.List<MgtdHeader> mgtdList;
    /**
     * 待办事项列表
     */
    private java.util.List<MgtdHeader> todoList;
    /**
     * 过期事项列表
     */
    private java.util.List<MgtdHeader> histList;
    /**
     * 提醒视图列表
     */
    private java.util.List<IHintView> viewList;
    private java.util.Calendar calendar;
    private int counter;

    HintMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    public void init()
    {
        mgtdList = new java.util.ArrayList<MgtdHeader>();
        todoList = new java.util.ArrayList<MgtdHeader>();
        histList = new java.util.ArrayList<MgtdHeader>();
        counter = userMdl.getHintInt();

        Time.getInstance().registerAction(new Task(3, 1, "mpwd-hint", ""), new IBackCall<String, Task>()
        {

            @Override
            public boolean callBack(String options, Task object)
            {
                object.setCounter(0);
                return reload(false);
            }
        });
    }

    public void registerHintView(IHintView view)
    {
        if (viewList == null)
        {
            viewList = new java.util.ArrayList<IHintView>();
        }
        viewList.add(view);
    }

    public synchronized boolean reload(boolean forced)
    {
        calendar = java.util.Calendar.getInstance();

        showTime();

        // 读取数据信息
        if (counter >= userMdl.getHintInt() || forced)
        {
            if (DBAccess.locked)
            {
                return false;
            }
            mgtdList.clear();
            DBA4000.findHintList(userMdl, mgtdList);
            counter = 0;
        }
        else
        {
            counter += 1;
        }

        // 到期提示判断
        todoList.clear();
        histList.clear();
        java.util.HashMap<String, Integer> updtList = new java.util.HashMap<String, Integer>();
        int x;
        for (MgtdHeader header : mgtdList)
        {
            // 等提醒
            x = Time.isOnTime(calendar, 1800000, header);
            if (x == 0)
            {
                todoList.add(header);
                if (header.getP30F0303() == ConsDat.MGTD_STATUS_INIT)
                {
                    header.setP30F0303(ConsDat.MGTD_STATUS_READY);
                    updtList.put(header.getP30F0309(), ConsDat.MGTD_STATUS_READY);
                }
            }
            // 已过期
            else if (x == -1)
            {
                histList.add(header);
                // 初始化或进行中的任务，修改为已过期
                if (header.getP30F0303() == ConsDat.MGTD_STATUS_READY || header.getP30F0303() == ConsDat.MGTD_STATUS_INIT)
                {
                    header.setP30F0303(ConsDat.MGTD_STATUS_DELAY);
                    updtList.put(header.getP30F0309(), ConsDat.MGTD_STATUS_DELAY);
                }
            }
        }

        // 更新记录的状态
        if (updtList.size() > 0)
        {
            DBA4000.updtMgtdStatus(userMdl, updtList);
        }

        showHint();
        return true;
    }

    /**
     * 显示日期信息
     */
    public void showTime()
    {
        // 没有观察者的情况
        if (viewList == null || viewList.size() < 1)
        {
            return;
        }

        java.util.Date date = calendar.getTime();
        for (IHintView view : viewList)
        {
            view.showTime(date);
        }
    }

    /**
     * 显示待办信息
     */
    public void showHint()
    {
        // 没有观察者的情况
        if (viewList == null || viewList.size() < 1)
        {
            return;
        }

        int todo = todoList.size();
        int hist = histList.size();

        for (IHintView view : viewList)
        {
            view.showHint(todo, hist);
        }
    }

    public java.util.List<MgtdHeader> getTodoList()
    {
        return todoList;
    }

    public java.util.List<MgtdHeader> getHistList()
    {
        return histList;
    }
}
