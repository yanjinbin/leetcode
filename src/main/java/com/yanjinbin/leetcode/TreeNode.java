package com.yanjinbin.leetcode;

import lombok.*;

// 二叉树点
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {
    int val;
    @ToString.Exclude
    TreeNode left;
    @ToString.Exclude
    TreeNode right;
    // extra
    String name;

    TreeNode(int x) {
        val = x;
    }
}