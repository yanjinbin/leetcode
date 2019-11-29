package com.yanjinbin.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class TreeSP {


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
        // 后续遍历可以首先想到的就是 左右子树均为null--->向上搜寻
        // 出现 左右子树一个为空的时候 你需要 标识上次的递归 必须是左右子树为null
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


    // 199. 二叉树的右视图
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList();
        if (root == null) return ans;
        LinkedList<TreeNode> queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            TreeNode n = queue.get(len - 1);
            ans.add(n.val);
            for (int i = 0; i < len; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.addLast(node.left);
                if (node.right != null) queue.addLast(node.right);
            }
        }
        return ans;
    }

    // 333. 最大 BST 子树
    public int ans = 0;

    public int largestBSTSubTree(TreeNode root) {
        dfs(root);
        return ans;
    }

    public void dfs(TreeNode root) {
        if (root == null) return;
        int res = countBfs(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (res != -1) {
            ans = Math.max(ans, res);
            return;
        }
        dfs(root.left);
        dfs(root.right);
    }

    public int countBfs(TreeNode root, int min, int max) {
        if (root == null) return 0;
        if (root.val <= min) return -1;
        int left = countBfs(root.left, min, root.val);
        if (left == -1) return -1;
        if (root.val >= max) return -1;
        int right = countBfs(root.right, root.val, max);
        if (right == -1) return -1;
        return left + right + 1;
    }

    // 426. 将二叉搜索树转化为排序的双向链表
    // 构建一个引子节点，指向head，遍历完, prev is tail ,然后首尾相连即可
    public TreeNode treeToDoublyList(TreeNode root) {
        if (root == null) return null;
        // pre 上一次迭代的节点，
        TreeNode back = new TreeNode(-1), pre = back;
        Stack<TreeNode> s = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !s.isEmpty()) {
            if (cur != null) {
                s.push(cur);
                cur = cur.left;
            } else {
                cur = s.pop();
                pre.right = cur;
                cur.left = pre;
                pre = cur;
                cur = cur.right;
            }
        }
        TreeNode head = back.right;
        TreeNode tail = pre;
        head.left = tail;
        tail.right = head;
        return head;
    }

    // 663. 均匀树划分
    public boolean checkEqualTree(TreeNode root) {
        root = rebuild(root);
        return help(root);
    }

    public boolean help(TreeNode root) {
        if (root == null || root.val % 2 == 1) return false;
        if (root.left != null && root.val / 2 == root.left.val) {
            return true;
        }
        if (root.right != null && root.val / 2 == root.right.val) {
            return true;
        } else return help(root.left) || help(root.right);
    }

    public int sum(TreeNode root) {
        if (root == null) return 0;
        int left = sum(root.left);
        int right = sum(root.right);
        return left + right + root.val;
    }

    public TreeNode rebuild(TreeNode root) {
        if (root == null) return new TreeNode(0);
        TreeNode left = rebuild(root.left);
        TreeNode right = rebuild(root.right);
        root.val += left.val + right.val;
        return root;
    }

    // 863. 二叉树中所有距离为 K 的结点
    // 图的遍历
    Map<TreeNode, List<TreeNode>> map = new HashMap();

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> res = new ArrayList<>();
        if (root == null || K < 0) return res;
        buildEdges(root, null);
        if (!map.containsKey(target)) return res;

        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(target);
        visited.add(target);
        while (!q.isEmpty()) {
            int size = q.size();
            if (K == 0) {
                for (int i = 0; i < size; i++) res.add(q.poll().val);
                return res;
            }
            for (int i = 0; i < size; i++) {// BFS遍历
                TreeNode elem = q.poll();
                for (TreeNode next : map.get(elem)) {
                    if (visited.contains(next)) continue;
                    visited.add(next);
                    q.add(next);
                }
            }
            K--;
        }
        return res;
    }

    public void buildEdges(TreeNode c, TreeNode p) {
        if (c == null) return;
        if (!map.containsKey(c)) {
            map.put(c, new ArrayList<>());
            if (p != null) {
                map.get(c).add(p);
                map.get(p).add(c);
            }
            buildEdges(c.left, c);
            buildEdges(c.right, c);
        }
    }

    // 889. 根据前序和后序遍历构造二叉树

    // 606. 根据二叉树创建字符串

    // 988. 从叶结点开始的最小字符串

    // 662. 二叉树最大宽度  // 思路: 给节点编号  i 2i,2i+1;  起点
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        Deque<TreeNode> q = new LinkedList<>();
        root.val = 1; // 0 ,1 在这个题目条件下均可
        q.add(root);
        int max = 1;
        while (!q.isEmpty()) {
            int N = q.size();
            max = Math.max(max, q.peekLast().val - q.peekFirst().val + 1);
            for (int i = 0; i < N; i++) {
                root = q.poll();
                if (root.left != null) {
                    root.left.val = 2 * root.val;
                    q.add(root.left);
                }
                if (root.right != null) {
                    root.right.val = 2 * root.val + 1;
                    q.add(root.right);
                }
            }
        }
        return max;
    }


    // 654. 最大二叉树
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        // 分治法
        return null;
    }

    // 637. 二叉树的层平均值


    // 110. 平衡二叉树
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int l = dfsHeight(root.left);
        int r = dfsHeight(root.right);
        if (Math.abs(l - r) <= 1) return isBalanced(root.left) && isBalanced(root.right);
        else return false;
    }

    public int dfsHeight(TreeNode root) {
        if (root == null) return 0;
        return Math.max(dfsHeight(root.left), dfsHeight(root.right)) + 1;
    }


    // 104. 二叉树的最大深度
    // 解法1
    public int maxDepth0(TreeNode root) {
        return dfsDepthHelper(root, 0);
    }

    public int dfsDepthHelper(TreeNode root, int level) {
        if (root == null) return level;
        return Math.max(dfsDepthHelper(root.left, level + 1), dfsDepthHelper(root.right, level + 1));
    }

    // 111. 二叉树的最小深度 出题定义的最小深度是有问题的!  [1] 深度居然是1 明显就是0啊
    public int minDepth(TreeNode root) {
        // http://bit.ly/2ov18lf
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        int l = minDepth(root.left);
        int r = minDepth(root.right);
        if (root.left != null && root.right != null) return Math.min(l, r) + 1;
        else return l + r + 1;
    }

    // 559 N叉树的最大深度
    public int maxDepth(Node root) {
        if (root == null) return 0;
        int m = 0;
        for (Node node : root.children) {
            m = Math.max(m, maxDepth(node));
        }
        return m + 1;
    }


    // 解法2 这是求二叉树的最大高度了  虽然最大深度和高度是一样的!!!
  /*  public int maxDepth1(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth1(root.left), maxDepth1(root.right));
    }*/


    // 864. 获取所有钥匙的最短路径

    // 847. 访问所有节点的最短路径

    // 797. 所有可能的路径

    // 1091. 二进制矩阵中的最短路径

    //  112. 路径总和
    public boolean hasPathSum(TreeNode root, int sum) {
        // 注意审题,是叶子节点。
        /*if (root == null && sum == 0) return true;
        if (root == null && sum !=0) return false;
        boolean left = hasPathSum(root.left, sum - root.val);
        boolean right = hasPathSum(root.right, sum - root.val);
        return left || right;*/
        if (root == null) return false;
        sum = sum - root.val;
        if (root.left == null && root.right == null) {
            return sum == 0;
        }
        return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);

    }

    // 113. 路径总和 II
    public List<List<Integer>> pathSum2(TreeNode root, int sum) {
        List<List<Integer>> ans = new ArrayList<>();
        dfsHelp(root, sum, ans, new LinkedList<>());
        return ans;
    }

    public void dfsHelp(TreeNode root, int sum, List<List<Integer>> ans, LinkedList<Integer> sub) {
        if (root == null) return;
        sub.add(root.val);
        sum = sum - root.val;
        if (root.left == null && root.right == null && sum == 0) {
            ans.add(new ArrayList<>(sub));
            // return; 不然 return提早返回,sub多余的元素没去除
            // 或者 在return前 pollLast()
           /* sub.pollLast();
            return;*/

        }
        dfsHelp(root.left, sum, ans, sub);
        dfsHelp(root.right, sum, ans, sub);
        sub.pollLast();
    }

    // 437. 路径总和 III  思路  http://bit.ly/2LukqSa  易懂 不好想出来
    public int pathSum(TreeNode root, int sum) {
        if (root == null) return 0;
        return pathHelper(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
    }

    public int pathHelper(TreeNode root, int sum) {
        if (root == null) return 0;
        sum = sum - root.val;
        return (sum == 0 ? 1 : 0) + pathHelper(root.left, sum) + pathHelper(root.right, sum);
    }


    // 1026 节点与祖先最大差值  update diff = cur = max(abs(root.val-min),abs(root.val-max)) ,then   pass min,max to left and right;
    // update diff= cur = max(cur,dfs(root.left) dfs(root.right) ) again;
    public int maxAncestorDiff(TreeNode root) {
        if (root == null) return 0;
        return dfs(root, root.val, root.val);
    }

    public int dfs(TreeNode root, int min, int max) {
        if (root == null) return 0;
        int cur = Math.max(Math.abs(root.val - min), Math.abs(root.val - max));
        min = Math.min(min, root.val);
        max = Math.max(max, root.val);
        return Math.max(cur, Math.max(dfs(root.left, min, max), dfs(root.right, min, max)));
    }


    // 235/236  二叉搜索树的最近公共祖先
    // O(N)
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root.val == p.val || root.val == q.val) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        if (left == null) return right;
        else return left;
    }


    // 比较低效 O(N²)
    public TreeNode lowestCommonAncestor02(TreeNode root, TreeNode p, TreeNode q) {
        boolean b1 = connected(root.left, p) && connected(root.left, q);
        boolean b2 = connected(root.right, p) && connected(root.right, q);
        if (b1) return lowestCommonAncestor(root.left, p, q);
        else if (b2) return lowestCommonAncestor(root.right, p, q);
        else return root;
    }

    public boolean connected(TreeNode root, TreeNode p) {
        if (root == null) return false;
        if (root.val == p.val) return true;
        else return connected(root.left, p) || connected(root.right, p);
    }


    // 235  BST 特有的方法 log(N)
    public TreeNode lowestCommonAncestor01(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else {
                break;
            }
        }
        return root;
    }

    public int count(TreeNode root) {
        if (root == null) return 0;
        return 1 + count(root.left) + count(root.right);
    }

    // N叉树的前序
    // 589
    public List<Integer> preorder(Node root) {
        List<Integer> ans = new ArrayList();
        preOrderHelp(root, ans);
        return ans;
    }

    public void preOrderHelp(Node root, List<Integer> ans) {
        if (root == null) return;
        ans.add(root.val);
        for (Node node : root.children) {
            preOrderHelp(node, ans);
        }
    }

    //后序遍历
    public List<Integer> postorder(Node root) {
        List<Integer> ans = new ArrayList();
        postorder(root, ans);
        return ans;
    }

    public void postorder(Node root, List<Integer> ans) {
        if (root == null) return;
        for (Node node : root.children) {
            postorder(node, ans);
        }
        ans.add(root.val);
    }

    //层序遍历
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> ans = new ArrayList();
        Queue<Node> q = new LinkedList();
        if (root != null) q.add(root);
        while (!q.isEmpty()) {
            int len = q.size();
            List<Integer> sub = new LinkedList();
            for (int i = 0; i < len; i++) {
                Node x = q.poll();
                sub.add(x.val);
                for (Node child : x.children) {
                    q.add(child);
                }
            }
            ans.add(new LinkedList<>(sub));
        }
        return ans;
    }

    // 834. 树中距离之和
}
