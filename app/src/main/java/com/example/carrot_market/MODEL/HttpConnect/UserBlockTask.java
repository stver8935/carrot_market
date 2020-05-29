package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Handler;
import android.util.Log;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class UserBlockTask implements Runnable{
    private Handler handler;
    private String id,block_id;
    private RetrofitService retrofitService;
    private Retrofit retrofit;



    public UserBlockTask( String id, String block_id) {
        this.handler = handler;
        this.id = id;
        this.block_id = block_id;
        retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(API_URL).build();
        retrofitService=retrofit.create(RetrofitService.class);

    }

    @Override
    public void run() {


        retrofitService.user_block(id,block_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("user_block","1");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e("user_block","2");

            }
        });

    }
}
