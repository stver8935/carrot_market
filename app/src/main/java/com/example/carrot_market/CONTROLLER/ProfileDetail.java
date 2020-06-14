package com.example.carrot_market.CONTROLLER;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.Dialog.MannerLeaveCheckDialog;
import com.example.carrot_market.MODEL.DTO.DealReviewItem;
import com.example.carrot_market.MODEL.DTO.ReceiveMannerItem;
import com.example.carrot_market.MODEL.HttpConnect.DealReviewLoadTask;
import com.example.carrot_market.MODEL.HttpConnect.FollowCheckTask;
import com.example.carrot_market.MODEL.HttpConnect.FollowTask;
import com.example.carrot_market.MODEL.HttpConnect.MannerEvaluationTask;
import com.example.carrot_market.MODEL.HttpConnect.ProfileLoadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.DealReviewAdapter;
import com.example.carrot_market.RecyclerView.Adapter.ReceiveMannerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ProfileDetail extends AppCompatActivity {

    private RecyclerView recyclerView_manner,recyclerView_deal_review;
    private LinearLayoutManager linearLayoutManager_m,linearLayoutManager_d;
    private ReceiveMannerAdapter adapter_manner;
    private DealReviewAdapter adapter_deal_review;

    private ArrayList<ReceiveMannerItem> arrayList_manner=new ArrayList<>();
    private ArrayList<DealReviewItem> arrayList_deal_review=new ArrayList<>();

    private ImageButton back,more,share;
    private TextView deal_review_text,manner_text,badge,sell_product,manner,review,id,profile_detail_myTemperature;
    private CircleImageView profile_image;
    private ProgressBar manner_temperacture;
    private ProgressBar asd;

    private boolean manner_leave_check=true;
    private boolean follow_check;


    private Button collect_see,manner_evaluate;
    private UserInfoSave userInfoSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);


        userInfoSave=new UserInfoSave(ProfileDetail.this);
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


        back=findViewById(R.id.profile_detail_back);
        more=findViewById(R.id.profile_detail_more);
        share=findViewById(R.id.profile_detail_share);

        id=findViewById(R.id.profile_detail_id);
        manner_temperacture=(ProgressBar)findViewById(R.id.profile_detaile_manner_temperature);
        profile_image=findViewById(R.id.profile_detail_image);
        profile_detail_myTemperature=findViewById(R.id.profile_detail_myTemperature);


        //받은 매너평가나 거래 후기가 없을때 띄워줄 텍스트
        deal_review_text=findViewById(R.id.profile_detail_deal_review_text);
        manner_text=findViewById(R.id.profile_detail_receive_manner_text);


        badge=findViewById(R.id.profile_detail_badge);
        sell_product=findViewById(R.id.profile_detail_sell_product);


        manner=findViewById(R.id.profile_detail_receive_manner);
        review=findViewById(R.id.profile_detail_deal_review);


        //모아보기 매너 평가
        collect_see=findViewById(R.id.profile_detail_collect_see);
        manner_evaluate=findViewById(R.id.profile_detail_manner_evaluate);



        recyclerView_deal_review.setVisibility(View.VISIBLE);
        recyclerView_manner.setVisibility(View.VISIBLE);


        //???
