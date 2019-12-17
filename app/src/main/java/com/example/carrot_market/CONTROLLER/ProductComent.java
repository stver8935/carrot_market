package com.example.carrot_market.CONTROLLER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.ProductComentAdapter;
import com.example.carrot_market.MODEL.DTO.ProductComentItem;

import java.util.ArrayList;

public class ProductComent extends AppCompatActivity {
    private RecyclerView recyclerView_coment;
    private LinearLayoutManager linearLayoutManager;
    private ProductComentAdapter adapter;
    private ArrayList<ProductComentItem> arrayList=new ArrayList<>();
    private ImageButton back;
    private EditText write_coment;
    private Button write_commit;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_coment);



        //뒤로 가기 버튼
        back=findViewById(R.id.product_coment_back);

        //댓글 입력창
        write_coment=findViewById(R.id.product_coment_insert_text);

        //댓글 입력 완료
        write_commit=findViewById(R.id.product_coment_commit);

        title=findViewById(R.id.product_coment_title);


        recyclerView_coment=findViewById(R.id.product_coment_list_recycler);
        linearLayoutManager=new LinearLayoutManager(this);
        adapter=new ProductComentAdapter(arrayList,this,write_coment);
        recyclerView_coment.setAdapter(adapter);
        recyclerView_coment.setLayoutManager(linearLayoutManager);



        write_coment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()<=0){
                    write_commit.setTextColor(R.color.colorwhite);

                }else {
                    write_commit.setTextColor(R.color.colorblack);
                }
            }
        });


        write_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (write_coment.length()<=0){
                    Toast.makeText(ProductComent.this, "작성할 댓글을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                else {



                    //프론트 단에 댓글 보이기 이전에 댓글 데이터 서버에 입력
                    //로딩바 구현 commit 시 아히 명령문 실행


                    ProductComentItem item=new ProductComentItem();
                    item.setComent(write_coment.getText().toString());

                    //작성자 아이디 로드
                    item.setId(write_coment.getText().toString());


                    item.setProfile_image(R.drawable.test_chair);
                    arrayList.add(item);
                    adapter.notifyItemInserted(arrayList.size());



                    recyclerView_coment.scrollToPosition(arrayList.size()-1);
                    //입력했던 댓글 초기화
                    write_coment.setText(null);

                }

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });



        //댓글에 답변달기 버튼 클릭으로 들어온 경우라면 타이틀 변경

        if(getIntent().getStringExtra("key").equals("reply"))
        {

            title.setText("댓글에 답변달기");


            //제품을 식별하기위한 키
            getIntent().getStringExtra("product_key");

            //제품에에 달린 댓글을 식별하기 위한 키
            getIntent().getStringExtra("coment_key");
            //위에 있는 두개의 제품키와 댓글키를 이용하여 대댓글 로드
            //로컬디비에서 로드--데이터 삭제시 리셋 필요하므로 sharedpreference 로 사용
            //이하 댓글에 연관된 대댓글 로드


        }else {

            //모든 댓글과 각 댓글에 해당되는 대댓글을 로드

        }



    }


    @Override
    protected void onResume() {
        super.onResume();


    }
}
