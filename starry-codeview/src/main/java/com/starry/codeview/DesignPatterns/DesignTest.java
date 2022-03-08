package com.starry.codeview.DesignPatterns;

import com.starry.codeview.DesignPatterns.P01_ProxyPattern.MoneyService;
import com.starry.codeview.DesignPatterns.P02_DynamicProxy.GlobalDynamicProxy;
import com.starry.codeview.DesignPatterns.P01_ProxyPattern.MoneyServiceImpl;
import com.starry.codeview.DesignPatterns.P01_ProxyPattern.MoneyServiceProxy;
import com.starry.codeview.DesignPatterns.P02_DynamicProxy.TopDynamicProxy;

public class DesignTest {
    public static void main(String[] args) {
        System.out.println("-------静态代理模式--------------------");
        MoneyServiceProxy proxy = new MoneyServiceProxy(new MoneyServiceImpl());
        proxy.sendMoney();
        System.out.println("-------动态代理模式--------------------");
        GlobalDynamicProxy dynamicProxy = new GlobalDynamicProxy();
        // 动态代理 返回的是父类类型
        // MoneyServiceImpl o1 = (MoneyServiceImpl) dynamicProxy.initProxy(new MoneyServiceImpl());
        MoneyService o = (MoneyService) dynamicProxy.newProxyInstance(new MoneyServiceImpl());
        o.sendMoney();
        System.out.println("-------泛型动态代理模式--------------------");
        // 只能用父类来接收子类
        TopDynamicProxy<MoneyService> moneyServiceTopDynamicProxy = new TopDynamicProxy<>();
        MoneyService init = moneyServiceTopDynamicProxy.newProxyInstance(new MoneyServiceImpl());
        init.sendMoney();
    }
}
