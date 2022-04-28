package com.starry.codeview.collectionStudy;

import java.util.*;

public class LinkedListStudy {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 3);

        Integer[] integers1 = {1, 2, 3};
        List<Integer> integers2 = Arrays.asList(integers1);
        List<Integer> integers3 = Arrays.asList(new Integer[]{12, 3});

        LinkedList linkedList = new LinkedList();
        // 泛型的约束不能插入不同类型，除非反射
        LinkedList<Integer> linkedList2 = new LinkedList();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(5);
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");

        System.out.println(linkedList.isEmpty());
        System.out.println("**********************新增****************************");
        linkedList.addFirst(-1); // void返回值 方法
        linkedList.addLast(6);
        linkedList.addAll(Arrays.asList(new Integer[]{101, 102}));
        // offer内部就是调用的对应的add、addFirst、addLast,offer相当于适配器，基于void的add方法始终返回true
        linkedList.offer(999);  // boolean返回值 方法
        linkedList.offerFirst(888);
        linkedList.offerLast(888);

        linkedList.push(1); // 底层是调用的addFirst，相当于适配器

        System.out.println("**********************修改****************************");
        linkedList.set(1, "T");

        System.out.println("**********************弹出****************************");
        linkedList.poll(); // null 的时候不会抛出异常， 就是pollFirst
        linkedList.pollFirst();
        linkedList.pollLast();
        linkedList.remove(); // 就是调用的removeFirst()，null 的时候throw new NoSuchElementException();
        linkedList.removeFirst();
        linkedList.removeLast();
        // public E pop() {return removeFirst();}
        linkedList.pop(); // 就是调用的removeFirst()


        System.out.println("**********************查询****************************");
        // 不会有异常
        linkedList.peek();
        linkedList.peekFirst();
        linkedList.peekLast();
        // get方式获取会存在异常 throw new NoSuchElementException();
        linkedList.get(1);
        linkedList.getFirst();
        linkedList.getLast();

        linkedList.element(); // 就是getFirst();
    }
}
