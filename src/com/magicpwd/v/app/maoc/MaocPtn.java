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
package com.magicpwd.v.app.maoc;

import com.magicpwd.__a.AMpwdPtn;
import com.magicpwd._comn.D1S2;
import com.magicpwd._comp.BtnLabel;
import com.magicpwd._comp.WButtonGroup;
import com.magicpwd._comp.WTextBox;
import com.magicpwd._cons.ConsCfg;
import com.magicpwd._cons.LangRes;
import com.magicpwd._enum.AppView;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd._util.Lang;
import com.magicpwd._util.Logs;
import com.magicpwd._util.Util;
import com.magicpwd.d.db.DBA4000;
import com.magicpwd.m.UserMdl;
import com.magicpwd.m.maoc.MaocMdl;
import com.magicpwd._comn.mpwd.Mexp;
import com.magicpwd._enum.ExpType;
import com.magicpwd.v.app.MenuPtn;
import com.magicpwd.v.app.tray.TrayPtn;
import org.javia.arity.Symbols;

/**
 * 计算器
 * 
 * @author Amon
 */
public class MaocPtn extends AMpwdPtn
{

    private MaocMdl maocMdl;
    private MenuPtn menuPtn;
    private Symbols symbols;

    public MaocPtn(TrayPtn trayPtn, UserMdl userMdl)
    {
        super(trayPtn, userMdl);
    }

    @Override
    public boolean initView()
    {
        initArgView();
        initExpView();
        initBaseView();
        return true;
    }

