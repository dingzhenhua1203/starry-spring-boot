package com.starry.codeview.jucstudy;

public class T01_Thread extends Thread {
    private String name;

    public T01_Thread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        // super.run();
        System.out.println("当前线程" + currentThread().getName() + "执行" + name);
    }
}
