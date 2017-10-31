package com.yangyimeng.homework.leetcode;


import java.util.LinkedList;
import java.util.Queue;

public class MinimumDepthOfBinaryTree {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        TreeNode guard = new TreeNode(-1);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(guard);
        int level = 1;
        int min = 0;
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur == guard) {
                if (queue.isEmpty()) break;
                level++;
                queue.add(guard);
            } else {
                if (cur.right == null && cur.left == null) {
                    min = level;
                    break;
                }
                if (cur.left != null) queue.add(cur.left);
                if (cur.right != null) queue.add(cur.right);
            }
        }
        return min;
    }
}
