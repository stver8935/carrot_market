package com.example.carrot_market.CONTROLLER;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.carrot_market.MODEL.CHATTINGSERVICE.ChattingService;
import com.example.carrot_market.MODEL.DTO.UserInfoItem;
import com.example.carrot_market.MODEL.HttpConnect.LoginTask;
import com.example.carrot_market.MODEL.HttpConnect.MyLocationLoadTask;
import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    UserInfoSave autoLoginCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        autoLoginCheck=new UserInfoSave(SplashActivity.this);




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


                Log.e("what","what");

                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    //fcm 토큰 가져오기 실패
                                    Toast.makeText(SplashActivity.this, "토큰 업로드 실패! 잠시후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                                    Log.w("token_faile", "getInstanceId failed", task.getException());
                                    finish();
                                }
                                 String[] token = new String[1];
                                // Get new Instance ID token
                                token[0] = task.getResult().getToken();

                                try {


                                        Log.e("errr",""+autoLoginCheck.check());


                                        if (autoLoginCheck.check()) {
                                            JSONObject login_to_json = new JSONObject();
                                            login_to_json.put("id", autoLoginCheck.return_account().getId());
                                            login_to_json.put("password", autoLoginCheck.return_account().getPassword());
                                            login_to_json.put("token", token[0]);

                                            LoginTask loginTask = new LoginTask();
                                            String responscode = loginTask.execute(login_to_json.toString()).get();
                                            login(responscode);
                                        }else {
                                            Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void login(String rpcode){
        try {
            if (autoLoginCheck.check()){


                final UserInfoItem user=autoLoginCheck.return_account();


                    if (rpcode==null||!rpcode.equals("1")){

                        Toast.makeText(SplashActivity.this, "잠시후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        MyLocationLoadTask task=new MyLocationLoadTask();
                        try {
                            String asd=task.execute(user.getId()).get();
                            //php 에서 데이터 로드 안됨ㅇ
                            JSONArray jsonArray =new JSONArray(asd);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);

                            Log.e("splash_range",""+jsonArray.get(0)+jsonArray.getJSONObject(0).getInt("range")+jsonObject.getInt("range"));
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //채팅 서버 접속속

                       Intent chatting_service_intnet=new Intent(SplashActivity.this, ChattingService.class);

                        UserInfoSave userInfoSave=new UserInfoSave(this);
                        chatting_service_intnet.putExtra("id",userInfoSave.return_account().getId());
                        startService(chatting_service_intnet);


                        Toast.makeText(SplashActivity.this, "로그인 되셨습니다.", Toast.LENGTH_SHORT).show();


                        //로그인 되었을때 처리
                        if (getIntent().getIntExtra("chatting_check",0)==0){

                            Intent intent = new Intent(SplashActivity.this, MainFragment.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (getIntent().getIntExtra("chatting_check",0)==1){
                            Intent intent = new Intent(SplashActivity.this, MainFragment.class);
                            intent.putExtra("chatting_check",1);
                            intent.putExtra("id",getIntent().getStringExtra("id"));
                            intent.putExtra("product_key",getIntent().getStringExtra("product_key"));

                            //                                    intent.putExtra("id",getIntent().getStringExtra("id"));
//                                    Log.e("splash",getIntent().getStringExtra("message_Body"));

                            startActivity(intent);
                            finish();
                        }else if (getIntent().getIntExtra("chatting_check",0)==3) {

                            Intent intent = new Intent(SplashActivity.this, MainFragment.class);
                            intent.putExtra("chatting_check",3);
                            intent.putExtra("id",autoLoginCheck.return_account().getId());
                            intent.putExtra("product_key",getIntent().getStringExtra("product_key"));

                            startActivity(intent);
                            finish();

                        }else if (getIntent().getIntExtra("chatting_check",0)==4){

                        //상품 알림 메시지를 받고 들어왔을 경우우
                            Intent intent = new Intent(SplashActivity.this, MainFragment.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("chatting_check",4);
                            intent.putExtra("product_key",getIntent().getStringExtra("product_key"));
                            startActivity(intent);
                            finish();
                        }




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

}
