package com.jay.nettydemo.embededchanneldemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by jay on 2017/4/3.
 */
public class FixedFrameLengthDecoder extends ByteToMessageDecoder {

    private final int frameLength;

    public FixedFrameLengthDecoder(int frameLength) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= this.frameLength) {
            ByteBuf buf = in.readBytes(this.frameLength);
            out.add(buf);
        }
    }
}
