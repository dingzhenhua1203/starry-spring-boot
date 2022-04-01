package com.starry.codeview.reflactors;

import com.starry.codeview.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A is coming....
 * A is coming....aaa
 */
public class ReflactorTest {

    public static Object copy(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        System.out.println("Class:" + clazz.getName());

        //通过默认构造方法创建一个新的对象
        Object objectCopy = clazz.getConstructor(new Class[]{}).newInstance(new Object[]{});

        //获得对象的所有属性
        Field fields[] = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            //获得和属性对应的getXXX()方法的名字
            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            //获得和属性对应的setXXX()方法的名字
            String setMethodName = "set" + firstLetter + fieldName.substring(1);

            //获得和属性对应的getXXX()方法
            Method getMethod = clazz.getMethod(getMethodName, new Class[]{});
            //获得和属性对应的setXXX()方法
            Method setMethod = clazz.getMethod(setMethodName, new Class[]{field.getType()});

            //调用原对象的getXXX()方法
            Object value = getMethod.invoke(obj, new Object[]{});
            System.out.println(fieldName + ":" + value);
            //调用拷贝对象的setXXX()方法
            setMethod.invoke(objectCopy, new Object[]{value});
        }
        return objectCopy;
    }


    public static void main(String[] args) throws Exception {
        String className = "com.starry.codeview.reflactors.A";
        Class<?> clazz = Class.forName(className);
        Constructor<?> clazzDeclaredConstructor = clazz.getDeclaredConstructor();
        A o = (A) clazzDeclaredConstructor.newInstance();
        Method method = clazz.getMethod("doTest");
        method.invoke(clazz.newInstance());

        Method method2 = clazz.getMethod("doTest2", String.class);
        method2.invoke(clazz.newInstance(), "aaa");

        Object obj = new User();
        try {
            final Object copy = copy(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
