package com.yanjinbin.leetcode;

import lombok.AllArgsConstructor;
import lombok.Data;

// https://oi-wiki.org/ds/seg/
// 307 315
@Data
public class SegmentTree {

    @AllArgsConstructor
    public class SegmentTreeNode {
        int start, end;
        int sum;//can be max min
        SegmentTreeNode left;
        SegmentTreeNode right;

        public SegmentTreeNode(int start, int end, int sum) {
            this.start = start;
            this.end = end;
            this.sum = sum;
        }
    }

    public SegmentTreeNode root;

    public SegmentTree(int[] nums) {
        this.root = buildTree(0, nums.length - 1, nums);
    }

    public SegmentTreeNode buildTree(int start, int end, int[] vals) {
        if (start == end) return new SegmentTreeNode(start, end, vals[start]);
        int mid = (end - start) / 2 + start;
        SegmentTreeNode left = buildTree(start, mid, vals);
        SegmentTreeNode right = buildTree(mid + 1, end, vals);
        return new SegmentTreeNode(start, end, left.sum + right.sum);
    }


    public void updateTree(SegmentTreeNode root, int i, int val) {
        if (root.start == i && root.end == i) {
            root.sum = val;
            return;
        }
        int mid = (root.end - root.start) / 2 + root.start;
        if (i <= mid) {
            updateTree(root.left, i, val);
        } else {
            updateTree(root.right, i, val);
        }
        // 别忘记update!!!
        root.sum = root.left.sum + root.right.sum;
    }

    public int sumRange(SegmentTreeNode root, int i, int j) {
        if (root.start == i && j == root.end) {
            return root.sum;
        }
        int mid = (root.end - root.start) / 2 + root.start;
        if (j <= mid) {
            return sumRange(root.left, i, j);
        } else if (mid > i) {
            return sumRange(root.right, i, j);
        } else {
            // 别搞错mid+1放哪里哦
            return sumRange(root.left, i, mid) + sumRange(root.right, mid + 1, j);
        }
    }
}
