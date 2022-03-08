package com.starry.codeview.algorithms.basic;

/**
 * 写出一个打印int数字的二进制形式
 * 注意Int为32位的，Long为64位
 * 在java语言中int分正负，即有符号位，最高位表示符号位，高位为1表示负数，
 * 表示正数和负数时，只有31位可以使用，并且0也算入正数范围了，所以上限时2^31-1，而负数就是2^31
 * >> 1 表示除以2；<< 1 表示乘以2；~为取反符合  ；^ 为异或符合，相同为0，不同为1
 */
public class P01_IntBinary {

    public static void printBinary(Integer num) {
        // 注意Int为32位的，Long为64位，从左边最高位开始拼接字符串，原理时拿num&这个位置为1的二进制，如果还为1则该位就是1，否则该位为0
        String result = num < 0 ? "1" : "0";
        for (int i = 31; i > 0; i--) {
            // 求第n位就是1左移n-1位， 比如1左移1位到第二位上表示1*2=2。第二位就是1<<1 =2

            result += (num & (1 << (i - 1))) > 0 ? "1" : "0";
        }
        System.out.println(num + "=>" + result);
        System.out.println(num + "=>" + Integer.toBinaryString(num));
    }

    public static void main(String[] args) {
        printBinary(1);
        printBinary(2 >> 1);  // 右移  /2
        printBinary(2);
        printBinary(2 << 1); // 左移 *2
        printBinary(4);
        printBinary(5);
        printBinary(-1);
        printBinary(-99945);
        printBinary(~1);
        printBinary(~0);
        printBinary(Integer.MAX_VALUE);
        printBinary(Integer.MIN_VALUE);
    }
}
