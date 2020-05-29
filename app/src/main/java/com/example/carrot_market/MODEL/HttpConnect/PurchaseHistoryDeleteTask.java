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

public class PurchaseHistoryDeleteTask implements Runnable {

    private Handler handler;
    private String product_key;
    private RetrofitService retrofitService;
    private Retrofit retrofit;

    public PurchaseHistoryDeleteTask(Handler handler, String product_key) {
        this.handler = handler;
        this.product_key = product_key;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);

    }

    @Override
    public void run() {


        retrofitService.purchase_history_delete(product_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bundle bundle=new Bundle();
                Message message=new Message();
                try {
                    String product_key =response.body().string();

                    Log.e("delete.task",product_key);
                    bundle.putString("product_key",product_key);
                    message.setData(bundle);
                    message.what=1;
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
