package com.itheima.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

    public static void main(String[] args) {

        Map<String,String> map = new ConcurrentHashMap<>();
        map.put("name","zhangsan");
    }
}
