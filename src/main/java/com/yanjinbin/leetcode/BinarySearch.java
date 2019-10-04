package com.yanjinbin.leetcode;

import java.util.HashMap;
import java.util.Map;

public class BinarySearch {
    // 887. 鸡蛋掉落

    // 426 426. 将二叉搜索树转化为排序的双向链表

    // 1044. 最长重复子串


    //69 平方根 解乏2 二分法求
    public int mySqrt1(int x) {
        // 无法处理x= Integer.MAX_VALUE;
        long l = 1;
        long  r = x+1;
        while (l<r){
            long mid = l+(r-l)/2;
            if (mid>x/mid){
                r=mid;
            }else {
                l = mid+1;
            }
        }
        return  (int)(l-1);

        /*
        *
        *  if(x<=0) return 0;
        int res =1;
        int   l = 1;
        int   r = x;
        while (l<r){
            int mid = l+(r-l)/2;
            if (mid>x/mid){ // 避免溢出
                r=mid;
            }else {
                res = mid;
                l = mid+1;

            }
        }
        return  res;
        *
        * */
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
        int n = matrix.length, lo = matrix[0][0], hi = matrix[n - 1][n - 1]+1;
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

    // 493 翻转对 https://leetcode-cn.com/problems/reverse-pairs/

    // 327 区间和的个数 https://leetcode-cn.com/problems/count-of-range-sum/

    // 514 自由之路 https://leetcode-cn.com/problems/freedom-trail/


}
