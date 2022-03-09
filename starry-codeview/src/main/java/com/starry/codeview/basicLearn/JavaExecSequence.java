package com.starry.codeview.basicLearn;

/**
 * 执行结果：
 * 静态块
 * 构造块
 * 构造方法
 * hi
 * 构造块
 * 构造方法
 *
 * 结论
 * 静态块只会执行一次
 */
public class JavaExecSequence {

    private static String str = "h";

    public JavaExecSequence() {
        System.out.println("构造方法");
    }

    static {
        str += "i";
        System.out.println("静态块");
    }

    {
        System.out.println("构造块");
    }

    public static void main(String[] args) {
        JavaExecSequence a = new JavaExecSequence();
        System.out.println(a.str);
        JavaExecSequence b = new JavaExecSequence();
    }
}
