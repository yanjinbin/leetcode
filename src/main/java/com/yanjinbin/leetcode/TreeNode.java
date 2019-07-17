package com.yanjinbin.leetcode;

import lombok.*;

// 二叉树点
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {

    // extra
    String name;

    int val;

    TreeNode left;

    TreeNode right;


    TreeNode(int x) {
        val = x;
    }
}