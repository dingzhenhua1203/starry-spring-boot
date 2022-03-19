package com.starry.codeview.ioLearn.niosockets;

import java.nio.IntBuffer;

public class BufferDemo {
    public static void main(String[] args) {
        // 申请一个5空间的int类型buffer
        IntBuffer buffer = IntBuffer.allocate(5);
        int i = 0;
        // buffer容器写入内容
        while (i++ < buffer.capacity()) {
            buffer.put(i);
        }

        // 切换buffer 读写状态，上面是写， 下面是读
        buffer.flip();
        // 读取buffer
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }
}
