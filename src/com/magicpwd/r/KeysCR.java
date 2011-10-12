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
package com.magicpwd.r;

import com.magicpwd._comn.mpwd.MpwdHeader;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd.v.app.mpro.MproPtn;

/**
 *
 * @author Amon
 */
public class KeysCR extends javax.swing.JPanel implements javax.swing.ListCellRenderer
{

    private MproPtn mproPtn;
    private int style;

    public KeysCR(MproPtn mproPtn)
    {
        this.mproPtn = mproPtn;

        lb_Icon = new javax.swing.JLabel();
        lb_Text = new javax.swing.JLabel();
        lb_Major = new javax.swing.JLabel();
        lb_Label = new javax.swing.JLabel();
        lb_Other = new javax.swing.JLabel();

        setStyle(style);
    }

    public final void setStyle(int style)
    {
        if (24 == style)
        {
            style2();
        }
        else
        {
            style1();
            style = 16;
        }
        this.style = style;
    }

    public int getStyle()
    {
        return style;
    }

    private void style1()
    {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addComponent(lb_Icon);
        hsg.addComponent(lb_Text, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE);
        hsg.addComponent(lb_Other);
        hsg.addComponent(lb_Label);
        hsg.addComponent(lb_Major);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg.addComponent(lb_Icon);
        vpg.addComponent(lb_Text);
        vpg.addComponent(lb_Other);
        vpg.addComponent(lb_Label);
        vpg.addComponent(lb_Major);
        layout.setVerticalGroup(vpg);
    }

    private void style2()
    {
        lb_Icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(lb_Label);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(lb_Major);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(lb_Other);
        hsg1.addContainerGap(0, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lb_Text, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE);
        hpg1.addGroup(hsg1);
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addComponent(lb_Icon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addGroup(hpg1);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(hsg2));

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lb_Label);
        vpg1.addComponent(lb_Major);
        vpg1.addComponent(lb_Other);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addComponent(lb_Text);
        vsg1.addGap(0, 1, Short.MAX_VALUE);
        vsg1.addGroup(vpg1);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        vpg2.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vsg1);
        vpg2.addComponent(lb_Icon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE);
        layout.setVerticalGroup(vpg2);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.ListCR#getListCellRendererComponent(javax.swing.JList,
     *      java.lang.Object, int, boolean, boolean)
     */
    @Override
    public java.awt.Component getListCellRendererComponent(javax.swing.JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        // 前景及背景颜色设置
        if (isSelected)
        {
            this.setBackground(list.getSelectionBackground());
            lb_Text.setForeground(list.getSelectionForeground());
        }
        else
        {
            this.setBackground(list.getBackground());
            lb_Text.setForeground(list.getForeground());
        }

        // 可编辑状态设置
        setEnabled(list.isEnabled());

        // 文字属性设置
        lb_Text.setFont(list.getFont());

//        lb_Icon.setIcon(Util.getNone());

        // 口令列表专用
        if (value instanceof MpwdHeader)
        {
            MpwdHeader keys = (MpwdHeader) value;
            lb_Text.setText(keys.getP30F0109());
            setToolTipText(com.magicpwd._util.Char.isValidate(keys.getP30F010A()) ? keys.getP30F010A() : keys.getP30F0109());
            lb_Icon.setIcon(mproPtn.getDataIcon(keys.getP30F010C(), keys.getP30F010B(), style));
            String t = "keys-major" + (keys.getP30F0103() > 0 ? "+" : "") + keys.getP30F0103();
            lb_Major.setIcon(mproPtn.getFeelIcon(t, "var:" + t));
            t = "keys-label" + keys.getP30F0102();
            lb_Label.setIcon(mproPtn.getFeelIcon(t, "var:" + t));
            lb_Other.setIcon(Char.isValidateHash(keys.getP30F010E()) ? mproPtn.getFeelIcon("hint-time", "var:hint-time") : Bean.getNone());
        }
        // 其它
        else if (value != null)
        {
            lb_Text.setText(value.toString());
            lb_Label.setIcon(Bean.getNone());
            lb_Major.setIcon(Bean.getNone());
            lb_Other.setIcon(Bean.getNone());
        }

//        lb_Rest.setIcon(Util.getNone());

        return this;
    }
    private javax.swing.JLabel lb_Icon;
    private javax.swing.JLabel lb_Text;
    private javax.swing.JLabel lb_Major;
    private javax.swing.JLabel lb_Label;
    private javax.swing.JLabel lb_Other;
}
