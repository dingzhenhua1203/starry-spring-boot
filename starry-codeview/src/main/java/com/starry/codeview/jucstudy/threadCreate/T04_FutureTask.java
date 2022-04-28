package com.starry.codeview.jucstudy.threadCreate;

import com.starry.codeview.jucstudy.ThreadPoolUtils;

import java.util.concurrent.*;

/**
 * FutureTask 可以包装runnable callable
 */
public class T04_FutureTask {
    /**
     * 测试callable的几种写法
     * 不推荐 推荐异步通信
     * 因为futureTask.get()会产生阻塞
     * <p>
     * ForkJoin  stream并行流
     */
    public static void callableTest() throws ExecutionException, InterruptedException {
        // 两种写法.原生写法没有采用lambda
        T03_Callable ca = new T03_Callable();
        Callable<String> ca1 = () -> "哈哈";

        // 如何执行

        /*
        1.包装成futureTask 然后还是通过thread启动
        */
        FutureTask<String> futureTask1 = new FutureTask<>(ca1);
        // 也可以包装runnable接口
        FutureTask<String> futureTask2 = new FutureTask(() -> {
            System.out.println("ss");
        }, "success");
        new Thread(futureTask1).start();
        String str = futureTask1.get();

        // 2.用线程池执行
        String integer = Executors.newCachedThreadPool().submit(ca1).get();

        // 获取到执行结果，但是会阻塞，如果任务没有执行完，来获取get的话会造成阻塞
        System.out.println(futureTask1.isDone());
        System.out.println("1...." + futureTask1.get());
        System.out.println(futureTask1.isDone());


        // 多线程执行callable
        Future<String> submit = ThreadPoolUtils.threadPools.submit(() -> Thread.currentThread().getName() + "submit-call=Value");
        System.out.println("submit.get();..." + submit.get());
        //ThreadPoolUtils.threadPools.shutdown();

        ThreadPoolUtils.threadPools.submit(() -> System.out.println("2"));

        ThreadPoolUtils.threadPools.execute(
                () -> System.out.println(Thread.currentThread().getName() + "execute...."));

        Future<String> submit2 = ThreadPoolUtils.threadPools.submit(() -> Thread.currentThread().getName() + "submit2-call=Value");

        System.out.println("submit2.get();..." + submit2.get());
        // ThreadPoolUtils.threadPools.shutdown();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        T01_Thread thread = new T01_Thread("t1");
        thread.start();

        T02_Runnable runable = new T02_Runnable();
        new Thread(runable).start();

        callableTest();
    }
}
