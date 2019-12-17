package com.example.carrot_market.CONTROLLER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.DetailMannerAdapter;
import com.example.carrot_market.MODEL.DTO.DetailMannerItem;

import java.util.ArrayList;

public class DetailManner extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DetailMannerAdapter adapter;
    private ArrayList<DetailMannerItem> arrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_manner);

    recyclerView=findViewById(R.id.detail_manner_list_recycler);

    adapter=new DetailMannerAdapter(arrayList);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));




    for (int a=0;a<10;a++) {
        DetailMannerItem item = new DetailMannerItem();
        item.setCount("1");
        item.setTitle("인기도");
        arrayList.add(item);
        adapter.notifyItemInserted(arrayList.size());

    }



    }
}
