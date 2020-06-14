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

public class DealReviewCheckTask implements Runnable {
    private String id;

    private String review_id;
    private String product_key;
    private Handler handler;

    private RetrofitService retrofitService;
    private Retrofit retrofit;


    public DealReviewCheckTask(String id, String review_id, String product_key, Handler handler) {
        this.id = id;
        this.review_id = review_id;
        this.product_key = product_key;
        this.handler = handler;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }


    @Override
    public void run() {
        retrofitService.deal_review_check(id,review_id,product_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bundle bundle=new Bundle();
                Message message=new Message();
                try {
                    bundle.putString("deal_review_check",response.body().string());
                    message.setData(bundle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                message.what=10;
                handler.sendMessage(message);



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
