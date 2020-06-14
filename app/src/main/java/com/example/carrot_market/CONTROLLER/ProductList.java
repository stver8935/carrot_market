package com.example.carrot_market.CONTROLLER;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.ProductListItem;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.ProductListAdapter;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {


    private ArrayList<ProductListItem> arrayList=new ArrayList<>();
    private ProductListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ImageButton back;
    private ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView=findViewById(R.id.product_list_recycler);
        adapter=new ProductListAdapter(arrayList,this);
        linearLayoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        back=findViewById(R.id.product_list_back);
        search=findViewById(R.id.product_list_search);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductList.this,HomeSearch.class);
                startActivity(intent);
            }
        });



        for (int a=0;a<10;a++){
            ProductListItem item=new ProductListItem("asd","asd","asd","asd");
            arrayList.add(item);
            adapter.notifyItemInserted(a);

        }

    }


}
