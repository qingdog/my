package com.itheima.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolDemo implements Runnable {

    public static void main(String[] args) {

        //创建阻塞队列
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);

        ArrayBlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(5);


        //创建工厂
        ThreadFactory threadFactory = new ThreadFactory() {

            AtomicInteger atomicInteger = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                //创建线程把任务传递进去
                Thread thread = new Thread(r);
                //设置线程名称
                thread.setName("MyThread: "+atomicInteger.getAndIncrement());
                return thread;
            }
        };
        ThreadPoolExecutor pool  = new ThreadPoolExecutor(
                2,
                5,
                1,
                TimeUnit.SECONDS,
                arrayBlockingQueue,
                threadFactory,
                new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 100; i++) {
            pool.submit(new ThreadPoolDemo());
        }

        pool.shutdown();
    }

    @Override
    public void run() {
        //执行业务
        System.out.println(Thread.currentThread().getName()+" 进来了");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"出去了");
    }
}