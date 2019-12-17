package com.example.carrot_market.RecyclerView.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.DetailMannerItem;

import java.util.ArrayList;

public class DetailMannerAdapter extends RecyclerView.Adapter<DetailMannerAdapter.CustomViewHolder> {

    public DetailMannerAdapter(ArrayList<DetailMannerItem> arrayList) {
        this.arrayList = arrayList;
    }

    ArrayList<DetailMannerItem> arrayList;


    @NonNull
    @Override
    public DetailMannerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_manner_item,parent,false);
        DetailMannerAdapter.CustomViewHolder holder=new DetailMannerAdapter.CustomViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailMannerAdapter.CustomViewHolder holder, int position) {

        holder.title.setText(arrayList.get(holder.getAdapterPosition()).getTitle());
        holder.count.setText(arrayList.get(holder.getAdapterPosition()).getCount());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title,count;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        title=itemView.findViewById(R.id.detail_manner_item_title);
        count=itemView.findViewById(R.id.detail_manner_item_count);

        }
    }
}
