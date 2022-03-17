package com.starry.codeview.jucstudy;

import java.util.concurrent.Callable;

public class T03_Callable implements Callable {

    @Override
    public Object call() throws Exception {
        return "当前线程" + Thread.currentThread().getName() + "AAAAA";
    }
}
