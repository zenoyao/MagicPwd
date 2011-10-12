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
package com.magicpwd.u;

import com.magicpwd._cons.DBC3000;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 *
 * @author Amon
 */
public class DBU3000
{

    public void initView()
    {
    }

    public void initLang()
    {
    }

    public void initData()
    {
        try
        {
            Class.forName("org.hsqldb.jdbcDriver");

            Connection connSrc1 = DriverManager.getConnection("jdbc:hsqldb:file:D:/Free/hsqldb/data/amon");
            Statement statSrc1 = connSrc1.createStatement();
            Statement statSrc2 = connSrc1.createStatement();

            Connection connDst = DriverManager.getConnection("jdbc:hsqldb:file:./dat/amon");
            Statement statDst = connDst.createStatement();

            copyPwds(statSrc1, statSrc2, statDst);

            statSrc2.close();
            statSrc1.execute("SHUTDOWN");
            statSrc1.close();
            connSrc1.close();

            connDst.createStatement().execute("SHUTDOWN");
            connDst.close();
            connDst.close();
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

    private void copyPwds(Statement statSrc1, Statement statSrc2, Statement statDst) throws Exception
    {
        ResultSet result = statSrc1.executeQuery("select * from p30f0100 where p30f0101=1 order by p30f0104, p30f0102");
        String lastHash = "";
        StringBuffer buf = new StringBuffer();
        while (result.next())
        {

            String hash = result.getString("P30F0104");
            if (!lastHash.equalsIgnoreCase(hash))
            {
                lastHash = hash;
                buf.delete(0, buf.length());
                buf.append("insert into ").append(DBC3000.P30F0100).append(" values (");
                buf.append("'0',");
                buf.append("'").append(result.getString("P30F0101")).append("',");
                buf.append("'0',");
                buf.append("'").append(hash).append("',");
                buf.append("'00000000',");
                buf.append("'").append(result.getString("P30F0106")).append("',");
                buf.append("'").append(new Timestamp(Long.parseLong(result.getString("P30F0103"), 16))).append("',");
                buf.append("'',");
                buf.append("'").append(toDB(result.getString("P30F0107"))).append("',");
                buf.append("'").append(toDB(result.getString("P30F0108"))).append("',");
                buf.append("'0',");
                buf.append("'',");
                buf.append("'").append(toDB(result.getString("P30F010C"))).append("',");
                buf.append("'").append(toDB(result.getString("P30F010D"))).append("',");
                buf.append("'").append(toDB(result.getString("P30F010A"))).append("'");
                buf.append(")");
                statDst.executeUpdate(buf.toString());
            }

            buf.delete(0, buf.length());
            buf.append("insert into ").append(DBC3000.P30F0200).append(" values (");
            buf.append("'").append(result.getString("P30F0102")).append("',");
            buf.append("'").append(hash).append("',");
            buf.append("'").append(result.getString("P30F0109")).append("'");
            buf.append(")");
            statDst.executeUpdate(buf.toString());

            statSrc1.executeUpdate("delete from p30f0100 where p30f0104='" + hash + "'");
        }
        result.close();
    }

    private static String toDB(String text)
    {
        return text != null ? text.replace("'", "''") : text;
    }

    public static void main(String[] args)
    {
        new DBU3000().initData();
    }
}
