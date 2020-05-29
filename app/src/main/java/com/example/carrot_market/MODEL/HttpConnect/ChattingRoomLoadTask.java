package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ChattingRoomLoadTask implements Runnable {
    private Handler handler;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private String id;
    public ChattingRoomLoadTask(Handler handler,String id ) {
        this.handler = handler;
        this.id=id;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }


    @Override
    public void run() {

        retrofitService.chatting_room_load(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message=new Message();
                Bundle bundle =new Bundle();
                try {
                    bundle.putString("chat_room_list",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                message.what=0;
                message.setData(bundle);
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e("chatting_room_error",""+t);
            }
        });


    }
}
