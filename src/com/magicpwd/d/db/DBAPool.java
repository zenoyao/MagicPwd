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
package com.magicpwd.d.db;

import com.magicpwd._util.Logs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.LinkedList;

/**
 * 管理类DBConnectionManager支持对一个或多个由属性文件定义的数据库连接池的
 * 访问.客户程序可以调用getInstance()方法访问本类的唯一实例 
 * @author Amon
 */
public class DBAPool
{
    private int useCnt; // 当前连接数
    private LinkedList<Connection> list; // 保存所有可用连接
    private int maxCnt; // 此连接池允许建立的最大连接数
    private String name; // 连接池名字
    private String pwd; // 密码或null
    private String url; // 数据库的URL
    private String usr; // 数据库账号或null

    public DBAPool()
    {
        this("pool", 1);
    }

    /**
     * 创建新的连接池
     *
     * @param name 连接池名字
     * @param maxConn 此连接池允许建立的最大连接数
     */
    public DBAPool(String name, int maxConn)
    {
        list = new LinkedList<Connection>();
        maxCnt = maxConn > 0 ? maxConn : 1;
    }

    /**
     * 将不再使用的连接返回给连接池
     * @param con 客户程序释放的连接
     */
    public synchronized void freeConnection(Connection con)
    {
        // 将指定连接加入到向量末尾
        list.add(con);
        useCnt--;
        //notifyAll(); // 删除等待队列中的所有线程
    }

    /**
     * 从连接池获得一个可用连接.如果没有空闲的连接且当前连接数小于最大连接
     * 数限制,则创建新连接.如原来登记为可用的连接不再有效,则从向量删除之, 然后递归调用自己以尝试新的可用连接.
     */
    public synchronized Connection getConnection()
    {
        Connection con = null;
        if (list.size() > 0)
        {
            // 获取向量中第一个可用连接
            con = list.remove(0);
            try
            {
                if (con.isClosed())
                {
                    Logs.log("从连接池" + name + "删除一个无效连接");
                    // 递归调用自己,尝试再次获取可用连接
                    con = getConnection();
                }
            }
            catch (Exception e)
            {
                Logs.log("从连接池" + name + "删除一个无效连接");
                // 递归调用自己,尝试再次获取可用连接
                con = getConnection();
            }
        }
        else if (useCnt < maxCnt)
        {
            con = newConnection();
        }
        if (con != null)
        {
            useCnt++;
        }
        return con;
    }

    /**
     * 从连接池获取可用连接.可以指定客户程序能够等待的最长时间 参见前一个getConnection()方法.
     *
     * @param timeout 以毫秒计的等待时间限制
     */
    public synchronized Connection getConnection(long timeout)
    {
        long startTime = new Date().getTime();
        Connection conn;
        while ((conn = getConnection()) == null)
        {
            try
            {
                wait(timeout);
            }
            catch (InterruptedException e)
            {
                Logs.exception(e);
            }
            if ((new Date().getTime() - startTime) >= timeout)
            {
                // wait()返回的原因是超时
                return null;
            }
        }
        return conn;
    }

    /**
     * 关闭所有连接
     */
    public synchronized void exitConnection()
    {
        for (Connection conn : list)
        {
            try
            {
                conn.close();
                Logs.log("关闭连接池" + name + "中的一个连接");
            }
            catch (Exception e)
            {
                Logs.exception(e);
            }
        }
        list.clear();
    }

    /**
     * 创建新的连接
     */
    private Connection newConnection()
    {
        Connection conn = null;
        try
        {
            conn = (usr != null ? DriverManager.getConnection(url, usr, pwd) : DriverManager.getConnection(url));
            Logs.log("连接池" + name + "创建一个新的连接");
        }
        catch (Exception e)
        {
            Logs.exception(e);
        }
        return conn;
    }
}
