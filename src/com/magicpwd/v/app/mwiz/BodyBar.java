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
package com.magicpwd.v.app.mwiz;

import com.magicpwd.__a.AEditBean;
import com.magicpwd.__i.IEditItem;
import com.magicpwd.__i.mwiz.IMwizBean;
import com.magicpwd._bean.mwiz.AreaBean;
import com.magicpwd._bean.mwiz.DataBean;
import com.magicpwd._bean.mwiz.DateBean;
import com.magicpwd._bean.mwiz.FileBean;
import com.magicpwd._bean.mwiz.LinkBean;
import com.magicpwd._bean.mwiz.ListBean;
import com.magicpwd._bean.mwiz.MailBean;
import com.magicpwd._bean.mwiz.PwdsBean;
import com.magicpwd._bean.mwiz.TextBean;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._util.Bean;
import com.magicpwd.m.mwiz.KeysMdl;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-10-30 8:55:09
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class BodyBar extends javax.swing.JPanel
{

    private MwizPtn normPtn;
    private KeysMdl keysMdl;
    private int currStep;

    public BodyBar(MwizPtn normPtn, KeysMdl keysMdl)
    {
        this.normPtn = normPtn;
        this.keysMdl = keysMdl;
    }

    public int initView(int step)
    {
        currStep = step;

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        javax.swing.GroupLayout.ParallelGroup hpg0 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);

        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();

        int tmp = 0;
        int max = keysMdl.getItemSize();
        int row = ConsEnv.MWIZ_ROWS_MAX;
        javax.swing.JLabel label;
        AEditBean panel;
        javax.swing.GroupLayout.ParallelGroup vpg;
        while (step > -1 && step < max)
        {
            int type = keysMdl.getItemAt(step).getType();
            row -= (type == ConsDat.INDX_AREA || type == ConsDat.INDX_LIST) ? ConsEnv.MWIZ_AREA_ROW : 1;
            if (row < 0)
            {
                break;
            }
            step += 1;

            if (tmp == 0)
            {
                vsg.addContainerGap();
            }
            else
            {
                vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            }

            label = getLabel(tmp);
            panel = getPanel(tmp++, type);
            panel.initView();

            hpg0.addComponent(label);
            hpg1.addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE);

            vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
            vpg.addComponent(label);
            vpg.addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            vsg.addGroup(vpg);
        }

        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addGroup(hpg0);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg1);
        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        vsg.addContainerGap(0, Short.MAX_VALUE);
        layout.setVerticalGroup(vsg);

        return step;
    }

    public void initLang()
    {
        IEditItem item;
        javax.swing.JLabel label;
        String name;
        IMwizBean bean;
        for (int i = 0, j = currStep; i < ConsEnv.MWIZ_ROWS_MAX; i += 1)
        {
            label = lb_EditList[i];
            if (label == null)
            {
                break;
            }
            item = keysMdl.getItemAt(j++);
            name = item.getName();
            if (com.magicpwd._util.Char.isValidate(name) && name.startsWith(ConsDat.SP_TPL_LS) && name.endsWith(ConsDat.SP_TPL_RS))
            {
                name = name.substring(1, name.length() - 1);
            }
            Bean.setText(label, name + "(@" + (i + 1) + ')');

            bean = (IMwizBean) pl_EditList[i];
            bean.setLabelFor(label);
            bean.initLang();
        }
    }

    public void initData()
    {
        javax.swing.JLabel label;
        for (int i = 0, j = currStep; i < ConsEnv.MWIZ_ROWS_MAX; i += 1)
        {
            label = lb_EditList[i];
            if (label == null)
            {
                break;
            }
            ((IMwizBean) pl_EditList[i]).initData();
        }

        javax.swing.KeyStroke stroke = javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK);
        javax.swing.AbstractAction action = new javax.swing.AbstractAction()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                copyData();
            }
        };
        Bean.registerKeyStrokeAction(this, stroke, action, "alt-c", javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void showData()
    {
        AEditBean panel;
        for (int i = 0, j = currStep; i < ConsEnv.MWIZ_ROWS_MAX; i += 1)
        {
            panel = pl_EditList[i];
            if (panel == null)
            {
                break;
            }
            ((IMwizBean) panel).showData(keysMdl.getItemAt(j++));
        }
        requestFocus();
    }

    public boolean copyData()
    {
        AEditBean panel;
        for (int i = 0, j = currStep; i < ConsEnv.MWIZ_ROWS_MAX; i += 1)
        {
            panel = pl_EditList[i];
            if (panel == null)
            {
                break;
            }
            if (((IMwizBean) panel).copyData())
            {
                return true;
            }
        }
        return false;
    }

    public boolean saveData()
    {
        AEditBean panel;
        for (int i = 0, j = currStep; i < ConsEnv.MWIZ_ROWS_MAX; i += 1)
        {
            panel = pl_EditList[i];
            if (panel == null)
            {
                break;
            }
            if (!((IMwizBean) panel).saveData())
            {
                panel.requestFocus();
                return false;
            }
        }
        return true;
    }

    @Override
    public void requestFocus()
    {
        pl_EditList[0].requestFocus();
    }

    private javax.swing.JLabel getLabel(int index)
    {
        if (lb_EditList == null)
        {
            lb_EditList = new javax.swing.JLabel[ConsEnv.MWIZ_ROWS_MAX];
        }
        javax.swing.JLabel label = lb_EditList[index];
        if (label == null)
        {
            label = new javax.swing.JLabel();
            lb_EditList[index] = label;
        }
        return label;
    }

    private AEditBean getPanel(int index, int type)
    {
        if (pl_EditList == null)
        {
            pl_EditList = new AEditBean[ConsEnv.MWIZ_ROWS_MAX];
        }
        AEditBean panel = pl_EditList[index];
        if (panel == null)
        {
            switch (type)
            {
                case ConsDat.INDX_TEXT:
                    panel = new TextBean(normPtn);
                    break;
                case ConsDat.INDX_PWDS:
                    panel = new PwdsBean(normPtn);
                    break;
                case ConsDat.INDX_LINK:
                    panel = new LinkBean(normPtn);
                    break;
                case ConsDat.INDX_MAIL:
                    panel = new MailBean(normPtn);
                    break;
                case ConsDat.INDX_DATE:
                    panel = new DateBean(normPtn);
                    break;
                case ConsDat.INDX_AREA:
                    panel = new AreaBean(normPtn);
                    break;
                case ConsDat.INDX_FILE:
                    panel = new FileBean(normPtn);
                    break;
                case ConsDat.INDX_DATA:
                    panel = new DataBean(normPtn);
                    break;
                case ConsDat.INDX_LIST:
                    panel = new ListBean(normPtn);
                    break;
                default:
                    panel = null;
                    break;
            }
            pl_EditList[index] = panel;
        }
        return panel;
    }
    private javax.swing.JLabel[] lb_EditList;
    private AEditBean[] pl_EditList;
}
