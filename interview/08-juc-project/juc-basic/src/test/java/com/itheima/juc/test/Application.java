package com.itheima.juc.test;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Application {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(10);

        CountDownLatch countDownLatch = new CountDownLatch(10);

    }
}
