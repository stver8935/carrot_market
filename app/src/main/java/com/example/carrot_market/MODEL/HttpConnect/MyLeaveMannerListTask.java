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

public class MyLeaveMannerListTask implements Runnable {
    private String id,opponent_id;
    private android.os.Handler handler;
    private String manner_type;
    private RetrofitService retrofitService;
    private Retrofit retrofit;


    public MyLeaveMannerListTask(String id, String opponent_id, String manner_type, Handler handler) {
        this.id = id;
        this.opponent_id = opponent_id;
        this.handler = handler;
        this.manner_type = manner_type;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    @Override
    public void run() {

        retrofitService.my_leave_manner_list(id,opponent_id,manner_type).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message=new Message();
                Bundle bundle=new Bundle();
                try {
                    bundle.putString("manner_list",response.body().string());
                    message.what=1;
                    message.setData(bundle);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                handler.sendMessage(message);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }
}
