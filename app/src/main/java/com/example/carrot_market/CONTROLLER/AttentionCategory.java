package com.example.carrot_market.CONTROLLER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.carrot_market.R;

import java.util.ArrayList;

public class AttentionCategory extends AppCompatActivity implements View.OnClickListener {

    CheckBox category1,category2,category3,category4,category5,category6,category7,category8,category9,category10,
    category11,category12,category13,category14;
    ImageButton back;
    ArrayList<CheckBox> category_list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_category);



        category1=findViewById(R.id.category_1);
        category2=findViewById(R.id.category_2);
        category3=findViewById(R.id.category_3);
        category4=findViewById(R.id.category_4);
        category5=findViewById(R.id.category_5);
        category6=findViewById(R.id.category_6);
        category7=findViewById(R.id.category_7);
        category8=findViewById(R.id.category_8);
        category9=findViewById(R.id.category_9);
        category10=findViewById(R.id.category_10);
        category11=findViewById(R.id.category_11);
        category12=findViewById(R.id.category_12);
        category13=findViewById(R.id.category_13);
        category14=findViewById(R.id.category_14);

        category_list.add(category1);
        category_list.add(category2);
        category_list.add(category3);
        category_list.add(category4);
        category_list.add(category5);
        category_list.add(category6);
        category_list.add(category7);
        category_list.add(category8);
        category_list.add(category9);
        category_list.add(category10);
        category_list.add(category11);
        category_list.add(category12);
        category_list.add(category13);
        category_list.add(category14);

        back=findViewById(R.id.attention_category_back);
        btn_setting();


        //최소 1개 이상의 카테고리가  체크 되어 있어야함 1개 이하 체크 됬을시 경고문구
        //ArrayList<CheckBox> arrayList=new ArrayList<>();




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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.category_1:

                if (category_check()){
                    category_list.get(0).setChecked(true);
                }

                break;
            case R.id.category_2:
                if (category_check()){
                    category_list.get(1).setChecked(true);
                }
                break;
            case R.id.category_3:
                if (category_check()){
                    category_list.get(2).setChecked(true);
                }
                break;
            case R.id.category_4:
                if (category_check()){
                    category_list.get(3).setChecked(true);
                }
                break;
            case R.id.category_5:
                if (category_check()){
                    category_list.get(4).setChecked(true);
                }
                break;
            case R.id.category_6:
                if (category_check()){
                    category_list.get(5).setChecked(true);
                }
                break;
            case R.id.category_7:
                if (category_check()){
                    category_list.get(6).setChecked(true);
                }
                break;
            case R.id.category_8:
                if (category_check()){
                    category_list.get(7).setChecked(true);
                }
                break;
            case R.id.category_9:
                if (category_check()){
                    category_list.get(8).setChecked(true);
                }
                break;
            case R.id.category_10:
                if (category_check()){
                    category_list.get(9).setChecked(true);
                }
                break;
            case R.id.category_11:
                if (category_check()){
                    category_list.get(10).setChecked(true);
                }
                break;
            case R.id.category_12:
                if (category_check()){
                    category_list.get(11).setChecked(true);
                }
                break;
            case R.id.category_13:
                if (category_check()){
                    category_list.get(12).setChecked(true);
                }
                break;
            case R.id.category_14:
                if (category_check()){
                    category_list.get(13).setChecked(true);
                }
                break;
                //액티비티 종료 버튼
            case R.id.attention_category_back:
                finish();
                break;

        }

    }

    private void btn_setting(){
        category1.setOnClickListener(this);
        category2.setOnClickListener(this);
        category3.setOnClickListener(this);
        category4.setOnClickListener(this);
        category5.setOnClickListener(this);
        category6.setOnClickListener(this);
        category7.setOnClickListener(this);
        category8.setOnClickListener(this);
        category9.setOnClickListener(this);
        category10.setOnClickListener(this);
        category11.setOnClickListener(this);
        category12.setOnClickListener(this);
        category13.setOnClickListener(this);
        category14.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private boolean category_check(){
        int category_check_num=0;
        for (int i=0;i<category_list.size();i++){

            if(category_list.get(i).isChecked()){
            category_check_num++;
            }
        }
        if (category_check_num <1) {
            Toast.makeText(this, "카테고리를 한개 이상 선택해 주세요", Toast.LENGTH_SHORT).show();
            return true;
        }
    return false;
    }


}
