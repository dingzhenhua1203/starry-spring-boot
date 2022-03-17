package com.starry.codeview.jucstudy;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class JUCTest {
    public static void main(String[] args) {
        T01_Thread thred = new T01_Thread("t1");
        thred.start();

        T02_Runable runable = new T02_Runable();
        new Thread(runable).start();

        T03_Callable ca = new T03_Callable();
        //这里要用FutureTask，否则不能加入Thread构造方法
        FutureTask futureTask = new FutureTask(ca);
        new Thread(futureTask).start();
        try {
            System.out.println("callable..." + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
