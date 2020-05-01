package com.yanjinbin.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// https://oi-wiki.org/dp dp大纲
// dp 比较经典的 01背包问题 完全背包问题 多重背包问题 区间dp
// 背包问题九讲:https://drive.google.com/file/d/1VTBouzm_LrpTGdbQ8PtWg5P96CAo4hiu/view?usp=sharing
public class Pack {


    // 01背包问题 代表题目 https://www.luogu.org/problem/P2871
    // 01背包问题
    // 有N件物品,背包容量为V,每次装入第I件物品的花费是Ci,获得的价值是Wi,试求出,在尽量装满背包,扣除费用之后背包收益最大,
    // Time Complexity: O(VN) , Space Complexity: O(VN)
    public int ZeroOnePack(int N, int V, int[] C, int[] W) {
        int[][] dp = new int[N + 1][V + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= V; j++) {
                if (C[i] > j) dp[i + 1][j] = dp[i][j];
                    // C[i]代表第i+1件
                else dp[i + 1][j] = Math.max(dp[i][j], dp[i][j - C[i]] + W[i]);
            }
        }
        return dp[N][V];
    }

    // MLE error: https://www.luogu.org/record/24011470
    public int ZeroOnePack_ME(int N, int V, int[] C, int[] W) {
        int[][] dp = new int[N + 1][V + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= V; j++) {
                if (C[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - C[i - 1]] + W[i - 1]);
                }
            }
        }
        return dp[N][V];
    }

 /*   public int ZeroOnePack_Wrong(int N, int V, int[] C, int[] W) {
        int[][] dp = new int[N + 1][V + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= V; j++) { // 递增
                System.out.println(i + " || " + j);
                if (C[i] > j) dp[i][j] = dp[i - 1][j];
                    // 这个方程式 依旧是有问题的 并不是拿个状态转移方程式 因为C[i] W[i]的取值  注意i代表的是什么意思
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - C[i]] + W[i]);
            }
        }
        return dp[N][V];
    }*/


    // 降维,二维DP --> 一维DP
    // 代码通过测试: https://www.luogu.org/record/24012605
    // Time Complexity: O(VN) , Space Complexity: O(V)
    public int ZeroOnePack_Optimize(int N, int V, int[] C, int[] W) {
        int[] dp = new int[V + 1];
        dp[0] = 0;// init
        for (int i = 0; i < N; i++) {
            for (int j = V; j >= C[i]; j--) { // !!! 递减顺序  以及 C,W数组是0 based index.
                dp[j] = Math.max(dp[j], dp[j - C[i]] + W[i]);
            }
        }
        return dp[V];
    }

    // 抽象化: 抽取01背包问题为处理一件物品一种费用的过程
    public int ZeroOnePack(int ci, int wi, int[] dp, int V) {
        for (int j = V; j >= ci; j--) {
            dp[j] = Math.max(dp[j], dp[j - ci] + wi);
        }
        return dp[V];// 返回的是容量为V时候,处理一件物品获取的最大值
    }

    // 抽象化
    public int _2DZeroOnePack(int ci, int di, int wi, int[][] dp, int V, int U) {
        for (int i = V; i >= ci; i--) {
            for (int j = U; j >= di; j--) {
                dp[i][j] = Math.max(dp[i][j], dp[i - ci][j - di] + wi);
            }
        }
        return dp[V][U];
    }

    // 完全背包问题 https://www.luogu.org/problem/P1616
    // 问题描述: 此时问题的问法变化了, N种物品,容量V,每种物品的费用Ci,价值Wi,如何装入(第I种物品数量不限), 才能使得价值最大
    // 提交记录: https://www.luogu.org/record/24014209
    public int CompletePack_Original(int N, int V, int[] C, int[] W) {
        int[][] dp = new int[N + 1][V + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= V; j++) {
                if (j >= C[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - C[i - 1]] + W[i - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[N][V];
    }

    // 降维: 2维-->1维
    // 提交记录: https://www.luogu.org/record/24014348
    public int CompletePack_Optimize(int N, int V, int[] C, int[] W) {
        int[] dp = new int[V + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= V; j++) {
                if (j >= C[i - 1]) dp[j] = Math.max(dp[j], dp[j - C[i - 1]] + W[i - 1]);
            }
        }
        return dp[V];
    }

    // 抽象化: 对一种物品的处理过程 一维
    public int CompletePack(int ci, int wi, int[] dp, int V) {
        for (int j = 0; j <= V; j++) {
            if (j >= ci) dp[j] = Math.max(dp[j], dp[j - ci] + wi);
        }
        return dp[V];
    }

    // 二维化
    public int _2DCompletePack(int ci, int di, int wi, int[][] dp, int V, int U) {
        for (int i = 0; i <= V && i >= ci; i++) {
            for (int j = 0; j <= U && j >= di; j++) {
                dp[i][j] = Math.max(dp[i][j], dp[i - ci][j - di] + wi);
            }
        }
        return dp[V][U];
    }

    // 多重背包问题 多重背包问题建立在01背包和完全背包问题问题基础上
    // 提交记录: https://www.luogu.org/record/24015733
    public int MultiPack(int N, int V, int[] C, int[] W, int[] M) {
        int[] dp = new int[V + 1];
        for (int i = 0; i < N; i++) {
            MultiplePack(C[i], W[i], M[i], dp, V);
        }
        return dp[V];
    }

    // 抽象话 一维维度
    public int MultiplePack(int ci, int wi, int mi, int[] dp, int V) {
        if (ci * mi >= V) {
            // https://www.luogu.org/recordnew/show/24249357
            CompletePack(ci, wi, dp, V);
            return dp[V];
        }
        int k = 1;
        while (k < mi) {
            ZeroOnePack(ci * k, wi * k, dp, V);
            mi = mi - k;
            k = 2 * k;
        }
        ZeroOnePack(ci * mi, wi * mi, dp, V);
        return dp[V];
    }

    // 抽象化: 二维维度
    public int _2DMultiplePack(int ci, int di, int wi, int mi, int[][] dp, int V, int U) {
        if (ci * mi >= V || di * mi >= U) {
            _2DCompletePack(ci, di, wi, dp, V, U);
            return dp[V][U];
        }
        int k = 1;
        while (k < mi) {
            _2DZeroOnePack(ci, di, wi, dp, V, U);
            mi = mi - k;
            k = 2 * k;
        }
        _2DZeroOnePack(ci * mi, wi * mi, wi, dp, V, U);
        return dp[V][U];
    }

    // 混合背包问题
    public int MixPack(int N, int V, int[] C, int[] W, int[] M) {
        int[] dp = new int[V + 1];
        for (int i = 0; i < N; i++) {
            int val = M[i];
            if (val == 0) { // 完全背包问题
                CompletePack(C[i], W[i], dp, V);
            } else if (val == 1) { // 01 背包
                ZeroOnePack(C[i], W[i], dp, V);
            } else { // 混合背包
                MultiplePack(C[i], W[i], val, dp, V);
            }
        }
        return dp[V];
    }


    // 474 二维费用背包问题 http://bit.ly/32L0X3W http://bit.ly/2LyFQfx
    // V,U代表背包的2个维度的费用,Ci和Di分别代表第I件物品消耗 V和U的2种费用
    public int _2DPack(int N, int V, int U, int[] C, int[] D, int[] W, int[] M) {
        int[][] dp = new int[V + 1][U + 1];
        for (int i = 0; i < N; i++) {
            int val = M[i];
            if (val == 0) {
                _2DCompletePack(C[i], D[i], W[i], dp, V, U);
            } else if (val == 1) {
                _2DZeroOnePack(C[i], D[i], W[i], dp, V, U);
            } else {
                _2DMultiplePack(C[i], D[i], W[i], M[i], dp, V, U);
            }
        }
        return dp[V][U];
    }


    // 分组背包问题
    public int GroupPack(int N, int V, int[] C, int[] W, int[] G) {
        int K = Integer.MIN_VALUE;
        int[][] memo = new int[N + 1][N + 1];// memo和cnt 均+1的原因是因为 需要处理所有元素都属于同一组的情况
        int[] cnt = new int[N + 1];
        int[] dp = new int[V + 1];
        for (int i = 0; i < N; i++) {
            int gi = G[i];
            cnt[gi]++; // 1 based
            memo[gi][cnt[gi]] = i; // 1 based
            K = Math.max(K, gi);
        }
        for (int k = 1; k <= K; k++) {
            for (int v = V; v >= 0; v--) {
                for (int i = 1; i <= cnt[k]; i++) {
                    int px = memo[k][i];
                    if (v >= C[px]) {
                        dp[v] = Math.max(dp[v], dp[v - C[px]] + W[px]);
                    }
                }
            }
        }
        return dp[V];
    }

    // 错误的!!!
    // https://www.cnblogs.com/five20/p/7806278.html#commentform
    // https://www.luogu.org/problemnew/solution/P1064
    // 有依赖的背包问题
    // V表示背包容量,N表示物件总数,C表示物品费用,W表示物品价值,M代表物件的主从关系(0表示主件,非0表示附件枞树主件编号)
    // 思路 对于一个主件及其附件 采用分组背包问题思想解决
    public int TopologyPack() {
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt();
        int M = cin.nextInt();
        int[] v = new int[M];
        int[] p = new int[M];
        int[] t = new int[M];
        int[] dp = new int[N + 1];
        int[] g = new int[N + 1];

        for (int i = 1; i <= M; i++) {
            v[i] = cin.nextInt();
            p[i] = cin.nextInt() * v[i];
            t[i] = cin.nextInt();

        }
        for (int i = 1; i <= M; i++) {
            if (t[i] == 0) {// 主件
                for (int j = 1; j < v[i]; j++) g[j] = 0;
                for (int j = v[i]; j <= N; j++) g[j] = dp[j - v[i]] + p[i];
                for (int j = 1; j <= M && t[i] == i; j++)
                    for (int k = N; k >= v[i] + v[j]; k--)
                        g[k] = Math.max(g[k], g[k - v[j]] + p[j]);
                for (int j = v[i]; j <= N; j++) dp[j] = Math.max(dp[j], g[j]);

            }
        }
        return dp[N];
    }

    //② 416 01 背包问题
    public boolean canPartition01(int[] nums) {
        int total = 0;
        for (int i : nums) {
            total += i;
        }
        if ((total & 1) == 1) return false;
        int ret = total >> 1;
        int[] dp = new int[ret + 1];
        for (int i = 0; i < nums.length; i++) {
            for (int j = ret; j >= 0 && j >= nums[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
            }
        }
        return dp[ret] == ret;
    }

    // 解法2
    public boolean canPartition02(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum = sum + nums[i];
        }
        int target = sum / 2;
        if (sum % 2 == 1) return false;
        boolean[] dp = new boolean[target + 1];
        // init
        dp[0] = true;
        for (int num : nums) {
            // 为什么递减不是递增呢 因为递增的化 都为true了阿
            for (int i = target; i >= num; i--) {
                dp[i] = dp[i] || dp[i - num];
            }
        }
        return dp[target];
    }

    // ③ follow up 698. 划分为k个相等的子集
    // 答案来自于 http://bit.ly/32YpVNg  ,
    // 更好奇的是 为什么不能用01背包DP来做了,只能用backtrack来做了 据说是NP问题? 有待考证
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (k < 0 || sum % k != 0) return false;
        boolean[] visited = new boolean[nums.length];
        return dfsP(nums, visited, sum / k, k, 0, 0);
    }

    public boolean dfsP(int[] nums, boolean[] visited, int target, int k, int start, int sum) {
        if (k == 1) return true;
        if (sum == target) return dfsP(nums, visited, target, k - 1, 0, 0);
        for (int i = start; i < nums.length; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            if (dfsP(nums, visited, target, k, i + 1, sum + nums[i])) return true;
            visited[i] = false;
        }
        return false;
    }

    // ③ 474 01背包问题扩展 二维费用背包
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < strs.length; i++) {

            // 计算 0 和 1的费用
            char[] arr = strs[i].toCharArray();
            int zeros = 0;
            int ones = 0;
            for (char c : arr)
                if (c == '0') zeros++;
                else ones++;

            for (int j1 = m; j1 >= zeros; j1--) {
                for (int j2 = n; j2 >= ones; j2--) {
                    dp[j1][j2] = Math.max(dp[j1][j2], dp[j1 - zeros][j2 - ones] + 1);
                }
            }
        }
        return dp[m][n];
    }

    // ③ 494  01背包  https://youtu.be/zks6mN06xdQ?t=525
    // P-N=target,P代表正数，N代表负数
    //  P+N+P-N=target+sum
    //  2*P =(target+sum)
    //  target+sum 必须是even
    public int findTargetSum(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum < target || ((sum + target) & 1) == 1) return 0;
        int V = (target + sum) >> 1;
        int[] dp = new int[V + 1];
        dp[0] = 1;// init 初始化为1。 因为求个数
        for (int i = 0; i < nums.length; i++) {
            for (int j = V; j >= nums[i]; j--) {
                dp[j] = dp[j] + dp[j - nums[i]];
            }
        }
        return dp[V];
    }

    // ③ 322 完全背包问题 恰好背包?
    public int coinChange(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE); // 背包类问题的初始化 参见背包九讲
        dp[0] = 0;
        for (int i = 0; i < coins.length; i++) {
            for (int j = 0; j <= amount; j++) {
                if (j >= coins[i] && dp[j - coins[i]] != Integer.MAX_VALUE)
                    dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    //③ 518 零钱兑换 完全背包问题  不过问法变成了,求组合数(非排列总数)
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;// 容量为0的时候,什么也不装是一种方案,所以为1
        for (int i = 0; i < coins.length; i++) {
            for (int j = 0; j <= amount; j++) {
                if (j >= coins[i]) {
                    // 采用coins[i]方案
                    // 参考这里的解释 https://blog.csdn.net/wumuzi520/article/details/7021210
                    // ；当j >= C[i]时， F[i][j] = F[i][j-C[i]] + F[i-1][j],为什么是两者的和，
                    // 因为F[i][j-C[i]]和F[i-1][j]都是[i][j]状态时把背包装满的方案，且两者互斥。
                    dp[j] = dp[j] + dp[j - coins[i]];
                }
            }
        }
        return dp[amount];
    }

    // 1049 最后一块石头重量
    // 看成是两堆石头，用动态规划来求，
    // 当其中一堆重量最接近sum/2时碰撞后消耗的重量最多，
    // 剩下重量最少，即求sum/2的背包里最多能装多少重量的物品
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int i : stones) sum += i;
        int[] dp = new int[sum / 2 + 1];
        for (int i = 0; i < stones.length; i++) {
            for (int j = sum / 2; j >= stones[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - stones[i]] + stones[i]);
            }
        }
        return sum - 2 * dp[sum / 2];
    }

    // ② 377 组合总和Ⅳ  ,这个 应该叫做排列 而非组合
    //  也可以自己画个数, 可以观察出来 就是如下分解
    // dp[4] = dp[4-1]+dp[4-2]+dp[4-3] = dp[3]+dp[2]+dp[1]
    //
    //dp[1] = dp[0] = 1;
    //dp[2] = dp[1]+dp[0] = 2;
    //dp[3] = dp[2]+dp[1]+dp[0] = 4;
    //dp[4] = dp[4-1]+dp[4-2]+dp[4-3] = dp[3]+dp[2]+dp[1] = 7
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i >= nums[j]) dp[i] += dp[i - nums[j]];
            }
        }
        return dp[target];
    }

    // ②  这是求组合问题
    public int combinationSum4_(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j <= target; j++) {
                if (j - nums[i] >= 0) dp[j] = dp[j] + dp[j - nums[i]];
            }
        }
        return dp[target];

    }

    // 经典DP问题

    //  1143  最长公共子序列
    // LCS https://leetcode.com/problems/longest-common-subsequence/ https://oi-wiki.org/dp/#_7
    // dp[i][j] denote LCS between  s1[0:i] and s2[0:j], then
    // dp[i][j] = dp[i-1][j-1]+1 if s1[i] == s2[j], else dp[i][j] = Max(dp[i-1][j],dp[i][j-1])
    public int longestCommonSubsequence(String s1, String s2) {
        int N1 = s1.length();
        int N2 = s2.length();
        int[][] dp = new int[N1 + 1][N2 + 1];
        for (int i = 0; i < N1; i++) {
            for (int j = 0; j < N2; j++) {
                if (s1.charAt(i) == s2.charAt(j)) dp[i + 1][j + 1] = 1 + dp[i][j];
                else dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
            }
        }
        return dp[N1][N2];
    }

    // follow up 优化


    // ② 300 最长上升子序列 复杂度 O(N²)
    //  花花讲解 http://bit.ly/2Oga3BP
    //  http://bit.ly/2S18Z4A 看动画就能理解为什么了 哈哈
    //  [tag:面筋  很容易会被问到]

    // dp[i]=Max(dp[i],dp[j]+1),0<=j<i<N,以i结尾
    public int lengthOfLIS(int[] nums) {
        int N = nums.length;
        if (N == 0 || N == 1) return N;
        int[] dp = new int[N];
        // init
        Arrays.fill(dp, 1);
        int ans = Integer.MIN_VALUE;
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, i);
        }
        return ans;
    }

    /*
    //  divide and conquer  分治法求 复杂度 O(NlgN) 二分法解决
    //  参考如下  http://bit.ly/2kSr74M
    //  ❌ 面试不友好，想不出。
    public int lengthOfLIS01(int[] nums) {
        int N = nums.length;
        int[] top = new int[N];
        int piles = 0;
        for (int i = 0; i < N; i++) {
            int poker = nums[i];
            int l = 0, r = piles;
            while (l < r) {
                int mid = (r - l) / 2 + l;
                if (top[mid] < poker) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            }
            if (l == piles) piles++;
            top[l] = poker;
        }
        return piles;
    }*/

    // ③ 674  最长连续递增序列
    public int findLengthOfLCIS(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int len = 0;
        int cur = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num > cur) {
                len++;
            } else {
                ans = Math.max(ans, len);
                len = 1;
            }
            cur = num;
        }
        ans = Math.max(ans, len);
        return ans;
    }

    // 解法2 滑动窗口解决
    public int findLengthOfLCIS01(int[] nums) {
        int N = nums.length;
        int ans = 0, anchor = 0;
        for (int i = 0; i < N; i++) {
            if (i > 0 && nums[i - 1] >= nums[i]) anchor = i;
            ans = Math.max(ans, i - anchor + 1);
        }
        return ans;
    }

    // 5 最长回文子串  DP O(N²)
    public String longestPalindrome(String s) {
        if (s.length() < 2 || s == null) return s;
        int N = s.length();
        boolean[][] dp = new boolean[N][N];
        int start = 0;
        int maxLen = 1;
        for (int i = 0; i < N; i++) {
            dp[i][i] = true;
            for (int j = 0; j < i; j++) {
                // 处理偶数回文对称 & dp状态转移
                dp[j][i] = (s.charAt(i) == s.charAt(j)) && (dp[j + 1][i - 1] || (i - j) < 2);
                if (dp[j][i] && maxLen < i - j + 1) { // 限制条件别忘记 ，否则 有可能出错了的
                    maxLen = i - j + 1;
                    start = j;
                }
            }
        }
        return s.substring(start, start + maxLen);
    }

    // 落谷P2758 编辑距离 https://www.luogu.org/problemnew/solution/P2758
    //  72
    public int minDistance(String s1, String s2) {
        int N1 = s1.length();
        int N2 = s2.length();
        int[][] memo = new int[N1][N2];
        return dfs(s1, 0, s2, 0, memo);
    }

    public int dfs(String s1, int i, String s2, int j, int[][] memo) {
        if (j == s2.length()) return s1.length() - j;
        if (i == s1.length()) return s2.length() - i;
        if (memo[i][j] > 0) return memo[i][j];
        if (s1.charAt(i) == s2.charAt(j)) {
            return dfs(s1, i + 1, s2, j + 1, memo);
        } else {
            int insertCnt = dfs(s1, i, s2, j + 1, memo);
            int deleteCnt = dfs(s1, i + 1, s2, j, memo);
            int replaceCnt = dfs(s1, i + 1, s2, j + 1, memo);
            memo[i][j] = Math.min(insertCnt, Math.min(deleteCnt, replaceCnt)) + 1;
        }
        return memo[i][j];
    }

    // 区间DP系列

    // 87 扰乱字符串
    // dp[i][j][len] = True( (dp[i][j][k] && [i+k][j+k][len-k]) || dp[i][j+n-k] ][len )
    // dp(i,j,k)&&dp(i+k,j+K,len-k) ||  dp(i,j+len-k,k)&&dp(i+k,j,len-k)  len ∈[1,length], 0<K<len
    public boolean isScramble(String s1, String s2) {
        if (s1.length() != s2.length() || s1 == null || s2 == null) return false;
        if (s1.length() == 0) return true;
        int N = s1.length();
        boolean[][][] dp = new boolean[N][N][N + 1];
        // init len = 1
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dp[i][j][1] = s1.charAt(i) == s2.charAt(j);
            }
        }
        for (int len = 2; len <= N; len++) {
            for (int i = 0; i <= N - len; i++) {
                for (int j = 0; j <= N - len; j++) {
                    // 有效的切分
                    for (int k = 1; k < len; k++) {
                        dp[i][j][len] = dp[i][j][len]
                                || (dp[i][j][k] && dp[i + k][j + k][len - k])
                                || (dp[i][j + len - k][k] && dp[i + k][j][len - k]);
                    }
                }
            }
        }
        return dp[0][0][N];
    }

    // 516 最长回文子序列 不是子串哦! a+bbb+b   b+bbb+a
    // dp[i][j]=dp[i+1][j-1] +2 if(s[i]==s[j]]) else dp [i][j]= max(dp[i+1][j],dp[i][j-1]);
    // 区间DP
    public int longestPalindromeSubseq(String s) {
        int N = s.length();
        int[][] dp = new int[N][N];
        for (int len = 1; len <= N; len++) {
            for (int i = 0; i <= N - len; i++) {
                int j = len + i - 1;
                if (i == j) {
                    dp[i][j] = 1;
                    continue;
                }
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][N - 1];
    }

    // 813 最大平均值和的分组
    // dp(i,j,k) = max(dp(i,s,k-1)+dp(s+1,j,1),dp(i,j,k));
    // 0<=i<=s<j<N;
    public double largestSumOfAverages(int[] nums, int K) {
        int N = nums.length;
        // 前缀和
        int[] preSum = new int[N];
        preSum[0] = nums[0];
        for (int i = 1; i < N; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        // init
        double[][][] dp = new double[N][N][K + 1];
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                dp[i][j][1] = 1.0 * preSum(i, j, preSum) / (j - i + 1);
            }
        }
        for (int len = 2; len <= K; len++) {
            for (int i = 0; i < N; i++) {
                for (int j = i + len - 1; j < N; j++) {
                    for (int s = i; s < j; s++) {
                        dp[i][j][len] = Math.max(dp[i][j][len], dp[i][s][len - 1] + preSum(s + 1, j, preSum) / (j - s));
                    }
                }
            }
        }
        return dp[0][N - 1][K];
    }

    /**
     * @param x      inclusive
     * @param y      inclusive
     * @param preSum prefix sum for arr[0:N-1]
     * @return sum of arr[x:y]
     */
    public int preSum(int x, int y, int[] preSum) {
        return x == 0 ? preSum[y] : preSum[y] - preSum[x - 1];
    }

    // 区间 312. 戳气球 DP思想 迭代 http://bit.ly/2K4T01Z dp[i,j]
    public int maxCoins(int[] arr) {
        // ready data
        int N = arr.length;
        int[] nums = new int[N + 2];
        nums[0] = nums[N + 1] = 1;
        for (int i = 0; i < N; i++) {
            nums[i + 1] = arr[i];
        }
        int[][] dp = new int[N + 2][N + 2];

        for (int len = 1; len <= N; len++) {
            for (int i = 1; len <= N - i + 1; i++) {
                int j = i + len - 1;
                for (int k = i; k <= j; k++) {
                    // 求出dp[i,j]区间 第k个气球被打破时候的最大值Max(dp[i,j])
                    // k 的遍历区间 [i,i+len-1]
                    dp[i][j] = Math.max(dp[i][j], nums[i - 1] * nums[k] * nums[j + 1] + dp[i][k - 1] + dp[k + 1][j]);
                }
            }
        }
        return dp[1][N];
    }

    // 647. 回文子串 http://bit.ly/2LugfFU 区间DP
    public int countSubstrings(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        int len = s.length();
        int res = 0;
        for (int i = 0; i < len; i++) {
            // 还是要遍历阿 从字符串中找到一个回文子串
            // 考虑对称的奇偶性状况
            res += dfsCountSubHelper(s, i, i) + dfsCountSubHelper(s, i, i + 1);
        }
        return res;
    }

    // 状态转移方程 dp[i,j]=dp[i+1,j-1]+(dp[i]==dp[j]  逆向化--->init i+1 == j-1
    public int dfsCountSubHelper(String s, int i, int j) {
        int res = 0;
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i--;
            j++;
            res++;
        }
        return res;
    }

    //  877 石子游戏 区间DP
    // dp(i,j)表示区间(i,j)呢 先手的人能获得最大石子数
    // dp(i,j) = Max{ sum[i,j]-dp(i+1,j),sum[i,j]-dp(i,j-1) }
    public boolean stoneGame(int[] piles) {
        int N = piles.length;
        int[] preSum = new int[N];
        preSum[0] = piles[0];
        for (int i = 1; i < N; i++) {
            preSum[i] = preSum[i - 1] + piles[i];
        }
        int[][] dp = new int[N][N];
        // init
        for (int i = 0; i < N; i++) {
            dp[i][i] = piles[i];
        }
        // 区间Dp 最重要的一点是遍历方式的不同 因为这是DP性质决定啊(小问题决策影响大问题决策)
        for (int len = 1; len < N; len++) {
            for (int i = 0; i < N - len; i++) {
                int j = i + len;
                dp[i][j] = Math.max(preSum(i, j, preSum) - dp[i + 1][j], preSum(i, j, preSum) - dp[i][j - 1]);
            }

        }
        return dp[0][N - 1] > (preSum[N - 1] / 2);

    }

    // LC  1000  合并石头的最低成本
    // 0<=i<=t<j<N  1<=k<=K
    // 有2次状态转移
    // dp(i,j,k) = min(dp(i,j,k),dp(i,t,k-1)+dp(t+1,j,1))
    //dp(i,j,1) = min(dp(i,j,1)
    public int mergeStone(int[] stones, int K) {
        int N = stones.length;
        if ((N - 1) % (K - 1) != 0) return -1;// 是否有解的前提条件
        //前缀和
        int[] preSum = new int[N];
        preSum[0] = stones[0];
        for (int i = 1; i < N; i++) {
            preSum[i] = preSum[i - 1] + stones[i];
        }
        int[][][] dp = new int[N][N][K + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
        }
        // init
        for (int i = 0; i < N; i++) {
            dp[i][i][1] = 0;// 注意是0哦
        }
        for (int len = 2; len <= N; len++) { // sub problem length
            for (int i = 0; i <= N - len; i++) {
                int j = i + len - 1;
                for (int k = 2; k <= K; k++) {
                    for (int m = i; m < j; m += K - 1) {
                        dp[i][j][k] = Math.min(dp[i][j][k], dp[i][m][1] + dp[m + 1][j][k - 1]);
                        dp[i][j][1] = preSum(i, j, preSum) + dp[i][j][k];
                    }
                }
            }
        }
        return dp[0][N - 1][1];
    }

    // 区间DP https://oi-wiki.org/dp/interval/
    // 石子合并 https://www.luogu.org/problem/P1880
    public int[] mergeStone(int N, int[] stones) {
        int[] sum = new int[2 * N];
        int[] s = new int[2 * N];
        for (int i = 0; i < N; i++) s[i + N] = s[i] = stones[i];
        sum[0] = stones[0];
        int[][] minDp = new int[2 * N][2 * N];
        int[][] maxDp = new int[2 * N][2 * N];
        for (int i = 1; i < 2 * N; i++) sum[i] = sum[i - 1] + s[i];
        for (int len = 1; len < N; len++) {// len 为何不取0 呢呢 todo
            for (int i = 1, j = i + len; i < 2 * N && j < 2 * N; i++, j = i + len) {
                minDp[i][j] = Integer.MAX_VALUE;
                maxDp[i][j] = Integer.MIN_VALUE;
                for (int k = i; k < j; k++) { // 假设len =0; 则不进行下列循环
                    minDp[i][j] = Math.min(minDp[i][j], minDp[i][k] + minDp[k + 1][j] + sum[j] - sum[i - 1]);
                    maxDp[i][j] = Math.max(maxDp[i][j], maxDp[i][k] + maxDp[k + 1][j] + sum[j] - sum[i - 1]);
                }
            }
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < N; i++) {
            min = Math.min(min, minDp[i][i + N - 1]);
            max = Math.max(max, maxDp[i][i + N - 1]);
        }
        System.out.printf("%d\n%d", min, max);
        return new int[]{min, max};
    }

    // 546 移除盒子 区间dP  http://bit.ly/2OgJson
    public int removeBoxes(int[] boxes) {
        int N = boxes.length;
        int[][][] dp = new int[N][N][N];
        return intervalDpDFS(dp, boxes, 0, N - 1, 0);
    }

    public int intervalDpDFS(int[][][] dp, int[] boxes, int l, int r, int k) {
        if (l > r) return 0;
        if (dp[l][r][k] > 0) return dp[l][r][k];
        dp[l][r][k] = intervalDpDFS(dp, boxes, l, r - 1, 0) + (k + 1) * (k + 1); // case 1
        for (int p = l; p < r; p++) {
            if (boxes[p] == boxes[r]) {
                // case 2
                dp[l][r][k] = Math.max(dp[l][r][k],
                        // p 取代 r  成为分割点,则分解成2个子问题, 其中 k --->  k+1,
                        intervalDpDFS(dp, boxes, l, p, k + 1) + intervalDpDFS(dp, boxes, p + 1, r - 1, 0));
            }
        }
        return dp[l][r][k];
    }

    // 树形DP https://oi-wiki.org/dp/tree/
    // 没有上司的舞会 https://www.luogu.org/problem/P1352
    // https://www.cnblogs.com/hanruyun/p/9788170.html
    // 树形DP 是一种思想,解决有依赖型背包问题的好方法
    // todo
    public int DanceParty() {
        /*
        List<List<Integer>> tree = new ArrayList<List<Integer>>();
        int[] flag = new int[L.length];
        for (int i = 0; i < L.length + 1; i++) {
            int l = L[i];
            // int ri = R[i];
            tree.get(l).add(i);
            flag[l] = 1;
        }*/
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt();
        int[] R = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            R[i] = cin.nextInt();
        }
        int[] flag = new int[N];
        List<List<Integer>> tree = new ArrayList<List<Integer>>();
        for (int i = 1; i <= N - 1; i++) {
            int k = cin.nextInt();
            int l = cin.nextInt();
            tree.get(l).add(k);
            flag[k] = 1;
        }
        int root = -1;
        for (int i = 0; i < N; i++) {
            if (flag[i] != 1) {
                root = i;
                break;
            }
        }
        int[][] dp = new int[N][N];
        return Math.max(dp[root][0], dp[root][1]);
    }

    public void dp(int root, int[][] dp, List<List<Integer>> tree, int[] R) {
        dp[root][0] = 0;
        dp[root][1] = R[root];
        for (int i = 0; i < tree.get(root).size(); i++) {
            int son = tree.get(root).get(i);
            dp(son, dp, tree, R);
            dp[root][0] += Math.max(dp[son][0], dp[son][1]);
            dp[root][1] += dp[son][0];
        }
    }


    // DAG DP
    // uva 437 巴比伦塔  the tower of babylon  暂时C++展示

    // 矩形嵌套问题类似

    // LC 1048


    // LC 691


    // 状压DP 638 464  691  1125
 /*   // 完全背包问题
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {

    }

    // LC 464  https://www.acwing.com/solution/LeetCode/content/6911/
    // https://blog.csdn.net/zhaohaibo_/article/details/85345229
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {

    }

   // 691  http://www.voidcn.com/article/p-vldwpvis-boa.html
    public int minStickers(String[] stickers, String target) {

    }

    // 背包dp 1125
  //  https://leetcode-cn.com/problems/smallest-sufficient-team/solution/bei-bao-wen-ti-by-mike-meng/
    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {

    }*/

    // 943 473 1125
    // 状压DP 1349
    public int maxStudents(char[][] seats) {
        return -1;
    }

    // LG P1896 [SCOI2005]互不侵犯

    // DP: dp[i,j,k] = ∑dp[i-1,x,k-king(x)]
    // state数组记录可用状态的十进制，king记录该状态的国王数。
    // 我们用dp(i,j,k) 表示前 i 行，当前状态为 j ，且已经放置 k 个国王时的方案数。

    public long nonAggression(int N, int K) {
        // init
        int cnt = 0;
        // based-1 index
        Map<Integer, Long> state = new HashMap<>();
        Map<Integer, Integer> king = new HashMap<>();
        for (long i = 0; i < (1 << N); i++) {
            if ((i & (i << 1 | i >> 1)) != 0) continue;//说明存在左右相邻元素有1，不满足方案，剔除。
            int kings = 0;
            for (long j = 0; j < N; j++) {
                if ((i & (1 << j)) != 0) kings++;// 计算国王个数 就是i位1的个数
            }
            cnt++;
            state.put(cnt, i);
            king.put(cnt, kings);
        }

        // dp
        long[][][] dp = new long[N + 1][cnt + 1][K + 1];
        // // 注意 后面的i,j,开头的时候，i可以取到0，j只能从1开始去，k可以取到0
        // 也可以先枚举i=1，此时任何状态的方案个数均为1、这个符合人的一般思维
        dp[0][1][0] = 1;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= cnt; j++) {
                for (int k = 0; k <= K; k++) {
                    if (k >= king.get(j)) {
                        long s1 = state.get(j);
                        for (int t = 1; t <= cnt; t++) {// 枚举上一行状态
                            long s2 = state.get(t);
                            if (((s2 | s2 >> 1 | s2 << 1) & s1) != 0) continue;
                            dp[i][j][k] += dp[i - 1][t][k - king.get(j)];
                        }
                    }
                }
            }
        }
        long ans = 0;
        for (int i = 1; i <= cnt; i++) { // 枚举 第N行，i状态下，已经放置k个国王数的方案总数
            ans += dp[N][i][K];
        }
        return ans;
    }

    // LG P2704 [NOI2001]炮兵阵地
    public int artilleryPermutation(int n, int m) {
        int[] map = new int[100];
        int[] s = new int[1000];
        int[] g = new int[1000];
        int[][][] f = new int[1000][1000][1000];
        Scanner cin = new Scanner(System.in);
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                char c = (char) cin.nextByte();
                if (c == 'H') map[i] = map[i] + 1 << j;
            }
        }
        int k = 0;
        for (int i = 0; i < (1 << m); i++) {
            if (((i & (i << 1)) == 0) || ((i & (i << 2)) == 0) || ((i & (i >> 1)) == 0) || ((i & (i >> 2)) == 0)) {
                k++;
                s[k] = i;
                g[k] = getSum(i);
                if ((i & map[1]) == 0) f[1][0][k] = g[k];//初始化第一行
            }
        }
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= k; j++) {
                if (((s[i] & s[j]) == 0) && ((s[j] & map[2]) == 0)) {
                    f[2][i][j] = Math.max(f[2][i][j], f[1][0][i] + g[j]);//初始化第二行
                }
            }
        }

        //dp
        for (int i = 3; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                if ((map[i] & s[j]) == 0) {
                    for (int p = 1; p <= k; p++) {
                        if ((s[p] & s[j]) == 0) {
                            for (int q = 1; q <= k; q++) {
                                if ((s[q] & s[p]) == 0 && (s[q] & s[j]) == 0) {// j p q
                                    f[i][p][j] = Math.max(f[i][p][j], f[i - 1][q][p] + g[j]);
                                }
                            }
                        }
                    }
                }
            }
        }
        int ans = 0;
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= k; j++) {
                ans = Math.max(ans, f[n][i][j]);
            }
        }
        return ans;
    }

    public int getSum(int x) {
        int cnt = 0;
        while (x != 0) {
            if ((x & 1) != 0) cnt++;
            x = x >> 1;
        }
        return cnt;
    }

    //P1879 [USACO06NOV]玉米田Corn Fields
    // dp(i,j)=∑dp(i-1,k),k和j状态不互斥
    public long cornField() {
        Scanner cin = new Scanner(System.in);
        int m = cin.nextInt(), n = cin.nextInt();
        int mod = 100000000;
        int[] F = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                F[i] = (F[i] << 1) + cin.nextInt();
            }
        }
        long[][] f = new long[m + 1][1 << n + 1];
        f[0][0] = 1;
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j < (1 << n); j++) {
                if ((j & (j << 1)) == 0 && (j & (j >> 1)) == 0 && (j & F[i]) == j) {
                    for (int k = 0; k < (1 << n); k++) {
                        // 最内层循环要求前一层 k和j不互斥即可，也就是说 可以允许k自身可以有公共边或者完全荒废，这是和互不侵犯以及炮兵阵地不同的地方
                        if ((k & j) == 0) {
                            f[i][j] = (f[i][j] + f[i - 1][k]) % mod;
                        }
                    }
                }
            }
        }

        long ans = 0;
        for (int i = 0; i < (1 << n); i++) {
            ans = (ans + f[m][i]) % mod;
        }
        System.out.println(ans);
        return ans;
    }


    //数位dp
    // [SCOI2009]windy数 https://www.luogu.com.cn/problem/P2657
    // https://www.luogu.com.cn/problemnew/solution/P2657
    public static int[][] dp = new int[15][15];
    public static int[] a = new int[15];

    /*
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int lo = cin.nextInt(), hi = cin.nextInt();
        init();
        int ans = work(hi + 1) - work(lo);
        System.out.println(ans);
    }
    */

    public static void init() {
        for (int i = 0; i <= 9; i++) dp[1][i] = 1;
        for (int i = 2; i <= 10; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    if (Math.abs(j - k) >= 2) {
                        dp[i][j] += dp[i - 1][k];
                    }
                }
            }
        }
    }

    public static int work(int x) {
        int len = 0, ans = 0;
        while (x != 0) {
            len++;
            a[len] = x % 10;
            x = x / 10;
        }
        for (int i = 1; i <= len - 1; i++) {
            for (int j = 1; j <= 9; j++) {
                ans += dp[i][j];
            }
        }
        for (int i = 1; i < a[len]; i++) {
            ans += dp[len][i];
        }
        for (int i = len - 1; i >= 1; i--) {
            for (int j = 0; j <= a[i] - 1; j++) {
                if (Math.abs(j - a[i + 1]) >= 2) ans += dp[i][j];
            }
            if (Math.abs(a[i + 1] - a[i]) < 2) break;
        }
        return ans;
    }

    // 233
    public int countDigitOne(int n) {
        return -1;
    }

    //LC 1012 233

    // 233 https://www.acwing.com/solution/LeetCode/content/280/
    // 1012 https://www.acwing.com/solution/LeetCode/content/1184/


    // HDU 6148 Valley Number

// CF55D Beautiful numbers

// CF628D Magic Numbers

// CF401D Roman and Numbers

//概率dp lc 1230

//树形dp 337 124 https://www.cnblogs.com/hanruyun/p/9788170.html

//字符串

//trie kmp AC自动机 LC 30 1032

// KMP算法与AC自动机. KMP算法(28 459)——用于单模匹配。 AC自动机——用于多模匹配，需要了解KMP原理和Trie树。


    // graph
// 拓扑排序（207，210，269，329，444，1203，802）
    // 最短路径 743 flyod 和dikstra算法 787 Bellman-Ford算法
    // 最短路径743，505，787，847，1091，

    // 最小生成树 kruskal prim Boruvka 算法 (1130,  https://www.acwing.com/file_system/file/content/whole/index/content/160894/ 1135 https://www.okcode.net/article/43507)  tarjan联通
// 卡特兰数96 等差等比数列 413 素数筛法 204 欧拉定理 507 1015 费马小定理 372
    // 剩余定理/贝祖定理 1250


}