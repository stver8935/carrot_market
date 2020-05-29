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

public class SearchProductTask implements Runnable {


    private android.os.Handler handler;
    private RetrofitService retrofitService;
    private Retrofit retrofit;
    private String id,content,search_count;

    public SearchProductTask(Handler handler, String id, String content, String search_count) {
        this.handler = handler;
        this.id = id;
        this.content = content;
        this.search_count = search_count;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    @Override
    public void run() {

        retrofitService.product_search(id,content,search_count).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message=new Message();
                Bundle bundle=new Bundle();
                try {
                    bundle.putString("search_product_list",response.body().string());
                    Log.e("search_list",bundle.getString("search_product_list"));

                    message.what=0;
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


                Log.e("삼품을 받아오지 못했습니다",""+t);




            }
        });





    }
}
