package com.yanjinbin.leetcode;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class BFS {

    // 407 接雨水Ⅱ 优先队列+BFS https://youtu.be/7niUr7LlviY
    public int trapRainWater(int[][] heightMap) {
        PriorityQueue<Tuple> q = new PriorityQueue<Tuple>((o1, o2) -> o1.z - o2.z);
        int m = heightMap.length;
        int n = heightMap[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            q.offer(new Tuple(i, 0, heightMap[i][0]));
            q.offer(new Tuple(i, n - 1, heightMap[i][n - 1]));
            visited[i][0] = true;
            visited[i][n - 1] = true;
        }
        for (int j = 0; j < n; j++) {
            q.offer(new Tuple(0, j, heightMap[0][j]));
            q.offer(new Tuple(m - 1, j, heightMap[m - 1][j]));
            visited[0][j] = true;
            visited[m - 1][j] = true;
        }

        int[] dirs = new int[]{0, -1, 0, 1, 0};
        int water = 0;
        while (!q.isEmpty()) {
            Tuple p = q.poll();
            int x = p.x, y = p.y, height = p.z;
            for (int i = 0; i < 4; i++) {
                int nx = dirs[i] + x;
                int ny = dirs[i + 1] + y;
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    if (height > heightMap[nx][ny]) {// 注意q存储的 tuple的height 是height还是heightMap[nx][ny]
                        water += height - heightMap[nx][ny];
                        q.offer(new Tuple(nx, ny, height));
                    } else {
                        q.offer(new Tuple(nx, ny, heightMap[nx][ny]));
                    }
                }
            }
        }
        return water;
    }

    // tag: min-max
    // https://youtu.be/umdk98ynLSY
    // 778 解法1 优先队列+BFS ,总结:最大里面取最小!!(从小堆里面取留下的大元素)
    public int swimInWater01(int[][] grid) {
        PriorityQueue<Point> minHeap = new PriorityQueue<Point>((o1, o2) -> o1.val - o2.val);
        int M = grid.length;
        boolean[] visited = new boolean[M * M];
        minHeap.offer(new Point(0, 0, grid[0][0]));
        // x*n+y
        visited[0] = true;
        int[] dirs = new int[]{-1, 0, 1, 0, -1};
        while (!minHeap.isEmpty()) {
            Point p = minHeap.poll();
            if (p.x == M - 1 && p.y == M - 1) return p.val;
            for (int i = 0; i < 4; i++) {
                int nx = p.x + dirs[i];
                int ny = p.y + dirs[i + 1];
                if (nx >= M || nx < 0 || ny >= M || ny < 0 || visited[nx * M + ny]) continue;
                visited[nx * M + ny] = true;
                //注意是取max哦.因为heap是minHeap
                minHeap.offer(new Point(nx, ny, Math.max(grid[nx][ny], p.val)));
            }
        }
        return -1;
    }

    // 778 解法2 bfs+二分查找
    public int swimInWater02(int[][] grid) {
        int lo = 0, hi = grid.length * grid.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (hasPath(mid, grid.length, grid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;

    }

    public boolean hasPath(int t, int N, int[][] grid) {
        if (grid[0][0] > t) return false;
        Queue<Integer> q = new LinkedList<>();
        boolean[] seen = new boolean[N * N];
        int[] dirs = new int[]{-1, 0, 1, 0, -1};
        q.add(0);
        while (!q.isEmpty()) {
            int idx = q.poll();
            int x = idx % N;
            int y = idx / N;
            if (x == N - 1 && y == N - 1) return true;
            for (int i = 0; i < 4; i++) {
                int nx = x + dirs[i];
                int ny = y + dirs[i + 1];
                if (nx < 0 || ny < 0 || nx >= N || ny >= N || grid[nx][ny] > t) continue;
                int ndx = ny * N + nx;
                if (seen[ndx]) continue;
                seen[ndx] = true;
                q.add(ndx);
            }
        }
        return false;
    }


    // 拓扑排序（269，329，444，1203，802）
    // http://bit.ly/38Q5FDP
    // 269 火星词典
    public String alienOrder(String[] words) {
        // -------------------------------------------------mmp 这题的题目意思我看了半天 初始化
        //  同一个 String 中 “abc” 这三个之间是没有相互顺序的 我的奶奶个腿的
        //  谁出的题出来挨打
        Map<Character, Set<Character>> G = new HashMap();

        for (int i = 0; i < words.length - 1; i++) {
            // 这里改成这样就是为了防止 abc
            int len = Math.max(words[i].length(), words[i + 1].length());

            for (int j = 0; j < len; j++) {
                // 这里是个坑 要防止 abc -> ab 这种情况
                if (j >= words[i].length()) break;
                if (j >= words[i + 1].length()) return "";
                if (words[i].charAt(j) == words[i + 1].charAt(j)) continue;
                char u = words[i].charAt(j);
                char v = words[i + 1].charAt(j);
                Set<Character> set = G.getOrDefault(u, new HashSet<>());
                set.add(v);
                G.put(u, set);
                break;
            }
        }

        // --------------------------------------------------这一下是拓扑排序----------------------------

        Queue<Character> q = new LinkedList();
        Set<Character> set = new HashSet<>();
        // 入度初始化
        int[] indeg = new int[26];
        Arrays.fill(indeg, -1);
        for (String word : words) {
            for (char c : word.toCharArray()) {
                indeg[c - 'a'] = 0;
                set.add(c);
            }
        }
        int count = set.size();
        // 入度计算
        for (Character key : G.keySet()) {
            for (Character value : G.get(key)) {
                indeg[value - 'a']++;
            }
        }

        for (int i = 0; i < 26; i++) {
            if (indeg[i] == 0) {
                q.add((char) (i + 'a'));
            }
        }
        StringBuilder ans = new StringBuilder();
        while (!q.isEmpty()) {
            char u = q.poll();
            ans.append(u);
            if (G.containsKey(u)) {
                for (char v : G.get(u)) {
                    indeg[v - 'a']--;
                    if (indeg[v - 'a'] == 0) {
                        q.add(v);
                    }
                }
            }
        }
        if (ans.length() != count) {
            return "";
        }
        return ans.toString();
    }

    // 329
    // http://bit.ly/2EXW1yR 解法3 topological sort 构建拓扑排序, 问题转换为 有向图的中的拓扑排序下的最长路径
    public int longestIncreasingPath03(int[][] matrix) {
        int[] dirs = {0, 1, 0, -1, 0};
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] indegree = new int[m][n];
        Queue<int[]> queue = new LinkedList();
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                for (int i = 0; i < 4; i++) {
                    int nx = dirs[i] + x;
                    int ny = dirs[i + 1] + y;
                    if (nx < 0 || ny < 0 || nx >= m || ny >= n) continue;
                    if (matrix[x][y] > matrix[nx][ny]) indegree[x][y]++;
                }
                if (indegree[x][y] == 0) {
                    queue.offer(new int[]{x, y});
                }
            }
        }
        int len = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                int x = pos[0];
                int y = pos[1];
                for (int k = 0; k < 4; k++) {
                    int nx = x + dirs[k];
                    int ny = y + dirs[k + 1];
                    if (nx < 0 || ny < 0 || nx >= m || ny >= n || matrix[x][y] >= matrix[nx][ny]) continue;
                    indegree[nx][ny]--;
                    if (indegree[nx][ny] == 0) {
                        queue.offer(new int[]{nx, ny});
                    }

                }
            }
            len++;
        }
        return len;
    }

    public Edge[] edges;
    public int[] head;
    public int cnt;

    public void addEdge(int u, int v, int w) {
        edges[++cnt] = new Edge();
        edges[cnt].to = v;
        edges[cnt].w = w;
        edges[cnt].next = head[u];
        head[u] = cnt;
    }

    // 444  https://bit.ly/35IVlMd  拓扑排序唯一性
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        int maxN = 10000 + 1;
        edges = new Edge[maxN];
        head = new int[maxN];
        Arrays.fill(head, -1);
        int[] indeg = new int[maxN];
        Arrays.fill(indeg, -1);
        Set<Integer> sets = new HashSet<>();
        for (List<Integer> seq : seqs) {
            for (int i = 1; i < seq.size(); i++) {
                int u = seq.get(i - 1), v = seq.get(i);
                addEdge(u, v, 0);
                if (indeg[v] == -1) {
                    indeg[v] = 1;
                } else indeg[v]++;
                if (indeg[u] == -1) {
                    indeg[u] = 0;
                }
                sets.add(u);
                sets.add(v);
            }
        }
        int count = sets.size();
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < maxN; i++) {
            if (indeg[i] == 0) {
                q.add(i);// indegree = 0 added
            }
        }
        int cycle = 0;
        boolean uniqueOk = true;
        boolean hasCycle = false;
        List<Integer> ans = new ArrayList<>();
        while (!q.isEmpty()) {
            if (q.size() > 1) {
                uniqueOk = false;
                break;
            }
            int u = q.poll();
            ans.add(u);
            cycle++;
            for (int i = head[u]; i != -1; i = edges[i].next) {
                int v = edges[i].to;
                indeg[v]--;
                if (indeg[v] == 0) {
                    q.add(v);
                }
            }
        }
        hasCycle = cycle != count;
        int[] ret = ans.stream().mapToInt(Integer::valueOf).toArray();
        return uniqueOk && Arrays.equals(ret, org);
    }


    // 802 dfs
    public List<Integer> eventualSafeNodes01(int[][] graph) {
        int[] seen = new int[graph.length];
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            if (Safe(i, seen, graph)) {
                ans.add(i);
            }
        }
        return ans;
    }

    // 1 visiting 2 visited 0 unknown
    public boolean Safe(int cur, int[] seen, int[][] graph) {
        if (seen[cur] != 0) return seen[cur] == 2;
        seen[cur] = 1;
        for (int v : graph[cur]) {
            if (!Safe(v, seen, graph)) return false;// false的时候才返回，true的时候要继续遍历到底。 因为 cur 的 邻接边有可能进入环中。
        }
        seen[cur] = 2;
        return true;
    }

    // 802 bfs  出度为0的，简历反向图，outdeg -- 入列
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        int[] outdegree = new int[n];
        List<Set<Integer>> rg = new ArrayList<Set<Integer>>();
        for (int i = 0; i < n; i++) {
            rg.add(new HashSet<>());// 初始化 犯过的错
        }
        for (int i = 0; i < n; i++) {
            for (int j : graph[i]) {
                rg.get(j).add(i);
                outdegree[i]++;
            }
        }
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < outdegree.length; i++) {
            if (outdegree[i] == 0) {
                q.add(i);
            }
        }
        List<Integer> ans = new ArrayList<>();
        while (!q.isEmpty()) {
            int v = q.poll();
            ans.add(v);
            for (int u : rg.get(v)) {
                outdegree[u]--;
                if (outdegree[u] == 0) {
                    q.add(u);
                }
            }
        }
        Collections.sort(ans);
        return ans;
    }


    // Leetcode 1044 最长重复子串 https://youtu.be/N7EE0VamNqc
    public String longestDupSubstring(String s) {
        int base = 26;
        long mod = 1L << 32;
        int lo = 0, hi = s.length();
        while (lo < hi) {
            int len = lo + (hi - lo) / 2;
            if (RabinKarpSearch(len, base, mod, s) != -1) lo = len + 1;
            else hi = len;
        }
        int start = RabinKarpSearch(lo - 1, base, mod, s);
        return start != -1 ? s.substring(start, start + lo - 1) : "";
    }

    public int RabinKarpSearch(int len, int base, long mod, String s) {
        long h = 0;
        long baseLen = 1;
        for (int i = 0; i < len; ++i) {
            h = (h * base + s.charAt(i) - 'a') % mod;
            baseLen = (baseLen * base) % mod;
        }
        HashSet<Long> seen = new HashSet();
        seen.add(h);
        /*for (int start = 1; start < s.length() - len + 1; ++start) {
            h = (h * base - (s.charAt(start - 1) - 'a') * baseLen % mod + mod) % mod;
            h = (h + s.charAt(start + len - 1) - 'a') % mod;
            if (seen.contains(h)) return start;
            seen.add(h);
        }*/
        // rolling hash的方法
        for (int i = len; i < s.length(); i++) {
            h = (h * base - (s.charAt(i - len) - 'a') * baseLen % mod + mod) % mod;
            h = (h + s.charAt(i) - 'a') % mod;
            if (seen.contains(h)) return i - len + 1;// 返回 遭遇hash相同的起点位置
            seen.add(h);
        }
        return -1;
    }

    // 829
    // https://tinyurl.com/yxzykll7
    // # 1个数时，必然有一个数可构成N
    // # 2个数若要构成N，第2个数与第1个数差为1，N减掉这个1能整除2则能由商与商+1构成N
    // # 3个数若要构成N，第2个数与第1个数差为1，第3个数与第1个数的差为2，N减掉1再减掉2能整除3则能由商、商+1与商+2构成N
    // # 依次内推，当商即第1个数小于等于0时结束
    public int consecutiveNumbersSum(int N) {
        int ans = 0;
        int i = 1;
        while (N > 0) {
            if (N % i == 0) {
                ans++;
            }
            N = N - i;
            i = i + 1;
        }
        return ans;
    }

    // 458
    public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        int pigs = 0;
        int base = minutesToTest / minutesToDie + 1;
        while (Math.pow(base, pigs) < buckets) {
            pigs++;
        }
        return pigs;
    }

    // 827
    public int getArea(int color, int i, int j, int[][] grid) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != 1) return 0;
        grid[i][j] = color;
        return 1 + getArea(color, i - 1, j, grid) + getArea(color, i + 1, j, grid) + getArea(color, i, j - 1, grid) + getArea(color, i, j + 1, grid);
    }

    public int largestIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int color = 1;
        int ans = Integer.MIN_VALUE;
        Map<Integer, Integer> areas = new HashMap<>();// color->area;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    color++;
                    int area = getArea(color, i, j, grid);
                    areas.put(color, area);
                    ans = Math.max(ans, area);
                }
            }
        }
        int[] dirs = new int[]{1, 0, -1, 0, 1};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    Set<Integer> colors = new HashSet<>();// color去重
                    int area = 1;
                    for (int d = 0; d < 4; d++) {
                        int nx = i + dirs[d];
                        int ny = j + dirs[d + 1];
                        if (nx < 0 || ny < 0 || nx >= m || ny >= n) continue;
                        color = grid[nx][ny];
                        if (color > 1) colors.add(color);
                    }

                    for (Integer item : colors) {
                        area += areas.get(item);
                    }
                    ans = Math.max(ans, area);
                }
            }
        }
        return ans;
    }

    // 1136
    public int minimumSemesters(int N, int[][] relations) {
        int[] indeg = new int[N + 1];
        Map<Integer, List<Integer>> G = new HashMap<>();
        for (int i = 0; i < relations.length; i++) {
            int u = relations[i][0], v = relations[i][1];
            List<Integer> vs = G.getOrDefault(u, new ArrayList<>());
            vs.add(v);
            G.put(u, vs);
            indeg[v]++;
        }
        Queue<Integer> q = new LinkedList();
        for (int v = 1; v <= N; v++) {
            if (indeg[v] == 0) {
                q.add(v);
            }
        }
        int ans = 0;
        int count = 0;
        while (!q.isEmpty()) {
            int len = q.size();
            ans++;
            for (int i = 0; i < len; i++) {
                int u = q.poll();
                count++;
                if (G.containsKey(u)) {
                    List<Integer> vs = G.get(u);
                    for (int j = 0; j < vs.size(); j++) {
                        int v = vs.get(j);
                        indeg[v]--;
                        if (indeg[v] == 0) q.add(v);
                    }
                }
            }
        }
        return count == N ? ans : -1;
    }



}
