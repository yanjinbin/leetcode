import java.io.*;
import java.util.*;

public class Main {
    // 落谷OI提交用的模版
    public static void main(String args[]) {
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt();
        int M = cin.nextInt();


    }


    // 分组背包问题
    public static int GroupPack(int N, int V, int[] C, int[] W, int[] G) {
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


    // 多重背包问题 多重背包问题建立在01背包和完全背包问题问题基础上
    public static int MultiPack(int N, int V, int[] C, int[] W, int[] M) {
        int[] dp = new int[V + 1];
        for (int i = 0; i < N; i++) {
            MultiplePack(C[i], W[i], M[i], dp, V);
        }
        return dp[V];
    }

    public static void MultiplePack(int ci, int wi, int mi, int[] dp, int V) {
        if (ci * mi >= V) {
            CompletePack(ci, wi, dp, V);
            return;
        }
        int s = 1;
        while (s < mi) {//条件：amount-s>0
            ZeroOnePack(s * ci, s * wi, dp, V);
            mi -= s;
            s *= 2;
        }
        ZeroOnePack(mi * ci, mi * wi, dp, V);
    }

    // 抽象化: 抽取01背包问题为处理一件物品
    public static int ZeroOnePack(int ci, int wi, int[] dp, int V) {
        for (int j = V; j >= ci; j--) {
            dp[j] = Math.max(dp[j], dp[j - ci] + wi);
        }
        return dp[V];// 返回的是容量为V时候,处理一件物品获取的最大值
    }

    // 抽象化: 对一种物品的处理过程
    public static int CompletePack(int ci, int wi, int[] dp, int V) {
        for (int j = 0; j <= V; j++) {
            if (j >= ci) dp[j] = Math.max(dp[j], dp[j - ci] + wi);
        }
        return dp[V];
    }
}