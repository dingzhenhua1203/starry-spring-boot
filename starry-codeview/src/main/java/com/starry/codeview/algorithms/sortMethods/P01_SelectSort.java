package com.starry.codeview.algorithms.sortMethods;

import java.util.ArrayList;

/**
 * 选择排序
 * 在要排序的一组数中，选出最小（或者最大）的一个数与第1个位置的数交换；然后在剩下的数当中再找最小（或者最大）的与第2个位置的数交换，
 * 依次类推，直到第n-1个元素（倒数第二个数）和第n个元素（最后一个数）比较为止。
 * 第一趟，从n 个记录中找出关键码最小的记录与第一个记录交换
 * 第二趟，从第二个记录开始的n-1 个记录中再选出关键码最小的记录与第二个记录交换
 * 以此类推.....
 * 第i 趟，则从第i 个记录开始的n-i+1 个记录中选出关键码最小的记录与第i 个记录交换
 * 直到整个序列按关键码有序。
 *
 * https://m.html.cn/softprog/java/1114273077900.html
 */
public class P01_SelectSort {
    public static void main(String[] args) {
        ArrayList<Integer> result=new ArrayList<>();
    }
}
