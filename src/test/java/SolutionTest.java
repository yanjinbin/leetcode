import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.yanjinbin.leetcode.ListNode;
import com.yanjinbin.leetcode.Solution;
import org.junit.Before;
import org.junit.Test;

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
    public void misc() {
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


}
