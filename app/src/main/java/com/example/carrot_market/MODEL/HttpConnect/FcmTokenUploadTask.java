package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Handler;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class FcmTokenUploadTask implements Runnable {
    String id ;
    String token;
    Handler handler;

    RetrofitService retrofitService;
    Retrofit retrofit;

    public FcmTokenUploadTask(String id, String token, Handler handler) {
        this.id = id;
        this.token = token;
        this.handler = handler;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);

    }


    @Override
    public void run() {

        retrofitService.fcm_token_upload(id,token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}
