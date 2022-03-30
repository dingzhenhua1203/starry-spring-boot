package com.starry.codeview.jucstudy.parallels;

import com.starry.codeview.jucstudy.ThreadPoolUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelDemo {
    static List<Integer> list = new ArrayList<>();
    static Long total = 100_0000_0000L;

    public static void streamTest() {
        // 累加
        System.out.println(LongStream.rangeClosed(0, 200_000_00L).parallel().sum());
    }

    public static void parallelTest() throws ExecutionException, InterruptedException {
        //  底层是ForkJoinPool
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ForkJoinPool.commonPool-worker-3..task1
            System.out.println(Thread.currentThread().getName() + "..task1");
        });

        // 有返回值的supplyAsync
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ForkJoinPool.commonPool-worker-3..task1
            System.out.println(Thread.currentThread().getName() + "..task1");
            // return gData("task2");
            return "task2";
        });
        System.out.println(Thread.currentThread().getName() + "...main");
        task1.get();
        System.out.println(Thread.currentThread().getName() + "...task2.get()=" + task2.get());

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        streamTest();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        //  本质forkjoin的底层
        list.parallelStream().forEach(x -> {
            System.out.println(x);
        });

        list.stream().parallel().forEach(x -> {
            System.out.println(x);
        });

        List<Integer> numList = Stream.of(1, 2, 3, 4, 5, 6, 7, 8).collect(Collectors.toList());


        Runnable task1 = () -> {
            System.out.println("查询数据A");
        };
        Runnable task2 = () -> {
            System.out.println("查询数据B");
        };
        Runnable task3 = () -> {
            System.out.println("查询数据C");
        };

        // .invokeAll(task1,task2,task3)
        List<Callable<Object>> collect = Arrays.asList(task1, task2, task3).stream().map(x -> Executors.callable(x)).collect(Collectors.toList());
        ThreadPoolUtils.threadPools.invokeAll(collect);


        Callable<String> task4 = () -> {
            System.out.println("查询数据D");
            Thread.sleep(5000);
            return "查询数据D";
        };
        Callable<String> task5 = () -> {
            System.out.println("查询数据E");
            Thread.sleep(10000);
            return "查询数据E";
        };
        /*List<Callable<String>> asss = new ArrayList<Callable<String>>();
        asss.add(task4);
        asss.add(task5)*/
        CopyOnWriteArrayList<String> conCurrentList = new CopyOnWriteArrayList<>();
        List<Future<String>> futures = ThreadPoolUtils.threadPools.invokeAll(Arrays.asList(task4, task5));
        futures.stream().forEach(x -> {
            try {
                conCurrentList.add(x.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        // conCurrentList[查询数据D, 查询数据E]
        System.out.println("conCurrentList" + conCurrentList.toString());

        parallelTest();
    }
}
