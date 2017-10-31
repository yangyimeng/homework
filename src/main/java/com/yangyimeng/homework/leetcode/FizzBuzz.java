package com.yangyimeng.homework.leetcode;


import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {

    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            String tmp = i + "";
            if (i % 3 == 0 && i % 5 == 0) {
                tmp = "FizzBuzz";
            } else if (i % 5 == 0) {
                tmp = "Buzz";
            } else if (i % 3 == 0) {
                tmp = "Fizz";
            }
            result.add(tmp);
        }
        return result;
    }

}
