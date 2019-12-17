package com.example.carrot_market.CONTROLLER;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.HomeSearchFragmentAdapter;
import com.example.carrot_market.RecyclerView.Adapter.TagAdapter;
import com.example.carrot_market.MODEL.DTO.HomeSearchFragmentItem;
import com.example.carrot_market.MODEL.DTO.TagItem;

import java.util.ArrayList;


public class HomeSearchFragmentLocalInfo extends Fragment {

   private TagAdapter adapter_tag;
    private ArrayList<TagItem> arrayList_tag=new ArrayList<>();
   private   RecyclerView recyclerView_tag;
    private GridLayoutManager gridLayoutManager_tag;


   private HomeSearchFragmentAdapter adapter_search_list;
   private ArrayList<HomeSearchFragmentItem> arrayList_search_list=new ArrayList<>();
    private LinearLayoutManager layoutManager_search_list;
    private RecyclerView recyclerView_search_list;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int a=0;a<11;a++) {
            TagItem item = new TagItem();
            item.setTag("자전거");
            arrayList_tag.add(item);

        }

        for (int a=0;a<10;a++) {
            HomeSearchFragmentItem item = new HomeSearchFragmentItem();
            item.setSearch_word("자전거");
            arrayList_search_list.add(item);

        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v=inflater.inflate(R.layout.activity_home_search_fragment_local_info,container,false);


    //태그와 관련된 리사이클러뷰 세팅
    recyclerView_tag=v.findViewById(R.id.home_search_local_info_tag_recyclerview);

    gridLayoutManager_tag=new GridLayoutManager(v.getContext(),5);



    recyclerView_tag.setLayoutManager(gridLayoutManager_tag);
    recyclerView_tag.setHasFixedSize(false);
    recyclerView_tag.setVerticalScrollBarEnabled(false);

    adapter_tag=new TagAdapter(arrayList_tag,v.getContext());

    recyclerView_tag.setAdapter(adapter_tag);


    //검색목록 리사이클러뷰 세팅
    recyclerView_search_list=v.findViewById(R.id.home_search_local_info_recyclerview);
    layoutManager_search_list=new LinearLayoutManager(v.getContext());
    adapter_search_list=new HomeSearchFragmentAdapter(arrayList_search_list,v.getContext());


    recyclerView_search_list.setLayoutManager(layoutManager_search_list);
    recyclerView_search_list.setAdapter(adapter_search_list);






        return v;
    }




}

