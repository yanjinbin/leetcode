package com.yanjinbin.leetcode;

public class DSU {
    public int[] fa;
    public int[] rank;

    public DSU(int n) {
        fa = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            fa[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int u) {
        if (u != fa[u]) {
            fa[u] = find(fa[u]);
        }
        return fa[u];
    }

    public void merge(int u, int v) {
        int x = find(u);
        int y = find(v);
        if (rank[x] > rank[y]) {
            fa[y] = x;
        } else if (rank[x] < rank[y]) {
            fa[x] = y;
        }else {
            fa[x]=y;
            rank[x]++;
        }
    }
}
