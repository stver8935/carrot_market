package com.example.carrot_market.RecyclerView.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;

import java.util.ArrayList;

public class MyLeaveDealReviewListAdapter extends RecyclerView.Adapter<MyLeaveDealReviewListAdapter.CustomViewHolder> {

    private ArrayList<String> arrayList;


    public MyLeaveDealReviewListAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyLeaveDealReviewListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_leave_deal_review,parent,false);
        MyLeaveDealReviewListAdapter.CustomViewHolder holder=new MyLeaveDealReviewListAdapter.CustomViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyLeaveDealReviewListAdapter.CustomViewHolder holder, int position) {

        holder.review_title.setText(arrayList.get(holder.getAdapterPosition()));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView review_title;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            review_title=itemView.findViewById(R.id.item_my_leave_deal_review_info);


        }
    }
}
