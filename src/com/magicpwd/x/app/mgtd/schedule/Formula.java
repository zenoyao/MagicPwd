/*
 *  Copyright (C) 2011 Aven
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
package com.magicpwd.x.app.mgtd.schedule;

import com.magicpwd.__i.mgtd.IMgtdBean;
import com.magicpwd._comn.mpwd.MgtdDetail;
import com.magicpwd._comn.mpwd.MgtdHeader;
import com.magicpwd._cons.ConsDat;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd.x.app.mgtd.MgtdDlg;
import org.javia.arity.Symbols;

/**
 * Application: MagicPwd
 * Author     : Aven
 * Encoding   : UTF-8
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class Formula extends javax.swing.JPanel implements IMgtdBean
{

    private MgtdDlg mgtdDlg;

    public Formula(MgtdDlg mgtdDlg)
    {
        this.mgtdDlg = mgtdDlg;
    }

    @Override
    public void initView()
    {
        lbFormula = new javax.swing.JLabel();
        tfFormula = new javax.swing.JTextField();
        ltFormula = new javax.swing.JLabel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(ltFormula, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE);
        hpg1.addComponent(tfFormula, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
//        hsg.addContainerGap();
        hsg.addComponent(lbFormula);
        hsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg.addGroup(hpg1);
//        hsg.addContainerGap();
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lbFormula);
        vpg1.addComponent(tfFormula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
//        vsg.addContainerGap();
        vsg.addGroup(vpg1);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addComponent(ltFormula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
//        vsg.addContainerGap();
        layout.setVerticalGroup(vsg);
    }

    @Override
    public void initLang()
    {
        lbFormula.setText("执行公式");
        ltFormula.setText("<html>【提醒条件】<br>表达式执行结果为0。<br>【可用参数】<br>年：nian/n/year<br>月：yue/y/month<br>日：ri/r/day<br>时：shi/s/hour<br>分：fen/f/minute<br>秒：miao/m/second<br>周：zhou/z/week</html>");
    }

    @Override
    public int getKey()
    {
        return ConsDat.MGTD_INTVAL_FORMULA;
    }

    @Override
    public String getName()
    {
        return "formula";
    }

    @Override
    public String getTitle()
    {
        return "公式提醒";
    }

    @Override
    public boolean showData(MgtdHeader mgtd)
    {
        MgtdDetail hint = mgtd.getHint(0);
        if (hint != null)
        {
            tfFormula.setText(hint.getP30F0406());
            return true;
        }
        return false;
    }

    @Override
    public boolean saveData(MgtdHeader mgtd)
    {
        mgtd.setP30F030D(0L);
        mgtd.setP30F030E(0L);
        mgtd.setP30F030F(0L);

        String text = tfFormula.getText();
        if (!Char.isValidate(text))
        {
            Lang.showMesg(mgtdDlg, null, "请输入计算公式！");
            tfFormula.requestFocus();
            return false;
        }
        text = text.replaceAll("\\s+", "");
        String temp = text.replaceAll("nian|n|year|yue|y|month|ri|r|day|zhou|z|week|shi|s|hour|fen|f|minute|miao|m|second", "1");
        if (!java.util.regex.Pattern.matches("^[-+*/%0-9]+$", temp))
        {
            Lang.showMesg(mgtdDlg, null, "您输入的公式含有非法字符！");
            tfFormula.requestFocus();
            return false;
        }
        try
        {
            new Symbols().eval(temp);
        }
        catch (Exception exp)
        {
            Lang.showMesg(mgtdDlg, null, exp.toString());
            tfFormula.requestFocus();
            return false;
        }

        java.util.List<MgtdDetail> list = new java.util.ArrayList<MgtdDetail>();

        MgtdDetail hint = new MgtdDetail();
        hint.setP30F0403(System.currentTimeMillis());
        hint.setP30F0404(0);
        hint.setP30F0405(0);
        hint.setP30F0406(text);
        list.add(hint);

        mgtd.setHintList(list);
        return true;
    }
    private javax.swing.JLabel lbFormula;
    private javax.swing.JLabel ltFormula;
    private javax.swing.JTextField tfFormula;
}
