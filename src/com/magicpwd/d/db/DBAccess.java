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

import com.magicpwd._cons.ConsCfg;
import com.magicpwd._util.Char;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.magicpwd._util.Logs;
import com.magicpwd.m.UserMdl;

/**
 * @author Amon
 * 
 */
public class DBAccess
{
    public static boolean locked = false;
    private static String dbPath;

    /**
     * 默认构造函数，事务默认自动提交
     */
    public DBAccess()
    {
    }

    /**
     * 在保持同一个连接的基础上，重新设置查寻SQL语句，以进行新的数据库操作
     */
    public void init(UserMdl userMdl) throws SQLException
    {
        if (!Char.isValidate(dbPath))
        {
            StringBuilder buf = new StringBuilder();
            buf.append("jdbc:hsqldb:file:").append(userMdl.getDataDir()).append("amon");
            String sc = userMdl.getCfg(ConsCfg.CFG_DB_SC, "");
            String sk = userMdl.getCfg(ConsCfg.CFG_DB_SK, "");
            if (Char.isValidate(sc) && Char.isValidate(sk))
            {
                buf.append(";crypt_type=").append(sc);
                buf.append(";crypt_key=").append(sk);
            }
            dbPath = buf.toString();
        }
        connect();

        // 参数列表
        paramList = new ArrayList<String>();
        // 关联符号
        signList = new ArrayList<String>();
        // 数据列表
        valueList = new ArrayList<String>();

        // 字段缓冲
        columList = new StringBuffer();
        // 表格缓冲
        tableList = new StringBuffer();
        // 关联每件缓冲
        whereList = new StringBuffer();
        // 排序依据缓冲
        orderList = new StringBuffer();
    }

    public void reInit()
    {
        limit = null;

        this.paramList.clear();
        this.signList.clear();
        this.valueList.clear();

        this.columList.delete(0, columList.length());
        this.tableList.delete(0, tableList.length());
        this.whereList.delete(0, whereList.length());
        this.orderList.delete(0, orderList.length());
    }

    private Statement connect() throws SQLException
    {
        if (conn == null || conn.isClosed())
        {
            conn = DriverManager.getConnection(dbPath);
        }
        if (stat == null || stat.isClosed())
        {
            stat = conn.createStatement();
        }
        return stat;
    }

    /**
     * 关闭数据库
     * @throws SQLException
     */
    public void dispose()
    {
        if (stat != null)
        {
            try
            {
                stat.close();
            }
            catch (SQLException exp)
            {
                Logs.exception(exp);
            }
            finally
            {
                stat = null;
            }
        }

        // Connection 关闭
        if (conn != null)
        {
            try
            {
                conn.close();
            }
            catch (SQLException exp)
            {
                Logs.exception(exp);
            }
            finally
            {
                conn = null;
            }
        }
    }

    public void backup()
    {
        try
        {
            connect().execute("BACKUP DATABASE to '../bak/' BLOCKING");
        }
        catch (SQLException exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            dispose();
        }
    }

    /**
     * 关闭数据库
     */
    public void shutdown()
    {
        try
        {
            connect().execute("SHUTDOWN");
        }
        catch (SQLException exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            dispose();
        }
    }

    /**
     * 添加要使用的表格
     * 
     * @param table
     */
    public void addTable(String table)
    {
        if (com.magicpwd._util.Char.isValidate(table))
        {
            tableList.append(", ").append(table);
        }
    }

    /**
     * 添加要使用的表格
     * @param table 表格名称
     * @param alias 表格别名
     */
    public void addTable(String table, String alias)
    {
        if (com.magicpwd._util.Char.isValidate(table))
        {
            tableList.append(", ").append(table).append(" AS ").append(alias);
        }
    }

    /**
     * 添加带连接的表格
     * 
     * @param tableLeft
     * @param tableRight
     * @param joinStyle
     *            详细参见：<br>
     *            cons.SysCons.DB0008.DBCS_JOIN_CROSS<br>
     *            cons.SysCons.DB0008.DBCS_JOIN_FULL<br>
     *            cons.SysCons.DB0008.DBCS_JOIN_LEFT<br>
     *            cons.SysCons.DB0008.DBCS_JOIN_RIGHT<br>
     */
    public void addTable(String tableLeft, String tableRight, String joinStyle)
    {
        tableList.append(", ").append(tableLeft).append(' ').append(joinStyle).append(' ').append(tableRight);
    }

