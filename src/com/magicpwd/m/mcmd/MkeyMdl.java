/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magicpwd.m.mcmd;

import com.magicpwd.__i.mcmd.IPageMdl;
import com.magicpwd._comn.mpwd.MpwdHeader;
import com.magicpwd._cons.mcmd.McmdEnv;
import com.magicpwd._util.Char;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.UserMdl;

/**
 *
 * @author Aven
 */
public class MkeyMdl implements IPageMdl
{
    private UserMdl userMdl;
    private int curPage;
    private int maxPage;
    private boolean onePage;
    private java.util.List<MpwdHeader> mpwdList;
    private java.util.Map<String, MpwdHeader> mpwdMaps;

    public MkeyMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    public void init()
    {
        mpwdList = new java.util.ArrayList<MpwdHeader>();
        mpwdMaps = new java.util.HashMap<String, MpwdHeader>();
    }

    @Override
    public void clear()
    {
        mpwdList.clear();
        mpwdMaps.clear();
    }

    public void listKey(String catHash)
    {
        mpwdList.clear();
        if (!Char.isValidate(catHash))
        {
            catHash = "0";
        }
        DBA4000.listKeyHeaderByCat(userMdl, catHash, mpwdList);
    }

    @Override
    public String print()
    {
        int tmp = mpwdList.size();
        int s;
        int e;
        if (onePage)
        {
            s = 0;
            e = tmp;
        }
        else
        {
            s = curPage * McmdEnv.KEY_PAGE_SIZE;
            e = s + McmdEnv.KEY_PAGE_SIZE;
            if (e > tmp)
            {
                e = tmp;
            }
        }

        char c = 'a';
        String t;
        MpwdHeader header;
        StringBuilder buf = new StringBuilder();
        while (s < e)
        {
            header = mpwdList.get(s++);
            t = genKey(c++);
            mpwdMaps.put(t, header);
            buf.append(t).append(' ').append(header.getP30F0109()).append('\n');
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

    public MpwdHeader getKey(String cmd)
    {
        cmd = cmd.trim().toLowerCase();
        return java.util.regex.Pattern.matches("^\\d*[a-z]$", cmd) ? mpwdMaps.get(cmd) : null;
    }

    private String genKey(char c)
    {
        return (isOnePage() ? "" + curPage : "") + c;
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
