package com.yangyimeng.homework.leetcode;


import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicate {

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> integerSet = new HashSet<>();
        for (int num :nums) {
            if (integerSet.contains(num)) {
                return true;
            }
            integerSet.add(num);
        }
        return false;
    }

    public static void main(String [] args) {

    }

}
