package com.example.carrot_market.CONTROLLER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.HomeFragmentAdapter;
import com.example.carrot_market.RecyclerView.Adapter.HomeSearchFragmentAdapter;
import com.example.carrot_market.RecyclerView.Adapter.TagAdapter;
import com.example.carrot_market.MODEL.DTO.HomeSearchFragmentItem;
import com.example.carrot_market.MODEL.DTO.TagItem;

import java.util.ArrayList;

public class HomeSearchFragmentDeal extends Fragment {

     LinearLayoutManager linearLayoutManager;
     GridLayoutManager gridLayoutManager;
     RecyclerView recyclerView,recyclerView_tag;
     HomeSearchFragmentAdapter adapter;

     TagAdapter tagAdapter;
     ArrayList<HomeSearchFragmentItem> arrayList=new ArrayList<>();
     ArrayList<TagItem> arrayList_tag=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v=inflater.inflate(R.layout.activity_home_search_fragment_deal,container,false);
        //리사이클러 변수 초기화
        recyclerView=v.findViewById(R.id.home_search_deal_recyclerview);
        recyclerView_tag=v.findViewById(R.id.home_search_deal_search_word_recyclerview);

        /*화면에 뿌려질 아이템의 구조를 정의하기 위해 레이아웃매니저 객체 생성*/
        //최근검색어
        linearLayoutManager=new LinearLayoutManager(v.getContext());
        //
        gridLayoutManager=new GridLayoutManager(v.getContext(),5);



        //리사이클러뷰에 레이아웃 매니저 적용
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView_tag.setLayoutManager(gridLayoutManager);

        //어댑터 생성자로 객체 생성하여 아이템리스트를 넣어줌
        adapter=new HomeSearchFragmentAdapter(arrayList,v.getContext());
        tagAdapter=new TagAdapter(arrayList_tag,v.getContext());

        //리사이클러뷰에 어댑터 적용
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView_tag.setAdapter(tagAdapter);


        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int a=0;a<10;a++) {
            TagItem item = new TagItem();
            item.setTag("자전거");
            arrayList_tag.add(item);

        }

        for (int a=0;a<10;a++) {
            HomeSearchFragmentItem item = new HomeSearchFragmentItem();
            item.setSearch_word("자전거"+a);
            arrayList.add(item);

        }


    }
}
