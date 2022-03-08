package com.starry.codeview.threads.P05_ThreadInterrupt;

/**
 * 线程中断失败， 因为目标线程收到中断信号并没有做出处理
 */
public class T01_ThreadInterrupt_Failed {
    static int i = 10;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (i++ < 56) {
                System.out.println("t1线程执行");
                Thread.yield();
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
