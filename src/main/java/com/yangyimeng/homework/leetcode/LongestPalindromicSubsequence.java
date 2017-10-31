package com.yangyimeng.homework.leetcode;


public class LongestPalindromicSubsequence {

    public int longestPalindromeSubseq(String s) {
        char [] s1 = s.toCharArray();
        char [] s2 = new char[s1.length];
        for (int i = 0; i < s1.length; i ++) {
            s2[i] = s1[s1.length - i - 1];
        }

        int [][] dp = new int[s1.length + 1][s1.length + 1];

        for (int i = 1; i <= s1.length; i++) {
            for (int j = 1; j <= s1.length; j++) {
                if (s1[i - 1] == s2[j - 1]) {
                    dp[i][j] = dp[i -1][j -1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[s1.length][s1.length];
    }


    public static void main(String [] args) {
        System.out.println(new LongestPalindromicSubsequence().longestPalindromeSubseq("bbbabbab"));
    }

}
