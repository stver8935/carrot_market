package com.example.carrot_market.MODEL.HttpConnect;

import android.os.AsyncTask;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class FindAccountTask extends AsyncTask<String, String, String > {

    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private Call<ResponseBody> call;




    @Override
    protected void onPreExecute() {
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);

    }

    @Override
    protected String doInBackground(String... pms) {

        call=retrofitService.Find_Account(pms[0],pms[1],pms[2]);

        try {
            String RpCode=call.execute().body().string();
            return RpCode;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
