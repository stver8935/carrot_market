package com.example.carrot_market.CONTROLLER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.carrot_market.R;
import com.example.carrot_market.ViewPager.Adapter.HomeSearchSectionPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeSearch extends AppCompatActivity {


    //사용할 뷰 페이저
     ViewPager viewPager;

    //뷰페이저를 사용하기위한 어댑터 객체를 생성해주고 생성자에 프래그먼트 매니저를 넣어준다.
    HomeSearchSectionPageAdapter adapter=new HomeSearchSectionPageAdapter(getSupportFragmentManager());
    EditText search_text;
    ImageButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);

        viewPager=findViewById(R.id.home_search_viewpager);

        setViewPager(viewPager);

        TabLayout tabLayout=findViewById(R.id.home_search_tab);
        tabLayout.setupWithViewPager(viewPager);




        search_text=findViewById(R.id.home_search_text);
        back_button=findViewById(R.id.home_search_back);

    //뒤로 가기 버튼 클릭 이벤트
    back_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });






    }

    //뷰페이저에 각각의 프래그먼트를 세팅해주기 위한 메서드
    public void setViewPager(ViewPager viewPager){

        adapter.addFragment(new HomeSearchFragmentDeal(), "중고거래");
        adapter.addFragment(new HomeSearchFragmentLocalInfo(), "동네정보");
        adapter.addFragment(new HomeSearchFragmentPerson(), "사람");

        viewPager.setAdapter(adapter);
    }





}
