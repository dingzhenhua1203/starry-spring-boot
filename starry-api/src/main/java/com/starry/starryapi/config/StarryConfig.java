package com.starry.starryapi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
// @ComponentScan({"com.starry.starryapi.processors", "com.starry.starryapi.processors.tests"})
@ComponentScan("com.starry.starryapi.processors")
@Import(StarryConfig2.class)
public class StarryConfig {


}
