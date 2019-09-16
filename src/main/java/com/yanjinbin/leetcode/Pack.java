package com.yanjinbin.leetcode;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

// https://oi-wiki.org/dp dp大纲
// dp 比较经典的 01背包问题 完全背包问题 多重背包问题 区间dp
// 背包问题九讲 https://drive.google.com/file/d/1VTBouzm_LrpTGdbQ8PtWg5P96CAo4hiu/view?usp=sharing
public class Pack {


    // 01背包问题 代表题目 https://www.luogu.org/problem/P2871
    // 01背包问题
    // 有N件物品,背包容量为V,每次装入第I件物品的花费是Ci,获得的价值是Wi,试求出,在尽量装满背包,扣除费用之后背包收益最大,
    // Time Complexity: O(VN) , Space Complexity: O(VN)
    public int ZeroOnePack(int N, int V, int[] C, int[] W) {
        int[][] dp = new int[N + 1][V + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= V; j++) {
                if (C[i] > j) dp[i + 1][j] = dp[i][j];
                else dp[i + 1][j] = Math.max(dp[i][j], dp[i][j - C[i]] + W[i]);
            }
        }
        return dp[N][V];
    }

    // MLE error: https://www.luogu.org/record/24011470
    public int ZeroOnePack_ME(int N, int V, int[] C, int[] W) {
        int[][] dp = new int[N + 1][V + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= V; j++) {
                if (C[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - C[i - 1]] + W[i - 1]);
                }
            }
        }
        return dp[N][V];
    }

    public int ZeroOnePack_Wrong(int N, int V, int[] C, int[] W) {
        int[][] dp = new int[N + 1][V + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= V; j++) { // 递增
                System.out.println(i + " || " + j);
                if (C[i] > j) dp[i][j] = dp[i - 1][j];
                    // 这个方程式 依旧是有问题的 并不是拿个状态转移方程式 因为C[i] W[i]的取值  注意i代表的是什么意思
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - C[i]] + W[i]);
            }
        }
        return dp[N][V];
    }


    // 降维,二维DP --> 一维DP
    // 代码通过测试: https://www.luogu.org/record/24012605
    // Time Complexity: O(VN) , Space Complexity: O(V)
    public int ZeroOnePack_Optimize(int N, int V, int[] C, int[] W) {
        int[] dp = new int[V + 1];
        for (int i = 0; i < N; i++) {
            for (int j = V; j >= C[i]; j--) { // !!! 递减顺序  以及 C,W数组是0 based index.
                dp[j] = Math.max(dp[j], dp[j - C[i]] + W[i]);
            }
        }
        return dp[V];
    }

    // 抽象化: 抽取01背包问题为处理一件物品
    public int ZeroOnePack(int ci, int wi, int[] dp, int V) {
        for (int j = V; j >= ci; j--) {
            dp[j] = Math.max(dp[j], dp[j - ci] + wi);
        }
        return dp[V];// 返回的是容量为V时候,处理一件物品获取的最大值
    }

    // 完全背包问题 https://www.luogu.org/problem/P1616
    // 问题描述: 此时问题的问法变化了, N种物品,容量V,每种物品的费用Ci,价值Wi,如何装入(第I种物品数量不限), 才能使得价值最大
    // 提交记录: https://www.luogu.org/record/24014209
    public int CompletePack_Original(int N, int V, int[] C, int[] W) {
        int[][] dp = new int[N + 1][V + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= V; j++) {
                if (j >= C[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - C[i - 1]] + W[i - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[N][V];
    }

    // 降维: 2维-->1维
    // 提交记录: https://www.luogu.org/record/24014348
    public int CompletePack_Optimize(int N, int V, int[] C, int[] W) {
        int[] dp = new int[V + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= V; j++) {
                if (j >= C[i - 1]) dp[j] = Math.max(dp[j], dp[j - C[i - 1]] + W[i - 1]);
                else dp[j] = dp[j];
            }
        }
        return dp[V];
    }

    // 抽象化: 对一种物品的处理过程
    public int CompletePack(int ci, int wi, int[] dp, int V) {
        for (int j = 0; j <= V; j++) {
            if (j >= ci) dp[j] = Math.max(dp[j], dp[j - ci] + wi);
        }
        return dp[V];
    }

    // 多重背包问题 多重背包问题建立在01背包和完全背包问题问题基础上
    // 提交记录: https://www.luogu.org/record/24015733
    public int MultiPack(int N, int V, int[] C, int[] W, int[] M) {
        int[] dp = new int[V + 1];
        for (int i = 0; i < N; i++) {
            MultiplePack(C[i], W[i], M[i], dp, V);
        }
        return dp[V];
    }

    public void MultiplePack(int ci, int wi, int mi, int[] dp, int V) {
        if (ci * mi >= V) {
            CompletePack(ci * mi, wi * mi, dp, V);
            return;
        }
        int k = 1;
        while (k < mi) {
            ZeroOnePack(ci * k, wi * k, dp, V);
            mi = mi - k;
            k = 2 * k;
        }
        ZeroOnePack(ci * mi, wi * mi, dp, V);
    }
}