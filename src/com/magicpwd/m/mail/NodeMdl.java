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
package com.magicpwd.m.mail;

import com.magicpwd._util.Logs;
import com.magicpwd._bean.mail.Connect;
import javax.mail.Folder;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Amon
 * 
 */
public class NodeMdl extends DefaultMutableTreeNode
{

    private Connect connect;
    private String display;
    private String keyWord;
    private boolean haveMsg;

    public NodeMdl(Connect connect, Folder folder)
    {
        if (folder != null)
        {
            try
            {
                this.connect = connect;
                keyWord = folder.getFullName();
                if (!com.magicpwd._util.Char.isValidate(keyWord))
                {
                    display = connect.getMail();
                }
                else
                {
                    display = com.magicpwd._util.Char.format("{0}({1}/{2})", folder.getName(), Integer.toString(folder.getUnreadMessageCount()), Integer.toString(folder.getMessageCount()));
                }
                haveMsg = (folder.getType() & Folder.HOLDS_MESSAGES) != 0;
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
        }
    }

    @Override
    public boolean isLeaf()
    {
        return false;
    }

    @Override
    public String toString()
    {
        return com.magicpwd._util.Char.isValidate(display) ? display : connect.getMail();
    }

    /**
     * @return the connect
     */
    public Connect getConnect()
    {
        return connect;
    }

    /**
     * @return the keyWord
     */
    public String getKeyWord()
    {
        return keyWord;
    }

    /**
     * @return the haveMsg
     */
    public boolean isHaveMsg()
    {
        return haveMsg;
    }
}
