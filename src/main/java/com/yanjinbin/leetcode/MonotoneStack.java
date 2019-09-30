package com.yanjinbin.leetcode;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class MonotoneStack {
    // 42
    // 42. 接雨水 trap rain water  http://bit.ly/2RKoy0k
    // ✅
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

    // 单调递减栈
    public int trap4(int[] height) {
        Stack<Integer> s = new Stack<>();
        int i = 0, n = height.length, res = 0;
        while (i < n) {
            if (s.isEmpty() || height[i] <= height[s.peek()]) {
                s.push(i++);
            } else {
                int t = s.pop();
                if (s.isEmpty()) continue;
                //   int v1 = Math.min(height[i], height[s.peek()]) - height[t];
                //  int v2 = i-t;
                // int v3 = i-s.peek()-1;
                // System.out.println("v1: "+v1+" v2: "+v2+" v3: "+v3);
                res += Math.min(height[i], height[s.peek()] - height[t]) * (i - s.peek() - 1);
            }
        }
        return res;
    }

    // 496 单调
    public int[] nextGreaterElement01(int[] nums1, int[] nums2) {
        int[] ans = new int[nums1.length];
        Stack<Integer> s = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums2) {
            // 单调栈,栈顶元素最小 单调递减栈
            while (!s.isEmpty() && s.peek() < i) {
                map.put(s.peek(), i);
                s.pop();
            }
            s.push(i);
        }
        for (int i = 0; i < nums1.length; i++) {
            if (map.containsKey(nums1[i])) {
                ans[i] = map.get(nums1[i]);
            } else {
                ans[i] = -1;
            }
        }
        return ans;
    }

    // 解法2 类似
    public int[] nextGreaterElements02(int[] sub, int[] all) {
        int[] ans = new int[sub.length];
        Stack<Integer> s = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();
        int N = all.length;
        for (int i = N - 1; i >= 0; i--) {
            // 如果栈顶数  小于 待入栈数 那么 我一直削栈顶元素,知道 大于待入栈元素位置 ,然后入栈 所以是单调递减栈
            while (!s.isEmpty() && s.peek() < all[i]) {// 维护单调性
                s.pop();
            }
            if (!s.isEmpty()) map.put(all[i], s.peek());
            s.push(all[i]);// 维护递增还是递减属性
        }
        for (int i = 0; i < sub.length; i++) {
            if (map.containsKey(sub[i])) {
                ans[i] = map.get(sub[i]);
            } else ans[i] = -1;
        }
        return ans;
    }

    // 503

    public int[] nextGreaterElements01(int[] A) {
        int n = A.length, res[] = new int[n];
        Arrays.fill(res, -1);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n * 2; i++) {
            while (!stack.isEmpty() && A[stack.peek()] < A[i % n])
                res[stack.pop()] = A[i % n];
            stack.push(i % n);
        }
        return res;
    }

    public int[] nextGreaterElements02(int[] nums) {
        int N = nums.length;
        int[] ans = new int[N];
        Stack<Integer> s = new Stack<>();
        for (int i = 2 * N - 1; i >= 0; i--) {
            while (!s.isEmpty() && s.peek() <= nums[i % N]) {
                s.pop();
            }
            ans[i % N] = s.isEmpty() ? -1 : s.peek();
            s.push(nums[i % N]);
        }
        return ans;
    }

    public int[] nextGreaterElements03(int[] nums) {
        int n = nums.length, next[] = new int[n];
        Arrays.fill(next, -1);
        Stack<Integer> stack = new Stack<>(); // index stack
        for (int i = 0; i < n * 2; i++) {
            int num = nums[i % n];
            while (!stack.isEmpty() && nums[stack.peek()] < num)
                next[stack.pop()] = num;
            stack.push(i % n);
        }
        return next;
    }


    // 739
    // 739. 每日温度  递增栈
    public int[] dailyTemperatures(int[] T) {
        Stack<Integer> s = new Stack<>();
        int N = T.length;
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            while (!s.isEmpty() && T[s.peek()] < T[i]) {
                int idx = s.pop();
                ans[idx] = i - idx;
            }
            s.push(i);
        }
        return ans;
    }

    // 用分治 也可以实现
    // 84 最大柱形图面积 单调递减栈的运用
    public int largestRectangleArea01(int[] heights) {
        Stack<Integer> s = new Stack<>();
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            while (!s.isEmpty() && heights[s.peek()] > heights[i]) {
                //http://bit.ly/2m5KpEo
                maxArea = Math.max(maxArea, heights[s.pop()] * (i - (s.isEmpty() ? 0 : s.peek() + 1)));
            }
            s.push(i);
        }
        // finally pop out any bar left in the stack and calculate the area based on it
        while (!s.isEmpty()) {
            //                                                相当于i= len
            maxArea = Math.max(maxArea, heights[s.pop()] * (heights.length - (s.isEmpty() ? 0 : s.peek() + 1)));
        }
        return maxArea;
    }

    public int largestRectangleArea02(int[] heights) {
        Stack<Integer> s = new Stack<>();
        int maxArea = 0;
        int[] h = Arrays.copyOf(heights, heights.length + 1);
        for (int i = 0; i < h.length; i++) {
            while (!s.isEmpty() && h[s.peek()] > h[i]) {
                int idx = s.pop();
                System.out.println(h[idx] + " " + (i - (s.isEmpty() ? 0 : (s.peek() + 1))));
                maxArea = Math.max(maxArea, h[idx] * (i - (s.isEmpty() ? 0 : s.peek() + 1)));
            }
            s.push(i);
        }
        return maxArea;
    }
    /*
    public int largestRectangleArea03(int[] heights) {
        if (heights.length==1) return heights[0];// 错误 ,如果输入数据是[2,2]
        Stack<Integer> s = new Stack<>();
        int maxArea = 0;
        for (int i=0;i<heights.length;i++){
            while (!s.isEmpty()&&heights[s.peek()]>heights[i]){
                maxArea = Math.max(maxArea,heights[s.pop()]*(i-(s.isEmpty()? 0:s.peek()+1)));
            }
            s.push(i);
        }
        return maxArea;
    }*/

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
            res = Math.max(res, largestRectangleArea01(height));
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

    // 单调队列 http://poj.org/problem?id=2823
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

    // 456
    // 给定一个整数序列：a1, a2, ..., an，一个132模式的子序列 ai, aj, ak 被定义为：
    // 当 i < j < k 时，ai < ak < aj。设计一个算法，
    // 当给定有 n 个数字的序列时，验证这个序列中是否含有132模式的子序列。


    public boolean find132pattern(int[] nums) {
        int N = nums.length;
        if (N < 3) return false;
        int ak = Integer.MIN_VALUE;// 次大  第二大
        Stack<Integer> s = new Stack<>();
        for (int i = N - 1; i >= 0; i--) { // ai,aj  -->nums[i]   ak-->flag
            if (nums[i] < ak) {
                return true;
            } else {
                while (!s.isEmpty() && s.peek() < nums[i]) {
                    ak = Math.max(ak, s.pop());
                }
                s.push(nums[i]);
            }
        }
        return false;
    }

    // 解法2  还是错的  无法处理  边界情况 [-1,3,2]  这种从
    public boolean find132pattern01(int[] nums) {
        int N = nums.length;
        if (N < 3) return false;
        int flag = Integer.MAX_VALUE;
        Stack<Integer> s = new Stack<>();
        s.add(nums[0]);
        for (int i = 1; i < N; i++) {
            if (nums[i] > flag) {
                return true;
            } else {
                while (!s.isEmpty() && nums[i] < s.peek()) {
                    flag = Math.min(flag, s.pop());
                }
                s.push(nums[i]);
            }
        }
        return false;
    }
}
