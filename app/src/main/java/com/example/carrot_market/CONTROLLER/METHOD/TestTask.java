package com.example.carrot_market.CONTROLLER.METHOD;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestTask extends AsyncTask<String,String,String>{




    @Override
    protected String doInBackground(String... strings) {



        File file = null;
     String param=strings[0];

        String savePath = Environment.getExternalStorageDirectory() + File.separator + "temp/";

        File dir = new File(savePath);
        //상위 디렉토리가 존재하지 않을 경우 생성
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String localPath = savePath + "fileName" + ".jpg";


        URL imgUrl = null;
        try {
            imgUrl = new URL(param);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        //서버와 접속하는 클라이언트 객체 생성
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) imgUrl.openConnection();

            int response = conn.getResponseCode();

            file = new File(localPath);

            InputStream is = conn.getInputStream();
            OutputStream outStream = new FileOutputStream(file);

            byte[] buf = new byte[1024];
            int len = 0;

            while ((len = is.read(buf)) > 0) {
                outStream.write(buf, 0, len);
            }

            Log.e("thread",""+ Uri.fromFile(file));
            outStream.close();
            is.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }




        return Uri.fromFile(file).toString();
    }
}
