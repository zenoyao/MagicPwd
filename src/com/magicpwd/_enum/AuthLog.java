/*
 *  Copyright (C) 2011 Amon
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
package com.magicpwd._enum;

/**
 *
 * @author Amon
 */
public enum AuthLog
{

    NONE,
    /**
     * 用户登录
     */
    signIn,
    /**
     * 锁定用户屏幕
     */
    signLs,
    /**
     * 权限验证
     */
    signRs,
    /**
     * 用户注册
     */
    signUp,
    /**
     * 口令找回
     */
    signFp,
    /**
     * 更改登录口令
     */
    signPk,
    /**
     * 更改安全口令
     */
    signSk,
    /**
     * 添加附属用户
     */
    signSu,
    /**
     * 在线用户登录
     */
    signNw,
    /**
     * 设置云存储用户
     */
    signCs,
    /**
     * 数据升级
     */
    signDu
}
