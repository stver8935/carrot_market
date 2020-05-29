package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Handler;

import com.example.carrot_market.MODEL.HttpConnect.ImageUploadTest.ImageUpload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ProfileImageUpdateTask implements Runnable {

    private String id,profile_image_path;
    private Handler handler;
    private ImageUpload retrofitService;
    private Retrofit retrofit;


    public ProfileImageUpdateTask(String id, String profile_image_path, Handler handler) {
        this.id = id;
        this.profile_image_path = profile_image_path;
        this.handler = handler;
        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(ImageUpload.class);
    }

    @Override
    public void run() {
        File file = new File(profile_image_path);
        final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestfile);
        Map<String,RequestBody> my_id_map=new HashMap<>();

        RequestBody my_id_b=RequestBody.create(MediaType.parse("text/plain"),id);
        my_id_map.put("id",my_id_b);

        //$manner_temperature_evaluation_count

        retrofitService.profile_image_update(my_id_map,body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
