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
package com.magicpwd.m;

import com.magicpwd._comn.prop.Char;
import com.magicpwd.d.db.DBA4000;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 * 口令字符究竟模型
 * @author Amon
 */
public final class CharMdl extends AbstractListModel
{

    private static boolean withSys;
    private boolean charUpd;
    private List<Char> charSys;
    private List<Char> charUsr;
    private UserMdl userMdl;

    CharMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    void initData()
    {
        charSys = new ArrayList<Char>(7);

        Char c = new Char();
        c.setP30F2103("1000000000000001");
        c.setP30F2104("数字");
        c.setP30F2105("数字");
        c.setP30F2106("0123456789");
        charSys.add(c);

        c = new Char();
        c.setP30F2103("1000000000000002");
        c.setP30F2104("小写字母");
        c.setP30F2105("小写字母");
        c.setP30F2106("abcdefghijklmnopqrstuvwxyz");
        charSys.add(c);

        c = new Char();
        c.setP30F2103("1000000000000003");
        c.setP30F2104("大写字母");
        c.setP30F2105("大写字母");
        c.setP30F2106("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        charSys.add(c);

        c = new Char();
        c.setP30F2103("1000000000000004");
        c.setP30F2104("特殊字符");
        c.setP30F2105("特殊字符");
        c.setP30F2106("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
        charSys.add(c);

        c = new Char();
        c.setP30F2103("1000000000000005");
        c.setP30F2104("大小写字母");
        c.setP30F2105("大小写字母");
        c.setP30F2106("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        charSys.add(c);

        c = new Char();
        c.setP30F2103("1000000000000006");
        c.setP30F2104("字母及数字");
        c.setP30F2105("字母及数字");
        c.setP30F2106("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        charSys.add(c);

        c = new Char();
        c.setP30F2103("1000000000000007");
        c.setP30F2104("可输入字符");
        c.setP30F2105("可输入字符");
        c.setP30F2106("!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");
        charSys.add(c);

        charUsr = DBA4000.selectCharData(userMdl);
    }

    @Override
    public int getSize()
    {
        if (charUsr == null)
        {
            charUsr = DBA4000.selectCharData(userMdl);
        }
        return (isWithSys() ? charSys.size() : 0) + charUsr.size();
    }

    @Override
    public Object getElementAt(int index)
    {
        if (isWithSys())
        {
            if (index < charSys.size())
            {
                return charSys.get(index);
            }
            index -= charSys.size();
            return charUsr.get(index);
        }

        return charUsr.get(index);
    }

    /**
     * @return the charUpd
     */
    public boolean isCharUpd()
    {
        return charUpd;
    }

    /**
     * @param charUpd
     *            the charUpd to set
     */
    public void setCharUpd(boolean charUpd)
    {
        this.charUpd = charUpd;
    }

    public void appendItem(Char data)
    {
        int index = charUsr.size();
        data.setP30F2101(index);
        charUsr.add(data);
        DBA4000.saveCharData(userMdl, data);
        fireIntervalAdded(this, index, index);

        charUpd = true;
    }

    public void updateItemAt(int index, Char data)
    {
        if (withSys)
        {
            index -= charSys.size();
        }
        charUsr.set(index, data);
        DBA4000.saveCharData(userMdl, data);
        fireContentsChanged(this, index, index);

        charUpd = true;
    }

    public void removeItemAt(int index)
    {
        if (withSys)
        {
            index -= charSys.size();
        }
        if (index < 0 || index >= charUsr.size())
        {
            return;
        }
        DBA4000.deleteCharData(userMdl, charUsr.get(index));
        charUsr.remove(index);
        fireIntervalRemoved(this, index, index);

        charUpd = true;
    }

    public void changeItemAt(int index, int toward)
    {
        if (withSys)
        {
            index -= charSys.size();
        }
        if (toward == 0 || index < 0 || index >= charUsr.size())
        {
            return;
        }
        Char src = charUsr.get(index);

        toward += index;
        if (toward < 0)
        {
            toward = 0;
        }
        if (toward >= charUsr.size())
        {
            toward = charUsr.size() - 1;
        }
        Char dst = charUsr.get(toward);
        dst.setP30F2101(index);
        DBA4000.saveCharData(userMdl, dst);
        src.setP30F2101(toward);
        DBA4000.saveCharData(userMdl, src);
        charUsr.set(index, dst);
        charUsr.set(toward, src);

        charUpd = true;
    }

    public List<Char> getCharSys()
    {
        return charSys;
    }

    public List<Char> getCharUsr()
    {
        return charUsr;
    }

    /**
     * @return the withSys
     */
    public static boolean isWithSys()
    {
        return withSys;
    }

    /**
     * @param aWithSys the withSys to set
     */
    public static void setWithSys(boolean aWithSys)
    {
        withSys = aWithSys;
    }
}
