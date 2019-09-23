### DP专题 Summary
[背包九讲读后总结](https://drive.google.com/drive/folders/1gMiJgnOQLcntvPrQXHJehiIuuEuTPBGx): 
背包九讲问题 前面1-6讲还是不错的 7讲 可以树形DP解决  8讲 看不透 9讲讲的是问题的变形

[完全背包状态转移方程式 为何是 i 而不是 i-1](http://bit.ly/2NqGWMG)
这个推理还是挺棒棒的  也感谢之前大佬 关于 完全背包状态转移方程式 为何是 i 而不是 i-1  

01背包讲解:

待定视频:
https://www.cnblogs.com/en-heng/p/7257071.html

背包类型待定: 58  712 746 802 207   
-[X]  139 这是一道普通的DP题目,分治思想状态转移方程式: S[0,i) = S[0,j) || S[j,i)  , 0 <= j < i <= len(s);
-[X] 960 就是一道复杂的DP , 好像不是背包问题

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
区间DP[https://www.dreamwings.cn/stack/acm/dynamic-programming/interval-dp] 813 87 312 
- [X] 546 
 471 5 1000 1039 712 109 546 471 647 877(有可能是 https://cloud.tencent.com/developer/article/1445805)

DAG DP
354[http://bit.ly/2LyVM1f] 329[http://bit.ly/32H2Nms][!] 
802[http://bit.ly/2LAn4Eo] 207 [http://bit.ly/32JlndB]

拓扑排序 链式前向星
207 210 1048[http://bit.ly/32L41NG] 

树形DP [https://copyfuture.com/blogs-details/8cd66ceb5beb157c706b8c99d84e0b2c]
337 124 310 834 968 

单调栈
42 496  503 739
https://blog.csdn.net/qq_17550379/article/details/86519771
https://zhuanlan.zhihu.com/p/26465701
经典问题
-[X] 1143 最长公共子序列 LCS 
-[x] 300  最长上升子序列 LIC 
-[x] 674  最长连续递增序列 
-[x] 516  最长回文子序列 
-[x] 5 最长回文子串(马拉车算法)
72 编辑距离
最长公共子串

常用行业俗语:
剪枝==相当于求DP重叠子问题时候记忆化搜索
