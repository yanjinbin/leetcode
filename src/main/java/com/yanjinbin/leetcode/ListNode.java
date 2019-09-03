package com.yanjinbin.leetcode;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListNode {

    public int val;

    public ListNode next;

    public ListNode(int x) {
        val = x;
    }
}
