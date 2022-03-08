package com.starry.codeview.DesignPatterns.P02_DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TopDynamicProxy<T> implements InvocationHandler {
    T proxyInstance;

    public T newProxyInstance(T obj) {
        proxyInstance = obj;
        T o = (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
        return o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(proxyInstance, args);
        return result;
    }
}
