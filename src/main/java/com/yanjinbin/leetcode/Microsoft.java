package com.yanjinbin.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

public class Microsoft {

    // [tag:微软 2019-2-8] https://www.1point3acres.com/bbs/thread-479082-1-1.html
    // 单调队列 http://poj.org/problem?id=2823
    // 239. 滑动窗口最大值 这道题目也是考察数据结构的熟悉程度了 大堆 优先队列
    // 也可以当做RMQ问题 ST解决 http://bit.ly/35lfJkh

    // T:O(NlgN)
    public int[] maxSlidingWindow0(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        if (nums.length == 0 || nums == null) return new int[0];
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> (o2 - o1));
        // init
        for (int i = 0; i < k; i++) {
            pq.offer(nums[i]);
        }
        res[0] = pq.peek();
        for (int i = k; i < nums.length; i++) {
            pq.remove(nums[i - k]);
            pq.add(nums[i]);
            res[i - k + 1] = pq.peek();
        }
        return res;
    }

    // 单调递减队列 用这个比较好
    public int[] maxSlidingWindow1(int[] nums, int k) {
        if (nums == null || k <= 0) return new int[0];
        int N = nums.length;
        int[] ans = new int[N - k + 1];
        Deque<Integer> q = new LinkedList();

        for (int i = 0; i < N; i++) {
            while (!q.isEmpty() && i - q.peekFirst() + 1 > k) q.pollFirst();
            while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) {
                q.pollLast();
            }
            q.addLast(i);
            if (i >= k - 1) {
                ans[i + 1 - k] = nums[q.peekFirst()];
            }
        }
        return ans;
    }

    // [tag:微软 2019-3-5] https://www.1point3acres.com/bbs/thread-490657-1-1.html
    // https://www.geeksforgeeks.org/largest-independent-set-problem-dp-26/

    // DP题目  LISS(X) = MAX { (1 + sum of LISS for all grandchildren of X),
    //                     (sum of LISS for all children of X) }

    public int LISS(TreeNode root) {
        if (root == null) return 0;
        int size_excl = LISS(root.left) + LISS(root.right);

        int size_incl = 1;
        if (root.left != null) {
            size_incl += LISS(root.left.left) + LISS(root.left.right);
        }
        if (root.right != null) {
            size_incl += LISS(root.right.left) + LISS(root.right.left);
        }
        return Math.max(size_incl, size_excl);
    }

    //


    public static void AmicablePair(int max) {
        int N = max + 1;
        int[] sum = new int[N];
        Arrays.fill(sum, 1);

        // 求解 约数之和
        for (int i = 2; i < N / 2; i++) {
            int j = i + i;
            while (j < N) {
                sum[j] += i;
                j = j + i;
            }
        }
        //  System.out.println(Arrays.toString(sum));
        // 遍历sum，找出亲和数
        for (int i = 220; i < N; i++) {
            if (sum[i] > i && sum[i] < N && i == sum[sum[i]]) {
                System.out.println(i + " " + sum[i]);
            }
        }
    }

    // [tag:微软 2019-2-8] https://www.1point3acres.com/bbs/thread-479082-1-1.html
    //② #19. 删除链表的倒数第N个节点, 完美处理corner case
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

    // ② 上面的2个方法都是异曲同工, 都是同样的错误思路!
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0 || matrix == null) return false;
        int m = matrix.length, n = matrix[0].length;
        if (matrix[0][0] > target || target > matrix[m - 1][n - 1]) return false;
        int x = matrix.length - 1, y = 0;
        while (x >= 0 && y <= n - 1) {
            if (matrix[x][y] > target) x--;
            else if (matrix[x][y] < target) y++;
            else return true;
        }
        return false;
    }


    // ① 124. 二叉树中的最大路径和 https://www.bilibili.com/video/av39111141
    private int ans124 = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        if (root == null) return 0;
        dfsPathSum(root);
        return ans124;
    }

    public int dfsPathSum(TreeNode root) {
        if (root == null) return 0;
        int l = Math.max(dfsPathSum(root.left), 0);
        int r = Math.max(dfsPathSum(root.right), 0);
        int sum = l + r + root.val;
        ans124 = Math.max(ans124, sum);
        return Math.max(l, r) + root.val;
    }

    // 83
    public ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        while (cur != null && cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }

    // 实现字符串分隔
    // https://www.geeksforgeeks.org/split-string-substrings-using-delimiter/
    public List<String> split(String s, char dl) {
        List<String> ans = new ArrayList<>();
        String word = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != dl) {
                word = word + s.charAt(i);
            } else {
                if (!word.isEmpty()) ans.add(word);
                // reset
                word = "";
            }
        }
        return ans;
    }

    // 285 解法1 二叉搜索树中的中序后继
    public TreeNode inorderSuccessor01(TreeNode root, TreeNode p) {
        Stack<TreeNode> s = new Stack();
        boolean hint = false;
        TreeNode cur = root;
        while (cur != null || !s.isEmpty()) {
            if (cur != null) {
                s.push(cur);
                cur = cur.left;
            } else {
                cur = s.pop();
                if (hint) return cur;
                if (cur.val == p.val) {
                    hint = true;
                }
                cur = cur.right;
            }
        }
        return null;
    }

    // 递归 有点难理解
    // [5,3,6,2,4,null,null,1]
    // 1

    // [2,1,3]
    // 1;

    // 285 解法2
    private TreeNode suc = null, pre = null;

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) return null;
        dfs(root, p);
        return suc;
    }

    public void dfs(TreeNode root, TreeNode p) {
        if (root == null) return;
        dfs(root.left, p);
        if (pre == p) {
            suc = root;
        }
        pre = root;
        dfs(root.right, p);
    }

    // 利用BST性质
    public TreeNode inorderSuccessor03(TreeNode root, TreeNode p) {
        TreeNode suc = null;
        while (root != null) {
            if (root.val > p.val) {
                suc = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return suc;
    }

    // 510 二叉搜索树中的中序后继 Ⅱ
    public InorderNode inorderSuccessor(InorderNode x) {
        if (x.right != null) {
            // 找到右子树的最左节点
            x = x.right;
            while (x.left != null) {
                x = x.left;
            }
            return x;
        }
        // 往父节点找，找到第一个>cmp的节点
        int cmp = x.val;
        while (x != null && x.val <= cmp) {
            x = x.parent;
        }
        return x;
    }

    // 468 验证IP地址
    public String validIPAddress(String IP) {
        String[] v4 = IP.split("\\.", -1);
        String[] v6 = IP.split("\\:", -1);

        if (IP.chars().filter(c -> c == '.').count() == 3) {
            for (String s : v4) {
                if (isIPv4(s)) continue;
                else return "Neither";
            }
            return "IPv4";
        }

        if (IP.chars().filter(c -> c == ':').count() == 7) {
            for (String s : v6) {
                if (isIPv6(s)) continue;
                else return "Neither";
            }
            return "IPv6";
        }
        return "Neither";
    }

    public static boolean isIPv4(String s) {
        try {
            //  s.indexOf('0') != 0 这个 比如  12.1.-12  失效了
            return String.valueOf(Integer.valueOf(s)).equals(s) && Integer.parseInt(s) >= 0 && Integer.parseInt(s) <= 255;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isIPv6(String s) {
        if (s.length() > 4) return false;
        try {
            return Integer.parseInt(s, 16) >= 0 && s.charAt(0) != '-';
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 实习 https://www.1point3acres.com/bbs/thread-506842-1-1.html
    // 419 甲板上的战舰
    public int countBattleships01(char[][] board) {
        int count = 0;
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!visited[i][j]) {
                    dfs(board, visited, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    public void dfs(char[][] board, boolean[][] visited, int i, int j) {
        if (i < 0 || i > board.length || j < 0 || j > board[i].length || visited[i][j]) {
            return;
        }
        visited[i][j] = true;
        if (board[i][j] == board[i + 1][j]) dfs(board, visited, i + 1, j);
        if (board[i][j] == board[i][j + 1]) dfs(board, visited, i, j + 1);
    }

    // 解法2 不需要额外空间
    public int countBattleships02(char[][] board) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'X') {// dfs遍历的时候 根据战舰的规则，左or上是X,就不计数。
                    if (i > 0 && board[i - 1][j] == 'X' || j > 0 && board[i][j - 1] == 'X') {
                        continue;
                    } else {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // https://youtu.be/00r9qf7lgAk
    // 450 删除BST种的一个节点，用递归比迭代更好处理多个case
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return root;
        }
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else {
            // found key = root.val
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            root.val = findMin(root.right);
            root.right = deleteNode(root.right, root.val);
        }
        return root;
    }

    public int findMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root.val;
    }

    // 116 填充每个节点的下一个右侧节点指针
    // 解法1 递归调用
    public TreeNode connect(TreeNode root) {
        if (root == null || root.left == null) return root;
        root.left.next = root.right;
        if (root.next != null) {
            root.right.next = root.next.left;
        }
        connect(root.left);
        connect(root.right);
        return root;
    }


    // 117  填充每个节点的下一个右侧节点指针 II
    public Node connect02(Node root) {
        if (root == null) return root;

        //水平遍历  找到 left or right要指向的next节点
        Node p = root.next;
        while (p != null) {
            if (p.left != null) {
                p = p.left;
                break;
            }
            if (p.right != null) {
                p = p.right;
                break;
            }
            p = p.next;
        }
        if (root.left != null) root.left.next = root.right == null ? p : root.right;
        if (root.right != null) root.right.next = p;
        connect02(root.left);
        connect02(root.right);
        return root;
    }

    // 116 117 通用解法  Queue
    public Node connectByQueue(Node root) {
        Queue<Node> q = new LinkedList<>();
        if (root != null) q.add(root);
        while (!q.isEmpty()) {
            int len = q.size();
            for (int i = 0; i < len; i++) {
                Node n = q.poll();
                // 最后一个节点，不需要Next
                if (i < len - 1) n.next = q.peek();
                if (n.left != null) q.add(n.left);
                if (n.right != null) q.add(n.right);
            }
        }
        return root;
    }

    // 116 117 通用解法  用dummyNode
    public Node connect(Node root) {
        // dummyNode 表示 root节点的下一次遍历的root的牵引，暨dummyNode.next!!!
        Node dummyNode = new Node(), cur = dummyNode, head = root;
        while (root != null) {
            if (root.left != null) {
                cur.next = root.left;
                cur = cur.next;
            }
            if (root.right != null) {
                cur.next = root.right;
                cur = cur.next;
            }

            root = root.next;

            if (root == null) {
                //
                cur = dummyNode;
                root = dummyNode.next;
                dummyNode.next = null;
            }
        }
        return head;
    }


    // 410 分割数组的最大值  花花酱 https://youtu.be/_k-Jb4b7b_0
    // dp[i][j]表示 前j个数字切分为i个子数组和最大值的 最小值。
    // dp[i][j]=min(dp[i][j],Max(dp[i-1][k],sum[j]-sum[k])),
    //  init: dp 为Integer.MAX_VALUE ,  dp[1][j]=preSum[j]
    public int splitArray(int[] nums, int m) {
        int N = nums.length;
        int[] preSum = new int[N];
        // 求前缀和
        preSum[0] = nums[0];
        for (int i = 1; i < N; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        // DP init
        int[][] dp = new int[m + 1][N];
        // 题目是要各子数组和最大值  最小！！所以初始化的时候 init MAX_VALUE.
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for (int j = 0; j < N; j++) {
            dp[1][j] = preSum[j];
        }

        for (int i = 2; i <= m; i++) {
            for (int j = i - 1; j < N; j++) {// j = i-1
                for (int k = 0; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[i - 1][k], preSum[j] - preSum[k]));
                }
            }
        }
        return dp[m][N - 1];
    }

    // 解法2 二分搜索 思路参见花花酱的  就是讲nums均等分m份
    public int splitArray02(int[] nums, int m) {
        long sum = 0;
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
            sum += num;
        }
        return (int) binary(nums, m, sum + 1, max);
    }

    private long binary(int[] nums, int m, long high, long low) {
        while (low < high) {
            long mid = (high - low) / 2 + low;
            if (valid(nums, m, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private boolean valid(int[] nums, int m, long max) {
        int cur = 0;
        int count = 1;
        for (int num : nums) {
            cur += num;
            if (cur > max) {
                cur = num;
                count++;
                if (count > m) {
                    return false;
                }
            }
        }
        return true;
    }

    // 40. 组合总和 II
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        dfs(candidates, target, 0, ans, new LinkedList<>());
        return ans;
    }

    public void dfs(int[] candidates, int target, int s, List<List<Integer>> ans, LinkedList<Integer> sub) {
        if (target == 0) {
            ans.add(new ArrayList<>(sub));
            return;
        }
        for (int i = s; i < candidates.length; i++) {
            if (target < candidates[i]) return;
            // 去除重复
            if (i > s && candidates[i] == candidates[i - 1]) continue;
            sub.addLast(candidates[i]);
            dfs(candidates, target - candidates[i], s + 1, ans, sub);
            sub.pollLast();
        }
    }

    // 127 单词接龙
    // 解法1 单广BFS
    public int ladderLength01(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> words = new HashSet<>(wordList);
        if (!words.contains(endWord)) return 0;
        Queue<String> q = new LinkedList();
        q.offer(beginWord);
        int step = 0;
        // BFS遍历
        while (!q.isEmpty()) {
            step++;
            int size = q.size();
            while (size-- > 0) {  // level 遍历
                String str = q.poll();
                char[] chars = str.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char chr = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == chr) continue;
                        chars[i] = c;
                        String s = new String(chars);
                        if (s.equals(endWord)) return step + 1;
                        if (words.contains(s)) {
                            words.remove(s);
                            q.offer(s);
                        }
                    }
                    // 复位
                    chars[i] = chr;
                }
            }
        }
        return 0;
    }

    // 解法2 双广 BFS
    public int ladderLength02(String beginWord, String endWord, List<String> wordList) {
        Set<String> words = new HashSet<>(wordList);
        if (!words.contains(endWord)) return 0;
        Set<String> s1 = new HashSet<>();
        Set<String> s2 = new HashSet<>();
        s1.add(beginWord);
        s2.add(endWord);
        int step = 0;

        while (!s1.isEmpty() && !s2.isEmpty()) {
            step++;
            // swap, for balance
            if (s1.size() > s2.size()) {
                Set<String> tmp = s1;
                s1 = s2;
                s2 = tmp;
            }

            Set<String> q = new HashSet<>();
            for (String str : s1) {
                char[] chars = str.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char chr = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[i] = c;
                        String t = new String(chars);
                        if (s2.contains(t)) return step + 1;
                        if (words.contains(t)) {
                            // 从字典表移除，并且添加到新构建的下一层次的Queue。
                            words.remove(t);
                            q.add(t);
                        }
                    }
                    chars[i] = chr;
                }
            }
            s1 = q;
        }
        return 0;
    }


    // 126 单词接龙2 todo
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> ans = new ArrayList<>();
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return ans;
        dict.remove(beginWord);
        dict.remove(endWord);

        Map<String, Integer> steps = new HashMap<>();
        steps.put(beginWord, 1);

        Map<String, List<String>> parents = new HashMap<>();
        Queue<String> q = new LinkedList<>();
        q.offer(beginWord);

        int len = beginWord.length();
        int step = 0;
        boolean found = false;
        while (!q.isEmpty() && !found) {
            step++;
            for (int j = q.size(); j > 0; j--) {
                String p = q.poll();
                char[] w = p.toCharArray();
                for (int i = 0; i < len; i++) {
                    char chr = w[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == chr) continue;
                        w[i] = c;
                        String t = new String(w);
                        if (t.equals(endWord)) {
                            List<String> l = parents.getOrDefault(t, new LinkedList<>());
                            l.add(p);
                            parents.put(t, l);
                            found = true;
                        } else if (steps.containsKey(t) && step < steps.get(t)) {
                            List<String> l = parents.getOrDefault(t, new LinkedList<>());
                            l.add(p);
                            parents.put(t, l);
                        }

                        if (dict.contains(t)) {
                            dict.remove(t);
                            q.offer(t);
                            steps.put(t, steps.getOrDefault(p, 0) + 1);
                            List<String> l = parents.getOrDefault(t, new LinkedList<>());
                            l.add(p);
                            parents.put(t, l);
                        }
                    }
                    w[i] = chr;
                }
            }
        }
        if (found) {
            LinkedList<String> l = new LinkedList<>();
            l.add(endWord);
            dfsPaths(endWord, beginWord, parents, ans, l);
        }
        return ans;

    }

    // dfs求解
    public void dfsPaths(String word, String beginWord, Map<String, List<String>> parents, List<List<String>> ans, LinkedList<String> sub) {
        if (word == beginWord) {
            Collections.reverse(sub);
            ans.add(new ArrayList<>(sub));
            return;
        }
        for (String s : parents.getOrDefault(word, new LinkedList<>())) {
            sub.addLast(s);
            dfsPaths(s, beginWord, parents, ans, sub);
            sub.pollLast();
        }
    }

    // https://www.geeksforgeeks.org/array-rotation/
    // LC 189
    void leftRotate(int arr[], int d, int n) {
        for (int i = 0; i < d; i++)
            leftRotatebyOne(arr, n);
    }

    void leftRotatebyOne(int arr[], int n) {
        int i, temp;
        temp = arr[0];
        for (i = 0; i < n - 1; i++)
            arr[i] = arr[i + 1];
        arr[i] = temp;
    }

    //  https://www.hackerrank.com/challenges/almost-sorted/problem


    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] ar = new int[n];
        for (int i = 0; i < n; i++)
            ar[i] = in.nextInt();
        Vector<Integer> vec = new Vector<Integer>();
        for (int i = 0; i < n - 1; i++)
            if (ar[i] > ar[i + 1])
                vec.add(i);
        int v_size = vec.size();
        if (v_size == 0)
            System.out.println("yes");
        else if (v_size == 1) {
            if (n == 2)
                System.out.println("yes\nswap " + (vec.get(0) + 1) + " " + (vec.get(0) + 2));
            else if (ar[vec.get(0)] < ar[vec.get(0) + 2])
                System.out.println("yes\nswap " + (vec.get(0) + 1) + " " + (vec.get(0) + 2));
            else
                System.out.println("no");
        } else if (v_size == 2)
            System.out.println("yes\nswap " + (vec.get(0) + 1) + " " + (vec.get(1) + 2));
        else if (vec.get(v_size - 1) - vec.get(0) + 1 == v_size)
            System.out.println("yes\nreverse " + (vec.get(0) + 1) + " " + (vec.get(v_size - 1) + 2));
        else
            System.out.println("no");
    }

    // [tag:微软] https://www.1point3acres.com/bbs/thread-529016-1-1.html
    // https://www.hackerrank.com/challenges/almost-sorted/problem
    public boolean almostSorted(int[] arr) {
        return false;
        // 找出 reverse 和 swap的种类，先reverse之后 再swap ，存在顺序依赖关系
    }

    // 第K小
    // 解法1 sort  S: O(M+N),  T: O(k)
    public int kthElements01(int[] arr1, int[] arr2, int k) {
        int m = arr1.length, n = arr2.length;
        if (m + n < k) return -1;
        int[] arr = new int[m + n];
        int i = 0, j = 0, idx = 0;
        while (i < m && j < n) {
            if (arr1[i] < arr2[j]) {
                arr[idx++] = arr1[i++];
            } else {
                arr[idx++] = arr2[j++];
            }
        }
        while (i < m) {
            arr[idx++] = arr1[i++];
        }
        while (j < n) {
            arr[idx++] = arr2[j++];
        }
        return arr[k - 1];
    }

    // 对解法1改进 S:O(1)  T:O(N)
    public int kthElements02(int[] arr1, int[] arr2, int k) {
        int m = arr1.length, n = arr2.length;
        int i = 0, j = 0, c = 0;// c代表第几个
        while (i < m && j < n) {
            c++;
            if (arr1[i] < arr2[j]) {
                if (c == k) {
                    return arr1[i];
                }
                i++;
            } else {
                if (c == k) {
                    return arr2[j];
                }
                j++;
            }

        }
        while (i < m) {
            c++;
            if (c == k) return arr1[i];
            i++;

        }
        while (j < n) {
            c++;
            if (c == k) return arr2[j];
            j++;

        }
        return -1;
    }

    // 解法 2 PQ  S: O(K) T:O(KlgK)
    public int kthElements03(int[] arr1, int[] arr2, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int num : arr1) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) maxHeap.poll();
        }
        for (int num : arr2) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) maxHeap.poll();
        }
        return maxHeap.peek();
    }

    // https://www.geeksforgeeks.org/k-th-element-two-sorted-arrays/
    // 解法3 分治 divide and conquer
    // 参考这个  http://bit.ly/2YUmy9F  http://bit.ly/34uQudj

    /**
     * @param start1 inclusive
     * @param end1   inclusive
     * @param start2 inclusive
     * @param end2   inclusive
     * @param k      KthSmallest
     * @return
     */
    public int FindKthSmallest(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
        if (len1 > len2) return FindKthSmallest(nums2, start2, end2, nums1, start1, end1, k);
        if (len1 == 0) return nums2[start2 + k - 1];
        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            return FindKthSmallest(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return FindKthSmallest(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }

    // ③ 301 删除最小数量的无效括号  tag:BFS
    public List<String> removeInvalidParentheses(String str) {
        List<String> ans = new ArrayList<>();
        if (str == null) return ans;
        Set<String> seen = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        seen.add(str);
        q.add(str);
        boolean found = false;
        while (!q.isEmpty()) {
            String s = q.poll();
            if (isValid(s)) {
                ans.add(s);
                found = true;
            }
            if (found) continue;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                String tmp = s.substring(0, i) + s.substring(i + 1);
                if ((c == '(' || c == ')') && !seen.contains(tmp)) {
                    seen.add(tmp);
                    q.add(tmp);
                }
            }
        }
        return ans;
    }


    // 注意 这里 有效性验证 !!
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


    // ② [tag:微软面筋] https://www.1point3acres.com/bbs/thread-541121-1-1.html
    // 8  字符串转换整数 (atoi)
    public int myAtoi(String str) {
        int sign = 1, i = 0, N = str.length(), ans = 0;
        if (str == null || str.isEmpty()) return 0;

        while (i < N && str.charAt(i) == ' ') i++;
        if (i < N && (str.charAt(i) == '+' || str.charAt(i) == '-')) {
            sign = str.charAt(i) == '+' ? 1 : -1;
            i++;
        }
        while (i < N && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            // 防止int越界问题  int   [  214748364 7 ]
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7)) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            ans = ans * 10 + str.charAt(i) - '0';
            i++;
        }
        return sign * ans;
    }

    // 1292
    public int maxSideLength01(int[][] mat, int k) {
        int M = mat.length, N = mat[0].length;
        int[][] dp = new int[M + 1][N + 1];
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1] + mat[i - 1][j - 1];
            }
        }
        int ans = 0;
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {// 优化点len = ans + 1
                for (int len = ans + 1; i + len - 1 <= M && j + len - 1 <= N; len++) {
                    if (len <= ans) continue;
                    int val = rangeSum(dp, i, j, len);
                    if (val <= k) {
                        ans = Math.max(ans, len);
                    }
                }
            }
        }
        return ans;
    }

    public int rangeSum(int[][] dp, int i, int j, int len) {
        return dp[i + len - 1][j + len - 1] - dp[i + len - 1][j - 1] - dp[i - 1][j + len - 1] + dp[i - 1][j - 1];
    }

    // T: M*N*lg(Min(M,N))
    public int maxSideLength02(int[][] mat, int k) {
        int M = mat.length, N = mat[0].length;
        int[][] dp = new int[M + 1][N + 1];
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1] + mat[i - 1][j - 1];
            }
        }

        int lo = 1, hi = Math.min(M, N) + 1, ans = 0;
        while (lo < hi) {
            int mid = (hi - lo) / 2 + lo;
            boolean OK = false;
            for (int i = 1; i + mid - 1 <= M; i++) {
                for (int j = 1; j + mid - 1 <= N; j++) {
                    if (rangeSum(dp, i, j, mid) <= k) {
                        OK = true;
                        break;
                    }
                }
            }
            if (OK) {
                ans = Math.max(ans, mid);
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return ans;
    }

    // k=6,n=10000,递归时候栈过多. T(K*N2)
    public int superEggDrop01(int k, int n) {
        int[][] memo = new int[k + 1][n + 1];
        return dp(k, n, memo);
    }

    public int dp(int k, int n, int[][] memo) {
        if (k == 1) return n;
        if (n == 0) return 0;
        if (memo[k][n] != 0) return memo[k][n];
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            res = Math.min(res, Math.max(dp(k - 1, i - 1, memo), dp(k, n - i, memo)) + 1); // broken = dp(k-1,i-1),unbroken = dp(k,n-i);
        }
        memo[k][n] = res;
        return res;
    }

    // T(K*N2) accept不了
    public int superEggDrop02(int K, int N) {
        int[][] dp = new int[K + 1][N + 1];
        // init
        for (int i = 0; i <= K; i++) Arrays.fill(dp[i], Integer.MAX_VALUE);
        for (int i = 0; i <= N; i++) {
            dp[1][i] = i; // k = 1,方案是N;
            dp[0][i] = 0;
        }
        for (int i = 0; i <= K; i++) {
            dp[i][0] = 0;
        }
        // DP
        for (int k = 2; k <= K; k++) {
            for (int n = 1; n <= N; n++) {
                for (int i = 1; i <= n; i++) {
                    dp[k][n] = Math.min(dp[k][n], Math.max(dp[k - 1][i - 1], dp[k][n - i]) + 1);
                }
            }
        }
        // ans
        return dp[K][N];
    }

    // 时间复杂度 T(KNlgN)
    public int superEggDrop03(int K, int N) {
        int[][] dp = new int[K + 1][N + 1];
        for (int i = 0; i <= N; i++) {
            dp[1][i] = i; // k = 1,方案是N;
        }
        for (int i = 0; i <= K; i++) {
            dp[i][0] = 0;
        }

        for (int k = 2; k <= K; k++) {
            for (int n = 1; n <= N; n++) {
                dp[k][n] = n; // 理论要尝试的最大次数n
                int lo = 1, hi = n + 1; // 左闭右开
                while (lo < hi) {
                    int mid = lo + (hi - lo) / 2;
                    int broken = dp[k - 1][mid - 1], unbroken = dp[k][n - mid];
                    if (broken < unbroken) {
                        lo = mid + 1;
                    } else {
                        hi = mid;
                    }
                }
                dp[k][n] = Math.min(dp[k][n], Math.max(dp[k - 1][lo - 1], dp[k][n - lo]) + 1);
            }
        }
        return dp[K][N];
    }

    // 现在，我们稍微修改dp数组的定义，确定当前的鸡蛋个数和最多允许的扔鸡蛋次数，就知道能够确定F的最高楼层数。
    //
    //有点绕口，具体来说是这个意思：
    //
    //dp[k][m] = n
    //# 当前有 k 个鸡蛋，可以尝试扔 m 次鸡蛋
    //# 这个状态下，最坏情况下最多能确切测试一栋 n 层的楼
    //
    //# 比如说 dp[1][7] = 7 表示：
    //# 现在有 1 个鸡蛋，允许你扔 7 次;
    //# 这个状态下最多给你 7 层楼，
    //# 使得你可以确定楼层 F 使得鸡蛋恰好摔不碎
    //# （一层一层线性探查嘛）
    // 基于下面两个事实：
    // 1、无论你在哪层楼扔鸡蛋，鸡蛋只可能摔碎或者没摔碎，碎了的话就测楼下，没碎的话就测楼上。
    // 2、无论你上楼还是下楼，总的楼层数 = 楼上的楼层数 + 楼下的楼层数 + 1（当前这层楼）。
    // 重写状态转移方程式 http://bit.ly/37gMGy8
    // T:O(KN)
    public int superEggDrop04(int K, int N) {
        int[][] dp = new int[K + 1][N + 1];
        int m = 0;
        //   m 最多不会超过 N 次（线性扫描）
        while (dp[K][m] < N) {
            m++;
            for (int k = 1; k <= K; k++) {
                dp[k][m] = dp[k][m - 1] + dp[k - 1][m - 1] + 1;
            }
        }
        return m;
    }


    // 1044. 最长重复子串

    // https://www.geeksforgeeks.org/k-th-element-two-sorted-arrays//

    // [tag:微软 2019-2-17] https://www.1point3acres.com/bbs/thread-482753-1-1.html
    // 74 240


    // [tag:微软 2019-8-16] https://www.1point3acres.com/bbs/thread-543569-1-1.html

    // 缺失的第一个正数  鸽巢原理
    // 字符串解码和压缩
    // 306. 累加数  大数相加

    // https://www.1point3acres.com/bbs/thread-482779-1-1.html
    // LC 117 40   |  删除 BST 节点并rebalance。| bfs 求最短路径+


    // 设计 hashMap 和twitter ，java多继承 菱形问题 GC
    // [tag:微软 2018-12-28] https://www.1point3acres.com/bbs/thread-468068-1-1.html
    // 450 79 215


    // [tag:微软 2018-4-23] https://www.1point3acres.com/bbs/thread-407652-1-1.html
    // lc 494

    //[tag:微软 2019-8-17] https://www.1point3acres.com/bbs/thread-543800-1-1.html
    // LC31 LC 126 410

    //[tag:微软  2019-4-10 实习] https://www.1point3acres.com/bbs/thread-512316-1-1.html
    // LC 53 91   BST 先序遍历 找比target小的最大的数字

    // [tag:微软 2019-4-20] https://www.1point3acres.com/bbs/thread-517289-1-1.html
    // https://www.techiedelight.com/find-pair-array-minimum-absolute-sum/
    // LC 208

    //[tag:微软 实习 2019-04-05] https://www.1point3acres.com/bbs/thread-510474-1-1.html


    // https://www.1point3acres.com/bbs/thread-513482-1-1.html
    // 93 468

    //[tag:微软 2018-4-26] https://www.1point3acres.com/bbs/thread-411805-1-1.html
    // 第一轮上来就做题，介绍都不用。 find k th smallest element in N sorted array
    //第二轮： find the target number in BST，return target后一个的数。 follow up: 有parent 怎么写

    // [tag:微软 ] https://www.1point3acres.com/bbs/thread-462940-1-1.html
    // LC 297 428 449  LC 83

    // [tag:微软]https://www.1point3acres.com/bbs/thread-460409-1-1.html
    // LC 98 285

    // [tag：微软实习] https://www.1point3acres.com/bbs/thread-506392-1-1.html
    // lc235 236

    // [tag:微软] https://www.1point3acres.com/bbs/thread-551057-1-1.html
    // http://ddrv.cn/a/84771  IP判定  给定一个有序数组，建立一颗高度最小的搜索二叉树

    // https://www.1point3acres.com/bbs/thread-479082-1-1.html
    // LC 53  239 https://www.cnblogs.com/kexianting/p/8744241.html https://www.cnblogs.com/sxmcACM/p/4803047.html
    // 数组中元素差的最大值
    // 最大子数组和 整数相除 链表倒数第K个节点

    // 全职 2019-7-9 https://www.1point3acres.com/bbs/thread-536123-1-1.html

    // https://leetcode.com/discuss/interview-question/398023/Microsoft-Online-Assessment-Questions

    // =================

    // [tag:微软 2019-8-7]https://www.1point3acres.com/bbs/thread-541862-1-1.html
    // 285 510  类似139 单次拆分

    // [tag:微软 2019-10-10] https://www.1point3acres.com/bbs/thread-558573-1-1.html
    // 74 124 239

    // Min Moves to Obtain String Without 3 Identical Consecutive Letters
}
