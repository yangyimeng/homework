package com.yangyimeng.homework.leetcode;


public class IncreasingTripletSubsequence {

    public boolean increasingTriplet(int[] nums) {
        int max = Integer.MAX_VALUE, min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= min) {
                min = nums[i];
            } else if (nums[i] <= max) {
                max = nums[i];
            } else return true;
        }
        return false;
    }

    public static void main(String [] args) {
        int [] nums = new int[] {1, 2, 3, 4, 5};
        System.out.println(new IncreasingTripletSubsequence().increasingTriplet(nums));
    }

}
