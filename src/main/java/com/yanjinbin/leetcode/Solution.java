package com.yanjinbin.leetcode;

import java.util.*;

/**
 * top-100-liked-questions
 * https://leetcode.com/problemset/top-100-liked-questions/
 */
public class Solution {

    public static void demo() {
        System.out.println("测试");
    }

    // #1
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }


    // #2  两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1;
        ListNode q = l2;
        ListNode curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = p != null ? p.val : 0;
            int y = q != null ? q.val : 0;
            int sum = x + y + carry;
            // 更新carry
            carry = sum / 10;

            curr.next = new ListNode(sum % 10);
            // self update
            curr = curr.next;
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }

        // 某位如果超过10 需要在循环外 更新一次
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        // dummyhead 是0 所以是返回他的下一个节点,作为头部节点
        return dummyHead.next;
    }


    public static ListNode reverseListInt(ListNode head, ListNode newHead) {
        if (head == null) {
            return newHead;
        }
        ListNode next = head.next;
        head.next = newHead;
        return reverseListInt(next, head);
    }

    // 21. Merge Two Sorted Lists
    public ListNode mergeTwoLists_0(ListNode l1, ListNode l2) {
        ListNode ret = l1.val > l2.val ? l2 : l1;
        ListNode cursor;
        // 这样想 ,其实有问题的 l1 l2长短不一的时候. 就会NPE阿
        while (l1 != null || l2 != null) {
            if (l1.val > l2.val) {
                cursor = l2;
                cursor.next = l1;
            } else {
                cursor = l1;
                cursor.next = l2;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return ret;
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    // 141. Linked List Cycle
    // todo 如何证明呢
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    // #148 归并排序
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        //seperate the list into two parts
        //Track the last node of first list and point the end to null
        ListNode prev = null;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        prev.next = null;
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);

        return merge(l1, l2);
    }

    public ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode l = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                l.next = l1;
                l1 = l1.next;
            } else {
                l.next = l2;
                l2 = l2.next;
            }
            l = l.next;
        }

        while (l1 != null) {
            l.next = l1;
            l1 = l1.next;
            l = l.next;
        }
        while (l2 != null) {
            l.next = l2;
            l2 = l2.next;
            l = l.next;
        }
        System.out.println("dummy:" + dummy.val + "next:" + dummy.next);
        return dummy.next;
    }


    private ListNode merge_1(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val <= l2.val) {
            l1.next = this.merge_1(l1.next, l2);
            return l1;
        } else {
            l2.next = this.merge_1(l1, l2.next);
            return l2;
        }
    }

    // 234 Palindrome Linked List 回文单链表
    public boolean isPalindrome(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            // prevent fast.next is null
            fast = fast.next.next;
        }
        if (fast != null) {
            slow = slow.next;
        }
        ListNode newHead = reverseSingleLinkedList(slow);
        while (newHead != null) {
            if (head.val != newHead.val) {
                return false;
            }
        }
        return true;
    }

    // 反转单链表
    public ListNode reverseSingleLinkedList(ListNode head) {
        ListNode prev = null, next = null;
        ListNode cursor = head;
        while (cursor != null) {
            next = cursor.next;
            cursor.next = prev;
            prev = cursor;
            cursor = next;
        }
        // 这里返回prev 是因为while循环跳出之后cursor = null 说明 prev is tail node了.
        return prev;
    }

    //这道题还有一种特别巧妙的方法，虽然题目中强调了链表中不存在环，
    // 但是我们可以用环的思想来做，我们让两条链表分别从各自的开头开始往后遍历，
    // 当其中一条遍历到末尾时，我们跳到另一个条链表的开头继续遍历。两个指针最终会相等，
    // 而且只有两种情况，
    // 一种情况是在交点处相遇，
    // 另一种情况是在各自的末尾的空节点处相等。
    // 为什么一定会相等呢，因为两个指针走过的路程相同，
    // 是两个链表的长度之和，所以一定会相等。这个思路真的很巧妙，而且更重要的是代码写起来特别的简洁，参见代码如下：

    //  [160]. Intersection of Two Linked Lists
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode a = headA, b = headB;
        // 两个指针最终会相等，
        //    // 而且只有两种情况，
        //    // 一种情况是在交点处相遇，
        //    // 另一种情况是在各自的末尾的空节点处相等。
        while (a != b) {
            a = (a != null) ? a.next : headB;
            b = (b != null) ? b.next : headA;
        }
        return a;
    }


    // lt 3 无重复最长子串
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int ans = 0, i = 0, j = 0;
        Set<Character> set = new HashSet<>();
        while (i < n && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j));
                j++;
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i));
                i++;
            }
        }
        return ans;
    }

    // 121. 买卖股票的最佳时机
    public int maxProfit(int prices[]) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if (prices[i] - minPrice > maxProfit) {
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }

    // leetcode 15 3sum
    // 解决重复个数问题 1 2 3
    // 3的化就是0
    // 2的化 就是x+x+y=0 or (x+y+y=0,不用考虑 )
    public List<List<Integer>> threeSum(int[] nums) {
        List ret = new ArrayList();
        Arrays.sort(nums);
        if (nums[0] > 0) {
            return Collections.EMPTY_LIST;
        }
        for (int num : nums) {
            System.out.println(num);
        }
        int cursor = 0;
        while (cursor <= nums.length - 3) {

            int headcursor = cursor + 1;
            int tailcursor = nums.length - 1;
            int target = 0 - nums[cursor];
            // corner case  if cursor value = head value then cursor++
            System.out.println(nums[cursor] + "\t" + nums[headcursor] + "\t" + nums[tailcursor]);
            if (nums[headcursor] == nums[cursor]) {
                cursor++;
                headcursor++;
                System.out.println("===update===");
                System.out.println(nums[cursor] + "\t" + nums[headcursor] + "\t" + nums[tailcursor]);
            }

            while (headcursor < tailcursor) {
                int sum = nums[headcursor] + nums[tailcursor];
                if (sum > target) {
                    tailcursor--;
                } else if (sum < target) {
                    headcursor++;
                } else {
                    List<Integer> elements = new ArrayList();
                    elements.add(nums[headcursor]);
                    elements.add(nums[tailcursor]);
                    elements.add(nums[cursor]);
                    ret.add(elements);
                    headcursor++;
                    tailcursor--;
                }
            }
            cursor++;
        }
        return ret;
    }

    // #19. 删除链表的倒数第N个节点
    // 无法处理 corner case,
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
    }

    //// #19. 删除链表的倒数第N个节点, 完美处理corner case
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

    //  TODO 需要深刻理解 [LeetCode] 4. Median of Two Sorted Arrays 两个有序数组的中位数 http://bit.ly/2ROgk7B
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;
        return (findKth(nums1, 0, nums2, 0, left) + findKth(nums1, 0, nums2, 0, right)) / 2.0;
    }

    public int findKth(int[] nums1, int i, int[] nums2, int j, int k) {
        if (i >= nums1.length) return nums2[j + k - 1];
        if (j >= nums2.length) return nums1[i + k - 1];
        if (k == 1) return Math.min(nums1[i], nums2[j]);
        int midVal1 = (i + k / 2 - 1 < nums1.length) ? nums1[i + k / 2 - 1] : Integer.MAX_VALUE;
        int midVal2 = (j + k / 2 - 1 < nums2.length) ? nums2[j + k / 2 - 1] : Integer.MAX_VALUE;
        if (midVal1 < midVal2) {
            return findKth(nums1, i + k / 2, nums2, j, k - k / 2);
        } else {
            return findKth(nums1, i, nums2, j + k / 2, k - k / 2);
        }
    }

    //  最长回文字符串 5. Longest Palindromic Substring
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int end = 0, start = 0;
        for (int i = 0; i < s.length(); i++) {
            // 处理奇偶问题
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > (end - start)) {
                //
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L > 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R-L-1;
    }
}
