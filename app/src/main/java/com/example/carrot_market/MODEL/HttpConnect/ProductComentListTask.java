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

public class ProductComentListTask implements Runnable {

    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private String product_key;
    private String coment_count;
    private Handler handler;


    public ProductComentListTask(String product_key,String coment_count,Handler handler){
        this.product_key=product_key;
        this.handler=handler;
        this.coment_count=coment_count;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }
    @Override
    public void run() {

        retrofitService.product_coment_list(product_key,coment_count).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bundle bundle=new Bundle();
                try {

                    Message message=new Message();
                    bundle.putString("coment_list",response.body().string());
                    message.setData(bundle);
                    message.what=0;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error code",""+t);

            }
        });

    }
}
