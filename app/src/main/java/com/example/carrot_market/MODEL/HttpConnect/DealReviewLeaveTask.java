package com.example.carrot_market.MODEL.HttpConnect;

import android.os.Handler;
import android.util.Log;

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

public class DealReviewLeaveTask implements Runnable {

    private Retrofit retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private ImageUpload imageUpload=retrofit.create(ImageUpload.class);
    private Handler handler;
    private String id,opponent_id,product_key,deal_review_image_path,coment;
    private String deal_review_list;
    private int denial_and_positive;

    private Map<String,String> map=new HashMap<>();

    public DealReviewLeaveTask(Handler handler,int denial_and_positive,String deal_review_list,String id,String opponent_id,String coment,String product_key,String deal_review_image_path) {
        this.handler = handler;
        this.id=id;
        this.product_key=product_key;
        this.deal_review_image_path=deal_review_image_path;
        this.coment=coment;
        this.deal_review_list=deal_review_list;
        this.denial_and_positive=denial_and_positive;
        this.opponent_id=opponent_id;
    }
    @Override
    public void run() {

        File file = new File(deal_review_image_path);
        final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestfile);

        map.put("producy_key",product_key);

        Map<String,RequestBody> product_key_map=new HashMap<>();
        Map<String,RequestBody> my_id_map=new HashMap<>();
        Map<String,RequestBody> other_id_map=new HashMap<>();
        Map<String,RequestBody> coment_map=new HashMap<>();

        Map<String,RequestBody> deal_review_list_map=new HashMap<>();

        Map<String,RequestBody> denial_and_positive_map=new HashMap<>();

        RequestBody product_key_b = RequestBody.create(MediaType.parse("text/plain"), product_key);
        RequestBody my_id_b=RequestBody.create(MediaType.parse("text/plain"),id);
        RequestBody other_id_b=RequestBody.create(MediaType.parse("text/plain"),opponent_id);
        RequestBody coment_b=RequestBody.create(MediaType.parse("text/plain"),coment);

        RequestBody deal_review_list_b=RequestBody.create(MediaType.parse("text/plain"), deal_review_list);

        RequestBody denial_and_positive_b=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(denial_and_positive));


        product_key_map.put("product_key",product_key_b);
        my_id_map.put("my_id",my_id_b);
        other_id_map.put("other_id",other_id_b);
        coment_map.put("coment",coment_b);

        deal_review_list_map.put("manner_temperature_evaluation_list",deal_review_list_b);

        denial_and_positive_map.put("denial_and_positive",denial_and_positive_b);
        //$manner_temperature_evaluation_count

        imageUpload.deal_review_leave_upload(denial_and_positive_map,deal_review_list_map,my_id_map,other_id_map,coment_map,product_key_map,body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {



//                Log.e("deal_review_response","ok");
//
//                Bundle bundle=new Bundle();
//                Message message=new Message();
//
//                    bundle.putString("deal_review_leave",opponent_id);
//                    message.what=10;
//                    message.setData(bundle);
//                    handler.handleMessage(message);
//
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("deal_review_response","fail"+t);
            }
        });

    }
}
