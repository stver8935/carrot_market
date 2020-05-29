package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.Dialog.PurchaseHistoryDialog;
import com.example.carrot_market.CONTROLLER.Product;
import com.example.carrot_market.CONTROLLER.SelectBuyer;
import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.CustomViewholder> {



    ArrayList<HomeFragmentItem> arrayList;


    Context context;

    public PurchaseHistoryAdapter(ArrayList<HomeFragmentItem> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public CustomViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_product_histoy,parent,false);
        PurchaseHistoryAdapter.CustomViewholder holder=new PurchaseHistoryAdapter.CustomViewholder(v);
        context=parent.getContext();

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewholder holder, int position) {
        Glide.with(context).load(API_URL+"image/"+arrayList.get(position).getImage_path()).into(holder.product_image);

        holder.deal_review_leave.setText("거래 후기 남기기");
        holder.deal_review_leave.setTextColor(context.getResources().getColor(R.color.colororange));
        holder.deal_review_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, SelectBuyer.class);
                intent.putExtra("product_key",""+arrayList.get(holder.getAdapterPosition()).getProduct_key());
                intent.putExtra("image_path",arrayList.get(holder.getAdapterPosition()).getImage_path());
                intent.putExtra("title",arrayList.get(holder.getAdapterPosition()).getTitle());
                context.startActivity(intent);

            }
        });


        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(arrayList.get(holder.getAdapterPosition()).getPrice());
        holder.price.setText(formattedStringPrice+" 원");

        holder.locaiton.setText(arrayList.get(holder.getAdapterPosition()).getLocation());


        if (arrayList.get(holder.getAdapterPosition()).getChatting_count()<1){
            holder.chat_count.setVisibility(View.GONE);
        }else {
            holder.chat_count.setVisibility(View.VISIBLE);
        }
        if (arrayList.get(holder.getAdapterPosition()).getFavorite_count()<1){
            holder.favorite_count.setVisibility(View.GONE);
        }else {
            holder.favorite_count.setVisibility(View.VISIBLE);
        }
        if (arrayList.get(holder.getAdapterPosition()).getComnet_count()<1){
            holder.coment_count.setVisibility(View.GONE);
        }else {
            holder.coment_count.setVisibility(View.VISIBLE);
        }

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PurchaseHistoryDialog  PurchaseHistoryDialog= new PurchaseHistoryDialog(context,""+arrayList.get(holder.getAdapterPosition()).getProduct_key()); // 왼쪽 버튼 이벤트
                PurchaseHistoryDialog.setCancelable(true);
                PurchaseHistoryDialog.getWindow().setGravity(Gravity.TOP);
                PurchaseHistoryDialog.show();

            }
        });



        holder.title.setText(arrayList.get(holder.getAdapterPosition()).getTitle());
        holder.chat_count.setText(""+arrayList.get(holder.getAdapterPosition()).getChatting_count());
        holder.favorite_count.setText(""+arrayList.get(holder.getAdapterPosition()).getFavorite_count());
        holder.coment_count.setText(""+arrayList.get(holder.getAdapterPosition()).getComnet_count());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Product.class);
                intent.putExtra("product_key",""+arrayList.get(holder.getAdapterPosition()).getProduct_key());
                context.startActivity(intent);
            }
        });

        if (arrayList.get(holder.getAdapterPosition()).getSales_completed().equals("0")){
            holder.sales_completed.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams params=(ConstraintLayout.LayoutParams)holder.price.getLayoutParams();
            params.topMargin=100;
            params.leftMargin=0;
            holder.price.setLayoutParams(params);
        }else {
            holder.sales_completed.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewholder extends RecyclerView.ViewHolder {
        protected ImageView product_image,more;
        protected TextView title,price,locaiton,sales_completed,deal_review_leave,chat_count,favorite_count,coment_count;

        public CustomViewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.product_histroy_item_title);
            product_image=itemView.findViewById(R.id.product_history_imageview);
            more=itemView.findViewById(R.id.product_histoy_more);
            price=itemView.findViewById(R.id.product_histroy_item_price);
            locaiton=itemView.findViewById(R.id.product_history_item_location);
            sales_completed=itemView.findViewById(R.id.product_histroy_item_sales_completed);
            deal_review_leave=itemView.findViewById(R.id.product_history_deal_review_leave);
            chat_count=itemView.findViewById(R.id.product_history_item_chat_count);
            favorite_count=itemView.findViewById(R.id.product_history_item_favorite_count);
            coment_count=itemView.findViewById(R.id.product_history_item_coment_count);


        }
    }
}
