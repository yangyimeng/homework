package com.yangyimeng.homework.leetcode;


import java.util.LinkedList;

public class CourseSchedule {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int [] indegree = new int[numCourses];
        int [][] graph = new int[numCourses][numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            int pre = prerequisites[i][1];
            int cur = prerequisites[i][0];
            if (graph[pre][cur] == 0) {
                indegree[cur]++;
            }
            graph[pre][cur]++;
        }

        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                stack.push(i);
            }
        }

        int num = 0;

        while (!stack.isEmpty()) {
            int cur = stack.pop();
            num++;
            for (int i = 0; i < numCourses; i++) {
                if (i != cur) {
                    if (graph[cur][i] != 0) {
                        indegree[i]--;
                        if (indegree[i] == 0) {
                            stack.push(i);
                        }
                    }
                }
            }
        }

        return num == numCourses;
    }

    public static void main(String [] args) {

    }

}