    /**
     * 添加带连接的表格
     * 
     * @param tableLeft
     * @param tableRight
     * @param joinStyle
     *            详细参见：<br>
     *            cons.SysCons.DB0008.DBCS_JOIN_CROSS<br>
     *            cons.SysCons.DB0008.DBCS_JOIN_FULL<br>
     *            cons.SysCons.DB0008.DBCS_JOIN_LEFT<br>
     *            cons.SysCons.DB0008.DBCS_JOIN_RIGHT<br>
     * @param columnLeft
     * @param columnRight
     */
    public void addTable(String tableLeft, String tableRight, String joinStyle, String columnLeft, String columnRight)
    {
        tableList.append(", ").append(tableLeft).append(' ').append(joinStyle).append(' ').append(tableRight);
        tableList.append(" ON ").append(columnLeft).append(" = ").append(columnRight);
    }

    /**
     * 添加查寻字段
     * 
     * <pre>
     * SELECT colname1, colname2, ... FROM tablename
     * </pre>
     * 
     * @param colname 对应于要查寻的表格的相关字段
     */
    public void addColumn(String colname)
    {
        if (com.magicpwd._util.Char.isValidate(colname))
        {
            columList.append(", ").append(colname);
        }
    }

    /**
     * 添加查寻字段
     * 
     * <pre>
     * SELECT colname1 AS a, colname2 AS b, ... FROM tablename
     * </pre>
     * 
     * @param colname 对应于要查寻的表格的相关字段
     * @param alias 字段别名
     */
    public void addColumn(String colname, String alias)
    {
        if (com.magicpwd._util.Char.isValidate(colname))
        {
            columList.append(", ").append(colname).append(" AS ").append(alias);
        }
    }

    public void addParam(String key, Integer value)
    {
        addParam(key, "=", value);
    }

    /**
     * 数据更新：添加数据库操作的KEY-VALUE对。
     * 
     * <pre>
     * UPDATE SET key1=value1, key2=value2 ...
     * </pre>
     * 
     * @param key 数据库字段名
     * @param value 对应字段的值
     */
    public void addParam(String key, Long value)
    {
        addParam(key, "=", value);
    }

    /**
     * 数据更新：添加数据库操作的KEY-VALUE对。
     * 
     * <pre>
     * UPDATE SET key1=value1, key2=value2 ...
     * </pre>
     * 
     * @param key 数据库字段名
     * @param value 对应字段的值
     */
    public void addParam(String key, String value)
    {
        addParam(key, value, true);
    }

    /**
     * 数据更新：添加数据库操作的KEY-VALUE对。
     * 
     * <pre>
     * UPDATE SET key1=value1, key2=value2 ...
     * </pre>
     * 
     * @param key 数据库字段名
     * @param value 对应字段的值
     * @param isLiteral 当前字段是否为纯文本，true为纯文本，false为其它格式。
     */
    public void addParam(String key, String value, boolean isLiteral)
    {
        addParam(key, "=", value, isLiteral);
    }

    /**
     * 数据更新：添加数据库操作的KEY-VALUE对。
     * 
     * @param param 数据库字段名
     * @param sign 运算操作符
     * @param value 对应字段的值
     */
    public void addParam(String param, String sign, String value)
    {
        addParam(param, sign, value, true);
    }

    /**
     * 数据更新：添加数据库操作的KEY-VALUE对。
     * 
     * @param param 数据库字段名
     * @param sign 运算操作符
     * @param value 对应字段的值
     * @param isLiteral 当前字段是否为纯文本，true为纯文本，false为其它格式。
     */
    public void addParam(String param, String sign, String value, boolean isLiteral)
    {
        if (com.magicpwd._util.Char.isValidate(param))
        {
            paramList.add(param);
            signList.add(sign);

            // 值信息处理
            if (value == null)
            {
                valueList.add("NULL");
            }
            else if (isLiteral)
            {
                valueList.add(" '" + value + "'");
            }
            else
            {
                valueList.add(value);
            }
        }
    }

    public void addParam(String param, String sign, Integer value)
    {
        paramList.add(param);
        signList.add(sign);
        valueList.add(value == null ? "" : Integer.toString(value, 10));
    }

