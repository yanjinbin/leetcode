package com.yanjinbin.leetcode;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// post系统设计算法角度  http://bit.ly/35qJnUj
// 花花酱视频链接： http://bit.ly/2D8jOLA
// HashMap<key,CacheNode> + PriorityQueue(minHeap,排序freq+tick)
public class LFUCache01 {
    // 计数
    int tick;
    // 长度
    int len;
    // 容量
    int capacity;
    PriorityQueue<CacheNode> minHeap;
    Map<Integer, CacheNode> map;

    public LFUCache01(int capacity) {
        // Write your code here
        this.capacity = capacity;
        this.len = 0;
        this.minHeap = new PriorityQueue<CacheNode>();
        this.map = new HashMap<Integer, CacheNode>();
        this.tick = 0;
    }

    public int get(int key) {
        if (capacity == 0) return -1;
        if (map.containsKey(key)) {
            CacheNode old = map.get(key);
            minHeap.remove(old);
            CacheNode nw = new CacheNode(key, old.value, old.freq + 1, this.tick++);
            map.put(key, nw);
            minHeap.offer(nw);
            return old.value;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        CacheNode nw = null;
        if (capacity == 0) return;
        if (map.containsKey(key)) {
            CacheNode old = map.get(key);
            minHeap.remove(old);
            nw = new CacheNode(key, value, old.freq + 1, this.tick++);
        } else if (len == capacity) {
            CacheNode delete = minHeap.poll();
            map.remove(delete.key);
            nw = new CacheNode(key, value, 1, this.tick++);
        } else {
            len++;
            nw = new CacheNode(key, value, 1, this.tick++);
        }
        map.put(key, nw);
        minHeap.offer(nw);
    }

    public class CacheNode implements Comparable<CacheNode> {
        int key;
        int value;
        int freq;
        int tick;

        @Override
        public int compareTo(CacheNode o) {
            if (this.freq == o.freq) {
                return this.tick - o.tick;
            } else {
                return this.freq - o.freq;
            }
        }

        public CacheNode(int key, int value, int freq, int tick) {
            this.key = key;
            this.value = value;
            this.freq = freq;
            this.tick = tick;
        }
    }
}


/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
