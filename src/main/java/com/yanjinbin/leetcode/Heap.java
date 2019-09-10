package com.yanjinbin.leetcode;

import lombok.Data;

import java.util.Arrays;

@Data
public class Heap {

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param pq the array to be sorted
     */
    public void sort(Comparable[] pq) {
        // System.out.println(Arrays.toString(pq));
        int n = pq.length;
        for (int k = n / 2; k >= 1; k--)
            sink(pq, k, n);
        System.out.println("====堆构造结果====");
        System.out.println(Arrays.toString(pq));
        while (n > 1) {
            exch(pq, 1, n--);
            sink(pq, 1, n);
        }
    }

    public void sort1(Comparable[] pq) {
        int n = pq.length;
        for (int k = n / 2 - 1; k >= 0; k--) {
            sink1(pq, k, n);
        }
        System.out.println("====堆构造结果====");
        System.out.println(Arrays.toString(pq));
        n = n - 1;
        while (n > 0) {
            swap(pq, 0, n--);
            sink1(pq, 0, n);
        }
    }

    /***************************************************************************
     * Helper functions to restore the heap invariant.
     ***************************************************************************/

    public void sink(Comparable[] pq, int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(pq, j, j + 1)) j++;
            if (!less(pq, k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    /***************************************************************************
     * Helper functions for comparisons and swaps.
     * Indices are "off-by-one" to support 1-based indexing.
     ***************************************************************************/
    public static boolean less(Comparable[] pq, int i, int j) {
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }

    public boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }


    public void exch(Object[] pq, int i, int j) {
        Object swap = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = swap;
    }

    public void swap(Object[] pq, int i, int j) {
        Object tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

    // 二叉堆从 0开始

    public void sink1(Comparable[] pq, int k, int n) {
                // 注意这里的while 条件不等式 2k+1 <=n 和  2k+2 <n的区别
                // if n=6, k 能不能取2的问题  k应该能取2, 因为 2只有一个叶子节点就是5
                while (2 * k  <= n-1) {
                    int j = 2 * k + 1;
                    if (j + 1 < n && less(pq[j], pq[j + 1])) j++;
                    if (!less(pq[k], pq[j])) break;
                    swap(pq, k, j);
            k = j;
        }
    }


    /**
     * Reads in a sequence of strings from standard input; heapsorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Integer[] a = new Integer[]{8, 7, 9, 5, 6, 4, 2, 3, 1, 0};
        // 二叉堆 索引从1开始 parent = i, child = 2i,2i+1， parent=child/2;
        Heap HEAP = new Heap();
        HEAP.sort(a);
        System.out.println(Arrays.toString(a));
        // 二叉堆 索引从0 开始
        Integer[] a1 = new Integer[]{8, 7, 9, 5, 6, 4, 2, 3, 1, 0};
        HEAP.sort1(a1);
        System.out.println(Arrays.toString(a1));
    }
}