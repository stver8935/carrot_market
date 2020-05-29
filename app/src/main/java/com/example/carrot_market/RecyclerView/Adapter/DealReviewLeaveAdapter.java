package com.example.carrot_market.RecyclerView.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.DealReviewLeaveItem;
import com.example.carrot_market.R;

import java.util.ArrayList;

public class DealReviewLeaveAdapter extends RecyclerView.Adapter<DealReviewLeaveAdapter.CustomViewHolder> {

    private ArrayList<DealReviewLeaveItem> arrayList;

    public DealReviewLeaveAdapter(ArrayList<DealReviewLeaveItem> arrayList) {
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal_review_leave,parent,false);
        DealReviewLeaveAdapter.CustomViewHolder holder=new DealReviewLeaveAdapter.CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {
        holder.checkBox.setText(arrayList.get(holder.getAdapterPosition()).getTitle());
        holder.checkBox.setChecked(arrayList.get(holder.getAdapterPosition()).getCheck());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.get(holder.getAdapterPosition()).setCheck(holder.checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        checkBox=itemView.findViewById(R.id.item_deal_review_leave_chekbox);

        }
    }
    public void addItem(DealReviewLeaveItem data) {
        // 외부에서 item을 추가시킬 함수입니다.
        arrayList.add(data);
    }

    @Override
    public long getItemId(int position){
        return arrayList.get(position).getValue();
    }

    public int check_count(){
        int check_count = 0;
        for (int i=0;i<arrayList.size();i++){

        if (arrayList.get(i).getCheck()){
            check_count++;
        }

        }
        return check_count;
    }
}
