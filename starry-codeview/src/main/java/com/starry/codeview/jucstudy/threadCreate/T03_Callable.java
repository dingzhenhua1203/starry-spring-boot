package com.starry.codeview.jucstudy.threadCreate;

import java.util.concurrent.Callable;

public class T03_Callable implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "当前线程" + Thread.currentThread().getName() + "call值";
    }
}
