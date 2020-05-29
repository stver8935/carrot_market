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

import com.example.carrot_market.CONTROLLER.Product;
import com.example.carrot_market.MODEL.DTO.ProductListItem;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.CustomViewHolder> {

    ArrayList<ProductListItem> arrayList;
    Context context;
    private UserInfoSave userInfoSave;


    public ProductListAdapter(ArrayList<ProductListItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        userInfoSave=new UserInfoSave(context);
    }


    @NonNull
    @Override
    public ProductListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);

        ProductListAdapter.CustomViewHolder holder=new ProductListAdapter.CustomViewHolder(v);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductListAdapter.CustomViewHolder holder, int position) {



        holder.title.setText(arrayList.get(holder.getAdapterPosition()).getTitle());
        holder.location.setText(arrayList.get(holder.getAdapterPosition()).getLocation());
        holder.time.setText(arrayList.get(holder.getAdapterPosition()).getTime());
        holder.price.setText(arrayList.get(holder.getAdapterPosition()).getPrice());
        holder.chatcount.setText(""+arrayList.get(holder.getAdapterPosition()).getChat_count());
        holder.favoritecount.setText(""+arrayList.get(holder.getAdapterPosition()).getFavorite_count());
        holder.comentcount.setText(""+arrayList.get(holder.getAdapterPosition()).getComent_count());
        holder.product_image.setImageResource(arrayList.get(holder.getAdapterPosition()).getImage());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Product.class);
                intent.putExtra("product_key",arrayList.get(holder.getAdapterPosition()).getTitle());//제품키 넣어주기
                intent.putExtra("id",userInfoSave.return_account().getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView title,price,location,time,chatcount,favoritecount,comentcount;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image=itemView.findViewById(R.id.product_list_item_image);
            time=itemView.findViewById(R.id.product_list_item_time);
            title=itemView.findViewById(R.id.product_list_item_title);
            price=itemView.findViewById(R.id.product_list_item_price);
            location=itemView.findViewById(R.id.product_list_item_location);

            chatcount=itemView.findViewById(R.id.product_list_item_chat);
            favoritecount=itemView.findViewById(R.id.product_list_item_favorite);
            comentcount=itemView.findViewById(R.id.product_list_item_coment);
        }
    }
}
