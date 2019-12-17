package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.NotifyKeywordItem;

import java.util.ArrayList;

public class NotifyKeywordAdapter extends RecyclerView.Adapter<NotifyKeywordAdapter.CustomViewHolder> {

    ArrayList<NotifyKeywordItem> arrayList;
    Context context;

    public NotifyKeywordAdapter(ArrayList<NotifyKeywordItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public NotifyKeywordAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.keyword_item,parent,false);

        NotifyKeywordAdapter.CustomViewHolder holder=new NotifyKeywordAdapter.CustomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotifyKeywordAdapter.CustomViewHolder holder,  int position) {
        holder.text.setText(arrayList.get(position).getText());
        holder.date.setText(arrayList.get(position).getDate());
        //holder.imageView.setImageURI();
        if (arrayList.get(position).getClear()){
            holder.clear.setVisibility(View.GONE);
        }
        else {
            holder.clear.setVisibility(View.VISIBLE);
        }

        holder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //로컬데이터베이스에서 실제 데이터 삭제 넣기
                arrayList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                Toast.makeText(context, ""+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
       ImageView imageView,clear;
       TextView text,date;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            clear=itemView.findViewById(R.id.keyword_clear);
            imageView=itemView.findViewById(R.id.keyword_image);
            text=itemView.findViewById(R.id.keyword_text);
            date=itemView.findViewById(R.id.keyword_date);
        }
    }
}
