package com.itheima.other;

public class VolatileDemo {

    private static boolean flag = false;

    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{

            synchronized (lock){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(!flag){
                }
                System.out.println("第一个线程执行完毕...");
            }
        }).start();

        Thread.sleep(100);

        new Thread(()->{
            synchronized (lock){
                flag = true;//思考：第一个线程会不会跳槽while循环，输出System.out.println("第一个线程执行完毕...");
                System.out.println("第二线程执行完毕...");
            }

        }).start();

    }

}