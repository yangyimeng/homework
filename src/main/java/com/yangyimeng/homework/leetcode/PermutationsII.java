package com.yangyimeng.homework.leetcode;


import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/***
 *
 *
 Given a collection of numbers that might contain duplicates, return all possible unique permutations.
 For example,
 [1,1,2] have the following unique permutations:
 [
     [1,1,2],
     [1,2,1],
     [2,1,1]
 ]
 *
 *
 */

public class PermutationsII {


    public void solve_permute(int [] nums, int level, List<List<Integer>> result) {
        int length = nums.length;
        if (level == length - 1) {
            List<Integer> itemList = new ArrayList<Integer>();
            for (int i = 0; i < length; i++) {
                itemList.add(nums[i]);
                System.out.print(nums[i]);
            }
            System.out.println();
            result.add(itemList);
            return;
        }
        BitSet posBitSet = new BitSet();
        BitSet negBitSet = new BitSet();
        for (int k = level; k < length; k++) {
            if (nums[k] >= 0 && posBitSet.get(nums[k])) continue;
            if (nums[k] < 0 && negBitSet.get(-nums[k])) continue;
            if (level != k && nums[level] == nums[k]) continue;
            int tmp = nums[level];
            nums[level] = nums[k];
            nums[k] = tmp;
            if (nums[level] >= 0) posBitSet.set(nums[level]);
            if (nums[level] < 0) negBitSet.set(-nums[level]);
            solve_permute(nums, level + 1, result);
            tmp = nums[level];
            nums[level] = nums[k];
            nums[k] = tmp;
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        solve_permute(nums, 0, result);
        return result;
    }

    public static void main(String [] args) {
        int [] nums = new int[] {2,2,1,1};
        PermutationsII permutationsII = new PermutationsII();
        permutationsII.permuteUnique(nums);
    }

}
