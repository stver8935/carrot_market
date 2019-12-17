package com.example.carrot_market.CONTROLLER;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.example.carrot_market.R;


public class MainFragment extends AppCompatActivity implements View.OnClickListener {

    Button home_btn,category_btn,write_btn,chatting_btn,my_carrot_btn;

    static Fragment selectfragment=new HomeFragment();
    CreatePostDialog dialog;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_fragment);




        home_btn=findViewById(R.id.nav_home);
    category_btn=findViewById(R.id.nav_category);
    write_btn=findViewById(R.id.nav_write);
    chatting_btn=findViewById(R.id.nav_chatting);
    my_carrot_btn=findViewById(R.id.nav_my_carrot);

        home_btn.setOnClickListener(this);
        category_btn.setOnClickListener(this);
        write_btn.setOnClickListener(this);
        chatting_btn.setOnClickListener(this);
        my_carrot_btn.setOnClickListener(this);


    //초기 프레그먼트 화면를 HomeFragment로 지정
        selectfragment=new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectfragment).commit();






    }



    //게시글 작성 다이얼로그에서 중고거래 게시글 작성 버튼 클릭 이벤트 작성
private View.OnClickListener del= new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MainFragment.this,AddProduct.class);
        startActivity(intent);
        dialog.dismiss();
    }
};

private View.OnClickListener promotion= new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MainFragment.this,AddPromotion.class);
        startActivity(intent);
        dialog.dismiss();
    }
};

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.nav_home:

                selectfragment=new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectfragment).commit();

                break;
            case R.id.nav_category:

                selectfragment=new CategoeyList();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectfragment).commit();

                break;

            case R.id.nav_write:

                dialog = new CreatePostDialog(MainFragment.this,del,promotion); // 왼쪽 버튼 이벤트
                dialog.setCancelable(true);
                dialog.getWindow().setGravity(Gravity.TOP);
                dialog.show();
                break;


            case R.id.nav_chatting:

                selectfragment=new ChattingList();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectfragment).commit();

                break;
            case R.id.nav_my_carrot:

                selectfragment=new MyProfile();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectfragment).commit();

                break;

        }
    }


}
