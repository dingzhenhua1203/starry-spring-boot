package com.starry.codeview.jucstudy.parallels;

import java.util.ArrayList;
import java.util.List;

public class ParallelDemo {
    static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        list.parallelStream().forEach(x -> {
            System.out.println(x);
        });

        list.stream().parallel().forEach(x -> {
            System.out.println(x);
        });

        Runnable task1 = () -> {
            System.out.println("查询数据A");
        };
        Runnable task2 = () -> {
            System.out.println("查询数据B");
        };
        Runnable task3 = () -> {
            System.out.println("查询数据C");
        };

    }
}
