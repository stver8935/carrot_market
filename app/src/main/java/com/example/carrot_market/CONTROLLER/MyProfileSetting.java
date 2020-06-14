package com.example.carrot_market.CONTROLLER;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.Dialog.ProfileImageSetting;
import com.example.carrot_market.MODEL.HttpConnect.ProfileEditTask;
import com.example.carrot_market.MODEL.HttpConnect.ProfileLoadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.sangcomz.fishbun.define.Define;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class MyProfileSetting extends AppCompatActivity {

    public CircleImageView profile_image;

    private CircleImageView profile_camera;

    private EditText insert_nick_name;
    private TextView nick_name_info;
    private Button commit_button;
    public Uri profile_image_uri;
    private ProgressDialog progressDialog;
    private ImageButton back;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_setting);


        insert_nick_name=findViewById(R.id.my_profile_setting_nick_name);
        nick_name_info=findViewById(R.id.my_profile_setting_nick_name_info);
        back=findViewById(R.id.my_profile_setting_back);
        profile_image=findViewById(R.id.my_profile_setting_profile_image);
        profile_camera=findViewById(R.id.my_profile_setting_profile_image_camera);


        commit_button=findViewById(R.id.my_profile_setting_commit);


        ProfileLoadTask profileLoadTask=new ProfileLoadTask(handler,new UserInfoSave(MyProfileSetting.this).return_account().getId());
        Thread thread=new Thread(profileLoadTask);
        thread.start();







        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        insert_nick_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()<2){
                    nick_name_info.setText("닉네임은 최소 2자 이상입니다");
                    commit_button.setClickable(false);
                }else {
                    nick_name_info.setText("사용할 수 있는 닉네임 입니다.");
                    commit_button.setClickable(true);
                }

            }
        });



        profile_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FishBun.with(MyProfileSetting.this).setImageAdapter(new GlideAdapter()).setCamera(true).setMaxCount(1).startAlbum();


                //프로필 수정 다이얼로그
                ProfileImageSetting profileImageSetting=new ProfileImageSetting(MyProfileSetting.this);
                profileImageSetting.setCancelable(true);
                profileImageSetting.getWindow().setGravity(Gravity.TOP);
                profileImageSetting.show();

            }
        });





        commit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                progressDialog=new ProgressDialog(MyProfileSetting.this);
                progressDialog.setMessage("프로필 수정 중 입니다");
                progressDialog.setCancelable(false);
                progressDialog.show();


                String id,password;
                id=new UserInfoSave(MyProfileSetting.this).return_account().getId();
                password=new UserInfoSave(MyProfileSetting.this).return_account().getPassword();




                ProfileEditTask profileEditTask;

                   profileEditTask = new ProfileEditTask(id, password
                            , insert_nick_name.getText().toString(), profile_image_uri.toString(), handler, MyProfileSetting.this);


                Thread thread=new Thread(profileEditTask);
                thread.start();





            }
        });

    }




    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //위에서 인텐트에 담아준 리캐스트 코트를 액티비티 리슐트에서 받아서 처리한다.
        switch (requestCode){

            case Define.ALBUM_REQUEST_CODE:
                if (resultCode== RESULT_OK){

                    ArrayList<Parcelable> path = data.getParcelableArrayListExtra(Define.INTENT_PATH);

                    assert data != null;
                    profile_image_uri = (Uri) path.get(0);

                    Glide.with(MyProfileSetting.this).load(profile_image_uri).into(profile_image);
                }
        }
    }




    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {


            switch (msg.what){

                //프로필 정보
                case 0:
                    try {

                        JSONObject user_profile_json_array=new JSONObject(msg.getData().getString("user_profile"));
                        Log.e("user_profile",user_profile_json_array.toString());

                        insert_nick_name.setText(user_profile_json_array.getString("name"));


                        if (!user_profile_json_array.getString("profile_image").equals("null")){
                        profile_image_uri= Uri.parse(API_URL+"image/"+user_profile_json_array.getString("profile_image"));
                            Glide.with(MyProfileSetting.this).load(profile_image_uri).into(profile_image);

                        }else{
                            profile_image.setImageDrawable(getResources().getDrawable(R.drawable.profile_image_man));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;


                    //설정 실패
                case 1:

                    break;




                    //수정 완료 종료버튼
                case 2:


                    Log.e("수정완료","완료!");

                    try {
                        JSONObject profile_edit_response_to_json=new JSONObject(msg.getData().getString("profile_edit_response"));
                        profile_edit_response_to_json.getString("profile_image");


                        Intent intent=new Intent(MyProfileSetting.this,MyProfile.class);
                        intent.putExtra("result_profile_image",profile_edit_response_to_json.getString("profile_image"));
                        setResult(4,intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    progressDialog.dismiss();
                    finish();

                    break;

            }

            return false;
        }
    });

    public interface ProfileImageUpdate {
        void profile_update(String image_path);
    }


}
