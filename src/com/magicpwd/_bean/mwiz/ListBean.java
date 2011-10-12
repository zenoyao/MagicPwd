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
package com.magicpwd._bean.mwiz;

import com.magicpwd.__i.IEditItem;
import com.magicpwd.__i.mwiz.IMwizBean;
import com.magicpwd._bean.AListBean;
import com.magicpwd.v.app.mwiz.MwizPtn;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-10-27 21:28:18
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class ListBean extends AListBean implements IMwizBean
{

    public ListBean(MwizPtn normPtn)
    {
        super(normPtn);
    }

    @Override
    public void initView()
    {
    }

    @Override
    public void initLang()
    {
        initConfLang();
    }

    @Override
    public void initData()
    {
        initConfData();
    }

    @Override
    public void showData(IEditItem itemData)
    {
        this.itemData = itemData;

//        tf_PropData.setText(itemData.getData());

        showConfData();
    }

    @Override
    public void setLabelFor(javax.swing.JLabel label)
    {
    }

    @Override
    public javax.swing.JComponent getComponent()
    {
        return this;
    }

    @Override
    public boolean copyData()
    {
        return true;
    }

    @Override
    public boolean saveData()
    {
        saveName();
        return true;
    }

    @Override
    public void requestFocus()
    {
//        ls_PropData.requestFocus();
    }
}
