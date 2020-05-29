package com.example.carrot_market.MODEL.HttpConnect;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.carrot_market.MODEL.DTO.AddProductItem;
import com.example.carrot_market.MODEL.HttpConnect.ImageUploadTest.ImageUpload;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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

public class ProductUpLoadTask extends AsyncTask<String,String,String> {

    public ProductUpLoadTask(Context context){

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected String doInBackground(String... params) {


        try {
            String id=params[0];

            JSONObject product_info_jsonobj=new JSONObject(params[1]);

            JSONArray image_uri_path_jsonArray=new JSONArray(params[2]);



            List<MultipartBody.Part> list = new ArrayList<>();

            for (int i =0 ; i < image_uri_path_jsonArray.length();i++) {
                //uri json 전송

                //addproduct_에서 이미지 패스를 절대 경로로 변환한뒤 json Array로 보내줌 jsonobj 키값은 image_path


                File file = new File(image_uri_path_jsonArray.getJSONObject(i).getString("image_path"));
                RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file[]", file.getName(), requestfile);
                list.add(body);
            }

            Retrofit builder = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
            ImageUpload fileUploadService  = builder.create(ImageUpload.class);

            AddProductItem item=new AddProductItem();
            item.setCategory(product_info_jsonobj.getString("category"));
            item.setTitle(product_info_jsonobj.getString("title"));
            item.setPrice(Integer.parseInt(product_info_jsonobj.getString("price")));
            item.setDeal(product_info_jsonobj.getBoolean("deal"));
            item.setText(product_info_jsonobj.getString("text"));
            item.setRange(product_info_jsonobj.getInt("range"));


            Log.e("insert array",""+item.getImageitemArrayList_convert_JSONArray());

            Call<ResponseBody> call = fileUploadService.upload(id,item.return_jsonobj().toString(),list);

            try {
                String aaa=call.execute().body().string();
                Log.e("aaa",aaa);

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return "1";
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    Log.e("commit","commit");
    }
}
