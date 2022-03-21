package com.starry.starryapi.service;

import com.starry.starryapi.annotations.LogRecord;
import com.starry.starryapi.config.StarryConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class TestDemo {

    private String name;

    private String id;

    @LogRecord(methodName = "测试", logType = "Debug")
    public String doTest(String name) {
        System.out.println("执行dotest:" + name);
        return "dotestReturn";
    }


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(StarryConfig.class);
        TestDemo testDemo = applicationContext.getBean(TestDemo.class);
        testDemo.doTest("哈哈哈");
    }
}
