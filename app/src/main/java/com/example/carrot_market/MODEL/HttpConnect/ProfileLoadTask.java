package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Bundle;
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

public class ProfileLoadTask implements Runnable {

    private android.os.Handler handler;
    private String id;
    private Retrofit retrofit;
    private RetrofitService retrofitService;

    public ProfileLoadTask(android.os.Handler handler, String id) {
        this.handler = handler;
        this.id = id;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }


    @Override
    public void run() {


        retrofitService.user_profile_load(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Bundle bundle=new Bundle();
                Message message=new Message();
                try {

                    bundle.putString("user_profile",response.body().string());
                message.what=0;
                message.setData(bundle);
                handler.sendMessage(message);
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
