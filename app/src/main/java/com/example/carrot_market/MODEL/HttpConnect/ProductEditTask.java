package com.example.carrot_market.MODEL.HttpConnect;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import com.example.carrot_market.MODEL.DTO.AddProductImageItem;
import com.example.carrot_market.MODEL.DTO.AddProductItem;
import com.example.carrot_market.MODEL.HttpConnect.ImageUploadTest.ImageUpload;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ProductEditTask implements Runnable {

    AddProductItem addProductItem;
    ArrayList<AddProductImageItem> image_uri_path_array;
    String id;
    Handler handler;
    Context context;
    String product_key;
    public ProductEditTask(AddProductItem addProductItem, ArrayList<AddProductImageItem> image_uri_path_array, String id,String product_key, Handler handler, Context context) {
        this.addProductItem = addProductItem;
        this.image_uri_path_array = image_uri_path_array;
        this.id = id;
        this.handler = handler;
        this.context=context;
        this.product_key=product_key;
    }



    @Override
    public void run() {
        Log.e("start","??");
        try {


             JSONArray jsonArray=new JSONArray();
            for (int i =0; i<image_uri_path_array.size();i++) {
                //url 형식이라면
                if (image_uri_path_array.get(i).getImage().matches(".*"+API_URL+".*")){


                    String file_path = null;

                        file_path=imagefile(image_uri_path_array.get(i).getImage());

                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("image_path",file_path);
                    jsonArray.put(jsonObject);
                }else {

                    Cursor c = context.getContentResolver().query(Uri.parse(image_uri_path_array.get(i).getImage()), null, null, null, null);
                    c.moveToFirst();
                    String path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("image_path",path);
                    jsonArray.put(jsonObject);
                }

            }


            //jsonArray -- 이미지 경로를 담은 어레이



            List<MultipartBody.Part> list = new ArrayList<>();
            //서버에 이미지를 보내기 위한 멀티파트 형태의 리스트

            for (int i =0 ; i < jsonArray.length();i++) {
                //uri json 전송

                //addproduct_에서 이미지 패스를 절대 경로로 변환한뒤 json Array로 보내줌 jsonobj 키값은 image_path

                File file = new File(jsonArray.getJSONObject(i).getString("image_path"));
                RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file[]", file.getName(), requestfile);
                list.add(body);


            }

            Retrofit builder = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
            ImageUpload fileUploadService  = builder.create(ImageUpload.class);


            Log.e("insert array",""+addProductItem.getImageitemArrayList_convert_JSONArray());

            Call<ResponseBody> call =fileUploadService.product_edit(product_key,id,addProductItem.return_jsonobj().toString(),list);
//                    fileUploadService.upload(id,item.return_jsonobj().toString(),list);

            try {
                String aaa=call.execute().body().string();
                Log.e("aaa",aaa);

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Message message=new Message();
        message.what=10;
        handler.sendMessage(message);

    }
    public void writeFile(InputStream is, OutputStream os) throws IOException
    {
        int c = 0;
        while((c = is.read()) != -1)
            os.write(c);
        os.flush();
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


}
