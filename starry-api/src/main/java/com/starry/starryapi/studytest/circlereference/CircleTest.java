package com.starry.starryapi.studytest.circlereference;


import com.starry.starryapi.config.StarryConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 循环依赖报错
 * org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'b': Unsatisfied dependency expressed through field 'a';
 * nested exception is org.springframework.beans.factory.BeanCurrentlyInCreationException:
 * Error creating bean with name 'a': Requested bean is currently in creation: Is there an unresolvable circular reference?
 * at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.resolveFieldValue(AutowiredAnnotationBeanPostProcessor.java:659)
 */
public class CircleTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(StarryConfig.class);
        A a = ac.getBean(A.class);
        a.sayHello();
        B b = ac.getBean(B.class);
        b.sayCircle1();
        b.sayCircle2();
    }
}
