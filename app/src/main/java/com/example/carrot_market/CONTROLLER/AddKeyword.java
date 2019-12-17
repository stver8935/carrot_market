package com.example.carrot_market.CONTROLLER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.AddKeywordAdapter;
import com.example.carrot_market.MODEL.DTO.AddKeywordItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddKeyword extends AppCompatActivity {


    ArrayList<AddKeywordItem> arrayList=new ArrayList<>();
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    AddKeywordAdapter adapter;
    ImageButton back;
    Button keyword_insert_button;
    TextView keyword_count;
    EditText keyword_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keyword);

        back=findViewById(R.id.add_keyword_back);
        keyword_text=findViewById(R.id.add_keyword_insert_text);
        keyword_insert_button=findViewById(R.id.add_keyword_insert_button);
        keyword_count=findViewById(R.id.add_keyword_count);


        recyclerView=findViewById(R.id.add_keyword_recycler);
        adapter=new AddKeywordAdapter(arrayList,this,keyword_count);

        gridLayoutManager=new GridLayoutManager(this,2);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);






        //현재 액티비티 종료 버튼
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //키워드 입력버튼 이벤트 처리
        keyword_insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(keyword_text.getText().toString().equals("")) {
                    Toast.makeText(AddKeyword.this, "키워드를 입력해 주세요", Toast.LENGTH_SHORT).show();

                }else if (30<=arrayList.size()){
                    Toast.makeText(AddKeyword.this, "키워드를 30개 이상 등록 할수 없습니다.", Toast.LENGTH_SHORT).show();
                }else {

                    if (check_keyword(keyword_text.getText().toString())){
                    AddKeywordItem item=new AddKeywordItem();
                    item.setKeyword(keyword_text.getText().toString());
                    arrayList.add(item);

                    keyword_count.setText("("+arrayList.size()+"/30)");

                    adapter.notifyItemInserted(arrayList.size());

                    //데이터베이스에 추가 코드 넣기 로컬디비에서 먼저 불러오고 서버 디비 데이터로 업데이트 로컬디비는 쉐어드

                    keyword_text.setText(null);
                    }
                    else {

                        Toast.makeText(AddKeyword.this, "이미 추가된 키워드 입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }


//이미 추가된 키워드가 있는 체크하는 메서드
public boolean check_keyword(String insert_keyword){
        for (AddKeywordItem keyword : arrayList){
            if (keyword.getKeyword().equals(insert_keyword)){
                return false;
            }
        }


        return true;
}
}
