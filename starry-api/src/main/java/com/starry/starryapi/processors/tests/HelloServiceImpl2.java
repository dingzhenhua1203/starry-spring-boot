package com.starry.starryapi.processors.tests;

import org.springframework.stereotype.Service;


// @Service
public class HelloServiceImpl2 implements HelloService {

    @Override
    public void sayHello() {
        System.out.println("Hello V2....");
    }
}

