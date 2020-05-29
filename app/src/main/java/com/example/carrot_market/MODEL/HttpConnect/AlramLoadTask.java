package com.example.carrot_market.MODEL.HttpConnect;

import android.util.Log;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class AlramLoadTask implements Runnable {
   private String chatting_room_key;
   private RetrofitService retrofitService;
   private Retrofit retrofit;

    public AlramLoadTask(String chatting_room_key) {
        this.chatting_room_key = chatting_room_key;
    retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    retrofitService=retrofit.create(RetrofitService.class);


    }


    @Override
    public void run() {

        retrofitService.alram_load(chatting_room_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                try {
                    String date_to_string=response.body().string();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date=simpleDateFormat.parse(date_to_string);
                    Log.e("accept_date",""+date);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
