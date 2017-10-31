package com.yangyimeng.homework.leetcode;


public class PalindromeLinkedList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static ListNode fromArray(int []  nums) {
        ListNode head = null;
        ListNode pre = null;
        for (int num : nums) {
            if (head == null) {
                head = new ListNode(num);
                pre = head;
            } else {
                pre.next = new ListNode(num);
                pre = pre.next;
            }
        }
        return head;
    }


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


    public boolean isPalindrome(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && slow != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if (head == null || head.next == null)
            return true;
        fast = reverseListNode(slow.next);
        slow.next = null;
        slow = head;

        while (slow != null && fast != null) {
            if (slow.val == fast.val) {
                slow = slow.next;
                fast = fast.next;
            }  else {
                return false;
            }
        }
        return true;
    }

    public static void main(String [] args) {
        int [] nums = new int[] {0, 0};
        System.out.println(new PalindromeLinkedList().isPalindrome(fromArray(nums)));
    }

}
