package com.starry.codeview.ioLearn.niosockets.channels;

import com.starry.codeview.ioLearn.IOConsts;
import com.starry.starrycore.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class NIOFileChannelDemo {

    public static void nioWrite(String path, String content) throws Exception {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            FileChannel channel = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(content.getBytes(StandardCharsets.UTF_8));

            byteBuffer.flip();

            // 从buffer数据写入channel
            int write = channel.write(byteBuffer);
        } catch (Exception e) {

        } finally {
            StreamUtils.closeStream(fileOutputStream);
        }

    }

    public static String nioRead(String path) throws Exception {
        String respStr = "";
        FileInputStream fileInputStream = null;
        try {
            File file = new File(path);
            // fileInputStream = new FileInputStream(path);
            fileInputStream = new FileInputStream(file);
            FileChannel channel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
            int read = channel.read(byteBuffer);

            byteBuffer.flip();

            respStr = StreamUtils.bytesToStr(byteBuffer.array(), byteBuffer.limit());
            fileInputStream.close();
            System.out.println(respStr);
            return respStr;
        } catch (Exception e) {

        } finally {
            StreamUtils.closeStream(fileInputStream);
        }
        return respStr;
    }

    /**
     * 【循环读取方式】利用fileChannel将文件复制到另一个地方，只能使用一个byteBuffer容器来实现
     *
     * @param sourcePath
     * @param targetPath
     */
    public static void copyFile(String sourcePath, String targetPath) {
        // 1.读取文件
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel channel = null;
        FileChannel writeChannel = null;
        try {
            fileInputStream = new FileInputStream(sourcePath);
            channel = fileInputStream.getChannel();
            fileOutputStream = new FileOutputStream(targetPath);
            writeChannel = fileOutputStream.getChannel();
            /*
            因为读取文件不可能知道文件有多大，所以这里申请的容器大小是没法估量的，
            并且考虑系统性能和内存大小，也不能一次性读出来
            所以需要循环来读取
             */
            ByteBuffer byteBuffer = ByteBuffer.allocate(8);
            while (channel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                writeChannel.write(byteBuffer);
                // 每次都要讲buffer重置，否则下次没法读取
                byteBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writeChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            StreamUtils.closeStream(fileInputStream);
            StreamUtils.closeStream(fileOutputStream);
        }
    }

    public static void copyFile_useTransfer(String sourcePath, String targetPath) {
        // 1.读取文件
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel channel = null;
        FileChannel writeChannel = null;
        try {
            fileInputStream = new FileInputStream(sourcePath);
            channel = fileInputStream.getChannel();
            fileOutputStream = new FileOutputStream(targetPath);
            writeChannel = fileOutputStream.getChannel();
            channel.transferTo(0, channel.size(), writeChannel);
            // writeChannel.transferFrom(channel, 0, channel.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writeChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            StreamUtils.closeStream(fileInputStream);
            StreamUtils.closeStream(fileOutputStream);
        }
    }

    public static void main(String[] args) throws Exception {
        String path = IOConsts.BasePath.concat("file1.txt");
        nioWrite(path, "hello,你好呀，我是nio01,我正在学习netty。祝我早日找到好公司把。");
        nioRead(path);
        copyFile(path, IOConsts.BasePath.concat("file2.txt"));
        copyFile_useTransfer(IOConsts.BasePath.concat("张钧甯.jpg"), IOConsts.BasePath.concat("abc.jpg"));
    }
}
