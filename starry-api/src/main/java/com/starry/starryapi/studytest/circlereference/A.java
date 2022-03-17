package com.starry.starryapi.studytest.circlereference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class A {

    @Autowired
    private B b;

    public void sayHello(){
        b.sayB();
    }

    public void sayA(){
        System.out.println("hi,我是A");
    }
}
