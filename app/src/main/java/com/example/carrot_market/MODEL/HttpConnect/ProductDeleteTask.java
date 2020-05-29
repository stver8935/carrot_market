package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Handler;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import retrofit2.Retrofit;

public class ProductDeleteTask implements Runnable {
    private String id,product_key;
    private Handler handler;
    RetrofitService retrofitService;
    Retrofit retrofit;

     public ProductDeleteTask(String id, String product_key, Handler handler) {
        this.id = id;
        this.product_key = product_key;
        this.handler=handler;


    }


    @Override
    public synchronized void run() {




    }
}
