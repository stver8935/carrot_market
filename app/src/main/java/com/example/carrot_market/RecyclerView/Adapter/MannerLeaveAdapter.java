package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.DealReviewLeaveItem;
import com.example.carrot_market.R;

import java.util.ArrayList;

public class MannerLeaveAdapter extends RecyclerView.Adapter<MannerLeaveAdapter.CustomViewHolder> {


    private Context context;
    private ArrayList<DealReviewLeaveItem> arrayList;

    public MannerLeaveAdapter(ArrayList<DealReviewLeaveItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal_review_leave,parent,false);
        CustomViewHolder holder=new CustomViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {


        holder.manner_leave_list_item.setChecked(arrayList.get(holder.getAdapterPosition()).getCheck());

        holder.manner_leave_list_item.setText(arrayList.get(holder.getAdapterPosition()).getTitle());


        holder.manner_leave_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.get(holder.getAdapterPosition()).setCheck(holder.manner_leave_list_item.isChecked());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private CheckBox manner_leave_list_item;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

        manner_leave_list_item=itemView.findViewById(R.id.item_deal_review_leave_chekbox);

        }
    }
}