    private void initArgView()
    {
        sp_ArgBean = new javax.swing.JPanel();

        ls_NumList = new javax.swing.JList();
        ls_NumList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        javax.swing.JScrollPane sp_NumList = new javax.swing.JScrollPane(ls_NumList);
        ls_FunList = new javax.swing.JList();
        ls_FunList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        javax.swing.JScrollPane sp_FunList = new javax.swing.JScrollPane(ls_FunList);

        javax.swing.JSplitPane sp = new javax.swing.JSplitPane();
        sp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        sp.setDividerLocation(120);
        sp.setOneTouchExpandable(true);
        sp.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sp.setTopComponent(sp_NumList);
        sp.setRightComponent(sp_FunList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(sp_ArgBean);
        sp_ArgBean.setLayout(layout);

        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE);
        hsg.addGap(3);
        layout.setHorizontalGroup(hsg);

        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE);
        layout.setVerticalGroup(vsg);
    }

    private void initExpView()
    {
        sp_ExpBean = new javax.swing.JPanel();
        tb_ExpList = new javax.swing.JTable();
        lb_ExpText = new javax.swing.JLabel();
        tf_ExpText = new javax.swing.JTextField();
        textBox = new WTextBox(tf_ExpText);
        bt_AocHelp = new BtnLabel();
        bt_ExpText = new BtnLabel();

        tb_ExpList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        javax.swing.JScrollPane sp_ExpList = new javax.swing.JScrollPane(tb_ExpList);

        textBox.initView();
        lb_ExpText.setLabelFor(tf_ExpText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(sp_ExpBean);
        sp_ExpBean.setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg1 = layout.createSequentialGroup();
        hsg1.addComponent(lb_ExpText);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(tf_ExpText, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(bt_ExpText, 19, 19, 19);
        hsg1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        hsg1.addComponent(bt_AocHelp, 19, 19, 19);
        javax.swing.GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING);
        hpg1.addComponent(sp_ExpList, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE);
        hpg1.addGroup(hsg1);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup().addGap(6).addGroup(hpg1);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hsg));

        javax.swing.GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER);
        vpg1.addComponent(lb_ExpText);
        vpg1.addComponent(tf_ExpText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
        vpg1.addComponent(bt_AocHelp, 19, 19, 19);
        vpg1.addComponent(bt_ExpText, 19, 19, 19);
        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
//        vsg.addContainerGap();
        vsg.addComponent(sp_ExpList, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE);
        vsg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        vsg.addGroup(vpg1);
//        vsg.addContainerGap();
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vsg));
    }

    private void initBaseView()
    {
        javax.swing.JSplitPane sp = new javax.swing.JSplitPane();
        sp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        sp.setDividerLocation(123);
        sp.setOneTouchExpandable(true);
        sp.setLeftComponent(sp_ArgBean);
        sp.setRightComponent(sp_ExpBean);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        javax.swing.GroupLayout.SequentialGroup hsg = layout.createSequentialGroup();
        hsg.addContainerGap();
        hsg.addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE);
        hsg.addContainerGap();
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(hsg));

        javax.swing.GroupLayout.SequentialGroup vsg = layout.createSequentialGroup();
        vsg.addContainerGap();
        vsg.addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE);
        vsg.addContainerGap();
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(vsg));

        this.setIconImage(Bean.getLogo(16));

        this.pack();
        Bean.centerForm(this, null);
        this.setVisible(true);
    }

    @Override
    public boolean initLang()
    {
        this.setTitle(Lang.getLang(LangRes.P30FB201, "数值运算"));
        Bean.setText(lb_ExpText, Lang.getLang(LangRes.P30FB301, "计算式(@F)"));

        Bean.setText(bt_ExpText, Lang.getLang(LangRes.P30FB501, "@C"));
        Bean.setTips(bt_ExpText, Lang.getLang(LangRes.P30FB502, "运算(ALT + C)"));

        Bean.setText(bt_AocHelp, Lang.getLang(LangRes.P30FB503, "@O"));
        Bean.setTips(bt_AocHelp, Lang.getLang(LangRes.P30FB504, "选项(ALT + O)"));

        textBox.initLang();

        this.pack();
        Bean.centerForm(this, null);
        return true;
    }

    @Override
    public boolean initData()
    {
        maocMdl = new MaocMdl(userMdl);
        maocMdl.init();

        ls_NumList.setModel(maocMdl.getMnumMdl());
        ls_FunList.setModel(maocMdl.getMfunMdl());
        tb_ExpList.setModel(maocMdl.getMexpMdl());
        int w = tb_ExpList.getFontMetrics(tb_ExpList.getFont()).stringWidth("99999");
        javax.swing.table.TableColumn col = tb_ExpList.getColumnModel().getColumn(0);
        col.setMaxWidth(w);
        col.setPreferredWidth(w);
        col.setWidth(w);

        symbols = new Symbols();

        java.awt.event.ActionListener listener = new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                bt_ExpTextActionPerformed(e);
            }
        };
        tf_ExpText.addActionListener(listener);

        bt_ExpText.setIcon(getFeelIcon(null, "var:maoc-calc"));
        bt_ExpText.addActionListener(listener);

        bt_AocHelp.setIcon(getFeelIcon(null, "var:maoc-help"));
        bt_AocHelp.addActionListener(new java.awt.event.ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                bt_AocHelpActionPerformed(e);
            }
        });

        pm_NumMenu = new javax.swing.JPopupMenu();
        Bean.registerPopupMenuAction(ls_NumList, pm_NumMenu);
        pm_FunMenu = new javax.swing.JPopupMenu();
        Bean.registerPopupMenuAction(ls_FunList, pm_FunMenu);
        pm_ExpMenu = new javax.swing.JPopupMenu();
        Bean.registerPopupMenuAction(tb_ExpList, pm_ExpMenu);

        pm_AocHelp = new javax.swing.JPopupMenu();
        menuPtn = new MenuPtn(trayPtn, userMdl);
        try
        {
            menuPtn.loadData(new java.io.File(userMdl.getDataDir(), "maoc.xml"));
            menuPtn.getPopMenu("mnum", pm_NumMenu);
            menuPtn.getPopMenu("mfun", pm_FunMenu);
            menuPtn.getPopMenu("mexp", pm_ExpMenu);
            menuPtn.getPopMenu("help", pm_AocHelp);
            menuPtn.getStrokes("maoc", rootPane);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }

        // 初始化：是否使用逗号分隔符
        boolean b = ConsCfg.DEF_TRUE.equalsIgnoreCase(userMdl.getCfg(AppView.maoc, ConsCfg.CFG_MAOC_GCU, ConsCfg.DEF_TRUE));
        maocMdl.getFormat().setGroupingUsed(b);
        javax.swing.AbstractButton bt = menuPtn.getButton("grouping-used");
        if (bt != null)
        {
            bt.setSelected(b);
        }

        // 初始化：是否多列显示
        String t = userMdl.getCfg(AppView.maoc, ConsCfg.CFG_MAOC_MCV, "1");
        maocMdl.getMexpMdl().setMultiColumn("2".equals(t));
        WButtonGroup bg = menuPtn.getGroup("view-mode");
        if (bg != null)
        {
            bg.setSelected(t, true);
        }

        // 初始化：小数位精度
        maocMdl.getFormat().setMaximumFractionDigits(8);
        bg = menuPtn.getGroup("data-precision");
        if (bg != null)
        {
            bg.setSelected("dec:8", true, "dec:-1");
        }

        textBox.initData();
        tf_ExpText.requestFocus();

        this.pack();
        Bean.centerForm(this, null);

        java.util.List<Mexp> maocList = DBA4000.readMexpData(userMdl);
        if (maocList != null)
        {
            for (Mexp maoc : maocList)
            {
                if (maoc.getP30F0802() == ExpType.FUN.ordinal())
                {
                    appendFun(maoc);
                    continue;
                }
                if (maoc.getP30F0802() == ExpType.NUM.ordinal())
                {
                    appendNum(maoc);
                    continue;
                }
            }
        }

        return true;
    }

    @Override
    public boolean showData()
    {
        return true;
    }

    @Override
    public MenuPtn getMenuPtn()
    {
        return null;
    }

    @Override
    public boolean endSave()
    {
        return true;
    }

    @Override
    public void requestFocus()
    {
    }

    public void setMultiColumnView(String colNum)
    {
        maocMdl.getMexpMdl().setMultiColumn("2".equals(colNum));
        userMdl.setCfg(AppView.maoc, ConsCfg.CFG_MAOC_MCV, colNum);
    }

    public void setGroupingUsed(boolean used)
    {
        maocMdl.getFormat().setGroupingUsed(used);
        userMdl.setCfg(AppView.maoc, ConsCfg.CFG_MAOC_GCU, used ? ConsCfg.DEF_TRUE : ConsCfg.DEF_FALSE);
    }

    public String trim(String exp)
    {
        if (!Char.isValidate(exp))
        {
            return "";
        }
        StringBuilder buf = new StringBuilder(exp);
        buf = Char.replace(buf, "０", "0");
        buf = Char.replace(buf, "１", "1");
        buf = Char.replace(buf, "２", "2");
        buf = Char.replace(buf, "３", "3");
        buf = Char.replace(buf, "４", "4");
        buf = Char.replace(buf, "５", "5");
        buf = Char.replace(buf, "６", "6");
        buf = Char.replace(buf, "７", "7");
        buf = Char.replace(buf, "８", "8");
        buf = Char.replace(buf, "９", "9");

//        buf = Char.replace(Char.replace(buf, "｛", "{"), "{", "(");
//        buf = Char.replace(Char.replace(buf, "｝", "}"), "}", ")");
//        buf = Char.replace(Char.replace(buf, "［", "["), "[", "(");
//        buf = Char.replace(Char.replace(buf, "］", "]"), "]", ")");
        buf = Char.replace(Char.replace(buf, "｛", "("), "{", "(");
        buf = Char.replace(Char.replace(buf, "｝", ")"), "}", ")");
        buf = Char.replace(Char.replace(buf, "［", "("), "[", "(");
        buf = Char.replace(Char.replace(buf, "］", ")"), "]", ")");
        buf = Char.replace(buf, "（", "(");
        buf = Char.replace(buf, "）", ")");

        buf = Char.replace(Char.replace(Char.replace(buf, "。", "."), "．", "."), "·", ".");
        buf = Char.replace(buf, "＋", "+");
        buf = Char.replace(buf, "－", "-");
        buf = Char.replace(buf, "×", "*");
        buf = Char.replace(buf, "÷", "/");
        buf = Char.replace(buf, "／", "%");
        buf = Char.replace(buf, "＝", "=");
        buf = Char.replace(buf, "！", "!");

        buf = Char.replace(buf, "ε", "e");
        buf = Char.replace(buf, "π", "pi");

        return buf.toString();
    }

    private void bt_ExpTextActionPerformed(java.awt.event.ActionEvent e)
    {
        String exp = tf_ExpText.getText().trim();
        if (!Char.isValidate(exp))
        {
            return;
        }

        String tmp = trim(exp).replaceAll("\\s*[=]+\\s*$", "");
        if (!Char.isValidate(tmp))
        {
            return;
        }

        try
        {
            int r = maocMdl.getMexpMdl().getRowCount();
            double value = symbols.eval(tmp);
            maocMdl.getMexpMdl().appendItem(new D1S2(value, exp, maocMdl.getFormat().format(value)));
            Util.scrollToVisible(tb_ExpList, r, 0, false);
        }
        catch (Exception ex)
        {
            Logs.exception(ex);
            Lang.showMesg(this, null, ex.toString());
        }
    }

    private void bt_AocHelpActionPerformed(java.awt.event.ActionEvent e)
    {
        pm_AocHelp.show(bt_AocHelp, 0, bt_AocHelp.getHeight());
    }

    public int getPrecision()
    {
        return maocMdl.getFormat().getMaximumFractionDigits();
    }

    /**
     * @param precision the precision to set
     */
    public void setPrecision(int precision)
    {
        if (precision >= 0 && precision <= 16)
        {
            maocMdl.getFormat().setMaximumFractionDigits(precision);
        }
    }

    public Mexp getSelectedNum()
    {
        int row = ls_NumList.getSelectedIndex();
        return row > -1 ? maocMdl.getMnumMdl().getItemAt(row) : null;
    }

    public boolean appendNum(Mexp mexp)
    {
        if (!Char.isValidate(mexp.getP30F0804()) || !Char.isValidate(mexp.getP30F0805()))
        {
            return false;
        }
        try
        {
            org.javia.arity.FunctionAndName fan = symbols.compileWithName(mexp.getP30F0804() + '=' + mexp.getP30F0805());
            symbols.define(fan);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(this, null, exp.toString());
            return false;
        }
        maocMdl.getMnumMdl().appendItem(mexp);
        return true;
    }

    public void deleteNum()
    {
        int row = ls_NumList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        Mexp item = maocMdl.getMnumMdl().getItemAt(row);
        if (maocMdl.getMnumMdl().deleteItem(row))
        {
            try
            {
                org.javia.arity.FunctionAndName fan = symbols.compileWithName(item.getP30F0804() + '=' + item.getP30F0805());
                symbols.remove(fan.name, fan.function);
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
                Lang.showMesg(this, null, exp.toString());
            }
        }
    }

    public void updateNum(Mexp mexp)
    {
        int row = ls_NumList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        if (!Char.isValidate(mexp.getP30F0804()) || !Char.isValidate(mexp.getP30F0805()))
        {
            return;
        }

        try
        {
            Mexp temp = maocMdl.getMnumMdl().getItemAt(row);
            org.javia.arity.FunctionAndName oldFan = symbols.compileWithName(temp.getP30F0804() + '=' + temp.getP30F0805());
            symbols.remove(oldFan.name, oldFan.function);
            org.javia.arity.FunctionAndName newFan = symbols.compileWithName(mexp.getP30F0804() + '=' + mexp.getP30F0805());
            symbols.define(newFan);
            temp.setP30F0804(mexp.getP30F0804());
            temp.setP30F0805(mexp.getP30F0805());
            temp.setP30F0806(mexp.getP30F0806());
            temp.setP30F0807(mexp.getP30F0807());
            maocMdl.getMnumMdl().updateItem(temp);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(this, null, exp.toString());
        }
    }

    public void copyNumName()
    {
        int row = ls_NumList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        Mexp item = maocMdl.getMnumMdl().getItemAt(row);
        if (item != null)
        {
            Util.copy2Clipboard(item.getP30F0804());
        }
    }

    public void copyNumValue()
    {
        int row = ls_NumList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        Mexp item = maocMdl.getMnumMdl().getItemAt(row);
        if (item != null)
        {
            Util.copy2Clipboard(item.getP30F0805());
        }
    }

    public void reuseNumName()
    {
        int row = ls_NumList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        Mexp item = maocMdl.getMnumMdl().getItemAt(row);
        if (item != null)
        {
            replaceExpression(item.getP30F0804());
        }
    }

    public void reuseNumValue()
    {
        int row = ls_NumList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        Mexp item = maocMdl.getMnumMdl().getItemAt(row);
        if (item != null)
        {
            replaceExpression(item.getP30F0805());
        }
    }

    public Mexp getSelectedFun()
    {
        int row = ls_FunList.getSelectedIndex();
        return row > -1 ? maocMdl.getMfunMdl().getItemAt(row) : null;
    }

    public boolean appendFun(Mexp item)
    {
        if (!Char.isValidate(item.getP30F0804()) || !Char.isValidate(item.getP30F0805()))
        {
            return false;
        }
        try
        {
            org.javia.arity.FunctionAndName fan = symbols.compileWithName(item.getP30F0804() + '=' + item.getP30F0805());
            symbols.define(fan);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(this, null, exp.toString());
            return false;
        }
        maocMdl.getMfunMdl().appendItem(item);
        return true;
    }

    public void deleteFun()
    {
        int row = ls_FunList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        Mexp item = maocMdl.getMfunMdl().getItemAt(row);
        if (maocMdl.getMfunMdl().deleteItem(row))
        {
            try
            {
                org.javia.arity.FunctionAndName fan = symbols.compileWithName(item.getP30F0804() + '=' + item.getP30F0805());
                symbols.remove(fan.name, fan.function);
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
                Lang.showMesg(this, null, exp.toString());
            }
        }
    }

    public void updateFun(Mexp mexp)
    {
        int row = ls_FunList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        if (!Char.isValidate(mexp.getP30F0804()) || !Char.isValidate(mexp.getP30F0805()))
        {
            return;
        }

        try
        {
            Mexp temp = maocMdl.getMfunMdl().getItemAt(row);
            org.javia.arity.FunctionAndName oldFan = symbols.compileWithName(temp.getP30F0804() + '=' + temp.getP30F0805());
            symbols.remove(oldFan.name, oldFan.function);
            org.javia.arity.FunctionAndName newFan = symbols.compileWithName(mexp.getP30F0804() + '=' + mexp.getP30F0805());
            symbols.define(newFan);
            temp.setP30F0804(mexp.getP30F0804());
            temp.setP30F0805(mexp.getP30F0805());
            temp.setP30F0806(mexp.getP30F0806());
            temp.setP30F0807(mexp.getP30F0807());
            maocMdl.getMfunMdl().updateItem(temp);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            Lang.showMesg(this, null, exp.toString());
        }
    }

    public void copyFunName()
    {
        int row = ls_FunList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        Mexp item = maocMdl.getMfunMdl().getItemAt(row);
        if (item != null)
        {
            Util.copy2Clipboard(item.getP30F0804());
        }
    }

    public void copyFunValue()
    {
        int row = ls_FunList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        Mexp item = maocMdl.getMfunMdl().getItemAt(row);
        if (item != null)
        {
            Util.copy2Clipboard(item.getP30F0805());
        }
    }

    public void reuseFunName()
    {
        int row = ls_FunList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        Mexp item = maocMdl.getMfunMdl().getItemAt(row);
        if (item != null)
        {
            replaceExpression(item.getP30F0804());
        }
    }

    public void reuseFunValue()
    {
        int row = ls_FunList.getSelectedIndex();
        if (row < 0)
        {
            return;
        }
        Mexp item = maocMdl.getMfunMdl().getItemAt(row);
        if (item != null)
        {
            replaceExpression(item.getP30F0805());
        }
    }

    public void appendExp(String expression)
    {
        tf_ExpText.setText(tf_ExpText.getText() + expression);
    }

    public void removeExpCur()
    {
        int row = tb_ExpList.getSelectedRow();
        if (row < 0)
        {
            return;
        }
        maocMdl.getMexpMdl().removeItemAt(row);
    }

    public void removeExpAll()
    {
        maocMdl.getMexpMdl().removeAll();
    }

    public void copyExpName()
    {
        int row = tb_ExpList.getSelectedRow();
        if (row < 0)
        {
            return;
        }

        D1S2 item = maocMdl.getMexpMdl().getItemAt(row);
        if (item != null)
        {
            Util.copy2Clipboard(item.getK());
        }
    }

    public void copyExpValue()
    {
        int row = tb_ExpList.getSelectedRow();
        if (row < 0)
        {
            return;
        }

        D1S2 item = maocMdl.getMexpMdl().getItemAt(row);
        if (item != null)
        {
            Util.copy2Clipboard(item.getV());
        }
    }

    public void reuseExpName()
    {
        int row = tb_ExpList.getSelectedRow();
        if (row < 0)
        {
            return;
        }

        D1S2 item = maocMdl.getMexpMdl().getItemAt(row);
        if (item != null)
        {
            replaceExpression(item.getK());
        }
    }

    public void reuseExpValue()
    {
        int row = tb_ExpList.getSelectedRow();
        if (row < 0)
        {
            return;
        }

        D1S2 item = maocMdl.getMexpMdl().getItemAt(row);
        if (item != null)
        {
            replaceExpression(item.getV().replace(",", ""));
        }
    }

    public void replaceExpression(String expression)
    {
        tf_ExpText.replaceSelection(expression);
    }

    public void replaceExpression(String expression, boolean forward, int step)
    {
        int s = tf_ExpText.getCaretPosition();
        tf_ExpText.replaceSelection(expression);
        if (step > -1)
        {
            tf_ExpText.setCaretPosition(forward ? s + step : s + expression.length() - step);
        }
    }

    public void setCaretPosition(int position)
    {
        tf_ExpText.setCaretPosition(position);
    }

    public void moveCaretPosition(int position)
    {
        tf_ExpText.setCaretPosition(tf_ExpText.getCaretPosition() + position);
    }
    private WTextBox textBox;
    private BtnLabel bt_AocHelp;
    private BtnLabel bt_ExpText;
    private javax.swing.JList ls_FunList;
    private javax.swing.JList ls_NumList;
    private javax.swing.JLabel lb_ExpText;
    private javax.swing.JTable tb_ExpList;
    private javax.swing.JPanel sp_ArgBean;
    private javax.swing.JPanel sp_ExpBean;
    private javax.swing.JPopupMenu pm_AocHelp;
    private javax.swing.JPopupMenu pm_ExpMenu;
    private javax.swing.JPopupMenu pm_FunMenu;
    private javax.swing.JPopupMenu pm_NumMenu;
    private javax.swing.JTextField tf_ExpText;
}
