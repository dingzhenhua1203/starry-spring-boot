package com.starry.codeview.algorithms.sortMethods;

import com.starry.codeview.algorithms.arrayCalc.RandomArraySample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 快排
 * 1）选择一个基准元素,通常选择第一个元素或者最后一个元素
 * 2）通过一趟排序将待排序的记录分割成两部分，其中一部分记录的元素值均比基准元素值小。另一部分记录的元素值比基准值大。
 * 3）此时基准元素在其排好序后的正确位置
 * 4）然后分别对这两部分记录用同样的方法继续进行排序，直到整个序列有序。
 */
public class P05_QuickSort {
    public static Integer[] QuickSort(Integer[] arr) {
        Integer[] result = arr;
        if (arr == null || arr.length <= 1) {
            return result;
        }
        if (arr.length == 2) {
            if (arr[0] > arr[1]) {
                arr[0] = arr[0] + arr[1];
                arr[1] = arr[0] - arr[1];
                arr[0] = arr[0] - arr[1];
            }
            return arr;
        }

        int raw = arr[0];
        List<Integer> min = new ArrayList();
        List<Integer> max = new ArrayList();
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < raw) {
                min.add(arr[i]);
            } else {
                max.add(arr[i]);
            }
        }
        min = Arrays.stream(P05_QuickSort.QuickSort(min.toArray(new Integer[0]))).collect(Collectors.toList());
        min.add(raw);
        max = Arrays.stream(P05_QuickSort.QuickSort(max.toArray(new Integer[0]))).collect(Collectors.toList());
        min.addAll(max);
        result = min.toArray(new Integer[0]);
        return result;
    }

    public static void main(String[] args) {
        // Integer[] data = Stream.of(1, 2, 5, 8, 3, 2, 7,45, 23, 2, 7, 4).toArray(Integer[]::new);
        Integer[] data = RandomArraySample.randomArray();
        Integer[] integers = QuickSort(data);
        System.out.println(Arrays.toString(integers));
    }
}
