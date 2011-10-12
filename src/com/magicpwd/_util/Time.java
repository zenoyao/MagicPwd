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
package com.magicpwd._util;

import com.magicpwd.__i.IBackCall;
import com.magicpwd._comn.Task;
import com.magicpwd._comn.mpwd.MgtdDetail;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._cons.ConsDat;
import org.javia.arity.Symbols;

/**
 *
 * @author Amon
 */
public class Time implements java.awt.event.ActionListener
{

    private static Time taskTime;
    private java.util.HashMap<Task, IBackCall<String, Task>> tasks;
    private javax.swing.Timer timer;
    private long lastTime;

    private Time()
    {
        tasks = new java.util.HashMap<Task, IBackCall<String, Task>>();
        timer = new javax.swing.Timer(500, this);
        timer.setInitialDelay(3000);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        taskActionPerformed(e);
    }

    public static Time getInstance()
    {
        if (taskTime == null)
        {
            taskTime = new Time();
        }
        return taskTime;
    }

    public void registerAction(Task item, IBackCall<String, Task> backCall)
    {
        if (!tasks.containsKey(item))
        {
            tasks.put(item, backCall);
        }

        if (!timer.isRunning())
        {
            timer.start();
        }
    }

    public void removeAction(Task info)
    {
        tasks.remove(info);
        if (tasks.size() < 1)
        {
            if (timer.isRunning())
            {
                timer.stop();
            }
        }
    }

    public IBackCall<String, Task> getAction(String taskName)
    {
        for (Task info : tasks.keySet())
        {
            if (info.getTaskName().equals(taskName))
            {
                return tasks.get(info);
            }
        }
        return null;
    }

    public IBackCall getAction(Task info)
    {
        return tasks.get(info);
    }

    public boolean reActive(String key, int interval)
    {
        if (key == null)
        {
            return false;
        }

        for (Task info : tasks.keySet())
        {
            if (info.getTaskName().equals(key))
            {
                info.setCounter(0);
                info.setInterval(interval);
                return true;
            }
        }

        return false;
    }

    public boolean deActive(String key)
    {
        if (key == null)
        {
            return false;
        }

        for (Task info : tasks.keySet())
        {
            if (info.getTaskName().equals(key))
            {
                info.setCounter(info.getInterval() + 1);
                return true;
            }
        }

        return false;
    }

    public boolean reActive(String key, int initiate, int interval)
    {
        if (key == null)
        {
            return false;
        }

        for (Task info : tasks.keySet())
        {
            if (info.getTaskName().equals(key))
            {
                info.setCounter(0);
                info.setInitiate(initiate);
                info.setInterval(interval);
                return true;
            }
        }

        return false;
    }

    private void taskActionPerformed(java.awt.event.ActionEvent e)
    {
        long t = System.currentTimeMillis();
        if (t - lastTime < 1000)
        {
            return;
        }
        lastTime = t;
        for (final Task task : tasks.keySet())
        {
            if (task.getInitiate() > 0)
            {
                task.addInitiate(-1);
                continue;
            }

            if (task.getCounter() > task.getInterval())
            {
                continue;
            }

            task.addCounter(1);
            if (task.getCounter() == task.getInterval())
            {
                new Thread()
                {

                    @Override
                    public void run()
                    {
                        tasks.get(task).callBack(null, task);
                    }
                }.start();
            }
        }
    }

