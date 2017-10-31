package com.yangyimeng.homework.leetcode;


public class LongestIncreasingSubsequence {

    public int lengthOfLIS(int[] nums) {
        int [] dp = new int[nums.length];
        if (nums.length == 0)
            return 0;
        int max = 1;
        for (int i = 0; i < nums.length; i++) dp[i] = 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    if (dp[i] < dp[j] + 1) {
                        dp[i] = dp[j] + 1;
                    }
                }
            }
            if (max < dp[i]) {
                max = dp[i];
            }
        }
        return max;
    }

    public static void main(String [] args) {
        int [] nums = new int[] {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(new LongestIncreasingSubsequence().lengthOfLIS(nums));
    }

}
