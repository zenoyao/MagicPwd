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
package com.magicpwd._comn;

/**
 *
 * @author Amon
 */
public class Task
{

    private int counter;
    private int initiate;
    private int interval;
    private String taskName;
    private String taskDesp;

    public Task()
    {
    }

    public Task(int interval, String taskName, String taskDesp)
    {
        this(0, interval, taskName, taskDesp);
    }

    public Task(int initiate, int interval, String taskName, String taskDesp)
    {
        this.initiate = initiate;
        this.interval = interval;
        this.taskName = taskName;
        this.taskDesp = taskDesp;
    }

    /**
     * @return the counter
     */
    public int getCounter()
    {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(int counter)
    {
        this.counter = counter;
    }

    public void addCounter(int counter)
    {
        this.counter += counter;
    }

    /**
     * @return the initiate
     */
    public int getInitiate()
    {
        return initiate;
    }

    /**
     * @param initiate the initiate to set
     */
    public void setInitiate(int initiate)
    {
        this.initiate = initiate;
    }

    public void addInitiate(int initiate)
    {
        this.initiate += initiate;
    }

    /**
     * @return the interval
     */
    public int getInterval()
    {
        return interval;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(int interval)
    {
        this.interval = interval;
    }

    public void addInterval(int interval)
    {
        this.interval += interval;
    }

    /**
     * @return the taskName
     */
    public String getTaskName()
    {
        return taskName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    /**
     * @return the taskDesp
     */
    public String getTaskDesp()
    {
        return taskDesp;
    }

    /**
     * @param taskDesp the taskDesp to set
     */
    public void setTaskDesp(String taskDesp)
    {
        this.taskDesp = taskDesp;
    }
}
