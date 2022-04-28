package com.starry.codeview.jucstudy.threadCreate;

import static java.lang.Thread.currentThread;

/**
 * 推荐实现Runnable接口来做
 */
public class T02_Runnable implements Runnable {
    @Override
    public void run() {
        System.out.println("实现Runable的多线程" + currentThread().getName());
    }
}
