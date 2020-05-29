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

public class InsertKeyWordTask implements Runnable {
    private String id ,key_word;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private Handler handler;

    public InsertKeyWordTask(String id, String key_word, Handler handler) {
        this.handler=handler;
        this.id = id;
        this.key_word = key_word;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    @Override
    public void run() {

        retrofitService.insert_key_word(id,key_word).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bundle bundle=new Bundle();
                Message message=new Message();
                try {
                    bundle.putString("insert_key_word_result",response.body().string());
                    message.what=0;
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e("키워드 입력 에러",""+t);

            }
        });

    }
}
