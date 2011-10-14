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

import com.magicpwd.MagicPwd;
import com.magicpwd.__i.IEditItem;
import com.magicpwd._comn.mpwd.MpwdHeader;
import com.magicpwd._comn.mpwd.Mcat;
import com.magicpwd._comn.S1S2;
import com.magicpwd._comn.S1S3;
import com.magicpwd.__a.AEditItem;
import com.magicpwd._comn.I1S2;
import com.magicpwd._comn.mpwd.Mexp;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._comn.mpwd.MgtdDetail;
import com.magicpwd._comn.prop.Mucs;
import com.magicpwd._comn.prop.Mlib;
import com.magicpwd._cons.ConsCfg;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._cons.DBC4000;
import com.magicpwd._enum.AppView;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Hash;
import com.magicpwd._util.Logs;
import com.magicpwd._util.Util;
import com.magicpwd.m.UserMdl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Amon
 */
public class DBA4000
{

    public static String readConfig(UserMdl userMdl, String key)
    {
        DBAccess dba = new DBAccess();

        String result = null;
        try
        {
            dba.init(userMdl);
            dba.addTable(DBC4000.P30F0000);
            dba.addColumn(DBC4000.P30F0002);
            dba.addWhere(DBC4000.P30F0001, Util.text2DB(key));

            ResultSet resultSet = dba.executeSelect();
            if (resultSet.next())
            {
                result = resultSet.getString(DBC4000.P30F0002);
            }
            resultSet.close();
        }
        catch (SQLException ex)
        {
            Logs.exception(ex);
            result = null;
        }
        finally
        {
            dba.dispose();
        }
        return result;
    }

    public static void updtMgtdStatus(UserMdl userMdl, java.util.Map<String, Integer> mgtdList)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            for (String key : mgtdList.keySet())
            {
                dba.addTable(DBC4000.P30F0300);
                dba.addParam(DBC4000.P30F0303, mgtdList.get(key));
                dba.addWhere(DBC4000.P30F0309, key);
                dba.addUpdateBatch();
                dba.reInit();
            }

