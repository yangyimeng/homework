package com.yangyimeng.homework.leetcode;


import java.util.LinkedList;

public class NextGreaterElementII {


    public int[] nextGreaterElements(int[] nums) {
        LinkedList<Integer> stack = new LinkedList<Integer>();
        int [] result = new int[nums.length];
        for (int i = 0; i < result.length; i++) result[i] = -1;
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                result[stack.pop()] = nums[i];
            }
            stack.push(i);
        }
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                result[stack.pop()] = nums[i];
            }
            stack.push(i);
        }
        return result;
    }


    public static void main(String [] args) {

    }

}
