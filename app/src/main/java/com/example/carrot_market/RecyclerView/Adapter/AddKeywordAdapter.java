package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.AddKeywordItem;
import com.example.carrot_market.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddKeywordAdapter extends RecyclerView.Adapter<AddKeywordAdapter.CustomViewHolder> {

    ArrayList<AddKeywordItem> arrayList;
    TextView count;
    public AddKeywordAdapter(ArrayList<AddKeywordItem> arrayList, Context context,TextView count) {
        this.arrayList = arrayList;
        this.context = context;
        this.count=count;
    }

    Context context;
    @NonNull
    @Override
    public AddKeywordAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_keyword_item,parent,false);
        AddKeywordAdapter.CustomViewHolder holder=new AddKeywordAdapter.CustomViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AddKeywordAdapter.CustomViewHolder holder, int position) {


        holder.keyword.setText(arrayList.get(holder.getAdapterPosition()).getKeyword());

        holder.keyword_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                arrayList.remove(holder.getAdapterPosition());
                count.setText("("+arrayList.size()+"/30)");

                notifyItemRemoved(holder.getAdapterPosition());

                //실제 키워드 데이터 삭제
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView keyword;
        CircleImageView keyword_clear;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            keyword_clear=itemView.findViewById(R.id.add_keyword_item_clear);
            keyword=itemView.findViewById(R.id.add_keyword_item_text);
        }
    }
}
