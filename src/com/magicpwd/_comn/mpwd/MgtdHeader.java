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

import java.io.Serializable;

/**
 * 计划任务
 * @author Aven
 */
public class MgtdHeader implements Serializable
{

    /**显示排序*/
    private int P30F0301;
    /**任务类型*/
    private int P30F0302;
    /**任务状态*/
    private int P30F0303;
    /**任务级别*/
    private int P30F0304;
    /**提醒周期*/
    private int P30F0305;
    /**提示方式*/
    private int P30F0306;
    /**可否共用*/
    private int P30F0307;
    /**完成度*/
    private int P30F0308;
    /**任务索引*/
    private String P30F0309;
    /**上级任务*/
    private String P30F030A;
    /**前置任务*/
    private String P30F030B;
    /**任务名称*/
    private String P30F030C;
    /**起始时间*/
    private Long P30F030D;
    /**结束时间*/
    private Long P30F030E;
    /**执行时间*/
    private Long P30F030F;
    /**执行参数*/
    private String P30F0310;
    /**附加参数*/
    private String P30F0311;
    /**是否提前*/
    private Integer P30F0312;
    /**提前间隔*/
    private Integer P30F0313;
    /**相关说明*/
    private String P30F0314;
    /**提示列表*/
    private java.util.List<MgtdDetail> hintList;

    /**
     * 显示排序
     * @return the P30F0301
     */
    public int getP30F0301()
    {
        return P30F0301;
    }

    /**
     * 显示排序
     * @param P30F0301 the P30F0301 to set
     */
    public void setP30F0301(int P30F0301)
    {
        this.P30F0301 = P30F0301;
    }

    /**
     * 任务类型
     * @return the P30F0302
     */
    public int getP30F0302()
    {
        return P30F0302;
    }

    /**
     * 任务类型
     * @param P30F0302 the P30F0302 to set
     */
    public void setP30F0302(int P30F0302)
    {
        this.P30F0302 = P30F0302;
    }

    /**
     * 任务状态
     * @return the P30F0303
     */
    public int getP30F0303()
    {
        return P30F0303;
    }

    /**
     * 任务状态
     * @param P30F0303 the P30F0303 to set
     */
    public void setP30F0303(int P30F0303)
    {
        this.P30F0303 = P30F0303;
    }

    /**
     * 任务级别
     * @return the P30F0304
     */
    public int getP30F0304()
    {
        return P30F0304;
    }

    /**
     * 任务级别
     * @param P30F0304 the P30F0304 to set
     */
    public void setP30F0304(int P30F0304)
    {
        this.P30F0304 = P30F0304;
    }

    /**
     * 提醒周期
     * @return the P30F0305
     */
    public int getP30F0305()
    {
        return P30F0305;
    }

    /**
     * 提醒周期
     * @param P30F0305 the P30F0305 to set
     */
    public void setP30F0305(int P30F0305)
    {
        this.P30F0305 = P30F0305;
    }

    /**
     * 提示方式
     * @return the P30F0306
     */
    public int getP30F0306()
    {
        return P30F0306;
    }

    /**
     * 提示方式
     * @param P30F0306 the P30F0306 to set
     */
    public void setP30F0306(int P30F0306)
    {
        this.P30F0306 = P30F0306;
    }

    /**
     * 可否共用
     * @return the P30F0307
     */
    public int getP30F0307()
    {
        return P30F0307;
    }

    /**
     * 可否共用
     * @param P30F0307 the P30F0307 to set
     */
    public void setP30F0307(int P30F0307)
    {
        this.P30F0307 = P30F0307;
    }

    /**
     * 完成度
     * @return the P30F0308
     */
    public int getP30F0308()
    {
        return P30F0308;
    }

    /**
     * 完成度
     * @param P30F0308 the P30F0308 to set
     */
    public void setP30F0308(int P30F0308)
    {
        this.P30F0308 = P30F0308;
    }

    /**
     * 任务索引
     * @return the P30F0309
     */
    public String getP30F0309()
    {
        return P30F0309;
    }

