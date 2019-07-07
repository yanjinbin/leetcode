package com.yanjinbin.leetcode;

import java.util.*;

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

    // #148 归并排序
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        //seperate the list into two parts
        //Track the last node of first list and point the end to null
        ListNode prev = null;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        prev.next = null;
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);

        return merge(l1, l2);
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
        System.out.println("dummy:" + dummy.val + "next:" + dummy.next);
        return dummy.next;
    }


    private ListNode merge_1(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val <= l2.val) {
            l1.next = this.merge_1(l1.next, l2);
            return l1;
        } else {
            l2.next = this.merge_1(l1, l2.next);
            return l2;
        }
    }

    // 234 Palindrome Linked List 回文单链表
    public boolean isPalindrome(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            // prevent fast.next is null
            fast = fast.next.next;
        }
        if (fast != null) {
            slow = slow.next;
        }
        ListNode newHead = reverseSingleLinkedList(slow);
        while (newHead != null) {
            if (head.val != newHead.val) {
                return false;
            }
        }
        return true;
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

    //这道题还有一种特别巧妙的方法，虽然题目中强调了链表中不存在环，
    // 但是我们可以用环的思想来做，我们让两条链表分别从各自的开头开始往后遍历，
    // 当其中一条遍历到末尾时，我们跳到另一个条链表的开头继续遍历。两个指针最终会相等，
    // 而且只有两种情况，
    // 一种情况是在交点处相遇，
    // 另一种情况是在各自的末尾的空节点处相等。
    // 为什么一定会相等呢，因为两个指针走过的路程相同，
    // 是两个链表的长度之和，所以一定会相等。这个思路真的很巧妙，而且更重要的是代码写起来特别的简洁，参见代码如下：

    //  [160]. Intersection of Two Linked Lists
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode a = headA, b = headB;
        // 两个指针最终会相等，
        //    // 而且只有两种情况，
        //    // 一种情况是在交点处相遇，
        //    // 另一种情况是在各自的末尾的空节点处相等。
        while (a != b) {
            a = (a != null) ? a.next : headB;
            b = (b != null) ? b.next : headA;
        }
        return a;
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

    // 121. 买卖股票的最佳时机
    public int maxProfit(int prices[]) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if (prices[i] - minPrice > maxProfit) {
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }

    // leetcode 15 3sum
    // https://www.cnblogs.com/grandyang/p/4481576.html
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
    // https://www.cnblogs.com/grandyang/p/4606710.html

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
        reverse(nums, i + 1, nums.length - 1);

    }

    public void swap(int[] nums, int i, int j) {
        if (i == j) return;
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public void reverse(int[] nums, int i, int j) {
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

    // 53. 最大子序和 错误解法
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
    // todo follow up问题 https://blog.crayygy.com/14599905787744.html#toc_6
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
}
