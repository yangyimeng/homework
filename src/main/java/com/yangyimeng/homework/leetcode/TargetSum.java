package com.yangyimeng.homework.leetcode;


public class TargetSum {

    int total = 0;

    public void findTargetSumRecursive(int [] nums, int S, int level, int tmpSum) {
        if (level >= nums.length) {
            if (tmpSum == S)
                total++;
            return;
        }
        findTargetSumRecursive(nums, S, level + 1, tmpSum + nums[level]);
        findTargetSumRecursive(nums, S, level + 1, tmpSum - nums[level]);
    }

    public int findTargetSumWays(int[] nums, int S) {
        total = 0;
        findTargetSumRecursive(nums, S, 0, 0);
        return total;
    }


    public static void main(String [] args) {
        int [] data = new int[] {1, 1, 1, 1, 1};
        System.out.println(new TargetSum().findTargetSumWays(data, 3));
    }

}
