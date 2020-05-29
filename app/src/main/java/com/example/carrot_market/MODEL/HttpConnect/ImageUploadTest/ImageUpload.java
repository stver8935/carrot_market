package com.example.carrot_market.MODEL.HttpConnect.ImageUploadTest;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ImageUpload {


    @Multipart
    @POST("upload_img.php")
    Call<ResponseBody> upload(@Part("id")String id , @Part("product_info")String product_info, @Part List<MultipartBody.Part> uploaded_file);


    @Multipart
    @POST("product_edit.php")
    Call<ResponseBody> product_edit(@Part("product_key")String product_key ,@Part("id")String id , @Part("product_info")String product_info, @Part List<MultipartBody.Part> uploaded_file);


    @Multipart
    @POST("deal_review_leave_upload.php")
    Call<ResponseBody> deal_review_leave_upload(@PartMap Map<String, RequestBody> check,@PartMap Map<String, RequestBody> check_count,@PartMap Map<String, RequestBody> my,@PartMap Map<String, RequestBody> other, @PartMap Map<String, RequestBody> coment, @PartMap Map<String, RequestBody> product_key, @Part MultipartBody.Part review_image);

    //@Field @Part 차이??

    @FormUrlEncoded
    @POST("user_block.php")
    Call<ResponseBody> user_block(@Field("id")String id, @Field("block_id")String block_id);


    @Multipart
    @POST("profile_image_update.php")
    Call<ResponseBody> profile_image_update(@PartMap Map<String, RequestBody> id, @Part MultipartBody.Part profile_image);


//    $profile_image_file=$_FILES['profile_image_file'];
//    $id=$_POST['id'];


}
