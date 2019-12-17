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
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.CONTROLLER.Product;
import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.ProductSellerProductListItem;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ProductSellerProductListAdapter extends RecyclerView.Adapter<ProductSellerProductListAdapter.CustomViewHolder> {
ArrayList<ProductSellerProductListItem> arrayList;
Context context;
    public ProductSellerProductListAdapter(Context context,ArrayList<ProductSellerProductListItem> arrayList) {
        this.context=context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ProductSellerProductListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_seller_product_list_recycler_item,parent,false);

        ProductSellerProductListAdapter.CustomViewHolder holder=new ProductSellerProductListAdapter.CustomViewHolder(v);


        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    public void onBindViewHolder(@NonNull final ProductSellerProductListAdapter.CustomViewHolder holder, final int i) {


        holder.product_image.setClipToOutline(true);
        holder.product_image.setImageBitmap(arrayList.get(i).getProduct_image());
        holder.product_title.setText(arrayList.get(i).getProduct_title());
        holder.product_price.setText(arrayList.get(i).getProduct_price());




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Product.class);

                //상품리스트의 아이템을 클릭했을때 상품을 식별한 고유 번호를 인텐트에 담아서 보내준다.
                intent.putExtra("product_key",arrayList.get(holder.getAdapterPosition()).getProduct_key());

                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView product_image;
        protected TextView product_title;
        protected TextView product_price;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.product_image=itemView.findViewById(R.id.product_seller_product_list_item_image);
            this.product_title=itemView.findViewById(R.id.product_seller_product_list_item_title);
            this.product_price=itemView.findViewById(R.id.product_seller_product_list_item_price);

        }
    }
}
