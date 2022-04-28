package com.starry.codeview.whatTransmit;

public class WhatTransmitTest {
    public static void main(String[] args) {
        Food food = new Food(100, "米饭");
        Animal animal = new Animal(1, "鸭鸭", food);
        System.out.println(animal.toString()); // Animal(no=1, name=鸭鸭, food=Food(no=100, name=米饭))
        doSomething(animal);
        System.out.println(animal.toString()); // Animal(no=2, name=小鸡, food=Food(no=200, name=面条))
        doSomething2(animal);
        System.out.println(animal.toString()); // Animal(no=2, name=小鸡, food=Food(no=200, name=面条))
    }

    /**
     * 注意此方法是用的同一个内存地址，修改操作针对的是同一个内存地址
     * @param animal
     */
    public static void doSomething(Animal animal) {
        Food food = new Food(200, "面条");
        animal.setNo(2);
        animal.setName("小鸡");
        animal.setFood(food);
    }

    /**
     * 注意此方法是重新将形参指向新内存地址，修改操作针对的是新内存地址
     * @param animal
     */
    public static void doSomething2(Animal animal) {
        Food food = new Food(300, "方便面");
        animal = new Animal(); // 差异点
        animal.setNo(3);
        animal.setName("小牛");
        animal.setFood(food);
    }
}
