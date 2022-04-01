package com.starry.starryapi.controller;

import com.starry.codeview.jucstudy.T01_Thread;
import com.starry.codeview.jucstudy.T02_Runable;
import com.starry.codeview.jucstudy.T03_Callable;
import com.starry.starryapi.entity.UsrUserSimple;
import com.starry.starryapi.mapper.UserMapper;
import com.starry.starryapi.service.UnitTestService;
import com.starry.starryapi.studytest.circlereference.C;
import com.starry.starrycore.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 单元测试
 */
@RestController
@RequestMapping("unit-test")
public class UnitTestController {

    @Autowired
    UnitTestService _unitTestService;

    @Autowired(required = false)
    MailUtil mailUtil;

    /**
     * 获取用户是否授权
     *
     * @param usercode 用户编号
     * @return true 为授权
     */
    @GetMapping("get-isauth")
    public boolean getIsAuth(String usercode) {
        return false;
    }

    @GetMapping("test-Sync-Export")
    public String testSyncExport() {
        _unitTestService.Sync();
        return "Success";
    }

    @GetMapping("test-Async-Export")
    public String testAsyncExport() {
        _unitTestService.Async();
        return "Success";
    }

    @GetMapping("sendmail")
    public void SendMail() {
        try {
            mailUtil.SendTextMail("dingzhenhua1203@qq.com", "这是一个测试邮件", "测试专用");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("test-all-thread")
    public void TestAll() {
        T01_Thread log = new T01_Thread("lua");
        log.start();
        System.out.println("-------------------------");

        T02_Runable log2 = new T02_Runable();
        new Thread(log2).start();
        System.out.println("-------------------------");

        T03_Callable log3 = new T03_Callable();
        // 使用FutureTask包装
        FutureTask<String> futureTask = new FutureTask<>(log3);
        // 包装为Thread
        Thread thread = new Thread(futureTask, "futureTask");
        thread.start();

        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(log3);
    }

    @Autowired
    private C c;

    @GetMapping("test-autowired")
    public void TestThreadAutowired() {
        c.sayHi();
    }

    @Autowired
    private UserMapper userMapper;

    @GetMapping("find-size")
    public Integer findSize() {
        List<UsrUserSimple> users = userMapper.getUsers();
        Integer count = users.size();

        System.out.println("*********find-size:"+count);
        // System.out.println(users.toString());
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }

    @GetMapping("find-test")
    public Integer findTest() {
        List<UsrUserSimple> users = userMapper.getUsers();
        Integer count = users.size();

        System.out.println("*********find-test:"+count);

        return count;
    }
}
