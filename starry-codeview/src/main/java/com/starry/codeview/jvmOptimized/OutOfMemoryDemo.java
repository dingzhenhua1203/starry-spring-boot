package com.starry.codeview.jvmOptimized;

import java.util.LinkedList;
import java.util.List;

public class OutOfMemoryDemo {
    public static void main(String[] args) {
        List<byte[]> result = new LinkedList<>();
        System.out.println("GC........");
        for (; ; ) {
            result.add(new byte[1024 * 1024]);
        }
    }
}
