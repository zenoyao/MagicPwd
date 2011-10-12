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

import com.magicpwd.__a.ADialog;
import com.magicpwd._bean.mwiz.GuidBean;
import com.magicpwd._bean.mwiz.HeadBean;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd.m.mwiz.KeysMdl;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-10-24 22:46:25
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class EditPtn extends ADialog
{

    private int currStep;
    private boolean canEdit;
    private KeysMdl keysMdl;
    private MwizPtn mwizPtn;
    private GuidBean guidBean;
    private HeadBean headBean;

    public EditPtn(MwizPtn mwizPtn)
    {
        super(mwizPtn, true);
        this.mwizPtn = mwizPtn;
        ls_BodyList = new java.util.ArrayList<BodyBar>(5);
    }

    public void initView()
    {
        initNoteView();
        initBaseView();

        guidBean = new GuidBean(mwizPtn);
        guidBean.initView();
        pl_EditArea.add("guid", guidBean);

        headBean = new HeadBean(mwizPtn, this);
        headBean.initView();
        pl_EditArea.add("head", headBean);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//        setIconImage(Bean.getLogo(16));
        setResizable(false);
    }

    public void initLang()
    {
        guidBean.initLang();
        headBean.initLang();

        Bean.setText(bt_PrevStep, Lang.getLang(LangRes.P30F6501, "上一步(@P)"));
        Bean.setText(bt_NextStep, Lang.getLang(LangRes.P30F6502, "下一步(@N)"));
        Bean.setText(bt_Update, Lang.getLang(LangRes.P30F6503, "保存(@S)"));
        Bean.setText(bt_Cancel, Lang.getLang(LangRes.P30F6504, "取消(@D)"));
    }

    public void initData()
    {
        guidBean.initData();
        headBean.initData();

        bt_Update.setVisible(false);
        bt_PrevStep.setVisible(false);

        processEscape();
    }

    public final void showData(KeysMdl keysMdl, boolean canEdit)
    {
        currStep = -2;
        this.keysMdl = keysMdl;
        this.canEdit = canEdit;
        initGuidView();

        pl_NoteArea.setVisible(canEdit);
        pack();
        if (!isVisible())
        {
            Bean.centerForm(this, mwizPtn);
            setVisible(true);
        }
    }

    private void initBody()
    {
        int indx = 0;
        int step = ConsEnv.PWDS_HEAD_SIZE;

        if (keysMdl.getItemSize() <= ConsEnv.PWDS_HEAD_SIZE)
        {
            keysMdl.initBody();
        }
        ls_BodyList.clear();
//            pl_EditArea.removeAll();
//            pl_EditArea.add("guid", guidBean);
//            pl_EditArea.add("head", headBean);

        while (step < keysMdl.getItemSize())
        {
            BodyBar bar = new BodyBar(mwizPtn, keysMdl);
            step = bar.initView(step);
            bar.initLang();
            bar.initData();
            pl_EditArea.add("body" + (indx++), bar);
            ls_BodyList.add(bar);
        }
    }

    private void initNoteView()
    {
        pl_NoteArea = new javax.swing.JPanel();
        ta_NoteArea = new javax.swing.JTextArea();

//        ta_NoteArea.setEditable(false);
        ta_NoteArea.setEnabled(false);
        ta_NoteArea.setFocusable(false);
        ta_NoteArea.setLineWrap(true);
        ta_NoteArea.setRows(3);
//        ta_NoteArea.setForeground(java.awt.Color.darkGray);
        javax.swing.JScrollPane jsp = new javax.swing.JScrollPane(ta_NoteArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(pl_NoteArea);
        pl_NoteArea.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addContainerGap();
        hsg1.addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE);
        hsg1.addContainerGap();
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(hsg1));

        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
//        vsg1.addContainerGap();
        vsg1.addComponent(jsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
//        vsg1.addContainerGap();
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(vsg1));
    }

    private void initBaseView()
    {
        pl_EditArea = new javax.swing.JPanel();
        bt_Cancel = new javax.swing.JButton();
        bt_Update = new javax.swing.JButton();
        bt_NextStep = new javax.swing.JButton();
        bt_PrevStep = new javax.swing.JButton();

        cl_Layout = new java.awt.CardLayout();
        pl_EditArea.setLayout(cl_Layout);

        bt_Cancel.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_CancelActionPerformed(evt);
            }
        });

        bt_Update.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_UpdateActionPerformed(evt);
            }
        });

        bt_NextStep.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_NextStepActionPerformed(evt);
            }
        });

        bt_PrevStep.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bt_PrevStepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        hpg1.addComponent(pl_NoteArea, javax.swing.GroupLayout.DEFAULT_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE);
        hpg1.addComponent(pl_EditArea, javax.swing.GroupLayout.DEFAULT_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addContainerGap();
        hsg1.addGroup(hpg1);
        hsg1.addContainerGap();
        javax.swing.GroupLayout.SequentialGroup hsg2 = layout.createSequentialGroup();
        hsg2.addContainerGap(1, Short.MAX_VALUE);
        hsg2.addComponent(bt_PrevStep);
        //hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addComponent(bt_NextStep);
        //hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addComponent(bt_Update);
        //hsg2.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg2.addComponent(bt_Cancel);
        hsg2.addContainerGap();
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(hpg1).addGroup(hsg2));

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
        vpg1.addComponent(bt_Cancel);
        vpg1.addComponent(bt_Update);
        vpg1.addComponent(bt_NextStep);
        vpg1.addComponent(bt_PrevStep);
        javax.swing.GroupLayout.SequentialGroup vsg1 = layout.createSequentialGroup();
        vsg1.addContainerGap();
        vsg1.addComponent(pl_NoteArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addComponent(pl_EditArea, javax.swing.GroupLayout.DEFAULT_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE);
        vsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg1.addGroup(vpg1);
        vsg1.addContainerGap();
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(vsg1));
    }

    private void initGuidView()
    {
        currStep = -2;

        ta_NoteArea.setText(Lang.getLang(LangRes.P30F6B01, "第一步：选择您要使用的口令模板"));
        cl_Layout.show(pl_EditArea, "guid");
        guidBean.showData(keysMdl);
//        guidBean.requestFocus();

        bt_PrevStep.setVisible(false);
        bt_NextStep.setVisible(true);
        bt_Update.setVisible(false);
    }

    private void initHeadView()
    {
        currStep = -1;

        ta_NoteArea.setText(Lang.getLang(LangRes.P30F6B02, "第二步：输入口令基本信息及设置提醒"));
        cl_Layout.show(pl_EditArea, "head");
        headBean.showData(keysMdl);
        headBean.requestFocus();

        boolean end = keysMdl.getItemSize() <= ConsEnv.PWDS_HEAD_SIZE;
        bt_PrevStep.setVisible(true);
        bt_NextStep.setVisible(!keysMdl.isUpdate() || !end);
        bt_Update.setVisible(canEdit && (keysMdl.isUpdate() && end));
    }

    private void prevBodyView()
    {
        currStep -= 1;

        cl_Layout.show(pl_EditArea, "body" + currStep);
        ls_BodyList.get(currStep).showData();

        boolean end = ls_BodyList.size() - 1 <= currStep;
        bt_NextStep.setVisible(!end);
        bt_Update.setVisible(canEdit && (keysMdl.isUpdate() || end));
    }

    private void nextBodyView()
    {
        currStep += 1;

        ta_NoteArea.setText(Lang.getLang(LangRes.P30F6B03, "第三步：录入口令内容信息"));
        cl_Layout.show(pl_EditArea, "body" + currStep);
        ls_BodyList.get(currStep).showData();

        boolean end = ls_BodyList.size() - 1 <= currStep;
        bt_NextStep.setVisible(!end);
        bt_Update.setVisible(canEdit && (keysMdl.isUpdate() || end));
    }

    private void bt_PrevStepActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (currStep == -1)
        {
            initGuidView();
            if (!keysMdl.isUpdate())
            {
                keysMdl.clear(1);
            }
            bt_Update.setVisible(false);
            bt_PrevStep.setVisible(false);
            return;
        }

        if (currStep == 0)
        {
            initHeadView();
            return;
        }

        prevBodyView();
    }

    private void bt_NextStepActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (currStep == -2)
        {
            if (guidBean.saveData())
            {
                initHeadView();
            }
            return;
        }

        if (currStep == -1)
        {
            if (headBean.saveData())
            {
                initBody();

                nextBodyView();
            }
            return;
        }

        if (ls_BodyList.get(currStep).saveData())
        {
            nextBodyView();
        }
    }

    private void bt_UpdateActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (currStep == -1)
        {
            if (!headBean.saveData())
            {
                return;
            }
        }
        else if (currStep >= 0)
        {
            if (!ls_BodyList.get(currStep).saveData())
            {
                return;
            }
        }

        try
        {
            keysMdl.saveData(true);
        }
        catch (Exception ex)
        {
            Logs.exception(ex);
            return;
        }

        setVisible(false);
        mwizPtn.getHintPtn().showNote(true);
        mwizPtn.findLast();
        mwizPtn.requestFocus();
    }

    private void bt_CancelActionPerformed(java.awt.event.ActionEvent evt)
    {
        hideDialog();
    }

    @Override
    protected void processWindowEvent(java.awt.event.WindowEvent e)
    {
        if (e.getID() == java.awt.event.WindowEvent.WINDOW_CLOSING)
        {
            if (!hideDialog())
            {
                return;
            }
        }
        super.processWindowEvent(e);
    }

    @Override
    protected boolean hideDialog()
    {
        if (canEdit && currStep > -2)
        {
            if (javax.swing.JOptionPane.YES_OPTION != Lang.showFirm(this, LangRes.P30F6A04, "您的口令数据尚未保存，确认放弃修改吗？"))
            {
                return false;
            }
        }
        setVisible(false);
        mwizPtn.requestFocus();
        return true;
    }
    private java.awt.CardLayout cl_Layout;
    private javax.swing.JButton bt_Cancel;
    private javax.swing.JButton bt_NextStep;
    private javax.swing.JButton bt_PrevStep;
    private javax.swing.JButton bt_Update;
    private javax.swing.JPanel pl_EditArea;
    private javax.swing.JPanel pl_NoteArea;
    private javax.swing.JTextArea ta_NoteArea;
    // 动态组件
    private java.util.List<BodyBar> ls_BodyList;
}
