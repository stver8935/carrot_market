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

public class KeyWordDeleteTask implements Runnable {
    private String key_word_key;
    private Handler handler;
    private RetrofitService retrofitService;
    private Retrofit retrofit;


    public KeyWordDeleteTask(String key_word_key, Handler handler) {
        this.key_word_key = key_word_key;
        this.handler = handler;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }


    @Override
    public void run() {

        retrofitService.delete_key_word(key_word_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bundle bundle=new Bundle();
                Message message=new Message();

                try {
                    bundle.putString("delete_key_word",response.body().string());

                    message.what=2;
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
