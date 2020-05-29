package com.example.carrot_market.MODEL.HttpConnect;

import android.os.AsyncTask;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class MyLocationRangeTask extends AsyncTask<String,String,String> {
    private Call<ResponseBody> call;
    private RetrofitService retrofitService;
    private Retrofit retrofit;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    retrofitService=retrofit.create(RetrofitService.class);
    }

    @Override
    protected String doInBackground(String... params) {
        String Responsecode="0";

        String id=params[0];
        int range= Integer.parseInt(params[1]);


        call=retrofitService.location_range(id,range);


        try {
            Responsecode=call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return Responsecode;
    }
}
