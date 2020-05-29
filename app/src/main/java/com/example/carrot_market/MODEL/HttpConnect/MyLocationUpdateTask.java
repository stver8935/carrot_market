package com.example.carrot_market.MODEL.HttpConnect;

import android.content.Context;
import android.os.AsyncTask;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class MyLocationUpdateTask extends AsyncTask<String,String,String> {

    private Handler handler;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private Call<ResponseBody> call;
    private Context context;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);

    }


    @Override
    protected String doInBackground(String... params) {

        //address location lat lng range id

        try {
            call= retrofitService.location_update(params[0],params[1],params[2]);
            String Responsecode=call.execute().body().string();

            return Responsecode;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
