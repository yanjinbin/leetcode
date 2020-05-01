package com.yanjinbin.leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Graph {


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

    // Bellman-Ford/SPFA 算法  wiki 定义： https://bit.ly/39z3lOr
    // P1186 1144 1744 AT3883 AT2154 UVA721 p2419 P2910

    public int cnt;
    public Edge[] e1; // 1_based index
    public Edge[] e2;
    public int[] h1; //另外还有一个数组head[],它是用来表示以i为起点的第一条边存储的位置,实际上你会发现这里的第一条边存储的位置其实
    //在以i为起点的所有边的最后输入的那个编号.
    public int[] h2;
    public int[] d1;
    public int[] d2;
    public boolean[] in;

    // P1821
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
            int u = q.poll();
            in[u] = false;
            for (int i = h1[u]; i != -1; i = e1[i].next) {
                int v = e1[i].to;
                int w = e1[i].w;
                if (d1[v] > d1[u] + w) {
                    d1[v] = d1[u] + w;
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
            int u = q.poll();
            in[u] = false;
            for (int i = h2[u]; i != -1; i = e2[i].next) {
                int v = e2[i].to;
                int w = e2[i].w;
                if (d2[v] > d2[u] + w) {
                    d2[v] = d2[u] + w;
                    if (!in[v]) {
                        in[v] = true;
                        q.add(v);
                    }
                }
            }
        }
    }

    // dijkstra 算法  实现： https://bit.ly/3dJiPmk
    // P5837

   /* public Edge[] e;
    public int[] limit;
    public int[] d;
    public boolean[] seen;
    public int INF = 1001;
    public int[] h;

    public int milkPumpingG() {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt(), m = cin.nextInt();
        // init
        seen = new boolean[n + 1];
        d = new int[n + 1];
        e = new Edge[2 * n + 1];
        limit = new int[n + 1];
        int cnt = 0;

        for (int i = 1; i <= m; i++) {
            // 无向图，因故正相反向都需要构建
            cnt++;
            int a = cin.nextInt(), b = cin.nextInt(), c = cin.nextInt(), f = cin.nextInt();
            limit[i] = f;
            // 正向
            e[cnt] = new Edge();
            e[cnt].to = b;
            e[cnt].next = h[a];
            e[cnt].cost = c;
            e[cnt].limit = f;
            h[a] = cnt;
            // 反向
            cnt++;
            e[cnt] = new Edge();
            e[cnt].to = a;
            e[cnt].next = h[b];
            e[cnt].cost = c;
            e[cnt].limit = f;
            h[b] = cnt;

        }

        // traversal
        int ans = 0;
        for (int i = 1; i <= m; i++) {
            ans = Math.max(ans, limit[i] * 1000000 / dijkstra(limit[i], n));
        }
        System.out.println(ans);
        return ans;
    }


    public int dijkstra(int limit, int N) {
        Queue<Edge> q = new PriorityQueue<>((o1, o2) -> (o1.cost - o2.cost));
        Arrays.fill(seen, false);
        Arrays.fill(d, INF);
        d[1] = 0;
        Edge eg = new Edge();
        eg.limit = limit;
        eg.cost = 0;
        eg.to = 1;
        q.add(eg);
        while (!q.isEmpty()) {
            int u = q.poll().to;
            if (seen[u]) continue;
            seen[u] = true;
            for (int i = h[u]; i != -1; i = e[i].next) {
                Edge v = e[i];
                if (v.limit > limit && d[v.to] > d[u] + v.cost) {// relax
                    d[v.to] = d[u] + v.cost;
                    v.limit = limit;
                    v.cost = d[v.to];
                    q.add(v);
                }
            }
        }
        return d[N];
    }
*/

    public static List<Node>[] e;
    public static int[] limit;
    public static int[] dis;
    public static boolean[] seen;
    public static int INF = 0x7F7F7F7F;
    // 1001 不行的原因 是数据范围不满足，假设经过说有点，1000*1000+1000=1000000
    //                                                  dis[N]+N.cost = 1001000

    public void milkPumpingG() {
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt(), M = cin.nextInt();
        limit = new int[M + 1];
        e = new LinkedList[N + 1];
        seen = new boolean[N + 1];
        dis = new int[N + 1];

        for (int i = 1; i <= M; i++) {
            int a = cin.nextInt(), b = cin.nextInt(), x = cin.nextInt();
            limit[i] = cin.nextInt();
            if (e[a] == null) {
                e[a] = new LinkedList<>();
            }
            e[a].add(new Node(b, x, limit[i]));
            if (e[b] == null) {
                e[b] = new LinkedList<>();
            }
            e[b].add(new Node(a, x, limit[i]));
        }
        int ans = 0;
        for (int i = 1; i <= M; i++) {
            ans = Math.max(ans, limit[i] * 1000000 / dijkstra(limit[i], N));
        }
        System.out.println(ans);
    }

    public static int dijkstra(int limit, int N) {
        PriorityQueue<Node> Q = new PriorityQueue<>((o1, o2) -> (o1.cost - o2.cost));
        Arrays.fill(seen, false);
        Arrays.fill(dis, INF);
        dis[1] = 0;
        Q.add(new Node(1, 0, limit));
        while (!Q.isEmpty()) {
            int u = Q.poll().to;
            if (seen[u]) continue;
            seen[u] = true;
            for (Node v : e[u]) {
                if (v.limit >= limit && dis[v.to] > dis[u] + v.cost) {
                    dis[v.to] = dis[u] + v.cost;
                    Q.add(new Node(v.to, dis[v.to], limit));
                }
            }
        }
        return dis[N];
    }

    public static class Node {
        public int to, cost, limit;

        public Node(int to, int cost, int limit) {
            this.to = to;
            this.cost = cost;
            this.limit = limit;
        }
    }

    // 最小生成树 minimum spanning tree, MST
    // Kruskal 算法
    // P2212  https://www.luogu.com.cn/problemnew/solution/P2212

    public static class Point {
        public int u, v, w;
    }

    public int[] fa;
    public int N, C;

    public int WateringTheFields() {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        C = cin.nextInt();
        int[] X = new int[N + 1], Y = new int[N + 1];
        int count = 0;
        Point[] e = new Point[N * N + 1];
        for (int i = 1; i <= N; i++) {
            X[i] = cin.nextInt();
            Y[i] = cin.nextInt();
            for (int j = 1; j <= i; j++) {
                int distance = (X[i] - X[j]) * (X[i] - X[j]) + (Y[i] - Y[j]) * (Y[i] - Y[j]);
                if (distance > C) {
                    e[++count] = new Point();
                    e[count].u = i;
                    e[count].v = j;
                    e[count].w = distance;
                }
            }
        }
        Arrays.sort(e, (o1, o2) -> o1.w - o2.w);
        return 0;
    }


    public void kruskal() {
        int u, v, dis;
        int cnt = 0, ans = 0;
        for (int i = 1; i <= N; i++);
    }


    // P2387

    // prim算法


    // boruvka算法


    // 最小生成树唯一性问题


    // 瓶颈生成树 最小瓶颈路

    // 忽略  次小生成树（非严格次小生成树，严格次小生成树）


}