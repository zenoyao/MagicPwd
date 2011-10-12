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
package com.magicpwd._bean;

import com.magicpwd.__a.AEditBean;
import com.magicpwd.__a.AMpwdPtn;
import com.magicpwd._comn.item.DateItem;
import com.magicpwd._comp.BtnLabel;
import com.magicpwd._comp.WButtonGroup;
import com.magicpwd._comp.WTextBox;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd._util.Date;
import com.magicpwd._util.Lang;

/**
 *
 * @author Amon
 */
public abstract class ADateBean extends AEditBean
{

    private WTextBox dataBox;
    private java.text.DateFormat format;

    public ADateBean(AMpwdPtn mpwdPtn)
    {
        this.formPtn = mpwdPtn;
    }

    protected void initConfView()
    {
        tf_PropData = new javax.swing.JTextField();

        dataBox = new WTextBox(tf_PropData, true);
        dataBox.initView();

        pl_PropConf = new javax.swing.JPanel();
        pl_PropConf.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 0));

        bt_DateTime = new BtnLabel();
        bt_DateTime.setIcon(formPtn.getFeelIcon(null, "var:date-time"));
        bt_DateTime.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_DateViewActionPerformed(evt);
            }
        });
        pl_PropConf.add(bt_DateTime);

        bt_DateConf = new BtnLabel();
        bt_DateConf.setIcon(formPtn.getFeelIcon(null, "var:date-options"));
        bt_DateConf.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_DateConfActionPerformed(evt);
            }
        });
        pl_PropConf.add(bt_DateConf);

        pm_MenuTime = new javax.swing.JPopupMenu();
        mi_TimeDef = new javax.swing.JMenuItem();
        mi_TimeDef.setActionCommand("now:");
        pm_MenuTime.add(mi_TimeDef);
        pm_MenuTime.addSeparator();

        pm_MenuConf = new javax.swing.JPopupMenu();
        mi_ConfDef = new javax.swing.JCheckBoxMenuItem();
        pm_MenuConf.add(mi_ConfDef);
        pm_MenuConf.addSeparator();
    }

    protected void initConfLang()
    {
        Lang.setWText(bt_DateTime, LangRes.P30F1513, "@T");
        Lang.setWTips(bt_DateTime, LangRes.P30F1514, "选择时间(Alt + T)");

        Lang.setWText(bt_DateConf, LangRes.P30F151F, "@O");
        Lang.setWTips(bt_DateConf, LangRes.P30F1520, "当前时间(Alt + O)");

        Bean.setText(mi_TimeDef, "当前时间");
        Bean.setText(mi_ConfDef, "默认格式");

        dataBox.initLang();
    }

    protected void initConfData()
    {
        java.awt.event.ActionListener diAction = new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                mi_DateTimeActionPerformed(e);
            }
        };
        mi_TimeDef.addActionListener(diAction);
        formPtn.getMenuPtn().getSubMenu("date-interval", pm_MenuTime, diAction);

        java.awt.event.ActionListener dtAction = new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                mi_DateTpltActionPerformed(e);
            }
        };
        formPtn.getMenuPtn().getSubMenu("date-template", pm_MenuConf, dtAction);
        formPtn.getMenuPtn().getGroup("date-template").add(mi_ConfDef);

        dataBox.initData();
    }

    protected void showConfData()
    {
        WButtonGroup group = formPtn.getMenuPtn().getGroup("date-template");
        boolean b = false;
        if (group != null)
        {
            b = group.setSelected(itemData.getSpec(DateItem.SPEC_FORMAT), true);
        }
        mi_ConfDef.setSelected(!b);
    }

    private void bt_DateViewActionPerformed(java.awt.event.ActionEvent evt)
    {
        pm_MenuTime.show(bt_DateTime, 0, bt_DateTime.getSize().height);
    }

    private void bt_DateConfActionPerformed(java.awt.event.ActionEvent evt)
    {
        pm_MenuConf.show(bt_DateConf, 0, bt_DateConf.getSize().height);
    }

    private void mi_DateTimeActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (format == null)
        {
            format = getDateFormat(itemData.getSpec(DateItem.SPEC_FORMAT));
        }
        tf_PropData.setText(format.format(Date.toDate(evt.getActionCommand()).getTime()));
    }

    private void mi_DateTpltActionPerformed(java.awt.event.ActionEvent evt)
    {
        String cmd = evt.getActionCommand();
        if (!Char.isValidate(cmd))
        {
            return;
        }
        itemData.setSpec(DateItem.SPEC_FORMAT, cmd);
        format = getDateFormat(cmd);
    }

    static java.text.DateFormat getDateFormat(String t)
    {
        java.text.DateFormat format;
        if (!Char.isValidate(t))
        {
            format = java.text.DateFormat.getDateTimeInstance();
        }
        if (t.startsWith("java:"))
        {
            java.util.regex.Matcher dm = java.util.regex.Pattern.compile("\\d+d").matcher(t);
            String date = dm.find() ? dm.group().replace("d", "") : null;
            java.util.regex.Matcher tm = java.util.regex.Pattern.compile("\\d+t").matcher(t);
            String time = tm.find() ? tm.group().replace("t", "") : null;
            if (date != null && time != null)
            {
                format = java.text.DateFormat.getDateTimeInstance(Integer.parseInt(date), Integer.parseInt(time));
                return format;
            }
            if (date != null)
            {
                format = java.text.DateFormat.getDateInstance(Integer.parseInt(date));
                return format;
            }
            if (time != null)
            {
                format = java.text.DateFormat.getTimeInstance(Integer.parseInt(time));
                return format;
            }

            format = java.text.DateFormat.getDateTimeInstance();
            return format;
        }

        if (t.startsWith("user:"))
        {
            format = new java.text.SimpleDateFormat(t.substring(5));
            return format;
        }

        format = java.text.DateFormat.getDateTimeInstance();
        return format;
    }
    protected javax.swing.JTextField tf_PropData;
    protected javax.swing.JPanel pl_PropConf;
    protected javax.swing.JCheckBoxMenuItem mi_ConfDef;
    private BtnLabel bt_DateTime;
    private BtnLabel bt_DateConf;
    private javax.swing.JPopupMenu pm_MenuTime;
    private javax.swing.JPopupMenu pm_MenuConf;
    private javax.swing.JMenuItem mi_TimeDef;
}
