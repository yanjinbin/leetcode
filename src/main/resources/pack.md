### DP专题 Summary
[背包九讲读后总结](https://drive.google.com/drive/folders/1gMiJgnOQLcntvPrQXHJehiIuuEuTPBGx): 
背包九讲问题 前面1-6讲还是不错的 7讲 可以树形DP解决  8讲 看不透 9讲讲的是问题的变形

[完全背包状态转移方程式 为何是 i 而不是 i-1](http://bit.ly/2NqGWMG)
这个推理还是挺棒棒的  也感谢之前大佬 关于 完全背包状态转移方程式 为何是 i 而不是 i-1  

[石头合并](http://bit.ly/2n1prX5)
01背包讲解:

待定视频:
https://www.cnblogs.com/en-heng/p/7257071.html

背包类型待定: 58  712 746 802 
- [X]  139 这是一道普通的DP题目,分治思想状态转移方程式: S[0,i) = S[0,j) || S[j,i)  , 0 <= j < i <= len(s);
- [X] 960 就是一道复杂的DP , 好像不是背包问题
- [x] 471 google题目  还不知道是那一类的DP题目?
- [X] 712 待定


01背包
- [X] 416 
- [x] 474
- [X] 1049
- [x] 377
   140 494] 

计数DP
115

完全背包
- [x] 322 
- [x] 518
- [X] 39  

多重背包
- [x] HDU 1059 
- [x] 691 798 799 不是多重背包问题 lintcode 798 799 是多重背包?

区间DP
区间DP
- [X] 87 
- [x] 813  
- [x] 312 
- [X] 546  
- [X] 647
- [ ] 488 暂定 看起来挺复杂的啊
- [X] 877 解决 这是移到区间DP题目 idea参见:http://bit.ly/2lBsaX1
- [X] 1000
- [ ] 1039 不想做了 区间DP 以后再来做

DAG DP
[354](http://bit.ly/2LyVM1f) [329](http://bit.ly/32H2Nms)
[802](http://bit.ly/2LAn4Eo) [207](http://bit.ly/32JlndB)

拓扑排序 链式前向星
- [x] 207
210 1048[http://bit.ly/32L41NG] 

树形DP [https://copyfuture.com/blogs-details/8cd66ceb5beb157c706b8c99d84e0b2c]
337 124 310 834 968 

单调栈
- [x] 42 
- [x] 496
- [x] 503
- [x] 739
https://blog.csdn.net/qq_17550379/article/details/86519771
https://zhuanlan.zhihu.com/p/26465701
https://www.cnblogs.com/grandyang/p/8887985.html

单调队列 
https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/discuss/204290/Monotonic-Queue-Summary

经典问题
- [X] 1143 最长公共子序列 LCS 
- [x] 300  最长上升子序列 LIC ˙
- [x] 674  最长连续递增序列 
- [x] 516  最长回文子序列 
- [x] 5 最长回文子串(马拉车算法)
- [X] 72 编辑距离

常用行业俗语:
剪枝==相当于求DP重叠子问题时候记忆化搜索

p2365 CDQ分治/斜率优化/单调队列 https://www.luogu.com.cn/problem/P2365
// HDU 6148 Valley Number

// CF55D Beautiful numbers

// CF628D Magic Numbers

// CF401D Roman and Numbers


//字符串

//trie kmp AC自动机 LC 30 1032

// KMP算法与AC自动机. KMP算法(28 459)——用于单模匹配。 AC自动机——用于多模匹配，需要了解KMP原理和Trie树。


    // graph
//   拓扑排序（207，210，269，329，444，1203，802）
    // 最短路径 743 flyod 和dikstra算法 787 Bellman-Ford算法
    // 最短路径743，505，787，847，1091，

    // 最小生成树 kruskal prim Boruvka 算法 (1130,  https://www.acwing.com/file_system/file/content/whole/index/content/160894/ 1135 https://www.okcode.net/article/43507)  tarjan联通
// 卡特兰数96 等差等比数列 413 素数筛法 204 欧拉定理 507 1015 费马小定理 372
    // 剩余定理/贝祖定理 1250