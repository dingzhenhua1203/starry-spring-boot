package com.starry.starryapi.service.starrythreads;

/**
 * 不推荐使用， 推荐实现Runnable接口来做
 */
public class LogThread extends Thread {
    @Override
    public void run() {
        // super.run();
        System.out.println("当前线程"+currentThread().getName()+"执行");
    }
}
