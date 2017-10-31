package com.yangyimeng.homework.leetcode;


public class SumOfTwoIntegers {

    public int getSum(int a, int b) {
        while (b != 0) {
            int carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }

    public static void main(String [] args) {
        System.out.println(new SumOfTwoIntegers().getSum(1, 2));
    }

}
