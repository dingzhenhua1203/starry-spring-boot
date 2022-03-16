package com.starry.starryapi.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 自定义后置处理器
 */
// @Component
public class MyBeanPostProcessor implements BeanPostProcessor, Ordered {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcess...BeforeInitialization....."+beanName);
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcess...AfterInitialization....."+beanName);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    /**
     * 默认值为 0，优先级最高，值越大优先级越低
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
