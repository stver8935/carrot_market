package com.example.carrot_market.Server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded of [SERVER]");
        Channel incoming = ctx.channel();
        for (Channel channel : channelGroup) {
            //사용자가 추가되었을 때 기존 사용자에게 알림

            channel.write("[SERVER] - " + incoming.remoteAddress()+ "참여 하셨습니다!\n");
        }
        channelGroup.add(incoming);
    }



    //사용자가 접속하여 채널이 활성화 됬을때 실행되는 메서드
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 사용자가 접속했을 때 서버에 표시.

    }


    //사용자가 접속을 종료하여 채널그룹에서 삭제 되었을때 호출 되는 메서드
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved of [SERVER]");
        Channel incoming = ctx.channel();


            //사용자가 나갔을 때 기존 사용자에게 알림

            channelGroup.write("[SERVER] - " + incoming.remoteAddress() + "나가 셨습니다!\n");
            //나갔다는 문자를 바로 적용해 준다.
            channelGroup.flush();
            //채널 그룹에서 incoming이라는 채널을 지워준다.
        channelGroup.remove(incoming);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = null;
        message = (String)msg;
        System.out.println("channelRead of [SERVER]" +  message);
        Channel incoming = ctx.channel();
        for (Channel channel : channelGroup) {
            if (channel != incoming) {
                //메시지 전달.
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + message + "\n");
            }
        }

        //사용자가 bye 라는 문자열을 쳤을때 그사용자는 채널에서 나가진다.
        if ("bye".equals(message.toLowerCase())) {
            ctx.close();
        }
    }

}
