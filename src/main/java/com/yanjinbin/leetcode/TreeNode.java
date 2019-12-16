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

    public int val;
    // for leetcode 315
    @Exclude
    public int count;

    public TreeNode left;
    @Exclude
    public TreeNode next;

    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }
}