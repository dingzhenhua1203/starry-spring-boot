package com.starry.codeview.jucstudy;

import java.util.concurrent.*;

public class JUCTest {
    /**
     * 测试callable的几种写法
     * 不推荐 推荐异步通信
     * 因为futuretask.get()会产生阻塞
     * <p>
     * ForkJoin  stream并行流
     */
    public static void callableTest() throws ExecutionException, InterruptedException {
        // 两种写法.原生写法没有采用lambda
        T03_Callable ca = new T03_Callable();
        Callable<String> ca1 = () -> "哈哈";
        // 如何执行呢？
        /*
        new Thread(Runnable target).start();
        而FutureTask继承了RunnableFuture,RunnableFuture继承了Runnable和Future
        并且FutureTask可以执行callable接口
         */
        FutureTask<String> stringFutureTask = new FutureTask<>(ca);
        // 包装完成后启动，还是通过thread启动
        new Thread(stringFutureTask).start();
        // 获取到执行结果，但是会阻塞，如果任务没有执行完，来获取get的话会造成阻塞
        System.out.println(stringFutureTask.isDone());
        System.out.println("1...." + stringFutureTask.get());
        System.out.println(stringFutureTask.isDone());

        //这里要用FutureTask，否则不能加入Thread构造方法
        FutureTask<String> futureTask = new FutureTask<>(ca1);
        new Thread(futureTask).start();
        System.out.println("callable..." + futureTask.get());

        // 多线程执行callable
        Future<String> submit = ThreadPoolUtils.threadPools.submit(() -> Thread.currentThread().getName() + "submit-call=Value");

        System.out.println("submit.get();..." + submit.get());
        //ThreadPoolUtils.threadPools.shutdown();

        ThreadPoolUtils.threadPools.submit(()-> System.out.println("2"));

        ThreadPoolUtils.threadPools.execute(
                () -> System.out.println(Thread.currentThread().getName() + "execute...."));

        Future<String> submit2 = ThreadPoolUtils.threadPools.submit(() -> Thread.currentThread().getName() + "submit2-call=Value");

        System.out.println("submit2.get();..." + submit2.get());
        // ThreadPoolUtils.threadPools.shutdown();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        T01_Thread thred = new T01_Thread("t1");
        thred.start();

        T02_Runable runable = new T02_Runable();
        new Thread(runable).start();

        callableTest();
    }
}
