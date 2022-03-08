package com.starry.codeview.threads.P06_ProducerConsumer;

import java.util.LinkedList;

/**
 * 生产者消费者问题
 * 生产者生产数据之后直接放置在共享数据区中，并不需要关心消费者的行为；
 * 消费者只需要从共享数据区中去获取数据，就不再需要关心生产者的行为。
 * 但是，这个共享数据区域中应该具备这样的线程间并发协作的功能：
 * 如果共享数据区已满的话，阻塞生产者继续生产数据放置入内；
 * 如果共享数据区为空的话，阻塞消费者继续消费数据；
 */
public class T01_sync_wait_notify {

    /**
     * 10个空间的容器
     */
    static LinkedList<Integer> goodsList = new LinkedList<Integer>();
    final static Integer MaxLength = 10;
    static Object locker = new Object();

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
            super.setName(name);
        }

        void produce() {
            synchronized (locker) {
                while (goodsList.size() < MaxLength) {
                    Integer total = goodsList.size();
                    goodsList.add(1);
                    System.out.println("总量" + total + "，生产者" + getName() + "生产了一个商品，剩余数量为" + goodsList.size());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    locker.notifyAll();

                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    locker.notifyAll();
                    locker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            produce();
        }
    }

    final static class Consumer extends Thread {

        public Consumer(String name) {
            super.setName(name);
        }

        void consume() {
            synchronized (locker) {
                while (goodsList.size() > 0) {
                    Integer total = goodsList.size();
                    goodsList.pollFirst();
                    System.out.println("总量" + total + "，消费者" + getName() + "购买了一个商品，剩余数量为" + goodsList.size());

                    if (goodsList.size() < MaxLength) {
                        locker.notifyAll();
                    }
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    locker.notifyAll();
                    locker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            consume();
        }
    }
}
