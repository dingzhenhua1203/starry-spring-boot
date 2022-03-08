package com.starry.codeview.threads.P03_ABC;

import java.util.concurrent.CountDownLatch;

/**
 * notify wait 释放锁 要么是synchronized 代码块执行完毕释放 要么是wait动作让出锁
 * 如下方法两个锁，没法同时调用两次wait.所以需要控制内部的锁的范围！！！
 *
 * 需要注意for循环结束时 自身锁要不要通过wait释放并阻塞起来。 实际每个线程最后一次循环后 任务就终结了，因此不再需要阻塞
 */
public class T02_sync_wait_notify {

    static Object lockerA = new Object();
    static Object lockerB = new Object();
    static Object lockerC = new Object();
    static CountDownLatch latch1 = new CountDownLatch(1);
    static CountDownLatch latch2 = new CountDownLatch(1);

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lockerA) {
                for (int i = 0; i < 5; i++) {
                    synchronized (lockerB) {
                        System.out.print("A");
                        latch1.countDown();
                        lockerB.notify(); // 通知持有lockerB的其中一个线程唤醒， 自己本身不进入阻塞,也不会释放lockerB锁
                    }
                    try {
                        // 当i达到循环尾部的时候 不需要再调用wait阻塞了 直接让线程执行完毕彻底结束掉
                        if (i != 4) {
                            lockerA.wait();  // 表示当前线程释放掉lockerA锁，进入等待的阻塞状态
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            try {
                latch1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockerB) {
                for (int i = 0; i < 5; i++) {
                    synchronized (lockerC) {
                        System.out.print("B");
                        latch2.countDown();
                        lockerC.notify();
                    }
                    try {
                        if (i != 4) {
                            lockerB.wait();  // 表示当前线程释放掉lockerB锁，进入等待的阻塞状态
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();

        new Thread(() -> {
            try {
                latch2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockerC) {
                for (int i = 0; i < 5; i++) {
                    synchronized (lockerA) {
                        System.out.print("C");
                        lockerA.notify();

                    }
                    try {
                        if (i != 4) {
                            lockerC.wait();  // 表示当前线程释放掉lockerC锁，进入等待的阻塞状态
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
