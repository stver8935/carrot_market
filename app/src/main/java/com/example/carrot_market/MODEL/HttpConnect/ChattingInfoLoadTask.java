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

public class ChattingInfoLoadTask implements Runnable {
    String id;
    String product_key;
    Handler handler;
    RetrofitService retrofitService;
    Retrofit retrofit;

    public ChattingInfoLoadTask(String id, String product_key, Handler handler) {
        this.id = id;
        this.product_key = product_key;
        this.handler = handler;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    @Override
    public void run() {

        retrofitService.chatting_info_load(id,product_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bundle bundle=new Bundle();
                try {
                    bundle.putString("chatting_info",response.body().string());
                    Message message=new Message();
                    message.what=1;
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("chatting_info_load","error"+t);
            }
        });





    }

}
