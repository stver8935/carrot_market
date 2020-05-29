package com.example.carrot_market.MODEL.HttpConnect;

import android.util.Log;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.sql.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;


public class AlramUploadTask implements Runnable {

    private Date date;
    private String chatting_room_key;
    private Retrofit retrofit;
    private RetrofitService retrofitService;

    public AlramUploadTask(String chatting_room_key,Date date) {
        this.date=date;
        this.chatting_room_key=chatting_room_key;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    @Override
    public void run() {


        retrofitService.alram_upload(chatting_room_key,date).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("response",""+response);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }
}
