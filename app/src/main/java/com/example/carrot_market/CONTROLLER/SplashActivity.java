package com.example.carrot_market.CONTROLLER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Toast;

import com.example.carrot_market.MODEL.HttpConnect.LoginTask;
import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;
import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.UserInfo;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.AutoLoginCheck;

import com.example.carrot_market.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

   private ConstraintLayout constraintLayout;
    private Animation animation;


    private Retrofit retrofit;
    private RetrofitService service;
    private Call<ResponseBody> call;



    public static final String API_URL = "http://13.125.130.142/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animation= AnimationUtils.loadAnimation(this,R.anim.fadein);

        constraintLayout=findViewById(R.id.splash_layout);
        constraintLayout.setAnimation(animation);

        retrofit=new Retrofit.Builder()
                .baseUrl("http://13.125.130.142/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service=retrofit.create(RetrofitService.class);



            constraintLayout.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                //쉐어드의 자동로그인 체크 한뒤에
                //서버 통신을 통해서 로그인 처리



                AutoLoginCheck autoLoginCheck=new AutoLoginCheck(SplashActivity.this);


                try {
                    if (autoLoginCheck.check()){

                        UserInfo user=autoLoginCheck.return_account();


                        LoginTask loginTask=new LoginTask();



                        try {
                            String responscode=loginTask.execute(user.getId(),user.getPassword()).get();

                            if (!responscode.equals("1")){


                            }
                            else {
                                Toast.makeText(SplashActivity.this, "로그인 되셨습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SplashActivity.this,MainFragment.class);
                            startActivity(intent);
                            finish();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }else{

                        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
