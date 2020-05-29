package com.example.carrot_market.MODEL.HttpConnect.RETROFIT;

import java.sql.Date;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitService {


    @FormUrlEncoded
    @POST("activity_notification_delete.php")
    Call<ResponseBody>activity_notification_delete(@Field("key") String key);


    @GET("activity_notification_list.php")
    Call<ResponseBody> activity_notification_list(@Query("id") String id ,@Query("count") String count );



    @FormUrlEncoded
    @POST("key_word_notification_list_delete.php")
    Call<ResponseBody>product_notification_list_delete(@Field("id") String id,@Field("key_word_key") String key);

    @GET("product_key_word_notification_list.php")
    Call<ResponseBody> product_notification_list(@Query("id") String id ,@Query("count") String count );

    @FormUrlEncoded
    @POST("delete_key_word.php")
    Call<ResponseBody>delete_key_word(@Field("key") String key);


    @FormUrlEncoded
    @POST("load_key_word.php")
    Call<ResponseBody>load_key_word(@Field("id") String id);

    @FormUrlEncoded
    @POST("insert_key_word.php")
    Call<ResponseBody>insert_key_word(@Field("id") String id,@Field("key_word") String keyword);


    @Multipart
    @POST("profile_edit.php")
    Call<ResponseBody> profile_edit(@Part("id")String id ,@Part("password")String password ,@Part("name")String name , @Part MultipartBody.Part profile_image);

    @GET("user_search.php")
    Call<ResponseBody> user_search(@Query("search_name") String search_name,@Query("search_user_count")String user_search_count);


    @GET("product_search.php")
    Call<ResponseBody> product_search(@Query("id") String id ,@Query("content") String content ,@Query("search_count") String search_count );

    @FormUrlEncoded
    @POST("find_sellers_in_chat.php")
    Call<ResponseBody>find_sellers_in_chat(@Field("id") String id);

    @FormUrlEncoded
    @POST("my_leave_deal_review.php")
    Call<ResponseBody>my_leave_deal_review(@Field("id") String id,@Field("opponent_id") String opponent_id,@Field("product_key")String product_key);

    @FormUrlEncoded
    @POST("buyer_list.php")
    Call<ResponseBody>buyer_list(@Field("id") String id,@Field("product_key") String product_key);


    @FormUrlEncoded
    @POST("purchase_history_delete.php")
    Call<ResponseBody>purchase_history_delete(@Field("product_key") String product_key);


    @FormUrlEncoded
    @POST("purchase_history.php")
    Call<ResponseBody>purchase_history_load(@Field("id") String id,@Field("count") String count);

    @FormUrlEncoded
    @POST("follow_list.php")
    Call<ResponseBody>follow_list(@Field("id") String id);


    @FormUrlEncoded
    @POST("follow_check.php")
    Call<ResponseBody>follow_check(@Field("id") String id,@Field("follow_id") String follow_id);


    @FormUrlEncoded
    @POST("follow.php")
    Call<ResponseBody>follow(@Field("id") String id,@Field("follow_id") String follow_id);

    @FormUrlEncoded
    @POST("follow_product.php")
    Call<ResponseBody>follow_product(@Field("id") String id,@Field("product_count") String product_count);


    @FormUrlEncoded
    @POST("product_pull_up.php")
    Call<ResponseBody>product_pull_up(@Field("product_key") String product_key);


    @FormUrlEncoded
    @POST("product_delete.php")
    Call<ResponseBody>product_delete(@Field("id") String id,@Field("product_key") String product_key);

    @FormUrlEncoded
    @POST("product_salescompleted.php")
    Call<ResponseBody>product_salescompleted(@Field("id") String id,@Field("product_key") String product_key,@Field("sales_completed")int sales_completed);

    @FormUrlEncoded
    @POST("product_hidden.php")
    Call<ResponseBody> product_hidden(@Field("id")String id ,@Field("product_key")String product_key, @Field("hidden")String hidden);


    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody>Login(@Field("id") String id, @Field("password") String password, @Field("token") String token);


    @FormUrlEncoded
    @POST("signup.php")
    Call<ResponseBody>Sign_up(@Field("id") String id, @Field("password") String password, @Field("name") String name, @Field("phone") String phone, @Field("email") String email);


    @FormUrlEncoded
    @POST("findaccount.php")
    Call<ResponseBody>Find_Account(@Field("name") String name, @Field("email") String password);

    @FormUrlEncoded
    @POST("findaccountauthonticationcode.php")
    Call<ResponseBody>FindAccountAtonticationCode(@Field("name") String name, @Field("email") String email);

    @FormUrlEncoded
    @POST("idcheck.php")
    Call<ResponseBody> idcheck(@Field("id") String id);

    @FormUrlEncoded
    @POST("my_location_update.php")
    Call<ResponseBody> location_update(@Field("before_address_list")String before_address,@Field("address") String address,@Field("id") String id);

    @FormUrlEncoded
    @POST("my_location_insert.php")
    Call<ResponseBody> location_insert(@Field("address") String address,@Field("id") String id);

    @FormUrlEncoded
    @POST("my_location_delete.php")
    Call<ResponseBody> location_delete(@Field("id") String id,@Field("address") String address);

    @FormUrlEncoded
    @POST("location_select_update.php")
    Call<ResponseBody> address_update(@Field("id") String id,@Field("address") String address);

    @FormUrlEncoded
    @POST("location_range_update.php")
    Call<ResponseBody> location_range(@Field("id") String id,@Field("range") int range);

    @FormUrlEncoded
    @POST("location_load.php")
    Call<ResponseBody> location_load(@Field("id") String id);


    @FormUrlEncoded
    @POST("product_upload.php")
    Call<ResponseBody> product_upload(@Field("id") String id,String product_text);




//    $user_product_count=$_POST['product_count'];
//    $user_id=$_POST['id'];
//    $sales_completed=$_POST['sales_completed'];
//    $hidden=$_POST['hidden'];
//    sales_product.php


    @FormUrlEncoded
    @POST("sales_product.php")
    Call<ResponseBody> sales_product(@Field("id")String id,@Field("product_count")String product_count,@Field("hidden") String hidden,@Field("sales_completed")String sales_completed);

    //hidden 추가가
   @FormUrlEncoded
    @POST("core.php")
    Call<ResponseBody> product_download(@Field("id") String id,@Field("product_count") int Product_count
            ,@Field("product_master_id") String Product_master_id, @Field("sales_completed") String sales_complited);


    @GET("product_download_all.php")
    Call<ResponseBody> product_downloa_all(@Query("product_count") int Product_count
            ,@Query("product_master_id") String Product_master_id, @Query("sales_completed") String sales_complited);


    @GET("product_detaile.php")
    Call<ResponseBody> product_detaile_download(@Query("product_key") String product_key,@Query("user_id") String user_id,@Query("coment_start_count") String coment_start_count,@Query("coment_end_count") String coment_end_count);

    @GET("product_coment_list.php")
    Call<ResponseBody> product_coment_list(@Query("product_key") String product_key,@Query("coment_count") String coment_count);

    @GET("manner_evaluation.php")
    Call<ResponseBody> manner_evaluation_list(@Query("id") String id,@Query("count") int count);


    @FormUrlEncoded
    @POST("favorite.php")
    Call<ResponseBody> product_favorite(@Field("id") String id,@Field("product_key") int key);

    @GET("favorite_product_list.php")
    Call<ResponseBody> favorite_product_list(@Query("id")String id,@Query("count")int product_count);


    @FormUrlEncoded
    @POST("product_coment_insert.php")
    Call<ResponseBody> product_coment_insert(@Field("id") String id,@Field("coment") String coment,@Field("product_key") int key);


    @GET("category_loadd.php")
    Call<ResponseBody> category_load(@Query("id") String id);

    @FormUrlEncoded
    @POST("category_update.php")
    Call<ResponseBody> cateogry_update(@Field("id")String id,@Field("key") String key);

    @FormUrlEncoded
    @POST("coment_delete.php")
    Call<ResponseBody> coment_delete(@Field("coment_key") String coment_key);

    @FormUrlEncoded
    @POST("coment_update.php")
    Call<ResponseBody> coment_update(@Field("coment_key") String coment_key,@Field("id") String id ,@Field("coment") String coment);


    @FormUrlEncoded
    @POST("chatting_room_load.php")
    Call<ResponseBody> chatting_room_load(@Field("id") String id);

    @FormUrlEncoded
    @POST("chatting_room_insert.php")
    Call<ResponseBody> chatting_room_insert(@Field("seller") String seller, @Field("buyer") String buyer ,@Field("product_key") String product_key);

//    exit_chatting_room

    @FormUrlEncoded
    @POST("exit_chatting_room.php")
    Call<ResponseBody> exit_chatting_room(@Field("id")String id,@Field("chatting_room_key")String chatting_room_key);



    @FormUrlEncoded
    @POST("chatting_load.php")
    Call<ResponseBody> chatting_load(@Field("product_key") String product_key, @Field("id")String id);


//    @FormUrlEncoded
//    @POST("chatting_upload.php")
//    Call<ResponseBody> chatting_upload(@Field("id") String id ,@Field("message") String message ,@Field("buyer") String buyer,@Field("product_key") String product_key);
//    //message는 JSONObject 형태의 Strinf 값
    //구조는 아이디와 메시지 내용으로 이루어짐
    // 메시지 보낸시간은 서버에서 설정



    @FormUrlEncoded
    @POST("user_profile_image.php")
    Call<ResponseBody> user_profile_image_load(@Field("id") String id);


    @GET("profile.php")
    Call<ResponseBody> user_profile_load(@Query("id") String id);


    @GET("deal_review_load.php")
    Call<ResponseBody> deal_review_load(@Query("user_type") String user_type,@Query("id") String id,@Query("start_count") int start_count,@Query("count") int count);


    @FormUrlEncoded
    @POST("chatting_info_load.php")
    Call<ResponseBody> chatting_info_load(@Field("id") String id ,@Field("product_key")String product_key);

    @FormUrlEncoded
    @POST("fcm_token_upload.php")
    Call<ResponseBody> fcm_token_upload(@Field("id")String id , @Field("token") String token);

    @FormUrlEncoded
    @POST("fcm_token_load.php")
    Call<ResponseBody> fcm_token_load(@Field("id")String id);


    @FormUrlEncoded
    @POST("user_channel_update.php")
    Call<ResponseBody> user_channe_update(@Field("id")String id,@Field("channel_ud")String channel_id);


    @FormUrlEncoded
    @POST("alram_upload.php")
    Call<ResponseBody> alram_upload(@Field("chatting_room_key")String chatting_room_key, @Field("date") Date date);

    @FormUrlEncoded
    @POST("alram_load.php")
    Call<ResponseBody> alram_load(@Field("chatting_room_key")String chatting_room_key);

    @FormUrlEncoded
    @POST("user_block.php")
    Call<ResponseBody> user_block(@Field("id")String id, @Field("block_id")String block_id);

    @FormUrlEncoded
    @POST("user_unblock.php")
    Call<ResponseBody> user_unblock(@Field("id")String id,@Field("block_id")String block_id);
}

