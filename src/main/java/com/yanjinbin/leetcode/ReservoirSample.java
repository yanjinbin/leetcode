package com.yanjinbin.leetcode;

import java.util.List;
import java.util.Random;

// 水塘抽样法 398. 随机数索引 382 随机链表节点
// http://bit.ly/2YUQzZe http://bit.ly/2YZoz6E
public class ReservoirSample {

    public ListNode head;

    public int[] nums;

    public Random random;

    public ReservoirSample(int[] nums) {
        this.nums = nums;
        this.random = new Random();
    }

    public int pick(int target) {
        int index = -1;
        int cnt = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target && random.nextInt(++cnt) == 0) {
                index = i;
            }
        }
        return index;
    }


    // 382
    public ReservoirSample(ListNode head) {
        this.head = head;
        this.random = new Random();
    }

    public int getRandom() {
        int ret = -1;
        ListNode cur = head;
        for (int i = 1; cur != null; i++) {
            if (random.nextInt(i) == 0) {
                ret = cur.val;
            }
            cur = cur.next;
        }
        return ret;

    }

}
