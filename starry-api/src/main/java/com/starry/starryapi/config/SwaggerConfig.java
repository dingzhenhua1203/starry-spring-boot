package com.starry.starryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*可以有参 可以无参， 传入Environment判断环境参数*/
    @Bean
    public Docket getJdbcDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                // apiInfo 配置swagger文档描述信息
                .apiInfo(new ApiInfoBuilder()
                        .title("JDBC文档")
                        .description("这是一个JDBC的API接口文档")
                        .version("1.0")
                        .license("The Apache License")
                        .build())
                //  select 配置接口范围
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.starry.controller"))
                .paths(PathSelectors.ant("/jdbc/**"))
                .build()
                .groupName("JDBC")
                .enable(true);
    }

    @Bean
    public Docket getTaskJobDecket(Environment env) {
        // 设置哪些环境启用swagger
        Profiles profiles=Profiles.of("dev | test");
        // env.acceptsProfiles判断是否出在配置的环境中
        boolean enableFlag = env.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                // apiInfo 配置swagger文档描述信息
                .apiInfo(new ApiInfoBuilder()
                        .title("TaskJob文档")
                        .description("这是一个TaskJob的API接口文档")
                        .version("1.0")
                        .license("The Apache License")
                        .build())
                //  select 配置接口范围
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.starry.controller"))
                .build()
                .groupName("TaskJob")
                .enable(enableFlag);
    }
}
