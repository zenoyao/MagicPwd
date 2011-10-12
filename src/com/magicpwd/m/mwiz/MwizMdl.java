/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magicpwd.m.mwiz;

import com.magicpwd.m.UserMdl;

/**
 *
 * @author Amon
 */
public final class MwizMdl
{

    private UserMdl userMdl;
    private GridMdl gridMdl;
    private KeysMdl keysMdl;

    public MwizMdl(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    public void init()
    {
        this.keysMdl = new KeysMdl(userMdl);
        this.keysMdl.init();
        this.gridMdl = new GridMdl(userMdl);
        this.gridMdl.init();
    }

    /**
     * @return the gridMdl
     */
    public GridMdl getGridMdl()
    {
        return gridMdl;
    }

    /**
     * @param gridMdl the gridMdl to set
     */
    public void setGridMdl(GridMdl gridMdl)
    {
        this.gridMdl = gridMdl;
    }

    /**
     * @return the keysMdl
     */
    public KeysMdl getKeysMdl()
    {
        return keysMdl;
    }

    /**
     * @param keysMdl the keysMdl to set
     */
    public void setKeysMdl(KeysMdl keysMdl)
    {
        this.keysMdl = keysMdl;
    }
}
