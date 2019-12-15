package com.yanjinbin.leetcode;

import java.util.Stack;

// LC 155
public class MinStack {

    /*
    private Stack<Integer> s1=new Stack<>();
    private Stack<Integer> s2=new Stack<>();
    public MinStack() {

    }

    public void push(int x) {
        s1.push(x);
        if (s2.isEmpty() || s2.peek() >= x) s2.push(x); //维护 S2 单调递减栈。
    }

    public void pop() {
        int  x = s1.pop();
        // 如果X是s2.peek元素，那么s2要剔除。
        if (x == s2.peek()) s2.pop();
    }

    public int top() {
        return s1.peek();
    }

    public int getMin() {
        return s2.peek();
    }*/


    // 上面需要2个栈，故空间复杂度较高。
    private int min_val = Integer.MAX_VALUE;
    private Stack<Integer> s = new Stack<>();
    public MinStack() {

    }
    public void push(int x) {
        if (x <= min_val) {
            s.push(min_val);// 多一次压入
            min_val = x;
        }
        s.push(x);
    }

    public void pop() {
        if (s.pop() == min_val) min_val = s.pop(); // pop出多余的
    }

    public int top() {
        return s.peek();
    }

    public int getMin() {
        return min_val;
    }
}
