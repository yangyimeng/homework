package com.yangyimeng.homework.leetcode;


public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) { val = x; }
    @Override
    public String toString() {
        return Integer.toString(val) + " " + next;
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
}
