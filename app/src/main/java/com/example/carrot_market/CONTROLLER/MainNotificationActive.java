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
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.NotifyActiveAdapter;
import com.example.carrot_market.MODEL.DTO.NotifyActiveItem;

import java.util.ArrayList;

public class MainNotificationActive extends Fragment {

    RecyclerView recyclerView;
    NotifyActiveAdapter activeAdapter;
    ArrayList<NotifyActiveItem> arrayList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ImageButton asd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main_notification_active,container,false);
        recyclerView=v.findViewById(R.id.main_notification_active_recycler);
        activeAdapter=new NotifyActiveAdapter(arrayList,v.getContext());
        linearLayoutManager=new LinearLayoutManager(v.getContext());

        recyclerView.setAdapter(activeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);


        for (int a=0; a< 10; a++){
            NotifyActiveItem item=new NotifyActiveItem("aa","aa");
            arrayList.add(item);
            activeAdapter.notifyItemInserted(a);
        }

        return v;
    }
}
