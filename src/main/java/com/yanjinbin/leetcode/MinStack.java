package com.yanjinbin.leetcode;

import java.util.Stack;

public class MinStack {

    /*private Stack<Integer> s1=new Stack<>();
    private Stack<Integer> s2=new Stack<>();

    */
    /**
     * initialize your data structure here.
     *//*
    public MinStack() {

    }

    public void push(int x) {
        s1.push(x);
        if (s2.isEmpty() || s2.peek() >= x) s2.push(x);
    }

    public void pop() {
        Integer x = s1.pop();
        if (x == s2.peek()) ;
        s2.pop();

    }

    public int top() {
        return s1.peek();
    }

    public int getMin() {
        return s2.peek();
    }*/

    private int min_val = Integer.MAX_VALUE;
    private Stack<Integer> s = new Stack<>();

    /**
     * initialize your data structure here.
     */
    public MinStack() {

    }
    // 解法2的意思把 minVal 是第二小的时候先压入栈,然后压入第一小的minVal 然后 pop的时候 去判断 pop的元素是不是minVal 那么再次pop 并将这个值更新为minVal即可
    //
    //解决的最小栈 输入为-2,0,-3的时候 如何在pop -3的时候 getMin获取的值是-2
    //
    //本质就是更新Minval的时候 先存入原先的minVal
    //
    //不过这道题目 确实挺无聊的阿 哈哈😄

    public void push(int x) {
        if (x <= min_val) {
            s.push(min_val);
            min_val = x;
        }
        s.push(x);
    }

    public void pop() {
        if (s.pop() == min_val) min_val = s.pop();
    }

    public int top() {
        return s.peek();
    }

    public int getMin() {
        return min_val;
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */