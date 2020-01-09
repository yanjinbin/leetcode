package com.yanjinbin.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Solution {

    public void show2DArray(int[][] board) {
        System.out.println("==============");
        for (int i = 0; i < board.length; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
        System.out.println("==============");
    }

    public boolean isAscendSort(int[] arr) {
        if (arr == null || arr.length == 1) return true;
        int i = 0;
        while (i < arr.length - 1) {
            if (arr[i] > arr[i + 1]) return false;
            i++;
        }
        return true;
    }

    public static void swap(int[] nums, int i, int j) {
        if (i == j) return;
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // shuffle array lower bound  inclusive   upper bound exclusive
    public static void shuffle(int[] nums, int lower, int upper) {
        Random rand = new Random();
        for (int i = lower; i < upper; i++) {
            int j = lower + rand.nextInt(i - lower + 1);
            swap(nums, i, j);
        }
    }

    //② #2  两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyNode = new ListNode(0);
        ListNode p = l1;
        ListNode q = l2;
        ListNode cur = dummyNode;
        int carry = 0;
        while (p != null || q != null) {
            int x = p != null ? p.val : 0;
            int y = q != null ? q.val : 0;
            int sum = x + y + carry;
            // 更新carry
            carry = sum / 10;

            cur.next = new ListNode(sum % 10);
            // self update
            cur = cur.next;
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }

        // 某位如果超过10 需要在循环外 更新一次
        if (carry > 0) {
            cur.next = new ListNode(carry);
        }
        // dummyNode 是0 所以是返回他的下一个节点,作为头部节点
        return dummyNode.next;
    }


    // ③ 141. Linked List Cycle
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    // ② 234 Palindrome Linked List 回文单链表
    public boolean isPalindrome(ListNode head) {
        if (head == null) return true;
        ListNode cur = head;
        Stack<ListNode> s = new Stack<>();
        while (cur != null) {
            s.push(cur);
            cur = cur.next;
        }
        while (!s.isEmpty()) {
            if (s.pop().val != head.val) return false;
            head = head.next;
        }
        return true;
    }

    // 快慢指针法
    public boolean isPalindrome1(ListNode head) {
        if (head == null || head.next == null) return true;
        // 比较一半元素即可
        ListNode slow = head;
        ListNode fast = head;
        Stack<ListNode> s = new Stack<>();
        s.add(head);

        while (fast != null && fast.next != null) {
            slow = slow.next;
            s.push(slow);
            fast = fast.next.next;
        }

        s.pop();
        // 奇数时候 去掉中间不参与比较的数字
        if (fast != null) {
            slow = slow.next;
        }

        while (!s.isEmpty()) {
            if (slow.val != s.pop().val) return false;
            slow = slow.next;

        }
        return true;
    }

    // 递归方法
    // cur必须 是个成员变量阿
    public ListNode cur;

    public boolean isPalindrome2(ListNode head) {
        cur = head;
        return helper(head);
    }

    public boolean helper(ListNode root) {
        if (root == null) return true;
        boolean res = helper(root.next) && (root.val == cur.val);
        if (res) cur = cur.next;
        return res;
    }

    //② 3 无重复最长子串 双指针法
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int ans = 0, i = 0, j = 0;
        Set<Character> set = new HashSet<>();
        while (i < n && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j));
                j++;
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i));
                i++;
            }
        }
        return ans;
    }

    //③  #5 最长回文字符串 5. Longest Palindromic Substring 官方题解垃圾的一点就是 start 和 end的更新问题 有问题
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int end = 0, start = 0;
        for (int i = 0; i < s.length(); i++) {
            // 处理奇偶问题
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > (end - start)) {
                // 为什么要减一呢 （反推确实验证是越界问题,如果len是2的话）
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    // 最长回文子串 最佳解法 中心扩散法
    // http://bit.ly/2KMyIgk
    public int lo, maxLen;

    public String GoodLongestPalindrome(String s) {
        if (s.length() < 2) {
            return s;
        }
        int length = s.length();
        for (int i = 0; i < length - 1; i++) {
            extendPalindrome(s, i, i);
            extendPalindrome(s, i, i + 1);
        }
        return s.substring(lo, maxLen + lo);
    }

    public void extendPalindrome(String s, int j, int k) {
        while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
            j--;
            k++;
        }
        // Update
        if (maxLen < k - j - 1) {
            lo = j + 1;
            maxLen = k - j - 1;
        }
    }

    // ③ 21. 合并两个有序链表Copy Merge Two Sorted Lists 迭代方法
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummyNode = new ListNode(-1);
        ListNode cur = dummyNode;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = new ListNode(l1.val);
                l1 = l1.next;
            } else {
                cur.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if (l1 == null) {
            cur.next = l2;
        } else {
            cur.next = l1;
        }
        return dummyNode.next;
    }

    //②   递归解法 O(M+N)
    public ListNode mergeTwoSortedList(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoSortedList(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoSortedList(l1, l2.next);
            return l2;
        }
    }

    // 错误的解法
    /*public ListNode mergeTwoLists_0(ListNode l1, ListNode l2) {
        ListNode ret = l1.val > l2.val ? l2 : l1;
        ListNode cursor;
        // 这样想 ,其实有问题的 l1 l2长短不一的时候. 就会NPE阿
        while (l1 != null || l2 != null) {
            if (l1.val > l2.val) {
                cursor = l2;
                cursor.next = l1;
            } else {
                cursor = l1;
                cursor.next = l2;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return ret;
    }*/


    // ③  [tag:微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
    // 23. 合并K个排序链表 Merge k Sorted Lists
    // http://bit.ly/2LtXUbI
    // T: O(lgK)
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return mergeKLists(lists, 0, lists.length - 1);
    }

    // 采用分治思想，递归解决此问题
    public ListNode mergeKLists(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        }
        int mid = (end - start) / 2 + start;

        ListNode l1 = mergeKLists(lists, start, mid);
        ListNode l2 = mergeKLists(lists, mid + 1, end);
        return mergeTwoSortedList(l1, l2);
    }

    // ② 22. 括号生成  回溯法(http://bit.ly/2KPYQHi)  假如我先添加一个左括号，next 接下来我可以添加
    // tips:还可以BFS 也可以 dp
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList();
        backtrack(ans, "", 0, 0, n);
        return ans;
    }

    // DFS 不需要状态重置
    public void backtrack(List<String> ans, String cur, int open, int close, int max) {
        // 问题的解 达成
        if (cur.length() == max * 2) {
            ans.add(cur);
            return;
        }
        // DFS探索
        if (close < open)
            backtrack(ans, cur + ")", open, close + 1, max);
        if (open < max)
            backtrack(ans, cur + "(", open + 1, close, max);
    }

    // ② [tag: 字节面筋 http://bit.ly/2Na3nW1]
    // 11. 盛最多水的容器  T:O(N)
    // 双指针法，左右移动时候，选择移动 高度短的 可能能增加面积 如果是盛水最少的容器呢
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int maxArea = 0;
        while (left < right) {
            maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));
            // 移动策略
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }

    // ③ 206 反转链表  迭代和递归2种方法
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cursor = head;
        while (cursor != null) {
            ListNode next = cursor.next;
            // 更新cursor.next 指向
            cursor.next = prev;
            prev = cursor;
            // iterator
            // cursor = cursor.next 为什么错了 因为cursor.next 已经换了对象了
            cursor = next;
        }
        return prev;
    }


    // ③ 递归方法 1
    public ListNode reverseList1(ListNode head) {
        return help(head, null);
    }

    public ListNode help(ListNode cur, ListNode prev) {
        if (cur == null) return prev;
        ListNode next = cur.next;
        cur.next = prev;
        return help(next, cur);
    }

   /* // 递归方法 2  不好看
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode reversedListHead = reverseList2(head.next);
        // reverse next
        ListNode nextNode = head.next;
        nextNode.next = head;
        head.next = null;
        return reversedListHead;
    }*/


    // ③ 31. 下一个排列 首先理解字典序   找下一个字典序更大的 如果最大了 就全局升序排列了
    //  题解连接 http://bit.ly/2RS8Wbd
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) i--;// 找到第一个破坏 descend order -->i
        if (i >= 0) {
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) j--;//step1: 从右往左找第一个大于i的值 并且swap
            swap(nums, i, j); //
        }
        //step2: 然后 逆序 [i+1,len-1]区间
        reverse(nums, i + 1, nums.length - 1);
    }

    public void reverse(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i++, j--);
        }
    }

    // ③ [tag:微软面筋] https://www.1point3acres.com/bbs/thread-542957-1-1.html
    //  48 旋转图像
    //  http://bit.ly/2RNX8a6
    //  http://bit.ly/2JmVgB7
    //  展示每次移动的元素 https://photos.app.goo.gl/LaeDGURidfWi1oLa7
    public void rotate(int[][] matrix) {
/*        int n = matrix.length;
        // i < n/2 的原因是 从 外层到内层  你需要转移置换 多少个 "4数字"
        for (int i = 0; i < n / 2; i++) {
            // 告诉你  每一层 你需要移动的元素个数
            for (int j = i; j < n - i - 1; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = tmp;
            }
        }*/

        // step: 对角线折一次，然后逆序下。
        int N = matrix.length;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }

        for (int i = 0; i < N; i++) {
            reverse(matrix[i], 0, matrix.length - 1);
        }
    }

    // ② 53. 最大子序和
    // dp[i] = dp[i-1]+A[i],if A[i]>0;else dp[i] = d[i-1]
    public int maxSubArray01(int[] nums) {
        int res = Integer.MIN_VALUE, curSum = 0;
        for (int num : nums) {
            // 为什么比较的是curSum+num 和 num呢  而不是curSum+num 和 curSum呢  也就是判断num的正负呢
            // http://bit.ly/2KTtSOr 理解这里的增益效果是关键
            // 思考角度从num元素个体来出发 而不是从连续累加和来出发
            curSum = Math.max(curSum + num, num);
            res = Math.max(res, curSum);
        }
        return res;
    }

    // 解法2 dp
    // dp[i]=dp[i-1]+nums[i] if dp[i-1]>=0
    // dp[i] =nums[i] if dp[i-1]<=0;
    public int maxSubArray02(int[] nums) {
        int N = nums.length;
        int[] dp = new int[N];
        dp[0] = nums[0];
        for (int i = 1; i < N; i++) {
            if (dp[i - 1] > 0) dp[i] = dp[i - 1] + nums[i];
            else dp[i] = nums[i];
        }
        int ans = Integer.MIN_VALUE;
        for (int i : dp) {
            ans = Math.max(ans, i);
        }
        return ans;
    }

    // ③ 152 乘积最大的连续 子序列 http://bit.ly/2RZ9AUo
    // 这道题目 关键在于 负数 以及0的处理
    // 最大的最小的有可能互换  以及0 会使得  一切乘积都为0
    // 因此需要保留 minDp和maxDp
    public int maxProduct(int[] nums) {
        int N = nums.length;
        int[] mindp = new int[N];
        int[] maxdp = new int[N];
        int res = nums[0];
        mindp[0] = nums[0];
        maxdp[0] = nums[0];
        for (int i = 1; i < N; i++) {
            if (nums[i] > 0) {
                maxdp[i] = Math.max(maxdp[i - 1] * nums[i], nums[i]);
                mindp[i] = Math.min(mindp[i - 1] * nums[i], nums[i]);
            } else {
                maxdp[i] = Math.max(mindp[i - 1] * nums[i], nums[i]);
                mindp[i] = Math.min(maxdp[i - 1] * nums[i], nums[i]);
            }
            res = Math.max(res, maxdp[i]);
        }
        return res;
    }

    // ③  70 climbing stairs
    // 转义公式 dp[i] = dp[i - 1] + dp[i - 2];
    public int climbStairs1(int n) {
        if (n <= 1) return 1;
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n - 1];
    }

    // 递归+记忆数组
    // DP问题: 记忆数组+迭代
    public int climbStairs2(int n) {
        int[] memo = new int[n + 1];
        return climbHelper(n, memo);
    }

    public int climbHelper(int n, int[] memo) {
        if (n <= 1) return 1;
        if (memo[n] > 0) {
            return memo[n];
        }
        return memo[n] = climbHelper(n - 1, memo) + climbHelper(n - 2, memo);
    }

    // ② 64. 最小路径和  这是一道很经典的题目  哈哈 😁  忠于自己亲手做出来了 😘
    // DFS遍历 或者 DP都可以
    // dp(i,j) = grid(i,j)+min(dp(i-1,j)+grid(i-1,j),dp(i,j-1)+grid(i,j-1))
    public int minPathSum(int[][] grid) {
        int rowLen = grid.length;
        int colLen = grid[rowLen].length;
        int[][] memo = new int[rowLen][colLen];
        return routerHelper(rowLen - 1, colLen - 1, grid, memo);
    }

    public int routerHelper(int i, int j, int[][] grid, int[][] memo) {
        // 存在记忆了 不用再次计算
        if (memo[i][j] > 0) {
            return memo[i][j];
        }

        if (i == 0 && j == 0) {
            return memo[i][j] = grid[0][0];
        }
        if (i == 0) {
            return memo[i][j] = routerHelper(i, j - 1, grid, memo) + grid[i][j];
        }
        if (j == 0) {
            return memo[i][j] = routerHelper(i - 1, j, grid, memo) + grid[i][j];
        }
        return memo[i][j] = Math.min(routerHelper(i - 1, j, grid, memo), routerHelper(i, j - 1, grid, memo)) + grid[i][j];
    }

    // ③ 75. 颜色分类  只要遇到 0和2 就进行交换即可
    // 这道题目的 swap 和 3项快排很像。但是目测还是不同的
    public void sortColors(int[] nums) {
        // 双指针法 移动
        int red = 0, blue = nums.length - 1;
        for (int i = 0; i <= blue; i++) {
            if (nums[i] == 0) {
                swap(nums, red++, i);
            } else if (nums[i] == 2) {
                // 为什么i--呢 这里是关键点哦 因为1 比 0大 可能还需要再交换一次啦阿
                swap(nums, blue--, i--);
            }
        }
    }

    // 组合经典题型  tips: 不要写错了，poll是pollFirst而不是pollLast

    // ② 78 子集和
    //👎🏻 解法1 看起来比较难理解额 树形展开
    public List<List<Integer>> subsets01(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        for (int num : nums) {
            int size = res.size();
            for (int i = 0; i < size; i++) {
                List<Integer> sub = new ArrayList<>(res.get(i));
                sub.add(num);
                res.add(sub);
            }
        }
        return res;
    }

    // 解法2 dfs  但是不让过 ，因为顺序不同
    public List<List<Integer>> subsets02(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i <= nums.length; i++) {
            dfsComb(nums, i, 0, ans, new LinkedList<>());
        }
        return ans;
    }

    public void dfsComb(int[] nums, int len, int start, List<List<Integer>> ans, LinkedList<Integer> sub) {
        if (sub.size() == len) {
            ans.add(new ArrayList<>(sub));
            return;
        }
        for (int i = start; i < nums.length; i++) {
            sub.add(nums[i]);
            dfsComb(nums, len, i + 1, ans, sub);
            // 不要写错了，poll是pollFirst而不是pollLast
            sub.pollLast();
        }
    }

    // 解法3
    public List<List<Integer>> subsets03(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        dfsComb78(nums, 0, new LinkedList<>(), ans);
        return ans;
    }

    private void dfsComb78(int[] nums, int start, LinkedList<Integer> sub, List<List<Integer>> ans) {
        ans.add(new ArrayList<>(sub));
        for (int i = start; i < nums.length; i++) {
            sub.add(nums[i]);
            dfsComb78(nums, i + 1, sub, ans);
            sub.pollLast();
        }
    }

    // ② LC 90 子集Ⅱ
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        dfsComb(nums, 0, new LinkedList<>(), ans);
        return ans;
    }

    private void dfsComb(int[] nums, int start, LinkedList<Integer> sub, List<List<Integer>> ans) {
        ans.add(new ArrayList<>(sub));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;
            sub.add(nums[i]);
            dfsComb(nums, i + 1, sub, ans);
            sub.pollLast();
        }
    }

    // ② 79 单词搜索   T:O(M*N)
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || (board.length == 1 && board[0].length == 0)) return false;
        int collen = board.length;
        int rowLen = board[0].length;
        boolean[][] visited = new boolean[collen][rowLen];
        char[] words = word.toCharArray();
        for (int i = 0; i < collen; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // dfs search  back trace
                if (dfsSearch(i, j, collen, rowLen, board, 0, words, visited)) return true;
            }
        }
        return false;
    }

    public boolean dfsSearch(int i, int j, int colLen, int rowLen, char[][] board, int macth, char[] word, boolean[][] visited) {
        if (macth == word.length) return true;
        if (i < 0 || j < 0 || i >= colLen || j >= rowLen || visited[i][j] || board[i][j] != word[macth]) return false;

        visited[i][j] = true;

        // 为什么这么写就错了呢
       /* int[] dirs = new int[]{-1, 0, 1, 0, -1};
        boolean exist = false;
        for (int k = 1; k < 4; k++) {
            exist  |= dfsSearch(i + dirs[k], j + dirs[k + 1], colLen, rowLen, board, macth + 1, word, visited);
        }*/

        boolean exist = dfsSearch(i + 1, j, colLen, rowLen, board, macth + 1, word, visited) ||
                dfsSearch(i, j + 1, colLen, rowLen, board, macth + 1, word, visited) ||
                dfsSearch(i - 1, j, colLen, rowLen, board, macth + 1, word, visited) ||
                dfsSearch(i, j - 1, colLen, rowLen, board, macth + 1, word, visited);


        visited[i][j] = false;
        return exist;
    }

    // ③ 139 单词拆分 http://bit.ly/2Ld41Bt  0起点,长度为N的字符串 能否被words填充

    // dp[j] 代表 第j个字符，故dp[i]=dp[j]&&s.substring(j,i); dp[0]= true;
    // S[0,i)= S[0,j) || S[j,i)  0 <= j < i <= s.length()
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;// init
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    // ③ 287. 寻找重复数  除了下面这2种 ,lowB点用set处理或者排好序
    // 可以把index对应的value当做一个状态值 那么 value重复的化 就相当于是存在还了,可以使用floyd算法来检测
    // 这道题目跟处理链表是否存在环比较相似
    // Floyd算法wiki ---->   http://bit.ly/2S1omdy  龟兔赛跑方法
    public int findDuplicate01(int[] nums) {
        // Find the intersection point of the two runners.
        int tortoise = nums[0];
        int hare = nums[0];
        // do while结构 ！！ 之前写错了，协程while(){}
        do {
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
        } while (tortoise != hare);

        // Find the "entrance" to the cycle.
        int s = nums[0];
        int m = tortoise;
        while (s != m) {
            s = nums[s];
            m = nums[m];
        }
        return s;

    }

    // ③  解法2  鸽巢原理
    public int findDuplicate02(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, nums[i] - 1, i);
            }
        }
        return nums[n - 1];
    }

    // 287 ③ 这种二分法还是比较少见的 但是也存在多钟限制阿 中间数的计算近似 median=(right+left)/2;
    public int findDuplicate03(int[] nums) {
        // // 特殊case n = 1 ,长度为2，{1,1} ; n= 2 ,长度为3,{1,2,2} or {1,1,2}
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0;
            for (int num : nums) { // 计算小于mid的个数,
                if (num <= mid) count++;
            }
            if (count <= mid) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }


    // [tag:鸽巢原理]
    //③ 41 缺失的第一个正数  http://bit.ly/2Pir4Mc 类似鸽巢原理 ,放到正确的位置
    public int firstMissingPositive01(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, nums[i] - 1, i);
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {// （i+1 代表各自，i代表鸽巢位置）
                return i + 1;
            }
        }
        return n + 1;
    }

    // ③ 解法 2 ,set 判定去重
    public int firstMissingPositive02(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            set.add(i);
        }
        int i = 1;
        while (set.contains(i)) i++;
        return i;
    }

    //③ 442 数组中重复的数据  鸽巢原理
    public List<Integer> findDuplicates(int[] nums) {
        int N = nums.length;
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            while (nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < N; i++) {
            if (nums[i] != i + 1) {  // i+1这个鸽子不在正确的巢 i里
                ret.add(nums[i]);       // 这个巢本该有的鸽子就是缺失的数字
            }
        }
        return ret;
    }

    //③ 448. 找到所有数组中消失的数字 http://bit.ly/2qMgEKN
    public List<Integer> findDisappearedNumbers01(int[] nums) {
        int N = nums.length;
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            while (nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < N; i++) {
            if (nums[i] != i + 1) {  // i+1这个鸽子不在正确的巢里
                ret.add(i + 1);       // 这个巢本该有的鸽子就是缺失的数字
            }
        }
        return ret;
    }

    //  [tag:微软] https://www.1point3acres.com/bbs/thread-506243-1-1.html
    // ③ 142. 环形链表 II
    // 环检测 https://leetcode-cn.com/problems/linked-list-cycle-ii/
    // 解除环 环长度
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }
        //  条件没写全 if (fast != slow)
        if (fast != slow || fast == null || fast.next == null) return null;
        slow = head;
        ListNode m = fast;
        int len = 0;
        while (m != slow) {
            slow = slow.next;
            m = m.next;
            len++;
        }
        // len 长度
        return slow;
    }

    // [tag:微软] https://www.1point3acres.com/bbs/thread-506243-1-1.html
    // 摩尔投票法 仔细想想 还是对的 因为不管如何排列,众数 频次肯定>=1阿  whatever even or odd
    // 169
    public int majorityElement01(int[] nums) {
        int count = 1, candidate = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                candidate = nums[i];
                count = 1;
            } else if (nums[i] == candidate) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }

    //follow up
    // 229 超过1/3
    public List<Integer> majorityElement02(int[] nums) {
        List<Integer> ans = new ArrayList();
        if (nums == null || nums.length == 0) return ans;
        int candidateA = nums[0];
        int cntA = 0;
        int candidateB = nums[0];
        int cntB = 0;
        for (int num : nums) {
            if (candidateA == num) {
                cntA++;
            } else if (candidateB == num) {
                cntB++;
            } else if (cntA == 0) {
                cntA = 1;
                candidateA = num;
            } else if (cntB == 0) {
                cntB = 1;
                candidateB = num;
            } else {
                cntA--;
                cntB--;
            }
        }

        // RESET
        cntA = 0;
        cntB = 0;
        for (int num : nums) {
            if (candidateA == num) {
                cntA++;
            } else if (candidateB == num) {
                cntB++;
            }
        }
        if (cntA > nums.length / 3) {
            ans.add(candidateA);
        }
        if (cntB > nums.length / 3) {
            ans.add(candidateB);
        }
        return ans;

    }


    // ② 283. 移动零
    public void moveZeroes(int[] nums) {
        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                swap(nums, i, idx);
                idx++;
            }
        }
    }

    // 581. 最短无序连续子数组
    // ② 先排序
    public int findUnsortedSubarray01(int[] nums) {
        int start = 0, end = nums.length - 1;
        int[] bak = Arrays.copyOf(nums, nums.length);
        Arrays.sort(bak);
        while (start < nums.length && nums[start] == bak[start]) start++;
        while (end > start && nums[end] == bak[end]) end--;
        return end - start + 1;
    }

    // ② 解法2 单调栈一个递增，求min,一个递减，求max。  interview friendly T: O(N)
    public int findUnsortedSubarray02(int[] nums) {
        Stack<Integer> s = new Stack();
        int l = nums.length - 1, r = 0;
        for (int i = 0; i < nums.length; i++) {
            while (!s.isEmpty() && nums[s.peek()] > nums[i]) {
                int idx = s.pop();
                l = Math.min(l, idx);
            }
            s.push(i);
        }
        s.clear();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!s.isEmpty() && nums[s.peek()] < nums[i]) {
                r = Math.max(r, s.pop());
            }
            s.push(i);
        }
        return r - l > 0 ? r - l + 1 : 0;
    }

    // ③ 560. 和为K的子数组
    // corner case
    // map.put(0,1) 这个为什么需要呢
    //比如 [2,2,2,2,2] k=4 ; [1,3,2,2,4] k=4 ; 以及 [0,0,0,0] k=0
    //就是从起始数开始求的连续和为K 那么 这种corner case 你就需要 放上map.put(0,1) 0 1可以理解为0出现的次数为1 相当于 sum(0,i)=k --> sum(0,i)-k =0
    //同理 count +=map.get(sum-k) 而不是count++哈哈
    //自己可以 画个表格 列出nums[i] sum sum-k 函数count(k) count(sum-k)
    // 花花酱视频  http://bit.ly/2S3K2We
    public int subarraySum(int[] nums, int k) {
        int ans = 0, sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);// 比较tricky的啊,和为0的个数是1.
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) ans += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }

    // ③ 56. 合并区间
    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) return intervals;
        Arrays.sort(intervals, (o1, o2) -> {
            return o1[0] - o2[0];
        });
        List<int[]> ret = new ArrayList<>();
        int[] cursor = intervals[0];
        ret.add(cursor);
        for (int[] interval : intervals) {
            if (cursor[1] >= interval[0]) {// overlap update
                cursor[1] = Math.max(interval[1], cursor[1]);
            } else {// disjoint interval ,just add it
                cursor = interval;
                ret.add(cursor);
            }
        }
        return ret.toArray(new int[ret.size()][]);
    }

    // ② 215. 数组中的第K个最大元素
    // quick sort思想
    public int findKthLargest01(int[] nums, int k) {
        int left = 0, right = nums.length - 1;
        while (true) {
            int pos = partition(nums, left, right);
            if (pos == k - 1) return nums[pos];
            if (pos < k - 1) {
                left = pos + 1;
            } else {
                right = pos - 1;
            }
        }
    }

    public int partition(int[] nums, int lo, int hi) {
        int pivot = nums[lo];
        int l = lo + 1, r = hi;
        while (l <= r) {
            if (nums[l] < pivot && pivot < nums[r]) {
                swap(nums, l++, r--);
            }
            if (nums[l] >= pivot) l++;
            if (nums[r] <= pivot) r--;
        }
        // l-r=1
        //为什么必须要 交换的是R呢
        // 分三种情况
        // l=r的时候 nums[l]>=pivot 或者 nums[r]<=pivot
        // l+1=r的时候 l++,r--此时 此时l = r ,r = l,那么 此时需要交交还的依旧是因为nums[r]>nums[l]
        swap(nums, lo, r);
        return r;
    }

    // ② 大小堆来做
    public int findKthLargest02(int[] nums, int k) {
        // init heap 'the smallest element first'
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>(((o1, o2) -> o1 - o2));

        // keep k largest elements in the heap
        for (int n : nums) {
            heap.add(n);
            if (heap.size() > k)
                heap.poll();
        }
        // output
        return heap.peek();
    }

    //② 148 排序链表  归并排序  O(NlgN)
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head, fast = head;
        ListNode prev = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // break
        prev.next = null;
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);
        return mergeTwoSortedList(l1, l2);
    }

    // ③ 238. 除自身以外数组的乘积 至少需要2次遍历来
   /* public int[] productExceptSelf(int[] nums) {
        int size = nums.length;
        int[] res = new int[size];
        int[] l = new int[size];
        int[] r = new int[size];
        l[0] = 1;
        r[size - 1] = 1;
        for (int i = 1; i < size; i++) {
            l[i] = l[i - 1] * nums[i - 1];
            r[size - 1 - i] = r[size - i] * nums[size - i];

        }
        for (int i = 0; i < size; i++) {
            res[i] = l[i] * r[i];
        }
        return res;
    }*/

    //②  解法 2
    public int[] productExceptSelf(int[] nums) {
        int N = nums.length;
        int[] ans = new int[N];
        int k = 1;
        for (int i = 0; i < N; i++) {
            ans[i] = k;
            k = k * nums[i];// 左积
        }
        k = 1;
        for (int i = N - 1; i >= 0; i--) {
            ans[i] = ans[i] * k;//
            k = k * nums[i]; // 右积
        }
        return ans;
    }

    //② 33. 搜索旋转排序数组 tips: 构建不等式约束关系 来 选择 边界、
    // 注意  折转之后的  曲线 参考图见：https://youtu.be/qKgKU7gMZ1I?t=300
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < nums[hi]) { // 构建不等式约束关系
                if (nums[mid] < target && target <= nums[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            } else {
                if (nums[lo] <= target && target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
        }
        return -1;
    }

    // ② 34. 在排序数组中查找元素的第一个和最后一个位置
    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) return new int[]{-1, -1};
        int lo = 0, hi = nums.length, left = -1, right = -1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < target) {
                lo = mid + 1;
            } else { // 往闭区间一侧lo靠近
                hi = mid;
            }
        }
        if (hi == nums.length || nums[hi] != target) return new int[]{-1, -1};
        left = hi;


        // upper bound函数
        lo = -1;
        hi = nums.length - 1;
        while (lo < hi) {
            int mid = hi - (hi - lo) / 2;
            if (nums[mid] <= target) { // 往闭区间hi一侧靠近
                lo = mid;
            } else {
                hi = mid - 1;
            }

        }

        if (hi == -1 || nums[hi] != target) return new int[]{-1, -1};
        right = hi;
        return new int[]{left, right};
    }

    // 背包套路不适合 这道题目还是通过回溯法去解决
    //③ 39. 组合总和 Ⅰ
    // http://bit.ly/2XHHBi2  这个方法感觉还是不够优雅阿
    public List<List<Integer>> combinationSum1(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(candidates, target, res, 0, new LinkedList<>());
        return res;
    }

    public void backtrack(int[] candidates, int target, List<List<Integer>> res, int start, LinkedList<Integer> sub) {
        if (target < 0) return;
        if (target == 0) {
            res.add(new ArrayList(sub));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            sub.addLast(candidates[i]);
            backtrack(candidates, target - candidates[i], res, i, sub);
            sub.pollLast();
        }
    }


    // ③  40. 组合总和 II 和 LC 90 子集Ⅱ 相同
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, ans, 0, new LinkedList<Integer>());
        return ans;
    }

    public void dfs(int[] candidates, int target, List<List<Integer>> ans, int start, LinkedList<Integer> sub) {
        if (target < 0) return;
        if (target == 0) {
            ans.add(new ArrayList<>(sub));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (target < candidates[i]) return;
            if (i > start && candidates[i - 1] == candidates[i]) continue;
            sub.add(candidates[i]);
            dfs(candidates, target - candidates[i], ans, i + 1, sub);
            sub.pollLast();
        }
    }

    // 216. 组合总和 III
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList();
        dfs(k, n, 1, ans, new LinkedList());
        return ans;
    }

    public void dfs(int k, int n, int s, List<List<Integer>> ans, LinkedList<Integer> sub) {
        if (n == 0) {
            if (k == 0) ans.add(new ArrayList(sub));
            return;
        }
        for (int i = s; i <= 9; i++) {
            if (i > n) return;
            sub.addLast(i);
            dfs(k - 1, n - i, i + 1, ans, sub);
            sub.pollLast();
        }
    }

    // ③ 377 组合总和Ⅳ 完全背包问题,求个数
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;// 求个数，故初始化 dp[0]=1;
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
                // 互斥，故相加。
                if (i >= nums[j]) dp[i] = dp[i] + dp[i - nums[j]];
            }
        }
        return dp[target];
    }

    // 不同路径系列

    // ③ 62. 不同路径 dp解法
    // dp[i][j] = dp[i-1][j]+dp[i][j-1], i∈[0,m),j∈[0,n);
    public int uniquePaths01(int m, int n) {
        int[][] dp = new int[m][n];
        // 初始化
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }
        // dp
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    // ③ 解法 2 dfs
    public int uniquePaths02(int m, int n) {
        int[][] memo = new int[m + 1][n + 1];
        return path(m, n, memo);
    }

    public int path(int i, int j, int[][] memo) {
        if (memo[i][j] > 0) {
            return memo[i][j];
        }
        if (i == 1 && j == 1) {
            return memo[i][j] = 1;
        }
        if (j > 1) {
            memo[i][j] += path(i, j - 1, memo);
        }
        if (i > 1) {
            memo[i][j] += path(i - 1, j, memo);
        }
        return memo[i][j];
    }

    // 63. 不同路径 II

    // 980 不同路径 III

    // ② 77  组合
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList();
        LinkedList<Integer> sub = new LinkedList();
        dfsCombine(ans, sub, 1, k, n);
        return ans;
    }

    public void dfsCombine(List<List<Integer>> ans, LinkedList<Integer> sub, int start, int k, int n) {
        if (k == 0) {
            ans.add(new ArrayList(sub));
            return;
        }
        for (int i = start; i <= n; i++) {
            sub.addLast(i);
            // 写错了
            // dfsCombine(ans,sub,start+1,k-1,n);
            dfsCombine(ans, sub, i + 1, k - 1, n);
            sub.pollLast();
        }
    }

    // 全排列系列
    //③ 46. 全排列 dfs +   visited boolean数组
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        LinkedList<Integer> sub = new LinkedList<>();
        boolean[] visit = new boolean[nums.length];
        dfsPermute(nums, 0, visit, sub, ret);
        return ret;
    }

    public void dfsPermute(int[] nums, int level, boolean[] visit, LinkedList<Integer> sub, List<List<Integer>> ret) {
        if (level == nums.length) {
            // 错误写法
            // ret.add(sub);
            ret.add(new ArrayList<>(sub));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visit[i]) continue;
            visit[i] = true;
            sub.addLast(nums[i]);
            dfsPermute(nums, level + 1, visit, sub, ret);
            sub.pollLast();
            visit[i] = false;
        }
    }

    // 47 全排列Ⅱ
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        LinkedList<Integer> sub = new LinkedList<>();
        Arrays.sort(nums);
        boolean[] seen = new boolean[nums.length];
        dfsPermuteUnique(nums, 0, seen, ans, sub);
        return ans;
    }

    public void dfsPermuteUnique(int[] nums, int level, boolean[] seen, List<List<Integer>> ans, LinkedList<Integer> sub) {
        if (level == nums.length) {
            ans.add(new ArrayList<>(sub));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (seen[i] || (i > 0 && seen[i - 1] && nums[i] == nums[i - 1])) continue;
            seen[i] = true;
            sub.addLast(nums[i]);
            dfsPermuteUnique(nums, level + 1, seen, ans, sub);
            sub.pollLast();
            seen[i] = false;
        }
    }


    // [tag:微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
    // 128. 最长连续序列 map set solve  哈希表/并查集/DP    parent = i , son = i+1;
    // ② 复杂度 nLog(N)
    public int longestConsecutive01(int[] nums) {
        Arrays.sort(nums);
        int ans = 1;
        int res = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] == 1) {
                ans++;
            } else {
                res = Math.max(res, ans);
                ans = 1;
            }
        }
        return res;
    }

    // ② tips: 存入set,找到连续递增序列的第一个数  nums-1   T:O(N)
    public int longestConsecutive02(int[] nums) {
        Set<Integer> sets = new HashSet();
        for (int i : nums) {
            sets.add(i);
        }

        int ans = 0;
        for (int num : nums) {
            // 如果是连续序列,找到第一个起点
            if (!sets.contains(num - 1)) {
                int currentNum = num;
                int res = 1;
                while (sets.contains(currentNum + 1)) {
                    currentNum += 1;
                    res++;
                }
                ans = Math.max(ans, res);
            }
        }
        return ans;
    }

    //  接下去 进入 二叉树专题
    // ③ 94. 二叉树的中序遍历 递归做法
    // tips: 搞清楚 树的迭代是有轮回的 也就是说 中序遍历的左右子树要看清楚是哪个部分,子树层层递进的起点
    public List<String> inorder01(TreeNode root) {
        List<String> ret = new ArrayList<>();
        inorderHelper(root, ret);
        return ret;
    }

    public void inorderHelper(TreeNode root, List<String> ret) {
        if (root == null) return;
        inorderHelper(root.left, ret);
        ret.add(root.name);
        inorderHelper(root.right, ret);
    }

    // ③ 解法2  栈来做
    public List<Integer> inorder02(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        TreeNode cur = root;
        while (!s.isEmpty() || cur != null) {
            if (cur != null) {
                s.push(cur);
                cur = cur.left;
            } else {
                TreeNode node = s.pop();
                ans.add(node.val);  // Add after all left children
                cur = node.right;
            }
        }
        return ans;
    }

    //③ morris 要回答 什么时候访问节点,左子树没有的时候,左子树有的时候,访问玩pre前驱节点,断开,然后访问root节点
    // 解法 3 Morris遍历算法  理解起来很麻烦 http://bit.ly/2jXmyW5,
    public List<Integer> inorderTraversal04(TreeNode root) {
        // morris 遍历 核心 就是建立 root和 左子树 最右边节点的关系 pre = root.left; pre.right = root;
        List<Integer> ret = new ArrayList();
        while (root != null) {
            if (root.left == null) {
                ret.add(root.val);
                root = root.right;
            } else {
                TreeNode pre = root.left;
                while (pre.right != null && pre.right != root) {
                    pre = pre.right;
                }

                if (pre.right == null) {
                    // 建立morris 关键步骤
                    pre.right = root;
                    root = root.left;
                } else {
                    // 断开 morris 关系
                    pre.right = null;
                    ret.add(root.val);
                    root = root.right;
                }
            }
        }
        return ret;
    }

    // ③ 144. 二叉树的前序遍历 递归玩法
    public List<Integer> preorder01(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        preorderHelper(root, ret);
        return ret;
    }

    public void preorderHelper(TreeNode root, List<Integer> ret) {
        if (root == null) return;
        ret.add(root.val);
        preorderHelper(root.left, ret);
        preorderHelper(root.right, ret);
    }

    public List<Integer> preorder02(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        TreeNode cur = root;
        while (!s.isEmpty() || cur != null) {
            if (cur != null) {
                s.push(cur);
                ans.add(cur.val);  // Add before going to children
                cur = cur.left;
            } else {
                TreeNode node = s.pop();
                cur = node.right;
            }
        }
        return ans;
    }

    // ③ morris 前序遍历
    public List<Integer> preOrder03(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        while (root != null) {
            if (root.left == null) {
                ret.add(root.val);
                root = root.right;
            } else {
                TreeNode pre = root.left;
                while (pre.right != null && pre.right != root) {
                    pre = pre.right;
                }
                if (pre.right == null) {
                    // 建立morris 关系
                    pre.right = root;
                    // 先访问 root!!!
                    ret.add(root.val);
                    root = root.left;
                } else {
                    // 断开
                    pre.right = null;
                    root = root.right;
                }
            }
        }
        return ret;
    }

    // ③ 145. 二叉树的后序遍历 http://bit.ly/2SodiqQ
    public List<Integer> postorder01(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        postorderHelper(root, ret);
        return ret;
    }

    public void postorderHelper(TreeNode root, List<Integer> ret) {
        if (root == null) return;
        postorderHelper(root.left, ret);
        postorderHelper(root.right, ret);
        ret.add(root.val);
    }

    // 解法 2 栈
    public List<Integer> postorder02(TreeNode root) {
        LinkedList<Integer> ans = new LinkedList<>();
        Stack<TreeNode> s = new Stack<>();
        TreeNode cur = root;
        while (!s.isEmpty() || cur != null) {
            if (cur != null) {
                s.push(cur);
                ans.addFirst(cur.val);  // Reverse the process of preorder
                cur = cur.right;             // Reverse the process of preorder
            } else {
                TreeNode node = s.pop();
                cur = node.left;           // Reverse the process of preorder
            }
        }
        return ans;
    }

    // morris遍历
    public List<Integer> postorder03(TreeNode root) {
        LinkedList<Integer> ans = new LinkedList<>();
        TreeNode cur = root;
        while (cur != null) {
            if (cur.right == null) {
                ans.addFirst(cur.val);
                cur = cur.left;
            } else {
                TreeNode prev = cur.right;
                while (prev.left != null && prev.left != cur) {
                    prev = prev.left;
                }
                if (prev.left == null) {
                    prev.left = cur;
                    ans.addFirst(cur.val);
                    cur = cur.right;
                } else {
                    prev.left = null;
                    cur = cur.left;
                }
            }
        }
        return ans;
    }

    // ③ 102. 二叉树的层次遍历
    public List<List<Integer>> levelOrder0(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        List<List<Integer>> ret = new ArrayList<>();
        while (!queue.isEmpty()) {
            List<Integer> subSet = new ArrayList<>();
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                TreeNode node = queue.poll();
                subSet.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            if (!subSet.isEmpty()) {
                ret.add(subSet);
            }
        }
        return ret;
    }

    // 递归做法
    public List<List<Integer>> levelOrder1(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        levelHelper(root, 0, ret);
        return ret;
    }

    public void levelHelper(TreeNode root, int level, List<List<Integer>> ret) {
        if (root == null) {
            return;
        }
        // 为什么要加上这一判断呢 因为 index >= size的时候 就会包index out of range 错误了 tips 值得注意的点
        if (ret.size() == level) {
            ret.add(new ArrayList<>());
        }
        ret.get(level).add(root.val);

        levelHelper(root.left, level + 1, ret);
        levelHelper(root.right, level + 1, ret);
    }

    // ③ 96. 不同的二叉搜索树 BST ,  dp http://bit.ly/36HVnRO
    //  G（n） = f(1)+f(2)+f(3)+...+f(n)
    //  f(i)  = G(i-1)*G(n-i)
    // G(n)=G(0)∗G(n−1)+G(1)∗(n−2)+...+G(n−1)∗G(0)
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        // init
        dp[0] = dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[i - j] * dp[j - 1];
            }
        }
        return dp[n];
    }

    // ② 95 不同的二叉搜索树 dp
    // 这道题目很经典,这道题目,比96 求解个数, 换了一种形式.
    public List<TreeNode> generateTrees(int N) {
        if (N == 0) return new ArrayList<TreeNode>();
        List<TreeNode>[] dp = new List[N + 1];
        // init;
        dp[0] = new ArrayList();
        dp[0].add(null);
        for (int i = 1; i <= N; i++) {
            dp[i] = new ArrayList<TreeNode>();
            for (int j = 1; j <= i; j++) {
                for (TreeNode left : dp[j - 1]) {
                    for (TreeNode right : dp[i - j]) {
                        TreeNode root = new TreeNode(j);
                        root.left = left;
                        root.right = clone(right, j);
                        dp[i].add(root);
                    }
                }
            }
        }
        return dp[N];
    }

    public TreeNode clone(TreeNode root, int delta) {
        if (root == null) return root;
        TreeNode node = new TreeNode(root.val + delta);
        node.left = clone(root.left, delta);
        node.right = clone(root.right, delta);
        return node;
    }


    // ③ 98. 验证二叉搜索树
    // 用Long 代替int 就是为了满足边界条件 if root.val= Integer.MAX_VALUE
    public boolean isValidBST0(TreeNode root) {
        long right = Long.MAX_VALUE;
        long left = Long.MIN_VALUE;
        return dfsValidateHelper(root, left, right);
    }

    public boolean dfsValidateHelper(TreeNode cur, long left, long right) {
        if (cur == null) return true;
        if (cur.val < right && cur.val > left)
            return dfsValidateHelper(cur.left, left, cur.val) && dfsValidateHelper(cur.right, cur.val, right);
        return false;
    }

    //③ 101. 对称二叉树递归左右对称即可。 迭代做法 两个队列放入元素顺序需要做到对称
    // tips: 本质上是比较2个左右子树的对称性 也就是2棵树。
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return validateSymmetricHelper(root.left, root.right);
    }

    public boolean validateSymmetricHelper(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left != null && right != null && left.val == right.val) {
            return validateSymmetricHelper(left.left, right.right) && validateSymmetricHelper(left.right, right.left);
        }
        return false;
    }

    //② 114. 二叉树展开为链表   看懂图解哦
    public void flatten01(TreeNode root) {
        if (root == null) return;
        flatten01(root.left);
        flatten01(root.right);
        TreeNode tmp = root.right;
        root.right = root.left;
        root.left = null;
        //  key tips: 更新root.right节点
        while (root.right != null) {
            root = root.right;
        }
        root.right = tmp;
    }

    //② 解法 2
    public void flatten02(TreeNode root) {
        if (root == null) return;
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode p = cur.left;
                while (p.right != null) p = p.right;
                p.right = cur.right;
                cur.right = cur.left;
                cur.left = null;
            }
            cur = cur.right;
        }
    }

    // ③ 226. 翻转二叉树
    public TreeNode invertTree(TreeNode root) {
        postInvert(root);
        return root;
    }

    // 后续遍历
    public void postInvert(TreeNode root) {
        if (root == null) return;
        postInvert(root.left);
        postInvert(root.right);
        // 交换
        TreeNode tmp = root.right;
        root.right = root.left;
        root.left = tmp;
    }

    // ③ 538. 把二叉搜索树转换为累加树  BST 中序遍历 满足顺序关系  先访问右子树-->root-->左子树 降序排列
    public int delta = 0;

    public TreeNode convertBST(TreeNode root) {
        if (root == null) return root;
        convertBST(root.right);
        root.val = root.val + delta;
        delta = root.val;
        convertBST(root.left);
        return root;
    }

    // ③ 687
    private int ans687 = Integer.MIN_VALUE;

    public int longestUnivaluePath(TreeNode root) {
        if (root == null) return 0;
        dfsPost(root);
        return ans687;
    }

    public int dfsPost(TreeNode root) {
        if (root == null) return 0;
        int l = dfsPost(root.left);
        int r = dfsPost(root.right);
        int pl = 0;// 单独设置pl,pr是为了方便更新ans,不需要再次判断 节点是否为null
        int pr = 0;
        if (root.left != null && root.val == root.left.val) pl = l + 1;
        if (root.right != null && root.val == root.right.val) pr = r + 1;
        ans687 = Math.max(ans687, pl + pr);
        return Math.max(pl, pr); // 注意返回值,不是pl+pr哦 ,想想是为什么!
    }

    // ③ 543. 二叉树的直径
    // 第二种解法 为什么更快呢 好奇怪?
    public int res = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        dfsPostDiameter(root);
        return res;
    }

    public int dfsPostDiameter(TreeNode root) {
        if (root == null) return -1;
        int left = dfsPostDiameter(root.left);
        int right = dfsPostDiameter(root.right);
        res = Math.max(res, left + right + 2);
        return Math.max(left, right) + 1;
    }
    // 上文的方法 较好
   /* public int diameterOfBinaryTree0(TreeNode root) {
        if (root == null) return 0;
        int left = dfsHeight(root.left);
        int right = dfsHeight(root.right);
        int height = left + right;
        return left > right ? Math.max(diameterOfBinaryTree0(root.left), height) : Math.max(diameterOfBinaryTree0(root.right), height);
    }

    public int dfsHeight(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(dfsHeight(root.left), dfsHeight(root.right));
    }*/


    // 106 105 108  617 构建二叉树的本质在于找到根节点,然后构建根节点的左右子树
    //②  617. 合并二叉树
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return null;
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        TreeNode node = new TreeNode(t1.val + t2.val);
        node.right = mergeTrees(t1.right, t2.right);
        node.left = mergeTrees(t1.left, t2.left);
        return node;
    }

    // ②  108. 将有序数组转换为二叉搜索树 tips 考察的就是二分法
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        return bstHelper(nums, 0, nums.length - 1);
    }

    public TreeNode bstHelper(int[] nums, int left, int right) {
        if (left > right) return null;//忘记递归base条件！
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = bstHelper(nums, left, mid - 1);
        root.right = bstHelper(nums, mid + 1, right);
        return root;
    }

    // ③ 876
    public ListNode middleNode(ListNode head) {
        // 看之前错误的提交记录 就很有意思,fast取 head.next 还是head呢
        // 奇数 2n+1, 中点:n+1,偶数 如果fast取head,中点是n+1;取head.next,那么是n
        if (head == null) return head;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // ③  LC 109 这个方法最好,不需要断开节点!
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        return preDfs(head, null);
    }

    // head inclusive, tail exclusive
    public TreeNode preDfs(ListNode head, ListNode tail) {
        if (head == tail) { // 说明 递归条件终止了,返回Null。
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        TreeNode root = new TreeNode(slow.val);
        root.left = preDfs(head, slow);
        root.right = preDfs(slow.next, tail);
        return root;
    }

    // ② 105. 从前序与中序遍历序列构造二叉树
    // 所以构建二叉树的问题本质上就是：
    // tips:
    //找到各个子树的根节点 root
    //构建该根节点的左子树
    //构建该根节点的右子树
    public TreeNode buildTreePreIn(int[] preorder, int[] inorder) {
        return preBuildPreAndIn(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    //前序遍历方式,重建二叉树
    public TreeNode preBuildPreAndIn(int[] preOrder, int pLeft, int pRight, int[] inorder, int iLeft, int iRight) {
        if (pLeft > pRight || iLeft > iRight) {
            return null;
        }
        int i = 0;
        // 找到根root
        for (i = iLeft; i <= iRight; i++) {
            if (preOrder[pLeft] == inorder[i]) {
                break;
            }
        }
        TreeNode root = new TreeNode(preOrder[pLeft]);
        //  参考 http://bit.ly/2LoQpTz
        //  [pLeft+1 , pLeft+(i-iLeft)]是左子树元素区间哦
        root.left = preBuildPreAndIn(preOrder, pLeft + 1, pLeft + (i - iLeft), inorder, iLeft, i - 1);
        root.right = preBuildPreAndIn(preOrder, pLeft + i - iLeft + 1, pRight, inorder, i + 1, iRight);
        return root;
    }


    // ③ 106. 从中序与后序遍历序列构造二叉树
    public TreeNode buildTreeInPost(int[] inorder, int[] postorder) {
        return preBuildInAndPost(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    public TreeNode preBuildInAndPost(int[] inorder, int iLeft, int iRight, int[] postOrder, int pLeft, int pRight) {
        if (pLeft > pRight || iLeft > iRight) {
            return null;
        }
        int i = 0;
        for (i = iRight; i >= iLeft; i--) {
            if (postOrder[pRight] == inorder[i]) break;
        }
        TreeNode root = new TreeNode(postOrder[pRight]);
        root.left = preBuildInAndPost(inorder, iLeft, i - 1, postOrder, pLeft, pRight - (iRight - i) - 1);
        root.right = preBuildInAndPost(inorder, i + 1, iRight, postOrder, pRight - (iRight - i), pRight - 1);
        return root;
    }


    //③ 889. 根据前序和后序遍历构造二叉树(结果不唯一)
    public TreeNode constructFromPrePost01(int[] pre, int[] post) {
        return preBuildPrePost(pre, 0, pre.length - 1, post, 0, post.length - 1);
    }

    public TreeNode preBuildPrePost(int[] pre, int el, int er, int[] post, int pl, int pr) {
        // base
        if (el > er || pl > pr) return null;
        TreeNode root = new TreeNode(pre[el]);
        int idx = el + 1;
        // 这个就有点技巧在了,想想看为什么 之前的历史提交记录里面就有越界的问题存在.
        // https://leetcode-cn.com/submissions/detail/40560538/
        for (int i = el + 1; i <= er; i++) {
            if (pre[i] == post[pr - 1]) {
                idx = i;
            }
        }
        root.left = preBuildPrePost(pre, el + 1, idx - 1, post, pl, pl + idx - el - 2);
        root.right = preBuildPrePost(pre, idx, er, post, pl + idx - el - 1, pr - 1);
        return root;
    }

    // 参考 http://bit.ly/38VJzNJ
    int preIndex = 0, posIndex = 0;

    public TreeNode constructFromPrePost02(int[] pre, int[] post) {
        TreeNode root = new TreeNode(pre[preIndex++]);
        if (root.val != post[posIndex])
            root.left = constructFromPrePost02(pre, post);
        if (root.val != post[posIndex])
            root.right = constructFromPrePost02(pre, post);
        posIndex++;
        return root;
    }

    // 297. 二叉树的序列化与反序列化 这道题目就直接抄把 没啥好说的了 http://bit.ly/2LteuIY
    // Encodes a tree to a single string.
    public String serialize(TreeNode root, String str) {
        if (root == null) {
            str += "null,";
        } else {
            str += root.val + ",";
            str = serialize(root.left, str);
            str = serialize(root.right, str);
        }
        return str;
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return serialize(root, "[") + "]";
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(LinkedList<String> l) {
        if (l.get(0).equals("null")) {
            l.pollFirst();
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(l.pollFirst()));
        root.left = deserialize(l);
        root.right = deserialize(l);
        return root;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        // remove "["   "]"
        data = data.substring(1, data.length() - 1);

        String[] data_array = data.split(",");
        LinkedList<String> data_list = new LinkedList<String>(Arrays.asList(data_array));
        return deserialize(data_list);
    }

    // 打家劫舍系列

    // ③ 198. 打家劫舍  DP dp[i]=Math.max(dp[i-2]+nums[i],dp[i-1])
    public int rob1(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int len = nums.length;
        int[] dp = new int[len];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < len; i++) {// 可以采用滚动数组优化，找出dp[i] dp[i-1] dp[i-2] 每一次迭代变更关系即可
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[len - 1];
    }

    //  只用到memo[i] memo[i-1] 所以 用两个变量 去迭代更新即可
    // http://bit.ly/2SppaZW
    public int rob2(int[] nums) {
        // dp[i]=Max(dp[i-1],dpp[i-2]+nums[i]);
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int prev1 = nums[0];
        int prev2 = Math.max(nums[0], nums[1]);
        int ret = Math.max(prev2, prev1);
        // 滚动数组优化
        for (int i = 1; i < nums.length; i++) {
            ret = Math.max(prev2, prev1 + nums[i]);
            prev1 = prev2;
            prev2 = ret;
        }
        return ret;
    }

    // ③ 213. 打家劫舍 II tag:环形数组
    public int rob3(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        return Math.max(robHelper(nums, 0, nums.length - 1), robHelper(nums, 1, nums.length));
    }

    // index: left inclusive , right exclusive
    public int robHelper(int[] nums, int left, int right) {
        int len = right - left;
        if (len <= 1) return nums[left];
        int[] dp = new int[len];
        dp[0] = nums[left];
        dp[1] = Math.max(nums[left], nums[left + 1]);
        for (int i = 2; i < len; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i + left], dp[i - 1]);
        }
        return dp[len - 1];
    }


    // ③ 337. 打家劫舍 III  看懂比较模型即可 http://bit.ly/2LtppCe
    // dfs 做比较下即可.
    public int rob4(TreeNode root) {
        Map<TreeNode, Integer> map = new HashMap<>();
        return dfsRobHelper(root, map);
    }

    public int dfsRobHelper(TreeNode root, Map<TreeNode, Integer> map) {
        if (root == null) return 0;
        if (map.containsKey(root)) return map.get(root);
        int val = 0;
        if (root.left != null) {
            val += dfsRobHelper(root.left.left, map) + dfsRobHelper(root.left.right, map);
        }
        if (root.right != null) {
            val += dfsRobHelper(root.right.left, map) + dfsRobHelper(root.right.right, map);
        }
        val = Math.max(val + root.val, dfsRobHelper(root.left, map) + dfsRobHelper(root.right, map));
        map.put(root, val);
        return val;
    }

    // ② 647 字符串回文个数 dp[i,j]  = dp[i+1][j-1] if s[i]=s[j],   0<=i<=j<N;
    public int countSubstrings(String s) {
        int N = s.length();
        int count = 0;
        boolean[][] dp = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            dp[i][i] = true;
        }
        for (int i = N - 1; i >= 0; i--) {// 注意遍历方式  i-- ，j++
            for (int j = i; j < N; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1]);
                if (dp[i][j]) count++;
            }
        }
        return count;
    }

    // ③ 160. 相交链表
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode a = headA;
        ListNode b = headB;
        while (a != b) {
            a = a != null ? a.next : headB;
            b = b != null ? b.next : headA;
        }
        return a;
    }

    //③ 461. 汉明距离
    public int hammingDistance(int x, int y) {
        int xor = x ^ y, count = 0;
        for (int i = 0; i < 32; i++) count += (xor >> i) & 1;
        return count;
    }

    // ③  494 DFS 参加 pack.java下的dp 解答。
    public int findTargetSumWays(int[] nums, int S) {
        return dfsTargetSumHelper(nums, S, 0, res);
    }

    public int dfsTargetSumHelper(int[] nums, int S, int start, int res) {
        if (start >= nums.length) {
            if (S == 0) res++;
            return res;
        }
        return dfsTargetSumHelper(nums, S - nums[start], start + 1, res) + dfsTargetSumHelper(nums, S + nums[start], start + 1, res);
    }

    // 406. 根据身高重建队列
    public int[][] reconstructQueue0(int[][] people) {
        Arrays.sort(people, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0]);
        List<int[]> res = new LinkedList<>();
        for (int[] person : people) {
            // tips: add的用法 是第二次重复插入的时候 需要右移 shift current element to right.
            res.add(person[1], person);
        }
        return res.toArray(new int[res.size()][]);
    }

    // ② 200. 岛屿数量
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '0' || visited[i][j]) {
                    continue;
                }
                dfsIslandHelper(grid, visited, i, j);
                count++;//key
            }
        }
        return count;
    }

    public void dfsIslandHelper(char[][] grid, boolean[][] visit, int x, int y) {
        // 别忘记transfer的时候 对grid[x][y] == '0' 判断哦
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[x].length || grid[x][y] == '0' || visit[x][y]) return;
        visit[x][y] = true;
        dfsIslandHelper(grid, visit, x + 1, y);
        dfsIslandHelper(grid, visit, x - 1, y);
        dfsIslandHelper(grid, visit, x, y + 1);
        dfsIslandHelper(grid, visit, x, y - 1);
    }

    // ③ 44. 通配符匹配  思路和LC  10 差不多
    // 解法2 dp来做  解题思路参考这个做法 http://bit.ly/2OPPKf0
    // dp[i][j]=dp[i-1][j-1] s[i-1]=p[j-1] || p[j-1]=?   i∈[0,M]  j∈[0,N];
    // dp[i][j]=dp[i][j-1] || dp[i-1][j]  p[j-1]="*";
    public boolean isMatch1(String s, String p) {
        int M = s.length(), N = p.length();
        boolean[][] dp = new boolean[M + 1][N + 1];
        dp[0][0] = true;

        // 2处DP  此处DP ,补全下面DP不足的问题
        for (int j = 1; j <= N; j++) {// 处理 * 可以代表空串的问题
            dp[0][j] = dp[0][j - 1] && p.charAt(j - 1) == '*';
        }

        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?');
                }
            }
        }
        return dp[M][N];
    }

    // 👎🏻  interview
   /* // 参考这篇文章 匹配优先向下原则(说的不是通配哦!)
    // 本质上是在构建NFA  http://bit.ly/2LyOYSq 但是理解这方面 你需要有深刻的理解 不适合面试哈!
    public boolean isMatch0(String s, String p) {
        char[] S = s.toCharArray(), P = p.toCharArray();
        int i = 0, j = 0, sStar = -1, pStar = -1;
        while (i < s.length()) {
            if (j < p.length() && (S[i] == P[j] || P[j] == '?')) { //如果匹配，两指针同时后移
                i++;
                j++;
            } else if (j < p.length() && P[j] == '*') { //如果不匹配但j指向'*'，那么记录此时j的位置以构建回路，同时记录i的位置以标记i此时可以后移却停留在此一次，同时j后移
                pStar = j;
                j = j + 1;
                sStar = i;
            } else if (sStar >= 0) { //仍然不匹配，但是i有路可走，且i已经停在那一次了，那么i要后移，连同i停留的位置也要更新，j直接到回路'*'的后一个位置。此时j也可以取pStar，但运行速度会变慢
                j = pStar + 1;
                i = ++sStar;
            } else return false; //仍然不匹配，i与j均已无路可走，返回false
        }
        while (j < p.length() && P[j] == '*') j++; //i扫描完成后要看j能不能够到达终点，即j可以沿着'*'行程的通路一直向下
        return j == p.length(); //i与j同时到达终点完成匹配
    }
*/

    // ③ LC 10 正则表达式匹配 http://bit.ly/2SsG9dA  这个状态转移方程不够优雅!!,还是第一个好点
    // 1. dp[i][j] = dp[i-1][j-1] when s[i-1]==p[j-1] or p[j-1] = " . ";
    // 2. dp[i][j] = dp[i][j-2] if p[j-1]="*" repeat 0 times. 表示长度缩短了 这个转台转移优先
    // 3. dp[i][j] = dp[i-1][j] if p[j-1]="*" & s[i-1]=p[j-2],p[j-2]="." repeat at least 1 times,
    // dp[0][j] always false;
    // dp[i][0] always false except for dp[0][0] =true;
    public boolean isMatch2(String s, String p) {
        int M = s.length(), N = p.length();
        boolean[][] dp = new boolean[M + 1][N + 1];
        dp[0][0] = true;
        for (int i = 0; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = (j > 1 && dp[i][j - 2]) || (i > 0 && j > 1 && dp[i - 1][j] && (p.charAt(j - 2) == s.charAt(i - 1) || p.charAt(j - 2) == '.'));
                } else {
                    dp[i][j] = (i > 0) && dp[i - 1][j - 1] && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.');
                }
            }
        }
        return dp[M][N];
    }


    // ③ 394. 字符串解码 用栈存储结果.int 和 string stack 分别暂时存储结果.sb 负责append.
    //  字符+数字+[+字母+] 的 模型 http://bit.ly/2qdR2WP
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        int multi = 0;
        Stack<Integer> ints = new Stack();
        Stack<String> strs = new Stack();
        for (char c : s.toCharArray()) {
            if (c >= '0' && c <= '9') {
                multi = multi * 10 + c - '0';
            } else if (c == '[') {
                ints.push(multi);
                strs.push(sb.toString());
                sb = new StringBuilder();
                multi = 0;
            } else if (c == ']') {
                StringBuilder tmp = new StringBuilder();
                int cur_multi = ints.pop();
                for (int i = 0; i < cur_multi; i++) {
                    tmp.append(sb);
                }
                sb = new StringBuilder();
                //  这里要对内层的进行合并!!
                sb.append(strs.pop()).append(tmp);
            } else {
                sb.append(c);
            }

        }
        return sb.toString();
    }

    // ② 72. 编辑距离 DP的递归做法
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] memo = new int[m][n];
        return dfsMinDistanceHelper(word1, 0, word2, 0, memo);
    }

    public int dfsMinDistanceHelper(String word1, int i, String word2, int j, int[][] memo) {
        if (j == word2.length()) return word1.length() - i;
        if (i == word1.length()) return word2.length() - j;
        if (memo[i][j] > 0) return memo[i][j];
        int res;
        if (word1.charAt(i) == word2.charAt(j)) {
            return dfsMinDistanceHelper(word1, i + 1, word2, j + 1, memo);
        } else {
            int insertCnt = dfsMinDistanceHelper(word1, i, word2, j + 1, memo);
            int deleteCnt = dfsMinDistanceHelper(word1, i + 1, word2, j, memo);
            int replaceCnt = dfsMinDistanceHelper(word1, i + 1, word2, j + 1, memo);
            res = Math.min(insertCnt, Math.min(deleteCnt, replaceCnt)) + 1;
        }
        return memo[i][j] = res;
    }

    // 解法2 DP的迭代做法 参考这个连接 http://bit.ly/2SyePLi
    //   dp[i][j] 表示从 word1 的前i个字符转换到 word2 的前j个字符所需要的步骤
    public int minDistance1(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        // init
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int i = 0; i <= n; i++) dp[0][i] = i;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 替换 插入 删除 操作
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }
        return dp[m][n];
    }

    // http://bit.ly/2LvcJLu
    // ② 438. 找到字符串中所有字母异位词 双指针
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int[] letters = new int[26];
        for (char c : p.toCharArray()) {
            letters[c - 'a']++;
        }
        int i = 0;
        while (i < s.length()) {
            boolean match = true;
            int[] cnt = Arrays.copyOf(letters, letters.length);
            // 忘记add j < s.length()条件了
            for (int j = i; j < p.length() + i; j++) {
                if (j >= s.length() || --cnt[s.charAt(j) - 'a'] < 0) {
                    match = false;
                    break;
                }
            }
            if (match) {
                res.add(i);
            }
            i++;
        }
        return res;
    }

    // ② 解法2 滑动窗口   观察 p 频数的自增自减
    public List<Integer> findAnagrams1(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (s.isEmpty()) return res;
        int[] m = new int[26];
        int left = 0, right = 0, cnt = p.length(), n = s.length();
        for (char c : p.toCharArray()) {
            m[c - 'a']++;
        }
        while (right < n) {
            if (m[s.charAt(right++) - 'a']-- >= 1) --cnt;
            if (cnt == 0) res.add(left);
            if (right - left == p.length() && m[s.charAt(left++) - 'a']++ >= 0) ++cnt;
        }
        return res;
    }

    //② 49. 字母异位词分组  key就是异位词排序之后同一个key,然后map归类即可
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> m = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = Arrays.toString(chars);
            List<String> value = m.getOrDefault(key, new ArrayList<>());
            value.add(str);
            m.put(key, value);
        }
        return new ArrayList<>(m.values());
    }

    // 解法2 和解1 没什么区别 说真的,本质就是维护异位词key的唯一性
    public List<List<String>> groupAnagrams1(String[] strs) {
        Map<String, List<String>> m = new HashMap<>();
        for (String str : strs) {
            int[] cnt = new int[26];
            for (char c : str.toCharArray()) {
                cnt[c - 'a']++;
            }
            String key = "";
            for (int i : cnt) {
                key += i + "/";
            }
            List<String> value = m.getOrDefault(key, new ArrayList<>());
            value.add(str);
            m.put(key, value);
        }
        return new ArrayList<>(m.values());
    }


    // 621. 任务调度器  参考: http://bit.ly/2MdJkUu
    /*public int leastInterval01(char[] tasks, int n) {
        int[] cnt = new int[26];
        for (char c : tasks) {
            cnt[c - 'A']++;
        }
        Arrays.sort(cnt);
        int i = 25;
        // 最高频次
        while (i >= 0 && cnt[i] == cnt[25]) i--;
        // if n =0; 取task.length
        return Math.max(tasks.length, (cnt[25] - 1) * (n + 1) + 25 - i);
    }
*/
    // round-robin 算法 BFS
    public int leastInterval02(char[] tasks, int n) {
        Map<Character, Integer> counts = new HashMap();
        for (char t : tasks) {
            counts.put(t, counts.getOrDefault(t, 0) + 1);
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        maxHeap.addAll(counts.values());
        int alltime = 0;
        int cycleLen = n + 1;
        while (!maxHeap.isEmpty()) {
            int roundTime = 0;
            List<Integer> round = new ArrayList<>();
            for (int i = 0; i < cycleLen; i++) {
                if (!maxHeap.isEmpty()) {
                    // 取n个出来
                    round.add(maxHeap.poll());
                    roundTime++;
                }
            }
            for (int freq : round) {
                if (--freq > 0) {// 放回去
                    maxHeap.offer(freq);
                }
            }
            alltime += maxHeap.isEmpty() ? roundTime : cycleLen;
        }
        return alltime;
    }


    // ③ 252. 会议室 排个序,比较相邻的位置大小即可.
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1])
                return false;
        }
        return true;
    }

    // 253. 会议室 II
    //③ interview friendly 参考这个视频 https://youtu.be/wB884_Os58U  主要是人脑怎么处理的问题
    // 如何复用最先空出来的房间
    public int minMeetingRooms1(int[][] intervals) {
        if (intervals.length == 0) return 0;
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        // init
        pq.add(intervals[0][1]);
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= pq.peek()) {
                pq.poll();
            }
            pq.add(intervals[i][1]);
        }
        return pq.size();
    }

    //③  忘记没看懂
    public int minMeetingRooms0(int[][] intervals) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int[] interval : intervals) {
            map.put(interval[0], map.getOrDefault(interval[0], 0) + 1);
            map.put(interval[1], map.getOrDefault(interval[1], 0) - 1);
        }
        int rooms = 0;
        int res = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            // 求前缀和的最大值
            res = Math.max(res, rooms += entry.getValue());
        }
        return res;
    }

    // ② 347. 前 K 个高频元素
    public List<Integer> topKFrequent1(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            cnt.put(num, cnt.getOrDefault(num, 0) + 1);
        }
        // 最小堆
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>((o1, o2) -> o1.getValue() - o2.getValue());
        for (Map.Entry<Integer, Integer> item : cnt.entrySet()) {
            minHeap.offer(item);
            // 剔除 最小k
            if (minHeap.size() > k) minHeap.poll();
        }

        List<Integer> res = new LinkedList<>();
        while (!minHeap.isEmpty()) {
            Map.Entry<Integer, Integer> item = minHeap.poll();
            res.add(0, item.getKey());//注意下tips
        }
        return res;
    }

    // quick sort
    public List<Integer> topKFrequent2(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap();
        for (int i : nums) {
            cnt.put(i, cnt.getOrDefault(i, 0) + 1);
        }
        Set<Integer> set = cnt.keySet();
        int[] arr = new int[set.size()];
        int i = 0;
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            arr[i++] = iterator.next();
        }
        int l = 0, r = set.size() - 1;
        while (true) {
            int pos = partition(arr, cnt, l, r);
            if (pos > k - 1) r = pos - 1;
            if (pos < k - 1) l = pos + 1;
            if (pos == k - 1) break;
        }
        List<Integer> ret = new ArrayList<>();
        for (int j = 0; j < k; j++) {
            ret.add(arr[j]);
        }
        return ret;

    }

    public int partition(int[] arr, Map<Integer, Integer> cnt, int l, int r) {
        int pivot = cnt.get(arr[l]);
        int lo = l + 1, hi = r;
        while (lo <= hi) {
            if (cnt.get(arr[lo]) < pivot && pivot < cnt.get(arr[hi])) {
                swap(arr, lo++, hi--);
            }
            if (cnt.get(arr[lo]) >= pivot) lo++;
            if (cnt.get(arr[hi]) <= pivot) hi--;
        }
        swap(arr, l, hi);
        return hi;
    }

    //② 279. 完全平方数 http://bit.ly/2Yl0Xt2 四平方和定理 但是 不太推荐
    // 这道题目 意义不强阿
    // dp[n+1]  dp[0]=0;
    // dp[i+j*j]=Math.min(dp[i]+1,dp[i+j*j]),i ∈[0,n],j∈[1,n]
    public int numSquares2(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 0; i <= n; i++) {
            for (int j = 1; i + j * j <= n; j++) {
                int idx = i + j * j;
                dp[idx] = Math.min(dp[idx], dp[i] + 1);
            }
        }
        return dp[n];
    }

    // ② 32. 最长有效括号
    // dp解决 存在2种状态转移方程式哦!! 参考官方题解http://bit.ly/2VvELbE
    public int longestValidParentheses01(String str) {
        int ans = 0;
        int[] dp = new int[str.length()];
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == ')') {
                if (str.charAt(i - 1) == '(') {
                    dp[i] = (i - 2 >= 0 ? dp[i - 2] : 0) + 2;
                }
                if ((i - dp[i - 1] - 1 >= 0) && str.charAt(i - dp[i - 1] - 1) == '(') {
                    // 就是这个状态转移方程比较难推到了...
                    // 分为2部分 dp[i]= dp[i-1]+2+还有要加上dp[i-dp[i-1]-2]这部分。
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public int longestValidParentheses02(String str) {
        int ans = 0;
        Stack<Integer> s = new Stack<>();
        s.push(-1);//  特殊case "()"
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                s.push(i);
            } else {
                s.pop();
                if (s.isEmpty()) { // 特殊case ")()"
                    s.push(i);
                } else ans = Math.max(ans, i - s.peek());
            }
        }
        return ans;
    }

    // 7. 整数反转  ③ 注意下边界条件即可
    public int reverse(int x) {
        int res = 0;
        while (x != 0) {
            if (Math.abs(res) > Integer.MAX_VALUE / 10) return 0;
            res = res * 10 + x % 10;
            x = x / 10;
        }
        return res;
    }

    //③ 88. 合并两个有序数组
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i1 = m - 1;
        int i2 = n - 1;
        int k = m + n - 1;
        while (i1 >= 0 && i2 >= 0) {
            if (nums1[i1] > nums2[i2]) {
                nums1[k--] = nums1[i1--];
            } else {
                nums1[k--] = nums2[i2--];
            }
        }
        while (i2 >= 0) nums1[k--] = nums1[i2--];
    }


    // ③ 237. 删除链表中的节点
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        ListNode tmp = node.next;
        node.next = node.next.next;
        tmp.next = null;
    }

    // ③ 326. 3的幂
    public boolean isPowerOfThree0(int n) {
        // 1162261467 is 3^19,  3^20 is bigger than int
        // int x = 1162261467;
        int x = maxPowerUnderN(Integer.MAX_VALUE, 3);
        return (n > 0 && x % n == 0);
    }

    public int maxPowerUnderN(int n, int expBase) {
        int ans = 1;
        while (ans < n / expBase) {
            ans = ans * expBase;
        }
        return ans;
    }

    public boolean isPowerOfThree1(int n) {
        while (n % 3 == 0 && n >= 3) {
            n = n / 3;
        }
        return n == 1;
    }

    // 338. 比特位计数
    // http://bit.ly/2SNB67J  观察下数字规律
    public int[] countBits(int num) {
        int[] res = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            res[i] = res[i & (i - 1)] + 1;
        }
        return res;
    }

    // 171. Excel表列序号  进制转换问题
    public int titleToNumber(String s) {
        int i = 0;
        int res = 0;
        while (i < s.length() && s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
            res = res * 26 + s.charAt(i) - 'A' + 1;
            i++;
        }
        return res;
    }

    // 12 数字转罗马
    public String intToRoman01(int num) {
        Map<Integer, String> dict = new HashMap();
        dict.put(1000, "M");
        dict.put(500, "D");
        dict.put(100, "C");
        dict.put(50, "L");
        dict.put(10, "X");
        dict.put(5, "V");
        dict.put(1, "I");
        StringBuilder ans = new StringBuilder();
        int[] ints = new int[]{1000, 500, 100, 50, 10, 5, 1};
        while (num != 0) {
            for (int i = 0; i < ints.length; i++) {
                int mod = ints[i];
                int a = num / mod;
                int b = num % mod;
                if (num >= 900 && mod == 500) {
                    ans.append("CM");
                    num -= 900;
                } else if (num >= 400 && mod == 100) {
                    ans.append("CD");
                    num -= 400;
                } else if (num >= 90 && mod == 50) {
                    ans.append("XC");
                    num -= 90;
                } else if (num >= 40 && mod == 10) {
                    ans.append("XL");
                    num -= 40;
                } else if (num >= 9 && mod == 5) {
                    ans.append("IX");
                    num -= 9;
                } else if (num >= 4 && mod == 1) {
                    ans.append("IV");
                    num -= 4;
                } else if (a != 0) {
                    num = b;
                    while (a-- > 0) {
                        ans.append(dict.get(mod));
                    }
                }
            }
        }
        return ans.toString();

    }

    // 解法2 http://bit.ly/2FxS6sN
    public String intToRoman02(int num) {
        StringBuilder ans = new StringBuilder();
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arab = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        int index = 0;
        while (num != 0) {
            int count = num / arab[index];
            while (count-- > 0) {
                ans.append(roman[index]);
            }
            num = num % arab[index];
            index++;
        }
        return ans.toString();
    }


    //② 13. 罗马数字转整数
    public int romanToInt(String s) {
        Map<Character, Integer> dict = new HashMap<>();
        dict.put('I', 1);
        dict.put('V', 5);
        dict.put('X', 10);
        dict.put('L', 50);
        dict.put('C', 100);
        dict.put('D', 500);
        dict.put('M', 1000);

        int res = dict.get(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int cur = dict.get(s.charAt(i));
            int before = dict.get(s.charAt(i - 1));
            if (cur <= before) {
                res += cur;
            } else {
                // 减去之前增加的
                res += cur - 2 * before;
            }
        }
        return res;
    }

    //② 118. 杨辉三角
    public List<List<Integer>> generate(int numRows) {
        List<Integer>[] ret = new List[numRows];
        for (int i = 0; i < numRows; i++) {
            // i+1
            List sub = new ArrayList();
            for (int j = 0; j < i + 1; j++) {
                if (j == 0 || j == i) {
                    sub.add(1);
                } else {
                    sub.add(ret[i - 1].get(j) + ret[i - 1].get(j - 1));
                }
            }
            ret[i] = sub;
        }
        return Arrays.asList(ret);
    }

    //② 202 快乐数 解题关键 set 记录出现过的数字
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        int ret = n;
        while (true) {
            ret = square(ret);
            if (ret == 1) return true;
            if (!set.contains(ret)) set.add(ret);// 不要出现循环，出现循环了就不是快乐数了
            else return false;
        }
    }


    public int square(int n) {
        int res = 0;
        while (n != 0) {
            int modRet = n % 10;
            res += modRet * modRet;
            n = n / 10;
        }
        return res;
    }

    //解法2 快慢指针方法 if存在环
    public boolean isHappy1(int n) {
        int slow = n, fast = n;
        while (true) {
            slow = square(slow);
            fast = square(square(fast));
            if (slow == 1) return true;
            if (slow == fast) return false;
        }

    }

    //② 371. 两整数之和  [和的位运算实现] http://bit.ly/2MpSWN0  求和运算 sum=2*C(进位)+S(本位), 李永乐老师 本位 进位 https://youtu.be/pUwYvJtfbsY
    public int getSum(int a, int b) {
        if (b == 0) return a;
        // 本位
        int sum = a ^ b;
        // 进位
        int carry = (a & b) << 1;
        return getSum(sum, carry);
    }

    // 两数想减
    public int substract(int a, int b) {
        return getSum(a, -b);
    }

    // 两数相乘
    public int multiple(int a, int b) {
        int m = Math.abs(a);
        int n = Math.abs(b);
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = getSum(m, res);
        }
        return ((a > 0) ^ (b > 0)) ? -res : res;

    }

    // ③ 29. 两数相除 这个放弃 太难了
    // ③ dividend 被除数， divisor 除数
    public int divide01(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE; //corner case,防止 ans 溢出
        int sign = (dividend < 0) ^ (divisor < 0) ? -1 : 1;
        long x = Math.abs(((long) dividend));
        long y = Math.abs(((long) divisor));
        int ans = 0;//result越界问题
        while (x >= y) {
            x -= y;
            ans++;
        }
        return ans * sign;
    }

    // ③ 解法2 二进制表示， a = b *2的0次+ b*2的1次+。。。+b*2的n次。  O(lg(dividend))
    public int divide02(int dividend, int divisor) {
        if (dividend == 1 << 31 && divisor == -1) return (1 << 31) - 1;
        int a = Math.abs(dividend), b = Math.abs(divisor), ans = 0;
        while (a - b >= 0) {
            int x = 0;
            // <<1 是为了 直接获取x的值，而不用取 x-1
            while (a - ((b << x) << 1) >= 0) {
                x++;
            }
            ans = ans + (1 << x);
            a = a - (b << x);
        }
        int sign = (dividend > 0) ^ (divisor > 0) ? -1 : 1;
        return ans * sign;
    }


    // ② 204 质数计数
    // 质数是指在大于1的自然数中，除了1和它本身以外不再有其他因数的自然数
    // 2是最小的质数,质数乘以任何数的积 就不是质数
    public int countPrimes(int n) {
        // 非质数数组
        boolean[] dp = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!dp[i]) {
                count++;
                for (int j = 2; j * i < n; j++) {
                    dp[i * j] = true;
                }
            }
        }
        return count;
    }

    // ③ 14 最长公共前缀， 第一个作为公共前缀，不断减小if  others.indexOf（prefix） ！= 0
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix == "") return "";
            }
        }
        return prefix;
    }


    // ③ 69 x 平方根 利用牛顿求根法来做[http://bit.ly/2ypO02m] 牛顿求根法视频讲解 https://youtu.be/VUpQwEVsyFk
    // f(x1)-f(x2) / x1-x2 = f'(x1) 令 f(x2)=0 即可求出
    //  f(x1)-f(x2)  / x1-x2 = f'(x2) , 令 f(x1) =  0;
    public int mySqrt(int x) {
        if (x <= 1) return x;
        double last = 0;
        double res = 1;
        while (Math.abs(res - last) > 1e-9) {
            last = res;
            res = (res + x / res) / 2;

        }
        return (int) res;
    }

    // ③  解法2 二分法求 lg(x)
    public int mySqrt1(int x) {
        // 无法处理x= Integer.MAX_VALUE;
        long l = 1;
        long r = x + 1;
        while (l < r) {
            long mid = l + (r - l) / 2;
            if (mid > x / mid) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return (int) (l - 1);
    }

    // follow up  立方根  牛顿求根法解决
    public int cubeRoot(int x) {
        if (x <= 1) return x;
        double last = 0;
        double res = 1;
        while (Math.abs(res - last) > 1e-9) {
            last = res;
            res = (res * 2 + x / (res * res)) / 3;
        }
        return (int) res;
    }

    // ② 28. 实现strStr()
    // 遍历每一个字符作为子字符串  字符串长度计算 左闭右开 不熟悉
    public int strStr1(String haystack, String needle) {
        if (needle == "") return 0;
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            if (haystack.substring(i, i + needle.length()).equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    // 错误哎
    public int strStr_wrong(String haystack, String needle) {
        int i = 0;
        int j = 0;
        while (i < haystack.length() && j < needle.length()) {
            if (haystack.charAt(i++) != needle.charAt(j++)) {
                j = 0;
            }
        }
        return j == needle.length() ? i - j : -1;
    }

    // ② 172. 阶乘后的零  tips:连续5的前缀后数列 O(N/5)
    public int trailingZeroes(int n) {
        int count = 0;
        while (n != 0) {
            count += n / 5; // 算出 5倍数的个数了
            n = n / 5;
        }
        return count;
    }

    // ③ 26. 删除排序数组中的重复项
    public int removeDuplicates(int[] nums) {
        int i = 0; // i作为非重复元素的坐标。
        int j = 0;// j作为检查员
        while (j < nums.length)
            if (nums[i] == nums[j]) {
                j++;
            } else {
                nums[++i] = nums[j];
            }
        // 返回新数组长度
        return i + 1;
    }

    // 重复元素系列 217 219 220
    // ② 217. 存在重复元素
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) return true;
            else set.add(num);
        }
        return false;
    }

    //② 191. 位1的个数
    public int hammingWeight(int n) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            count += n & 1;
            n >>= 1;
        }
        return count;
    }


    // 66. 加一
    public int[] plusOne0(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] == 9) {
                digits[i] = 0;
            }// 少了一个else  错误,容易犯的错误
            digits[i] = digits[i] + 1;
            return digits;
        }
        int[] ret = new int[digits.length + 1];
        ret[0] = 1;
        return ret;
    }

    public int[] plusOne1(int[] digits) {
        for (int i = digits.length - 1; i >= 0; --i) {
            if (digits[i] < 9) {
                digits[i] = digits[i] + 1;
                return digits;
            }
            digits[i] = 0;
        }
        int[] res = new int[digits.length + 1];
        res[0] = 1;
        return res;
    }


    // ② 190. 颠倒二进制位
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int res = 0;
        for (int i = 0; i < 32; ++i) {
            // 注意下运算符的优先级
            // 为什么要用位运算而不是 ans =ans*2+n%2;n=n/2;
            // 因为n%2，n=-3 负数时，n%2 = -1 不是我们想要的1
            res = (res << 1) | 1;
            n = n >> 1;
        }
        return res;
    }

    // ② 125 验证回文字符串
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (!isAlphaNum(s.charAt(left))) {
                left++;
            } else if (!isAlphaNum(s.charAt(right))) {
                right--;
            } // 别忘记加else 因为 下面的和上上面的是并行关系
            else if ((s.charAt(left) - 'A') % 32 == (s.charAt(right) - 'A') % 32) {
                right--;
                left++;
            } else {
                return false;
            }
        }
        return true;
    }


    // ② 242. 有效的字母异位词
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] m = new int[26];
        for (char c : s.toCharArray()) {
            m[c - 'a']++;
        }
        for (char c : t.toCharArray()) {
            if (--m[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }


    public boolean isAlphaNum(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    //② 268 缺失数字
    //解法1  ② 异或 结合律 http://bit.ly/2Kkra1B  这个最好了
    public int missingNumber1(int[] nums) {
        int miss = nums.length;
        for (int i = nums.length - 1; i >= 0; i--) {
            miss ^= i ^ nums[i];
        }
        return miss;
    }

    // ② 解法2
    public int missingNumber2(int[] nums) {
        Arrays.sort(nums);
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > mid) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    // follow up  一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字？
    public void findTwoSingleNum(int[] nums) {
        int AXORB = 0;
        for (int i : nums) {
            AXORB ^= i;
        }
        assert AXORB != 0;
        assert AXORB == (3 ^ 7);
        int firstOneBit = findFirstOneBit(AXORB);
        System.out.println(AXORB + " " + firstOneBit);
        int a = 0;
        int b = 0;
        for (int i : nums) {
            if ((i & firstOneBit) == firstOneBit) {
                a ^= i;
            }
        }
        b = AXORB ^ a;
        System.out.println("a:" + a + "\tb:" + b);
    }

    public int findFirstOneBit(int num) {
        return num & ~(num - 1);
    }

    // ②  137. 只出现一次的数字 II
    // 解法1 重新定义运算规则
    public int singleNumberⅡ0(int[] nums) {
        int ones = 0, twos = 0, threes = 0;
        for (int num : nums) {
            twos |= ones & num;
            ones ^= num;
            threes = ones & twos;
            ones &= ~threes;
            twos &= ~threes;
        }
        return ones;
    }

    // 异或的妙用 http://lijinma.com/blog/2014/05/29/amazing-xor/
    //  彻底理解 https://www.cnblogs.com/bjwu/p/9323808.html
    //  关键点:根据真值表,写逻辑表达式 从二进制推导出三进制
    //
//    current(a, b)	incoming(c)	       next(a, b)
//        0, 0	       0	              0, 0
//        0, 1	       0	              0, 1
//        1, 0	       0	              1, 0
//        0, 0	       1	              0, 1
//        0, 1	       1	              1, 0
//        1, 0	       1	              0, 0

    // (其实不难：对于a，把next中a=1对应的行组合选出来，
    // 对于每一个组合，凡取值为1的变量写成原变量，取值为0的变量写成反变量，
    // 各变量相乘后得到一个乘积项；最后，把各个组合对应的乘积项相加，就得到了相应的逻辑表达式。对于b同理)
    //✅  ②  最佳实践  interview friendlY
    public int singleNumberⅡ1(int[] nums) {
        int A = 0;
        int B = 0;
        for (int c : nums) {
            int tmp = (A & ~B & ~c) | (~A & B & c);
            B = (~A & B & ~c) | (~A & ~B & c);
            A = tmp;
        }
        return A | B;
    }

    // 解法2 有点玄学了 但是复杂度O(N²) 还是真值表来得易懂
    public int singleNumberⅡ2(int[] nums) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            int sum = 0;
            for (int j = 0; j < nums.length; j++) {
                sum += (nums[j] >> i) & 1;
            }
            res = res | ((sum % 3) << i);
        }

        return res;
    }

    //② 387  字符串中的第一个唯一字符 // 一次遍历构建cnt,另一次遍历查找count=1即可
    public int firstUniqChar(String s) {
        Map<Character, Integer> dict = new HashMap<>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            dict.put(c, dict.getOrDefault(c, 0) + 1);
        }
        for (int i = 0; i < chars.length; i++) {
            if (dict.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }

    public int firstUniqChar1(String s) {
        int index = -1;
        //反过来，只有26个字符
        for (char ch = 'a'; ch <= 'z'; ch++) {
            int beginIndex = s.indexOf(ch);
            // 从头开始的位置是否等于结束位置，相等说明只有一个，
            if (beginIndex != -1 && beginIndex == s.lastIndexOf(ch)) {
                //取小的，越小代表越前。
                index = (index == -1 || index > beginIndex) ? beginIndex : index;
            }
        }
        return index;
    }

    // ③ 189. 旋转数组
    public void rotate(int[] nums, int k) {
        int[] tmp = Arrays.copyOf(nums, nums.length);
        for (int i = 0; i < tmp.length; i++) {
            nums[i] = tmp[(i + k) % nums.length];
        }
    }

    public void rotate1(int[] nums, int k) {
        for (int i = 0; i < k; i++) {
            shift(nums);
        }
    }

    public void shift(int[] nums) {
        int prev = nums[nums.length - 1];
        int tmp;
        for (int i = 0; i < nums.length; i++) {
            tmp = nums[i];
            nums[i] = prev;
            prev = tmp;
        }
    }

    // 👎🏻 来自 http://bit.ly/2KkELWH  ,并不推荐 说真的 太难看了
    public void rotate2(int[] nums, int k) {
        if (nums == null || (k %= nums.length) == 0) return;
        int start = 0, idx = 0, pre = 0, cur = nums[0], n = nums.length;
        for (int i = 0; i < n; ++i) {
            pre = cur;
            idx = (idx + k) % n;
            cur = nums[idx];
            nums[idx] = pre;
            if (idx == start) {
                idx = ++start;
                cur = nums[idx];
            }
        }
    }

    // 参考答案: http://bit.ly/332rMSi
    // 核心思想就是从o开始  交换k次 然后在进行下一轮k次交换 [经典]
    public void rotate3(int[] nums, int k) {
        if (nums.length == 0) return;
        int n = nums.length, start = 0;
        while (n != 0 && (k %= n) != 0) {
            for (int i = 0; i < k; ++i) {
                swap(nums, i + start, n - k + i + start);
            }
            n -= k;
            start += k;
        }

    }

    // ③ ✅ 买卖股票系列问题 121 122 123  188 参考 http://bit.ly/333JDIm
    //  dp[N][k+1][2]
    // dp[i][k][0]=Math.max(dp[i-1][k][0],dp[i-1][k][1]+prices[i]);

    // dp[i][k][1]=Math.max(dp[i-1][k][1],dp[i-1][k-1][0]-prices[i]);
    // i ∈ [0,N), k ∈ [0，K]

    // base case ,i = 0;
    //  dp[0][k][0]=Math.max(dp[-1][k][0],dp[-1][k][1]+prices[0]);
    //  dp[0][k][1]=Math.max(dp[-1][k][1],dp[-1][k-1][0]-prices[0]);
    // base case ,k = 0;
    //  dp[i][0][0]=Math.max(dp[i-1][0][0],dp[i-1][0][1]+prices[i]);
    //  dp[i][0][1]=Math.max(dp[i-1][0][1],dp[i-1][-1][0]-prices[i])


    // 121. 买卖股票的最佳时机
    // dp[i][0] = 0;
    // 解释：
    //   dp[i][0] = max(dp[-1][0], dp[-1][1] + prices[i]) = max(0, -infinity + prices[i]) = 0
    //  dp[i][1] = -prices[i];
    //解释：
    //   dp[i][1] = max(dp[-1][1], dp[-1][0] - prices[i]) = max(-infinity, 0 - prices[i])
    // = -prices[i]

    public int maxProfit121A(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        // int k = 1;
        int n = prices.length;
        int[][] dp = new int[prices.length][2];

        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[n - 1][0];
    }

    public int maxProfit121B(int[] prices) {
        if (prices.length == 0 || prices == null) return 0;
        int N = prices.length;
        int k = 1;
        int[][][] dp = new int[N][k + 1][2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= k; j++) {
                if (i == 0) {
                    dp[0][j][0] = 0;
                    dp[0][j][1] = -prices[0];
                    continue;
                }
                if (j == 0) {
                    dp[i][0][0] = 0;
                    dp[i][0][1] = Integer.MIN_VALUE;
                    continue;
                }
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        return dp[N - 1][1][0];
    }

    // ✅122. 买卖股票的最佳时机 II
    public int maxProfit122(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;
        int[][] dp = new int[n][2];
        // init
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);// 和121就差这一行
        }
        return dp[n - 1][0];
    }


    // ✅ 123. 买卖股票的最佳时机 III
    public int maxProfit123(int[] prices, int K) {
        if (prices.length == 0 || prices == null) return 0;
        int N = prices.length;
        int[][][] dp = new int[N][K + 1][2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= K; j++) {
                if (i == 0) {
                    dp[0][j][0] = 0;
                    dp[0][j][1] = -prices[0];
                    continue;
                }
                if (j == 0) {
                    dp[i][0][0] = 0;
                    dp[i][0][1] = Integer.MIN_VALUE;
                    continue;
                }
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        return dp[N - 1][K][0];
    }

    //✅ 188. 买卖股票的最佳时机 IV
    public int maxProfit188(int k, int[] prices) {
        // 还需要优化  一次交易由买入和卖出构成，至少需要两天。
        // 所以说有效的限制 k 应该不超过 n/2，如果超过，就没有约束作用了，相当于 k = +infinity。

        if (prices.length == 0 || prices == null) return 0;
        int N = prices.length;
        if (k > N / 2) {
            // 不限次数交易
            return maxProfit122(prices);
        }
        int[][][] dp = new int[N][k + 1][2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= k; j++) {
                if (i == 0) {
                    dp[0][j][0] = 0;
                    dp[0][j][1] = -prices[0];
                    continue;
                }
                if (j == 0) {
                    dp[i][0][0] = 0;
                    dp[i][0][1] = Integer.MIN_VALUE;
                    continue;
                }
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        return dp[N - 1][k][0];
    }


    //✅ 309. 最佳买卖股票时机含冷冻期
    // dp(i,0) = Max{ dp(i-1,0),dp(i-1,1)+prices[i]
    // dp(i,1) = Max{ dp(i-1,1),dp(i-2,0)-prices[i]
    public int maxProfit309(int[] prices) {
        // input validate
        if (prices == null || prices.length <= 1) return 0;
        int N = prices.length;
        int[][] dp = new int[N][2];
        // BASE CASE
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        dp[1][0] = Math.max(dp[0][0], dp[0][1] + prices[1]);
        dp[1][1] = Math.max(dp[0][1], -prices[1]);

        for (int i = 2; i < N; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
        }
        return dp[N - 1][0];
    }

    //✅ 714. 买卖股票的最佳时机含手续费
    public int maxProfit714(int[] prices, int fee) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;
        int[][] dp = new int[n][2];
        // init
        dp[0][0] = 0;
        dp[0][1] = -prices[0] - fee;
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i] - fee);
        }
        return dp[n - 1][0];
    }

    //② 36 验证是否是有效的数独   // interview friendly  构建 col row group坐标系即可
    public boolean isValidSudoku(char[][] board) {
        Map<String, Boolean> map = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] >= '1' && board[i][j] <= '9') {
                    char c = board[i][j];
                    String rowKey = i + "row" + c;
                    String colKey = j + "col" + c;
                    // group key 为什么没想出来呢  是因为没想出更具坐标系(i,j) 对属于同一个小方格的元素进行归约!!!
                    String groupKey = (i / 3 * 3 + j / 3) + "group" + c;
                    //寻找是否有重复的数字
                    if (map.getOrDefault(rowKey, false)
                            || map.getOrDefault(colKey, false)
                            || map.getOrDefault(groupKey, false)) {
                        return false;
                    }
                    //更新遍历记录
                    map.put(rowKey, true);
                    map.put(colKey, true);
                    map.put(groupKey, true);
                }
            }
        }
        return true;
    }


    // 37 解数独 填充
    public void solveSudoku(char[][] board) {
        boolean[][] cu = new boolean[9][10], ru = new boolean[9][10], bu = new boolean[9][10];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int n = board[i][j] - '0';
                if (1 <= n && n <= 9) {
                    ru[i][n] = true;
                    cu[j][n] = true;
                    bu[(i / 3) * 3 + (j / 3)][n] = true;
                }
            }
        }
        fill(board, ru, cu, bu, 0, 0);
    }
    //  dfs回溯，只需要其中一种解，仔细体会与一般的DFS不同，加了个boolean返回值！！！http://bit.ly/2sThirj
    public boolean fill(char[][] board, boolean[][] ru, boolean[][] cu, boolean[][] bu, int r, int c) {
        if (r == 9) return true;
        int nc = (c + 1) % 9;
        int nr = (nc == 0) ? (r + 1) : r;
        if (board[r][c] != '.') return fill(board, ru, cu, bu, nr, nc);
        for (int i = 1; i <= 9; i++) {
            int bIdx = ((r / 3) * 3) + (c / 3);
            if (!ru[r][i] && !cu[c][i] && !bu[bIdx][i]) {
                ru[r][i] = true;
                cu[c][i] = true;
                bu[bIdx][i] = true;
                board[r][c] = (char) (i + '0');
                if (fill(board, ru, cu, bu, nr, nc)) return true;
                board[r][c] = '.';
                bu[bIdx][i] = false;
                cu[c][i] = false;
                ru[r][i] = false;
            }
        }
        return false;
    }


    // ③ 50 pow(x,n) 注意这道题目 是能用基本算术运算的!!
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        double half = myPow(x, n / 2);
        // 分奇偶
        if (n % 2 == 0) return half * half;
        if (n > 0) return half * half * x;
        // 负数情况下  -5  =  -2 -2 -1  -1 看做 /x即可!
        return half * half / x;
    }

    // 想不出来啊
    public double myPow1(double x, int n) {
        // 比较难想 不好处理
        // x=2 n=11  r // x = 2 n =5   // x =2  n =5/2 =2 // x =2 n =1 // x=2 n=0
        double res = 1.0;
        for (int i = n; i != 0; i = i / 2) {
            if (i % 2 != 0) res = res * x;
            x = x * x;

        }
        return n > 0 ? res : 1 / res;
    }

    //② 179. 最大 int数组编程字符串数组 然后从大到下排列
    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(strs, (o1, o2) -> (o2 + o1).compareTo(o1 + o2));
        String ret = "";
        for (String item : strs) {
            ret += item;
        }
        // corner case 连续0
        return ret.charAt(0) == '0' ? "0" : ret;
    }

    //③ 328 奇偶链表 题解： 奇偶都走2部，奇数尾.next = 偶数头
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode oddTail = head;
        ListNode evenHead = head.next;
        ListNode evenTail = head.next;
        while (evenTail != null && evenTail.next != null) {
            oddTail.next = oddTail.next.next;
            oddTail = oddTail.next;
            evenTail.next = evenTail.next.next;
            evenTail = evenTail.next;
        }
        oddTail.next = evenHead;
        return head;
    }

    // [tag:微软面经] https://www.1point3acres.com/bbs/thread-506842-1-1.html
    // 329. 矩阵中的最长递增路径
    public static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    // 解法 1
    public int longestIncreasingPath01(int[][] matrix) {
        if (matrix.length == 0) return 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, dfsSearchPath(matrix, dp, i, j, m, n));
            }
        }
        return res;
    }

    // 单调最长路径
    public int dfsSearchPath(int[][] matrix, int[][] memo, int i, int j, int m, int n) {
        if (memo[i][j] != 0) return memo[i][j];
        int[] dirs = new int[]{0, 1, 0, -1, 0};
        int len = 1;
        for (int k = 0; k < 4; k++) {
            int x = i + dirs[k], y = j + dirs[k + 1];
            if (x >= 0 && x < m && y >= 0 && y < n && matrix[i][j] < matrix[x][y]) {
                len = Math.max(len, 1 + dfsSearchPath(matrix, memo, x, y, m, n));
            }
        }
        memo[i][j] = len;
        return len;
    }


    // http://bit.ly/2EXW1yR
    // BFS   没看懂！！ 解法3 topological sort 构建拓扑排序, 问题转换为 有向图的中的拓扑排序下的最长路径
    public int longestIncreasingPath03(int[][] matrix) {
        int[] dirs = {0, 1, 0, -1, 0};
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] indegree = new int[m][n];
        Queue<int[]> queue = new LinkedList();
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                for (int i = 0; i < 4; i++) {
                    int nx = dirs[i] + x;
                    int ny = dirs[i + 1] + y;
                    if (nx < 0 || ny < 0 || nx >= m || ny >= n) continue;
                    if (matrix[x][y] > matrix[nx][ny]) indegree[x][y]++;
                }
                if (indegree[x][y] == 0) {
                    queue.offer(new int[]{x, y});
                }
            }
        }
        int len = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                int x = pos[0];
                int y = pos[1];
                for (int k = 0; k < 4; k++) {
                    int nx = x + dirs[k];
                    int ny = y + dirs[k + 1];
                    if (nx < 0 || ny < 0 || nx >= m || ny >= n || matrix[x][y] >= matrix[nx][ny]) continue;
                    indegree[nx][ny]--;
                    if (indegree[nx][ny] == 0) {
                        queue.offer(new int[]{nx, ny});
                    }

                }
            }
            len++;
        }

        return len;
    }


    // 计算器系列 224 227  772    // 770 真心没意思
    //③ 224. 基本计算器  没有优先级了 我真滴服了 审题要仔细哦
    public int calculate224(String str) {
        int ans = 0, sign = 1, num = 0, n = str.length();
        Stack<Integer> s = new Stack();
        for (int i = 0; i < n; i++) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                num = num * 10 + c - '0';
            }
            if (c == '+' || c == '-') {
                ans += sign * num;
                num = 0;
                sign = (c == '+') ? 1 : -1;
            }
            if (c == '(') {
                s.push(ans); // 左括号外的res 压入
                s.push(sign); // 左括号外的+还是-sign 压入
                ans = 0;
                sign = 1;
            }
            if (c == ')') {
                ans += sign * num;
                num = 0;
                ans *= s.pop();// sign
                ans += s.pop(); // 左括号外的res
            }
        }
        ans += sign * num; // edge case ---> "1 + 1" ,头补上 0 + rest
        return ans;
    }

    // ③ 227. 基本计算器 II
    public int calculate227(String str) {
        Stack<Integer> s = new Stack<>();
        int ans = 0, sign = 1, num = 0, N = str.length();
        char op = '+';
        Set<Character> ops = new HashSet<>();
        ops.add('+');
        ops.add('-');
        ops.add('/');
        ops.add('*');
        for (int i = 0; i < N; i++) {
            char c = str.charAt(i);
            if ('0' <= c && c <= '9') {
                num = num * 10 + c - '0';
            }
            if (c == '(') {
                int j = i, cnt = 0;
                while (i < N) {
                    if (str.charAt(i) == '(') cnt++;
                    if (str.charAt(i) == ')') cnt--;
                    if (cnt == 0) {
                        num = calculate772(str.substring(j + 1, i));
                        break;
                    }
                    i++;
                }
            }

            if (ops.contains(c) || i == N - 1) {
                if (op == '+') {
                    s.push(num);
                }
                if (op == '-') {
                    s.push(-num);
                }
                if (op == '*') {
                    s.push(s.pop() * num);
                }
                if (op == '/') {
                    s.push(s.pop() / num);
                }
                // 更新前置运算符
                op = c;
                num = 0;
            }
        }
        while (!s.isEmpty()) {
            ans = ans + s.pop();
        }
        return ans;
    }

    //③  772 基本运算器Ⅲ 和227方法类似,  递归调用注意break
    public int calculate772(String str) {
        Stack<Integer> s = new Stack<>();
        int ans = 0, sign = 1, num = 0, N = str.length();
        char op = '+';
        Set<Character> ops = new HashSet<>();
        ops.add('+');
        ops.add('-');
        ops.add('/');
        ops.add('*');
        for (int i = 0; i < N; i++) {
            char c = str.charAt(i);
            if ('0' <= c && c <= '9') {
                num = num * 10 + c - '0';
            }
            if (ops.contains(c) || i == N - 1) {
                if (op == '+') {
                    s.push(num);
                }
                if (op == '-') {
                    s.push(-num);
                }
                if (op == '*') {
                    s.push(s.pop() * num);
                }
                if (op == '/') {
                    s.push(s.pop() / num);
                }
                op = c;
                num = 0;
            }
        }
        while (!s.isEmpty()) {
            ans = ans + s.pop();
        }
        return ans;
    }


    public int cnt;

    // ❌放弃 //数学 行列式的运用   149 直线上最多的点数   // https://youtu.be/bzsdelrRgNk // 对角线乘积和反对角线乘积之差/2 是 S△
    public int maxPoints(int[][] points) {
        int res = 0, n = points.length;
        for (int i = 0; i < n; i++) {
            int duplicate = 1;
            for (int j = i + 1; j < n; j++) {
                long x1 = points[i][0], y1 = points[i][1], x2 = points[j][0], y2 = points[j][1];
                if (x1 == x2 && y1 == y2) {
                    duplicate++;
                    // 不加 就不是3点共线了 就是2点一条直线,下面的等式任何时候都能成立
                    // 加上去 就代表3个不同点
                    continue;
                }
                for (int k = 0; k < n; k++) {
                    long x3 = points[k][0], y3 = points[k][1];
                    if ((x1 * y2 + x2 * y3 + x3 * y1 - x1 * y3 - x2 * y1 - x3 * y2) == 0) {
                        cnt++;
                    }
                }
                res = Math.max(res, cnt);
            }
            res = Math.max(res, duplicate);
        }
        return res;
    }

    // ② 54. 螺旋矩阵
    public List<Integer> spiralOrder54(int[][] matrix) {
        List<Integer> res = new ArrayList();
        if (matrix.length == 0) return res;
        int colBegin = 0, colEnd = matrix[0].length - 1, rowBegin = 0, rowEnd = matrix.length - 1;
        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            // right
            for (int j = colBegin; j <= colEnd; j++) {
                res.add(matrix[rowBegin][j]);
            }
            rowBegin++;
            // down
            for (int j = rowBegin; j <= rowEnd; j++) {
                res.add(matrix[j][colEnd]);
            }
            colEnd--;

            // left check rowBegin<=rowEnd
            if (rowBegin <= rowEnd) {
                for (int j = colEnd; j >= colBegin; j--) {
                    res.add(matrix[rowEnd][j]);
                }
            }
            // break contract rowBegin > rowEnd
            rowEnd--;
            // up
            if (colBegin <= colEnd) {
                for (int j = rowEnd; j >= rowBegin; j--) {
                    res.add(matrix[j][colBegin]);
                }
            }
            // break contract colBegin > colEnd
            colBegin++;
        }
        return res;
    }

    // follow up 59 螺旋矩阵2
    public int[][] spiralOrder59(int N) {
        int[][] matrix = new int[N][N];
        int cur = 1;
        int colBegin = 0, colEnd = matrix[0].length - 1, rowBegin = 0, rowEnd = matrix.length - 1;
        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            // right
            for (int j = colBegin; j <= colEnd; j++) {
                matrix[rowBegin][j] = cur++;
            }
            rowBegin++;
            // down
            for (int j = rowBegin; j <= rowEnd; j++) {
                matrix[j][colEnd] = cur++;
            }
            colEnd--;

            // left check rowBegin<=rowEnd
            if (rowBegin <= rowEnd) {
                for (int j = colEnd; j >= colBegin; j--) {
                    matrix[rowEnd][j] = cur++;
                }
            }
            // break contract rowBegin > rowEnd
            rowEnd--;
            // up
            if (colBegin <= colEnd) {
                for (int j = rowEnd; j >= rowBegin; j--) {
                    matrix[j][colBegin] = cur++;
                }
            }
            // break contract colBegin > colEnd
            colBegin++;
        }
        return matrix;
    }


    // 277 搜寻名人
    public int findCelebrity(int n) {
        // 这道题 的解题步骤排除法 很多地方都会用到 假定均为true,然后遍历 根据条件去除 最后返回名人i
        boolean[] candidates = new boolean[n];
        Arrays.fill(candidates, true);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && candidates[i]) {
                    if (knows(i, j) || !knows(j, i)) {
                        candidates[i] = false;
                        break;
                    } else {
                        candidates[j] = false;
                    }
                }
            }
            if (candidates[i]) return i;
        }
        return -1;
    }

    /*public int findCelebrity1(int n) {
        for (int i = 0, j = 0; i < n; ++i) {
            for (j = 0; j < n; ++j) {
                if (i != j && (knows(i, j) || !knows(j, i))) break;
            }
            if (j == n) return i;
        }
        return -1;
    }
*/

    public boolean knows(int a, int b) {
        return true;
    }


    // ③ 76 最小覆盖子串 http://bit.ly/2LvcJLu  双指针 滑动窗口方法  ✅  经典方法
    // 解法1
    public String minWindow01(String s, String t) {
        int count = 0, l = 0, r = 0, minLen = Integer.MAX_VALUE;
        String ans = "";
        int[] bank = new int[127];
        for (char c : t.toCharArray()) {
            bank[c - 'A']++;
        }

        while (r < s.length()) {
            char c1 = s.charAt(r);
            if (bank[c1 - 'A'] > 0) { // 匹配了
                count++;
            }
            bank[c1 - 'A']--;
            r++;
            while (count == t.length()) {
                if (minLen > r - l) {
                    minLen = r - l;
                    ans = s.substring(l, r);
                }
                char c2 = s.charAt(l);
                if (bank[c2 - 'A'] == 0) {// 匹配了
                    count--;
                }
                bank[c2 - 'A']++;
                l++;
            }
        }
        return ans;
    }

    // 解法2 最后一个case 没通过
    public String minWindow02(String s, String t) {
        int l = 0, r = 0, N = s.length(), start = 0, minLen = Integer.MAX_VALUE;
        Map<Character, Integer> needs = new HashMap();
        for (char c : t.toCharArray()) {
            needs.put(c, needs.getOrDefault(c, 0) + 1);
        }
        Map<Character, Integer> windows = new HashMap();
        int match = 0;
        while (r < N) {
            char c1 = s.charAt(r);
            if (needs.containsKey(c1)) {
                windows.put(c1, windows.getOrDefault(c1, 0) + 1);
                if (windows.get(c1).equals(needs.get(c1))) {
                    //用equals不用Integer的原因是因为cache   http://bit.ly/2ZgAomJ
                    match++;
                }
            }
            r++;
            // find 可行解, pursue 最优解
            while (match == needs.size()) {
                // update
                if (r - l < minLen) {
                    start = l;
                    minLen = r - l;
                }
                char c2 = s.charAt(l);
                if (needs.containsKey(c2)) {
                    windows.put(c2, windows.get(c2) - 1);
                    if (windows.get(c2) < needs.get(c2)) {
                        match--;
                    }
                }
                l++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }

    //③  162 寻找峰值 二分确定 二分的条件
    public int findPeakElement1(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] < nums[mid + 1]) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    /*// 二分法 logN
    public int findPeakElement0(int[] nums) {
        return binarySearch0(nums, 0, nums.length - 1);
    }

    // 二分法错误做法
    // 二分法推导
    // l = l ,r =l+1, mid = l;
    // then divided into two pieces,  binary(l,l-1)  illegal!!! , binary(l,r)  infinite loop
    // then the right way is --->  binary(l,l)  (l+1,l+1);
    public int binarySearch0(int[] nums, int l, int r) {
        if (l == r) return l;
        int mid = (r - l) / 2 + l;
        if (nums[mid + 1] > nums[mid]) {// l=2 r=3 就会陷入无限循环
            return binarySearch0(nums, mid, r);
        } else {
            return binarySearch0(nums, l, mid - 1);
        }
    }

*/
    public int findPeakElement2(int[] nums) {
        return binarySearch2(nums, 0, nums.length - 1);
    }

    // 递归版本
    public int binarySearch2(int[] nums, int l, int r) {
        if (l == r) return l;
        int mid = (r - l) / 2 + l;
        if (nums[mid] < nums[mid - 1]) { // 越界 l=0, r=1, mid=0  数组越界
            return binarySearch2(nums, l, mid);
        } else {
            return binarySearch2(nums, mid + 1, l);
        }
    }

    // ③ 91 解码方法 ✅ DP  斐波那些数列翻版  dp[i] 表示 前I个表示方法;
    // dp解决  状态转移方程式: if 1<= s[i-1] <=9, dp[i]+=dp[i-1],if 10<=s[i-2:i)<=26 dp[i] +=dp[i-2]
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) return 0;
        int N = s.length();
        int[] dp = new int[N + 1];
        dp[0] = 1;
        dp[1] = (s.charAt(0) == '0' ? 0 : 1);
        for (int i = 2; i <= N; i++) {
            int first = Integer.valueOf(s.substring(i - 1, i));
            int second = Integer.valueOf(s.substring(i - 2, i));
            if (first >= 1 && first <= 9) {
                dp[i] += dp[i - 1];
            }
            if (second >= 10 && second <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[N];
    }


    // ②  130 被围绕的区域  http://bit.ly/2L0HsND
    // 解题思路: 从四条边的O除法,用@字符暂时替代下 dfs完毕后, 还有O的地方用X,@ 用回0
    public void solve(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if ((i == 0 || i == board.length - 1 || j == 0 || j == board[i].length - 1) && board[i][j] == 'O') {
                    // dfsSolve(board, i, j);
                    dfsSolve1(board, i, j);
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                if (board[i][j] == '@') board[i][j] = 'O';
            }
        }

    }

    public void dfsSolve1(char[][] board, int i, int j) {
        if (board[i][j] == 'X' || board[i][j] == '@') return;
        if (board[i][j] == 'O') {
            board[i][j] = '@';
        }
        if (i < board.length - 2 && board[i + 1][j] == 'O') dfsSolve1(board, i + 1, j);
        if (i > 1 && board[i - 1][j] == 'O') dfsSolve1(board, i - 1, j);
        if (j > 1 && board[i][j - 1] == 'O') dfsSolve1(board, i, j - 1);
        if (j < (board[i].length - 2) && board[i][j + 1] == 'O') dfsSolve1(board, i, j + 1);
    }


    public void dfsSolve(char[][] board, int i, int j) {
        if (board[i][j] == 'X' || board[i][j] == '@') return;
        if (board[i][j] == 'O') {
            board[i][j] = '@';
        }
        if (i < board.length - 2) dfsSolve(board, i + 1, j);
        if (i > 1) dfsSolve(board, i - 1, j);
        if (j > 1) dfsSolve(board, i, j - 1);
        if (j < (board[i].length - 2)) dfsSolve(board, i, j + 1);
    }

    // ③  131 分割回文串
    // 验证回文串. 收集回文串
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        LinkedList<String> sub = new LinkedList<>();
        hasCycle(s, 0, sub, ans);
        return ans;
    }

    public void hasCycle(String s, int pos, LinkedList<String> sub, List<List<String>> ans) {
        if (pos == s.length()) {
            ans.add(new ArrayList<>(sub));
            sub.clear();
            return;
        }
        for (int i = pos; i < s.length(); i++) {
            if (isPal(s, pos, i)) {
                sub.addLast(s.substring(pos, i + 1));
                hasCycle(s, i + 1, sub, ans);
                sub.pollLast();
            }
        }
    }

    public boolean isPal(String s, int left, int right) {
        while (left <= right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }
    // 132 分割回文串Ⅱ dp

    //212 单词搜索Ⅱ 构造单词表, 并通过前缀树 及时停止无效DFS.
    public Set<String> result212 = new HashSet();

    public List<String> findWords(char[][] board, String[] words) {
        Trie trie = new Trie();
        // 构造单词树
        for (String word : words) {
            trie.insert(word);
        }
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 穷举搜索,并通过前缀树及时返回
                dfsFindWord(board, visited, "", i, j, trie);
            }
        }
        return new ArrayList(result212);
    }

    public void dfsFindWord(char[][] board, boolean[][] visited, String str, int x, int y, Trie trie) {
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) return;
        if (visited[x][y]) return;
        str += board[x][y];
        if (!trie.startsWith(str)) return;
        if (trie.search(str)) {
            result212.add(str);
        }
        visited[x][y] = true;
        dfsFindWord(board, visited, str, x - 1, y, trie);
        dfsFindWord(board, visited, str, x, y - 1, trie);
        dfsFindWord(board, visited, str, x, y + 1, trie);
        dfsFindWord(board, visited, str, x + 1, y, trie);
        // reset 从 i=0,j=0起点的 完成之后 重置,下一轮dfs过程.
        visited[x][y] = false;
    }


    // ③  134 加油站⛽
    public int canCompleteCircuit(int[] gas, int[] cost) {
        // 遍历的时候 if sum <0 说明这段区间内 均不行,
        // 那么再下个起点继续  下个起点 if sum < 0 那么就下个起点再继续下去哦
        int total = 0, sum = 0, start = 0;
        for (int i = 0; i < gas.length; i++) {
            int tmp = gas[i] - cost[i];
            total += tmp;
            sum += tmp;
            if (sum < 0) {
                sum = 0;
                start = i + 1;
            }
        }
        return total < 0 ? -1 : start;
    }


    //③ 150 逆波兰表达式求值
    public int evalRPN(String[] tokens) {
        Stack<Integer> s = new Stack();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (!isOp(token)) {
                int tmp = decodeStr(tokens[i]);
                s.push(tmp);
            } else {
                int m = s.pop();
                int n = s.pop();
                // res = op(m, n, token);
                // 注意左右操作数
                int ans = op(n, m, token);
                s.push(ans);

            }
        }
        return s.peek();

    }

    public int op(int i, int j, String op) {
        switch (op) {
            case "*":
                return i * j;
            case "/":
                return i / j;
            case "+":
                return i + j;
            case "-":
                return i - j;
            default:
                throw new IllegalStateException("非法字符");

        }
    }

    public boolean isOp(String s) {
        return s.equals("*") || s.equals("/") || s.equals("+") || s.equals("-");
    }

    public int decodeStr(String s) {
        int sign = 1;
        if (s.charAt(0) == '-') {
            sign = -1;
            s = s.substring(1);
        }
        int ans = 0;
        for (char c : s.toCharArray()) {
            ans = ans * 10 + c - '0';
        }
        return ans * sign;
    }

    // ②  73 矩阵置零
    public void setZeroes(int[][] matrix) {
        int MODIFIED = -9999;
        int row = matrix.length;
        for (int i = 0; i < row; i++) {
            int col = matrix[i].length;
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    for (int k = 0; k < row; k++) {
                        if (matrix[k][j] != 0)
                            matrix[k][j] = MODIFIED;
                    }
                    for (int k = 0; k < col; k++) {
                        if (matrix[i][k] != 0)
                            matrix[i][k] = MODIFIED;
                    }
                }
            }
        }
        // 疑问 为什么统一置为 MODIFIED 呢 因为 置为0是不可以的嘛 , 那就统一置为 MODIFIED 了
        for (int i = 0; i < row; i++) {
            int col = matrix[i].length;
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == MODIFIED) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // ② 166 分数到小数
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }
        StringBuilder res = new StringBuilder();
        res.append((numerator ^ denominator) > 0 ? "" : "-");
        // 为什么要加(long)呢
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);// denominator  取  Integer.MIN_VALUE abs之后还是负的


        // integer part
        res.append(num / den);
        num = num % den;
        if (num == 0) {
            return res.toString();
        }

        // fraction part
        res.append(".");
        Map<Long, Integer> map = new HashMap();
        map.put(num, res.length()); // 建立数和index的映射关系。
        while (num != 0) {
            num = num * 10;
            res.append(num / den);
            num = num % den;
            if (map.containsKey(num)) {// 出现循环了
                int index = map.get(num);
                res.insert(index, "(");
                res.append(")");
                break;
            } else {
                map.put(num, res.length());
            }
        }
        return res.toString();
    }

    //③ 334 递增的三元子序列 注意关键字 是 3哦  想想为什么呢
    // 解法1
    public boolean increasingTriplet(int[] nums) {
        // start with two largest values, as soon as we find a number bigger than both, while both have been updated, return true.
        int small = Integer.MAX_VALUE, big = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n <= small) small = n;// update small if n is smaller than both
            else if (n <= big) big = n; // update big only if greater than small but smaller than big
            else return true; // return if you find a number bigger than both
        }
        return false;
    }

    // 解法2 dp
    public boolean increasingTriplet01(int[] nums) {
        int N = nums.length;
        int[] dp = new int[N];
        Arrays.fill(dp, 1);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
                if (dp[i] >= 3) return true;
            }
        }
        return false;
    }


    //②  138 复制带随机指针的链表
    // http://bit.ly/2KWFfDW 解题的关键在于,访问链表节点的随机指正的时候 需要记录已经访问的节点

    public Map<RandomNode, RandomNode> visitedHash138 = new HashMap();

    public RandomNode copyRandomList(RandomNode head) {

        if (head == null) {
            return null;
        }

        if (visitedHash138.containsKey(head)) {// avoid cycle
            return this.visitedHash138.get(head);
        }

        RandomNode node = new RandomNode();
        node.val = head.val;

        visitedHash138.put(head, node);

        node.next = copyRandomList(node.next);
        node.random = copyRandomList(node.random);
        return node;
    }

    // 解法2  不好想出来啊
    public RandomNode copyRandomList1(RandomNode head) {
        if (head == null) {
            return null;
        }
        RandomNode ptr = head;
        // weave next link
        // A-->B-->C
        // A -->A'-->B-->B'--->C.....
        while (ptr != null) {
            RandomNode newNode = new RandomNode();
            newNode.val = ptr.val;
            newNode.next = ptr.next;
            ptr.next = newNode;
            // next iterator
            ptr = newNode.next;
        }

        ptr = head;
        //  weave random link
        while (ptr != null) {
            // 因为是备份
            ptr.next.random = (ptr.random != null) ? ptr.random.next : null;
            // next iterator
            ptr = ptr.next.next;
        }


        RandomNode ptr_old_list = head;
        RandomNode ptr_new_list = head.next;
        RandomNode head_old = head.next;

        // unweave
        while (ptr_old_list != null) {
            ptr_old_list.next = ptr_old_list.next.next;
            ptr_new_list.next = (ptr_new_list.next != null) ? ptr_new_list.next.next : null;
            ptr_old_list = ptr_old_list.next;
            ptr_new_list = ptr_new_list.next;
        }
        return head_old;
    }

    // ②  289 生命游戏
    public void gameOfLife(int[][] board) {
        // count(live) when board[i][j]
        // if[i][j]=1, count(live) <2 DEAD  2-3 live >3 DEAD
        // if [i][j]=0 ,  count(live)>3 LIVE or Dead

        int DEAD = 0, LIVE = 1, toLive = 2, toDead = 3;
        int n = board.length;
        int m = board[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int cmp = board[i][j];
                int cnt = countLive(board, i, j);
                if (cmp == 1 && (cnt < 2 || cnt > 3)) {
                    board[i][j] = toDead;
                }
                if (cmp == 0 && cnt == 3) {
                    board[i][j] = toLive;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == toLive) board[i][j] = LIVE;
                if (board[i][j] == toDead) board[i][j] = DEAD;
            }
        }
    }


    public int countLive(int[][] board, int i, int j) {
        int n = board.length, m = board[0].length;

        int cnt = 0;
        if (i < n - 1 && (board[i + 1][j] == 1 || board[i + 1][j] == 3)) cnt++;
        if (i > 0 && (board[i - 1][j] == 1 || board[i - 1][j] == 3)) cnt++;
        if (j > 0 && (board[i][j - 1] == 1 || board[i][j - 1] == 3)) cnt++;
        if (j < m - 1 && (board[i][j + 1] == 1 || board[i][j + 1] == 3)) cnt++;
        if (i > 0 && j > 0 && (board[i - 1][j - 1] == 1 || board[i - 1][j - 1] == 3)) cnt++;
        if (i > 0 && j < m - 1 && (board[i - 1][j + 1] == 1 || board[i - 1][j + 1] == 3)) cnt++;
        if (i < n - 1 && j > 0 && (board[i + 1][j - 1] == 1 || board[i + 1][j - 1] == 3)) cnt++;
        if (i < n - 1 && j < m - 1 && (board[i + 1][j + 1] == 1 || board[i + 1][j + 1] == 3)) cnt++;
        return cnt;
    }

    // 315 计算右侧小于当前元素的个数  O(N²)  不符合 [2,0,1]
    // 解法1 二分法
    public List<Integer> countSmaller01(int[] nums) {
        int n = nums.length;
        Integer[] ans = new Integer[n];
        List<Integer> sorted = new ArrayList();
        // try to insert val to list, use binary search
        for (int i = n - 1; i >= 0; i--) {
            int index = findIndex(sorted, nums[i]);
            ans[i] = index;
            sorted.add(index, nums[i]);
        }
        return Arrays.asList(ans);
    }

    // http://bit.ly/32512ix
    // ②  二分查找小于target的个数 即是 index  ✅interview friendly
    public int findIndex(List<Integer> sorted, int target) {
        int lo = 0, hi = sorted.size();
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (sorted.get(mid) < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    // 解法2  BST 解法  只能作为参考 不能拿来当做面试用 http://bit.ly/326UjoA
    public List<Integer> countSmaller02(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        TreeNode root = null;
        for (int i = n - 1; i >= 0; i--) {
            root = insert(root, nums[i], ans, i);
        }
        return Arrays.stream(ans).boxed().collect(Collectors.toList());
    }

    public TreeNode insert(TreeNode root, int val, int[] ans, int i) {
        if (root == null) {
            root = new TreeNode(val);
        } else if (val <= root.val) {
            root.count += 1;
            root.left = insert(root.left, val, ans, i);
        } else if (val > root.val) {
            ans[i] += root.count + 1;
            root.right = insert(root.right, val, ans, i);
        }
        return root;
    }

    // ③  324 摆动排序Ⅱ
    // 👍🏻 interview friendly 已经排好序的数组 前半部分和后半部分  对折之后  交替插入
    public void wiggleSort01(int[] nums) {
        int len = nums.length;
        int[] bak = Arrays.copyOf(nums, len);
        Arrays.sort(bak);
        // int sStart = (len % 2) == 0 ? len / 2 : (len / 2 + 1);
        // 优化成如下
        int sStart = (len + 1) / 2;
        int bStart = len - 1;
        sStart--;
        for (int i = 0; i < len / 2; i++) {
            nums[2 * i] = bak[sStart];
            nums[2 * i + 1] = bak[bStart];
            sStart--;
            bStart--;
        }
        // 处理len(small) - len (big)=1;
        if (len % 2 != 0) {
            nums[len - 1] = bak[0];
        }
    }

    //② 解法1 快排最佳实践 http://bit.ly/353KVnO  http://bit.ly/354yckZ  三项快速排序 需要构造newIdx, 需要一次中值切分
    public void wiggleSort02(int[] nums) {
        int median = findKthLargest01(nums, (nums.length + 1) / 2);
        int n = nums.length;
        int lt = 0, i = 0, gt = n - 1;
        while (i <= gt) {
            int idx = newIndex(i, n);
            if (nums[idx] > median) {
                int leftIdx = newIndex(lt, n);
                swap(nums, leftIdx, idx);
                lt++;
                i++;
            } else if (nums[idx] < median) {
                int rightIdx = newIndex(gt, n);
                swap(nums, rightIdx, idx);
                gt--;
            } else {
                i++;
            }
        }
    }

    private int newIndex(int index, int n) {
        int var = (1 + 2 * index) % (n | 1);
        return var;
    }

   /* //  解法3 错误的 无法处理带有重复元素的
    public void wiggleSort03(int[] nums) {
        int n = nums.length;
        if (n <= 1) return;
        Arrays.sort(nums);
        int i = 1;
        while (i < nums.length) {
            if (i < nums.length - 1) swap(nums, i, i + 1);
            i = i + 2;
        }
    }*/

    // ③ 454  四数相加Ⅱ
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                int key = A[i] + B[j];
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
        }

        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < D.length; j++) {
                int target = -1 * (C[i] + D[j]);
                res += map.getOrDefault(target, 0);
            }
        }
        return res;
    }

    // ② 38报数  关键点:尾部加个字母"0";
    // for the nth number, you just need to count characters of the (n-1)th number,
    // for the (n-1)th number, you just need to count characters of  the (n-2)th number,
    // 解法1 递归
    public String countAndSay01(int n) {
        if (n == 1) return "1";
        StringBuilder res = new StringBuilder();
        // recursively call for (n-1) th number, "0" is only for the edge case at the end of the loop with `s.charAt(i+1)`
        String s = countAndSay01(n - 1) + "0";// edge case   len-1  len-2
        for (int i = 0, c = 1; i < s.length() - 1; i++) {
            if (s.charAt(i) != s.charAt(i + 1)) {
                res.append(c).append(s.charAt(i));
                c = 1;
            } else {
                c++;
            }
        }
        return res.toString();
    }

    //解法2  迭代
    public String countAndSay02(int n) {
        String s = "1";
        for (int i = 1; i < n; i++) {
            s = cntMap(s);
        }
        return s;
    }

    public String cntMap(String s) {
        StringBuilder res = new StringBuilder();
        s += "0";// 因为corner case 1的原因啊
        for (int i = 0, c = 1; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                c++;
            } else {
                res.append(c).append(s.charAt(i));
                c = 1;
            }
        }
        return res.toString();
    }

    //  special专题  top k系列问题
    //②  378 有序矩阵中 第K小的元素
    public int kthSmallest(int[][] matrix, int k) {
        // 最大堆 max heap
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                pq.add(matrix[i][j]);
                if (pq.size() > k) pq.poll();
            }
        }
        return pq.peek();
    }

    // 解法2 二分查找
    public int kthSmallest1(int[][] matrix, int k) {
        int n = matrix.length, lo = matrix[0][0], hi = matrix[n - 1][n - 1] + 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = lessEqual(matrix, mid);
            if (count < k) lo = mid + 1;
            else hi = mid;
        }
        return hi;
    }

    //  from left-bottom or right-top can count how much numbers are less equal then target
    public int lessEqual(int[][] matrix, int target) {
        int cnt = 0, N = matrix.length, i = N - 1, j = 0;
        while (i >= 0 && j < N) {
            if (matrix[i][j] > target) i--;
            else {
                cnt = cnt + i + 1;
                j++;
            }
        }
        return cnt;
    }

    // ② 230 二叉搜索树中第k小 通用方法
    public int kthSmallest(TreeNode root, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((o1, o2) -> o2 - o1);
        traversal(root, pq, k);
        return pq.peek();
    }

    public void traversal(TreeNode root, PriorityQueue<Integer> pq, int k) {
        if (root == null) return;
        pq.add(root.val);
        if (pq.size() > k) pq.poll();
        traversal(root.left, pq, k);
        traversal(root.right, pq, k);
    }

    //② 解法2  中序遍历  也是B+数range查询的基本原理了
    public int count230;
    public int ans230 = 0;

    public int kthSmallest1(TreeNode root, int k) {
        inorder(root, k);
        return ans230;
    }

    public void inorder(TreeNode root, int k) {
        if (root == null) {
            return;
        }
        inorder(root.left, k);
        count230 = count230 + 1;
        if (count230 == k) {
            ans230 = root.val;
            return;
        }
        inorder(root.right, k);
    }

    // ② 解法3 二分查找法 tips:  左半右半部分
    // ----------k-------------
    // ---cnt--cnt+1---cnt+2--->
    // ---root.left--root---root.right--->
    public int kthSmallest2(TreeNode root, int k) {
        int cnt = count(root.left);
        if (k < cnt + 1) {
            return kthSmallest2(root.left, k);
        } else if (k > cnt + 1) {
            // [error]  k 的更新哦 k-cnt-1 不是原来的k
            return kthSmallest2(root.right, k - cnt - 1);
        }
        // k=cnt+1;
        return root.val;
    }

    public int count(TreeNode root) {
        if (root == null) return 0;
        return 1 + count(root.left) + count(root.right);
    }

    // follow up   每个点 记录 count信息
    public TreeNode count1(TreeNode root) {
        if (root == null) return null;
        int left = root.left == null ? 0 : count1(root.left).count;
        int right = root.right == null ? 0 : count1(root.right).count;
        root.count = 1 + left + right;
        return root;
    }

    // ② 159 最多有2个不同字符的最长子串
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        return lengthOfLongestSubstringKDistinct(s, 2);
    }

    // 解法3 不需要额外空间的做法 参考解法4 http://bit.ly/2ZMzCws 类似于LC 904
    public int lengthOfLongestSubstringTwoDistinct1(String s) {
        int res = 0, cur = 0, cntLast = 0;
        char first = 0, second = 0;
        for (char c : s.toCharArray()) {
            // 如果是first or second 字符那么 cur++ ,如果不是
            cur = (c == first || c == second) ? cur + 1 : cntLast + 1;
            // cntLast表示 second字符连续的个数
            cntLast = (c == second) ? cntLast + 1 : 1;
            // first 和 second字符
            // 交替更换first second的值
            if (c != second) {
                first = second;
                second = c;
            }
            res = Math.max(res, cur);
        }
        return res;
    }

    // [tag: 微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
    // ② 340 至多包含k个不同字符的最长子串  滑动窗口双指针法
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0) return 0;
        Map<Character, Integer> counts = new HashMap();
        int l = 0, ans = Integer.MIN_VALUE;
        for (int i = 0; i < s.length(); i++) {
            char key = s.charAt(i);
            counts.put(key, counts.getOrDefault(key, 0) + 1);
            while (counts.size() > k) {
                char lk = s.charAt(l);
                counts.put(lk, counts.get(lk) - 1);
                if (counts.get(lk) == 0) {
                    counts.remove(lk);
                }
                l++;
            }
            ans = Math.max(ans, i - l + 1);
        }
        return ans;
    }

    /*// 解法2
    public int lengthOfLongestSubstringKDistinct1(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, res = 0;
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), i); // 赋值 or 覆盖更新
            while (map.size() > k) {
                char leftKey = s.charAt(left);
                if (map.get(leftKey) == left) map.remove(leftKey);
                left++;
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }*/

    // ③ 395 至少有k个重复的字符的最长子串   双指针法 i,j
    public int longestSubstring01(String s, int k) {
        int ans = 0, i = 0, N = s.length();
        while (i < N - k + 1) {
            int[] m = new int[26];
            int mask = 0;
            int separator = i;
            for (int j = i; j < N; j++) {
                int idx = s.charAt(j) - 'a';
                m[idx]++;
                if (m[idx] < k) {
                    mask = mask | (1 << idx); // 指定位掩码 BitOp
                } else {
                    mask = mask & (~(1 << idx));// 指定位掩码 BitOp
                }
                if (mask == 0) {
                    ans = Math.max(ans, j - i + 1);
                    separator = j;
                }
            }
            i = separator + 1;
        }
        return ans;
    }

    // 👍🏻 interview friendly ③ 解法3 DP
    public int longestSubstring02(String s, int k) {
        int res = 0, N = s.length(), maxIdx = 0;
        int[] times = new int[26];
        boolean ok = true;
        for (int i = 0; i < N; i++) {
            times[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < N; i++) {
            if (times[s.charAt(i) - 'a'] < k) {
                // 子递归的第一种情况
                res = Math.max(res, longestSubstring02(s.substring(maxIdx, i), k));
                maxIdx = i + 1;
                ok = false;
            }
        }
        // 子递归的第二种情况
        return ok ? N : Math.max(res, longestSubstring02(s.substring(maxIdx, N), k));
    }

    // ② 解法3 递归迭代解法
    public int longestSubstring03(String s, int k) {
        int len = s.length();
        if (len == 0 || k > len) return 0;
        if (k < 2) return len;
        return count(s.toCharArray(), k, 0, s.length() - 1);
    }

    public int count(char[] chars, int k, int p1, int p2) {
        if (p2 - p1 + 1 < k) return 0;
        int[] times = new int[26];

        for (int i = p1; i <= p2; i++) {
            times[chars[i] - 'a']++;
        }
        while (p2 - p1 + 1 >= k && times[chars[p1] - 'a'] < k) {
            ++p1;
        }
        while (p2 - p1 + 1 >= k && times[chars[p2] - 'a'] < k) {
            --p2;
        }
        if (p2 - p1 + 1 < k) return 0;

        for (int i = p1; i <= p2; i++) {
            if (times[chars[i] - 'a'] < k) {
                return Math.max(count(chars, k, p1, i - 1), count(chars, k, i + 1, p2));
            }
        }
        return p2 - p1 + 1;
    }

    // ③ 440 字典序第K小的数字 http://bit.ly/2nKscwE https://youtu.be/yMnR63e3KLo
    // image成10叉树， 招到相连向量
    public int findKthNumber(int n, int k) {
        int cur = 1;
        k = k - 1;
        while (k > 0) { // if n=1;k=1;
            int gap = findGap(n, cur, cur + 1);
            if (gap <= k) {// 在隔壁子树节点下
                cur = cur + 1;
                k = k - gap;
            } else {// 在当前节点子树下
                cur = cur * 10;
                k = k - 1;
            }
        }
        return cur;
    }

    public int findGap(int n, long cur, long neighbour) {  // [cur,neighbour)或者说(cur,Neighbour] 之间的距离
        int gap = 0;
        while (cur <= n) {
            gap += Math.min(n, neighbour) - cur;
            cur = cur * 10;
            neighbour = neighbour * 10;
        }
        return gap + 1;
    }

    // 类似于46的全排列问题
    // 51 N皇后问题
    char Queen = 'Q', Empty = '.';

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList();
        boolean[][] matrix = new boolean[n][n];
        backTrack(ans, new LinkedList<>(), matrix, 0, n);
        return ans;
    }

    public void backTrack(List<List<String>> ans, LinkedList<String> track, boolean[][] matrix, int row, int n) {
        if (track.size() == row) {
            ans.add(new ArrayList(track));
        } else {
            for (int j = 0; j < n; j++) {
                if (!isValid(row, j, matrix, n)) continue;
                track.addLast(convert(n, j));
                matrix[row][j] = true;
                backTrack(ans, track, matrix, row + 1, n);// 放置Q, track.add() 撤销Q,track.remove
                track.pollLast();
                matrix[row][j] = false;
            }
        }
    }

    public boolean isValid(int row, int col, boolean[][] matrix, int n) {
        // 正上方
        for (int i = row - 1; i >= 0; i--) {
            if (matrix[i][col]) return false;
        }
        // 左斜上方
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (matrix[i][j]) return false;
        }
        // 右斜上方
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (matrix[i][j]) return false;
        }
        return true;
    }

    public String convert(int n, int pos) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < n; i++) {
            ret.append(Empty);
        }
        ret.setCharAt(pos, Queen);
        return ret.toString();
    }

    //③ 516  [tag:微软面筋]  https://www.1point3acres.com/bbs/thread-541121-1-1.html
    // 求M的N次方的后3位
    public int getLastThreeNum(int m, int n) {
        int res = 1;
        for (int i = 0; i < n; i++) {
            res = (res * (m % 1000)) % 1000;
        }
        return res;
    }

    //③ 103 二叉树的锯齿形层遍历
    public List<List<Integer>> zigzagLevelOrder0(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) return ret;
        Queue<TreeNode> s = new LinkedList<>();
        s.add(root);
        boolean lToR = true;
        while (!s.isEmpty()) {
            int len = s.size();
            LinkedList<Integer> sub = new LinkedList<>();
            for (int i = 0; i < len; i++) {
                TreeNode cur = s.poll();
                if (lToR) {
                    sub.addLast(cur.val);
                } else {
                    sub.addFirst(cur.val);
                }
                if (cur.left != null) s.add(cur.left);
                if (cur.right != null) s.add(cur.right);
            }
            lToR = !lToR;
            ret.add(sub);
        }
        return ret;
    }

    // 解法2
    public List<LinkedList<Integer>> zigzagLevelOrder1(TreeNode root) {
        List<LinkedList<Integer>> ans = new ArrayList();
        dfsZigzag(root, 0, ans);
        return ans;
    }

    public void dfsZigzag(TreeNode root, int level, List<LinkedList<Integer>> ret) {
        if (root == null) return;
        if (ret.size() <= level) { // 这里比较trick 什么时候new 一个List
            LinkedList<Integer> newLevel = new LinkedList<>();
            ret.add(newLevel);
        }
        LinkedList<Integer> sub = ret.get(level);
        if ((level & 1) == 0) {
            sub.addLast(root.val);
        } else {
            sub.addFirst(root.val);
        }
        dfsZigzag(root.left, level + 1, ret);
        dfsZigzag(root.right, level + 1, ret);

    }

    // 三 594 最长和谐子序列
    public int findLHS(int[] nums) {
        Map<Integer, Integer> map = new HashMap();
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        int ans = 0;
        for (int key : map.keySet()) { // keyset用法了解下  java还是麻烦啊
            if (map.containsKey(key + 1)) {
                ans = Math.max(ans, map.get(key) + map.get(key + 1));
            }
        }
        return ans;
    }

    // ③ 1027. 最长等差数列
    //  dp[i][step]=dp[j][step]+1;  j < i
    public int longestArithSeqLength(int[] A) {
        int res = 2, n = A.length;
        Map<Integer, Integer>[] dp = new HashMap[n];
        for (int i = 0; i < A.length; i++) {
            dp[i] = new HashMap<>();
            for (int j = 0; j < i; j++) {
                int d = A[i] - A[j];
                dp[i].put(d, dp[j].getOrDefault(d, 1) + 1);
                res = Math.max(res, dp[i].get(d));
            }
        }
        return res;
    }

    // ③ 最大公约数
    // gcd(104,40) = 8  辗转相除法
    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // ③ 最小公倍数 * 最大公约数 = a*b
    public int lcm(int a, int b) {
        return (b) / Math.abs(gcd(a, b)) * a;
    }

    // 36进制加法运算？
    //将'0'-'9'映射到数字0-9，将'a'-'z'映射到数字10-35
    int GetInt(char c) {
        if ((c - '0') > 0 && (c <= '9')) {
            return (c - '0');
        } else {
            return (c - 'A') + 10;
        }
    }

    private char[] letter = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public String Add36(String s1, String s2) {
        char[] a = s1.toCharArray();
        int i = a.length - 1;
        char[] b = s2.toCharArray();
        int j = a.length - 1;
        StringBuilder ans = new StringBuilder();
        int carry = 0;
        // 处理sign 空格
        while (i >= 0 && j >= 0) {
            int ret = GetInt(a[i]) + GetInt(b[j]) + carry;
            if (ret >= 36) {
                carry = 1;
                ans.append(letter[ret % 36]);
            } else {
                carry = 0;
                ans.append(letter[ret]);
            }
            i--;
            j--;
        }

        while (i >= 0) {
            int ret = GetInt(a[i]) + carry;
            if (ret >= 36) {
                carry = 1;
                ans.append(letter[ret % 36]);
            } else {
                carry = 0;
                ans.append(letter[ret]);
            }
            i--;
        }
        while (j >= 0) {
            int ret = GetInt(b[j]) + carry;
            if (ret >= 36) {
                carry = 1;
                ans.append(letter[ret % 36]);
            } else {
                carry = 0;
                ans.append(letter[ret]);
            }
            j--;
        }
        return ans.toString();

    }

    int VISITED = -1;
    int VISITING = -2;

    // 207 课程表  构建通topology sort  模板题。
    public boolean canFinish01(int numCourses, int[][] prerequ) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < prerequ.length; i++) {
            int key = prerequ[i][0];
            List<Integer> arr = map.getOrDefault(key, new ArrayList<>());
            arr.add(prerequ[i][1]);
            map.put(key, arr);
        }
        int[] visit = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (hasCycle(map, i, visit)) return false;
        }
        return true;
    }

    public boolean hasCycle(Map<Integer, List<Integer>> map, int key, int[] visit) {
        if (visit[key] == VISITED) return false;
        if (visit[key] == VISITING) return true;
        visit[key] = VISITING;
        if (map.containsKey(key)) {
            for (Integer i : map.get(key)) {
                if (hasCycle(map, i, visit)) return true;
            }
        }
        visit[key] = VISITED;
        return false;
    }

    // 210 课程表Ⅱ
    // 花花的解法 用DFS也能做呢
    // http://zxi.mytechroad.com/blog/graph/leetcode-210-course-schedule-ii/
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < prerequisites.length; i++) {
            int key = prerequisites[i][0];
            List<Integer> arr = map.getOrDefault(key, new ArrayList<>());
            arr.add(prerequisites[i][1]);
            map.put(key, arr);
        }
        int[] visit = new int[numCourses];
        LinkedList<Integer> ans = new LinkedList<>();
        int[] ret = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (hasCycle(map, i, visit, ans)) return ret;
        }
        for (int i = 0; i < ans.size(); i++) {
            ret[i] = ans.get(i);
        }
        return ret;
    }

    public boolean hasCycle(Map<Integer, List<Integer>> map, int key, int[] visit, LinkedList<Integer> ans) {
        if (visit[key] == VISITED) return false;
        if (visit[key] == VISITING) return true;
        visit[key] = VISITING;
        if (map.containsKey(key)) {
            for (Integer i : map.get(key)) {
                if (hasCycle(map, i, visit, ans)) return true;
            }
        }
        visit[key] = VISITED;
        ans.addFirst(key);
        return false;
    }

    // 207 BFS topology sort 参考链接：http://bit.ly/33SdXpG
    // 210 也可以用这道题目来做呢
    public boolean canFinish02(int numCourses, int[][] prerequisites) {
        if (numCourses < 0) return false;
        int plen = prerequisites.length;
        if (plen == 0) return true;
        int[] indegree = new int[numCourses];
        for (int[] cp : prerequisites) {
            indegree[cp[0]]++;
        }
        LinkedList<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) q.addLast(i);
        }

        List<Integer> ans = new ArrayList<>();
        while (!q.isEmpty()) {
            int zero = q.pollFirst();
            ans.add(zero);
            for (int[] cp : prerequisites) {
                if (cp[1] == zero) {
                    indegree[cp[0]]--; // 入度减一
                    if (indegree[cp[0]] == 0) {
                        q.add(cp[0]);// 如果依赖节点indegree是0 ，那么入队，说明先导课程已经修完了
                    }
                }
            }
        }
        return ans.size() == numCourses;
    }


    // 链式前向星 excu me?  链式前向星介绍参见 https://oi-wiki.org/graph/basic/
    // http://bit.ly/2KfuHPN
    // 题目答案参见  http://bit.ly/2OdVc9c
    // 链式前向星据说是来自OI圈的叫法，不过换成边，比之前的容易理解
    public Edge[] edges;
    public int[] head;
    public int[] deg; //记录入度

    void add(int u, int v) {
        edges[cnt] = new Edge();
        edges[cnt].to = v;
        edges[cnt].next = head[u];//理解这个是关键点啊
        head[u] = cnt++;
    }

    public boolean solve(int n) {
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < n; ++i) {
            if (deg[i] == 0) { //存入度为0的点
                queue.offer(i);
            }
        }
        int k = 0;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            ++k;  //记录入度为0的个数
            for (int i = head[u]; i != -1; i = edges[i].next) { //循环与其相连的点
                int v = edges[i].to;
                --deg[v];
                if (deg[v] == 0) { //入度为0加入队列中
                    queue.offer(v);
                }
            }
        }
        return k == n; //若为n说明不存在环
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int n = prerequisites.length;
        int l = Math.max(numCourses, n); //取大
        cnt = 0;
        edges = new Edge[l];
        head = new int[l];
        deg = new int[l];
        Arrays.fill(head, -1);
        for (int i = 0; i < n; ++i) {
            add(prerequisites[i][1], prerequisites[i][0]); //建图
            ++deg[prerequisites[i][0]]; //记录入度
        }
        return solve(numCourses);
    }

    public int[] smallestRange(List<List<Integer>> nums) {
        //小根堆，堆顶为各列表最小当前元素 二维坐标
        Queue<int[]> minQueue = new PriorityQueue<>(Comparator.comparingInt(arr -> nums.get(arr[0]).get(arr[1])));
        //大根堆，堆顶为各列表最大当前元素 二维坐标
        Queue<int[]> maxQueue = new PriorityQueue<>((arr1, arr2) -> nums.get(arr2[0]).get(arr2[1]) - nums.get(arr1[0]).get(arr1[1]));
        int[] ans = new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int i = 0; i < nums.size(); i++) {
            //初始化各列表第一个元素，小根堆&大根堆添加同一个对象，方便后面remove
            int[] arr = new int[]{i, 0};
            minQueue.offer(arr);
            maxQueue.offer(arr);
        }
        while (minQueue.size() == nums.size()) {
            //推出小根堆顶元素，小根堆size-1
            int[] minArr = minQueue.poll();
            //小根堆顶元素与大根堆顶元素区间，每个列表至少有一个数包含在其中
            int[] maxArr = maxQueue.peek();
            //注意此处相减值溢出，需要转成long
            if ((long) nums.get(maxArr[0]).get(maxArr[1]) - (long) nums.get(minArr[0]).get(minArr[1]) < (long) ans[1] - (long) ans[0]) {
                ans[0] = nums.get(minArr[0]).get(minArr[1]);
                ans[1] = nums.get(maxArr[0]).get(maxArr[1]);
            }
            //丢弃小根堆顶元素，取其所在列表下一元素重新构建堆
            if (minArr[1] < nums.get(minArr[0]).size() - 1) {
                int[] newArr = new int[]{minArr[0], minArr[1] + 1};
                minQueue.offer(newArr);
                //因为添加相同对象，可以直接remove
                maxQueue.remove(minArr);
                maxQueue.offer(newArr);
            }
        }
        return ans;
    }

    // ③ 547 朋友圈
    public int findCircleNum(int[][] M) {
        boolean[] visited = new boolean[M.length];
        int count = 0;
        for (int i = 0; i < M.length; i++) {
            if (visited[i]) {
                dfs(M, visited, i);
                count++;
            }
        }
        return count;
    }


    public void dfs(int[][] M, boolean[] visited, int i) {
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                dfs(M, visited, j);
            }
        }
    }

    //
    // https://www.geeksforgeeks.org/sort-linked-list-already-sorted-absolute-values/
    // HEAD>-- PREV --> CUR -->NEXT
    public ListNode sortedList(ListNode head) {
        ListNode prev = head;
        ListNode curr = head.next;
        while (cur != null) {
            if (curr.val < prev.val) {
                prev.next = curr.next;
                cur.next = head;
                head = cur;
                cur = prev;

            } else {
                prev = curr;
            }
            curr = curr.next;
        }
        return head;
    }


    // ③ 366 寻找完全二叉树的叶子节点
    public List<List<Integer>> findLeaves(TreeNode root) {
        // 用TreeMap代替,然后转换成ans
        Map<Integer, List<Integer>> map = new TreeMap<>();
        dfs(root, map);

        List<List<Integer>> ans = new ArrayList<>();
        for (List<Integer> value : map.values()) {
            ans.add(value);
        }
        return ans;
    }

    public int dfs(TreeNode root, Map<Integer, List<Integer>> map) {
        if (root == null) return -1;
        int depth = Math.max(dfs(root.left, map), dfs(root.right, map)) + 1;
        List<Integer> sub = map.getOrDefault(depth, new ArrayList<>());
        sub.add(root.val);
        map.put(depth, sub);
        return depth;
    }

    // 327 303

    // 629 k个逆序对 DP
    // 等式A dp(n,k)= dp(n-1,k)+dp(n-1,k-1)+...dp(n-1,k+1-(n-1))+dp(n-1,k-(n-1))
    // 等式B dp(n,k+1) = dp(n-1,k+1)+dp(n-1,k)+dp(n-1,k-1)+...dp(n-1,k+1-(n-1))

    //  做消元 ，dp（n,k+1) = dp(n,k)+dp(n-1,k+1)-dp(n-1,k-n+1), n
    public int kInversePairs(int n, int k) {
        return -1;
    }

    // LC 358   todo K距离间隔重排字符串
    public String rearrangeString(String s, int k) {
        return "";
    }
    // 630 课程表Ⅲ

    // 354 俄罗斯套娃信封问题

    // 218
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> ans = new ArrayList();
        List<int[]> height = new ArrayList();
        for (int[] b : buildings) {
            height.add(new int[]{b[0], -b[2]});
            height.add(new int[]{b[1], b[2]});
        }
        // 入点 降序， 出点 升序, 入点选高的，出点选低的
        Collections.sort(height, (a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            } else {
                return a[1] - b[1];
            }
        });
        //构建最大堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>((a, b) -> b - a);
        maxHeap.offer(0);
        int prev = 0;
        for (int[] h : height) {
            if (h[1] < 0) {
                maxHeap.offer(-h[1]);
            } else {
                // 删除 出点第一大的，保证出点是第二大的
                maxHeap.remove(h[1]);
            }
            int cur = maxHeap.peek();
            if (prev != cur) { // 若相等，说明 还在点内
                ans.add(new int[]{h[0], cur});
                prev = cur;
            }
        }
        return ans;
    }


    // 1293 有障碍物的最短路径，拥有K个消除权力的
    public int shortestPath(int[][] grid, int k) {
        int[] dirs = new int[]{0, -1, 0, 1, 0};
        int m = grid.length, n = grid[0].length;
        int[] seen = new int[m * n];
        Arrays.fill(seen, Integer.MAX_VALUE);
        LinkedList<Tuple> q = new LinkedList<>();
        int ans = 0;
        q.add(new Tuple(0, 0, 0));
        seen[0] = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                Tuple t = q.poll();
                int x = t.x, y = t.y, z = t.z;
                if (x == m - 1 && y == n - 1) return ans;
                for (int j = 0; j < 4; j++) {
                    int nx = x + dirs[j];
                    int ny = y + dirs[j + 1];
                    if (nx < 0 || ny < 0 || nx >= m || ny >= n) continue;
                    int ob = z + grid[x][y];
                    if (ob >= seen[nx * n + ny] || ob > k) continue;
                    seen[nx * n + ny] = ob;
                    q.add(new Tuple(nx, ny, ob));
                }
            }
            ans++;
        }
        return -1;
    }

    // 1231 最大得分的路径数目
    // 这里有意思的i，j 代表的不是坐标索引，而是第几个，差1的关系，和第二种方法一样用了padding
    public int[] pathWithMaxScore01(List<String> board) {
        int kMod = (int) (1e9 + 7);
        int m = board.size();
        int[][] dp = new int[m + 1][m + 1];
        int[][] cc = new int[m + 1][m + 1];
        // init;
        cc[1][1] = 1;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m; j++) {
                char c = board.get(i - 1).charAt(j - 1);
                if (c == 'S' || c == 'E') c = '0';
                int val = Math.max(Math.max(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                dp[i][j] = m + c - '0';
                if (val == dp[i - 1][j]) {
                    cc[i][j] = (cc[i][j] + cc[i - 1][j]) % kMod;
                }
                if (val == dp[i][j - 1]) {
                    cc[i][j] = (cc[i][j] + cc[i][j - 1]) % kMod;
                }
                if (val == dp[i - 1][j - 1]) {
                    cc[i][j] = (cc[i][j] + cc[i - 1][j - 1]) % kMod;
                }
            }
        }
        return new int[]{cc[m][m] == 0 ? 0 : dp[m][m], cc[m][m]};
    }

    public int[] pathsWithMaxScore02(List<String> board) {
        int kMod = (int) (1e9 + 7);
        int m = board.size();
        int[][] dp = new int[m + 1][m + 1];
        int[][] cc = new int[m + 1][m + 1];
        // init
        cc[m - 1][m - 1] = 1;
        // dp
        for (int i = m - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                char c = board.get(i).charAt(j);
                if (c == 'S' || c == 'E') c = '0';// 起点和終点的处理
                if (c == 'X') continue;
                int val = Math.max(Math.max(dp[i + 1][j], dp[i][j + 1]), dp[i + 1][j + 1]);
                dp[i][j] = val + c - '0';
                if (val == dp[i + 1][j]) {
                    cc[i][j] = (cc[i][j] + cc[i + 1][j]) % kMod;
                }
                if (val == dp[i][j + 1]) {
                    cc[i][j] = (cc[i][j] + cc[i][j + 1]) % kMod;
                }
                if (val == dp[i + 1][j + 1]) {
                    cc[i][j] = (cc[i][j] + cc[i + 1][j + 1]) % kMod;
                }
            }
        }
        return new int[]{cc[0][0] == 0 ? 0 : dp[0][0], cc[0][0]};
    }

    // ❓
    public int[] pathsWithMaxScore03(List<String> board) {
        int m = board.size();
        char ob = 'X';
        int[] dirs = new int[]{0, -1, -1, 0};
        PriorityQueue<Tuple> q = new PriorityQueue<>((a, b) -> a.z - b.z);
        int ans = 0, count = 0;
        q.add(new Tuple(m - 1, m - 1, 0));
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                Tuple t = q.poll();
                int x = t.x, y = t.y, z = t.z;
                for (int k = 0; k < 3; k++) {
                    int nx = x + dirs[k];
                    int ny = y + dirs[k + 1];
                    if (nx < 0 || ny < 0 || nx >= m || ny >= m || board.get(nx).charAt(ny) == ob) continue;
                    if (nx == 0 && ny == 0) {
                        if (ans == z) count++;
                        if (ans < z) {
                            count = 1;
                            ans = z;
                        }
                    } else {
                        int update = board.get(nx).charAt(ny) - '0' + z;
                        if (update >= ans) q.offer(new Tuple(nx, ny, update));
                    }
                }
            }
        }
        return new int[]{ans, count};

    }


    // 55 跳跃游戏
    public boolean canJump(int[] nums) {
        int N = nums.length;
        boolean[] dp = new boolean[N];
        dp[0] = nums[0] > 0;
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (!dp[i]) dp[i] = (dp[j] && (i - j) <= nums[j]);
            }
        }
        return dp[N - 1];
    }

    // 1298 BFS  S:O(盒子个数） T(盒子个数）
    public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        int ans = 0;
        int open = 1, closed = 0;
        boolean[] seen = new boolean[status.length];
        LinkedList<Integer> q = new LinkedList<>();
        for (int i : initialBoxes) {
            seen[i] = true;
            if (status[i] == open) q.push(i);
        }
        while (!q.isEmpty()) {
            int b = q.poll();
            ans += candies[b];
            for (int t : containedBoxes[b]) { // 内嵌的盒子找到了，如果是出于打开状态，那么入列
                seen[t] = true;
                if (status[t] == open) q.push(t);
            }
            for (int t : keys[b]) { // 拥有打开盒子的钥匙，那么 1盒子需要招到了2没有打开过 ，那么才可以入列。
                if (seen[t] && status[t] == closed) q.push(t);
                status[t] = open;
            }
        }
        return ans;
    }

    // 969 https://youtu.be/RB9hlDDWQY0
    public List<Integer> pancakeSort(int[] A) {
        List<Integer> ans = new ArrayList<>();
        int n = A.length, largest = n;
        for (int i = 0; i < n; i++) {
            int index = find(A, largest);
            flip(A, index);//执行前index+1 flip
            flip(A, largest - 1);// 执行largest个 flip.
            ans.add(index + 1);
            ans.add(largest--);
        }
        return ans;
    }

    private int find(int[] A, int target) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] == target) {
                return i;
            }
        }
        return -1;
    }

    private void flip(int[] A, int index) {
        int i = 0, j = index;
        while (i < j) {
            int temp = A[i];
            A[i++] = A[j];
            A[j--] = temp;
        }
    }

    // LC 815 公交线路
    // 解法1 存 stop
    public int numBusesToDestination01(int[][] routes, int S, int T) {
        if (S == T) return 0;
        LinkedList<Integer> q = new LinkedList();// 公交车
        Map<Integer, List<Integer>> m = buildEdges(routes);// 站-->公交车
        q.push(S);
        boolean[] seen = new boolean[routes.length];
        int ans = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            ans++;
            while (size-- > 0) {
                int stop = q.poll();// 站点
                for (int bus : m.get(stop)) {
                    if (seen[bus]) continue;
                    seen[bus] = true;
                    for (int i : routes[bus]) {
                        if (i == T) return ans;
                        q.add(i);
                    }
                }

            }
        }

        return -1;
    }

    // 解法2 存 bus
    public int numBusesToDestination02(int[][] routes, int S, int T) {
        if (S == T) return 0;
        LinkedList<Integer> q = new LinkedList();// 公交车
        Map<Integer, List<Integer>> m = buildEdges(routes);// 站-->公交车
        boolean[] seen = new boolean[routes.length];
        Set<Integer> s = new HashSet();
        s.add(S);
        for (int bus : m.get(S)) {
            seen[bus] = true;
            q.add(bus);
        }
        int ans = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            ans++;
            while (size-- > 0) {
                int bus = q.poll();// 公交车
                int[] stops = routes[bus];
                for (int stop : stops) {
                    if (stop == T) return ans;
                    if (s.contains(stop)) continue;
                    s.add(stop);
                    List<Integer> buses = m.get(stop);
                    for (int i : buses) {
                        if (seen[i]) continue;
                        q.add(i);
                    }
                }
            }

        }
        return -1;
    }


    public Map<Integer, List<Integer>> buildEdges(int[][] routes) {
        Map<Integer, List<Integer>> m = new HashMap();
        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].length; j++) {
                int stop = routes[i][j];
                List<Integer> buses = m.getOrDefault(stop, new ArrayList());
                buses.add(i);
                m.put(stop, buses);
            }
        }
        return m;
    }

    // LC 1245
    public int treeDiameter(int[][] edges) {
        int N = edges.length;
        List<Integer>[] m = new ArrayList[N + 1];
        Arrays.fill(m, new ArrayList<>());
        boolean[] seen = new boolean[N + 1];
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            m[u].add(v);
            m[v].add(u);
        }
        dfs(m, 0, seen);
        return res;
    }

    // ⚠️  这是一个很强的小技巧
    public int dfs(List<Integer>[] map, int idx, boolean[] seen) {
        seen[idx] = true;
        List<Integer> edges = map[idx];
        int max1 = 0;
        int max2 = 0;
        for (int next : edges) {
            if (!seen[next]) {
                int num = dfs(map, next, seen);
                if (num > max1) {
                    max2 = max1;
                    max1 = num;
                } else if (num > max2) {
                    max2 = num;
                }
            }
        }
        res = Math.max(res, max1 + max2);
        return Math.max(max1, max2) + 1;
    }

    // 24 两两交换链表中的节点
    public ListNode swapPairs(ListNode head) {
        ListNode cur = head;
        int count = 0;
        int k = 2;
        while (count < k) {
            if (cur == null) return head;
            cur = cur.next;
            count++;
        }

        ListNode pre = swapPairs(cur);
        while (count > 0) {
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
            count--;
        }
        return pre;
    }


    // 25  K个一组翻转链表
    //1-2-3-4-5
    // 2
    // 2-1-4-3-5
    // http://bit.ly/2QuAULl 评论里面有更好的
    public ListNode reverseKGroup01(ListNode head, int k) {
        ListNode cur = head;
        int count = 0;
        while (count < k) { // 获取下一个节点
            if (cur == null) return head;
            cur = cur.next;
            count++;
        }
        ListNode pre = reverseKGroup01(cur, k);// pre代表下一个一组的head节点
        while (count > 0) {// pre和之前的head节点要进行reverse。
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
            count--;
        }
        return pre;
    }

    // 解法2 非递归 todo
    public ListNode reverseKGroup02(ListNode head, int k) {
        if (head == null || k == 1) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy, nex = dummy, pre = dummy;
        int count = 0;
        while (cur.next != null) {
            cur = cur.next;
            count++;
        }
        while (count >= k) {
            cur = pre.next;
            nex = cur.next;
            for (int i = 1; i < k; i++) {
                cur.next = nex.next;
                nex.next = pre.next;
                pre.next = nex;
                nex = cur.next;
            }
            pre = cur;
            count -= k;
        }
        return dummy.next;
    }

    // 6 Z字形变换
    public String zigzagConvert(String str, int rows) {
        // init的时候 curR 应该从false-->true
        if (rows == 1) return str;
        String[] strs = new String[rows];
        Arrays.fill(strs, "");
        boolean down = false;
        int curR = 0;
        for (char c : str.toCharArray()) {
            strs[curR] = strs[curR] + c;
            if (curR == 0 || curR == rows - 1) {
                down = !down;
            }
            curR += down ? 1 : -1;
        }
        String ans = "";
        for (String s : strs) {
            ans += s;
        }
        return ans;
    }

    // 27 原地移除元素
    public int removeElement(int[] nums, int val) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[count] = nums[i];
                count++;
            }
        }
        return count;
    }

    // 这道题目其实很经典的，如何处理tradeoff http://bit.ly/2QPGTcx
    // 9 是否是回文数字
    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;
        int revN = 0;
        while (x > revN) {
            revN = revN * 10 + x % 10;
            x = x / 10;
        }
        // 奇偶
        return x == revN / 10 || x == revN;
    }

    // 100 是否相同的树
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val == q.val) return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        return false;
    }

    // 67 二进制求和
    public String addBinary(String a, String b) {
        StringBuilder ans = new StringBuilder();
        int carry = 0;
        for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
            int sum = carry;
            sum += i >= 0 ? a.charAt(i) - '0' : 0;
            sum += j >= 0 ? b.charAt(j) - '0' : 0;
            ans.append(sum % 2);
            carry = sum / 2;
        }
        if (carry == 1) ans.append(carry);
        return ans.reverse().toString();
    }

    // 55 BFS来做 O(N²)
    public int jump01(int[] nums) {
        int N = nums.length;
        LinkedList<Pair> Q = new LinkedList<Pair>();
        boolean[] seen = new boolean[N];
        Q.add(new Pair(0, nums[0]));
        int ans = 0;
        while (!Q.isEmpty()) {
            int size = Q.size();
            for (int i = 0; i < size; i++) {
                Pair p = Q.poll();
                int start = p.key, step = p.val;
                if (start == N - 1) return ans;
                for (int k = Math.min(start + step, N - 1); k > start; k--) {
                    if (!seen[k]) {
                        Q.add(new Pair(k, nums[k]));
                        seen[k] = true;
                    }
                }
            }
            ans++;
        }
        return -1;
    }

    // BFS O(n)
    public int jump02(int[] nums) {
        if (nums.length <= 1) return 0;
        int start = 0, reach = 0, step = 0;
        while (reach < nums.length - 1) {
            if (start > reach) return -1;
            int farest = 0;
            for (int i = start; i <= reach; i++) {
                farest = Math.max(farest, i + nums[i]);
            }
            start = reach + 1;
            reach = farest;
            step++;
        }
        return step;
    }

    // 115 不同的子序列
    public int numDistinct(String s, String t) {
        // init
        int N = s.length(), M = t.length();
        int[][] dp = new int[M + 1][N + 1];
        // ✨
        Arrays.fill(dp[0], 1);

        // transition
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                // if t[i]==s[j];
                dp[i][j] += dp[i][j - 1];
                if (t.charAt(i - 1) == s.charAt(j - 1)) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }
        // return
        return dp[M][N];
    }

    // 43 字符串数字相乘
    public String multiply(String s1, String s2) {
        if ("0".equals(s1) || "0".equals(s2)) return "0";
        String ans = "0";
        int m = s1.length(), n = s2.length();
        for (int i = n - 1; i >= 0; i--) {
            int carry = 0;
            StringBuilder tmp = new StringBuilder();
            for (int k = 0; k < n - 1 - i; k++) tmp.append("0");
            int c = s2.charAt(i) - '0';
            for (int j = m - 1; j >= 0; j--) {
                int product = (s1.charAt(j) - '0') * c + carry;
                tmp.append(product % 10);
                carry = product / 10;

            }
            // carry可以是1-9
            if (carry != 0) tmp.append(carry);
            ans = add(ans, tmp.reverse().toString());
        }
        return ans;
    }

    public String add(String s1, String s2) {
        int carry = 0, m = s1.length(), n = s2.length();
        StringBuilder ans = new StringBuilder();
        for (int i = m - 1, j = n - 1; i >= 0 || j >= 0; i--, j--) {
            int sum = carry;
            sum += i >= 0 ? s1.charAt(i) - '0' : 0;
            sum += j >= 0 ? s2.charAt(j) - '0' : 0;
            ans.append(sum % 10);
            carry = sum / 10;
        }
        if (carry != 0) ans.append(carry);
        return ans.reverse().toString();
    }

    //[KSUM系列] 1 两数之和 15 三数之和 18 四数之和 系列
    //③ 1 两数之和
    public int[] twoSum01(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    // 解法2
    public int[] twoSum02(int[] nums, int target) {
        Arrays.sort(nums);
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int sum = nums[lo] + nums[hi];
            if (sum < target) {
                lo++;
            } else if (sum > target) {
                hi--;
            } else {
                return new int[]{nums[lo], nums[hi]};
            }
        }
        return new int[]{};
    }
    //

    // ② 15 3数字和，先排序  i j k指针，跳过重复数
    // http://bit.ly/2Sp1iFG
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ret = new ArrayList();
        if (nums.length < 3) return ret;
        Arrays.sort(nums);
        int i = 0;
        while (i < nums.length - 2) {
            if (nums[i] > 0) break;
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == 0) ret.add(Arrays.asList(nums[i], nums[j], nums[k]));
                // 跳过重复数字 注意前置++ -- 运算符 运算好立即调用 而不是等到下一次
                if (sum < 0) while (nums[j] == nums[++j] && j < k) ;
                if (sum > 0) while (nums[k] == nums[--k] && j < k) ;
            }
            // 跳过重复数字
            while (nums[i] == nums[++i] && i < nums.length - 2) ;
        }
        return ret;
    }

    // 18 四数之和
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length < 4) return ans;
        Arrays.sort(nums);
        int N = nums.length;
        for (int i = 0; i < N - 3; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                for (int j = i + 1; j < N - 2; j++) {
                    if (j == i + 1 || nums[j] != nums[j - 1]) {
                        int lo = j + 1, hi = N - 1;
                        int v = target - nums[i] - nums[j];
                        while (lo < hi) {
                            // edge case [-1,2,2,-5,0,-1,4],target =3 吧过滤重复元素放在这里就是错误了！！
                            //  while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                            //  while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
                            if (nums[lo] + nums[hi] == v) {
                                ans.add(Arrays.asList(nums[i], nums[j], nums[lo], nums[hi]));
                                // 跳过原有的
                                while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                                while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
                                lo++;
                                hi--;
                            } else if (nums[lo] + nums[hi] > v) {
                                hi--;
                            } else {
                                lo++;
                            }
                        }
                    }
                }
            }
        }
        return ans;
    }

    //
    /*public List<List<Integer>> fourSum_(int[] arr, int target) {

        List<List<Integer>> ans = new ArrayList<>();
        if (arr == null || arr.length < 4) return ans;
        Arrays.sort(arr);
        int N = arr.length;
        if (target > 4 * arr[N - 1] || target < 4 * arr[0]) return ans;
        for (int i = 0; i <= N - 4; i++) {
            if (i == 0 || arr[i] != arr[i - 1]) {
                for (int j = i + 1; j <= N - 3; j++) {
                    if (j == 0 || arr[j] != arr[j - 1]) {
                        for (int k = j + 1; k <= N - 2; k++) {
                            // 还是无法做到去重
                            int t = target - arr[i] - arr[j] - arr[k];
                            int idx = binarySearch(arr, k + 1, N, t);
                            if (idx == N || arr[idx] != t) continue;
                            ans.add(Arrays.asList(arr[i], arr[j], arr[k], t));
                        }
                    }
                }
            }
        }
        return ans;

    }

    public int binarySearch(int[] arr, int lo, int hi, int v) {
        while (lo < hi) {
            int mid = (hi - lo) / 2 + lo;
            if (arr[mid] < v) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }*/

    // 错误思路，把他当做DFS了，回顾DFS，我们设定参数，start,sum,cnt,ans,sub,
    // 然后进行DFS遍历，发现DFS遍历写错了， 举个例子{-2，-1，0，1，2} 我们要找到三数之和
    // 下面的代码 并没有进行正确的DFS遍历 因为不会遍历到-2，0，2这样的，他只是进行连续遍历。
    // 所以结果为空

   /*
   public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList();
        Arrays.sort(nums);
        boolean[] seen = new boolean[nums.length];
        for (int i = 0; i < nums.length; i++) {
            dfs(nums, i, 0, seen, target, new LinkedList(), ans);
        }
        return ans;
    }

    public void dfs(int[] nums, int start, int cnt, boolean[] seen, int sum, LinkedList<Integer> sub, List<List<Integer>> ans) {
        if (sum == 0 && cnt == 3) {
            ans.add(new ArrayList(sub));
            return;
        }
        if (sum < 0 && cnt == 3) {
            return;
        }
        if (cnt > 3) {
            return;
        }
        if (start < nums.length && !seen[start]) {
            seen[start] = true;
            sub.add(nums[start]);
            cnt++;
            dfs(nums, start + 1, cnt + 1, seen, sum - nums[start], sub, ans);
            cnt--;
            seen[start] = false;
            sub.pollLast();

        }
    }
    */

    // 16 最接近的3数之和
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) return -1;
        Arrays.sort(nums);
        int d = Integer.MAX_VALUE;
        int N = nums.length;
        int ans = 0;
        for (int i = 0; i < N - 2; i++) {
            int lo = i + 1, hi = N - 1;
            while (lo < hi) {
                int v = nums[i] + nums[lo] + nums[hi];
                if (v == target) return target;
                int diff = Math.abs(v - target);
                if (diff < d) {
                    d = diff;
                    ans = v;
                }
                if (v < target) {
                    lo++;
                } else {
                    hi--;
                }
            }
        }
        return ans;
    }

    // 30 . 串联所有单词的子串 这是一道题意都很难读懂的题目 可能读懂需要hard程序！
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        if (s == null || s.length() == 0 || words == null || words.length == 0) return ans;
        int sLen = words[0].length();
        int nums = words.length;
        int len = sLen * nums;
        Map<String, Integer> cnt = new HashMap<>();
        for (String item : words) {
            cnt.put(item, cnt.getOrDefault(item, 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (i + len > s.length()) break;
            String str = s.substring(i, i + len);
            Map<String, Integer> freq = new HashMap<>();
            for (int j = 0; j + sLen <= str.length(); j = j + sLen) {
                String key = str.substring(j, j + sLen);
                freq.put(key, freq.getOrDefault(key, 0) + 1);
            }
            if (freq.equals(cnt)) {
                ans.add(i);
            }
        }
        return ans;
    }


}








