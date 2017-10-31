package com.yangyimeng.homework.leetcode;


import java.util.*;

public class TopKFrequentElements {

    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> integerHashMap = new HashMap<>();
        for (int num : nums) {
            integerHashMap.put(num, integerHashMap.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> priorityQueue =
                new PriorityQueue<>((a, b) -> (b.getValue() - a.getValue()));

        for (Map.Entry<Integer, Integer> entry : integerHashMap.entrySet()) {
            priorityQueue.add(entry);
        }

        List<Integer> integers = new ArrayList<>();
        while (integers.size() < k) {
            integers.add(priorityQueue.poll().getKey());
        }

        return integers;
    }



}
