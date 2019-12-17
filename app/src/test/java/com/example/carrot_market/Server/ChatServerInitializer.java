package com.example.carrot_market.Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslctx;

    public ChatServerInitializer(SslContext sslctx) {
        this.sslctx = sslctx;
    }


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //채팅서버의 채널을 초기화 하는 클래스 입니다.

        ChannelPipeline pipeline=ch.pipeline();

        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());

        pipeline.addLast(new EchoServerHandler());




    }
}
