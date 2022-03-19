package com.starry.codeview.jucstudy.parallels;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelDemo {
    static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        //  本质forkjoin的底层
        list.parallelStream().forEach(x -> {
            System.out.println(x);
        });

        list.stream().parallel().forEach(x -> {
            System.out.println(x);
        });

        List<Integer> numList=Stream.of(1,2,3,4,5,6,7,8).collect(Collectors.toList());


        Runnable task1 = () -> {
            System.out.println("查询数据A");
        };
        Runnable task2 = () -> {
            System.out.println("查询数据B");
        };
        Runnable task3 = () -> {
            System.out.println("查询数据C");
        };

        // ThreadPoolDemo.threadPools.invokeAll(task1,task2,task3)
    }
}
