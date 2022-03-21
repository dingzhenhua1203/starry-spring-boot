package com.starry.starryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MyTaskScheduleService {

    @Autowired
    private TestDemo testDemo;

    @Scheduled(cron = "0/10 * * * * ? ")
    public void PrintfHelloJob() {
        testDemo.doTest("测试aaa");
        System.out.println("同步Hello，当前时间" + LocalDate.now() + "线程名" + Thread.currentThread().getName() + ",hello!!");
    }

    // @Scheduled(cron = "0/8 * * * * ? ")
    public void PrintfTestJob() {
        System.out.println("同步test，当前时间" + LocalDate.now() + "线程名" + Thread.currentThread().getName() + ",Test!!");
    }

    /**
     * 多线程执行定时任务，否则下一次执行会被上次执行情况干扰
     */
    @Async
    @Scheduled(cron = "0/10 * * * * ? ")
    public void AsyncPrintfHelloJob() {
        System.out.println("异步JOB,当前时间" + LocalDate.now() + "线程名" + Thread.currentThread().getName() + ",hello!!");
    }
}
