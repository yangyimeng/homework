package com.yangyimeng.homework.leetcode;


public class ReorderList {

    public ListNode reverseListNode(ListNode head) {
        ListNode pre = null;
        for (ListNode node = head; node != null;) {
            ListNode next = node.next;
            node.next = pre;
            pre = node;
            node = next;
        }
        return pre;
    }

    public void reorderList(ListNode head) {
        ListNode fast = head, slow = head;
        if (head == null || head.next == null || head.next.next == null)
            return ;

        while (fast != null && slow != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        fast = reverseListNode(slow.next);
        slow.next = null;
        slow = head;
        ListNode tail = null;
        while (slow != null && fast != null) {
            if (tail != null) {
                tail.next = slow;
            }
            ListNode tmp = slow.next;
            slow.next = fast;
            slow = tmp;
            tail = fast;
            fast = fast.next;
        }
        if (slow != null) {
            tail.next = slow;
        }
        if (fast != null) {
            tail.next = fast;
        }
    }

    public static void main(String [] args) {
        int [] nums = new int[] {1, 2};
        ListNode node = ListNode.fromArray(nums);
        new ReorderList().reorderList(node);
        System.out.println(node);
    }

}
