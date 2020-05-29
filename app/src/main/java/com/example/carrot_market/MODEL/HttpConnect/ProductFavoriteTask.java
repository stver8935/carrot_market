package com.example.carrot_market.MODEL.HttpConnect;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ProductFavoriteTask implements Runnable {
    private RetrofitService retrofitService;
    private Retrofit retrofit;
    private String id,product_key;


    public ProductFavoriteTask(String id, String product_key) {
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
        this.id=id;
        this.product_key=product_key;
    }

    @Override
    public void run() {

        retrofitService.product_favorite(id,Integer.parseInt(product_key)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
