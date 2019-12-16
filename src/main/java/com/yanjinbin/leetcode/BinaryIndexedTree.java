package com.yanjinbin.leetcode;

import java.util.Arrays;

// http://bit.ly/37MP9Sl
// https://en.wikipedia.org/wiki/Fenwick_tree
// http://bit.ly/35R5fIP
//  同名（Fenwick Tree） ，
public class BinaryIndexedTree {
    int[] nums;// 原数组   based 0 index
    int[] tree;// 树状数组  based 1 index

    // 构建
    public BinaryIndexedTree(int[] nums) {
        this.nums = nums;
        this.tree = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            tree[i + 1] = nums[i];
        }
        System.out.println(" 注意有些 tree[index] 会被更新多次!!!");
        System.out.println(Arrays.toString(tree));
        for (int i = 1; i < tree.length; i++) {
            int j = i + lowBit(i);

            if (j < tree.length) {
                System.out.println("i的值：" + i + "  " + tree[i] + "\t\tj的值：" + j + "  " + tree[j]);
                // 注意有些 tree[index] 会被更新多次!!!
                tree[j] += tree[i];
                System.out.println(Arrays.toString(tree));
            }
        }
    }
    // 返回x 二进制 右边第一个1代表的值。

    // 1110 -->  0001 ---> 0010
    public int lowBit(int x) {
        return x & (~x + 1);
    }

    public void add(int idx, int delta) {
        nums[idx] += delta;
        idx++;
        while (idx < tree.length) {
            tree[idx] += delta;
            idx = idx + lowBit(idx);
        }
    }

    public int prefixSum(int idx) {
        idx++;
        int ans = 0;
        while (idx > 0) {
            ans += tree[idx];
            idx = idx - lowBit(idx);
        }
        return ans;
    }

    // 区间和

    /**
     * range sum
     *
     * @param from inclusive
     * @param to   inclusive
     * @return
     */
    public int rangeSum(int from, int to) {
        return prefixSum(to) - prefixSum(from - 1);
    }

    // 参考：http://bit.ly/2L7PiWO
    // http://bit.ly/2OXl1Lh
    int find_kth(int k) {

        int ans = 0, cnt = 0;
        //这里的20适当的取值，与MAX_VAL有关，一般取lg(MAX_VAL)
        throw new IllegalStateException("没看懂带思考");
     /*
      for (int i = 20; i >= 0; i--) {

            int maxN = -1;//暂时看不懂
            ans += (1 << i);
            if (ans >= maxN || cnt + tree[ans] >= k)
                ans -= (1 << i);
            else
                cnt += tree[ans];
        }
        return ans + 1;
        */
    }


    // 剑指offer 36 逆序对
    public static void main(String[] args) {

        int[] nums = new int[]{1, 7, 3, 0, 5, 8, 3, 2, 6, 2, 1, 1, 4, 5};
        BinaryIndexedTree fenWick = new BinaryIndexedTree(nums);
        System.out.println(Arrays.toString(fenWick.tree));
        // 求第K大
        assert fenWick.find_kth(8) == 4;

        int x = 8;
        int y = -x;
        System.out.println(Integer.toBinaryString(8));
        System.out.println(Integer.toBinaryString(-8));
        System.out.println(Integer.toBinaryString(~8));
        System.out.println(x & (-x));

        System.out.println(Integer.toBinaryString(5));
        System.out.println(Integer.toBinaryString(-5));
        System.out.println(5 & (-5));

      /*  for (int i = 1; i <= 14; i++) {
            int j = i - (i & (-i));
            if (j <= 14)
                System.out.println("i的值：" + i + "\tj的值：" + j);
        }
        System.out.println("==========");

        for (int i = 1; i <= 14; i++) {
            int j = i + (i & (-i));
            if (j <= 14)
                System.out.println("i的值：" + i + "\tj的值：" + j);
        }*/
    }

}
