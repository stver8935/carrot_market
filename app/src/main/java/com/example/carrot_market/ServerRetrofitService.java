package com.example.carrot_market;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServerRetrofitService {

    @FormUrlEncoded
    @POST("channel_update.php")
    Call<ResponseBody> channel(@Field("id") String id);

    @FormUrlEncoded
    @POST("chatting_upload.php")
    Call<ResponseBody> chatting_upload(@Field("id")String id,@Field("opponent")String opponent,@Field("message")String message,@Field("product_key")String product_key,@Field("message_type")String message_type);

    @FormUrlEncoded
    @POST("exit_chatting_room.php")
    Call<ResponseBody> exit_chatting_room(@Field("id")String id,@Field("chatting_room_key")String chatting_room_key);

    @FormUrlEncoded
    @POST("chatting_channel_load.php")
    Call<ResponseBody> chatting_channel_load(@Field("id") String id );

    @FormUrlEncoded
    @POST("chatting_channel_update.php")
    Call<ResponseBody> chatting_channel_update(@Field("id") String id ,@Field("channel") String channel);



}