    /**
     * 
     * @param param
     * @param sign
     * @param value
     */
    public void addParam(String param, String sign, Long value)
    {
        paramList.add(param);
        signList.add(sign);
        valueList.add(value == null ? "" : Long.toString(value, 10));
    }

    /**
     * 用户自定义关联条件
     * 
     * @param where
     */
    public void addWhere(String where)
    {
        whereList.append(" AND (").append(where).append(") ");
    }

    public void addWhere(String key, long value)
    {
        addWhere(key, "=", value);
    }

    /**
     * 添加WHERE查寻条件，默认运算符为等号“=”，默认值为纯文本
     * 
     * <pre>
     * UPDATE tablename SET ... WHERE key1=value1 AND key2=value2 ...
     * </pre>
     * 
     * @param key 参照数据库表格字段名
     * @param value 参照值
     */
    public void addWhere(String key, String value)
    {
        addWhere(key, value, true);
    }

    /**
     * 添加WHERE查寻条件，默认运算符为等号“=”
     * 
     * <pre>
     * UPDATE tablename SET ... WHERE key1=value1 AND key2=value2 ...
     * </pre>
     * 
     * @param key 参照数据库表格字段名
     * @param value 参照值
     * @param isLiteral 是否为纯文本字符串，true为纯文本，false为非文本
     */
    public void addWhere(String key, String value, boolean isLiteral)
    {
        addWhere(key, "=", value, isLiteral);
    }

    public void addWhere(String key, String sign, long value)
    {
        if (com.magicpwd._util.Char.isValidate(key))
        {
            whereList.append(" AND ").append(key).append(" ").append(sign).append(" ").append(value);
        }
    }

    /**
     * 添加WHERE查寻条件
     * 
     * <pre>
     * UPDATE tablename SET ... WHERE key1=value1 AND key2=value2 ...
     * </pre>
     * 
     * @param key 参照数据库表格字段名
     * @param sign 参照运算符，如+、-、=、!=等
     * @param value 参照值
     * @param plainText 是否为纯文本字符串，true为纯文本，false为非文本
     */
    public void addWhere(String key, String sign, String value, boolean plainText)
    {
        if (com.magicpwd._util.Char.isValidate(key) && value != null && com.magicpwd._util.Char.isValidate(sign))
        {
            whereList.append(" AND ").append(key);
            whereList.append(" ").append(sign);

            // 值信息处理
            if (plainText)
            {
                whereList.append(" '").append(value).append("'");
            }
            else
            {
                whereList.append(" ").append(value);
            }
        }
    }

    /**
     * 默认升序排序
     * 
     * @param key
     */
    public void addSort(String key)
    {
        addSort(key, true);
    }

    /**
     * 添加排序依据
     * 
     * <pre>
     * SELECT * FROM tablename WHERE ... ORDER BY key1 value1, key2 value2, ...
     * </pre>
     * 
     * @param key 参照数据库表格字段
     * @param value 排序方法:ASC表示升序;DESC表示降序
     */
    public void addSort(String key, boolean asc)
    {
        if (com.magicpwd._util.Char.isValidate(key))
        {
            orderList.append(", ").append(key);
            orderList.append(" ").append(asc ? "ASC" : "DESC");
        }
    }

    public void addLimit(int begin, int offset)
    {
        limit = "limit " + begin + ' ' + offset;
    }

    /**
     * 
     * @param sql
     * @throws SQLException
     */
    public void addBatch(String sql) throws SQLException
    {
        Logs.log(sql);

        stat.addBatch(sql);
    }

    /**
     * 数据复制
     * @param f
     * @param t
     * @throws SQLException
     */
    public void addCopyBatch(String t, String f) throws SQLException
    {
        addBatch(getCopySQL(t, f));
    }

    /**
     * 
     * @throws SQLException
     */
    public void addDeleteBatch() throws SQLException
    {
        addBatch(getDeleteSQL());
    }

    /**
     * 
     * @throws SQLException
     */
    public void addInsertBatch() throws SQLException
    {
        addBatch(getInsertSQL());
    }

    public void addUpdateBatch() throws SQLException
    {
        addBatch(getUpdateSQL());
    }

    /**
     * 
     * @throws SQLException
     */
    public void executeBatch() throws SQLException
    {
        if (locked)
        {
            return;
        }

        stat.executeBatch();
        stat.clearBatch();
    }

