package com.yangyimeng.homework.leetcode;


import java.util.BitSet;

public class LongestPalindromicSubstring {

    public String longestPalindrome(String s) {
        boolean [][] dp = new boolean[s.length()][s.length()];
        char [] data = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }
        int index = 0;
        int max = 1;
        for (int len = 2; len <= data.length; len++) {
            for (int i = 0; i < data.length - len + 1; i++) {
                int j = i + len - 1;
                if (j > data.length) continue;
                if (data[i] == data[j] && (j == i + 1 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    if (len > max) {
                        index = i;
                        max = len;
                    }
                }
            }
        }
        char [] result = new char[max];
        for (int i = 0; i < max; i++) {
            result[i] = data[index + i];
        }
        return new String(result);
    }

    public static void main(String [] args) {
        System.out.println(new LongestPalindromicSubstring().longestPalindrome("aaaa"));
    }

}
