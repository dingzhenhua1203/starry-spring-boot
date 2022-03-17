package com.starry.codeview.jucstudy;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁ReentrantReadWriteLock
 * 相比ReentrentLock来说，不需要每次都阻塞
 * 如果是读锁，可与共享使用，只有写锁才需要阻塞
 * 案例中20个线程读数据可以同时读，否则只能一个一个排队读耗时
 */
public class ReadWriteLockDemo {

    static ReentrantLock reentrantLock = new ReentrantLock();
    static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    public static void readData() {
        readLock.lock();
        try {
            System.out.println("读数据了");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            readLock.unlock();
        }
    }

    public static void writeData() {
        writeLock.lock();
        try {
            System.out.println("写入数据");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                readData();
            }).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                writeData();
            }).start();
        }
    }
}
