package com.starry.codeview.threads.P02_A1B2C3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock Condition 无法控制谁先执行 还需要CountDownLatch配合
 */
public class T05_ReentrantLock {
    static char[] chars = "ABCDEF".toCharArray();
    static char[] chars1 = "123456".toCharArray();

    static Thread thread1 = null, thread2 = null;

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Condition c1 = lock.newCondition();
        thread1 = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                for (char aChar : chars) {
                    System.out.println(aChar);
                    c1.signal();
                    c1.await();
                }

                c1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        });
        thread2 = new Thread(() -> {
            lock.lock();
            try {
                for (char c : chars1) {
                    System.out.println(c);
                    c1.signal();
                    c1.await();
                }

                c1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }


        });

        thread1.start();
        thread2.start();
    }
}
