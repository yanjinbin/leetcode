package com.yanjinbin.leetcode;


// ArrayDeque（双端队列）内部实现是一个循环数组，bit 巧妙运用


import javax.sound.sampled.Line;
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

    // ② 75. 颜色分类  只要遇到 0和2 就进行交换即可
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

    // 78 子集和
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

    //② 41 缺失的第一个正数  http://bit.ly/2Pir4Mc 类似鸽巢原理 ,放到正确的位置
    public int firstMissingPositive01(int[] nums) {
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

    public int firstMissingPositive02(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            set.add(i);
        }
        int i = 1;
        while (set.contains(i)) i++;
        return i;
    }

    // ② 287. 寻找重复数  除了下面这2种 ,lowB点用set处理或者排好序
    // 可以把index对应的value当做一个状态值 那么 value重复的化 就相当于是存在还了,可以使用floyd算法来检测
    // Floyd算法wiki ---->   http://bit.ly/2S1omdy  龟兔赛跑方法
    public int findDuplicate0(int[] nums) {
        // Find the intersection point of the two runners.
        int tortoise = nums[0];
        int hare = nums[0];
        while (tortoise != hare) {
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
        }

        // Find the "entrance" to the cycle.
        int s = nums[0];
        int m = tortoise;
        while (s != m) {
            s = nums[s];
            m = nums[m];
        }
        return s;

    }

    // ② 这种二分法还是比较少见的 但是也存在多钟限制阿 中间数的计算近似 median=(right+left)/2;
    public int findDuplicate1(int[] nums) {
        // // 特殊case n = 1 ,长度为2，{1,1} ; n= 2 ,长度为3,{1,2,2} or {1,1,2}
        int lo = 0, hi = nums.length;
        while(lo<hi){
            int mid = lo+(hi-lo)/2;
            int count = 0;
            for(int num:nums){ // 计算小于mid的个数,
                if(num<=mid)count++;
            }
            if(count<=mid){
                lo = mid+1;
            }else{
                hi = mid;
            }
        }
        return lo;
    }

    //② 442 数组中重复的数据  鸽巢原理
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

    //② 448. 找到所有数组中消失的数字 http://bit.ly/2qMgEKN
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

    // bit map 用法
    public List<Integer> findDisappearedNumbers02(int[] nums) {
        return null;
    }

    // ② 142. 环形链表 II
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
        while (m != slow) {
            slow = slow.next;
            m = m.next;
        }
        return slow;
    }

    // 摩尔投票法 仔细想想 还是对的 因为不管如何排列,众数 频次肯定>=1阿  whatever even or odd
    // 169
    public int majorityElement(int[] nums) {
        int candidate = nums[0], count = 1;
        for (int i = 1; i < nums.length; ++i) {
            if (count == 0) {
                candidate = nums[i];
                count = 1;// 互斥
            } else if (nums[i] == candidate) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
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

    // ② 581. 最短无序连续子数组
    // 先排序
    public int findUnsortedSubarray0(int[] nums) {
        int start = 0, end = nums.length - 1;
        int[] bak = Arrays.copyOf(nums, nums.length);
        Arrays.sort(bak);
        while (start < nums.length && nums[start] == bak[start]) start++;
        while (end > start && nums[end] == bak[end]) end--;
        return end - start + 1;
    }

    public int findUnsortedSubarray2(int[] nums) {
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
        return end - start + 1;
    }

    // ② 560. 和为K的子数组
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
        map.put(0, 1);// 比较tricky的啊
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    //② 56. 合并区间
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

  /*  public int partition(int[] nums,int lo, int hi){
        int pivot = nums[lo];
        int l = lo+1, r = hi;
        while(l<=r){
            if(nums[l] < pivot && pivot < nums[r]){
                swap(nums,l++,r--);
            }
            if(nums[l]>=pivot)l++;
            if(nums[r]<=pivot)r--;
        }
        swap(nums,lo,r);
        return r;
    }*/

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
        //为什么必须要 交还的是R呢
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


    // ② 238. 除自身以外数组的乘积 至少需要2次遍历来
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

    //② 33. 搜索旋转排序数组 tips: 构建不等式约束关系 来 选择 边界
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

    // 39. 组合总和
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
    // todo
    // 40. 组合总和 II

    // 216. 组合总和 III

    // 377. 组合总和 Ⅳ


    //②  62. 不同路径 dfs/dp解法
    // dp[i][j] = dp[i-1][j]+dp[i][j-1], i∈[0,m),j∈[0,n);
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    public int uniquePaths01(int m, int n) {
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

    // 错误 算组合去了
    public List<List<Integer>> combine01(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        Map<Integer, Boolean> dict = new HashMap();
        for (int i = 1; i <= n; i++) {
            dict.put(i, false);
        }
        dfsCombine01(ans, new LinkedList(), 0, n, k, dict);
        return ans;
    }

    public void dfsCombine01(List<List<Integer>> ans, LinkedList<Integer> sub, int level, int n, int k, Map<Integer, Boolean> dict) {
        if (level == k) {
            ans.add(new ArrayList(sub));
            return;
        }

        for (int i = 1; i <= n; i++) {
            if (dict.get(i)) continue;
            dict.put(i, true);
            sub.add(i);
            dfsCombine01(ans, sub, level + 1, n, k, dict);
            dict.put(i, false);
            sub.pollLast();
        }
    }


    //②  46. 全排列 dfs +   visited boolean数组
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

    // [tag:微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
    // 128. 最长连续序列 map set solve  哈希表/并查集/DP
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

    // ② tips: 存入set,找到连续递增序列的第一个数  nums-1
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

    // ② 96. 不同的二叉搜索树  DP题目  可以采用卡塔兰数,不过目前看起来暂时好难理解
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;
        for (int level = 2; level <= n; level++) {
            for (int root = 1; root <= level; root++) {
                dp[level] += dp[root - 1] * dp[level - root];
            }
        }
        return dp[n];
    }

    // ② 98. 验证二叉搜索树
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

    //② 101. 对称二叉树递归左右对称即可。 迭代做法 两个队列放入元素顺序需要做到对称
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

    // 114. 二叉树展开为链表 http://bit.ly/2LtZDhc  看懂图解哦
    public void flatten(TreeNode root) {
        if (root == null) return;
        flatten(root.left);
        flatten(root.right);
        TreeNode tmp = root.right;
        root.right = root.left;
        root.left = null;
        //  key tips: 更新root.right节点
        while (root.right != null) {
            root = root.right;
        }
        root.right = tmp;
    }

    // ② 226. 翻转二叉树
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

    // 538. 把二叉搜索树转换为累加树  BST 中序遍历 满足顺序关系  先访问右子树-->root-->左子树 降序排列
    public int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        convert(root);
        return root;
    }

    public void convert(TreeNode cur) {
        if (cur == null) return;
        convertBST(cur.right);
        sum = sum + cur.val;
        cur.val = sum;
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


    //② 543. 二叉树的直径
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
    // 106 105 108  617 构建二叉树的本质在于招到根节点,然后构建根节点的左右子树

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
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = bstHelper(nums, left, mid - 1);
        root.right = bstHelper(nums, mid + 1, right);
        return root;
    }

    // ② 105. 从前序与中序遍历序列构造二叉树
    // 所以构建二叉树的问题本质上就是：
    // tips:
    //找到各个子树的根节点 root
    //构建该根节点的左子树
    //构建该根节点的右子树
    public TreeNode buildTreePreIn(int[] preorder, int[] inorder) {
        return buildTrePreIn(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    public TreeNode buildTrePreIn(int[] preOrder, int pLeft, int pRight, int[] inorder, int iLeft, int iRight) {
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
        root.left = buildTrePreIn(preOrder, pLeft + 1, pLeft + (i - iLeft), inorder, iLeft, i - 1);
        root.right = buildTrePreIn(preOrder, pLeft + i - iLeft + 1, pRight, inorder, i + 1, iRight);
        return root;
    }


    // ② 106. 从中序与后序遍历序列构造二叉树
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
        root.right = buildTreeInPost(inorder, i + 1, iRight, postOrder, pRight - (iRight - i), pRight - 1);
        return root;
    }


    //889. 根据前序和后序遍历构造二叉树(结果不唯一)


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


    // ② 647 字符串回文个数 dp[i,j]  = dp[i+1][j-1] if s[i]=s[j]
    public int countSubstrings1(String s) {
        int res = 0;
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = i; j >= 0; j--) {

                dp[i][j] = s.charAt(i) == s.charAt(j) && (i - j <= 2 || dp[i + 1][j - 1]);
                if (dp[i][j]) {
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


    // ② 160. 相交链表
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

    //② 461. 汉明距离
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

    // ② 200. 岛屿数量
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
                res++;//key
            }
        }
        return res;
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

    // ② 44. 通配符匹配  思路和leetcode 10 差不多
    // 解法2 dp来做  解题思路参考这个做法 http://bit.ly/2OPPKf0
    // dp[i][j]=dp[i-1][j-1] s[i-1]=p[j-1] || p[j-1]=?   i∈[0,M]  j∈[0,N];
    // dp[i][j]=dp[i][j-1] || dp[i-1][j]  p[j-1]="*";
    public boolean isMatch1(String s, String p) {
        int M = s.length(), N = p.length();
        boolean[][] dp = new boolean[M + 1][N + 1];
        dp[0][0] = true;
        // edge case
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


    // 参考这篇文章 匹配优先向下原则(说的不是通配哦!)
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


    // ② leetcode 10 正则表达式匹配 http://bit.ly/2SsG9dA
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


    // ② 394. 字符串解码 用栈存储结果
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
    //     dp[i][j] 表示从 word1 的前i个字符转换到 word2 的前j个字符所需要的步骤
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
                // 存储下一层可能的候选
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


    // http://bit.ly/2LvcJLu
    // ② 438. 找到字符串中所有字母异位词
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

    // 621. 任务调度器 好难  http://bit.ly/2LxLShE [经典] https://www.youtube.com/watch?v=YCD_iYxyXoo
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

    // ② 252. 会议室 排个序,比较相邻的位置大小即可.
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1])
                return false;
        }
        return true;
    }

    // 253. 会议室 II
    //② interview friendly 参考这个视频 https://youtu.be/wB884_Os58U  主要是人脑怎么处理的问题
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
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    // 这个各种条件处理起来比较复杂
    public int longestValidParentheses02(String s) {
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


    // 7. 整数反转  ② 注意下边界条件即可
    public int reverse(int x) {
        int res = 0;
        while (x != 0) {
            if (Math.abs(res) > Integer.MAX_VALUE / 10) return 0;
            res = res * 10 + x % 10;
            x = x / 10;
        }
        return res;
    }

    // ② 88. 合并两个有序数组
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

    // ② 237. 删除链表中的节点
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        ListNode tmp = node.next;
        node.next = node.next.next;
        tmp.next = null;
    }

    // ② 326. 3的幂
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

    //② 202 快乐数
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

    // 29. 两数相除 这个放弃 太难了
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
        while (count > 0) {

            count--;
            divisor >>= 1;
            int temp = dividend;
            if (divisor <= dividend) {
                result = result + 1 << count;
                dividend = dividend - divisor;
            }
        }
        if (sign) result = -result;
        return result;
    }

    // ② 204 质数计数
    // 质数是指在大于1的自然数中，除了1和它本身以外不再有其他因数的自然数
    // 2是最小的质数,质数乘以任何数的积 就不是质数
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

    // ② 14 最长公共前缀
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


    // ② 69 x 平方根 利用牛顿求根法来做[http://bit.ly/2ypO02m] 牛顿求根法视频讲解 https://youtu.be/VUpQwEVsyFk
    // f(x1)-f(x2) / x1-x2 = f'(x1) 令 f(x2)=0 即可求出
    public int mySqrt(int x) {
        if (x <= 1) return x;
        double last = 0;
        double res = 1;
        while (Math.abs(res - last) > 1e-9) {
            last = res;
            res = (res + x / res) / 2;
            // res = (res*res+x)/(2*res)
        }
        return (int) res;
    }

    //② 解乏2 二分法求
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

        /*
        *
        *  if(x<=0) return 0;
        int res =1;
        int   l = 1;
        int   r = x;
        while (l<r){
            int mid = l+(r-l)/2;
            if (mid>x/mid){ // 避免溢出
                r=mid;
            }else {
                res = mid;
                l = mid+1;

            }
        }
        return  res;
        *
        * */
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
    public int strStr_bad(String haystack, String needle) {
        int i = 0;
        int j = 0;
        while (i < haystack.length() && j < needle.length()) {
            if (haystack.charAt(i++) != needle.charAt(j++)) {
                j = 0;
            }
        }
        return j == needle.length() ? i - j : -1;
    }


    // ② 172. 阶乘后的零  tips:连续5的前缀后数列
    public int trailingZeroes2(int n) {
        int count = 0;
        while (n != 0) {
            count += n / 5; // 算出 5倍数的个数了
            n = n / 5;
        }
        return count;
    }

    // ② 26. 删除排序数组中的重复项
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


    // ② leetcode 137 137. 只出现一次的数字 II
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

    // 解法2 有点玄学了 但是复杂度O(N²)
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

    // ② 189. 旋转数组
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

    // ✅ 买卖股票系列问题 参考下面这篇 Blog 属实牛逼 http://bit.ly/333JDIm
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


    // ✅122. 买卖股票的最佳时机 II   逢涨必抛  贪心算法
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


    // ✅ 123. 买卖股票的最佳时机 III
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

    //✅ 188. 买卖股票的最佳时机 IV
    public int maxProfit4A(int k, int[] prices) {
        int n = prices.length;
        if (k > n / 2) { // 一天内
            return maxProfit2B(prices);
        }
        return maxProfit3B(prices, k);
    }


    //✅ 309. 最佳买卖股票时机含冷冻期
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


    //✅ 714. 买卖股票的最佳时机含手续费
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

    // ② [tag:微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
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

    // ② 50 pow(x,n) 注意这道题目 是能用基本算术运算的!!
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        double half = myPow(x, n / 2);
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

    //② 328 奇偶链表
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

    // 计算器系列
    //② 224. 基本计算器  没有优先级了 我真滴服了 审题要仔细哦
    public int calculate224(String str) {
        int res = 0, sign = 1, num = 0, n = str.length();
        Stack<Integer> s = new Stack();
        for (int i = 0; i < n; i++) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                num = num * 10 + c - '0';
            }
            if (c == '+' || c == '-') {
                res += sign * num;
                num = 0;
                sign = (c == '+') ? 1 : -1;
            }
            if (c == '(') {
                s.push(res); // 左括号外的res 压入
                s.push(sign); // 左括号外的+还是-sign 压入
                res = 0;
                sign = 1;
            }
            if (c == ')') {
                res += sign * num;
                num = 0;
                res *= s.pop();// sign
                res += s.pop(); // 左括号外的res
            }
        }
        res += sign * num;
        return res;
    }

    // ② 227. 基本计算器 II
    public int calculate227(String s) {
        // 用栈的思想来做
        Stack<Integer> stack = new Stack<>();
        char op = '+';
        int n = s.length();
        int res = 0;
        int num = 0;
        Set<Character> ops = new HashSet<>();
        ops.add('-');
        ops.add('+');
        ops.add('*');
        ops.add('/');
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                num = num * 10 + s.charAt(i) - '0';
            }

            if (ops.contains(s.charAt(i)) || i == n - 1) {
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
    // folllow up 59 螺旋矩阵2

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


    // ② 76 最小覆盖子串 http://bit.ly/2LvcJLu  双指针 滑动窗口方法  ✅  经典方法
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

    //②  162 寻找峰值
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

    // 91 解码方法 ✅ DP  斐波那些数列翻版  dp[i] 表示 前I个表示方法;
    // dp解决  状态转移方程式: if s[i-1,i)!=0, dp[i]+=dp[i-1],if 10<=s[i-2:i)<=26 dp[i] +=dp[i-2]
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


    // ②  用@字符暂时替代下 130 被围绕的区域  http://bit.ly/2L0HsND
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

    // ② 131 分割回文串
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
            sub.clear();
            return;
        }
        for (int i = pos; i < s.length(); i++) {
            if (isPal(s, pos, i)) {
                sub.add(s.substring(pos, i + 1));
                dfs(s, i + 1, sub, res);
                // sub.remove(sub.size() - 1); // TODO  移除之后,
            }

        }
    }

    public boolean isPal(String s, int left, int right) {
        while (left <= right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }

    // todo 127 单词接龙  BFS  邻接
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


    // ② 134 加油站⛽
    public int canCompleteCircuit(int[] gas, int[] cost) {
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


    //② 150 逆波兰表达式求值
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
        int res = 0;
        for (char c : s.toCharArray()) {
            res = res * 10 + c - '0';
        }
        return res * sign;
    }

    public boolean isNumber(char c) {
        return c >= '0' && c <= '9';
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

    //② 334 递增的三元子序列 注意关键字 是 3哦  想想为什么呢
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

    // dp 解决
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

    public void show2DArray(int[][] board) {
        System.out.println("==============");
        for (int i = 0; i < board.length; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
        System.out.println("==============");
    }

    // 315 计算右侧小于当前元素的个数  O(N²)  不符合 [2,0,1]
    /*public List<Integer> countSmaller0(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (nums[i] > nums[j]) {
                    // 错误的 状态转移方程不成立
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return Arrays.stream(dp).boxed().collect(Collectors.toList());
    }*/

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
    // ②  二分查找小于target的个数 即是 index  ✅interview friendly
    public int findIndex(List<Integer> sorted, int target) {
        int i = 0;
        int j = sorted.size();
        // this is the right way to binary search ,idea from c++ lower_bound()  method
        // range from i (inclusive) to j (exclusive)  ----->   [i,j)
        while (i < j) {
            int mid = i + (j - i) / 2;
            // sorted.get(mid) <= target is wrong , when duplicate nums exist
            if (sorted.get(mid) < target) {
                // i is assigned to mid+1 ,prevent  infinite cycle and out of range  error
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
    // ② 240 搜索二维矩阵 Ⅱ
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

    // ② 上买呢的2个方法都是异曲同工, 都是同样的错误思路!
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

    // 324 摆动排序Ⅱ
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

    //② 解法1 快排最佳实践 http://bit.ly/353KVnO  http://bit.ly/354yckZ  三项快速排序 需要构造newIdx, 需要一次中值切分
    public void wiggleSort01(int[] nums) {
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

    // ② 454  四数相加Ⅱ
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
        int n = matrix.length, lo = matrix[0][0], hi = matrix[n - 1][n - 1] + 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = lessEqual(matrix, mid);
            if (count < k) lo = mid + 1;
                // 为什么是mid-1呢 而不是mid
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

    //② 解法2  中序遍历  也是B+数range查询的基本原理了
    public int count230;
    public int res230 = 0;

    public int kthSmallest1(TreeNode root, int k) {
        inorder(root, k);
        return res230;
    }

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

    // 解法3 不需要额外空间的做法 参考解法4 http://bit.ly/2ZMzCws 类似于leetcode 904
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
    // ② 340 至多包含k个不同字符的最长子串
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        Map<Character, Integer> map = new HashMap();
        int left = 0;
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char key = s.charAt(i);
            map.put(key, map.getOrDefault(key, 0) + 1);
            while (map.size() > k) {
                char leftKey = s.charAt(left);
                map.put(leftKey, map.get(leftKey) - 1);
                if (map.get(leftKey) == 0) {
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

    // ② 395 至少有k个重复的字符的最长子串
    public int longestSubstring(String s, int k) {
        int res = 0, i = 0, N = s.length();
        while (i + k - 1 < N) {
            int[] m = new int[26];
            int mask = 0;
            int maxIdx = i;
            for (int j = i; j < N; j++) {
                int idx = s.charAt(j) - 'a';
                m[idx]++;
                if (m[idx] < k) {
                    mask = mask | (1 << idx);
                } else {
                    mask = mask & (~(1 << idx));
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

    // ② 解法2 递归迭代解法
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

    //②  解法3 DP 解法interview friendly
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

    // ② 440 字典序第K小的数字 http://bit.ly/2nKscwE https://youtu.be/yMnR63e3KLo
    public int findKthNumber(int n, int k) {
        int curr = 1;
        k = k - 1;
        while (k > 0) { // if n=1;k=1;
            int gap = findGap(n, curr, curr + 1);
            if (gap <= k) {// 在隔壁子树节点下
                curr = curr + 1;
                k = k - gap;
            } else {// 在当前节点子树下
                curr = curr * 10;
                k = k - 1;
            }
        }
        return curr;
    }

    public int findGap(int n, long cur, long neighbour) {  // [cur,neighbour)或者说(cur,Neighbour] 之间的距离
        int gap = 0;
        while (cur <= n) {
            gap += Math.min(n + 1, neighbour) - cur;
            cur = cur * 10;
            neighbour = neighbour * 10;
        }
        return gap;
    }

    //use long in case of overflow
    public int calSteps(int n, long n1, long n2) { //计算curr开头和curr+1开头之间的字符串数量
        int steps = 0;
        while (n1 <= n) {
            steps += Math.min(n + 1, n2) - n1;  //每次加上当前的字符串数量
            n1 *= 10;       //每次均扩大10倍
            n2 *= 10;
        }
        return steps;
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

    // leetcode 516  [tag:微软面筋]  https://www.1point3acres.com/bbs/thread-541121-1-1.html
    // [tag:微软面筋] 求M的N次方的后3位
    public int getLastThreeNum(int m, int n) {
        int res = 1;
        for (int i = 0; i < n; i++) {
            res = (res * (m % 1000)) % 1000;
        }
        return res;
    }

    //② 103 二叉树的锯齿形层遍历
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

    // ② 594 最长和谐子序列
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

    // ② 1027. 最长等差数列
    //  dp[i][step]=dp[j][step]+1;  j < i
    public int longestArithSeqLength(int[] A) {
        int res = 2, n = A.length;
        Map<Integer, Integer>[] dp = new HashMap[n];
        for (int j = 0; j < A.length; j++) {
            dp[j] = new HashMap<>();
            for (int i = 0; i < j; i++) {
                int d = A[j] - A[i];
                dp[j].put(d, dp[i].getOrDefault(d, 1) + 1);
                res = Math.max(res, dp[j].get(d));
            }
        }
        return res;
    }


    // 403 青蛙过河

    //  464 我能赢吗

    // 1092. 最短公共超序列

    // 718. 最长重复子数组


    // 873

    // 673. 最长递增子序列的个数

    // 915. 分割数组
    //

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

    //654 RMQ

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


    // kmp字符串匹配的实现
    // leetcode 260. 只出现一次的数字 III

    // leetcode 178

    // 80

    // chapter 8.2 P442
    // 这个问题 网上也已经有很多人分析过如何写出正确的二分查找了
    //  8.2章节  也给出标准示范了 , 以及几个细节的分析
    // 二分查找的标准函数 http://bit.ly/32512ix
    public int binarySearch(int[] array, int lo, int hi, int target) {
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (array[mid] <= target) { // 返回满足 arr[i] > value的第一个位置
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

}







