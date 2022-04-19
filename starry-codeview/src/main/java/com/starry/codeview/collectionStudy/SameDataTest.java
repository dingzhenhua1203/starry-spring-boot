package com.starry.codeview.collectionStudy;

import java.util.HashSet;

public class SameDataTest {
    public static void main(String[] args) {
        String a = "abc";
        String b = "abc";
        String c = new String("haha");
        String d = new String("haha");

        System.out.println("a==b " + (a == b)); // a==b true
        System.out.println("a.equals(b)" + a.equals(b)); // a==b true
        System.out.println("c==d " + (c == d)); // c==d false
        System.out.println("c.equals(d) " + c.equals(d)); // c.equals(d) true

        System.out.println("*******************************");
        HashSet hashSet = new HashSet();
        System.out.println(hashSet.add(a)); // true
        System.out.println(hashSet.add(b)); // false
        System.out.println(hashSet.add(c)); // true
        System.out.println(hashSet.add(d)); // false

    }
}
