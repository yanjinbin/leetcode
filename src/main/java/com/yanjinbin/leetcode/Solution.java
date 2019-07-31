package com.yanjinbin.leetcode;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
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

/**
 * top-100-liked-questions
 * https://leetcode.com/problemset/top-100-liked-questions/
 */
public class Solution {

    public static void demo() {
        System.out.println("测试");
    }

    // #1
    public int[] twoSum(int[] nums, int target) {
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


    // #2  两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1;
        ListNode q = l2;
        ListNode curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = p != null ? p.val : 0;
            int y = q != null ? q.val : 0;
            int sum = x + y + carry;
            // 更新carry
            carry = sum / 10;

            curr.next = new ListNode(sum % 10);
            // self update
            curr = curr.next;
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }

        // 某位如果超过10 需要在循环外 更新一次
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        // dummyhead 是0 所以是返回他的下一个节点,作为头部节点
        return dummyHead.next;
    }


    public static ListNode reverseListInt(ListNode head, ListNode newHead) {
        if (head == null) {
            return newHead;
        }
        ListNode next = head.next;
        head.next = newHead;
        return reverseListInt(next, head);
    }

    // 141. Linked List Cycle
    // todo 如何证明呢
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

    // 234 Palindrome Linked List 回文单链表
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
        // 比较一般元素即可
        ListNode slow = head;
        ListNode fast = head;
        Stack<ListNode> s = new Stack<>();
        s.add(head);

        while (fast != null && fast.next != null) {
            slow = slow.next;
            s.push(slow);
            fast = fast.next.next;
        }
        System.out.println("head: " + (head == null ? "null" : head) + "\tslow: " + (slow == null ? "null" : slow.val) + "\tfast: " + (fast == null ? "null " : fast.val) + "\n");
        for (ListNode listNode : s) {
            System.out.println("栈元素 " + listNode.val);
        }
        // System.out.println("===stack end ===");
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
    private ListNode cur;

    public boolean isPalindrome2(ListNode head) {
        cur = head;
        return helper(head);
    }

    boolean helper(ListNode node) {
        if (node == null) return true;
        boolean res = helper(node.next) && (node.val == cur.val);
        if (res) cur = cur.next;
        return res;
    }

    // 反转单链表
    public ListNode reverseSingleLinkedList(ListNode head) {
        ListNode prev = null, next = null;
        ListNode cursor = head;
        while (cursor != null) {
            next = cursor.next;
            cursor.next = prev;
            prev = cursor;
            cursor = next;
        }
        // 这里返回prev 是因为while循环跳出之后cursor = null 说明 prev is tail node了.
        return prev;
    }


    // lt 3 无重复最长子串
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

    // leetcode 15 3sum
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

    // #19. 删除链表的倒数第N个节点
    // 无法处理 corner case,
    public ListNode removeNthFromEnd_poor(ListNode head, int n) {
        ListNode delayNode = head, startNode = head;
        int start = 0;
        while (startNode.next != null) {
            startNode = startNode.next;
            start++;
            if (start > n) {
                delayNode = delayNode.next;
            }
        }
        // 只有一个节点的情况下 就很难处理了 NPE
        ListNode next = delayNode.next.next;
        delayNode.next.next = null;
        delayNode.next = next;
        return head;
    }

    //// #19. 删除链表的倒数第N个节点, 完美处理corner case
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // init dummy node
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;
        ListNode delayNode = dummyNode, start = dummyNode;
        for (int i = 0; i < n; i++) {
            start = start.next;
        }
        while (start.next != null) {
            start = start.next;
            delayNode = delayNode.next;
        }
        delayNode.next = delayNode.next.next;
        // 参考如下代码 http://bit.ly/2xo4dEI
        // return head; 错误的原因在于head 节点也有可能Update 为Null阿  在更新code---> " delayNode.next = delayNode.next.next; "
        return dummyNode.next;
    }

    //  TODO 需要深刻理解 [LeetCode] 4. Median of Two Sorted Arrays 两个有序数组的中位数 http://bit.ly/2ROgk7B
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;
        return (findKth(nums1, 0, nums2, 0, left) + findKth(nums1, 0, nums2, 0, right)) / 2.0;
    }

    public int findKth(int[] nums1, int i, int[] nums2, int j, int k) {
        if (i >= nums1.length) return nums2[j + k - 1];
        if (j >= nums2.length) return nums1[i + k - 1];
        if (k == 1) return Math.min(nums1[i], nums2[j]);
        int midVal1 = (i + k / 2 - 1 < nums1.length) ? nums1[i + k / 2 - 1] : Integer.MAX_VALUE;
        int midVal2 = (j + k / 2 - 1 < nums2.length) ? nums2[j + k / 2 - 1] : Integer.MAX_VALUE;
        if (midVal1 < midVal2) {
            return findKth(nums1, i + k / 2, nums2, j, k - k / 2);
        } else {
            return findKth(nums1, i, nums2, j + k / 2, k - k / 2);
        }
    }

    //  #5 最长回文字符串 5. Longest Palindromic Substring 官方题解垃圾的一点就是 start 和 end的更新问题 有问题
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
                System.out.println("update\t i:" + i + "\tstart:" + start + "\tend:" + end + "\tlen:" + len);
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        System.out.println("final! start:" + start + "\tend:" + end);
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    // 最长回文子串 最佳解法
    // http://bit.ly/2KMyIgk
    private int lo, maxLen;

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
        if (maxLen < k - j - 1) {
            // System.out.println("before，maxLen:"+maxLen+"\tlo:"+lo+"\tk:"+k+"\tj:"+j);
            lo = j + 1;
            maxLen = k - j - 1;
            // System.out.println("after，maxLen:"+maxLen+"\tlo:"+lo+"\tk:"+k+"\tj:"+j);
        }
    }

    // 21. 合并两个有序链表Copy Merge Two Sorted Lists

    public ListNode mergeTwoLists_1(ListNode l1, ListNode l2) {
        ListNode dummyNode = new ListNode(0);
        ListNode cur = dummyNode;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                cur.next = new ListNode(l1.val);
                l1 = l1.next;
            } else {
                cur.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            cur = cur.next;
        }
        cur.next = l1 == null ? l2 : l1;
        return dummyNode.next;

    }

    // 21. Merge Two Sorted Lists

    // 错误的解法
    public ListNode mergeTwoLists_0(ListNode l1, ListNode l2) {
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
    }
