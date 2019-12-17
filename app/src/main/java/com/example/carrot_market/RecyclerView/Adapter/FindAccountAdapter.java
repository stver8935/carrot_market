package com.example.carrot_market.RecyclerView.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.FindAccountItem;

import java.util.ArrayList;

public class FindAccountAdapter extends RecyclerView.Adapter<FindAccountAdapter.CustomViewHolder> {



    private ArrayList<FindAccountItem> arrayList;


    public FindAccountAdapter(ArrayList<FindAccountItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public FindAccountAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.find_account_item,parent,false);
        FindAccountAdapter.CustomViewHolder holder=new FindAccountAdapter.CustomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FindAccountAdapter.CustomViewHolder holder, int position) {
        holder.id.setText(arrayList.get(holder.getAdapterPosition()).getId());
        holder.password.setText(arrayList.get(holder.getAdapterPosition()).getPassword());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView id,password;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        id=itemView.findViewById(R.id.find_account_item_id);
        password=itemView.findViewById(R.id.find_account_item_password);

        }
    }
}
