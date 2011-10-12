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
package com.magicpwd._bean.mpro;

import com.magicpwd.__i.IBackCall;
import com.magicpwd.__i.IEditItem;
import com.magicpwd.__i.mpro.IMproBean;
import com.magicpwd._comp.WEditBox;
import com.magicpwd._comn.item.LogoItem;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Lang;
import com.magicpwd.v.app.mpro.MproPtn;
import com.magicpwd.x.app.icon.IcoDialog;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 * 在用户指定徽标的情况下，显示用户徽标，否则显示所属类别徽标。
 */
public class LogoBean extends javax.swing.JPanel implements IMproBean, IBackCall<String, String>
{

    private MproPtn mproPtn;
    private LogoItem itemData;
    private WEditBox dataEdit;

    public LogoBean(MproPtn mproPtn)
    {
        this.mproPtn = mproPtn;
    }

    @Override
    public void initView()
    {
        dataEdit = new WEditBox(mproPtn, this, false);
        dataEdit.initView();
        dataEdit.setCopyButtonVisible(false);
        dataEdit.setDropButtonVisible(false);

        lb_PropName = new javax.swing.JLabel();
        ib_PropName = new javax.swing.JLabel();
        //ib_PropName.setIcon(Bean.getNone());
        ib_PropName.setOpaque(true);
        ib_PropName.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ib_PropName.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.background"));
        ib_PropName.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        lb_PropName.setLabelFor(ib_PropName);

        lb_PropData = new javax.swing.JLabel();
        ta_PropData = new javax.swing.JTextArea();
        lb_PropData.setLabelFor(ta_PropData);
        ta_PropData.setLineWrap(true);
        ta_PropData.setRows(3);

        javax.swing.JScrollPane sp_PropData = new javax.swing.JScrollPane(ta_PropData);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lb_PropName);
        hpg1.addComponent(lb_PropData);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(ib_PropName, 21, 21, 21);
        hpg2.addComponent(sp_PropData, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addGroup(hpg1);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg2);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(dataEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER);
        vpg1.addComponent(lb_PropName);
        vpg1.addComponent(ib_PropName, 21, 21, 21);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addComponent(lb_PropData);
        vsg1.addContainerGap(49, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg2.addGroup(vsg1);
        vpg2.addComponent(sp_PropData, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup vsg2 = layout.createSequentialGroup();
        vsg2.addGroup(vpg1);
        vsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg2.addGroup(vpg2);
        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg.addGroup(vsg2);
        vpg.addComponent(dataEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        layout.setVerticalGroup(vpg);
    }

    @Override
    public void initLang()
    {
        dataEdit.initLang();

        Lang.setWText(lb_PropName, LangRes.P30F131D, "徽标(@P)");

        Lang.setWText(lb_PropData, LangRes.P30F131E, "名称(@N)");

        Lang.setWText(ib_PropName, LangRes.P30F131F, "@O");
        Lang.setWTips(ib_PropName, LangRes.P30F1320, "点击选择徽标(Alt + O)");
    }

    @Override
    public void initData()
    {
        ib_PropName.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                ib_PropDataActionPerformed(evt);
            }
        });
    }

    @Override
    public void showData(IEditItem item)
    {
        itemData = (LogoItem) item;

        if (itemData.getPath() != null && itemData.getPath().length() > 0)
        {
            ib_PropName.setIcon(mproPtn.getDataIcon(itemData.getPath(), item.getName(), 16));
        }
        ta_PropData.setText(item.getData());
    }

    @Override
    public void saveDataActionPerformed(java.awt.event.ActionEvent evt)
    {
//        String name = tf_PropName.getText();
//        if (com.magicpwd._util.Char.isValidateHash(itemData.getData()) && !com.magicpwd._util.Char.isValidate(name))
//        {
//            Lang.showMesg(MagicPwd.getCurrForm(), LangRes.P30F7A39, "请输入徽标名称！");
//            return;
//        }
        itemData.setData(ta_PropData.getText());
        mproPtn.updateSelectedItem();
    }

    @Override
    public void copyDataActionPerformed(java.awt.event.ActionEvent evt)
    {
    }

    @Override
    public void dropDataActionPerformed(java.awt.event.ActionEvent evt)
    {
    }

    @Override
    public boolean callBack(String options, String object)
    {
        if (IBackCall.OPTIONS_ABORT.equalsIgnoreCase(options))
        {
            return false;
        }

        if ("0".equals(options))
        {
            ib_PropName.setIcon(Bean.getNone());
            itemData.setName(options);
            itemData.setPath("");
            return true;
        }

        if (!com.magicpwd._util.Char.isValidateHash(options))
        {
            return false;
        }
        ib_PropName.setIcon(mproPtn.getDataIcon(object, options, 16));
        itemData.setName(options);
        itemData.setPath(object);
        return true;
    }

    @Override
    public void requestFocus()
    {
        ta_PropData.requestFocus();
    }

    private void ib_PropDataActionPerformed(java.awt.event.MouseEvent evt)
    {
        IcoDialog ico = new IcoDialog(mproPtn, mproPtn.getUserMdl(), this);
        ico.initView();
        ico.initLang();
        ico.initData();
        ico.showData("", itemData.getName(), 16);
        ico.setVisible(true);
    }
    private javax.swing.JLabel lb_PropName;
    private javax.swing.JLabel ib_PropName;
    private javax.swing.JLabel lb_PropData;
    private javax.swing.JTextArea ta_PropData;
}
