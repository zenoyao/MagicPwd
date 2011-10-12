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
package com.magicpwd._comp;

import com.magicpwd._comn.S1S1;

/**
 * @author Amon
 */
public final class ScreenKB extends javax.swing.JPanel
{

    private int currPage;
    private int currCase;
    private int dim = 21;
    private int gap = 1;

    public ScreenKB()
    {
    }

    public void initView()
    {
        lb_CharButn = new BtnLabel[30];
        for (int i = 0, j = lb_CharButn.length; i < j; i += 1)
        {
            lb_CharButn[i] = new BtnLabel();
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        javax.swing.GroupLayout.ParallelGroup hpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        javax.swing.GroupLayout.SequentialGroup hsg;
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        javax.swing.GroupLayout.ParallelGroup vpg;

        int max = dim << 1;
        int row = 0;
        int tmp = 0;
        while (row < 3)
        {
            int col = 0;
            hsg = layout.createSequentialGroup();
            hsg.addGap(gap);
            vpg = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
            while (col < 9)
            {
                hsg.addComponent(lb_CharButn[tmp], dim, dim, dim);
                hsg.addGap(gap);

                vpg.addComponent(lb_CharButn[tmp], dim, dim, dim);
                col += 1;
                tmp += 1;
            }
            hsg.addComponent(lb_CharButn[27 + row], max, max, max);
            hpg.addGroup(hsg);

            vpg.addComponent(lb_CharButn[27 + row], dim, dim, dim);
            vsg.addGap(gap);
            vsg.addGroup(vpg);

            row += 1;
        }
        vsg.addGap(gap);

        layout.setHorizontalGroup(hpg);
        layout.setVerticalGroup(vsg);
    }

    public void initLang()
    {
        lbNextPage = lb_CharButn[26];
        lbNextPage.setText("#");

        lbBackBtn = lb_CharButn[27];
        lbBackBtn.setText("Back");

        lbCapsBtn = lb_CharButn[28];
        lbCapsBtn.setText("Caps");

        lbShiftBtn = lb_CharButn[29];
        lbShiftBtn.setText("Shift");

        nextK(getKeyA(), lb_CharButn);
    }

    public void initData()
    {
        // 换页键
        lbNextPage.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                nextK(currPage == 0 ? getKeyB() : getKeyA(), lb_CharButn);
                currPage = 1 - currPage;
            }
        });

        // 退格键
        lbBackBtn.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
            }
        });

        // 大写键
        lbCapsBtn.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                nextV(currCase == 0 ? getKeyA() : getKeyB(), lb_CharButn);
                currCase = 1 - currCase;
            }
        });

        // 上档键
        lbShiftBtn.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
            }
        });
    }

    private void nextK(S1S1[] cvs, BtnLabel[] lbs)
    {
        for (int i = 0; i < 26; i += 1)
        {
            lbs[i].setText(cvs[i].getK());
        }
    }

    private void nextV(S1S1[] cvs, BtnLabel[] lbs)
    {
        for (int i = 0, j = cvs.length; i < j; i += 1)
        {
            lbs[i].setText(cvs[i].getV());
        }
    }

    private S1S1[] getKeyA()
    {
        int i = 0;
        S1S1[] cv1 = new S1S1[26];
        cv1[i++] = new S1S1("a", "A");
        cv1[i++] = new S1S1("b", "B");
        cv1[i++] = new S1S1("c", "C");
        cv1[i++] = new S1S1("d", "D");
        cv1[i++] = new S1S1("e", "E");
        cv1[i++] = new S1S1("f", "F");
        cv1[i++] = new S1S1("g", "G");
        cv1[i++] = new S1S1("h", "H");
        cv1[i++] = new S1S1("i", "I");
        cv1[i++] = new S1S1("j", "J");
        cv1[i++] = new S1S1("k", "K");
        cv1[i++] = new S1S1("l", "L");
        cv1[i++] = new S1S1("m", "M");
        cv1[i++] = new S1S1("n", "N");
        cv1[i++] = new S1S1("o", "O");
        cv1[i++] = new S1S1("p", "P");
        cv1[i++] = new S1S1("q", "Q");
        cv1[i++] = new S1S1("r", "R");
        cv1[i++] = new S1S1("s", "S");
        cv1[i++] = new S1S1("t", "T");
        cv1[i++] = new S1S1("u", "U");
        cv1[i++] = new S1S1("v", "V");
        cv1[i++] = new S1S1("w", "W");
        cv1[i++] = new S1S1("x", "X");
        cv1[i++] = new S1S1("y", "Y");
        cv1[i++] = new S1S1("z", "Z");
        return cv1;
    }

    private S1S1[] getKeyB()
    {
        int i = 0;
        S1S1[] cv1 = new S1S1[26];
        cv1[i++] = new S1S1("`", "~");
        cv1[i++] = new S1S1("1", "!");
        cv1[i++] = new S1S1("2", "@");
        cv1[i++] = new S1S1("3", "#");
        cv1[i++] = new S1S1("4", "$");
        cv1[i++] = new S1S1("5", "%");
        cv1[i++] = new S1S1("6", "^");
        cv1[i++] = new S1S1("7", "&");
        cv1[i++] = new S1S1("8", "*");
        cv1[i++] = new S1S1("9", "(");
        cv1[i++] = new S1S1("0", ")");
        cv1[i++] = new S1S1("-", "_");
        cv1[i++] = new S1S1("=", "+");
        cv1[i++] = new S1S1("\\", "|");
        cv1[i++] = new S1S1("[", "{");
        cv1[i++] = new S1S1("]", "}");
        cv1[i++] = new S1S1(";", ":");
        cv1[i++] = new S1S1("'", "\"");
        cv1[i++] = new S1S1(",", "<");
        cv1[i++] = new S1S1(".", ">");
        cv1[i++] = new S1S1("/", "?");
        cv1[i++] = new S1S1(" ", " ");
        cv1[i++] = new S1S1("；", "：");
        cv1[i++] = new S1S1("，", "？");
        cv1[i++] = new S1S1("。", "！");
        cv1[i++] = new S1S1("、", "￥");
        return cv1;
    }
    private BtnLabel[] lb_CharButn;
    private BtnLabel lbBackBtn;
    private BtnLabel lbCapsBtn;
    private BtnLabel lbShiftBtn;
    private BtnLabel lbNextPage;
}
