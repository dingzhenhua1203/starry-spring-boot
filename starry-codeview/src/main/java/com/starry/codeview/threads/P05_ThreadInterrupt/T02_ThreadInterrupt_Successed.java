package com.starry.codeview.threads.P05_ThreadInterrupt;

/**
 * 线程中断成功， 目标线程收到中断信号做出了处理
 */
public class T02_ThreadInterrupt_Successed {
    static int i = 10;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (i++ < 56) {
                System.out.println("t1线程执行");
                Thread.yield();

                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("t1线程被中断，退出");
                    return;
                }
            }
        });

        t1.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }
}
