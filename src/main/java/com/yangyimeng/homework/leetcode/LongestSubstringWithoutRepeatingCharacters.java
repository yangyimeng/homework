package com.yangyimeng.homework.leetcode;


import java.util.BitSet;

public class LongestSubstringWithoutRepeatingCharacters {

    public int lengthOfLongestSubstring(String s) {
        BitSet bitSet = new BitSet();
        char [] data = s.toCharArray();
        if (data.length == 0) {
            return 0;
        }
        int i = 0, j = 0;
        int max = 1;
        while (i < data.length && j < data.length && i <= j) {
            if (bitSet.get(data[j])) {
                for (int k = i; k < j; k++) {
                    if (data[k] != data[j]) {
                        bitSet.clear(data[k]);
                    } else {
                        i = k + 1;
                        break;
                    }
                }

            } else {
                bitSet.set(data[j]);
            }
            if (j - i + 1 > max) {
                max = j - i + 1;
            }
            j++;
        }
        return max;
    }

    public static void main(String [] args) {
        System.out.println(new LongestSubstringWithoutRepeatingCharacters().lengthOfLongestSubstring("abcabcbb"));
    }

}
