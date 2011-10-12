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
package com.magicpwd.m.maoc;

import com.magicpwd._cons.maoc.MaocEnv;
import com.magicpwd._util.Char;
import com.magicpwd._util.Logs;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * <ul>
 * <li>功能说明：</li>
 * <br />
 * 表达式计算工具，用于计算用户给定表达式的值，并根据需要返回每一步的计算结果。
 * <li>使用说明：</li>
 * <br />
 * 流程说明：<br />
 * 本计算流程采用如下计算方式：<br />
 * 1、去除原计算表达式结尾的等号“=”；<br />
 * 2、把原表达式放置在小括号“()”内，以进行相同的算法处理；<br />
 * 3、记录每一步的运算结果，并保存在S1S2对象内；<br />
 * S1S2对象存储结构如下：<br />
 * k:上一步计算表达式；<br />
 * v1：当前步骤待进行计算的操作子；<br />
 * v2：当前运算结果；<br />
 * 4、返回运算结果。<br />
 * <br />
 * 使用说明：<br />
 * 1、用户表达式为空的情况下，系抛出异常；<br />
 * 2、步骤列表为NULL的情况下，不进行运算步骤的记录；<br />
 * 3、运算结果可以直接返回；<br />
 * 4、运算步骤的最后一步用户可以根据需要选择是否显示，原因：<br />
 * 在运算过程中额外添加了一对小括号“”，见流程说明2，此步骤是否存在不影响结果的显示。<br />
 * </ul>
 * @author Amon
 */
public final class WComputer
{

    /** 操作数堆栈 */
    private Stack<String> numStack;
    /** 操作符堆栈 */
    private Stack<WOperator> oprStack;

    public WComputer()
    {
    }

    /**
     * 仅支持小括号及 + - * / % 运算的方法
     *
     * @param exp
     * @return
     */
    public BigDecimal calculate(String exp, MathContext ctx) throws Exception
    {
        if (!Char.isValidate(exp))
        {
            return BigDecimal.ZERO;
        }

        exp = exp.trim();
        exp = '(' + exp + ')';

        Stack<BigDecimal> numStack = new Stack<BigDecimal>();
        Stack<Character> oprStack = new Stack<Character>();

        StringBuilder sb = new StringBuilder();
        char[] a = exp.toCharArray();
        char t;
        boolean b = false;
        for (char c : a)
        {
            if (c == '(')
            {
                oprStack.push(c);
                b = true;
                continue;
            }
            if (c == ')')
            {
                if (sb.length() > 0)
                {
                    numStack.push(new BigDecimal(sb.toString()));
                    sb.delete(0, sb.length());
                }
                while (!oprStack.isEmpty())
                {
                    t = oprStack.peek();
                    if (t == '(')
                    {
                        break;
                    }
                    numStack.push(calculate(numStack.pop(), oprStack.pop(), numStack.pop(), ctx));
                }
                oprStack.pop();
                b = false;
                continue;
            }
            if (c == '-')
            {
                if (b)
                {
                    sb.append(c);
                    b = false;
                    continue;
                }

                if (sb.length() > 0)
                {
                    numStack.push(new BigDecimal(sb.toString()));
                    sb.delete(0, sb.length());
                }
                oprStack.push(c);
                b = true;
                continue;
            }
            if (c == '+')
            {
                if (b)
                {
                    sb.append(c);
                    b = false;
                    continue;
                }

                if (sb.length() > 0)
                {
                    numStack.push(new BigDecimal(sb.toString()));
                    sb.delete(0, sb.length());
                }
                oprStack.push(c);
                b = true;
                continue;
            }
            if (c == '*' || c == '/' || c == '%')
            {
                if (sb.length() > 0)
                {
                    numStack.push(new BigDecimal(sb.toString()));
                    sb.delete(0, sb.length());
                }
                while (!oprStack.isEmpty())
                {
                    t = oprStack.peek();
                    if (t == '(' || t == '+' || t == '-')
                    {
                        break;
                    }
                    numStack.push(calculate(numStack.pop(), oprStack.pop(), numStack.pop(), ctx));
                }
                oprStack.push(c);
                b = true;
                continue;
            }
            if ((c >= '0' && c <= '9') || c == '.')
            {
                sb.append(c);
                b = false;
                continue;
            }
            throw new Exception("表达式中存在未知字符" + c);
        }
        return numStack.pop();
    }

