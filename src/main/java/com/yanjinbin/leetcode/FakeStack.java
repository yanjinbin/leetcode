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
        int len = q1.size();
        for (int i=0;i<len;i++){
            q1.add(q1.poll());
        }
        return q1.poll();
    }

    public static void main(String[] args) {
        FakeStack s = new FakeStack();
        s.push(1);s.push(2);s.push(3);s.push(4);
        System.out.println(s.q1);
        System.out.println(s.pop());
        System.out.println(s.q1);
    }
}
