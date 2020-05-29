package com.example.carrot_market.CONTROLLER;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import com.example.carrot_market.CONTROLLER.InterFace.Search;
import com.example.carrot_market.R;
import com.example.carrot_market.ViewPager.Adapter.HomeSearchSectionPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeSearch extends AppCompatActivity   {


    //사용할 뷰 페이저
    private ViewPager viewPager;

    //뷰페이저를 사용하기위한 어댑터 객체를 생성해주고 생성자에 프래그먼트 매니저를 넣어준다.
    private HomeSearchSectionPageAdapter adapter=new HomeSearchSectionPageAdapter(getSupportFragmentManager());
    private SearchView search_text;
    private ImageButton back_button;
    private HomeSearchFragmentPerson HomeSearchFragmentPerson;
    private HomeSearchFragmentDeal HomeSearchFragmentDeal;
    private CreatePostDialog createPostDialog;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        HomeSearchFragmentDeal=new HomeSearchFragmentDeal();
        HomeSearchFragmentPerson=new HomeSearchFragmentPerson();



        viewPager=findViewById(R.id.home_search_viewpager);

        setViewPager(viewPager);

        TabLayout tabLayout=findViewById(R.id.home_search_tab);
        tabLayout.setupWithViewPager(viewPager);


        //검색창 자동 포커스
        search_text=findViewById(R.id.home_search_text);
        search_text.setIconified(false);



        back_button=findViewById(R.id.home_search_back);

        search_text.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {



                //검색 완료후 키보드를 내려서 뷰의 업데이트를 바로 보이게 해준다.
                View searchView =getCurrentFocus();
                if(searchView != null){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(),0);
                }
                Search context=(Search)HomeSearchFragmentDeal;
                context.search_deal(query);

                 context=(Search)HomeSearchFragmentPerson;
                context.search_deal(query);




                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




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

        adapter.addFragment(HomeSearchFragmentDeal, "중고거래");
        adapter.addFragment(HomeSearchFragmentPerson, "사람");

        viewPager.setAdapter(adapter);
    }


}
