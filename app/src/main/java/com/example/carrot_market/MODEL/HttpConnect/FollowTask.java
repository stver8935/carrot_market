package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class FollowTask implements Runnable {
    private String id,follow_id;
    private Handler handler;
    private Retrofit retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private RetrofitService retrofitService=retrofit.create(RetrofitService.class);

    public FollowTask(String id, String follow_id, Handler handler) {
        this.id = id;
        this.follow_id = follow_id;
        this.handler = handler;
    }

    @Override
    public void run() {
        retrofitService.follow(id,follow_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bundle bundle=new Bundle();
                Message message=new Message();
                try {
                    if (handler!=null) {
                        bundle.putString("follow_check", response.body().string());
                        message.what = 3;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
