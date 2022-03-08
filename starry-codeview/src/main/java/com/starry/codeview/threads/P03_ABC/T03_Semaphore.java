package com.starry.codeview.threads.P03_ABC;

import java.util.concurrent.Semaphore;

public class T03_Semaphore {
    static Semaphore semaphoreA = new Semaphore(1);
    static Semaphore semaphoreB = new Semaphore(0);
    static Semaphore semaphoreC = new Semaphore(0);

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    semaphoreA.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("A");
                semaphoreB.release(1);
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    semaphoreB.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("B");
                semaphoreC.release(1);
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    semaphoreC.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("C");
                semaphoreA.release(1);
            }
        }, "C").start();
    }
}