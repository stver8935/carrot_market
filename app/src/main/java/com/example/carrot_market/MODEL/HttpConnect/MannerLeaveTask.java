package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class MannerLeaveTask implements Runnable {
    private String id;

    private String manner_id;
    private String manner_type;

    //매너 리스트는 String 형태의 json 값 으로 보낸다.
    private String manner_list;
    private Handler handler;
    private RetrofitService retrofitService;
    private Retrofit retrofit;
    public MannerLeaveTask(String id, String manner_id, String manner_type, String manner_list, Handler handler) {
        this.id = id;
        this.manner_id = manner_id;
        this.manner_type = manner_type;
        this.manner_list = manner_list;
        this.handler = handler;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }




    @Override
    public void run() {

        RequestBody id_b=RequestBody.create(MediaType.parse("text/plain"),id);
        RequestBody manner_id_b=RequestBody.create(MediaType.parse("text/plain"),manner_id);
        RequestBody manner_type_b=RequestBody.create(MediaType.parse("text/plain"), manner_type);
        RequestBody manner_list_b=RequestBody.create(MediaType.parse("text/plain"), manner_list.toString());

        HashMap<String, RequestBody> id_h=new HashMap<>();
        HashMap<String, RequestBody> manner_id_h=new HashMap<>();
        HashMap<String, RequestBody> manner_type_h=new HashMap<>();
        HashMap<String, RequestBody> manner_list_h=new HashMap<>();


        id_h.put("id",id_b);
        manner_id_h.put("manner_id",manner_id_b);
        manner_type_h.put("manner_type",manner_type_b);
        manner_list_h.put("manner_list",manner_list_b);


        retrofitService.manner_leave(id_h,manner_id_h,manner_type_h,manner_list_h).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message=new Message();
                Bundle bundle=new Bundle();
                try {
                    bundle.putString("manner_response_code",response.body().string());
                    message.what=3;
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
