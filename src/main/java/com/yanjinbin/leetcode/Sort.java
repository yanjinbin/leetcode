package com.yanjinbin.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Sort {
    // bucket sort
    // 这不是真正的桶排序 wikisha
    /*private int indexFor(int a, int min, int step) {
        return (a - min) / step;
    }

    public void buckerSort(int[] arr, int step) {
        int max = arr[0];
        int min = arr[0];
        for (int a : arr) {
            if (max < a) {
                max = a;
            }
            if (min > a) {
                min = a;
            }
        }

        int bucketNum = max / step - min / step + 1;
        List<List<Integer>> bucketList = new ArrayList<List<Integer>>();
        for (int i = 1; i <= bucketNum; i++) {
            bucketList.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < arr.length; i++) {
            int index = indexFor(arr[i], min, step);
            bucketList.get(index).add(arr[i]);
        }

        List<Integer> bucket = null;
        int index = 0;
        for (int i = 0; i < bucketNum; i++) {
            bucket = bucketList.get(i);
            insertSort(bucket);
            for (int k : bucket) {
                arr[index++] = k;
            }
        }
    }


    // 把桶內元素插入排序
    public void insertSort(List<Integer> bucket) {
        for (int i = 1; i < bucket.size(); i++) {
            int temp = bucket.get(i);
            int j = i - 1;
            for (; j >= 0 && bucket.get(j) > temp; j--) {
                bucket.set(j + 1, bucket.get(j));
            }
            bucket.set(j + 1, temp);
        }
    }
*/


}
