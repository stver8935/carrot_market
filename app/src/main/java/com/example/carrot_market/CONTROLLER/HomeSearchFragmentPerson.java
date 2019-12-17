package com.example.carrot_market.CONTROLLER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.HomeSearchFragmentAdapter;
import com.example.carrot_market.MODEL.DTO.HomeSearchFragmentItem;

import java.util.ArrayList;

public class HomeSearchFragmentPerson extends Fragment {


    private LinearLayoutManager layoutManager_search_list;
    private HomeSearchFragmentAdapter adapter_search_list;
    private ArrayList<HomeSearchFragmentItem> arrayList_search_list=new ArrayList<>();
    private RecyclerView recyclerView_search_list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        for (int a=0; a<10 ;a++) {
            HomeSearchFragmentItem item = new HomeSearchFragmentItem();
            item.setSearch_word("자전거");
            arrayList_search_list.add(item);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_home_search_fragment_person,container,false);


        recyclerView_search_list=v.findViewById(R.id.home_search_person_recyclerview);

        layoutManager_search_list=new LinearLayoutManager(v.getContext());

        adapter_search_list=new HomeSearchFragmentAdapter(arrayList_search_list,v.getContext());

        recyclerView_search_list.setAdapter(adapter_search_list);
        recyclerView_search_list.setLayoutManager(layoutManager_search_list);


        return v;
    }


}
