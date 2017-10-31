package com.yangyimeng.homework.leetcode;


public class ConvertBSTToGreaterTree {

    public int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        sum = 0;
        travelRecurse(root);
        return root;
    }

    public void  travelRecurse(TreeNode node) {
        if (node == null) return;
        if (node.right != null) {
            travelRecurse(node.right);
        }
        int tmp = node.val;
        node.val = node.val + sum;
        sum = sum + tmp;
        if (node.left != null) {
            travelRecurse(node.left);
        }
    }



}
