package com.itheima.basic;

public class StartAndRunTest {

    public static void main(String[] args) {

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                System.out.println("running....");
            }
        };
//        t1.run();
//        t1.run();
//        t1.run();
        t1.start();
        t1.start();
        t1.start();
    }
}
