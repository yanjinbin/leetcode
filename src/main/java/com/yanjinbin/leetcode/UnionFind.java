package com.yanjinbin.leetcode;


import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.yanjinbin.leetcode.Solution.swap;

// https://oi-wiki.org/ds/dsu/
// https://leetcode-cn.com/tag/union-find/
// 花花酱UNION FIND  https://youtu.be/VJnUwsE4fWA?list=WL
@NoArgsConstructor
public class UnionFind {
    private int[] fa;
    private int[] rank;

    public UnionFind(int n) {
        fa = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 0; i < fa.length; ++i) {
            fa[i] = i;
            rank[i] = 1;
        }
    }

    // 查找
    /*public int find(int u) {
        if (fa[u] == u) {
            return u;
        } else {
            return find(fa[u]);
        }
    }*/

    // 路径压缩
    public int find(int u) {
        if (u != fa[u]) {
            fa[u] = find(fa[u]);
        }
        return fa[u];
    }

    // 合并
    public boolean Union(int u, int v) {
        int x = find(u);
        int y = find(v);
        if (x == y) return false;
        if (rank[x] > rank[y]) {
            fa[y] = x;
        } else if (rank[x] < rank[y]) {
            fa[x] = y;
        } else {
            fa[x] = y;
            rank[x]++;
        }
        return true;
    }

    // 684
    public int[] findRedundantConnection(int[][] edges) {
        UnionFind s = new UnionFind(edges.length);
        for (int[] edge : edges)
            if (!s.Union(edge[0], edge[1]))
                return edge;
        return null;
    }

    // 685 挺复杂的。处理好几个case
    // https://youtu.be/lnmJT5b4NlM
    // 这个中文解释的不错哦  http://bit.ly/37OBJFj
    // int[] fa;
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int N = edges.length;
        fa = new int[N + 1];
        int[] parent = new int[N + 1];
        int[] edge1 = null, edge2 = null, edgeCircle = null;
        for (int[] pair : edges) {
            int u = pair[0], v = pair[1];
            if (fa[u] == 0) fa[u] = u;
            if (fa[v] == 0) fa[v] = v;
            if (parent[v] != 0) {//跳过下面else部分！！重点理解这个
                edge1 = new int[]{parent[v], v};
                edge2 = pair;
            } else {
                parent[v] = u;
                int ancU = find(u);
                int ancV = find(v);
                if (ancU != ancV) {
                    fa[ancV] = ancU;
                } else {// 碰到了环
                    edgeCircle = pair;
                }
            }
        }
        if (edge1 != null && edge2 != null) return edgeCircle == null ? edge2 : edge1;
        else return edgeCircle;
    }

    // 547 朋友圈
    public int findCircleNum(int[][] M) {
        int N = M.length;
        UnionFind uf = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (M[i][j] == 1) {
                    uf.Union(i, j);
                }
            }
        }
        Set<Integer> sets = new HashSet();
        for (int i = 0; i < N; i++) {
            sets.add(find(i));
        }
        return sets.size();
    }


    // 737
    public boolean areSentenceSimilarityTwo(String[] w1, String[] w2, List<List<String>> pairs) {
        if (w1.length != w2.length) return false;
        int N = pairs.size() * 2;
        UnionFind uf = new UnionFind(N);
        Map<String, Integer> map = new HashMap<>();
        int id = 0;
        for (List<String> strs : pairs) {
            for (String str : strs) {
                if (!map.containsKey(str)) {
                    map.put(str, id);
                    id++;
                }
            }
            uf.Union(map.get(strs.get(0)), map.get(strs.get(1)));
        }


        for (int i = 0; i < w1.length; i++) {
            if (w1[i].equals(w2[i])) continue;
            if (!map.containsKey(w1[i]) || !map.containsKey(w2[i]) || uf.find(map.get(w1[i])) != uf.find(map.get(w2[i]))) {
                return false;
            }
        }
        return true;
    }
    // 721 账户合并
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        return null;
    }

    // 847

    // 928 尽量减少恶意软件的传播 II

    /// 803 打砖块

    // 1101. 彼此熟识的最早时间

    // 399. 除法求值


    // 778 水位上升的泳池中游泳

    // ==============


}