            dba.executeBatch();
        }
        catch (SQLException ex)
        {
            Logs.exception(ex);
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean initDataBase(UserMdl userMdl)
    {
        DBAccess dba = new DBAccess();
        java.io.InputStream stream = null;
        java.io.BufferedReader reader = null;

        try
        {
            dba.init(userMdl);

            stream = MagicPwd.class.getResourceAsStream("/res/sql/ddl.sql");
            if (stream == null)
            {
                return false;
            }

            reader = new java.io.BufferedReader(new java.io.InputStreamReader(stream, ConsEnv.FILE_ENCODING));
            String line = reader.readLine();
            while (line != null)
            {
                if (com.magicpwd._util.Char.isValidate(line))
                {
                    dba.addBatch(line);
                }
                line = reader.readLine();
            }
            dba.executeBatch();
            dba.reInit();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            Bean.closeStream(stream);
            Bean.closeReader(reader);
        }

        try
        {
            stream = MagicPwd.class.getResourceAsStream("/res/sql/dml.sql");
            if (stream == null)
            {
                return false;
            }

            reader = new java.io.BufferedReader(new java.io.InputStreamReader(stream, ConsEnv.FILE_ENCODING));
            String line = reader.readLine();
            while (line != null)
            {
                if (com.magicpwd._util.Char.isValidate(line))
                {
                    dba.addBatch(line);
                }
                line = reader.readLine();
            }
            dba.executeBatch();
            dba.reInit();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        return true;
    }

    public static boolean saveConfig(UserMdl userMdl, String key, String value)
    {
        key = Util.text2DB(key);
        if (!com.magicpwd._util.Char.isValidate(key, 1, DBC4000.P30F0001_SIZE))
        {
            return false;
        }

        DBAccess dba = new DBAccess();

        boolean isOK = false;
        try
        {
            dba.init(userMdl);
            dba.addTable(DBC4000.P30F0000);
            dba.addWhere(DBC4000.P30F0001, key);

            ResultSet resultSet = dba.executeSelect();
            boolean isUpdate = resultSet.next();
            resultSet.close();

            dba.reInit();
            dba.addTable(DBC4000.P30F0000);
            dba.addParam(DBC4000.P30F0002, Util.text2DB(value));
            dba.addParam(DBC4000.P30F0003, DBC4000.SQL_NOW, false);
            if (isUpdate)
            {
                dba.addWhere(DBC4000.P30F0001, key);
                dba.executeUpdate();
            }
            else
            {
                dba.addParam(DBC4000.P30F0001, key);
                dba.executeInsert();
            }
            isOK = true;
        }
        catch (SQLException ex)
        {
            Logs.exception(ex);
            isOK = false;
        }
        finally
        {
            dba.dispose();
        }
        return isOK;
    }

    /**
     * 添加用户过滤条件
     * @param dba
     */
    private static void addUserSort(DBAccess dba, UserMdl cfg)
    {
//        dba.addWhere(DBC4000.P30F0102, "");
        dba.addWhere(DBC4000.P30F0105, cfg.getCode());
    }

    /**
     * 添加显示排序
     * @param dba
     */
    private static void addDataSort(DBAccess dba, UserMdl cfg)
    {
        boolean asc = ConsCfg.DEF_TRUE.equals(cfg.getCfg(AppView.mpro, ConsCfg.CFG_VIEW_LIST_ASC, ConsCfg.DEF_FALSE));
        String key = cfg.getCfg(AppView.mpro, ConsCfg.CFG_VIEW_LIST_KEY, "09");

        if (!Pattern.matches("^[0-9A-Z]{2}$", key))
        {
            key = "09";
        }
        dba.addSort("P30F01" + key, asc);
    }

    /**
     * 读取口令信息
     * @param rest
     * @param list
     * @throws SQLException
     */
    private static void getNameData(ResultSet rest, List<MpwdHeader> list) throws SQLException
    {
        MpwdHeader mkey;
        while (rest.next())
        {
            mkey = new MpwdHeader();
            mkey.setP30F0101(rest.getInt(DBC4000.P30F0101));
            mkey.setP30F0102(rest.getInt(DBC4000.P30F0102));
            mkey.setP30F0103(rest.getInt(DBC4000.P30F0103));
            mkey.setP30F0104(rest.getString(DBC4000.P30F0104));
            mkey.setP30F0105(rest.getString(DBC4000.P30F0105));
            mkey.setP30F0106(rest.getString(DBC4000.P30F0106));
            mkey.setP30F0107(rest.getString(DBC4000.P30F0107));
            mkey.setP30F0108(rest.getString(DBC4000.P30F0108));
            mkey.setP30F0109(rest.getString(DBC4000.P30F0109));
            mkey.setP30F010A(rest.getString(DBC4000.P30F010A));
            mkey.setP30F010B(rest.getString(DBC4000.P30F010B));
            mkey.setP30F010C(rest.getString(DBC4000.P30F010C));
            mkey.setP30F010D(rest.getString(DBC4000.P30F010D));
            mkey.setP30F010E(rest.getString(DBC4000.P30F010E));
            mkey.setP30F010F(rest.getString(DBC4000.P30F010F));
            mkey.setP30F0110(rest.getString(DBC4000.P30F0110));
            list.add(mkey);
        }
        rest.close();
    }

    /**
     * 读取指定类别下的所有口令数据
     * @param kindHash
     * @param list
     * @return
     */
    public static boolean listKeyHeaderByCat(UserMdl userMdl, String kindHash, List<MpwdHeader> list)
    {
        // 数据库连接初始化
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            // 查询语句拼接
            dba.addTable(DBC4000.P30F0100);
            dba.addWhere(DBC4000.P30F0106, kindHash);
            addUserSort(dba, userMdl);
            addDataSort(dba, userMdl);

            getNameData(dba.executeSelect(), list);
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean listRecHeaderByLabel(UserMdl userMdl, int label, List<MpwdHeader> list)
    {
        // 数据库连接初始化
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            // 查询语句拼接
            dba.addTable(DBC4000.P30F0100);
            dba.addWhere(DBC4000.P30F0102, label);
            addUserSort(dba, userMdl);
            addDataSort(dba, userMdl);

            getNameData(dba.executeSelect(), list);
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean listRecHeaderByMajor(UserMdl userMdl, int major, List<MpwdHeader> list)
    {
        // 数据库连接初始化
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            // 查询语句拼接
            dba.addTable(DBC4000.P30F0100);
            dba.addWhere(DBC4000.P30F0103, major);
            addUserSort(dba, userMdl);
            addDataSort(dba, userMdl);

            getNameData(dba.executeSelect(), list);
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    private static String text2Like(String text)
    {
        text = Util.text2DB(text.toLowerCase().replace('　', ' ').replace('＋', '+'));
        return text.replaceFirst("^[+\\s]*", "%").replaceFirst("[+\\s]*$", "%").replaceAll("[+%\\s]+", "%");
    }

    /**
     * 查询标题或关键搜索中含有指定字符的口令数据
     * @param text
     * @param list
     * @return
     */
    public static boolean findUserData(UserMdl userMdl, String text, List<MpwdHeader> list)
    {
        if (!com.magicpwd._util.Char.isValidate(text))
        {
            return false;
        }

        // 数据库连接初始化
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            // 查询语句拼接
            dba.addTable(DBC4000.P30F0100);
            dba.addWhere(com.magicpwd._util.Char.format("LOWER({0}) LIKE '{2}' OR LOWER({1}) LIKE '{2}'", DBC4000.P30F0109, DBC4000.P30F010A, text2Like(text)));
            addUserSort(dba, userMdl);
            addDataSort(dba, userMdl);

            getNameData(dba.executeSelect(), list);
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean findHintList(UserMdl userMdl, java.util.List<MpwdHeader> mkeyList, java.util.List<MgtdHeader>... hintList)
    {
        if (hintList == null || hintList.length < 1)
        {
            return false;
        }

        StringBuilder buf = new StringBuilder();
        for (java.util.List<MgtdHeader> list : hintList)
        {
            for (MgtdHeader hint : list)
            {
                buf.append(",'").append(hint.getP30F0309()).append('\'');
            }
        }
        if (buf.length() < 1)
        {
            return false;
        }

        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F0100);
            dba.addWhere(DBC4000.P30F010E, "in", '(' + buf.substring(1) + ')', false);

            ResultSet rest = dba.executeSelect();
            getNameData(rest, mkeyList);
            rest.close();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean findUnitList(UserMdl userMdl, List<S1S2> list)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            StringBuilder buf = new StringBuilder();
            buf.append("SELECT ");
            buf.append(DBC4000.C2010203);
            buf.append(" FROM ");
            buf.append(DBC4000.C2010200);
            buf.append(" WHERE ").append(DBC4000.C2010208).append("='unit'");

            dba.addTable(DBC4000.P30F0100);
            dba.addColumn(DBC4000.P30F0104);
            dba.addColumn(DBC4000.P30F0109);
            dba.addColumn(DBC4000.P30F010A);
            dba.addWhere(DBC4000.P30F0106 + " IN (" + buf.toString() + ')');
            addUserSort(dba, userMdl);
            addDataSort(dba, userMdl);

            ResultSet rest = dba.executeSelect();
            S1S2 item;
            while (rest.next())
            {
                item = new S1S2();
                item.setK(rest.getString(DBC4000.P30F0104));
                item.setV(rest.getString(DBC4000.P30F0109));
                item.setV2(rest.getString(DBC4000.P30F010A));
                list.add(item);
            }
            rest.close();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
    }

    /**
     * 查询在当前日期到指定日期之间的口令数据
     * @param time
     * @param list
     * @return
     */
    public static boolean listRecHeaderByTime(UserMdl userMdl, long s, long t, List<MpwdHeader> list)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F0100);
            dba.addTable(DBC4000.P30F0300);
            dba.addColumn(DBC4000.P30F0101);
            dba.addColumn(DBC4000.P30F0102);
            dba.addColumn(DBC4000.P30F0103);
            dba.addColumn(DBC4000.P30F0104);
            dba.addColumn(DBC4000.P30F0105);
            dba.addColumn(DBC4000.P30F0106);
            dba.addColumn(DBC4000.P30F0107);
            dba.addColumn(DBC4000.P30F0108);
            dba.addColumn(DBC4000.P30F0109);
            dba.addColumn(DBC4000.P30F010A);
            dba.addColumn(DBC4000.P30F010B);
            dba.addColumn(DBC4000.P30F010C);
            dba.addColumn(DBC4000.P30F010D);
            dba.addColumn(DBC4000.P30F010E);
            dba.addColumn(DBC4000.P30F010F);
            dba.addColumn(DBC4000.P30F0110);
            dba.addColumn(DBC4000.P30F0111);
            dba.addWhere(DBC4000.P30F010E, DBC4000.P30F0309, false);

            StringBuilder buf = new StringBuilder();
            buf.append(DBC4000.P30F0302).append('=').append(ConsDat.MGTD_INTVAL_SPECIAL);
            buf.append(" OR ((");
            buf.append(DBC4000.P30F030D).append("=0 OR ");
            buf.append(DBC4000.P30F030D).append("<=").append(s).append(") AND (");
            buf.append(DBC4000.P30F030E).append("=0 OR ");
            buf.append(DBC4000.P30F030E).append(">=").append(s);
            buf.append(")) OR ((");
            buf.append(DBC4000.P30F030D).append("=0 OR ");
            buf.append(DBC4000.P30F030D).append("<=").append(t).append(") AND (");
            buf.append(DBC4000.P30F030E).append("=0 OR ");
            buf.append(DBC4000.P30F030E).append(">=").append(t);
            buf.append("))");
            dba.addWhere(buf.toString());

            ResultSet rset = dba.executeSelect();
            while (rset.next())
            {
                getNameData(dba.executeSelect(), list);
            }
            rset.close();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static MgtdHeader readGtdHeader(UserMdl userMdl, String mgtdHash)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);
            return readGtdHeader(dba, mgtdHash);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return null;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static MgtdHeader readMgtd(UserMdl userMdl, String mgtdHash)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);
            MgtdHeader mgtd = readGtdHeader(dba, mgtdHash);
            mgtd.setHintList(readGtdDetail(dba, mgtdHash));
            return mgtd;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return null;
        }
        finally
        {
            dba.dispose();
        }
    }

    private static MgtdHeader readGtdHeader(DBAccess dba, String mgtdHash) throws Exception
    {
        MgtdHeader mgtd = null;
        dba.addTable(DBC4000.P30F0300);
//            dba.addWhere(DBC4000.P30F0307, "1");
        dba.addWhere(DBC4000.P30F0309, mgtdHash);
        dba.addSort(DBC4000.P30F0305);
//            dba.addSort(DBC4000.p30f03);

        ResultSet rest = dba.executeSelect();
        if (rest.next())
        {
            mgtd = loadGtdHeader(rest);
        }
        rest.close();
        return mgtd;
    }

    private static java.util.List<MgtdDetail> readGtdDetail(DBAccess dba, String mgtdHash) throws Exception
    {
        dba.reInit();
        dba.addTable(DBC4000.P30F0400);
        dba.addColumn(DBC4000.P30F0403);
        dba.addColumn(DBC4000.P30F0404);
        dba.addColumn(DBC4000.P30F0405);
        dba.addColumn(DBC4000.P30F0406);
        dba.addWhere(DBC4000.P30F0402, mgtdHash);
        dba.addSort(DBC4000.P30F0401);
        ResultSet rset = dba.executeSelect();
        java.util.List<MgtdDetail> list = new java.util.ArrayList<MgtdDetail>();
        MgtdDetail hint;
        while (rset.next())
        {
            hint = new MgtdDetail();
            hint.setP30F0403(rset.getLong(DBC4000.P30F0403));
            hint.setP30F0404(rset.getInt(DBC4000.P30F0404));
            hint.setP30F0405(rset.getInt(DBC4000.P30F0405));
            hint.setP30F0406(rset.getString(DBC4000.P30F0406));
            list.add(hint);
        }

        return list;
    }

    public static java.util.ArrayList<MgtdHeader> readMgtdList(UserMdl userMdl)
    {
        java.util.ArrayList<MgtdHeader> mgtdList = new java.util.ArrayList<MgtdHeader>();

        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F0300);
            dba.addWhere(DBC4000.P30F0307, "1");
            dba.addSort(DBC4000.P30F0305);
