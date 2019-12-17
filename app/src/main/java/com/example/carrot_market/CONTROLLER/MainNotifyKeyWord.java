package com.example.carrot_market.CONTROLLER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.NotifyKeywordAdapter;
import com.example.carrot_market.MODEL.DTO.NotifyKeywordItem;

import java.util.ArrayList;

public class MainNotifyKeyWord extends Fragment {

    NotifyKeywordAdapter adapter;
     ArrayList<NotifyKeywordItem> arrayList=new ArrayList<>();
    ConstraintLayout all_del_bar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_main_notify_key_word,container,false);
        RecyclerView recyclerView = v.findViewById(R.id.main_notify_key_word_recycler);
        adapter=new NotifyKeywordAdapter(arrayList,v.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        all_del_bar=v.findViewById(R.id.main_notify_key_word_del_all_bar);
        TextView del_all = v.findViewById(R.id.main_notify_key_word_del_all);





        for (int a=0;a<10;a++){
            NotifyKeywordItem item=new NotifyKeywordItem("asd"+a,"asd");
            arrayList.add(item);
            adapter.notifyItemInserted(a);


        }
        del_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로컬디비에 있는 알림 리스트 삭제 시키기

                arrayList.clear();
                    adapter.notifyDataSetChanged();


            }
        });
        return v;
    }
}
