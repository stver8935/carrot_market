package com.example.carrot_market.CONTROLLER;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.InterFace.ProfileImageUpdate;
import com.example.carrot_market.MODEL.HttpConnect.LogoutTask;
import com.example.carrot_market.MODEL.HttpConnect.ProfileLoadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;


public class MyProfile extends Fragment implements ProfileImageUpdate,View.OnClickListener {


    protected CircleImageView profile_image;
    protected ImageView sales_history,purchase_history,favorite_list;
    protected Button my_location_setting,my_location_certification,my_key_word_notify,collect_view,profile_view,logout;
    private TextView my_profile_id,my_profile_location;
    protected Context context;
    private UserInfoSave userInfoSave;
    private LoadingDialog loadingDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfoSave=new UserInfoSave(getContext());
        ProfileLoadTask profileLoadTask=new ProfileLoadTask(handler,userInfoSave.return_account().getId());
        Thread thread=new Thread(profileLoadTask);
        thread.run();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_my_profile,container,false);

        context=getActivity();



        profile_image=v.findViewById(R.id.my_profile_profile_image);

        sales_history=v.findViewById(R.id.my_profile_sales_histoty);
        purchase_history=v.findViewById(R.id.my_profile_purchase_history);
        favorite_list=v.findViewById(R.id.my_profile_favorite_list);

        my_profile_id=v.findViewById(R.id.my_profile_login_id);
        my_profile_location=v.findViewById(R.id.my_profile_location);


        my_location_setting=v.findViewById(R.id.my_profile_my_location_setting);
        my_location_certification=v.findViewById(R.id.my_profile_my_location_certification);
        my_key_word_notify=v.findViewById(R.id.my_profile_key_word_notify);
        collect_view=v.findViewById(R.id.my_profile_collect_view);

        profile_view=v.findViewById(R.id.my_profile_view);
        logout=v.findViewById(R.id.my_profile_logout);



        profile_view.setOnClickListener(this);
        profile_image.setOnClickListener(this);
        sales_history.setOnClickListener(this);
        purchase_history.setOnClickListener(this);
        favorite_list.setOnClickListener(this);
        my_location_setting.setOnClickListener(this);
        my_location_certification.setOnClickListener(this);
        my_key_word_notify.setOnClickListener(this);
        collect_view.setOnClickListener(this);


        logout.setOnClickListener(this);




        return v;
    }


    //프로필 액티비티의 버튼 클릭 이벤트 처리
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){




            case R.id.my_profile_profile_image:
                intent=new Intent(context,MyProfileSetting.class);
                startActivityForResult(intent,4);

                break;
            case R.id.my_profile_view:
                intent=new Intent(context,ProfileDetail.class);
                intent.putExtra("profile_id",userInfoSave.return_account().getId());
                context.startActivity(intent);
                break;


                //내 판매 품목
            case R.id.my_profile_sales_histoty:
                intent=new Intent(context,ProductHistory.class);
                context.startActivity(intent);

                break;


                //구매 품목
            case R.id.my_profile_purchase_history:
                intent=new Intent(context,PurchaseHistory.class);
                context.startActivity(intent);

                break;

                //좋아요 한 상품
            case R.id.my_profile_favorite_list:
                Intent intent1=new Intent(context,FavoriteListActivity.class);
                startActivity(intent1);

                break;

            case R.id.my_profile_my_location_setting:
                intent=new Intent(context,MyLocationSetting.class);
                context.startActivity(intent);

                break;

            case R.id.my_profile_my_location_certification:
                intent=new Intent(context, MyLocationCertification.class);
                context.startActivity(intent);
                break;
                //키워드 알림 버튼
            case R.id.my_profile_key_word_notify:
                intent=new Intent(context,AddKeyword.class);
                startActivity(intent);

                break;
                //모이보기 버튼
            case R.id.my_profile_collect_view:
                intent=new Intent(context,FollowProduct.class);
                startActivity(intent);

                break;

                //로그아웃 처리
            case R.id.my_profile_logout:

                loadingDialog=new LoadingDialog(getContext(),"로그아웃 중입니다.");
                loadingDialog.setCancelable(false);
                loadingDialog.show();
                LogoutTask logoutTask=new LogoutTask(userInfoSave.return_account().getId(),userInfoSave.return_account().getPassword(),handler);
                Thread thread=new Thread(logoutTask);
                thread.start();
                break;
        }


    }
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {


            switch (msg.what){

                case 0:
                    //profile_info load

                    JSONObject jsonObject= null;
                    try {
                        jsonObject = new JSONObject(msg.getData().getString("user_profile"));
                        my_profile_id.setText(jsonObject.getString("id"));
                        my_profile_location.setText(jsonObject.getString("location"));
                        if (!jsonObject.getString("profile_image").equals("null")) {
                            Glide.with(MyProfile.this).load(API_URL + "image/" + jsonObject.getString("profile_image")).into(profile_image);
                        }else {
                            profile_image.setImageDrawable(context.getResources().getDrawable(R.drawable.profile_image_man));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case 1:

                    if (msg.getData()!=null){


                        try {
                            JSONObject logout_check_to_json=new JSONObject(msg.getData().getString("logout_check"));

                            if (logout_check_to_json.getString("id").equals(userInfoSave.return_account().getId())
                                    &&logout_check_to_json.getBoolean("logout_check")){

                                Intent intent=new Intent(getContext(),MainActivity.class);
                                startActivity(intent);
                                ((MainFragment)context).finish();
                                Toast.makeText(context, "로그아웃 하셨습니다.", Toast.LENGTH_SHORT).show();
                                loadingDialog.dismiss();
                            }else {
                                Toast.makeText(context, "로그아웃에 실패 하셨습니다.", Toast.LENGTH_SHORT).show();
                                loadingDialog.dismiss();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }



                    break;
            }

            return false;
        }
    });


    @Override
    public void profile_update(String image_path) {
        Glide.with(this).load(API_URL+"image/"+image_path).into(profile_image);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){

            case 4:

                if (data!=null) {

                    if (data.getStringExtra("result_profile_image").equals("null")){
                     profile_image.setImageDrawable(getResources().getDrawable(R.drawable.profile_image_man));
                    }else {
                        profile_update(data.getStringExtra("result_profile_image"));
                    }
                    }
                break;
        }

    }
}


