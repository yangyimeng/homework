package com.yangyimeng.homework.leetcode;


import java.util.LinkedList;

public class Pattern132 {

    class Data {
        int max;
        int min;
        public  Data(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }


    public boolean find132pattern(int[] nums) {
        LinkedList<Data> stack = new LinkedList<Data>();
        for (int num : nums) {
            if (stack.isEmpty() || stack.peek().min > num) {
                stack.push(new Data(num, num));
            } else if (stack.peek().min < num && stack.peek().max > num){
                return true;
            } else if (stack.peek().min < num){
                Data last = stack.pop();
                while (!stack.isEmpty() && stack.peek().max <= num) stack.pop();
                if (!stack.isEmpty() && stack.peek().min < num) return true;
                last.max = num;
                stack.push(last);
            }
        }
        return false;
    }


    public static void main(String [] args) {
        int [] nums = new int[] {3, 1, 4, 2};
        System.out.println(new Pattern132().find132pattern(nums));
    }


}
