package com.example.carrot_market.MODEL.HttpConnect;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class AddProductUploadTask extends AsyncTask<String, String , String > {


    private RetrofitService retrofitService;
    private Retrofit retrofit;
    private Call<ResponseBody> call;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... pms) {


        return null;
    }
}
