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
package com.magicpwd._comp.date;

/**
 *
 * @author Aven
 */
public class DayStatus
{

    private int day;
    private int status;

    public DayStatus()
    {
    }

    public DayStatus(int day, int status)
    {
        this.day = day;
        this.status = status;
    }

    /**
     * @return the day
     */
    public int getDay()
    {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day)
    {
        this.day = day;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }
}
