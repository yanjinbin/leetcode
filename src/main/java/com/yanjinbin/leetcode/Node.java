package com.yanjinbin.leetcode;

import lombok.*;

import java.util.List;

/**
 * 多叉树节点
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Node {
    public int val;
    //@ToString.Exclude
    public List<Node> children;

    public String name;

}