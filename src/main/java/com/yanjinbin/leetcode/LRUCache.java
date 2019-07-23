package com.yanjinbin.leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class LRUCache {

    private int capacity;

    private LinkedList<Integer> elems;

    private Map<Integer, Integer> m;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        elems = new LinkedList<>();
        m = new HashMap<>();
    }

    public int get(int key) {
        if (m.containsKey(key)) {
            // java 的 linkedlist so bad
            elems.remove((Integer) key);
            elems.addFirst(key);
            return m.get(key);
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if (m.containsKey(key)) {
            // 容量不增加
            m.put(key, value);
            elems.remove((Integer) key);
            elems.addFirst(key);
        } else {
            m.put(key, value);
            elems.addFirst(key);
            // 判断容量
            if (m.size() > capacity) {
                Integer delKey = elems.pollLast();
                m.remove(delKey);
            }
        }
    }
}