package com.starry.codeview.algorithms;

/**
 * 汉诺塔算法
 */
public class P01_Hanoi {

    public static void hanoi(int size) {
        int from, temp, to;
        if (size == 1) {
            to = size;
            System.out.println(to + "移动到右");
        }

        temp = size - 1;

        to = size;
        System.out.println(to);

    }
}
