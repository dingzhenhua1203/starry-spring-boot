package com.starry.codeview.threads.P06_ProducerConsumer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这种写法 将执行的条件作为循环，那么阻塞就需要await干预，就导致生产者和消费者不容易随机执行，而是普遍以交替的方式执行！
 * 改进方法 是将执行的逻辑放在循环外 这样执行完毕就能自动释放锁 而不需要await
 */
public class T02_ReentrantLock_Condition {

    /**
     * 10个空间的容器
     */
    static LinkedList<Integer> goodsList = new LinkedList<Integer>();
    final static Integer MaxLength = 10;
    static ReentrantLock lock = new ReentrantLock();
    static Condition cProducer = lock.newCondition();
    static Condition cConsumer = lock.newCondition();

    public static void main(String[] args) {
        Producer producer1 = new Producer("producer1");
        Producer producer2 = new Producer("producer2");
        Producer producer3 = new Producer("producer3");
        Consumer consumer1 = new Consumer("consumer1");
        Consumer consumer2 = new Consumer("consumer2");
        Consumer consumer3 = new Consumer("consumer3");
        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }

    final static class Producer extends Thread {

        public Producer(String name) {
            setName(name);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            while (goodsList.size() < MaxLength) {
                Integer total = goodsList.size();
                goodsList.add(1);
                System.out.println("总量" + total + "，生产者" + getName() + "生产了一个商品，剩余数量为" + goodsList.size());
                cConsumer.signalAll();
                try {
                    cProducer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cConsumer.signalAll();
            lock.unlock();

        }
    }

    final static class Consumer extends Thread {

        public Consumer(String name) {
            setName(name);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            while (goodsList.size() > 0) {
                Integer total = goodsList.size();
                goodsList.pollFirst();
                System.out.println("总量" + total + "，消费者" + getName() + "购买了一个商品，剩余数量为" + goodsList.size());
                cProducer.signalAll();
                try {
                    cConsumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cProducer.signalAll();
            lock.unlock();
        }
    }
}
