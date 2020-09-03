package com.yanjinbin.leetcode;

import lombok.AllArgsConstructor;

import java.util.*;

/**
 * // 博弈系列
 * // 292  NIM游戏
 * // 1205 除数博弈
 * // 375 猜数字大小Ⅱ
 * // 464 我能赢么
 * // 486 预测赢家
 * // 843 猜猜单词
 * // 913
 * // 石头系列
 * // 877
 * // 1140
 * // 1406
 * // 1563
 */
public class GameTheory {

    // 1025 除数博弈
    boolean divisorGame(int N) {
        boolean[] dp = new boolean[N + 1];// dp[1]=false
        for (int i = 2; i <= N; i++) {
            for (int j = 1; j < i; j++) {
                if (i % j != 0) continue;
                if (dp[i - j] == false) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[N];
    }

    // 292 NIM游戏 经典博弈入门dp
    public boolean canWinNim01(int n) {// 数学归纳法
        return (n % 4 != 0);
    }

    //  dp[n] = !(dp[n-1]  && dp[n-2] && dp[n-3])
    // // 数据规模过大，TLE
    public boolean canWinNim02(int n) {
        boolean[] dp = new boolean[n + 1];
        for (int i = 1; i < n; i++) {
            boolean ans = true;
            ans &= dp[i - 1];
            if (i >= 2) {
                ans &= dp[i - 2];
            }
            if (i >= 3) {
                ans &= dp[i - 3];
            }
            dp[i] = !ans;
        }
        return dp[n];
    }

    // 375 猜数字大小Ⅱ，就是一道区间dp题目 tag:区间DP
    // dp[i,j]=k+Max(dp[i,k-1],dp[k+1,j])
    //  0<= i<= k <= j <= n
    public int getMoneyAmount(int n) {
        int[][] dp = new int[n + 1][n + 1];
        for (int len = 2; len <= n; len++) {
            for (int i = 1; n - i + 1 >= len; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    dp[i][j] = Math.min(dp[i][j],
                            k + Math.max(
                                    k - 1 <= 0 ? 0 : dp[i][k - 1], k == j ? 0 : dp[k + 1][j]
                            )
                    );
                }
            }
        }
        return dp[1][n];
    }

    // 464 我能赢么 博弈论
    private byte[] m_;// 0: unknown, 1: won, -1: lost

    public boolean canIWin(int M, int T) {
        if (M * (M + 1) / 2 < T) return false;
        if (T <= 0) return true;
        m_ = new byte[1 << M];
        return canIWin(M, T, 0);
    }

    public boolean canIWin(int M, int T, int state) {
        if (T <= 0) return false;
        if (m_[state] != 0) return m_[state] == 1; // memorize
        for (int i = 0; i < M; i++) {
            if ((state & (1 << i)) > 0) continue;// skip used
            if (!canIWin(M, T - (i + 1), state | (1 << i))) {
                m_[state] = 1;
                return true;
            }
        }
        m_[state] = -1;
        return false;
    }

    // 486 预测赢家
    public boolean PredictTheWinner01(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len * len];
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                if (i == j) dp[i * len + j] = nums[i];
                else if (j - 1 >= 0 && (i + 1 < len))
                    dp[i * len + j] = Math.max(nums[i] - dp[(i + 1) * len + j], nums[j] - dp[i * len + j - 1]);
            }
        }
        return dp[len - 1] >= 0;
    }

    // https://bit.ly/31QIVQb
    public boolean PredictTheWinner02(int[] nums) {
        int len = nums.length;
        int[][] memo = new int[len][len];
        for (int i = 0; i < len; i++) {
            Arrays.fill(memo[i], Integer.MIN_VALUE);
        }
        return dfs(nums, 0, len - 1, memo) >= 0;
    }

    private int dfs(int[] nums, int i, int j, int[][] memo) {
        if (i > j) {
            return 0;
        }
        if (memo[i][j] != Integer.MIN_VALUE) {
            return memo[i][j];
        }
        int chooseLeft = nums[i] - dfs(nums, i + 1, j, memo);
        int chooseRight = nums[j] - dfs(nums, i, j - 1, memo);
        memo[i][j] = Math.max(chooseLeft, chooseRight);
        return memo[i][j];
    }

    interface Master {
        int guess(String word);
    }

    // 843 猜猜这个单词 todo

    int[][] H;

    //  https://youtu.be/2j_9YWgTxHk
    public void findSecretWord(String[] wordlist, Master master) {
        int N = wordlist.length;
        H = new int[N][N];
        for (int i = 0; i < N; ++i)
            for (int j = i; j < N; ++j) {
                int match = 0;
                for (int k = 0; k < 6; ++k)
                    if (wordlist[i].charAt(k) == wordlist[j].charAt(k))
                        match++;
                H[i][j] = H[j][i] = match;
            }

        List<Integer> possible = new ArrayList();
        List<Integer> path = new ArrayList();
        for (int i = 0; i < N; ++i) possible.add(i);
        while (!possible.isEmpty()) {
            int guess = solve(possible, path);
            int matches = master.guess(wordlist[guess]);
            if (matches == wordlist[0].length()) return;
            List<Integer> possible2 = new ArrayList();
            for (Integer j : possible) if (H[guess][j] == matches) possible2.add(j);
            possible = possible2;
            path.add(guess);
        }
    }

    public int solve(List<Integer> possible, List<Integer> path) {
        if (possible.size() <= 2) return possible.get(0);
        List<Integer> ansgrp = possible;
        int ansguess = -1;

        for (int guess = 0; guess < H.length; ++guess) {
            if (!path.contains(guess)) {
                ArrayList<Integer>[] groups = new ArrayList[7];
                for (int i = 0; i < 7; ++i) groups[i] = new ArrayList<Integer>();
                for (Integer j : possible)
                    if (j != guess) {
                        groups[H[guess][j]].add(j);
                    }
                ArrayList<Integer> maxgroup = groups[0];
                for (int i = 0; i < 7; ++i)
                    if (groups[i].size() > maxgroup.size())// max
                        maxgroup = groups[i];

                if (maxgroup.size() < ansgrp.size()) {// min
                    ansgrp = maxgroup;
                    ansguess = guess;
                }
            }
        }
        return ansguess;
    }

    // 913 猫和老鼠
    @AllArgsConstructor
    class Node {
        int i, j, turn;// x-> 🐭 ，y->🐱，turn 0表示老鼠先走，1表示猫先走
    }

    Queue<Node> q;
    int[][] g;
    int n;
    int[][][] ans = new int[205][205][2];
    int[][][] indeg = new int[205][205][2];

    public int catMouseGame(int[][] graph) {
        g = graph;
        n = graph.length;
        q = new LinkedList<>();
        while (!q.isEmpty()) q.poll();
        bfs();
        bbfs();
        return ans[1][2][0];
    }

    public void bfs() {
        for (int i = 0; i < n; i++) {
            if (i != 0) {
                q.add(new Node(i, i, 0));
                q.add(new Node(i, i, 1));
                q.add(new Node(0, i, 0));
                q.add(new Node(0, i, 1));
                ans[i][i][0] = ans[i][i][1] = 2; // 猫必胜局面
                ans[0][i][0] = ans[0][i][1] = 1; // 老鼠必胜局面
                indeg[i][i][0] = indeg[i][i][1] = 1; // 只是为了防止再次进队
            }
        }
        while (!q.isEmpty()) {
            Node front = q.poll();

        }

    }

    public void bbfs() {

    }

    // 877 石头游戏  tag: min-max dp
    int[][] mem;

    public boolean stoneGame01(int[] piles) {
        int len = piles.length;
        mem = new int[len][len];
        for (int i = 0; i < len; i++) {
            Arrays.fill(mem[i], Integer.MIN_VALUE);
        }
        return score(piles, 0, len - 1) > 0;
    }

    public int score(int[] piles, int l, int r) {
        if (l == r) return piles[l];
        if (mem[l][r] == Integer.MIN_VALUE) {
            mem[l][r] = Math.max(piles[l] - score(piles, l + 1, r), piles[r] - score(piles, l, r - 1));
        }
        return mem[l][r];
    }

    // 第二种解法  min-max+dp 为什么不是区间dp呢？
    public boolean stoneGame02(int[] piles) {
        int n = piles.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i];
        }
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                dp[i][j] = Math.max(piles[i] - dp[i + 1][j], piles[j] - dp[i][j - 1]);
            }
        }
        return dp[0][n - 1] > 0;
    }

    // 1140. 石子游戏 II ① https://youtu.be/e_FrC5xavwI
    int[][] cache;
    int[] sum;

    public int stoneGameII01(int[] piles) {
        int tot = 0;
        int n = piles.length;
        sum = new int[n];
        cache = new int[n][2 * n];
        for (int i = 0; i < n; i++) {
            tot += piles[i];
            sum[i] = tot;
        }
        return (tot + solve(0, 1, piles)) / 2;
    }

    public int preSum(int[] preSum, int x, int y) {
        return x == 0 ? preSum[y] : preSum[y] - preSum[x - 1];
    }

    public int solve(int s, int m, int[] piles) {
        if (s >= piles.length) return 0;
        if (cache[s][m] > 0) return cache[s][m];// 已经计算过 state(s,m) 状态了
        if (s + 2 * m >= piles.length) return cache[s][m] = preSum(sum, s, piles.length - 1);// 全部拿走最有利
        int best = Integer.MIN_VALUE;// 表示不存在
        int cur = 0;
        for (int x = 1; x <= 2 * m; x++) {
            cur += piles[s + x - 1];
            best = Math.max(best, cur - solve(s + x, Math.max(x, m), piles));// max 最大的
        }
        return cache[s][m] = best;
    }

    // ② 第二种解法
    // 在每个玩家的回合中，该玩家可以拿走剩下的 前 X 堆的所有石子，其中 1 <= X <= 2M。然后，令 M = max(M, X),所以 M∈[1,len]
    // 0 << s < len , 0=<m<=len;
    //  s+2m >= len, dp[s][m]=sum[s:len-1];
    //  s+2m < len, dp[s][m]=Math.max(dp[s][M],sum[s:len-1]-dp[s+x][Max(m,x)],1<=x<=2m)  ✅
    // 由于本题中的状态是从后往前递推的，如：假如最后只剩一堆，一定能算出来最佳方案，但是剩很多堆时不好算（依赖后面的状态）。所以我们选择从后往前递推。
    public int stoneGameII02(int[] piles) {
        int len = piles.length;
        int sum = 0;
        int[][] dp = new int[len][len + 1];
        for (int i = len - 1; i >= 0; i--) {
            sum += piles[i];
            for (int m = 1; m <= len; m++) {
                if (i + 2 * m >= len) {
                    dp[i][m] = sum;
                } else {
                    for (int x = 1; x <= 2 * m; x++) {
                        dp[i][m] = Math.max(dp[i][m], sum - dp[i + x][Math.max(m, x)]);
                    }
                }
            }
        }
        return dp[0][1];
    }

    // 1406. 石子游戏 III
    public String stoneGameIII01(int[] stoneValue) {
        int len = stoneValue.length;
        int[] mem = new int[len];
        Arrays.fill(mem, Integer.MIN_VALUE);
        int score = score(0, stoneValue, mem);
        System.out.println(Arrays.toString(mem));
        return score > 0 ? "Alice" : (score == 0 ? "Tie" : "Bob");
    }

    public int score(int i, int[] stoneValue, int[] mem) {
        if (i >= stoneValue.length) return 0;
        if (mem[i] != Integer.MIN_VALUE) return mem[i];
        for (int j = 0, s = 0; j < 3 && i + j < stoneValue.length; ++j) {
            s += stoneValue[i + j];
            mem[i] = Math.max(mem[i], s - score(i + j + 1, stoneValue, mem));
        }
        return mem[i];
    }

    // 从前面的递归也可以推导出dp状态转移方程式 https://bit.ly/3lOsDiI
    public String stoneGameIII02(int[] stoneValue) {
        int len = stoneValue.length;
        int[] dp = new int[len + 1];
        Arrays.fill(dp, Integer.MIN_VALUE);
        for (int i = len - 1; i >= 0; i--) {
            int take = 0;
            for (int j = i; j < Math.min(i + 3, n); j++) {
                take += stoneValue[j];
                dp[i] = Math.max(dp[i], take - (j + 1) < len ? dp[j + 1] : 0);
            }
        }
        int diff = dp[0];
        return diff > 0 ? "Alice" : (diff == 0 ? "Tie" : "Bob");
        /*
        // 这个方法也可以
        int len = stoneValue.length;
        int[] dp = new int[len];
        Arrays.fill(dp, Integer.MIN_VALUE);
        for (int i = len - 1; i >= 0; i--) {
            int take = 0;
            for (int j = i; j < Math.min(i + 3, len); j++) {
                take += stoneValue[j];
                dp[i] = Math.max(dp[i], take - ( (j + 1) < len ? dp[j + 1] : 0));
            }
        }
        int diff = dp[0];
        return diff > 0 ? "Alice" : (diff == 0 ? "Tie" : "Bob");
         */
    }

    // 1563. 石子游戏 V https://bit.ly/2DpykSU 区间DP
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        int[] preSum = new int[n];
        int[][] dp = new int[n][n];
        preSum[0] = stoneValue[0];
        for (int i = 1; i < n; i++) {
            preSum[i] = preSum[i - 1] + stoneValue[i];
        }
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                for (int k = i; k < j; k++) {
                    int l = dp[i][k];
                    int r = dp[k + 1][j];
                    int ls = preSum[k] - (i>=1 ? preSum[i-1]:0);
                    int rs = preSum[j] - preSum[k];
                    if (ls == rs) {
                        int score = Math.max(l, r) + rs;
                        dp[i][j] = Math.max(score, dp[i][j]);
                    }
                    if (ls > rs) {
                        dp[i][j] = Math.max(dp[i][j], r + rs);
                    }
                    if (ls < rs) {
                        dp[i][j] = Math.max(dp[i][j], l + ls);
                    }
                }
            }
        }
        return dp[0][n-1];
    }
}
