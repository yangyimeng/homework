package com.yangyimeng.homework.leetcode;


public class SwapNodesInPairs {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    private ListNode swap(ListNode node) {
        if (node == null || node.next == null)
            return node;
        ListNode next = node.next;
        node.next = next.next;
        next.next = node;
        return next;
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null) return head;
        ListNode pre = new ListNode(0);
        ListNode first = pre;
        first.next = head;
        for (ListNode node = head; node != null && node.next != null; ) {
            pre.next = swap(node);
            pre = node;
            node = node.next;
        }
        return first.next;
    }

}
