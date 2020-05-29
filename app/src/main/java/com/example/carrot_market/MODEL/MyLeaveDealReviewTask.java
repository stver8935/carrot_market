package com.example.carrot_market.MODEL;

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

public class MyLeaveDealReviewTask implements Runnable {

    private String id,opponent_id,product_key;
    private Handler handler;
    private RetrofitService retrofitService;
    private Retrofit retrofit;

    public MyLeaveDealReviewTask(String id, String opponent_id,String product_key, Handler handler) {
        this.id = id;
        this.opponent_id = opponent_id;
        this.product_key=product_key;
        this.handler = handler;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }


    @Override
    public void run() {
        retrofitService.my_leave_deal_review(id,opponent_id,product_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message=new Message();
                Bundle bundle=new Bundle();

                try {
                    bundle.putString("my_leave_deal_review",response.body().string());

                    message.setData(bundle);

                    if (product_key==null){

                        message.what=1;
                    }else {

                        message.what=0;
                    }
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
