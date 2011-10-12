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
package com.magicpwd._comn.apps;

import com.magicpwd.__i.lock.ILockClient;

/**
 *
 * @author Amon
 */
public class SocketClient implements ILockClient
{

    private java.net.Socket request(int port)
    {
        java.net.Socket socket = null;
        try
        {
            socket = new java.net.Socket("127.0.0.1", port);
            socket.setSoTimeout(200);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return socket;
    }

    private boolean read(java.net.Socket socket)
    {
        if (socket == null)
        {
            return false;
        }

        boolean running = false;
        java.io.OutputStream out = null;
        java.io.BufferedWriter bw = null;
        try
        {
            out = socket.getOutputStream();
            bw = new java.io.BufferedWriter(new java.io.OutputStreamWriter(out));
            bw.write("");
            bw.flush();
            bw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                bw.flush();
                bw.close();
                out.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        java.io.InputStream in = null;
        java.io.BufferedReader br = null;
        try
        {
            in = socket.getInputStream();
            br = new java.io.BufferedReader(new java.io.InputStreamReader(in));
            running = "".equals(br.readLine());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                br.close();
                in.close();
                socket.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return running;
    }

    @Override
    public boolean canRead()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
