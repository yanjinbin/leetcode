package com.yanjinbin.leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Graph {


    // https://algs4.cs.princeton.edu/40graphs/
    // 最短路径问题 
    int[] dist;

    public boolean relax(int u, int v) {
        if (dist[v] > dist[u] + weight(u, v)) {
            dist[v] = dist[u] + weight(u, v);
            return true;
        } else {
            return false;
        }
    }

    public int weight(int u, int v) {
        return -1;
    }


    // floyd 算法  实现： https://bit.ly/39x6XAj

    // P2888 [USACO07NOV]Cow Hurdles S
    // 求跨栏最小高度
    public static void cowHurdles() {
        Scanner cin = new Scanner(System.in);
        int INF = Integer.MAX_VALUE;
        int n = cin.nextInt();
        int m = cin.nextInt();
        int t = cin.nextInt();
        int[][] dist = new int[n + 1][n + 1];
        // INIT 
        for (int i = 0; i < dist.length; i++) {
            Arrays.fill(dist[i], INF);
        }
        for (int i = 0; i < m; i++) {
            int a = cin.nextInt();
            int b = cin.nextInt();
            int c = cin.nextInt();
            dist[a][a] = 0;
            dist[b][b] = 0;
            dist[a][b] = c;
        }
        // floyd算法
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    dist[i][j] = Math.min(dist[i][j], Math.max(dist[i][k], dist[k][j]));
                }
            }
        }

        for (int i = 1; i <= t; i++) {
            int x = cin.nextInt();
            int y = cin.nextInt();
            System.out.println(dist[x][y] == INF ? -1 : dist[x][y]);
        }

    }

    public int cnt;
    public Edge[] e1; // 1_based index
    public Edge[] e2;
    public int[] h1;
    public int[] h2;
    public int[] d1;
    public int[] d2;
    public boolean[] in;

    // 构建正向图和反向图，进行SPFA ,因为SPFA是单源最短路径
    public void silverCowParty() {
        int INF = 1000 * 100000 + 1;
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt(), M = cin.nextInt(), S = cin.nextInt();
        d1 = new int[N + 1];
        d2 = new int[N + 1];
        Arrays.fill(d1, INF);
        Arrays.fill(d2, INF);
        h1 = new int[N + 1];
        h2 = new int[N + 1];
        in = new boolean[N + 1];
        Arrays.fill(h1, -1);
        Arrays.fill(h2, -1);
        e1 = new Edge[M + 1];
        e2 = new Edge[M + 1];

        for (int i = 1; i <= M; i++) {
            int a = cin.nextInt(), b = cin.nextInt(), l = cin.nextInt();
            cnt++;
            e1[cnt] = new Edge(); // 要new一个阿 别忘记了 否则NPE
            e1[cnt].to = b;
            e1[cnt].next = h1[a];
            h1[a] = cnt;
            e1[cnt].w = l;

            e2[cnt] = new Edge();
            e2[cnt].to = a;
            e2[cnt].next = h2[b];
            e2[cnt].w = l;
            h2[b] = cnt;
        }
        //SPFA
        SPFA1(S);// 正向
        Arrays.fill(in, false);
        SPFA2(S);// 反向
        int ans = 0;
        for (int i = 1; i <= N; i++) {
            ans = Math.max(ans, d1[i] + d2[i]);
        }
        System.out.println(ans);
    }

    // 单源最短路径
    public void SPFA1(int s) {
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        in[s] = true;
        d1[s] = 0;
        while (!q.isEmpty()) {
            int t = q.poll();
            in[t] = false;
            for (int i = h1[t]; i != -1; i = e1[i].next) {
                int v = e1[i].to;
                int w = e1[i].w;
                if (d1[v] > d1[t] + w) {
                    d1[v] = d1[t] + w;
                    if (!in[v]) {
                        in[v] = true;
                        q.add(v);
                    }
                }
            }
        }
    }

    // 单源最短路径
    public void SPFA2(int s) {
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        in[s] = true;
        d2[s] = 0;
        while (!q.isEmpty()) {
            int t = q.poll();
            in[t] = false;
            for (int i = h2[t]; i != -1; i = e2[i].next) {
                int v = e2[i].to;
                int w = e2[i].w;
                if (d2[v] > d2[t] + w) {
                    d2[v] = d2[t] + w;
                    if (!in[v]) {
                        in[v] = true;
                        q.add(v);
                    }
                }
            }
        }
    }


    // p2419 [USACO08JAN]Cow Contest S
    // P2910 Clear And Present Danger S

    // Bellman-Ford/SPFA 算法  wiki 定义： https://bit.ly/39z3lOr  https://algs4.cs.princeton.edu/44sp/BellmanFordSP.java.html
    // P1186 1144 1744 AT3883 AT2154 UVA721


    // dijkstra 算法  实现： https://bit.ly/3dJiPmk https://algs4.cs.princeton.edu/44sp/DijkstraSP.java.html
    public int dijkstra() {
        return -1;
    }


    // 最小生成树  
    // Prim


    // Kruskal 
}