package com.yangyimeng.homework.leetcode;


import java.util.*;

public class KillProcess {

    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        Map<Integer, List<Integer>> integerMap = new HashMap<>();
        for (int i = 0; i < pid.size(); i++) {
            List<Integer> childList = integerMap.get(ppid.get(i));
            if (childList == null) {
                childList = new ArrayList<>();
                integerMap.put(ppid.get(i), childList);
            }
            childList.add(pid.get(i));
        }
        List<Integer> result = new ArrayList<>();
        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(kill);
        while (!stack.isEmpty()) {
            int top = stack.pop();
            result.add(top);
            List<Integer> childList = integerMap.get(top);
            if (childList != null) {
                for (int child : childList) {
                    stack.push(child);
                }
            }
        }
        return result;
    }

    public static void main(String [] args) {

        System.out.println(new KillProcess().killProcess(Arrays.asList(1, 3, 10, 5),
                Arrays.asList(3, 0, 5, 3), 5));
    }

}
