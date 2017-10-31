package com.yangyimeng.homework.leetcode;


public class BinaryTreeTilt {


    public int sum = 0;


    public int findTiltRecurse(TreeNode root) {

        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return root.val;
        }
        int left = root.left == null ? 0 : findTiltRecurse(root.left);
        int right = root.right == null ? 0 : findTiltRecurse(root.right);
        sum = sum + Math.abs(right - left);
        return left + right + root.val;
    }

    public int findTilt(TreeNode root) {
        sum = 0;
        findTiltRecurse(root);
        return sum;
    }





}
