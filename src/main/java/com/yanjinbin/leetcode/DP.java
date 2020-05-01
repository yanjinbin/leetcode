package com.yanjinbin.leetcode;

// dp专题 背包问题九讲
public class DP {

    // P428  最大连续和 问题等同于 LC 53 最大子序和
    // 复杂度 T:O(n),S:O(N)
    public int maxConsecutiveSum01(int[] arr) {
        int N = arr.length;
        int[] dp = new int[N];
        int ans = arr[0];
        dp[0] = arr[0];
        for (int i = 1; i < N; i++) {
            dp[i] = Math.max(dp[i - 1] + arr[i], arr[i]);
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    // 滚动数组优化  T:O(N) S:O(1)
    public int maxConsecutiveSum02(int[] arr) {
        int N = arr.length;
        int ans = arr[0];
        int prev = arr[0];
        for (int i = 1; i < N; i++) {
            prev = Math.max(prev + arr[i], arr[i]);
            ans = Math.max(ans, prev);
        }
        return ans;
    }

    //解法3 分治法 T: O(lgN)
    public int maxConsecutiveSum03(int[] arr) {
        return maxSum(arr, 0, arr.length);
    }

    public int maxSum(int[] arr, int x, int y) {
        int v, L, R, maxs;
        // L R 代表从mid 往左和往右能获得的最大连续和 这样子问题的解,对于原问题的解是有效的
        if (y - x == 1) return arr[x];
        int mid = x + (y - x) / 2;// 1:划分子问题
        maxs = Math.max(maxSum(arr, x, mid), maxSum(arr, mid, y)); // 2: 递归求解
        //  合并子问题
        v = 0;
        L = arr[mid - 1];
        for (int i = mid - 1; i >= x; i--) {
            v = v + arr[i];
            L = Math.max(L, v);
        }
        v = 0;
        R = arr[mid];
        for (int i = mid; i < y; i++) {
            v = v + arr[i];
            R = Math.max(R, v);
        }
        return Math.max(maxs, L + R);
    }

    //256 粉刷房子 paint house
    public int minCost(int[][] costs) {
        int N = costs.length;
        int[][] dp = new int[N][3];
        dp[0][0] = costs[0][0];
        dp[0][1] = costs[0][1];
        dp[0][2] = costs[0][2];
        for (int i = 1; i < N; i++) {
            dp[i][0] = Math.min(dp[i - 1][1], dp[i - 1][2]) + costs[i][0];
            dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][2]) + costs[i][1];
            dp[i][2] = Math.min(dp[i - 1][0], dp[i - 1][1]) + costs[i][2];
        }
        return Math.min(Math.min(dp[N - 1][0], dp[N - 1][1]), dp[N - 1][2]);
    }

    // 265 粉刷房子Ⅱ https://youtu.be/a6rDSwdW2Mo?t=1116
    // tips: 当前房间刷成j色成本 = (j色和上一间房间最小成本的颜色min1相同的话,
    // 取上一件房间第二小成本的颜色Min2,否则取min1
    public int minCostⅡ(int[][] cost) {
        if (cost == null || cost.length == 0) return 0;
        int n = cost.length, k = cost[0].length;
        int[][] dp = new int[n][k];
        int min1 = -1, min2 = -2;// min1 和 min2代表刷成min1和min2颜色的成本第一小和第二小
        for (int i = 0; i < n; i++) {
            // 暂时存储上一次存储的颜色
            int last1 = min1, last2 = min2;
            min1 = -1;
            min2 = -1;
            // 比对当前房间和上间房间刷成的颜色相同和不同的成本
            for (int j = 0; j < k; j++) {
                if (j != last1) {// 颜色不同
                    dp[i][j] = cost[i][j] + last1 < 0 ? 0 : dp[i - 1][last1];
                } else {// 颜色相同
                    dp[i][j] = (last2 < 0 ? 0:dp[i - 1][last2] )+ cost[i][j];
                }
                if (min1 < 0 || dp[i][j] < dp[i][min1]) { // 当前刷成j色的成本比之前的min1开销成本还低
                    min2 = min1;
                    min1 = j;
                } else if (min2 < 0 || dp[i][j] < dp[i][min2]) {// 介于 min1和min2志坚
                    min2 = j;
                }
            }
        }
        return dp[n - 1][min1];
    }

}
