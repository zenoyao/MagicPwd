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
package com.magicpwd.x.app.mail;

import com.magicpwd._comn.I1S2;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Lang;
import java.util.List;

/**
 *
 * @author Amon
 */
public class MailOpt extends javax.swing.JPanel
{

    public MailOpt()
    {
    }

    public void initView()
    {
        lb_PropMail = new javax.swing.JLabel();
        cb_PropMail = new javax.swing.JComboBox();
        lb_PropMail.setLabelFor(cb_PropMail);

        lb_PropName = new javax.swing.JLabel();
        cb_PropUser = new javax.swing.JComboBox();
        lb_PropName.setLabelFor(cb_PropUser);

        lb_PropPwds = new javax.swing.JLabel();
        cb_PropPwds = new javax.swing.JComboBox();
        lb_PropPwds.setLabelFor(cb_PropPwds);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lb_PropMail);
        hpg1.addComponent(lb_PropName);
        hpg1.addComponent(lb_PropPwds);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(cb_PropMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(cb_PropUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg2.addComponent(cb_PropPwds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addGroup(hpg1);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg2);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lb_PropMail);
        vpg1.addComponent(cb_PropMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lb_PropName);
        vpg2.addComponent(cb_PropUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg3.addComponent(lb_PropPwds);
        vpg3.addComponent(cb_PropPwds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addGroup(vpg1);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg2);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg3);
        layout.setVerticalGroup(vsg);
    }

    public void initLang()
    {
        Lang.setWText(lb_PropMail, LangRes.P30F131A, "电子邮件");
        Lang.setWText(lb_PropName, LangRes.P30F131B, "登录用户");
        Lang.setWText(lb_PropPwds, LangRes.P30F131C, "认证口令");
    }

    public void initMail(List<I1S2> mail)
    {
        for (I1S2 temp : mail)
        {
            cb_PropMail.addItem(temp);
        }
    }

    public void initUser(List<I1S2> user)
    {
        for (I1S2 temp : user)
        {
            cb_PropUser.addItem(temp);
        }
    }

    public void initPwds(List<I1S2> pwds)
    {
        for (I1S2 temp : pwds)
        {
            cb_PropPwds.addItem(temp);
        }
    }

    public int getMail()
    {
        return cb_PropMail.getSelectedIndex();
    }

    public int getUser()
    {
        return cb_PropUser.getSelectedIndex();
    }

    public int getPwds()
    {
        return cb_PropPwds.getSelectedIndex();
    }
    private javax.swing.JLabel lb_PropMail;
    private javax.swing.JLabel lb_PropName;
    private javax.swing.JLabel lb_PropPwds;
    private javax.swing.JComboBox cb_PropMail;
    private javax.swing.JComboBox cb_PropUser;
    private javax.swing.JComboBox cb_PropPwds;
}
