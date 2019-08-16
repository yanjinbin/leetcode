package com.yanjinbin.leetcode;

import java.util.PriorityQueue;

public class MedianFinder {
    private int count;
    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;

    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
        this.count = 0;
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>((a, b) -> b - a);
    }
    // max 流入在前,min流入在后
    // 思想就是二叉堆的优先队列 插入和删除事logN 基于数组或者链表的要么1 要么N 而且要排序 排序NlogN
    // 堆 poll最值 只需要对数级别 logN

    public void addNum(int num) {
        count++;
        maxHeap.add(num);
        minHeap.add(maxHeap.poll());
        if ((count & 1) != 0) {// 奇数的话 从min堆里面 拿回一个
            maxHeap.add(minHeap.poll());
        }
    }

    public double findMedian() {
        if ((count & 1) == 0) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
        return maxHeap.peek();
    }
}
