package com.yangyimeng.homework.leetcode;


import java.util.LinkedList;

public class SubtreeOfAnotherTree {
    //3 (4 (1()()) (2 (0()()) () ) ) (5()())
    public boolean isSubtree(TreeNode s, TreeNode t) {
        String sString = getSubTreeString(s);
        String tString = getSubTreeString(t);
        return sString.contains(tString);
    }

    public String getSubTreeString(TreeNode treeNode) {
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        StringBuilder stringBuilder = new StringBuilder();
        TreeNode guard = new TreeNode(0);
        while (treeNode != null) {
            stack.push(treeNode);
            stringBuilder.append("." + treeNode.val);
            stringBuilder.append("(");
            treeNode = treeNode.left;
        }
        while (!stack.isEmpty()) {
            TreeNode top = stack.pop();
            stringBuilder.append(")");
            if (top != guard) {
                if (top.right != null) {
                    treeNode = top.right;
                    stringBuilder.append("(");
                    stack.push(guard);
                    while (treeNode != null) {
                        stack.push(treeNode);
                        stringBuilder.append("." + treeNode.val);
                        stringBuilder.append("(");
                        treeNode = treeNode.left;
                    }
                } else {
                    stringBuilder.append("()");
                }
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String [] args) {
        TreeNode treeNode = new TreeNode(3);
        treeNode.right = new TreeNode(5);
        treeNode.left = new TreeNode(4);
        treeNode.left.left = new TreeNode(1);
        treeNode.left.right = new TreeNode(2);
        treeNode.left.right.left = new TreeNode(0);

        TreeNode treeNode1 = new TreeNode(4);
        treeNode1.left = new TreeNode(1);
        treeNode1.right = new TreeNode(2);

        TreeNode a = new TreeNode(12);
        TreeNode b = new TreeNode(2);

        System.out.println(new SubtreeOfAnotherTree().isSubtree(a, b));
    }
}
