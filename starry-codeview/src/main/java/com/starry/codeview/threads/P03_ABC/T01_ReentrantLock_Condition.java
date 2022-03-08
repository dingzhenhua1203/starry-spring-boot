package com.starry.codeview.threads.P03_ABC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition内部也是使用同样的方式，内部维护了一个 等待队列，
 * 所有调用condition.await方法的线程会加入到等待队列中，并且线程状态转换为等待状态
 */
public class T01_ReentrantLock_Condition {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Condition cA = lock.newCondition();
        Condition cB = lock.newCondition();
        Condition cC = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("A");
                    cB.signal();
                    // cC.await(); // 不可以在这里写多个await=>所有调用condition.await方法的线程会加入到等待队列中
                    cA.await(); // 所有调用condition.await方法的线程会加入到等待队列中
                }

                cB.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("B");
                    cC.signal();
                    cB.await();
                }

                cC.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("C");
                    cA.signal();
                    cC.await();
                }

                cA.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
    }
}
