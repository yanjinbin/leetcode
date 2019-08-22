import com.yanjinbin.leetcode.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class SolutionTest {

    private static Solution INSTANCE = new Solution();
    private static CareerUp INSTANCEM2 = new CareerUp();
    private ListNode headNode;
    private ListNode demoNode;

    @Before()
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

    // 基础知识准备
    @Test
    public void operator() {
        System.out.println("====自增运算符====");
        int i = 1;
        i++;
        ++i;
        System.out.println(i); // 3
        i = i++;
        System.out.println(i); // 3
        System.out.println(i); // 3
        //  System.out.println("abcdefg".charAt(i++)); // d
        //  System.out.println("abcdefg".charAt(++i)); // f
        int j = i++;
        System.out.println(j); // 3
        System.out.println(i); // 4
        j = ++i;
        System.out.println(i); // 5
        System.out.println(j); // 5

    }

    @Test
    public void whileFor() {
        int i = 3;
        for (; i < 5; ++i) {
            System.out.println("i值: " + i);
        }
        // reset
        i = 3;
        for (; i < 5; i++) {
            System.out.println("i值: " + i);
        }
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(1);
        ll.add(2);
        System.out.println(ll);
        ll.addFirst(3);
        System.out.println(ll);
        ll.pollLast();
        System.out.println(ll);
        ll.add(2, 10);
        System.out.println(ll);
        ll.add(2, 77);
        System.out.println(ll);
        // Integer类型了
        Integer[] nums = {5, 4, 3, 2, 1};
        Arrays.sort(nums);
        for (int num : nums) {
            System.out.printf("%d \t", num);
            System.out.println();
        }

        int tmp = nums[1];
        Arrays.sort(nums, Collections.reverseOrder());
        List<Integer> integers = Arrays.asList(1, 2, 3);
        System.out.println(integers);
    }


    @Test
    public void StringAPI() {
        System.out.println("abc".charAt(1));
        char[] chars = "abc".toCharArray();
        System.out.println(chars[0]);
        // char 单子字符 256
        chars = new char[256];
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
    public void sortList() {
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
    public void ReverseSingleLinkedList() {
        System.out.println(headNode);
        ListNode listNode = INSTANCE.reverseSingleLinkedList(headNode);
        System.out.println(listNode);
    }


    @Test
    public void isPalindrome() {


        ListNode heada = ListNode.builder().val(1).next(ListNode.builder().val(2).next(
                ListNode.builder().val(3).next(ListNode.builder().val(4).next(
                        ListNode.builder().val(5).next(ListNode.builder().val(6).build()).build()).build()
                ).build()).build()).build();


        ListNode head = ListNode.builder().val(1).next(ListNode.builder().val(2).next(
                ListNode.builder().val(3).next(ListNode.builder().val(4).next(
                        ListNode.builder().val(5).next(ListNode.builder().val(6).next(
                                ListNode.builder().val(7).build()
                        ).build()).build()).build()
                ).build()).build()).build();


        ListNode head0 = ListNode.builder().val(7).next(ListNode.builder().val(23).next(ListNode.builder().val(11).build()).build()).build();


        ListNode head1 = ListNode.builder().val(7).next(ListNode.builder().val(23).build()).build();

        ListNode head2 = ListNode.builder().val(9).build();

        ListNode head3 = null;

        System.out.println(INSTANCE.isPalindrome1(heada));
        System.out.println(INSTANCE.isPalindrome1(head));
        System.out.println(INSTANCE.isPalindrome1(head0));
        System.out.println(INSTANCE.isPalindrome1(head1));
        System.out.println(INSTANCE.isPalindrome1(head2));
        System.out.println(INSTANCE.isPalindrome1(head3));


        ListNode head4 = ListNode.builder().val(1).next(ListNode.builder().val(2).next(ListNode.builder().val(1).build()).build()).build();
        System.out.println(INSTANCE.isPalindrome2(head4));
    }

    @Test
    public void Threesum() {
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

        int k = 10;
        System.out.println(k-- >= 10);
        int y = 10;
        System.out.println(--y >= 10);

        char[] key = {'c', 'b', 'a'};
        System.out.println(Arrays.toString(key));
        System.out.println(String.valueOf(key));
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

    @Test(timeout = 1000)
    public void traversal() {

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

        System.out.println("====中序遍历=====");
        System.out.println(INSTANCE.inorderTraversal0(F));
        System.out.println(INSTANCE.inorderTraversal1(root));
        System.out.println(INSTANCE.inorderTraversal2(F));


        TreeNode a = TreeNode.builder().name("a").val(3).build();
        TreeNode b = TreeNode.builder().name("b").val(9).build();
        TreeNode c = TreeNode.builder().name("c").val(20).build();
        TreeNode d = TreeNode.builder().name("d").val(15).build();
        TreeNode e = TreeNode.builder().name("e").val(7).build();
        a.setLeft(b);
        a.setRight(c);
        b.setLeft(null);
        b.setRight(null);
        c.setLeft(d);
        c.setRight(e);

        System.out.println("=====层次遍历====");
        System.out.println(INSTANCE.levelOrder0(F));
        System.out.println(INSTANCE.levelOrder1(a));


    }


    @Test(timeout = 1000)
    public void preorder() {

        TreeNode F = TreeNode.builder().name("F").val(6).build();
        TreeNode B = TreeNode.builder().name("B").val(2).build();
        TreeNode G = TreeNode.builder().name("G").val(7).build();
        TreeNode A = TreeNode.builder().name("A").val(1).build();
        TreeNode D = TreeNode.builder().name("D").val(4).build();
        TreeNode I = TreeNode.builder().name("I").val(9).build();
        TreeNode C = TreeNode.builder().name("C").val(3).build();
        TreeNode E = TreeNode.builder().name("E").val(5).build();
        TreeNode H = TreeNode.builder().name("H").val(8).build();

        F.setLeft(B);
        F.setRight(G);
        B.setLeft(A);
        B.setRight(D);
        D.setLeft(C);
        D.setRight(E);
        G.setRight(I);
        I.setLeft(H);
        System.out.println("======前序遍历=====");
        System.out.println(INSTANCE.preorderTraversal0(F));
        System.out.println(INSTANCE.preorderTraversal1(F));
    }

    @Test(timeout = 3000)
    public void postorder() {

        TreeNode F = TreeNode.builder().name("F").val(6).build(); // ROOT
        TreeNode B = TreeNode.builder().name("B").val(2).build();
        TreeNode G = TreeNode.builder().name("G").val(7).build();
        TreeNode A = TreeNode.builder().name("A").val(1).build();
        TreeNode D = TreeNode.builder().name("D").val(4).build();
        TreeNode I = TreeNode.builder().name("I").val(9).build();
        TreeNode C = TreeNode.builder().name("C").val(3).build();
        TreeNode E = TreeNode.builder().name("E").val(5).build();
        TreeNode H = TreeNode.builder().name("H").val(8).build();

        F.setLeft(B);
        F.setRight(G);
        B.setLeft(A);
        B.setRight(D);
        D.setLeft(C);
        D.setRight(E);
        G.setRight(I);
        I.setLeft(H);
        System.out.println("===后续遍历===");
        System.out.println(INSTANCE.postorderTraversal0(F));
        System.out.println(INSTANCE.postorderTraversal1(F));
    }

    @Test
    public void numTrees() {
        System.out.println(INSTANCE.numTrees(3));
    }

    @Test(timeout = 1000)
    public void maxDepth() {
        TreeNode a = TreeNode.builder().name("a").val(3).build();
        TreeNode b = TreeNode.builder().name("b").val(9).build();
        TreeNode c = TreeNode.builder().name("c").val(20).build();
        TreeNode d = TreeNode.builder().name("d").val(15).build();
        TreeNode e = TreeNode.builder().name("e").val(7).build();
        a.setLeft(b);
        a.setRight(c);
        b.setLeft(null);
        b.setRight(null);
        c.setLeft(d);
        c.setRight(e);
        assert INSTANCE.maxDepth0(a) == 3;
        assert INSTANCE.maxDepth1(a) == 3;
    }


    @Test(timeout = 2000)
    public void isValidBST() {
        TreeNode root = TreeNode.builder().val(2).name("A").build();
        TreeNode node0 = TreeNode.builder().val(1).name("B").build();
        TreeNode node1 = TreeNode.builder().val(3).name("B").build();
        root.setLeft(node0);
        root.setRight(node1);
        assert INSTANCE.isValidBST0(root) == true;
        assert INSTANCE.isValidBST0(TreeNode.builder().val(Integer.MAX_VALUE).build()) == true;
    }

    @Test(timeout = 2000)
    public void isSymmetric() {
        assert (true ^ true) == false;
        assert (false ^ false) == false;
        assert (true ^ false) == true;

        TreeNode root = TreeNode.builder().val(2).name("A").build();
        TreeNode node0 = TreeNode.builder().val(3).name("B").build();
        TreeNode node1 = TreeNode.builder().val(3).name("B").build();
        root.setLeft(node0);
        root.setRight(node1);
        assert INSTANCE.isSymmetric(root) == true;
    }

    @Test(timeout = 2000)
    public void flatten() {
        TreeNode a = TreeNode.builder().name("a").val(1).build();
        TreeNode b = TreeNode.builder().name("b").val(2).build();
        TreeNode c = TreeNode.builder().name("c").val(3).build();
        TreeNode d = TreeNode.builder().name("d").val(4).build();
        TreeNode e = TreeNode.builder().name("e").val(5).build();
        TreeNode f = TreeNode.builder().name("f").val(6).build();
        a.setLeft(b);
        a.setRight(e);
        b.setLeft(c);
        b.setRight(d);
        e.setRight(f);
        INSTANCE.flatten(a);
        System.out.println(a);
    }

    @Test(timeout = 2000)
    public void invertTree() {
        TreeNode a = TreeNode.builder().name("a").val(1).build();
        TreeNode b = TreeNode.builder().name("b").val(2).build();
        TreeNode c = TreeNode.builder().name("c").val(3).build();
        b.setLeft(a);
        b.setRight(c);
        System.out.println(INSTANCE.invertTree(b));
    }

    @Test(timeout = 2000)
    public void pathSum() {
        TreeNode root = TreeNode.builder().val(1).build();

        INSTANCE.pathSum0(root, 7);
        INSTANCE.pathSum1(root, 7);
        INSTANCE.pathSum2(root, 7);
    }

    @Test(timeout = 2000)
    public void convertBST() {
        TreeNode a = TreeNode.builder().name("a").val(1).build();
        TreeNode b = TreeNode.builder().name("b").val(2).build();
        TreeNode c = TreeNode.builder().name("c").val(3).build();
        TreeNode d = TreeNode.builder().name("d").val(4).build();
        TreeNode e = TreeNode.builder().name("e").val(5).build();
        TreeNode f = TreeNode.builder().name("f").val(6).build();
        d.setLeft(b);
        d.setRight(c);
        b.setLeft(a);
        d.setRight(e);
        e.setRight(f);
        INSTANCE.convertBST(d);
        System.out.println(d);
    }

    @Test(timeout = 3000)
    public void mergeTrees() {

        TreeNode a = TreeNode.builder().name("a").val(1).build();
        TreeNode b = TreeNode.builder().name("b").val(2).build();
        TreeNode c = TreeNode.builder().name("c").val(3).build();

        b.setLeft(a);
        b.setRight(c);

        TreeNode d = TreeNode.builder().name("d").val(4).build();
        TreeNode e = TreeNode.builder().name("e").val(5).build();
        TreeNode f = TreeNode.builder().name("f").val(6).build();
        d.setRight(e);
        e.setRight(f);

        System.out.println(INSTANCE.mergeTrees0(a, d));
        System.out.println(INSTANCE.mergeTrees1(a, d));
    }

    @Test(timeout = 1000)
    public void diameterOfBinaryTree() {

        TreeNode a = TreeNode.builder().name("a").val(1).build();
        TreeNode b = TreeNode.builder().name("b").val(2).build();
        TreeNode c = TreeNode.builder().name("c").val(3).build();
        TreeNode d = TreeNode.builder().name("d").val(4).build();
        TreeNode e = TreeNode.builder().name("e").val(5).build();
        TreeNode f = TreeNode.builder().name("f").val(6).build();
        d.setLeft(b);
        d.setRight(c);
        b.setLeft(a);
        d.setRight(e);
        e.setRight(f);

        assert INSTANCE.diameterOfBinaryTree0(d) == 4;
        assert INSTANCE.diameterOfBinaryTree1(d) == 4;
        System.out.println(INSTANCE.diameterOfBinaryTree0(d));

    }

    @Test(timeout = 1000)
    public void sortedArrayToBST() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assert INSTANCE.sortedArrayToBST(nums).getVal() == 5;
    }

    @Test(timeout = 1000)
    public void buildTree() {
        int[] preOrder = {5, 4, 11, 8, 13, 9};
        int[] inOrder = {11, 4, 5, 13, 8, 9};
        int[] postOrder = {11, 4, 13, 9, 8, 5};
        assert INSTANCE.buildTreePreIn(preOrder, inOrder).getVal() == 5;
        assert INSTANCE.buildTreeInPost(inOrder, postOrder).getVal() == 5;

    }

    @Test(timeout = 3)
    public void rob() {
        int[] nums = {1, 2, 3, 1};
        // 打家劫舍Ⅰ
        assert INSTANCE.rob1(nums) == 4;
        assert INSTANCE.rob2(nums) == 4;

        // 打家劫舍Ⅱ
        assert INSTANCE.rob3(nums) == 4;

        // 打家劫舍Ⅲ
        TreeNode a = TreeNode.builder().name("a").val(1).build();
        TreeNode b = TreeNode.builder().name("b").val(2).build();
        TreeNode c = TreeNode.builder().name("c").val(3).build();
        TreeNode d = TreeNode.builder().name("d").val(4).build();
        TreeNode e = TreeNode.builder().name("e").val(5).build();
        TreeNode f = TreeNode.builder().name("f").val(6).build();
        d.setLeft(b);
        d.setRight(e);
        b.setLeft(a);
        b.setRight(c);
        e.setRight(f);
        assert INSTANCE.rob4(d) == 14;

    }

    @Test(timeout = 1000)
    public void serializeDeserialize() {

        Integer[] nums = {1, 2, 3, 4, 5, null, 6};
        System.out.println(Arrays.toString(nums));
        String s = Arrays.toString(nums);
        System.out.println("====");
        System.out.println(s.substring(1, s.length() - 1));

        TreeNode a = TreeNode.builder().name("a").val(1).build();
        TreeNode b = TreeNode.builder().name("b").val(2).build();
        TreeNode c = TreeNode.builder().name("c").val(3).build();
        TreeNode d = TreeNode.builder().name("d").val(4).build();
        TreeNode e = TreeNode.builder().name("e").val(5).build();
        TreeNode f = TreeNode.builder().name("f").val(6).build();
        d.setLeft(b);
        d.setRight(e);
        b.setLeft(a);
        b.setRight(c);
        e.setRight(f);

        System.out.println(INSTANCE.serialize(d));
        assert INSTANCE.deserialize(INSTANCE.serialize(d)).getVal() == d.getVal();
    }

    @Test(timeout = 2000)
    public void countSubstrings() {
        String data = "abdccdbbdca";
        System.out.println(INSTANCE.countSubstrings(data));
        data = "aaa";
        assert INSTANCE.countSubstrings(data) == 6;


        //   INSTANCE.countSubstrings1("fdsklf");
        System.out.println("-=====");
        INSTANCE.countSubstrings2("fdsklf");

    }

    @Test
    public void getIntersectionNode() {
        ListNode a = ListNode.builder().val(1).next(
                ListNode.builder().val(2).next(
                        ListNode.builder().val(3).build()).build()).build();
        ListNode b = ListNode.builder().val(4).next(
                ListNode.builder().val(5).next(
                        ListNode.builder().val(6).next(ListNode.builder().val(7).next(ListNode.builder().val(8).build()).build()).build()).build()).build();

        ListNode c = ListNode.builder().val(9).next(
                ListNode.builder().val(10).next(
                        ListNode.builder().val(11).next(
                                ListNode.builder().val(12).next(
                                        ListNode.builder().val(13).build()).build()).build()).build()).build();
        a.setNext(c);
        b.setNext(c);
        assert INSTANCE.getIntersectionNode(a, b).getVal() == 9;
    }

    @Test
    public void hammingDistance() {
        assert INSTANCE.hammingDistance(1, 4) == 2;
    }

    @Test(timeout = 1000)
    public void findrams() {
        System.out.println(INSTANCE.findAnagrams1("cbaebabacd", "abc"));
        // 越界了
        System.out.println(INSTANCE.findAnagrams("abab", "ab"));

        System.out.println(INSTANCE.findAnagrams1("acac", "ac"));

        System.out.println(INSTANCE.isAnagram("rat", "tar"));
    }

    @Test
    public void minStack() {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        minStack.getMin();   // --> 返回 -3.
        minStack.pop();
        minStack.top();      // --> 返回 0.
        assert minStack.getMin() == -2;

    }

    @Test
    public void findTargetSumWays() {
        int[] nums = {1, 1, 1, 1, 1};
        INSTANCE.findTargetSumWays(nums, 3);
    }

    @Test
    public void reconstructedQueue() {
        int[][] people = new int[][]{{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}};
        Arrays.sort(people, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0]);
        INSTANCE.reconstructQueue0(people);
    }

    @Test(timeout = 3000)
    public void isMatch() {
        assert INSTANCE.isMatch0("aa", "a") == false;
        assert INSTANCE.isMatch0("aa", "*") == true;
        assert INSTANCE.isMatch0("cb", "*a") == false;
        assert INSTANCE.isMatch0("adceb", "*a*b") == true;
        assert INSTANCE.isMatch0("acdcb", "a*c?b") == false;

        assert INSTANCE.isMatch1("aa", "a") == false;
        assert INSTANCE.isMatch1("adceb", "*a*b") == true;

        System.out.println(INSTANCE.isMatch2("ab", ".*"));
    }

    @Test
    public void decodeNumStr() {
        // 展示字符串如何转成数字 例: "789e" ---> 789
        System.out.println("展示字符串如何转成数字 例: \"789e\" ---> 789");
        String s = "789e";
        int cnt = 0;
        int idx = 0;
        while (s.charAt(idx) >= '0' && s.charAt(idx) <= '9') {
            // 利用ascii 性质 int =  char -'0'
            cnt = cnt * 10 + s.charAt(idx++) - '0';
            //  cnt = cnt * 10 + s.charAt(idx++) - 48;
        }
        System.out.println("=======");
        System.out.println("值: " + cnt);

    }

    @Test
    public void decodeString() {
        INSTANCE.decodeString("3[a2[c]]").equals("accaccacc");
        // reset
        INSTANCE.idx = 0;
        System.out.println(INSTANCE.decodeString("3[a]2[bc]"));

    }


    @Test
    public void TwoDArray() {
        System.out.println("==常识===");
        int[][] nums = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assert nums[0][0] == 1;
        assert nums[0][1] == 2;
        assert nums[1][0] == 4;
    }


    @Test
    public void minDistance() {
        assert INSTANCE.minDistance("horse", "rose") == 2;
        assert INSTANCE.minDistance1("horse", "rois") == 3;
    }

    @Test
    public void removeInvalidParentheses() {
        System.out.println(INSTANCE.isValid("(((a)()))"));
        System.out.println(INSTANCE.isValid(")()("));

        System.out.println(INSTANCE.removeInvalidParentheses0("()())()"));
        System.out.println(INSTANCE.removeInvalidParentheses0("(a)())()"));
        System.out.println(INSTANCE.removeInvalidParentheses0(")("));
    }

    @Test
    public void maxCoins() {
        int[] nums = {3, 1, 5, 8};
        assert INSTANCE.maxCoins(nums) == 167;
    }

    @Test
    public void groupAnagrams() {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(INSTANCE.groupAnagrams(strs));
        System.out.println(INSTANCE.groupAnagrams1(strs));
    }

    @Test
    public void leastInterval() {
        char[] tasks = {'A', 'A', 'A', 'B', 'B', 'B', 'C'};
        int n = 2;
        System.out.println(INSTANCE.leastInterval(tasks, n));
        System.out.println(INSTANCE.leastInterval1(tasks, n));
        // corner case
        n = 0;
        System.out.println(INSTANCE.leastInterval(tasks, n));

    }

    @Test
    public void meeting() {
        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}};
        assert INSTANCE.canAttendMeetings(intervals) == false;

        assert INSTANCE.minMeetingRooms0(intervals) == 2;
        assert INSTANCE.minMeetingRooms1(intervals) == 2;

    }

    @Test
    public void topKFrequent() {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        System.out.println(INSTANCE.topKFrequent2(nums, k));
    }

    @Test
    public void numSquares() {
        // 四平方和定理 不好掌握 就用迭代DP方法算吧
        System.out.println(INSTANCE.numSquares1(12));
        assert INSTANCE.numSquares2(12) == 3;
    }

    @Test
    public void maxSlidingWindow() {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        System.out.println(Arrays.toString(INSTANCE.maxSlidingWindow0(nums, k)));
        System.out.println(Arrays.toString(INSTANCE.maxSlidingWindow1(nums, k)));
    }

    @Test
    public void bitOperation() {

        System.out.println(Integer.toBinaryString(209));
        System.out.println(true ^ true);
        System.out.println(true ^ false);
        System.out.println(false ^ true);
        System.out.println(false ^ false);
        System.out.println(true || true);
        System.out.println(true || false);
        System.out.println(false || false);

        System.out.println("ArrayDeque函数的用到的位运算");
        int cap = 16;
        int head = 18;
        int ret = (head + 1) & (cap - 1);
        System.out.println(ret);
        System.out.println("=====上述这些都不是问题关键关键是如何处理head从尾回到头 ===");
        // 参考链接 http://bit.ly/2YsFIpg
        head = 15;
        ret = (head + 1) & (cap - 1);
        System.out.println(ret);
        assert ret == 0;
        assert head++ == 15;

        System.out.println("8&7的值: " + (8 & 7));
        System.out.println(5 & 3);
        System.out.println(219 / 10);
        System.out.println(219 % 10);
        System.out.println(219 / 10);
        System.out.println(21 % 10);
        System.out.println(21 / 10);
        System.out.println(2 / 10);
    }

    @Test
    public void longestValidParentheses() {
        assert INSTANCE.longestValidParentheses(")(()()()(") == 6;
    }

    @Test
    public void reverse() {
        assert INSTANCE.reverse0(543) == 345;
        assert INSTANCE.reverse0(-543) == -345;
        // int overflow
        System.out.println(INSTANCE.reverse(1534236469));
        assert INSTANCE.reverse(-543) == -345;
    }

    @Test
    public void powerThree() {
        assert INSTANCE.isPowerOfThree0(27) == true;
        assert INSTANCE.isPowerOfThree1(27) == true;
    }

    @Test
    public void countBits() {
        int[] res = INSTANCE.countBits(7);
        System.out.println(Arrays.toString(res));
    }

    @Test
    public void permute() {
        int[] nums = {1, 2, 3};
        System.out.println(INSTANCE.permute(nums));
    }

    @Test
    public void titleToNumber() {
        assert INSTANCE.titleToNumber("AB") == 28;
        assert INSTANCE.titleToNumber("AA") == 27;
        assert INSTANCE.titleToNumber("ZY") == 701;
        System.out.println(INSTANCE.titleToNumber("CFDGSXM"));
    }

    @Test
    public void romanToInt() {
        assert INSTANCE.romanToInt("MCMXCIV") == 1994;
        // http://bit.ly/32Zi9DF  有点迷 这道题目 不太合理
        System.out.println(INSTANCE.romanToInt("IIX"));
        System.out.println(INSTANCE.romanToInt("III"));
    }

    @Test
    public void pascalTriangle() {
        System.out.println(INSTANCE.generate(5));
    }

    @Test
    public void isHappy() {
        assert INSTANCE.isHappy(82) == true;
        assert INSTANCE.isHappy1(82) == true;
    }

    @Test
    public void getSum() {
        // 参考 [计算机加减乘除运算实现原理]()
        System.out.println(INSTANCE.getSum(759, 674));
    }

    @Test
    public void arithmetic() {
        assert INSTANCE.getSum(623, 469) == 1092;
        assert INSTANCE.substract(10, 3) == 7;
        assert INSTANCE.multiple(-3, -5) == 15;
        //     System.out.println(INSTANCE.divide2(25, 3));
        System.out.println(1 << 31 == Integer.MIN_VALUE);
        System.out.println(3 << 0);
        System.out.println(3 << 1);
        // 从左到右
        System.out.println(3 << 0 << 1);
        System.out.println(1 << 0 << 3);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
        System.out.println(INSTANCE.divide2(Integer.MIN_VALUE, 2));
        System.out.println(Math.abs(Integer.MIN_VALUE) > 0);
    }

    @Test
    public void longestCommonPrefix() {
        String[] strs = {"follow", "fox", "flower"};
        INSTANCE.longestCommonPrefix(strs);
    }

    @Test
    public void mySqrt() {
        System.out.println(INSTANCE.mySqrt(9));
        System.out.println(INSTANCE.mySqrt(8));
        System.out.println(INSTANCE.cubeRoot(27));
    }

    @Test
    public void strStr() {
        System.out.println(INSTANCE.strStr0("hello", "ll"));
        // ississ连续重复导致 无法识别
        System.out.println(INSTANCE.strStr0("mississippi", "issip"));
        System.out.println(INSTANCE.strStr1("mississippi", "issip"));
    }

    @Test(timeout = 6000)
    public void trailingZeroes() {
        assert INSTANCE.trailingZeroes0(6) == 1;
        // 代码有错,错在25 可以拆成5*5, 所以 5(1) 10(1) 15(1) 20(1) 25(2) 30(1)  = 7
        assert INSTANCE.trailingZeroes1(1808548329) == 452137076; // 需要耗时3s
        assert INSTANCE.trailingZeroes2(30) == 7;
        assert INSTANCE.trailingZeroes0(30) == 6;
    }

    @Test
    public void removeDuplicates() {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println(INSTANCE.removeDuplicates(nums));
        nums = new int[]{1, 2};
        System.out.println(INSTANCE.removeDuplicates(nums));
    }

    @Test
    public void hammingWeight() {
        assert INSTANCE.hammingWeight(5) == 2;
        assert INSTANCE.hammingWeight(-3) == 31;
    }

    @Test
    public void plusOne() {
        int[] nums = new int[]{9};
        INSTANCE.plusOne0(nums);
        INSTANCE.plusOne1(nums);
        nums = new int[]{9, 9, 9, 9};
        INSTANCE.plusOne0(nums);
        INSTANCE.plusOne1(nums);
        nums = new int[]{4, 3, 2, 9};
        INSTANCE.plusOne0(nums);
        INSTANCE.plusOne1(nums);
    }

    @Test
    public void reverseBits() {
        System.out.println(Integer.toBinaryString(13));
        int ret = INSTANCE.reverseBits(13);
        System.out.println(ret);
        System.out.println(Integer.toBinaryString(ret));
    }

    @Test
    public void isPalindromeStr() {
        System.out.println((32 & 31) == 0);
        INSTANCE.isPalindrome(" ");
        assert INSTANCE.isPalindrome("A man, a plan, a canal: Panama") == true;
        // 特殊case 0P ASCII 0 -->48,P-->80
        System.out.println(INSTANCE.isPalindrome("0P"));
    }

    @Test
    public void missingNumber() {
        int[] nums = new int[]{0, 1, 2, 4, 5, 6, 7, 3, 9};
        assert INSTANCE.missingNumber0(nums) == 8;
        assert INSTANCE.missingNumber1(nums) == 8;
        System.out.println(INSTANCE.missingNumber2(nums));
    }

    @Test
    public void firstUniqChar() {
        System.out.println(INSTANCE.firstUniqChar("leetcode"));
        System.out.println(INSTANCE.firstUniqChar1("bumblebuemrry"));
    }

    @Test
    public void rotate() {
        int[] nums = new int[]{3, 4, 5, 6, 7, 8, 9};
        INSTANCE.shift(nums);
        INSTANCE.rotate1(nums, 3);
        INSTANCE.rotate(nums, 3);
        INSTANCE.rotate2(nums, 3);
        //
        nums = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        INSTANCE.rotate3(nums, 3);
    }


    @Test
    public void maxProfit() {
        //  [一个通用方法团灭 6 道股票问题](http://bit.ly/333JDIm)
        System.out.println("=====参考 这位大佬的  http://bit.ly/333JDIm  ====");

        int[] nums = new int[]{7, 1, 3, 4, 9, 2, 6, 1, 8};
        assert INSTANCE.maxProfit1A(nums) == 8;
        assert INSTANCE.maxProfit1B_Good(nums) == 8;

        assert INSTANCE.maxProfit2A(nums) == 19;
        assert INSTANCE.maxProfit2B(nums) == 19;


        assert INSTANCE.maxProfit3B(nums, 6) == 19;
        assert INSTANCE.maxProfit4A(6, nums) == 19;
        assert INSTANCE.maxProfit4A(2, nums) == 15;

        assert INSTANCE.maxProfit5A(nums) == 15;
        assert INSTANCE.maxProfit5B(nums) == 15;
        assert INSTANCE.maxProfit6A(nums, 1) == 16;

    }

    @Test
    public void atoi() {
        System.out.println(INSTANCE.myAtoi("+-2"));
    }

    @Test
    public void isValidSudoku() {

        char[][] boards = {{'5', '3', '.', '.', '7', '.', '.', '.', '.'}, {'6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {'8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        INSTANCE.isValidSudoku(boards);
    }

    @Test
    public void myPow() {
        INSTANCE.myPow1(2.0, 11);
    }

    @Test
    public void findMissingPositive() {
        int[] nums = {1, 2, 0};
        System.out.println(INSTANCE.firstMissingPositive(nums));
        System.out.println(INSTANCE.firstMissingPositive_1(nums));
        nums = new int[]{3, 4, -1, 1};
        System.out.println(INSTANCE.firstMissingPositive(nums));
        System.out.println(INSTANCE.firstMissingPositive_1(nums));
        nums = new int[]{7, 8, 9, 11, 12};
        System.out.println(INSTANCE.firstMissingPositive(nums));
        System.out.println(INSTANCE.firstMissingPositive_1(nums));
    }

    @Test
    public void sort() {


        int[] nums = new int[]{17, 16, 14, 13, 11, 10, 7, 6, 18, 5, 2, 19, 4, 3, 8, 1, 21, 12, 15, 9, 31, 99, 65, 45, 77, 33, 29};
        //    System.out.println(INSTANCE.partition(nums, 0, nums.length - 1));
        System.out.println(Arrays.toString(nums));

/*
        Sort INSTANCE_SORT = new Sort();
        INSTANCE_SORT.bucketSort(nums, 8);

        System.out.println(Arrays.toString(nums));
        Integer[] nums1 = new Integer[]{17, 16, 14, 13, 11, 10, 7, 6, 18, 28};
        List<Integer> ints = Arrays.asList(nums1);
        System.out.println(ints);
        INSTANCE_SORT.insertSort(ints);
        System.out.println(ints);*/


    }


    @Test
    public void largestNumber() {
        int[] nums = new int[]{10, 2};
        assert INSTANCE.largestNumber(nums).equals("210");
    }

    @Test
    public void oddEvenList() {
        ListNode head = ListNode.builder().val(1).next(
                ListNode.builder().val(2).next(
                        ListNode.builder().val(3).next(
                                ListNode.builder().val(4).next(
                                        ListNode.builder().val(5).build()
                                ).build()).build()).build()
        ).build();

        INSTANCE.oddEvenList(head);
    }

    @Test
    public void calculate() {
        System.out.println("==计算器系列问题==");
        // corner case  最后一个数 2 运行截止
        String arithmetic = " 3+5 / 2 ";
        System.out.println(INSTANCE.calculate227(arithmetic));
    }

    @Test
    public void longestIncreasingPath() {
        int[][] matrix = new int[][]{
                {1, 2, 3}, {4, 5, 6}, {7, 8, 9},
        };
        INSTANCE.longestIncreasingPath(matrix);
        matrix = new int[][]{{9, 9, 4}, {6, 6, 8}, {2, 1, 1},};
        System.out.println("===========");
        INSTANCE.longestIncreasingPath1(matrix);
        System.out.println("=========");
        INSTANCE.longestIncreasingPath2(matrix);
    }

    @Test
    public void maxPointLine() {
        int[][] points = {{1, 1}, {2, 2}, {3, 3}, {2, 2}, {4, 4}, {5, 4}};
        assert INSTANCE.maxPoints(points) == 5;
    }

    @Test
    public void spiralOrder() {
        int[][] matrix = {{1, 2, 3, 4}, {6, 7, 8, 9}, {10, 11, 12, 13}, {14, 15, 16, 17}};
        System.out.println(INSTANCE.spiralOrder(matrix));
        matrix = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12},};
        System.out.println(INSTANCE.spiralOrder1(matrix));

    }

    @Test
    public void topKSeries() {
        System.out.println("===Top K系列问题===");
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        pq.add(1);
        pq.add(10);
        pq.add(9);
        pq.add(8);
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }

    @Test
    public void getSkylines() {
        int[][] building = new int[][]{};
        // INSTANCE.getSkyline(building);
    }

    @Test
    public void maxPQ() {
        // 插入元素 删除最大元素 均能做到 logN级别   算法4(P358)
        MaxPQ<Integer> pq = new MaxPQ<>();
        pq.insert(10);
        pq.insert(100);
        pq.insert(99);
        pq.insert(23);
        pq.insert(1);
        System.out.println(pq.max());
        System.out.println(pq.delMax());
        System.out.println(pq.max());
    }


    @Test
    public void medianFinder() {
        MedianFinder mf = new MedianFinder();
        mf.addNum(11);
        System.out.println(mf.findMedian());
        mf.addNum(13);
        System.out.println(mf.findMedian());
        mf.addNum(18);
        mf.addNum(9);
        mf.addNum(7);
        System.out.println(mf.findMedian());
        System.out.println(3 & 1);
        System.out.println(4 & 1);
    }

    @Test
    public void findCelebrity() {
        INSTANCE.findCelebrity(10);
    }

    @Test
    public void minimumSlidingWindow() {
        String s = "EBBANCF";
        String t = "ABC";
        System.out.println(INSTANCE.minimumWindow(s, t));
        System.out.println(INSTANCE.minimumWindow0(s, t));
    }

    @Test(timeout = 1000)
    public void findPeekElement() {
        int[] nums0 = {1, 2, 3, 1};
        int[] nums1 = {1, 2};
        int[] nums2 = {5, 4, 3, 2, 7};
        INSTANCE.findPeakElement0(nums0);
        // 越界
        //  INSTANCE.findPeekElement2(nums1);
        // 正确做法
        INSTANCE.findPeakElement3(nums0);
        // 无限循环
        INSTANCE.findPeakElement2(nums0);

    }

    @Test
    public void numDecode() {
        System.out.println(INSTANCE.numDecodings("2300192"));
        System.out.println(INSTANCE.numDecodings("230192"));
        System.out.println(INSTANCE.numDecodings("10192"));
        assert INSTANCE.numDecodings0("226") == INSTANCE.numDecodings("226");
    }

    @Test
    public void surroundedRegions() {
        char[][] board = new char[][]{{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'O', 'X', 'X'}};
        INSTANCE.solve(board);
    }
}
