package com.starry.starryapi.studytest.circlereference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class B {

    @Autowired
    @Lazy
    private A a;

    public void sayCircle1() {
        System.out.println("sayCircle1");
        a.sayA();
    }

    public void sayCircle2() {
        System.out.println("sayCircle2");
        A a1 = BeanUtil.getBean(A.class);
        System.out.println(a1.toString());
        a1.sayA();
    }

    public void sayB() {
        System.out.println("hi,我是B");
    }
}
