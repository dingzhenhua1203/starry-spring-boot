package com.starry.starrycore;

public class PrintHelper {
    public static String printFullLog(String log) {
        String d = "线程名称: " + Thread.currentThread().getName() + " | " + Thread.currentThread().getId() + " | " + log;
        System.out.println(d);
        return d;
    }
}
