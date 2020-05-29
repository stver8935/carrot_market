package com.example.carrot_market.MODEL.HttpConnect;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class CreateUrlImagefileTask extends AsyncTask<String,String,String > {
    Context context;
    Handler handler;
    public CreateUrlImagefileTask(Context context, Handler handler) {
        this.context = context;
        this.handler=handler;
    }

    @Override
    protected String doInBackground(String... strings) {

        File outputDir = context.getCacheDir(); // context being the Activity pointer
        File outputFile = null;
        try {
            outputFile = File.createTempFile("prefix", "extension.jpg", outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        try {
            inputStream = new URL(strings[0]).openStream();
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


        Bundle bundle=new Bundle();
        Message message=new Message();
        bundle.putString("image_path",outputFile.getAbsolutePath());
        message.what=10;
        message.setData(bundle);
        handler.sendMessage(message);
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
