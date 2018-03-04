package com.jay.nettydemo.telnet;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by jay on 2017/3/26.
 */
public class TelnetClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8087"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap clientBootstrap = new Bootstrap();
            clientBootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new TelnetClientInitializer());

            Channel ch = clientBootstrap.connect(HOST, PORT).sync().channel();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            ChannelFuture lastFuture = null;
            for (;;) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }

                lastFuture = ch.writeAndFlush(line + "\r\n");

                if ("bye".equals(line)) {
                    ch.closeFuture().sync();
                    break;
                }
            }

            if (lastFuture != null) {
                lastFuture.sync();
            }

        } finally {
            group.shutdownGracefully();
        }
    }
}
