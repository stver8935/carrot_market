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

public class ProductDetaileDownloadTask implements Runnable {
    private String product_key,user_id;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private Call<ResponseBody> call;
    private String coment_start_count;
    private String coment_end_count;
    private Handler handler;


    public ProductDetaileDownloadTask(String product_key,String user_id,String coment_start_count,String coment_end_count, android.os.Handler handler){
        this.product_key=product_key;
        this.handler=handler;
        this.coment_start_count=coment_start_count;
        this.coment_end_count=coment_end_count;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
        this.user_id=user_id;
    }

    @Override
    public void run() {

        call=retrofitService.product_detaile_download(product_key,user_id,coment_start_count,coment_end_count);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    Message message=new Message();
                    Bundle bundle = new Bundle();
                try {
                    bundle.putString("product_detaile_list",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                message.setData(bundle);
                    message.what=0;
                    handler.sendMessage(message);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("product_detail_error",""+t);

            }
        });



    }
}
