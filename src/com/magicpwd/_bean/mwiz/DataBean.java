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
import com.magicpwd._bean.ADataBean;
import com.magicpwd._util.Util;
import com.magicpwd.v.app.mwiz.MwizPtn;

/**
 *
 * @author Amon
 */
public class DataBean extends ADataBean implements IMwizBean
{

    public DataBean(MwizPtn normPtn)
    {
        super(normPtn);
    }

    @Override
    public void initView()
    {
        initConfView();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addComponent(tf_PropData, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(pl_PropConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hsg));

        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER);
        vpg.addComponent(pl_PropConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg.addComponent(tf_PropData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        layout.setVerticalGroup(vpg);
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

        tf_PropData.setText(itemData.getData());

        showConfData();
    }

    @Override
    public void setLabelFor(javax.swing.JLabel label)
    {
        if (label != null)
        {
            label.setLabelFor(tf_PropData);
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
        if (!tf_PropData.hasFocus())
        {
            return false;
        }

        tf_PropData.selectAll();
        Util.setClipboardContents(tf_PropData.getText());
        return true;
    }

    @Override
    public boolean saveData()
    {
        saveName();
        return processData();
    }

    @Override
    public void requestFocus()
    {
        tf_PropData.requestFocus();
    }
}
