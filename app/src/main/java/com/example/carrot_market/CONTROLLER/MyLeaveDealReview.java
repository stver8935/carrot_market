package com.example.carrot_market.CONTROLLER;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.MODEL.MyLeaveDealReviewTask;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.MyLeaveDealReviewListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyLeaveDealReview extends AppCompatActivity {

    private ArrayList<String> arrayList;
    private MyLeaveDealReviewListAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView my_leave_deal_review_back;

    private TextView my_leave_deal_review_title,my_leave_deal_review_info,my_leave_deal_review_id;
    String opponent_id = null;
    String product_key;
    boolean accept_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_leave_deal_review);

        my_leave_deal_review_title=findViewById(R.id.my_leave_deal_review_title);
        my_leave_deal_review_info=findViewById(R.id.my_leave_deal_review_info);
        my_leave_deal_review_id=findViewById(R.id.my_leave_deal_review_id);
        my_leave_deal_review_back=findViewById(R.id.my_leave_deal_review_back);

        recyclerView=findViewById(R.id.my_leave_deal_review_recyclerview);
        arrayList=new ArrayList<>();
        adapter=new MyLeaveDealReviewListAdapter(arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyLeaveDealReview.this){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }

            @Override
            public boolean canScrollHorizontally() { //가로 스크롤막기
                return false;
            }
        });
        recyclerView.setAdapter(adapter);




        if (getIntent()!=null) {


            opponent_id = getIntent().getStringExtra("opponent_id");
            product_key=getIntent().getStringExtra("product_key");

            //받은 받은 리뷰인지 보낸 리뷰인지
            //받은 리뷰라면 true 보낸 리뷰라면 false
            accept_check=getIntent().getBooleanExtra("accept_check",false);




            my_leave_deal_review_id.setText("To."+opponent_id);



            //product_key 는 스트링 문자열 null 이다
            Log.e("check",""+product_key.equals("null"));
            MyLeaveDealReviewTask task;

            //거래에대한 매너 평가 라면 product_key 가 null
            //거래에 대한 매너 평가 인지 그냥 매너 평가인지
            if (product_key.equals("null")){
                //받은 데이터 라면
                if (accept_check){
                    task=new MyLeaveDealReviewTask(opponent_id,new UserInfoSave(MyLeaveDealReview.this).return_account().getId(),null,handler);

                }else {
                    task=new MyLeaveDealReviewTask(new UserInfoSave(MyLeaveDealReview.this).return_account().getId(),opponent_id,null,handler);
                }

            }else {

                if (accept_check){
                    task=new MyLeaveDealReviewTask(opponent_id,new UserInfoSave(MyLeaveDealReview.this).return_account().getId(),product_key,handler);

                }else {
                    task=new MyLeaveDealReviewTask(new UserInfoSave(MyLeaveDealReview.this).return_account().getId(),opponent_id,product_key,handler);

                }

            }

            Thread thread=new Thread(task);
            thread.start();

        }


    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            if (accept_check){
                my_leave_deal_review_title.setText(opponent_id+"님이 보낸 따듯한 후기가 도착했어요.");
            }else {
                my_leave_deal_review_title.setText(opponent_id+"님에게 따듯한 후기를 보냈어요");
            }


            switch (msg.what){
                case 0:
                    try {
                    JSONArray jsonArray=new JSONArray(msg.getData().getString("my_leave_deal_review"));
                        Log.e("my_leave_deal_review",""+jsonArray) ;


                        my_leave_deal_review_info.setText(opponent_id+"님과 "+jsonArray.getJSONObject(0).getString("product_title")+"을 거래 했어요");

                        for (int i=1;i<jsonArray.length();i++){
                        JSONObject item_obj=jsonArray.getJSONObject(i);
                        arrayList.add(item_obj.getString("review_title"));
                        adapter.notifyItemInserted(arrayList.size()-1);
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 1:
                    my_leave_deal_review_info.setVisibility(View.GONE);
                    JSONArray jsonArray= null;
                    try {
                        jsonArray = new JSONArray(msg.getData().getString("my_leave_deal_review"));
                    Log.e("my_leave_deal_review",""+jsonArray) ;

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject item_obj=jsonArray.getJSONObject(i);
                        arrayList.add(item_obj.getString("review_title"));
                        adapter.notifyItemInserted(arrayList.size()-1);
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }

            return false;
        }
    });

}
