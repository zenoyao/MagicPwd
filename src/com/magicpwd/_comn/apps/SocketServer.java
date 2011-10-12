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

import com.magicpwd.__i.lock.ILockServer;

/**
 *
 * @author Amon
 */
public class SocketServer implements ILockServer
{

    private java.util.HashMap<String, String> apps;
    private java.net.ServerSocket listenServerSocket;

    private java.net.Socket listen(java.net.ServerSocket serverSocket)
    {
        java.net.Socket socket = null;
        try
        {
            socket = serverSocket.accept();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return socket;
    }

    public void run()
    {
        new Thread()
        {

            public void run()
            {
                while (true)
                {
                    write(listen(listenServerSocket));
                }
            }
        }.start();
    }

    private void write(java.net.Socket socket)
    {
        if (socket == null)
        {
            return;
        }

        java.io.OutputStream out = null;
        java.io.OutputStreamWriter outr = null;
        java.io.BufferedWriter bw = null;
        try
        {
            out = socket.getOutputStream();
            outr = new java.io.OutputStreamWriter(out);
            bw = new java.io.BufferedWriter(outr);
            bw.write(apps.get(""));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                bw.close();
                outr.close();
                out.close();
                socket.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean tryLock()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
