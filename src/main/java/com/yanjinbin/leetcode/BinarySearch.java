package com.yanjinbin.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BinarySearch {

    //69 平方根 解乏2 二分法求
    public int mySqrt1(int x) {
        // 无法处理x= Integer.MAX_VALUE;
        long l = 1;
        long r = x + 1;
        while (l < r) {
            long mid = l + (r - l) / 2;
            if (mid > x / mid) {
                // 避免溢出 为什么不写成 mid<x/mid ，
                // 因为现在要找到大于它 而非大于等于 的上边界，那么-1就是它的最后一个<= x的数字了
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return (int) (l - 1);
    }

    // ② 230 二叉搜索树中第k小 通用方法
    // ② 解法3 二分查找法 tips:  左半右半部分
    // ----------k-------------
    // ---cnt--cnt+1---cnt+2--->
    // ---root.left--root---root.right--->
    public int kthSmallest2(TreeNode root, int k) {
        int cnt = count(root.left);
        if (k < cnt + 1) {
            return kthSmallest2(root.left, k);
        } else if (k > cnt + 1) {
            // [error]  k 的更新哦 k-cnt-1 不是原来的k
            return kthSmallest2(root.right, k - cnt - 1);
        }
        // k=cnt+1;
        return root.val;
    }

    public int count(TreeNode root) {
        if (root == null) return 0;
        return 1 + count(root.left) + count(root.right);
    }


    //②  162 寻找峰值
    public int findPeakElement1(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] < nums[mid + 1]) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    //② 658 K个最接近X的元素
    // x-arr[mid] < arr[mid]+k -x
    public List<Integer> findKClosestElements(int[] arr, int k, int x) {
        int lo = 0, hi = arr.length - k;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (x - arr[mid] > arr[mid + k] - x) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int j = lo; j < lo + k; j++) {
            list.add(arr[j]);
        }
        return list;
    }


    // 287 ② 这种二分法还是比较少见的 但是也存在多钟限制阿 中间数的计算近似 median=(right+left)/2;
    public int findDuplicate1(int[] nums) {
        // // 特殊case n = 1 ,长度为2，{1,1} ; n= 2 ,长度为3,{1,2,2} or {1,1,2}
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0;
            for (int num : nums) { // 计算小于mid的个数,
                if (num <= mid) count++;
            }
            if (count <= mid) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    // ② 378 有序矩阵中 第K小的元素
    // 解法2 二分查找
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length, lo = matrix[0][0], hi = matrix[n - 1][n - 1] + 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = lessEqual(matrix, mid);
            if (count < k) lo = mid + 1;
            else hi = mid;
        }
        return hi;
    }

    //  from left-bottom or right-top can count how much numbers are less equal then target
    public int lessEqual(int[][] matrix, int target) {
        int cnt = 0, N = matrix.length, i = N - 1, j = 0;
        while (i >= 0 && j < N) {
            if (matrix[i][j] > target) i--;
            else {
                cnt = cnt + i + 1;
                j++;
            }
        }
        return cnt;
    }

    // LC 668
    public int findKthNumber(int m, int n, int k) {
        int lo = 1, hi = m * n + 1;
        while (lo < hi) {
            int mid = (hi - lo) / 2 + lo;
            if (len(mid, m, n) < k) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    //通用项: 1*i ,2*i,3*i....m*i;
    public int len(int target, int m, int n) {
        int cnt = 0;
        for (int i = 1; i <= m; i++) {
            cnt += Math.min(n, target / i);
        }
        return cnt;
    }

    // ⚠️  注意 二分的条件函数f=g(M)是找到<= m 而不是 < m ,一定要包含m这个元素
    // LC 786 第 K 个最小的素数分数
    // 难点,二分分法,根据fraction表,往右和往上递减,计算元素个数
    public int[] kthSmallestPrimeFraction(int[] A, int k) {
        int n = A.length;
        double lo = A[0] / A[n - 1], hi = 1.0;
        while (lo < hi) {
            double mid = (hi + lo) / 2;
            double max_f = 0.0;
            int cnt = 0, p = 0, q = 0;
            for (int i = 0, j = 1; i < n - 1; i++) {
                // find j坐标是的A[i]/A[j] <= mid
                while (j < n && A[i] > mid * A[j]) j++;
                cnt += n - j;
                if (n == j) break;
                double f = A[i] * 1.0 / A[j];
                if (f > max_f) {
                    p = i;
                    q = j;
                    max_f = f;
                }
            }
            if (cnt == k) {
                return new int[]{A[p], A[q]};
            } else if (cnt > k) {
                hi = mid;
            } else {
                lo = mid;
            }
        }
        return new int[]{};
    }


    // LC 719
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int l = 0, r = nums[n - 1] - nums[0] + 1;
        while (l < r) {
            int count = 0;
            int j = 0;
            int m = (r - l) / 2 + l;
            for (int i = 0; i < n; i++) {
                while (j < n && nums[j] - nums[i] <= m) j++;
                count += j - i - 1;
            }

            if (count < k) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    // P2115 [USACO14MAR]Sabotage G https://www.luogu.com.cn/problem/solution/P2115
    public int N;
    public int[] S;


    public double[] hmax, qmin, C;

    public boolean check01(double m) {
        for (int i = 0; i <= N; i++) C[i] = S[i] - m * i;
        qmin[1] = C[1];
        for (int i = 2; i <= N; i++) qmin[i] = Math.min(qmin[i - 1], C[i]);
        hmax[N - 1] = C[N - 1];
        for (int i = N - 2; i >= 1; i--) hmax[i] = Math.max(hmax[i + 1], C[i]);
        for (int i = 2; i < N; i++) {
            if (hmax[i] - qmin[i - 1] > C[N]) return false;
        }
        return true;
    }

    public double SabotagG01() {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        S = new int[N + 1];
        hmax = new double[N + 1];
        qmin = new double[N + 1];
        C = new double[N + 1];
        for (int i = 1; i <= N; i++) {
            S[i] = S[i - 1] + cin.nextInt();
        }

        double l = 0L, r = 1e4 + 100;
        double m;
        // 相当于 直接给你预估二分次数了 60
        for (int i = 1; i <= 60; i++) {
            m = (l + r) / 2;
            if (check01(m)) l = m;
            else r = m;
        }
        System.out.printf("%.3f", l);
        return 0L;
    }

    // 解法2
    public boolean check02(double x) {
        double minv = S[1] - x * 1;
        for (int i = 2; i < N; ++i) {
            if (S[N] - x * N - (S[i] - x * i) + minv <= 0) return true;
            minv = Math.min(minv, S[i] - x * i);
        }
        return false;
    }

    public double SabotagG02() {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        S = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            S[i] = S[i - 1] + cin.nextInt();
        }
        System.out.println(Arrays.toString(S));
        double l = 0L, r = 10000;
        while (r - l > 1e-5) {
            double m = (l + r) / 2;
            if (check02(m)) r = m;
            else l = m;
        }
        System.out.printf("%.3f", l);
        return 0L;

    }

}
