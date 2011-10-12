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
package com.magicpwd.v.app.mruc;

import com.magicpwd.__i.IEditItem;
import com.magicpwd._comn.item.DataItem;
import com.magicpwd._comn.item.SignItem;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.m.maoc.WComputer;
import com.magicpwd.m.mruc.UnitMdl;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author Amon
 */
public class BodyPtn extends javax.swing.JPanel
{

    private MrucPtn mrucPtn;
    private UnitMdl unitMdl;
    private int currStep;
    private int lastStep;
    private String formula;
    private static WComputer computer;

    public BodyPtn(MrucPtn mrucPtn, UnitMdl unitMdl)
    {
        this.mrucPtn = mrucPtn;
        this.unitMdl = unitMdl;
        computer = new WComputer();
    }

    public int initView(int step)
    {
        ls_NameList = new java.util.ArrayList<javax.swing.JLabel>();
        ls_DataList = new java.util.ArrayList<javax.swing.JTextField>();

        IEditItem editItem = unitMdl.getItemAt(step);
        if (editItem.getType() != ConsDat.INDX_SIGN)
        {
            return step + 1;
        }

        formula = editItem.getData();
        currStep = step;

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        javax.swing.JLabel label;
        javax.swing.JTextField field;

        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);

        step += 1;
        int j = unitMdl.getItemSize();
        int cnt = 0;
        while (step < j)
        {
            editItem = unitMdl.getItemAt(step);
            if (editItem.getType() == ConsDat.INDX_SIGN)
            {
                break;
            }
            step += 1;
            if (editItem.getType() != ConsDat.INDX_DATA)
            {
                ls_NameList.add(null);
                ls_DataList.add(null);
                continue;
            }

            label = new javax.swing.JLabel();
            ls_NameList.add(label);
            hpg1.addComponent(label, javax.swing.GroupLayout.Alignment.TRAILING);
            field = new javax.swing.JTextField(16);
            ls_DataList.add(field);
            hpg2.addComponent(field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            cnt += 1;
        }
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addGroup(hpg1);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg2);
        hsg.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(hsg));

        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        javax.swing.GroupLayout.ParallelGroup vpg;

        int i = 0;
        j = 0;
        cnt -= 1;
        while (j < cnt)
        {
            if (ls_NameList.get(i) != null)
            {
                vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
                vpg.addComponent(ls_NameList.get(i));
                vpg.addComponent(ls_DataList.get(i), javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
                vsg.addGroup(vpg);
                vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
                j += 1;
            }
            i += 1;
        }
        cnt += 1;
        while (j < cnt)
        {
            if (ls_NameList.get(i) != null)
            {
                vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
                vpg.addComponent(ls_NameList.get(i));
                vpg.addComponent(ls_DataList.get(i), javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
                vsg.addGroup(vpg);
                vsg.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
                break;
            }
            i += 1;
        }
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(vsg));

        return step;
    }

    public void initLang()
    {
        if (ls_NameList.size() < 1 || currStep < ConsEnv.PWDS_HEAD_SIZE)
        {
            return;
        }

        int step = currStep;
        IEditItem editItem = unitMdl.getItemAt(step++);
        if (editItem.getType() == ConsDat.INDX_SIGN && "div".equals(editItem.getSpec(SignItem.SPEC_SIGN_TYPE)))
        {
            setBorder(javax.swing.BorderFactory.createTitledBorder(editItem.getName()));
        }

        for (int i = 0, j = ls_NameList.size(); i < j; i += 1)
        {
            if (ls_NameList.get(i) == null)
            {
                continue;
            }
            editItem = unitMdl.getItemAt(step + i);
            Bean.setText(ls_NameList.get(i), editItem.getName());
        }
    }

    public void initData()
    {
        lastStep = -1;
        if (ls_DataList.size() < 1 || currStep < ConsEnv.PWDS_HEAD_SIZE)
        {
            return;
        }

        java.awt.event.ActionListener listener = new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                tf_FieldActionPerformed(evt);
            }
        };
        javax.swing.JTextField field;
        for (int i = 0, j = ls_DataList.size(); i < j; i += 1)
        {
            field = ls_DataList.get(i);
            if (field == null)
            {
                continue;
            }
            field.setActionCommand(Integer.toString(i));
            field.addActionListener(listener);
        }
    }

    public void showData(String input)
    {
        if (!Char.isValidate(input))
        {
            return;
        }

        BigDecimal temp;
        IEditItem item;
        javax.swing.JTextField field;
        int step = currStep + 1;
        for (int i = 0, j = ls_DataList.size(); i < j; i += 1)
        {
            field = ls_DataList.get(i);
            if (field == null)
            {
                continue;
            }
            if (i == lastStep)
            {
                lastStep = -1;
                continue;
            }
            item = unitMdl.getItemAt(step + i);
            if (item.getType() != ConsDat.INDX_DATA)
            {
                continue;
            }

            try
            {
                temp = computer.calculate(input.replace("$ratio", item.getData()), new MathContext(Integer.parseInt(item.getSpec(DataItem.SPEC_DOT_DEC))));
                field.setText(temp.toPlainString());
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
            }
        }
    }

    private void tf_FieldActionPerformed(java.awt.event.ActionEvent e)
    {
        String cmd = e.getActionCommand();
        if (!Char.isValidateInteger(cmd))
        {
            return;
        }
        lastStep = Integer.parseInt(cmd);

        Object object = e.getSource();
        if (!(object instanceof javax.swing.JTextField))
        {
            return;
        }

        javax.swing.JTextField field = (javax.swing.JTextField) object;
        String val = field.getText().replaceAll("\\s+", "");
        if (val.length() < 1 || !Char.isValidatePositiveDecimal(val))
        {
            Lang.showMesg(mrucPtn, LangRes.P30FBA01, "请输入一个非负数值！");
            field.requestFocus();
            return;
        }

        IEditItem item = unitMdl.getItemAt(currStep + 1 + lastStep);
        if (item.getType() != ConsDat.INDX_DATA)
        {
            return;
        }

        mrucPtn.compute(formula.replace("$input", val).replace("$scale", item.getData()));
    }
    private java.util.ArrayList<javax.swing.JLabel> ls_NameList;
    private java.util.ArrayList<javax.swing.JTextField> ls_DataList;
}
