package com.yangyimeng.homework.exam;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassPathXmlApplicationContextText {

    public static void main(String [] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classPathXmlApplicationContext-example.xml");
        ClassLoader classLoader = applicationContext.getClassLoader();
        System.out.println();

    }
}
