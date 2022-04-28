package com.starry.codeview.algorithms.arrayCalc;

import com.starry.starrycore.RandomUtils;

import java.util.Arrays;
import java.util.Random;

/**
 * 随机数组样本生成器
 */
public class RandomArraySample {
    private final static int MaxLength = 20;

    private final static int MaxArrayValue = 15;


    public static Integer[] randomArray() {
        int length = RandomUtils.randomInt(MaxLength);
        Integer[] arr = new Integer[length];
        for (int i = 0; i < length; i++) {
            arr[i] = RandomUtils.randomInt(MaxArrayValue);
        }
        // System.out.println(Arrays.toString(arr));
        return arr;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Integer[] integers = randomArray();
            System.out.println(Arrays.toString(integers));
        }
    }

}
