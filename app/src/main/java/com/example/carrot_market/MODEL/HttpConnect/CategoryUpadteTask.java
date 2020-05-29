package com.example.carrot_market.MODEL.HttpConnect;

import android.util.Log;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class CategoryUpadteTask implements Runnable {



    private String id;
    private JSONObject category;
    private Handler handler;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private String key;
    private Boolean value;

    public CategoryUpadteTask(String id, String key) {
        this.id = id;
        this.key = key;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }


    @Override
    public void run() {

        retrofitService.cateogry_update(id,key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                try {
                    Log.e("update?","aaa"+response.body().string());
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
