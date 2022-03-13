package com.starry.starryapi.service.starrythreads;

import java.util.concurrent.*;

public class ThreadPoolDemo {

    private static ExecutorService es = new ThreadPoolExecutor(50, 100,
            5L, TimeUnit.SECONDS,
            new LinkedBlockingQueue(100000));

    public static void ThreadMethod() {

        /**
         * corePoolSize 是在不超时情况下，保持活跃的最少线程数
         * maxPoolSize定义了可以创建的最大线程数
         * maxPoolSize依赖于queueCapacity，因为ThreadPoolTaskExecutor只会在其队列中的项目数超过queueCapacity*时创建一个新线程
         * 当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中
         * 当线程数大于或等于核心线程，且任务队列已满时，线程池会创建新的线程，直到线程数量达到maxPoolSize。
         * 如果线程数已等于maxPoolSize，且任务队列已满，则已超出线程池的处理能力，线程池会拒绝处理任务而抛出异常
         * keepAliveTime
         * 当线程空闲时间达到keepAliveTime，该线程会退出，直到线程数量等于corePoolSize。如果allowCoreThreadTimeout设置为true，则所有线程均会退出直到线程数量为0。
         */
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue(2000), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        // 如果我们将allowCoreThreadTimeOut设置为true，那么所有线程都可能超时，等于将corePoolSize的值设置为零，默认情况下就是false不会退出
        poolExecutor.allowCoreThreadTimeOut(false);
    }

    public static void main(String[] args) {
        // 创建一个线程
        ExecutorService es = Executors.newSingleThreadExecutor();
        // 创建固定长度的线程池，比如4个
        ExecutorService expool = Executors.newFixedThreadPool(4);
        // 创建弹性可伸缩的线程池
        Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < 10; i++) {
                final int temp = i;
                es.execute(() -> {
                    System.out.printf(Thread.currentThread().getName() + String.valueOf(temp)); // 永远是同一个线程执行
                });
            }

            for (int i = 0; i < 10; i++) {
                final int temp = i;
                expool.execute(() -> {
                    System.out.printf(Thread.currentThread().getName() + String.valueOf(temp)); // 最多4个线程协作执行
                });
            }
        } finally {
            // 关闭线程池
            es.shutdown();

            // 关闭线程池
            expool.shutdown();
        }
    }
}
