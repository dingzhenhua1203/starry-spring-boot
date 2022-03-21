package com.starry.starryapi.events;

import org.springframework.context.ApplicationEvent;

/**
 * ApplicationEvent&Listener完成业务解耦
 * ApplicationEvent以及Listener是Spring为我们提供的一个事件监听、订阅的实现，
 * 内部实现原理是观察者设计模式，设计初衷也是为了系统业务逻辑之间的解耦，提高可扩展性以及可维护性。
 * 事件发布者并不需要考虑谁去监听，监听具体的实现内容是什么，发布者的工作只是为了发布事件而已。
 */
public class CustomLogEvent extends ApplicationEvent {
    public CustomLogEvent(Object source) {
        super(source);
    }
}
