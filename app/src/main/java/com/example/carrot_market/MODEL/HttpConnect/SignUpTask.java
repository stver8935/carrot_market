package com.example.carrot_market.MODEL.HttpConnect;

import android.os.AsyncTask;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class SignUpTask extends AsyncTask<String, String, String> {

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

            call= retrofitService.Sign_up(params[0],params[1],params[2],params[3],params[4]);

            try {
                String Responsecode=call.execute().body().string();

                return Responsecode;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

}
