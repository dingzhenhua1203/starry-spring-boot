package com.starry.starryapi.controller;

import com.starry.starryapi.service.UnitTestService;
import com.starry.codeview.jucstudy.LogThread;
import com.starry.codeview.jucstudy.LogThread2;
import com.starry.codeview.jucstudy.LogThread3;
import com.starry.starrycore.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("test-all")
    public void TestAll() {
        LogThread log = new LogThread();
        log.start();
        System.out.println("-------------------------");

        LogThread2 log2 = new LogThread2("luffy");
        log2.start();
        LogThread3 log3 = new LogThread3();
        new Thread(log3).start();
    }
}
