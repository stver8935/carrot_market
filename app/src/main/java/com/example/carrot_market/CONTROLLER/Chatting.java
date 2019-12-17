package com.example.carrot_market.CONTROLLER;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carrot_market.MODEL.CHATTINGSERVICE.ChattingService;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.ChattingAdapter;


import com.example.carrot_market.MODEL.DTO.ChattingItem;

import java.util.ArrayList;


import static android.graphics.Color.GRAY;


public class Chatting extends AppCompatActivity {

    public static ChattingAdapter adapter_c;
    public static RecyclerView recyclerView;
    public static LinearLayoutManager linearLayoutManager;
    public static ArrayList<ChattingItem> arrayList_c=new ArrayList<>();
    public static Context context;

    //채팅창 내부에서 사용할 뷰의 변수 선언
    TextView product_title,product_price;
    EditText edit_contents;
    ImageView product_image,chatting_send;
    Button deal_review;
    ImageButton add;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        context=this;
        //리사이클러 변수 초기화
        recyclerView=findViewById(R.id.chatting_recycler);


        //화면에 뿌려질 아이템의 구조를 정의하기 위해 레이아웃매니저 객체 생성
        linearLayoutManager=new LinearLayoutManager(this);

        //리사이클러뷰에 레이아웃 매니저 적용
        recyclerView.setLayoutManager(linearLayoutManager);


        //어댑터 생성자로 객체 생성하여 아이템리스트를 넣어줌
        adapter_c=new ChattingAdapter(this, arrayList_c);

        //리사이클러뷰에 어댑터 적용
        recyclerView.setAdapter(adapter_c);

add=findViewById(R.id.chatting_add);





        edit_contents=findViewById(R.id.chatting_contents);
        chatting_send=findViewById(R.id.chatting_send);





//adapter_c.registerAdapterDataObserver(new DataSetObserver(){
//    @Override
//    public void onChanged(){
//        super.onChanged();
//
//        Log.e("감지완료","삽입성공!");
//
//    }
//});



//add.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        for (int a=0;a<arrayList_c.size()-1;a++){
//            adapter_c.notifyItemInserted(a);
//        }
//        Log.e("asd","asd");
//    }
//});


        edit_contents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


            @Override
            public void afterTextChanged(Editable s) {

                if (s.length()<=0) {
                    chatting_send.setClickable(false);
                    chatting_send.setColorFilter(GRAY);
                }else {
                    chatting_send.setClickable(true);
                    chatting_send.setColorFilter(Color.parseColor("#ff9900"));
                }

            }
        });





chatting_send.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View v) {

        ChattingItem item=new ChattingItem();
        item.setContents(edit_contents.getText().toString());
        arrayList_c.add(item);
        adapter_c.notifyItemInserted(arrayList_c.size());

        recyclerView.scrollToPosition(arrayList_c.size()-1);

        ChattingService.SendmsgTask asd = new ChattingService.SendmsgTask();
        asd.execute(edit_contents.getText().toString());

        edit_contents.setText(null);




    }
});

    }



}
