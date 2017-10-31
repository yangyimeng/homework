package com.yangyimeng.homework.leetcode;


import java.util.LinkedList;

public class MinimumAbsoluteDifferenceInBST {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    public int getMinimumDifference(TreeNode root) {
        LinkedList<TreeNode> treeNodes = new LinkedList<>();
        TreeNode tmp = root;
        while (tmp != null) {
            treeNodes.push(tmp);
            tmp = tmp.left;
        }

        int min = Integer.MAX_VALUE;
        TreeNode pre = null;
        while (!treeNodes.isEmpty()) {
            TreeNode node = treeNodes.pop();
            if (pre != null) {
                if (node.val - pre.val < min) {
                    min = node.val - pre.val;
                }
            }
            pre = node;
            tmp = node.right;
            while (tmp != null) {
                treeNodes.push(tmp);
                tmp = tmp.left;
            }
        }
        return min;
    }

    public static void main(String [] args) {

    }

}
