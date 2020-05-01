package com.yanjinbin.leetcode;

// https://oi-wiki.org/ds/sparse-table/
// [tag：高级数据结构] todo
// https://www.cnblogs.com/zwfymqz/p/8581995.html
// https://zh.wikipedia.org/wiki/%E8%8C%83%E5%9B%B4%E6%9C%80%E5%80%BC%E6%9F%A5%E8%AF%A2
public class SparseTable {
    int N = 1000;
    int[][] dp = new int[1000][];
    //O(NlogN)
    public void Build(int N, int M, int[] nums) {

        for (int i = 1; i <= N; i++) {
            dp[i][0] = nums[i];
        }
        for (int j = 1; j <= 21; j++) {
            for (int i = 1; i + (1 << j) - 1 <= N; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], dp[i + (1 << (j - 1))][j - 1]);
            }
        }
    }
    //O(1)
    public int Query(int l, int r) {
        int k = (int) Math.log(r - l + 1);
        return Math.max(dp[l][k], dp[r - (1 << k) + 1][k]);
    }
}
