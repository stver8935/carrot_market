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

public class UserSearchTask implements Runnable {
    private String search_name;
    private Handler handler;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private String user_search_count;

    public UserSearchTask(String search_name,String user_search_count, Handler handler) {
        this.search_name = search_name;
        this.user_search_count=user_search_count;
        this.handler = handler;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    @Override
    public void run() {
        retrofitService.user_search(search_name,user_search_count).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message=new Message();
                Bundle bundle=new Bundle();
                try {
                    bundle.putString("user_list",response.body().string());
                    message.setData(bundle);
                    message.what=0;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("서버에러","유저 리스트를 가져오지 못했습니다"+t);

            }
        });

    }
}
