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

public class FollowProductTask implements Runnable {
    String id;
    String product_count;
    Handler handler;

    public FollowProductTask(String id, String product_count,Handler handler) {
        this.id = id;
        this.product_count = product_count;
        this.handler=handler;
    }

    Retrofit retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    RetrofitService retrofitService=retrofit.create(RetrofitService.class);

    @Override
    public void run() {
        retrofitService.follow_product(id,product_count).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Bundle bundle=new Bundle();
                Message message=new Message();
                try {

                    bundle.putString("follow_product_list",response.body().string());
                    message.setData(bundle);
                    message.what=0;
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
