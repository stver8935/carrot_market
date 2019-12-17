package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.CONTROLLER.Chatting;
import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.ChattingListItem;

import java.util.ArrayList;

public class ChattingListAdapter extends RecyclerView.Adapter<ChattingListAdapter.CustomViewHolder> {

    Context context;
    ArrayList<ChattingListItem> arrayList;

    public ChattingListAdapter(Context context, ArrayList<ChattingListItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_list_item,parent,false);
        ChattingListAdapter.CustomViewHolder holder=new ChattingListAdapter.CustomViewHolder(v);

        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int i) {

//        holder.id.setText(arrayList.get(i).getProfile_id());
//        holder.location.setText(arrayList.get(i).getLocation());
//        holder.time.setText(arrayList.get(i).getTime());
//        holder.count.setText(arrayList.get(i).getCount());
//        holder.contents.setText(arrayList.get(i).getContents());
//

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Chatting.class);
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id,location,time,count,contents;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.chatting_list_item_profile_id);
            location=itemView.findViewById(R.id.chatting_list_item_location);
            time=itemView.findViewById(R.id.chatting_list_item_chatting_time);
            count=itemView.findViewById(R.id.chatting_list_item_chatting_count);
            contents=itemView.findViewById(R.id.chatting_list_item_chatting_contents);

        }


    }
}
