package com.example.carrot_market.CONTROLLER;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.METHOD.MannerListSetting;
import com.example.carrot_market.MODEL.DTO.DealReviewLeaveItem;
import com.example.carrot_market.MODEL.HttpConnect.MannerLeaveTask;
import com.example.carrot_market.MODEL.HttpConnect.MyLeaveMannerListTask;
import com.example.carrot_market.MODEL.HttpConnect.ProfileLoadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.MannerLeaveAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class MannerLeave extends AppCompatActivity {


    private ImageView back;
    private CircleImageView profile_image;
    private TextView profile_id,profile_location,manner_temperature,manner_leave_info,manner_leave_type,leave_yes,leave_no;


    private RecyclerView manner_leave_list_recyclerview;
    private MannerLeaveAdapter adapter;
    private ArrayList<DealReviewLeaveItem> arrayList;




    private Intent accept_intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manner_leave);



        //받은 인텐트 처리
        accept_intent=getIntent();



        //매너를 남길 상대 정보
        profile_image=findViewById(R.id.manner_leave_profile_image);
        profile_id=findViewById(R.id.manner_leave_id);
        profile_location=findViewById(R.id.manner_leave_location);
        manner_temperature=findViewById(R.id.manner_leave_temperature);




        manner_leave_info=findViewById(R.id.manner_leave_info);
        manner_leave_type=findViewById(R.id.manner_leave_type);



        //매너평가 리스트를 위한 리사이클러뷰 및 어댑터 세팅
        manner_leave_list_recyclerview=findViewById(R.id.manner_leave_list_recycler_view);
        arrayList=new ArrayList<>();
        adapter=new MannerLeaveAdapter(arrayList);

        manner_leave_list_recyclerview.setAdapter(adapter);

        //이중 스크롤 스크롤 막기
        manner_leave_list_recyclerview.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }

            @Override
            public boolean canScrollHorizontally() { //가로 스크롤막기
                return false;
            }
        });




        //매너 평가 남기기 확인 또는 취소
        leave_yes=findViewById(R.id.manner_leave_yes);
        leave_no=findViewById(R.id.manner_leave_no);
        back=findViewById(R.id.manner_leave_back);


        ProfileLoadTask profileLoadTask=new ProfileLoadTask(handler,getIntent().getStringExtra("profile_id"));
        Thread thread=new Thread(profileLoadTask);
        thread.run();

        //매너 평가 타입이 긍정이거나 부정일때

            MyLeaveMannerListTask myLeaveMannerListTask=new MyLeaveMannerListTask(new UserInfoSave(this).return_account().getId()
                    ,accept_intent.getStringExtra("profile_id")
                    ,accept_intent.getStringExtra("manner_type"),handler);
            Thread thread1=new Thread(myLeaveMannerListTask);
            thread1.start();


            if (accept_intent.getStringExtra("manner_type").equals("0")){
                arrayList= new MannerListSetting(arrayList).insert_bad_manner_list();
                adapter.notifyItemRangeInserted(0,arrayList.size());
                manner_leave_info.setText("불편했던 점을 선택해 주세요.(최대 5개)");
                manner_leave_type.setVisibility(View.VISIBLE);
                manner_leave_type.setText("일반 비매너");
            }else {
                arrayList=new MannerListSetting(arrayList).insert_good_manner_list();
                adapter.notifyItemRangeInserted(0,arrayList.size());
                manner_leave_info.setText("남기고 싶은 칭찬을 선택해 주세요.");
                manner_leave_type.setVisibility(View.GONE);
            }




        leave_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //매너 평가 남기기 완료 처리 하기
                try {
                    manner_leave();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        leave_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public void manner_leave() throws JSONException {

        int check_count=0;
        JSONArray manner_list=new JSONArray();

        for (int i=0;i<arrayList.size();i++){
            if (arrayList.get(i).getCheck()){
                JSONObject manner_obj=new JSONObject();
                manner_obj.put("review_title",arrayList.get(i).getTitle());
                manner_list.put(manner_obj);

                check_count++;
            }
        }


        if (5<check_count) {
            Toast.makeText(this, "매너 평가를 5개 이상 선택 할수 없습니다.", Toast.LENGTH_SHORT).show();
        }else{

            Log.e("manner_check_list",manner_list.toString());
            Log.e("manner_check_id",accept_intent.getStringExtra("profile_id"));
            Log.e("manner_check_manner_t",accept_intent.getStringExtra("manner_type"));

            MannerLeaveTask mannerLeaveTask=new MannerLeaveTask(new UserInfoSave(this).return_account().getId()
                    ,accept_intent.getStringExtra("profile_id"),accept_intent.getStringExtra("manner_type"),
                    manner_list.toString(),handler);
            Thread thread=new Thread(mannerLeaveTask);
            thread.start();
        }


    }


    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:

                    JSONObject jsonObject= null;
                    try {
                        jsonObject = new JSONObject(msg.getData().getString("user_profile"));

                        Log.e("profile",""+jsonObject);
                    profile_id.setText(jsonObject.getString("id"));
                    manner_temperature.setText(""+jsonObject.getString("manner_temperature"));

                    if (!jsonObject.getString("profile_image").equals("null")){
                        Glide.with(MannerLeave.this).load(API_URL+"image/"+jsonObject.getString("profile_image")).into(profile_image);
                    }else {
                        profile_image.setImageDrawable(getResources().getDrawable(R.drawable.profile_image_man));
                    }

                    profile_location.setText(jsonObject.getString("location"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    //받은 매너평가 통계
                    try {
                        Log.e("manner",msg.getData().getString("manner_list"));
                        JSONArray review_evaluation_list_json_array=new JSONArray(msg.getData().getString("manner_list"));

                        for (int i=0;i<review_evaluation_list_json_array.length();i++){
                            for (int ii=0;ii<arrayList.size();ii++){
                                if (arrayList.get(ii).getTitle().equals(review_evaluation_list_json_array.getJSONObject(i).getString("review_title"))){
                                    arrayList.get(ii).setCheck(true);
                                    adapter.notifyItemChanged(ii);
                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;


                    //매너 리스트 업로드 성공여부
                case 3:
                    try {
                        JSONObject upload_response_code=new JSONObject(msg.getData().getString("manner_response_code"));

                        if (upload_response_code.getString("seccess").equals("true")){
                            Toast.makeText(MannerLeave.this, "매너 평가를 완료 했습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(MannerLeave.this, "매너 평가를 실패 했습니다.", Toast.LENGTH_SHORT).show();
                            Log.e("manner_leave_failed",upload_response_code.getString("info"));
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
