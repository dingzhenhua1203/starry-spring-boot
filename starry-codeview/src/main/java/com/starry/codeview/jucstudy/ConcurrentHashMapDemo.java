package com.starry.codeview.jucstudy;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {
    static ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap();

    public static void main(String[] args) {
        concurrentHashMap.put(1, "A"); // 已存在会覆盖
        concurrentHashMap.put(2, "B");
        concurrentHashMap.put(3, "C");
        // key存在就直接返回原值，不替换。否则才会添加进去，返回null
        Object o = concurrentHashMap.putIfAbsent(2, "A");
        Object o2 = concurrentHashMap.putIfAbsent(4, "D");
        System.out.println(o); // B
        System.out.println(o2); // null
        System.out.println(concurrentHashMap); // {1=A, 2=B, 3=C, 4=D}
        // 方法对 hashMap 中指定 key 的值进行重新计算，如果不存在这个 key，则添加到 hashMap 中
        concurrentHashMap.computeIfAbsent(4, key -> "A");  // 存在不变
        concurrentHashMap.computeIfAbsent(5, key -> "E"); //不存在 所以可以插入进去
        System.out.println(concurrentHashMap);
        concurrentHashMap.computeIfPresent(4, (key, value) -> "haha"); // 存在可以覆盖
        concurrentHashMap.computeIfPresent(6, (key, value) -> "hhi"); // 不存在无法插入进去
        System.out.println(concurrentHashMap);
    }
}
