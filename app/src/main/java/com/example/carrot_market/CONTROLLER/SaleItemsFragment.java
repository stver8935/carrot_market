package com.example.carrot_market.CONTROLLER;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.ProductListAdapter;
import com.example.carrot_market.MODEL.DTO.ProductListItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SaleItemsFragment extends Fragment {

    private RecyclerView product_recyclerview;
    private ProductListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ProductListItem> arrayList=new ArrayList<>();
    private Context context;
    private int tab_count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_sale_items,container,false);
        context=getContext();


        product_recyclerview=v.findViewById(R.id.fragment_sale_items_recycler);
        adapter=new ProductListAdapter(arrayList,context);
        linearLayoutManager=new LinearLayoutManager(context);

        product_recyclerview.setAdapter(adapter);
        product_recyclerview.setLayoutManager(linearLayoutManager);


        for (int a=0;a<10;a++){
            ProductListItem item=new ProductListItem("의자1","방배동","어제","1,000원");
            arrayList.add(item);
            adapter.notifyItemInserted(a);
        }

        /*
        * 0-전체 물품 판매 데이터
        * 1-판매중인 물품 데이터
        * 2-판매가 완료된 물품 데이터
        * 아이템-ProductListItem
        * 리스트-arraylist
        *어댑터-adapter
        */


        ((SaleItemes)getActivity()).pager_tab_layout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {

                    case 0:
                        Log.e("전체 데이터 로드",""+tab.getPosition());

                        break;

                    case 1:
                        Log.e("판매중 데이터 로드",""+tab.getPosition());

                        break;


                    case 2:
                        Log.e("판매완료 데이터 로드드",""+tab.getPosition());
                       break;

                    default:
                        Toast.makeText(context, "잠시후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }



}