//    // 递归解法

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
    // 23. 合并K个排序链表 Merge k Sorted Lists
    // http://bit.ly/2LtXUbI

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return mergeKLists(lists, 0, lists.length - 1);
    }

    // 采用分治思想，递归解决此问题
    public ListNode mergeKLists(ListNode[] lists, int start, int end) {
        if (start > end) {
            return null;
        } else if (start == end) {
            return lists[start];
        }
        int mid = (end - start) / 2 + start;
        ListNode l1 = mergeKLists(lists, start, mid);
        ListNode l2 = mergeKLists(lists, mid + 1, end);
        return mergeTwoSortedList(l1, l2);
    }

    //  22. 括号生成  回溯法(http://bit.ly/2KPYQHi)  假如我先添加一个左括号，next 接下来我可以添加
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList();
        backtrack(ans, "", 0, 0, n);
        return ans;
    }

    public void backtrack(List<String> ans, String cur, int open, int close, int max) {
        // 问题的解 达成
        if (cur.length() == max * 2) {
            ans.add(cur);
            return;
        }
        // 达到终点，问题的解 未达成，回溯
        // 问题的解 不达成
        // return

        // DFS探索
        if (close < open)
            backtrack(ans, cur + ")", open, close + 1, max);
        if (open < max)
            backtrack(ans, cur + "(", open + 1, close, max);
    }

    // 11. 盛最多水的容器 双指针法，左右移动时候，选择移动 高度短的 可能能增加面积 如果是盛水最少的容器呢
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

    // 42. 接雨水 trap rain water  http://bit.ly/2RKoy0k
    public int trap1(int[] height) {
        // 遍历一次，找左边最大值，然后遍历一次，找右边最大值，选个最大的dp[i],if dp[i] > height[i],则add
        int res = 0, mx = 0, n = height.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; ++i) {
            dp[i] = mx;
            mx = Math.max(mx, height[i]);
        }
        // reset
        mx = 0;
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = Math.min(dp[i], mx);
            mx = Math.max(mx, height[i]);
            if (dp[i] - height[i] > 0) res = res + dp[i] - height[i];
        }
        return res;
    }

    //  42. 接雨水 trap rain water 双指针法
    public int trap2(int[] height) {
        int res = 0, l = 0, r = height.length - 1;
        while (l < r) {
            int min = Math.min(height[l], height[r]);
            if (min == height[l]) {
                l++;
                while (l < r && height[l] < min) {
                    System.out.println("l:" + l + "r:" + r);
                    res = res + min - height[l++];
                }
            } else {
                r--;
                while (l < r && height[r] < min) {
                    System.out.println("l:" + l + "r:" + r);
                    res = res + min - height[r--];
                }
            }

        }
        return res;
    }

    // 更简洁写法
    public int trap3(int[] height) {
        int res = 0, l = 0, r = height.length - 1, level = 0;
        while (l < r) {
            int lower = height[height[l] < height[r] ? l++ : r--];
            level = Math.max(level, lower);
            System.out.println("l:" + l + "r:" + r);
            res = res + level - lower;
        }
        return res;
    }

    public int trap4(int[] height) {
        Stack<Integer> s = new Stack<>();
        int i = 0, n = height.length, res = 0;
        while (i < n) {
            if (s.isEmpty() || height[i] <= height[s.peek()]) {
                s.push(i++);
            } else {
                int t = s.pop();
                if (s.isEmpty()) continue;
                res += (Math.min(height[i], height[s.peek()]) - height[t]) * (i - s.peek() - 1);
            }
        }
        return res;
    }

    // 206 反转链表 todo 递归解法 需要crack
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cursor = head;
        while (cursor != null) {
            ListNode temp = cursor.next;
            // 更新cursor.next 指向
            cursor.next = prev;
            prev = cursor;
            // iterator
            // cursor = cursor.next 为什么错了 因为cursor.next 已经换了对象了
            cursor = temp;
        }
        return prev;
    }

    // 206 Leetcode 错误解答
    public ListNode reverseListBad1(ListNode head) {
        ListNode prev = null;
        ListNode cursor = head;
        while (cursor != null) {
            cursor.next = prev;
            ListNode temp = cursor;
            prev = temp;
            cursor = cursor.next;
        }
        return prev;
    }

    // 206 Leetcode 错误解答
    public ListNode reverseListBad2(ListNode head) {
        ListNode prev = null;
        ListNode cursor = head;
        while (cursor != null) {
            ListNode temp = cursor;
            cursor = cursor.next;
            prev = cursor;
            prev.next = temp;

        }
        return prev;
    }


    //  31. 下一个排列 首先理解字典序  找下一个字典序更大的 如果最大了 就全局升序排列了
    //  题解连接 http://bit.ly/2RS8Wbd
    //  总结 这道底模本身不难
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0) return;

        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) i--; // 找到第一个破坏 descend order -->i
        if (i >= 0) {
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) j--;// i 指向元素 从右往左找第一个
            swap(nums, i, j); //
        }
        reverse0(nums, i + 1, nums.length - 1);

    }

    public void swap(int[] nums, int i, int j) {
        if (i == j) return;
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public void reverse0(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i++, j--);
        }
    }

    // 48 旋转图像 todo 这道题目 需要观察下规律
    //  http://bit.ly/2RNX8a6
    //  http://bit.ly/2JmVgB7
    //  展示每次移动的元素 https://photos.app.goo.gl/LaeDGURidfWi1oLa7
    public void rotate(int[][] matrix) {
        int n = matrix.length;
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
        }

    }

    // 53. 最大子序和
    public int maxSubArray(int[] nums) {
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

    // 53. 最大子序和 错误解法
    public int maxSubArray1(int[] nums) {
        int cursum = 0;
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (cursum > 0) {
                cursum = cursum + nums[i];
            }
            if (res > cursum) {
                res = cursum;
            }
        }
        return res;
    }

    // leetcode 70 climbing stairs
    // todo follow up问题 http://bit.ly/2SplozJ
    // 迭代形式
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
        // 为什么 n+1长度呢 这个怎么算出来的呢
        // 首先memo 是用来存储数组用的
        //  递归一次 n 分别-1 -2
        // 递归终止条件是n <=1
        // 假设 n= 1 可以推导出  climbHelper(n,memo)[memo(n)] --> memo[2]=climber(1)+climber(0),或者memo[3]=climber(2)+climber(1)--> memo[2]=climber(1)+climber(0),因此递归的时候memo数组的长度是n+1啦

        int[] memo = new int[n + 1];
        return climbHelper(n, memo);
    }

    private int climbHelper(int n, int[] memo) {
        if (n <= 1) return 1;
        if (memo[n] > 0) {
            //  System.out.println("n:" + n + "memo:" + memo[n]);
            return memo[n];
        }
        return memo[n] = climbHelper(n - 1, memo) + climbHelper(n - 2, memo);
    }
    // follow up 问题

    /**
     * @param n 总步长
     * @param m 每一次最能能跨的最大台阶数
     * @return
     */
    public int climbStairFU(int n, int m) {
        int stepCount = 0;
        if (n == 0) {
            return 1;
        }
        if (n >= m) {
            for (int i = 1; i <= m; i++) {
                stepCount = stepCount + climbStairFU(n - i, m);
            }
        } else {
            stepCount += climbStairFU(n, n);
        }
        return stepCount;
    }

    // 64. 最小路径和  这是一道很经典的题目  哈哈 😁  忠于自己亲手做出来了 😘
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

    // 75. 颜色分类  只要遇到 0和2 就进行交换即可
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

    // leetcode 78 子集和
    public List<List<Integer>> subsets1(int[] nums) {
        Arrays.sort(nums);
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

    // dfs
    public List<List<Integer>> subsets2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        List empty = new ArrayList();
        // 忘记添加empty了
        res.add(empty);
        dfsAdd(0, nums, res, empty);
        return res;
    }

    // subset 类型 写错了 理解不到位 写成List<List<Integer>> subset 类型了。
    public void dfsAdd(int level, int[] nums, List<List<Integer>> res, List<Integer> subset) {
        // 注意循环便利次数阿  少写了一个=
        if (level >= nums.length)
            return;
        subset = new ArrayList<>(subset);
        dfsAdd(level + 1, nums, res, subset);
        subset.add(nums[level]);
        res.add(subset);
        dfsAdd(level + 1, nums, res, subset);
    }
    // 位运算法

    // backtrack 回溯算法


    // leetcode 79 单次搜索
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || (board.length == 1 && board[0].length == 0)) return false;
        int collen = board.length;
        int rowLen = board[collen - 1].length;
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

    public boolean dfsSearch(int i, int j, int colLen, int rowLen, char[][] board, int cursor, char[] word, boolean[][] visited) {
        if (cursor == word.length) {
            return true;
        }
        // board[i][j] != word[cursor]  放到最后面阿  . visit数组 条件 不要漏掉哦
        if (i < 0 || j < 0 || i >= colLen || j >= rowLen || visited[i][j] || board[i][j] != word[cursor]) {
            return false;
        }
        visited[i][j] = true;
        boolean exist = dfsSearch(i + 1, j, colLen, rowLen, board, cursor + 1, word, visited) ||
                dfsSearch(i, j + 1, colLen, rowLen, board, cursor + 1, word, visited) ||
                dfsSearch(i - 1, j, colLen, rowLen, board, cursor + 1, word, visited) ||
                dfsSearch(i, j - 1, colLen, rowLen, board, cursor + 1, word, visited);
        // 这是一个小细节阿 看到了没??!!!
        // reset 重置哦
        visited[i][j] = false;
        return exist;
    }

    private boolean exist = false;

    // leetcode 139  单词拆分 http://bit.ly/2Ld41Bt 这是一道DP题目
    public boolean wordBreak(String s, List<String> wordDict) {
        // 为什么要加1呢,因为要避免空数组, 这和哑节点类似(避免长度1的链表)
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        // 关于对下面dp 状态转移方程的一点思考, 那就是 状态转移条件(内循环的if更新)和状态转移的更新迭代器(dict.contain(s.substring(j,i)---->dp[i]=true)
        // i有写错了呢 !!! i要从1开始哦,这样最后一个字符串才能包含进去阿  substring 左闭右开
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


    // leetcode 139 错误做法
    public boolean wordBreakbad(String s, List<String> wordDict) {
        splitHelper(s, s.length(), 0, wordDict);
        return exist;
    }

    public void splitHelper(String s, int length, int cursor, List<String> wordDict) {
        if (exist) return;
        if (cursor > length) exist = false;
        if (cursor == length) exist = true;
        System.out.println(s + " " + cursor);
        String ret = s.substring(cursor);
        for (String word : wordDict) {
            if (ret.startsWith(word)) {
                cursor = cursor + word.length();
                splitHelper(s, length, cursor, wordDict);
            }
        }
    }

    // 这道题目 关键在于 负数 以及0的处理
    // Leetcode 152 乘积最大的连续 子序列 http://bit.ly/2RZ9AUo
    // 最大的最小的有可能互换  以及0 会使得  一切乘积都为0
    public int maxProduct(int[] nums) {
        int[] f = new int[nums.length];
        int[] g = new int[nums.length];
        // init
        int res;
        res = f[0] = g[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            f[i] = Math.max(Math.max(nums[i], f[i - 1] * nums[i]), nums[i] * g[i - 1]);
            g[i] = Math.min(Math.min(nums[i], f[i - 1] * nums[i]), nums[i] * g[i - 1]);
            res = Math.max(res, f[i]);
        }
        return res;
    }

    // 287. 寻找重复数
    // 可以把index对应的value当做一个状态值 那么 value重复的化 就相当于是存在还了,可以使用floyd算法来检测
    // Floyd算法wiki ---->   http://bit.ly/2S1omdy
    public int findDuplicate0(int[] nums) {
        // Find the intersection point of the two runners.
        int tortoise = nums[0];
        int hare = nums[0];
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

    //  这种二分法还是比较参见的 但是也存在多钟限制阿 中间数的计算近似 median=(right+left)/2;
    public int findDuplicate1(int[] nums) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (right + left) / 2;
            int cnt = 0;
            for (int num : nums) {
                if (num <= mid) {
                    cnt++;
                }
            }
            if (cnt <= mid) left = mid + 1;
            else right = mid;

        }
        return right;
    }


    // 448. 找到所有数组中消失的数字
    // http://bit.ly/2S1ZqT0 这个解释的通
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < nums.length; i++) {
            int val = Math.abs(nums[i]) - 1;
            if (nums[val] > 0) {
                nums[val] = -nums[val];
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                ret.add(i + 1);
            }
        }
        return ret;
    }

    // http://bit.ly/2LuhFQO
    // 142. 环形链表 II
    // 环检测 https://leetcode-cn.com/problems/linked-list-cycle-ii/
    // 解除环 环长度
    public ListNode detectCycle0(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                // 错误写法
                // return slow;
                break;
            }
        }
        //  条件没写全 if (fast != slow)
        if (fast != slow || fast == null || fast.next == null) return null;
        slow = head;
        ListNode m = fast;
        while (m != slow) {
            slow = slow.next;
            m = m.next;
        }
        return slow;
    }

    private ListNode getIntersect(ListNode head) {
        ListNode tortoise = head;
        ListNode hare = head;

        // A fast pointer will either loop around a cycle and meet the slow
        // pointer or reach the `null` at the end of a non-cyclic list.
        while (hare != null && hare.next != null) {
            //  System.out.println("1");
            tortoise = tortoise.next;
            hare = hare.next.next;
            if (tortoise == hare) {
                return tortoise;
            }
        }

        return null;
    }

    public ListNode detectCycle2(ListNode head) {
        if (head == null) {
            return null;
        }

        // If there is a cycle, the fast/slow pointers will intersect at some
        // node. Otherwise, there is no cycle, so we cannot find an e***ance to
        // a cycle.
        ListNode intersect = getIntersect(head);
        if (intersect == null) {
            return null;
        }

        // To find the entrance to the cycle, we have two pointers traverse at
        ListNode ptr1 = head;
        ListNode ptr2 = intersect;
        while (ptr1 != ptr2) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }
        return ptr1;

    }

    // LT 416. 分割等和子集  经典题目 dp
    public boolean canPartition0(int[] nums) {
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
                //  System.out.println(i + "\t" + num);
                dp[i] = dp[i] || dp[i - num];
            }
        }
        return dp[target];
    }

    public boolean canPartition1(int[] nums) {
        // todo DP之外 还可以用bitset来解 真滴有意思
        return false;
    }

    // 300. 最长上升子序列
    // http://bit.ly/2S18Z4A 看动画就能理解为什么了 哈哈
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int maxLen = 1;
        for (int i = 1; i < nums.length; i++) {
            int maxVal = 0;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    maxVal = Math.max(maxVal, dp[j]);
                }
            }
            dp[i] = maxVal + 1;
            maxLen = Math.max(dp[i], maxLen);
        }
        return maxLen;

    }

    // 审题审错了
    public int lengthOfLISbad(int[] nums) {
        int maxLen = 0, prevLen = 0;
        int start = 0, end = 0;
        while (end < nums.length - 1) {
            if (nums[end] < nums[++end]) {
                maxLen++;
            } else {
                //  System.out.println(start + "\t" + end + "\t" + maxLen + "\t" + prevLen);
                // reset
                start = end;
                prevLen = Math.max(maxLen, prevLen);
                maxLen = 0;
            }
        }
        return prevLen;
    }

    // 121. 买卖股票的最佳时机
    public int maxProfit(int[] prices) {
        if (prices.length == 0) return 0;
        int min = prices[0];
        int gap = 0;
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            gap = Math.max(prices[i] - min, gap);
        }
        return gap;
    }

    // 摩尔投票法 仔细想想 还是对的 因为不管如何排列,众数 频次肯定>=1阿  whatever even or odd
    // 写的还是啰嗦,主要在于初始化步骤
    // 另外一种哈希算法 就不做了
    // 还有 分治算法来做
    public int majorityElement0(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int cnt = 1;
        int major = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == major) {
                cnt++;
            } else {
                cnt--;
                if (cnt == 0) {
                    major = nums[i];
                    cnt = 1;
                }
            }
        }
        return major;
    }

    public int majorityElement1(int[] nums) {
        if (nums.length == 0) return 0;
        int cnt = 0;
        int major = 0;
        for (int i = 0; i < nums.length; i++) {
            if (cnt == 0) {
                //reset
                major = nums[i];
                cnt = 1;
            } else if (major == nums[i]) {
                cnt++;
            } else {
                cnt--;
            }
        }
        return major;
    }

    public int majorityElement2(int[] nums) {
        if (nums.length == 0) return 0;
        int cnt = 0;
        int major = 0;
        for (int i = 0; i < nums.length; i++) {
            if (cnt == 0) {
                //reset
                major = nums[i];
            }
            cnt += (nums[i] == major) ? 1 : -1;
        }
        return major;
    }

    // 283. 移动零
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
    // 先排序
    public int findUnsortedSubarray0(int[] nums) {
        int start = 0, end = nums.length - 1;
        int[] bak = Arrays.copyOf(nums, nums.length);
        Arrays.sort(bak);
        while (start < nums.length && nums[start] == bak[start]) start++;
        while (end > start && nums[end] == bak[end]) end--;
        return end - start + 1;
    }

    public int findUnsortedSubarray1(int[] nums) {
        int n = nums.length;
        // -1 和 -2 也是有意义的哦 end-start+1 = 0!!!
        int start = -1;
        int end = -2;
        int mn = nums[n - 1];
        int mx = nums[0];
        for (int i = 1; i < n; i++) {
            mx = Math.max(mx, nums[i]);
            mn = Math.min(mn, nums[n - 1 - i]);
            if (mx > nums[i]) end = i;
            if (mn < nums[n - 1 - i]) start = n - 1 - i;
        }
        System.out.println("end" + end + "start" + start);
        return end - start + 1;
    }

    // 560. 和为K的子数组
    // corner case
    // map.put(0,1) 这个为什么需要呢 道友门 注意下列入参
    //比如 [2,2,2,2,2] k=4 ; [1,3,2,2,4] k=4 ; 以及 [0,0,0,0] k=0
    //就是从起始数开始求的连续和为K 那么 这种corner case 你就需要 放上map.put(0,1) 0 1可以理解为0出现的次数为1 相当于 sum(0,i)=k --> sum(0,i)-k =0
    //同理 count +=map.get(sum-k) 而不是count++哈哈
    //自己可以 画个表格 列出nums[i] sum sum-k 函数count(k) count(sum-k)
    // 花花酱视频  http://bit.ly/2S3K2We
    public int subarraySum(int[] nums, int k) {
        int count = 0, sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        //  map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    // 56. 合并区间
    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) return intervals;
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
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

    //  215. 数组中的第K个最大元素
    // quick sort思想
    public int findKthLargest0(int[] nums, int k) {
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
            if (nums[l] > pivot) l++;
            if (nums[r] < pivot) r--;
        }
        swap(nums, lo, r);
        return r;
    }


    private static boolean less(int v, int w) {
        if (v == w) return false;   // optimization when reference equals
        return v - w < 0;
    }

    private static void exch(int[] nums, int i, int j) {
        System.out.println("i " + i + " j " + j);
        int swap = nums[i];
        nums[i] = nums[j];
        nums[j] = swap;
    }

    // leetcode 148 排序链表
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
        ListNode merge = merge(l1, l2);
        return merge;

    }

    public ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode l = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                l.next = l1;
                l1 = l1.next;
            } else {
                l.next = l2;
                l2 = l2.next;
            }
            l = l.next;
        }

        while (l1 != null) {
            l.next = l1;
            l1 = l1.next;
            l = l.next;
        }
        while (l2 != null) {
            l.next = l2;
            l2 = l2.next;
            l = l.next;
        }
        // System.out.println("dummy:" + dummy.val + "next:" + dummy.next);
        return dummy.next;
    }


    int[] items;

    public void swap(int a, int b) {
        int tmp = this.items[a];
        this.items[a] = this.items[b];
        this.items[b] = tmp;
    }


    public int partition(int left, int right, int pivot_index) {
        int pivot = this.items[pivot_index];
        // 1. move pivot to end
        swap(pivot_index, right);
        int store_index = left;

        // 2. move all smaller elements to the left
        for (int i = left; i <= right; i++) {
            if (this.items[i] < pivot) {
                swap(store_index, i);
                store_index++;
            }
        }

        // 3. move pivot to its final place
        swap(store_index, right);

        return store_index;
    }

    public int quickselect(int left, int right, int k_smallest) {
    /*
    Returns the k-th smallest element of list within left..right.
    */

        if (left == right) // If the list contains only one element,
            return this.items[left];  // return that element

        // select a random pivot_index
        Random random_num = new Random();
        int pivot_index = left + 1;

        pivot_index = partition(left, right, pivot_index);

        // the pivot is on (N - k)th smallest position
        if (k_smallest == pivot_index)
            return this.items[k_smallest];
            // go left side
        else if (k_smallest < pivot_index)
            return quickselect(left, pivot_index - 1, k_smallest);
        // go right side
        return quickselect(pivot_index + 1, right, k_smallest);
    }

    public int findKthLargest1(int[] nums, int k) {
        this.items = nums;
        int size = nums.length;
        // kth largest is (N - k)th smallest
        return quickselect(0, size - 1, size - k);
    }

    //  大小堆来做
    public int findKthLargest2(int[] nums, int k) {
        // init heap 'the smallest element first'
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>(Comparator.comparingInt(n -> n));

        // keep k largest elements in the heap
        for (int n : nums) {
            heap.add(n);
            if (heap.size() > k)
                heap.poll();
        }

        // output
        return heap.poll();
    }

    //  238. 除自身以外数组的乘积 至少需要2次遍历来
    public int[] productExceptSelf(int[] nums) {
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
        // System.out.println(Arrays.toString(res));
        return res;
    }

    // 33. 搜索旋转排序数组
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < nums[right]) {
                if (nums[mid] < target && nums[right] >= target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }


    // 34. 在排序数组中查找元素的第一个和最后一个位置
    //  这个方法相当low逼阿 哈哈
    // 看别人的解题思路 有一种卧槽 还可以这样子的感觉!
    int minIdx = Integer.MAX_VALUE;
    int maxIdx = Integer.MIN_VALUE;

    public int[] searchRange0(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }
        int left = 0, right = nums.length - 1;
        search(nums, left, right, target);
        if (minIdx == Integer.MAX_VALUE || maxIdx == Integer.MAX_VALUE) return new int[]{-1, -1};
        return new int[]{minIdx, maxIdx};
    }

    public void search(int[] nums, int left, int right, int target) {
        int mid = left + (right - left) / 2;
        if (left > right) {
            return;
        }
        if (left == right) {
            if (nums[left] == target) {
                maxIdx = Math.max(maxIdx, mid);
                minIdx = Math.min(minIdx, mid);
            }
            return;
        }
        if (nums[mid] < target) {
            search(nums, mid + 1, right, target);
        } else if (nums[mid] > target) {
            search(nums, left, mid - 1, target);
        } else if (nums[mid] == target) {
            maxIdx = Math.max(maxIdx, mid);
            minIdx = Math.min(minIdx, mid);
            search(nums, mid + 1, right, target);
            search(nums, left, mid - 1, target);
        }
    }
