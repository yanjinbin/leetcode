package com.yanjinbin.leetcode;


// ArrayDeque（双端队列）内部实现是一个循环数组，bit 巧妙运用

import edu.princeton.cs.algs4.In;

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
 * top 100 interviewed question
 * https://leetcode-cn.com/problemset/top/
 */
public class Solution {

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
        ListNode cur = dummyHead;
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
        // dummyhead 是0 所以是返回他的下一个节点,作为头部节点
        return dummyHead.next;
    }

    // bad
    public ListNode addTwoNumber(ListNode l1, ListNode l2) {
        // 这种方法有个缺陷是无法处理 111  88999999 这样的
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        int sum = 0, carry = 0;
        while (l1 != null && l2 != null) {
            sum = l1.val + l2.val + carry;
            carry = sum >= 10 ? 1 : 0;
            int newVal = sum % 10;
            cur.next = new ListNode(newVal);
            cur = cur.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        if (l1 == null && l2 == null && carry == 1) {
            cur.next = new ListNode(carry);
        }
        if (l1 == null && l2 != null) {
            l2.val += carry;
            cur.next = l2;
        }
        if (l1 != null && l2 == null) {
            l1.val += carry;
            cur.next = l1;
        }
        return dummy.next;
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
    public ListNode cur;

    public boolean isPalindrome2(ListNode head) {
        cur = head;
        return helper(head);
    }

    public boolean helper(ListNode node) {
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

    // [tag:微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
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

    public int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    // 最长回文子串 最佳解法
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

    // [tag:微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
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

    //[tag: 面筋 http://bit.ly/2Na3nW1] 11. 盛最多水的容器 双指针法，左右移动时候，选择移动 高度短的 可能能增加面积 如果是盛水最少的容器呢
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

    public ListNode reverseList1(ListNode head) {
        return help(head, null);
    }

    public ListNode help(ListNode cur, ListNode prev) {
        if (cur == null) return prev;
        ListNode next = cur.next;
        cur.next = prev;
        return help(next, cur);
    }

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

    public void reverse0(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i++, j--);
        }
    }

    // [tag:微软面筋] https://www.1point3acres.com/bbs/thread-542957-1-1.html
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

    public int climbHelper(int n, int[] memo) {
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

    public boolean exist = false;

    // leetcode 139  单词拆分 http://bit.ly/2Ld41Bt 这是一道DP题目
    // 分治思想 DP
    // S[0,i)= S[0,j) || S[j,i)  0 <= j < i <= s.length()
    public boolean wordBreak(String s, List<String> wordDict) {
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

    public ListNode getIntersect(ListNode head) {
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
                dp[i] = dp[i] || dp[i - num];
            }
        }
        return dp[target];
    }

    // 300. 最长上升子序列
    // http://bit.ly/2S18Z4A 看动画就能理解为什么了 哈哈
    //  [tag:面筋  很容易会被问到]
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
        // System.out.println("end" + end + "start" + start);
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
            if (nums[l] >= pivot) l++;
            if (nums[r] <= pivot) r--;
        }
        // l-r=1
        swap(nums, lo, r);
        return r;
    }


    public static boolean less(int v, int w) {
        if (v == w) return false;   // optimization when reference equals
        return v - w < 0;
    }

    public static void exch(int[] nums, int i, int j) {
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


    public int[] items;

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
    public int minIdx = Integer.MAX_VALUE;
    public int maxIdx = Integer.MIN_VALUE;

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

    public void backtrack(int[] candidates, int target, List<List<Integer>> res, int i, ArrayList<Integer> tmp_list) {
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


    // 77  组合 todo
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ret = new ArrayList<>();
        Map<Integer, Boolean> dict = new HashMap();
        for (int i = 1; i <= n; i++) {
            dict.put(i, false);
        }
        return ret;
    }

    public void backTrackCombine(List<List<Integer>> ret, LinkedList<Integer> sub, int level, int k, Map<Integer, Boolean> dict) {
        if (level == k) {
            ret.add(new ArrayList(sub));
            return;
        }
        for (int i = 1; i <= dict.size(); i++) {
            if (dict.get(i)) continue;
            dict.put(i, true);
            sub.add(i);
            //backTrackCombine();
            dict.put(i, false);
            sub.pollLast();
        }
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
    // [tag:微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
    // 128. 最长连续序列 map set solve

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

    public void inorderHelper(TreeNode root, List<String> ret) {
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
        // morris 遍历 核心 就是简历  root和 左子树 最右边节点的关系 pre = root.left; pre.right = root;
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

    // morris 前序遍历
    public List<Integer> morrisPreorder(TreeNode root) {
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
                    // 先访问 root
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


    // time complexity O(2N)
    public List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) return ret;
        Stack<TreeNode> s1 = new Stack();
        Stack<TreeNode> s2 = new Stack();
        s1.push(root);
        while (!s1.isEmpty()) {
            TreeNode cur = s1.pop();
            s2.push(cur);
            if (cur.left != null) s1.push(cur.left);
            if (cur.right != null) s1.push(cur.right);
        }
        // 和 下面的add First 异曲同工
        while (!s2.isEmpty()) {
            ret.add(s2.pop().val);
        }
        return ret;
    }

    public List<Integer> postorderTraversal3(TreeNode root) {
        LinkedList<Integer> ret = new LinkedList();
        if (root == null) return ret;
        Stack<TreeNode> s = new Stack();
        s.push(root);
        while (!s.isEmpty()) {
            TreeNode cur = s.pop();
            // addFirst 关键
            ret.addFirst(cur.val);
            if (cur.right != null) s.push(cur.right);
            if (cur.left != null) s.push(cur.left);
        }
        return ret;
    }

    // 后续遍历 这是最好的 也是最棒的
    public List<Integer> postorderTraversal4(TreeNode root) {
        List<Integer> ret = new LinkedList<>();
        if (root == null) return ret;
        Stack<TreeNode> s = new Stack<>();

        TreeNode cur = null;
        s.push(root);
        s.push(root);
        while (!s.isEmpty()) {
            cur = s.pop();
            if (s.isEmpty() || cur != s.peek()) {
                ret.add(cur.val);
            } else {
                if (cur.right != null) {
                    s.push(cur.right);
                    s.push(cur.right);
                }
                if (cur.left != null) {
                    s.push(cur.left);
                    s.push(cur.left);
                }
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
    // 解法1
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

    // 解法2
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

    public static boolean validateTreeNode(TreeNode node) {
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


    public int counter;

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


    public int cnt;

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
    public int sum = 0;

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
    public int res = 0;

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

    public TreeNode buildTrePreIn(int[] preOrder, int pLeft, int pRight, int[] inorder, int iLeft, int iRight) {
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
    public int dfsCountSubHelper(String s, int i, int j) {
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

    // 背包问题
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
    public int idx394;

    public String decodeString(String s) {
        return decode(s);
    }

    //  字符+数字+[+字母+] 的 模型
    public String decode(String s) {
        String res = "";
        int n = s.length();
        while (idx394 < n && s.charAt(idx394) != ']') {
            if (s.charAt(idx394) > '9' || s.charAt(idx394) < '0') {
                res += s.charAt(idx394);
                idx394++;
            } else {
                // cal numstr to cnt
                int cnt = 0;
                while (s.charAt(idx394) >= '0' && s.charAt(idx394) <= '9') {
                    cnt = cnt * 10 + s.charAt(idx394) - '0';
                    idx394++;
                }
                // 进入左括号后第一个
                idx394++;
                String t = decode(s);
                // 当进入循环的时候的s.charAt(i=6)=']'。  那么，需要跳过去， 所以idx++。
                idx394++;
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

    public String repeatStr(String s, int num) {
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

    // 还是很难像到的
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
            while (!q.isEmpty() && q.peek() + k - 1 < i) {
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
        }
        return ret;
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

    }

    //29. 两数相除
    // 这个方法是最好的  但是也是听难理解的 放弃
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
    // f(x1)-f(x2) / x1-x2 = f'(x1) 令 f(x2)=0 即可求出
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

    //解乏2 二分法求值
    public int mySqrt1(int x) {
        long i = 0;
        long j = x / 2 + 1;
        while (i <= j) {
            long mid = i + (j - i) / 2;
            long sq = mid * mid;
            if (sq == x) {
                return (int) mid;
            } else if (sq < x) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        return (int) j + 1;
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
            //  res= (res<<1)+(n&1);
            n = n >> 1;
        }
        return res;
    }

    //  125 验证回文字符串
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
                //  System.out.println("非回文字符串\t" + left + "left: " + s.charAt(left) + "\t" + right + "\tright: " + s.charAt(right));
                return false;
            }

        }
        return true;
    }


    // 242. 有效的字母异位词
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

    // 268 缺失数字
    public int missingNumber0(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return ((nums.length * (nums.length + 1)) >> 1) - sum;
    }

    // 异或 结合律 http://bit.ly/2Kkra1B
    public int missingNumber1(int[] nums) {
        int miss = nums.length;
        for (int i = nums.length - 1; i >= 0; i--) {
            miss ^= i ^ nums[i];
        }
        return miss;
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

    // 二分法 todo 二分法需要做个专题研究
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

    // leetcode 137 137. 只出现一次的数字 II
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

    // 解法2 易懂 但是复杂度O(N²)
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


    // leetcode 260. 只出现一次的数字 III

    //387  字符串中的第一个唯一字符
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

    // 189. 旋转数组
    public void rotate(int[] nums, int k) {
        int[] tmp = Arrays.copyOf(nums, nums.length);
        for (int i = 0; i < tmp.length; i++) {
            nums[i] = tmp[(i + k) % nums.length];
        }
        //   System.out.println(Arrays.toString(nums));
    }

    //
    public void rotate1(int[] nums, int k) {
        // System.out.println(Arrays.toString(nums));
        for (int i = 0; i < k; i++) {
            shift(nums);
        }
        //  System.out.println(Arrays.toString(nums));
    }

    public void shift(int[] nums) {
        //  System.out.println(Arrays.toString(nums));
        int prev = nums[nums.length - 1];
        int tmp;
        for (int i = 0; i < nums.length; i++) {
            tmp = nums[i];
            nums[i] = prev;
            prev = tmp;
        }
        //   System.out.println(Arrays.toString(nums));
    }

    // 来自 http://bit.ly/2KkELWH  ,并不推荐 说真的  太难看了
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

    // 买卖股票系列问题 参考下面这篇 Blog 属实牛逼 http://bit.ly/333JDIm
    // 121. 买卖股票的最佳时机
    public int maxProfit1A(int[] prices) {
        if (prices.length == 0) return 0;
        int min = prices[0];
        int gap = 0;
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            gap = Math.max(prices[i] - min, gap);
        }
        return gap;
    }

    public int maxProfit1B_Bad(int[] prices) {
        int k = 1;
        int n = prices.length;
        int[][] dp = new int[prices.length][2];
        for (int i = 0; i < n; i++) { // 没有处理base case
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[n - 1][0];
    }

    public int maxProfit1B_Good(int[] prices) {

        if (prices == null || prices.length == 0) return 0;
        // int k = 1;
        int n = prices.length;
        int[][] dp = new int[prices.length][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                dp[i][0] = 0;
                // 解释：
                //   dp[i][0]
                // = max(dp[-1][0], dp[-1][1] + prices[i])
                // = max(0, -infinity + prices[i]) = 0
                dp[i][1] = -prices[i];
                //解释：
                //   dp[i][1]
                // = max(dp[-1][1], dp[-1][0] - prices[i])
                // = max(-infinity, 0 - prices[i])
                // = -prices[i]
                continue;
            }
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[n - 1][0];




/*
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
        return dp[n - 1][0];*/
    }

    public int maxProfit1C(int[] prices) {
        int n = prices.length;
        // base case: dp[-1][0] = 0, dp[-1][1] = -infinity
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            // dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            // dp[i][1] = max(dp[i-1][1], -prices[i])
            dp_i_1 = Math.max(dp_i_1, -prices[i]);
        }
        return dp_i_0;

    }


    // 122. 买卖股票的最佳时机 II   逢涨必抛  贪心算法
    public int maxProfit2A(int[] prices) {
        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                res += prices[i] - prices[i - 1];
            }
        }
        return res;
    }

    //  股票系列通用的模版
    public int maxProfit2B(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;
        int[][] dp = new int[n][2];
        // init
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }


    // 123. 买卖股票的最佳时机 III
    // 解法1  没看懂
    public int maxProfit3A(int[] prices, int k) {
        if (prices.length == 0 || prices == null) return 0;
        int n = prices.length;
        int[][] dpG = new int[n][k];
        int[][] dpL = new int[n][k];
        dpG[n][3] = 0;
        dpL[n][3] = 0;
        for (int i = 1; i < n; ++i) {
            int diff = prices[i] - prices[i - 1];
            for (int j = 1; j <= 2; ++j) {
                dpL[i][j] = Math.max(dpG[i - 1][j - 1] + Math.max(diff, 0), dpL[i - 1][j] + diff);
                dpG[i][j] = Math.max(dpL[i][j], dpG[i - 1][j]);
            }
        }
        return dpG[n - 1][2];
    }

    // 解法2
    //
    public int maxProfit3B(int[] prices, int k) {
        if (prices.length == 0 || prices == null) return 0;
        int n = prices.length;
        // k 代表最多可以进行的交易次数
        int[][][] dp = new int[n][k + 1][2];
        // init
        for (int i = 0; i < n; i++) {
            // j >=1 也可以 因为 j =0 代表不进行交易,那么是负无穷大
            for (int j = 1; j <= k; j++) {
                if (i == 0) {
                    dp[0][j][0] = 0;
                    dp[0][j][1] = -prices[i];
                    continue;
                }
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        return dp[n - 1][k][0];
    }

    // 188. 买卖股票的最佳时机 IV
    public int maxProfit4A(int k, int[] prices) {
        int n = prices.length;
        if (k > n / 2) { // 一天内
            return maxProfit2B(prices);
        }
        return maxProfit3B(prices, k);
    }


    // 309. 最佳买卖股票时机含冷冻期
    public int maxProfit5A(int[] prices) {
        // corner case
        if (prices == null || prices.length <= 1) return 0;

        if (prices.length == 2) {
            return prices[1] - prices[0] > 0 ? prices[1] - prices[0] : 0;
        }

        int n = prices.length;
        int[][] dp = new int[n][2];

        // general case
        // init
        // k=0;
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        // k = 1
        dp[1][0] = Math.max(dp[0][0], dp[0][1] + prices[1]);
        dp[1][1] = Math.max(dp[0][1], 0 - prices[1]);

        for (int i = 2; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    // 改写上面maxProfit5A方法

    public int maxProfit5B(int[] prices) {
        int n = prices.length;
        int dp_i_0 = 0;
        int dp_i_1 = Integer.MIN_VALUE;
        int dp_pre_0 = 0;
        for (int i = 0; i < n; i++) {
            // 改写
            int temp = dp_i_0;// 上一次的  0-->1
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);// 更新了一次1-->2
            dp_i_1 = Math.max(dp_i_1, dp_pre_0 - prices[i]);//0  哲理可以看到 dp_pre_0 的使用和被赋值规律
            dp_pre_0 = temp; // 0-->1
        }
        return dp_i_0;
    }


    // 714. 买卖股票的最佳时机含手续费
    public int maxProfit6A(int[] prices, int fee) {
        int n = prices.length;
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int temp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1, temp - prices[i] - fee);
        }
        return dp_i_0;
    }

    // [tag:微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
    // leetcode 8  字符串转换整数 (atoi)
    public int myAtoi(String str) {
        int sign = 1;
        int i = 0;
        int n = str.length();
        int ret = 0;
        if (str == null || str.isEmpty()) return 0;

        while (i < n && str.charAt(i) == ' ') i++;
        // 加上逻辑或的时候 并没有注意 整体IF 条件属性改变
        if (i < n && (str.charAt(i) == '+' || str.charAt(i) == '-')) {
            sign = str.charAt(i) == '+' ? 1 : -1;
            i++;
        }
        while (i < n && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            // 防止int越界问题  int   [  214748364 7 ]
            if (ret > Integer.MAX_VALUE / 10 || (ret == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7)) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            ret = ret * 10 + str.charAt(i) - '0';
            i++;
        }
        return sign * ret;
    }

    // 36 验证是否是有效的数独   // interview friendly
    public boolean isValidSudoku(char[][] board) {
        Map<String, Boolean> map = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] >= '1' && board[i][j] <= '9') {
                    char c = board[i][j];
                    String rowKey = i + "row" + c;
                    String colKey = j + "col" + c;
                    // group key 为什么没想出来呢  是因为没想出更具坐标系(i,j) 对属于同一个小方格的元素进行归约!!!
                    String groupKey = (i / 3 + "_" + j / 3 * 3) + "group" + c;
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

    // 50 pow(x,n) 注意这道题目 是能用基本算术运算的!!
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        double half = myPow(x, n / 2);
        if (n % 2 == 0) return half * half;
        if (n > 0) return half * half * x;
        // 负数情况下  -5  =  -2 -2 -1  -1 看做 /x即可!
        return half * half / x;
    }


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

    // 41 缺失的第一个正整数
    public int firstMissingPositive_0(int[] nums) {
        // 错误 nums 可能有负数
        Arrays.sort(nums);
        int ret = 1;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (ret != num) {
                return ret;
            }
            ret++;
        }

        return ret;
    }

    public int firstMissingPositive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            set.add(i);
        }
        int i = 1;
        while (set.contains(i)) i++;
        return i;
    }

    // 缺失的第一个征整数
    public int firstMissingPositive_1(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, nums[i] - 1, i);
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }

    // 179. 最大数
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

    // 328 奇偶链表
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
    /*public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int res = 1, m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, dfs(matrix, dp, i, j));
            }
        }
        return res;
    }

    public int dfs(int[][] matrix, int[][] dp, int i, int j) {
        if (dp[i][j] != 0) return dp[i][j];
        int mx = 1, m = matrix.length, n = matrix[0].length;
        for (int[] a : dirs) {
            int x = i + a[0], y = j + a[1];
            if (x < 0 || x >= m)
        }
    }*/
   /* public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix[0].length == 0) {
            return 0;
        }
        int res = 1, m = matrix.length;
        n = matrxi[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, dfsSearchPath(matrix, dp, i, j));
            }
        }
        return res;
    }

    public int dfsSearchPath(int[][] matrix, int[][] dp, int i, int j) {
        if (dp[i][j]!=0) return dp[i][j];
        int mx = 1, m = matrix.size(), n = matrix[0].size();
        for (auto a : dirs) {
            int x = i + a[0], y = j + a[1];
            if (x < 0 || x >= m || y < 0 | a | y >= n || matrix[x][y] <= matrix[i][j]) continue;
            int len = 1 + dfs(matrix, dp, x, y);
            mx = max(mx, len);
        }
        dp[i][j] = mx;
        return mx;
    }
*/

    public static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    // 解法 1
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix.length == 0) return 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //  res = Math.max(res, dfsSearchPath(matrix, dp, i, j, m, n));
                res = Math.max(res, dfsSearchPath1(matrix, dp, i, j, m, n));
            }
        }
        return res;
    }

    // 单调最长路径
    public int dfsSearchPath(int[][] matrix, int[][] dp, int i, int j, int m, int n) {
        if (dp[i][j] != 0) return dp[i][j];
        int len = 1;
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n && matrix[i][j] < matrix[x][y]) {
                len = Math.max(len, 1 + dfsSearchPath(matrix, dp, x, y, m, n));
            }
        }
        dp[i][j] = len;
        return len;
    }

    // 这个比较易懂!!!
    public int dfsSearchPath1(int[][] matrix, int[][] dp, int i, int j, int m, int n) {
        if (dp[i][j] != 0) return dp[i][j];
        int len = 1;
        if (i - 1 >= 0 && matrix[i - 1][j] > matrix[i][j])
            len = Math.max(len, 1 + dfsSearchPath1(matrix, dp, i - 1, j, m, n));
        if (i + 1 < m && matrix[i + 1][j] > matrix[i][j])
            len = Math.max(len, 1 + dfsSearchPath1(matrix, dp, i + 1, j, m, n));
        if (j - 1 >= 0 && matrix[i][j - 1] > matrix[i][j])
            len = Math.max(len, 1 + dfsSearchPath1(matrix, dp, i, j - 1, m, n));
        if (j + 1 < n && matrix[i][j + 1] > matrix[i][j]) {
            len = Math.max(len, 1 + dfsSearchPath1(matrix, dp, i, j + 1, m, n));
        }
        return dp[i][j] = len;
    }

    // 解法2 https://youtu.be/yKr4iyQnBpY  bottom up方法
    public int longestIncreasingPath1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> (matrix[a[0]][a[1]] - matrix[b[0]][b[1]]));
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                minHeap.offer(new int[]{i, j});
            }
        }
        int longest = 1;
        // 最小堆  每次取最小, 有比她更小的则更新
        while (!minHeap.isEmpty()) {
            int[] cur = minHeap.poll();
            int i = cur[0];
            int j = cur[1];
            //  System.out.println("=====:\t" + matrix[i][j]);
            dp[i][j] = 1;
            if (i - 1 >= 0 && matrix[i - 1][j] < matrix[i][j])
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j] + 1);
            if (i + 1 < m && matrix[i + 1][j] < matrix[i][j])
                dp[i][j] = Math.max(dp[i][j], dp[i + 1][j] + 1);
            if (j - 1 >= 0 && matrix[i][j - 1] < matrix[i][j])
                dp[i][j] = Math.max(dp[i][j], dp[i][j - 1] + 1);
            if (j + 1 < n && matrix[i][j + 1] < matrix[i][j])
                dp[i][j] = Math.max(dp[i][j], dp[i][j + 1] + 1);
            longest = Math.max(dp[i][j], longest);
        }
        return longest;
    }

    // 解法3 topological sort 构建拓扑排序, 问题转换为 有向图的中的拓扑排序下的最长路径
    // todo  还是不会阿
    public int longestIncreasingPath2(int[][] matrix) {
        int[] shift = {0, 1, 0, -1, 0};
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] indegree = new int[m][n];
        Queue<int[]> queue = new LinkedList();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i - 1 >= 0 && matrix[i - 1][j] < matrix[i][j])
                    indegree[i][j] += 1;
                if (i + 1 < m && matrix[i + 1][j] < matrix[i][j])
                    indegree[i][j] += 1;
                if (j - 1 >= 0 && matrix[i][j - 1] < matrix[i][j])
                    indegree[i][j] += 1;
                if (j + 1 < n && matrix[i][j + 1] < matrix[i][j])
                    indegree[i][j] += 1;
                if (indegree[i][j] == 0) queue.offer(new int[]{i, j});
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
                    int newX = x + shift[k];
                    int newY = y + shift[k + 1];
                    if (0 <= newX && newX < m && 0 <= newY && newY < n && matrix[x][y] < matrix[newX][newY]) {
                        indegree[newX][newY]--;
                        if (indegree[newX][newY] == 0)
                            queue.offer(new int[]{newX, newY});
                    }
                }
            }
            len++;
        }
        return len;
    }

    // 59. 螺旋矩阵 II
    public int[][] generateMatrix(int n) {
        return null;
    }

    // 计算器系列
    // 224. 基本计算器  没有优先级了 我真滴服了 审题要仔细哦
    public int calculate224(String s) {
        int n = s.length(), res = 0;
        return 1;
    }

    // 227. 基本计算器 II
    public int calculate0(String s) {
        int res = 0;
        int last = 0;
        int pre = 0;
        char prevOperator = '+';
        char curOperator = '+';
        char lastOperator = '+';
        for (int i = 0; i < s.toCharArray().length; i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                res = res * 10 + s.charAt(i) - '0';
            }
            prevOperator = curOperator;
            curOperator = s.charAt(i);
            // 进行规则判定
            if ((curOperator == '*' || curOperator == '/') && (prevOperator == '-' || prevOperator == '+')) {
                lastOperator = prevOperator;
                last = pre;
                pre = res;
                res = 0;
            } else {
                //
                pre = cal(pre, res, prevOperator);
                res = 0;
            }
        }
        // 行不通 太复杂了 处理的情况 太复杂了 不够通用
        return res;
    }

    // 227. 基本计算器 II
    public int calculate227(String s) {
        // 用栈的思想来做
        Stack<Integer> stack = new Stack<>();
        char op = '+';
        int n = s.length();
        int res = 0;
        int num = 0;
        Set<Character> set = new HashSet<>();
        set.add('-');
        set.add('+');
        set.add('*');
        set.add('/');
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                num = num * 10 + s.charAt(i) - '0';
            }

            if (set.contains(s.charAt(i)) || i == n - 1) {
                // 并没有让op成为前置数字的运算符?? 而是后置运算符了
                if (op == '+') {
                    stack.push(num);
                }
                if (op == '-') {
                    stack.push(-num);
                }
                if (op == '*' || op == '/') {
                    int tmp = (op == '*') ? stack.peek() * num : stack.peek() / num;
                    stack.pop();
                    stack.push(tmp);
                }
                // update
                op = s.charAt(i);
                num = 0;
            }
        }
        while (!stack.isEmpty()) {
            res += stack.pop();
        }
        return res;
    }

    //   772. 基本计算器 III


    public int cal(int x, int y, char operator) {
        switch (operator) {
            case '-':
                return x - y;
            case '+':
                return x + y;
            case '/':
                return x / y;
            case '*':
                return x * y;
            default:
                throw new IllegalStateException("非法字符");
        }
    }

    // K系列


    // 149 直线上最多的点数 // https://youtu.be/bzsdelrRgNk // 对角线乘积和反对角线乘积之差/2 是 S△
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

    // 54. 螺旋矩阵
    public List<Integer> spiralOrder(int[][] matrix) {
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

    // 解法2
    public List<Integer> spiralOrder1(int[][] matrix) {
        List<Integer> res = new ArrayList();
        if (matrix.length == 0) return res;
        int colBegin = 0, colEnd = matrix[0].length - 1, rowBegin = 0, rowEnd = matrix.length - 1;
        while (true) {
            // right
            for (int j = colBegin; j <= colEnd; j++) {
                res.add(matrix[rowBegin][j]);
            }
            rowBegin++;
            if (rowBegin > rowEnd || colBegin > colEnd) break;
            // down
            for (int j = rowBegin; j <= rowEnd; j++) {
                res.add(matrix[j][colEnd]);
            }
            colEnd--;
            if (rowBegin > rowEnd || colBegin > colEnd) break;

            // left check rowBegin<=rowEnd
            if (rowBegin <= rowEnd) {
                for (int j = colEnd; j >= colBegin; j--) {
                    res.add(matrix[rowEnd][j]);
                }
            }
            // break contract rowBegin > rowEnd
            rowEnd--;
            if (rowBegin > rowEnd || colBegin > colEnd) break;
            // up
            if (colBegin <= colEnd) {
                for (int j = rowEnd; j >= rowBegin; j--) {
                    res.add(matrix[j][colBegin]);
                }
            }
            // break contract colBegin > colEnd
            colBegin++;
            if (rowBegin > rowEnd || colBegin > colEnd) break;
        }
        return res;
    }

    /*  public List<List<Integer>> getSkyline(int[][] building) {
          throw new IllegalStateException("扫描线方法 todo 有点难啊");
          List<List<Integer>> res = new ArrayList();
          int n = building.length;
          Arrays.sort(building, (a, b) -> {
              return a[0] - b[0];
          });
          for (int i = 0; i < n; i++) {
              List<Integer> sub = new ArrayList();
              // adjacent building
              while (building[i + 1][0] <= building[i][1]) {
                  int[] a = building[i];
                  int[] b = building[i + 1];
                  int aLi = building[i][0], aHi = building[i][1], aRi = building[i][2];
                  int bLi = building[i + 1][0], bHi = building[i + 1][1], bRi = building[i + 1][2];
                  //  第二轮的时候 你会发现情况越来越复杂了
                  if (bRi > aRi) {
                      if (bHi > aHi) {
                          aLi, bLi, bRi;
                      } else (bHi<aHi) {
                              aLi, aRi, bRi;
                      }else if (bHi == aHi) {
                          aLi, bRi;
                      }
                  } else {
                      if (bHi > aHi) {
                          aLi, bLi, bRi, aRi;
                      } else if (bHi == aHi) {
                          aLi, bRi
                      } else {
                          aLi, bRi
                      }
                  }

              }
              res.add(sub);
          }
          return res;
      }*/

    // 227 搜寻名人
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

    public int findCelebrity1(int n) {
        for (int i = 0, j = 0; i < n; ++i) {
            for (j = 0; j < n; ++j) {
                if (i != j && (knows(i, j) || !knows(j, i))) break;
            }
            if (j == n) return i;
        }
        return -1;
    }


    /*
    public int findCelebrity(int n) {
        // 思考错了   之前想的 col(j) 均为1 就是名人是错的. All col(j)=1 and row(j,j)=1 and row(i!=j,j)均为0
        // 看看别人的思路
        Map<Integer, Integer> memo = new HashMap();
        for (int j = 0; j < n; j++) {
            if (knows(0, j)) memo.put(j, 0);
        }

        Map<Integer, Integer> map = memo;
        for (int i = 1; i < n; i++) {
            memo = map;
            map = new HashMap<>();
            for (int j = 0; j < n; j++) {
                if (knows(i, j) && memo.containsKey(j)) {
                    map.put(j, i);
                    System.out.println(j + " | " + i);
                }
            }
        }
        System.out.println(map);
        Set<Integer> set = map.keySet();
        Integer key = -1;
        if (set.size() == 1) {
            for (Integer i : set) {
                key = i;
            }
        }
        return key;
    }*/

    public boolean knows(int a, int b) {
        return true;
    }


    /*
        public int findCelebrity(int n) {
            int[][] people = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    // 需要辅助数组
                    aux[i][j] = pepople[j][i];
                }
            }
            // 遍历一边 哪个 i下都是 know(i,j) true即可以
            return -1;
        }

        boolean knows(a, b) {
            return true;
        }
    */

    // 76 最小覆盖子串 http://bit.ly/2LvcJLu
    public String minimumWindow0(String s, String t) {
        int left = 0, right = 0, n = s.length(), start = 0, minLen = Integer.MAX_VALUE;
        Map<Character, Integer> cntTable = new HashMap();
        for (char c : t.toCharArray()) {
            cntTable.put(c, cntTable.getOrDefault(c, 0) + 1);
        }
        Map<Character, Integer> record = new HashMap();
        int match = 0;
        while (right < n) {
            // expend right
            char c1 = s.charAt(right);
            if (cntTable.containsKey(c1)) {
                record.put(c1, record.getOrDefault(c1, 0) + 1);
                if (record.get(c1) == cntTable.get(c1)) {
                    match++;
                }
            }
            right++;

            // find 可行解, pursue 最优解
            while (match == cntTable.size()) {
                // update
                if (right - left < minLen) {
                    start = left;
                    minLen = right - left;
                }
                char c2 = s.charAt(left);
                if (cntTable.containsKey(c2)) {
                    record.put(c2, record.getOrDefault(c2, 0) - 1);
                    if (record.get(c2) < cntTable.get(c2)) {
                        match--;
                    }
                }
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }

    // 解法2
    public String minimumWindow(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length()) return "";
        int[] bank = new int[27];
        int left = 0, right = 0, count = 0;
        int min = Integer.MAX_VALUE;
        String minStr = "";
        for (int i = 0; i < t.length(); i++) {
            bank[t.charAt(i) - 'A']++;
        }
        while (right < s.length()) {
            // if = 1 then bank--, count++
            // if =0 then bank--  go next
            if (bank[s.charAt(right++) - 'A']-- > 0) {
                count++;
            }
            //可行解
            while (count == t.length()) {
                if (min > right - left) {
                    min = right - left;
                    minStr = s.substring(left, right);
                }
                // 这里需要解释下
                // narrow left
                if (bank[s.charAt(left) - 'A']++ == 0) {
                    count--;
                }
                left++;
            }
        }
        return minStr;
    }

    // 162 寻找峰值
    // stack O(N)
    public int findPeakElement1(int[] nums) {
        Stack<Integer> s = new Stack();
        // init
        s.push(0);
        for (int i = 1; i < nums.length; i++) {
            if (nums[s.peek()] < nums[i]) s.push(i);
            return s.peek();
        }
        return nums.length - 1;
    }

    // 二分法 logN
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
        if (nums[mid] > nums[mid - 1]) {// l=2 r=3 就会陷入无限循环
            return binarySearch0(nums, mid, r);
        } else {
            return binarySearch0(nums, l, mid - 1);
        }
    }


    public int findPeakElement2(int[] nums) {
        return binarySearch2(nums, 0, nums.length - 1);
    }

    public int binarySearch2(int[] nums, int l, int r) {
        if (l == r) return l;
        int mid = (r - l) / 2 + l;
        if (nums[mid] < nums[mid - 1]) { // 越界 l=0, r=1, mid=0  数组越界
            return binarySearch2(nums, l, mid);
        } else {
            return binarySearch2(nums, mid + 1, l);
        }
    }

    public int findPeakElement3(int[] nums) {
        return binarySearch3(nums, 0, nums.length - 1);
    }

    public int binarySearch3(int[] nums, int l, int r) {
        if (l == r) {
            return l;
        }
        int mid = (r - l) / 2 + l;
        if (nums[mid] > nums[mid + 1]) {//  保证不会越界 l=l,r=l+1,mid=l; 所以访问mid+1就是r 保证不会越界. 如果是mid-1,既l-1,如果l是0，那就越界了！
            return binarySearch3(nums, l, mid);
        } else {
            return binarySearch3(nums, mid + 1, r);
        }
    }


    // 1 0的位置  两位数的范围
    // 91 解码方法 斐波那些数列翻版
    public int numDecodings(String s) {
        int n = s.length();
        if (n == 0 || s.isEmpty() || s.charAt(0) == '0') return 0;
        if (n == 1) return 1;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            if (s.charAt(i - 1) != '0') dp[i] += dp[i - 1];
            if (i >= 2 && s.substring(i - 2, i).compareTo("10") >= 0 && s.substring(i - 2, i).compareTo("26") <= 0) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[n];
    }

    // 91 解码方法 还是有点晦涩哦
    public int numDecodings0(String s) {
        int n = s.length();
        if (n == 0 || s.isEmpty() || s.charAt(0) == '0') return 0;
        if (n == 1) return 1;
        int w1 = 1;
        int w2 = 1;
        for (int i = 1; i < n; i++) {
            int w = 0;
            char c1 = s.charAt(i), c2 = s.charAt(i - 1);
            boolean b1 = isValid(c1), b2 = isValid(c2, c1);
            if (!b1 && !b2) return 0;
            if (b1) w = w1; // w1--> dp[i-1]
            if (b2) w += w2; //w2--> dp[i-2]
            w2 = w1;
            w1 = w;
        }
        return w1;
    }

    public boolean isValid(char c) {
        return c != '0';
    }

    public boolean isValid(char l, char r) {
        return l == '1' || (l == '2' && r <= '6');
    }

    // 下面都是错的
    public int numDecodings2(String s) {
        if (s.isEmpty() || s.charAt(0) != '0') return 0;
        int n = s.length();
        int[] dp = new int[n + 1];
        return 1;
    }


    public int numDecodings3(String s) {
        return numDecodeHelper(s, 0);
    }

    public int numDecodeHelper(String s, int count) {
        if (s == null || s.length() == 0 || s.length() == 1) return count;
        if (s.length() == 2) {
            int res = num2num(s);
            if (res >= 11 && res <= 26 && res != 20) {
                return count + 2;
            }
            return count + 1;
        }
        int n = s.length();
        if (s.charAt(0) != '0') {
            count = numDecodeHelper(s.substring(1, n), count + 1);
            count = numDecodeHelper(s.substring(2, n), count + 1);
        }
        if (s.charAt(0) == '0' && s.charAt(1) != '0') {
            count = numDecodeHelper(s.substring(2, n), count + 1);
        }
        return count;

    }

    public int num2num(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res = res * 10 + s.charAt(i) - '0';
        }
        return res;
    }

    // 130 被围绕的区域  http://bit.ly/2L0HsND
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

    // 131 分割回文串
    // 验证回文串. 收集回文串
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        List<String> sub = new ArrayList<>();
        dfs(s, 0, sub, res);
        return res;
    }

    public void dfs(String s, int pos, List<String> sub, List<List<String>> res) {
        if (pos == s.length()) {
            res.add(new ArrayList<>(sub));
            return;
        }
        for (int i = pos; i < s.length(); i++) {
            if (isPal(s, pos, i)) {
                sub.add(s.substring(pos, i + 1));
                dfs(s, i + 1, sub, res);
                sub.remove(sub.size() - 1);
            }

        }
    }

    public boolean isPal(String s, int left, int right) {
        while (left <= right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }

    // 127 单词接龙 tag:BFS  邻接
    // 这个方法 不好的一点在于,要判定 每个单词是否与单次列表相连通
    // 这个问题 可以变成Graph 的 最短路径问题
    //  我们需要构建邻接表
    public int ret127 = Integer.MAX_VALUE;

    public int ladderLength1(String beginWord, String endWord, List<String> wordList) {
        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).equals(endWord))
                return ladderHelper(beginWord, endWord, 1, wordList);
            ;
        }
        return 0;
    }

    public int ladderHelper(String beginWord, String endWord, int res, List<String> word) {
        if (connected(beginWord, endWord)) {
            return res + 1;
        }
        if (word.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        for (int i = 0; i < word.size(); i++) {
            String s = word.get(i);
            if (connected(beginWord, s)) {
                word.remove(i);
                beginWord = s;
                ret127 = Math.min(ret127, ladderHelper(beginWord, endWord, res + 1, word));
            }
        }
        return ret127;
    }

    public boolean connected(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        Map<Character, Integer> map = new HashMap<>();
        int res = 0;
        for (char c : s1.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
            res++;
        }
        for (char c : s2.toCharArray()) {
            if (map.containsKey(c)) {
                res--;
            }
        }
        return res == 1 ? true : false;
    }

    // 解法2 问题化为 构建邻接表  , 转变成无向图的最短路径问题。
    public int ladderLength2(String beginWord, String endWord, List<String> words) {
        if (!words.contains(endWord)) return 0;
        int L = beginWord.length();
        Map<String, List<String>> allComboDict = new HashMap();
        // 构建 adjacent edges;
        for (String word : words) {
            for (int i = 0; i < L; i++) {
                String newWord = word.substring(0, i) + "*" + word.substring(i + 1, L);
                List<String> wildCards = allComboDict.getOrDefault(newWord, new ArrayList());
                wildCards.add(word);
                allComboDict.put(newWord, wildCards);
            }
        }

        // BFS init
        Queue<Pair<String, Integer>> queue = new LinkedList();
        queue.add(new Pair(beginWord, 1));
        Map<String, Boolean> visited = new HashMap();
        visited.put(beginWord, true);

        // BFS traversal
        while (!queue.isEmpty()) {
            Pair<String, Integer> point = queue.remove();
            String word = point.key;
            Integer level = point.val;
            for (int i = 0; i < L; i++) {
                String newWord = word.substring(0, i) + "*" + word.substring(i + 1, L);
                for (String adjacentWord : allComboDict.getOrDefault(newWord, new ArrayList<>())) {
                    if (adjacentWord.equals(endWord)) {
                        return level + 1;
                    }
                    if (!visited.containsKey(adjacentWord)) {
                        visited.put(adjacentWord, true);
                        queue.add(new Pair(adjacentWord, level + 1));
                    }
                }
            }
        }
        return 0;
    }


    public int ladderLength3(String beginWord, String endWord, List<String> words) {
        if (!words.contains(endWord)) return 0;
        // 构建邻接表
        Map<String, List<String>> combo = new HashMap<>();
        int n = beginWord.length();
        for (String s : words) {
            for (int i = 0; i < n; i++) {
                String wildWord = s.substring(0, i) + "*" + s.substring(i + 1, n);
                List<String> matchWords = combo.getOrDefault(wildWord, new ArrayList());
                matchWords.add(s);
                combo.put(wildWord, matchWords);
            }
        }
        // double BFS init
        Queue<Pair<String, Integer>> startQueue = new LinkedList();
        Map<String, Integer> startVisited = new HashMap();
        startQueue.add(new Pair(beginWord, 1));
        startVisited.put(beginWord, 1);

        Queue<Pair<String, Integer>> endQueue = new LinkedList();
        Map<String, Integer> endVisited = new HashMap();
        endQueue.add(new Pair(endWord, 1));
        endVisited.put(endWord, 1);

        while (!startQueue.isEmpty() && !endQueue.isEmpty()) {
            Pair<String, Integer> p1 = startQueue.remove();
            String w1 = p1.key;
            Integer l1 = p1.val;
            for (int i = 0; i < n; i++) {
                String wild1 = w1.substring(0, i) + "*" + w1.substring(i + 1, n);
                for (String adjacentWord : combo.getOrDefault(wild1, new ArrayList<>())) {
                    if (endVisited.containsKey(adjacentWord)) {
                        return l1 + endVisited.get(adjacentWord);
                    }
                    if (!startVisited.containsKey(adjacentWord)) {
                        startVisited.put(adjacentWord, l1 + 1);
                        startQueue.add(new Pair(adjacentWord, l1 + 1));
                    }
                }
            }

            Pair<String, Integer> p2 = endQueue.remove();
            String w2 = p2.key;
            Integer l2 = p2.val;
            for (int i = 0; i < n; i++) {
                String wild2 = w2.substring(0, i) + "*" + w2.substring(i + 1, n);
                for (String adjacentWord : combo.getOrDefault(wild2, new ArrayList<>())) {
                    if (startVisited.containsKey(adjacentWord)) {
                        return l2 + startVisited.get(adjacentWord);
                    }
                    if (!endVisited.containsKey(adjacentWord)) {
                        endVisited.put(adjacentWord, l2 + 1);
                        endQueue.add(new Pair(adjacentWord, l2 + 1));
                    }
                }
            }
        }

        return 0;
    }

    //212 单词搜索Ⅱ
    public Set<String> result212 = new HashSet();

    public List<String> findWords(char[][] board, String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfsFindWord(board, visited, "", i, j, trie);
            }
        }
        return new ArrayList(result212);
    }

    //
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


    // 134 加油站⛽️  bad 错误解法
    public int canCompleteCircuitbad(int[] gas, int[] cost) {
        int n = gas.length;
        int idx = 0;
        int delta = 0;
        for (int i = 0; i < n; i++) {
            if (gas[i] - cost[i] >= 0) {
                int temp = gas[i] - cost[i] + gas[(1 + i) % n];
                if (temp > delta) {
                    idx = i;
                    delta = temp;
                }
            }
        }
        System.out.println(Arrays.toString(gas) + "\n" + Arrays.toString(cost));
        System.out.println(idx + " " + delta);
        int gi = idx;
        int sum = gas[gi];
        gi = (gi + 1) % n;
        int ci = idx;

        while (gi != idx) {
            int temp = sum - cost[ci];
            if (temp < 0) return -1;
            sum = sum - cost[ci] + gas[gi];
            gi = (gi + 1) % n;
            ci = (ci + 1) % n;
        }
        // 缺少这个条件
        if (sum - cost[ci] < 0) return -1;
        return idx;
    }

    public int canCompleteCircuit1(int[] gas, int[] cost) {
        // 遍历的时候 if sum <0 说明这段区间内 均不行, 那么再下个起点继续  下个起点 if sum < 0 那么就下个起点再继续下去哦
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

    // 150 逆波兰表达式求值
    public int evalRPN(String[] tokens) {
        Stack<Integer> s = new Stack();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (!isOp(tokens[i])) {
                int tmp = decodeStr(tokens[i]);
                s.push(tmp);
            } else {
                int m = s.pop();
                int n = s.pop();
                // res = op(m, n, token);
                // 注意左右操作数
                res = op(n, m, token);
                s.push(res);

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
        for (char c : s.toCharArray()) {
            res = res * 10 + c - '0';
        }
        return res * sign;
    }

    public boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    // 矩阵置零
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

    // 166 分数到小数
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }
        StringBuilder res = new StringBuilder();
        res.append((numerator ^ denominator) > 0 ? "" : "-");
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
      /*  long num = Math.abs( numerator);
        long den = Math.abs(denominator);*/
        // den 还是没变哦 取完绝对值还是负数
        System.out.println(num + " " + den);

        // integer part
        res.append(num / den);
        num = num % den;
        if (num == 0) {
            return res.toString();
        }

        // fraction part
        res.append(".");
        Map<Long, Integer> map = new HashMap();
        map.put(num, res.length());
        while (num != 0) {
            num = num * 10;
            res.append(num / den);
            num = num % den;
            if (map.containsKey(num)) {
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

   /* public void demo(){
        if (numerator == 0) {
            return "0";
        }
        StringBuilder res = new StringBuilder();
        // "+" or "-"
        res.append(((numerator > 0) ^ (denominator > 0)) ? "-" : "");
        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);

        // integral part
        res.append(num / den);
        num %= den;
        if (num == 0) {
            return res.toString();
        }

        // fractional part
        res.append(".");
        HashMap<Long, Integer> map = new HashMap<Long, Integer>();
        map.put(num, res.length());
        while (num != 0) {
            num *= 10;
            res.append(num / den);
            num %= den;
            if (map.containsKey(num)) {
                int index = map.get(num);
                res.insert(index, "(");
                res.append(")");
                break;
            }
            else {
                map.put(num, res.length());
            }
        }
        return res.toString();

    }
*/
    //  3 4 5 7 8 9
    //  1---> m1 =3, m2 = MAX_VALUE
    // 2 --->  m2 = 4
    //  3  return true;

    // 6 5 4 8 7 9
    // 1 ---> m1 = 6 m2 =MAX_VALUE
    // 2 ---> m1 = 5 m2 =Max_VALUE
    //  3  ----> m1 =4  m2 =MAX_VALUE
    //  4 ---- > m1 = 4 m2 = 8
    // 5 ---->  m1 =4 m2 = 7;
    //  6  return true;
    ///


    // 334 递增的三元子序列 注意关键字 是 3哦  想想为什么呢
    public boolean increasingTriplet1(int[] nums) {
        int m1 = Integer.MAX_VALUE, m2 = Integer.MAX_VALUE;
        for (int num : nums) {
            // m1 <= m ,  m2 <= m 并且m1 <  m2
            if (m1 > num) m1 = num;
            else if (m2 > num) m2 = num;
            else return true;
        }
        return false;
    }

    public boolean increasingTriplet(int[] nums) {
        if (nums.length == 0 || nums == null) return false;
        int n = nums.length;
        int[] foward = new int[n];
        int[] backword = new int[n];
        int min = nums[0];
        int max = nums[n - 1];
        for (int i = 0; i < n; i++) {
            if (nums[i] < min) {
                min = nums[i];
            }
            foward[i] = min;
        }
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] > max) {
                max = nums[i];
            }
            backword[i] = max;
        }
        for (int i = 0; i < n; i++) {
            if (foward[i] < nums[i] && nums[i] < backword[i]) return true;
        }
        return false;
    }

    // 138 复制带随机指针的链表
    // http://bit.ly/2KWFfDW 解题的关键在于,访问链表节点的随机指正的时候 需要记录已经访问的节点

    public Map<RandomNode, RandomNode> visitedHash138 = new HashMap();

    public RandomNode copyRandomList(RandomNode head) {

        if (head == null) {
            return null;
        }

        if (visitedHash138.containsKey(head)) {
            return this.visitedHash138.get(head);
        }

        RandomNode node = new RandomNode();
        node.val = head.val;

        visitedHash138.put(head, node);

        node.next = copyRandomList(node.next);
        node.random = copyRandomList(node.random);
        return node;
    }

    // 解法2
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

    // 289 生命游戏
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
        // show2DArray(board);
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

    public void show2DArray(int[][] board) {
        System.out.println("==============");
        for (int i = 0; i < board.length; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
        System.out.println("==============");
    }

    // 315 计算右侧小于当前元素的个数  O(N²)  不符合 [2,0,1]
    public List<Integer> countSmaller0(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return Arrays.stream(dp).boxed().collect(Collectors.toList());
    }

    // 解法1 二分法
    public List<Integer> countSmaller(int[] nums) {
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
    public int findIndex(List<Integer> sorted, int target) {
        int i = 0;
        int j = sorted.size();
        // this is the right way to binary search ,idea from c++ lower_bound()  method
        // range from i (inclusive) to j (exclusive)  ----->   [i,j)
        while (i < j) {
            int mid = i + (j - i) / 2;
            // sorted.get(mid) <= target is wrong , when duplicate nums exist
            if (sorted.get(mid) < target) {
                // i is assigned to mid+1 ,prevent    infinite cycyle and out of range  error
                i = mid + 1;
            } else {
                j = mid;
            }
        }
        // no matter return i or j  ,  which is same value
        return i;
    }

    // 解法2  BST 解法  只能作为参考 不能拿来当做面试用 http://bit.ly/326UjoA
    public List<Integer> countSmaller1(int[] nums) {
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

    // 74
    // 240 搜索二维矩阵 Ⅱ
    // 错误思路  对角线对称部分 不能保证上半部分>下半部分  因为 是左至右递增 以及 上到下递增
    // 正确思路应该是 先判断他在上下半部分,然后 再判断他在左右半部分.
    public boolean searchMatrix1(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0 || matrix == null) return false;
        int m = matrix.length, n = matrix[0].length;
        if (matrix[0][0] > target || target > matrix[m - 1][n - 1]) return false;
        int u = 0, d = m, l = 0, r = n;
        int cmp = 0;
        while (u < d || l < r) {
            int m1 = u + (d - u) / 2;
            int m2 = l + (r - l) / 2;
            if (m1 == m) {
                return matrix[m - 1][m2] == target;
            } else if (m2 == n) {
                return matrix[m1][n - 1] == target;
            } else cmp = matrix[m1][m2];

            if (cmp == target) return true;
            else if (cmp < target) {
                u = m1 + 1;
                l = m2 + 1;
            } else if (cmp > target) {
                d = m1;
                r = m2;
            }
        }
        return false;
    }


    public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0 || matrix == null) return false;
        int m = matrix.length, n = matrix[0].length;
        if (matrix[0][0] > target || matrix[m - 1][n - 1] < target) return false;
        int u = 0, d = m;
        while (u < d) {
            int m1 = u + (d - u) / 2;
            int cmp1 = matrix[m1][0];
            if (cmp1 < target) {
                u = m1 + 1;
            } else if (cmp1 > target) {
                d = m1;
            } else return true;
        }
        u = u - 1;

        int l = 0, r = n;
        while (l < r) {
            int m2 = l + (r - l) / 2;
            int cmp2 = matrix[u][m2];
            if (cmp2 < target) {
                l = m2 + 1;
            } else if (cmp2 > target) {
                r = m2;
            } else return true;
        }
        return false;

    }

    // 上买呢的2个方法都是异曲同工, 都是同样的错误思路!
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0 || matrix == null) return false;
        int m = matrix.length, n = matrix[0].length;
        if (matrix[0][0] > target || target > matrix[m - 1][n - 1]) return false;
        int x = matrix.length - 1, y = 0;
        while (true) {
            if (matrix[x][y] > target) x--;
            else if (matrix[x][y] < target) y++;
            else return true;
            if (x < 0 || y > n - 1) break;
        }
        return false;
    }

    //324 摆动排序Ⅱ
    // 已经排好序的数组 前半部分和后半部分  对折之后  交替插入
    public void wiggleSort(int[] nums) {
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

    // 解法1 快排最佳实践 todo
    public void wiggleSort1(int[] nums) {
        int n = nums.length;
        int median = findKthLargest0(nums, (n + 1) / 2);
        int l = 0, i = 0, r = n - 1;
        while (i < r) {
            if (nums[newIndex(i, n)] > median) {
                swap(nums, newIndex(l++, n), newIndex(i++, n));
            } else if (nums[newIndex(i, n)] < median) {
                swap(nums, newIndex(r--, n), newIndex(i, n));
            } else {
                i++;
            }
        }

    }

    public int newIndex(int index, int n) {
        return (1 + 2 * index) % (n | 1);
    }


    //  解法2 错误的 无法处理带有重复元素的
    public void wiggleSort2(int[] nums) {
        int n = nums.length;
        if (n <= 1) return;
        Arrays.sort(nums);
        int i = 1;
        while (i < nums.length) {
            if (i < nums.length - 1) swap(nums, i, i + 1);
            i = i + 2;
        }
    }

    // 452 四数相加Ⅱ
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

    // 38报数
    // for the nth number, you just need to count characters of the (n-1)th number,
    // for the (n-1)th number, you just need to count characters of  the (n-2)th number,
    // 解法1 递归
    public String countAndSay(int n) {
        if (n == 1) return "1";
        StringBuilder res = new StringBuilder();
        // recursively call for (n-1) th number, "0" is only for the edge case at the end of the loop with `s.charAt(i+1)`
        String s = countAndSay(n - 1) + "0";
        for (int i = 0, c = 1; i < s.length() - 1; i++, c++) {
            // if next digit is different, then append the count so far `c` and the digit itself, then set count `c` to zero
            if (s.charAt(i + 1) != s.charAt(i)) {
                res.append(c).append(s.charAt(i));
                c = 0;
            }
        }

        return res.toString();
    }

    // 解法1 递归
    public String countAndSay1(int n) {
        if (n == 1) return "1";
        StringBuilder res = new StringBuilder();
        String s = countAndSay1(n - 1) + "0";// edge case   len-1  len-2

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
    public String countAndSay2(int n) {
        String s = "1";
        for (int i = 1; i < n; i++) {
            s = cntMap(s);
        }
        return s;
    }

    public String cntMap(String s) {
        StringBuilder res = new StringBuilder();
        s += "0";
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
    //  378 有序矩阵中 第K小的元素
    public int kthSmallest(int[][] matrix, int k) {
        // 最大堆 max heap
        PriorityQueue<Integer> pq = new PriorityQueue(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
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
        int n = matrix.length, lo = matrix[0][0], hi = matrix[n - 1][n - 1];
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = lessEqual(matrix, mid);
            if (count < k) lo = mid + 1;
                // 为什么是mid-1呢 而不是mid
            else hi = mid;
        }
        return lo;
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

    // 230 二叉搜索树中第k小 通用方法
    public int kthSmallest(TreeNode root, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((o1, o2) -> {
            return o2.compareTo(o1);
        });
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

    // 解法2  中序遍历
    public int count230;
    public int res230 = 0;

    public int kthSmallest1(TreeNode root, int k) {
        inorder(root, k);
        return res230;
    }

    // 还是有点不太明白为什么cnt 又会变成0了？？？
    public void inorder(TreeNode root, int k) {
        if (root == null) {
            return;
        }
        inorder(root.left, k);
        count230 = count230 + 1;
        if (count230 == k) {
            res230 = root.val;
            return;
        }
        inorder(root.right, k);
    }

    // 解法3 二分查找法
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

    // 159 最多有2个不同字符的最长子串
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        return lengthOfLongestSubstringKDistinct(s, 2);
    }

    //  解法3 不需要额外空间的做法 参考解法4 http://bit.ly/2ZMzCws 类似于leetcode 904
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
    // 340 至多包含k个不同字符的最长子串
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        Map<Character, Integer> map = new HashMap();
        int left = 0;
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char key = s.charAt(i);
            map.put(key, map.getOrDefault(key, 0) + 1);
            while (map.size() > k) {
                char leftKey = s.charAt(left);
                int count = map.get(leftKey);
                count = count - 1;
                // 忘记更新了
                map.put(leftKey, count);
                if (count == 0) {
                    map.remove(leftKey);
                }
                left++;
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }

    // 解法2
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
    }

    // 395 至少有k个重复的字符的最长子串
    public int longestSubstring(String s, int k) {
        int res = 0, i = 0, N = s.length();
        while (i + k - 1 < N) {
            int[] m = new int[26];
            int mask = 0;
            int maxIdx = i;
            for (int j = i; j < N; j++) {
                int t = s.charAt(j) - 'a';
                m[t]++;
                // bit mask 操作
                if (m[t] < k) {
                    mask = mask | (1 << t);
                } else {
                    mask = mask & (~(1 << t));
                }
                if (mask == 0) {
                    res = Math.max(res, j - i + 1);
                    maxIdx = j;
                }
            }
            i = maxIdx + 1;
        }
        return res;
    }

    // 解法2 递归
    public int longestSubstring1(String s, int k) {
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

    // 解法3 interview friendly
    public int longestSubstring2(String s, int k) {
        int res = 0, N = s.length(), maxIdx = 0;
        int[] times = new int[128];
        boolean ok = true;
        for (int i = 0; i < N; i++) {
            times[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < N; i++) {
            if (times[s.charAt(i) - 'a'] < k) {
                // 子递归的第一种情况
                res = Math.max(res, longestSubstring2(s.substring(maxIdx, i), k));
                maxIdx = i + 1;
                ok = false;
            }
        }
        // 子递归的第二种情况
        return ok ? N : Math.max(res, longestSubstring2(s.substring(maxIdx, N), k));
    }


    // 207 课程表 todo
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        return false;
    }

    // 440 字典序第K小的数字 todo 10叉树
    public int findKthSmallest(int n, int k) {
        int cur = 1;
        k--;
        while (k > 0) {
            int step = 0, first = cur, last = cur + 1;
            while (first <= n) {
                step += Math.min(n + 1, last) - first;
                first *= 10;
                last *= 10;
            }
            // if (step)
        }
        return -1;

    }

    // 类似于46的全排列问题
    // 51 N皇后问题
    char Queen = 'Q', Empty = '.';

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList();
        boolean[][] matrix = new boolean[n][n];
        backTrack(ans, new ArrayList(), matrix, 0, n);
        return ans;
    }

    public void backTrack(List<List<String>> ans, List<String> track, boolean[][] matrix, int row, int n) {
        if (track.size() == row) {
            ans.add(new ArrayList(track));
        } else {
            for (int j = 0; j < n; j++) {
                if (!isValid(row, j, matrix, n)) continue;
                ;
                track.add(convert(n, j));
                matrix[row][j] = true;
                backTrack(ans, track, matrix, row + 1, n);// 放置Q, track.add() 撤销Q,track.remove
                track.remove(track.size() - 1);
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
    // 位运算法

    // backtrack 回溯算法

    // [special专题]（分治思想/二分查找）https://www.cnblogs.com/grandyang/p/6854825.html

    //  grocery
    //  http://bit.ly/2HVV8Zz  遍历问题 注意不要无限递归下去就行 就是要找出所有可能的base case  return


    // 面筋题目
    //  http://bit.ly/32H6YPn
    // leetcode 664
    public int strangePrinter(String s) {
        return -1;
    }

    // leetcode 415
    // https://leetcode-cn.com/problems/add-strings/
    // 415. 字符串相加

    // follow up todo 590. N叉树的后序遍历
    public List<Integer> postorder(Node root) {
        return null;
    }

    // 207. 课程表 拓扑排序

    // 124. 二叉树中的最大路径和
    public int maxPathSum(TreeNode root) {
        return 1;
    }

    // [tag:微软实习面筋] https://www.1point3acres.com/bbs/thread-540776-1-1.html
    // 验证是否是一棵二叉树
    public boolean validBST(TreeNode root) {
        int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
        return help(root, min, max);
    }

    public boolean help(TreeNode root, int min, int max) {
        int left = root.left.val;
        int val = root.val;
        int right = root.right.val;
        if (min <= left && left <= val && val <= right && right <= max) {
            help(root.left, min, val);
            help(root.right, val, max);
        }
        return false;
    }
    // leetcode 516  [tag:微软面筋]  https://www.1point3acres.com/bbs/thread-541121-1-1.html

    // [tag:微软面筋] 求M的N次方的后3位
    public int getLastThreeNum(int m, int n) {
        int res = 1;
        for (int i = 0; i < n; i++) {
            res = (res * (m % 1000)) % 1000;
        }
        return res;
    }

    // leetcode 103 二叉树的锯齿形层遍历
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

    public List<List<Integer>> zigzagLevelOrder1(TreeNode root) {
        List<List<Integer>> ret = new ArrayList();
        dfsZigzag(root, 0, ret);
        return ret;
    }

    public void dfsZigzag(TreeNode root, int level, List<List<Integer>> ret) {
        if (root == null) return;
        if (ret.size() <= level) { // 这里比较trick 什么时候new 一个List
            List<Integer> newLevel = new LinkedList<>();
            ret.add(newLevel);
        }
        List<Integer> sub = ret.get(level);
        if ((level & 1) == 0) {
            sub.add(root.val);
        } else {
            sub.add(0, root.val);
        }
        dfsZigzag(root.left, level + 1, ret);
        dfsZigzag(root.right, level + 1, ret);

    }
    // 共同祖先问题


    // follow up
    //  数2012的M次方与数2012的N次方的最后三位数相同,求正整数M和N,使M+N最小
    /*public int resMinMN = 0;
    public int MinMN(int m,int n){
       int r1 = getLastThreeNum(2012,m);
       int r2 = getLastThreeNum(2012,n);
       if (r1==r2){
          res = Math.min(m+n,res);
       }else {

       }

    }
    */
    //[tag:微软面经] https://www.1point3acres.com/bbs/thread-506842-1-1.html
    // leetcode 415
    public String addStrings(String num1, String num2) {
        return "";
    }

    //[tag:微软面经] leetcode 419 https://www.1point3acres.com/bbs/thread-506842-1-1.html
    public int countBattleships(char[][] board) {
        return -1;
    }

    // [tag:微软面经] https://www.1point3acres.com/bbs/thread-535401-1-1.html
    // https://www.geeksforgeeks.org/k-th-element-two-sorted-arrays/
    int kth(int arr1[], int arr2[], int m, int n, int k) {
        return -1;
    }

    //  [tag:微软面经] https://www.1point3acres.com/bbs/thread-535401-1-1.html
    // https://www.geeksforgeeks.org/array-rotation/


    // [tag:微软面经] https://www.1point3acres.com/bbs/thread-535401-1-1.html
    // leetcode 716
    // [tag:微软面经] https://www.1point3acres.com/bbs/thread-529016-1-1.html
    // https://www.hackerrank.com/challenges/almost-sorted/problem
    // leetcode 969 https://leetcode.com/problems/pancake-sorting/

    // 字节
    // https://blog.csdn.net/kuangsonghan/article/details/82767363

    // airbnb 面筋
    // leetcode 316. 去除重复字母

    // https://www.1point3acres.com/bbs/thread-532266-1-1.html
    // http://www.voidcn.com/article/p-srkptnpu-bnt.html

}







