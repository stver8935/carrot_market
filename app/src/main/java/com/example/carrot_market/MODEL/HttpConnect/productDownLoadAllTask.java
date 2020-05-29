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

public class productDownLoadAllTask implements Runnable {


    private Retrofit retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private RetrofitService retrofitService=retrofit.create(RetrofitService.class);
    private int product_count;
    private  String master_id,sales_completed;
    private Handler handler;

    public productDownLoadAllTask(int product_count, String master_id, String sales_completed, Handler handler) {
        this.handler=handler;
        this.product_count = product_count;
        this.master_id = master_id;
        this.sales_completed = sales_completed;
    }


    @Override
    public void run() {


        retrofitService.product_downloa_all(product_count,master_id,sales_completed).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bundle bundle=new Bundle();
                try {
                    bundle.putString("product_list",response.body().string());
                    Message message=new Message();
                    message.what=4;
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
