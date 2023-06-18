package com.itheima.lock;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class SyncSample {

    private String name;
    private int age;

    private static Sample LOCK = new Sample();

    public static void main(String[] args) {

        System.out.println(VM.current().details());

        /*System.out.println("----------------------------------------------------------------------------");

        System.out.println(ClassLayout.parseClass(LOCK.class).toPrintable());

        System.out.println("----------------------------------------------------------------------------");

        System.out.println(ClassLayout.parseInstance(LOCK).toPrintable());

        System.out.println("----------------------------------------------------------------------------");

        */
        synchronized (LOCK) {
            System.out.println(ClassLayout.parseInstance(LOCK).toPrintable());
        }
    }

    static class Sample {

    }
}
