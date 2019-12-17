package com.example.carrot_market.CONTROLLER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.carrot_market.R;
import com.example.carrot_market.ViewPager.Adapter.SaleItemsAdapter;
import com.google.android.material.tabs.TabLayout;

public class DealReviewDetail extends AppCompatActivity {


    public TabLayout tabLayout;
    private ViewPager viewPager;
    private SaleItemsAdapter adapter=new SaleItemsAdapter(getSupportFragmentManager());
    private ImageButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_review_detail);

        tabLayout=findViewById(R.id.deal_review_detail_tab_layout);
        viewPager=findViewById(R.id.deal_review_detail_view_pager);
        tabLayout.setupWithViewPager(viewPager);

        pager_setting();

        back=findViewById(R.id.deal_review_detail_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void pager_setting(){
        adapter.addFragment(new DealReviewDetailFragment(),"전체 후기");
        adapter.addFragment(new DealReviewDetailFragment(),"구매자 후기");
        adapter.addFragment(new DealReviewDetailFragment(),"판매자 후기");
        viewPager.setAdapter(adapter);
    }

}
