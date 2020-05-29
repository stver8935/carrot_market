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

public class SalesProductTask implements Runnable {
//    @Field("id")String id,
//    @Field("product_count")String product_count
//    ,@Field("hidden") String hidden
//    ,@Field("sales_completed")String sales_completed
    private String id,product_count,hidden,sales_completed;
    private Retrofit retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private RetrofitService retrofitService=retrofit.create(RetrofitService.class);
    private Handler handler;

    public SalesProductTask(String id, String product_count, String hidden, String sales_completed, Handler handler) {
        this.id = id;
        this.product_count = product_count;
        this.hidden = hidden;
        this.sales_completed = sales_completed;
        this.handler = handler;
    }



    @Override
    public void run() {
        retrofitService.sales_product(id,product_count,hidden,sales_completed).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Bundle bundle=new Bundle();
                Message message=new Message();
                try {
                    bundle.putString("sales_product",response.body().string());
                    message.what=0;
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
