package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ImageDownLoadTask implements Runnable {

    String path;
    Retrofit retrofit;
    RetrofitService retrofitService;
    Handler handler;
    Call<ResponseBody> call;
    int image_count;
   public ImageDownLoadTask(String StringImageUrl,int image_count,Handler handler) throws MalformedURLException {
        path=StringImageUrl;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);
        this.handler=handler;
        this.image_count=image_count;
   }

    @Override
    public void run() {


//       call=retrofitService.imageload(path);
       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

               Message msg = new Message();
               Bundle bundle = new Bundle();

               Log.e("response",""+response);

               bundle.putInt("image_count",image_count);
               bundle.putByteArray("image", inputStreamToByteArray(response.body().byteStream()));
//                    bundle.putInt("image_size",response.body().byteStream().);

               msg.setData(bundle);
               msg.what = 1;
               handler.sendMessage(msg);


           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {

           }
       });


    }
    public static byte[] inputStreamToByteArray(InputStream is) {

        byte[] resBytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int read = -1;
        try {
            while ( (read = is.read(buffer)) != -1 ) {
                bos.write(buffer, 0, read);
            }

            resBytes = bos.toByteArray();
            bos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return resBytes;
    }
}