/*
    public int[] searchRange1(int[] items, int target) {

    }

    public int searchBinary(int[] items, int target) {
        int left = 0, right = items.length - 1;
        int mid = left + (right - left) >> 2;
        while (left <= right) {
            if (items[mid] < target) {
                left = mid + 1;
            } else if (items[mid] > target) {
                right = mid - 1;
            } else if (items[mid] == target) {
                left++;
               // searchBinary(items,left,right)
            }
        }
        return left;
    }*/

    // 62. 不同路径
    public int uniquePaths(int m, int n) {
        int count = 0;
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


    //39. 组合总和

    // http://bit.ly/2XHHBi2  这个方法感觉还是不够优雅阿
    public List<List<Integer>> combinationSum1(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(candidates, target, res, 0, new ArrayList<Integer>());
        return res;
    }

    private void backtrack(int[] candidates, int target, List<List<Integer>> res, int i, ArrayList<Integer> tmp_list) {
        if (target < 0) return;
        if (target == 0) {
            res.add(tmp_list);
        }
        for (int start = i; start < candidates.length; start++) {
            target = target - candidates[i];
            tmp_list.add(candidates[i]);
            backtrack(candidates, target, res, start, tmp_list);
            // 这个方法难看懂  很容易出错阿
            tmp_list.remove(tmp_list.size() - 1);
        }
    }


    // 77  组合
    public List<List<Integer>> combine(int n, int k) {
        return null;
    }

    // follow up https://www.cnblogs.com/grandyang/p/4358831.html
    public List<List<Integer>> combine1(int n, int k) {
        return null;
    }


    // 46. 全排列
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

    // 739. 每日温度  递增栈
    public int[] dailyTemperatures(int[] temperatures) {
        Stack<Integer> stack = new Stack<>();
        int[] ret = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            // 维护一个有序的栈,仔细观察可以 发现 ret[idx]不是按顺序计算出她的等待天数的哦!!
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                Integer idx = stack.pop();
                // 不是ret[i]哦
                ret[idx] = i - idx;
            }
            stack.push(i);
        }
        return ret;
    }

    // 84. 柱状图中最大的矩形  局部峰值 选取第一个转折点(从大变小的那个点)  O(N²)
    public int largestRectangleArea0(int[] heights) {
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            if (i + 1 < heights.length && heights[i] <= heights[i + 1]) continue;
            int minV = heights[i];
            for (int j = i; j >= 0; j--) {
                minV = Math.min(minV, heights[j]);
                int tmpArea = minV * (i - j + 1);
                maxArea = Math.max(tmpArea, maxArea);
            }
        }
        return maxArea;
    }

    // 和解法1 局部峰值思想类似
    // 优化点在于 内层for循环的时候 j-- 应该在哪里停止的问题 ?
    //解法2 给出了
    //输入数组是[2,1,5,6,2,3],
    //当 j 回退到 值 1 指向的idx 为1 的时候，
    //就应该停止 比较 面积大小 。因为值1 < 值2
    //紧接着 idx=5的值2 压入栈
    // 表现在 stack.pop()和 stack.peek()操作
    //可以结合博主提供的那篇博文 阅读思考或者debug下
    //
    //第二个疑点 为什么h长度 要多+1. 因为， 数组最后一个值 3 右边的值永远是0。这个是必定要进行面积大小比较的。
    //时间复杂度应该还是O(N²)
    public int largestRectangleArea1(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        int maxArea = 0;
        int[] h;
        h = Arrays.copyOf(heights, heights.length + 1);
        while (i < h.length) {
            if (stack.isEmpty() || h[stack.peek()] <= h[i]) {
                stack.push(i);
                i++;
            } else {
                int t = stack.pop();
                maxArea = Math.max(maxArea, h[t] * (stack.isEmpty() ? i : i - stack.peek() - 1));
            }
        }
        return maxArea;
    }

    // 85. 最大矩形
    public int maximalRectangle0(char[][] matrix) {
        int res = 0;
        int length = 0;
        int[] height = new int[length];
        for (int i = 0; i < matrix.length; i++) {
            // reset
            height = Arrays.copyOf(height, Math.max(length, matrix[i].length));
            for (int j = 0; j < matrix[i].length; j++) {
                height[j] = (matrix[i][j] == '0' ? 0 : (height[j] + 1));
            }
            res = Math.max(res, largestRectangleArea1(height));
        }
        return res;
    }

    //todo 其他解法待做 http://bit.ly/2Ga4HmE
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int res = 0;
        int[][] hMax = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == '0') continue;
                if (j > 0) {
                    hMax[i][j] = hMax[i][j - 1] + 1;
                } else {
                    hMax[i][0] = 1;
                }

            }
        }
        // todo 哈
        return 1;
    }

    //128. 最长连续序列 map set solve

    public int longestConsecutive(int[] nums) {
        int res = 0;
        Set<Integer> set = new HashSet<>(nums.length);
        for (int num : nums) {
            set.add(num);
        }

        for (int num : nums) {

            int cmp = 1;
            set.remove(num);
            int left = num - 1;
            while (set.contains(left)) {
                set.remove(left);
                cmp++;
                left--;
            }
            int right = num + 1;
            while (set.contains(right)) {
                set.remove(right);
                cmp++;
                right++;
            }
            res = Math.max(res, cmp);
        }
        return res;

    }

    //  接下去 进入 二叉树专题
    // 94. 二叉树的中序遍历 递归做法
    // tips: 搞清楚 树的迭代是有轮回的 也就是说 中序遍历的左右子树要看清楚是哪个部分,子树层层递进的起点
    public List<String> inorderTraversal0(TreeNode root) {
        List<String> ret = new ArrayList<>();
        inorderHelper(root, ret);
        return ret;
    }

    private void inorderHelper(TreeNode root, List<String> ret) {
        if (root == null) return;
        inorderHelper(root.left, ret);
        ret.add(root.name);
        inorderHelper(root.right, ret);
    }

    // 解法2  栈来做
    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            ret.add(cur.val);
            cur = cur.right;
        }
        return ret;
    }

    // 解法 3 Morris遍历算法 todo 有点绕 ,  理解起来很麻烦 http://bit.ly/2jXmyW5
    public List<Integer> inorderTraversal2(TreeNode root) {
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
                    pre.right = root;
                    root = root.left;
                } else {
                    pre.right = null;
                    ret.add(root.val);
                    root = root.right;
                }
            }

        }
        return ret;
    }

    // 102. 二叉树的层次遍历
    public List<List<Integer>> levelOrder0(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
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
        // 为什么要加上这一判断呢 因为阿  index >= size的时候 就会包index out of range 错误了 tips 值得注意的点
        if (ret.size() == level) {
            ret.add(new ArrayList<>());
        }

        ret.get(level).add(root.val);
        levelHelper(root.left, level + 1, ret);
        levelHelper(root.right, level + 1, ret);
    }

    // 144. 二叉树的前序遍历 递归玩法
    public List<Integer> preorderTraversal0(TreeNode root) {
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

    // 144. 二叉树的前序遍历 栈的做法
    public List<Integer> preorderTraversal1(TreeNode root) {
        if (root == null) return Collections.EMPTY_LIST;
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        List<Integer> ret = new ArrayList<>();
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            ret.add(cur.val);
            // 先压入右边的因为右边的后访问阿
            if (cur.right != null) {
                stack.push(cur.right);
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
        return ret;
    }

    // 145. 二叉树的后序遍历 http://bit.ly/2SodiqQ
    public List<Integer> postorderTraversal0(TreeNode root) {
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

    public List<Integer> postorderTraversal1(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) return ret;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        // 后续遍历可以首先想到的就是 左右子树均为null--->向上搜寻 出现 左右子树一个为空的时候 你需要 标识上次的递归 必须是左右子树为null
        TreeNode flag = root;
        while (!stack.isEmpty()) {
            TreeNode t = stack.peek();
            if ((t.right == null && t.left == null) || t.left == flag || t.right == flag) {
                ret.add(t.val);
                stack.pop();
                flag = t;
            } else {
                if (t.right != null) stack.push(t.right);
                if (t.left != null) stack.push(t.left);
            }
        }
        return ret;
    }

    //96. 不同的二叉搜索树 卡塔兰数的运用
    // 真的不太会做这种题目阿 mmp 好难
    // http://bit.ly/2LqUWVL
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        return dp[n];
    }

    // 104. 二叉树的最大深度
    //
    public int maxDepth0(TreeNode root) {
        int level = 0;
        return dfsHeightHelper(root, level);
    }

    public int dfsHeightHelper(TreeNode root, int level) {
        if (root == null) return level;
        level++;
        // 写错递归入参了 写了两个root.left
        return Math.max(dfsHeightHelper(root.left, level), dfsHeightHelper(root.right, level));
    }

    //
    public int maxDepth1(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth1(root.left), maxDepth1(root.right));
    }


    // 98. 验证二叉搜索树
    // 没做约束
    //  http://bit.ly/2Sq9V2U
    // 这道题目的基础是理解用递归 栈 以及Morris方法来做 可以参考上述链接
    // 不过更推荐下面这种做法 dfs递归 确定左右边界.
    public boolean isValidBSTBad(TreeNode root) {
        if (root == null) return true;
        return validateTreeNode(root) ? (isValidBSTBad(root.left) && isValidBSTBad(root.right)) : false;
    }

    private static boolean validateTreeNode(TreeNode node) {
        if (node.right == null && node.left == null) return true;
        if (node.right == null) {
            return node.val > node.left.val;
        }
        if (node.left == null) {
            return node.val < node.right.val;
        }
        return node.val > node.left.val && node.val < node.right.val;
    }

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

    // 101. 对称二叉树递归左右对称即可。 迭代做法 两个队列放入元素顺序需要做到对称
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


    // 114. 二叉树展开为链表
    // http://bit.ly/2LtZDhc
    // 看懂图解哦
    public void flatten(TreeNode root) {
        if (root == null) return;
        flatten(root.left);
        flatten(root.right);
        TreeNode tmp = root.right;
        root.right = root.left;
        root.left = null;
        while (root.right != null) {
            root = root.right;
        }
        root.right = tmp;
    }

    // 226. 翻转二叉树
    public TreeNode invertTree(TreeNode root) {
        invertHelper(root);
        return root;
    }

    public void invertHelper(TreeNode root) {
        if (root == null) return;
        invertHelper(root.left);
        invertHelper(root.right);
        // 交换
        TreeNode tmp = root.right;
        root.right = root.left;
        root.left = tmp;
    }


    int counter;

    // 437. 路径总和 III  思路  http://bit.ly/2LukqSa
    public int pathSum1(TreeNode root, int sum) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        dfsPathSumHelper(root, sum, 0, queue);
        return counter;
    }

    public void dfsPathSumHelper(TreeNode node, int sum, int curSum, LinkedList<TreeNode> queue) {
        if (node == null) return;
        curSum = curSum + node.val;
        queue.add(node);
        if (curSum == sum) counter = counter + 1;
        int tmp = curSum;
        for (int i = 0; i < (queue.size() - 1); i++) {
            tmp = tmp - queue.get(i).val;
            if (tmp == sum) counter = counter + 1;
        }
        dfsPathSumHelper(node.left, sum, curSum, queue);
        dfsPathSumHelper(node.right, sum, curSum, queue);
        // 维护队列
        queue.pollLast();
    }

    // 易懂 不好想出来
    public int pathSum2(TreeNode root, int sum) {
        if (root == null) return 0;
        return pathHelper(root, sum) + pathSum2(root.left, sum) + pathSum2(root.right, sum);
    }

    public int pathHelper(TreeNode root, int sum) {
        if (root == null) return 0;
        sum = sum - root.val;
        return (sum == 0 ? 1 : 0) + pathHelper(root.left, sum) + pathHelper(root.right, sum);
    }


    int cnt;

    // 错的阿
    public int pathSum0(TreeNode root, int sum) {
        dfsPathSumHelper0(root, root, sum, sum);
        return cnt;
    }

    public void dfsPathSumHelper0(TreeNode root, TreeNode cur, int sum, int remainder) {
        if (root == null || cur == null) return;
        if ((cur.val - remainder) == 0) {
            cnt = cnt + 1;
            // reset
            dfsPathSumHelper0(root.left, root.left, sum, sum);
            dfsPathSumHelper0(root.right, root.right, sum, sum);
        }
        // continue
        dfsPathSumHelper0(root, cur.left, sum, remainder - root.val);
        dfsPathSumHelper0(root, cur.right, sum, remainder - root.val);


        dfsPathSumHelper0(root.left, root.left, sum, sum);
        dfsPathSumHelper0(root.right, root.right, sum, sum);
    }

    // 538. 把二叉搜索树转换为累加树  BST 中序遍历 满足顺序关系  先访问右子树-->root-->左子树 降序排列
    int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        convert(root);
        return root;
    }

    public void convert(TreeNode cur) {
        if (cur == null) return;
        convertBST(cur.right);
        cur.val = cur.val + sum;
        sum = cur.val;
        convertBST(cur.left);
    }

    public TreeNode convertBST1(TreeNode root) {
        dfsPreSumHelper1(root);
        return root;
    }

    public int dfsPreSumHelper1(TreeNode root) {
        if (root == null) return 0;
        int left = dfsPreSumHelper1(root.left);
        int right = dfsPreSumHelper1(root.right);
        int cmp = root.val;
        boolean cl = cmp < left;
        boolean cr = cmp < right;
        boolean lr = left < right;
        if (cl && lr) {
            cmp = cmp + left + right;
            left = left + right;
        }
        if (cr && !lr) {
            cmp = cmp + left + right;
            right = right + left;
        }
        // 6种排列顺序关系组合了(A3) 那就很麻烦了 不再继续写下去
        return root.val;
    }

    // 617. 合并二叉树
    public TreeNode mergeTrees0(TreeNode t1, TreeNode t2) {
        TreeNode node = new TreeNode(0);
        return mergeHelper(t1, t2, node);

    }

    public TreeNode mergeHelper(TreeNode t1, TreeNode t2, TreeNode node) {
        if (t1 == null && t2 == null) return null;
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        node.val = t1.val + t2.val;
        TreeNode left = mergeHelper(t1.left, t2.left, new TreeNode(0));
        TreeNode right = mergeHelper(t1.right, t2.right, new TreeNode(0));
        node.left = left;
        node.right = right;
        return node;
    }

    public TreeNode mergeTrees1(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return null;
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        TreeNode node = new TreeNode(t1.val + t2.val);
        node.right = mergeTrees1(t1.right, t2.right);
        node.left = mergeTrees1(t1.left, t2.left);
        return node;
    }

    // 543. 二叉树的直径
    public int diameterOfBinaryTree0(TreeNode root) {
        if (root == null) return 0;
        int left = dfsHeight(root.left);
        int right = dfsHeight(root.right);
        int height = left + right;
        return left > right ? Math.max(diameterOfBinaryTree0(root.left), height) : Math.max(diameterOfBinaryTree0(root.right), height);
    }

    public int dfsHeight(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(dfsHeight(root.left), dfsHeight(root.right));
    }


    // 第二种解法 为什么更快呢 好奇怪?
    int res = 0;

    public int diameterOfBinaryTree1(TreeNode root) {
        DFS(root);
        return res;
    }

    public int DFS(TreeNode root) {
        if (root == null) return 0;
        int left = DFS(root.left);
        int right = DFS(root.right);
        res = Math.max(res, left + right);
        return Math.max(left, right) + 1;
    }

    //108. 将有序数组转换为二叉搜索树 tips 考察的就是二分法
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        return bstHelper(nums, 0, nums.length - 1);
    }

    public TreeNode bstHelper(int[] nums, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = bstHelper(nums, left, mid - 1);
        root.right = bstHelper(nums, mid + 1, right);
        return root;
    }

    //105. 从前序与中序遍历序列构造二叉树
    public TreeNode buildTreePreIn(int[] preorder, int[] inorder) {
        return buildTrePreIn(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    TreeNode buildTrePreIn(int[] preOrder, int pLeft, int pRight, int[] inorder, int iLeft, int iRight) {
        if (pLeft > pRight || iLeft > iRight) {
            return null;
        }
        int i = 0;
        for (i = iLeft; i <= iRight; i++) {
            //  System.out.println("pLeft " + pLeft + "\t" + preOrder[pLeft] + "\ti: " + i + "\t" + inorder[i]);
            if (preOrder[pLeft] == inorder[i]) {
                break;
            }
        }
        TreeNode root = new TreeNode(preOrder[pLeft]);
        //  参考 http://bit.ly/2LoQpTz
        //  [pLeft+1 , pLeft+(i-iLeft)]是左子树元素区间哦
        root.left = buildTrePreIn(preOrder, pLeft + 1, pLeft + (i - iLeft), inorder, iLeft, i - 1);
        root.right = buildTrePreIn(preOrder, pLeft + i - iLeft + 1, pRight, inorder, i + 1, iRight);
        return root;
    }


    // 106. 从中序与后序遍历序列构造二叉树
    public TreeNode buildTreeInPost(int[] inorder, int[] postorder) {
        return buildTreeInPost(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    public TreeNode buildTreeInPost(int[] inorder, int iLeft, int iRight, int[] postOrder, int pLeft, int pRight) {
        if (pLeft > pRight || iLeft > iRight) {
            return null;
        }
        int i = 0;
        for (i = iRight; i >= iLeft; i--) {
            if (postOrder[pRight] == inorder[i]) break;
        }
        TreeNode root = new TreeNode(postOrder[pRight]);
        root.left = buildTreeInPost(inorder, iLeft, i - 1, postOrder, pLeft, pRight - (iRight - i) - 1);
        //   root.right = buildTreeInPost(inorder, i + 1, iRight, postOrder, pRight - (iRight - i) +1, pRight-1);
        // 为什么 +1 就错了呢 详见 http://bit.ly/2SooOma
        root.right = buildTreeInPost(inorder, i + 1, iRight, postOrder, pRight - (iRight - i), pRight - 1);
        return root;
    }


    //889. 根据前序和后序遍历构造二叉树(结果不唯一)
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        return null;
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
    public TreeNode deserialize(List<String> l) {
        if (l.get(0).equals("null")) {
            l.remove(0);
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(l.get(0)));
        l.remove(0);
        root.left = deserialize(l);
        root.right = deserialize(l);
        return root;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        // remove "["   "]"
        data = data.substring(1, data.length() - 1);

        String[] data_array = data.split(",");
        List<String> data_list = new LinkedList<String>(Arrays.asList(data_array));
        return deserialize(data_list);
    }

    // 打家劫舍系列

    // 198. 打家劫舍  DP dp[i]=Math.max(dp[i-2]+nums[i],dp[i-1])
    public int rob1(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int len = nums.length;
        int[] dp = new int[len];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < len; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[len - 1];
    }

    //  只用到memo[i] memo[i-1] 所以 用两个变量 去迭代更新即可
    // http://bit.ly/2SppaZW
    public int rob2(int[] nums) {
        int prev1 = 0;
        int prev2 = 0;
        for (int num : nums) {
            int tmp = prev1;
            prev1 = Math.max(prev1, prev2 + num);
            prev2 = tmp;
        }
        return prev1;
    }

    //  213. 打家劫舍 II
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


    // 337. 打家劫舍 III  看懂比较模型即可 http://bit.ly/2LtppCe
    // dp(right) = max(dp(root),dp(left)+right.val)
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

    // 647. 回文子串 http://bit.ly/2LugfFU
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
    int dfsCountSubHelper(String s, int i, int j) {
        int res = 0;
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i--;
            j++;
            res++;
        }
        return res;
    }

    // 解法2 错误的双层for循环 越界了
    // 解法2 并不太推崇 如果 i j的初始化方向不一样的话 还是有越界问题
    // 我觉得 要避免这种问题 还是不好想到的
    // 本质上是输入数据 "fdsklf" ff重复了 引发的问题
    // 而且 感觉有些重复计算问题存在 , 并不优雅啦
    // 解法1 比较可取 !
    public int countSubstrings1(String s) {
        int res = 0;
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = i; j >= 0; j--) {

                dp[i][j] = s.charAt(i) == s.charAt(j) && (i - j <= 2 || dp[i + 1][j - 1]);
                if (dp[i][j]) {
                    System.out.println("i:" + i + "\tj:" + j);
                    res++;
                }
            }
        }
        return res;
    }

    // 这道题目就是有点取巧了阿  不推荐这种做法
    public int countSubstrings2(String s) {
        int res = 0;
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {

                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1]);
                if (dp[i][j]) {
                    System.out.println("i:" + (i) + "\tj:" + (j));
                    res++;
                }
            }
        }
        return res;
    }


    // 322. 零钱兑换  ★ 经典题目阿
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        int[] f = new int[amount + 1];
        f[0] = 0;
        for (int i = 1; i <= amount; i++) {
            int cost = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (i - coins[j] >= 0 && f[i - coins[j]] != Integer.MAX_VALUE) {
                    cost = Math.min(cost, f[i - coins[j]] + 1);
                }
            }
            f[i] = cost;
        }
        return f[amount] == Integer.MAX_VALUE ? -1 : f[amount];
    }
    //  518. 零钱兑换 II


    // 160. 相交链表
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

    // 461. 汉明距离
    public int hammingDistance(int x, int y) {
        int xor = x ^ y, count = 0;
        for (int i = 0; i < 32; i++) count += (xor >> i) & 1;
        return count;
    }

    public int findTargetSumWays(int[] nums, int S) {
        return dfsTargetSumHelper(nums, S, 0, res);
    }

    int dfsTargetSumHelper(int[] nums, int S, int start, int res) {
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

    // 解法2
    public int[][] reconstructQueue1(int[][] people) {
        return null;
    }


    // 岛屿系列 todo
    // 200. 岛屿数量
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '0' || visited[i][j]) {
                    continue;
                }
                dfsIslandHelper(grid, visited, i, j);
                res++;
            }
        }
        return res;
    }

    public void dfsIslandHelper(char[][] grid, boolean[][] visit, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[x].length || grid[x][y] == '0' || visit[x][y]) return;
        visit[x][y] = true;
        dfsIslandHelper(grid, visit, x + 1, y);
        dfsIslandHelper(grid, visit, x - 1, y);
        dfsIslandHelper(grid, visit, x, y + 1);
        dfsIslandHelper(grid, visit, x, y - 1);
    }

    // 44. 通配符匹配  参考这篇文章 匹配优先向下原则(说的不是通配哦!)  本质上是在构建NFA  http://bit.ly/2LyOYSq
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

    // 解法2 dp来做  解题思路参考这个做法 http://bit.ly/2StUSFc
    public boolean isMatch1(String s, String p) {
        char[] S = s.toCharArray();
        int sLen = S.length;
        char[] P = p.toCharArray();
        int pLen = P.length;
        boolean[][] dp = new boolean[sLen + 1][pLen + 1];
        dp[0][0] = true;
        // 处理特殊情况
        // 当s为空，p为连续的星号时的情况。由于星号是可以代表空串的，所以只要s为空，那么连续的星号的位置都应该为 true，所以我们现将连续星号的位置都赋为 true
        for (int i = 1; i <= pLen; i++) {
            if (P[i - 1] == '*') dp[0][i] = dp[0][i - 1];
        }

        for (int i = 1; i <= sLen; i++) {
            for (int j = 1; j <= pLen; j++) {
                if (P[j - 1] == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] && (S[i - 1] == P[j - 1] || P[j - 1] == '?');
                }
            }
        }
        return dp[sLen][pLen];
    }


    // leetcode 10 正则表达式匹配 http://bit.ly/2SsG9dA  todo 暂时放弃  看不懂示例3为什么true!
    public boolean isMatch2(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;

        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*' && dp[0][i - 1]) {
                dp[0][i + 1] = true;
            }
        }

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < p.length(); j++) {
                if (p.charAt(j) == '.') {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                if (p.charAt(j) == s.charAt(i)) {
                    dp[i + 1][j + 1] = dp[i][j];
                }

                if (p.charAt(j) == '*') {
                    if (p.charAt(j - 1) != s.charAt(i) && p.charAt(j - 1) != '.') {
                        dp[i + 1][j + 1] = dp[i + 1][j - 1];
                    } else {
                        dp[i + 1][j + 1] = (dp[i + 1][j] || dp[i][j + 1] || dp[i + 1][j - 1]);
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    // 394. 字符串解码  递归解法 注意idx的取值阿
    public int idx;

    public String decodeString(String s) {
        return decode(s);
    }

    //  字符+数字+[+字母+] 的 模型
    public String decode(String s) {
        String res = "";
        int n = s.length();
        while (idx < n && s.charAt(idx) != ']') {
            if (s.charAt(idx) > '9' || s.charAt(idx) < '0') {
                res += s.charAt(idx);
                idx++;
            } else {
                // cal numstr to cnt
                int cnt = 0;
                while (s.charAt(idx) >= '0' && s.charAt(idx) <= '9') {
                    cnt = cnt * 10 + s.charAt(idx) - '0';
                    idx++;
                }
                // 进入左括号后第一个
                idx++;
                String t = decode(s);
                // 当进入循环的时候的s.charAt(i=6)=']'。  那么，需要跳过去， 所以idx++。
                idx++;
                while (cnt-- > 0) {
                    res += t;
                }
            }
        }
        return res;
    }


    // 解法2
    // 不懂阿
    public String decodeString0(String s) {
        StringBuilder res = new StringBuilder();
        Stack<Integer> numStack = new Stack<>();
        Stack<String> strStack = new Stack<>();
        String tempStr = null;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (s.charAt(i) == ']') {
                String str = strStack.pop();
                int num = numStack.pop();
                String nowStr = repeatStr(str, num);
                if (!numStack.isEmpty()) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(strStack.peek());
                    builder.append(nowStr);
                    int m = i + 1;
                    while (s.charAt(m) != ']' && !('0' < s.charAt(m) && '9' >= s.charAt(m))) {
                        m++;
                    }
                    builder.append(s.substring(i + 1, m));
                    strStack.set(strStack.size() - 1, builder.toString());
                    i = m - 1;
                } else {
                    tempStr = null;
                    res.append(nowStr);
                }
            } else if ('0' <= c && '9' >= c) {
                int m = i + 1;
                while ('0' <= s.charAt(m) && '9' >= s.charAt(m)) {
                    m++;
                }
                numStack.push(Integer.parseInt(s.substring(i, m)));
                i = m - 1;
                int k = i + 2;
                while (s.charAt(k) != ']' && !('0' <= s.charAt(k) && '9' >= s.charAt(k))) {
                    k++;
                }
                strStack.push(s.substring(i + 2, k));
                i = k - 1;
            } else if (numStack.isEmpty()) {
                res.append(s.charAt(i));
            }
        }
        return res.toString();

    }

    private String repeatStr(String s, int num) {
        StringBuilder sb = new StringBuilder();
        if (num <= 0) return "";
        for (int i = 0; i < num; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    // 72. 编辑距离 DP的递归做法
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] memo = new int[m][n];
        return dfsMinDistanceHelper(word1, 0, word2, 0, memo);
    }

    public int dfsMinDistanceHelper(String word1, int i, String word2, int j, int[][] memo) {
        if (i == word1.length()) return word2.length() - j;
        if (j == word2.length()) return word1.length() - i;
        if (memo[i][j] > 0) return memo[i][j];
        int res = 0;
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

    //301 删除最小数量的无效括号 BFS扫描做法
    public List<String> removeInvalidParentheses0(String s) {
        List<String> res = new ArrayList<>();
        // sanity check
        if (s == null) return res;
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(s);
        queue.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {
            s = queue.poll();

            if (isValid(s)) {
                res.add(s);
                found = true;
            }
            // 这里是实现BFS的关键哦  判断是否要进行下一层次(子节点)的BFS扫描,if true ,执行同级节点的扫描.
            if (found) continue;

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != '(' && s.charAt(i) != ')') continue;
                String t = s.substring(0, i) + s.substring(i + 1);
                if (!visited.contains(t)) {
                    queue.add(t);
                    visited.add(t);
                }
            }
        }
        return res;
    }


    public boolean isValid(String s) {
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') count++;
            // 左右括号存在顺序性
            if (c == ')' && --count < 0) return false;
        }
        return count == 0;
    }

    // 312. 戳气球 DP思想 迭代 http://bit.ly/2K4T01Z dp[i,j]
    public int maxCoins(int[] nums) {
        // ready data
        int n = nums.length;
        int[] numbers = new int[n + 2];
        numbers[0] = numbers[numbers.length - 1] = 1;
        for (int i = 0; i < n; i++) {
            numbers[i + 1] = nums[i];
        }
        int[][] dp = new int[n + 2][n + 2];

        for (int len = 1; len <= n; len++) {
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                for (int k = i; k <= j; k++) {
                    // 求出dp[i,j]区间 第k个气球被打破时候的最大值Max(dp[i,j])
                    // k 的遍历区间 [i,i+len-1]
                    dp[i][j] = Math.max(dp[i][j], numbers[i - 1] * numbers[k] * numbers[j + 1] + dp[i][k - 1] + dp[k + 1][j]);
                }
            }
        }

        return dp[1][n];
    }

    // 解法2 递归 todo

    // http://bit.ly/2LvcJLu
    // 438. 找到字符串中所有字母异位词
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int[] letters = new int[27];
        for (char c : p.toCharArray()) {
            letters[c - 'a'] = letters[c - 'a'] + 1;
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

    // 解法2 滑动窗口   观察 p 频数的自增自减
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

    // 49. 字母异位词分组
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
            int[] cnt = new int[27];
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

    // 621. 任务调度器 好难  http://bit.ly/2LxLShE [经典]
    public int leastInterval(char[] tasks, int n) {
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

    // http://bit.ly/2LxLShE
    public int leastInterval1(char[] tasks, int n) {
        int mx = 0;
        int mxCnt = 0;
        int[] cnt = new int[26];
        for (char c : tasks) {
            cnt[c - 'A']++;
            if (mx == cnt[c - 'A']) {
                mxCnt++;
            } else if (mx < cnt[c - 'A']) {
                mx = cnt[c - 'A'];
                mxCnt = 1;
            }
        }

        //     return Math.max(tasks.length, (mx - 1) * (n + 1) + mxCnt);

        int partCnt = mx - 1;
        int partLen = n - (mxCnt - 1);
        int emptySlot = partCnt * partLen;
        int taskLeft = tasks.length - mx * mxCnt;
        int idles = Math.max(0, emptySlot - taskLeft);
        return tasks.length + idles;
    }

    // 252. 会议室
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1])
                return false;
        }
        return true;
    }


    // 253. 会议室 II
    public int minMeetingRooms0(int[][] intervals) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int[] interval : intervals) {
            map.put(interval[0], map.getOrDefault(interval[0], 0) + 1);
            map.put(interval[1], map.getOrDefault(interval[1], 0) - 1);
        }
        int rooms = 0;
        int res = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("entry:\t" + entry + "\troom: " + rooms);
            // 求前缀和的最大值
            res = Math.max(res, rooms += entry.getValue());
        }
        return res;
    }

    // 参考这个视频 https://youtu.be/wB884_Os58U  主要是人脑怎么处理的问题
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


    // 347. 前 K 个高频元素
    // follow up
    // 692 Top K Frequent Words
    // 451 Sort Characters By Frequency
    public List<Integer> topKFrequent1(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            cnt.put(num, cnt.getOrDefault(num, 0) + 1);
        }
        // 最小堆
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>((o1, o2) -> o1.getValue() - o2.getValue());
        for (Map.Entry<Integer, Integer> item : cnt.entrySet()) {
            pq.offer(item);
            // 剔除 最小k
            if (pq.size() > k) pq.poll();
        }

        List<Integer> res = new LinkedList<>();
        while (!pq.isEmpty()) {
            Map.Entry<Integer, Integer> item = pq.poll();
            res.add(0, item.getKey());
        }
        return res;
    }

    // 解法2 TreeMap
    public List<Integer> topKFrequent2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        TreeMap<Integer, List<Integer>> freqMap = new TreeMap<>();
        for (int num : map.keySet()) {
            int freq = map.get(num);
            if (!freqMap.containsKey(freq)) {
                freqMap.put(freq, new LinkedList<>());
            }
            freqMap.get(freq).add(num);
        }

        List<Integer> res = new ArrayList<>();
        while (res.size() < k) {
            Map.Entry<Integer, List<Integer>> entry = freqMap.pollLastEntry();
            // System.out.println(entry);
            res.addAll(entry.getValue());
        }
        return res;
    }

    // todo 解法3 bucket sort

    // 279. 完全平方数 http://bit.ly/2Yl0Xt2 四平方和定理 但是 不太推荐
    public int numSquares1(int n) {
        System.out.println("四平方和定理 不过不好记住");
        /*

        while (n % 4 == 0) n /= 4;
        if (n % 8 == 7) return 4;
        for (int a = 0; a * a <= n; ++a) {
            int b = sqrt(n - a * a);
            if (a * a + b * b == n) {
                return !!a + !!b;
            }
        }
        return 3;

        * */
        return -1;
    }

    // 这道题目 意义不强阿
    public int numSquares2(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 1; i + j * j <= n; j++) {
                int idx = i + j * j;
                dp[idx] = Math.min(dp[idx], dp[i] + 1);
                System.out.println("i值: " + i + "\tj值: " + j + "\tj平方: " + (j * j) + "\t(i+j*j): " + idx + "\tdp:" + dp[idx] + "\n");
            }
        }
        return dp[n];
    }

    // 239. 滑动窗口最大值 这道题目也是考察数据结构的熟悉程度了 大堆 优先队列
    public int[] maxSlidingWindow0(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        if (nums.length == 0 || nums == null) return new int[0];
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> (o2 - o1));
        // init
        for (int i = 0; i < k; i++) {
            pq.add(nums[i]);
        }
        res[0] = pq.peek();
        for (int i = k; i < nums.length; i++) {
            pq.remove(nums[i - k]);
            pq.add(nums[i]);
            res[i - k + 1] = pq.peek();
        }
        return res;
    }

    public int[] maxSlidingWindow1(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return new int[0];
        }
        int len = nums.length;
        int[] ret = new int[len - k + 1];

        int retIdx = 0;
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < len; i++) {

            // 超过长度 无条件移除head
            while (!q.isEmpty() && q.peek() < i - k + 1) {
                q.pollFirst();
            }
            while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) {
                // 入列的时候 从尾部剔除小于nums[i]元素
                q.pollLast();
            }
            q.offer(i);
            if (i >= k - 1) {// 表示 i可以赋值了，因为i必须到k-1时候，获取到第一个滑动窗口。
                // head代表 固定长度k的头部元素为最大值,赋值给ret
                ret[retIdx++] = nums[q.peek()];
            }
            System.out.println("i值:" + i + "长度为3的单调栈:" + q);
        }
        return ret;
    }


    // 399. 除法求值
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        return null;
    }

    // 32. 最长有效括号
    public int longestValidParentheses(String s) {
        int res = 0, start = 0;
        Stack<Integer> m = new Stack<>();
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '(') m.push(i);
            else if (s.charAt(i) == ')') {
                if (m.empty()) start = i + 1;
                else {
                    m.pop();
                    res = m.empty() ? Math.max(res, i - start + 1) : Math.max(res, i - m.peek());
                }
            }
        }
        return res;
    }


    // 76. 最小覆盖子串//  滑动窗口解决问题  http://bit.ly/2LvcJLu
    //
    public String minWindow(String s, String t) {
        return "";
    }

    // follow up todo 590. N叉树的后序遍历
    public List<Integer> postorder(Node root) {
        return null;
    }

    // 207. 课程表 拓扑排序

    // 124. 二叉树中的最大路径和
    public int maxPathSum(TreeNode root) {
        return 1;
    }

    // https://leetcode-cn.com/problems/add-strings/
    // 415. 字符串相加

    // 7. 整数反转
    // 无法处理负数问题阿
    public int reverse0(int x) {
        Queue<Integer> s = new LinkedList<>();
        int i = x;
        while (i != 0) { // i !=0
            int mod = i % 10;
            s.add(mod);
            i = i / 10;
        }
        int res = 0;
        while (!s.isEmpty()) {
            if (Math.abs(res) > Integer.MAX_VALUE / 10) {
                return 0;
            }
            res = res * 10 + s.poll();
        }
        return res;
    }

    public int reverse(int x) {
        int res = 0;
        while (x != 0) {
            if (Math.abs(res) > Integer.MAX_VALUE / 10) return 0;
            res = res * 10 + x % 10;
            x = x / 10;
        }
        return res;
    }

    // 88. 合并两个有序数组
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


    public void merge1(int[] nums1, int m, int[] nums2, int n) {
        int i1 = m - 1;
        int i2 = n - 1;
        int k = m + n - 1;
        while (i2 >= 0) nums1[k--] = (i1 >= 0 && nums1[i1] > nums2[i2]) ? nums1[i1--] : nums2[i2--];
    }

    // 237. 删除链表中的节点
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        ListNode tmp = node.next;
        node.next = node.next.next;
        tmp.next = null;
    }

    // 326. 3的幂
    public boolean isPowerOfThree0(int n) {
        // 1162261467 is 3^19,  3^20 is bigger than int
        return (n > 0 && 1162261467 % n == 0);
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

    // 13. 罗马数字转整数
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
                res += cur - 2 * before;
            }
        }
        return res;
    }


    /*public int romanToInt1(String s) {

        Map<Character, Integer> dict = new HashMap<>();
        dict.put('I', 1);
        dict.put('V', 5);
        dict.put('X', 10);
        dict.put('L', 50);
        dict.put('C', 100);
        dict.put('D', 500);
        dict.put('M', 1000);
        int res = 0;
        for (int i = 1; i < s.length(); i++) {
            int val = dict.get(s.charAt(i));

            if (i == s.length() - 1 || dict.get(s.charAt(i + 1)) <= val) {
                res += val;
            } else {
                res -= val;
            }
        }
        return res;
    }
*/

    // 118. 杨辉三角
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

    // 202 快乐数
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        int ret = n;
        while (true) {
            ret = square(ret);
            if (ret == 1) return true;
            if (!set.contains(ret)) set.add(ret);
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

    // 快慢指针方法 if存在环
    public boolean isHappy1(int n) {
        int slow = n, fast = n;
        while (true) {
            slow = square(slow);
            fast = square(square(fast));
            if (slow == fast) break;
        }
        return slow == 1;
    }

    // 371. 两整数之和  [和的位运算实现] http://bit.ly/2MpSWN0  求和运算 sum=2*C(进位)+S(本位), 李永乐老师 本位 进位 https://youtu.be/pUwYvJtfbsY
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
        // return ((a > 0) == (b > 0)) ? res : -res;
    }

    //29. 两数相除
    // 这个方法是最好的
    public int divide2(int dividend, int divisor) {
        if (dividend == 1 << 31 && divisor == -1) return (1 << 31) - 1;
        int a = Math.abs(dividend), b = Math.abs(divisor), res = 0, x = 0;
        while (a - b >= 0) {
            for (x = 0; a - (b << x << 1) >= 0; x++) ;
            res += 1 << x;
            a -= b << x;
        }
        return (dividend > 0) == (divisor > 0) ? res : -res;
    }

    public int divide3(int dividend, int divisor) {
        if (dividend == 1 << 31 && divisor == -1) return (1 << 31) - 1;
        int a = Math.abs(dividend), b = Math.abs(divisor), res = 0, x = 0;
        while (a - b >= 0) {
            for (x = 0; a - (b << x << 1) >= 0; x++) ;
            res += 1 << x;
            // 错误 运算符优先级 - 比 << 先执行
            a = a - b << x;
        }
        return (dividend > 0) == (divisor > 0) ? res : -res;
    }


    public int divide0(int dividend, int divisor) {
        // 这个除法挺lowB的
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        int sign = ((dividend < 0) ^ (divisor < 0)) ? -1 : 1;
        long x = Math.abs(((long) dividend));
        long y = Math.abs(((long) divisor));
        int result = 0;
        while (x >= y) {
            x -= y;
            result++;
        }
        return result * sign;
    }

    public int divide1(int dividend, int divisor) {// 这个方法不行 会存在 int 越界问题
        if (dividend == Integer.MIN_VALUE && divisor == -1)
            return Integer.MAX_VALUE;

        boolean sign = (dividend < 0) ^ (divisor < 0);
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        int count = 0;
        //  System.out.println("count: " + count + "\tdivisor: " + divisor);
        while (dividend >= divisor) {
            // 会存在int越界问题 divisor ---> 0
            count++;
            divisor <<= 1;
            System.out.println("count: " + count + "\tdivisor: " + divisor);
            if (divisor == 0) return -1;

        }

        int result = 0;
        System.out.println("=====split====");
        while (count > 0) {

            count--;
            divisor >>= 1;
            int temp = dividend;
            if (divisor <= dividend) {
                result = result + 1 << count;
                dividend = dividend - divisor;
            }
            System.out.println("count: " + count + "\tdivisor: " + divisor + "\t before: " + temp + "\tdividend: " + dividend + "\tresult: " + result);
        }
        if (sign) result = -result;
        return result;
    }

    // 204 质数计数
    public int countPrimes(int n) {
        // 非质素素组
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!notPrime[i]) {
                count++;
                for (int j = 2; j * i < n; j++) {
                    notPrime[i * j] = true;
                }
            }
        }
        return count;
    }

    // 14 最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        int len = strs.length;
        int i = 0;
        Arrays.sort(strs);
        int range = Math.min(strs[0].length(), strs[len - 1].length());
        while (i < range && strs[0].charAt(i) == strs[len - 1].charAt(i)) i++;
        return strs[0].substring(0, i);
    }

    // 69 x 平方根 利用牛顿求根法来做[http://bit.ly/2ypO02m] 牛顿求根法视频讲解 https://youtu.be/VUpQwEVsyFk
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

    // follow up  立方根
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

    // 28. 实现strStr()
    public int strStr0(String haystack, String needle) {
        int i = 0;
        int j = 0;
        while (i < haystack.length() && j < needle.length()) {
            if (haystack.charAt(i++) != needle.charAt(j++)) {
                j = 0;
            }
        }
        return j == needle.length() ? i - j : -1;
    }

    // 遍历每一个字符作为其实字符的子字符串  字符串长度计算 左闭右开 不熟悉
    public int strStr1(String haystack, String needle) {
        if (needle == "") return 0;
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            if (haystack.substring(i, i + needle.length()).equals(needle)) {
                return i;
            }
        }
        return -1;
    }
    // kmp字符串匹配的实现

    // 172. 阶乘后的零
    public int trailingZeroes0(int n) {
        // 错误解法
        int count = 0;
        for (int i = 5; i <= n; i++) {
            if ((i >= 5 && i % 5 == 0)) {
                count++;
            }
        }
        return count;
    }

    public int trailingZeroes1(int n) {
        // lowB写法哈哈 TLE了 233
        int count = 0;
        int i = 5;
        while (i <= n) {
            int dividend = i;
            while (dividend != 0) {
                if (dividend % 5 == 0) {
                    count++;
                    dividend = dividend / 5;
                } else break;
            }
            i++;
        }
        return count;
    }

    // 连续5的前缀后数列
    public int trailingZeroes2(int n) {
        int count = 0;
        while (n != 0) {
            count += n / 5; // 算出 5倍数的个数了
            n = n / 5;
        }
        return count;
    }

    // 26. 删除排序数组中的重复项
    public int removeDuplicates(int[] nums) {
        int i = 0;
        int j = 0;
        while (j < nums.length)
            if (nums[i] == nums[j]) {
                j++;
            } else {
                nums[++i] = nums[j];
            }
        // 返回新数组长度
        return i + 1;
    }

    // 217. 存在重复元素
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) return true;
            else set.add(num);
        }
        return false;
    }

    //191. 位1的个数
    public int hammingWeight(int n) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            count += n & 1;
            n >>= 1;
        }
        return count;

        /**
         *  除余运算也是不合适的
         *  int count = 0;
         *         while (n != 0) {
         *             if (n % 2 == 1) {
         *                 count++;
         *             }
         *             n >>= 1;
         *         }
         *         return count;
         */
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


    // 190. 颠倒二进制位
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int res = 0;
        for (int i = 0; i < 32; ++i) {
            if ((n & 1) == 1) {
                // 注意下运算符的优先级
                res = (res << 1) + 1;
            } else {
                res = res << 1;
            }
            //   res= (res<<1)+(n&1);
            n = n >> 1;
        }
        return res;
    }

    //
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (!isAlphaNum(s.charAt(left))) {
                left++;
            } else if (!isAlphaNum(s.charAt(right))) {
                right--;
            } // 别忘记加else 因为 下面的和上上面的是并行关系
            else if ((s.charAt(left) + 32 - 'a') % 32 == (s.charAt(right) + 32 - 'a') % 32) {
                right--;
                left++;
            } else {
                System.out.println("非回文字符串\t"+left + "left: " + s.charAt(left) + "\t" + right + "\tright: " + s.charAt(right));
                return false;
            }

        }
        return true;
    }

    public boolean isAlphaNum(char c) {

        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }


}




