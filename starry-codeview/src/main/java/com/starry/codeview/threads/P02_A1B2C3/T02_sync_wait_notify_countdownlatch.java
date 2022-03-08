package com.starry.codeview.threads.P02_A1B2C3;

import java.util.concurrent.CountDownLatch;

/**
 * 最经典的 sync wait notify
 * 每个sync内部 最后都需要notify 唤醒线程， 否则程序永远就挂死在那了
 * 增加CountDownLatch 来控制先执行那个线程的打印动作
 */
public class T02_sync_wait_notify_countdownlatch {
    private static Object locker = new Object();

    static char[] chars = "ABCDEF".toCharArray();
    static char[] chars1 = "123456".toCharArray();

    static Thread thread1 = null, thread2 = null;

    static CountDownLatch latch = new CountDownLatch(1); // 门栓

    public static void main(String[] args) {
        thread1 = new Thread(() -> {
            synchronized (locker) {
                for (int i = 0; i < chars.length; i++) {
                    System.out.println(chars[i]);
                    latch.countDown();  // 门栓打开
                    locker.notify();
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                locker.notify(); // 务必加上notify 目的是唤醒线程， 否则程序就死在这了！！！
            }
        });
        thread2 = new Thread(() -> {
            try {
                latch.await();  // 线程进来不管三七二十一就拴上
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (locker) {

                for (int i = 0; i < chars1.length; i++) {
                    System.out.println(chars1[i]);
                    locker.notify();
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                locker.notify(); // 务必加上notify 目的是唤醒线程， 否则程序就死在这了！！！
            }
        });

        thread1.start();
        thread2.start();
    }
}
