package com.starry.codeview.threads2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {

    static List<String> resource = new CopyOnWriteArrayList();

    static ReentrantLock lock = new ReentrantLock();
    static Condition procd = lock.newCondition();
    static Condition consu = lock.newCondition();

    public static void main(String[] args) {
        Thread p1 = new Thread(() -> {
            produce();
        }, "p1");
        Thread p2 = new Thread(() -> {
            produce();
        }, "p2");
        Thread c1 = new Thread(() -> {
            consume();
        }, "c1");
        Thread c2 = new Thread(() -> {
            consume();
        }, "c2");

        p1.start();
        p2.start();
        c1.start();
        c2.start();


    }

    static void produce() {
        lock.lock();
        try {
            while (resource.size() == 5) {
                try {
                    System.out.println("已经生产满");
                    consu.signalAll();
                    procd.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            resource.add(Thread.currentThread().getName());
            System.out.println("生产了" + Thread.currentThread().getName());
            consu.signalAll();
            // procd.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    static void consume() {
        lock.lock();
        try {
            while (resource.size() == 0) {
                try {
                    System.out.println("已经消费光");
                    procd.signalAll();
                    //consu.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String remove = resource.remove(0);
            System.out.println(Thread.currentThread().getName() + "消费了" + remove);
            procd.signalAll();
            // consu.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

