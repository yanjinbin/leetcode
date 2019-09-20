package com.yanjinbin.leetcode;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

    // 抽象化: 抽取01背包问题为处理一件物品一种费用的过程
    public int ZeroOnePack(int ci, int wi, int[] dp, int V) {
        for (int j = V; j >= ci; j--) {
            dp[j] = Math.max(dp[j], dp[j - ci] + wi);
        }
        return dp[V];// 返回的是容量为V时候,处理一件物品获取的最大值
    }

    // 抽象画_
    public int _2DZeroOnePack(int ci, int di, int wi, int[][] dp, int V, int U) {
        for (int i = V; i >= ci; i--) {
            for (int j = U; j >= di; j--) {
                dp[i][j] = Math.max(dp[i][j], dp[i - ci][j - di] + wi);
            }
        }
        return dp[V][U];
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

    // 抽象化: 对一种物品的处理过程 一维
    public int CompletePack(int ci, int wi, int[] dp, int V) {
        for (int j = 0; j <= V; j++) {
            if (j >= ci) dp[j] = Math.max(dp[j], dp[j - ci] + wi);
        }
        return dp[V];
    }

    // 二维
    public int _2DCompletePack(int ci, int di, int wi, int[][] dp, int V, int U) {
        for (int i = 0; i <= V && i >= ci; i++) {
            for (int j = 0; j <= U && j >= di; j++) {
                dp[i][j] = Math.max(dp[i][j], dp[i - ci][j - di] + wi);
            }
        }
        return dp[V][U];
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

    // 抽象话 一维维度
    public int MultiplePack(int ci, int wi, int mi, int[] dp, int V) {
        if (ci * mi >= V) {
            CompletePack(ci * mi, wi * mi, dp, V);
            return dp[V];
        }
        int k = 1;
        while (k < mi) {
            ZeroOnePack(ci * k, wi * k, dp, V);
            mi = mi - k;
            k = 2 * k;
        }
        ZeroOnePack(ci * mi, wi * mi, dp, V);
        return dp[V];
    }

    // 抽象化: 二维维度
    public int _2DMultiplePack(int ci, int di, int wi, int mi, int[][] dp, int V, int U) {
        if (ci * mi >= V || di * mi >= U) {
            _2DCompletePack(ci, di, wi, dp, V, U);
            return dp[V][U];
        }
        int k = 1;
        while (k < mi) {
            _2DZeroOnePack(ci, di, wi, dp, V, U);
            mi = mi - k;
            k = 2 * k;
        }
        _2DZeroOnePack(ci * mi, wi * mi, wi, dp, V, U);
        return dp[V][U];
    }

    // 混合背包问题
    public int MixPack(int N, int V, int[] C, int[] W, int[] M) {
        int[] dp = new int[V + 1];
        for (int i = 0; i < N; i++) {
            int val = M[i];
            if (val == 0) { // 完全背包问题
                CompletePack(C[i], W[i], dp, V);
            } else if (val == 1) { // 01 背包
                ZeroOnePack(C[i], W[i], dp, V);
            } else { // 混合背包
                MultiplePack(C[i], W[i], val, dp, V);
            }
        }
        return dp[V];
    }


    // 474 二维费用背包问题 http://bit.ly/32L0X3W http://bit.ly/2LyFQfx
    // V,U代表背包的2个维度的费用,Ci和Di分别代表第I件物品消耗 V和U的2种费用
    public int _2DPack(int N, int V, int U, int[] C, int[] D, int[] W, int[] M) {
        int[][] dp = new int[V + 1][U + 1];
        for (int i = 0; i < N; i++) {
            int val = M[i];
            if (val == 0) {
                _2DCompletePack(C[i], D[i], W[i], dp, V, U);
            } else if (val == 1) {
                _2DZeroOnePack(C[i], D[i], W[i], dp, V, U);
            } else {
                _2DMultiplePack(C[i], D[i], W[i], M[i], dp, V, U);
            }
        }
        return dp[V][U];
    }


    // 分组背包问题
    public int GroupPack(int N, int V, int[] C, int[] W, int[] G) {
        int K = Integer.MIN_VALUE;
        int[][] memo = new int[N + 1][N + 1];// memo和cnt 均+1的原因是因为 需要处理所有元素都属于同一组的情况
        int[] cnt = new int[N + 1];
        int[] dp = new int[V + 1];
        for (int i = 0; i < N; i++) {
            int gi = G[i];
            cnt[gi]++; // 1 based
            memo[gi][cnt[gi]] = i; // 1 based
            K = Math.max(K, gi);
        }
        for (int k = 1; k <= K; k++) {
            for (int v = V; v >= 0; v--) {
                for (int i = 1; i <= cnt[k]; i++) {
                    int px = memo[k][i];
                    if (v >= C[px]) {
                        dp[v] = Math.max(dp[v], dp[v - C[px]] + W[px]);
                    }
                }
            }
        }
        return dp[V];
    }

    // 错误的!!!
    // https://www.cnblogs.com/five20/p/7806278.html#commentform
    // https://www.luogu.org/problemnew/solution/P1064
    // 有依赖的背包问题
    // V表示背包容量,N表示物件总数,C表示物品费用,W表示物品价值,M代表物件的主从关系(0表示主件,非0表示附件枞树主件编号)
    // 思路 对于一个主件及其附件 采用分组背包问题思想解决
    public int TopologyPack() {
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt();
        int M = cin.nextInt();
        int[] v = new int[M];
        int[] p = new int[M];
        int[] t = new int[M];
        int[] dp = new int[N + 1];
        int[] g = new int[N + 1];

        for (int i = 1; i <= M; i++) {
            v[i] = cin.nextInt();
            p[i] = cin.nextInt() * v[i];
            t[i] = cin.nextInt();

        }
        for (int i = 1; i <= M; i++) {
            if (t[i] == 0) {// 主件
                for (int j = 1; j < v[i]; j++) g[j] = 0;
                for (int j = v[i]; j <= N; j++) g[j] = dp[j - v[i]] + p[i];
                for (int j = 1; j <= M && t[i] == i; j++)
                    for (int k = N; k >= v[i] + v[j]; k--)
                        g[k] = Math.max(g[k], g[k - v[j]] + p[j]);
                for (int j = v[i]; j <= N; j++) dp[j] = Math.max(dp[j], g[j]);

            }
        }
        return dp[N];
    }

    // 区间DP https://oi-wiki.org/dp/interval/
    // 石子合并 https://www.luogu.org/problem/P1880
    public int[] mergeStone(int N, int[] stones) {
        int[] sum = new int[2 * N];
        int[] s = new int[2 * N];
        for (int i = 0; i < N; i++) s[i + N] = s[i] = stones[i];
        sum[0] = stones[0];
        int[][] minDp = new int[2 * N][2 * N];
        int[][] maxDp = new int[2 * N][2 * N];
        for (int i = 1; i < 2 * N; i++) sum[i] = sum[i - 1] + s[i];
        for (int len = 1; len < N; len++) {// len 为何不取0 呢呢 todo
            for (int i = 1, j = i + len; i < 2 * N && j < 2 * N; i++, j = i + len) {
                minDp[i][j] = Integer.MAX_VALUE;
                maxDp[i][j] = Integer.MIN_VALUE;
                for (int k = i; k < j; k++) { // 假设len =0; 则不进行下列循环
                    minDp[i][j] = Math.min(minDp[i][j], minDp[i][k] + minDp[k + 1][j] + sum[j] - sum[i - 1]);
                    maxDp[i][j] = Math.max(maxDp[i][j], maxDp[i][k] + maxDp[k + 1][j] + sum[j] - sum[i - 1]);
                }
            }
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < N; i++) {
            min = Math.min(min, minDp[i][i + N - 1]);
            max = Math.max(max, maxDp[i][i + N - 1]);
        }
        System.out.printf("%d\n%d", min, max);
        return new int[]{min, max};
    }


    // 树形DP https://oi-wiki.org/dp/tree/
    // 没有上司的舞会 https://www.luogu.org/problem/P1352
    // https://www.cnblogs.com/hanruyun/p/9788170.html
    // 树形DP 是一种思想,解决有依赖型背包问题的好方法
    //
    public int DanceParty() {/*
        List<List<Integer>> tree = new ArrayList<List<Integer>>();
        int[] flag = new int[L.length];
        for (int i = 0; i < L.length + 1; i++) {
            int l = L[i];
            // int ri = R[i];
            tree.get(l).add(i);
            flag[l] = 1;
        }*/
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt();
        int[] R = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            R[i] = cin.nextInt();
        }
        int[] flag = new int[N];
        List<List<Integer>> tree = new ArrayList<List<Integer>>();
        for (int i = 1; i <= N - 1; i++) {
            int k = cin.nextInt();
            int l = cin.nextInt();
            tree.get(l).add(k);
            flag[k] = 1;
        }
        int root = -1;
        for (int i = 0; i < N; i++) {
            if (flag[i] != 1) {
                root = i;
                break;
            }
        }
        int[][] dp = new int[N][N];
        return Math.max(dp[root][0], dp[root][1]);
    }

    public void dp(int root, int[][] dp, List<List<Integer>> tree, int[] R) {
        dp[root][0] = 0;
        dp[root][1] = R[root];
        for (int i = 0; i < tree.get(root).size(); i++) {
            int son = tree.get(root).get(i);
            dp(son, dp, tree, R);
            dp[root][0] += Math.max(dp[son][0], dp[son][1]);
            dp[root][1] += dp[son][0];
        }
    }


}