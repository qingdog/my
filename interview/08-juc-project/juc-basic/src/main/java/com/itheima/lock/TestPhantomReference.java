package com.itheima.lock;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

public class TestPhantomReference {

    public static void main(String[] args) throws InterruptedException {

        ReferenceQueue<String> queue = new ReferenceQueue<>();
        List<MyResourse> list = new ArrayList<>();
        list.add(new MyResourse(new String("a"),queue));
        list.add(new MyResourse("b",queue));
        list.add(new MyResourse(new String("c"),queue));

        System.gc();

        Thread.sleep(100);

        Object ref ;
        while ((ref =queue.poll()) != null){
            if(ref instanceof MyResourse){
                MyResourse.clean();
            }
        }

    }

    static class MyResourse extends PhantomReference<String> {

        public MyResourse(String referent, ReferenceQueue<? super  String> q){
            super(referent,q);
        }

        //释放外部资源的方法
        public static void clean(){
            System.out.println("clean");
        }
    }
}
