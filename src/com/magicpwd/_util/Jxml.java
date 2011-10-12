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
package com.magicpwd._util;

import com.magicpwd._comn.mpwd.MpwdHeader;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._cons.LangRes;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Amon
 */
public class Jxml
{

    public static Document create()
    {
        Document doc = DocumentHelper.createDocument();

        // ProcessingInstruction
        // Map<String, String> pi = new HashMap<String, String>();
        // pi.put("type", "text/xsl");
        // pi.put("href", "http://amon.me/mpwd/MagicPwd.xsl");
        // document.addProcessingInstruction("xml-stylesheet", pi);

        return doc;
    }

    public static void save(String file, MpwdHeader keys, List pwds)
    {
        Document doc = create();

        // root element
        Element root = doc.addElement("MagicPwd").addAttribute("site", ConsEnv.HOMEPAGE);

        Element node = root.addElement("Key").addAttribute("type", "pwds");
        node.addElement(Lang.getLang(LangRes.P30F1C01, "status")).addAttribute("name", Lang.getLang(LangRes.P30F1C02, "使用状态")).setText(keys.getP30F0102() + "");
        node.addElement(Lang.getLang(LangRes.P30F1C03, "catalog")).addAttribute("name", Lang.getLang(LangRes.P30F1C04, "所属类别")).setText(keys.getP30F0106());
        node.addElement(Lang.getLang(LangRes.P30F1C04, "create")).addAttribute("name", Lang.getLang(LangRes.P30F1C06, "注册日期")).setText(keys.getP30F0107().toString());
        node.addElement(Lang.getLang(LangRes.P30F1C07, "title")).addAttribute("name", Lang.getLang(LangRes.P30F1C08, "口令标题")).setText(keys.getP30F0109());
        node.addElement(Lang.getLang(LangRes.P30F1C09, "meta")).addAttribute("name", Lang.getLang(LangRes.P30F1C0A, "关键搜索")).setText(keys.getP30F010A());
        node.addElement(Lang.getLang(LangRes.P30F1C0B, "logo")).addAttribute("name", Lang.getLang(LangRes.P30F1C0C, "口令徽标")).setText(keys.getP30F010B());
        node.addElement(Lang.getLang(LangRes.P30F1C0D, "limit")).addAttribute("name", Lang.getLang(LangRes.P30F1C0E, "到期日期")).setText(keys.getP30F010E().toString());
        node.addElement(Lang.getLang(LangRes.P30F1C0F, "hint")).addAttribute("name", Lang.getLang(LangRes.P30F1C10, "到期提示")).setText(keys.getP30F010F());
        node.addElement(Lang.getLang(LangRes.P30F1C11, "memo")).addAttribute("name", Lang.getLang(LangRes.P30F1C12, "相关说明")).setText(keys.getP30F0110());
    }

    public static void save(String file, MpwdHeader keys)
    {
        Document doc = create();

        // root element
        Element root = doc.addElement("MagicPwd").addAttribute("site", ConsEnv.HOMEPAGE);

        Element node = root.addElement("Key").addAttribute("type", "note");
        node.addElement(Lang.getLang(LangRes.P30F1C03, "catalog")).addAttribute("name", Lang.getLang(LangRes.P30F1C04, "所属类别")).setText(keys.getP30F0106());
        node.addElement(Lang.getLang(LangRes.P30F1C07, "title")).addAttribute("name", Lang.getLang(LangRes.P30F1C08, "口令标题")).setText(keys.getP30F0109());
        node.addElement(Lang.getLang(LangRes.P30F1C09, "meta")).addAttribute("name", Lang.getLang(LangRes.P30F1C0A, "关键搜索")).setText(keys.getP30F010A());
        node.addElement(Lang.getLang(LangRes.P30F1C0B, "logo")).addAttribute("name", Lang.getLang(LangRes.P30F1C0C, "口令徽标")).setText(keys.getP30F010B());
        node.addElement(Lang.getLang(LangRes.P30F1C11, "memo")).addAttribute("name", Lang.getLang(LangRes.P30F1C12, "相关说明")).setText(keys.getP30F0110());
        node.addElement(Lang.getLang(LangRes.P30F1C11, "content")).addAttribute("name", Lang.getLang(LangRes.P30F1C12, "记事内容")).setText(keys.getMpwd().getP30F0203().toString());
    }

    public static void save(Document document, File file) throws IOException
    {
        // 美化格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 缩减格式
        // OutputFormat format = OutputFormat.createCompactFormat();
        // 指定XML编码
        // format.setEncoding("GBK");
        XMLWriter writer = new XMLWriter(new FileWriter(file), format);
        writer.write(document);
        writer.close();
    }

    public static Document load(File file) throws DocumentException
    {
        Document doc = new SAXReader().read(file);
        //doc.selectNodes("//students/student/@sn");
        return doc;
    }
}
