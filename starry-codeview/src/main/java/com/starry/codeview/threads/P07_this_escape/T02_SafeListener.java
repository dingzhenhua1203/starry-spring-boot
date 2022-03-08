package com.starry.codeview.threads.P07_this_escape;

/**
 * 正确构造过程
 * 在这个构造中，我们看到的最大的一个区别就是：当构造好了SafeListener对象（通过构造器构造）之后，我们才启动了监听线程，也就确保了SafeListener对象是构造完成之后再使用的SafeListener对象。
 *
 * 对于这样的技术，书里面也有这样的注释：
 *
 * 具体来说，只有当构造函数返回时，this引用才应该从线程中逸出。构造函数可以将this引用保存到某个地方，只要其他线程不会在构造函数完成之前使用它
 */
public class T02_SafeListener {
    private final EventListener listener;

    private T02_SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    public static T02_SafeListener newInstance(EventSource source) {
        T02_SafeListener safe = new T02_SafeListener();
        source.registerListener(safe.listener);
        return safe;
    }

    void doSomething(Event e) {
    }

    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
}
