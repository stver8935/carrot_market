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

public class PurchaseHistoryTask implements Runnable {
    private String id,count;
    private RetrofitService retrofitService;
    private Retrofit retrofit;
    private Handler handler;

    public PurchaseHistoryTask(String id, String count,Handler handler) {
        this.handler=handler;
        this.id = id;
        this.count = count;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }



    @Override
    public void run() {
        retrofitService.purchase_history_load(id,count).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message=new Message();
                Bundle bundle=new Bundle();
                try {
                    bundle.putString("purchase_history",response.body().string());
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
