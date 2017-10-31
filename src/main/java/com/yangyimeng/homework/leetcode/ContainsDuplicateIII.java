package com.yangyimeng.homework.leetcode;

import java.util.TreeSet;

/***
 *
 *
 Given an array of integers, find out whether there are two distinct indices
 i and j in the array such that the absolute difference between nums[i] and nums[j]
 is at most t and the absolute difference between i and j is at most k.
 | nums[j] - nums[i] | <= t
 | j - i | <= k
 */

public class ContainsDuplicateIII {

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return false;
        }
        TreeSet<Long> treeSet = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            Long floor = treeSet.floor(  ((long) nums[i] + t) );
            Long ceiling = treeSet.ceiling( ((long) nums[i] - t) );
            if ((floor != null && floor >= (long) nums[i]) || (ceiling != null && ceiling <= (long) nums[i]))
                return true;
            treeSet.add((long) nums[i]);
            if (i >= k)
                treeSet.remove((long) nums[i - k]);
        }
        return false;
    }

    public static void main(String [] args) {
        int [] nums = new int[] {-2147483648,-2147483647};
        int k = 3, t = 3;
        System.out.print(new ContainsDuplicateIII().containsNearbyAlmostDuplicate(nums, k, t));
    }


}
