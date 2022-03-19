package com.starry.codeview.ioLearn;

import com.starry.starrycore.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileDemo {

    public static void fileWrite(String path, String str) throws IOException {
        FileOutputStream file = new FileOutputStream(path);
        file.write(str.getBytes(StandardCharsets.UTF_8));
        file.close();
    }

    public static void nioFileRead(String path) throws IOException {

        File file = new File(path);
        // FileInputStream file = new FileInputStream(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        // 构建通道
        FileChannel fileChannel = fileInputStream.getChannel();
        // 缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 开始读
        int read = fileChannel.read(buffer);
        buffer.flip();
        System.out.println(StreamUtils.bytesToStr(buffer.array(), buffer.limit()));

    }

    public static void main(String[] args) throws IOException {
        String path = IOConsts.BasePath.concat("a.txt");
        fileWrite(path, "123asd222");
        nioFileRead(path);
    }
}
