package com.yangyimeng.homework.leetcode;


import java.util.LinkedList;

public class MinStack {

    LinkedList<Integer> stack;
    LinkedList<Integer> min;


    /** initialize your data structure here. */
    public MinStack() {
        stack = new LinkedList<Integer>();
        min = new LinkedList<Integer>();
    }

    public void push(int x) {
        stack.push(x);
        if (min.isEmpty()) {
            min.push(x);
        } else {
            if (min.peek() >= x) {
                min.push(x);
            } else {
                min.push(min.peek());
            }
        }
    }

    public void pop() {
        stack.pop();
        min.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min.peek();
    }
}
