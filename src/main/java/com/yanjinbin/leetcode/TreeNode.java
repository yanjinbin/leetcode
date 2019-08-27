package com.yanjinbin.leetcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

// 二叉树点
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {

    // extra
    String name;

    int val;
    // for leetcode 315
    @Exclude
    int count;

    TreeNode left;

    TreeNode right;


    TreeNode(int x) {
        val = x;
    }
}