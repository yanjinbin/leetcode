package com.yanjinbin.leetcode;

// dp专题 背包问题九讲
public class DP {
    // P428  最大连续和 问题等同于 [leetcode 53 最大子序和]
    // 解法1 暴力枚举
    public int maxConsecutiveSum1(int[] arr) {
        int tot = 0;
        int N = arr.length;
        int best = arr[0];
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum = sum + arr[k];
                    tot++;
                }
                if (sum > best) best = sum;
            }
        }
        System.out.println("运算次数:\t" + tot);
        return best;
    }

    //解法2 写出递推公式 Max(arr[i:j]) = S[0:j]-S[0:i-1]
    public int maxConsecutiveSum2(int[] arr) {
        int tot = 0;
        int[] S = new int[arr.length + 1];
        int N = S.length;
        //init
        S[0] = 0;

        // for
        for (int i = 1; i < N; i++) {
            S[i] = S[i - 1] + arr[i - 1];
            tot++;
        }

        int best = S[0];
        for (int i = 1; i < N; i++) {
            for (int j = i; j < N; j++) {
                best = Math.max(best, S[j] - S[i - 1]);
                tot++;
            }
        }
        System.out.println("运算次数:\t" + tot);
        return best;
    }

    //解法3 分治法
    public int maxConsecutiveSum3(int[] arr) {
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


    // chapter 8.2 P442
    // 这个问题 网上也已经有很多人分析过如何写出正确的二分查找了
    //  8.2章节  也给出标准示范了 , 以及几个细节的分析
    // 二分查找的标准函数 http://bit.ly/32512ix
    public int binarySearch(int[] array, int first, int last, int value) {
        //  System.out.println("first " + first + " last " + last + " value " + value);
        while (first < last) {
            int mid = first + (last - first) / 2;
            if (array[mid] <= value) { // 返回满足 arr[i] > value的第一个位置
                first = mid + 1;
            } else {
                last = mid;
            }
        }
        // System.out.println("first " + first + " last " + last + " value " + value);
        return first;
    }

    public int binarySearch01(int[] arr, int l, int r, int val) {
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] >= val) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
    // chapter 8.3  递归与分治


}
