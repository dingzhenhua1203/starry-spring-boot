package com.starry.codeview.DesignPatterns.P02_DynamicProxy;

import com.starry.common.MonitorUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理模式：
 * 代理类主要负责为委托类预处理消息、过滤消息、把消息转发给委托类，以及事后对返回结果的处理等。
 * 代理类本身并不真正实现服务，而是通过调用委托类的相关方法，来提供特定的服务。
 * 真正的业务功能还是由委托类来实现，
 * 可以在业务功能执行的前后加入一些公共的服务。
 * 例如我们想给项目加入缓存、日志、接口耗时、事务、拦截器、权限控制等这些功能，
 * 我们就可以使用代理类来完成，而没必要打开已经封装好的委托类
 */
@Slf4j
public class GlobalDynamicProxy implements InvocationHandler {
    private Object proxyInstance;

    public Object newProxyInstance(Object proxy) {
        this.proxyInstance = proxy;
        Object result = Proxy.newProxyInstance(proxy.getClass().getClassLoader(), proxy.getClass().getInterfaces(), this);
        return result;
    }

    /**
     * proxy:代表动态代理对象
     * method：代表正在执行的方法
     * args：代表调用目标方法时传入的实参
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //代理过程中插入监测方法,计算该方法耗时
        MonitorUtil.start();
        Object result = null;
        try {
            //调用目标方法 注意这里写的是类的proxyInstance，而不是传参的proxy
            result = method.invoke(proxyInstance, args);
            // 23:16:38.331 [main] INFO com.starry.codeview.DesignPatterns.P02_DynamicProxy.GlobalDynamicProxy - hahaha ,记录日志啦
            log.info("hahaha ,记录日志啦");
            return result;
        } catch (Exception e) {
            log.error("调用{}.{}发生异常", proxyInstance.getClass().getName(), method.getName(), e);
            throw e;
        } finally {
            MonitorUtil.finish(method.getName());
        }
    }
}
