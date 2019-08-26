package com.yanjinbin.leetcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}