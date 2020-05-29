package com.example.carrot_market.CONTROLLER;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.CheckBox;
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
    ArrayList<CheckBox> category_list = new ArrayList<>();
    RecyclerView recyclerView;
    AttentionCategoryAdapter attentionCategoryAdapter;
    ArrayList<AttentionCategoryItem> arrayList = new ArrayList<>();
    UserInfoSave userInfoSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_category);

        recyclerView = findViewById(R.id.attention_category_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(AttentionCategory.this, 2));
        attentionCategoryAdapter = new AttentionCategoryAdapter(arrayList);
        recyclerView.setAdapter(attentionCategoryAdapter);
        userInfoSave=new UserInfoSave(this);


        back = findViewById(R.id.attention_category_back);


        //최소 1개 이상의 카테고리가  체크 되어 있어야함 1개 이하 체크 됬을시 경고문구
        //ArrayList<CheckBox> arrayList=new ArrayList<>();


        CategoryLoadTask task=new CategoryLoadTask(userInfoSave.return_account().getId(),handler);
        Thread thread=new Thread(task);
        thread.run();






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
                            switch (jsonObject.getInt("category_select")){
                                case 0:
                                    item.setCheck(false);
                                    break;
                                case 1:
                                item.setCheck(true);
                            break;

                            }

                            item.setTitle(jsonObject.getString("category_name"));
                            arrayList.add(item);
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


}
