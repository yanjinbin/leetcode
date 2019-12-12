package com.yanjinbin.leetcode;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
public class Point {
    public int x;
    public int y;
    public int val;
    public Point(int x,int y,int val){
        this.x=x;
        this.y=y;
        this.val=val;
    }
}
