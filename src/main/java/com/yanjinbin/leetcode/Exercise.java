package com.yanjinbin.leetcode;

public class Exercise {
    // 链表反转
    // 206
    public ListNode reverseList(ListNode head) {
        return help(null, head);
    }

    public ListNode help(ListNode prev, ListNode cur) {
        if (cur == null) return prev;
        ListNode nxt = cur.next;
        cur.next = prev;
        return help(cur, nxt);
    }

    public ListNode reverseList_(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode nxt = cur.next;
            cur.next = prev;
            prev = cur;
            cur = nxt;
        }
        return prev;
    }

    // 92  还是比较难以理解
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummyNode = new ListNode(-1);
        ListNode cur = dummyNode;
        ListNode pre = dummyNode;
        dummyNode.next = cur;
        for (int i = 0; i < left; i++) {
            pre = cur;
            cur = cur.next;
        }
        //  1    2    3    4   要变成 1->3->2->4

        //  1->2->4
        //  3->4

        // 1->2->4
        // 3->2

        // 1->3->2->4


        //  pre cur   nxt  nnxt
        for (int i = left; i < right; i++) {

            ListNode nxt = cur.next; //
            // pre cur nnxt
            // 1  2 -> 4
            //    nxt nnxt
            //    3 -> 4
            cur.next = nxt.next;
            // pre cur  nxt
            //  1  2->4
            //    3->2
            nxt.next = pre.next;
            // 1->3->2->4
            pre.next = nxt;
        }


        return dummyNode.next;

    }


    // 25
    //  1-2-3-4-5-6-7-8
    //  3-2-1 6-5-4 8 7
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode cur = head;
        int count = 0;
        while (count < k) {
            if (cur == null) return head;
            cur = cur.next;
            count++;
        }
        ListNode pre = reverseKGroup(cur, k);
        while (count > 0) {
            ListNode nxt = head.next;
            head.next = pre;
            pre = head;
            head = nxt;
            count--;
        }
        return pre;
    }


}
