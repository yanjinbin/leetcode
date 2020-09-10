package com.yanjinbin.leetcode;

import java.util.*;

public class Graph {

  /*  public static void main(String[] args) {
        Graph g = new Graph();
        g.WateringTheFields();
    }*/

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

    //public int cnt;
    public com.yanjinbin.leetcode.Edge[] e1; // 1_based index
    public com.yanjinbin.leetcode.Edge[] e2;
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
        e1 = new com.yanjinbin.leetcode.Edge[M + 1];
        e2 = new com.yanjinbin.leetcode.Edge[M + 1];

        for (int i = 1; i <= M; i++) {
            int a = cin.nextInt(), b = cin.nextInt(), l = cin.nextInt();
            cnt++;
            e1[cnt] = new com.yanjinbin.leetcode.Edge(); // 要new一个阿 别忘记了 否则NPE
            e1[cnt].to = b;
            e1[cnt].next = h1[a];
            h1[a] = cnt;
            e1[cnt].w = l;

            e2[cnt] = new com.yanjinbin.leetcode.Edge();
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


    // https://oi-wiki.org/graph/mst/
    // 最小生成树 minimum spanning tree, MST
    // kruskal 和 prim 比较   最小生成树模版---> https://www.luogu.com.cn/blog/80049/template-Minimum-Spanning-Tree


    // Kruskal 算法
    // P2212  https://www.luogu.com.cn/problemnew/solution/P2212
    public static class Edge {
        public int u, v, w;
    }

    public int[] fa;
    public int N, C;

    public int WateringTheFields() {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        C = cin.nextInt();
        int[] X = new int[N + 1], Y = new int[N + 1];
        fa = new int[N + 1];
        Arrays.fill(fa, -1);
        for (int i = 1; i <= N; i++) {
            X[i] = cin.nextInt();
            Y[i] = cin.nextInt();
            fa[i] = i;
        }

        int cnt = 0;
        Edge[] e = new Edge[N * N + 1];
        // 直角三角形的遍历,复杂度降低1/2;
        for (int i = 1; i <= N; i++) {
            for (int j = i + 1; j <= N; j++) {
                int dis = (X[i] - X[j]) * (X[i] - X[j]) + (Y[i] - Y[j]) * (Y[i] - Y[j]);
                if (dis >= C) {
                    e[++cnt] = new Edge();
                    e[cnt].u = i;
                    e[cnt].v = j;
                    e[cnt].w = dis;
                }
            }
        }
        // 排序  MlgM 复杂度
        Arrays.sort(e, 1, cnt + 1, (o1, o2) -> o1.w - o2.w);
        int p = 0;
        int ans = 0;
        for (int i = 1; i <= cnt; i++) {
            int ru = find(e[i].u);
            int rv = find(e[i].v);
            if (ru != rv) {
                p++;
                fa[ru] = rv;
                ans += e[i].w;
                if (p == N - 1) break;
            }
        }
        if (p == N - 1) System.out.printf("%d", ans);
        else System.out.printf("-1");
        return 0;
    }

    // 查找带路径压缩
    public int find(int u) {
        if (u != fa[u]) {
            fa[u] = find(fa[u]);
        }
        // 不要写成 return u了。
        return fa[u];
    }


    // prim

    // SCOI2005  https://www.luogu.com.cn/problem/P2330
    // 繁忙都市
    public void BusyCity() {
        Scanner cin = new Scanner(System.in);
        //  step 1, 构建链式前向星
        int N = cin.nextInt(), M = cin.nextInt();
        com.yanjinbin.leetcode.Edge[] edges = new com.yanjinbin.leetcode.Edge[M + 1];
        int[] h = new int[N + 1];
        boolean[] seen = new boolean[N + 1];
        Arrays.fill(h, -1);
        int cnt = 0;
        for (int i = 1; i <= M; i++) {
            int u = cin.nextInt(), v = cin.nextInt(), w = cin.nextInt();
            edges[++cnt] = new com.yanjinbin.leetcode.Edge();
            edges[cnt].to = v;
            edges[cnt].w = w;
            edges[cnt].next = h[u];
            h[u] = cnt++;
        }

        Queue<com.yanjinbin.leetcode.Edge> Q = new PriorityQueue<>((o1, o2) -> o1.w - o2.w);
        for (int i = h[1]; i != -1; i = edges[i].next) {
            Q.add(edges[i]);
        }
        int ans = 0;
        while (!Q.isEmpty()) {
            com.yanjinbin.leetcode.Edge tmp = Q.poll();
            if (seen[tmp.to]) continue;
            seen[tmp.to] = true;
            ans = Math.max(ans, tmp.w);
            for (int i = h[tmp.to]; i != -1; i = edges[i].next) {
                if (!seen[edges[i].to]) Q.add(edges[i]);
            }

        }
    }

    public static int cnt = 1;
    public static com.yanjinbin.leetcode.Edge[] edges;
    public static int[] head;
   // public static boolean[] seen;
    static Pre[] p;

    public class Pre {
        int next; // 增广路 靠近S点反向的前一个点
        int eIdx;// edges数组的index
    }

    public static void addEdge(int u, int v, int w) {
        edges[++cnt].to = v;
        edges[cnt].w = w;
        edges[cnt].next = head[u];
        head[u] = cnt;
    }

    public static boolean bfs(int n, int m, int s, int t) {
        Arrays.fill(seen, false);
        seen[s] = true;
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int i = head[u]; i != -1; i = edges[i].next) {
                int v = edges[i].to;
                if (!seen[v] && edges[i].w != 0) {
                    p[v].next = u;
                    p[v].eIdx = i;
                    //判断 是不是 t点放前面
                    if (v == t) {
                        return true;
                    }
                    seen[v] = true;
                    q.offer(v);
                }
            }
        }
        return false;
    }

    static int EK(int n, int m, int s, int t) {
        int ans = 0;
        while (bfs(n, m, s, t)) {
            int min = Integer.MAX_VALUE;
            for (int i = t; i != s; i = p[i].next) {
                min = Math.min(min, edges[p[i].eIdx].w);
            }
            for (int i = t; i != s; i = p[i].next) {
                edges[p[i].eIdx].w -= min;
                edges[p[i].eIdx ^ 1].w += min;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt(), m = cin.nextInt(), s = cin.nextInt(), t = cin.nextInt();
        for (int i = 1; i <= m; i++) {
            int u = cin.nextInt(), v = cin.nextInt(), w = cin.nextInt();
            addEdge(u, v, w);
            addEdge(v, w, 0);
        }
        Arrays.fill(head, -1);
        EK(n,m,s,t);
    }

}