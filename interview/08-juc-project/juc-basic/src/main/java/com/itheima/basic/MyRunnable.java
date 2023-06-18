package com.itheima.basic;

public class MyRunnable implements Runnable {

    @Override
    public void run(){
        System.out.println("MyRunnable...run...");
    }

    public static void main(String[] args) {

        // 创建MyRunnable对象
        MyRunnable mr = new MyRunnable();

        // 创建Thread对象
        Thread t1 = new Thread(mr);
        Thread t2 = new Thread(mr);

        // 调用start方法启动线程
        t1.start();
        t2.start();

    }
}