package com.yanjinbin.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
                res +=Math.min(height[i],height[s.peek()]-height[t])*(i-s.peek()-1);
            }
        }
        return res;
    }

    // 496 单调
    public int[] nextGreaterElement01(int[] nums1, int[] nums2){
        int[] ans = new int[nums1.length];
        Stack<Integer> s = new Stack<>();
        Map<Integer,Integer> map = new HashMap<>();
        for (int i:nums2){
            // 单调栈,栈顶元素最小 单调递减栈
            while (!s.isEmpty()&&s.peek()<i){
                map.put(s.peek(),i);
                s.pop();
            }
            s.push(i);
        }
        for (int i=0;i<nums1.length;i++){
            if (map.containsKey(nums1[i])){
                ans[i]=map.get(nums1[i]);
            }
            else {
                ans[i]=-1;
            }
        }
        return ans;
    }
    // 解法2 类似
    public int[] nextGreaterElements02(int[] sub,int[] all){
        int[] ans = new int[sub.length];
        Stack<Integer> s = new Stack<>();
        Map<Integer,Integer> map = new HashMap<>();
        int N = all.length;
        for(int i= N-1;i>=0;i--){
            // 如果栈顶数  小于 待入栈数 那么 我一直削栈顶元素,知道 大于待入栈元素位置 ,然后入栈 所以是单调递减栈
            while (!s.isEmpty()&&s.peek()<all[i]){// 维护单调性
                s.pop();
            }
            if (!s.isEmpty()) map.put(all[i],s.peek());
            s.push(all[i]);// 维护递增还是递减属性
        }
        for (int i=0;i<sub.length;i++){
            if (map.containsKey(sub[i])){
                ans[i]=map.get(sub[i]);
            }else ans[i]=-1;
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

    public int[] nextGreaterElements02(int[] nums){
        int N = nums.length;
        int[] ans = new int[N];
        Stack<Integer> s = new Stack<>();
        for(int i= 2*N-1;i>=0;i--){
            while (!s.isEmpty()&&s.peek()<=nums[i%N]){
                s.pop();
            }
            ans[i%N] = s.isEmpty()?-1:s.peek();
            s.push(nums[i%N]);
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
             stack.push(i%n);
        }
        return next;
    }


    // 739
    // 739. 每日温度  递增栈
    public int[] dailyTemperatures(int[] T) {
        Stack<Integer> s = new Stack<>();
        int N = T.length;
        int[] ans = new int[N];
        for (int i=0;i<N;i++){
            while (!s.isEmpty()&&T[s.peek()]<T[i]){
                int idx = s.pop();
                ans[idx]=i-idx;
            }
            s.push(i);
        }
        return ans;
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

    // 456

    public int find132pattern(int[] nums){
        return -1;
    }

}
