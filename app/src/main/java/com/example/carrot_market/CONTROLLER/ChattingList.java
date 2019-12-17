package com.example.carrot_market.CONTROLLER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.carrot_market.MODEL.CHATTINGSERVICE.ChatClientinitializer;

import com.example.carrot_market.MODEL.CHATTINGSERVICE.ChattingService;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.ChattingListAdapter;

import com.example.carrot_market.MODEL.DTO.ChattingListItem;

import java.util.ArrayList;



import io.netty.channel.Channel;




public class ChattingList extends Fragment {
     ChattingListAdapter adapter;

       RecyclerView recyclerView;
//컴퓨터 내부 아이피 주소 -110.70.16.56

    //핸드폰 내부 아이피 주소AND 컴퓨터 외부 아이피 주소 -- 118.235.8.79


      LinearLayoutManager linearLayoutManager;
   ArrayList<ChattingListItem> arrayList=new ArrayList<>();
    private String line;

    static final String HOST = System.getProperty("host", "192.168.0.21");
    static final int PORT = Integer.parseInt(System.getProperty("port", "3306"));


    //채팅을 위한 변수
   private Handler handler;
    static Channel socketchannel;
   private String data;


   public static TextView title;

    static Context context;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent=new Intent(getContext(),ChattingService.class);
        getActivity().startService(intent);



    }







    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
        try {
//            socketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_chatting_list,container,false);

        //리사이클러 변수 초기화
        recyclerView=v.findViewById(R.id.chatting_list_recycler);


        //화면에 뿌려질 아이템의 구조를 정의하기 위해 레이아웃매니저 객체 생성
        linearLayoutManager=new LinearLayoutManager(v.getContext());

        //리사이클러뷰에 레이아웃 매니저 적용
        recyclerView.setLayoutManager(linearLayoutManager);


        //어댑터 생성자로 객체 생성하여 아이템리스트를 넣어줌
        adapter=new ChattingListAdapter(v.getContext(),arrayList);

        //리사이클러뷰에 어댑터 적용
        recyclerView.setAdapter(adapter);

        ChattingListItem item = new ChattingListItem();
        item.setProfile_id("asd");
        item.setContents("asd");
        item.setTime("asd");
        item.setLocation("asd");
        item.setCount("asd");
        arrayList.add(item);
        adapter.notifyItemInserted(0);







v.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        Log.e("SEND","SEND!!");



    }
});

        return v;



    }


}
