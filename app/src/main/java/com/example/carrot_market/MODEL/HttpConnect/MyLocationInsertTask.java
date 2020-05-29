package com.example.carrot_market.MODEL.HttpConnect;

import android.os.AsyncTask;
import android.util.Log;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class MyLocationInsertTask extends AsyncTask<String, String,String> {

    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private Call<ResponseBody> call;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);

    }

    @Override
    protected String doInBackground(String... params) {


        String address=params[0];
        String id=params[1];
        Log.e("aaaaaaaaaaaa","aaaaaaaaaa"+address+"//"+id);

        call=retrofitService.location_insert(address,id);

        try {
           String RpCode= call.execute().body().string();
            Log.e("code",RpCode);

           return RpCode;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
