package com.starry.codeview.jucstudy;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {

    static final int totalTimes = 10;
    static int int_sum = 0;
    static AtomicInteger atomicCount = new AtomicInteger(0);
    static CountDownLatch countDownLatch = new CountDownLatch(totalTimes);

    private static void testAtomicIncrBy() {
        for (int i = 0; i < 10_000; i++) {
            atomicCount.incrementAndGet();
        }
    }

    private static void testIntIncrBy() {
        for (int i = 0; i < 10_000; i++) {
            int_sum++;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < totalTimes; i++) {
            ThreadPoolUtils.threadPools.execute(() -> {
                testAtomicIncrBy();
                // testIntIncrBy();
            });
        }

        /*try {
            ThreadPoolDemo.threadPools.shutdown(); // 当前线程池已接收的所有任务都要执行完
            // ThreadPoolDemo.threadPools.shutdownNow();// 这个会忽略掉阻塞队列中排队的任务
            ThreadPoolDemo.threadPools.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("atomicCount=" + atomicCount);
        System.out.println("int_sum=" + int_sum);
    }
}
