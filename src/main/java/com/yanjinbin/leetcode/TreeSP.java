package com.yanjinbin.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeSP {


    //  834. 树中距离之和

    // 199. 二叉树的右视图

    // 333. 最大 BST 子树

    // 426. 将二叉搜索树转化为排序的双向链表

    // 663. 均匀树划分

    // 863. 二叉树中所有距离为 K 的结点

    // 889. 根据前序和后序遍历构造二叉树

    // 606. 根据二叉树创建字符串

    // 988. 从叶结点开始的最小字符串

    // 655. 输出二叉树

    // 662. 二叉树最大宽度  // 思路: 给节点编号  i 2i,2i+1;  起点
    public int widthOfBinaryTree(TreeNode root) {
       if (root==null) return 0;
       Deque<TreeNode> q = new LinkedList<>();
       root.val = 1; // 0 ,1 在这个题目条件下均可
       q.add(root);
       int max = 1;
       while (!q.isEmpty()){
           int N = q.size();
           max =Math.max(max,q.peekLast().val-q.peekFirst().val+1);
           for (int i=0;i<N;i++){
               root = q.poll();
               if (root.left!=null){
                   root.left.val = 2*root.val;
                   q.add(root.left);
               }
               if (root.right!=null){
                   root.right.val = 2*root.val+1;
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

    // 113. 路径总和 II

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

    // 666. 路径和 IV

    // 共同祖先问题

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

    // 1123. 最深叶节点的最近公共祖先
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        // 无法理解题意 pass
        return null;
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


}