    /**
     * 计算指定任务是否满足可提示条件
     * @param time 计算参照时间
     * @param length 可接受的时间段，以毫秒计
     * @param hint 计划任务
     * @return -2:异常、-1:过期、0满足、1未到
     */
    public static int isOnTime(java.util.Calendar time, long length, MgtdHeader mgtd)
    {
        switch (mgtd.getP30F0312())
        {
            case ConsDat.MGTD_UNIT_SECOND:
                time.add(java.util.Calendar.SECOND, mgtd.getP30F0313());
                break;
            case ConsDat.MGTD_UNIT_MINUTE:
                time.add(java.util.Calendar.MINUTE, mgtd.getP30F0313());
                break;
            case ConsDat.MGTD_UNIT_HOUR:
                time.add(java.util.Calendar.HOUR_OF_DAY, mgtd.getP30F0313());
                break;
            case ConsDat.MGTD_UNIT_DAY:
                time.add(java.util.Calendar.DAY_OF_MONTH, mgtd.getP30F0313());
                break;
            case ConsDat.MGTD_UNIT_WEEK:
                time.add(java.util.Calendar.DAY_OF_MONTH, 7 * mgtd.getP30F0313());
                break;
            case ConsDat.MGTD_UNIT_MONTH:
                time.add(java.util.Calendar.MONTH, mgtd.getP30F0313());
                break;
            case ConsDat.MGTD_UNIT_YEAR:
                time.add(java.util.Calendar.YEAR, mgtd.getP30F0313());
                break;
        }
        long now = time.getTimeInMillis();

        // 定时
        if (mgtd.getP30F0305() == ConsDat.MGTD_INTVAL_FIXTIME)
        {
            for (MgtdDetail detail : mgtd.getHintList())
            {
                long dif = detail.getP30F0403() - now;
                return dif < 0 ? -1 : (dif <= length ? 0 : 1);
            }
        }
        // 公式
        if (mgtd.getP30F0305() == ConsDat.MGTD_INTVAL_FORMULA)
        {
            for (MgtdDetail detail : mgtd.getHintList())
            {
                String exp = detail.getP30F0406();
                if (!Char.isValidate(exp))
                {
                    continue;
                }

                try
                {
                    exp = exp.replaceAll("(n|nian|year)", "" + time.get(java.util.Calendar.YEAR));
                    exp = exp.replaceAll("(y|yue|month)", "" + (time.get(java.util.Calendar.MONTH) + 1));
                    exp = exp.replaceAll("(r|ri|day)", "" + time.get(java.util.Calendar.DAY_OF_MONTH));
                    exp = exp.replaceAll("(s|shi|hour)", "" + time.get(java.util.Calendar.HOUR_OF_DAY));
                    exp = exp.replaceAll("(f|fen|minute)", "" + time.get(java.util.Calendar.MINUTE));
                    exp = exp.replaceAll("(m|miao|second)", "" + time.get(java.util.Calendar.SECOND));
                    exp = exp.replaceAll("(z|zhou|date)", "" + (time.get(java.util.Calendar.DAY_OF_WEEK) - 1));
                    double dif = new Symbols().eval(exp);
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                catch (Exception e)
                {
                    Logs.exception(e);
                }
            }
            return -2;
        }
        if (mgtd.getP30F0305() == ConsDat.MGTD_INTVAL_PERIOD)
        {
            for (MgtdDetail detail : mgtd.getHintList())
            {
                // 按秒重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_SECOND)
                {
                    long dif = detail.getP30F0403() - now;
                    dif %= (detail.getP30F0405() * 1000);
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按分重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_MINUTE)
                {
                    // long diff = (now - mtss.getP30F0404()) / 1000 / 60;
                    long dif = detail.getP30F0403() - now;
                    dif %= (detail.getP30F0405() * 60000);
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按时重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_HOUR)
                {
                    // long diff = (now - mtss.getP30F0404()) / 1000 / 60 / 60;
                    long dif = detail.getP30F0403() - now;
                    dif %= (detail.getP30F0405() * 3600000);
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按天重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_DAY)
                {
                    // long diff = (now - mtss.getP30F0404()) / 1000 / 60 / 60 / 24;
                    long dif = detail.getP30F0403() - now;
                    dif %= (detail.getP30F0405() * 86400000);
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按周重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_WEEK)
                {
                    // long diff = (now - mtss.getP30F0404()) / 1000 / 60 / 60 / 24 / 7;
                    long dif = detail.getP30F0403() - now;
                    dif %= (detail.getP30F0405() * 604800000);
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按月重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_MONTH)
                {
                    java.util.Calendar tmp = (java.util.Calendar) time.clone();
                    tmp.setTimeInMillis(detail.getP30F0403());
                    int dif = (time.get(java.util.Calendar.YEAR) - tmp.get(java.util.Calendar.YEAR)) * 12 + time.get(java.util.Calendar.MONTH) - tmp.get(java.util.Calendar.MONTH);
                    dif %= detail.getP30F0405();
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按年重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_YEAR)
                {
                    java.util.Calendar tmp = (java.util.Calendar) time.clone();
                    tmp.setTimeInMillis(detail.getP30F0403());
                    int dif = time.get(java.util.Calendar.YEAR) - tmp.get(java.util.Calendar.YEAR);
                    dif %= detail.getP30F0405();
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
            }
            return -2;
        }
        if (mgtd.getP30F0305() == ConsDat.MGTD_INTVAL_INTVAL)
        {
            for (MgtdDetail detail : mgtd.getHintList())
            {
                // 按秒重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_SECOND)
                {
                    // long diff = (now - mtss.getP30F0404()) / 1000;
                    long dif = detail.getP30F0403() - now;
                    dif %= (detail.getP30F0405() * 1000);
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按分重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_MINUTE)
                {
                    // long diff = (now - mtss.getP30F0404()) / 1000 / 60;
                    long dif = detail.getP30F0403() - now;
                    dif %= (detail.getP30F0405() * 60000);
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按时重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_HOUR)
                {
                    // long diff = (now - mtss.getP30F0404()) / 1000 / 60 / 60;
                    long dif = detail.getP30F0403() - now;
                    dif %= (detail.getP30F0405() * 3600000);
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按天重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_DAY)
                {
                    // long diff = (now - mtss.getP30F0404()) / 1000 / 60 / 60 / 24;
                    long dif = detail.getP30F0403() - now;
                    dif %= (detail.getP30F0405() * 86400000);
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按周重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_WEEK)
                {
                    // long diff = (now - mtss.getP30F0404()) / 1000 / 60 / 60 / 24 / 7;
                    long dif = detail.getP30F0403() - now;
                    dif %= (detail.getP30F0405() * 604800000);
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按月重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_MONTH)
                {
                    java.util.Calendar tmp = (java.util.Calendar) time.clone();
                    tmp.setTimeInMillis(detail.getP30F0403());
                    int dif = (time.get(java.util.Calendar.YEAR) - tmp.get(java.util.Calendar.YEAR)) * 12 + time.get(java.util.Calendar.MONTH) - tmp.get(java.util.Calendar.MONTH);
                    dif %= detail.getP30F0405();
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
                // 按年重复
                if (detail.getP30F0404() == ConsDat.MGTD_UNIT_YEAR)
                {
                    java.util.Calendar tmp = (java.util.Calendar) time.clone();
                    tmp.setTimeInMillis(detail.getP30F0403());
                    int dif = time.get(java.util.Calendar.YEAR) - tmp.get(java.util.Calendar.YEAR);
                    dif %= detail.getP30F0405();
                    return dif < 0 ? -1 : (dif <= length ? 0 : 1);
                }
            }
            return -2;
        }
        return -2;
    }
}
