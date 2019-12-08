package com.yanjinbin.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Microsoft {

    // [tag:微软 2019-2-8] https://www.1point3acres.com/bbs/thread-479082-1-1.html
    // 单调队列 http://poj.org/problem?id=2823
    // 239. 滑动窗口最大值 这道题目也是考察数据结构的熟悉程度了 大堆 优先队列
    // 也可以当做RMQ问题 ST解决 http://bit.ly/35lfJkh
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

    // [tag:微软] https://www.1point3acres.com/bbs/thread-529016-1-1.html
    // https://www.hackerrank.com/challenges/almost-sorted/problem
    public boolean almostSorted(int[] arr) {
        return false;
        // 找出 reverse 和 swap的种类，先reverse之后 再swap ，存在顺序依赖关系

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
    // #19. 删除链表的倒数第N个节点
    /*// 无法处理 corner case,
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
    }*/

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

    // [tag:微软 2019-8-7]https://www.1point3acres.com/bbs/thread-541862-1-1.html
    // 285 510  类似139 单次拆分

    // [tag:微软 2019-10-10] https://www.1point3acres.com/bbs/thread-558573-1-1.html
    // 74 124 239

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

    // 实习 https://www.1point3acres.com/bbs/thread-506842-1-1.html
    // 419

    // https://www.1point3acres.com/bbs/thread-513482-1-1.html
    // 93 468

    //[tag:微软 2018-4-26] https://www.1point3acres.com/bbs/thread-411805-1-1.html
    // 第一轮上来就做题，介绍都不用。 find k th smallest element in N sorted array
    //第二轮： find the target number in BST，return target后一个的数。 follow up: 有parent 怎么写

    // [tag:微软 ] https://www.1point3acres.com/bbs/thread-462940-1-1.html
    // LC 297 428 449  LC 83


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

    // [tag:微软]https://www.1point3acres.com/bbs/thread-460409-1-1.html
    // LC 98 230

    // [tag：微软实习] https://www.1point3acres.com/bbs/thread-506392-1-1.html
    // lc235 236

    // [tag:微软] https://www.1point3acres.com/bbs/thread-551057-1-1.html
    // http://ddrv.cn/a/84771  IP判定  给定一个有序数组，建立一颗高度最小的搜索二叉树

    // https://www.1point3acres.com/bbs/thread-479082-1-1.html
    // LC 53  239 https://www.cnblogs.com/kexianting/p/8744241.html https://www.cnblogs.com/sxmcACM/p/4803047.html
    // 数组中元素差的最大值
    // 最大子数组和 整数相除 链表倒数第K个节点

    // 全职 2019-7-9 https://www.1point3acres.com/bbs/thread-536123-1-1.html

    public static void main(String[] args) {
        AmicablePair(10000);
    }
    // https://leetcode.com/discuss/interview-question/398023/Microsoft-Online-Assessment-Questions

    // =================

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
            while (cur != null) {
                s.push(cur);
                cur = cur.left;
            }
            cur = s.pop();
            if (hint) return cur;
            if (cur.val == p.val) {
                hint = true;
            }
            cur = cur.right;
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


}
