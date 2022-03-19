package com.starry.starrycore;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StreamUtils {

    public static byte[] strToBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static String bytesToStr(byte[] bytes) {
        return new String(bytes);
    }

    /**
     * 针对byte数组中有效长度不足的情况
     *
     * @param bytes  字节数组
     * @param length 指定读取长度
     * @return
     */
    public static String bytesToStr(byte[] bytes, int length) {
        return new String(bytes, 0, length);
    }


    public static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
