package com.starry.codeview.threads.P02_A1B2C3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock Condition 无法控制谁先执行 还需要CountDownLatch配合
 * condition内部也是使用同样的方式，内部维护了一个 等待队列，
 * 所有调用condition.await方法的线程会加入到等待队列中，并且线程状态转换为等待状态
 */
public class T06_ReentrantLock_countdownlatch {
    static char[] chars = "ABCDEF".toCharArray();
    static char[] chars1 = "123456".toCharArray();

    static Thread thread1 = null, thread2 = null;

    static ReentrantLock lock = new ReentrantLock();
    static CountDownLatch latch = new CountDownLatch(1);

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
                    latch.countDown();
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
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
