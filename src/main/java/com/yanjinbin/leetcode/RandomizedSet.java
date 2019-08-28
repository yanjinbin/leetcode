package com.yanjinbin.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

// 花花酱 视频---> https://youtu.be/y240Qh9H9uk  删除非尾部元素,需要交换数组内被删除元素和尾部元素
public class RandomizedSet {
    public ArrayList<Integer> vals; // index->val
    public HashMap<Integer, Integer> map;//  val->index
    public Random random;

    /**
     * Initialize your data structure here.
     */
    public RandomizedSet() {
        this.vals = new ArrayList<>();
        this.map = new HashMap<>();
        this.random = new Random();
    }

    /**
     * Inserts a value to the set. Returns true if the set did not already contain the specified element.
     */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        // update
        map.put(val, vals.size());
        vals.add(val);
        return true;
    }

    /**
     * Removes a value from the set. Returns true if the set contained the specified element.
     */
    public boolean remove(int val) {
        if (!map.containsKey(val)) return false;
        int idx = map.get(val);
        //
        if (idx < vals.size() - 1) {
            int lastOne = vals.get(vals.size() - 1);
            map.put(lastOne, idx);
            vals.set(idx, val);
        }

        map.remove(val);
        vals.remove(vals.size() - 1);
        return true;
    }

    /**
     * Get a random element from the set.
     */
    public int getRandom() {
        return vals.get(random.nextInt(vals.size()));
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */