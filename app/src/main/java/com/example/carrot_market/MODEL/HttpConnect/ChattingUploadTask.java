package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Handler;

import com.example.carrot_market.ServerRetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Retrofit;

public class ChattingUploadTask implements Runnable{

    Retrofit retrofit;
    ServerRetrofitService retrofitService;
    String message;
    Handler handler;

    public ChattingUploadTask(String message, Handler handler) {
        this.message=message;
        this.handler=handler;

    }
    @Override
    public void run() {
        try {
            JSONObject message_json=new JSONObject(message);

       //리스폰스 값 고려

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
