package com.yangyimeng.homework.leetcode;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class NextGreaterElementI {

    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        Map<Integer, Integer> greaterMapper = new HashMap<Integer, Integer>();
        LinkedList<Integer> stack = new LinkedList<Integer>();
        for (int num : nums) {
            while (!stack.isEmpty() && stack.peek() < num) {
                greaterMapper.put(stack.pop(), num);
            }
            stack.push(num);
        }

        int [] result = new int[findNums.length];
        for (int i = 0; i < findNums.length; i++) {
            result[i] = greaterMapper.getOrDefault(i, -1);
        }
        return result;
    }

    public static void main(String [] args) {
        int [] findNums = new int[] {2, 4};
        int [] nums = new int[] {1, 2, 3, 4};
        int [] results = new NextGreaterElementI().nextGreaterElement(findNums, nums);
        for (int result : results) {
            System.out.print(result + " ");
        }
        System.out.println();
    }

}