//        DealReviewItem item=new DealReviewItem();
//        arrayList_deal_review.add(item);
//        adapter_deal_review.notifyItemInserted(0);





        //--- 프로필 정보 로드 ----------------------------------
        //전체 리뷰를 대상으로 불러오기 때문에 user_type 에 null 값을 넣어준다
        //나에게 남긴 구매자 리뷰를 원한다면 buyer 판매자 리뷰를 원한다면 seller 문자열을 넣어준다.
    
        if (getIntent().getStringExtra("profile_id")!=null){
        DealReviewLoadTask dealReviewLoadTask=new DealReviewLoadTask(null,getIntent().getStringExtra("profile_id"),0,3,handler);
        Thread thread1=new Thread(dealReviewLoadTask);
        thread1.run();


        final MannerEvaluationTask mannerEvaluationTask=new MannerEvaluationTask(handler,getIntent().getStringExtra("profile_id"),20);
        Thread thread2=new Thread(mannerEvaluationTask);
        thread2.run();






        //상대편 매너평가 남기기
       manner_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                 Intent intent = new Intent(ProfileDetail.this, MannerLeave.class);
//                 intent.putExtra("id",getIntent().getStringExtra("profile_id"));
//                 startActivity(intent);
                MannerLeaveCheckDialog mannerLeaveCheckDialog=new MannerLeaveCheckDialog(ProfileDetail.this,getIntent().getStringExtra("profile_id"));
                mannerLeaveCheckDialog.setCancelable(true);
                mannerLeaveCheckDialog.show();



            }
        });




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        ProfileLoadTask profileLoadTask=new ProfileLoadTask(handler,getIntent().getStringExtra("profile_id"));
        Thread thread=new Thread(profileLoadTask);
        thread.run();


        if (!getIntent().getStringExtra("profile_id").equals(userInfoSave.return_account().getId())){
            FollowCheckTask followCheckTask=new FollowCheckTask(userInfoSave.return_account().getId(),getIntent().getStringExtra("profile_id"),handler);
            Thread thread3=new Thread(followCheckTask);
            thread3.run();
        }

        }


        //--- 프로필 정보 로드 ----------------------------------



        //더보기 버튼 클릭 이벤트
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popupMenu=new PopupMenu(getApplicationContext(),v);
                MenuInflater inflater=getMenuInflater();
                inflater.inflate(R.menu.profile_detail_more_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.profile_detail_more:
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

            if (follow_check){
                collect_see.setBackground(getResources().getDrawable(R.drawable.round_corner_nomal));
                follow_check=false;
            }else {
                collect_see.setBackground(getResources().getDrawable(R.drawable.corner_round_color_orange));
                follow_check=true;
            }

            FollowTask followTask=new FollowTask(userInfoSave.return_account().getId(),id.getText().toString(),handler);
            Thread thread=new Thread(followTask);
            thread.start();





        }
    });





    //판매중인 물품
    sell_product.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ProfileDetail.this, ""+id.getText().toString(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(ProfileDetail.this,SaleItemes.class);
            intent.putExtra("id",getIntent().getStringExtra("profile_id"));
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
            intent.putExtra("id",getIntent().getStringExtra("profile_id"));
            startActivity(intent);
        }
    });

    //거래 후기 보기 버튼
    review.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(ProfileDetail.this,DealReviewDetail.class);
            intent.putExtra("id",getIntent().getStringExtra("profile_id"));
            
            startActivity(intent);
        }
    });

    }




    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            //0은 프로필 정보 1은 받은 평가 2 는 받은 리뷰 글 4는 판매상품 갯수
            switch (msg.what){
                
                //유저 프로필 로드
                case 0:
                    try {
                        Log.e("profiessss",msg.getData().getString("user_profile"));
                        JSONObject jsonObject=new JSONObject(msg.getData().getString("user_profile"));
                        id.setText(jsonObject.getString("id"));

                        if (id.getText().toString().equals(userInfoSave.return_account().getId())){
                            collect_see.setVisibility(View.GONE);
                            manner_evaluate.setVisibility(View.GONE);

                        }


                        manner_temperacture.setProgress((int) jsonObject.getDouble("manner_temperature")*10);
                        profile_detail_myTemperature.setText(""+jsonObject.getDouble("manner_temperature"));
                        sell_product.setText("판매상품 "+jsonObject.getString("product_count")+"개");
                        if (!jsonObject.getString("profile_image").equals("null")){
                            Glide.with(ProfileDetail.this).load(API_URL+"image/"+jsonObject.getString("profile_image")).into(profile_image);
                        }else {
                            profile_image.setImageDrawable(getResources().getDrawable(R.drawable.profile_image_man));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                    case 1:
                        //받은 매너평가 통계
                        try {
                            Log.e("manner",msg.getData().getString("manner_evaluation_list"));
                            JSONArray review_evaluation_list_json_array=new JSONArray(msg.getData().getString("manner_evaluation_list"));

                            for (int i=0;i<review_evaluation_list_json_array.length();i++){
                            JSONObject review_evaluation_obj=review_evaluation_list_json_array.getJSONObject(i);
                            if (review_evaluation_obj.getInt("evaluation_type")==1){
                                recyclerView_manner.setVisibility(View.VISIBLE);
                                manner_text.setVisibility(View.GONE);
                                ReceiveMannerItem item=new ReceiveMannerItem();
                                item.setText(review_evaluation_obj.getString("review_title"));
                                item.setCount(review_evaluation_obj.getInt("review_count"));
                                arrayList_manner.add(item);
                                adapter_manner.notifyItemInserted(i);
                            }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        break;
                        
                        //거래 후기 부분
                case 2:
                    try {

                        //리스트의 0번째는 리뷰리스트의 유저타입별 갯수를 넣어준것
                        Log.e("review",msg.getData().getString("review"));

                        JSONArray review_json_array=new JSONArray(msg.getData().getString("review"));

                        if (review_json_array.length()-1<=0){
                            deal_review_text.setVisibility(View.VISIBLE);
                            deal_review_text.setText("받은 거래 후기가 없습니다.");
                        }else {
                            deal_review_text.setVisibility(View.GONE);
                        }


                        review.setText("받은 거래 후기("+review_json_array.getJSONObject(0).getString("review_count")+")");

                        for (int i=1;i<review_json_array.length();i++){
                            JSONObject deal_review_obj=review_json_array.getJSONObject(i);

                            DealReviewItem dealReviewItem=new DealReviewItem();
                            dealReviewItem.setUser_type(deal_review_obj.getString("user_type"));
                            dealReviewItem.setId(deal_review_obj.getString("id"));
                            dealReviewItem.setComent(deal_review_obj.getString("coment"));
                            dealReviewItem.setProduct_image(deal_review_obj.getString("review_image_path"));
                            dealReviewItem.setDate(deal_review_obj.getString("time_stamp"));


                            if (!deal_review_obj.getString("profile_image_path").equals("null")){
                                Glide.with(ProfileDetail.this).load(API_URL+"image/"+deal_review_obj.getString("profile_image_path")).into(profile_image);
                            }else {
                                profile_image.setImageDrawable(getResources().getDrawable(R.drawable.profile_image_man));
                            }


                            dealReviewItem.setAddress(deal_review_obj.getString("address"));
                            dealReviewItem.setLocation(deal_review_obj.getString("location"));
                            dealReviewItem.setLat(deal_review_obj.getDouble("lat"));
                            dealReviewItem.setLng(deal_review_obj.getDouble("lng"));

                            arrayList_deal_review.add(dealReviewItem);
                            adapter_deal_review.notifyItemInserted(i);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                    
                    //모아보기 결과 받는 부분 
                case 3:
                    Toast.makeText(ProfileDetail.this, "모아보기 설정 완료", Toast.LENGTH_SHORT).show();
                    break;

                    //팔로우 상태 체크
                case 4:
                    //팔로워 상태였다면
                    Log.e("follow_check",""+msg.getData().getString("follow_check"));

                    if (msg.getData().getString("follow_check").equals("1")){
                        follow_check=true;
                        collect_see.setBackground(getResources().getDrawable(R.drawable.corner_round_color_orange));

                    }else {
                        collect_see.setBackground(getResources().getDrawable(R.drawable.round_corner_nomal));
                        follow_check=false;
                    }

                    break;


                    //매너 평가를 남긴 상태였다면
                case 5:
                    if (msg.getData().getString("manner_check").equals("1")){
                        manner_evaluate.setBackground(getResources().getDrawable(R.drawable.corner_round_color_orange));
                        manner_leave_check=true;
                    }else {
                        manner_evaluate.setBackground(getResources().getDrawable(R.drawable.round_corner_nomal));
                        manner_leave_check=false;
                    }


                    break;

            }


            return false;
        }
    });






}
