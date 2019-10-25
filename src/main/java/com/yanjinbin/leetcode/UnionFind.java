package com.yanjinbin.leetcode;


// https://oi-wiki.org/ds/dsu/
// https://leetcode-cn.com/tag/union-find/
public class UnionFind {

    int[] fa;
    // 查找
    public int find(int x){
        if (fa[x]==x){
            return x;
        }else {
          return find(fa[x]);
        }
    }

    // 路径压缩
    public int findCompress(int x){
        if (x!=fa[x]){
            fa[x]=findCompress(fa[x]);
        }
        return x;
    }
    // 合并

    public void unionSet(int x,int y){
        x= find(x);
        y= find(y);
        if (x==y)return;
        fa[x]=y;// 把X的祖先变成Y的儿子
    }

    // 928 尽量减少恶意软件的传播 II

    /// 803 打砖块

    // 1101. 彼此熟识的最早时间

    // 399. 除法求值

    // 721 账户合并

    // 778 水位上升的泳池中游泳

}
