package com.example.carrot_market.MODEL.HttpConnect;

import android.util.Log;

import io.netty.channel.ChannelFuture;

import static com.example.carrot_market.MODEL.CHATTINGSERVICE.ChattingService.socketchannel;

public class SendMessageTask implements Runnable {
    private String message;
    public SendMessageTask(String message) {
        this.message = message;
    }


    @Override
    public void run() {

        try {

            if (socketchannel!=null){
            ChannelFuture lastWriteFuture = null;
            lastWriteFuture = socketchannel.writeAndFlush(message+"\n");
            lastWriteFuture.sync();
            }else {
                Log.e("채팅서버 에러","채팅서버에 채널이 없습니다.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

