package com.yanjinbin.leetcode;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
// 看看就好了  太麻烦了
// leetcode 460 LFU缓存 https://leetcode-cn.com/problems/lfu-cache/
// 抄的答案 来自 http://bit.ly/31jpb5b  这道题目 太麻烦了,一点也不适合 理解下几个变量的设置就行
class LFUCache {
    Map<Integer, Set<Integer>> freqToKeys; // cnt==> key list
    Map<Integer,Integer> map;
    Map<Integer,Integer> keyToFreq;
    int min;// 最少使用频次
    int size;
    int capacity;
    public LFUCache(int capacity) {
        this.capacity=capacity;
        map = new HashMap<>();
        keyToFreq= new HashMap<>();
        freqToKeys = new HashMap<>();
        min=0;
        size = 0;
    }

    private void updateFreq(int key){
        int frequency = keyToFreq.get(key);
        Set<Integer> cur = freqToKeys.get(frequency);
        cur.remove(key);
        freqToKeys.put(frequency,cur);
        if(cur.size()==0&&frequency==min)++min;
        ++frequency;
        Set<Integer> next = freqToKeys.getOrDefault(frequency,new LinkedHashSet<>());
        next.add(key);
        freqToKeys.put(frequency,next);
        keyToFreq.put(key,frequency);
    }

    public int get(int key) {
        if(!keyToFreq.containsKey(key))return -1;
        updateFreq(key);
        return map.get(key);
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            map.put(key,value);
            updateFreq(key);
        }
        else{
            if(size==capacity){
                Set<Integer> minFreq = freqToKeys.get(min);
                if(size>0){
                    Integer evict = minFreq.iterator().next();
                    minFreq.remove(evict);
                    keyToFreq.remove(evict);
                    map.remove(evict);
                    freqToKeys.put(min,minFreq);
                    --size;
                }
                else{
                    return;
                }
            }
            map.put(key,value);
            Set<Integer> firstSet = freqToKeys.getOrDefault(0,new LinkedHashSet<>());
            firstSet.add(key);
            freqToKeys.put(0,firstSet);
            keyToFreq.put(key,0);
            min=0;
            ++size;
        }
    }
}