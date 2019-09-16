import java.io.*;
import java.util.*;

public class Main {
    // 落谷OI提交用的模版
    public static void main(String args[]) {
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt(), V = cin.nextInt();
        int[] C = new int[N];
        int[] W = new int[N];
        int[] M = new int[N];
        for (int i = 0; i < N; i++) {
            W[i] = cin.nextInt();
            C[i] = cin.nextInt();
            M[i] = cin.nextInt();
        }
        int ret = MultiPack(N, V, C, W, M);
        System.out.println(ret);
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