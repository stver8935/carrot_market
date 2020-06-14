package com.example.carrot_market.CONTROLLER;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.carrot_market.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainFragment extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private Fragment selectfragment;
    private CreatePostDialog dialog;
    public static Context context;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_fragment);
    context=MainFragment.this;



    navigationView=findViewById(R.id.main_fragment_navibar);



        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                       @Override

               public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        switch (menuItem.getItemId()){
            case R.id.home_button:
                Log.e("chatting_room_size",""+ ChattingList.arrayList.size());
                selectfragment=new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectfragment).commit();

                break;


            case R.id.write_button:

                dialog = new CreatePostDialog(MainFragment.this,del); // 왼쪽 버튼 이벤트
                dialog.setCancelable(true);
                dialog.getWindow().setGravity(Gravity.TOP);
                dialog.show();

                break;


            case R.id.chat_button:

                selectfragment=new ChattingList();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectfragment).commit();

                break;
            case R.id.profile_button:

                selectfragment=new MyProfile();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectfragment).commit();

                break;

        }
        return true;
                               }
                           });
//
//    home_btn=findViewById(R.id.nav_home);
//    write_btn=findViewById(R.id.nav_write);
//    chatting_btn=findViewById(R.id.nav_chatting);
//    my_carrot_btn=findViewById(R.id.nav_my_carrot);

//        home_btn.setOnClickListener(this);
//        write_btn.setOnClickListener(this);
//        chatting_btn.setOnClickListener(this);
//        my_carrot_btn.setOnClickListener(this);


                Intent intent = getIntent();
        int chatting_check=intent.getIntExtra("chatting_check",0);
        String  product_key= intent.getStringExtra("product_key");
        Log.e("mainfragmnet_chatting_c",""+chatting_check);


        if (chatting_check==1){

//채팅룸 프래그먼트로 이동

//
            selectfragment=new ChattingList();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectfragment).commit();

                Intent intent1=new Intent(MainFragment.this,Chatting.class);
                intent1.putExtra("product_key",product_key);
                intent1.putExtra("id",intent.getStringExtra("id"));
                startActivity(intent1);


        }else if (chatting_check==0){


            //초기 프레그먼트 화면를 HomeFragment로 지정
            selectfragment=new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectfragment).commit();

        }else if(chatting_check==4){
            selectfragment=new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectfragment).commit();
            Intent intent1=new Intent(MainFragment.this,Product.class);
            intent1.putExtra("product_key",product_key);
            startActivity(intent1);

        }






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


//
//    @Override
//    public void onClick(View v) {
//
//    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("종료").setMessage("종료 하시겠습니까?");

        builder.setPositiveButton("네", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                finish();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectfragment.onActivityResult(requestCode,resultCode,data);
    }
}
