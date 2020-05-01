package com.yanjinbin.leetcode;

import lombok.Data;

@Data
public class Edge {
    int next; // 同起点i的下一条编的存储位置
    int to; // edge[i].to表示第i条边的终点,
    int w; // edge[i].w为边权值

    // P5837
    int cost;
    int limit;

    public static void main(String[] args) {
        int i = 0x3f3f3f3f , j=0x7f7f7f7f;
        System.out.println(i+" "+Integer.toBinaryString(i));
        System.out.println(2*i+" "+Integer.toBinaryString(i+i));
        System.out.println(4*i+" "+Integer.toBinaryString(4*i));
        System.out.println(j+" "+Integer.toBinaryString(j));
        System.out.println(2*j+" "+Integer.toBinaryString(j*2));
        System.out.println(Integer.MAX_VALUE+" "+Integer.toBinaryString(Integer.MAX_VALUE));
    }

}
