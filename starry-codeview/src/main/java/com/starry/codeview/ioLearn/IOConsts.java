package com.starry.codeview.ioLearn;

/**
 * 一般可以认为是"/"的作用等同于"\\"
 * 在java中路径一般用"/"
 * windows中的路径一般用"\"
 * linux、unix中的路径一般用"/"
 * 最好用“/”  因为java是跨平台的。“\”（在java代码里应该是\\）是windows环境下的路径分隔符，Linux和Unix下都是用“/”。而在windows下也能识别“/”。所以最好用“/”
 */
public class IOConsts {
    // public static final String BasePath = "E:\\var\\www\\uploads\\";
    public static final String BasePath = "E:/var/www/uploads/";
}
