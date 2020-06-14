package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.DealReviewLeave;
import com.example.carrot_market.CONTROLLER.MyLeaveDealReview;
import com.example.carrot_market.CONTROLLER.SelectBuyer;
import com.example.carrot_market.MODEL.DTO.SellerListItem;
import com.example.carrot_market.R;

import java.util.ArrayList;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class SellerListAdapter extends RecyclerView.Adapter<SellerListAdapter.CustomViewHolder> {

    private ArrayList<SellerListItem> arrayList;
    private Context context;
    private String product_title;
    private String product_key;

    public SellerListAdapter(ArrayList<SellerListItem> arrayList, String title,String product_key) {
        this.arrayList = arrayList;
        this.product_title=title;
        this.product_key=product_key;

    }

    @NonNull
    @Override
    public SellerListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buyer_list,parent,false);
        SellerListAdapter.CustomViewHolder holder=new SellerListAdapter.CustomViewHolder(v);
        context=parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SellerListAdapter.CustomViewHolder holder, int position) {


        holder.id.setText(arrayList.get(holder.getAdapterPosition()).getId());

        String[] address= arrayList.get(holder.getAdapterPosition()).getAddress().split("구");
        String[] sub_address=address[0].split("시");

        holder.address.setText(sub_address[1]+"구 "+arrayList.get(holder.getAdapterPosition()).getLocation());



        if (!arrayList.get(holder.getAdapterPosition()).getProfile_image().equals("null")){
            Glide.with(context).load(API_URL+"image/"+arrayList.get(holder.getAdapterPosition()).getProfile_image()).into(holder.profile_image);
        }else {
            holder.profile_image.setImageDrawable(context.getResources().getDrawable(R.drawable.profile_image_man));
        }

        if (arrayList.get(holder.getAdapterPosition()).isReview_commit_check()){
            holder.deal_review.setText("후기 작성 마침");

            holder.deal_review.setTextColor(context.getResources().getColor(R.color.colorblack));
            holder.deal_review.setBackground(context.getResources().getDrawable(R.drawable.border_line));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, MyLeaveDealReview.class);
                    intent.putExtra("opponent_id",arrayList.get(holder.getAdapterPosition()).getId());
                    intent.putExtra("product_key",product_key);
                    context.startActivity(intent);
                }
            });




        }else {
            holder.deal_review.setText("후기 작성 하기");
            holder.deal_review.setTextColor(context.getResources().getColor(R.color.colorwhite));
            holder.deal_review.setBackground(context.getResources().getDrawable(R.drawable.corner_round_color_orange));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, DealReviewLeave.class);
                    intent.putExtra("opponent_id",arrayList.get(holder.getAdapterPosition()).getId());
                    intent.putExtra("product_key",product_key);

                    ((SelectBuyer)context).startActivityForResult(intent,0);

                }

            });
        }







    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_image;
        TextView id,address,deal_review;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        profile_image=itemView.findViewById(R.id.item_buyer_list_profile_image);
        id=itemView.findViewById(R.id.item_buyer_list_id);
        address=itemView.findViewById(R.id.item_buyer_list_address);
        deal_review=itemView.findViewById(R.id.item_buyer_list_deal_review);

        }
    }
}
