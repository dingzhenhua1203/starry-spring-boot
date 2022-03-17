package com.starry.codeview.jucstudy;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏。大概的意思就是一个可循环利用的屏障
 * 它的作用就是会让指定数量的所有线程都等待完成后才会继续下一步行动
 * 可以用于多线程计算数据，最后合并计算结果的场景
 * 每个线程执行完就cyclicBarrier.await()一次,所有线程都await就执行汇总
 * 每满足parties数量，就执行什么。
 * 例如100次 每满20次执行依次，就能执行5次。
 * 但是如果99次的话，最后一波满足不了20，就会阻塞在那等满为止
 */
public class CyclicBarrierDemo {
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(20, () -> {
        System.out.println("满足要求10，执行xxxx");
    });


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
