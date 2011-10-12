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
package com.magicpwd._comn.mpwd;

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
public class MgtdDetail implements java.io.Serializable
{

    private int P30F0401;
    private String P30F0402;
    private Long P30F0403;
    private Integer P30F0404;
    private Integer P30F0405;
    /**表达式*/
    private String P30F0406;

    /**
     * 计划索引
     * @return the P30F0402
     */
    public String getP30F0402()
    {
        return P30F0402;
    }

    /**
     * 计划索引
     * @param P30F0402 the P30F0402 to set
     */
    public void setP30F0402(String P30F0402)
    {
        this.P30F0402 = P30F0402;
    }

    /**
     * 指定时间
     * @return the P30F0403
     */
    public Long getP30F0403()
    {
        return P30F0403;
    }

    /**
     * 指定时间
     * @param P30F0403 the P30F0403 to set
     */
    public void setP30F0403(Long P30F0403)
    {
        this.P30F0403 = P30F0403;
    }

    /**
     * 间隔单位
     * @return the P30F0404
     */
    public Integer getP30F0404()
    {
        return P30F0404;
    }

    /**
     * 间隔单位
     * @param P30F0404 the P30F0404 to set
     */
    public void setP30F0404(Integer P30F0404)
    {
        this.P30F0404 = P30F0404;
    }

    /**
     * 间隔时间
     * @return the P30F0405
     */
    public Integer getP30F0405()
    {
        return P30F0405;
    }

    /**
     * 间隔时间
     * @param P30F0405 the P30F0405 to set
     */
    public void setP30F0405(Integer P30F0405)
    {
        this.P30F0405 = P30F0405;
    }

    /**
     * 表达式
     * @return the P30F0406
     */
    public String getP30F0406()
    {
        return P30F0406;
    }

    /**
     * 表达式
     * @param P30F0406 the P30F0406 to set
     */
    public void setP30F0406(String P30F0406)
    {
        this.P30F0406 = P30F0406;
    }
}