    /**
     * 其它相关的数据库操作，如COMMIT、HSQL专有的SHUTDOWN等。
     * 
     * @param sql 要执行的SQL语句
     * @return 当前操作是否成功
     * @throws SQLException
     */
    public boolean execute(String sql) throws SQLException
    {
        if (locked)
        {
            return false;
        }

        boolean success = false;
        if (stat != null)
        {
            success = stat.execute(sql);
        }
        return success;
    }

    public void executeCopy()
    {
        if (locked)
        {
            return;
        }
    }

    /**
     * 数据库查寻
     * 
     * @return 查寻结果集
     * @throws SQLException
     */
    public ResultSet executeSelect() throws SQLException
    {
        return executeSelect(getSelectSQL());
    }

    /**
     * 数据库查寻
     * 
     * @param sql 查寻语句
     * @return 查寻结果集
     * @throws SQLException
     */
    public ResultSet executeSelect(String sql) throws SQLException
    {
        if (locked)
        {
            return null;
        }

        Logs.log(sql);

        ResultSet rest = null;
        if (stat != null)
        {
            rest = stat.executeQuery(sql);
        }
        return rest;
    }

    /**
     * 数据库更新
     * 
     * @return 当前操作所影响的行数
     * @throws SQLException
     */
    public int executeUpdate() throws SQLException
    {
        return executeUpdate(getUpdateSQL());
    }

    /**
     * 数据库更新
     * 
     * @param sql 更新语句
     * @return 当前操作所影响的行数
     * @throws SQLException
     */
    public int executeUpdate(String sql) throws SQLException
    {
        int recSize = -1;
        if (locked)
        {
            return recSize;
        }

        Logs.log(sql);

        if (stat != null && com.magicpwd._util.Char.isValidate(sql))
        {
            recSize = stat.executeUpdate(sql);
        }
        return recSize;
    }

