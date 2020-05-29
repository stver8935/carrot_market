package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.MODEL.DTO.DealReviewItem;
import com.example.carrot_market.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

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
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_review_detail_item,parent,false);
        DealReviewAdapter.CustomViewHolder holder=new DealReviewAdapter.CustomViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DealReviewAdapter.CustomViewHolder holder, int position) {

        holder.id.setText(arrayList.get(holder.getAdapterPosition()).getId());
        holder.location.setText(arrayList.get(holder.getAdapterPosition()).getLocation());
        holder.date.setText(arrayList.get(holder.getAdapterPosition()).getDate());
        holder.coment.setText(arrayList.get(holder.getAdapterPosition()).getComent());

        if (1<arrayList.get(holder.getAdapterPosition()).getProduct_image().length()) {
            holder.product_image.setVisibility(View.VISIBLE);
            Glide.with(context).load(API_URL + "image/" + arrayList.get(holder.getAdapterPosition()).getProduct_image()).into(holder.product_image);
        }else {
//            holder.product_image.setVisibility(View.GONE);
        }
        if (!arrayList.get(holder.getAdapterPosition()).getProfile_image().isEmpty()) {
            holder.profile_image.setVisibility(View.VISIBLE);
            Glide.with(context).load(API_URL + "image/" + arrayList.get(holder.getAdapterPosition()).getProfile_image()).into(holder.profile_image);
        }else {

        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
       TextView id,location,date,coment;
       CircleImageView profile_image;
       ImageView product_image;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
       id=itemView.findViewById(R.id.deal_review_detail_id);
       location=itemView.findViewById(R.id.deal_review_detail_location);
       date=itemView.findViewById(R.id.deal_review_detail_date);
       profile_image=itemView.findViewById(R.id.deal_review_detail_profile_image);
        product_image=itemView.findViewById(R.id.deal_review_detail_product_image);
        coment=itemView.findViewById(R.id.deal_review_detail_coment);

        }
    }
}
