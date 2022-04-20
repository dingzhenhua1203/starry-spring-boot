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
    static Long total = 100_0000_0000L;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 累加 LongStream
        System.out.println(LongStream.rangeClosed(0, 200_000_00L).parallel().sum());

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        //  本质forkJoin的底层
        list.parallelStream().forEach(x -> {
            System.out.println(x);
        });

        list.stream().parallel().forEach(x -> {
            System.out.println(x);
        });

        List<Integer> numList = Stream.of(1, 2, 3, 4, 5, 6, 7, 8).collect(Collectors.toList());
    }
}
