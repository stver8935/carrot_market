package com.example.carrot_market.CONTROLLER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.DealReviewAdapter;
import com.example.carrot_market.RecyclerView.Adapter.ReceiveMannerAdapter;
import com.example.carrot_market.MODEL.DTO.DealReviewItem;
import com.example.carrot_market.MODEL.DTO.ReceiveMannerItem;

import java.util.ArrayList;

public class ProfileDetail extends AppCompatActivity {

    private RecyclerView recyclerView_manner,recyclerView_deal_review;
    private LinearLayoutManager linearLayoutManager_m,linearLayoutManager_d;
    private ReceiveMannerAdapter adapter_manner;
    private DealReviewAdapter adapter_deal_review;

    private ArrayList<ReceiveMannerItem> arrayList_manner=new ArrayList<>();
    private ArrayList<DealReviewItem> arrayList_deal_review=new ArrayList<>();

    private ImageButton back,more,share;
    private TextView deal_review_text,manner_text,badge,sell_product,manner,review;
    private Button collect_see,manner_evaluate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        recyclerView_manner=findViewById(R.id.profile_detail_receive_manner_list);
        recyclerView_deal_review=findViewById(R.id.profile_detail_deal_review_list);

        linearLayoutManager_m=new LinearLayoutManager(this);
        linearLayoutManager_d=new LinearLayoutManager(this);

        adapter_manner=new ReceiveMannerAdapter(arrayList_manner,this);
        adapter_deal_review=new DealReviewAdapter(arrayList_deal_review,this);


        //리사이클러뷰에 어댑터와 레이아웃 매니저 적용
        recyclerView_manner.setAdapter(adapter_manner);
        recyclerView_manner.setLayoutManager(linearLayoutManager_m);

        recyclerView_deal_review.setAdapter(adapter_deal_review);
        recyclerView_deal_review.setLayoutManager(linearLayoutManager_d);

//        recyclerView_deal_review.setVisibility(View.GONE);
//        recyclerView_manner.setVisibility(View.GONE);

        //버튼 초기화

//        private ImageButton back,more,share;
//        private TextView deal_review,badge,sell_product,manner;

        //badge,sell_product,manner,review
        back=findViewById(R.id.profile_detail_back);
        more=findViewById(R.id.profile_detail_more);
        share=findViewById(R.id.profile_detail_share);


        //받은 매너평가나 거래 후기가 없을때 띄워줄 텍스트
        deal_review_text=findViewById(R.id.profile_detail_deal_review_text);
        manner_text=findViewById(R.id.profile_detail_receive_manner_text);


        //
        badge=findViewById(R.id.profile_detail_badge);
        sell_product=findViewById(R.id.profile_detail_sell_product);


        manner=findViewById(R.id.profile_detail_receive_manner);
        review=findViewById(R.id.profile_detail_deal_review);
        collect_see=findViewById(R.id.profile_detail_collect_see);
        manner_evaluate=findViewById(R.id.profile_detail_manner_evaluate);




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //더보기 버튼 클릭 이벤트
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popupMenu=new PopupMenu(getApplicationContext(),v);
                MenuInflater inflater=getMenuInflater();
                inflater.inflate(R.menu.profile_detail_more_menu, popupMenu.getMenu());

                Toast.makeText(ProfileDetail.this, "test", Toast.LENGTH_SHORT).show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.profile_detail_more:
                            Toast.makeText(ProfileDetail.this, "hello", Toast.LENGTH_SHORT).show();
                            break;

                        }

                        return false;


                    }
                });

                popupMenu.show();


            }
        });

    //공유하기 버튼
    share.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });


    //모아보기 버튼 팔로워 팔로잉 기능
    collect_see.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });

    // 매너 평가하기 버튼
    manner_evaluate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });



    //판매중인 물품
    sell_product.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(ProfileDetail.this,SaleItemes.class);
            startActivity(intent);

        }
    });

    //유저가 취득한 타이틀 바로 가기 버튼
    badge.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    });

    //유저가 받은 매너 평가 바로가기
    manner.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(ProfileDetail.this,DetailManner.class);
            startActivity(intent);
        }
    });

    //거래 후기 보기 버튼
    review.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(ProfileDetail.this,DealReviewDetail.class);
            startActivity(intent);
        }
    });

    }


    //받은 매너평가나 거래후기가 있는지 체크하는 메서드

    private void check_coment(){
        //if (/*매너평가*/){
         //   recyclerView_manner.setVisibility(View.VISIBLE);
            //데이터 넣기
        //}
       // if (/*거래후기*/){
          //  recyclerView_deal_review.setVisibility(View.VISIBLE);
            //데이터 넣기
        //}

    }




}
