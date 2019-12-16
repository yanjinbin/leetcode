package com.yanjinbin.leetcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 多叉树节点
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    public int val;
    //@ToString.Exclude
    public List<Node> children;

    public String name;

    // 116 117专用
    @ToString.Exclude
    public Node left,right,next;

}