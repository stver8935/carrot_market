package com.example.carrot_market.MODEL.HttpConnect.RETROFIT;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ProductDownloadTask implements Runnable {
    private Handler handler;
    private Context context;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private Call<ResponseBody> call;
    private UserInfoSave userInfoSave;
    private String product_master_id;
    private String sales_completed;
    private String id;
    private int product_count;

    public ProductDownloadTask(Handler handler, Context context,String id,int product_count,String product_master_id,String sales_completed){
    this.handler=handler;
    this.context=context;
    this.product_master_id=product_master_id;
    this.sales_completed=sales_completed;
    this.id=id;
    this.product_count=product_count;

    retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    retrofitService=retrofit.create(RetrofitService.class);
    userInfoSave=new UserInfoSave(context);

    }

    @Override
    public void run() {



        call=retrofitService.product_download(id,product_count,product_master_id,sales_completed);

        call.enqueue(new Callback<ResponseBody>() {
            String responsecode = null;
            JSONArray jsonArray = null;
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                jsonArray=new JSONArray(response.body().string());
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("product_list", "" +jsonArray);
                msg.setData(bundle);
                msg.what = 0;
                handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
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
