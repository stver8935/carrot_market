package com.example.carrot_market.MODEL.HttpConnect;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ExitChattingRoomTask implements Runnable {
    private String id;
    private String chatting_room_key;
    private RetrofitService retrofitService;
    private Retrofit retrofit;


    public ExitChattingRoomTask(String id, String chatting_room_key) {
        this.id = id;
        this.chatting_room_key = chatting_room_key;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);

    }


    @Override
    public void run() {


    }
}