    /**
     * 数据库插入
     * 
     * @return 当前操作所影响的行数
     * @throws SQLException
     */
    public int executeInsert() throws SQLException
    {
        return executeInsert(getInsertSQL());
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     */
    public int executeInsert(String sql) throws SQLException
    {
        int recSize = -1;
        if (locked)
        {
            return recSize;
        }

        Logs.log(sql);

        if (stat != null && com.magicpwd._util.Char.isValidate(sql))
        {
            recSize = stat.executeUpdate(sql);
        }
        return recSize;
    }

    /**
     * 数据库删除
     * 
     * @return
     * @throws SQLException
     */
    public int executeDelete() throws SQLException
    {
        return executeDelete(getDeleteSQL());
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     */
    public int executeDelete(String sql) throws SQLException
    {
        int recSize = -1;
        if (locked)
        {
            return recSize;
        }

        Logs.log(sql);

        if (stat != null && com.magicpwd._util.Char.isValidate(sql))
        {
            recSize = stat.executeUpdate(sql);
        }
        return recSize;
    }

    private String getCopySQL(String t, String f)
    {
        StringBuilder sqlBuf = new StringBuilder();

        // 插入目标
        sqlBuf.append("INSERT INTO ").append(t).append(" (");
        int j = paramList.size() - 1;
        for (int i = 0; i < j; i += 1)
        {
            sqlBuf.append(paramList.get(i)).append(", ");
        }
        sqlBuf.append(paramList.get(j)).append(") ");

        // 复制来源
        sqlBuf.append(" SELECT ");
        j = valueList.size() - 1;
        for (int i = 0; i < j; i += 1)
        {
            sqlBuf.append(valueList.get(i)).append(", ");
        }
        sqlBuf.append(valueList.get(j));

        sqlBuf.append(" FROM ").append(f);
        // 查寻关联条件拼接
        if (whereList.length() > 0)
        {
            sqlBuf.append(" WHERE ").append(whereList.substring(5));
        }

        return sqlBuf.toString();
    }

    /**
     * 获取数据库查寻SQL语句
     * 
     * @return SQL语句，若操作表格为空，则返回空语句
     */
    private String getSelectSQL()
    {
        // 数据合法性判断
        if (tableList.length() < 1)
        {
            return "";
        }

        StringBuilder sqlBuf = new StringBuilder();

        // 查寻字段拼接
        sqlBuf.append("SELECT ");
        if (limit != null)
        {
            sqlBuf.append(' ').append(limit).append(' ');
        }
        if (columList.length() > 0)
        {
            sqlBuf.append(columList.substring(2));
        }
        else
        {
            sqlBuf.append("*");
        }

        // 查寻表格拼接
        sqlBuf.append(" FROM ").append(tableList.substring(2));

        // 查寻关联条件拼接
        if (whereList.length() > 0)
        {
            sqlBuf.append(" WHERE ").append(whereList.substring(5));
        }

        // 排序依据拼接
        if (orderList.length() > 0)
        {
            sqlBuf.append(" ORDER BY ").append(orderList.substring(2));
        }

        return sqlBuf.toString();
    }

    /**
     * 获取数据库更新SQL语句
     * 
     * @return SQL语句，若操作表格为空，则返回空语句
     */
    private String getUpdateSQL()
    {
        // 数据合法性判断
        if (tableList.length() < 1 || paramList.size() < 1)
        {
            return "";
        }

        StringBuilder sqlBuf = new StringBuilder();

        // 更新表格拼接
        sqlBuf.append("UPDATE ").append(tableList.substring(2)).append(" SET ");

        // 更新字段及值拼接
        int idx = 0;
        for (int j = paramList.size() - 1; idx < j; idx += 1)
        {
            sqlBuf.append(paramList.get(idx));
            sqlBuf.append(signList.get(idx));
            sqlBuf.append(valueList.get(idx));
            sqlBuf.append(", ");
        }
        sqlBuf.append(paramList.get(idx));
        sqlBuf.append(signList.get(idx));
        sqlBuf.append(valueList.get(idx));

        // 参照条件拼接
        if (whereList.length() > 0)
        {
            sqlBuf.append(" WHERE ").append(whereList.substring(5));
        }

        return sqlBuf.toString();
    }

    /**
     * 获取数据库插入SQL语句
     * 
     * @return SQL语句，若操作表格为空，则返回空语句
     */
    private String getInsertSQL()
    {
        // 数据合法性判断
        if (tableList.length() < 1)
        {
            return "";
        }

        StringBuilder sqlBuf = new StringBuilder();

        // 数据库表格拼接
        sqlBuf.append(" INSERT INTO ").append(tableList.substring(2));

        // 要插入字段拼接
        sqlBuf.append(" (");
        int idx = 0;
        for (int j = paramList.size() - 1; idx < j; idx += 1)
        {
            sqlBuf.append(paramList.get(idx));
            sqlBuf.append(", ");
        }
        sqlBuf.append(paramList.get(idx));
        sqlBuf.append(")");

        // 插入数据拼接
        sqlBuf.append(" VALUES (");
        idx = 0;
        for (int j = valueList.size() - 1; idx < j; idx += 1)
        {
            sqlBuf.append(valueList.get(idx));
            sqlBuf.append(", ");
        }
        sqlBuf.append(valueList.get(idx));
        sqlBuf.append(")");

        return sqlBuf.toString();
    }

    /**
     * 获取数据库删除SQL语句
     * 
     * @return SQL语句，若操作表格为空，则返回空语句
     */
    private String getDeleteSQL()
    {
        // 数据合法性判断
        if (tableList.length() < 1)
        {
            return "";
        }

        StringBuilder sqlBuf = new StringBuilder();

        // 数据库表格拼接
        sqlBuf.append("DELETE FROM ").append(tableList.substring(2));

        // 关联条件拼接
        sqlBuf.append(" WHERE ").append(whereList.substring(5));

        return sqlBuf.toString();
    }
    // ////////////////////////////////////////////////////////////////////////
    // 内部成员变量
    // ////////////////////////////////////////////////////////////////////////
    /** 数据源连接 */
    private static Connection conn;
    /** 数据库操作 */
    private Statement stat;
    /** 数据KEY-VALUE对持有变量 */
    private List<String> paramList;
    /** 符号持有变量 */
    private List<String> signList;
    /** 数据插入时数据列表（以逗号“,”分隔符区分） */
    private List<String> valueList;
    /** 当前要操作的字段名称（列表（以逗号“,”分隔符区分）） */
    private StringBuffer columList;
    /** 当前要操作的表格名称（列表（以逗号“,”分隔符区分）） */
    private StringBuffer tableList;
    /** 数据库操作时的关联条件列表（以逗号“,”分隔符区分） */
    private StringBuffer whereList;
    /** 数据库查寻时的排序依据（列表（以逗号“,”分隔符区分）） */
    private StringBuffer orderList;
    private String limit;
}
