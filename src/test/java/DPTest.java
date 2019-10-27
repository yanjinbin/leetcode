import com.yanjinbin.leetcode.DP;
import com.yanjinbin.leetcode.Pack;
import com.yanjinbin.leetcode.Solution;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DPTest {
    public static DP DP = new DP();
    public static Solution SOLUTION = new Solution();
    public static Pack PACK = new Pack();

    @Before
    public void init() {
        // 背包问题等系列专攻,以及算法分析竞赛入门的7 8 9章,6章二叉树的DFS遍历,(递归,非递归,morris)前序 后序 中序, BFS 水平,层次和蛇形遍历
        System.out.println("====背包问题等系列专攻====");
        System.out.println(" https://drive.google.com/open?id=1gMiJgnOQLcntvPrQXHJehiIuuEuTPBGx ");
        System.out.println("======资料链接如上=======");
    }

    @Test(timeout = 3000)
    public void maxConsecutiveSum() {
        // 经典问题分析
        System.out.println("====解法1====");
        int[] arr = new int[]{3, -1, -4, 9, 8, 7};
        Solution.shuffle(arr, 0, arr.length);
        System.out.println(Arrays.toString(arr));
        assert DP.maxConsecutiveSum1(arr) == SOLUTION.maxSubArray01(arr);
        System.out.println("====解法2====");
        assert DP.maxConsecutiveSum2(arr) == SOLUTION.maxSubArray01(arr);
        System.out.println("====解法3====");
        assert DP.maxConsecutiveSum3(arr) == SOLUTION.maxSubArray01(arr);

    }

    @Test
    public void ZeroOnePack() {
        int N = 4, V = 6;
        int[] C = new int[]{1, 2, 3, 2};
        int[] W = new int[]{4, 6, 12, 7};
        assert PACK.ZeroOnePack_ME(N, V, C, W) == PACK.ZeroOnePack(N, V, C, W);
        assert PACK.ZeroOnePack(N, V, C, W) == PACK.ZeroOnePack_Optimize(N, V, C, W);
        int ci = 2, wi = 1;
        V = 1;
        int[] dp = new int[V + 1];
        System.out.println(PACK.ZeroOnePack(ci, wi, dp, V));
    }

    @Test
    public void CompletePack() {
        int N = 3, V = 70;
        int[] C = {71, 69, 1};
        int[] W = {100, 1, 2};
        assert PACK.CompletePack_Original(N, V, C, W) == PACK.CompletePack_Optimize(N, V, C, W);
        int ci = 2, wi = 3;
        V = 5;
        int[] dp = new int[V + 1];
        System.out.println(PACK.CompletePack(ci, wi, dp, V));
    }

    @Test
    public void MultiplePack() {
        int m = new Random().nextInt(40);
        int k = 1;
        while (k < m) {
            System.out.println("k值:\t" + k + "\tm值\t" + m);
            m = m - k;
            k = 2 * k;
        }
        System.out.println("k值:\t" + k + "\tm值\t" + m);
    }

    @Test
    public void MultiPack() {
        // https://www.luogu.org/record/24015733
        int N = 4, V = 20;
        int[] W = new int[]{3, 5, 9, 8};
        int[] C = new int[]{9, 9, 4, 1};
        int[] M = new int[]{3, 1, 2, 3};
        assert PACK.MultiPack(N, V, C, W, M) == 47;
    }

    @Test
    public void _2DPack() {
        // PACK._2DPack()d
    }

    @Test
    public void GroupPack() {
        int N = 3, V = 45;
        int[] C = new int[]{10, 10, 50};
        int[] W = new int[]{10, 5, 400};
        int[] G = new int[]{1, 1, 2};
        assert PACK.GroupPack(N, V, C, W, G) == 10;
    }

    @Test
    public void TopologyPack() {
        int N = 5, V = 100;
        int[] C = new int[]{800, 400, 300, 400, 500,};
        int[] P = new int[]{2, 5, 5, 3, 2};
        int[] Q = new int[]{0, 1, 1, 0, 0};
        //  assert PACK.TopologyPack(V, N, C, P, Q) == 2200;
    }

    @Test
    public void IntervalDP() {
        int n = 4;
        int[] stones = new int[]{4, 5, 9, 4};
        assert Arrays.equals(PACK.mergeStone(n, stones), new int[]{43, 54});
        assert PACK.isScramble("great", "rgeat") == true;

        int[] nums = new int[]{9,1,2,3,9};
        int k =3;
        assert PACK.largestSumOfAverages(nums, k) == 20;
        int[] boxes = new int[]{1, 3, 2, 2, 2, 3, 4, 3, 1};
        assert PACK.removeBoxes(boxes) == 23;

        String data = "aaa";
        assert PACK.countSubstrings(data) == 6;

        int[] piles= new int[]{5,3,4,5};
        assert PACK.stoneGame(piles) == true;

        stones = new int[]{3,2,4,1};
        k = 2;
        assert PACK.mergeStone(stones, k) == 20;

        stones = new int[] {3,5,1,2,6};
        k = 3;
        assert  PACK.mergeStone(stones,k)==25;
    }

    @Test
    public void TreeDP() {
        System.out.println("测试数据 https://www.luogu.org/problem/P1352");
        PACK.DanceParty();
    }

    @Test
    public void ZeroOnePackSeries() {
        int[] nums = new int[]{1, 5, 11, 5};
        assert PACK.canPartition01(nums) == true;
        String[] strs = new String[]{"10", "0001", "111001", "1", "0"};
        int m = 5, n = 3;
        assert PACK.findMaxForm(strs, m, n) == 4;

        assert PACK.lastStoneWeightII(new int[]{2, 7, 4, 1, 8, 1}) == 1;

        assert PACK.findTargetSum(new int[]{1, 1, 1, 1, 1}, 3) == 5;

    }

    @Test
    public void CompletePackSeries() {
        int amount = 11;
        int[] coins = new int[]{1, 2, 5};
        PACK.coinChange(amount, coins);
        amount = 3;
        coins = new int[]{2};
        PACK.coinChange(amount, coins);

        amount = 5;
        coins = new int[]{1, 2, 5};

        assert PACK.change(amount, coins) == 4;

        assert PACK.combinationSum4(new int[]{1, 2, 3}, 4) == 7;

        System.out.println("leetcode 698 分隔为k个等和子集 这道题 只是一道回溯法而已");
        int[] nums = {4, 3, 2, 3, 5, 2, 1};
        int k = 4;
        assert PACK.canPartitionKSubsets(nums, k) == true;

    }

    @Test
    public void DivideAndConquer() {
        String s = "applepenapple";
        List<String> wordDict = new ArrayList<>();
        wordDict.add("apple");
        wordDict.add("pen");
        assert SOLUTION.wordBreak(s, wordDict) == true;
    }

    @Test
    public void ClassicDP(){
        assert PACK.longestPalindromeSubseq("bbbab") == 4;
        assert PACK.longestPalindrome("abbcbb").equalsIgnoreCase("bbcbb");
     //   PACK.longestPalindrome01("abbcbb");
        assert PACK.longestCommonSubsequence("abcdegf", "acedf")==4;

        int[] nums = {6, 3, 5, 10, 2, 9, 7, 4, 8};
        assert PACK.lengthOfLIS(nums) == 4;
        assert PACK.lengthOfLIS01(nums)==4;
        nums=new int[]{2,3,4,1,5,7,2,4,6,8,11,9};
        assert PACK.findLengthOfLCIS(nums)==5;
        assert PACK.findLengthOfLCIS01(nums)==5;



    }
}
