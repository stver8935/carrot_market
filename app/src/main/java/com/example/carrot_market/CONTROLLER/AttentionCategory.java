package com.example.carrot_market.CONTROLLER;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.AttentionCategoryItem;
import com.example.carrot_market.MODEL.HttpConnect.CategoryLoadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.AttentionCategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AttentionCategory extends AppCompatActivity {

    ImageButton back;

    RecyclerView recyclerView;
    AttentionCategoryAdapter attentionCategoryAdapter;


    ArrayList<AttentionCategoryItem> temp_category_list = new ArrayList<>();
    ArrayList<AttentionCategoryItem> category_list = new ArrayList<>();
    UserInfoSave userInfoSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_category);

        recyclerView = findViewById(R.id.attention_category_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(AttentionCategory.this, 2));
        attentionCategoryAdapter = new AttentionCategoryAdapter(category_list);
        recyclerView.setAdapter(attentionCategoryAdapter);
        userInfoSave=new UserInfoSave(this);


        back = findViewById(R.id.attention_category_back);


        //최소 1개 이상의 카테고리가  체크 되어 있어야함 1개 이하 체크 됬을시 경고문구
        //ArrayList<CheckBox> arrayList=new ArrayList<>();


        CategoryLoadTask task=new CategoryLoadTask(userInfoSave.return_account().getId(),handler);
        Thread thread=new Thread(task);
        thread.run();



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    /*
     * 카테고리 1부터 14까지의 해쉬테이블
     * 1-디지털 가전 2-가구인테리어
     * 3-유아동/유아도서 4-생활/가공식품
     * 5-여성의류  6-여성잡화
     * 7-뷰티미용 8-남성패션/잡화
     * 9-스포츠/레저 10-게임/취미
     * 11-도서/티켓/음반 12-반려동물용품
     * 13-기타 중고물품 14-삽니다
     * */



    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    try {


                        JSONArray jsonArray=new JSONArray(msg.getData().getString("category_list"));



                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                            AttentionCategoryItem item = new AttentionCategoryItem();
                        AttentionCategoryItem temp_item = new AttentionCategoryItem();

                            switch (jsonObject.getInt("category_select")){
                                case 0:
                                    item.setCheck(false);
                                    temp_item.setCheck(false);
                                    break;
                                case 1:
                                item.setCheck(true);
                                temp_item.setCheck(true);
                                break;

                            }

                            item.setTitle(jsonObject.getString("category_name"));
                            temp_item.setTitle(jsonObject.getString("category_name"));
                            category_list.add(item);
                            temp_category_list.add(temp_item);
                            attentionCategoryAdapter.notifyItemInserted(i);
                    }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }



            return false;
        }
    });


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        CategorySetResult();
        super.finish();
    }

    private void CategorySetResult(){

        for (int i=0;i<category_list.size();i++){


            //처음 카테고리 리스트와 비교중 값의 변화가 있을시 반복문 종료후 값 반환
            if (!category_list.get(i).getTitle().equals(temp_category_list.get(i).getTitle())||category_list.get(i).getCheck()!=temp_category_list.get(i).getCheck()){

                Log.e("callresult","ok");
                setResult(1);
                break;
            }else if (i==category_list.size()){
                //마지막 비교일때
                Log.e("callresult","no");
                setResult(0);

            }

            Log.e("listcompare","list"+category_list.get(i).getTitle()+"//"+category_list.get(i).getCheck()+"---" +
                    "temp_list"+temp_category_list.get(i).getTitle()+"//"+temp_category_list.get(i).getCheck());

        }
    }

}
