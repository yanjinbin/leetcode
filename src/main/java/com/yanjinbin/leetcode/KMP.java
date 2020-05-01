package com.yanjinbin.leetcode;

import java.util.Arrays;
// 最后看这篇文章，才能弄懂哦 http://bit.ly/2QPXVZK
// http://bit.ly/2qNlirY
// http://bit.ly/2XNgUoV
public class KMP {


    // 暴力匹配算法
    public int violentMatch(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
        int i = 0, j = 0;
        while (i < sLen && j < pLen) {
            if (s.charAt(i) == p.charAt(j)) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        if (j == pLen) return i - pLen;
        else return -1;
    }

    // https://www.cnblogs.com/yjiyjige/p/3263858.html
    public static int KMP(String ts, String ps) {
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        int i = 0; // 主串的位置
        int j = 0; // 模式串的位置
        int[] next = getNext(ps);
        while (i < t.length && j < p.length) {
            if (j == -1 || t[i] == p[j]) { // 当j为-1时，要移动的是i，当然j也要归0
                i++;
                j++;
            } else {
                // i不需要回溯了
                // i = i - j + 1;
                j = next[j]; // j回到指定位置
            }
        }

        if (j == p.length) {
            return i - j;
        } else {
            return -1;
        }

    }

    public static int[] getNext(String ps) {
        char[] p = ps.toCharArray();
        int[] next = new int[p.length];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                if (p[++j] == p[++k]) {// 当有2个相等的字符时候要跳过
                    next[j] = next[k];
                } else {
                    next[j] = k;
                }
            } else {
                // 思路： 看图去理解，对称性，模式的自我匹配
                k = next[k];
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String s = "ABCDABD";
        int[] next = getNext(s);
        System.out.println(Arrays.toString(next));
    }
}
