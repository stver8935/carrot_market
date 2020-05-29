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

public class FavoriteProductListTask implements Runnable {
    private String id;
    private int product_count;
    private Handler handler;
    private Retrofit retrofit;
    private RetrofitService retrofitService;

    public FavoriteProductListTask(String id,int product_count, android.os.Handler handler) {
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);

        this.product_count=product_count;
        this.id = id;
        this.handler = handler;
    }


    @Override
    public void run() {

        retrofitService.favorite_product_list(id,product_count).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bundle bundle=new Bundle();
                Message message=new Message();
                try {

                    Log.e("response_ok","ok");
                    bundle.putString("favorite_product_list",response.body().string());
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
