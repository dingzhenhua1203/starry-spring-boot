package com.starry.codeview;

/**
 * 输出结果为
 * 8
 * 9
 * 9
 * 涉及到底层知识，方法压栈的过程
 * 0 bipush 8
 * 2 istore_1
 * 3 iload_1
 * 4 iinc 1 by 1
 * 7 istore_1
 * 8 getstatic #2 <java/lang/System.out>
 * 11 iload_1
 * 12 invokevirtual #3 <java/io/PrintStream.println>
 * 15 return
 *   * 参考看图
 *   * https://img.dcmickey.cn/ImgSource/20220309164049.png
 *   * https://img.dcmickey.cn/ImgSource/20220309164837.png
 * 分析：
 * 8是因为，
 * 首先i=8,
 * 然后iload_1将局部变量表1位置的值也就是8用重新压入栈，
 * 执行i++操作 也就是 iinc 1 by 1是对i进行i++ 变成9,而压入栈的那个8并没有变化
 * 然后执行赋值动作 将压入栈的8赋值给i, 所以i从9又变成了8
 * 所以结果是8
 */
public class P02_PlusPlus {
    public static void main(String[] args) throws InterruptedException {
        int i = 8;
        i = (i++);  // i=i 也就是8， 然后i++
        Thread.sleep(2000);
        System.out.println(i);  // fucking 居然是8
        /*---------------------------------------------*/
        int j = 8;
        j++;
        System.out.println(j);  // 9
        /*---------------------------------------------*/
        int t = 8;
        t = ++t; // t++ 后 然后t=t
        System.out.println(t);  // 9

        /**
         * 输出结果为
         * 8
         * 9
         * 9
         */
    }
}
