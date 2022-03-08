package com.starry.starryapi.service.starrythreads;

public class LogThread2 extends Thread {
    private String name;

    public LogThread2(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        // super.run();
        System.out.println("当前线程" + currentThread().getName() + "执行" + name);
    }
}
