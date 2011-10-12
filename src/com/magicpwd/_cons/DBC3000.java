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
package com.magicpwd._cons;

/**
 *
 * @author Amon
 */
public interface DBC3000
{

    /**
     * 数据库常量：日期
     */
    String SQL_NOW = "NOW";
    /**
     * ////////////////////////////////////////////////////
     * 一级类别
     * ////////////////////////////////////////////////////
     */
    String C2010000 = "C2010100";
    /**
     * 显示排序
     */
    String C2010001 = "C2010101";
    /**
     * 类别索引
     */
    String C2010002 = "C2010102";
    int C2010002_SIZE = 16;
    /**
     * 系统索引
     */
    String C2010003 = "C2010103";
    int C2010003_SIZE = 8;
    /**
     * 类别名称
     */
    String C2010004 = "C2010104";
    int C2010004_SIZE = 256;
    /**
     * 类别提示
     */
    String C2010005 = "C2010105";
    int C2010005_SIZE = 256;
    /**
     * 类别键值
     */
    String C2010006 = "C2010106";
    int C2010006_SIZE = 64;
    /**
     * 类别描述
     */
    String C2010007 = "C2010107";
    int C2010007_SIZE = 2048;
    /**
     * 更新时间
     */
    String C2010008 = "C2010108";
    /**
     * 创建时间
     */
    String C2010009 = "C2010109";
    /**
     * ////////////////////////////////////////////////////
     * 多级类别
     * ////////////////////////////////////////////////////
     */
    String C2010100 = "C2010200";
    /**
     * 显示排序
     */
    String C2010101 = "C2010201";
    /**
     * 类别级别
     */
    String C2010102 = "C2010202";
    /**
     * 类别索引
     */
    String C2010103 = "C2010203";
    int C2010103_SIZE = 16;
    /**
     * 一级索引
     */
    String C2010104 = "C2010204";
    int C2010104_SIZE = 16;
    /**
     * 类别名称
     */
    String C2010105 = "C2010205";
    int C2010105_SIZE = 256;
    /**
     * 类别提示
     */
    String C2010106 = "C2010206";
    int C2010106_SIZE = 256;
    /**
     * 类别键值
     */
    String C2010107 = "C2010207";
    int C2010107_SIZE = 64;
    /**
     * 类别描述
     */
    String C2010108 = "C2010208";
    int C2010108_SIZE = 2048;
    /**
     * 更新时间
     */
    String C2010109 = "C2010209";
    /**
     * 创建时间
     */
    String C201010A = "C201020A";
    /**
     * ////////////////////////////////////////////////////
     * 数据配置表格
     * ////////////////////////////////////////////////////
     */
    String P30F0000 = "P30F0000";
    /**
     * 数据索引
     */
    String P30F0001 = "P30F0001";
    int P30F0001_SIZE = 16;
    /**
     * 配置信息
     */
    String P30F0002 = "P30F0002";
    int P30F0002_SIZE = 1024;
    /**
     * 配置时间
     */
    String P30F0003 = "P30F0003";
    /**
     * ////////////////////////////////////////////////////
     * 口令信息表格
     * ////////////////////////////////////////////////////
     */
    String P30F0100 = "P30F0100";
    /**
     * 显示排序
     */
    String P30F0101 = "P30F0101";
    /**
     * 使用状态
     */
    String P30F0102 = "P30F0102";
    /**
     * 紧要程度
     */
    String P30F0103 = "P30F0103";
    /**
     * 口令索引
     */
    String P30F0104 = "P30F0104";
    int P30F0104_SIZE = 16;
    /**
     * 用户索引
     */
    String P30F0105 = "P30F0105";
    int P30F0105_SIZE = 8;
    /**
     * 所属类别
     */
    String P30F0106 = "P30F0106";
    int P30F0106_SIZE = 16;
    /**
     * 注册日期
     */
    String P30F0107 = "P30F0107";
    /**
     * 模板索引
     */
    String P30F0108 = "P30F0108";
    int P30F0108_SIZE = 16;
    /**
     * 口令标题
     */
    String P30F0109 = "P30F0109";
    int P30F0109_SIZE = 256;
    /**
     * 关键搜索
     */
    String P30F010A = "P30F010A";
    int P30F010A_SIZE = 1024;
    /**
     * 口令图标
     */
    String P30F010B = "P30F010B";
    int P30F010B_SIZE = 16;
    /**
     * 图标说明
     */
    String P30F010C = "P30F010C";
    int P30F010C_SIZE = 512;
    /**
     * 到期日期
     */
    String P30F010D = "P30F010D";
    /**
     * 到期提示
     */
    String P30F010E = "P30F010E";
    int P30F010E_SIZE = 1024;
    /**
     * 相关说明
     */
    String P30F010F = "P30F010F";
    int P30F010F_SIZE = 2048;
    /**
     * ////////////////////////////////////////////////////
     * 口令内容表格
     * ////////////////////////////////////////////////////
     */
    String P30F0200 = "P30F0200";
    /**
     * 内容索引
     */
    String P30F0201 = "P30F0201";
    /**
     * 口令索引
     */
    String P30F0202 = "P30F0202";
    int P30F0202_SIZE = 16;
    /**
     * 口令内容
     */
    String P30F0203 = "P30F0203";
    int P30F0203_SIZE = 4096;
    /**
     * ////////////////////////////////////////////////////
     * 历史信息表格
     * ////////////////////////////////////////////////////
     */
    String P30F0A00 = "P30F0A00";
    /**
     * 日志索引
     */
    String P30F0A01 = "P30F0A01";
    int P30F0A01_SIZE = 16;
    /**
     * 使用状态
     */
    String P30F0A02 = "P30F0A02";
    /**
     * 紧要程度
     */
    String P30F0A03 = "P30F0A03";
    /**
     * 口令索引
     */
    String P30F0A04 = "P30F0A04";
    int P30F0A04_SIZE = 16;
    /**
     * 用户索引
     */
    String P30F0A05 = "P30F0A05";
    int P30F0A05_SIZE = 8;
    /**
     * 所属类别
     */
    String P30F0A06 = "P30F0A06";
    int P30F0A06_SIZE = 16;
    /**
     * 注册日期
     */
    String P30F0A07 = "P30F0A07";
    /**
     * 模板索引
     */
    String P30F0A08 = "P30F0A08";
    int P30F0A08_SIZE = 16;
    /**
     * 口令标题
     */
    String P30F0A09 = "P30F0A09";
    int P30F0A09_SIZE = 256;
    /**
     * 关键搜索
     */
    String P30F0A0A = "P30F0A0A";
    int P30F0A0A_SIZE = 1024;
    /**
     * 口令图标
     */
    String P30F0A0B = "P30F0A0B";
    int P30F0A0B_SIZE = 16;
    /**
     * 图标说明
     */
    String P30F0A0C = "P30F0A0C";
    int P30F0A0C_SIZE = 512;
    /**
     * 到期日期
     */
    String P30F0A0D = "P30F0A0D";
    /**
     * 到期提示
     */
    String P30F0A0E = "P30F0A0E";
    int P30F0A0E_SIZE = 1024;
    /**
     * 相关说明
     */
    String P30F0A0F = "P30F0A0F";
    int P30F0A0F_SIZE = 2048;
    /**
     * ////////////////////////////////////////////////////
     * 历史数据表格
     * ////////////////////////////////////////////////////
     */
    String P30F0B00 = "P30F0B00";
    /**
     * 日志索引
     */
    String P30F0B01 = "P30F0B01";
    int P30F0B01_SIZE = 16;
    /**
     * 内容索引
     */
    String P30F0B02 = "P30F0B02";
    /**
     * 口令索引
     */
    String P30F0B03 = "P30F0B03";
    int P30F0B03_SIZE = 16;
    /**
     * 口令内容
     */
    String P30F0B04 = "P30F0B04";
    int P30F0B04_SIZE = 8192;
    /**
     * ////////////////////////////////////////////////////
     * 口令模板表格
     * ////////////////////////////////////////////////////
     */
    /**口令模板*/
    String P30F1100 = "P30F1100";
    /**排序依据*/
    String P30F1101 = "P30F1101";
    /**属性类别*/
    String P30F1102 = "P30F1102";
    /**属性索引*/
    String P30F1103 = "P30F1103";
    int P30F1103_SIZE = 16;
    /**类别索引*/
    String P30F1104 = "P30F1104";
    int P30F1104_SIZE = 16;
    /**属性名称*/
    String P30F1105 = "P30F1105";
    int P30F1105_SIZE = 256;
    /**属性提示*/
    String P30F1106 = "P30F1106";
    int P30F1106_SIZE = 256;
    /**属性说明*/
    String P30F1107 = "P30F1107";
    int P30F1107_SIZE = 2048;
    /**更新日期*/
    String P30F1108 = "P30F1108";
    /**创建日期*/
    String P30F1109 = "P30F1109";
    /**
     * ////////////////////////////////////////////////////
     * 字符空间表格
     * ////////////////////////////////////////////////////
     */
    String P30F2100 = "P30F2100";
    /**排序依据*/
    String P30F2101 = "P30F2101";
    /**使用状态*/
    String P30F2102 = "P30F2102";
    /**空间索引*/
    String P30F2103 = "P30F2103";
    int P30F2103_SIZE = 16;
    /**空间名称*/
    String P30F2104 = "P30F2104";
    int P30F2104_SIZE = 256;
    /**空间提示*/
    String P30F2105 = "P30F2105";
    int P30F2105_SIZE = 256;
    /**空间字符*/
    String P30F2106 = "P30F2106";
    int P30F2106_SIZE = 512;
    /**相关说明*/
    String P30F2107 = "P30F2107";
    int P30F2107_SIZE = 2048;
    /**更新日期*/
    String P30F2108 = "P30F2108";
    /**创建日期*/
    String P30F2109 = "P30F2109";
}
