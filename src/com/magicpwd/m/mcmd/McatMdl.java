/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magicpwd.m.mcmd;

import com.magicpwd.__i.mcmd.IPageMdl;
import com.magicpwd._comn.mpwd.Mcat;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._cons.mcmd.McmdEnv;
import com.magicpwd._util.Char;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.UserMdl;

/**
 *
 * @author Aven
 */
public class McatMdl implements IPageMdl
{
    private UserMdl userMdl;
    private int curPage;
    private int maxPage;
    private boolean onePage;
    private java.util.List<Mcat> mcatList;
    private java.util.Map<String, Mcat> mcatMaps;
    private java.util.List<Mcat> mcatPath;
    private int curPath;

    public McatMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    public void init()
    {
        mcatList = new java.util.ArrayList<Mcat>();
        mcatMaps = new java.util.HashMap<String, Mcat>();

        mcatPath = new java.util.ArrayList<Mcat>(5);
        Mcat mcat = new Mcat();
        mcat.setC2010203(ConsDat.HASH_ROOT);
        mcat.setC2010106("魔方密码");
        mcat.setC2010207("魔方密码");
        mcatPath.add(mcat);
    }

    @Override
    public void clear()
    {
        mcatList.clear();
        mcatMaps.clear();
    }

    @Override
    public String print()
    {
        int tmp = mcatList.size();
        int s;
        int e;
        if (onePage)
        {
            s = 0;
            e = tmp;
        }
        else
        {
            s = curPage * McmdEnv.CAT_PAGE_SIZE;
            e = s + McmdEnv.CAT_PAGE_SIZE;
            if (e > tmp)
            {
                e = tmp;
            }
        }

        StringBuilder buf = new StringBuilder();
        int i = 0;
        Mcat mcat;
        String t;
        while (s < e)
        {
            mcat = mcatList.get(s++);
            t = genKey(i++);
            buf.append(t).append(' ').append(mcat.getC2010206()).append('\n');
            mcatMaps.put(t, mcat);
        }
        return buf.toString();
    }

    @Override
    public String firstPage()
    {
        curPage = 0;
        return print();
    }

    @Override
    public String prevPage()
    {
        if (curPage <= 0)
        {
            return "";
        }

        curPage -= 1;
        return print();
    }

    @Override
    public String nextPage()
    {
        if (curPage >= maxPage)
        {
            return "";
        }

        curPage += 1;
        return print();
    }

    @Override
    public String lastPage()
    {
        curPage = maxPage;
        return print();
    }

    public void listCat()
    {
        mcatList = DBA4000.listCatByHash(userMdl, mcatPath.get(curPath).getC2010203());
    }

    public String showPath()
    {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i <= curPath; i += 1)
        {
            buf.append('/').append(mcatPath.get(i).getC2010206());
        }
        buf.append('\n');
        return buf.toString();
    }

    public boolean changeCat(String cmd)
    {
        if (!Char.isValidate(cmd))
        {
            return false;
        }
        cmd = cmd.trim().toLowerCase();
        if (".".equals(cmd))
        {
            return true;
        }
        if ("..".equals(cmd))
        {
            if (curPath > 0)
            {
                mcatPath.remove(curPath);
                curPath -= 1;
            }
            return true;
        }
        if ("~".equals(cmd))
        {
            curPath = 0;
            return true;
        }
        if (!java.util.regex.Pattern.matches("^\\d+$", cmd))
        {
            return false;
        }
        Mcat cat = mcatMaps.get(cmd);
        if (cat == null)
        {
            return false;
        }
        mcatPath.add(cat);
        curPath += 1;
        return true;
    }

    public Mcat getCat()
    {
        return mcatPath.get(curPath);
    }

    private String genKey(int i)
    {
        return (isOnePage() ? "" + curPage : "") + i;
    }

    /**
     * @return the onePage
     */
    public boolean isOnePage()
    {
        return onePage;
    }

    /**
     * @param onePage the onePage to set
     */
    public void setOnePage(boolean onePage)
    {
        this.onePage = onePage;
    }
}
