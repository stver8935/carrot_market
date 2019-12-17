package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.DealReviewDetailItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DealReviewDetailAdapter extends RecyclerView.Adapter<DealReviewDetailAdapter.CustomViewHolder> {

    ArrayList<DealReviewDetailItem> arrayList;
    Context context;


    public DealReviewDetailAdapter(ArrayList<DealReviewDetailItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public DealReviewDetailAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_review_detail_item,parent,false);
        DealReviewDetailAdapter.CustomViewHolder holder=new DealReviewDetailAdapter.CustomViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DealReviewDetailAdapter.CustomViewHolder holder, int position) {

        holder.id.setText(arrayList.get(holder.getAdapterPosition()).getId());
        holder.location.setText(arrayList.get(holder.getAdapterPosition()).getLocation());
        holder.date.setText(arrayList.get(holder.getAdapterPosition()).getDate());

        holder.profile_image.setImageResource(arrayList.get(holder.getAdapterPosition()).getProfile_image());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
       TextView id,location,date;
       CircleImageView profile_image;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
       id=itemView.findViewById(R.id.deal_review_detail_id);
       location=itemView.findViewById(R.id.deal_review_detail_location);
       date=itemView.findViewById(R.id.deal_review_detail_date);
       profile_image=itemView.findViewById(R.id.deal_review_detail_profile_image);

        }
    }
}
