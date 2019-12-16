package com.yanjinbin.leetcode;

public class DivideConquer {

    // [tag:微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
    // 4 两个有序数组的中位数 https://youtu.be/KB9IcSCDQ9k
    // tips: 看懂哪个模型即可 m1-1,m1, m2-1,m2
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m > n) return findMedianSortedArrays(nums2, nums1);
        int k = (m + n + 1) / 2;
        int l = 0, r = m;
        while (l < r) {
            int m1 = (r - l) / 2 + l;
            int m2 = k - m1;
            if (nums1[m1] < nums2[m2 - 1]) {
                // nums1[m1]是没有选到的,nums2[m2-1]是选到的 如果没被选到的<被选到的,那么L增大
                l = m1 + 1;
            } else {
                r = m1;
            }
        }
        int m1 = l;
        int m2 = k - l;
        int c1 = Math.max(
                m1 <= 0 ? Integer.MIN_VALUE : nums1[m1 - 1],
                m2 <= 0 ? Integer.MIN_VALUE : nums2[m2 - 1]
        );
        if ((m + n) % 2 == 1) {
            return c1;
        }
        int c2 = Math.min(m1 >= m ? Integer.MAX_VALUE : nums1[m1], m2 >= n ? Integer.MAX_VALUE : nums2[m2]);
        return (c1 + c2) * 0.5;
    }

    //②  [tag:微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
    // 23. 合并K个排序链表 Merge k Sorted Lists
    // http://bit.ly/2LtXUbI
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return mergeKLists(lists, 0, lists.length - 1);
    }

    // 采用分治思想，递归解决此问题
    public ListNode mergeKLists(ListNode[] lists, int start, int end) {
        if (start > end) {
            return null;
        } else if (start == end) {
            return lists[start];
        }
        int mid = (end - start) / 2 + start;
        ListNode l1 = mergeKLists(lists, start, mid);
        ListNode l2 = mergeKLists(lists, mid + 1, end);
        return mergeTwoSortedList(l1, l2);
    }

    //   递归解法
    public ListNode mergeTwoSortedList(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoSortedList(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoSortedList(l1, l2.next);
            return l2;
        }
    }

}
