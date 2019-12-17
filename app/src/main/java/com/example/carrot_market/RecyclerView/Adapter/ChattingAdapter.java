package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.ChattingItem;

import java.util.ArrayList;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.CustomViewHolder> {

    ArrayList<ChattingItem> arrayList;
    Context context;

    public ChattingAdapter( Context context , ArrayList<ChattingItem> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChattingAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_item,parent,false);
        ChattingAdapter.CustomViewHolder holder=new ChattingAdapter.CustomViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChattingAdapter.CustomViewHolder holder, int position) {

        holder.contents.setText(arrayList.get(position).getContents());

        Log.e("감지완료","삽입성공!11");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void registerAdapterDataObserver(DataSetObserver dataSetObserver) {
        this.notifyItemInserted(arrayList.size());
        Log.e("감지완료","삽입성공!123213");
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView contents_image;
        TextView image_time,contents_time,contents;
        ProgressBar Image_progress;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        image_time=itemView.findViewById(R.id.chatting_contents_iamge_time);
            contents_time=itemView.findViewById(R.id.chatting_contents_time);
            contents=itemView.findViewById(R.id.chatting_item_contents);
            contents_image=itemView.findViewById(R.id.chatting_item_contents_image);
            Image_progress=itemView.findViewById(R.id.chatting_item_contents_image_progress);


        }
    }
}
