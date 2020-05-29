package com.example.carrot_market.CONTROLLER;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.AlramTimeItem;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.AlramTimeAdapter;

import java.util.ArrayList;

public class AlramTimeDialog extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<AlramTimeItem> arrayList=new ArrayList<AlramTimeItem>();
    AlramTimeAdapter alramTimeAdapter;
    LinearLayoutManager linearLayoutManager;
    Context context;
    Handler handler;
    TextView alram_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alram_time);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT /*our width*/, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);


        linearLayoutManager=new LinearLayoutManager(context);
        recyclerView=findViewById(R.id.alram_time_dialog_recycler);
        alramTimeAdapter=new AlramTimeAdapter(arrayList,alram_text);
        recyclerView.setAdapter(alramTimeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);



        for (int i =1; i<6;i++){
            AlramTimeItem item=new AlramTimeItem();

         if(i<=3){
             item.setMinite(i * 10);
             item.setTime_text(item.getMinite()+" 분 전");
         }else {
             item.setMinite(arrayList.get(i-2).getMinite()*2);
             item.setTime_text(item.getMinite()/60+" 시간 전");
         }
         arrayList.add(item);
         alramTimeAdapter.notifyItemInserted(arrayList.size()-1);


        }



    }





}