package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.NotifyActiveItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotifyActiveAdapter extends RecyclerView.Adapter<NotifyActiveAdapter.CustomViewHolder> {

    ArrayList<NotifyActiveItem> arrayList;
    Context context;

    public NotifyActiveAdapter(ArrayList<NotifyActiveItem> arrayList, Context context) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public NotifyActiveAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.active_item,parent,false);

        NotifyActiveAdapter.CustomViewHolder holder=new NotifyActiveAdapter.CustomViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final NotifyActiveAdapter.CustomViewHolder holder,  int position) {


        //데이터 베이스에서 실제 데이터로 받기
        holder.text.setText(arrayList.get(position).getText());
        holder.date.setText(arrayList.get(position).getDate());
        //holder.imageView.setImageURI();

        if (arrayList.get(position).getClear()) {
            holder.clear.setVisibility(View.GONE);
        }
        else {
            holder.clear.setVisibility(View.VISIBLE);
        }

        holder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //로컬 데이터베이스에서 실제데이터 삭제 넣기
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
        private CircleImageView imageView;
        private TextView text,date;
        private ImageView clear;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            clear=itemView.findViewById(R.id.active_clear);
            imageView=itemView.findViewById(R.id.active_image);
            text=itemView.findViewById(R.id.active_text);
            date=itemView.findViewById(R.id.active_date);

        }
    }
}
