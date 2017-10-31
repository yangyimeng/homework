package com.yangyimeng.homework.leetcode;


public class DeleteNodeInABST {

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode treeNode = root.right;
                while (treeNode.left != null) {
                    treeNode = treeNode.left;
                }
                treeNode.left = root.left;
                return root.right;
            }
        }
        return root;
    }

}
