package com.starry.codeview.threads.P02_A1B2C3;

import java.util.concurrent.locks.LockSupport;

/**
 * 这是最简单的写法！！！ JUC的
 * lockSupport可以先叫醒unpack 在挂起park。 不会报错
 * 如果先叫醒了， 等到执行park动作时，发现有个叫醒标记，那么就不会挂起，继续往下执行
 * 其他的wait notify  lock ReentrantLock 都不行
 */
public class T01_LockSupport {
    static char[] chars = "ABCDEF".toCharArray();
    static char[] chars1 = "123456".toCharArray();

    static Thread thread1 = null, thread2 = null;

    public static void main(String[] args) {
        thread1 = new Thread(() -> {
            for (int i = 0; i < chars.length; i++) {
                System.out.println(chars[i]);
                LockSupport.unpark(thread2); // 叫醒thread2
                LockSupport.park(); // 当前执行的线程睡觉
            }
        });
        thread2 = new Thread(() -> {
            for (int i = 0; i < chars1.length; i++) {
                LockSupport.park();  // 上来直接睡觉 ，保证A字母一定是先输出
                System.out.println(chars1[i]);
                LockSupport.unpark(thread1);
            }
        });

        thread1.start();
        thread2.start();
    }
}
