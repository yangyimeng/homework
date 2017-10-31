package com.yangyimeng.homework.exam;


public class Student {

    private int number;
    private String name;

    public Student(int number, String name) {
        this.number = number;
        this.name = name;
        System.out.println(String.format("add %dst student, %s", number, name));
    }

    @Override
    public String toString() {
        return String.format("%d:%s", number, name);
    }
}
