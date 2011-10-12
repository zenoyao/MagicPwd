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
import com.magicpwd._bean.AAreaBean;
import com.magicpwd._util.Util;
import com.magicpwd.v.app.mwiz.MwizPtn;

/**
 *
 * @author Amon
 */
public class AreaBean extends AAreaBean implements IMwizBean
{

    public AreaBean(MwizPtn normPtn)
    {
        super(normPtn);
    }

    @Override
    public void initView()
    {
        initConfView();

        ta_PropData.setColumns(20);
        ta_PropData.setRows(4);
        javax.swing.JScrollPane jsp = new javax.swing.JScrollPane(ta_PropData);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
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

        ta_PropData.setText(itemData.getData());

        showConfData();
    }

    @Override
    public void setLabelFor(javax.swing.JLabel label)
    {
        if (label != null)
        {
            label.setLabelFor(ta_PropData);
        }
    }

    @Override
    public javax.swing.JComponent getComponent()
    {
        return this;
    }

    @Override
    public boolean copyData()
    {
        if (!ta_PropData.hasFocus())
        {
            return false;
        }

        ta_PropData.selectAll();
        Util.setClipboardContents(ta_PropData.getText());
        return true;
    }

    @Override
    public boolean saveData()
    {
        saveName();
        itemData.setData(ta_PropData.getText());
        return true;
    }

    @Override
    public void requestFocus()
    {
        ta_PropData.requestFocus();
    }
}
