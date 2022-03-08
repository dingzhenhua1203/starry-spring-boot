package com.starry.codeview.threads.P05_ThreadInterrupt;

/**
 * 线程中断失败，Sleep遇到线程中断catch到异常会清除掉中断标记，所以线程会继续循环
 */
public class T03_ThreadInterrupt_Sleep_Failed {
    static int i = 10;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (i++ < 56) {
                System.out.println("t1线程执行");
                Thread.yield();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("t1线程sleep被中断");
                    e.printStackTrace();
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
