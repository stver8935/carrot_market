package com.example.carrot_market.CONTROLLER;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.carrot_market.R;
import com.example.carrot_market.ViewPager.Adapter.HomeNotifySelectionPageAdapter;
import com.google.android.material.tabs.TabLayout;

import static com.example.carrot_market.R.color.colororange;
import static com.example.carrot_market.R.color.colorwhite;

public class MainNotification extends AppCompatActivity {


    HomeNotifySelectionPageAdapter pageAdapter=new HomeNotifySelectionPageAdapter(getSupportFragmentManager());


    ViewPager viewPager;
    ImageButton delete,add_keyword,back;
    TextView title_text;
    ConstraintLayout title_layout;

    //탭 레이아웃에 넣어줄 프래그 먼트 객체 생성
    MainNotifyKeyWord keyword_fragment=new MainNotifyKeyWord();
    MainNotificationActivity notification_active_fragment=new MainNotificationActivity();
    //삭제모드를 구분하는 boolean 변수 생성;
    Boolean del_bool=true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notification);

        viewPager=findViewById(R.id.main_notification_pager);

        final TabLayout tabLayout=findViewById(R.id.main_notification_tab);
        tabLayout.setupWithViewPager(viewPager);
        setViewPager(viewPager);

        //각 버튼및 뷰 초기화
        add_keyword=findViewById(R.id.main_notification_edit);
        title_text=findViewById(R.id.main_notification_title_text);
        delete=findViewById(R.id.main_notification_del);
        back=findViewById(R.id.main_notification_back);

        title_layout=findViewById(R.id.main_notification_title_bar);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //키워드 탭으로 갔을시 키워드 추가버튼을 보여주게끔 하는 이벤트 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==1) {
                    add_keyword.setVisibility(View.VISIBLE);
                }
                else {
                    add_keyword.setVisibility(View.GONE);

                }
                }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        add_keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainNotification.this,AddKeyword.class);
                startActivity(intent);

            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (del_bool) {
                    keyword_fragment.all_del_bar.setVisibility(View.VISIBLE);
                    for (int a=0;a<keyword_fragment.arrayList.size();a++){
                    keyword_fragment.arrayList.get(a).setClear(false);
                    }
                    for (int a=0;a<notification_active_fragment.arrayList.size();a++){
                    notification_active_fragment.arrayList.get(a).setDelete_check(false);
                    }

                    notification_active_fragment.activeAdapter.notifyDataSetChanged();
                    keyword_fragment.adapter.notifyDataSetChanged();

                    title_layout.setBackgroundColor(v.getContext().getColor(colororange));
                    setTitle_color(false);
                    del_bool=false;
                }
                else {
                    keyword_fragment.all_del_bar.setVisibility(View.GONE);
                    for (int a=0;a<keyword_fragment.arrayList.size();a++){
                        keyword_fragment.arrayList.get(a).setClear(true);
                    }
                    for (int a=0;a<notification_active_fragment.arrayList.size();a++){
                        notification_active_fragment.arrayList.get(a).setDelete_check(true);
                    }
                    notification_active_fragment.activeAdapter.notifyDataSetChanged();
                    keyword_fragment.adapter.notifyDataSetChanged();
                    setTitle_color(true);


                    del_bool=true;
                }


            }
        });



    }

    public void setViewPager(ViewPager viewPager){
        pageAdapter.addFragment(notification_active_fragment, "활동알림");
        pageAdapter.addFragment(keyword_fragment, "키워드알림");
        viewPager.setAdapter(pageAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setTitle_color(Boolean check){

        if (check) {
            title_layout.setBackgroundColor(this.getColor(R.color.colorwhite));
            title_text.setTextColor(this.getColor(R.color.colorblack));
        }
        else {
            title_layout.setBackgroundColor(this.getColor(colororange));
            title_text.setTextColor(this.getColor(colorwhite));
        }

    }

}
