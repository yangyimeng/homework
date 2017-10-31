package com.yangyimeng.homework.leetcode;


public class OddEvenLinkedList {

    public ListNode oddEvenList(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (slow != null && fast!= null && fast.next != null && fast.next.next != null) {
            ListNode tmp = fast.next.next;
            fast.next.next = tmp.next;
            tmp.next = slow.next;
            fast = fast.next;
            slow.next = tmp;
            slow = tmp;

        }
        return head;
    }


    public static void main(String [] args) {
        int [] nums = new int[] {1, 2, 3, 4, 5};
        System.out.println(new OddEvenLinkedList().oddEvenList(ListNode.fromArray(nums)));
    }

}
