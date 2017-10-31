package com.yangyimeng.homework.leetcode;


public class ContinuousSubarraySum {

    public boolean checkSubarraySum(int[] nums, int k) {
        int [] dp = new int[nums.length];
        if (nums.length < 2 ) return false;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j <= i; j++) {
                dp[j] = dp[j] + nums[i];
                if (i != j && k != 0 && dp[j] % k == 0) return true;
                if (i != j && k == 0 && dp[j] == 0) return true;
            }
        }
        return false;
    }

    public static void main(String [] args) {
        ContinuousSubarraySum continuousSubarraySum = new ContinuousSubarraySum();
        int [] nums = new int[] {23, 2, 4, 6, 7};
        System.out.println(continuousSubarraySum.checkSubarraySum(nums, 9));
    }

}
