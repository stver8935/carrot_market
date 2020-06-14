package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ComentDeleteTask implements Runnable {

    private Handler handler;
    private String comnet_key;
    private Retrofit retrofit;
    private RetrofitService retrofitService;


    public ComentDeleteTask(String comnet_key,Handler handler){
        this.comnet_key=comnet_key;
        this.handler=handler;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);

    }


    @Override
    public void run() {

        retrofitService.coment_delete(comnet_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message=new Message();

                Bundle bundle=new Bundle();
                JSONObject coment_delete=new JSONObject();
                try {

                coment_delete.put("coment_key",comnet_key);
                coment_delete.put("coment_delete_check",true);
                bundle.putString("coment_delete",coment_delete.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                message.setData(bundle);
                message.what=3;

                handler.sendMessage(message);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("coment_delete_error" ,""+t);

            }
        });



    }
}
