package com.starry.codeview.basicLearn;

/**
 * Finial的理解
 * 只可以赋值一次  否则编译不通过，直接语法爆红
 */
public class FinalCharpter {
    public final int a;
    public final int b = 1;
    public final int c = 2;
    public final static int d;
    public final static int e = 5;

    static {
        d = 7;
        // e = 19;  // 不可以，编译不通过
    }


    public FinalCharpter() {
        a = 111;
        // b = 234;  // 不可以，编译不通过

    }
}
