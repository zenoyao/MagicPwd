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

/**
 *
 * @author Amon
 */
public class Hash
{
    /**
     * 大质数
     */
    static int MASK = 0x8765fed1;

    public static String hash(boolean bigCase)
    {
        return Util.encodeLong(Date.utcDate().getTimeInMillis(), bigCase);
    }

    /**
     * 加法hash
     * @param text 字符串
     * @return hash结果
     */
    public static int add(String text)
    {
        int hash = text.length();
        for (char c : text.toCharArray())
        {
            hash += c;
        }
        return (hash % MASK);
    }

    /**
     * 旋转hash
     * @param text 输入字符串
     * @return hash值
     */
    public static int rot(String text)
    {
        int hash = text.length();
        for (char c : text.toCharArray())
        {
            hash = (hash << 4) ^ (hash >> 28) ^ c;
        }
        return (hash % MASK);
        // return (hash ^ (hash>>10) ^ (hash>>20));
    }

    /**
     * 一次一个hash
     * @param text 输入字符串
     * @return 输出hash值
     */
    public static int one(String text)
    {
        int hash = 0;
        for (char c : text.toCharArray())
        {
            hash += c;
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        return hash;
        // return (hash & MASK);
    }

    /**
     * MySQL中出现的字符串Hash函数
     * @param text
     * @return
     */
    public static int sql(String text)
    {
        int hash = 1;
        int t = 4;

        for (byte b : text.getBytes())
        {
            hash ^= (((hash & 63) + t) * (b)) + (hash << 8);
            t += 3;
        }

        return hash;
    }

    /**
     * Bernstein's hash
     * @param text 输入字节数组
     * @return 结果hash
     */
    public static long ber(String text)
    {
        int hash = 0;
        for (char c : text.toCharArray())
        {
            hash = 33 * hash + c;
        }

        return hash;
    }

    /**
     * Universal Hashing
     * @param text
     * @param tab
     * @return
     */
    public static int uni(String text, int[] tab)
    {
        char[] c = text.toCharArray();
        int hash = c.length;
        for (int i = 0, j = c.length << 3; i < j; i += 8)
        {
            char k = c[i >> 3];
            if ((k & 0x01) == 0)
            {
                hash ^= tab[i + 0];
            }
            if ((k & 0x02) == 0)
            {
                hash ^= tab[i + 1];
            }
            if ((k & 0x04) == 0)
            {
                hash ^= tab[i + 2];
            }
            if ((k & 0x08) == 0)
            {
                hash ^= tab[i + 3];
            }
            if ((k & 0x10) == 0)
            {
                hash ^= tab[i + 4];
            }
            if ((k & 0x20) == 0)
            {
                hash ^= tab[i + 5];
            }
            if ((k & 0x40) == 0)
            {
                hash ^= tab[i + 6];
            }
            if ((k & 0x80) == 0)
            {
                hash ^= tab[i + 7];
            }
        }
        return (hash & MASK);
    }

    /**
     * Zobrist Hashing
     * @param text
     * @param tab
     * @return
     */
    public static int zob(String text, int[][] tab)
    {
        int hash = text.length();
        char[] c = text.toCharArray();
        for (int i = 0; i < c.length; ++i)
        {
            hash ^= tab[i][c[i]];
        }
        return (hash & MASK);
    }

    /**
     * 32位FNV算法
     * @param text 字符串
     * @return int值
     */
    public static int fnv(String text)
    {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (char c : text.toCharArray())
        {
            hash = (hash ^ c) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }

    /**
     * Thomas Wang的整数算法
     * @param hash
     * @return
     */
    public static int twh(int hash)
    {
        hash += ~(hash << 15);
        hash ^= (hash >>> 10);
        hash += (hash << 3);
        hash ^= (hash >>> 6);
        hash += ~(hash << 11);
        hash ^= (hash >>> 16);
        return hash;
    }

    /**
     * RS算法
     * @param text
     * @return
     */
    public static int rsh(String text)
    {
        int b = 378551;
        int a = 63689;
        int hash = 0;

        for (char c : text.toCharArray())
        {
            hash = hash * a + c;
            a = a * b;
        }

        return (hash & 0x7FFFFFFF);
    }

    /**
     * JS算法
     * @param text
     * @return
     */
    public static int jsh(String text)
    {
        int hash = 1315423911;

        for (char c : text.toCharArray())
        {
            hash ^= ((hash << 5) + c + (hash >> 2));
        }

        return (hash & 0x7FFFFFFF);
    }

    /**
     * PJW算法
     * @param text
     * @return
     */
    public static int pjw(String text)
    {
        int hash = 0;

        int b = 32;
        int q = (b * 3) / 4;
        int e = b / 8;
        int h = 0xFFFFFFFF << (b - e);
        int t = 0;

        for (char c : text.toCharArray())
        {
            hash = (hash << e) + c;
            t = hash & h;
            if (t != 0)
            {
                hash = ((hash ^ (t >> q)) & (~h));
            }
        }

        return (hash & 0x7FFFFFFF);
    }

    /**
     * ELF算法
     * @param text
     * @return
     */
    public static int elf(String text)
    {
        int hash = 0;
        int t = 0;

        for (char c : text.toCharArray())
        {
            hash = (hash << 4) + c;
            t = (int) (hash & 0xF0000000L);

            if (t != 0)
            {
                hash ^= (t >> 24);
                hash &= ~t;
            }
        }

        return (hash & 0x7FFFFFFF);
    }

    /**
     * BKDR算法
     * @param text
     * @return
     */
    public static int bkd(String text)
    {
        int seed = 131; // 31 131 1313 13131 131313 etc..
        int hash = 0;

        for (char c : text.toCharArray())
        {
            hash = (hash * seed) + c;
        }

        return (hash & 0x7FFFFFFF);
    }
    /* End Of BKDR Hash Function */

    /**
     * SDBM算法
     * @param text
     * @return
     */
    public static int sdb(String text)
    {
        int hash = 0;

        for (char c : text.toCharArray())
        {
            hash = c + (hash << 6) + (hash << 16) - hash;
        }

        return (hash & 0x7FFFFFFF);
    }

    /**
     * DJB算法
     * @param text
     * @return
     */
    public static int djb(String text)
    {
        int hash = 5381;

        for (char c : text.toCharArray())
        {
            hash = ((hash << 5) + hash) + c;
        }

        return (hash & 0x7FFFFFFF);
    }

    /**
     * DEK算法
     * @param str
     * @return
     */
    public static int dek(String text)
    {
        int hash = text.length();

        for (char c : text.toCharArray())
        {
            hash = ((hash << 5) ^ (hash >> 27)) ^ c;
        }

        return (hash & 0x7FFFFFFF);
    }

    /**
     * AP算法
     * @param text
     * @return
     */
    public static int aph(String text)
    {
        int hash = 0;
        char[] c = text.toCharArray();
        for (int i = 0, j = c.length; i < j; i++)
        {
            hash ^= ((i & 1) == 0) ? ((hash << 7) ^ c[i] ^ (hash >> 3)) : (~((hash << 11) ^ c[i] ^ (hash >> 5)));
        }
        // return (hash & 0x7FFFFFFF);
        return hash;
    }

    /**
     * JAVA自己带的算法
     * @param text
     * @return
     */
    public static int jha(String text)
    {
        int h = 0;
        for (char c : text.toCharArray())
        {
            h = 31 * h + c;
        }
        return h;
    }

    /**
     * 混合hash算法，输出64位的值
     * @param text
     * @return
     */
    public static long mix(String text)
    {
        long hash = text.hashCode();
        hash <<= 32;
        hash |= fnv(text);
        return hash;
    }
}