    /**
     * 任务索引
     * @param P30F0309 the P30F0309 to set
     */
    public void setP30F0309(String P30F0309)
    {
        this.P30F0309 = P30F0309;
    }

    /**
     * 上级任务
     * @return the P30F030A
     */
    public String getP30F030A()
    {
        return P30F030A;
    }

    /**
     * 上级任务
     * @param P30F030A the P30F030A to set
     */
    public void setP30F030A(String P30F030A)
    {
        this.P30F030A = P30F030A;
    }

    /**
     * 前置任务
     * @return the P30F030B
     */
    public String getP30F030B()
    {
        return P30F030B;
    }

    /**
     * 前置任务
     * @param P30F030B the P30F030B to set
     */
    public void setP30F030B(String P30F030B)
    {
        this.P30F030B = P30F030B;
    }

    /**
     * 任务名称
     * @return the P30F030C
     */
    public String getP30F030C()
    {
        return P30F030C;
    }

    /**
     * 任务名称
     * @param P30F030C the P30F030C to set
     */
    public void setP30F030C(String P30F030C)
    {
        this.P30F030C = P30F030C;
    }

    /**
     * 起始时间
     * @return the P30F030D
     */
    public Long getP30F030D()
    {
        return P30F030D;
    }

    /**
     * 起始时间
     * @param P30F030D the P30F030D to set
     */
    public void setP30F030D(Long P30F030D)
    {
        this.P30F030D = P30F030D;
    }

    /**
     * 结束时间
     * @return the P30F030E
     */
    public Long getP30F030E()
    {
        return P30F030E;
    }

    /**
     * 结束时间
     * @param P30F030E the P30F030E to set
     */
    public void setP30F030E(Long P30F030E)
    {
        this.P30F030E = P30F030E;
    }

    /**
     * 执行时间
     * @return the P30F030F
     */
    public Long getP30F030F()
    {
        return P30F030F;
    }

    /**
     * 执行时间
     * @param P30F030F the P30F030F to set
     */
    public void setP30F030F(Long P30F030F)
    {
        this.P30F030F = P30F030F;
    }

    /**
     * 执行参数
     * @return the P30F0310
     */
    public String getP30F0310()
    {
        return P30F0310;
    }

    /**
     * 执行参数
     * @param P30F0310 the P30F0310 to set
     */
    public void setP30F030F(String P30F0310)
    {
        this.P30F0310 = P30F0310;
    }

    /**
     * 附加参数
     * @return the P30F0311
     */
    public String getP30F0311()
    {
        return P30F0311;
    }

    /**
     * 附加参数
     * @param P30F0311 the P30F0311 to set
     */
    public void setP30F0311(String P30F0311)
    {
        this.P30F0311 = P30F0311;
    }

    /**
     * 是否提前
     * @return the P30F0312
     */
    public Integer getP30F0312()
    {
        return P30F0312;
    }

    /**
     * 是否提前
     * @param P30F0312 the P30F0312 to set
     */
    public void setP30F0312(Integer P30F0312)
    {
        this.P30F0312 = P30F0312;
    }

    /**
     * 提前间隔
     * @return the P30F0313
     */
    public Integer getP30F0313()
    {
        return P30F0313;
    }

    /**
     * 提前间隔
     * @param P30F0313 the P30F0313 to set
     */
    public void setP30F0313(Integer P30F0313)
    {
        this.P30F0313 = P30F0313;
    }

    /**
     * 相关说明
     * @return the P30F0314
     */
    public String getP30F0314()
    {
        return P30F0314;
    }

    /**
     * 相关说明
     * @param P30F0314 the P30F0314 to set
     */
    public void setP30F0314(String P30F0314)
    {
        this.P30F0314 = P30F0314;
    }

    /**
     * @return the hintList
     */
    public java.util.List<MgtdDetail> getHintList()
    {
        return hintList;
    }

    /**
     * @param hintList the hintList to set
     */
    public void setHintList(java.util.List<MgtdDetail> hintList)
    {
        this.hintList = hintList;
    }

    public MgtdDetail getHint(int index)
    {
        if (hintList != null && index > -1 && index < hintList.size())
        {
            return hintList.get(index);
        }
        return null;
    }
}
