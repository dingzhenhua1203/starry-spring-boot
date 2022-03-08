package com.starry.codeview;

public class P01_ClassLoader {
    public static void main(String[] args) {
        /**
         * 调用T.count的时候，会通过类加载器将T加载到JVM中，这是第一步loading
         * 第二步Linking， 1.验证是否JVM文件规范 2.赋静态成员变量的默认值 3.resolution 不重要
         * 第三步Initializing 给静态成员变量附上初始值
         * 那么count初始值为2，t的初始值为new T(),触发构造函数进行count++
         * 所以打印结果为3
         */
        System.out.println(T.count);  // 输出几？？？    答案是3！！！


        /**
         * 调用B.count的时候，会通过类加载器将T加载到JVM中，这是第一步loading
         * 第二步Linking， 1.验证是否JVM文件规范 2.赋静态成员变量的默认值 3.resolution 不重要
         * 第三步Initializing 给静态成员变量附上初始值
         * 那么先是t的初始值为new T(),触发构造函数进行count++ 此时count变为1了。在给count初始值，count变为2
         * 所以打印结果为2
         */
        System.out.println(B.count);  // 输出几？？？     答案是2！！！
    }
}

class T {
    public static int count = 2;
    public static T t = new T();

    private T() {
        count++;
    }
}

class B {
    public static B t = new B();
    public static int count = 2;

    private B() {
        count++;
    }
}
