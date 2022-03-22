package com.starry.codeview.reflactors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A is coming....
 * A is coming....aaa
 */
public class ReflactorTest {
    public static void main(String[] args) throws Exception {
        String className = "com.starry.codeview.reflactors.A";
        Class<?> aClass = Class.forName(className);
        Method method = aClass.getMethod("doTest");
        method.invoke(aClass.newInstance());

        Method method2 = aClass.getMethod("doTest2", String.class);
        method2.invoke(aClass.newInstance(), "aaa");
    }
}
