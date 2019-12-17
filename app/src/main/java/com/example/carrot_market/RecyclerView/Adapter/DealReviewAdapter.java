package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.DealReviewItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DealReviewAdapter extends RecyclerView.Adapter<DealReviewAdapter.CustomViewHolder> {
    ArrayList<DealReviewItem> arrayList;
    Context context;

    public DealReviewAdapter(ArrayList<DealReviewItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DealReviewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_review_item,parent,false);
        DealReviewAdapter.CustomViewHolder holder=new DealReviewAdapter.CustomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DealReviewAdapter.CustomViewHolder holder, int position) {

        holder.image.setImageResource(arrayList.get(holder.getAdapterPosition()).getProfile_image());
        holder.profile_id.setText(arrayList.get(holder.getAdapterPosition()).getProfile_id());
        holder.profile_date_location.setText(arrayList.get(holder.getAdapterPosition()).getLocation_date());
        holder.coment.setText(arrayList.get(holder.getAdapterPosition()).getComent());

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
         private CircleImageView image;
        private TextView profile_id,profile_date_location,coment;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.deal_review_item_profile_image);
            profile_id=itemView.findViewById(R.id.deal_review_item_profile_id);
            profile_date_location=itemView.findViewById(R.id.deal_review_item_profile_location_and_date);
            coment=itemView.findViewById(R.id.deal_review_item_profile_coment);


        }
    }
}
