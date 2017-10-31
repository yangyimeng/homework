package com.yangyimeng.homework.leetcode;


public class RemoveNthNodeFromEndOfList {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) { val = x; }

        @Override
        public String toString() {
            return Integer.toString(val) + " " + next;
        }
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

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head;
        ListNode second = head;
        int step = 0;
        while (first != null) {
            step++;
            if (step == n)
                break;
            first = first.next;
        }
        if (first == null) return head;
        first = first.next;
        ListNode pre = null;
        while (second != null && first != null) {
            pre = second;
            second = second.next;
            first = first.next;
        }
        if (pre == null) {
            head = head.next;
        } else {
            pre.next = second.next;
        }
        return head;
    }


    public static void main(String [] args) {
        int [] nums = new int[] {1, 2, 3, 4, 5};
        int n = 2;
        System.out.println(new RemoveNthNodeFromEndOfList().removeNthFromEnd(fromArray(nums), n));
    }

}
