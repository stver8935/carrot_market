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

public class UserUnblockTask implements Runnable {

    private Handler handler;
    private String id,block_id;
    private RetrofitService retrofitService;
    private Retrofit retrofit;


    public UserUnblockTask(String id, String block_id,Handler handler) {
        this.id=id;
        this.block_id=block_id;
        this.handler=handler;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    @Override
    public void run() {

        retrofitService.user_unblock(id,block_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
