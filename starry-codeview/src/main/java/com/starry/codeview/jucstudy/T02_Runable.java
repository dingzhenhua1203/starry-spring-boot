package com.starry.codeview.jucstudy;

import static java.lang.Thread.currentThread;

/**
 * 推荐实现Runnable接口来做
 */
public class T02_Runable implements Runnable {
    @Override
    public void run() {
        System.out.println("实现Runable的多线程" + currentThread().getName());
    }
}
