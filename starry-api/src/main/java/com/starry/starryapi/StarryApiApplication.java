package com.starry.starryapi;

import com.starry.starryapi.config.StarryConfig;
import com.starry.starryapi.service.TestDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@SpringBootApplication
public class StarryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarryApiApplication.class, args);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(StarryConfig.class);
        TestDemo testDemo = applicationContext.getBean(TestDemo.class);
        testDemo.doTest("哈哈哈");
        System.out.println("hashdfsd");
    }

}
