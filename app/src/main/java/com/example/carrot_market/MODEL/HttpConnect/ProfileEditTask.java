package com.example.carrot_market.MODEL.HttpConnect;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

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


public class ProfileEditTask implements Runnable {
    private String id,password,name,profile_image_path;
    private Handler handler;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private Context context;
    public ProfileEditTask(String id, String password, String name, String profile_image_path, Handler handler, Context context) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.profile_image_path = profile_image_path;
        this.handler = handler;
        this.context=context;

        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);

    }





    @Override
    public void run() {

        String path;

        if (profile_image_path.matches(".*"+API_URL+".*")){


            path=imagefile(profile_image_path);

        }else {

            Cursor c = context.getContentResolver().query(Uri.parse(profile_image_path), null, null, null, null);
            c.moveToFirst();
            path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));

        }



        Uri uri=Uri.parse(profile_image_path);

        File file = new File(path);

        final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part profile_image_multipart_body = MultipartBody.Part.createFormData("profile_image", file.getName(), requestfile);

        retrofitService.profile_edit(id,password,name,profile_image_multipart_body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                try {
                    Message message=new Message();
                    message.what=2;
                    handler.sendMessage(message);

                    Log.e("프로필 수정에 성공 했습니다",""+""+response.body().string());


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("프로필 수정에 실패 했습니다.",""+t);


            }
        });


    }

    public String imagefile(String uri) {
        File outputDir = context.getCacheDir();
        File outputFile = null;
        try {
            outputFile = File.createTempFile("prefix", "extension.jpg", outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        try {
            inputStream = new URL(uri).openStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(outputFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            writeFile(inputStream, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return outputFile.getAbsolutePath();
    }



    public void writeFile(InputStream is, OutputStream os) throws IOException
    {
        int c = 0;
        while((c = is.read()) != -1)
            os.write(c);
        os.flush();
    }
}
