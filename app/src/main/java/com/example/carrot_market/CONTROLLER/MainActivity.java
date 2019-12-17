package com.example.carrot_market.CONTROLLER;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrot_market.CONTROLLER.Dialog.FindAccountDialog;

import com.example.carrot_market.MODEL.HttpConnect.LoginTask;
import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.AutoLoginCheck;

import com.example.carrot_market.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button activity_main_login;
    private EditText id,password;
    private TextView find_account,sign_up;
    private CheckBox auto_login;


    Retrofit retrofit;
    RetrofitService service;
    Call<ResponseBody> call;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity_main_login=findViewById(R.id.login_button);
        id=findViewById(R.id.login_id);
        password=findViewById(R.id.login_password);
        find_account=findViewById(R.id.login_find_account);
        sign_up=findViewById(R.id.login_sign_up);
        auto_login=findViewById(R.id.auto_login);

        SetClickListener();

         retrofit=new Retrofit.Builder()
                .baseUrl("http://13.125.130.142/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service=retrofit.create(RetrofitService.class);


        //자동로그인 체크 메서드

        try {
            auto_login();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };


        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();



    }




    //버튼 클릭 이벤트 구현
    @Override
    public void onClick(View v) {


        Intent intent;
        switch (v.getId()){
            case R.id.login_button:


                LoginTask loginTask=new LoginTask();

                try {

                    String responscode=loginTask.execute(id.getText().toString(),password.getText().toString()).get();

                    if (!responscode.equals("1")){
                        Toast.makeText(this, "아이디나 비밀번호를 다시 입력해 주세요"+responscode, Toast.LENGTH_SHORT).show();
                    }
                    else {


                        LoadingDialog dialog=new LoadingDialog(MainActivity.this,"로그인중 입니다.");
                        dialog.setCancelable(false);
                        dialog.show();

                        AutoLoginCheck autoLoginCheck=new AutoLoginCheck(MainActivity.this);
                        autoLoginCheck.insert_auto_login(auto_login.isChecked(),id.getText().toString(),password.getText().toString());

                        //계정정보 리턴해서 쉐어드에 저장 하기
                        intent=new Intent(MainActivity.this,MainFragment.class);
                        startActivity(intent);
                        dialog.dismiss();

                        finish();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
                //회원가입 버튼
            case R.id.login_sign_up:
                intent=new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
                break;

                //계정 찾기
                // 다이얼로그 형식으로 구현
            case R.id.login_find_account:
                intent=new Intent(MainActivity.this, FindAccountDialog.class);
                startActivity(intent);


                break;

        }

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    private void SetClickListener() {
        activity_main_login.setOnClickListener(this);
        find_account.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        auto_login.setOnClickListener(this);
    }


    private void auto_login() throws JSONException {
        AutoLoginCheck autoLoginCheck=new AutoLoginCheck(getApplicationContext());

        if (autoLoginCheck.check()){
            autoLoginCheck.insert_auto_login(auto_login.isChecked(),id.getText().toString(),password.getText().toString());
        }else {

        }


    }


}
