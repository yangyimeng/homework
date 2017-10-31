package com.yangyimeng.homework.leetcode;


import java.util.HashMap;
import java.util.Map;

public class ContainsDuplicateII {

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> integerMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (integerMap.containsKey(nums[i])) {
                if (i - integerMap.get(nums[i]) <= k) {
                    return true;
                }
            }
            integerMap.put(nums[i], i);
        }
        return false;
    }

    public static void main(String [] args) {
        int [] nums = new int[] {};
        int k = 0;
        System.out.println(new ContainsDuplicateII().containsNearbyDuplicate(nums, k));
    }


}
