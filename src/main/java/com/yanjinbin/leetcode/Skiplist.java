package com.yanjinbin.leetcode;

import java.util.Random;
import java.util.Stack;

class Skiplist {
    public SkipNode dummy;

    public Skiplist() {
        dummy = new SkipNode(-1, null, null);
    }

    public boolean search(int target) {
        SkipNode cur = dummy;
        while (cur != null) {
            while (cur.right != null && cur.right.val < target) {// 选cur.right而不是cur是因为cur遍历一层之后要更新
                cur = cur.right;
            }
            if (cur.right != null && cur.right.val == target) return true;
            cur = cur.down;
        }
        return false;
    }

    public void add(int num) {
        Stack<SkipNode> s = new Stack<>();
        SkipNode cur = dummy;
        while (cur != null) {
            while (cur.right != null && cur.right.val < num) {
                cur = cur.right;
            }
            s.push(cur);
            cur = cur.down;
        }
        boolean insert = true;
        SkipNode down = null;
        while (insert && !s.isEmpty()) {
            SkipNode item = s.pop();
            item.right = new SkipNode(num, item.right, down);
            down = item.right;
            insert = new Random().nextInt(2) == 0;
        }
        // new layer
        if (insert) {
            this.dummy = new SkipNode(-1, null, dummy);
        }

    }

    public boolean erase(int num) {
        SkipNode cur = dummy;
        boolean found = false;
        while (cur != null) {
            while (cur.right != null && cur.right.val < num) {
                cur = cur.right;
            }
            if (cur.right != null && cur.right.val == num) {
                cur.right = cur.right.right;
                found = true;
            }
            cur = cur.down;
        }
        return found;
    }
}

class SkipNode {
    int val;
    SkipNode right;
    SkipNode down;
    public SkipNode(int val, SkipNode right, SkipNode down) {
        this.val = val;
        this.right = right;
        this.down = down;
    }
}
/**
 * Your Skiplist object will be instantiated and called as such:
 * Skiplist obj = new Skiplist();
 * boolean param_1 = obj.search(target);
 * obj.add(num);
 * boolean param_3 = obj.erase(num);
 */