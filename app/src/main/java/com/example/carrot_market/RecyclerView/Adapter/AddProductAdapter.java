package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.AddProductItem;

import java.util.ArrayList;

public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.CustomViewHolder> {


    private Context context;
   private ArrayList<AddProductItem> arrayList;
    private TextView camera_count;
    public AddProductAdapter(Context context, ArrayList<AddProductItem> arrayList, TextView camera_count) {
        this.context = context;
        this.arrayList = arrayList;
        this.camera_count=camera_count;
    }


    @NonNull
    @Override
    public AddProductAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_product_image_item,parent,false);
        AddProductAdapter.CustomViewHolder holder=new AddProductAdapter.CustomViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AddProductAdapter.CustomViewHolder holder, int position) {

        holder.product_image.setImageURI(arrayList.get(holder.getAdapterPosition()).getImage());
        //이미지 삭제

        //부모 레이아웃에 있는 현재 첨부한 이미지의 갯수 카운트 해주기위한 명령문
        camera_count.setText(arrayList.size()+"/10");

        holder.product_image_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

                //부모 레이아웃에 있는 현재 첨부한 이미지의 갯수 카운트 해주기위한 명령문
                //삭제 될때
                camera_count.setText(arrayList.size()+"/10");
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        ImageView product_image_clear;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

        product_image=itemView.findViewById(R.id.add_product_image_item);
        product_image_clear=itemView.findViewById(R.id.add_product_image_clear);

        }
    }
}
