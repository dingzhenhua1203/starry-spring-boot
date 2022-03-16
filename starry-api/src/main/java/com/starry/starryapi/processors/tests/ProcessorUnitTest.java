package com.starry.starryapi.processors.tests;


import com.starry.starryapi.config.StarryConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProcessorUnitTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(StarryConfig.class);
        UnitService bean = annotationConfigApplicationContext.getBean(UnitService.class);
        bean.doSomeThing();

        HelloService hello = annotationConfigApplicationContext.getBean(HelloService.class);
        hello.sayHello();
        annotationConfigApplicationContext.close();
    }

}
