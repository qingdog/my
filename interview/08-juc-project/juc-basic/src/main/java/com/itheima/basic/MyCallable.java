package com.itheima.basic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        return "ok";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建MyCallable对象
        MyCallable mc = new MyCallable() ;
        // 创建FutureTask
        FutureTask<String> ft = new FutureTask<String>(mc) ;
        // 创建Thread对象
        Thread t1 = new Thread(ft) ;
        Thread t2 = new Thread(ft) ;
        // 调用start方法启动线程
        t1.start();
        // 调用ft的get方法获取执行结果
        String result = ft.get();
        // 输出
        System.out.println(result);
    }
}