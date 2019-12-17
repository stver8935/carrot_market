package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.ReceiveMannerItem;

import java.util.ArrayList;

public class ReceiveMannerAdapter extends RecyclerView.Adapter<ReceiveMannerAdapter.CustomViewHolder> {


    private ArrayList<ReceiveMannerItem> arrayList;
    private Context context;

    public ReceiveMannerAdapter(ArrayList<ReceiveMannerItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReceiveMannerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_manner_item,parent,false);
        ReceiveMannerAdapter.CustomViewHolder holder =new ReceiveMannerAdapter.CustomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiveMannerAdapter.CustomViewHolder holder, int position) {
        holder.count.setText(arrayList.get(holder.getAdapterPosition()).getCount());
        holder.text.setText(arrayList.get(holder.getAdapterPosition()).getText());

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView count,text;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            count=itemView.findViewById(R.id.receive_manner_item_count);
            text=itemView.findViewById(R.id.receive_manner_item_text);

        }
    }
}
