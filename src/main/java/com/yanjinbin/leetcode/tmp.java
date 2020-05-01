/*
import java.io.*;
import java.util.*;
public class Main {
    public  int cnt;
    public  Edge[] e; // 1_based index
    public  Edge[] e2;
    public  int[] h;
    public  int[] h2;
    public  int[] d1;
    public  int[] d2;
    public  boolean[] in; 

    public static void main(String args[]) throws Exception {
        int INF = 2000000000;
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt(), M = cin.nextInt(), S = cin.nextInt();
        d1 = new int[N + 1];
        d2 = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            d1[i] = INF;
            d2[i] = INF;
        }

        h = new int[N + 1];
        h2 = new int[N + 1];
        Arrays.fill(h, -1);
        Arrays.fill(h2, -1);
        e = new Edge[M + 1];
        e2 = new Edge[M + 1];

        for (int i = 1; i <= M; i++) {
            int a = cin.nextInt(), b = cin.nextInt(), l = cin.nextInt();
            cnt++;
            e[cnt].to = b;
            e[cnt].next = h[a];
            h[a] = cnt;
            e[cnt].w = l;

            e2[cnt].to = a;
            e2[cnt].next = h2[b];
            e2[cnt].w = l;
            h2[b] = cnt;
        }
        //SPFA
        SPFA1(S);// 正向
        Arrays.fill(in,false);
        SPFA2(S);// 反向
        int ans = 0;
        for (int i = 1; i <= N; i++) {
            ans = Math.max(ans, d1[i] + d2[i]);
        }
        System.out.println(ans);
        
    }

    
    // 单源最短路径
    public static void SPFA1(int s) {
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        in[s] = true;
        d1[s] = 0;
        while (!q.isEmpty()) {
            int t = q.poll();
            in[t] = false;
            for (int i = h[t]; i != -1; i = e[i].next) {
                int v = e[i].to;
                int w = e[i].w;
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
    public static void SPFA2(int s) {
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

    public static int cnt;
    public static Edge[] e1; // 1_based index
    public static Edge[] e2;
    public static int[] h1;
    public static int[] h2;
    public static int[] d1;
    public static int[] d2;
    public static boolean[] in_queue1;
    public static boolean[] in_queue2;

   
    class Edge {
    int next; // 同期点i的下一条编的存储位置
    int to; // edge[i].to表示第i条边的终点,
    int w; // edge[i].w为边权值
    }
}*/
