package com.starry.codeview.threads.P06_ProducerConsumer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 是将执行的逻辑放在循环外 这样执行完毕就能自动释放锁 而不需要await
 * 保证生产者和消费者执行的随机性，更加符合现实
 */
public class T03_ReentrantLock_Condition_V2 {
    /**
     * 10个空间的容器
     */
    static LinkedList<Integer> goodsList = new LinkedList<Integer>();
    final static Integer MaxLength = 10;
    static ReentrantLock lock = new ReentrantLock();
    static Condition cProducer = lock.newCondition();
    static Condition cConsumer = lock.newCondition();

    public static void main(String[] args) {
        T02_ReentrantLock_Condition.Producer producer1 = new T02_ReentrantLock_Condition.Producer("producer1");
        T02_ReentrantLock_Condition.Producer producer2 = new T02_ReentrantLock_Condition.Producer("producer2");
        T02_ReentrantLock_Condition.Producer producer3 = new T02_ReentrantLock_Condition.Producer("producer3");
        T02_ReentrantLock_Condition.Consumer consumer1 = new T02_ReentrantLock_Condition.Consumer("consumer1");
        T02_ReentrantLock_Condition.Consumer consumer2 = new T02_ReentrantLock_Condition.Consumer("consumer2");
        T02_ReentrantLock_Condition.Consumer consumer3 = new T02_ReentrantLock_Condition.Consumer("consumer3");
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
            // 重点是这里的思想  什么逻辑应该循环 什么不需要循环
            while (goodsList.size() >= MaxLength) {
                try {
                    cProducer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Integer total = goodsList.size();
            goodsList.add(1);
            System.out.println("总量" + total + "，生产者" + getName() + "生产了一个商品，剩余数量为" + goodsList.size());
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
            while (goodsList.size() == 0) {
                try {
                    cConsumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Integer total = goodsList.size();
            goodsList.pollFirst();
            System.out.println("总量" + total + "，消费者" + getName() + "购买了一个商品，剩余数量为" + goodsList.size());
            cProducer.signalAll();
            lock.unlock();
        }
    }
}

