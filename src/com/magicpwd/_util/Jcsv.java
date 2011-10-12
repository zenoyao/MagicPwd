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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.magicpwd._cons.ConsEnv;

/**
 * @author Amon
 * 
 */
public class Jcsv
{

    private java.io.File fn;
    /**
     * 是否包含头部
     */
    private boolean hd = true;
    private String es = "\"";
    private String ee = "\"";
    private String sp = ",";
    private String sl = "\n";
    private String fe = ConsEnv.FILE_ENCODING;
    private String fh;

    public Jcsv(java.io.File fileName)
    {
        this.fn = fileName;
    }

    public void setFilePath(java.io.File filePath)
    {
        this.fn = filePath;
    }

    public void setHd(boolean hd)
    {
        this.hd = hd;
    }

    public void setEs(String es)
    {
        this.es = es;
    }

    public void setEe(String ee)
    {
        this.ee = ee;
    }

    public void setFileEncoding(String encoding)
    {
        this.fe = encoding;
    }

    public ArrayList<ArrayList<String>> readFile() throws IOException
    {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fn), fe));

        boolean wrap = false;
        String row = null;
        String line;
        if (hd)
        {
            fh = br.readLine();
        }
        while (true)
        {
            line = br.readLine();
            // 最后一行存在错误的情况下，不做处理
            if (line == null)
            {
                break;
            }

            line = line.replaceAll("\r\n?", "\n").replaceAll("[" + es + "]{2}+", "\r").replaceAll("[" + ee + "]{2}+", "\b");
            // 已有换行
            if (row != null)
            {
                // 中间换行
                row += sl + line;
            }
            else
            {
                row = line;
            }

            if (!line.endsWith(sp))
            {
                line += sp;
            }
            line = line.replaceAll(sp + "\\s+" + es, sp + es);//,  "
            line = line.replaceAll(ee + "\\s+" + sp, ee + sp);//"  ,
            if (wrap)
            {
                int ie = line.indexOf(ee + sp);
                int is = line.indexOf(sp + es);
                if (ie < is || ie < 0)
                {
                    continue;
                }
            }
            else
            {
                int ie = line.lastIndexOf(ee + sp);//",
                int is = line.lastIndexOf(sp + es);//,"
                if (ie < is)
                {
                    wrap = true;
                    continue;
                }
            }

            data.add(deCode(row));
            row = null;
            wrap = false;
        }
        br.close();

        return data;
    }

    public void saveFile(ArrayList<ArrayList<String>> data) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fn), fe));
        if (hd)
        {
            bw.write(fh);
            bw.newLine();
        }
        for (ArrayList<String> temp : data)
        {
            bw.write(enCode(temp));
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    private void append(ArrayList<String> data, String text)
    {
        data.add(text.replace("\r", es).replace("\b", ee));
    }

    private ArrayList<String> deCode(String text)
    {
        ArrayList<String> data = new ArrayList<String>();

        if (text == null)
        {
            return data;
        }

        text = text.trim();
        if (text.length() < 1)
        {
            return data;
        }

        if (!text.endsWith(sp))
        {
            text += sp;
        }

        boolean b = false;
        int s = 0;
        int e = text.indexOf(sp, s);
        String temp;
        StringBuilder sb = new StringBuilder();
        while (e >= s)
        {
            temp = text.substring(s, e);
            if (!b)
            {
                if (temp.startsWith(es))
                {
                    temp = temp.substring(es.length());

                    if (temp.endsWith(ee))
                    {
                        temp = temp.substring(0, temp.length() - ee.length());
                        temp = temp.replace(es + es, es);
                        if (!ee.equals(es))
                        {
                            temp = temp.replace(ee + ee, ee);
                        }
                        append(data, temp);
                    }
                    else
                    {
                        sb.append(temp).append(sp);
                        b = true;
                    }
                }
                else
                {
                    append(data, temp);
                }
            }
            else
            {
                if (temp.endsWith(ee))
                {
                    sb.append(temp.substring(0, temp.length() - ee.length()));
                    temp = sb.toString().replace(es + es, es);
                    if (!ee.equals(es))
                    {
                        temp = temp.replace(ee + ee, ee);
                    }
                    append(data, temp);
                    sb.delete(0, sb.length());
                    b = false;
                }
                else
                {
                    sb.append(temp).append(sp);
                }
            }
            s = e + 1;
            e = text.indexOf(sp, s);
        }

        return data;
    }

    private String enCode(ArrayList<String> data)
    {
        if (data == null)
        {
            return "";
        }

        if (data.size() < 1)
        {
            return sp;
        }

        StringBuilder sb = new StringBuilder();

        boolean b = false;
        for (String temp : data)
        {
            if (temp.indexOf(es) > -1)
            {
                temp = temp.replace(es, es + es);
                b = true;
            }

            if (!ee.equals(es))
            {
                if (temp.indexOf(ee) > -1)
                {
                    temp = temp.replace(ee, ee + ee);
                    b = true;
                }
            }

            if (temp.indexOf(sp) > -1 || temp.indexOf(sl) > -1)
            {
                b = true;
            }

            if (b)
            {
                temp = es + temp + ee;
                b = false;
            }
            sb.append(temp).append(sp);
        }
        return sb.toString();
    }

    public void setHead(String head)
    {
        this.fh = head;
    }

    public String getHead()
    {
        return fh != null ? fh.trim() : null;
    }
}
