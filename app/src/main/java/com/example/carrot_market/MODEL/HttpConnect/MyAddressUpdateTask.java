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

public class MyAddressUpdateTask extends AsyncTask<String,String,String> {


    private RetrofitService retrofitService;
    private Retrofit retrofit;
    private Call<ResponseBody> call;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    @Override
    protected String doInBackground(String... params) {

        String address=params[1];
        String id=params[0];
        String responsecode="0";

        Log.e("setview",address);
        call=retrofitService.address_update(id,address);

        try {
            responsecode=call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responsecode;
    }


}
