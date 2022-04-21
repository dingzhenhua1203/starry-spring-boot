package com.starry.codeview.algorithms.arrayCalc;

import com.starry.codeview.algorithms.sortMethods.P05_QuickSort;

import java.util.Arrays;

/**
 * 排序对数器
 */
public class CheckSampleMachine {

    public static boolean checkSort() {
        for (int i = 0; i < 1000; i++) {
            Integer[] data = RandomArraySample.randomArray();

            Integer[] integers = P05_QuickSort.QuickSort(data);
            Arrays.sort(data);
            for (int j = 0; j < data.length; j++) {
                if (data[j] != integers[j]) {
                    System.out.println(Arrays.toString(integers));
                    return false;
                }
            }

        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(checkSort());
    }
}
