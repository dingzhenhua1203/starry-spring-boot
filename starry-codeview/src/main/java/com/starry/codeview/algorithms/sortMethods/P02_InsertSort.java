package com.starry.codeview.algorithms.sortMethods;

/**
 * 直接插入排序
 * 将一个记录插入到已排序好的有序表中，从而得到一个新，记录数增1的有序表。
 * 即：先将序列的第1个记录看成是一个有序的子序列，然后从第2个记录逐个进行插入，直至整个序列有序为止。
 * 如果碰见一个和插入元素相等的，那么插入元素把想插入的元素放在相等元素的后面。
 * 所以，相等元素的前后顺序没有改变，从原无序序列出去的顺序就是排好序后的顺序
 * 所以插入排序是稳定的
 */
public class P02_InsertSort {

    public static void sort(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                arr[i] = arr[i] + arr[i + 1];
                arr[i + 1] = arr[i] - arr[i + 1];
                arr[i] = arr[i] - arr[i + 1];
            }
        }

    }


    public static void main(String[] args) {
        Integer[] arr = new Integer[]{1, 9, 4, 8, 6, 7, 2, 3, 5};


    }
}
