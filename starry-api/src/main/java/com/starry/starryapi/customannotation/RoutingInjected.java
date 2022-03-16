package com.starry.starryapi.customannotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface RoutingInjected {
    String value() default "helloServiceImpl1";
}
