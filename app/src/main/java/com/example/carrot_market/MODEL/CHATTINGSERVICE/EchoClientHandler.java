package com.example.carrot_market.MODEL.CHATTINGSERVICE;


import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;

import android.os.Message;


import androidx.core.app.NotificationCompat;

import com.example.carrot_market.CONTROLLER.Chatting;
import com.example.carrot_market.CONTROLLER.ChattingList;
import com.example.carrot_market.MODEL.LOCALMODEL.DBHelper;
import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.ChattingItem;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;




public class EchoClientHandler extends ChannelInboundHandlerAdapter {




    @Override
    public void channelRead(ChannelHandlerContext arg0, Object arg1) throws Exception {



        Context context;
        context=  Chatting.context;

        DBHelper dbHelper=new DBHelper(context,"CHAT_LIST.db",null,1);

        dbHelper.insert(arg1.toString(),arg1.toString());


        System.out.println((String) arg1);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("알림 제목");
        builder.setContentText(""+arg1.toString());

        NotificationManager notificationManager= (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());

        ChattingItem item=new ChattingItem();
        item.setContents(arg1.toString());
        Chatting.arrayList_c.add(item);
        
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }





}
