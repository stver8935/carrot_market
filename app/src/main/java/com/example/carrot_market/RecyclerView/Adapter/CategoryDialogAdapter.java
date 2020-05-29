package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.CONTROLLER.Dialog.Category;
import com.example.carrot_market.MODEL.DTO.CategoryDialogItem;
import com.example.carrot_market.R;

import java.util.ArrayList;

public class CategoryDialogAdapter extends RecyclerView.Adapter<CategoryDialogAdapter.CustomViewHolder> {

    private ArrayList<CategoryDialogItem> arrayList;
    private Context context;


    public CategoryDialogAdapter(ArrayList<CategoryDialogItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CategoryDialogAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    context=parent.getContext();
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
    CategoryDialogAdapter.CustomViewHolder holder=new CategoryDialogAdapter.CustomViewHolder(v);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryDialogAdapter.CustomViewHolder holder, int position) {
        holder.cateogry.setText(arrayList.get(holder.getAdapterPosition()).getCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("category_title",arrayList.get(holder.getAdapterPosition()).getCategory());
                ((Category)context).setResult(1,intent);
                ((Category) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView cateogry;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            cateogry=itemView.findViewById(R.id.category_dial_text);
        }
    }
}
