package com.starry.starryapi.service;

import com.starry.starryapi.config.StarryConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 解决ThreadLocalTest弱引用会造成的内存泄漏问题
 * 1.使用完线程共享变量后，显示调用 ThreadLocalMap.remove 方法清除线程共享变量。
 * 2.JDK 建议 ThreadLocal 定义为 private static，这样 ThreadLocal 的弱引用问题则不存在了
 * <p>
 * 执行结果：
 * doThreadLocal1...main获取值：parentVal
 * doThreadLocal1...main获取值：parentValTest2
 * doThreadLocal2...thread-local-luffy获取值：parentVal
 * doThreadLocal2...thread-local-luffy获取值：null
 * doThreadLocal3...thread-local-luffy获取值：TestDemo(name=张三, id=thread-local-luffy)
 * doThreadLocal3...thread-local-luffy获取值：A thread local value
 */
@Service
public class ThreadLocalTest {
    private static ThreadLocal<String> parentLocal = new InheritableThreadLocal<>(); // 这个可以父子线程中共享
    private static ThreadLocal<String> parentLocal2 = new ThreadLocal<>();  // 这个只能当前线程
    private static ThreadLocal<TestDemo> testThreadLocal = new ThreadLocal<>();

    public void doThreadLocal() {
        parentLocal.set("parentVal");
        parentLocal2.set("parentValTest2");
        doThreadLocal1();
        new Thread(this::doThreadLocal2, "thread-local-luffy").start();

    }

    public void doThreadLocal1() {
        System.out.println("doThreadLocal1..." + Thread.currentThread().getName() + "获取值：" + parentLocal.get());
        System.out.println("doThreadLocal1..." + Thread.currentThread().getName() + "获取值：" + parentLocal2.get());
    }

    public void doThreadLocal2() {
        System.out.println("doThreadLocal2..." + Thread.currentThread().getName() + "获取值：" + parentLocal.get());
        System.out.println("doThreadLocal2..." + Thread.currentThread().getName() + "获取值：" + parentLocal2.get());
        TestDemo s = new TestDemo();
        s.setName("张三").setId(Thread.currentThread().getName());
        testThreadLocal.set(s);
        parentLocal2.set("A thread local value");

        this.doThreadLocal3();
    }

    public void doThreadLocal3() {
        System.out.println("doThreadLocal3..." + Thread.currentThread().getName() + "获取值：" + testThreadLocal.get());
        System.out.println("doThreadLocal3..." + Thread.currentThread().getName() + "获取值：" + parentLocal2.get());
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(StarryConfig.class);
        ThreadLocalTest testDemo = applicationContext.getBean(ThreadLocalTest.class);
        testDemo.doThreadLocal();
    }
}
