package com.itheima.basic;

public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("MyThread...run...");
    }


    public static void main(String[] args) {

        // 创建MyThread对象
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();

        // 调用start方法启动线程
        t1.start();
        t2.start();

    }

}