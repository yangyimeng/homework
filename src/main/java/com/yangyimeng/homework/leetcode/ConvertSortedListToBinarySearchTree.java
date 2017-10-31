package com.yangyimeng.homework.leetcode;


public class ConvertSortedListToBinarySearchTree {

    public TreeNode sortedListToBST(ListNode head) {
        return toBST(head);
    }

    public TreeNode toBST(ListNode node) {
        if (node == null) {
            return null;
        }
        if (node.next == null || node.next.next == null) {
            TreeNode treeNode = new TreeNode(node.val);
            if (node.next != null) {
                TreeNode right = new TreeNode(node.next.val);
                treeNode.right = right;
            }
            return treeNode;
        }
        ListNode slow = node;
        ListNode fast = node;
        ListNode pre = null;
        while (slow != null && fast != null && fast.next != null && fast.next.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        pre.next = null;
        TreeNode treeNode = new TreeNode(slow.val);
        treeNode.left = toBST(node);
        treeNode.right = toBST(slow.next);
        return treeNode;
    }

}
