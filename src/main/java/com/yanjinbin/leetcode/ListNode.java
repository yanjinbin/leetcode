package com.yanjinbin.leetcode;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListNode {

    int val;

    @ToString.Exclude
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
