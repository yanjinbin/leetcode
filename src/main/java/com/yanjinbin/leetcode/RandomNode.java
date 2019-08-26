package com.yanjinbin.leetcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RandomNode {

    public RandomNode random;

    public RandomNode next;

    public Integer val;
}