    /**
     * @param numStack
     * @param c
     */
    private static BigDecimal calculate(BigDecimal r, char c, BigDecimal l, MathContext t)
    {
        switch (c)
        {
            case '+':
                return l.add(r, t);
            case '-':
                return l.subtract(r, t);
            case '*':
                return l.multiply(r, t);
            case '/':
                return l.divide(r, t);
            case '%':
                return l.divideToIntegralValue(r, t);
            default:
                return BigDecimal.ZERO;
        }
    }

    /**
     * 计算指定表格式的值
     *
     * @param exps 表达式
     * @param scale 计算精度
     * @param stepList 计算步骤列表，记录每一步的计算方法，为NULL时表示不记录运算步骤。<br />
     *        s0:当前要进行计算的步骤；<br />
     *        s1:将进行计算的运算单元；<br />
     *        s2:单元运算结果；<br />
     * @return
     */
    public String calculate(String exps, int scale, List<com.magicpwd._comn.Math> stepList) throws Exception
    {
        if (!Char.isValidate(exps))
        {
            throw new Exception("表达式为空！");
        }

        // 去除表达式中所有空格
        exps = exps.replaceAll("\\s+", "").toLowerCase();
        if (exps.endsWith("="))
        {
            exps = exps.substring(0, exps.length() - 1);
        }
        if (exps.length() < 1)
        {
            throw new Exception("表达式为空！");
        }

        // 是否需要记录运算步骤
        boolean recSteps = stepList != null;

        // 操作数堆栈
        numStack = new Stack<String>();
        // numStack.push("0");
        // 操作符堆栈
        oprStack = new Stack<WOperator>();
        // oprStack.push(new WOperator());

        // 操作数缓冲区
        StringBuilder numBuf = new StringBuilder();
        // 操作符缓冲区
        StringBuilder oprBuf = new StringBuilder();
        // 表达式字符串
        char[] expBuf = ('(' + exps + ')').toCharArray();
        // 临时字符串
        String tmpOpr;
        // 上一个字符（串）是否为操作符，true表示是；false表示否
        boolean lastIsOpr = false;

        // 左括号
        final String[] LBT_EXP =
        {
            //
            MaocEnv.OPR_SLB_EXP,//
            MaocEnv.OPR_MLB_EXP,//
            MaocEnv.OPR_LLB_EXP,//
        };
        final int[] LBT_INT =
        {
            //
            MaocEnv.OPR_SLB_INT,//
            MaocEnv.OPR_MLB_INT,//
            MaocEnv.OPR_LLB_INT,//
        };
        // 右括号
        final String[] RBT_EXP =
        {
            //
            MaocEnv.OPR_SRB_EXP,//
            MaocEnv.OPR_MRB_EXP,//
            MaocEnv.OPR_LRB_EXP,//
        };
        final int[] RBT_INT =
        {
            //
            MaocEnv.OPR_SRB_INT,//
            MaocEnv.OPR_MRB_INT,//
            MaocEnv.OPR_LRB_INT,//
        };

        // 操作符
        final String[] OPR_EXP =
        {
            //
            MaocEnv.OPR_ADD_EXP_EN,// 加
            MaocEnv.OPR_SUB_EXP_EN,// 减
            MaocEnv.OPR_MUL_EXP,// 乘
            MaocEnv.OPR_DIV_EXP,// 除
            MaocEnv.OPR_MOD_EXP,// 取模
            MaocEnv.OPR_POW_EXP,// 次幂
            MaocEnv.OPR_ROT_EXP,// 方根
            MaocEnv.OPR_LOG_EXP,// 10对数
            MaocEnv.OPR_LNE_EXP,// 自然对数
            MaocEnv.OPR_FAC_EXP,// 阶乘
            MaocEnv.OPR_SIN_EXP,//
            MaocEnv.OPR_COS_EXP,//
            MaocEnv.OPR_TAN_EXP,//
            MaocEnv.OPR_SEC_EXP,//
            MaocEnv.OPR_CSC_EXP,//
            MaocEnv.OPR_COT_EXP,//
        };
        final int[] OPR_INT =
        {
            //
            MaocEnv.OPR_ADD_INT,// 加
            MaocEnv.OPR_SUB_INT,// 减
            MaocEnv.OPR_MUL_INT,// 乘
            MaocEnv.OPR_DIV_INT,// 除
            MaocEnv.OPR_MOD_INT,// 取模
            MaocEnv.OPR_POW_INT,// 次幂
            MaocEnv.OPR_ROT_INT,// 方根
            MaocEnv.OPR_LOG_INT,// 10对数
            MaocEnv.OPR_LNE_INT,// 自然对数
            MaocEnv.OPR_FAC_INT,// 阶乘
            MaocEnv.OPR_SIN_INT,//
            MaocEnv.OPR_COS_INT,//
            MaocEnv.OPR_TAN_INT,//
            MaocEnv.OPR_SEC_INT,//
            MaocEnv.OPR_CSC_INT,//
            MaocEnv.OPR_COT_INT,//
        };

        Pattern numPtn = Pattern.compile("^((\\d*\\.\\d+)|(\\d+(\\.\\d*)?))([*]?[eE][-+]?\\d+)?");
        // 循环处理每一个表达式字符
        NEXT_O:
        for (char c : expBuf)
        {
            // 负号
            if (lastIsOpr && (c == '-' || c == '+'))
            {
                if (oprBuf.length() != 0)
                {
                    throw new Exception(Char.format("无法识别的运算符 {0}", oprBuf.toString()));
                }
                lastIsOpr = false;
                numBuf.append(c);
                continue;
            }

            // 操作数
            if ((c >= '0' && c <= '9') || c == '.')
            {
                if (oprBuf.length() != 0)
                {
                    throw new Exception(Char.format("无法识别的运算符 {0}", oprBuf.toString()));
                }
                lastIsOpr = false;
                numBuf.append(c);
                continue;
            }

            // e
            if (c == 'e' || c == 'E')
            {
                if (oprBuf.length() != 0)
                {
                    throw new Exception(Char.format("无法识别的运算符 {0}", oprBuf.toString()));
                }
                lastIsOpr = false;
                BigDecimal e = new BigDecimal(Math.E).setScale(scale, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
                String ep = e.toPlainString();
                numBuf.append(ep);
                exps = exps.replace("e", ep);
                exps = exps.replace("E", ep);
                continue;
            }

            // π
            if (c == 'π')
            {
                if (oprBuf.length() != 0)
                {
                    throw new Exception(Char.format("无法识别的运算符 {0}", oprBuf.toString()));
                }
                lastIsOpr = false;
                BigDecimal e = new BigDecimal(Math.PI).setScale(scale, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
                String ep = e.toPlainString();
                numBuf.append(ep);
                exps = exps.replace("π", ep);
                continue;
            }

            // 操作符
            tmpOpr = oprBuf.append(c).toString();

            // 左括号
            for (int i = 0, j = LBT_EXP.length; i < j; i += 1)
            {
                if (LBT_EXP[i].equals(tmpOpr))
                {
                    lastIsOpr = true;
                    // 清除当前操作符
                    oprBuf.delete(0, oprBuf.length());

                    // 左操作数不为空的情况下，左操作数入栈
                    if (numBuf.length() > 0)
                    {
                        numStack.push(numBuf.toString());
                        numBuf.delete(0, numBuf.length());
                    }

                    // 操作符入栈
                    oprStack.push(new WOperator(tmpOpr, LBT_INT[i]));

                    continue NEXT_O;
                }
            }

            // 右括号
            for (int i = 0, j = RBT_EXP.length; i < j; i += 1)
            {
                if (RBT_EXP[i].equals(tmpOpr))
                {
                    if (lastIsOpr)
                    {
                        throw new Exception("缺少操作数，请确认您输入表达式的是否正确！");
                    }

                    lastIsOpr = false;
                    // 清除当前操作符
                    oprBuf.delete(0, oprBuf.length());

                    if (numBuf.length() > 0)
                    {
                        numStack.push(numBuf.toString());
                        numBuf.delete(0, numBuf.length());
                    }

                    com.magicpwd._comn.Math kvItem;
                    WOperator o;

                    // 循环处理每一个操作符
                    NEXT_I:
                    while (true)
                    {
                        o = oprStack.peek();
                        for (int m = 0, n = LBT_EXP.length; m < n; m += 1)
                        {
                            if (LBT_EXP[m].equals(o.getS()))
                            {
                                // 操作符级别判断
                                if (LBT_INT[m] + 7 == RBT_INT[i])
                                {
                                    oprStack.pop();
                                    if (recSteps)
                                    {
                                        String t = numStack.peek();
                                        String p = LBT_EXP[m] + t + RBT_EXP[m];
                                        stepList.add(new com.magicpwd._comn.Math(exps, p, t));
                                        exps = exps.replace(p, t);
                                    }
                                    break NEXT_I;
                                }
                                throw new Exception(Char.format("您输入的表达式不正确：{0}与{1}不匹配！", LBT_EXP[m], tmpOpr));
                            }
                        }

                        // 运算结果
                        kvItem = calculate(exps, scale);
                        // 统计运算步骤
                        if (recSteps)
                        {
                            stepList.add(kvItem);
                            exps = exps.replace(kvItem.getS1(), kvItem.getS2());
                        }
                    }
                    continue NEXT_O;
                }
            }

            // 运算符运算
            for (int i = 0, j = OPR_EXP.length; i < j; i += 1)
            {
                if (OPR_EXP[i].equalsIgnoreCase(tmpOpr))
                {
                    lastIsOpr = MaocEnv.OPR_FAC_EXP.equals(tmpOpr) ? false : true;
                    // 清除当前操作符
                    oprBuf.delete(0, oprBuf.length());

                    // 左操作数不为空的情况下，左操作数入栈
                    if (numBuf.length() > 0)
                    {
                        numStack.push(numBuf.toString());
                        numBuf.delete(0, numBuf.length());
                    }

                    com.magicpwd._comn.Math kvItem = null;
                    // 当前运算符
                    WOperator newOpr = new WOperator(tmpOpr, OPR_INT[i]);

                    // 循环处理每一个操作符
                    while (true)
                    {
                        // 操作符级别判断
                        if (newOpr.getL() > oprStack.peek().getL())
                        {
                            oprStack.push(newOpr);
                            break;
                        }

                        // 运算结果
                        kvItem = calculate(exps, scale);
                        // 统计运算步骤
                        if (recSteps)
                        {
                            stepList.add(kvItem);
                            exps = exps.replace(kvItem.getS1(), kvItem.getS2());
                        }
                    }
                    break;
                }
            }
        }

        if (oprStack.size() > 0)
        {
            throw new Exception("表达式存在错误，系统无法计算！");
        }

        return numStack.size() != 0 ? numStack.pop() : "";
    }

    /**
     * 双目运算符运算
     *
     * @param opr 操作符
     * @param scale 计算精度
     * @param opds 操作数
     * @return
     */
    private com.magicpwd._comn.Math calculate(String exps, int scale) throws Exception
    {
        String lOpd = null;
        String mOpr = oprStack.pop().getS();
        String rOpd = numStack.pop();
        String tNum;

        com.magicpwd._comn.Math kvItem = new com.magicpwd._comn.Math();

        // 加
        if (MaocEnv.OPR_ADD_EXP_EN.equals(mOpr))
        {
            lOpd = numStack.pop();
            Logs.log("运算：" + lOpd + mOpr + rOpd);
            tNum = new BigDecimal(lOpd).add(new BigDecimal(rOpd)).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(lOpd + mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 减
        if (MaocEnv.OPR_SUB_EXP_EN.equals(mOpr))
        {
            lOpd = numStack.pop();
            Logs.log("运算：" + lOpd + mOpr + rOpd);
            tNum = new BigDecimal(lOpd).subtract(new BigDecimal(rOpd)).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(lOpd + mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 乘
        if (MaocEnv.OPR_MUL_EXP.equals(mOpr))
        {
            lOpd = numStack.pop();
            Logs.log("运算：" + lOpd + mOpr + rOpd);
            tNum = new BigDecimal(lOpd).multiply(new BigDecimal(rOpd)).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(lOpd + mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 除
        if (MaocEnv.OPR_DIV_EXP.equals(mOpr))
        {
            BigDecimal t = new BigDecimal(rOpd);
            if (t.compareTo(new BigDecimal(0)) == 0)
            {
                throw new Exception("除数为0");
            }
            lOpd = numStack.pop();
            Logs.log("运算：" + lOpd + mOpr + rOpd);
            tNum = new BigDecimal(lOpd).divide(t, scale, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(lOpd + mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 取模
        if (MaocEnv.OPR_MOD_EXP.equals(mOpr))
        {
            BigDecimal t = new BigDecimal(rOpd);
            if (t.compareTo(new BigDecimal(0)) == 0)
            {
                throw new Exception("除数为0");
            }
            lOpd = numStack.pop();
            Logs.log("运算：" + lOpd + mOpr + rOpd);
            tNum = new BigDecimal(lOpd).remainder(t).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(lOpd + mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 次幂
        if (MaocEnv.OPR_POW_EXP.equals(mOpr))
        {
            try
            {
                int t = new BigDecimal(rOpd).intValueExact();
                lOpd = numStack.pop();
                tNum = new BigDecimal(lOpd).pow(t).stripTrailingZeros().toPlainString();
                numStack.push(tNum);
                kvItem.setS0(exps);
                kvItem.setS1(lOpd + mOpr + rOpd);
                kvItem.setS2(tNum);
                return kvItem;
            }
            catch (ArithmeticException exp)
            {
                Logs.exception(exp);
                throw new Exception(Char.format("次幂时，指数{0}应为一个整数！", rOpd.toString()));
            }
        }

        // 方根
        if (MaocEnv.OPR_ROT_EXP.equals(mOpr))
        {
            lOpd = numStack.pop();
            BigDecimal tl = new BigDecimal(rOpd);
            BigInteger tr = new BigInteger(lOpd);
            tNum = root(tl, tr.intValue(), scale).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(lOpd + mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 正弦
        if (MaocEnv.OPR_SIN_EXP.equalsIgnoreCase(mOpr))
        {
            BigDecimal t = new BigDecimal(rOpd);
            t = new BigDecimal(Math.sin(t.doubleValue()));
            tNum = t.setScale(scale, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 余弦
        if (MaocEnv.OPR_COS_EXP.equalsIgnoreCase(mOpr))
        {
            BigDecimal t = new BigDecimal(rOpd);
            t = new BigDecimal(Math.cos(t.doubleValue()));
            tNum = t.setScale(scale, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 正切
        if (MaocEnv.OPR_TAN_EXP.equalsIgnoreCase(mOpr))
        {
            BigDecimal t = new BigDecimal(rOpd);
            t = new BigDecimal(Math.tan(t.doubleValue()));
            tNum = t.setScale(scale, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 正割
        if (MaocEnv.OPR_SEC_EXP.equalsIgnoreCase(mOpr))
        {
            BigDecimal t = new BigDecimal(rOpd);
            t = new BigDecimal(Math.asin(t.doubleValue()));
            tNum = t.setScale(scale, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 余割
        if (MaocEnv.OPR_CSC_EXP.equalsIgnoreCase(mOpr))
        {
            BigDecimal t = new BigDecimal(rOpd);
            t = new BigDecimal(Math.acos(t.doubleValue()));
            tNum = t.setScale(scale, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 反正切
        if (MaocEnv.OPR_COT_EXP.equalsIgnoreCase(mOpr))
        {
            BigDecimal t = new BigDecimal(rOpd);
            t = new BigDecimal(Math.atan(t.doubleValue()));
            tNum = t.setScale(scale, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 阶乘
        if (MaocEnv.OPR_FAC_EXP.equals(mOpr))
        {
            BigInteger t = null;
            try
            {
                t = new BigInteger(rOpd);
            }
            catch (Exception exp)
            {
                Logs.exception(exp);
                throw new Exception("阶乘数值只能为正整数！");
            }

            if (t.intValue() <= 0)
            {
                throw new Exception("阶乘值不能小于1！");
            }

            int i = t.intValue();
            while (--i > 1)
            {
                t = t.multiply(BigInteger.valueOf(i));
            }
            tNum = t.toString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(rOpd + mOpr);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // 10的对数
        if (MaocEnv.OPR_LOG_EXP.equalsIgnoreCase(mOpr))
        {
            BigDecimal t = new BigDecimal(rOpd);
            t = new BigDecimal(Math.log10(t.doubleValue()));
            tNum = t.setScale(scale, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        // e的对数
        if (MaocEnv.OPR_LNE_EXP.equalsIgnoreCase(mOpr))
        {
            BigDecimal t = new BigDecimal(rOpd);
            t = new BigDecimal(Math.log(t.doubleValue()));
            tNum = t.setScale(scale, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString();
            numStack.push(tNum);
            kvItem.setS0(exps);
            kvItem.setS1(mOpr + rOpd);
            kvItem.setS2(tNum);
            return kvItem;
        }

        return null;
    }

    /**
     * @param num
     * @param radix
     * @param precision
     * @return
     */
    private static BigDecimal root(BigDecimal num, int radix, int precision)
    {
        BigDecimal t = BigDecimal.ONE;
        BigDecimal r = BigDecimal.ONE;
        BigDecimal e = t.subtract(num);
        BigDecimal a = new BigDecimal(radix);
        MathContext c = new MathContext(precision);
        BigDecimal p = new BigDecimal(45).pow(-precision, c);
        BigDecimal w;
        while (e.abs().compareTo(p) > 0)
        {
            t = t.subtract(e.divide(r.multiply(a), c));
            w = t;
            int k = 1;
            while (k++ < radix)
            {
                w = w.multiply(t);
            }
            r = w.divide(t);
            e = w.subtract(num);
        }
        return t;
    }
}
