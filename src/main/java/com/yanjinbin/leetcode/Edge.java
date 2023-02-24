package com.yanjinbin.leetcode;

import lombok.Data;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

@Data
public class Edge {
    int next; // 同起点i的下一条编的存储位置
    int to; // edge[i].to表示第i条边的终点,
    int w; // edge[i].w为边权值

    // P5837
    int cost;
    int limit;

    public static void main(String[] args) {
        Integer[] arrs = {1, 3, 2, 4};
        Arrays.sort(arrs, (o1, o2) -> o2 - o1);
        System.out.println(Arrays.toString(arrs));

        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>((a, b) -> a - b);
        minHeap.add(7);
        minHeap.add(2);
        minHeap.add(3);
        System.out.println(minHeap.poll());

        int i = 0x3f3f3f3f, j = 0x7f7f7f7f;
        System.out.println(i + " " + Integer.toBinaryString(i));
        System.out.println(2 * i + " " + Integer.toBinaryString(i + i));
        System.out.println(4 * i + " " + Integer.toBinaryString(4 * i));
        System.out.println(j + " " + Integer.toBinaryString(j));
        System.out.println(2 * j + " " + Integer.toBinaryString(j * 2));
        System.out.println(Integer.MAX_VALUE + " " + Integer.toBinaryString(Integer.MAX_VALUE));
/*
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient redisson = Redisson.create(config);
        RLock lock = redisson.getLock("anyLock");
        try {
            lock.lock();

        } finally {
            lock.unlock();
        }*/

    }



}
