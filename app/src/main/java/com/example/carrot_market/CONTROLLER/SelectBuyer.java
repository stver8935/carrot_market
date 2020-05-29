package com.example.carrot_market.CONTROLLER;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.MODEL.DTO.SellerListItem;
import com.example.carrot_market.MODEL.HttpConnect.BuyerListTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.SellerListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class SelectBuyer extends AppCompatActivity {
    private ConstraintLayout product_layout;
    private ImageView product_image,select_buyer_back;
    private TextView product_title,select_buyer_no_buyer,find_buyer,no_select;

    //intent로 넘겨받을 제품키 변수
    private String product_key;
    private String title;


    private RecyclerView recyclerView;
    private SellerListAdapter buyerListAdapter;
    private ArrayList<SellerListItem> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_buyer);

         String product_image_path=getIntent().getStringExtra("image_path");
         title=getIntent().getStringExtra("title");
        product_key=getIntent().getStringExtra("product_key");



        arrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.select_buyer_recyclerview);
        buyerListAdapter=new SellerListAdapter(arrayList,title,product_key);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelectBuyer.this));
        recyclerView.setAdapter(buyerListAdapter);


        product_layout=findViewById(R.id.select_buyer_product_layout);
        product_image=findViewById(R.id.select_buyer_product_image);
        product_title=findViewById(R.id.select_buyer_product_title);
        select_buyer_back=findViewById(R.id.select_buyer_back);

        Glide.with(this).load(API_URL+"image/"+product_image_path).into(product_image);
        product_title.setText(title);

        //채팅창에서 사용자 찾기
    find_buyer=findViewById(R.id.select_buyer_find_buyer);
    //사용자목록이 없으신가요 텍스트
    select_buyer_no_buyer=findViewById(R.id.select_buyer_no_buyer);
    //선택안함 버튼
    no_select=findViewById(R.id.select_buyer_no_select);


        select_buyer_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    //채팅창에서 구매자 찾기 선택 보류
        find_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectBuyer.this,FindSellersInChat.class);
                intent.putExtra("product_title",title);
                intent.putExtra("product_key",product_key);
                startActivity(intent);
            }
        });


        //상품에대한 정보가 적혀있는 레이아웃 터치 이벤트
        product_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectBuyer.this,Product.class);
                intent.putExtra("product_key",product_key);
                startActivity(intent);
            }
        });


        //선택 안함
    no_select.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });




        BuyerListTask buyerListTask=new BuyerListTask(new UserInfoSave(this).return_account().getId(),product_key,handler);
        Thread thread=new Thread(buyerListTask);
        thread.start();
    }


    public Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

       switch (msg.what){
           case 0:
               try {
                   JSONArray jsonArray=new JSONArray(msg.getData().getString("buyer_list"));
                   Log.e("buyer_list",""+jsonArray);
                   if (0<jsonArray.length()){
                       //채팅창에서 사용자 찾기
                       find_buyer.setVisibility(View.GONE);
                       //사용자목록이 없으신가요 텍스트
                       select_buyer_no_buyer.setVisibility(View.GONE);

                   }

                   buyerListAdapter.notifyItemRangeRemoved(0,arrayList.size());
                   arrayList.clear();

               for (int i=0;i<jsonArray.length();i++){
                   JSONObject buyer_json=jsonArray.getJSONObject(i);
                   SellerListItem item=new SellerListItem();
                   item.setId(buyer_json.getString("id"));
                   item.setProfile_image(buyer_json.getString("profile_image"));
                   item.setAddress(buyer_json.getString("address"));
                   item.setLocation(buyer_json.getString("location"));
                   if (buyer_json.getInt("review_check")==1){
                   item.setReview_commit_check(true);
                   }else if (buyer_json.getInt("review_check")==0){
                       item.setReview_commit_check(false);
                   }

                   arrayList.add(item);
                   buyerListAdapter.notifyItemInserted(arrayList.size()-1);

               }

               } catch (JSONException e) {
                   e.printStackTrace();
               }

               break;

               //후기 작성시 업데이트 해주는 부분
               case 1:

                   for (int i=0;i<arrayList.size();i++){
                       if (arrayList.get(i).getId().equals(msg.getData().getString("id"))){
                           arrayList.get(i).setReview_commit_check(true);
                           buyerListAdapter.notifyItemChanged(i);
                       }

                   }

               break;
       }


            return false;
        }
    });


}
