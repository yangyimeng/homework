package com.yangyimeng.homework.exam;


import java.util.Random;
import java.util.concurrent.*;

public class CallableAndFuture {


    public void testThread() {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(10);
            }
        };

        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
        new Thread(futureTask).start();
        try {
            Integer value = futureTask.get();
            System.out.println(value);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            System.out.println("testThread Over");
        }
    }

    public void testExcutors() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(10);
            }
        });

        try {
            System.out.println(future.get());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("testExcutors Over");
        }
    }

    public static void main(String [] args) {
        CallableAndFuture callableAndFuture = new CallableAndFuture();
        callableAndFuture.testExcutors();
    }



}
