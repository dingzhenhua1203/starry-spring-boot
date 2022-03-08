package com.starry.codeview.threads.P02_A1B2C3;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * 这是一个炫技的方案。正常不推荐用,效率不高
 * TransferQueue 同步队列 是容量为0 的同步队列，即存即取！
 * 利用这个容量为0 来做，达到线程阻塞的作用
 * 两个线程互为对方的生产者消费者：一个线程往里存并取出对方线程存的内容，另一个负责取并存入自己的内容，达到交替目的。
 * transferQueue.transfer(aChar); // 将aChar插入队列中， 这句话执行完会挂起阻塞在这里，不会继续往下执行，直到队列的东西被取走
 */
public class T03_TransferQueue {
    static char[] chars = "ABCDEF".toCharArray();
    static char[] chars1 = "123456".toCharArray();

    static Thread thread1 = null, thread2 = null;
    static TransferQueue<Character> transferQueue = new LinkedTransferQueue<>();

    public static void main(String[] args) {
        thread1 = new Thread(() -> {
            for (char aChar : chars) {
                try {
                    transferQueue.transfer(aChar); // 将aChar插入队列中， 这句话执行完会挂起阻塞在这里，不会继续往下执行，直到队列的东西被取走
                    System.out.println(transferQueue.take()); // 从队列中取出元素
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread2 = new Thread(() -> {
            for (char c : chars1) {
                try {
                    System.out.println(transferQueue.take()); // 从队列中取出元素
                    transferQueue.transfer(c); // 将aChar插入队列中
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }

}
