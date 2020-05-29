package com.example.carrot_market.MODEL.CHATTINGSERVICE;

import android.content.Context;
import android.os.Handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class ChatClientinitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;
    private  Context context;


    public ChatClientinitializer(SslContext sslCtx, Context context,Handler handler) {
        this.sslCtx = sslCtx;
        this.context=context;
    }

    @Override
    protected void initChannel(SocketChannel arg0) {
        ChannelPipeline pipeline = arg0.pipeline();

        //pipeline.addLast(sslCtx.newHandler(arg0.alloc(), ChatClient.HOST, ChatClient.PORT));
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new EchoClientHandler());
    }


}
