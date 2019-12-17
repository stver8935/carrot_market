package com.example.carrot_market.CONTROLLER;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.carrot_market.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyProfile extends Fragment implements View.OnClickListener {


    protected CircleImageView profile_image;
    protected ImageButton app_setting_image_button;
    protected ImageView sales_history,purchase_history,favorite_list;
    protected Button my_location_setting,my_location_certification,my_key_word_notify,collect_view,invite_friends,share_market,notice,app_setting_button,profile_view,logout;
    protected Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_my_profile,container,false);

        context=getActivity();


        app_setting_image_button=v.findViewById(R.id.my_profile_setting);

        profile_image=v.findViewById(R.id.my_profile_profile_image);

        sales_history=v.findViewById(R.id.my_profile_sales_histoty);
        purchase_history=v.findViewById(R.id.my_profile_purchase_history);
        favorite_list=v.findViewById(R.id.my_profile_favorite_list);


        my_location_setting=v.findViewById(R.id.my_profile_my_location_setting);
        my_location_certification=v.findViewById(R.id.my_profile_my_location_certification);
        my_key_word_notify=v.findViewById(R.id.my_profile_key_word_notify);
        collect_view=v.findViewById(R.id.my_profile_collect_view);
        invite_friends=v.findViewById(R.id.my_profile_Invite_friends);
        share_market=v.findViewById(R.id.my_profile_share_market);
        notice=v.findViewById(R.id.my_profile_notice);
        app_setting_button=v.findViewById(R.id.my_profile_app_setting);
        profile_view=v.findViewById(R.id.my_profile_view);
        logout=v.findViewById(R.id.my_profile_logout);



        profile_view.setOnClickListener(this);
        profile_image.setOnClickListener(this);
        app_setting_image_button.setOnClickListener(this);
        sales_history.setOnClickListener(this);
        purchase_history.setOnClickListener(this);
        favorite_list.setOnClickListener(this);

        my_location_setting.setOnClickListener(this);

        my_location_certification.setOnClickListener(this);
        my_key_word_notify.setOnClickListener(this);
        collect_view.setOnClickListener(this);
        invite_friends.setOnClickListener(this);
        share_market.setOnClickListener(this);
        notice.setOnClickListener(this);
        app_setting_button.setOnClickListener(this);
        logout.setOnClickListener(this);

        return v;
    }


    //프로필 액티비티의 버튼 클릭 이벤트 처리
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){


            case R.id.my_profile_setting:
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();

                    break;

            case R.id.my_profile_profile_image:
                intent=new Intent(context,MyProfileSetting.class);
                context.startActivity(intent);
                break;
            case R.id.my_profile_view:
                intent=new Intent(context,ProfileDetail.class);
                context.startActivity(intent);
                break;

            case R.id.my_profile_sales_histoty:
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                break;

            case R.id.my_profile_purchase_history:
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                break;

            case R.id.my_profile_favorite_list:
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                break;
                //친구초대 버튼
            case R.id.my_profile_Invite_friends:
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                break;
                //당근마켓 공유 버튼
            case R.id.my_profile_share_market:
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                break;
                //공지사항 버튼
            case R.id.my_profile_notice:
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                break;
                //앱 설정 버튼
            case R.id.my_profile_app_setting:
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_profile_logout:
                intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
                ((MainFragment)context).finish();
                break;
        }


    }
}
