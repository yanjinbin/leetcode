package com.yanjinbin.leetcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Tuple {
    public int  x,y,z;
    public Tuple(int x,int y,int z){
        this.x=x;this.y=y;this.z=z;
    }
}
