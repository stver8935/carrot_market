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

public class CategoryLoadTask implements Runnable {

    private String id;
    private RetrofitService retrofitService;
    private Retrofit retrofit;
    private Handler handler;

    public CategoryLoadTask(String id, Handler handler){
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
        this.handler=handler;

        this.id = id;
    }


    @Override
    public void run() {

        retrofitService.category_load(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    Message message=new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("category_list",response.body().string());
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
