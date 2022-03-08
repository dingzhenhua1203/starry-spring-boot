package com.starry.codeview.DesignPatterns.P01_ProxyPattern;

/**
 * 代理模式：
 * 这里是静态代理（限定死了） ，主要是区别于动态代理（依靠反射做的代理）
 * 代理类主要负责为委托类预处理消息、过滤消息、把消息转发给委托类，以及事后对返回结果的处理等。
 * 代理类本身并不真正实现服务，而是通过调用委托类的相关方法，来提供特定的服务。
 * 真正的业务功能还是由委托类来实现，
 * 可以在业务功能执行的前后加入一些公共的服务。
 * 例如我们想给项目加入缓存、日志、接口耗时、事务、拦截器、权限控制等这些功能，
 * 我们就可以使用代理类来完成，而没必要打开已经封装好的委托类
 *
 * 缺点
 * 1.代理类和委托类实现了相同的接口，代理类通过委托类实现了相同的方法。这样就出现了大量的代码重复。
 * 2.如果接口增加一个方法，除了所有实现类需要实现这个方法外，所有代理类也需要实现此方法。增加了代码维护的复杂度
 * 3.代理对象只服务于一种类型的对象，如果要代理多个对象,就需要再添加其他类的代理
 */
public class MoneyServiceProxy implements MoneyService {

    MoneyService moneyService;


    private MoneyServiceProxy() {
        // 这里可以设置默认代理自己,没啥意义
        moneyService = new MoneyServiceProxy();
    }

    public MoneyServiceProxy(MoneyService _moneyService) {
        moneyService = _moneyService;
    }

    @Override
    public void sendMoney() {
        before();
        moneyService.sendMoney();
        after();
    }


    /**
     * 预处理
     */
    private void before() {
        // do something
    }

    /**
     * 后处理
     */
    private void after() {
        // do something
    }
}
