package com.yanjinbin.leetcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
// 队列实现栈
public class FakeStack {
    public Queue<Integer> q1 = new LinkedList<>();


    public void push(int i) {
        q1.add(i);
    }

    public int pop() {
        if (q1.isEmpty())return -1;
        int len = q1.size()-1;
        for (int i=0;i<len;i++){
            q1.add(q1.poll());
        }
        return q1.poll();
    }
}
