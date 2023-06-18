package com.itheima.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    static Lock  lock  = new ReentrantLock();

    public static void method1(){
        try{
            lock.lock();
            method2();
        }finally{
            lock.unlock();
        }
    }

    public static void method2(){
        try{
            lock.lock();
        }finally{
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        method1();
    }
}
