package com.yangyimeng.homework.leetcode;


import java.util.PriorityQueue;

public class KthSmallestElementInASortedMatrix {

    class Tuple implements Comparable<Tuple>{
        int x;
        int y;
        int value;
        Tuple(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public int compareTo(Tuple tuple) {
            return this.value - tuple.value;
        }
    }

    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Tuple> priorityQueue = new PriorityQueue<>();
        int n = matrix.length;
        for (int i = 0; i < n; i++) priorityQueue.offer(new Tuple(0, i, matrix[0][i]));
        while (k > 1 && !priorityQueue.isEmpty()) {
            k--;
            Tuple tuple = priorityQueue.poll();
            if (tuple.x == n - 1) continue;
            priorityQueue.offer(new Tuple(tuple.x + 1, tuple.y, matrix[tuple.x + 1][tuple.y]));
        }
        return priorityQueue.poll().value;
    }

    public static void main(String [] args) {
        int [] [] matrix = new int[][] {{1, 5, 9}, {10, 11, 13}, {12, 13, 15}};
        int k = 8;
        System.out.println(new KthSmallestElementInASortedMatrix().kthSmallest(matrix, k));
    }

}
