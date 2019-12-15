package com.yanjinbin.leetcode;

import java.util.Stack;

// 716 https://www.cnblogs.com/grandyang/p/7823424.html
// 看视频就懂了 一个常规栈和一个辅助栈
// https://youtu.be/y7T00suWkrY?t=131
// 这道题目API和MinStack还是有点不同的。
public class MaxStack {

    public Stack<Integer> s;
    public Stack<Integer> maxS;

    public MaxStack() {
        s = new Stack<>();
        maxS = new Stack<>();
    }

    public void push(int x) {
        int max = maxS.isEmpty() ? x : maxS.peek();
        maxS.push(max > x ? max : x);
        s.push(x);
    }

    public int pop() {
        maxS.pop();
        return s.pop();
    }

    public int top() {
        return s.peek();
    }

    public int peekMax() {
        return maxS.peek();
    }

    public int popMax() {
        int max = peekMax();
        Stack<Integer> buffer = new Stack<>();
        while (top() != max){
            //出栈元素转移buf
            System.out.println("=======");
            buffer.push(pop());}
        // 相等的时候pop
        pop();
        //入栈，重新放回
        while (!buffer.isEmpty()) push(buffer.pop());
        return max;
    }

    public static void main(String[] args) {
        MaxStack ms = new MaxStack();
        ms.push(2);ms.push(1);ms.push(5);ms.push(4);ms.push(3);ms.push(9);
        System.out.println(ms.popMax());
        System.out.println(ms.popMax());
        System.out.println(ms.popMax());
    }
}