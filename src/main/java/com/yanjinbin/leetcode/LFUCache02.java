package com.yanjinbin.leetcode;

import lombok.val;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// 花花酱视频链接： http://bit.ly/2D8jOLA
// hashMap+LinkedHashSet处理 参考链接：http://bit.ly/33eiHoc 本质上都是map key-->val, key--cnt,freq-->set
// tips:不能用数组或者链表来处理，不能兼顾remove和get 复杂度O(1)
public class LFUCache02 {

    HashMap<Integer, Integer> vals;// key-val
    HashMap<Integer, Integer> counts;// key--freq 记录key在List中的位置。
    HashMap<Integer, LinkedHashSet<Integer>> lists;// freq-cnt 最近最少驱逐策略
    int cap;
    int min = -1;

    public LFUCache02(int capacity) {
        cap = capacity;
        vals = new HashMap<>();
        counts = new HashMap<>();
        lists = new HashMap<>();
        lists.put(1, new LinkedHashSet<>());
    }

    public int get(int key) {
        if (!vals.containsKey(key)) {
            return -1;
        }
        int cnt = counts.get(key);
        counts.put(key, cnt + 1);
        lists.get(cnt).remove(key);
        // corner case
        if (cnt == min && lists.get(cnt).size() == 0) {
            min++;
        }
        if (!lists.containsKey(cnt + 1)) {
            lists.put(cnt + 1, new LinkedHashSet<>());
        }
        lists.get(cnt + 1).add(key);
        return vals.get(key);
    }

    public void put(int key, int val) {
        if (cap <= 0) return;
        if (vals.containsKey(key)) {
            vals.put(key, val);
            get(key);
            return;
        }
        // corner case
        if (vals.size() >= cap) {
            int evict = lists.get(min).iterator().next();
            lists.get(min).remove(evict);
            vals.remove(evict);
        }
        vals.put(key, val);
        counts.put(key, 1);
        min = 1;
        lists.get(1).add(key);
    }

}
