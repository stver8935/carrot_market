package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.Product;
import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FollowProductAdapter extends RecyclerView.Adapter<FollowProductAdapter.CustomViewHolder> {

    ArrayList<HomeFragmentItem> arrayList;
    Context context;

    public FollowProductAdapter(ArrayList<HomeFragmentItem> arrayList) {
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follow_product,parent,false);

       FollowProductAdapter.CustomViewHolder holder=new FollowProductAdapter.CustomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {
        holder.title.setText(arrayList.get(holder.getAdapterPosition()).getTitle());


        

        holder.id_and_location.setText(arrayList.get(holder.getAdapterPosition()).getId()+" . "+arrayList.get(holder.getAdapterPosition()).getLocation());
        if (arrayList.get(holder.getAdapterPosition()).getSales_completed().equals("0")){
            holder.sales_complted.setVisibility(View.GONE);
        }else {
            holder.sales_complted.setVisibility(View.VISIBLE);
        }


        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(arrayList.get(holder.getAdapterPosition()).getPrice());
        holder.price.setText(formattedStringPrice+" 원");


        if (arrayList.get(holder.getAdapterPosition()).getImage_path().length()>5) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.follow_product_image.setClipToOutline(true);
            }
            Glide.with(context).load(arrayList.get(holder.getAdapterPosition()).getImage_path()).into(holder.follow_product_image);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Product.class);
                intent.putExtra("product_key",""+arrayList.get(holder.getAdapterPosition()).getProduct_key());
                intent.putExtra("id",""+arrayList.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView follow_product_image;
        TextView id_and_location,price,title,sales_complted;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);


            sales_complted=itemView.findViewById(R.id.follow_product_item_sales_completed);

         title=itemView.findViewById(R.id.follow_product_item_title);
        follow_product_image=itemView.findViewById(R.id.follow_product_item_image);
        id_and_location=itemView.findViewById(R.id.follow_product_item_id_and_location);
        price=itemView.findViewById(R.id.follow_product_item_price);


        }
    }
}
