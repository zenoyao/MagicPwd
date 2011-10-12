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
package com.magicpwd._util;

import com.magicpwd._cons.ConsEnv;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 *
 * @author Amon
 */
public class Http
{

    public static InputStream post(HttpURLConnection con, HashMap<String, String> map) throws Exception
    {
        con.setDoOutput(true);// 打开写入属性
        con.setRequestMethod("POST");// 设置提交方法
//        con.setConnectTimeout(50000);// 连接超时时间
//        con.setReadTimeout(50000);

        StringBuilder buf = new StringBuilder();
        for (String key : map.keySet())
        {
            buf.append('&').append(key).append('=').append(java.net.URLEncoder.encode(map.get(key), ConsEnv.FILE_ENCODING));
        }
//        con.setRequestProperty("Content-length", Integer.toString(buf.length() - 1));
//        con.setRequestProperty("Content-Type", "application/x-www- form-urlencoded");
        con.connect();

        java.io.OutputStream out = con.getOutputStream();
        out.write(buf.substring(1).getBytes());
        out.flush();
        out.close();

        con.getResponseCode();
        return con.getInputStream();
    }
}
