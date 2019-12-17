package com.example.carrot_market.RecyclerView.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.SearchMyLocationItem;

import java.util.ArrayList;

public class SearchMYLocationAdapter extends RecyclerView.Adapter<SearchMYLocationAdapter.CustomViewHolder> {
    ArrayList<SearchMyLocationItem> SearchList;

    public SearchMYLocationAdapter(ArrayList<SearchMyLocationItem> searchList) {
        this.SearchList = searchList;
    }


    @NonNull
    @Override
    public SearchMYLocationAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_my_location_item,parent,false);
        SearchMYLocationAdapter.CustomViewHolder holder=new SearchMYLocationAdapter.CustomViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMYLocationAdapter.CustomViewHolder holder, int i) {

        holder.address.setText(SearchList.get(i).getAddress());

    }

    @Override
    public int getItemCount() {
        return SearchList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView address;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        this.address=itemView.findViewById(R.id.search_my_location_address_item);

        }
    }
}
