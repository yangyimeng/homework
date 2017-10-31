package com.yangyimeng.homework.leetcode;


import java.util.Collections;
import java.util.PriorityQueue;

public class MedianFinder {

    PriorityQueue<Integer> bigHeap = new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> smallHeap = new PriorityQueue<>();
    boolean even = true;

    /** initialize your data structure here. */
    public MedianFinder() {

    }

    public void addNum(int num) {
        if (even) {
            if (!smallHeap.isEmpty() && num > smallHeap.peek()) {
                smallHeap.offer(num);
                bigHeap.offer(smallHeap.poll());
            } else {
                bigHeap.offer(num);
            }
        } else {
            if ((!smallHeap.isEmpty() && num >= smallHeap.peek()) || num >= bigHeap.peek()) {
                smallHeap.offer(num);
            } else {
                bigHeap.offer(num);
                smallHeap.offer(bigHeap.poll());
            }
        }
        even = !even;
    }

    public double findMedian() {
        if (even) {
            return (bigHeap.peek()  + smallHeap.peek()) / 2.0;
        } else {
            return bigHeap.peek();
        }
    }

    public static void main(String [] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        medianFinder.addNum(3);
        System.out.println();
    }

}
