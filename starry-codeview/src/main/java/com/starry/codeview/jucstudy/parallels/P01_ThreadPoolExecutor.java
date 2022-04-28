package com.starry.codeview.jucstudy.parallels;

import com.starry.codeview.jucstudy.ThreadPoolUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class P01_ThreadPoolExecutor {

    public final static ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.threadPools;

    /**
     * 输出
     * a
     * a
     * a
     * null
     * haha
     * abc
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void exec_One() throws ExecutionException, InterruptedException {
        Runnable a = () -> {
            System.out.println("a");
            return;
        };

        Callable<String> c = () -> {
            return "abc";
        };

        Callable<String> d = () -> {
            int i = 1 / 0;
            return "d";
        };
        threadPoolExecutor.execute(a);
        // // 提交 有返回值，如果执行报错，通过.get()能拿到异常信息 而不是T的返回结果
        Future<?> submit1 = threadPoolExecutor.submit(a);// 通过 newTaskFor(task, null);适配成callable了,
        Future<?> submit2 = threadPoolExecutor.submit(a, "haha");
        Future<String> submit3 = threadPoolExecutor.submit(c);
        Future<String> submit4 = threadPoolExecutor.submit(d);
        System.out.println(submit1.get()); // 返回null
        System.out.println(submit2.get()); // 返回haha
        System.out.println(submit3.get()); // abc

        System.out.println(" error = " + submit4.get()); // 不会打印数据，直接抛出异常Exception in thread "main" java.util....
    }

    /**
     * invokeAll
     * 如果invokeAll 传入超时时间，则可能发生超时，部分任务未执行
     * 如果其中一个执行异常，会全部取消执行。
     */
    public static void invokeAll() {
        List<String> result = new ArrayList<>();

        Callable<String> supply1 = () -> {
            System.out.println("supply1");
            return "supply1";
        };
        Callable<String> supply2 = () -> {
            System.out.println("supply2");
            return "supply2";
        };
        Callable<String> supply3 = () -> {
            System.out.println("supply3");
            return "supply3";
        };
        List<Callable<String>> callableList = Arrays.asList(supply1, supply2, supply3);
        try {
            List<Future<String>> futures = ThreadPoolUtils.threadPools.invokeAll(callableList);
            futures.forEach(x -> {
                try {
                    result.add(x.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("result = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * invokeAny
     * 只要有一个执行完毕，就结束
     */
    public static void invokeAny() {
        List<String> result = new ArrayList<>();

        Callable<String> supply1 = () -> {
            System.out.println("supply1");
            Thread.sleep(1000);
            return "supply1";
        };
        Callable<String> supply2 = () -> {
            System.out.println("supply2");
            Thread.sleep(1000);
            return "supply2";
        };
        Callable<String> supply3 = () -> {
            System.out.println("supply3");
            Thread.sleep(1000);
            return "supply3";
        };
        List<Callable<String>> callableList = Arrays.asList(supply1, supply2, supply3);
        try {
            String s = ThreadPoolUtils.threadPools.invokeAny(callableList); // 只要有一个执行成功就结束
            System.out.println("result = " + s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        exec_One();
        System.out.println("***********【invokeAll】如果invokeAll 传入超时时间，则可能发生超时，部分任务未执行,如果其中一个执行异常，会全部取消执行。*****************");
        invokeAll();
        System.out.println("***********【invokeAny】只要有一个执行完毕，就结束***************");
        invokeAny();
    }
}
