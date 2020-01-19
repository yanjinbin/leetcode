package com.yanjinbin.leetcode;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

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

    // https://youtu.be/umdk98ynLSY
    // 778 解法1 dijkstra/优先队列+BFS ,总结:最大里面取最小!!(从小堆里面取留下的大元素)
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
}
