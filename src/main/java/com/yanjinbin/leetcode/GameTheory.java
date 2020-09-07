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
        boolean[] dp = new boolean[4];
        for (int i = 1; i < n; i++) {
            boolean ans = true;
            ans &= dp[(i - 1) % 4];
            if (i >= 2) {
                ans &= dp[(i - 2) % 4];
            }
            if (i >= 3) {
                ans &= dp[(i - 3) % 4];
            }
            dp[i % 4] = !ans;
        }
        return dp[n % 4];
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

    // 913 猫和老鼠 tag: dp
    // 1 🐭  2 🐱 3 平局
    // https://bit.ly/3gZU3Pa
    int[][][] f;
    public int catMouseGame02(int[][] graph) {
        int n = graph.length;
        f = new int[n * 2][n][n];
        for (int[][] i : f) {
            for (int[] j : i) {
                Arrays.fill(j, -1);
            }
        }
        return solve(graph, 0, 1, 2);// 起始状态
    }

    int solve(int[][] graph, int t, int x, int y) {
        if (t == graph.length * 2) return 0;
        if (x == y) return f[t][x][x] = 2;
        if (x == 0) return f[t][x][y] = 1;
        if (f[t][x][y] != -1) return f[t][x][y];

        int who = t % 2;
        boolean flag;
        if (who == 0) { // 老鼠先走 偶数
            flag = true;
            for (int i = 0; i < graph[x].length; i++) {
                int nxt = solve(graph, t + 1, graph[x][i], y);
                if (nxt == 1) {// 有1选1
                    return f[t][x][y] = 1;
                } else if (nxt != 2) {// 有 0 选 0
                    flag = false;
                }
            }
            if (flag) {
                return f[t][x][y] = 2;
            } else {
                return f[t][x][y] = 0;
            }
        } else {
            flag = true;
            for (int i = 0; i < graph[y].length; i++) {
                if (graph[y][i] != 0) {// 不进洞
                    int next = solve(graph, t + 1, x, graph[y][i]);
                    if (next == 2) {// 有2选2
                        return f[t][x][y] = 2;
                    } else if (next != 1) {// 有0选0
                        flag = false;
                    }
                }
            }
            if (flag) {
                return f[t][x][y] = 1;
            } else {
                return f[t][x][y] = 0;
            }
        }
    }

    // https://bit.ly/35eiR3N
    // time complexity O(n * n * 2)
    public int catMouseGame01(int[][] adj) {
        int n = adj.length;
        // 1. initial
        // status[i][j][k] 表示老鼠在i位置， 猫在j位置，k表示下一步由谁移动(0表示鼠移动， 1表示猫移动)
        // 结果为0，1，2(1表示鼠胜，2表示猫胜，0表示平局)
        int[][][] status = new int[n][n][2];
        Queue<int[]> queue = new ArrayDeque<>();
        // status[i][i][k] 表示鼠猫同位置，猫胜；
        // status[0][i][k] 表示鼠进洞，鼠胜；
        for (int i = 1; i < n; i++) {
            status[i][i][0] = 2;
            status[i][i][1] = 2;
            status[0][i][0] = 1;
            status[0][i][1] = 1;
            queue.add(new int[]{i, i, 0});
            queue.add(new int[]{i, i, 1});
            queue.add(new int[]{0, i, 0});
            queue.add(new int[]{0, i, 1});
        }

        // 2. BFS 搜索
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int i = cur[0], j = cur[1], k = cur[2];
            if (k == 0) { // 鼠行动，说明上一次是猫行动
                // 在倒推上一步的猫状态时需要确保满足题意，猫不会在0位置处
                if (status[i][j][0] == 2) { // 猫胜利，那么根据最优玩法，上次的猫行动可以直接选择胜利的玩法
                    for (int pre : adj[j]) {
                        if (pre != 0 && status[i][pre][1] == 0) {
                            status[i][pre][1] = 2;
                            queue.add(new int[]{i, pre, 1});
                        }
                    }
                } else { // 鼠胜利，那么只有当上次猫的所有行动下都是鼠胜利，猫才稳输
                    for (int pre : adj[j]) {
                        if (pre != 0 && status[i][pre][1] == 0) {
                            boolean canMouseWin = true;
                            for (int next : adj[pre]) {
                                if (next != 0 && status[i][next][0] != 1) {
                                    canMouseWin = false;
                                    break;
                                }
                            }
                            if (canMouseWin) {
                                status[i][pre][1] = 1;
                                queue.add(new int[]{i, pre, 1});
                            }
                        }
                    }
                }
            } else { // 猫行动，说明上次是鼠行动
                if (status[i][j][1] == 1) { // 鼠胜利，那么上次的鼠直接通过这次的选择即可确保胜利
                    for (int pre : adj[i]) {
                        if (status[pre][j][0] == 0) {
                            status[pre][j][0] = 1;
                            queue.add(new int[]{pre, j, 0});
                        }
                    }
                } else { //猫胜利，那么当且仅当上次的鼠的所有行动都为猫胜利，鼠才稳输
                    for (int pre : adj[i]) {
                        if (status[pre][j][0] == 0) {
                            boolean canCatWin = true;
                            for (int next : adj[pre]) {
                                if (status[next][j][1] != 2) {
                                    canCatWin = false;
                                    break;
                                }
                            }
                            if (canCatWin) {
                                status[pre][j][0] = 2;
                                queue.add(new int[]{pre, j, 0});
                            }
                        }
                    }
                }
            }
        }
        return status[1][2][0];
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
    public String stoneGameIII(int[] stoneValue) {
        int len = stoneValue.length;
        int[] dp = new int[len];
        Arrays.fill(dp, Integer.MIN_VALUE);
        for (int i = len - 1; i >= 0; i--) {
            int take = 0;
            for (int j = i; j < Math.min(i + 3, len); j++) {
                take += stoneValue[j];
                dp[i] = Math.max(dp[i], take - ((j + 1) < len ? dp[j + 1] : 0));
            }
        }
        int diff = dp[0];
        return diff > 0 ? "Alice" : (diff == 0 ? "Tie" : "Bob");
    }


    // 1563. 石子游戏 V
    // ① https://bit.ly/2DpykSU 区间DP
    public int stoneGameV01(int[] stoneValue) {
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
                    int ls = preSum[k] - (i >= 1 ? preSum[i - 1] : 0);
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
        return dp[0][n - 1];
    }

    // 1563. 石子游戏 V ②
    int[] preSum;

    // int[][] cache;
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        cache = new int[n][n];
        preSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            preSum[i + 1] = preSum[i] + stoneValue[i];
        }
        return dp(0, n - 1);
    }

    public int dp(int l, int r) {
        if (l == r) return 0;
        if (cache[l][r] > 0) return cache[l][r];//剪枝
        int ans = 0;
        for (int k = l; k < r; k++) {
            int left = preSum[k + 1] - preSum[l];
            int right = preSum[r + 1] - preSum[k + 1];
            if (left <= right) {
                ans = Math.max(ans, left + dp(l, k));
            }
            if (left >= right) {
                ans = Math.max(ans, right + dp(k + 1, r));
            }
        }
        return cache[l][r] = ans;
    }


    /*
    // 值得研究
    public int stoneGameV(int[] stoneValue) {
        if (stoneValue == null || stoneValue.length <= 1) return 0;
        int n = stoneValue.length;
        int[][] sum = new int[n][n], pt = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    sum[i][j] = stoneValue[i];
                    pt[i][j] = i;
                } else {
                    sum[i][j] = sum[i][j - 1] + stoneValue[j];
                    int mid = pt[i][j - 1];
                    while (sum[i][j] - sum[i][mid] > sum[i][mid]) {
                        mid++;
                    }
                    pt[i][j] = mid;
                }
            }
        }

        int[][] dp = new int[n][n], maxL = new int[n][n], maxR = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[i][j] = 0;  //只有一个石堆，得到0
                    maxL[i][j] = sum[i][j]; //dp[i][j] = 0
                    maxR[i][j] = sum[i][j];
                    continue;
                }
                int mid = pt[i][j];
                int sl = sum[i][mid], sr = mid < j ? sum[mid + 1][j] : 0;
                //左右相等
                if (sl == sr) {
                    dp[i][j] = Math.max(maxL[i][mid], maxR[mid + 1][j]);
                } else {
                    if (mid > i) {
                        dp[i][j] = Math.max(dp[i][j], maxL[i][mid - 1]);
                    }
                    if (mid < j) {
                        dp[i][j] = Math.max(dp[i][j], maxR[mid + 1][j]);
                    }
                }
                maxL[i][j] = Math.max(maxL[i][j - 1], sum[i][j] + dp[i][j]);
                maxR[i][j] = Math.max(maxR[i + 1][j], sum[i][j] + dp[i][j]);
            }
        }
        return dp[0][n - 1];
    }*/

}
