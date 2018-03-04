package com.jay.nettydemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Created by jay on 2017/4/2.
 */
public class ByteBufDemo {
    public static void main(String[] args) {
        Charset charset = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in action rocks", charset);

        System.out.println((char) buf.getByte(0));
        int readerIndex = buf.readerIndex();
        int writeIndex = buf.writerIndex();

        buf.setByte(0, 'B');

        assert readerIndex == buf.readerIndex();
        assert writeIndex == buf.writerIndex();
    }
}
