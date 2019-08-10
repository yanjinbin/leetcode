package com.yanjinbin.leetcode;


import lombok.Data;

import java.util.Arrays;
import java.util.Random;

// 384 打乱数组 http://bit.ly/2ZYlm4s  蒙特卡过方法检验数组的随机正确性 考察点在于洗牌种类数目的的正确性
@Data
public class ShuffleArray {
    private int randRange(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public int[] nums;

    public Random random;

    public ShuffleArray(int[] nums) {
        this.nums = nums;
        this.random = new Random();

    }

    /**
     * Resets the array to its original configuration and return it.
     */
    public int[] reset() {
        return nums;
    }

    /**
     * Returns a random shuffling of the array.
     */
    public int[] shuffle() {
        if (nums == null) return null;
        int[] a = Arrays.copyOf(nums, nums.length);
        for (int i = 0; i < nums.length; i++) {
            int rand = random.nextInt(i + 1);
            swap(a, i, rand);
        }
        return a;
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
