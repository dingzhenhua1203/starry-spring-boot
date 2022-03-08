package com.starry.codeview.threads.P02_A1B2C3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列
 * 定义两个容量为1的阻塞队列ArrayBlockingQueue
 */
public class T04_BlockingQueue {
    static char[] chars = "ABCDEF".toCharArray();
    static char[] chars1 = "123456".toCharArray();

    static Thread thread1 = null, thread2 = null;

    static BlockingQueue<String> queue1 = new ArrayBlockingQueue<>(1); // 容量为1
    static BlockingQueue<String> queue2 = new ArrayBlockingQueue<>(1);

    public static void main(String[] args) {
        thread1 = new Thread(() -> {
            for (char aChar : chars) {
                System.out.println(aChar);
                try {
                    queue1.put("1");
                    queue2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2 = new Thread(() -> {
            for (char c : chars1) {
                try {
                    queue1.take();
                    System.out.println(c);
                    queue2.put("1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
