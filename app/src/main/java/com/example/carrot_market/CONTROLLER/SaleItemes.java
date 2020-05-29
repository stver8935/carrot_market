package com.example.carrot_market.CONTROLLER;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.carrot_market.R;
import com.example.carrot_market.ViewPager.Adapter.SaleItemsAdapter;
import com.google.android.material.tabs.TabLayout;

public class SaleItemes extends AppCompatActivity {


    SaleItemsAdapter viewpager_adapter = new SaleItemsAdapter(getSupportFragmentManager());
    ViewPager tab_view_pager;
    TabLayout pager_tab_layout;

    ImageButton back;


    SaleItemsFragment fragment;
    SaleItemsFragment fragment2;
    SaleItemsFragment fragment3;


    int count = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_itemes);

        fragment = new SaleItemsFragment(null,getIntent().getStringExtra("id"));
        fragment2 = new SaleItemsFragment("0",getIntent().getStringExtra("id"));
        fragment3 = new SaleItemsFragment("1",getIntent().getStringExtra("id"));

        pager_tab_layout = findViewById(R.id.sale_items_tab);

        tab_view_pager = findViewById(R.id.sale_items_view_pager);

        setting_title(tab_view_pager);
        pager_tab_layout.setupWithViewPager(tab_view_pager);


        back = findViewById(R.id.sale_items_back);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }



    public void setting_title(ViewPager viewPager) {

        viewpager_adapter.addFragment(fragment, "전체", SaleItemes.this);
        viewpager_adapter.addFragment(fragment2, "판매중", SaleItemes.this);
        viewpager_adapter.addFragment(fragment3, "거래완료", SaleItemes.this);
        viewPager.setAdapter(viewpager_adapter);
    }


}