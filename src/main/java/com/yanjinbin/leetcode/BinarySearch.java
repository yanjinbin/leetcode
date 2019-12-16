package com.yanjinbin.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BinarySearch {

    //69 平方根 解乏2 二分法求
    public int mySqrt1(int x) {
        // 无法处理x= Integer.MAX_VALUE;
        long l = 1;
        long r = x + 1;
        while (l < r) {
            long mid = l + (r - l) / 2;
            if (mid > x / mid) {//避免溢出
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


    // ② 378 有序矩阵中 第K小的元素
    // 解法2 二分查找
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length, lo = matrix[0][0], hi = matrix[n - 1][n - 1] + 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = lessEqual(matrix, mid);
            if (count < k) lo = mid + 1;
                // 为什么是mid-1呢 而不是mid
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
                lo = mid+1;
            }else{
                hi = mid;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int j=lo;j<lo+k;j++){
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

}
