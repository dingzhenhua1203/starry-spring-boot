package com.starry.starryapi.processors.tests;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 执行顺序
 * static静态代码块
 * a....10
 * b....0
 * 构造方法
 * 属性注入值
 * postProcess...BeforeInitialization.....
 *
 * @PostConstruct（init-method）.... 程序真正调用的方法
 * postProcess...AfterInitialization.....
 * doSomeThing....
 * @PreDestroy（destory-method）....
 */
@Service
public class UnitService {
    private int a = 1;
    private int b;


    @Value("10086")
    public void setB(int b) {
        this.b = b;
        System.out.println("属性b注入值...." + b);
    }

    static {
        System.out.println("static静态代码块");
    }

    public UnitService() {
        a = 10;
        System.out.println("a...." + a);
        System.out.println("b...." + b);
        System.out.println("ProcessorUnitTest构造方法");
    }

    public void doSomeThing() {
        System.out.println("doSomeThing....");
    }

    // 并且只会被服务器调用一次
    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct（init-method）....");
    }

    /**
     * 对象用完不会执行这个，只是相当于将使用权给上交给一级缓存，不然其他服务想用就没得用了。只有容器销毁，单例bean才销毁
     * 只适用于单例对象，  原型对象不生效
     */
    @PreDestroy
    public void cusDistory() {
        System.out.println("@PreDestroy（destory-method）....");
    }
}
