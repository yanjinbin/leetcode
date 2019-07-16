package com.yanjinbin.leetcode;

import lombok.*;

// 二叉树点
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {
    int val;

    TreeNode left;

    TreeNode right;
    // extra
    String name;

    TreeNode(int x) {
        val = x;
    }
}