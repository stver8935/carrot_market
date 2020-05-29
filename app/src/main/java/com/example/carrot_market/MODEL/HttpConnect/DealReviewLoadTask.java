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

public class DealReviewLoadTask implements Runnable{
    private String id,user_type;
    private int count,start_count;
    private Handler handler;
    private Retrofit retrofit;
    private RetrofitService retrofitService;

    public DealReviewLoadTask(String user_type,String id,int start_count, int count, Handler handler) {
        this.user_type=user_type;
        this.id = id;
        this.count = count;
        this.handler = handler;
        this.start_count=start_count;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    @Override
    public void run() {

        retrofitService.deal_review_load(user_type,id,start_count,count).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bundle bundle=new Bundle();
                Message message=new Message();
                try {
                    bundle.putString("review",response.body().string());
                    message.setData(bundle);
                    message.what=2;
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
