package com.yangyimeng.homework.leetcode;

/***
 *
 *
 Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.

 For "(()", the longest valid parentheses substring is "()", which has length = 2.

 Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.

 Subscribe to see which companies asked this question.
 */
public class LongestValidParentheses {

    public int longestValidParentheses(String s) {
        int [] dp = new int[s.length()];
        char[] S = s.toCharArray();
        int max = 0;
        for (int i = 1; i < s.length(); i++) {
            if (S[i] == '(') {
                dp[i] = 0;
            } else {
                int start = i - 1 - dp[i - 1];
                if (start >= 0 && S[start] == '(') {
                    dp[i] = dp[i - 1] + 2;

                    if (start - 1 >= 0) {
                        dp[i] += dp[start - 1];
                    }
                }
                if (max < dp[i]) max = dp[i];
            }
        }
        return max;
    }

    public static void main(String [] args) {
        LongestValidParentheses longestValidParentheses = new LongestValidParentheses();
        System.out.println(longestValidParentheses.longestValidParentheses("()()))))()()("));

    }
}
