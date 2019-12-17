package com.example.carrot_market.MODEL.HttpConnect.RETROFIT;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody>Login(@Field("id") String id, @Field("password") String password);


    @FormUrlEncoded
    @POST("signup.php")
    Call<ResponseBody>Sign_up(@Field("id") String id, @Field("password") String password, @Field("name") String name, @Field("phone") String phone, @Field("email") String email);


    @FormUrlEncoded
    @POST("findaccount.php")
    Call<ResponseBody>Find_Account(@Field("name") String name, @Field("email") String password, @Field("code") String code);

    @FormUrlEncoded
    @POST("findaccountauthonticationcode.php")
    Call<ResponseBody>FindAccountAtonticationCode(@Field("name") String name, @Field("email") String email);

    @FormUrlEncoded
    @POST("idcheck.php")
    Call<ResponseBody> idcheck(@Field("id") String id);

}
