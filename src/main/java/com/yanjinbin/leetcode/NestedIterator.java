package com.yanjinbin.leetcode;

import java.util.*;

// 341 扁平化嵌套列表表达器 http://bit.ly/2L4sHus
public class NestedIterator implements Iterator<Integer> {

    public Queue<Integer> q = new LinkedList<>();

    public Stack<NestedInteger> s = new Stack<>();

    public NestedIterator(List<NestedInteger> list) {

        // 解法1
        flatQueue(list);

        // 解法2
        /*for (int i = list.size() - 1; i >= 0; i--) {
            s.push(list.get(i));
        }*/
    }


    @Override
    public Integer next() {
        return q.poll();

        /*NestedInteger item = s.pop();
        return item.getInteger();*/
    }

    @Override
    public boolean hasNext() {
        return !q.isEmpty();
        /*while (!s.isEmpty()) {
            NestedInteger item = s.peek();
            if (item.isInteger()) return true;
            s.pop();
            List<NestedInteger> list = item.getList();
            for (int i = list.size() - 1; i >= 0; i--) {
                s.push(list.get(i));
            }
        }
        return false;*/
    }

    public void flatQueue(List<NestedInteger> list) {
        for (NestedInteger item : list) {
            if (item.isInteger()) q.add(item.getInteger());
            else flatQueue(item.getList());
        }
    }
}

/*

  Your NestedIterator object will be instantiated and called as such:
  NestedIterator i = new NestedIterator(nestedList);
  while (i.hasNext()) v[f()] = i.next();
*/