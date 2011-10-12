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
 * @author Amon
 */
public interface ConsCfg
{

    /**
     * 配置数据：区分前缀
     */
    String CFG_PRE = "{0}";
    String CFG_DB = CFG_PRE + ".db";
    /**
     * 数据库安全：安全口令
     */
    String CFG_DB_SK = CFG_DB + ".sk";
    /**
     * 数据库安全：安全算法
     */
    String CFG_DB_SC = CFG_DB + ".sc";
    /**
     * 数据库安全：提供商
     */
    String CFG_DB_SP = CFG_DB + ".sp";
    /**
     * 用户信息：
     */
    String CFG_USER = CFG_PRE + ".user";
    /**
     * 用户信息：用户偏好配置
     */
    String CFG_USER_NAME = CFG_USER + ".{1}";
    /**
     * 用户信息：用户网站编码
     */
    String CFG_USER_CODE = CFG_USER_NAME + ".code";
    /**
     * 用户信息：用户安全信息
     */
    String CFG_USER_INFO = CFG_USER_NAME + ".info";
    /**
     * 用户信息：用户登录口令
     */
    String CFG_USER_PKEY = CFG_USER_NAME + ".pkey";
    /**
     * 用户信息：用户安全口令
     */
    String CFG_USER_SKEY = CFG_USER_NAME + ".skey";
    /**
     * 界面配置：界面风格
     */
    String CFG_VIEW = CFG_PRE + ".view";
    /**
     * 界面配置：窗口是否总在最上
     */
    String CFG_VIEW_TOPS = CFG_VIEW + ".tops";
    /**
     * 界面配置：是否显示菜单栏
     */
    String CFG_VIEW_MENU = CFG_VIEW + ".menu";
    /**
     * 界面配置：菜单栏显示位置
     */
    String CFG_VIEW_MENU_LOC = CFG_VIEW_MENU + ".loc";
    /**
     * 界面配置：是否显示工具栏
     */
    String CFG_VIEW_TOOL = CFG_VIEW + ".tool";
    /**
     * 界面配置：工具栏显示位置
     */
    String CFG_VIEW_TOOL_LOC = CFG_VIEW_TOOL + ".loc";
    String CFG_VIEW_TOOL_MOD = CFG_VIEW_TOOL + ".mod";
    String CFG_VIEW_TOOL_POS = CFG_VIEW_TOOL + ".pos";
    /**
     * 界面配置：是否显示信息栏
     */
    String CFG_VIEW_INFO = CFG_VIEW + ".info";
    /**
     * 界面配置：是否显示查找栏
     */
    String CFG_VIEW_FIND = CFG_VIEW + ".find";
    /**
     * 界面配置：
     */
    String CFG_VIEW_EDIT = CFG_VIEW + ".edit";
    /**
     * 界面配置：是否显示编辑板块
     */
    String CFG_VIEW_EDIT_VIW = CFG_VIEW_EDIT + ".viw";
    /**
     * 界面配置：编辑板块显示风格
     */
    String CFG_VIEW_EDIT_WND = CFG_VIEW_EDIT + ".wnd";
    /**
     * 界面配置：口令列表显示风格
     */
    String CFG_VIEW_LIST = CFG_PRE + ".list";
    /**
     * 界面配置：口令列表数据排序依据
     */
    String CFG_VIEW_LIST_KEY = CFG_VIEW_LIST + ".key";
    /**
     * 界面配置：口令列表数据排序方式
     */
    String CFG_VIEW_LIST_ASC = CFG_VIEW_LIST + ".asc";
    /**
     * 界面配置：口令列表界面布局方式
     */
    String CFG_VIEW_LIST_LAY = CFG_VIEW_LIST + ".lay";
    /**
     * 过期提示
     */
    String CFG_HINT = CFG_PRE + ".hint";
    /**
     * 过期提示：检测间隔
     */
    String CFG_HINT_INT = CFG_HINT + ".int";
    /**
     * 过期提示：提前提示时间
     */
    String CFG_HINT_PRE = CFG_HINT + ".pre";
    /**
     * 区域语言：语言设置
     */
    String CFG_LANG = CFG_PRE + ".lang";
    /**
     * 皮肤设置
     */
    String CFG_SKIN = CFG_PRE + ".skin";
    String CFG_SKIN_FEEL = CFG_SKIN + ".feel";
    String CFG_SKIN_LOOK = CFG_SKIN + ".look";//Look显示名称
    /**
     * 系统托盘显示方式
     */
    String CFG_TRAY = CFG_PRE + ".tray";
    String CFG_TRAY_PTN = CFG_TRAY + ".ptn";
    String CFG_TRAY_LOC = CFG_TRAY + ".loc";
    String CFG_TRAY_HINT_CNT = CFG_TRAY + ".cnt";
    /**
     * 口令选项：
     */
    String CFG_PWDS = CFG_PRE + ".pwds";
    /**
     * 口令选项：系统默认口令字符集
     */
    String CFG_PWDS_HASH = CFG_PWDS + ".hash";
    /**
     * 口令选项：系统默认口令长度
     */
    String CFG_PWDS_SIZE = CFG_PWDS + ".size";
    /**
     * 口令选项：生成口令时系统默认是否可以生现重复字符
     */
    String CFG_PWDS_LOOP = CFG_PWDS + ".loop";
    /**
     * 数据安全
     */
    String CFG_SAFE = CFG_PRE + ".safe";
    /**
     * 数据安全：用户数据
     */
    String CFG_SAFE_DATA = CFG_SAFE + ".data";
    /**
     * 数据安全：用户数据目录
     */
    String CFG_SAFE_DATA_DIR = CFG_SAFE_DATA + ".dir";
    /**
     * 数据安全：自动备份
     */
    String CFG_SAFE_DUMP = CFG_SAFE + ".dump";
    /**
     * 数据安全：自动备份文件数量
     */
    String CFG_SAFE_DUMP_CNT = CFG_SAFE_DUMP + ".cnt";
    /**
     * 数据安全：自动备份文件路径
     */
    String CFG_SAFE_DUMP_DIR = CFG_SAFE_DUMP + ".dir";
    /**
     * 数据安全：数据备份
     */
    String CFG_SAFE_BACK = CFG_SAFE + ".back";
    /**
     * 数据安全：本地备份目录
     */
    String CFG_SAFE_BACK_LOC = CFG_SAFE_BACK + ".loc";
    /**
     * 数据安全：云端存储配置
     */
    String CFG_SAFE_BACK_ACS = CFG_SAFE_BACK + ".acs";
    /**
     * 数据安全：剪贴板控制
     */
    String CFG_SAFE_CLIP = CFG_SAFE + ".clip";
    /**
     * 数据安全：剪贴板驻留时间
     */
    String CFG_SAFE_CLIP_DLT = CFG_SAFE_CLIP + ".dlt";
    /**
     * 向导模式：
     */
    String CFG_MPAD = CFG_PRE + ".mpad";
    String CFG_MPAD_WRAP = CFG_MPAD + ".wrap";
    /**
     * 数值运算：
     */
    String CFG_MAOC = CFG_PRE + ".maoc";
    /**
     * 数值运算：是否使用逗号分隔符
     */
    String CFG_MAOC_GCU = CFG_MAOC + ".gcu";
    /**
     * 数值运算：是否为多列显示
     */
    String CFG_MAOC_MCV = CFG_MAOC + ".mcv";
    /**
     * 默认数据：是
     */
    String DEF_TRUE = "true";
    /**
     * 默认数据：否
     */
    String DEF_FALSE = "false";
    /**
     * 默认风格：系统风格
     */
    String DEF_SKIN_DEF = "system";
    String DEF_SKIN_FEEL_DEF = "default";
    String DEF_SKIN_LOOK_DEF = "default";
    String DEF_SKIN_LOOK_SYS = "system";
    String DEF_TRAY = "icon";
    /**
     * 默认数据：剪贴板数据保留时长
     */
    String DEF_CLIP_DLT = "60";
    /**
     * 
     */
    String DEF_PWDS_HASH = "1000000000000006";
    /**
     * 默认数据：口令字符空间
     */
    String DEF_PWDS_CHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /**
     * 默认数据：口令长度
     */
    String DEF_PWDS_SIZE = "8";
    /**
     * 默认数据：备份目录
     */
    String DEF_DUMP_PATH = ConsEnv.DIR_BAK;
    /**
     * 默认数据：数据目录
     */
    String DEF_DATA_PATH = ConsEnv.DIR_DAT;
    /**
     * 默认数据：备份数量
     */
    String DEF_BACK_SIZE = "3";
}
