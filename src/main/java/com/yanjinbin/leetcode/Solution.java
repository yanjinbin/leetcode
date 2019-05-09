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

    // # 206. Reverse Linked List 反转单链表,说 内存超了什么鬼阿
    public ListNode reverseList_0(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        ListNode cursor = head;
        while (cursor != null) {
            stack.push(head);
            cursor = cursor.next;
        }

        ListNode pop = stack.pop();
        ListNode tempNode = pop;
        while (!stack.isEmpty()) {
            tempNode.next = stack.pop();
            tempNode = tempNode.next;
        }
        return pop;
    }

    public ListNode reverseList(ListNode head) {
        /* recursive solution */
        return reverseListInt(head, null);
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

    // 思路是对的 但是依旧无法处理corner case阿
    public ListNode removeNthFromEnd_0(ListNode head, int n) {
        System.out.println("head:" + head.val + "n:" + n);
        // init ready
        ListNode predecessor = head;
        // 遍历n-1次 if n=1 corner case
        for (int i = 0; i < n - 1; i++) {
            predecessor = predecessor.next;
        }
        System.out.println("predecessor:" + predecessor.val);
        ListNode cursor = head;
        ListNode prev = null;
        if (predecessor.next == null) {
            return null;
        }
        // traverse
        while (predecessor.next != null) {
            prev = cursor;
            cursor = cursor.next;
            predecessor = predecessor.next;
        }
        // reconstruct
        prev.next = cursor.next;
        return head;
    }

    // leetcode上的处理让我们看到了更一般话的通俗规则处理.通过新增一个节点消除了corner case
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode start = new ListNode(0);
        ListNode slow = start, fast = start;
        slow.next = head;
        for (int i = 0; i < n + 2; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return start.next;
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
    

}
