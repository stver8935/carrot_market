package com.example.carrot_market.MODEL.HttpConnect;

import android.os.AsyncTask;
import android.util.Log;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class LoginTask extends AsyncTask<String,String,String> {


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
    protected String doInBackground(String... pm) {

        try {
            Log.e("log",pm[0]);

            JSONObject jsonObject=new JSONObject(pm[0]);

            call=retrofitService.Login(jsonObject.getString("id"),jsonObject.getString("password"),jsonObject.getString("token"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            String RpCode=call.execute().body().string();


        return RpCode;


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