//            dba.addSort(DBC4000.p30f03);

            ResultSet rest = dba.executeSelect();
            while (rest.next())
            {
                mgtdList.add(loadGtdHeader(rest));
            }
            rest.close();
            return mgtdList;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return mgtdList;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean findHintList(UserMdl userMdl, List<MgtdHeader> list)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            String now = Long.toString(System.currentTimeMillis(), 10);

            dba.addTable(DBC4000.P30F0300);
            StringBuilder buf = new StringBuilder();
            buf.append(DBC4000.P30F0305).append('=').append(ConsDat.MGTD_INTVAL_STARTUP);
            buf.append(" OR (");
            buf.append(DBC4000.P30F0303).append(">=").append(ConsDat.MGTD_STATUS_DELAY);
            buf.append(" AND ");
            buf.append(DBC4000.P30F0303).append("<=").append(ConsDat.MGTD_STATUS_INIT);
            buf.append(" AND ");
            buf.append(com.magicpwd._util.Char.format("({0}=0 OR {0} < {1})", DBC4000.P30F030D, now));//已经开始了的
            buf.append(" AND ");
            buf.append(com.magicpwd._util.Char.format("({0}=0 OR {0} > {1})", DBC4000.P30F030E, now));//还末结束了的
            buf.append(")");
            dba.addWhere(buf.toString());

            ResultSet rest = dba.executeSelect();
            while (rest.next())
            {
                list.add(loadGtdHeader(rest));
            }
            rest.close();

            for (MgtdHeader header : list)
            {
                loadGtdDetail(dba, header);
            }
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean deleteMgtdData(UserMdl userMdl, MgtdHeader mgtd)
    {
        if (mgtd == null || !com.magicpwd._util.Char.isValidateHash(mgtd.getP30F0309()))
        {
            return false;
        }

        DBAccess dba = new DBAccess();
        try
        {
            dba.init(userMdl);
            dba.addTable(DBC4000.P30F0400);
            dba.addWhere(DBC4000.P30F0402, mgtd.getP30F0309());
            dba.addDeleteBatch();

            dba.reInit();
            dba.addTable(DBC4000.P30F0300);
            dba.addWhere(DBC4000.P30F0309, mgtd.getP30F0309());
            dba.addDeleteBatch();

            dba.reInit();
            dba.addTable(DBC4000.P30F0100);
            dba.addParam(DBC4000.P30F010E, "");
            dba.addWhere(DBC4000.P30F010E, mgtd.getP30F0309());
            dba.addUpdateBatch();

            dba.executeBatch();
            return true;
        }
        catch (Exception exp)
        {
            return false;
        }
    }

    public static boolean listGtdHeader(UserMdl userMdl, java.util.List<MgtdHeader> mgtdList)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F0300);
            dba.addSort(DBC4000.P30F0301);
            ResultSet rest = dba.executeSelect();
            while (rest.next())
            {
                mgtdList.add(loadGtdHeader(rest));
            }
            rest.close();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static void listGtdHeader(DBAccess dba, java.util.List<MgtdHeader> mgtdList) throws Exception
    {
        dba.addTable(DBC4000.P30F0300);
        dba.addSort(DBC4000.P30F0301);
        ResultSet rest = dba.executeSelect();
        while (rest.next())
        {
            mgtdList.add(loadGtdHeader(rest));
        }
        rest.close();
    }

    public static boolean listGtdDetail(UserMdl userMdl, MgtdHeader mgtd)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            listGtdDetail(dba, mgtd);
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static void listGtdDetail(DBAccess dba, MgtdHeader mgtd) throws Exception
    {
        dba.addTable(DBC4000.P30F0400);
        dba.addWhere(DBC4000.P30F0402, mgtd.getP30F0309());
        dba.addSort(DBC4000.P30F0401);
        ResultSet rest = dba.executeSelect();
        java.util.List<MgtdDetail> list = new java.util.ArrayList<MgtdDetail>();
        while (rest.next())
        {
            list.add(loadGtdDetail(rest));
        }
        rest.close();
        mgtd.setHintList(list);
    }

    public static boolean saveHintTime(UserMdl userMdl, MgtdHeader mgtd)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F0300);
            dba.addParam(DBC4000.P30F030F, mgtd.getP30F030F());
            dba.addWhere(DBC4000.P30F0309, mgtd.getP30F0309());
            return 1 == dba.executeUpdate();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean saveMgtdData(UserMdl userMdl, MgtdHeader mgtd)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);
            updateGtdHeader(dba, mgtd);
            updateGtdDetail(dba, mgtd);
            dba.executeBatch();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean findUserNote(UserMdl userMdl, String text, java.util.List<S1S2> list)
    {
        if (!com.magicpwd._util.Char.isValidate(text))
        {
            return false;
        }

        // 数据库连接初始化
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            // 查询语句拼接
            dba.addTable(DBC4000.P30F0100);
            dba.addColumn(DBC4000.P30F0104);
            dba.addColumn(DBC4000.P30F0109);
            dba.addColumn(DBC4000.P30F010A);
            dba.addWhere(DBC4000.P30F0105, userMdl.getCode());
            dba.addWhere(com.magicpwd._util.Char.format("LOWER({0}) LIKE '{2}' OR LOWER({1}) LIKE '{2}'", DBC4000.P30F0109, DBC4000.P30F010A, text2Like(text)));
            //dba.addWhere(DBC4000.P30F0102, ConsDat.PWDS_MODE_1);
            dba.addWhere(DBC4000.P30F0106, ConsDat.HASH_NOTE);

            ResultSet rest = dba.executeSelect();
            while (rest.next())
            {
                list.add(new S1S2(rest.getString(DBC4000.P30F0104), rest.getString(DBC4000.P30F0109), rest.getString(DBC4000.P30F010A)));
            }
            rest.close();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean readMpwdData(UserMdl userMdl, MpwdHeader mkey)
    {
        // 数据库连接初始化
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            loadMkey(dba, mkey);

            loadMpwd(dba, mkey);

            if (com.magicpwd._util.Char.isValidateHash(mkey.getP30F010E()))
            {
                loadGtdHeader(dba, mkey);

                loadGtdDetail(dba, mkey.getMgtd());
            }

            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean loadMkey(DBAccess dba, MpwdHeader mkey) throws Exception
    {
        // 查询语句拼接
        dba.addTable(DBC4000.P30F0100);
        dba.addColumn(DBC4000.P30F0101);
        dba.addColumn(DBC4000.P30F0102);
        dba.addColumn(DBC4000.P30F0103);
        dba.addColumn(DBC4000.P30F0104);
        dba.addColumn(DBC4000.P30F0105);
        dba.addColumn(DBC4000.P30F0106);
        dba.addColumn(DBC4000.P30F0107);
        dba.addColumn(DBC4000.P30F0108);
        dba.addColumn(DBC4000.P30F0109);
        dba.addColumn(DBC4000.P30F010A);
        dba.addColumn(DBC4000.P30F010B);
        dba.addColumn(DBC4000.P30F010C);
        dba.addColumn(DBC4000.P30F010D);
        dba.addColumn(DBC4000.P30F010E);
        dba.addColumn(DBC4000.P30F010F);
        dba.addColumn(DBC4000.P30F0110);
        //dba.addWhere(DBC4000.P30F0102, keys.getP30F0102());
        dba.addWhere(DBC4000.P30F0104, mkey.getP30F0104());
        dba.addWhere(DBC4000.P30F0105, mkey.getP30F0105());

        ResultSet rset = dba.executeSelect();
        boolean ok = rset.next();
        if (ok)
        {
            mkey.setP30F0101(rset.getInt(DBC4000.P30F0101));
            mkey.setP30F0102(rset.getInt(DBC4000.P30F0102));
            mkey.setP30F0103(rset.getInt(DBC4000.P30F0103));
            mkey.setP30F0104(rset.getString(DBC4000.P30F0104));
            mkey.setP30F0106(rset.getString(DBC4000.P30F0106));
            mkey.setP30F0107(rset.getString(DBC4000.P30F0107));
            mkey.setP30F0108(rset.getString(DBC4000.P30F0108));
            mkey.setP30F0109(rset.getString(DBC4000.P30F0109));
            mkey.setP30F010A(rset.getString(DBC4000.P30F010A));
            mkey.setP30F010B(rset.getString(DBC4000.P30F010B));
            mkey.setP30F010C(rset.getString(DBC4000.P30F010C));
            mkey.setP30F010D(rset.getString(DBC4000.P30F010D));
            mkey.setP30F010E(rset.getString(DBC4000.P30F010E));
            mkey.setP30F010F(rset.getString(DBC4000.P30F010F));
            mkey.setP30F0110(rset.getString(DBC4000.P30F0110));
        }
        rset.close();
        return ok;
    }

    private static boolean loadMpwd(DBAccess dba, MpwdHeader mkey) throws Exception
    {
        // 口令内容读取
        dba.reInit();
        dba.addTable(DBC4000.P30F0200);
        dba.addColumn(DBC4000.P30F0203);
        dba.addWhere(DBC4000.P30F0202, mkey.getP30F0104());
        dba.addSort(DBC4000.P30F0201);

        ResultSet rset = dba.executeSelect();
        StringBuffer sb = mkey.getMpwd().getP30F0203();
        while (rset.next())
        {
            sb.append(rset.getString(DBC4000.P30F0203));
        }
        mkey.getMpwd().setP30F0202(mkey.getP30F0104());
        rset.close();
        return true;
    }

    private static void loadGtdHeader(DBAccess dba, MpwdHeader mkey) throws Exception
    {
        dba.reInit();
        dba.addTable(DBC4000.P30F0300);
//      dba.addWhere(DBC4000.P30F0307, "1");
        dba.addWhere(DBC4000.P30F0309, mkey.getP30F010E());
        dba.addSort(DBC4000.P30F0305);
//      dba.addSort(DBC4000.p30f03);

        ResultSet rest = dba.executeSelect();
        if (rest.next())
        {
            mkey.setMgtd(loadGtdHeader(rest));
        }
        rest.close();
    }

    private static MgtdHeader loadGtdHeader(ResultSet rest) throws Exception
    {
        MgtdHeader mgtd = new MgtdHeader();
        mgtd.setP30F0301(rest.getInt(DBC4000.P30F0301));
        mgtd.setP30F0302(rest.getInt(DBC4000.P30F0302));
        mgtd.setP30F0303(rest.getInt(DBC4000.P30F0303));
        mgtd.setP30F0304(rest.getInt(DBC4000.P30F0304));
        mgtd.setP30F0305(rest.getInt(DBC4000.P30F0305));
        mgtd.setP30F0306(rest.getInt(DBC4000.P30F0306));
        mgtd.setP30F0307(rest.getInt(DBC4000.P30F0307));
        mgtd.setP30F0308(rest.getInt(DBC4000.P30F0308));
        mgtd.setP30F0309(rest.getString(DBC4000.P30F0309));
        mgtd.setP30F030A(rest.getString(DBC4000.P30F030A));
        mgtd.setP30F030B(rest.getString(DBC4000.P30F030B));
        mgtd.setP30F030C(rest.getString(DBC4000.P30F030C));
        mgtd.setP30F030D(rest.getLong(DBC4000.P30F030D));
        mgtd.setP30F030E(rest.getLong(DBC4000.P30F030E));
        mgtd.setP30F030F(rest.getLong(DBC4000.P30F030F));
        mgtd.setP30F030F(rest.getString(DBC4000.P30F0310));
        mgtd.setP30F0311(rest.getString(DBC4000.P30F0311));
        mgtd.setP30F0312(rest.getInt(DBC4000.P30F0312));
        mgtd.setP30F0313(rest.getInt(DBC4000.P30F0313));
        mgtd.setP30F0314(rest.getString(DBC4000.P30F0314));
        return mgtd;
    }

    private static void loadGtdDetail(DBAccess dba, MgtdHeader header) throws Exception
    {
        java.util.ArrayList<MgtdDetail> list = new java.util.ArrayList<MgtdDetail>();

        dba.reInit();
        dba.addTable(DBC4000.P30F0400);
        dba.addColumn(DBC4000.P30F0403);
        dba.addColumn(DBC4000.P30F0404);
        dba.addColumn(DBC4000.P30F0405);
        dba.addColumn(DBC4000.P30F0406);
        dba.addWhere(DBC4000.P30F0402, header.getP30F0309());
        dba.addSort(DBC4000.P30F0401);

        ResultSet rset = dba.executeSelect();
        while (rset.next())
        {
            list.add(loadGtdDetail(rset));
        }
        rset.close();
        header.setHintList(list);
    }

    private static MgtdDetail loadGtdDetail(ResultSet rset) throws Exception
    {
        MgtdDetail hint = new MgtdDetail();
        hint.setP30F0403(rset.getLong(DBC4000.P30F0403));
        hint.setP30F0404(rset.getInt(DBC4000.P30F0404));
        hint.setP30F0405(rset.getInt(DBC4000.P30F0405));
        hint.setP30F0406(rset.getString(DBC4000.P30F0406));
        return hint;
    }

    public static boolean saveKeysData(UserMdl userMdl, MpwdHeader keys)
    {
        // 数据库连接初始化
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);
            updateRecHeader(dba, keys);
            dba.executeBatch();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean saveMpwdData(UserMdl userMdl, MpwdHeader keys)
    {
        // 数据库连接初始化
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);
            saveMpwdData(dba, keys);
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static void saveMpwdData(DBAccess dba, MpwdHeader mkey) throws Exception
    {
        // 数据更新时，首先删除已有数据，再添加数据
        if (com.magicpwd._util.Char.isValidateHash(mkey.getP30F0104()))
        {
            if (mkey.isHistBack())
            {
                backup(dba, mkey.getP30F0104());
            }
            remove(dba, mkey);
        }

        MgtdHeader mgtd = mkey.getMgtd();
        if (!com.magicpwd._util.Char.isValidateHash(mkey.getP30F010E()))
        {
            // 新增
            if (mgtd != null)
            {
                updateGtdHeader(dba, mgtd);
                updateGtdDetail(dba, mgtd);
                mkey.setP30F010E(mgtd.getP30F0309());
            }
            // 删除
//            else
//            {
//                removeGtdHeader(dba, mgtd);
//                removeGtdDetail(dba, mgtd);
//            }
        }
        else if (mgtd != null)
        {
            // 变更
            if (mkey.getP30F010E().equals(mgtd.getP30F0309()))
            {
                updateGtdHeader(dba, mgtd);
                updateGtdDetail(dba, mgtd);
            }
            // 切换
            else
            {
                removeGtdHeader(dba, mgtd);
                removeGtdDetail(dba, mgtd);
            }
        }

        updateRecHeader(dba, mkey);
        updateRecDetail(dba, mkey);
        dba.executeBatch();
    }

    public static boolean deletePwdsData(UserMdl userMdl, String hash)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            String DELETE = "DELETE FROM {0} WHERE {1}='{2}'";
            // 删除信息数据
            dba.addBatch(com.magicpwd._util.Char.format(DELETE, DBC4000.P30F0100, DBC4000.P30F0104, hash));
            // 删除内容数据
            dba.addBatch(com.magicpwd._util.Char.format(DELETE, DBC4000.P30F0200, DBC4000.P30F0202, hash));
            // 删除信息备份
            dba.addBatch(com.magicpwd._util.Char.format(DELETE, DBC4000.P30F0A00, DBC4000.P30F0A04, hash));
            // 删除内容备份
            dba.addBatch(com.magicpwd._util.Char.format(DELETE, DBC4000.P30F0B00, DBC4000.P30F0B03, hash));
            dba.executeBatch();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    /**
     * 数据备份
     * @param dba
     * @param keys
     * @throws SQLException
     */
    private static void backup(DBAccess dba, String keysHash) throws SQLException
    {
        String hash = com.magicpwd._util.Date.nowTime();

        dba.addParam(DBC4000.P30F0A01, hash);
        dba.addParam(DBC4000.P30F0A02, DBC4000.P30F0102, false);
        dba.addParam(DBC4000.P30F0A03, DBC4000.P30F0103, false);
        dba.addParam(DBC4000.P30F0A04, DBC4000.P30F0104, false);
        dba.addParam(DBC4000.P30F0A05, DBC4000.P30F0105, false);
        dba.addParam(DBC4000.P30F0A06, DBC4000.P30F0106, false);
        dba.addParam(DBC4000.P30F0A07, DBC4000.P30F0107, false);
        dba.addParam(DBC4000.P30F0A08, DBC4000.P30F0108, false);
        dba.addParam(DBC4000.P30F0A09, DBC4000.P30F0109, false);
        dba.addParam(DBC4000.P30F0A0A, DBC4000.P30F010A, false);
        dba.addParam(DBC4000.P30F0A0B, DBC4000.P30F010B, false);
        dba.addParam(DBC4000.P30F0A0C, DBC4000.P30F010C, false);
        dba.addParam(DBC4000.P30F0A0D, DBC4000.P30F010D, false);
        dba.addParam(DBC4000.P30F0A0E, DBC4000.P30F010E, false);
        dba.addParam(DBC4000.P30F0A0F, DBC4000.P30F010F, false);
        dba.addParam(DBC4000.P30F0A10, DBC4000.P30F0110, false);
        dba.addWhere(DBC4000.P30F0104, keysHash);
        dba.addCopyBatch(DBC4000.P30F0A00, DBC4000.P30F0100);
        dba.reInit();

        dba.addParam(DBC4000.P30F0B01, hash);
        dba.addParam(DBC4000.P30F0B02, DBC4000.P30F0201, false);
        dba.addParam(DBC4000.P30F0B03, DBC4000.P30F0202, false);
        dba.addParam(DBC4000.P30F0B04, DBC4000.P30F0203, false);
        dba.addWhere(DBC4000.P30F0202, keysHash);
        dba.addCopyBatch(DBC4000.P30F0B00, DBC4000.P30F0200);
        dba.reInit();
    }

    /**
     * 数据清除
     * @param dba
     * @param pwds
     * @throws SQLException
     */
    private static void remove(DBAccess dba, MpwdHeader pwds) throws SQLException
    {
        if (!com.magicpwd._util.Char.isValidateHash(pwds.getP30F0104()))
        {
            return;
        }

        dba.addTable(DBC4000.P30F0200);
        dba.addWhere(DBC4000.P30F0202, pwds.getP30F0104());
        dba.addDeleteBatch();
        dba.reInit();
    }

    /**
     * 数据只在
     * @param dba
     * @param keys
     * @throws SQLException
     */
    private static void updateRecHeader(DBAccess dba, MpwdHeader keys) throws SQLException
    {
        dba.addTable(DBC4000.P30F0100);
        dba.addParam(DBC4000.P30F0101, keys.getP30F0101());
        dba.addParam(DBC4000.P30F0102, keys.getP30F0102());
        dba.addParam(DBC4000.P30F0103, keys.getP30F0103());
        dba.addParam(DBC4000.P30F0105, keys.getP30F0105());
        dba.addParam(DBC4000.P30F0106, keys.getP30F0106());
        dba.addParam(DBC4000.P30F0107, keys.getP30F0107().toString());
        dba.addParam(DBC4000.P30F0108, keys.getP30F0108());
        dba.addParam(DBC4000.P30F0109, Util.text2DB(keys.getP30F0109()));
        dba.addParam(DBC4000.P30F010A, Util.text2DB(keys.getP30F010A()));
        dba.addParam(DBC4000.P30F010B, Util.text2DB(keys.getP30F010B()));
        dba.addParam(DBC4000.P30F010C, Util.text2DB(keys.getP30F010C()));
        dba.addParam(DBC4000.P30F010D, Util.text2DB(keys.getP30F010D()));
        dba.addParam(DBC4000.P30F010E, keys.getP30F010E());
        dba.addParam(DBC4000.P30F010F, Util.text2DB(keys.getP30F010F()));
        dba.addParam(DBC4000.P30F0110, Util.text2DB(keys.getP30F0110()));
        dba.addParam(DBC4000.P30F0111, DBC4000.SQL_NOW, false);

        if (com.magicpwd._util.Char.isValidateHash(keys.getP30F0104()))
        {
            // 数据更新
            dba.addWhere(DBC4000.P30F0104, keys.getP30F0104());
            dba.addUpdateBatch();
        }
        else
        {
            // 新增数据
            keys.setP30F0104(Hash.hash(false));
            dba.addParam(DBC4000.P30F0104, keys.getP30F0104());
            dba.addInsertBatch();
        }

        dba.reInit();
    }

    /**
     * 更新记录头信息
     * @param c
     * @param v
     * @param k
     * @throws SQLException
     */
    public static void updateInfo(String c, String v, String k) throws SQLException
    {
        // 数据库连接初始化
        DBAccess dba = new DBAccess();

        if (!com.magicpwd._util.Char.isValidateHash(k))
        {
            return;
        }
        dba.addTable(DBC4000.P30F0100);
        dba.addParam(c, v);
        dba.addWhere(DBC4000.P30F0104, k);
        dba.executeUpdate();

        dba.dispose();
    }

    /**
     * 更新记录体信息
     * @param dba
     * @param keys
     * @throws SQLException
     */
    private static void updateRecDetail(DBAccess dba, MpwdHeader keys) throws SQLException
    {
        StringBuffer pwd = keys.getMpwd().getP30F0203();
        int len = pwd.length();
        int idx = 0;
        int t1 = 0;
        int t2 = t1 + ConsEnv.PWDS_DATA_SIZE;
        // 循环处理每一节段数据
        while (t2 < len)
        {
            dba.addTable(DBC4000.P30F0200);
            dba.addParam(DBC4000.P30F0201, idx++);
            dba.addParam(DBC4000.P30F0202, keys.getP30F0104());
            dba.addParam(DBC4000.P30F0203, pwd.substring(t1, t2));

            dba.addInsertBatch();
            dba.reInit();

            t1 = t2;
            t2 += ConsEnv.PWDS_DATA_SIZE;
        }

        // 处理剩余节段数据
        dba.addTable(DBC4000.P30F0200);
        dba.addParam(DBC4000.P30F0201, idx);
        dba.addParam(DBC4000.P30F0202, keys.getP30F0104());
        dba.addParam(DBC4000.P30F0203, pwd.substring(t1));

        dba.addInsertBatch();
        dba.reInit();
    }

    /**
     * 更新任务头信息
     * @param dba
     * @param mkey
     * @throws SQLException
     */
    private static void updateGtdHeader(DBAccess dba, MgtdHeader mgtd) throws Exception
    {
        dba.addTable(DBC4000.P30F0300);
        dba.addParam(DBC4000.P30F0301, mgtd.getP30F0301());
        dba.addParam(DBC4000.P30F0302, mgtd.getP30F0302());
        dba.addParam(DBC4000.P30F0303, mgtd.getP30F0303());
        dba.addParam(DBC4000.P30F0304, mgtd.getP30F0304());
        dba.addParam(DBC4000.P30F0305, mgtd.getP30F0305());
        dba.addParam(DBC4000.P30F0306, mgtd.getP30F0306());
        dba.addParam(DBC4000.P30F0307, mgtd.getP30F0307());
        dba.addParam(DBC4000.P30F0308, mgtd.getP30F0308());
        dba.addParam(DBC4000.P30F030A, mgtd.getP30F030A());
        dba.addParam(DBC4000.P30F030B, mgtd.getP30F030B());
        dba.addParam(DBC4000.P30F030C, mgtd.getP30F030C());
        dba.addParam(DBC4000.P30F030D, mgtd.getP30F030D());
        dba.addParam(DBC4000.P30F030E, mgtd.getP30F030E());
        dba.addParam(DBC4000.P30F030F, mgtd.getP30F030F());
        dba.addParam(DBC4000.P30F0310, mgtd.getP30F0310());
        dba.addParam(DBC4000.P30F0311, mgtd.getP30F0311());
        dba.addParam(DBC4000.P30F0312, mgtd.getP30F0312());
        dba.addParam(DBC4000.P30F0313, mgtd.getP30F0313());
        dba.addParam(DBC4000.P30F0314, mgtd.getP30F0314());
        if (com.magicpwd._util.Char.isValidateHash(mgtd.getP30F0309()))
        {
            dba.addWhere(DBC4000.P30F0309, mgtd.getP30F0309());
            dba.addUpdateBatch();

            dba.reInit();
            dba.addTable(DBC4000.P30F0400);
            dba.addWhere(DBC4000.P30F0402, mgtd.getP30F0309());
            dba.addDeleteBatch();
        }
        else
        {
            mgtd.setP30F0309(Hash.hash(false));
            dba.addParam(DBC4000.P30F0309, mgtd.getP30F0309());
            dba.addInsertBatch();
        }
        dba.reInit();
    }

    private static void removeGtdHeader(DBAccess dba, MgtdHeader mgtd) throws Exception
    {
        dba.addTable(DBC4000.P30F0300);
        dba.addWhere(DBC4000.P30F0309, mgtd.getP30F0309());
        dba.addDeleteBatch();
        dba.reInit();
    }

    /**
     * 更新任务体信息
     * @param dba
     * @param keys
     * @throws SQLException
     */
    private static void updateGtdDetail(DBAccess dba, MgtdHeader mgtd) throws Exception
    {
        int row = 1;
        for (MgtdDetail mtts : mgtd.getHintList())
        {
            dba.addTable(DBC4000.P30F0400);
            dba.addParam(DBC4000.P30F0401, row++);
            dba.addParam(DBC4000.P30F0402, mgtd.getP30F0309());
            dba.addParam(DBC4000.P30F0403, mtts.getP30F0403());
            dba.addParam(DBC4000.P30F0404, mtts.getP30F0404());
            dba.addParam(DBC4000.P30F0405, mtts.getP30F0405());
            dba.addParam(DBC4000.P30F0406, mtts.getP30F0406());
            dba.addInsertBatch();
            dba.reInit();
        }
    }

    private static void removeGtdDetail(DBAccess dba, MgtdHeader mgtd) throws Exception
    {
    }

    public static boolean deleteKindData(UserMdl userMdl, String root, Mcat item, int step)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.C2010200);
            dba.addParam(DBC4000.C2010204, root);
            dba.addParam(DBC4000.C2010201, DBC4000.C2010201 + "+" + step, false);
            dba.addWhere(DBC4000.C2010204, item.getC2010203());
            dba.addUpdateBatch();

            dba.reInit();

            dba.addTable(DBC4000.C2010200);
            dba.addWhere(DBC4000.C2010203, item.getC2010203());
            dba.addDeleteBatch();

            dba.executeBatch();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean updateKindData(UserMdl userMdl, Mcat item)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.C2010200);
            dba.addParam(DBC4000.C2010201, item.getC2010201());
            dba.addParam(DBC4000.C2010202, item.getC2010202());
            dba.addParam(DBC4000.C2010204, item.getC2010204());
            dba.addParam(DBC4000.C2010205, Util.text2DB(item.getC2010206()));// 类别名称
            dba.addParam(DBC4000.C2010206, Util.text2DB(item.getC2010207()));// 类别提示
            dba.addParam(DBC4000.C2010208, Util.text2DB(item.getC2010209()));//
            dba.addParam(DBC4000.C2010209, Util.text2DB(item.getC201020A()));// 类别描述
            dba.addParam(DBC4000.C201020A, DBC4000.SQL_NOW, false);// 更新时间
            if (com.magicpwd._util.Char.isValidateHash(item.getC2010203()))
            {
                dba.addWhere(DBC4000.C2010203, item.getC2010203());// 类别索引
                dba.executeUpdate();
            }
            else
            {
                item.setC2010203(Hash.hash(false));
                dba.addParam(DBC4000.C2010203, item.getC2010203());// 类别索引
                dba.addParam(DBC4000.C201020B, DBC4000.SQL_NOW, false);// 更新时间
                dba.executeInsert();
            }
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static List<S1S3> selectKindData(UserMdl userMdl)
    {
        List<S1S3> list = new ArrayList<S1S3>();

        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.C2010200);
            dba.addColumn(DBC4000.C2010203);// 类别索引
            dba.addColumn(DBC4000.C2010205);// 类别名称
            dba.addColumn(DBC4000.C2010206);// 类别提示
            dba.addColumn(DBC4000.C2010209);// 类别提示
            dba.addSort(DBC4000.C2010201, true);

            ResultSet rest = dba.executeSelect();
            S1S3 item;
            while (rest.next())
            {
                item = new S1S3();
                item.setK(rest.getString(DBC4000.C2010203));
                item.setV(rest.getString(DBC4000.C2010205));
                item.setV2(rest.getString(DBC4000.C2010206));
                item.setV3(rest.getString(DBC4000.C2010209));
                list.add(item);
            }
            rest.close();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            dba.dispose();
        }
        return list;
    }

    public static List<Mcat> listCatByHash(UserMdl userMdl, String catHash)
    {
        List<Mcat> list = new ArrayList<Mcat>();

        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.C2010200);
            dba.addColumn(DBC4000.C2010201);
            dba.addColumn(DBC4000.C2010202);
            dba.addColumn(DBC4000.C2010203);// 类别索引
            dba.addColumn(DBC4000.C2010204);
            dba.addColumn(DBC4000.C2010205);// 类别名称
            dba.addColumn(DBC4000.C2010206);// 类别提示
            dba.addColumn(DBC4000.C2010208);
            dba.addColumn(DBC4000.C2010209);// 类别提示
            dba.addWhere(DBC4000.C2010204, catHash);
            dba.addSort(DBC4000.C2010201, true);

            ResultSet rest = dba.executeSelect();
            Mcat item;
            while (rest.next())
            {
                item = new Mcat();
                item.setC2010201(rest.getInt(DBC4000.C2010201));
                item.setC2010202(rest.getInt(DBC4000.C2010202));
                item.setC2010203(rest.getString(DBC4000.C2010203));
                item.setC2010204(rest.getString(DBC4000.C2010204));
                item.setC2010106(rest.getString(DBC4000.C2010205));
                item.setC2010207(rest.getString(DBC4000.C2010206));
                item.setC2010209(rest.getString(DBC4000.C2010208));
                item.setC201020A(rest.getString(DBC4000.C2010209));
                list.add(item);
            }
            rest.close();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            dba.dispose();
        }
        return list;
    }

    public static boolean insertTpltKind(UserMdl userMdl, S1S2 kindItem, String kindDesp)
    {
        DBAccess dba = new DBAccess();

        kindItem.setK(Hash.hash(false));
        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F1100);
            dba.addParam(DBC4000.P30F1101, "0", false);
            dba.addParam(DBC4000.P30F1102, "-1", false);
            dba.addParam(DBC4000.P30F1103, kindItem.getK());
            dba.addParam(DBC4000.P30F1104, "");
            dba.addParam(DBC4000.P30F1105, Util.text2DB(kindItem.getV()));
            dba.addParam(DBC4000.P30F1106, Util.text2DB(kindItem.getV2()));
            dba.addParam(DBC4000.P30F1107, Util.text2DB(kindDesp));
            dba.addParam(DBC4000.P30F1108, DBC4000.SQL_NOW, false);
            dba.addParam(DBC4000.P30F1109, DBC4000.SQL_NOW, false);

            dba.executeInsert();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean updateTpltKind(UserMdl userMdl, S1S2 kindItem, String kindDesp)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F1100);
            dba.addParam(DBC4000.P30F1105, Util.text2DB(kindItem.getV()));
            dba.addParam(DBC4000.P30F1106, Util.text2DB(kindItem.getV2()));
            dba.addParam(DBC4000.P30F1107, Util.text2DB(kindDesp));
            dba.addParam(DBC4000.P30F1108, DBC4000.SQL_NOW, false);
            dba.addWhere(DBC4000.P30F1103, kindItem.getK());

            dba.executeUpdate();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static void saveListData(java.util.List<S1S2> list)
    {
    }

    /**
     * 读取列表属性的备选信息
     * @param hash
     * @return
     */
    public static List<I1S2> selectListData(UserMdl userMdl, String hash)
    {
        java.util.List<I1S2> list = new java.util.ArrayList<I1S2>();

        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F1200);
            dba.addColumn(DBC4000.P30F1201);
            dba.addColumn(DBC4000.P30F1202);
            dba.addColumn(DBC4000.P30F1204);
            dba.addWhere(DBC4000.P30F1203, hash);
            dba.addSort(DBC4000.P30F1201, true);

            ResultSet rest = dba.executeSelect();
            I1S2 item;
            while (rest.next())
            {
                item = new I1S2();
                item.setK(rest.getInt(DBC4000.P30F1201));
                item.setV(rest.getString(DBC4000.P30F1202));
                item.setV2(rest.getString(DBC4000.P30F1204));

                list.add(item);
            }

            rest.close();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            dba.dispose();
        }
        return list;
    }

    public static List<Mlib> selectTpltData(UserMdl userMdl, String hash)
    {
        List<Mlib> kindList = new ArrayList<Mlib>();

        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F1100);
            dba.addColumn(DBC4000.P30F1101);
            dba.addColumn(DBC4000.P30F1102);
            dba.addColumn(DBC4000.P30F1103);
            dba.addColumn(DBC4000.P30F1104);
            dba.addColumn(DBC4000.P30F1105);
            dba.addColumn(DBC4000.P30F1106);
            dba.addColumn(DBC4000.P30F1107);
            if (com.magicpwd._util.Char.isValidate(hash))
            {
                dba.addWhere(DBC4000.P30F1104, hash);
            }
            dba.addSort(DBC4000.P30F1101, true);

            ResultSet rest = dba.executeSelect();
            Mlib item;
            while (rest.next())
            {
                item = new Mlib();
                item.setP30F1101(rest.getInt(DBC4000.P30F1101));
                item.setP30F1102(rest.getInt(DBC4000.P30F1102));
                item.setP30F1103(rest.getString(DBC4000.P30F1103));
                item.setP30F1104(rest.getString(DBC4000.P30F1104));
                item.setP30F1105(rest.getString(DBC4000.P30F1105));
                item.setP30F1106(rest.getString(DBC4000.P30F1106));
                item.setP30F1107(rest.getString(DBC4000.P30F1107));

                kindList.add(item);
            }

            rest.close();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            dba.dispose();
        }
        return kindList;
    }

    public static boolean deleteTpltData(UserMdl userMdl, Mlib tpltItem)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F1100);
            dba.addWhere(com.magicpwd._util.Char.format("{1}='{0}' OR {2}='{0}'", tpltItem.getP30F1103(), DBC4000.P30F1103, DBC4000.P30F1104));
            dba.executeDelete();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean saveTpltData(UserMdl userMdl, Mlib tpltItem)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F1100);
            dba.addParam(DBC4000.P30F1101, tpltItem.getP30F1101());
            dba.addParam(DBC4000.P30F1102, tpltItem.getP30F1102());
            dba.addParam(DBC4000.P30F1104, tpltItem.getP30F1104());
            dba.addParam(DBC4000.P30F1105, Util.text2DB(tpltItem.getP30F1105()));
            dba.addParam(DBC4000.P30F1106, Util.text2DB(tpltItem.getP30F1106()));
            dba.addParam(DBC4000.P30F1107, Util.text2DB(tpltItem.getP30F1107()));
            dba.addParam(DBC4000.P30F1108, DBC4000.SQL_NOW, false);

            if (com.magicpwd._util.Char.isValidateHash(tpltItem.getP30F1103()))
            {
                dba.addWhere(DBC4000.P30F1103, tpltItem.getP30F1103());
                dba.executeUpdate();
            }
            else
            {
                tpltItem.setP30F1103(Hash.hash(false));
                dba.addParam(DBC4000.P30F1103, tpltItem.getP30F1103());
                dba.addParam(DBC4000.P30F1109, DBC4000.SQL_NOW, false);
                dba.executeInsert();
            }
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean updateTpltData(UserMdl userMdl, List<S1S2> list)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            int index = list.size();
            while (index > 0)
            {
                dba.addTable(DBC4000.P30F1100);
                dba.addParam(DBC4000.P30F1101, --index);
                dba.addWhere(DBC4000.P30F1103, list.get(index).getK());
                dba.addUpdateBatch();
                dba.reInit();
            }

            dba.executeBatch();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
        return true;
    }

    public static boolean selectTpltData(UserMdl userMdl, String kindHash, List<IEditItem> tpltList)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F1100);
            dba.addColumn(DBC4000.P30F1102);
            dba.addColumn(DBC4000.P30F1105);
            dba.addColumn(DBC4000.P30F1106);
            dba.addWhere(DBC4000.P30F1104, kindHash);
            dba.addSort(DBC4000.P30F1101, true);

            ResultSet rest = dba.executeSelect();
            IEditItem kv;
            while (rest.next())
            {
                kv = AEditItem.getInstance(userMdl, rest.getInt(DBC4000.P30F1102));
                kv.setName(rest.getString(DBC4000.P30F1105));
                kv.setData(rest.getString(DBC4000.P30F1106));
                tpltList.add(kv);
            }
            rest.close();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean deleteCharData(UserMdl userMdl, Mucs charItem)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F2100);
            dba.addParam(DBC4000.P30F2102, ConsDat.PWDS_MODE_3);
            dba.addWhere(DBC4000.P30F2103, charItem.getP30F2103());// 类别索引

            dba.executeDelete();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean saveCharData(UserMdl userMdl, Mucs charItem)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F2100);
            dba.addParam(DBC4000.P30F2101, charItem.getP30F2101());// 显示排序
            dba.addParam(DBC4000.P30F2102, charItem.getP30F2102());// 使用状态
            dba.addParam(DBC4000.P30F2104, Util.text2DB(charItem.getP30F2104()));// 空间名称
            dba.addParam(DBC4000.P30F2105, Util.text2DB(charItem.getP30F2105()));// 空间提示
            dba.addParam(DBC4000.P30F2106, Util.text2DB(charItem.getP30F2106()));// 空间字符
            dba.addParam(DBC4000.P30F2108, DBC4000.SQL_NOW, false);// 更新日期

            if (com.magicpwd._util.Char.isValidateHash(charItem.getP30F2103()))
            {
                dba.addWhere(DBC4000.P30F2103, charItem.getP30F2103());// 空间索引
                dba.executeUpdate();
            }
            else
            {
                charItem.setP30F2103(Hash.hash(false));
                dba.addParam(DBC4000.P30F2103, charItem.getP30F2103());// 空间索引
                dba.addParam(DBC4000.P30F2109, DBC4000.SQL_NOW, false);// 创建日期
                dba.executeInsert();
            }
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean deleteMexpData(UserMdl userMdl, Mexp mexp)
    {
        if (mexp == null || !com.magicpwd._util.Char.isValidateHash(mexp.getP30F0803()))
        {
            return false;
        }

        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);
            dba.addTable(DBC4000.P30F0800);
            dba.addWhere(DBC4000.P30F0803, mexp.getP30F0803());
            return 1 == dba.executeDelete();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static List<Mexp> readMexpData(UserMdl userMdl)
    {
        // 数据库连接初始化
        DBAccess dba = new DBAccess();

        java.util.List<Mexp> mexpList = new java.util.ArrayList<Mexp>();
        try
        {
            dba.init(userMdl);
            dba.addTable(DBC4000.P30F0800);
            dba.addSort(DBC4000.P30F0801, true);
            ResultSet rest = dba.executeSelect();
            Mexp mexp;
            while (rest.next())
            {
                mexp = new Mexp();
                mexp.setP30F0801(rest.getInt(DBC4000.P30F0801));
                mexp.setP30F0802(rest.getInt(DBC4000.P30F0802));
                mexp.setP30F0803(rest.getString(DBC4000.P30F0803));
                mexp.setP30F0804(rest.getString(DBC4000.P30F0804));
                mexp.setP30F0805(rest.getString(DBC4000.P30F0805));
                mexp.setP30F0806(rest.getString(DBC4000.P30F0806));
                mexp.setP30F0807(rest.getString(DBC4000.P30F0807));
                mexpList.add(mexp);
            }
            rest.close();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            dba.dispose();
        }
        return mexpList;
    }

    /**
     * 表达式
     * @param userMdl
     * @param mexp
     * @return
     */
    public static boolean saveMexpData(UserMdl userMdl, Mexp mexp)
    {
        // 数据库连接初始化
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);
            dba.addTable(DBC4000.P30F0800);
            dba.addParam(DBC4000.P30F0801, mexp.getP30F0801());
            dba.addParam(DBC4000.P30F0802, mexp.getP30F0802());
            dba.addParam(DBC4000.P30F0804, mexp.getP30F0804());
            dba.addParam(DBC4000.P30F0805, mexp.getP30F0805());
            dba.addParam(DBC4000.P30F0806, mexp.getP30F0806());
            dba.addParam(DBC4000.P30F0807, mexp.getP30F0807());

            if (com.magicpwd._util.Char.isValidateHash(mexp.getP30F0803()))
            {
                // 数据更新
                dba.addWhere(DBC4000.P30F0803, mexp.getP30F0803());
                dba.executeUpdate();
            }
            else
            {
                // 新增数据
                mexp.setP30F0803(Hash.hash(false));
                dba.addParam(DBC4000.P30F0803, mexp.getP30F0803());
                dba.executeInsert();
            }
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    /**
     * 读取字符空间列表
     * @return
     */
    public static List<Mucs> selectCharData(UserMdl userMdl)
    {
        List<Mucs> charList = new LinkedList<Mucs>();

        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F2100);
            dba.addColumn(DBC4000.P30F2103);
            dba.addColumn(DBC4000.P30F2104);
            dba.addColumn(DBC4000.P30F2105);
            dba.addColumn(DBC4000.P30F2106);
            dba.addSort(DBC4000.P30F2101, true);

            ResultSet rest = dba.executeSelect();
            Mucs charItem;
            while (rest.next())
            {
                charItem = new Mucs();
                charItem.setP30F2103(rest.getString(DBC4000.P30F2103));
                charItem.setP30F2104(rest.getString(DBC4000.P30F2104));
                charItem.setP30F2105(rest.getString(DBC4000.P30F2105));
                charItem.setP30F2106(rest.getString(DBC4000.P30F2106));

                charList.add(charItem);
            }

            rest.close();
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            dba.dispose();
        }
        return charList;
    }

    public static boolean pickupHistData(UserMdl userMdl, String keysHash, String logsHash, int sequence)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            backup(dba, keysHash);

            String DELETE = "DELETE FROM {0} WHERE {1}='{2}'";
            // 删除信息数据
            dba.addBatch(com.magicpwd._util.Char.format(DELETE, DBC4000.P30F0100, DBC4000.P30F0104, keysHash));
            // 删除内容数据
            dba.addBatch(com.magicpwd._util.Char.format(DELETE, DBC4000.P30F0200, DBC4000.P30F0202, keysHash));

            dba.addParam(DBC4000.P30F0101, sequence);
            dba.addParam(DBC4000.P30F0102, DBC4000.P30F0A02, false);
            dba.addParam(DBC4000.P30F0103, DBC4000.P30F0A03, false);
            dba.addParam(DBC4000.P30F0104, DBC4000.P30F0A04, false);
            dba.addParam(DBC4000.P30F0105, DBC4000.P30F0A05, false);
            dba.addParam(DBC4000.P30F0106, DBC4000.P30F0A06, false);
            dba.addParam(DBC4000.P30F0107, DBC4000.P30F0A07, false);
            dba.addParam(DBC4000.P30F0108, DBC4000.P30F0A08, false);
            dba.addParam(DBC4000.P30F0109, DBC4000.P30F0A09, false);
            dba.addParam(DBC4000.P30F010A, DBC4000.P30F0A0A, false);
            dba.addParam(DBC4000.P30F010B, DBC4000.P30F0A0B, false);
            dba.addParam(DBC4000.P30F010C, DBC4000.P30F0A0C, false);
            dba.addParam(DBC4000.P30F010D, DBC4000.P30F0A0D, false);
            dba.addParam(DBC4000.P30F010E, DBC4000.P30F0A0E, false);
            dba.addParam(DBC4000.P30F010F, DBC4000.P30F0A0F, false);
            dba.addParam(DBC4000.P30F0110, DBC4000.P30F0A10, false);
            dba.addWhere(DBC4000.P30F0A01, logsHash);
            dba.addCopyBatch(DBC4000.P30F0100, DBC4000.P30F0A00);
            dba.reInit();

            dba.addParam(DBC4000.P30F0201, DBC4000.P30F0B02, false);
            dba.addParam(DBC4000.P30F0202, DBC4000.P30F0B03, false);
            dba.addParam(DBC4000.P30F0203, DBC4000.P30F0B04, false);
            dba.addWhere(DBC4000.P30F0B01, logsHash);
            dba.addCopyBatch(DBC4000.P30F0200, DBC4000.P30F0B00);
            dba.reInit();

            dba.executeBatch();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean deleteHistData(UserMdl userMdl, String keysHash, String logsHash)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            boolean b = com.magicpwd._util.Char.isValidateHash(logsHash);

            dba.addTable(DBC4000.P30F0A00);
            dba.addWhere(DBC4000.P30F0A04, keysHash);
            if (b)
            {
                dba.addWhere(DBC4000.P30F0A01, logsHash);
            }
            dba.addDeleteBatch();

            dba.reInit();
            dba.addTable(DBC4000.P30F0B00);
            dba.addWhere(DBC4000.P30F0B03, keysHash);
            if (b)
            {
                dba.addWhere(DBC4000.P30F0B01, logsHash);
            }
            dba.addDeleteBatch();

            dba.executeBatch();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean selectHistData(UserMdl userMdl, String logsHash, MpwdHeader keys)
    {
        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F0A00);
            dba.addWhere(DBC4000.P30F0A01, logsHash);
            ResultSet rest = dba.executeSelect();
            if (!rest.next())
            {
                return false;
            }

            keys.setP30F0102(rest.getInt(DBC4000.P30F0A02));
            keys.setP30F0103(rest.getInt(DBC4000.P30F0A03));
            keys.setP30F0104(rest.getString(DBC4000.P30F0A04));
            keys.setP30F0106(rest.getString(DBC4000.P30F0A06));
            keys.setP30F0107(rest.getString(DBC4000.P30F0A07));
            keys.setP30F0108(rest.getString(DBC4000.P30F0A08));
            keys.setP30F0109(rest.getString(DBC4000.P30F0A09));
            keys.setP30F010A(rest.getString(DBC4000.P30F0A0A));
            keys.setP30F010B(rest.getString(DBC4000.P30F0A0B));
            keys.setP30F010C(rest.getString(DBC4000.P30F0A0C));
            keys.setP30F010D(rest.getString(DBC4000.P30F0A0D));
            keys.setP30F010E(rest.getString(DBC4000.P30F0A0E));
            keys.setP30F010F(rest.getString(DBC4000.P30F0A0F));
            keys.setP30F0110(rest.getString(DBC4000.P30F0A10));

            dba.reInit();
            dba.addTable(DBC4000.P30F0B00);
            dba.addColumn(DBC4000.P30F0B04);
            dba.addWhere(DBC4000.P30F0B01, logsHash);
            dba.addSort(DBC4000.P30F0B02);
            rest = dba.executeSelect();
            StringBuffer sb = keys.getMpwd().getP30F0203();
            while (rest.next())
            {
                sb.append(rest.getString(DBC4000.P30F0B04));
            }
            keys.getMpwd().setP30F0202(keys.getP30F0104());

            rest.close();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }

    public static boolean selectHistData(UserMdl userMdl, String hash, List<S1S2> list)
    {
        if (!com.magicpwd._util.Char.isValidateHash(hash))
        {
            return false;
        }

        DBAccess dba = new DBAccess();

        try
        {
            dba.init(userMdl);

            dba.addTable(DBC4000.P30F0A00);
            dba.addColumn(DBC4000.P30F0A01);
            dba.addWhere(DBC4000.P30F0A04, hash);
            dba.addSort(DBC4000.P30F0A01, false);

            S1S2 item;
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(ConsEnv.HINT_DATE);
            ResultSet rest = dba.executeSelect();
            while (rest.next())
            {
                item = new S1S2();
                item.setK(rest.getString(DBC4000.P30F0A01));
                item.setV(sdf.format(new Date(Long.parseLong(item.getK(), 16))));
                item.setV2(item.getV());

                list.add(item);
            }

            rest.close();
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            dba.dispose();
        }
    }
}
