package com.yangyimeng.homework.exam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoLoadStudent {

    private Student student;

    @Autowired
    public AutoLoadStudent(Student student) {
        this.student = student;
        System.out.println(String.format("auto load student %s", student));
        Runnable mytask = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        System.out.printf("Hello World , I'm thread %d\n", Thread.currentThread().getId());
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        new Thread(mytask).start();
    }

}
