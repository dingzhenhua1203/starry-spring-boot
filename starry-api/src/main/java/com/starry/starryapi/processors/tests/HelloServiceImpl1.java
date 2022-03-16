package com.starry.starryapi.processors.tests;

import com.starry.starryapi.customannotation.RoutingInjected;
import org.springframework.stereotype.Service;


@Service
public class HelloServiceImpl1 implements HelloService {

    @Override
    public void sayHello() {
        System.out.println("Hello V1....");
    }
}
