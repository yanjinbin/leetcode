package com.yanjinbin.leetcode;

import java.util.Stack;

// 栈实现队列
public class QueueByStack {
    private Stack<Integer> inStack;
    private Stack<Integer> outStack;

    /** Initialize your data structure here. */
    public QueueByStack() {
        inStack = new Stack<>();
        outStack = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        // 倒叙一遍，然后正序一遍即可
        while (!outStack.empty()) {
            inStack.push(outStack.pop());
        }
        inStack.push(x);

        while (!inStack.empty()) {
            outStack.push(inStack.pop());
        }
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        return outStack.pop();
    }

    /** Get the front element. */
    public int peek() {
        return outStack.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return outStack.empty();
    }

}
