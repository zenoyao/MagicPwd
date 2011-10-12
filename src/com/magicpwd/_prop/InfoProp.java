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
package com.magicpwd._prop;

import com.magicpwd.__i.IPropBean;
import com.magicpwd._comp.LnkLabel;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd.v.app.mpro.MproPtn;

/**
 *
 * @author Amon
 */
public class InfoProp extends javax.swing.JPanel implements IPropBean
{

    private MproPtn mainPtn;

    public InfoProp(MproPtn mainPtn)
    {
        this.mainPtn = mainPtn;
    }

    @Override
    public void initView()
    {
        lt_Soft = new javax.swing.JLabel();
        lc_Soft = new javax.swing.JLabel();
        lt_Vers = new javax.swing.JLabel();
        lc_Vers = new javax.swing.JLabel();
        lt_Plat = new javax.swing.JLabel();
        lc_Plat = new javax.swing.JLabel();
        lt_Site = new javax.swing.JLabel();
        lc_Site = new LnkLabel();
        lt_Copy = new javax.swing.JLabel();
        lc_Copy = new javax.swing.JLabel();
        javax.swing.JScrollPane sp = new javax.swing.JScrollPane();
        ta_Note = new javax.swing.JTextArea();

        lc_Site.setLinkUrl(ConsEnv.HOMEPAGE);
        lc_Site.setAutoOpenLink(true);

        ta_Note.setEditable(false);
        ta_Note.setLineWrap(true);
        ta_Note.setWrapStyleWord(true);
        sp.setViewportView(ta_Note);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(lt_Site);
        hpg1.addComponent(lt_Copy);
        hpg1.addComponent(lt_Vers);
        hpg1.addComponent(lt_Soft);
        javax.swing.GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg2.addComponent(lc_Soft);
        hpg2.addComponent(lc_Vers);
        hpg2.addComponent(lc_Site);
        hpg2.addComponent(lc_Copy);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addGroup(hpg1);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addGroup(hpg2);
        hsg1.addContainerGap(1, Short.MAX_VALUE);
        javax.swing.GroupLayout.ParallelGroup hpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg3.addGroup(hsg1);
        hpg3.addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE);
        layout.setHorizontalGroup(hpg3);

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(lt_Soft);
        vpg1.addComponent(lc_Soft);
        javax.swing.GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg2.addComponent(lt_Vers);
        vpg2.addComponent(lc_Vers);
        javax.swing.GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg3.addComponent(lt_Site);
        vpg3.addComponent(lc_Site);
        javax.swing.GroupLayout.ParallelGroup vpg4 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg4.addComponent(lt_Copy);
        vpg4.addComponent(lc_Copy);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addGroup(vpg1);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg2);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg3);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg4);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE);
        layout.setVerticalGroup(vsg1);
    }

    @Override
    public void initLang()
    {
        Lang.setWText(lt_Soft, LangRes.P30F83D1, "软件：");

        Lang.setWText(lt_Vers, LangRes.P30F83D2, "版本：");

        Lang.setWText(lt_Plat, LangRes.P30F83D3, "平台：");

        Lang.setWText(lt_Site, LangRes.P30F83D4, "首页：");

        Lang.setWText(lt_Copy, LangRes.P30F83D5, "版权：");
    }

    @Override
    public void initData()
    {
        lc_Soft.setText(ConsEnv.SOFTNAME);

        lc_Vers.setText(ConsEnv.VERSIONS + " Build " + ConsEnv.BUILDER);

        lc_Plat.setText(new StringBuilder(System.getProperty("os.name")).append(' ').append(System.getProperty("os.version")).append(' ').append(System.getProperty("os.arch")).toString());

        lc_Site.setText(ConsEnv.HOMEPAGE);

        lc_Copy.setText(Char.format(ConsEnv.SOFTCOPY, "" + java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)));

        ta_Note.setText(Lang.getLang(LangRes.P30F2F01, ""));
    }

    @Override
    public void showData()
    {
    }

    @Override
    public void saveData()
    {
    }

    @Override
    public javax.swing.JPanel getPanel()
    {
        return this;
    }
    private javax.swing.JLabel lc_Copy;
    private javax.swing.JLabel lc_Plat;
    private LnkLabel lc_Site;
    private javax.swing.JLabel lc_Soft;
    private javax.swing.JLabel lc_Vers;
    private javax.swing.JLabel lt_Copy;
    private javax.swing.JLabel lt_Plat;
    private javax.swing.JLabel lt_Site;
    private javax.swing.JLabel lt_Soft;
    private javax.swing.JLabel lt_Vers;
    private javax.swing.JTextArea ta_Note;
}
