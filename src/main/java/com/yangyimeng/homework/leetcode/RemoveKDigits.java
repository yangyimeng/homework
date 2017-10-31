package com.yangyimeng.homework.leetcode;


import java.util.LinkedList;

public class RemoveKDigits {

    public String removeKdigits(String num, int k) {
        LinkedList<Character> characters = new LinkedList<Character>();
        for (char i :  num.toCharArray()) {
            while (k > 0 && !characters.isEmpty() && characters.peek().compareTo(i) > 0) {
                characters.pop();
                k--;
            }
            characters.push(i);
        }
        while (k > 0 && !characters.isEmpty()) {
            k--;
            characters.pop();
        }
        if (characters.isEmpty()) return "0";

        StringBuilder stringBuilder = new StringBuilder();
        boolean skipZero = true;
        while (!characters.isEmpty()) {
            Character character = characters.pollLast();
            if (character.equals('0') && skipZero) {
                continue;
            }
            skipZero = false;
            stringBuilder.append(character);
        }
        if (stringBuilder.length() == 0) {
            stringBuilder.append('0');
        }
        return stringBuilder.toString();
    }

    public static void main(String [] args) {
        String num = "10200";
        int k = 1;
        System.out.println(new RemoveKDigits().removeKdigits(num, k));
    }

}
