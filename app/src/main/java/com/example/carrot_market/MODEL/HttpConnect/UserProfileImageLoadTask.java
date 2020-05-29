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

public class UserProfileImageLoadTask implements Runnable{

    Handler handler;
    Retrofit retrofit;
    RetrofitService retrofitService;
    String id;
    public UserProfileImageLoadTask(String id,Handler handler) {
        this.id=id;
        this.handler=handler;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    @Override
    public void run() {

        retrofitService.user_profile_image_load(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message=new Message();
                Bundle bundle=new Bundle();
                try {
                    bundle.putString("user_profile_image",response.body().string());
                    message.what=0;
                    message.setData(bundle);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                handler.sendMessage(message);

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });





    }
}
