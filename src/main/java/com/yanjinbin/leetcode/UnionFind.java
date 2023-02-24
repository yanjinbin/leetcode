package com.yanjinbin.leetcode;


import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// https://oi-wiki.org/ds/dsu/
// https://leetcode-cn.com/tag/union-find/
// 花花酱UNION FIND  https://youtu.be/VJnUwsE4fWA?list=WL
@NoArgsConstructor
public class UnionFind {
    public int[] fa;
    public int[] rank;

    public UnionFind(int n) {// 冗余 0index
        fa = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 0; i < fa.length; i++) {
            fa[i] = i;
            rank[i] = 1;
        }
    }

    // 递归版本
    public int find(int u, int[] fa) {
        if (u != fa[u]) {
            fa[u] = find(fa[u], fa);
        }
        return fa[u];
    }


    // 非递归版本
    public int find02(int u, int[] fa) {
        while (u != fa[u]) {
            fa[u] = fa[fa[u]];
            u = fa[u];
        }
        return u;
    }

    // 查找带路径压缩
    public int find(int u) {
        if (u != fa[u]) {
            fa[u] = find(fa[u]);
        }
        // 不要写成 return u了。
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

    // 685 冗余链接Ⅱ 挺复杂的。处理好几个case : https://youtu.be/lnmJT5b4NlM?t=731
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int N = edges.length;
        int[] fa = new int[N + 1];
        int[] root = new int[N + 1];
        int[] rank = new int[N + 1];
        Arrays.fill(rank, 1);
        int[] ans1 = null, ans2 = null;
        // step1  是否有duplicate parents
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            if (fa[v] > 0) { // has two parents
                ans1 = new int[]{fa[v], v};
                ans2 = new int[]{u, v};
                // delete the later edge
                edge[0] = edge[1] = -1;
            }
            fa[v] = u;
        }

        // step2  分3种情况讨论
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            // invalid edge
            if (u < 0 || v < 0) continue;
            if (root[u] == 0) root[u] = u;
            if (root[v] == 0) root[v] = v;
            int pu = find(u, root);
            int pv = find(v, root);
            if (pu == pv) {
                return ans1 == null ? edge : ans1;
            }
            if (rank[pv] > rank[pu]) {
                int tmp = pv;
                pv = pu;
                pu = tmp;
            }
            // 做 merge
            root[pv] = pu;
            rank[pu] += rank[pv];
        }
        return ans2;
    }


    // 547 朋友圈 也可以DFS遍历，记录一个访问数组即可。
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
            sets.add(uf.find(i));
        }
        return sets.size();
    }


    // 737 句子相似性Ⅱ
    public boolean areSentenceSimilarityTwo(String[] w1, String[] w2, List<List<String>> pairs) {
        if (w1.length != w2.length) return false;
        int N = pairs.size() * 2;
        UnionFind uf = new UnionFind(N);
        Map<String, Integer> map = new HashMap<>();
        int id = 0;
        // 构建union
        for (List<String> strs : pairs) {
            for (String str : strs) {
                if (!map.containsKey(str)) {
                    map.put(str, id);
                    id++;
                }
            }
            uf.Union(map.get(strs.get(0)), map.get(strs.get(1)));
        }
        // 逐个比较，find 比对相似性
        for (int i = 0; i < w1.length; i++) {
            if (w1[i].equals(w2[i])) continue;
            if (!map.containsKey(w1[i]) || !map.containsKey(w2[i]) || uf.find(map.get(w1[i])) != uf.find(map.get(w2[i]))) {
                return false;
            }
        }
        return true;
    }

}
