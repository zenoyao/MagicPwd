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
package com.magicpwd.v.app;

import com.magicpwd.__i.IBackCall;
import com.magicpwd.__i.IHintView;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._util.Char;
import com.magicpwd._util.Date;
import com.magicpwd.m.HintMdl;
import com.magicpwd.m.UserMdl;

/**
 * 主窗口状态提示工具条
 * @author Amon
 */
public class HintBar extends javax.swing.JPanel implements IHintView
{

    private UserMdl userMdl;
    private IBackCall<String, java.util.List<MgtdHeader>> backCall;

    public HintBar(UserMdl userMdl)
    {
        this.userMdl = userMdl;
    }

    public void initView()
    {
        setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, getBackground().darker()));

        lb_HintLabel = new javax.swing.JLabel();

        lb_TimeLabel = new javax.swing.JLabel();
        lb_TimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addComponent(lb_HintLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, Short.MAX_VALUE);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addComponent(lb_TimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE);
        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup hpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        hpg.addComponent(lb_TimeLabel);
        hpg.addComponent(lb_HintLabel);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addGap(3);
        vsg.addGroup(hpg);
        vsg.addGap(3);
        layout.setVerticalGroup(vsg);
    }

    public void initLang()
    {
//        lb_InfoLabel.setText("请选择口令类别！");
        lb_HintLabel.setText("数据处理中……");
        lb_TimeLabel.setText("");
    }

    public void initData()
    {
        lb_HintLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                lbHintLabelMouseClicked(evt);
            }
        });
        userMdl.getHintMdl().registerHintView(this);
    }

    private void lbHintLabelMouseClicked(java.awt.event.MouseEvent evt)
    {
        if (backCall != null)
        {
            HintMdl hintMdl = userMdl.getHintMdl();
            java.util.List<MgtdHeader> hintList = new java.util.ArrayList<MgtdHeader>();
            hintList.addAll(hintMdl.getTodoList());
            hintList.addAll(hintMdl.getHistList());
            backCall.callBack("hint", hintList);
        }
    }

    public void showNote(boolean forced)
    {
        userMdl.getHintMdl().reload(forced);
    }

    /**
     * 显示日期信息
     */
    @Override
    public void showTime(java.util.Date date)
    {
        String text = Date.getLocaleDateFormat().format(date);
        lb_TimeLabel.setText(text);
        lb_TimeLabel.setToolTipText(text);
    }

    public void showInfo(String text)
    {
    }

    @Override
    public void showHint(int todoCnt, int histCnt)
    {
        StringBuilder buf = new StringBuilder();
        if (todoCnt > 0)
        {
            buf.append(Char.format("待处理：{0} ", Integer.toString(todoCnt)));
        }

        if (histCnt > 0)
        {
            buf.append(Char.format("已过期：{0} ", Integer.toString(histCnt)));
        }
        if (buf.length() > 0)
        {
            lb_HintLabel.setText(buf.toString());
            lb_HintLabel.setToolTipText("点击查看详细信息！");
            lb_HintLabel.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        }
        else
        {
            lb_HintLabel.setText("您目前没有需要提醒的数据！");
            lb_HintLabel.setToolTipText(null);
            lb_HintLabel.setCursor(java.awt.Cursor.getDefaultCursor());
        }
    }

    /**
     * @return the backCall
     */
    public IBackCall<String, java.util.List<MgtdHeader>> getBackCall()
    {
        return backCall;
    }

    /**
     * @param backCall the backCall to set
     */
    public void setBackCall(IBackCall<String, java.util.List<MgtdHeader>> backCall)
    {
        this.backCall = backCall;
    }
    private javax.swing.JLabel lb_HintLabel;
    private javax.swing.JLabel lb_TimeLabel;
}
