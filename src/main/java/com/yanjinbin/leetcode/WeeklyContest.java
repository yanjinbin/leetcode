package com.yanjinbin.leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;

public class WeeklyContest {

    // 1186
    public int maximumSum(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            if (i > max) max = i;
        }
        if (max <= 0) return max;
        int s0 = 0;
        int s1 = 0;// with  one deletion （optional） with one delete optional
        int ans = 0;
        for (int num : arr) {
            s1 = Math.max(s0, s1 + num);
            s0 = s0 + num;
            ans = Math.max(ans, Math.max(s1, s0));
            if (s0 < 0) s0 = 0;
            if (s1 < 0) s1 = 0;
        }
        return ans;
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((o1,o2)-> o1-o2);
        for (int i=1;i<10;i++){
            if (minHeap.size()>5){
                minHeap.poll();
            }
            minHeap.add(i);

        }
        System.out.println(minHeap.peek());
    }


}
