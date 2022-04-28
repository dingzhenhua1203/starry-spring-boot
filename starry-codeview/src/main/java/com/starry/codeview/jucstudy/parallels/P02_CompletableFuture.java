package com.starry.codeview.jucstudy.parallels;

import com.starry.codeview.jucstudy.ThreadPoolUtils;
import com.starry.starrycore.PrintHelper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class P02_CompletableFuture {
    public static void basic() throws ExecutionException, InterruptedException {
        //  底层是ForkJoinPool
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ForkJoinPool.commonPool-worker-3..task1
            PrintHelper.printFullLog("..task1");
        });

        // 有返回值的supplyAsync
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ForkJoinPool.commonPool-worker-3..task1
            PrintHelper.printFullLog("..task2");
            return "task2";
        });
        task1.get();
        PrintHelper.printFullLog(task2.get());

    }

    public static CompletableFuture<Integer>[] buildMutTask() {
        Supplier<Integer> call1 = () -> 1;
        Supplier<Integer> call2 = () -> 2;
        Supplier<Integer> call3 = () -> 3;
        Object[] objects = Stream.of(call1, call2, call3).toArray();
        String[] aaa = Stream.of("1", "2", "3").collect(Collectors.toList()).toArray(new String[0]);
        List<Supplier<Integer>> suppliers = Stream.of(call1, call2, call3).collect(Collectors.toList());
        List<CompletableFuture<Integer>> collect = suppliers.stream().map(x -> CompletableFuture.supplyAsync(x)).collect(Collectors.toList());
        CompletableFuture[] completableFutures = suppliers.stream().map(x -> CompletableFuture.supplyAsync(x)).toArray(CompletableFuture[]::new);
        CompletableFuture[] completableFutures2 = suppliers.stream().map(x -> CompletableFuture.supplyAsync(x)).toArray(x -> {
            CompletableFuture[] completableFutures1 = new CompletableFuture[suppliers.size()];
            return completableFutures1;
        });
        return completableFutures;
    }

    public static void multiHandle() throws ExecutionException, InterruptedException {
        // 直接创建
        CompletableFuture c0 = new CompletableFuture();
        // 直接创建一个已经做完的蛋糕
        CompletableFuture<String> c1 = CompletableFuture.completedFuture("cake");
        // 无返回值异步任务，会采用内部forkJoin线程池
        CompletableFuture<Void> c2 = CompletableFuture.runAsync(() -> {
            PrintHelper.printFullLog("c2执行");
        });
        // 无返回值异步任务，采用定制的线程池
        CompletableFuture<Void> c3 = CompletableFuture.runAsync(() -> {
            PrintHelper.printFullLog("c3执行");
        }, ThreadPoolUtils.threadPools);
        // 返回值异步任务，采用定制的线程池
        CompletableFuture<String> c4 = CompletableFuture.supplyAsync(() -> {
            PrintHelper.printFullLog("c4执行");
            return "c4";
        });
        CompletableFuture<String> c5 = CompletableFuture.supplyAsync(() -> {
            PrintHelper.printFullLog("c5执行");
            return "c5";
        }, ThreadPoolUtils.threadPools);
        // 返回值异步任务，采用内部forkJoin线程池
        CompletableFuture<String> c6 = CompletableFuture.supplyAsync(() -> {
            PrintHelper.printFullLog("c6执行");
            return "c6";
        });
        // 只要有一个完成，则完成，有一个抛异常，则携带异常
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(c1, c2, c3, c4, c5, c6);
        // 当所有的 future 完成时,新的 future 同时完成
        // 当某个方法出现了异常时,新 future 会在所有 future 完成的时候完成,并且包含一个异常.
        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.allOf(c1, c2, c3, c4, c5, c6);
        CompletableFuture<Integer>[] completableFutures = buildMutTask();
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(completableFutures);

        // 获取结果 join()和get()方法都是用来获取CompletableFuture异步之后的返回值。都会阻塞
        //  join不抛出中断异常 get返回异常,需要用户捕获异常,否则编译不通过
        Object join = objectCompletableFuture.join();
        Object tt = objectCompletableFuture.getNow("默认值");
        Void unused = voidCompletableFuture1.get();
        Void join1 = voidCompletableFuture.join();
    }

    public static void streamDoSomething() {
        CompletableFuture<String> makeCake2 = CompletableFuture.supplyAsync(() -> {
            PrintHelper.printFullLog("糕点师做蛋糕 v1");
            return "cake";
        });
        makeCake2.thenApply(cake -> {
            PrintHelper.printFullLog("蛋糕做好了，我来做牛奶 v1" + "接受:" + cake);
            return "milk";
        }).thenAccept(milk -> {
            PrintHelper.printFullLog("牛奶做好了 v1" + "接受:" + milk);
            PrintHelper.printFullLog("我开始吃早饭 v1");
        }).thenRun(() -> {
            PrintHelper.printFullLog("吃完早饭我去上班 v1");
        });

        makeCake2.join();

        // makeCake2.get(); // 需要用户捕获异常
    }

    public static void streamDoSomethingAsync() {
        CompletableFuture<String> makeCake = CompletableFuture.supplyAsync(() -> {
            PrintHelper.printFullLog("糕点师做蛋糕 v2");
            return "cake";
        });
        makeCake.thenApplyAsync(cake -> {
            PrintHelper.printFullLog("蛋糕做好了，我来做牛奶 v2" + "接受:" + cake);
            return "milk";
        }).thenAcceptAsync(milk -> {
            PrintHelper.printFullLog("牛奶做好了 v2" + "接受:" + milk);
            PrintHelper.printFullLog("我开始吃早饭 v2");
        }).thenRunAsync(() -> {
            PrintHelper.printFullLog("吃完早饭我去上班 v2");
        });

        // complete方法是 如果没有执行完成则使用此默认值传过去
        // boolean complete = makeCake.complete("123");
        // System.out.println(complete);
        makeCake.join();
    }

    public static void studyAPI() {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            PrintHelper.printFullLog("cf1");
            return "cf1";
        }).thenApplyAsync(x -> {
            PrintHelper.printFullLog("aaa" + " | " + x);
            int i = 1 / 0;
            return "aaa";
        }).exceptionally(e -> PrintHelper.printFullLog(e.getMessage())).thenApplyAsync(x -> {
            PrintHelper.printFullLog("bbb" + " | " + x);
            return "bbb";
        }).handleAsync((x, e) -> {
            PrintHelper.printFullLog("ccc" + " | " + x);
            return "ccc";
        }).whenComplete((x, e) -> {
            PrintHelper.printFullLog("ddd" + " | " + x);
            return;
        });

        cf1.join();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 基础
        //basic();
        System.out.println("**********************【高级API】***************************");
        // anyOf  allOf
        //multiHandle();
        System.out.println("**********************【xxx】***************************");
        // 流式任务

        streamDoSomethingAsync();
        //streamDoSomething();

        //studyAPI();

    }
}
