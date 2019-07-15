import com.yanjinbin.leetcode.ListNode;
import com.yanjinbin.leetcode.Solution;
import com.yanjinbin.leetcode.TreeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class SolutionTest {

    private static Solution INSTANCE = new Solution();

    private ListNode headNode;
    private ListNode demoNode;

    @Before
    public void init() {
        headNode = ListNode.builder()
                .val(9).next(ListNode.builder()
                        .val(11).next(ListNode.builder()
                                .val(10).next(ListNode.builder()
                                        .val(12).next(ListNode.builder()
                                                .val(32).next(ListNode.builder()
                                                        .val(13).build()).build()).build()).build()).build()).build();

        demoNode = ListNode.builder()
                .val(9).next(ListNode.builder()
                        .val(11).next(ListNode.builder()
                                .val(10).next(ListNode.builder()
                                        .val(12).next(ListNode.builder()
                                                .val(32).next(ListNode.builder()
                                                        .val(13).build()).build()).build()).build()).build()).build();
    }

    @Test
    public void Demo() {
        Solution.demo();
    }

    @Test
    public void twoSum() {
        int[] ints = {1, 34, 45};
        int target = 46;
        INSTANCE.twoSum(ints, target);
    }

    @Test
    public void addTwoNumber() {
        ListNode l1 = ListNode.builder()
                .val(1).next(
                        ListNode.builder().val(2).next(
                                ListNode.builder().val(3).build()).
                                build())
                .build();
        ListNode l2 = ListNode.builder()
                .val(9).next(
                        ListNode.builder().val(5).next(
                                ListNode.builder().val(7).build()).
                                build())
                .build();

        ListNode listNode = INSTANCE.addTwoNumbers(l1, l2);
    }

    @Test
    public void TestsortList() {
        ListNode head = ListNode.builder()
                .val(3).next(ListNode.builder()
                        .val(5).next(ListNode.builder()
                                .val(9).next(ListNode.builder()
                                        .val(11).next(ListNode.builder()
                                                .val(20).build()).build()).build()).build()).build();
        INSTANCE.sortList(head);

    }

    @Test
    public void testMidNode() {
        ListNode head = ListNode.builder()
                .val(3).next(ListNode.builder()
                        .val(5).next(ListNode.builder()
                                .val(9).next(ListNode.builder()
                                        .val(11).next(ListNode.builder()
                                                .val(20).next(ListNode.builder()
                                                        .val(23).build()).build()).build()).build()).build()).build();
        ListNode slow = head, fast = head;
        // 其实就是可以估算出while条件的反面就知道最后一次fast更新落在那个节点范围上
        while (fast != null && fast.getNext() != null) {
            System.out.println(slow.getVal() + "\tfast\t" + fast.getVal());
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
    }

    @Test
    public void TestReverseSingleLinkedList() {
        System.out.println(headNode);
        ListNode listNode = INSTANCE.reverseSingleLinkedList(headNode);
        System.out.println(listNode);
    }

    @Test
    public void Test3sum() {
        int[] ints = {-1, 0, 1, 2, -1, -4};
        System.out.println(INSTANCE.threeSum(ints));
    }


    @Test
    public void removeNthFromEnd() {
        /*
        ListNode head = ListNode.builder().val(1).next(
                ListNode.builder().val(2).next(
                        ListNode.builder().val(3).next(
                                ListNode.builder().val(4).next(
                                        ListNode.builder().val(5).build()).build()).build()).build()).build();
        ListNode node = INSTANCE.removeNthFromEnd(head, 2);*/

        /*System.out.println("======");
        ListNode head1 = ListNode.builder().val(1).next(
                ListNode.builder().val(2).next(
                        ListNode.builder().val(3).next(
                                ListNode.builder().val(4).next(
                                        ListNode.builder().val(5).build()).build()).build()).build()).build();

        ListNode n1 = INSTANCE.removeNthFromEnd1(head1, 2);*/

        ListNode head2 = ListNode.builder().val(1).build();
        ListNode rn = INSTANCE.removeNthFromEnd(head2, 1);
        System.out.println(rn);
    }

    @Test
    public void longestPalindrome() {
        String s1 = "bb", s2 = "feccbbddeg";
        String rs = INSTANCE.longestPalindrome(s1);
        System.out.println(rs);
        rs = INSTANCE.longestPalindrome(s2);
        System.out.println(rs);
        System.out.println("=======");
        rs = INSTANCE.GoodLongestPalindrome(s1);
        System.out.println(rs);
        rs = INSTANCE.GoodLongestPalindrome(s2);
        System.out.println(rs);
    }

    @Test
    public void generateParenthesis() {
        System.out.println(INSTANCE.generateParenthesis(4));
    }

    @Test
    public void maxArea() {
        int[] heights = {1, 4, 8, 4, 9, 5};
        System.out.println(INSTANCE.maxArea(heights));
    }

    @Test
    public void trapRainWater() {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        for (int i = 0; i < height.length; i++) {
            System.out.println("i:" + i + "height:" + height[i]);
        }
        System.out.println("trap1:\t" + INSTANCE.trap1(height));
        System.out.println("trap2:\t" + INSTANCE.trap2(height));
        System.out.println("trap3:\t" + INSTANCE.trap3(height));
        System.out.println("trap4:\t" + INSTANCE.trap4(height));
    }

    @Test(timeout = 200)
    public void reverseList() {
        System.out.println(INSTANCE.reverseList(demoNode));
        System.out.println(INSTANCE.reverseListBad1(demoNode));
        System.out.println(INSTANCE.reverseListBad2(demoNode));
    }

    @Test(timeout = 1000)
    public void nextPermutation() {
        int[] nums = {1, 3, 4, 11, 9, 5, 4, 2};
        INSTANCE.nextPermutation(nums);
        for (int num : nums) {
            System.out.printf("%d \t", num);
        }
    }

    @Test
    public void climbStairs() {
        System.out.println(INSTANCE.climbStairs1(5));
        System.out.println("======");
        System.out.println(INSTANCE.climbStairs2(5));
        System.out.println("=====爬楼梯follow up问题===");
        System.out.println(INSTANCE.climbStairFU(10, 5));
    }

    @Test
    public void minPathSum() {
        int[][] grid = {{1, 2}, {5, 6}, {1, 2}};
        int[][] grid1 = {{0, 1}, {1, 0}};
        int ret = INSTANCE.minPathSum(grid);
        System.out.println(ret);
        System.out.println("=======");
        System.out.println(INSTANCE.minPathSum(grid1));
    }

    @Test
    public void sortColor() {
        int[] sums = {0};
        INSTANCE.sortColors(sums);
    }

    @Test
    public void subset() {
        int[] sums = {1, 3, 7};
        List<List<Integer>> res = INSTANCE.subsets1(sums);
        System.out.println(res);
        res = INSTANCE.subsets2(sums);
        System.out.println(res);
    }

    @Test
    public void exist() {
        char[][] board = new char[][]{
                {'a', 'c', 'd'}, {'e', 'f', 'i'},
                {'j', 'p', 'w'},
        };
        String word = "cpf";
        System.out.println(INSTANCE.exist(board, word));
        board = new char[][]{{'a', 'a'},};
        word = "aaa";
        System.out.println(INSTANCE.exist(board, word));
    }

    @Test
    public void wordBreak() {
        /*
        List<String> words = new ArrayList<>();
        words.add("apple");
        words.add("pen");
        words.add("cherry");
        words.add("melon");
        String s = "applepencherrypenmelon";

         */

        List<String> words = new ArrayList<>();
        words.add("car");
        words.add("ca");
        words.add("rs");
        String s = "cars";
        boolean ret = INSTANCE.wordBreak(s, words);
        System.out.println(ret);
    }

    @Test
    public void maxProduct() {
        int[] nums = {2, 5, -3, 9, -1, 7, -2, 0, 10, 2, -9};
        System.out.println(INSTANCE.maxProduct(nums));
    }

    @Test
    public void findDuplicate() {
        int[] nums = {1, 3, 4, 6, 7, 8, 3, 3};
        System.out.println(INSTANCE.findDuplicate0(nums));
        System.out.println(INSTANCE.findDuplicate1(nums));
    }


    @Test
    public void misc() {

        int[][] intervals = new int[][]{{1, 2}, {10, 12}, {8, 9}, {3, 9}};
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        for (int i = 0; i < intervals.length; i++) {
            System.out.println(intervals[i][0] + "\t" + intervals[i][1]);
        }
        String substring = "ab4je9kcd".substring(1, 3);
        System.out.println(substring);
        int i = 1;
        int j = 0;
        String s = "abcd";
        int[] ints = {293, 394, 45, 8};
        System.out.println(s.charAt(i));
        System.out.println(s.charAt(i++));
        System.out.println(s.charAt(++i));
        System.out.println(ints[++j]);
        System.out.println(ints[j++]);
        System.out.println(ints[++j]);

        for (int k = 0; k < 10; k++) {
            System.out.println("k的值:\t" + k);
        }
        for (int m = 0; m < 10; ++m) {
            System.out.println("m的值:\t" + m);
        }

        int[][] grid = {{1, 3, 5, 7, 8}, {2, 4, 6, 8}, {3, 6, 9, 10, 11, 12}};
        System.out.println(grid[2][0]);
        System.out.println(grid[0][2]);
        System.out.println(grid[1][0]);
        System.out.println("length:" + grid.length);


        int m = 0;
        for (int k = 0; k < 10; k++) {
//            //
//            int temp = i;
//            i = i+1;
//            i = temp;

            m = m++;


            m = ++m;
            System.out.println(m);
        }
        System.out.println("m值:\t" + m);
        System.out.println("abcdimk".substring(3));
        System.out.println("abcdef".substring(1, 3));
        String res = "iwowjd";
        System.out.println(res.substring(2, res.length()));
        System.out.println(res.substring(res.length()).equals(""));
        int a = 1, b = 4, c = 14;
        System.out.println(a + (c - a) / 2);
        System.out.println((a + c) / 2);
        System.out.println(b + (c - b) / 2);
        System.out.println((b + c) / 2);
    }

    @Test
    public void detectedCycle() {
        ListNode node4 = ListNode.builder().val(-4).build();
        ListNode node2 = ListNode.builder().val(2).build();
        ListNode node3 = ListNode.builder().val(0).build();
        ListNode node1 = ListNode.builder().val(3).build();
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node2);
        System.out.println("ret\t" + INSTANCE.detectCycle0(node1));
        ListNode node5 = ListNode.builder().val(1).build();
        System.out.println("====");
        System.out.println(INSTANCE.detectCycle2(node5));
        System.out.println(INSTANCE.detectCycle0(null));
    }

    @Test
    public void canPartition() {
        int[] nums = {1, 5, 11, 5};
        System.out.println(INSTANCE.canPartition0(nums));
    }

    @Test
    public void lengthOfLIS() {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(INSTANCE.lengthOfLIS(nums));
    }

    @Test
    public void findDisappearedNumbers() {
        int[] nums = {1, 2, 5, 2, 1};
        // https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/discuss/92956/Java-accepted-simple-solution/97460
        // Each time when a new value X is read, it changes the corresponding Xth number (value at index X-1) into negative, indicating value X is read for the first time.
        INSTANCE.findDisappearedNumbers(nums);
    }

    @Test
    public void majorElement() {
        int[] nums = {2, 1, 3, 2, 1, 2, 3, 2};
        System.out.println(INSTANCE.majorityElement0(nums));
    }

    @Test
    public void moveZeros() {
        int[] nums = {0, 1, 0, 3, 12};
        INSTANCE.moveZeroes(nums);
        for (int num : nums) {
            System.out.println(num);
        }
    }

    @Test
    public void mergeIntervals() {
        int[][] intervals = new int[][]{{1, 2}, {10, 12}, {8, 9}, {3, 9}};
        System.out.println(INSTANCE.merge(intervals));
    }

    @Test
    public void findUnsortedSubarray() {
        //  int[] nums = {2, 6, 4, 8, 10, 9, 15};
        int[] nums = {1, 2, 3, 4};
        System.out.println(INSTANCE.findUnsortedSubarray0(nums));
        int[] nums1 = {9, 8, 7, 6, 12, 13, 15};
        System.out.println(INSTANCE.findUnsortedSubarray1(nums1));
    }

    @Test
    public void subarraySum() {
        int[] nums = {1, 3, 4, 2, 1, 5, 1, 1, 4, 2, 3, 6, 1};
        System.out.println(INSTANCE.subarraySum(nums, 7));
    }

    @Test
    public void mergeTwoSortedList() {
        ListNode node1 =
                ListNode.builder().val(1).next(
                        ListNode.builder().val(3).next(
                                ListNode.builder().val(5).next(
                                        ListNode.builder().val(7).build()).build()).build()).build();

        ListNode node2 = ListNode.builder().val(2).next
                (ListNode.builder().val(4).next(
                        ListNode.builder().val(6).next(
                                ListNode.builder().val(8).next(
                                        ListNode.builder().val(10).next(
                                                ListNode.builder().val(14).build()).build()).build()).build()).build()).build();
        System.out.println(INSTANCE.merge(node1, node2));

    }

    @Test
    public void sort() {
        int[] nums = new int[]{7, 5, 1, 6, 4, 3, 8,};
        System.out.println(INSTANCE.partition(nums, 0, nums.length - 1));
        System.out.println(Arrays.toString(nums));
    }

    @Test
    public void findKthLargest() {
        int[] nums = new int[]{7, 5, 1, 6, 4, 3, 8,};
        System.out.println(INSTANCE.findKthLargest0(nums, 3));
        System.out.println(INSTANCE.findKthLargest1(nums, 5));
        System.out.println(INSTANCE.findKthLargest2(nums, 4));
    }

    @Test
    public void productExceptSelf() {
        int[] nums = new int[]{1, 2, 3, 4};
        INSTANCE.productExceptSelf(nums);
        INSTANCE.productExceptSelf(nums);
    }

    @Test
    public void searchRange() {
        int[] nums = new int[]{5, 7, 7, 8, 8, 10};
        int target = 8;
        System.out.println(Arrays.toString(INSTANCE.searchRange0(nums, 8)));

        nums = new int[]{2, 2};
        target = 1;

        nums = new int[]{};
        target = 1;
    }

    @Test
    public void uniquePath() {
        int count = INSTANCE.uniquePaths(7, 3);
        assert count == 28;
    }

    @Test
    public void combinationSum() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(9);
        list.add(119);
        list.add(23);
        list.add(37);
        System.out.println(list.get(list.size() - 1));
        System.out.println(list.remove(list.size() - 1));

        int[] nums = {2, 3, 5};
        int target = 7;
        System.out.println(INSTANCE.combinationSum1(nums, target));
    }

    @Test
    public void dailyTemperature() {
        int[] T = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println(Arrays.toString(T));
        String ret = Arrays.toString(INSTANCE.dailyTemperatures(T));
        System.out.println(ret);

    }

    @Test
    public void largestRectangleArea() {
        int[] heights = new int[]{2, 1, 5, 6, 2, 3};
        System.out.println(INSTANCE.largestRectangleArea0(heights));
        System.out.println(INSTANCE.largestRectangleArea1(heights));
    }

    @Test
    public void maximalRectangle() {
        char[][] matrix = new char[][]{
                {'0', '1', '0', '1', '1'},
                {'0', '1', '1', '1', '0'},
                {'0', '0', '1', '1', '1'},
                {'0', '1', '1', '1', '1'},
                {'0', '1', '1', '1', '0'},
        };
        // System.out.println(INSTANCE.maximalRectangle0(matrix));
        assert INSTANCE.maximalRectangle0(matrix) == 8;
    }

    @Test
    public void longestConsecutive() {
        int[] nums = {100, 1, 200, 3, 4, 2};
        assert INSTANCE.longestConsecutive(nums) == 4;
        //System.out.println(INSTANCE.longestConsecutive(nums));
    }

    @Test
    public void inorderTraversal() {

        TreeNode root = TreeNode.builder().val(1).build();
        TreeNode node2 = TreeNode.builder().val(2).build();
        TreeNode node3 = TreeNode.builder().val(3).build();
        root.setRight(node2);
        node2.setLeft(node3);


        TreeNode F = TreeNode.builder().name("F").build();
        TreeNode B = TreeNode.builder().name("B").build();
        TreeNode G = TreeNode.builder().name("G").build();
        TreeNode A = TreeNode.builder().name("A").build();
        TreeNode D = TreeNode.builder().name("D").build();
        TreeNode I = TreeNode.builder().name("I").build();
        TreeNode C = TreeNode.builder().name("C").build();
        TreeNode E = TreeNode.builder().name("E").build();
        TreeNode H = TreeNode.builder().name("H").build();

        F.setLeft(B);
        F.setRight(G);
        B.setLeft(A);
        B.setRight(D);
        D.setLeft(C);
        D.setRight(E);
        G.setRight(I);
        I.setLeft(H);

        // System.out.println(INSTANCE.inorderTraversal0(F));
        // System.out.println(INSTANCE.inorderTraversal1(root));
        System.out.println(INSTANCE.inorderTraversal2(F));
    }
}
